package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ServiceManager {
   private static final ListenerCallQueue.Event<ServiceManager.Listener> HEALTHY_EVENT = new ListenerCallQueue.Event<ServiceManager.Listener>() {
      public void call(ServiceManager.Listener var1) {
         var1.healthy();
      }

      public String toString() {
         return "healthy()";
      }
   };
   private static final ListenerCallQueue.Event<ServiceManager.Listener> STOPPED_EVENT = new ListenerCallQueue.Event<ServiceManager.Listener>() {
      public void call(ServiceManager.Listener var1) {
         var1.stopped();
      }

      public String toString() {
         return "stopped()";
      }
   };
   private static final Logger logger = Logger.getLogger(ServiceManager.class.getName());
   private final ImmutableList<Service> services;
   private final ServiceManager.ServiceManagerState state;

   public ServiceManager(Iterable<? extends Service> var1) {
      ImmutableList var2 = ImmutableList.copyOf(var1);
      ImmutableList var5 = var2;
      if (var2.isEmpty()) {
         logger.log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", new ServiceManager.EmptyServiceManagerWarning());
         var5 = ImmutableList.of(new ServiceManager.NoOpService());
      }

      this.state = new ServiceManager.ServiceManagerState(var5);
      this.services = var5;
      WeakReference var6 = new WeakReference(this.state);

      boolean var4;
      Service var7;
      for(UnmodifiableIterator var3 = var5.iterator(); var3.hasNext(); Preconditions.checkArgument(var4, "Can only manage NEW services, %s", (Object)var7)) {
         var7 = (Service)var3.next();
         var7.addListener(new ServiceManager.ServiceListener(var7, var6), MoreExecutors.directExecutor());
         if (var7.state() == Service.State.NEW) {
            var4 = true;
         } else {
            var4 = false;
         }
      }

      this.state.markReady();
   }

   public void addListener(ServiceManager.Listener var1) {
      this.state.addListener(var1, MoreExecutors.directExecutor());
   }

   public void addListener(ServiceManager.Listener var1, Executor var2) {
      this.state.addListener(var1, var2);
   }

   public void awaitHealthy() {
      this.state.awaitHealthy();
   }

   public void awaitHealthy(long var1, TimeUnit var3) throws TimeoutException {
      this.state.awaitHealthy(var1, var3);
   }

   public void awaitStopped() {
      this.state.awaitStopped();
   }

   public void awaitStopped(long var1, TimeUnit var3) throws TimeoutException {
      this.state.awaitStopped(var1, var3);
   }

   public boolean isHealthy() {
      UnmodifiableIterator var1 = this.services.iterator();

      do {
         if (!var1.hasNext()) {
            return true;
         }
      } while(((Service)var1.next()).isRunning());

      return false;
   }

   public ImmutableMultimap<Service.State, Service> servicesByState() {
      return this.state.servicesByState();
   }

   public ServiceManager startAsync() {
      UnmodifiableIterator var1;
      Service var2;
      Service.State var3;
      boolean var4;
      for(var1 = this.services.iterator(); var1.hasNext(); Preconditions.checkState(var4, "Service %s is %s, cannot start it.", var2, var3)) {
         var2 = (Service)var1.next();
         var3 = var2.state();
         if (var3 == Service.State.NEW) {
            var4 = true;
         } else {
            var4 = false;
         }
      }

      var1 = this.services.iterator();

      while(var1.hasNext()) {
         Service var5 = (Service)var1.next();

         try {
            this.state.tryStartTiming(var5);
            var5.startAsync();
         } catch (IllegalStateException var8) {
            Logger var7 = logger;
            Level var10 = Level.WARNING;
            StringBuilder var9 = new StringBuilder();
            var9.append("Unable to start Service ");
            var9.append(var5);
            var7.log(var10, var9.toString(), var8);
         }
      }

      return this;
   }

   public ImmutableMap<Service, Long> startupTimes() {
      return this.state.startupTimes();
   }

   public ServiceManager stopAsync() {
      UnmodifiableIterator var1 = this.services.iterator();

      while(var1.hasNext()) {
         ((Service)var1.next()).stopAsync();
      }

      return this;
   }

   public String toString() {
      return MoreObjects.toStringHelper(ServiceManager.class).add("services", Collections2.filter(this.services, Predicates.not(Predicates.instanceOf(ServiceManager.NoOpService.class)))).toString();
   }

   private static final class EmptyServiceManagerWarning extends Throwable {
      private EmptyServiceManagerWarning() {
      }

      // $FF: synthetic method
      EmptyServiceManagerWarning(Object var1) {
         this();
      }
   }

   public abstract static class Listener {
      public void failure(Service var1) {
      }

      public void healthy() {
      }

      public void stopped() {
      }
   }

   private static final class NoOpService extends AbstractService {
      private NoOpService() {
      }

      // $FF: synthetic method
      NoOpService(Object var1) {
         this();
      }

      protected void doStart() {
         this.notifyStarted();
      }

      protected void doStop() {
         this.notifyStopped();
      }
   }

   private static final class ServiceListener extends Service.Listener {
      final Service service;
      final WeakReference<ServiceManager.ServiceManagerState> state;

      ServiceListener(Service var1, WeakReference<ServiceManager.ServiceManagerState> var2) {
         this.service = var1;
         this.state = var2;
      }

      public void failed(Service.State var1, Throwable var2) {
         ServiceManager.ServiceManagerState var3 = (ServiceManager.ServiceManagerState)this.state.get();
         if (var3 != null) {
            if (this.service instanceof ServiceManager.NoOpService ^ true) {
               Logger var4 = ServiceManager.logger;
               Level var5 = Level.SEVERE;
               StringBuilder var6 = new StringBuilder();
               var6.append("Service ");
               var6.append(this.service);
               var6.append(" has failed in the ");
               var6.append(var1);
               var6.append(" state.");
               var4.log(var5, var6.toString(), var2);
            }

            var3.transitionService(this.service, var1, Service.State.FAILED);
         }

      }

      public void running() {
         ServiceManager.ServiceManagerState var1 = (ServiceManager.ServiceManagerState)this.state.get();
         if (var1 != null) {
            var1.transitionService(this.service, Service.State.STARTING, Service.State.RUNNING);
         }

      }

      public void starting() {
         ServiceManager.ServiceManagerState var1 = (ServiceManager.ServiceManagerState)this.state.get();
         if (var1 != null) {
            var1.transitionService(this.service, Service.State.NEW, Service.State.STARTING);
            if (!(this.service instanceof ServiceManager.NoOpService)) {
               ServiceManager.logger.log(Level.FINE, "Starting {0}.", this.service);
            }
         }

      }

      public void stopping(Service.State var1) {
         ServiceManager.ServiceManagerState var2 = (ServiceManager.ServiceManagerState)this.state.get();
         if (var2 != null) {
            var2.transitionService(this.service, var1, Service.State.STOPPING);
         }

      }

      public void terminated(Service.State var1) {
         ServiceManager.ServiceManagerState var2 = (ServiceManager.ServiceManagerState)this.state.get();
         if (var2 != null) {
            if (!(this.service instanceof ServiceManager.NoOpService)) {
               ServiceManager.logger.log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[]{this.service, var1});
            }

            var2.transitionService(this.service, var1, Service.State.TERMINATED);
         }

      }
   }

   private static final class ServiceManagerState {
      final Monitor.Guard awaitHealthGuard;
      final ListenerCallQueue<ServiceManager.Listener> listeners;
      final Monitor monitor = new Monitor();
      final int numberOfServices;
      boolean ready;
      final SetMultimap<Service.State, Service> servicesByState;
      final Map<Service, Stopwatch> startupTimers;
      final Multiset<Service.State> states;
      final Monitor.Guard stoppedGuard;
      boolean transitioned;

      ServiceManagerState(ImmutableCollection<Service> var1) {
         SetMultimap var2 = MultimapBuilder.enumKeys(Service.State.class).linkedHashSetValues().build();
         this.servicesByState = var2;
         this.states = var2.keys();
         this.startupTimers = Maps.newIdentityHashMap();
         this.awaitHealthGuard = new ServiceManager.ServiceManagerState.AwaitHealthGuard();
         this.stoppedGuard = new ServiceManager.ServiceManagerState.StoppedGuard();
         this.listeners = new ListenerCallQueue();
         this.numberOfServices = var1.size();
         this.servicesByState.putAll(Service.State.NEW, var1);
      }

      void addListener(ServiceManager.Listener var1, Executor var2) {
         this.listeners.addListener(var1, var2);
      }

      void awaitHealthy() {
         this.monitor.enterWhenUninterruptibly(this.awaitHealthGuard);

         try {
            this.checkHealthy();
         } finally {
            this.monitor.leave();
         }

      }

      void awaitHealthy(long var1, TimeUnit var3) throws TimeoutException {
         this.monitor.enter();

         try {
            if (!this.monitor.waitForUninterruptibly(this.awaitHealthGuard, var1, var3)) {
               StringBuilder var4 = new StringBuilder();
               var4.append("Timeout waiting for the services to become healthy. The following services have not started: ");
               var4.append(Multimaps.filterKeys(this.servicesByState, Predicates.in(ImmutableSet.of(Service.State.NEW, Service.State.STARTING))));
               TimeoutException var7 = new TimeoutException(var4.toString());
               throw var7;
            }

            this.checkHealthy();
         } finally {
            this.monitor.leave();
         }

      }

      void awaitStopped() {
         this.monitor.enterWhenUninterruptibly(this.stoppedGuard);
         this.monitor.leave();
      }

      void awaitStopped(long var1, TimeUnit var3) throws TimeoutException {
         this.monitor.enter();

         Throwable var10000;
         label72: {
            boolean var10001;
            boolean var4;
            try {
               var4 = this.monitor.waitForUninterruptibly(this.stoppedGuard, var1, var3);
            } catch (Throwable var11) {
               var10000 = var11;
               var10001 = false;
               break label72;
            }

            if (var4) {
               this.monitor.leave();
               return;
            }

            label63:
            try {
               StringBuilder var13 = new StringBuilder();
               var13.append("Timeout waiting for the services to stop. The following services have not stopped: ");
               var13.append(Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.in(EnumSet.of(Service.State.TERMINATED, Service.State.FAILED)))));
               TimeoutException var5 = new TimeoutException(var13.toString());
               throw var5;
            } catch (Throwable var10) {
               var10000 = var10;
               var10001 = false;
               break label63;
            }
         }

         Throwable var12 = var10000;
         this.monitor.leave();
         throw var12;
      }

      void checkHealthy() {
         if (this.states.count(Service.State.RUNNING) != this.numberOfServices) {
            StringBuilder var1 = new StringBuilder();
            var1.append("Expected to be healthy after starting. The following services are not running: ");
            var1.append(Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.equalTo(Service.State.RUNNING))));
            throw new IllegalStateException(var1.toString());
         }
      }

      void dispatchListenerEvents() {
         Preconditions.checkState(this.monitor.isOccupiedByCurrentThread() ^ true, "It is incorrect to execute listeners with the monitor held.");
         this.listeners.dispatch();
      }

      void enqueueFailedEvent(final Service var1) {
         this.listeners.enqueue(new ListenerCallQueue.Event<ServiceManager.Listener>() {
            public void call(ServiceManager.Listener var1x) {
               var1x.failure(var1);
            }

            public String toString() {
               StringBuilder var1x = new StringBuilder();
               var1x.append("failed({service=");
               var1x.append(var1);
               var1x.append("})");
               return var1x.toString();
            }
         });
      }

      void enqueueHealthyEvent() {
         this.listeners.enqueue(ServiceManager.HEALTHY_EVENT);
      }

      void enqueueStoppedEvent() {
         this.listeners.enqueue(ServiceManager.STOPPED_EVENT);
      }

      void markReady() {
         this.monitor.enter();

         Throwable var10000;
         label229: {
            label233: {
               boolean var10001;
               try {
                  if (!this.transitioned) {
                     this.ready = true;
                     break label233;
                  }
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break label229;
               }

               ArrayList var1;
               UnmodifiableIterator var2;
               try {
                  var1 = Lists.newArrayList();
                  var2 = this.servicesByState().values().iterator();
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label229;
               }

               while(true) {
                  try {
                     if (!var2.hasNext()) {
                        break;
                     }

                     Service var3 = (Service)var2.next();
                     if (var3.state() != Service.State.NEW) {
                        var1.add(var3);
                     }
                  } catch (Throwable var22) {
                     var10000 = var22;
                     var10001 = false;
                     break label229;
                  }
               }

               try {
                  StringBuilder var25 = new StringBuilder();
                  var25.append("Services started transitioning asynchronously before the ServiceManager was constructed: ");
                  var25.append(var1);
                  IllegalArgumentException var26 = new IllegalArgumentException(var25.toString());
                  throw var26;
               } catch (Throwable var20) {
                  var10000 = var20;
                  var10001 = false;
                  break label229;
               }
            }

            this.monitor.leave();
            return;
         }

         Throwable var24 = var10000;
         this.monitor.leave();
         throw var24;
      }

      ImmutableMultimap<Service.State, Service> servicesByState() {
         ImmutableSetMultimap.Builder var1 = ImmutableSetMultimap.builder();
         this.monitor.enter();

         label93: {
            Throwable var10000;
            label92: {
               boolean var10001;
               Iterator var2;
               try {
                  var2 = this.servicesByState.entries().iterator();
               } catch (Throwable var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label92;
               }

               while(true) {
                  try {
                     Entry var3;
                     do {
                        if (!var2.hasNext()) {
                           break label93;
                        }

                        var3 = (Entry)var2.next();
                     } while(var3.getValue() instanceof ServiceManager.NoOpService);

                     var1.put(var3);
                  } catch (Throwable var8) {
                     var10000 = var8;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var10 = var10000;
            this.monitor.leave();
            throw var10;
         }

         this.monitor.leave();
         return var1.build();
      }

      ImmutableMap<Service, Long> startupTimes() {
         this.monitor.enter();

         ArrayList var1;
         label108: {
            Throwable var10000;
            label107: {
               boolean var10001;
               Iterator var2;
               try {
                  var1 = Lists.newArrayListWithCapacity(this.startupTimers.size());
                  var2 = this.startupTimers.entrySet().iterator();
               } catch (Throwable var10) {
                  var10000 = var10;
                  var10001 = false;
                  break label107;
               }

               while(true) {
                  try {
                     Service var4;
                     Stopwatch var12;
                     do {
                        do {
                           if (!var2.hasNext()) {
                              break label108;
                           }

                           Entry var3 = (Entry)var2.next();
                           var4 = (Service)var3.getKey();
                           var12 = (Stopwatch)var3.getValue();
                        } while(var12.isRunning());
                     } while(var4 instanceof ServiceManager.NoOpService);

                     var1.add(Maps.immutableEntry(var4, var12.elapsed(TimeUnit.MILLISECONDS)));
                  } catch (Throwable var9) {
                     var10000 = var9;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var11 = var10000;
            this.monitor.leave();
            throw var11;
         }

         this.monitor.leave();
         Collections.sort(var1, Ordering.natural().onResultOf(new Function<Entry<Service, Long>, Long>() {
            public Long apply(Entry<Service, Long> var1) {
               return (Long)var1.getValue();
            }
         }));
         return ImmutableMap.copyOf((Iterable)var1);
      }

      void transitionService(Service var1, Service.State var2, Service.State var3) {
         Preconditions.checkNotNull(var1);
         boolean var4;
         if (var2 != var3) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4);
         this.monitor.enter();

         Throwable var10000;
         label593: {
            boolean var10001;
            try {
               this.transitioned = true;
               var4 = this.ready;
            } catch (Throwable var61) {
               var10000 = var61;
               var10001 = false;
               break label593;
            }

            if (!var4) {
               this.monitor.leave();
               this.dispatchListenerEvents();
               return;
            }

            Stopwatch var5;
            try {
               Preconditions.checkState(this.servicesByState.remove(var2, var1), "Service %s not at the expected location in the state map %s", var1, var2);
               Preconditions.checkState(this.servicesByState.put(var3, var1), "Service %s in the state map unexpectedly at %s", var1, var3);
               var5 = (Stopwatch)this.startupTimers.get(var1);
            } catch (Throwable var60) {
               var10000 = var60;
               var10001 = false;
               break label593;
            }

            Stopwatch var63 = var5;
            if (var5 == null) {
               try {
                  var63 = Stopwatch.createStarted();
                  this.startupTimers.put(var1, var63);
               } catch (Throwable var59) {
                  var10000 = var59;
                  var10001 = false;
                  break label593;
               }
            }

            try {
               if (var3.compareTo(Service.State.RUNNING) >= 0 && var63.isRunning()) {
                  var63.stop();
                  if (!(var1 instanceof ServiceManager.NoOpService)) {
                     ServiceManager.logger.log(Level.FINE, "Started {0} in {1}.", new Object[]{var1, var63});
                  }
               }
            } catch (Throwable var58) {
               var10000 = var58;
               var10001 = false;
               break label593;
            }

            try {
               if (var3 == Service.State.FAILED) {
                  this.enqueueFailedEvent(var1);
               }
            } catch (Throwable var57) {
               var10000 = var57;
               var10001 = false;
               break label593;
            }

            label570: {
               try {
                  if (this.states.count(Service.State.RUNNING) == this.numberOfServices) {
                     this.enqueueHealthyEvent();
                     break label570;
                  }
               } catch (Throwable var56) {
                  var10000 = var56;
                  var10001 = false;
                  break label593;
               }

               try {
                  if (this.states.count(Service.State.TERMINATED) + this.states.count(Service.State.FAILED) == this.numberOfServices) {
                     this.enqueueStoppedEvent();
                  }
               } catch (Throwable var55) {
                  var10000 = var55;
                  var10001 = false;
                  break label593;
               }
            }

            this.monitor.leave();
            this.dispatchListenerEvents();
            return;
         }

         Throwable var62 = var10000;
         this.monitor.leave();
         this.dispatchListenerEvents();
         throw var62;
      }

      void tryStartTiming(Service var1) {
         this.monitor.enter();

         try {
            if ((Stopwatch)this.startupTimers.get(var1) == null) {
               this.startupTimers.put(var1, Stopwatch.createStarted());
            }
         } finally {
            this.monitor.leave();
         }

      }

      final class AwaitHealthGuard extends Monitor.Guard {
         AwaitHealthGuard() {
            super(ServiceManagerState.this.monitor);
         }

         public boolean isSatisfied() {
            boolean var1;
            if (ServiceManagerState.this.states.count(Service.State.RUNNING) != ServiceManagerState.this.numberOfServices && !ServiceManagerState.this.states.contains(Service.State.STOPPING) && !ServiceManagerState.this.states.contains(Service.State.TERMINATED) && !ServiceManagerState.this.states.contains(Service.State.FAILED)) {
               var1 = false;
            } else {
               var1 = true;
            }

            return var1;
         }
      }

      final class StoppedGuard extends Monitor.Guard {
         StoppedGuard() {
            super(ServiceManagerState.this.monitor);
         }

         public boolean isSatisfied() {
            boolean var1;
            if (ServiceManagerState.this.states.count(Service.State.TERMINATED) + ServiceManagerState.this.states.count(Service.State.FAILED) == ServiceManagerState.this.numberOfServices) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }
      }
   }
}

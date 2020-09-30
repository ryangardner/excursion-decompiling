package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractService implements Service {
   private static final ListenerCallQueue.Event<Service.Listener> RUNNING_EVENT = new ListenerCallQueue.Event<Service.Listener>() {
      public void call(Service.Listener var1) {
         var1.running();
      }

      public String toString() {
         return "running()";
      }
   };
   private static final ListenerCallQueue.Event<Service.Listener> STARTING_EVENT = new ListenerCallQueue.Event<Service.Listener>() {
      public void call(Service.Listener var1) {
         var1.starting();
      }

      public String toString() {
         return "starting()";
      }
   };
   private static final ListenerCallQueue.Event<Service.Listener> STOPPING_FROM_RUNNING_EVENT;
   private static final ListenerCallQueue.Event<Service.Listener> STOPPING_FROM_STARTING_EVENT;
   private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_NEW_EVENT;
   private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_RUNNING_EVENT;
   private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_STARTING_EVENT;
   private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_STOPPING_EVENT;
   private final Monitor.Guard hasReachedRunning = new AbstractService.HasReachedRunningGuard();
   private final Monitor.Guard isStartable = new AbstractService.IsStartableGuard();
   private final Monitor.Guard isStoppable = new AbstractService.IsStoppableGuard();
   private final Monitor.Guard isStopped = new AbstractService.IsStoppedGuard();
   private final ListenerCallQueue<Service.Listener> listeners = new ListenerCallQueue();
   private final Monitor monitor = new Monitor();
   private volatile AbstractService.StateSnapshot snapshot;

   static {
      STOPPING_FROM_STARTING_EVENT = stoppingEvent(Service.State.STARTING);
      STOPPING_FROM_RUNNING_EVENT = stoppingEvent(Service.State.RUNNING);
      TERMINATED_FROM_NEW_EVENT = terminatedEvent(Service.State.NEW);
      TERMINATED_FROM_STARTING_EVENT = terminatedEvent(Service.State.STARTING);
      TERMINATED_FROM_RUNNING_EVENT = terminatedEvent(Service.State.RUNNING);
      TERMINATED_FROM_STOPPING_EVENT = terminatedEvent(Service.State.STOPPING);
   }

   protected AbstractService() {
      this.snapshot = new AbstractService.StateSnapshot(Service.State.NEW);
   }

   private void checkCurrentState(Service.State var1) {
      Service.State var2 = this.state();
      if (var2 != var1) {
         StringBuilder var3;
         if (var2 == Service.State.FAILED) {
            var3 = new StringBuilder();
            var3.append("Expected the service ");
            var3.append(this);
            var3.append(" to be ");
            var3.append(var1);
            var3.append(", but the service has FAILED");
            throw new IllegalStateException(var3.toString(), this.failureCause());
         } else {
            var3 = new StringBuilder();
            var3.append("Expected the service ");
            var3.append(this);
            var3.append(" to be ");
            var3.append(var1);
            var3.append(", but was ");
            var3.append(var2);
            throw new IllegalStateException(var3.toString());
         }
      }
   }

   private void dispatchListenerEvents() {
      if (!this.monitor.isOccupiedByCurrentThread()) {
         this.listeners.dispatch();
      }

   }

   private void enqueueFailedEvent(final Service.State var1, final Throwable var2) {
      this.listeners.enqueue(new ListenerCallQueue.Event<Service.Listener>() {
         public void call(Service.Listener var1x) {
            var1x.failed(var1, var2);
         }

         public String toString() {
            StringBuilder var1x = new StringBuilder();
            var1x.append("failed({from = ");
            var1x.append(var1);
            var1x.append(", cause = ");
            var1x.append(var2);
            var1x.append("})");
            return var1x.toString();
         }
      });
   }

   private void enqueueRunningEvent() {
      this.listeners.enqueue(RUNNING_EVENT);
   }

   private void enqueueStartingEvent() {
      this.listeners.enqueue(STARTING_EVENT);
   }

   private void enqueueStoppingEvent(Service.State var1) {
      if (var1 == Service.State.STARTING) {
         this.listeners.enqueue(STOPPING_FROM_STARTING_EVENT);
      } else {
         if (var1 != Service.State.RUNNING) {
            throw new AssertionError();
         }

         this.listeners.enqueue(STOPPING_FROM_RUNNING_EVENT);
      }

   }

   private void enqueueTerminatedEvent(Service.State var1) {
      switch(null.$SwitchMap$com$google$common$util$concurrent$Service$State[var1.ordinal()]) {
      case 1:
         this.listeners.enqueue(TERMINATED_FROM_NEW_EVENT);
         break;
      case 2:
         this.listeners.enqueue(TERMINATED_FROM_STARTING_EVENT);
         break;
      case 3:
         this.listeners.enqueue(TERMINATED_FROM_RUNNING_EVENT);
         break;
      case 4:
         this.listeners.enqueue(TERMINATED_FROM_STOPPING_EVENT);
         break;
      case 5:
      case 6:
         throw new AssertionError();
      }

   }

   private static ListenerCallQueue.Event<Service.Listener> stoppingEvent(final Service.State var0) {
      return new ListenerCallQueue.Event<Service.Listener>() {
         public void call(Service.Listener var1) {
            var1.stopping(var0);
         }

         public String toString() {
            StringBuilder var1 = new StringBuilder();
            var1.append("stopping({from = ");
            var1.append(var0);
            var1.append("})");
            return var1.toString();
         }
      };
   }

   private static ListenerCallQueue.Event<Service.Listener> terminatedEvent(final Service.State var0) {
      return new ListenerCallQueue.Event<Service.Listener>() {
         public void call(Service.Listener var1) {
            var1.terminated(var0);
         }

         public String toString() {
            StringBuilder var1 = new StringBuilder();
            var1.append("terminated({from = ");
            var1.append(var0);
            var1.append("})");
            return var1.toString();
         }
      };
   }

   public final void addListener(Service.Listener var1, Executor var2) {
      this.listeners.addListener(var1, var2);
   }

   public final void awaitRunning() {
      this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);

      try {
         this.checkCurrentState(Service.State.RUNNING);
      } finally {
         this.monitor.leave();
      }

   }

   public final void awaitRunning(long var1, TimeUnit var3) throws TimeoutException {
      if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, var1, var3)) {
         try {
            this.checkCurrentState(Service.State.RUNNING);
         } finally {
            this.monitor.leave();
         }

      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Timed out waiting for ");
         var6.append(this);
         var6.append(" to reach the RUNNING state.");
         throw new TimeoutException(var6.toString());
      }
   }

   public final void awaitTerminated() {
      this.monitor.enterWhenUninterruptibly(this.isStopped);

      try {
         this.checkCurrentState(Service.State.TERMINATED);
      } finally {
         this.monitor.leave();
      }

   }

   public final void awaitTerminated(long var1, TimeUnit var3) throws TimeoutException {
      if (this.monitor.enterWhenUninterruptibly(this.isStopped, var1, var3)) {
         try {
            this.checkCurrentState(Service.State.TERMINATED);
         } finally {
            this.monitor.leave();
         }

      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Timed out waiting for ");
         var6.append(this);
         var6.append(" to reach a terminal state. Current state: ");
         var6.append(this.state());
         throw new TimeoutException(var6.toString());
      }
   }

   protected void doCancelStart() {
   }

   protected abstract void doStart();

   protected abstract void doStop();

   public final Throwable failureCause() {
      return this.snapshot.failureCause();
   }

   public final boolean isRunning() {
      boolean var1;
      if (this.state() == Service.State.RUNNING) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected final void notifyFailed(Throwable var1) {
      Preconditions.checkNotNull(var1);
      this.monitor.enter();

      Throwable var10000;
      label171: {
         boolean var10001;
         Service.State var2;
         int var3;
         try {
            var2 = this.state();
            var3 = null.$SwitchMap$com$google$common$util$concurrent$Service$State[var2.ordinal()];
         } catch (Throwable var17) {
            var10000 = var17;
            var10001 = false;
            break label171;
         }

         if (var3 != 1) {
            label172: {
               if (var3 != 2 && var3 != 3 && var3 != 4) {
                  if (var3 == 5) {
                     break label172;
                  }
               } else {
                  try {
                     AbstractService.StateSnapshot var4 = new AbstractService.StateSnapshot(Service.State.FAILED, false, var1);
                     this.snapshot = var4;
                     this.enqueueFailedEvent(var2, var1);
                  } catch (Throwable var16) {
                     var10000 = var16;
                     var10001 = false;
                     break label171;
                  }
               }

               this.monitor.leave();
               this.dispatchListenerEvents();
               return;
            }
         }

         label148:
         try {
            StringBuilder var18 = new StringBuilder();
            var18.append("Failed while in state:");
            var18.append(var2);
            IllegalStateException var5 = new IllegalStateException(var18.toString(), var1);
            throw var5;
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label148;
         }
      }

      var1 = var10000;
      this.monitor.leave();
      this.dispatchListenerEvents();
      throw var1;
   }

   protected final void notifyStarted() {
      this.monitor.enter();

      try {
         if (this.snapshot.state != Service.State.STARTING) {
            StringBuilder var5 = new StringBuilder();
            var5.append("Cannot notifyStarted() when the service is ");
            var5.append(this.snapshot.state);
            IllegalStateException var2 = new IllegalStateException(var5.toString());
            this.notifyFailed(var2);
            throw var2;
         }

         AbstractService.StateSnapshot var1;
         if (this.snapshot.shutdownWhenStartupFinishes) {
            var1 = new AbstractService.StateSnapshot(Service.State.STOPPING);
            this.snapshot = var1;
            this.doStop();
         } else {
            var1 = new AbstractService.StateSnapshot(Service.State.RUNNING);
            this.snapshot = var1;
            this.enqueueRunningEvent();
         }
      } finally {
         this.monitor.leave();
         this.dispatchListenerEvents();
      }

   }

   protected final void notifyStopped() {
      this.monitor.enter();

      try {
         Service.State var1 = this.state();
         switch(null.$SwitchMap$com$google$common$util$concurrent$Service$State[var1.ordinal()]) {
         case 1:
         case 5:
         case 6:
            StringBuilder var3 = new StringBuilder();
            var3.append("Cannot notifyStopped() when the service is ");
            var3.append(var1);
            IllegalStateException var6 = new IllegalStateException(var3.toString());
            throw var6;
         case 2:
         case 3:
         case 4:
            AbstractService.StateSnapshot var2 = new AbstractService.StateSnapshot(Service.State.TERMINATED);
            this.snapshot = var2;
            this.enqueueTerminatedEvent(var1);
         }
      } finally {
         this.monitor.leave();
         this.dispatchListenerEvents();
      }

   }

   public final Service startAsync() {
      if (this.monitor.enterIf(this.isStartable)) {
         try {
            AbstractService.StateSnapshot var9 = new AbstractService.StateSnapshot(Service.State.STARTING);
            this.snapshot = var9;
            this.enqueueStartingEvent();
            this.doStart();
         } catch (Throwable var7) {
            Throwable var8 = var7;

            try {
               this.notifyFailed(var8);
               return this;
            } finally {
               this.monitor.leave();
               this.dispatchListenerEvents();
            }
         }

         return this;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("Service ");
         var1.append(this);
         var1.append(" has already been started");
         throw new IllegalStateException(var1.toString());
      }
   }

   public final Service.State state() {
      return this.snapshot.externalState();
   }

   public final Service stopAsync() {
      if (this.monitor.enterIf(this.isStoppable)) {
         try {
            Service.State var1 = this.state();
            AbstractService.StateSnapshot var11;
            switch(null.$SwitchMap$com$google$common$util$concurrent$Service$State[var1.ordinal()]) {
            case 1:
               var11 = new AbstractService.StateSnapshot(Service.State.TERMINATED);
               this.snapshot = var11;
               this.enqueueTerminatedEvent(Service.State.NEW);
               break;
            case 2:
               var11 = new AbstractService.StateSnapshot(Service.State.STARTING, true, (Throwable)null);
               this.snapshot = var11;
               this.enqueueStoppingEvent(Service.State.STARTING);
               this.doCancelStart();
               break;
            case 3:
               var11 = new AbstractService.StateSnapshot(Service.State.STOPPING);
               this.snapshot = var11;
               this.enqueueStoppingEvent(Service.State.RUNNING);
               this.doStop();
               break;
            case 4:
            case 5:
            case 6:
               StringBuilder var10 = new StringBuilder();
               var10.append("isStoppable is incorrectly implemented, saw: ");
               var10.append(var1);
               AssertionError var2 = new AssertionError(var10.toString());
               throw var2;
            }
         } catch (Throwable var9) {
            Throwable var3 = var9;

            try {
               this.notifyFailed(var3);
               return this;
            } finally {
               this.monitor.leave();
               this.dispatchListenerEvents();
            }
         }
      }

      return this;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getClass().getSimpleName());
      var1.append(" [");
      var1.append(this.state());
      var1.append("]");
      return var1.toString();
   }

   private final class HasReachedRunningGuard extends Monitor.Guard {
      HasReachedRunningGuard() {
         super(AbstractService.this.monitor);
      }

      public boolean isSatisfied() {
         boolean var1;
         if (AbstractService.this.state().compareTo(Service.State.RUNNING) >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   private final class IsStartableGuard extends Monitor.Guard {
      IsStartableGuard() {
         super(AbstractService.this.monitor);
      }

      public boolean isSatisfied() {
         boolean var1;
         if (AbstractService.this.state() == Service.State.NEW) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   private final class IsStoppableGuard extends Monitor.Guard {
      IsStoppableGuard() {
         super(AbstractService.this.monitor);
      }

      public boolean isSatisfied() {
         boolean var1;
         if (AbstractService.this.state().compareTo(Service.State.RUNNING) <= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   private final class IsStoppedGuard extends Monitor.Guard {
      IsStoppedGuard() {
         super(AbstractService.this.monitor);
      }

      public boolean isSatisfied() {
         return AbstractService.this.state().isTerminal();
      }
   }

   private static final class StateSnapshot {
      @NullableDecl
      final Throwable failure;
      final boolean shutdownWhenStartupFinishes;
      final Service.State state;

      StateSnapshot(Service.State var1) {
         this(var1, false, (Throwable)null);
      }

      StateSnapshot(Service.State var1, boolean var2, @NullableDecl Throwable var3) {
         boolean var4 = false;
         boolean var5;
         if (var2 && var1 != Service.State.STARTING) {
            var5 = false;
         } else {
            var5 = true;
         }

         Preconditions.checkArgument(var5, "shutdownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", (Object)var1);
         boolean var6;
         if (var3 != null) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var1 == Service.State.FAILED) {
            var4 = true;
         }

         Preconditions.checkArgument(var4 ^ var6 ^ true, "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", var1, var3);
         this.state = var1;
         this.shutdownWhenStartupFinishes = var2;
         this.failure = var3;
      }

      Service.State externalState() {
         return this.shutdownWhenStartupFinishes && this.state == Service.State.STARTING ? Service.State.STOPPING : this.state;
      }

      Throwable failureCause() {
         boolean var1;
         if (this.state == Service.State.FAILED) {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkState(var1, "failureCause() is only valid if the service has failed, service is %s", (Object)this.state);
         return this.failure;
      }
   }
}

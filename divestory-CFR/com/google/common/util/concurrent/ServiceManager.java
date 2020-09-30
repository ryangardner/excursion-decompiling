/*
 * Decompiled with CFR <Could not determine version>.
 */
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
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.ListenerCallQueue;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ServiceManager {
    private static final ListenerCallQueue.Event<Listener> HEALTHY_EVENT;
    private static final ListenerCallQueue.Event<Listener> STOPPED_EVENT;
    private static final Logger logger;
    private final ImmutableList<Service> services;
    private final ServiceManagerState state;

    static {
        logger = Logger.getLogger(ServiceManager.class.getName());
        HEALTHY_EVENT = new ListenerCallQueue.Event<Listener>(){

            @Override
            public void call(Listener listener) {
                listener.healthy();
            }

            public String toString() {
                return "healthy()";
            }
        };
        STOPPED_EVENT = new ListenerCallQueue.Event<Listener>(){

            @Override
            public void call(Listener listener) {
                listener.stopped();
            }

            public String toString() {
                return "stopped()";
            }
        };
    }

    public ServiceManager(Iterable<? extends Service> object) {
        Object object2;
        object = object2 = ImmutableList.copyOf(object);
        if (((AbstractCollection)object2).isEmpty()) {
            logger.log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", new EmptyServiceManagerWarning());
            object = ImmutableList.of(new NoOpService());
        }
        this.state = new ServiceManagerState((ImmutableCollection<Service>)object);
        this.services = object;
        object2 = new WeakReference<ServiceManagerState>(this.state);
        Iterator iterator2 = ((ImmutableList)object).iterator();
        do {
            if (!iterator2.hasNext()) {
                this.state.markReady();
                return;
            }
            object = (Service)iterator2.next();
            object.addListener(new ServiceListener((Service)object, (WeakReference<ServiceManagerState>)object2), MoreExecutors.directExecutor());
            boolean bl = object.state() == Service.State.NEW;
            Preconditions.checkArgument(bl, "Can only manage NEW services, %s", object);
        } while (true);
    }

    public void addListener(Listener listener) {
        this.state.addListener(listener, MoreExecutors.directExecutor());
    }

    public void addListener(Listener listener, Executor executor) {
        this.state.addListener(listener, executor);
    }

    public void awaitHealthy() {
        this.state.awaitHealthy();
    }

    public void awaitHealthy(long l, TimeUnit timeUnit) throws TimeoutException {
        this.state.awaitHealthy(l, timeUnit);
    }

    public void awaitStopped() {
        this.state.awaitStopped();
    }

    public void awaitStopped(long l, TimeUnit timeUnit) throws TimeoutException {
        this.state.awaitStopped(l, timeUnit);
    }

    public boolean isHealthy() {
        Iterator iterator2 = this.services.iterator();
        do {
            if (!iterator2.hasNext()) return true;
        } while (((Service)iterator2.next()).isRunning());
        return false;
    }

    public ImmutableMultimap<Service.State, Service> servicesByState() {
        return this.state.servicesByState();
    }

    public ServiceManager startAsync() {
        Object object;
        for (Service object2 : this.services) {
            object = object2.state();
            boolean bl = object == Service.State.NEW;
            Preconditions.checkState(bl, "Service %s is %s, cannot start it.", (Object)object2, object);
        }
        Iterator iterator2 = this.services.iterator();
        while (iterator2.hasNext()) {
            Service service = (Service)iterator2.next();
            try {
                this.state.tryStartTiming(service);
                service.startAsync();
            }
            catch (IllegalStateException illegalStateException) {
                Logger logger = ServiceManager.logger;
                object = Level.WARNING;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to start Service ");
                stringBuilder.append(service);
                logger.log((Level)object, stringBuilder.toString(), illegalStateException);
            }
        }
        return this;
    }

    public ImmutableMap<Service, Long> startupTimes() {
        return this.state.startupTimes();
    }

    public ServiceManager stopAsync() {
        Iterator iterator2 = this.services.iterator();
        while (iterator2.hasNext()) {
            ((Service)iterator2.next()).stopAsync();
        }
        return this;
    }

    public String toString() {
        return MoreObjects.toStringHelper(ServiceManager.class).add("services", Collections2.filter(this.services, Predicates.not(Predicates.instanceOf(NoOpService.class)))).toString();
    }

    private static final class EmptyServiceManagerWarning
    extends Throwable {
        private EmptyServiceManagerWarning() {
        }
    }

    public static abstract class Listener {
        public void failure(Service service) {
        }

        public void healthy() {
        }

        public void stopped() {
        }
    }

    private static final class NoOpService
    extends AbstractService {
        private NoOpService() {
        }

        @Override
        protected void doStart() {
            this.notifyStarted();
        }

        @Override
        protected void doStop() {
            this.notifyStopped();
        }
    }

    private static final class ServiceListener
    extends Service.Listener {
        final Service service;
        final WeakReference<ServiceManagerState> state;

        ServiceListener(Service service, WeakReference<ServiceManagerState> weakReference) {
            this.service = service;
            this.state = weakReference;
        }

        @Override
        public void failed(Service.State state, Throwable throwable) {
            ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState == null) return;
            if (this.service instanceof NoOpService ^ true) {
                Logger logger = logger;
                Level level = Level.SEVERE;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Service ");
                stringBuilder.append(this.service);
                stringBuilder.append(" has failed in the ");
                stringBuilder.append((Object)state);
                stringBuilder.append(" state.");
                logger.log(level, stringBuilder.toString(), throwable);
            }
            serviceManagerState.transitionService(this.service, state, Service.State.FAILED);
        }

        @Override
        public void running() {
            ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState == null) return;
            serviceManagerState.transitionService(this.service, Service.State.STARTING, Service.State.RUNNING);
        }

        @Override
        public void starting() {
            ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState == null) return;
            serviceManagerState.transitionService(this.service, Service.State.NEW, Service.State.STARTING);
            if (this.service instanceof NoOpService) return;
            logger.log(Level.FINE, "Starting {0}.", this.service);
        }

        @Override
        public void stopping(Service.State state) {
            ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState == null) return;
            serviceManagerState.transitionService(this.service, state, Service.State.STOPPING);
        }

        @Override
        public void terminated(Service.State state) {
            ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState == null) return;
            if (!(this.service instanceof NoOpService)) {
                logger.log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[]{this.service, state});
            }
            serviceManagerState.transitionService(this.service, state, Service.State.TERMINATED);
        }
    }

    private static final class ServiceManagerState {
        final Monitor.Guard awaitHealthGuard;
        final ListenerCallQueue<Listener> listeners;
        final Monitor monitor = new Monitor();
        final int numberOfServices;
        boolean ready;
        final SetMultimap<Service.State, Service> servicesByState;
        final Map<Service, Stopwatch> startupTimers;
        final Multiset<Service.State> states;
        final Monitor.Guard stoppedGuard;
        boolean transitioned;

        ServiceManagerState(ImmutableCollection<Service> immutableCollection) {
            Multimap multimap = MultimapBuilder.enumKeys(Service.State.class).linkedHashSetValues().build();
            this.servicesByState = multimap;
            this.states = multimap.keys();
            this.startupTimers = Maps.newIdentityHashMap();
            this.awaitHealthGuard = new AwaitHealthGuard();
            this.stoppedGuard = new StoppedGuard();
            this.listeners = new ListenerCallQueue();
            this.numberOfServices = immutableCollection.size();
            this.servicesByState.putAll(Service.State.NEW, immutableCollection);
        }

        void addListener(Listener listener, Executor executor) {
            this.listeners.addListener(listener, executor);
        }

        void awaitHealthy() {
            this.monitor.enterWhenUninterruptibly(this.awaitHealthGuard);
            try {
                this.checkHealthy();
                return;
            }
            finally {
                this.monitor.leave();
            }
        }

        void awaitHealthy(long l, TimeUnit object) throws TimeoutException {
            this.monitor.enter();
            try {
                if (this.monitor.waitForUninterruptibly(this.awaitHealthGuard, l, (TimeUnit)((Object)object))) {
                    this.checkHealthy();
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Timeout waiting for the services to become healthy. The following services have not started: ");
                stringBuilder.append(Multimaps.filterKeys(this.servicesByState, Predicates.in(ImmutableSet.of(Service.State.NEW, Service.State.STARTING))));
                object = new TimeoutException(stringBuilder.toString());
                throw object;
            }
            finally {
                this.monitor.leave();
            }
        }

        void awaitStopped() {
            this.monitor.enterWhenUninterruptibly(this.stoppedGuard);
            this.monitor.leave();
        }

        void awaitStopped(long l, TimeUnit object) throws TimeoutException {
            this.monitor.enter();
            try {
                boolean bl = this.monitor.waitForUninterruptibly(this.stoppedGuard, l, (TimeUnit)((Object)object));
                if (bl) {
                    this.monitor.leave();
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Timeout waiting for the services to stop. The following services have not stopped: ");
                ((StringBuilder)object).append(Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.in(EnumSet.of(Service.State.TERMINATED, Service.State.FAILED)))));
                TimeoutException timeoutException = new TimeoutException(((StringBuilder)object).toString());
                throw timeoutException;
            }
            catch (Throwable throwable) {
                this.monitor.leave();
                throw throwable;
            }
        }

        void checkHealthy() {
            if (this.states.count((Object)Service.State.RUNNING) == this.numberOfServices) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected to be healthy after starting. The following services are not running: ");
            stringBuilder.append(Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.equalTo(Service.State.RUNNING))));
            throw new IllegalStateException(stringBuilder.toString());
        }

        void dispatchListenerEvents() {
            Preconditions.checkState(this.monitor.isOccupiedByCurrentThread() ^ true, "It is incorrect to execute listeners with the monitor held.");
            this.listeners.dispatch();
        }

        void enqueueFailedEvent(final Service service) {
            this.listeners.enqueue(new ListenerCallQueue.Event<Listener>(){

                @Override
                public void call(Listener listener) {
                    listener.failure(service);
                }

                public String toString() {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("failed({service=");
                    stringBuilder.append(service);
                    stringBuilder.append("})");
                    return stringBuilder.toString();
                }
            });
        }

        void enqueueHealthyEvent() {
            this.listeners.enqueue(HEALTHY_EVENT);
        }

        void enqueueStoppedEvent() {
            this.listeners.enqueue(STOPPED_EVENT);
        }

        void markReady() {
            this.monitor.enter();
            try {
                Object object2;
                if (!this.transitioned) {
                    this.ready = true;
                    return;
                }
                ArrayList<Object> arrayList = Lists.newArrayList();
                for (Object object2 : this.servicesByState().values()) {
                    if (object2.state() == Service.State.NEW) continue;
                    arrayList.add(object2);
                }
                Object object3 = new StringBuilder();
                ((StringBuilder)object3).append("Services started transitioning asynchronously before the ServiceManager was constructed: ");
                ((StringBuilder)object3).append(arrayList);
                object2 = new IllegalArgumentException(((StringBuilder)object3).toString());
                throw object2;
            }
            finally {
                this.monitor.leave();
            }
        }

        ImmutableMultimap<Service.State, Service> servicesByState() {
            ImmutableSetMultimap.Builder builder = ImmutableSetMultimap.builder();
            this.monitor.enter();
            try {
                Iterator<Map.Entry<Service.State, Service>> iterator2 = this.servicesByState.entries().iterator();
                while (iterator2.hasNext()) {
                    Map.Entry<Service.State, Service> entry = iterator2.next();
                    if (entry.getValue() instanceof NoOpService) continue;
                    builder.put(entry);
                }
                return builder.build();
            }
            finally {
                this.monitor.leave();
            }
        }

        ImmutableMap<Service, Long> startupTimes() {
            this.monitor.enter();
            ArrayList<Map.Entry<Service, Long>> arrayList = Lists.newArrayListWithCapacity(this.startupTimers.size());
            for (Map.Entry<Service, Stopwatch> entry : this.startupTimers.entrySet()) {
                Service service = entry.getKey();
                Stopwatch object = entry.getValue();
                if (object.isRunning() || service instanceof NoOpService) continue;
                arrayList.add(Maps.immutableEntry(service, object.elapsed(TimeUnit.MILLISECONDS)));
            }
            Collections.sort(arrayList, Ordering.natural().onResultOf(new Function<Map.Entry<Service, Long>, Long>(){

                @Override
                public Long apply(Map.Entry<Service, Long> entry) {
                    return entry.getValue();
                }
            }));
            return ImmutableMap.copyOf(arrayList);
            finally {
                this.monitor.leave();
            }
        }

        void transitionService(Service service, Service.State object, Service.State state) {
            Preconditions.checkNotNull(service);
            boolean bl = object != state;
            Preconditions.checkArgument(bl);
            this.monitor.enter();
            this.transitioned = true;
            bl = this.ready;
            if (!bl) {
                this.monitor.leave();
                this.dispatchListenerEvents();
                return;
            }
            Preconditions.checkState(this.servicesByState.remove(object, service), "Service %s not at the expected location in the state map %s", (Object)service, object);
            Preconditions.checkState(this.servicesByState.put(state, service), "Service %s in the state map unexpectedly at %s", (Object)service, (Object)state);
            Stopwatch stopwatch = this.startupTimers.get(service);
            object = stopwatch;
            if (stopwatch == null) {
                object = Stopwatch.createStarted();
                this.startupTimers.put(service, (Stopwatch)object);
            }
            if (state.compareTo(Service.State.RUNNING) >= 0 && ((Stopwatch)object).isRunning()) {
                ((Stopwatch)object).stop();
                if (!(service instanceof NoOpService)) {
                    logger.log(Level.FINE, "Started {0} in {1}.", new Object[]{service, object});
                }
            }
            if (state == Service.State.FAILED) {
                this.enqueueFailedEvent(service);
            }
            if (this.states.count((Object)Service.State.RUNNING) == this.numberOfServices) {
                this.enqueueHealthyEvent();
                return;
            }
            if (this.states.count((Object)Service.State.TERMINATED) + this.states.count((Object)Service.State.FAILED) != this.numberOfServices) return;
            this.enqueueStoppedEvent();
            return;
        }

        void tryStartTiming(Service service) {
            this.monitor.enter();
            try {
                if (this.startupTimers.get(service) != null) return;
                this.startupTimers.put(service, Stopwatch.createStarted());
                return;
            }
            finally {
                this.monitor.leave();
            }
        }

        final class AwaitHealthGuard
        extends Monitor.Guard {
            AwaitHealthGuard() {
                super(ServiceManagerState.this.monitor);
            }

            @Override
            public boolean isSatisfied() {
                if (ServiceManagerState.this.states.count((Object)Service.State.RUNNING) == ServiceManagerState.this.numberOfServices) return true;
                if (ServiceManagerState.this.states.contains((Object)Service.State.STOPPING)) return true;
                if (ServiceManagerState.this.states.contains((Object)Service.State.TERMINATED)) return true;
                if (ServiceManagerState.this.states.contains((Object)Service.State.FAILED)) return true;
                return false;
            }
        }

        final class StoppedGuard
        extends Monitor.Guard {
            StoppedGuard() {
                super(ServiceManagerState.this.monitor);
            }

            @Override
            public boolean isSatisfied() {
                if (ServiceManagerState.this.states.count((Object)Service.State.TERMINATED) + ServiceManagerState.this.states.count((Object)Service.State.FAILED) != ServiceManagerState.this.numberOfServices) return false;
                return true;
            }
        }

    }

}


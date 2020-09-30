/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenerCallQueue;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.Service;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractService
implements Service {
    private static final ListenerCallQueue.Event<Service.Listener> RUNNING_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> STARTING_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> STOPPING_FROM_RUNNING_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> STOPPING_FROM_STARTING_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_NEW_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_RUNNING_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_STARTING_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_STOPPING_EVENT;
    private final Monitor.Guard hasReachedRunning = new HasReachedRunningGuard();
    private final Monitor.Guard isStartable = new IsStartableGuard();
    private final Monitor.Guard isStoppable = new IsStoppableGuard();
    private final Monitor.Guard isStopped = new IsStoppedGuard();
    private final ListenerCallQueue<Service.Listener> listeners = new ListenerCallQueue();
    private final Monitor monitor = new Monitor();
    private volatile StateSnapshot snapshot = new StateSnapshot(Service.State.NEW);

    static {
        STARTING_EVENT = new ListenerCallQueue.Event<Service.Listener>(){

            @Override
            public void call(Service.Listener listener) {
                listener.starting();
            }

            public String toString() {
                return "starting()";
            }
        };
        RUNNING_EVENT = new ListenerCallQueue.Event<Service.Listener>(){

            @Override
            public void call(Service.Listener listener) {
                listener.running();
            }

            public String toString() {
                return "running()";
            }
        };
        STOPPING_FROM_STARTING_EVENT = AbstractService.stoppingEvent(Service.State.STARTING);
        STOPPING_FROM_RUNNING_EVENT = AbstractService.stoppingEvent(Service.State.RUNNING);
        TERMINATED_FROM_NEW_EVENT = AbstractService.terminatedEvent(Service.State.NEW);
        TERMINATED_FROM_STARTING_EVENT = AbstractService.terminatedEvent(Service.State.STARTING);
        TERMINATED_FROM_RUNNING_EVENT = AbstractService.terminatedEvent(Service.State.RUNNING);
        TERMINATED_FROM_STOPPING_EVENT = AbstractService.terminatedEvent(Service.State.STOPPING);
    }

    protected AbstractService() {
    }

    private void checkCurrentState(Service.State state) {
        Service.State state2 = this.state();
        if (state2 == state) return;
        if (state2 == Service.State.FAILED) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected the service ");
            stringBuilder.append(this);
            stringBuilder.append(" to be ");
            stringBuilder.append((Object)state);
            stringBuilder.append(", but the service has FAILED");
            throw new IllegalStateException(stringBuilder.toString(), this.failureCause());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected the service ");
        stringBuilder.append(this);
        stringBuilder.append(" to be ");
        stringBuilder.append((Object)state);
        stringBuilder.append(", but was ");
        stringBuilder.append((Object)state2);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void dispatchListenerEvents() {
        if (this.monitor.isOccupiedByCurrentThread()) return;
        this.listeners.dispatch();
    }

    private void enqueueFailedEvent(final Service.State state, final Throwable throwable) {
        this.listeners.enqueue(new ListenerCallQueue.Event<Service.Listener>(){

            @Override
            public void call(Service.Listener listener) {
                listener.failed(state, throwable);
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("failed({from = ");
                stringBuilder.append((Object)state);
                stringBuilder.append(", cause = ");
                stringBuilder.append(throwable);
                stringBuilder.append("})");
                return stringBuilder.toString();
            }
        });
    }

    private void enqueueRunningEvent() {
        this.listeners.enqueue(RUNNING_EVENT);
    }

    private void enqueueStartingEvent() {
        this.listeners.enqueue(STARTING_EVENT);
    }

    private void enqueueStoppingEvent(Service.State state) {
        if (state == Service.State.STARTING) {
            this.listeners.enqueue(STOPPING_FROM_STARTING_EVENT);
            return;
        }
        if (state != Service.State.RUNNING) throw new AssertionError();
        this.listeners.enqueue(STOPPING_FROM_RUNNING_EVENT);
    }

    private void enqueueTerminatedEvent(Service.State state) {
        switch (6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()]) {
            default: {
                return;
            }
            case 5: 
            case 6: {
                throw new AssertionError();
            }
            case 4: {
                this.listeners.enqueue(TERMINATED_FROM_STOPPING_EVENT);
                return;
            }
            case 3: {
                this.listeners.enqueue(TERMINATED_FROM_RUNNING_EVENT);
                return;
            }
            case 2: {
                this.listeners.enqueue(TERMINATED_FROM_STARTING_EVENT);
                return;
            }
            case 1: 
        }
        this.listeners.enqueue(TERMINATED_FROM_NEW_EVENT);
    }

    private static ListenerCallQueue.Event<Service.Listener> stoppingEvent(final Service.State state) {
        return new ListenerCallQueue.Event<Service.Listener>(){

            @Override
            public void call(Service.Listener listener) {
                listener.stopping(state);
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("stopping({from = ");
                stringBuilder.append((Object)state);
                stringBuilder.append("})");
                return stringBuilder.toString();
            }
        };
    }

    private static ListenerCallQueue.Event<Service.Listener> terminatedEvent(final Service.State state) {
        return new ListenerCallQueue.Event<Service.Listener>(){

            @Override
            public void call(Service.Listener listener) {
                listener.terminated(state);
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("terminated({from = ");
                stringBuilder.append((Object)state);
                stringBuilder.append("})");
                return stringBuilder.toString();
            }
        };
    }

    @Override
    public final void addListener(Service.Listener listener, Executor executor) {
        this.listeners.addListener(listener, executor);
    }

    @Override
    public final void awaitRunning() {
        this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);
        try {
            this.checkCurrentState(Service.State.RUNNING);
            return;
        }
        finally {
            this.monitor.leave();
        }
    }

    @Override
    public final void awaitRunning(long l, TimeUnit object) throws TimeoutException {
        if (!this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, l, (TimeUnit)((Object)object))) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Timed out waiting for ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" to reach the RUNNING state.");
            throw new TimeoutException(((StringBuilder)object).toString());
        }
        try {
            this.checkCurrentState(Service.State.RUNNING);
            return;
        }
        finally {
            this.monitor.leave();
        }
    }

    @Override
    public final void awaitTerminated() {
        this.monitor.enterWhenUninterruptibly(this.isStopped);
        try {
            this.checkCurrentState(Service.State.TERMINATED);
            return;
        }
        finally {
            this.monitor.leave();
        }
    }

    @Override
    public final void awaitTerminated(long l, TimeUnit object) throws TimeoutException {
        if (!this.monitor.enterWhenUninterruptibly(this.isStopped, l, (TimeUnit)((Object)object))) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Timed out waiting for ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" to reach a terminal state. Current state: ");
            ((StringBuilder)object).append((Object)this.state());
            throw new TimeoutException(((StringBuilder)object).toString());
        }
        try {
            this.checkCurrentState(Service.State.TERMINATED);
            return;
        }
        finally {
            this.monitor.leave();
        }
    }

    protected void doCancelStart() {
    }

    protected abstract void doStart();

    protected abstract void doStop();

    @Override
    public final Throwable failureCause() {
        return this.snapshot.failureCause();
    }

    @Override
    public final boolean isRunning() {
        if (this.state() != Service.State.RUNNING) return false;
        return true;
    }

    protected final void notifyFailed(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        this.monitor.enter();
        try {
            Service.State state = this.state();
            int n = 6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()];
            if (n != 1) {
                if (n != 2 && n != 3 && n != 4) {
                    if (n != 5) {
                        return;
                    }
                } else {
                    StateSnapshot stateSnapshot;
                    this.snapshot = stateSnapshot = new StateSnapshot(Service.State.FAILED, false, throwable);
                    this.enqueueFailedEvent(state, throwable);
                    return;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed while in state:");
            stringBuilder.append((Object)((Object)state));
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString(), throwable);
            throw illegalStateException;
        }
        finally {
            this.monitor.leave();
            this.dispatchListenerEvents();
        }
    }

    protected final void notifyStarted() {
        this.monitor.enter();
        try {
            if (this.snapshot.state == Service.State.STARTING) {
                StateSnapshot stateSnapshot;
                if (this.snapshot.shutdownWhenStartupFinishes) {
                    StateSnapshot stateSnapshot2;
                    this.snapshot = stateSnapshot2 = new StateSnapshot(Service.State.STOPPING);
                    this.doStop();
                    return;
                }
                this.snapshot = stateSnapshot = new StateSnapshot(Service.State.RUNNING);
                this.enqueueRunningEvent();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot notifyStarted() when the service is ");
            stringBuilder.append((Object)((Object)this.snapshot.state));
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            this.notifyFailed(illegalStateException);
            throw illegalStateException;
        }
        finally {
            this.monitor.leave();
            this.dispatchListenerEvents();
        }
    }

    protected final void notifyStopped() {
        this.monitor.enter();
        try {
            Service.State state = this.state();
            switch (6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()]) {
                default: {
                    return;
                }
                case 2: 
                case 3: 
                case 4: {
                    StateSnapshot stateSnapshot;
                    this.snapshot = stateSnapshot = new StateSnapshot(Service.State.TERMINATED);
                    this.enqueueTerminatedEvent(state);
                    return;
                }
                case 1: 
                case 5: 
                case 6: 
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot notifyStopped() when the service is ");
            stringBuilder.append((Object)((Object)state));
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
        finally {
            this.monitor.leave();
            this.dispatchListenerEvents();
        }
    }

    @Override
    public final Service startAsync() {
        if (!this.monitor.enterIf(this.isStartable)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Service ");
            stringBuilder.append(this);
            stringBuilder.append(" has already been started");
            throw new IllegalStateException(stringBuilder.toString());
        }
        try {
            StateSnapshot stateSnapshot;
            this.snapshot = stateSnapshot = new StateSnapshot(Service.State.STARTING);
            this.enqueueStartingEvent();
            this.doStart();
            return this;
        }
        catch (Throwable throwable) {
            try {
                this.notifyFailed(throwable);
                return this;
            }
            finally {
                this.monitor.leave();
                this.dispatchListenerEvents();
            }
        }
    }

    @Override
    public final Service.State state() {
        return this.snapshot.externalState();
    }

    @Override
    public final Service stopAsync() {
        if (!this.monitor.enterIf(this.isStoppable)) return this;
        try {
            StateSnapshot stateSnapshot;
            Service.State state = this.state();
            switch (6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()]) {
                default: {
                    return this;
                }
                case 4: 
                case 5: 
                case 6: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("isStoppable is incorrectly implemented, saw: ");
                    stringBuilder.append((Object)((Object)state));
                    AssertionError assertionError = new AssertionError((Object)stringBuilder.toString());
                    throw assertionError;
                }
                case 3: {
                    StateSnapshot stateSnapshot2;
                    this.snapshot = stateSnapshot2 = new StateSnapshot(Service.State.STOPPING);
                    this.enqueueStoppingEvent(Service.State.RUNNING);
                    this.doStop();
                    return this;
                }
                case 2: {
                    StateSnapshot stateSnapshot3;
                    this.snapshot = stateSnapshot3 = new StateSnapshot(Service.State.STARTING, true, null);
                    this.enqueueStoppingEvent(Service.State.STARTING);
                    this.doCancelStart();
                    return this;
                }
                case 1: 
            }
            this.snapshot = stateSnapshot = new StateSnapshot(Service.State.TERMINATED);
            this.enqueueTerminatedEvent(Service.State.NEW);
            return this;
        }
        catch (Throwable throwable) {
            try {
                this.notifyFailed(throwable);
                return this;
            }
            finally {
                this.monitor.leave();
                this.dispatchListenerEvents();
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append(" [");
        stringBuilder.append((Object)((Object)this.state()));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private final class HasReachedRunningGuard
    extends Monitor.Guard {
        HasReachedRunningGuard() {
            super(AbstractService.this.monitor);
        }

        @Override
        public boolean isSatisfied() {
            if (AbstractService.this.state().compareTo(Service.State.RUNNING) < 0) return false;
            return true;
        }
    }

    private final class IsStartableGuard
    extends Monitor.Guard {
        IsStartableGuard() {
            super(AbstractService.this.monitor);
        }

        @Override
        public boolean isSatisfied() {
            if (AbstractService.this.state() != Service.State.NEW) return false;
            return true;
        }
    }

    private final class IsStoppableGuard
    extends Monitor.Guard {
        IsStoppableGuard() {
            super(AbstractService.this.monitor);
        }

        @Override
        public boolean isSatisfied() {
            if (AbstractService.this.state().compareTo(Service.State.RUNNING) > 0) return false;
            return true;
        }
    }

    private final class IsStoppedGuard
    extends Monitor.Guard {
        IsStoppedGuard() {
            super(AbstractService.this.monitor);
        }

        @Override
        public boolean isSatisfied() {
            return AbstractService.this.state().isTerminal();
        }
    }

    private static final class StateSnapshot {
        @NullableDecl
        final Throwable failure;
        final boolean shutdownWhenStartupFinishes;
        final Service.State state;

        StateSnapshot(Service.State state) {
            this(state, false, null);
        }

        StateSnapshot(Service.State state, boolean bl, @NullableDecl Throwable throwable) {
            boolean bl2 = false;
            boolean bl3 = !bl || state == Service.State.STARTING;
            Preconditions.checkArgument(bl3, "shutdownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", (Object)state);
            boolean bl4 = throwable != null;
            if (state == Service.State.FAILED) {
                bl2 = true;
            }
            Preconditions.checkArgument(bl2 ^ bl4 ^ true, "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", (Object)state, (Object)throwable);
            this.state = state;
            this.shutdownWhenStartupFinishes = bl;
            this.failure = throwable;
        }

        Service.State externalState() {
            if (!this.shutdownWhenStartupFinishes) return this.state;
            if (this.state != Service.State.STARTING) return this.state;
            return Service.State.STOPPING;
        }

        Throwable failureCause() {
            boolean bl = this.state == Service.State.FAILED;
            Preconditions.checkState(bl, "failureCause() is only valid if the service has failed, service is %s", (Object)this.state);
            return this.failure;
        }
    }

}


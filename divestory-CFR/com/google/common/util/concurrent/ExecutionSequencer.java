/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

public final class ExecutionSequencer {
    private final AtomicReference<ListenableFuture<Object>> ref = new AtomicReference<ListenableFuture<Object>>(Futures.immediateFuture(null));

    private ExecutionSequencer() {
    }

    public static ExecutionSequencer create() {
        return new ExecutionSequencer();
    }

    public <T> ListenableFuture<T> submit(final Callable<T> callable, Executor executor) {
        Preconditions.checkNotNull(callable);
        return this.submitAsync(new AsyncCallable<T>(){

            @Override
            public ListenableFuture<T> call() throws Exception {
                return Futures.immediateFuture(callable.call());
            }

            public String toString() {
                return callable.toString();
            }
        }, executor);
    }

    public <T> ListenableFuture<T> submitAsync(AsyncCallable<T> object, Executor object2) {
        Preconditions.checkNotNull(object);
        final AtomicReference<RunningState> atomicReference = new AtomicReference<RunningState>(RunningState.NOT_RUN);
        Object object3 = new AsyncCallable<T>((AsyncCallable)object){
            final /* synthetic */ AsyncCallable val$callable;
            {
                this.val$callable = asyncCallable;
            }

            @Override
            public ListenableFuture<T> call() throws Exception {
                if (atomicReference.compareAndSet(RunningState.NOT_RUN, RunningState.STARTED)) return this.val$callable.call();
                return Futures.immediateCancelledFuture();
            }

            public String toString() {
                return this.val$callable.toString();
            }
        };
        SettableFuture settableFuture = SettableFuture.create();
        object = this.ref.getAndSet(settableFuture);
        object3 = Futures.submitAsync(object3, new Executor((ListenableFuture)object, (Executor)object2){
            final /* synthetic */ Executor val$executor;
            final /* synthetic */ ListenableFuture val$oldFuture;
            {
                this.val$oldFuture = listenableFuture;
                this.val$executor = executor;
            }

            @Override
            public void execute(Runnable runnable2) {
                this.val$oldFuture.addListener(runnable2, this.val$executor);
            }
        });
        object2 = Futures.nonCancellationPropagating(object3);
        object = new Runnable((ListenableFuture)object3, (ListenableFuture)object2, atomicReference, settableFuture, (ListenableFuture)object){
            final /* synthetic */ SettableFuture val$newFuture;
            final /* synthetic */ ListenableFuture val$oldFuture;
            final /* synthetic */ ListenableFuture val$outputFuture;
            final /* synthetic */ AtomicReference val$runningState;
            final /* synthetic */ ListenableFuture val$taskFuture;
            {
                this.val$taskFuture = listenableFuture;
                this.val$outputFuture = listenableFuture2;
                this.val$runningState = atomicReference;
                this.val$newFuture = settableFuture;
                this.val$oldFuture = listenableFuture3;
            }

            @Override
            public void run() {
                if (!this.val$taskFuture.isDone()) {
                    if (!this.val$outputFuture.isCancelled()) return;
                    if (!this.val$runningState.compareAndSet(RunningState.NOT_RUN, RunningState.CANCELLED)) return;
                }
                this.val$newFuture.setFuture(this.val$oldFuture);
            }
        };
        object2.addListener((Runnable)object, MoreExecutors.directExecutor());
        object3.addListener((Runnable)object, MoreExecutors.directExecutor());
        return object2;
    }

    static final class RunningState
    extends Enum<RunningState> {
        private static final /* synthetic */ RunningState[] $VALUES;
        public static final /* enum */ RunningState CANCELLED;
        public static final /* enum */ RunningState NOT_RUN;
        public static final /* enum */ RunningState STARTED;

        static {
            RunningState runningState;
            NOT_RUN = new RunningState();
            CANCELLED = new RunningState();
            STARTED = runningState = new RunningState();
            $VALUES = new RunningState[]{NOT_RUN, CANCELLED, runningState};
        }

        public static RunningState valueOf(String string2) {
            return Enum.valueOf(RunningState.class, string2);
        }

        public static RunningState[] values() {
            return (RunningState[])$VALUES.clone();
        }
    }

}


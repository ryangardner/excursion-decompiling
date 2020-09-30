/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.CombinedFuture.CombinedFutureInterruptibleTask
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.util.concurrent.AggregateFuture;
import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.InterruptibleTask;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class CombinedFuture<V>
extends AggregateFuture<Object, V> {
    private CombinedFuture<V> task;

    CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean bl, Executor executor, AsyncCallable<V> asyncCallable) {
        super(immutableCollection, bl, false);
        this.task = new AsyncCallableInterruptibleTask(asyncCallable, executor);
        this.init();
    }

    CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean bl, Executor executor, Callable<V> callable) {
        super(immutableCollection, bl, false);
        this.task = new CallableInterruptibleTask(callable, executor);
        this.init();
    }

    @Override
    void collectOneValue(int n, @NullableDecl Object object) {
    }

    @Override
    void handleAllCompleted() {
        CombinedFuture<V> combinedFuture = this.task;
        if (combinedFuture == null) return;
        ((CombinedFutureInterruptibleTask)((Object)combinedFuture)).execute();
    }

    @Override
    protected void interruptTask() {
        CombinedFuture<V> combinedFuture = this.task;
        if (combinedFuture == null) return;
        ((InterruptibleTask)((Object)combinedFuture)).interruptTask();
    }

    @Override
    void releaseResources(AggregateFuture.ReleaseResourcesReason releaseResourcesReason) {
        super.releaseResources(releaseResourcesReason);
        if (releaseResourcesReason != AggregateFuture.ReleaseResourcesReason.OUTPUT_FUTURE_DONE) return;
        this.task = null;
    }

    private final class AsyncCallableInterruptibleTask
    extends com.google.common.util.concurrent.CombinedFuture.CombinedFutureInterruptibleTask<ListenableFuture<V>> {
        private final AsyncCallable<V> callable;

        AsyncCallableInterruptibleTask(AsyncCallable<V> asyncCallable, Executor executor) {
            super(executor);
            this.callable = Preconditions.checkNotNull(asyncCallable);
        }

        ListenableFuture<V> runInterruptibly() throws Exception {
            this.thrownByExecute = false;
            return Preconditions.checkNotNull(this.callable.call(), "AsyncCallable.call returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", this.callable);
        }

        void setValue(ListenableFuture<V> listenableFuture) {
            CombinedFuture.this.setFuture(listenableFuture);
        }

        String toPendingString() {
            return this.callable.toString();
        }
    }

    private final class CallableInterruptibleTask
    extends CombinedFuture<V> {
        private final Callable<V> callable;

        CallableInterruptibleTask(Callable<V> callable, Executor executor) {
            super(executor);
            this.callable = Preconditions.checkNotNull(callable);
        }

        V runInterruptibly() throws Exception {
            this.thrownByExecute = false;
            return this.callable.call();
        }

        void setValue(V v) {
            CombinedFuture.this.set(v);
        }

        String toPendingString() {
            return this.callable.toString();
        }
    }

    private abstract class CombinedFutureInterruptibleTask<T>
    extends InterruptibleTask<T> {
        private final Executor listenerExecutor;
        boolean thrownByExecute = true;

        CombinedFutureInterruptibleTask(Executor executor) {
            this.listenerExecutor = Preconditions.checkNotNull(executor);
        }

        @Override
        final void afterRanInterruptibly(T t, Throwable throwable) {
            CombinedFuture.this.task = null;
            if (throwable == null) {
                this.setValue(t);
                return;
            }
            if (throwable instanceof ExecutionException) {
                CombinedFuture.this.setException(throwable.getCause());
                return;
            }
            if (throwable instanceof CancellationException) {
                CombinedFuture.this.cancel(false);
                return;
            }
            CombinedFuture.this.setException(throwable);
        }

        final void execute() {
            try {
                this.listenerExecutor.execute(this);
                return;
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                if (!this.thrownByExecute) return;
                CombinedFuture.this.setException(rejectedExecutionException);
            }
        }

        @Override
        final boolean isDone() {
            return CombinedFuture.this.isDone();
        }

        abstract void setValue(T var1);
    }

}


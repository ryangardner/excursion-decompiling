/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.FluentFuture;
import com.google.common.util.concurrent.InterruptibleTask;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class TrustedListenableFutureTask<V>
extends FluentFuture.TrustedFuture<V>
implements RunnableFuture<V> {
    private volatile InterruptibleTask<?> task;

    TrustedListenableFutureTask(AsyncCallable<V> asyncCallable) {
        this.task = new TrustedFutureInterruptibleAsyncTask(asyncCallable);
    }

    TrustedListenableFutureTask(Callable<V> callable) {
        this.task = new TrustedFutureInterruptibleTask(callable);
    }

    static <V> TrustedListenableFutureTask<V> create(AsyncCallable<V> asyncCallable) {
        return new TrustedListenableFutureTask<V>(asyncCallable);
    }

    static <V> TrustedListenableFutureTask<V> create(Runnable runnable2, @NullableDecl V v) {
        return new TrustedListenableFutureTask<V>(Executors.callable(runnable2, v));
    }

    static <V> TrustedListenableFutureTask<V> create(Callable<V> callable) {
        return new TrustedListenableFutureTask<V>(callable);
    }

    @Override
    protected void afterDone() {
        InterruptibleTask<?> interruptibleTask;
        super.afterDone();
        if (this.wasInterrupted() && (interruptibleTask = this.task) != null) {
            interruptibleTask.interruptTask();
        }
        this.task = null;
    }

    @Override
    protected String pendingToString() {
        InterruptibleTask<?> interruptibleTask = this.task;
        if (interruptibleTask == null) return super.pendingToString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("task=[");
        stringBuilder.append(interruptibleTask);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void run() {
        InterruptibleTask<?> interruptibleTask = this.task;
        if (interruptibleTask != null) {
            interruptibleTask.run();
        }
        this.task = null;
    }

    private final class TrustedFutureInterruptibleAsyncTask
    extends InterruptibleTask<ListenableFuture<V>> {
        private final AsyncCallable<V> callable;

        TrustedFutureInterruptibleAsyncTask(AsyncCallable<V> asyncCallable) {
            this.callable = Preconditions.checkNotNull(asyncCallable);
        }

        @Override
        void afterRanInterruptibly(ListenableFuture<V> listenableFuture, Throwable throwable) {
            if (throwable == null) {
                TrustedListenableFutureTask.this.setFuture(listenableFuture);
                return;
            }
            TrustedListenableFutureTask.this.setException(throwable);
        }

        @Override
        final boolean isDone() {
            return TrustedListenableFutureTask.this.isDone();
        }

        @Override
        ListenableFuture<V> runInterruptibly() throws Exception {
            return Preconditions.checkNotNull(this.callable.call(), "AsyncCallable.call returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", this.callable);
        }

        @Override
        String toPendingString() {
            return this.callable.toString();
        }
    }

    private final class TrustedFutureInterruptibleTask
    extends InterruptibleTask<V> {
        private final Callable<V> callable;

        TrustedFutureInterruptibleTask(Callable<V> callable) {
            this.callable = Preconditions.checkNotNull(callable);
        }

        @Override
        void afterRanInterruptibly(V v, Throwable throwable) {
            if (throwable == null) {
                TrustedListenableFutureTask.this.set(v);
                return;
            }
            TrustedListenableFutureTask.this.setException(throwable);
        }

        @Override
        final boolean isDone() {
            return TrustedListenableFutureTask.this.isDone();
        }

        @Override
        V runInterruptibly() throws Exception {
            return this.callable.call();
        }

        @Override
        String toPendingString() {
            return this.callable.toString();
        }
    }

}


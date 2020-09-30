/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingObject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class ForwardingFuture<V>
extends ForwardingObject
implements Future<V> {
    protected ForwardingFuture() {
    }

    @Override
    public boolean cancel(boolean bl) {
        return this.delegate().cancel(bl);
    }

    @Override
    protected abstract Future<? extends V> delegate();

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return this.delegate().get();
    }

    @Override
    public V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate().get(l, timeUnit);
    }

    @Override
    public boolean isCancelled() {
        return this.delegate().isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.delegate().isDone();
    }

    public static abstract class SimpleForwardingFuture<V>
    extends ForwardingFuture<V> {
        private final Future<V> delegate;

        protected SimpleForwardingFuture(Future<V> future) {
            this.delegate = Preconditions.checkNotNull(future);
        }

        @Override
        protected final Future<V> delegate() {
            return this.delegate;
        }
    }

}


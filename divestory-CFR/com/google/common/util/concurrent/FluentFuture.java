/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.ForwardingFluentFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.GwtFluentFutureCatchingSpecialization;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.errorprone.annotations.DoNotMock;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@DoNotMock(value="Use FluentFuture.from(Futures.immediate*Future) or SettableFuture")
public abstract class FluentFuture<V>
extends GwtFluentFutureCatchingSpecialization<V> {
    FluentFuture() {
    }

    @Deprecated
    public static <V> FluentFuture<V> from(FluentFuture<V> fluentFuture) {
        return Preconditions.checkNotNull(fluentFuture);
    }

    public static <V> FluentFuture<V> from(ListenableFuture<V> forwardingFluentFuture) {
        if (!(forwardingFluentFuture instanceof FluentFuture)) return new ForwardingFluentFuture(forwardingFluentFuture);
        return forwardingFluentFuture;
    }

    public final void addCallback(FutureCallback<? super V> futureCallback, Executor executor) {
        Futures.addCallback(this, futureCallback, executor);
    }

    public final <X extends Throwable> FluentFuture<V> catching(Class<X> class_, Function<? super X, ? extends V> function, Executor executor) {
        return (FluentFuture)Futures.catching(this, class_, function, executor);
    }

    public final <X extends Throwable> FluentFuture<V> catchingAsync(Class<X> class_, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        return (FluentFuture)Futures.catchingAsync(this, class_, asyncFunction, executor);
    }

    public final <T> FluentFuture<T> transform(Function<? super V, T> function, Executor executor) {
        return (FluentFuture)Futures.transform(this, function, executor);
    }

    public final <T> FluentFuture<T> transformAsync(AsyncFunction<? super V, T> asyncFunction, Executor executor) {
        return (FluentFuture)Futures.transformAsync(this, asyncFunction, executor);
    }

    public final FluentFuture<V> withTimeout(long l, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        return (FluentFuture)Futures.withTimeout(this, l, timeUnit, scheduledExecutorService);
    }

    static abstract class TrustedFuture<V>
    extends FluentFuture<V>
    implements AbstractFuture.Trusted<V> {
        TrustedFuture() {
        }

        @Override
        public final void addListener(Runnable runnable2, Executor executor) {
            super.addListener(runnable2, executor);
        }

        @Override
        public final boolean cancel(boolean bl) {
            return super.cancel(bl);
        }

        @Override
        public final V get() throws InterruptedException, ExecutionException {
            return super.get();
        }

        @Override
        public final V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return super.get(l, timeUnit);
        }

        @Override
        public final boolean isCancelled() {
            return super.isCancelled();
        }

        @Override
        public final boolean isDone() {
            return super.isDone();
        }
    }

}


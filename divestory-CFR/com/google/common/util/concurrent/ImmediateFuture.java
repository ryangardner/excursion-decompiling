/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class ImmediateFuture<V>
implements ListenableFuture<V> {
    static final ListenableFuture<?> NULL = new ImmediateFuture<Object>(null);
    private static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());
    @NullableDecl
    private final V value;

    ImmediateFuture(@NullableDecl V v) {
        this.value = v;
    }

    @Override
    public void addListener(Runnable runnable2, Executor executor) {
        Preconditions.checkNotNull(runnable2, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        try {
            executor.execute(runnable2);
            return;
        }
        catch (RuntimeException runtimeException) {
            Logger logger = log;
            Level level = Level.SEVERE;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RuntimeException while executing runnable ");
            stringBuilder.append(runnable2);
            stringBuilder.append(" with executor ");
            stringBuilder.append(executor);
            logger.log(level, stringBuilder.toString(), runtimeException);
        }
    }

    @Override
    public boolean cancel(boolean bl) {
        return false;
    }

    @Override
    public V get() {
        return this.value;
    }

    @Override
    public V get(long l, TimeUnit timeUnit) throws ExecutionException {
        Preconditions.checkNotNull(timeUnit);
        return this.get();
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[status=SUCCESS, result=[");
        stringBuilder.append(this.value);
        stringBuilder.append("]]");
        return stringBuilder.toString();
    }

    static final class ImmediateCancelledFuture<V>
    extends AbstractFuture.TrustedFuture<V> {
        ImmediateCancelledFuture() {
            this.cancel(false);
        }
    }

    static final class ImmediateFailedFuture<V>
    extends AbstractFuture.TrustedFuture<V> {
        ImmediateFailedFuture(Throwable throwable) {
            this.setException(throwable);
        }
    }

}


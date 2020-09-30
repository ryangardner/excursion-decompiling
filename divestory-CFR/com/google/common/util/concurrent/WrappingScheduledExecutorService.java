/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.util.concurrent.WrappingExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

abstract class WrappingScheduledExecutorService
extends WrappingExecutorService
implements ScheduledExecutorService {
    final ScheduledExecutorService delegate;

    protected WrappingScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        super(scheduledExecutorService);
        this.delegate = scheduledExecutorService;
    }

    @Override
    public final ScheduledFuture<?> schedule(Runnable runnable2, long l, TimeUnit timeUnit) {
        return this.delegate.schedule(this.wrapTask(runnable2), l, timeUnit);
    }

    @Override
    public final <V> ScheduledFuture<V> schedule(Callable<V> callable, long l, TimeUnit timeUnit) {
        return this.delegate.schedule(this.wrapTask(callable), l, timeUnit);
    }

    @Override
    public final ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable2, long l, long l2, TimeUnit timeUnit) {
        return this.delegate.scheduleAtFixedRate(this.wrapTask(runnable2), l, l2, timeUnit);
    }

    @Override
    public final ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable2, long l, long l2, TimeUnit timeUnit) {
        return this.delegate.scheduleWithFixedDelay(this.wrapTask(runnable2), l, l2, timeUnit);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.util.concurrent.ForwardingExecutorService;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class ForwardingListeningExecutorService
extends ForwardingExecutorService
implements ListeningExecutorService {
    protected ForwardingListeningExecutorService() {
    }

    @Override
    protected abstract ListeningExecutorService delegate();

    @Override
    public ListenableFuture<?> submit(Runnable runnable2) {
        return this.delegate().submit(runnable2);
    }

    @Override
    public <T> ListenableFuture<T> submit(Runnable runnable2, T t) {
        return this.delegate().submit(runnable2, t);
    }

    @Override
    public <T> ListenableFuture<T> submit(Callable<T> callable) {
        return this.delegate().submit(callable);
    }
}


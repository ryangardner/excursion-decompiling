/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.TrustedListenableFutureTask;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractListeningExecutorService
extends AbstractExecutorService
implements ListeningExecutorService {
    @Override
    protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable2, T t) {
        return TrustedListenableFutureTask.create(runnable2, t);
    }

    @Override
    protected final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return TrustedListenableFutureTask.create(callable);
    }

    @Override
    public ListenableFuture<?> submit(Runnable runnable2) {
        return (ListenableFuture)super.submit(runnable2);
    }

    @Override
    public <T> ListenableFuture<T> submit(Runnable runnable2, @NullableDecl T t) {
        return (ListenableFuture)super.submit(runnable2, t);
    }

    @Override
    public <T> ListenableFuture<T> submit(Callable<T> callable) {
        return (ListenableFuture)super.submit(callable);
    }
}


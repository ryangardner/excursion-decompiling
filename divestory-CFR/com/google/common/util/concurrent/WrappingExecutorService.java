/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

abstract class WrappingExecutorService
implements ExecutorService {
    private final ExecutorService delegate;

    protected WrappingExecutorService(ExecutorService executorService) {
        this.delegate = Preconditions.checkNotNull(executorService);
    }

    private <T> ImmutableList<Callable<T>> wrapTasks(Collection<? extends Callable<T>> object) {
        ImmutableList.Builder builder = ImmutableList.builder();
        object = object.iterator();
        while (object.hasNext()) {
            builder.add(this.wrapTask((Callable)object.next()));
        }
        return builder.build();
    }

    @Override
    public final boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate.awaitTermination(l, timeUnit);
    }

    @Override
    public final void execute(Runnable runnable2) {
        this.delegate.execute(this.wrapTask(runnable2));
    }

    @Override
    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        return this.delegate.invokeAll(this.wrapTasks(collection));
    }

    @Override
    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate.invokeAll(this.wrapTasks(collection), l, timeUnit);
    }

    @Override
    public final <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return this.delegate.invokeAny(this.wrapTasks(collection));
    }

    @Override
    public final <T> T invokeAny(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate.invokeAny(this.wrapTasks(collection), l, timeUnit);
    }

    @Override
    public final boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    @Override
    public final boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    @Override
    public final void shutdown() {
        this.delegate.shutdown();
    }

    @Override
    public final List<Runnable> shutdownNow() {
        return this.delegate.shutdownNow();
    }

    @Override
    public final Future<?> submit(Runnable runnable2) {
        return this.delegate.submit(this.wrapTask(runnable2));
    }

    @Override
    public final <T> Future<T> submit(Runnable runnable2, T t) {
        return this.delegate.submit(this.wrapTask(runnable2), t);
    }

    @Override
    public final <T> Future<T> submit(Callable<T> callable) {
        return this.delegate.submit(this.wrapTask(Preconditions.checkNotNull(callable)));
    }

    protected Runnable wrapTask(Runnable runnable2) {
        return new Runnable(this.wrapTask(Executors.callable(runnable2, null))){
            final /* synthetic */ Callable val$wrapped;
            {
                this.val$wrapped = callable;
            }

            @Override
            public void run() {
                try {
                    this.val$wrapped.call();
                    return;
                }
                catch (Exception exception) {
                    Throwables.throwIfUnchecked(exception);
                    throw new RuntimeException(exception);
                }
            }
        };
    }

    protected abstract <T> Callable<T> wrapTask(Callable<T> var1);

}


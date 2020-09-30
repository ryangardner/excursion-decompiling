/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.concurrent.Callable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Callables {
    private Callables() {
    }

    public static <T> AsyncCallable<T> asAsyncCallable(final Callable<T> callable, final ListeningExecutorService listeningExecutorService) {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(listeningExecutorService);
        return new AsyncCallable<T>(){

            @Override
            public ListenableFuture<T> call() throws Exception {
                return listeningExecutorService.submit(callable);
            }
        };
    }

    public static <T> Callable<T> returning(final @NullableDecl T t) {
        return new Callable<T>(){

            @Override
            public T call() {
                return (T)t;
            }
        };
    }

    static Runnable threadRenaming(final Runnable runnable2, final Supplier<String> supplier) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(runnable2);
        return new Runnable(){

            @Override
            public void run() {
                Thread thread2 = Thread.currentThread();
                String string2 = thread2.getName();
                boolean bl = Callables.trySetName((String)supplier.get(), thread2);
                try {
                    runnable2.run();
                    return;
                }
                finally {
                    if (bl) {
                        Callables.trySetName(string2, thread2);
                    }
                }
            }
        };
    }

    static <T> Callable<T> threadRenaming(final Callable<T> callable, final Supplier<String> supplier) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(callable);
        return new Callable<T>(){

            @Override
            public T call() throws Exception {
                Thread thread2 = Thread.currentThread();
                String string2 = thread2.getName();
                boolean bl = Callables.trySetName((String)supplier.get(), thread2);
                try {
                    Object v = callable.call();
                    return (T)v;
                }
                finally {
                    if (bl) {
                        Callables.trySetName(string2, thread2);
                    }
                }
            }
        };
    }

    private static boolean trySetName(String string2, Thread thread2) {
        try {
            thread2.setName(string2);
            return true;
        }
        catch (SecurityException securityException) {
            return false;
        }
    }

}


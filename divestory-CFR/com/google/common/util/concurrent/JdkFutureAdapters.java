/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ExecutionList;
import com.google.common.util.concurrent.ForwardingFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.Uninterruptibles;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

public final class JdkFutureAdapters {
    private JdkFutureAdapters() {
    }

    public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future) {
        if (!(future instanceof ListenableFuture)) return new ListenableFutureAdapter<V>(future);
        return (ListenableFuture)future;
    }

    public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future, Executor executor) {
        Preconditions.checkNotNull(executor);
        if (!(future instanceof ListenableFuture)) return new ListenableFutureAdapter<V>(future, executor);
        return (ListenableFuture)future;
    }

    private static class ListenableFutureAdapter<V>
    extends ForwardingFuture<V>
    implements ListenableFuture<V> {
        private static final Executor defaultAdapterExecutor;
        private static final ThreadFactory threadFactory;
        private final Executor adapterExecutor;
        private final Future<V> delegate;
        private final ExecutionList executionList = new ExecutionList();
        private final AtomicBoolean hasListeners = new AtomicBoolean(false);

        static {
            ThreadFactory threadFactory2;
            threadFactory = threadFactory2 = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ListenableFutureAdapter-thread-%d").build();
            defaultAdapterExecutor = Executors.newCachedThreadPool(threadFactory2);
        }

        ListenableFutureAdapter(Future<V> future) {
            this(future, defaultAdapterExecutor);
        }

        ListenableFutureAdapter(Future<V> future, Executor executor) {
            this.delegate = Preconditions.checkNotNull(future);
            this.adapterExecutor = Preconditions.checkNotNull(executor);
        }

        static /* synthetic */ Future access$000(ListenableFutureAdapter listenableFutureAdapter) {
            return listenableFutureAdapter.delegate;
        }

        static /* synthetic */ ExecutionList access$100(ListenableFutureAdapter listenableFutureAdapter) {
            return listenableFutureAdapter.executionList;
        }

        @Override
        public void addListener(Runnable runnable2, Executor executor) {
            this.executionList.add(runnable2, executor);
            if (!this.hasListeners.compareAndSet(false, true)) return;
            if (this.delegate.isDone()) {
                this.executionList.execute();
                return;
            }
            this.adapterExecutor.execute(new Runnable(){

                /*
                 * Unable to fully structure code
                 * Enabled unnecessary exception pruning
                 */
                @Override
                public void run() {
                    try {
                        Uninterruptibles.getUninterruptibly(ListenableFutureAdapter.access$000(ListenableFutureAdapter.this));
lbl4: // 2 sources:
                        do {
                            ListenableFutureAdapter.access$100(ListenableFutureAdapter.this).execute();
                            return;
                            break;
                        } while (true);
                    }
                    catch (Throwable var1_1) {
                        ** continue;
                    }
                }
            });
        }

        @Override
        protected Future<V> delegate() {
            return this.delegate;
        }

    }

}


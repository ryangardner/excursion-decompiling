/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AbstractCatchingFuture;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.AbstractTransformFuture;
import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.CollectionFuture;
import com.google.common.util.concurrent.CombinedFuture;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.FluentFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.FuturesGetChecked;
import com.google.common.util.concurrent.GwtFuturesCatchingSpecialization;
import com.google.common.util.concurrent.ImmediateFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.TimeoutFuture;
import com.google.common.util.concurrent.TrustedListenableFutureTask;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Futures
extends GwtFuturesCatchingSpecialization {
    private Futures() {
    }

    public static <V> void addCallback(ListenableFuture<V> listenableFuture, FutureCallback<? super V> futureCallback, Executor executor) {
        Preconditions.checkNotNull(futureCallback);
        listenableFuture.addListener(new CallbackListener<V>(listenableFuture, futureCallback), executor);
    }

    public static <V> ListenableFuture<List<V>> allAsList(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(iterable), true);
    }

    @SafeVarargs
    public static <V> ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V> ... arrlistenableFuture) {
        return new CollectionFuture.ListFuture<V>(ImmutableList.copyOf(arrlistenableFuture), true);
    }

    public static <V, X extends Throwable> ListenableFuture<V> catching(ListenableFuture<? extends V> listenableFuture, Class<X> class_, Function<? super X, ? extends V> function, Executor executor) {
        return AbstractCatchingFuture.create(listenableFuture, class_, function, executor);
    }

    public static <V, X extends Throwable> ListenableFuture<V> catchingAsync(ListenableFuture<? extends V> listenableFuture, Class<X> class_, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        return AbstractCatchingFuture.create(listenableFuture, class_, asyncFunction, executor);
    }

    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> class_) throws Exception {
        return FuturesGetChecked.getChecked(future, class_);
    }

    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> class_, long l, TimeUnit timeUnit) throws Exception {
        return FuturesGetChecked.getChecked(future, class_, l, timeUnit);
    }

    public static <V> V getDone(Future<V> future) throws ExecutionException {
        Preconditions.checkState(future.isDone(), "Future was expected to be done: %s", future);
        return Uninterruptibles.getUninterruptibly(future);
    }

    public static <V> V getUnchecked(Future<V> future) {
        Preconditions.checkNotNull(future);
        try {
            future = Uninterruptibles.getUninterruptibly(future);
        }
        catch (ExecutionException executionException) {
            Futures.wrapAndThrowUnchecked(executionException.getCause());
            throw new AssertionError();
        }
        return (V)future;
    }

    public static <V> ListenableFuture<V> immediateCancelledFuture() {
        return new ImmediateFuture.ImmediateCancelledFuture();
    }

    public static <V> ListenableFuture<V> immediateFailedFuture(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        return new ImmediateFuture.ImmediateFailedFuture(throwable);
    }

    public static <V> ListenableFuture<V> immediateFuture(@NullableDecl V v) {
        if (v != null) return new ImmediateFuture<V>(v);
        return ImmediateFuture.NULL;
    }

    public static <T> ImmutableList<ListenableFuture<T>> inCompletionOrder(Iterable<? extends ListenableFuture<? extends T>> immutableList) {
        int n;
        immutableList = immutableList instanceof Collection ? (ImmutableList<ListenableFuture<ListenableFuture>>)immutableList : ImmutableList.copyOf(immutableList);
        ListenableFuture[] arrlistenableFuture = immutableList.toArray(new ListenableFuture[immutableList.size()]);
        immutableList = new InCompletionOrderState(arrlistenableFuture);
        Object object = ImmutableList.builder();
        int n2 = 0;
        for (n = 0; n < arrlistenableFuture.length; ++n) {
            ((ImmutableList.Builder)object).add(new InCompletionOrderFuture((InCompletionOrderState)((Object)immutableList)));
        }
        object = ((ImmutableList.Builder)object).build();
        n = n2;
        while (n < arrlistenableFuture.length) {
            arrlistenableFuture[n].addListener(new Runnable((InCompletionOrderState)((Object)immutableList), (ImmutableList)object, n){
                final /* synthetic */ ImmutableList val$delegates;
                final /* synthetic */ int val$localI;
                final /* synthetic */ InCompletionOrderState val$state;
                {
                    this.val$state = inCompletionOrderState;
                    this.val$delegates = immutableList;
                    this.val$localI = n;
                }

                @Override
                public void run() {
                    this.val$state.recordInputCompletion(this.val$delegates, this.val$localI);
                }
            }, MoreExecutors.directExecutor());
            ++n;
        }
        return object;
    }

    public static <I, O> Future<O> lazyTransform(final Future<I> future, final Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(future);
        Preconditions.checkNotNull(function);
        return new Future<O>(){

            private O applyTransformation(I object) throws ExecutionException {
                try {
                    object = function.apply(object);
                }
                catch (Throwable throwable) {
                    throw new ExecutionException(throwable);
                }
                return (O)object;
            }

            @Override
            public boolean cancel(boolean bl) {
                return future.cancel(bl);
            }

            @Override
            public O get() throws InterruptedException, ExecutionException {
                return this.applyTransformation(future.get());
            }

            @Override
            public O get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
                return this.applyTransformation(future.get(l, timeUnit));
            }

            @Override
            public boolean isCancelled() {
                return future.isCancelled();
            }

            @Override
            public boolean isDone() {
                return future.isDone();
            }
        };
    }

    public static <V> ListenableFuture<V> nonCancellationPropagating(ListenableFuture<V> listenableFuture) {
        if (listenableFuture.isDone()) {
            return listenableFuture;
        }
        NonCancellationPropagatingFuture<V> nonCancellationPropagatingFuture = new NonCancellationPropagatingFuture<V>(listenableFuture);
        listenableFuture.addListener(nonCancellationPropagatingFuture, MoreExecutors.directExecutor());
        return nonCancellationPropagatingFuture;
    }

    public static <O> ListenableFuture<O> scheduleAsync(AsyncCallable<O> object, long l, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        object = TrustedListenableFutureTask.create(object);
        ((FluentFuture.TrustedFuture)object).addListener(new Runnable(scheduledExecutorService.schedule((Runnable)object, l, timeUnit)){
            final /* synthetic */ Future val$scheduled;
            {
                this.val$scheduled = future;
            }

            @Override
            public void run() {
                this.val$scheduled.cancel(false);
            }
        }, MoreExecutors.directExecutor());
        return object;
    }

    public static ListenableFuture<Void> submit(Runnable trustedListenableFutureTask, Executor executor) {
        trustedListenableFutureTask = TrustedListenableFutureTask.create(trustedListenableFutureTask, null);
        executor.execute(trustedListenableFutureTask);
        return trustedListenableFutureTask;
    }

    public static <O> ListenableFuture<O> submit(Callable<O> object, Executor executor) {
        object = TrustedListenableFutureTask.create(object);
        executor.execute((Runnable)object);
        return object;
    }

    public static <O> ListenableFuture<O> submitAsync(AsyncCallable<O> object, Executor executor) {
        object = TrustedListenableFutureTask.create(object);
        executor.execute((Runnable)object);
        return object;
    }

    public static <V> ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(iterable), false);
    }

    @SafeVarargs
    public static <V> ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V> ... arrlistenableFuture) {
        return new CollectionFuture.ListFuture<V>(ImmutableList.copyOf(arrlistenableFuture), false);
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function, Executor executor) {
        return AbstractTransformFuture.create(listenableFuture, function, executor);
    }

    public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction, Executor executor) {
        return AbstractTransformFuture.create(listenableFuture, asyncFunction, executor);
    }

    public static <V> FutureCombiner<V> whenAllComplete(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new FutureCombiner(false, ImmutableList.copyOf(iterable));
    }

    @SafeVarargs
    public static <V> FutureCombiner<V> whenAllComplete(ListenableFuture<? extends V> ... arrlistenableFuture) {
        return new FutureCombiner(false, ImmutableList.copyOf(arrlistenableFuture));
    }

    public static <V> FutureCombiner<V> whenAllSucceed(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new FutureCombiner(true, ImmutableList.copyOf(iterable));
    }

    @SafeVarargs
    public static <V> FutureCombiner<V> whenAllSucceed(ListenableFuture<? extends V> ... arrlistenableFuture) {
        return new FutureCombiner(true, ImmutableList.copyOf(arrlistenableFuture));
    }

    public static <V> ListenableFuture<V> withTimeout(ListenableFuture<V> listenableFuture, long l, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        if (!listenableFuture.isDone()) return TimeoutFuture.create(listenableFuture, l, timeUnit, scheduledExecutorService);
        return listenableFuture;
    }

    private static void wrapAndThrowUnchecked(Throwable throwable) {
        if (!(throwable instanceof Error)) throw new UncheckedExecutionException(throwable);
        throw new ExecutionError((Error)throwable);
    }

    private static final class CallbackListener<V>
    implements Runnable {
        final FutureCallback<? super V> callback;
        final Future<V> future;

        CallbackListener(Future<V> future, FutureCallback<? super V> futureCallback) {
            this.future = future;
            this.callback = futureCallback;
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled unnecessary exception pruning
         */
        @Override
        public void run() {
            void var1_4;
            Future<V> future = this.future;
            if (future instanceof InternalFutureFailureAccess && (future = InternalFutures.tryInternalFastPathGetFailure((InternalFutureFailureAccess)((Object)future))) != null) {
                this.callback.onFailure((Throwable)((Object)future));
                return;
            }
            try {
                future = Futures.getDone(this.future);
            }
            catch (Error error) {
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
            this.callback.onSuccess(future);
            return;
            this.callback.onFailure((Throwable)var1_4);
            return;
            catch (ExecutionException executionException) {
                this.callback.onFailure(executionException.getCause());
                return;
            }
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).addValue(this.callback).toString();
        }
    }

    public static final class FutureCombiner<V> {
        private final boolean allMustSucceed;
        private final ImmutableList<ListenableFuture<? extends V>> futures;

        private FutureCombiner(boolean bl, ImmutableList<ListenableFuture<? extends V>> immutableList) {
            this.allMustSucceed = bl;
            this.futures = immutableList;
        }

        public <C> ListenableFuture<C> call(Callable<C> callable, Executor executor) {
            return new CombinedFuture<C>(this.futures, this.allMustSucceed, executor, callable);
        }

        public <C> ListenableFuture<C> callAsync(AsyncCallable<C> asyncCallable, Executor executor) {
            return new CombinedFuture<C>(this.futures, this.allMustSucceed, executor, asyncCallable);
        }

        public ListenableFuture<?> run(final Runnable runnable2, Executor executor) {
            return this.call(new Callable<Void>(){

                @Override
                public Void call() throws Exception {
                    runnable2.run();
                    return null;
                }
            }, executor);
        }

    }

    private static final class InCompletionOrderFuture<T>
    extends AbstractFuture<T> {
        private InCompletionOrderState<T> state;

        private InCompletionOrderFuture(InCompletionOrderState<T> inCompletionOrderState) {
            this.state = inCompletionOrderState;
        }

        @Override
        protected void afterDone() {
            this.state = null;
        }

        @Override
        public boolean cancel(boolean bl) {
            InCompletionOrderState<T> inCompletionOrderState = this.state;
            if (!super.cancel(bl)) return false;
            inCompletionOrderState.recordOutputCancellation(bl);
            return true;
        }

        @Override
        protected String pendingToString() {
            InCompletionOrderState<T> inCompletionOrderState = this.state;
            if (inCompletionOrderState == null) return null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("inputCount=[");
            stringBuilder.append(inCompletionOrderState.inputFutures.length);
            stringBuilder.append("], remaining=[");
            stringBuilder.append(inCompletionOrderState.incompleteOutputCount.get());
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private static final class InCompletionOrderState<T> {
        private volatile int delegateIndex = 0;
        private final AtomicInteger incompleteOutputCount;
        private final ListenableFuture<? extends T>[] inputFutures;
        private boolean shouldInterrupt = true;
        private boolean wasCancelled = false;

        private InCompletionOrderState(ListenableFuture<? extends T>[] arrlistenableFuture) {
            this.inputFutures = arrlistenableFuture;
            this.incompleteOutputCount = new AtomicInteger(arrlistenableFuture.length);
        }

        private void recordCompletion() {
            if (this.incompleteOutputCount.decrementAndGet() != 0) return;
            if (!this.wasCancelled) return;
            ListenableFuture<? extends T>[] arrlistenableFuture = this.inputFutures;
            int n = arrlistenableFuture.length;
            int n2 = 0;
            while (n2 < n) {
                ListenableFuture<T> listenableFuture = arrlistenableFuture[n2];
                if (listenableFuture != null) {
                    listenableFuture.cancel(this.shouldInterrupt);
                }
                ++n2;
            }
        }

        private void recordInputCompletion(ImmutableList<AbstractFuture<T>> immutableList, int n) {
            ListenableFuture<? extends T>[] arrlistenableFuture = this.inputFutures;
            ListenableFuture<? extends T> listenableFuture = arrlistenableFuture[n];
            arrlistenableFuture[n] = null;
            n = this.delegateIndex;
            do {
                if (n >= immutableList.size()) {
                    this.delegateIndex = immutableList.size();
                    return;
                }
                if (((AbstractFuture)immutableList.get(n)).setFuture(listenableFuture)) {
                    this.recordCompletion();
                    this.delegateIndex = n + 1;
                    return;
                }
                ++n;
            } while (true);
        }

        private void recordOutputCancellation(boolean bl) {
            this.wasCancelled = true;
            if (!bl) {
                this.shouldInterrupt = false;
            }
            this.recordCompletion();
        }
    }

    private static final class NonCancellationPropagatingFuture<V>
    extends AbstractFuture.TrustedFuture<V>
    implements Runnable {
        private ListenableFuture<V> delegate;

        NonCancellationPropagatingFuture(ListenableFuture<V> listenableFuture) {
            this.delegate = listenableFuture;
        }

        @Override
        protected void afterDone() {
            this.delegate = null;
        }

        @Override
        protected String pendingToString() {
            ListenableFuture<V> listenableFuture = this.delegate;
            if (listenableFuture == null) return null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("delegate=[");
            stringBuilder.append(listenableFuture);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void run() {
            ListenableFuture<V> listenableFuture = this.delegate;
            if (listenableFuture == null) return;
            this.setFuture(listenableFuture);
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FluentFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Platform;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractCatchingFuture<V, X extends Throwable, F, T>
extends FluentFuture.TrustedFuture<V>
implements Runnable {
    @NullableDecl
    Class<X> exceptionType;
    @NullableDecl
    F fallback;
    @NullableDecl
    ListenableFuture<? extends V> inputFuture;

    AbstractCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> class_, F f) {
        this.inputFuture = Preconditions.checkNotNull(listenableFuture);
        this.exceptionType = Preconditions.checkNotNull(class_);
        this.fallback = Preconditions.checkNotNull(f);
    }

    static <V, X extends Throwable> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> object, Function<? super X, ? extends V> function, Executor executor) {
        object = new CatchingFuture<V, X>((ListenableFuture<? extends V>)listenableFuture, (Class<? super X>)object, function);
        listenableFuture.addListener((Runnable)object, MoreExecutors.rejectionPropagatingExecutor(executor, object));
        return object;
    }

    static <X extends Throwable, V> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> object, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        object = new AsyncCatchingFuture<V, X>((ListenableFuture<? extends V>)listenableFuture, (Class<? super X>)object, asyncFunction);
        listenableFuture.addListener((Runnable)object, MoreExecutors.rejectionPropagatingExecutor(executor, object));
        return object;
    }

    @Override
    protected final void afterDone() {
        this.maybePropagateCancellationTo(this.inputFuture);
        this.inputFuture = null;
        this.exceptionType = null;
        this.fallback = null;
    }

    @NullableDecl
    abstract T doFallback(F var1, X var2) throws Exception;

    @Override
    protected String pendingToString() {
        Object object = this.inputFuture;
        Serializable serializable = this.exceptionType;
        F f = this.fallback;
        CharSequence charSequence = super.pendingToString();
        if (object != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("inputFuture=[");
            stringBuilder.append(object);
            stringBuilder.append("], ");
            object = stringBuilder.toString();
        } else {
            object = "";
        }
        if (serializable != null && f != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append("exceptionType=[");
            ((StringBuilder)charSequence).append(serializable);
            ((StringBuilder)charSequence).append("], fallback=[");
            ((StringBuilder)charSequence).append(f);
            ((StringBuilder)charSequence).append("]");
            return ((StringBuilder)charSequence).toString();
        }
        if (charSequence == null) return null;
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append((String)object);
        ((StringBuilder)serializable).append((String)charSequence);
        return ((StringBuilder)serializable).toString();
    }

    @Override
    public final void run() {
        Serializable serializable;
        ListenableFuture<V> listenableFuture;
        Throwable throwable;
        F f;
        Class<X> class_;
        block14 : {
            block13 : {
                listenableFuture = this.inputFuture;
                class_ = this.exceptionType;
                f = this.fallback;
                boolean bl = true;
                boolean bl2 = listenableFuture == null;
                boolean bl3 = class_ == null;
                if (f != null) {
                    bl = false;
                }
                if (bl | (bl2 | bl3)) return;
                if (this.isCancelled()) {
                    return;
                }
                this.inputFuture = null;
                try {
                    serializable = listenableFuture instanceof InternalFutureFailureAccess ? InternalFutures.tryInternalFastPathGetFailure((InternalFutureFailureAccess)((Object)listenableFuture)) : null;
                    throwable = serializable;
                    if (serializable != null) break block13;
                    throwable = Futures.getDone(listenableFuture);
                    break block14;
                }
                catch (Throwable throwable2) {
                    // empty catch block
                }
                catch (ExecutionException executionException) {
                    throwable = executionException.getCause();
                    serializable = throwable;
                    if (throwable == null) {
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Future type ");
                        ((StringBuilder)serializable).append(listenableFuture.getClass());
                        ((StringBuilder)serializable).append(" threw ");
                        ((StringBuilder)serializable).append(executionException.getClass());
                        ((StringBuilder)serializable).append(" without a cause");
                        serializable = new NullPointerException(((StringBuilder)serializable).toString());
                    }
                    throwable = serializable;
                }
            }
            Object var9_13 = null;
            serializable = throwable;
            throwable = var9_13;
        }
        if (serializable == null) {
            this.set(throwable);
            return;
        }
        if (!Platform.isInstanceOfThrowableClass((Throwable)serializable, class_)) {
            this.setFuture(listenableFuture);
            return;
        }
        try {
            serializable = this.doFallback(f, serializable);
            this.exceptionType = null;
            this.fallback = null;
            this.setResult(serializable);
            return;
        }
        catch (Throwable throwable3) {
            try {
                this.setException(throwable3);
                return;
            }
            finally {
                this.exceptionType = null;
                this.fallback = null;
            }
        }
    }

    abstract void setResult(@NullableDecl T var1);

    private static final class AsyncCatchingFuture<V, X extends Throwable>
    extends AbstractCatchingFuture<V, X, AsyncFunction<? super X, ? extends V>, ListenableFuture<? extends V>> {
        AsyncCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> class_, AsyncFunction<? super X, ? extends V> asyncFunction) {
            super(listenableFuture, class_, asyncFunction);
        }

        @Override
        ListenableFuture<? extends V> doFallback(AsyncFunction<? super X, ? extends V> asyncFunction, X object) throws Exception {
            object = asyncFunction.apply(object);
            Preconditions.checkNotNull(object, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", asyncFunction);
            return object;
        }

        @Override
        void setResult(ListenableFuture<? extends V> listenableFuture) {
            this.setFuture(listenableFuture);
        }
    }

    private static final class CatchingFuture<V, X extends Throwable>
    extends AbstractCatchingFuture<V, X, Function<? super X, ? extends V>, V> {
        CatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> class_, Function<? super X, ? extends V> function) {
            super(listenableFuture, class_, function);
        }

        @NullableDecl
        @Override
        V doFallback(Function<? super X, ? extends V> function, X x) throws Exception {
            return function.apply(x);
        }

        @Override
        void setResult(@NullableDecl V v) {
            this.set(v);
        }
    }

}


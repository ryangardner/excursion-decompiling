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
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractTransformFuture<I, O, F, T>
extends FluentFuture.TrustedFuture<O>
implements Runnable {
    @NullableDecl
    F function;
    @NullableDecl
    ListenableFuture<? extends I> inputFuture;

    AbstractTransformFuture(ListenableFuture<? extends I> listenableFuture, F f) {
        this.inputFuture = Preconditions.checkNotNull(listenableFuture);
        this.function = Preconditions.checkNotNull(f);
    }

    static <I, O> ListenableFuture<O> create(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> object, Executor executor) {
        Preconditions.checkNotNull(object);
        object = new TransformFuture<I, O>((ListenableFuture<? super I>)listenableFuture, (Function<? super I, ? extends O>)object);
        listenableFuture.addListener((Runnable)object, MoreExecutors.rejectionPropagatingExecutor(executor, object));
        return object;
    }

    static <I, O> ListenableFuture<O> create(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> object, Executor executor) {
        Preconditions.checkNotNull(executor);
        object = new AsyncTransformFuture<I, O>((ListenableFuture<? super I>)listenableFuture, (AsyncFunction<? super I, ? extends O>)object);
        listenableFuture.addListener((Runnable)object, MoreExecutors.rejectionPropagatingExecutor(executor, object));
        return object;
    }

    @Override
    protected final void afterDone() {
        this.maybePropagateCancellationTo(this.inputFuture);
        this.inputFuture = null;
        this.function = null;
    }

    @NullableDecl
    abstract T doTransform(F var1, @NullableDecl I var2) throws Exception;

    @Override
    protected String pendingToString() {
        Object object = this.inputFuture;
        Object object2 = this.function;
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
        if (object2 != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append("function=[");
            ((StringBuilder)charSequence).append(object2);
            ((StringBuilder)charSequence).append("]");
            return ((StringBuilder)charSequence).toString();
        }
        if (charSequence == null) return null;
        object2 = new StringBuilder();
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append((String)charSequence);
        return ((StringBuilder)object2).toString();
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    public final void run() {
        ListenableFuture<I> listenableFuture = this.inputFuture;
        Object object = this.function;
        boolean bl = this.isCancelled();
        boolean bl2 = true;
        boolean bl3 = listenableFuture == null;
        if (object != null) {
            bl2 = false;
        }
        if (bl | bl3 | bl2) {
            return;
        }
        this.inputFuture = null;
        if (listenableFuture.isCancelled()) {
            this.setFuture(listenableFuture);
            return;
        }
        listenableFuture = Futures.getDone(listenableFuture);
        try {
            object = this.doTransform(object, listenableFuture);
            this.function = null;
            this.setResult(object);
            return;
        }
        catch (Throwable throwable) {
            try {
                this.setException(throwable);
                return;
            }
            finally {
                this.function = null;
            }
        }
        catch (Error error) {
            this.setException(error);
            return;
        }
        catch (RuntimeException runtimeException) {
            this.setException(runtimeException);
            return;
        }
        catch (ExecutionException executionException) {
            this.setException(executionException.getCause());
            return;
        }
        catch (CancellationException cancellationException) {
            this.cancel(false);
            return;
        }
    }

    abstract void setResult(@NullableDecl T var1);

    private static final class AsyncTransformFuture<I, O>
    extends AbstractTransformFuture<I, O, AsyncFunction<? super I, ? extends O>, ListenableFuture<? extends O>> {
        AsyncTransformFuture(ListenableFuture<? extends I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction) {
            super(listenableFuture, asyncFunction);
        }

        @Override
        ListenableFuture<? extends O> doTransform(AsyncFunction<? super I, ? extends O> asyncFunction, @NullableDecl I object) throws Exception {
            object = asyncFunction.apply(object);
            Preconditions.checkNotNull(object, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", asyncFunction);
            return object;
        }

        @Override
        void setResult(ListenableFuture<? extends O> listenableFuture) {
            this.setFuture(listenableFuture);
        }
    }

    private static final class TransformFuture<I, O>
    extends AbstractTransformFuture<I, O, Function<? super I, ? extends O>, O> {
        TransformFuture(ListenableFuture<? extends I> listenableFuture, Function<? super I, ? extends O> function) {
            super(listenableFuture, function);
        }

        @NullableDecl
        @Override
        O doTransform(Function<? super I, ? extends O> function, @NullableDecl I i) {
            return function.apply(i);
        }

        @Override
        void setResult(@NullableDecl O o) {
            this.set(o);
        }
    }

}


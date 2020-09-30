/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.api.internal.BasePendingResult;
import java.util.concurrent.TimeUnit;

public final class OptionalPendingResultImpl<R extends Result>
extends OptionalPendingResult<R> {
    private final BasePendingResult<R> zaa;

    public OptionalPendingResultImpl(PendingResult<R> pendingResult) {
        this.zaa = (BasePendingResult)pendingResult;
    }

    @Override
    public final void addStatusListener(PendingResult.StatusListener statusListener) {
        ((PendingResult)this.zaa).addStatusListener(statusListener);
    }

    @Override
    public final R await() {
        return ((PendingResult)this.zaa).await();
    }

    @Override
    public final R await(long l, TimeUnit timeUnit) {
        return ((PendingResult)this.zaa).await(l, timeUnit);
    }

    @Override
    public final void cancel() {
        ((PendingResult)this.zaa).cancel();
    }

    @Override
    public final R get() {
        if (!((OptionalPendingResult)this).isDone()) throw new IllegalStateException("Result is not available. Check that isDone() returns true before calling get().");
        return ((PendingResult)this).await(0L, TimeUnit.MILLISECONDS);
    }

    @Override
    public final boolean isCanceled() {
        return ((PendingResult)this.zaa).isCanceled();
    }

    @Override
    public final boolean isDone() {
        return this.zaa.isReady();
    }

    @Override
    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        ((PendingResult)this.zaa).setResultCallback(resultCallback);
    }

    @Override
    public final void setResultCallback(ResultCallback<? super R> resultCallback, long l, TimeUnit timeUnit) {
        ((PendingResult)this.zaa).setResultCallback(resultCallback, l, timeUnit);
    }

    @Override
    public final <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        return ((PendingResult)this.zaa).then(resultTransform);
    }
}


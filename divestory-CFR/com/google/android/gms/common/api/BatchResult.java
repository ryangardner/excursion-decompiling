/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.BatchResultToken;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.TimeUnit;

public final class BatchResult
implements Result {
    private final Status zaa;
    private final PendingResult<?>[] zab;

    BatchResult(Status status, PendingResult<?>[] arrpendingResult) {
        this.zaa = status;
        this.zab = arrpendingResult;
    }

    @Override
    public final Status getStatus() {
        return this.zaa;
    }

    public final <R extends Result> R take(BatchResultToken<R> batchResultToken) {
        boolean bl = batchResultToken.mId < this.zab.length;
        Preconditions.checkArgument(bl, "The result token does not belong to this batch");
        return (R)this.zab[batchResultToken.mId].await(0L, TimeUnit.MILLISECONDS);
    }
}


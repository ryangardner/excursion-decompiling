/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.BatchResult;
import com.google.android.gms.common.api.BatchResultToken;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.zab;
import java.util.ArrayList;
import java.util.List;

public final class Batch
extends BasePendingResult<BatchResult> {
    private int zab;
    private boolean zac;
    private boolean zad;
    private final PendingResult<?>[] zae;
    private final Object zaf = new Object();

    private Batch(List<PendingResult<?>> list, GoogleApiClient object) {
        super((GoogleApiClient)object);
        int n;
        this.zab = n = list.size();
        this.zae = new PendingResult[n];
        if (list.isEmpty()) {
            this.setResult(new BatchResult(Status.RESULT_SUCCESS, this.zae));
            return;
        }
        n = 0;
        while (n < list.size()) {
            this.zae[n] = object = list.get(n);
            ((PendingResult)object).addStatusListener(new zab(this));
            ++n;
        }
    }

    /* synthetic */ Batch(List list, GoogleApiClient googleApiClient, zab zab2) {
        this(list, googleApiClient);
    }

    static /* synthetic */ Object zaa(Batch batch) {
        return batch.zaf;
    }

    static /* synthetic */ boolean zaa(Batch batch, boolean bl) {
        batch.zad = true;
        return true;
    }

    static /* synthetic */ int zab(Batch batch) {
        int n = batch.zab;
        batch.zab = n - 1;
        return n;
    }

    static /* synthetic */ boolean zab(Batch batch, boolean bl) {
        batch.zac = true;
        return true;
    }

    static /* synthetic */ int zac(Batch batch) {
        return batch.zab;
    }

    static /* synthetic */ boolean zad(Batch batch) {
        return batch.zad;
    }

    static /* synthetic */ void zae(Batch batch) {
        super.cancel();
    }

    static /* synthetic */ boolean zaf(Batch batch) {
        return batch.zac;
    }

    static /* synthetic */ PendingResult[] zag(Batch batch) {
        return batch.zae;
    }

    @Override
    public final void cancel() {
        super.cancel();
        PendingResult<?>[] arrpendingResult = this.zae;
        int n = arrpendingResult.length;
        int n2 = 0;
        while (n2 < n) {
            arrpendingResult[n2].cancel();
            ++n2;
        }
    }

    @Override
    public final BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.zae);
    }

    public static final class Builder {
        private List<PendingResult<?>> zaa = new ArrayList();
        private GoogleApiClient zab;

        public Builder(GoogleApiClient googleApiClient) {
            this.zab = googleApiClient;
        }

        public final <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken batchResultToken = new BatchResultToken(this.zaa.size());
            this.zaa.add(pendingResult);
            return batchResultToken;
        }

        public final Batch build() {
            return new Batch(this.zaa, this.zab, null);
        }
    }

}


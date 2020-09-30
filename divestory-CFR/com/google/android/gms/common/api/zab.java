/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Batch;
import com.google.android.gms.common.api.BatchResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

final class zab
implements PendingResult.StatusListener {
    private final /* synthetic */ Batch zaa;

    zab(Batch batch) {
        this.zaa = batch;
    }

    @Override
    public final void onComplete(Status status) {
        Object object = Batch.zaa(this.zaa);
        synchronized (object) {
            if (((PendingResult)this.zaa).isCanceled()) {
                return;
            }
            if (status.isCanceled()) {
                Batch.zaa(this.zaa, true);
            } else if (!status.isSuccess()) {
                Batch.zab(this.zaa, true);
            }
            Batch.zab(this.zaa);
            if (Batch.zac(this.zaa) != 0) return;
            if (Batch.zad(this.zaa)) {
                Batch.zae(this.zaa);
            } else {
                status = Batch.zaf(this.zaa) ? new Status(13) : Status.RESULT_SUCCESS;
                Batch batch = this.zaa;
                BatchResult batchResult = new BatchResult(status, Batch.zag(this.zaa));
                batch.setResult(batchResult);
            }
            return;
        }
    }
}


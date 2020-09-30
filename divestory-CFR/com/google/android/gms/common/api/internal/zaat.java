/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.api.internal.zaap;

final class zaat
implements ResultCallback<Status> {
    private final /* synthetic */ StatusPendingResult zaa;
    private final /* synthetic */ boolean zab;
    private final /* synthetic */ GoogleApiClient zac;
    private final /* synthetic */ zaap zad;

    zaat(zaap zaap2, StatusPendingResult statusPendingResult, boolean bl, GoogleApiClient googleApiClient) {
        this.zad = zaap2;
        this.zaa = statusPendingResult;
        this.zab = bl;
        this.zac = googleApiClient;
    }

    @Override
    public final /* synthetic */ void onResult(Result result) {
        result = (Status)result;
        Storage.getInstance(zaap.zac(this.zad)).zaa();
        if (((Status)result).isSuccess() && ((GoogleApiClient)this.zad).isConnected()) {
            ((GoogleApiClient)this.zad).reconnect();
        }
        this.zaa.setResult(result);
        if (!this.zab) return;
        this.zac.disconnect();
    }
}


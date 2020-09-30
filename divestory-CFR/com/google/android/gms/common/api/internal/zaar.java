/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.atomic.AtomicReference;

final class zaar
implements GoogleApiClient.ConnectionCallbacks {
    private final /* synthetic */ AtomicReference zaa;
    private final /* synthetic */ StatusPendingResult zab;
    private final /* synthetic */ zaap zac;

    zaar(zaap zaap2, AtomicReference atomicReference, StatusPendingResult statusPendingResult) {
        this.zac = zaap2;
        this.zaa = atomicReference;
        this.zab = statusPendingResult;
    }

    @Override
    public final void onConnected(Bundle bundle) {
        zaap.zaa(this.zac, Preconditions.checkNotNull((GoogleApiClient)this.zaa.get()), this.zab, true);
    }

    @Override
    public final void onConnectionSuspended(int n) {
    }
}


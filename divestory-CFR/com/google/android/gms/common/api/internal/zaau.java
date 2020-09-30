/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.api.internal.zaap;

final class zaau
implements GoogleApiClient.OnConnectionFailedListener {
    private final /* synthetic */ StatusPendingResult zaa;

    zaau(zaap zaap2, StatusPendingResult statusPendingResult) {
        this.zaa = statusPendingResult;
    }

    @Override
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.zaa.setResult(new Status(8));
    }
}


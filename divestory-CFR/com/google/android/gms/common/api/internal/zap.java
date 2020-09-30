/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zar;
import com.google.android.gms.common.internal.Preconditions;

public final class zap
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    public final Api<?> zaa;
    private final boolean zab;
    private zar zac;

    public zap(Api<?> api, boolean bl) {
        this.zaa = api;
        this.zab = bl;
    }

    private final zar zaa() {
        Preconditions.checkNotNull(this.zac, "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
        return this.zac;
    }

    @Override
    public final void onConnected(Bundle bundle) {
        this.zaa().onConnected(bundle);
    }

    @Override
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.zaa().zaa(connectionResult, this.zaa, this.zab);
    }

    @Override
    public final void onConnectionSuspended(int n) {
        this.zaa().onConnectionSuspended(n);
    }

    public final void zaa(zar zar2) {
        this.zac = zar2;
    }
}


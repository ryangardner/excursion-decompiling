/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zaad;
import com.google.android.gms.common.api.internal.zaag;
import com.google.android.gms.common.api.internal.zaam;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.internal.zac;

final class zaao
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private final /* synthetic */ zaad zaa;

    private zaao(zaad zaad2) {
        this.zaa = zaad2;
    }

    /* synthetic */ zaao(zaad zaad2, zaag zaag2) {
        this(zaad2);
    }

    @Override
    public final void onConnected(Bundle object) {
        object = Preconditions.checkNotNull(zaad.zai(this.zaa));
        Preconditions.checkNotNull(zaad.zaf(this.zaa)).zaa(new zaam(this.zaa));
    }

    @Override
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        zaad.zac(this.zaa).lock();
        try {
            if (zaad.zab(this.zaa, connectionResult)) {
                zaad.zaj(this.zaa);
                zaad.zak(this.zaa);
                return;
            }
            zaad.zaa(this.zaa, connectionResult);
            return;
        }
        finally {
            zaad.zac(this.zaa).unlock();
        }
    }

    @Override
    public final void onConnectionSuspended(int n) {
    }
}


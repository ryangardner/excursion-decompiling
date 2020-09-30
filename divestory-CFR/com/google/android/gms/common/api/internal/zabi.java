/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.internal.IAccountAccessor;
import java.util.Set;

final class zabi
implements Runnable {
    private final /* synthetic */ ConnectionResult zaa;
    private final /* synthetic */ GoogleApiManager.zac zab;

    zabi(GoogleApiManager.zac zac2, ConnectionResult connectionResult) {
        this.zab = zac2;
        this.zaa = connectionResult;
    }

    @Override
    public final void run() {
        GoogleApiManager.zaa zaa2 = (GoogleApiManager.zaa)GoogleApiManager.zak(this.zab.GoogleApiManager.this).get(GoogleApiManager.zac.zaa(this.zab));
        if (zaa2 == null) {
            return;
        }
        if (!this.zaa.isSuccess()) {
            zaa2.onConnectionFailed(this.zaa);
            return;
        }
        GoogleApiManager.zac.zaa(this.zab, true);
        if (GoogleApiManager.zac.zab(this.zab).requiresSignIn()) {
            GoogleApiManager.zac.zac(this.zab);
            return;
        }
        try {
            GoogleApiManager.zac.zab(this.zab).getRemoteService(null, GoogleApiManager.zac.zab(this.zab).getScopesForConnectionlessNonSignIn());
            return;
        }
        catch (SecurityException securityException) {
            Log.e((String)"GoogleApiManager", (String)"Failed to get service from broker. ", (Throwable)securityException);
            GoogleApiManager.zac.zab(this.zab).disconnect("Failed to get service from broker.");
            zaa2.onConnectionFailed(new ConnectionResult(10));
            return;
        }
    }
}


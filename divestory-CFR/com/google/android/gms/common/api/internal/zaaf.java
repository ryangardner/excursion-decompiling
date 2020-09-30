/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zaad;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.api.internal.zaax;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;

final class zaaf
implements BaseGmsClient.ConnectionProgressReportCallbacks {
    private final WeakReference<zaad> zaa;
    private final Api<?> zab;
    private final boolean zac;

    public zaaf(zaad zaad2, Api<?> api, boolean bl) {
        this.zaa = new WeakReference<zaad>(zaad2);
        this.zab = api;
        this.zac = bl;
    }

    static /* synthetic */ boolean zaa(zaaf zaaf2) {
        return zaaf2.zac;
    }

    @Override
    public final void onReportServiceBinding(ConnectionResult connectionResult) {
        zaad zaad2 = (zaad)this.zaa.get();
        if (zaad2 == null) {
            return;
        }
        boolean bl = Looper.myLooper() == ((GoogleApiClient)zaad.zad((zaad)zaad2).zad).getLooper();
        Preconditions.checkState(bl, "onReportServiceBinding must be called on the GoogleApiClient handler thread");
        zaad.zac(zaad2).lock();
        bl = zaad.zaa(zaad2, 0);
        if (!bl) {
            zaad.zac(zaad2).unlock();
            return;
        }
        if (!connectionResult.isSuccess()) {
            zaad.zaa(zaad2, connectionResult, this.zab, this.zac);
        }
        if (!zaad.zal(zaad2)) return;
        zaad.zak(zaad2);
        return;
    }
}


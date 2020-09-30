/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zabm;
import com.google.android.gms.common.api.internal.zaq;
import com.google.android.gms.common.api.internal.zat;

final class zas
implements zabm {
    private final /* synthetic */ zaq zaa;

    private zas(zaq zaq2) {
        this.zaa = zaq2;
    }

    /* synthetic */ zas(zaq zaq2, zat zat2) {
        this(zaq2);
    }

    @Override
    public final void zaa(int n, boolean bl) {
        zaq.zaa(this.zaa).lock();
        try {
            if (!zaq.zac(this.zaa) && zaq.zad(this.zaa) != null && zaq.zad(this.zaa).isSuccess()) {
                zaq.zaa(this.zaa, true);
                zaq.zae(this.zaa).onConnectionSuspended(n);
                return;
            }
            zaq.zaa(this.zaa, false);
            zaq.zaa(this.zaa, n, bl);
            return;
        }
        finally {
            zaq.zaa(this.zaa).unlock();
        }
    }

    @Override
    public final void zaa(Bundle bundle) {
        zaq.zaa(this.zaa).lock();
        try {
            zaq.zaa(this.zaa, bundle);
            zaq.zaa(this.zaa, ConnectionResult.RESULT_SUCCESS);
            zaq.zab(this.zaa);
            return;
        }
        finally {
            zaq.zaa(this.zaa).unlock();
        }
    }

    @Override
    public final void zaa(ConnectionResult connectionResult) {
        zaq.zaa(this.zaa).lock();
        try {
            zaq.zaa(this.zaa, connectionResult);
            zaq.zab(this.zaa);
            return;
        }
        finally {
            zaq.zaa(this.zaa).unlock();
        }
    }
}


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

final class zau
implements zabm {
    private final /* synthetic */ zaq zaa;

    private zau(zaq zaq2) {
        this.zaa = zaq2;
    }

    /* synthetic */ zau(zaq zaq2, zat zat2) {
        this(zaq2);
    }

    @Override
    public final void zaa(int n, boolean bl) {
        zaq.zaa(this.zaa).lock();
        try {
            if (zaq.zac(this.zaa)) {
                zaq.zaa(this.zaa, false);
                zaq.zaa(this.zaa, n, bl);
                return;
            }
            zaq.zaa(this.zaa, true);
            zaq.zaf(this.zaa).onConnectionSuspended(n);
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
            zaq.zab(this.zaa, ConnectionResult.RESULT_SUCCESS);
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
            zaq.zab(this.zaa, connectionResult);
            zaq.zab(this.zaa);
            return;
        }
        finally {
            zaq.zaa(this.zaa).unlock();
        }
    }
}


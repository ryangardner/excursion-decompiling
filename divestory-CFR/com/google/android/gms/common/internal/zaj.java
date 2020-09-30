/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.SparseIntArray
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.util.SparseIntArray;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Preconditions;

public final class zaj {
    private final SparseIntArray zaa = new SparseIntArray();
    private GoogleApiAvailabilityLight zab;

    public zaj() {
        this(GoogleApiAvailability.getInstance());
    }

    public zaj(GoogleApiAvailabilityLight googleApiAvailabilityLight) {
        Preconditions.checkNotNull(googleApiAvailabilityLight);
        this.zab = googleApiAvailabilityLight;
    }

    public final int zaa(Context context, Api.Client client) {
        int n;
        int n2;
        int n3;
        block4 : {
            Preconditions.checkNotNull(context);
            Preconditions.checkNotNull(client);
            boolean bl = client.requiresGooglePlayServices();
            n = 0;
            if (!bl) {
                return 0;
            }
            n3 = client.getMinApkVersion();
            int n4 = this.zaa.get(n3, -1);
            if (n4 != -1) {
                return n4;
            }
            for (n2 = 0; n2 < this.zaa.size(); ++n2) {
                int n5 = this.zaa.keyAt(n2);
                if (n5 <= n3 || this.zaa.get(n5) != 0) continue;
                n2 = n;
                break block4;
            }
            n2 = n4;
        }
        n = n2;
        if (n2 == -1) {
            n = this.zab.isGooglePlayServicesAvailable(context, n3);
        }
        this.zaa.put(n3, n);
        return n;
    }

    public final void zaa() {
        this.zaa.clear();
    }
}


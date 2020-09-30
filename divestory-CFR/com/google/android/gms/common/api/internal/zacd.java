/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zacb;

final class zacd
implements Runnable {
    private final /* synthetic */ zacb zaa;

    zacd(zacb zacb2) {
        this.zaa = zacb2;
    }

    @Override
    public final void run() {
        zacb.zaa(this.zaa).zaa(new ConnectionResult(4));
    }
}


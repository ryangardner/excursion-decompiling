/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zacb;
import com.google.android.gms.signin.internal.zam;

final class zacc
implements Runnable {
    private final /* synthetic */ zam zaa;
    private final /* synthetic */ zacb zab;

    zacc(zacb zacb2, zam zam2) {
        this.zab = zacb2;
        this.zaa = zam2;
    }

    @Override
    public final void run() {
        zacb.zaa(this.zab, this.zaa);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zabf;

final class zabh
implements Runnable {
    private final /* synthetic */ zabf zaa;

    zabh(zabf zabf2) {
        this.zaa = zabf2;
    }

    @Override
    public final void run() {
        GoogleApiManager.zaa.zac(this.zaa.zaa).disconnect(String.valueOf(GoogleApiManager.zaa.zac(this.zaa.zaa).getClass().getName()).concat(" disconnecting because it was signed out."));
    }
}


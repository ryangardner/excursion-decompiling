/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.GoogleApiManager;

final class zabd
implements Runnable {
    private final /* synthetic */ int zaa;
    private final /* synthetic */ GoogleApiManager.zaa zab;

    zabd(GoogleApiManager.zaa zaa2, int n) {
        this.zab = zaa2;
        this.zaa = n;
    }

    @Override
    public final void run() {
        GoogleApiManager.zaa.zaa(this.zab, this.zaa);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.GoogleApiManager;

final class zabe
implements Runnable {
    private final /* synthetic */ GoogleApiManager.zaa zaa;

    zabe(GoogleApiManager.zaa zaa2) {
        this.zaa = zaa2;
    }

    @Override
    public final void run() {
        GoogleApiManager.zaa.zab(this.zaa);
    }
}


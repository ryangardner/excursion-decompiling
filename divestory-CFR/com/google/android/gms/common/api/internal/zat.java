/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zaq;

final class zat
implements Runnable {
    private final /* synthetic */ zaq zaa;

    zat(zaq zaq2) {
        this.zaa = zaq2;
    }

    @Override
    public final void run() {
        zaq.zaa(this.zaa).lock();
        try {
            zaq.zab(this.zaa);
            return;
        }
        finally {
            zaq.zaa(this.zaa).unlock();
        }
    }
}


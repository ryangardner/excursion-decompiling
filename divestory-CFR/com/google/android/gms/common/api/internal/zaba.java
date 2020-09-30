/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zaax;
import com.google.android.gms.common.api.internal.zaay;

abstract class zaba {
    private final zaay zaa;

    protected zaba(zaay zaay2) {
        this.zaa = zaay2;
    }

    protected abstract void zaa();

    public final void zaa(zaax zaax2) {
        zaax.zaa(zaax2).lock();
        zaay zaay2 = zaax.zab(zaax2);
        zaay zaay3 = this.zaa;
        if (zaay2 != zaay3) {
            zaax.zaa(zaax2).unlock();
            return;
        }
        this.zaa();
        return;
    }
}


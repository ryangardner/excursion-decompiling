/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.api.internal.zabl;
import java.lang.ref.WeakReference;

final class zaav
extends zabl {
    private WeakReference<zaap> zaa;

    zaav(zaap zaap2) {
        this.zaa = new WeakReference<zaap>(zaap2);
    }

    @Override
    public final void zaa() {
        zaap zaap2 = (zaap)this.zaa.get();
        if (zaap2 == null) {
            return;
        }
        zaap.zaa(zaap2);
    }
}


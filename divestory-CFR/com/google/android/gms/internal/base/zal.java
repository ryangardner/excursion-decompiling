/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.base;

import com.google.android.gms.internal.base.zam;
import com.google.android.gms.internal.base.zan;
import com.google.android.gms.internal.base.zao;

public final class zal {
    private static final zam zaa;
    private static volatile zam zab;

    static {
        zan zan2 = new zan(null);
        zaa = zan2;
        zab = zan2;
    }

    public static zam zaa() {
        return zab;
    }
}


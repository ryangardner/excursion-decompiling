/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zaad;
import com.google.android.gms.common.api.internal.zaal;
import com.google.android.gms.common.api.internal.zaay;
import com.google.android.gms.common.api.internal.zaba;
import com.google.android.gms.signin.internal.zad;
import com.google.android.gms.signin.internal.zam;
import java.lang.ref.WeakReference;

final class zaam
extends zad {
    private final WeakReference<zaad> zaa;

    zaam(zaad zaad2) {
        this.zaa = new WeakReference<zaad>(zaad2);
    }

    @Override
    public final void zaa(zam zam2) {
        zaad zaad2 = (zaad)this.zaa.get();
        if (zaad2 == null) {
            return;
        }
        zaad.zad(zaad2).zaa(new zaal(this, zaad2, zaad2, zam2));
    }
}


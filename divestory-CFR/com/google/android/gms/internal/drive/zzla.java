/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzlb;
import com.google.android.gms.internal.drive.zzlc;
import com.google.android.gms.internal.drive.zzld;

abstract class zzla {
    private static final zzla zztm = new zzlc(null);
    private static final zzla zztn = new zzld(null);

    private zzla() {
    }

    /* synthetic */ zzla(zzlb zzlb2) {
        this();
    }

    static zzla zzdt() {
        return zztm;
    }

    static zzla zzdu() {
        return zztn;
    }

    abstract void zza(Object var1, long var2);

    abstract <L> void zza(Object var1, Object var2, long var3);
}


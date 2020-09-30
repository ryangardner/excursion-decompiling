/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkp;
import com.google.android.gms.internal.drive.zzla;
import com.google.android.gms.internal.drive.zzlb;
import com.google.android.gms.internal.drive.zznd;
import java.util.Collection;

final class zzld
extends zzla {
    private zzld() {
        super(null);
    }

    /* synthetic */ zzld(zzlb zzlb2) {
        this();
    }

    private static <E> zzkp<E> zzc(Object object, long l) {
        return (zzkp)zznd.zzo(object, l);
    }

    @Override
    final void zza(Object object, long l) {
        zzld.zzc(object, l).zzbp();
    }

    @Override
    final <E> void zza(Object object, Object zzkp2, long l) {
        zzkp<E> zzkp3 = zzld.zzc(object, l);
        zzkp<E> zzkp4 = zzld.zzc(zzkp2, l);
        int n = zzkp3.size();
        int n2 = zzkp4.size();
        zzkp2 = zzkp3;
        if (n > 0) {
            zzkp2 = zzkp3;
            if (n2 > 0) {
                zzkp2 = zzkp3;
                if (!zzkp3.zzbo()) {
                    zzkp2 = zzkp3.zzr(n2 + n);
                }
                zzkp2.addAll(zzkp4);
            }
        }
        zzkp3 = zzkp4;
        if (n > 0) {
            zzkp3 = zzkp2;
        }
        zznd.zza(object, l, zzkp3);
    }
}


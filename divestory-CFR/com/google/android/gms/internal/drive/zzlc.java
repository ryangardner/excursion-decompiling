/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zziw;
import com.google.android.gms.internal.drive.zzkp;
import com.google.android.gms.internal.drive.zzky;
import com.google.android.gms.internal.drive.zzkz;
import com.google.android.gms.internal.drive.zzla;
import com.google.android.gms.internal.drive.zzlb;
import com.google.android.gms.internal.drive.zzmc;
import com.google.android.gms.internal.drive.zzna;
import com.google.android.gms.internal.drive.zznd;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

final class zzlc
extends zzla {
    private static final Class<?> zzto = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzlc() {
        super(null);
    }

    /* synthetic */ zzlc(zzlb zzlb2) {
        this();
    }

    private static <E> List<E> zzb(Object object, long l) {
        return (List)zznd.zzo(object, l);
    }

    @Override
    final void zza(Object object, long l) {
        List list = (List)zznd.zzo(object, l);
        if (list instanceof zzkz) {
            list = ((zzkz)list).zzds();
        } else {
            if (zzto.isAssignableFrom(list.getClass())) {
                return;
            }
            if (list instanceof zzmc && list instanceof zzkp) {
                object = (zzkp)list;
                if (!object.zzbo()) return;
                object.zzbp();
                return;
            }
            list = Collections.unmodifiableList(list);
        }
        zznd.zza(object, l, list);
    }

    @Override
    final <E> void zza(Object object, Object list, long l) {
        zzky zzky2 = zzlc.zzb(list, l);
        int n = zzky2.size();
        zzky zzky3 = zzlc.zzb(object, l);
        if (zzky3.isEmpty()) {
            list = zzky3 instanceof zzkz ? new zzky(n) : (zzky3 instanceof zzmc && zzky3 instanceof zzkp ? ((zzkp)zzky3).zzr(n) : new ArrayList(n));
            zznd.zza(object, l, list);
        } else if (zzto.isAssignableFrom(zzky3.getClass())) {
            list = new ArrayList(zzky3.size() + n);
            ((ArrayList)list).addAll(zzky3);
            zznd.zza(object, l, list);
        } else if (zzky3 instanceof zzna) {
            list = new zzky(zzky3.size() + n);
            ((zziw)list).addAll((zzna)((Object)zzky3));
            zznd.zza(object, l, list);
        } else {
            list = zzky3;
            if (zzky3 instanceof zzmc) {
                list = zzky3;
                if (zzky3 instanceof zzkp) {
                    zzkp zzkp2 = zzky3;
                    list = zzky3;
                    if (!zzkp2.zzbo()) {
                        list = zzkp2.zzr(zzky3.size() + n);
                        zznd.zza(object, l, list);
                    }
                }
            }
        }
        int n2 = list.size();
        n = zzky2.size();
        if (n2 > 0 && n > 0) {
            list.addAll(zzky2);
        }
        if (n2 > 0) {
            zzky2 = list;
        }
        zznd.zza(object, l, zzky2);
    }
}


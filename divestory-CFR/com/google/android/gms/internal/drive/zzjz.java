/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjx;
import com.google.android.gms.internal.drive.zzjy;
import com.google.android.gms.internal.drive.zzkb;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;
import java.util.Map;

final class zzjz
extends zzjy<Object> {
    zzjz() {
    }

    @Override
    final int zza(Map.Entry<?, ?> entry) {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    @Override
    final Object zza(zzjx zzjx2, zzlq zzlq2, int n) {
        return zzjx2.zza(zzlq2, n);
    }

    @Override
    final void zza(zzns zzns2, Map.Entry<?, ?> entry) throws IOException {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    @Override
    final zzkb<Object> zzb(Object object) {
        return ((zzkk.zzc)object).zzrw;
    }

    @Override
    final zzkb<Object> zzc(Object object) {
        return ((zzkk.zzc)object).zzdg();
    }

    @Override
    final void zzd(Object object) {
        ((zzjy)this).zzb(object).zzbp();
    }

    @Override
    final boolean zze(zzlq zzlq2) {
        return zzlq2 instanceof zzkk.zzc;
    }
}


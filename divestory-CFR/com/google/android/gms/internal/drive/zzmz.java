/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzmx;
import com.google.android.gms.internal.drive.zzmy;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;

final class zzmz
extends zzmx<zzmy, zzmy> {
    zzmz() {
    }

    private static void zza(Object object, zzmy zzmy2) {
        ((zzkk)object).zzrq = zzmy2;
    }

    @Override
    final /* synthetic */ void zza(Object object, int n, long l) {
        ((zzmy)object).zzb(n << 3, l);
    }

    @Override
    final /* synthetic */ void zza(Object object, int n, zzjc zzjc2) {
        ((zzmy)object).zzb(n << 3 | 2, zzjc2);
    }

    @Override
    final /* synthetic */ void zza(Object object, zzns zzns2) throws IOException {
        ((zzmy)object).zzb(zzns2);
    }

    @Override
    final /* synthetic */ void zzc(Object object, zzns zzns2) throws IOException {
        ((zzmy)object).zza(zzns2);
    }

    @Override
    final void zzd(Object object) {
        ((zzkk)object).zzrq.zzbp();
    }

    @Override
    final /* synthetic */ void zze(Object object, Object object2) {
        zzmz.zza(object, (zzmy)object2);
    }

    @Override
    final /* synthetic */ Object zzez() {
        return zzmy.zzfb();
    }

    @Override
    final /* synthetic */ void zzf(Object object, Object object2) {
        zzmz.zza(object, (zzmy)object2);
    }

    @Override
    final /* synthetic */ Object zzg(Object object, Object object2) {
        object = (zzmy)object;
        if (!((zzmy)(object2 = (zzmy)object2)).equals(zzmy.zzfa())) return zzmy.zza((zzmy)object, (zzmy)object2);
        return object;
    }

    @Override
    final /* synthetic */ int zzn(Object object) {
        return ((zzmy)object).zzcx();
    }

    @Override
    final /* synthetic */ Object zzr(Object object) {
        return ((zzkk)object).zzrq;
    }

    @Override
    final /* synthetic */ int zzs(Object object) {
        return ((zzmy)object).zzfc();
    }
}


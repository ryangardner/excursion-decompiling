/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjr;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzkm;
import com.google.android.gms.internal.drive.zzkz;
import com.google.android.gms.internal.drive.zzli;
import com.google.android.gms.internal.drive.zzlj;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzmf;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzjt
implements zzns {
    private final zzjr zznx;

    private zzjt(zzjr zzjr2) {
        this.zznx = zzjr2 = zzkm.zza(zzjr2, "output");
        zzjr2.zzoh = this;
    }

    public static zzjt zza(zzjr zzjr2) {
        if (zzjr2.zzoh == null) return new zzjt(zzjr2);
        return zzjr2.zzoh;
    }

    @Override
    public final void zza(int n, double d) throws IOException {
        this.zznx.zza(n, d);
    }

    @Override
    public final void zza(int n, float f) throws IOException {
        this.zznx.zza(n, f);
    }

    @Override
    public final void zza(int n, long l) throws IOException {
        this.zznx.zza(n, l);
    }

    @Override
    public final void zza(int n, zzjc zzjc2) throws IOException {
        this.zznx.zza(n, zzjc2);
    }

    @Override
    public final <K, V> void zza(int n, zzlj<K, V> zzlj2, Map<K, V> object) throws IOException {
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            this.zznx.zzb(n, 2);
            this.zznx.zzy(zzli.zza(zzlj2, entry.getKey(), entry.getValue()));
            zzli.zza(this.zznx, zzlj2, entry.getKey(), entry.getValue());
        }
    }

    @Override
    public final void zza(int n, Object object) throws IOException {
        if (object instanceof zzjc) {
            this.zznx.zzb(n, (zzjc)object);
            return;
        }
        this.zznx.zza(n, (zzlq)object);
    }

    @Override
    public final void zza(int n, Object object, zzmf zzmf2) throws IOException {
        this.zznx.zza(n, (zzlq)object, zzmf2);
    }

    @Override
    public final void zza(int n, String string2) throws IOException {
        this.zznx.zza(n, string2);
    }

    @Override
    public final void zza(int n, List<String> list) throws IOException {
        boolean bl = list instanceof zzkz;
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zza(n, list.get(n2));
                ++n2;
            }
            return;
        }
        zzkz zzkz2 = (zzkz)list;
        n2 = n3;
        while (n2 < list.size()) {
            Object object = zzkz2.zzao(n2);
            if (object instanceof String) {
                this.zznx.zza(n, (String)object);
            } else {
                this.zznx.zza(n, (zzjc)object);
            }
            ++n2;
        }
    }

    @Override
    public final void zza(int n, List<?> list, zzmf zzmf2) throws IOException {
        int n2 = 0;
        while (n2 < list.size()) {
            this.zza(n, list.get(n2), zzmf2);
            ++n2;
        }
    }

    @Override
    public final void zza(int n, List<Integer> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zzc(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n = 0;
        for (n2 = 0; n2 < list.size(); n += zzjr.zzac((int)list.get((int)n2).intValue()), ++n2) {
        }
        this.zznx.zzy(n);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzx(list.get(n));
            ++n;
        }
    }

    @Override
    public final void zzak(int n) throws IOException {
        this.zznx.zzb(n, 3);
    }

    @Override
    public final void zzal(int n) throws IOException {
        this.zznx.zzb(n, 4);
    }

    @Override
    public final void zzb(int n, long l) throws IOException {
        this.zznx.zzb(n, l);
    }

    @Override
    public final void zzb(int n, Object object, zzmf zzmf2) throws IOException {
        zzjr zzjr2 = this.zznx;
        object = (zzlq)object;
        zzjr2.zzb(n, 3);
        zzmf2.zza(object, zzjr2.zzoh);
        zzjr2.zzb(n, 4);
    }

    @Override
    public final void zzb(int n, List<zzjc> list) throws IOException {
        int n2 = 0;
        while (n2 < list.size()) {
            this.zznx.zza(n, list.get(n2));
            ++n2;
        }
    }

    @Override
    public final void zzb(int n, List<?> list, zzmf zzmf2) throws IOException {
        int n2 = 0;
        while (n2 < list.size()) {
            this.zzb(n, list.get(n2), zzmf2);
            ++n2;
        }
    }

    @Override
    public final void zzb(int n, List<Integer> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zzf(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n2 = 0;
        for (n = 0; n < list.size(); n2 += zzjr.zzaf((int)list.get((int)n).intValue()), ++n) {
        }
        this.zznx.zzy(n2);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzaa(list.get(n));
            ++n;
        }
    }

    @Override
    public final void zzb(int n, boolean bl) throws IOException {
        this.zznx.zzb(n, bl);
    }

    @Override
    public final void zzc(int n, int n2) throws IOException {
        this.zznx.zzc(n, n2);
    }

    @Override
    public final void zzc(int n, long l) throws IOException {
        this.zznx.zzc(n, l);
    }

    @Override
    public final void zzc(int n, List<Long> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zza(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n2 = 0;
        for (n = 0; n < list.size(); n2 += zzjr.zzo((long)list.get((int)n).longValue()), ++n) {
        }
        this.zznx.zzy(n2);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzl(list.get(n));
            ++n;
        }
    }

    @Override
    public final int zzcd() {
        return zzkk.zze.zzsi;
    }

    @Override
    public final void zzd(int n, int n2) throws IOException {
        this.zznx.zzd(n, n2);
    }

    @Override
    public final void zzd(int n, List<Long> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zza(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n = 0;
        for (n2 = 0; n2 < list.size(); n += zzjr.zzp((long)list.get((int)n2).longValue()), ++n2) {
        }
        this.zznx.zzy(n);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzl(list.get(n));
            ++n;
        }
    }

    @Override
    public final void zze(int n, int n2) throws IOException {
        this.zznx.zze(n, n2);
    }

    @Override
    public final void zze(int n, List<Long> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zzc(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n = 0;
        for (n2 = 0; n2 < list.size(); n += zzjr.zzr((long)list.get((int)n2).longValue()), ++n2) {
        }
        this.zznx.zzy(n);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzn(list.get(n));
            ++n;
        }
    }

    @Override
    public final void zzf(int n, int n2) throws IOException {
        this.zznx.zzf(n, n2);
    }

    @Override
    public final void zzf(int n, List<Float> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zza(n, list.get(n2).floatValue());
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n2 = 0;
        for (n = 0; n < list.size(); n2 += zzjr.zzb((float)list.get((int)n).floatValue()), ++n) {
        }
        this.zznx.zzy(n2);
        n = n3;
        while (n < list.size()) {
            this.zznx.zza(list.get(n).floatValue());
            ++n;
        }
    }

    @Override
    public final void zzg(int n, List<Double> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zza(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n = 0;
        for (n2 = 0; n2 < list.size(); n += zzjr.zzb((double)list.get((int)n2).doubleValue()), ++n2) {
        }
        this.zznx.zzy(n);
        n = n3;
        while (n < list.size()) {
            this.zznx.zza(list.get(n));
            ++n;
        }
    }

    @Override
    public final void zzh(int n, List<Integer> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zzc(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n2 = 0;
        for (n = 0; n < list.size(); n2 += zzjr.zzah((int)list.get((int)n).intValue()), ++n) {
        }
        this.zznx.zzy(n2);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzx(list.get(n));
            ++n;
        }
    }

    @Override
    public final void zzi(int n, long l) throws IOException {
        this.zznx.zza(n, l);
    }

    @Override
    public final void zzi(int n, List<Boolean> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zzb(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n2 = 0;
        for (n = 0; n < list.size(); n2 += zzjr.zzd((boolean)list.get((int)n).booleanValue()), ++n) {
        }
        this.zznx.zzy(n2);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzc(list.get(n));
            ++n;
        }
    }

    @Override
    public final void zzj(int n, long l) throws IOException {
        this.zznx.zzc(n, l);
    }

    @Override
    public final void zzj(int n, List<Integer> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zzd(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n = 0;
        for (n2 = 0; n2 < list.size(); n += zzjr.zzad((int)list.get((int)n2).intValue()), ++n2) {
        }
        this.zznx.zzy(n);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzy(list.get(n));
            ++n;
        }
    }

    @Override
    public final void zzk(int n, List<Integer> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zzf(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n = 0;
        for (n2 = 0; n2 < list.size(); n += zzjr.zzag((int)list.get((int)n2).intValue()), ++n2) {
        }
        this.zznx.zzy(n);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzaa(list.get(n));
            ++n;
        }
    }

    @Override
    public final void zzl(int n, List<Long> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zzc(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n2 = 0;
        for (n = 0; n < list.size(); n2 += zzjr.zzs((long)list.get((int)n).longValue()), ++n) {
        }
        this.zznx.zzy(n2);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzn(list.get(n));
            ++n;
        }
    }

    @Override
    public final void zzm(int n, int n2) throws IOException {
        this.zznx.zzf(n, n2);
    }

    @Override
    public final void zzm(int n, List<Integer> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zze(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n2 = 0;
        for (n = 0; n < list.size(); n2 += zzjr.zzae((int)list.get((int)n).intValue()), ++n) {
        }
        this.zznx.zzy(n2);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzz(list.get(n));
            ++n;
        }
    }

    @Override
    public final void zzn(int n, int n2) throws IOException {
        this.zznx.zzc(n, n2);
    }

    @Override
    public final void zzn(int n, List<Long> list, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        if (!bl) {
            while (n2 < list.size()) {
                this.zznx.zzb(n, list.get(n2));
                ++n2;
            }
            return;
        }
        this.zznx.zzb(n, 2);
        n = 0;
        for (n2 = 0; n2 < list.size(); n += zzjr.zzq((long)list.get((int)n2).longValue()), ++n2) {
        }
        this.zznx.zzy(n);
        n = n3;
        while (n < list.size()) {
            this.zznx.zzm(list.get(n));
            ++n;
        }
    }
}


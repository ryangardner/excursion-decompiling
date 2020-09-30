/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjr;
import com.google.android.gms.internal.drive.zzjy;
import com.google.android.gms.internal.drive.zzkb;
import com.google.android.gms.internal.drive.zzkd;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzkl;
import com.google.android.gms.internal.drive.zzko;
import com.google.android.gms.internal.drive.zzkx;
import com.google.android.gms.internal.drive.zzkz;
import com.google.android.gms.internal.drive.zzle;
import com.google.android.gms.internal.drive.zzll;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzmf;
import com.google.android.gms.internal.drive.zzmi;
import com.google.android.gms.internal.drive.zzmx;
import com.google.android.gms.internal.drive.zzmz;
import com.google.android.gms.internal.drive.zznd;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

final class zzmh {
    private static final Class<?> zzuz = zzmh.zzep();
    private static final zzmx<?, ?> zzva = zzmh.zzf(false);
    private static final zzmx<?, ?> zzvb = zzmh.zzf(true);
    private static final zzmx<?, ?> zzvc = new zzmz();

    static int zza(List<Long> zzle2) {
        int n = zzle2.size();
        int n2 = 0;
        int n3 = 0;
        if (n == 0) {
            return 0;
        }
        if (zzle2 instanceof zzle) {
            zzle2 = zzle2;
            int n4 = 0;
            do {
                n2 = n4;
                if (n3 >= n) return n2;
                n4 += zzjr.zzo(zzle2.getLong(n3));
                ++n3;
            } while (true);
        }
        int n5 = 0;
        n3 = n2;
        do {
            n2 = n5;
            if (n3 >= n) return n2;
            n5 += zzjr.zzo((Long)zzle2.get(n3));
            ++n3;
        } while (true);
    }

    private static <UT, UB> UB zza(int n, int n2, UB UB, zzmx<UT, UB> zzmx2) {
        UB UB2 = UB;
        if (UB == null) {
            UB2 = zzmx2.zzez();
        }
        zzmx2.zza(UB2, n, n2);
        return UB2;
    }

    static <UT, UB> UB zza(int n, List<Integer> object, zzko zzko2, UB UB, zzmx<UT, UB> zzmx2) {
        UB UB2;
        if (zzko2 == null) {
            return UB;
        }
        if (!(object instanceof RandomAccess)) {
            object = object.iterator();
            do {
                UB2 = UB;
                if (!object.hasNext()) return UB2;
                int n2 = (Integer)object.next();
                if (zzko2.zzan(n2)) continue;
                UB = zzmh.zza(n, n2, UB, zzmx2);
                object.remove();
            } while (true);
        }
        int n3 = object.size();
        int n4 = 0;
        int n5 = 0;
        do {
            if (n4 >= n3) {
                UB2 = UB;
                if (n5 == n3) return UB2;
                object.subList(n5, n3).clear();
                UB2 = UB;
                return UB2;
            }
            int n6 = (Integer)object.get(n4);
            if (zzko2.zzan(n6)) {
                if (n4 != n5) {
                    object.set(n5, n6);
                }
                ++n5;
            } else {
                UB = zzmh.zza(n, n6, UB, zzmx2);
            }
            ++n4;
        } while (true);
    }

    public static void zza(int n, List<String> list, zzns zzns2) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zza(n, list);
    }

    public static void zza(int n, List<?> list, zzns zzns2, zzmf zzmf2) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zza(n, list, zzmf2);
    }

    public static void zza(int n, List<Double> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzg(n, list, bl);
    }

    static <T, FT extends zzkd<FT>> void zza(zzjy<FT> zzjy2, T t, T object) {
        object = zzjy2.zzb(object);
        if (((zzkb)object).zzos.isEmpty()) return;
        zzjy2.zzc(t).zza((zzkb<FT>)object);
    }

    static <T> void zza(zzll zzll2, T t, T t2, long l) {
        zznd.zza(t, l, zzll2.zzb(zznd.zzo(t, l), zznd.zzo(t2, l)));
    }

    static <T, UT, UB> void zza(zzmx<UT, UB> zzmx2, T t, T t2) {
        zzmx2.zze(t, zzmx2.zzg(zzmx2.zzr(t), zzmx2.zzr(t2)));
    }

    static int zzb(List<Long> zzle2) {
        int n = zzle2.size();
        int n2 = 0;
        int n3 = 0;
        if (n == 0) {
            return 0;
        }
        if (zzle2 instanceof zzle) {
            zzle2 = zzle2;
            int n4 = 0;
            do {
                n2 = n4;
                if (n3 >= n) return n2;
                n4 += zzjr.zzp(zzle2.getLong(n3));
                ++n3;
            } while (true);
        }
        int n5 = 0;
        n3 = n2;
        do {
            n2 = n5;
            if (n3 >= n) return n2;
            n5 += zzjr.zzp((Long)zzle2.get(n3));
            ++n3;
        } while (true);
    }

    public static void zzb(int n, List<zzjc> list, zzns zzns2) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzb(n, list);
    }

    public static void zzb(int n, List<?> list, zzns zzns2, zzmf zzmf2) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzb(n, list, zzmf2);
    }

    public static void zzb(int n, List<Float> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzf(n, list, bl);
    }

    static int zzc(int n, Object object, zzmf zzmf2) {
        if (!(object instanceof zzkx)) return zzjr.zzb(n, (zzlq)object, zzmf2);
        return zzjr.zza(n, (zzkx)object);
    }

    static int zzc(int n, List<?> zzkz2) {
        int n2;
        int n3 = zzkz2.size();
        int n4 = 0;
        int n5 = 0;
        if (n3 == 0) {
            return 0;
        }
        n = n2 = zzjr.zzab(n) * n3;
        if (zzkz2 instanceof zzkz) {
            zzkz2 = zzkz2;
            n = n2;
            n4 = n5;
            do {
                n2 = n;
                if (n4 >= n3) return n2;
                Object object = zzkz2.zzao(n4);
                n2 = object instanceof zzjc ? zzjr.zzb((zzjc)object) : zzjr.zzm((String)object);
                n += n2;
                ++n4;
            } while (true);
        }
        do {
            n2 = n;
            if (n4 >= n3) return n2;
            Object e = zzkz2.get(n4);
            n2 = e instanceof zzjc ? zzjr.zzb((zzjc)e) : zzjr.zzm((String)e);
            n += n2;
            ++n4;
        } while (true);
    }

    static int zzc(int n, List<?> list, zzmf zzmf2) {
        int n2 = list.size();
        int n3 = 0;
        if (n2 == 0) {
            return 0;
        }
        int n4 = zzjr.zzab(n) * n2;
        n = n3;
        while (n < n2) {
            Object obj = list.get(n);
            n3 = obj instanceof zzkx ? zzjr.zza((zzkx)obj) : zzjr.zza((zzlq)obj, zzmf2);
            n4 += n3;
            ++n;
        }
        return n4;
    }

    static int zzc(List<Long> zzle2) {
        int n = zzle2.size();
        int n2 = 0;
        int n3 = 0;
        if (n == 0) {
            return 0;
        }
        if (zzle2 instanceof zzle) {
            zzle2 = zzle2;
            int n4 = 0;
            do {
                n2 = n4;
                if (n3 >= n) return n2;
                n4 += zzjr.zzq(zzle2.getLong(n3));
                ++n3;
            } while (true);
        }
        int n5 = 0;
        n3 = n2;
        do {
            n2 = n5;
            if (n3 >= n) return n2;
            n5 += zzjr.zzq((Long)zzle2.get(n3));
            ++n3;
        } while (true);
    }

    public static void zzc(int n, List<Long> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzc(n, list, bl);
    }

    static int zzd(int n, List<zzjc> list) {
        int n2 = list.size();
        int n3 = 0;
        if (n2 == 0) {
            return 0;
        }
        n2 *= zzjr.zzab(n);
        n = n3;
        n3 = n2;
        while (n < list.size()) {
            n3 += zzjr.zzb(list.get(n));
            ++n;
        }
        return n3;
    }

    static int zzd(int n, List<zzlq> list, zzmf zzmf2) {
        int n2 = list.size();
        int n3 = 0;
        if (n2 == 0) {
            return 0;
        }
        int n4 = 0;
        while (n3 < n2) {
            n4 += zzjr.zzc(n, list.get(n3), zzmf2);
            ++n3;
        }
        return n4;
    }

    static int zzd(List<Integer> zzkl2) {
        int n = zzkl2.size();
        int n2 = 0;
        int n3 = 0;
        if (n == 0) {
            return 0;
        }
        if (zzkl2 instanceof zzkl) {
            zzkl2 = zzkl2;
            int n4 = 0;
            do {
                n2 = n4;
                if (n3 >= n) return n2;
                n4 += zzjr.zzah(zzkl2.getInt(n3));
                ++n3;
            } while (true);
        }
        int n5 = 0;
        n3 = n2;
        do {
            n2 = n5;
            if (n3 >= n) return n2;
            n5 += zzjr.zzah((Integer)zzkl2.get(n3));
            ++n3;
        } while (true);
    }

    public static void zzd(int n, List<Long> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzd(n, list, bl);
    }

    static boolean zzd(Object object, Object object2) {
        if (object == object2) return true;
        if (object == null) return false;
        if (!object.equals(object2)) return false;
        return true;
    }

    static int zze(List<Integer> zzkl2) {
        int n = zzkl2.size();
        int n2 = 0;
        int n3 = 0;
        if (n == 0) {
            return 0;
        }
        if (zzkl2 instanceof zzkl) {
            zzkl2 = zzkl2;
            int n4 = 0;
            do {
                n2 = n4;
                if (n3 >= n) return n2;
                n4 += zzjr.zzac(zzkl2.getInt(n3));
                ++n3;
            } while (true);
        }
        int n5 = 0;
        n3 = n2;
        do {
            n2 = n5;
            if (n3 >= n) return n2;
            n5 += zzjr.zzac((Integer)zzkl2.get(n3));
            ++n3;
        } while (true);
    }

    public static void zze(int n, List<Long> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzn(n, list, bl);
    }

    public static zzmx<?, ?> zzem() {
        return zzva;
    }

    public static zzmx<?, ?> zzen() {
        return zzvb;
    }

    public static zzmx<?, ?> zzeo() {
        return zzvc;
    }

    private static Class<?> zzep() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static Class<?> zzeq() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    static int zzf(List<Integer> zzkl2) {
        int n = zzkl2.size();
        int n2 = 0;
        int n3 = 0;
        if (n == 0) {
            return 0;
        }
        if (zzkl2 instanceof zzkl) {
            zzkl2 = zzkl2;
            int n4 = 0;
            do {
                n2 = n4;
                if (n3 >= n) return n2;
                n4 += zzjr.zzad(zzkl2.getInt(n3));
                ++n3;
            } while (true);
        }
        int n5 = 0;
        n3 = n2;
        do {
            n2 = n5;
            if (n3 >= n) return n2;
            n5 += zzjr.zzad((Integer)zzkl2.get(n3));
            ++n3;
        } while (true);
    }

    private static zzmx<?, ?> zzf(boolean bl) {
        try {
            Class<?> class_ = zzmh.zzeq();
            if (class_ != null) return (zzmx)class_.getConstructor(Boolean.TYPE).newInstance(bl);
            return null;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static void zzf(int n, List<Long> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zze(n, list, bl);
    }

    static int zzg(List<Integer> zzkl2) {
        int n = zzkl2.size();
        int n2 = 0;
        int n3 = 0;
        if (n == 0) {
            return 0;
        }
        if (zzkl2 instanceof zzkl) {
            zzkl2 = zzkl2;
            int n4 = 0;
            do {
                n2 = n4;
                if (n3 >= n) return n2;
                n4 += zzjr.zzae(zzkl2.getInt(n3));
                ++n3;
            } while (true);
        }
        int n5 = 0;
        n3 = n2;
        do {
            n2 = n5;
            if (n3 >= n) return n2;
            n5 += zzjr.zzae((Integer)zzkl2.get(n3));
            ++n3;
        } while (true);
    }

    public static void zzg(int n, List<Long> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzl(n, list, bl);
    }

    public static void zzg(Class<?> class_) {
        if (zzkk.class.isAssignableFrom(class_)) return;
        Class<?> class_2 = zzuz;
        if (class_2 == null) return;
        if (!class_2.isAssignableFrom(class_)) throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
    }

    static int zzh(List<?> list) {
        return list.size() << 2;
    }

    public static void zzh(int n, List<Integer> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zza(n, list, bl);
    }

    static int zzi(List<?> list) {
        return list.size() << 3;
    }

    public static void zzi(int n, List<Integer> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzj(n, list, bl);
    }

    static int zzj(List<?> list) {
        return list.size();
    }

    public static void zzj(int n, List<Integer> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzm(n, list, bl);
    }

    public static void zzk(int n, List<Integer> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzb(n, list, bl);
    }

    public static void zzl(int n, List<Integer> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzk(n, list, bl);
    }

    public static void zzm(int n, List<Integer> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzh(n, list, bl);
    }

    public static void zzn(int n, List<Boolean> list, zzns zzns2, boolean bl) throws IOException {
        if (list == null) return;
        if (list.isEmpty()) return;
        zzns2.zzi(n, list, bl);
    }

    static int zzo(int n, List<Long> list, boolean bl) {
        if (list.size() != 0) return zzmh.zza(list) + list.size() * zzjr.zzab(n);
        return 0;
    }

    static int zzp(int n, List<Long> list, boolean bl) {
        int n2 = list.size();
        if (n2 != 0) return zzmh.zzb(list) + n2 * zzjr.zzab(n);
        return 0;
    }

    static int zzq(int n, List<Long> list, boolean bl) {
        int n2 = list.size();
        if (n2 != 0) return zzmh.zzc(list) + n2 * zzjr.zzab(n);
        return 0;
    }

    static int zzr(int n, List<Integer> list, boolean bl) {
        int n2 = list.size();
        if (n2 != 0) return zzmh.zzd(list) + n2 * zzjr.zzab(n);
        return 0;
    }

    static int zzs(int n, List<Integer> list, boolean bl) {
        int n2 = list.size();
        if (n2 != 0) return zzmh.zze(list) + n2 * zzjr.zzab(n);
        return 0;
    }

    static int zzt(int n, List<Integer> list, boolean bl) {
        int n2 = list.size();
        if (n2 != 0) return zzmh.zzf(list) + n2 * zzjr.zzab(n);
        return 0;
    }

    static int zzu(int n, List<Integer> list, boolean bl) {
        int n2 = list.size();
        if (n2 != 0) return zzmh.zzg(list) + n2 * zzjr.zzab(n);
        return 0;
    }

    static int zzv(int n, List<?> list, boolean bl) {
        int n2 = list.size();
        if (n2 != 0) return n2 * zzjr.zzj(n, 0);
        return 0;
    }

    static int zzw(int n, List<?> list, boolean bl) {
        int n2 = list.size();
        if (n2 != 0) return n2 * zzjr.zzg(n, 0L);
        return 0;
    }

    static int zzx(int n, List<?> list, boolean bl) {
        int n2 = list.size();
        if (n2 != 0) return n2 * zzjr.zzc(n, true);
        return 0;
    }
}


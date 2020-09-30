/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkm;
import com.google.android.gms.internal.drive.zzlf;
import com.google.android.gms.internal.drive.zzmf;
import com.google.android.gms.internal.drive.zzmg;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class zzmd {
    private static final zzmd zzuw = new zzmd();
    private final zzmg zzux = new zzlf();
    private final ConcurrentMap<Class<?>, zzmf<?>> zzuy = new ConcurrentHashMap();

    private zzmd() {
    }

    public static zzmd zzej() {
        return zzuw;
    }

    public final <T> zzmf<T> zzf(Class<T> object) {
        zzkm.zza(object, "messageType");
        zzmf<T> zzmf2 = (zzmf<T>)this.zzuy.get(object);
        Object object2 = zzmf2;
        if (zzmf2 != null) return object2;
        object2 = this.zzux.zze(object);
        zzkm.zza(object, "messageType");
        zzkm.zza(object2, "schema");
        object = this.zzuy.putIfAbsent((Class<?>)object, (zzmf<?>)object2);
        if (object == null) return object2;
        return object;
    }

    public final <T> zzmf<T> zzq(T t) {
        return this.zzf(t.getClass());
    }
}


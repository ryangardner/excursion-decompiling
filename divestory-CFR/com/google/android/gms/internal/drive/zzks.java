/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;

public final class zzks
extends Enum<zzks> {
    public static final /* enum */ zzks zzsr;
    public static final /* enum */ zzks zzss;
    public static final /* enum */ zzks zzst;
    public static final /* enum */ zzks zzsu;
    public static final /* enum */ zzks zzsv;
    public static final /* enum */ zzks zzsw;
    public static final /* enum */ zzks zzsx;
    public static final /* enum */ zzks zzsy;
    public static final /* enum */ zzks zzsz;
    public static final /* enum */ zzks zzta;
    private static final /* synthetic */ zzks[] zzte;
    private final Class<?> zztb;
    private final Class<?> zztc;
    private final Object zztd;

    static {
        zzks zzks2;
        zzsr = new zzks(Void.class, Void.class, null);
        zzss = new zzks(Integer.TYPE, Integer.class, 0);
        zzst = new zzks(Long.TYPE, Long.class, 0L);
        zzsu = new zzks(Float.TYPE, Float.class, Float.valueOf(0.0f));
        zzsv = new zzks(Double.TYPE, Double.class, 0.0);
        zzsw = new zzks(Boolean.TYPE, Boolean.class, false);
        zzsx = new zzks(String.class, String.class, "");
        zzsy = new zzks(zzjc.class, zzjc.class, zzjc.zznq);
        zzsz = new zzks(Integer.TYPE, Integer.class, null);
        zzta = zzks2 = new zzks(Object.class, Object.class, null);
        zzte = new zzks[]{zzsr, zzss, zzst, zzsu, zzsv, zzsw, zzsx, zzsy, zzsz, zzks2};
    }

    private zzks(Class<?> class_, Class<?> class_2, Object object) {
        this.zztb = class_;
        this.zztc = class_2;
        this.zztd = object;
    }

    public static zzks[] values() {
        return (zzks[])zzte.clone();
    }

    public final Class<?> zzdo() {
        return this.zztc;
    }
}


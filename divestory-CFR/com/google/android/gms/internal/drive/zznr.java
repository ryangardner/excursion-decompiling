/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;

public final class zznr
extends Enum<zznr> {
    public static final /* enum */ zznr zzxp;
    public static final /* enum */ zznr zzxq;
    public static final /* enum */ zznr zzxr;
    public static final /* enum */ zznr zzxs;
    public static final /* enum */ zznr zzxt;
    public static final /* enum */ zznr zzxu;
    public static final /* enum */ zznr zzxv;
    public static final /* enum */ zznr zzxw;
    public static final /* enum */ zznr zzxx;
    private static final /* synthetic */ zznr[] zzxy;
    private final Object zztd;

    static {
        zznr zznr2;
        zzxp = new zznr(0);
        zzxq = new zznr(0L);
        zzxr = new zznr(Float.valueOf(0.0f));
        zzxs = new zznr(0.0);
        zzxt = new zznr(false);
        zzxu = new zznr("");
        zzxv = new zznr(zzjc.zznq);
        zzxw = new zznr(null);
        zzxx = zznr2 = new zznr(null);
        zzxy = new zznr[]{zzxp, zzxq, zzxr, zzxs, zzxt, zzxu, zzxv, zzxw, zznr2};
    }

    private zznr(Object object) {
        this.zztd = object;
    }

    public static zznr[] values() {
        return (zznr[])zzxy.clone();
    }
}


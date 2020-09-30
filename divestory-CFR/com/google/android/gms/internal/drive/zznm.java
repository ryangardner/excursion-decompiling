/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zznl;
import com.google.android.gms.internal.drive.zznn;
import com.google.android.gms.internal.drive.zzno;
import com.google.android.gms.internal.drive.zznp;
import com.google.android.gms.internal.drive.zznq;
import com.google.android.gms.internal.drive.zznr;

public class zznm
extends Enum<zznm> {
    public static final /* enum */ zznm zzwu;
    public static final /* enum */ zznm zzwv;
    public static final /* enum */ zznm zzww;
    public static final /* enum */ zznm zzwx;
    public static final /* enum */ zznm zzwy;
    public static final /* enum */ zznm zzwz;
    public static final /* enum */ zznm zzxa;
    public static final /* enum */ zznm zzxb;
    public static final /* enum */ zznm zzxc;
    public static final /* enum */ zznm zzxd;
    public static final /* enum */ zznm zzxe;
    public static final /* enum */ zznm zzxf;
    public static final /* enum */ zznm zzxg;
    public static final /* enum */ zznm zzxh;
    public static final /* enum */ zznm zzxi;
    public static final /* enum */ zznm zzxj;
    public static final /* enum */ zznm zzxk;
    public static final /* enum */ zznm zzxl;
    private static final /* synthetic */ zznm[] zzxo;
    private final zznr zzxm;
    private final int zzxn;

    static {
        zznm zznm2;
        zzwu = new zznm(zznr.zzxs, 1);
        zzwv = new zznm(zznr.zzxr, 5);
        zzww = new zznm(zznr.zzxq, 0);
        zzwx = new zznm(zznr.zzxq, 0);
        zzwy = new zznm(zznr.zzxp, 0);
        zzwz = new zznm(zznr.zzxq, 1);
        zzxa = new zznm(zznr.zzxp, 5);
        zzxb = new zznm(zznr.zzxt, 0);
        zzxc = new zznn(zznr.zzxu, 2);
        zzxd = new zzno(zznr.zzxx, 3);
        zzxe = new zznp(zznr.zzxx, 2);
        zzxf = new zznq(zznr.zzxv, 2);
        zzxg = new zznm(zznr.zzxp, 0);
        zzxh = new zznm(zznr.zzxw, 0);
        zzxi = new zznm(zznr.zzxp, 5);
        zzxj = new zznm(zznr.zzxq, 1);
        zzxk = new zznm(zznr.zzxp, 0);
        zzxl = zznm2 = new zznm(zznr.zzxq, 0);
        zzxo = new zznm[]{zzwu, zzwv, zzww, zzwx, zzwy, zzwz, zzxa, zzxb, zzxc, zzxd, zzxe, zzxf, zzxg, zzxh, zzxi, zzxj, zzxk, zznm2};
    }

    private zznm(zznr zznr2, int n2) {
        this.zzxm = zznr2;
        this.zzxn = n2;
    }

    /* synthetic */ zznm(String string2, int n, zznr zznr2, int n2, zznl zznl2) {
        this(zznr2, n2);
    }

    public static zznm[] values() {
        return (zznm[])zzxo.clone();
    }

    public final zznr zzfj() {
        return this.zzxm;
    }

    public final int zzfk() {
        return this.zzxn;
    }
}


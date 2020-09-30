/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zziy;
import com.google.android.gms.internal.drive.zziz;
import com.google.android.gms.internal.drive.zzja;
import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjk;
import com.google.android.gms.internal.drive.zzjo;
import com.google.android.gms.internal.drive.zzjr;
import com.google.android.gms.internal.drive.zzju;
import com.google.android.gms.internal.drive.zzjx;
import com.google.android.gms.internal.drive.zzjy;
import com.google.android.gms.internal.drive.zzkb;
import com.google.android.gms.internal.drive.zzkd;
import com.google.android.gms.internal.drive.zzke;
import com.google.android.gms.internal.drive.zzkh;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzkl;
import com.google.android.gms.internal.drive.zzkm;
import com.google.android.gms.internal.drive.zzko;
import com.google.android.gms.internal.drive.zzkp;
import com.google.android.gms.internal.drive.zzkq;
import com.google.android.gms.internal.drive.zzla;
import com.google.android.gms.internal.drive.zzle;
import com.google.android.gms.internal.drive.zzli;
import com.google.android.gms.internal.drive.zzlj;
import com.google.android.gms.internal.drive.zzll;
import com.google.android.gms.internal.drive.zzlo;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzlv;
import com.google.android.gms.internal.drive.zzly;
import com.google.android.gms.internal.drive.zzmd;
import com.google.android.gms.internal.drive.zzme;
import com.google.android.gms.internal.drive.zzmf;
import com.google.android.gms.internal.drive.zzmh;
import com.google.android.gms.internal.drive.zzmi;
import com.google.android.gms.internal.drive.zzms;
import com.google.android.gms.internal.drive.zzmx;
import com.google.android.gms.internal.drive.zzmy;
import com.google.android.gms.internal.drive.zznd;
import com.google.android.gms.internal.drive.zznf;
import com.google.android.gms.internal.drive.zznm;
import com.google.android.gms.internal.drive.zznr;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sun.misc.Unsafe;

final class zzlu<T>
implements zzmf<T> {
    private static final int[] zzub = new int[0];
    private static final Unsafe zzuc = zznd.zzff();
    private final int[] zzud;
    private final Object[] zzue;
    private final int zzuf;
    private final int zzug;
    private final zzlq zzuh;
    private final boolean zzui;
    private final boolean zzuj;
    private final boolean zzuk;
    private final boolean zzul;
    private final int[] zzum;
    private final int zzun;
    private final int zzuo;
    private final zzly zzup;
    private final zzla zzuq;
    private final zzmx<?, ?> zzur;
    private final zzjy<?> zzus;
    private final zzll zzut;

    private zzlu(int[] arrn, Object[] arrobject, int n, int n2, zzlq zzlq2, boolean bl, boolean bl2, int[] arrn2, int n3, int n4, zzly zzly2, zzla zzla2, zzmx<?, ?> zzmx2, zzjy<?> zzjy2, zzll zzll2) {
        this.zzud = arrn;
        this.zzue = arrobject;
        this.zzuf = n;
        this.zzug = n2;
        this.zzuj = zzlq2 instanceof zzkk;
        this.zzuk = bl;
        bl = zzjy2 != null && zzjy2.zze(zzlq2);
        this.zzui = bl;
        this.zzul = false;
        this.zzum = arrn2;
        this.zzun = n3;
        this.zzuo = n4;
        this.zzup = zzly2;
        this.zzuq = zzla2;
        this.zzur = zzmx2;
        this.zzus = zzjy2;
        this.zzuh = zzlq2;
        this.zzut = zzll2;
    }

    private static <UT, UB> int zza(zzmx<UT, UB> zzmx2, T t) {
        return zzmx2.zzn(zzmx2.zzr(t));
    }

    /*
     * Unable to fully structure code
     */
    private final int zza(T var1_1, byte[] var2_2, int var3_3, int var4_4, int var5_5, int var6_6, int var7_7, int var8_8, int var9_9, long var10_10, int var12_11, zziz var13_12) throws IOException {
        block24 : {
            block25 : {
                var14_13 = zzlu.zzuc;
                var15_14 = this.zzud[var12_11 + 2] & 1048575;
                switch (var9_9) {
                    default: {
                        return var3_3;
                    }
                    case 68: {
                        if (var7_7 != 3) return var3_3;
                        var3_3 = zziy.zza(this.zzap(var12_11), (byte[])var2_2, var3_3, var4_4, var5_5 & -8 | 4, var13_12);
                        var2_2 = var14_13.getInt(var1_1, var15_14) == var6_6 ? var14_13.getObject(var1_1, var10_10) : null;
                        if (var2_2 == null) {
                            var14_13.putObject(var1_1, var10_10, var13_12.zznm);
                        } else {
                            var14_13.putObject(var1_1, var10_10, zzkm.zza(var2_2, var13_12.zznm));
                        }
                        break block24;
                    }
                    case 67: {
                        if (var7_7 != 0) return var3_3;
                        var3_3 = zziy.zzb((byte[])var2_2, var3_3, var13_12);
                        var14_13.putObject(var1_1, var10_10, zzjo.zzk(var13_12.zznl));
                        break block24;
                    }
                    case 66: {
                        if (var7_7 != 0) return var3_3;
                        var3_3 = zziy.zza((byte[])var2_2, var3_3, var13_12);
                        var14_13.putObject(var1_1, var10_10, zzjo.zzw(var13_12.zznk));
                        break block24;
                    }
                    case 63: {
                        if (var7_7 != 0) return var3_3;
                        var3_3 = zziy.zza((byte[])var2_2, var3_3, var13_12);
                        var4_4 = var13_12.zznk;
                        var2_2 = this.zzar(var12_11);
                        if (var2_2 != null && !var2_2.zzan(var4_4)) {
                            zzlu.zzo(var1_1).zzb(var5_5, var4_4);
                            return var3_3;
                        }
                        var14_13.putObject(var1_1, var10_10, var4_4);
                        break block24;
                    }
                    case 61: {
                        if (var7_7 != 2) return var3_3;
                        var3_3 = zziy.zze((byte[])var2_2, var3_3, var13_12);
                        var14_13.putObject(var1_1, var10_10, var13_12.zznm);
                        break block24;
                    }
                    case 60: {
                        if (var7_7 != 2) return var3_3;
                        var3_3 = zziy.zza(this.zzap(var12_11), (byte[])var2_2, var3_3, var4_4, var13_12);
                        var2_2 = var14_13.getInt(var1_1, var15_14) == var6_6 ? var14_13.getObject(var1_1, var10_10) : null;
                        if (var2_2 == null) {
                            var14_13.putObject(var1_1, var10_10, var13_12.zznm);
                        } else {
                            var14_13.putObject(var1_1, var10_10, zzkm.zza(var2_2, var13_12.zznm));
                        }
                        var14_13.putInt(var1_1, var15_14, var6_6);
                        return var3_3;
                    }
                    case 59: {
                        if (var7_7 != 2) return var3_3;
                        var3_3 = zziy.zza((byte[])var2_2, var3_3, var13_12);
                        var4_4 = var13_12.zznk;
                        if (var4_4 == 0) {
                            var14_13.putObject(var1_1, var10_10, "");
                        } else {
                            if ((var8_8 & 536870912) != 0) {
                                if (zznf.zze((byte[])var2_2, var3_3, var3_3 + var4_4) == false) throw zzkq.zzdn();
                            }
                            var14_13.putObject(var1_1, var10_10, new String((byte[])var2_2, var3_3, var4_4, zzkm.UTF_8));
                            var3_3 += var4_4;
                        }
                        var14_13.putInt(var1_1, var15_14, var6_6);
                        return var3_3;
                    }
                    case 58: {
                        if (var7_7 != 0) return var3_3;
                        var3_3 = zziy.zzb((byte[])var2_2, var3_3, var13_12);
                        var17_15 = var13_12.zznl != 0L;
                        var14_13.putObject(var1_1, var10_10, var17_15);
                        break block24;
                    }
                    case 57: 
                    case 64: {
                        if (var7_7 != 5) return var3_3;
                        var14_13.putObject(var1_1, var10_10, zziy.zza((byte[])var2_2, var3_3));
                        ** GOTO lbl90
                    }
                    case 56: 
                    case 65: {
                        if (var7_7 != 1) return var3_3;
                        var14_13.putObject(var1_1, var10_10, zziy.zzb((byte[])var2_2, var3_3));
                        break block25;
                    }
                    case 55: 
                    case 62: {
                        if (var7_7 != 0) return var3_3;
                        var3_3 = zziy.zza((byte[])var2_2, var3_3, var13_12);
                        var14_13.putObject(var1_1, var10_10, var13_12.zznk);
                        break block24;
                    }
                    case 53: 
                    case 54: {
                        if (var7_7 != 0) return var3_3;
                        var3_3 = zziy.zzb((byte[])var2_2, var3_3, var13_12);
                        var14_13.putObject(var1_1, var10_10, var13_12.zznl);
                        break block24;
                    }
                    case 52: {
                        if (var7_7 != 5) return var3_3;
                        var14_13.putObject(var1_1, var10_10, Float.valueOf(zziy.zzd((byte[])var2_2, var3_3)));
lbl90: // 2 sources:
                        var3_3 += 4;
                        break block24;
                    }
                    case 51: 
                }
                if (var7_7 != 1) return var3_3;
                var14_13.putObject(var1_1, var10_10, zziy.zzc((byte[])var2_2, var3_3));
            }
            var3_3 += 8;
        }
        var14_13.putInt(var1_1, var15_14, var6_6);
        return var3_3;
    }

    /*
     * Unable to fully structure code
     */
    private final int zza(T var1_1, byte[] var2_2, int var3_3, int var4_4, int var5_5, int var6_6, int var7_7, int var8_8, long var9_9, int var11_10, long var12_11, zziz var14_12) throws IOException {
        block60 : {
            var15_14 = var3_4;
            var17_16 = var16_15 = (zzkp<Integer>)zzlu.zzuc.getObject(var1_1, (long)var12_12);
            if (!var16_15.zzbo()) {
                var18_17 = var16_15.size();
                var18_17 = var18_17 == 0 ? 10 : (var18_17 <<= 1);
                var17_16 = var16_15.zzr(var18_17);
                zzlu.zzuc.putObject(var1_1, (long)var12_12, var17_16);
            }
            switch (var11_11) {
                default: {
                    var11_11 = var15_14;
                    return var11_11;
                }
                case 49: {
                    var11_11 = var15_14;
                    if (var7_8 != 3) return var11_11;
                    var1_1 = this.zzap((int)var8_9);
                    var6_7 = var5_6 & -8 | 4;
                    var3_4 = zziy.zza((zzmf)var1_1, var2_2 /* !! */ , var3_4, var4_5, var6_7, (zziz)var14_13);
                    var17_16.add((Integer)var14_13.zznm);
                    do {
                        var11_11 = var3_4;
                        if (var3_4 >= var4_5) return var11_11;
                        var7_8 = zziy.zza(var2_2 /* !! */ , var3_4, (zziz)var14_13);
                        var11_11 = var3_4;
                        if (var5_6 != var14_13.zznk) return var11_11;
                        var3_4 = zziy.zza((zzmf)var1_1, var2_2 /* !! */ , var7_8, var4_5, var6_7, (zziz)var14_13);
                        var17_16.add((Integer)var14_13.zznm);
                    } while (true);
                }
                case 34: 
                case 48: {
                    if (var7_8 == 2) {
                        var1_1 = (zzle)var17_16;
                        var3_4 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                        var4_5 = var14_13.zznk + var3_4;
                        do {
                            if (var3_4 >= var4_5) {
                                if (var3_4 != var4_5) throw zzkq.zzdi();
                                return var3_4;
                            }
                            var3_4 = zziy.zzb(var2_2 /* !! */ , var3_4, (zziz)var14_13);
                            var1_1.zzv(zzjo.zzk(var14_13.zznl));
                        } while (true);
                    }
                    var11_11 = var15_14;
                    if (var7_8 != false) return var11_11;
                    var1_1 = (zzle)var17_16;
                    var6_7 = zziy.zzb(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                    var1_1.zzv(zzjo.zzk(var14_13.zznl));
                    do {
                        var3_4 = var6_7;
                        if (var6_7 >= var4_5) return var3_4;
                        var7_8 = zziy.zza(var2_2 /* !! */ , var6_7, (zziz)var14_13);
                        var3_4 = var6_7;
                        if (var5_6 != var14_13.zznk) return var3_4;
                        var6_7 = zziy.zzb(var2_2 /* !! */ , var7_8, (zziz)var14_13);
                        var1_1.zzv(zzjo.zzk(var14_13.zznl));
                    } while (true);
                }
                case 33: 
                case 47: {
                    if (var7_8 == 2) {
                        var1_1 = (zzkl)var17_16;
                        var3_4 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                        var4_5 = var14_13.zznk + var3_4;
                        do {
                            if (var3_4 >= var4_5) {
                                if (var3_4 != var4_5) throw zzkq.zzdi();
                                return var3_4;
                            }
                            var3_4 = zziy.zza(var2_2 /* !! */ , var3_4, (zziz)var14_13);
                            var1_1.zzam(zzjo.zzw(var14_13.zznk));
                        } while (true);
                    }
                    var11_11 = var15_14;
                    if (var7_8 != false) return var11_11;
                    var1_1 = (zzkl)var17_16;
                    var6_7 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                    var1_1.zzam(zzjo.zzw(var14_13.zznk));
                    do {
                        var3_4 = var6_7;
                        if (var6_7 >= var4_5) return var3_4;
                        var7_8 = zziy.zza(var2_2 /* !! */ , var6_7, (zziz)var14_13);
                        var3_4 = var6_7;
                        if (var5_6 != var14_13.zznk) return var3_4;
                        var6_7 = zziy.zza(var2_2 /* !! */ , var7_8, (zziz)var14_13);
                        var1_1.zzam(zzjo.zzw(var14_13.zznk));
                    } while (true);
                }
                case 30: 
                case 44: {
                    if (var7_8 == 2) {
                        var3_4 = zziy.zza(var2_2 /* !! */ , var15_14, var17_16, (zziz)var14_13);
                    } else {
                        var11_11 = var15_14;
                        if (var7_8 != false) return var11_11;
                        var3_4 = zziy.zza((int)var5_6, var2_2 /* !! */ , var3_4, var4_5, var17_16, (zziz)var14_13);
                    }
                    var14_13 = (zzkk)var1_1;
                    var2_3 = var14_13.zzrq;
                    var1_1 = var2_3;
                    if (var2_3 == zzmy.zzfa()) {
                        var1_1 = null;
                    }
                    var1_1 = (zzmy)zzmh.zza(var6_7, var17_16, this.zzar((int)var8_9), var1_1, this.zzur);
                    var4_5 = var3_4;
                    if (var1_1 == null) return var4_5;
                    var14_13.zzrq = var1_1;
                    var4_5 = var3_4;
                    return var4_5;
                }
                case 28: {
                    var11_11 = var15_14;
                    if (var7_8 != 2) return var11_11;
                    var6_7 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                    var3_4 = var14_13.zznk;
                    if (var3_4 < 0) throw zzkq.zzdj();
                    if (var3_4 > var2_2 /* !! */ .length - var6_7) throw zzkq.zzdi();
                    if (var3_4 != 0) ** GOTO lbl111
                    var17_16.add((Integer)zzjc.zznq);
                    ** GOTO lbl115
lbl111: // 1 sources:
                    var17_16.add((Integer)zzjc.zzb(var2_2 /* !! */ , var6_7, var3_4));
                    do {
                        var6_7 += var3_4;
lbl115: // 2 sources:
                        do {
                            var3_4 = var6_7;
                            if (var6_7 >= var4_5) return var3_4;
                            var7_8 = zziy.zza(var2_2 /* !! */ , var6_7, (zziz)var14_13);
                            var3_4 = var6_7;
                            if (var5_6 != var14_13.zznk) return var3_4;
                            var6_7 = zziy.zza(var2_2 /* !! */ , var7_8, (zziz)var14_13);
                            var3_4 = var14_13.zznk;
                            if (var3_4 < 0) throw zzkq.zzdj();
                            if (var3_4 > var2_2 /* !! */ .length - var6_7) throw zzkq.zzdi();
                            if (var3_4 != 0) break;
                            var17_16.add((Integer)zzjc.zznq);
                        } while (true);
                        var17_16.add((Integer)zzjc.zzb(var2_2 /* !! */ , var6_7, var3_4));
                    } while (true);
                }
                case 27: {
                    var11_11 = var15_14;
                    if (var7_8 != 2) return var11_11;
                    return zziy.zza(this.zzap((int)var8_9), (int)var5_6, var2_2 /* !! */ , var3_4, var4_5, var17_16, (zziz)var14_13);
                }
                case 26: {
                    var11_11 = var15_14;
                    if (var7_8 != 2) return var11_11;
                    if ((var9_10 & 0x20000000L) != 0L) ** GOTO lbl150
                    var3_4 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                    var6_7 = var14_13.zznk;
                    if (var6_7 < 0) throw zzkq.zzdj();
                    if (var6_7 != 0) ** GOTO lbl147
                    var17_16.add((Integer)"");
                    ** GOTO lbl333
lbl147: // 1 sources:
                    var17_16.add((Integer)new String(var2_2 /* !! */ , var3_4, var6_7, zzkm.UTF_8));
                    break;
lbl150: // 1 sources:
                    var6_7 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                    var7_8 = var14_13.zznk;
                    if (var7_8 < 0) throw zzkq.zzdj();
                    if (var7_8 == 0) {
                        var17_16.add((Integer)"");
                        var3_4 = var6_7;
                    } else {
                        var3_4 = var6_7 + var7_8;
                        if (zznf.zze(var2_2 /* !! */ , var6_7, var3_4) == false) throw zzkq.zzdn();
                        var17_16.add((Integer)new String(var2_2 /* !! */ , var6_7, var7_8, zzkm.UTF_8));
                    }
                    do {
                        var11_11 = var3_4;
                        if (var3_4 >= var4_5) return var11_11;
                        var6_7 = zziy.zza(var2_2 /* !! */ , var3_4, (zziz)var14_13);
                        var11_11 = var3_4;
                        if (var5_6 != var14_13.zznk) return var11_11;
                        var6_7 = zziy.zza(var2_2 /* !! */ , var6_7, (zziz)var14_13);
                        var7_8 = var14_13.zznk;
                        if (var7_8 < 0) throw zzkq.zzdj();
                        if (var7_8 == 0) {
                            var17_16.add((Integer)"");
                            var3_4 = var6_7;
                            continue;
                        }
                        var3_4 = var6_7 + var7_8;
                        if (zznf.zze(var2_2 /* !! */ , var6_7, var3_4) == false) throw zzkq.zzdn();
                        var17_16.add((Integer)new String(var2_2 /* !! */ , var6_7, var7_8, zzkm.UTF_8));
                    } while (true);
                }
                case 25: 
                case 42: {
                    if (var7_8 != 2) ** GOTO lbl187
                    var1_1 = (zzja)var17_16;
                    var3_4 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                    var4_5 = var14_13.zznk + var3_4;
                    break block60;
lbl187: // 1 sources:
                    var11_11 = var15_14;
                    if (var7_8 != false) return var11_11;
                    var1_1 = (zzja)var17_16;
                    var3_4 = zziy.zzb(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                    var19_19 = var14_13.zznl != 0L;
                    var1_1.addBoolean(var19_19);
                    do {
                        var11_11 = var3_4;
                        if (var3_4 >= var4_5) return var11_11;
                        var6_7 = zziy.zza(var2_2 /* !! */ , var3_4, (zziz)var14_13);
                        var11_11 = var3_4;
                        if (var5_6 != var14_13.zznk) return var11_11;
                        var3_4 = zziy.zzb(var2_2 /* !! */ , var6_7, (zziz)var14_13);
                        var19_19 = var14_13.zznl != 0L;
                        var1_1.addBoolean(var19_19);
                    } while (true);
                }
                case 24: 
                case 31: 
                case 41: 
                case 45: {
                    if (var7_8 == 2) {
                        var1_1 = (zzkl)var17_16;
                        var3_4 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                        var4_5 = var14_13.zznk + var3_4;
                        do {
                            if (var3_4 >= var4_5) {
                                if (var3_4 != var4_5) throw zzkq.zzdi();
                                return var3_4;
                            }
                            var1_1.zzam(zziy.zza(var2_2 /* !! */ , var3_4));
                            var3_4 += 4;
                        } while (true);
                    }
                    var11_11 = var15_14;
                    if (var7_8 != 5) return var11_11;
                    var1_1 = (zzkl)var17_16;
                    var1_1.zzam(zziy.zza(var2_2 /* !! */ , var3_4));
                    do {
                        var3_4 = var6_7 = var15_14 + 4;
                        if (var6_7 >= var4_5) return var3_4;
                        var15_14 = zziy.zza(var2_2 /* !! */ , var6_7, (zziz)var14_13);
                        var3_4 = var6_7;
                        if (var5_6 != var14_13.zznk) return var3_4;
                        var1_1.zzam(zziy.zza(var2_2 /* !! */ , var15_14));
                    } while (true);
                }
                case 23: 
                case 32: 
                case 40: 
                case 46: {
                    if (var7_8 == 2) {
                        var1_1 = (zzle)var17_16;
                        var3_4 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                        var4_5 = var14_13.zznk + var3_4;
                        do {
                            if (var3_4 >= var4_5) {
                                if (var3_4 != var4_5) throw zzkq.zzdi();
                                return var3_4;
                            }
                            var1_1.zzv(zziy.zzb(var2_2 /* !! */ , var3_4));
                            var3_4 += 8;
                        } while (true);
                    }
                    var11_11 = var15_14;
                    if (var7_8 != true) return var11_11;
                    var1_1 = (zzle)var17_16;
                    var1_1.zzv(zziy.zzb(var2_2 /* !! */ , var3_4));
                    do {
                        var3_4 = var6_7 = var15_14 + 8;
                        if (var6_7 >= var4_5) return var3_4;
                        var15_14 = zziy.zza(var2_2 /* !! */ , var6_7, (zziz)var14_13);
                        var3_4 = var6_7;
                        if (var5_6 != var14_13.zznk) return var3_4;
                        var1_1.zzv(zziy.zzb(var2_2 /* !! */ , var15_14));
                    } while (true);
                }
                case 22: 
                case 29: 
                case 39: 
                case 43: {
                    if (var7_8 == 2) {
                        return zziy.zza(var2_2 /* !! */ , var15_14, var17_16, (zziz)var14_13);
                    }
                    var11_11 = var15_14;
                    if (var7_8 != false) return var11_11;
                    return zziy.zza((int)var5_6, var2_2 /* !! */ , var3_4, var4_5, var17_16, (zziz)var14_13);
                }
                case 20: 
                case 21: 
                case 37: 
                case 38: {
                    if (var7_8 == 2) {
                        var1_1 = (zzle)var17_16;
                        var3_4 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                        var4_5 = var14_13.zznk + var3_4;
                        do {
                            if (var3_4 >= var4_5) {
                                if (var3_4 != var4_5) throw zzkq.zzdi();
                                return var3_4;
                            }
                            var3_4 = zziy.zzb(var2_2 /* !! */ , var3_4, (zziz)var14_13);
                            var1_1.zzv(var14_13.zznl);
                        } while (true);
                    }
                    var11_11 = var15_14;
                    if (var7_8 != false) return var11_11;
                    var1_1 = (zzle)var17_16;
                    var6_7 = zziy.zzb(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                    var1_1.zzv(var14_13.zznl);
                    do {
                        var3_4 = var6_7;
                        if (var6_7 >= var4_5) return var3_4;
                        var7_8 = zziy.zza(var2_2 /* !! */ , var6_7, (zziz)var14_13);
                        var3_4 = var6_7;
                        if (var5_6 != var14_13.zznk) return var3_4;
                        var6_7 = zziy.zzb(var2_2 /* !! */ , var7_8, (zziz)var14_13);
                        var1_1.zzv(var14_13.zznl);
                    } while (true);
                }
                case 19: 
                case 36: {
                    if (var7_8 == 2) {
                        var1_1 = (zzkh)var17_16;
                        var3_4 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                        var4_5 = var14_13.zznk + var3_4;
                        do {
                            if (var3_4 >= var4_5) {
                                if (var3_4 != var4_5) throw zzkq.zzdi();
                                return var3_4;
                            }
                            var1_1.zzc(zziy.zzd(var2_2 /* !! */ , var3_4));
                            var3_4 += 4;
                        } while (true);
                    }
                    var11_11 = var15_14;
                    if (var7_8 != 5) return var11_11;
                    var1_1 = (zzkh)var17_16;
                    var1_1.zzc(zziy.zzd(var2_2 /* !! */ , var3_4));
                    do {
                        var3_4 = var6_7 = var15_14 + 4;
                        if (var6_7 >= var4_5) return var3_4;
                        var15_14 = zziy.zza(var2_2 /* !! */ , var6_7, (zziz)var14_13);
                        var3_4 = var6_7;
                        if (var5_6 != var14_13.zznk) return var3_4;
                        var1_1.zzc(zziy.zzd(var2_2 /* !! */ , var15_14));
                    } while (true);
                }
                case 18: 
                case 35: {
                    if (var7_8 == 2) {
                        var1_1 = (zzju)var17_16;
                        var3_4 = zziy.zza(var2_2 /* !! */ , var15_14, (zziz)var14_13);
                        var4_5 = var14_13.zznk + var3_4;
                        do {
                            if (var3_4 >= var4_5) {
                                if (var3_4 != var4_5) throw zzkq.zzdi();
                                return var3_4;
                            }
                            var1_1.zzc(zziy.zzc(var2_2 /* !! */ , var3_4));
                            var3_4 += 8;
                        } while (true);
                    }
                    var11_11 = var15_14;
                    if (var7_8 != true) return var11_11;
                    var1_1 = (zzju)var17_16;
                    var1_1.zzc(zziy.zzc(var2_2 /* !! */ , var3_4));
                    do {
                        var3_4 = var6_7 = var15_14 + 8;
                        if (var6_7 >= var4_5) return var3_4;
                        var15_14 = zziy.zza(var2_2 /* !! */ , var6_7, (zziz)var14_13);
                        var3_4 = var6_7;
                        if (var5_6 != var14_13.zznk) return var3_4;
                        var1_1.zzc(zziy.zzc(var2_2 /* !! */ , var15_14));
                    } while (true);
                }
            }
            do {
                var3_4 += var6_7;
lbl333: // 2 sources:
                do {
                    var11_11 = var3_4;
                    if (var3_4 >= var4_5) return var11_11;
                    var6_7 = zziy.zza(var2_2 /* !! */ , var3_4, (zziz)var14_13);
                    var11_11 = var3_4;
                    if (var5_6 != var14_13.zznk) return var11_11;
                    var3_4 = zziy.zza(var2_2 /* !! */ , var6_7, (zziz)var14_13);
                    var6_7 = var14_13.zznk;
                    if (var6_7 < 0) throw zzkq.zzdj();
                    if (var6_7 != 0) break;
                    var17_16.add((Integer)"");
                } while (true);
                var17_16.add((Integer)new String(var2_2 /* !! */ , var3_4, var6_7, zzkm.UTF_8));
            } while (true);
        }
        while (var3_4 < var4_5) {
            var3_4 = zziy.zzb(var2_2 /* !! */ , var3_4, (zziz)var14_13);
            var19_18 = var14_13.zznl != 0L;
            var1_1.addBoolean(var19_18);
        }
        if (var3_4 != var4_5) throw zzkq.zzdi();
        return var3_4;
    }

    private final <K, V> int zza(T object, byte[] arrby, int n, int n2, int n3, long l, zziz zziz2) throws IOException {
        Unsafe unsafe = zzuc;
        Map<?, ?> map = this.zzaq(n3);
        zzlj<?, ?> zzlj2 = unsafe.getObject(object, l);
        Object object2 = zzlj2;
        if (this.zzut.zzj(zzlj2)) {
            object2 = this.zzut.zzl(map);
            this.zzut.zzb(object2, zzlj2);
            unsafe.putObject(object, l, object2);
        }
        zzlj2 = this.zzut.zzm(map);
        map = this.zzut.zzh(object2);
        n = zziy.zza(arrby, n, zziz2);
        n3 = zziz2.zznk;
        if (n3 < 0) throw zzkq.zzdi();
        if (n3 > n2 - n) throw zzkq.zzdi();
        int n4 = n3 + n;
        object = zzlj2.zztv;
        object2 = zzlj2.zztx;
        do {
            if (n >= n4) {
                if (n != n4) throw zzkq.zzdm();
                map.put(object, object2);
                return n4;
            }
            int n5 = n + 1;
            int n6 = arrby[n];
            n3 = n5;
            n = n6;
            if (n6 < 0) {
                n3 = zziy.zza(n6, arrby, n5, zziz2);
                n = zziz2.zznk;
            }
            n6 = n >>> 3;
            n5 = n & 7;
            if (n6 != 1) {
                if (n6 == 2 && n5 == zzlj2.zztw.zzfk()) {
                    n = zzlu.zza(arrby, n3, n2, zzlj2.zztw, zzlj2.zztx.getClass(), zziz2);
                    object2 = zziz2.zznm;
                    continue;
                }
            } else if (n5 == zzlj2.zztu.zzfk()) {
                n = zzlu.zza(arrby, n3, n2, zzlj2.zztu, null, zziz2);
                object = zziz2.zznm;
                continue;
            }
            n = zziy.zza(n, arrby, n3, n2, zziz2);
        } while (true);
    }

    private static int zza(byte[] arrby, int n, int n2, zznm zznm2, Class<?> class_, zziz zziz2) throws IOException {
        switch (zzlv.zzox[zznm2.ordinal()]) {
            default: {
                throw new RuntimeException("unsupported field type.");
            }
            case 17: {
                return zziy.zzd(arrby, n, zziz2);
            }
            case 16: {
                n = zziy.zzb(arrby, n, zziz2);
                zziz2.zznm = zzjo.zzk(zziz2.zznl);
                return n;
            }
            case 15: {
                n = zziy.zza(arrby, n, zziz2);
                zziz2.zznm = zzjo.zzw(zziz2.zznk);
                return n;
            }
            case 14: {
                return zziy.zza(zzmd.zzej().zzf(class_), arrby, n, n2, zziz2);
            }
            case 12: 
            case 13: {
                n = zziy.zzb(arrby, n, zziz2);
                zziz2.zznm = zziz2.zznl;
                return n;
            }
            case 9: 
            case 10: 
            case 11: {
                n = zziy.zza(arrby, n, zziz2);
                zziz2.zznm = zziz2.zznk;
                return n;
            }
            case 8: {
                zziz2.zznm = Float.valueOf(zziy.zzd(arrby, n));
                return n += 4;
            }
            case 6: 
            case 7: {
                zziz2.zznm = zziy.zzb(arrby, n);
                return n += 8;
            }
            case 4: 
            case 5: {
                zziz2.zznm = zziy.zza(arrby, n);
                return n += 4;
            }
            case 3: {
                zziz2.zznm = zziy.zzc(arrby, n);
                return n += 8;
            }
            case 2: {
                return zziy.zze(arrby, n, zziz2);
            }
            case 1: 
        }
        n = zziy.zzb(arrby, n, zziz2);
        boolean bl = zziz2.zznl != 0L;
        zziz2.zznm = bl;
        return n;
    }

    static <T> zzlu<T> zza(Class<T> object, zzlo arrn, zzly zzly2, zzla zzla2, zzmx<?, ?> zzmx2, zzjy<?> zzjy2, zzll zzll2) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        if (!(arrn instanceof zzme)) {
            ((zzms)arrn).zzec();
            int n10 = zzkk.zze.zzsg;
            throw new NoSuchMethodError();
        }
        zzme zzme2 = (zzme)arrn;
        int n11 = zzme2.zzec();
        int n12 = zzkk.zze.zzsg;
        int n13 = 0;
        boolean bl = n11 == n12;
        object = zzme2.zzek();
        int n14 = ((String)object).length();
        int n15 = ((String)object).charAt(0);
        if (n15 >= 55296) {
            n2 = n15 & 8191;
            n7 = 1;
            n11 = 13;
            do {
                n12 = n7 + '\u0001';
                if ((n7 = ((String)object).charAt(n7)) < 55296) break;
                n2 |= (n7 & 8191) << n11;
                n11 += 13;
                n7 = n12;
            } while (true);
            n15 = n2 | n7 << n11;
            n11 = n12;
        } else {
            n11 = 1;
        }
        n12 = n11 + 1;
        n7 = ((String)object).charAt(n11);
        n11 = n12;
        n2 = n7;
        if (n7 >= 55296) {
            n2 = n7 & 8191;
            n11 = 13;
            n7 = n12;
            do {
                n12 = n7 + 1;
                if ((n7 = (int)((String)object).charAt(n7)) < 55296) break;
                n2 |= (n7 & 8191) << n11;
                n11 += 13;
                n7 = n12;
            } while (true);
            n2 |= n7 << n11;
            n11 = n12;
        }
        if (n2 == 0) {
            arrn = zzub;
            n9 = 0;
            n4 = 0;
            n6 = 0;
            n2 = 0;
            n8 = 0;
            n12 = 0;
            n7 = n13;
            n13 = n4;
        } else {
            n2 = n11 + 1;
            n12 = n11 = (int)((String)object).charAt(n11);
            n7 = n2;
            if (n11 >= 55296) {
                n12 = 13;
                n7 = n2;
                n2 = n11 &= 8191;
                do {
                    n11 = n7 + 1;
                    if ((n7 = (int)((String)object).charAt(n7)) < 55296) break;
                    n2 |= (n7 & 8191) << n12;
                    n12 += 13;
                    n7 = n11;
                } while (true);
                n12 = n2 | n7 << n12;
                n7 = n11;
            }
            n11 = n7 + 1;
            n8 = n2 = (int)((String)object).charAt(n7);
            n7 = n11;
            if (n2 >= 55296) {
                n7 = n2 & 8191;
                n2 = 13;
                n13 = n11;
                do {
                    n11 = n13 + 1;
                    if ((n13 = (int)((String)object).charAt(n13)) < 55296) break;
                    n7 |= (n13 & 8191) << n2;
                    n2 += 13;
                    n13 = n11;
                } while (true);
                n8 = n7 | n13 << n2;
                n7 = n11;
            }
            n2 = n7 + 1;
            n11 = n13 = (int)((String)object).charAt(n7);
            n7 = n2;
            if (n13 >= 55296) {
                n7 = n13 & 8191;
                n11 = 13;
                n13 = n2;
                do {
                    n2 = n13 + 1;
                    if ((n13 = (int)((String)object).charAt(n13)) < 55296) break;
                    n7 |= (n13 & 8191) << n11;
                    n11 += 13;
                    n13 = n2;
                } while (true);
                n11 = n7 | n13 << n11;
                n7 = n2;
            }
            n13 = n7 + 1;
            n6 = ((String)object).charAt(n7);
            n2 = n6;
            n7 = n13;
            if (n6 >= 55296) {
                n2 = n6 & 8191;
                n7 = 13;
                n6 = n13;
                n13 = n2;
                do {
                    n2 = n6 + 1;
                    if ((n6 = (int)((String)object).charAt(n6)) < 55296) break;
                    n13 |= (n6 & 8191) << n7;
                    n7 += 13;
                    n6 = n2;
                } while (true);
                n13 |= n6 << n7;
                n7 = n2;
                n2 = n13;
            }
            n13 = n7 + 1;
            n7 = n6 = (int)((String)object).charAt(n7);
            n4 = n13;
            if (n6 >= 55296) {
                n6 &= 8191;
                n7 = 13;
                n4 = n13;
                do {
                    n13 = n4 + 1;
                    if ((n4 = (int)((String)object).charAt(n4)) < 55296) break;
                    n6 |= (n4 & 8191) << n7;
                    n7 += 13;
                    n4 = n13;
                } while (true);
                n7 = n6 | n4 << n7;
                n4 = n13;
            }
            n6 = n4 + 1;
            n13 = n9 = (int)((String)object).charAt(n4);
            n4 = n6;
            if (n9 >= 55296) {
                n4 = n9 & 8191;
                n13 = 13;
                n9 = n6;
                do {
                    n6 = n9 + 1;
                    if ((n9 = (int)((String)object).charAt(n9)) < 55296) break;
                    n4 |= (n9 & 8191) << n13;
                    n13 += 13;
                    n9 = n6;
                } while (true);
                n13 = n4 | n9 << n13;
                n4 = n6;
            }
            n3 = n4 + 1;
            n9 = ((String)object).charAt(n4);
            if (n9 >= 55296) {
                n9 &= 8191;
                n4 = 13;
                do {
                    n6 = n3 + 1;
                    if ((n3 = (int)((String)object).charAt(n3)) < 55296) break;
                    n9 |= (n3 & 8191) << n4;
                    n4 += 13;
                    n3 = n6;
                } while (true);
                n9 |= n3 << n4;
            } else {
                n6 = n3;
            }
            n3 = n6 + 1;
            n4 = n5 = (int)((String)object).charAt(n6);
            n6 = n3;
            if (n5 >= 55296) {
                n4 = n5 & 8191;
                n6 = 13;
                n5 = n3;
                n3 = n4;
                do {
                    n4 = n5 + 1;
                    if ((n5 = (int)((String)object).charAt(n5)) < 55296) break;
                    n3 |= (n5 & 8191) << n6;
                    n6 += 13;
                    n5 = n4;
                } while (true);
                n3 |= n5 << n6;
                n6 = n4;
                n4 = n3;
            }
            arrn = new int[n4 + n13 + n9];
            n9 = n2;
            n = (n12 << 1) + n8;
            n2 = n4;
            n4 = n12;
            n3 = n6;
            n5 = n13;
            n12 = n2;
            n8 = n7;
            n2 = n;
            n6 = n11;
            n13 = n9;
            n11 = n3;
            n9 = n5;
            n7 = n4;
        }
        Unsafe unsafe = zzuc;
        Object[] arrobject = zzme2.zzel();
        Class<?> class_ = zzme2.zzee().getClass();
        int[] arrn2 = new int[n8 * 3];
        Object[] arrobject2 = new Object[n8 << 1];
        int n16 = n12 + n9;
        n4 = n12;
        n3 = n11;
        n = n16;
        n8 = 0;
        int n17 = 0;
        n11 = n4;
        n4 = n8;
        n8 = n6;
        n9 = n13;
        n13 = n3;
        n3 = n7;
        n5 = n14;
        while (n13 < n5) {
            int n18;
            int n19;
            int n20;
            int n21;
            int n22;
            int n23;
            int n24;
            int n25;
            block55 : {
                Object object2;
                block59 : {
                    block56 : {
                        block60 : {
                            block57 : {
                                block58 : {
                                    block54 : {
                                        n7 = n13 + 1;
                                        n14 = ((String)object).charAt(n13);
                                        if (n14 >= 55296) {
                                            n14 &= 8191;
                                            n13 = 13;
                                            do {
                                                n6 = n7 + 1;
                                                n21 = ((String)object).charAt(n7);
                                                n7 = n12;
                                                if (n21 < 55296) break;
                                                n14 |= (n21 & 8191) << n13;
                                                n13 += 13;
                                                n12 = n7;
                                                n7 = n6;
                                            } while (true);
                                            n14 |= n21 << n13;
                                            n13 = n6;
                                        } else {
                                            n13 = n7;
                                            n7 = n12;
                                        }
                                        n12 = n13 + 1;
                                        n19 = ((String)object).charAt(n13);
                                        if (n19 >= 55296) {
                                            n6 = n19 & 8191;
                                            n13 = 13;
                                            n21 = n12;
                                            do {
                                                n12 = n21 + 1;
                                                if ((n21 = (int)((String)object).charAt(n21)) < 55296) break;
                                                n6 |= (n21 & 8191) << n13;
                                                n13 += 13;
                                                n21 = n12;
                                            } while (true);
                                            n19 = n6 | n21 << n13;
                                            n13 = n12;
                                        } else {
                                            n13 = n12;
                                        }
                                        n23 = n19 & 255;
                                        n21 = n4;
                                        if ((n19 & 1024) != 0) {
                                            arrn[n4] = n17;
                                            n21 = n4 + 1;
                                        }
                                        if (n23 < 51) break block54;
                                        n4 = n13 + 1;
                                        n13 = ((String)object).charAt(n13);
                                        n12 = n4;
                                        n6 = n13;
                                        if (n13 >= 55296) {
                                            n6 = n13 & 8191;
                                            n12 = 13;
                                            do {
                                                n13 = n4 + 1;
                                                if ((n4 = (int)((String)object).charAt(n4)) < 55296) break;
                                                n6 |= (n4 & 8191) << n12;
                                                n12 += 13;
                                                n4 = n13;
                                            } while (true);
                                            n6 |= n4 << n12;
                                            n12 = n13;
                                        }
                                        if ((n4 = n23 - 51) != 9 && n4 != 17) {
                                            n13 = n2;
                                            if (n4 == 12) {
                                                n13 = n2;
                                                if ((n15 & 1) == 1) {
                                                    arrobject2[(n17 / 3 << 1) + 1] = arrobject[n2];
                                                    n13 = n2 + 1;
                                                }
                                            }
                                            n2 = n13;
                                        } else {
                                            arrobject2[(n17 / 3 << 1) + 1] = arrobject[n2];
                                            ++n2;
                                        }
                                        n13 = n6 << 1;
                                        object2 = arrobject[n13];
                                        if (object2 instanceof Field) {
                                            object2 = (Field)object2;
                                        } else {
                                            arrobject[n13] = object2 = zzlu.zza(class_, (String)object2);
                                        }
                                        n25 = (int)unsafe.objectFieldOffset((Field)object2);
                                        object2 = arrobject[++n13];
                                        if (object2 instanceof Field) {
                                            object2 = (Field)object2;
                                        } else {
                                            arrobject[n13] = object2 = zzlu.zza(class_, (String)object2);
                                        }
                                        n22 = (int)unsafe.objectFieldOffset((Field)object2);
                                        n18 = 0;
                                        n13 = n12;
                                        n24 = n11;
                                        n20 = n;
                                        n12 = n2;
                                        break block55;
                                    }
                                    n6 = n2 + 1;
                                    object2 = zzlu.zza(class_, (String)arrobject[n2]);
                                    if (n23 == 9 || n23 == 17) break block56;
                                    if (n23 == 27 || n23 == 49) break block57;
                                    if (n23 == 12 || n23 == 30 || n23 == 44) break block58;
                                    n12 = n6;
                                    n2 = n11;
                                    if (n23 == 50) {
                                        n12 = n11 + 1;
                                        arrn[n11] = n17;
                                        n4 = n17 / 3 << 1;
                                        n2 = n6 + 1;
                                        arrobject2[n4] = arrobject[n6];
                                        if ((n19 & 2048) != 0) {
                                            n11 = n2 + 1;
                                            arrobject2[n4 + 1] = arrobject[n2];
                                            n2 = n12;
                                            n12 = n11;
                                        } else {
                                            n11 = n2;
                                            n2 = n12;
                                            n12 = n11;
                                        }
                                    }
                                    break block59;
                                }
                                n12 = n6;
                                n2 = n11;
                                if ((n15 & 1) != 1) break block59;
                                n2 = n17 / 3;
                                n12 = n6 + 1;
                                arrobject2[(n2 << 1) + 1] = arrobject[n6];
                                break block60;
                            }
                            n2 = n17 / 3;
                            n12 = n6 + 1;
                            arrobject2[(n2 << 1) + 1] = arrobject[n6];
                        }
                        n2 = n11;
                        break block59;
                    }
                    arrobject2[(n17 / 3 << 1) + 1] = ((Field)object2).getType();
                    n2 = n11;
                    n12 = n6;
                }
                int n26 = (int)unsafe.objectFieldOffset((Field)object2);
                if ((n15 & 1) == 1 && n23 <= 17) {
                    n6 = n13 + 1;
                    object2 = object;
                    n4 = ((String)object2).charAt(n13);
                    n11 = n6;
                    n13 = n4;
                    if (n4 >= 55296) {
                        n4 &= 8191;
                        n11 = 13;
                        do {
                            n13 = n6 + 1;
                            if ((n6 = (int)((String)object2).charAt(n6)) < 55296) break;
                            n4 |= (n6 & 8191) << n11;
                            n11 += 13;
                            n6 = n13;
                        } while (true);
                        n6 = n4 | n6 << n11;
                        n11 = n13;
                        n13 = n6;
                    }
                    if ((object2 = arrobject[n6 = (n3 << 1) + n13 / 32]) instanceof Field) {
                        object2 = (Field)object2;
                    } else {
                        arrobject[n6] = object2 = zzlu.zza(class_, (String)object2);
                    }
                    n6 = (int)unsafe.objectFieldOffset((Field)object2);
                    n4 = n13 % 32;
                } else {
                    n6 = 0;
                    n4 = 0;
                    n11 = n13;
                }
                int n27 = n12;
                n25 = n26;
                n22 = n6;
                n18 = n4;
                n13 = n11;
                n24 = n2;
                n20 = n;
                n12 = n27;
                if (n23 >= 18) {
                    n25 = n26;
                    n22 = n6;
                    n18 = n4;
                    n13 = n11;
                    n24 = n2;
                    n20 = n;
                    n12 = n27;
                    if (n23 <= 49) {
                        arrn[n] = n26;
                        n20 = n + 1;
                        n12 = n27;
                        n24 = n2;
                        n13 = n11;
                        n18 = n4;
                        n22 = n6;
                        n25 = n26;
                    }
                }
            }
            n6 = n17 + 1;
            arrn2[n17] = n14;
            n4 = n6 + 1;
            n11 = (n19 & 512) != 0 ? 536870912 : 0;
            n2 = (n19 & 256) != 0 ? 268435456 : 0;
            arrn2[n6] = n25 | (n2 | n11 | n23 << 20);
            n17 = n4 + 1;
            arrn2[n4] = n18 << 20 | n22;
            n2 = n12;
            n12 = n7;
            n4 = n21;
            n11 = n24;
            n = n20;
        }
        return new zzlu<T>(arrn2, arrobject2, n8, n9, zzme2.zzee(), bl, false, arrn, n12, n16, zzly2, zzla2, zzmx2, zzjy2, zzll2);
    }

    private final <K, V, UT, UB> UB zza(int n, int n2, Map<K, V> map, zzko zzko2, UB object, zzmx<UT, UB> zzmx2) {
        zzlj<?, ?> zzlj2 = this.zzut.zzm(this.zzaq(n));
        Iterator<Map.Entry<K, V>> iterator2 = map.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<K, V> entry = iterator2.next();
            if (zzko2.zzan((Integer)entry.getValue())) continue;
            map = object;
            if (object == null) {
                map = zzmx2.zzez();
            }
            zzjk zzjk2 = zzjc.zzu(zzli.zza(zzlj2, entry.getKey(), entry.getValue()));
            object = zzjk2.zzby();
            try {
                zzli.zza(object, zzlj2, entry.getKey(), entry.getValue());
                zzmx2.zza(map, n2, zzjk2.zzbx());
                iterator2.remove();
                object = map;
            }
            catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
        }
        return object;
    }

    private static Field zza(Class<?> object, String string2) {
        try {
            return ((Class)object).getDeclaredField(string2);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Object object2 = ((Class)object).getDeclaredFields();
            int n = ((Field[])object2).length;
            int n2 = 0;
            do {
                Object object3;
                if (n2 >= n) {
                    object = ((Class)object).getName();
                    object3 = Arrays.toString((Object[])object2);
                    object2 = new StringBuilder(String.valueOf(string2).length() + 40 + String.valueOf(object).length() + String.valueOf(object3).length());
                    ((StringBuilder)object2).append("Field ");
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append(" for ");
                    ((StringBuilder)object2).append((String)object);
                    ((StringBuilder)object2).append(" not found. Known fields are ");
                    ((StringBuilder)object2).append((String)object3);
                    throw new RuntimeException(((StringBuilder)object2).toString());
                }
                object3 = object2[n2];
                if (string2.equals(((Field)object3).getName())) {
                    return object3;
                }
                ++n2;
            } while (true);
        }
    }

    private static void zza(int n, Object object, zzns zzns2) throws IOException {
        if (object instanceof String) {
            zzns2.zza(n, (String)object);
            return;
        }
        zzns2.zza(n, (zzjc)object);
    }

    private static <UT, UB> void zza(zzmx<UT, UB> zzmx2, T t, zzns zzns2) throws IOException {
        zzmx2.zza(zzmx2.zzr(t), zzns2);
    }

    private final <K, V> void zza(zzns zzns2, int n, Object object, int n2) throws IOException {
        if (object == null) return;
        zzns2.zza(n, this.zzut.zzm(this.zzaq(n2)), this.zzut.zzi(object));
    }

    private final void zza(T t, T object, int n) {
        long l = this.zzas(n) & 1048575;
        if (!this.zza(object, n)) {
            return;
        }
        Object object2 = zznd.zzo(t, l);
        object = zznd.zzo(object, l);
        if (object2 != null && object != null) {
            zznd.zza(t, l, zzkm.zza(object2, object));
            this.zzb(t, n);
            return;
        }
        if (object == null) return;
        zznd.zza(t, l, object);
        this.zzb(t, n);
    }

    private final boolean zza(T object, int n) {
        if (!this.zzuk) {
            if ((zznd.zzj(object, (n = this.zzat(n)) & 1048575) & 1 << (n >>> 20)) == 0) return false;
            return true;
        }
        n = this.zzas(n);
        long l = n & 1048575;
        switch ((n & 267386880) >>> 20) {
            default: {
                throw new IllegalArgumentException();
            }
            case 17: {
                if (zznd.zzo(object, l) == null) return false;
                return true;
            }
            case 16: {
                if (zznd.zzk(object, l) == 0L) return false;
                return true;
            }
            case 15: {
                if (zznd.zzj(object, l) == 0) return false;
                return true;
            }
            case 14: {
                if (zznd.zzk(object, l) == 0L) return false;
                return true;
            }
            case 13: {
                if (zznd.zzj(object, l) == 0) return false;
                return true;
            }
            case 12: {
                if (zznd.zzj(object, l) == 0) return false;
                return true;
            }
            case 11: {
                if (zznd.zzj(object, l) == 0) return false;
                return true;
            }
            case 10: {
                if (zzjc.zznq.equals(zznd.zzo(object, l))) return false;
                return true;
            }
            case 9: {
                if (zznd.zzo(object, l) == null) return false;
                return true;
            }
            case 8: {
                object = zznd.zzo(object, l);
                if (object instanceof String) {
                    if (((String)object).isEmpty()) return false;
                    return true;
                }
                if (!(object instanceof zzjc)) throw new IllegalArgumentException();
                if (zzjc.zznq.equals(object)) return false;
                return true;
            }
            case 7: {
                return zznd.zzl(object, l);
            }
            case 6: {
                if (zznd.zzj(object, l) == 0) return false;
                return true;
            }
            case 5: {
                if (zznd.zzk(object, l) == 0L) return false;
                return true;
            }
            case 4: {
                if (zznd.zzj(object, l) == 0) return false;
                return true;
            }
            case 3: {
                if (zznd.zzk(object, l) == 0L) return false;
                return true;
            }
            case 2: {
                if (zznd.zzk(object, l) == 0L) return false;
                return true;
            }
            case 1: {
                if (zznd.zzm(object, l) == 0.0f) return false;
                return true;
            }
            case 0: 
        }
        if (zznd.zzn(object, l) == 0.0) return false;
        return true;
    }

    private final boolean zza(T t, int n, int n2) {
        if (zznd.zzj(t, this.zzat(n2) & 1048575) != n) return false;
        return true;
    }

    private final boolean zza(T t, int n, int n2, int n3) {
        if (this.zzuk) {
            return this.zza(t, n);
        }
        if ((n2 & n3) == 0) return false;
        return true;
    }

    private static boolean zza(Object object, int n, zzmf zzmf2) {
        return zzmf2.zzp(zznd.zzo(object, n & 1048575));
    }

    private final zzmf zzap(int n) {
        zzmf zzmf2 = (zzmf)this.zzue[n = n / 3 << 1];
        if (zzmf2 != null) {
            return zzmf2;
        }
        this.zzue[n] = zzmf2 = zzmd.zzej().zzf((Class)this.zzue[n + 1]);
        return zzmf2;
    }

    private final Object zzaq(int n) {
        return this.zzue[n / 3 << 1];
    }

    private final zzko zzar(int n) {
        return (zzko)this.zzue[(n / 3 << 1) + 1];
    }

    private final int zzas(int n) {
        return this.zzud[n + 1];
    }

    private final int zzat(int n) {
        return this.zzud[n + 2];
    }

    private final int zzau(int n) {
        if (n < this.zzuf) return -1;
        if (n > this.zzug) return -1;
        return this.zzq(n, 0);
    }

    private final void zzb(T t, int n) {
        if (this.zzuk) {
            return;
        }
        n = this.zzat(n);
        long l = n & 1048575;
        zznd.zza(t, l, zznd.zzj(t, l) | 1 << (n >>> 20));
    }

    private final void zzb(T t, int n, int n2) {
        zznd.zza(t, (long)(this.zzat(n2) & 1048575), n);
    }

    /*
     * Unable to fully structure code
     */
    private final void zzb(T var1_1, zzns var2_2) throws IOException {
        if (!this.zzui) ** GOTO lbl-1000
        var3_3 = this.zzus.zzb(var1_1);
        if (!var3_3.zzos.isEmpty()) {
            var4_10 = var3_3.iterator();
            var3_4 = var4_10.next();
        } else lbl-1000: // 2 sources:
        {
            var4_10 = null;
            var3_5 = null;
        }
        var5_11 = -1;
        var6_12 = this.zzud.length;
        var7_13 = zzlu.zzuc;
        var8_14 = 0;
        var9_15 = 0;
        do {
            block118 : {
                var10_17 = var3_7;
                if (var8_14 >= var6_12) break;
                var11_21 = this.zzas(var8_14);
                var10_16 = this.zzud;
                var12_22 = var10_16[var8_14];
                var13_23 = (267386880 & var11_21) >>> 20;
                if (!this.zzuk && var13_23 <= 17) {
                    var14_24 = var10_16[var8_14 + 2];
                    var15_25 = var14_24 & 1048575;
                    var16_26 = var5_11;
                    if (var15_25 != var5_11) {
                        var9_15 = var7_13.getInt(var1_1, var15_25);
                        var16_26 = var15_25;
                    }
                    var15_25 = 1 << (var14_24 >>> 20);
                    var5_11 = var16_26;
                } else {
                    var15_25 = 0;
                }
                do {
                    var16_26 = var8_14;
                    if (var3_7 == null || this.zzus.zza((Map.Entry<?, ?>)var3_7) > var12_22) break;
                    this.zzus.zza(var2_2, (Map.Entry<?, ?>)var3_7);
                    if (var4_10.hasNext()) {
                        var3_8 = var4_10.next();
                        continue;
                    }
                    var3_9 = null;
                } while (true);
                var17_27 = var11_21 & 1048575;
                switch (var13_23) {
                    case 68: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zzb(var12_22, var7_13.getObject(var1_1, var17_27), this.zzap(var16_26));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 67: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zzb(var12_22, zzlu.zzh(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 66: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zze(var12_22, zzlu.zzg(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 65: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zzj(var12_22, zzlu.zzh(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 64: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zzm(var12_22, zzlu.zzg(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 63: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zzn(var12_22, zzlu.zzg(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 62: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zzd(var12_22, zzlu.zzg(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 61: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zza(var12_22, (zzjc)var7_13.getObject(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 60: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zza(var12_22, var7_13.getObject(var1_1, var17_27), this.zzap(var16_26));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 59: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            zzlu.zza(var12_22, var7_13.getObject(var1_1, var17_27), var2_2);
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 58: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zzb(var12_22, zzlu.zzi(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 57: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zzf(var12_22, zzlu.zzg(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 56: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zzc(var12_22, zzlu.zzh(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 55: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zzc(var12_22, zzlu.zzg(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 54: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zza(var12_22, zzlu.zzh(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 53: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zzi(var12_22, zzlu.zzh(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 52: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zza(var12_22, zzlu.zzf(var1_1, var17_27));
                            ** break;
                        }
                        ** GOTO lbl132
                    }
                    case 51: {
                        if (this.zza(var1_1, var12_22, var16_26)) {
                            var2_2.zza(var12_22, zzlu.zze(var1_1, var17_27));
                            ** break;
                        }
                    }
lbl132: // 20 sources:
                    default: {
                        ** break;
                    }
                    case 50: {
                        this.zza(var2_2, var12_22, var7_13.getObject(var1_1, var17_27), var16_26);
                        ** break;
                    }
                    case 49: {
                        zzmh.zzb(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, this.zzap(var16_26));
                        ** break;
                    }
                    case 48: {
                        zzmh.zze(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 47: {
                        zzmh.zzj(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 46: {
                        zzmh.zzg(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 45: {
                        zzmh.zzl(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 44: {
                        zzmh.zzm(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 43: {
                        zzmh.zzi(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 42: {
                        zzmh.zzn(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 41: {
                        zzmh.zzk(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 40: {
                        zzmh.zzf(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 39: {
                        zzmh.zzh(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 38: {
                        zzmh.zzd(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 37: {
                        zzmh.zzc(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 36: {
                        zzmh.zzb(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 35: {
                        zzmh.zza(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, true);
                        ** break;
                    }
                    case 34: {
                        zzmh.zze(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 33: {
                        zzmh.zzj(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 32: {
                        zzmh.zzg(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 31: {
                        zzmh.zzl(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 30: {
                        zzmh.zzm(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 29: {
                        zzmh.zzi(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 28: {
                        zzmh.zzb(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2);
                        ** break;
                    }
                    case 27: {
                        zzmh.zza(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, this.zzap(var16_26));
                        ** break;
                    }
                    case 26: {
                        zzmh.zza(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2);
                        ** break;
                    }
                    case 25: {
                        zzmh.zzn(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 24: {
                        zzmh.zzk(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 23: {
                        zzmh.zzf(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 22: {
                        zzmh.zzh(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 21: {
                        zzmh.zzd(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 20: {
                        zzmh.zzc(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 19: {
                        zzmh.zzb(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 18: {
                        zzmh.zza(this.zzud[var16_26], (List)var7_13.getObject(var1_1, var17_27), var2_2, false);
                        ** break;
                    }
                    case 17: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zzb(var12_22, var7_13.getObject(var1_1, var17_27), this.zzap(var16_26));
                            ** break;
                        }
                        break block118;
                    }
                    case 16: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zzb(var12_22, var7_13.getLong(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 15: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zze(var12_22, var7_13.getInt(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 14: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zzj(var12_22, var7_13.getLong(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 13: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zzm(var12_22, var7_13.getInt(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 12: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zzn(var12_22, var7_13.getInt(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 11: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zzd(var12_22, var7_13.getInt(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 10: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zza(var12_22, (zzjc)var7_13.getObject(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 9: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zza(var12_22, var7_13.getObject(var1_1, var17_27), this.zzap(var16_26));
                            ** break;
                        }
                        break block118;
                    }
                    case 8: {
                        if ((var15_25 & var9_15) != 0) {
                            zzlu.zza(var12_22, var7_13.getObject(var1_1, var17_27), var2_2);
                            ** break;
                        }
                        break block118;
                    }
                    case 7: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zzb(var12_22, zznd.zzl(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 6: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zzf(var12_22, var7_13.getInt(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 5: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zzc(var12_22, var7_13.getLong(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 4: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zzc(var12_22, var7_13.getInt(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 3: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zza(var12_22, var7_13.getLong(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 2: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zzi(var12_22, var7_13.getLong(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 1: {
                        if ((var15_25 & var9_15) != 0) {
                            var2_2.zza(var12_22, zznd.zzm(var1_1, var17_27));
                            ** break;
                        }
                        break block118;
                    }
                    case 0: 
                }
                if ((var15_25 & var9_15) != 0) {
                    var2_2.zza(var12_22, zznd.zzn(var1_1, var17_27));
                }
            }
            var8_14 = var16_26 + 3;
        } while (true);
        do {
            if (var10_18 == null) {
                zzlu.zza(this.zzur, var1_1, var2_2);
                return;
            }
            this.zzus.zza(var2_2, (Map.Entry<?, ?>)var10_18);
            if (var4_10.hasNext()) {
                var10_19 = var4_10.next();
                continue;
            }
            var10_20 = null;
        } while (true);
    }

    private final void zzb(T t, T object, int n) {
        int n2 = this.zzas(n);
        int n3 = this.zzud[n];
        long l = n2 & 1048575;
        if (!this.zza((T)object, n3, n)) {
            return;
        }
        Object object2 = zznd.zzo(t, l);
        object = zznd.zzo(object, l);
        if (object2 != null && object != null) {
            zznd.zza(t, l, zzkm.zza(object2, object));
            this.zzb(t, (T)n3, n);
            return;
        }
        if (object == null) return;
        zznd.zza(t, l, object);
        this.zzb(t, (T)n3, n);
    }

    private final boolean zzc(T t, T t2, int n) {
        if (this.zza(t, n) != this.zza(t2, n)) return false;
        return true;
    }

    private static <E> List<E> zzd(Object object, long l) {
        return (List)zznd.zzo(object, l);
    }

    private static <T> double zze(T t, long l) {
        return (Double)zznd.zzo(t, l);
    }

    private static <T> float zzf(T t, long l) {
        return ((Float)zznd.zzo(t, l)).floatValue();
    }

    private static <T> int zzg(T t, long l) {
        return (Integer)zznd.zzo(t, l);
    }

    private static <T> long zzh(T t, long l) {
        return (Long)zznd.zzo(t, l);
    }

    private static <T> boolean zzi(T t, long l) {
        return (Boolean)zznd.zzo(t, l);
    }

    private static zzmy zzo(Object object) {
        zzkk zzkk2 = (zzkk)object;
        zzmy zzmy2 = zzkk2.zzrq;
        object = zzmy2;
        if (zzmy2 != zzmy.zzfa()) return object;
        zzkk2.zzrq = object = zzmy.zzfb();
        return object;
    }

    private final int zzp(int n, int n2) {
        if (n < this.zzuf) return -1;
        if (n > this.zzug) return -1;
        return this.zzq(n, n2);
    }

    private final int zzq(int n, int n2) {
        int n3 = this.zzud.length / 3 - 1;
        while (n2 <= n3) {
            int n4 = n3 + n2 >>> 1;
            int n5 = n4 * 3;
            int n6 = this.zzud[n5];
            if (n == n6) {
                return n5;
            }
            if (n < n6) {
                n3 = n4 - 1;
                continue;
            }
            n2 = n4 + 1;
        }
        return -1;
    }

    @Override
    public final boolean equals(T t, T t2) {
        int n = this.zzud.length;
        int n2 = 0;
        do {
            boolean bl;
            block44 : {
                bl = true;
                if (n2 >= n) break;
                int n3 = this.zzas(n2);
                long l = n3 & 1048575;
                switch ((n3 & 267386880) >>> 20) {
                    default: {
                        break block44;
                    }
                    case 51: 
                    case 52: 
                    case 53: 
                    case 54: 
                    case 55: 
                    case 56: 
                    case 57: 
                    case 58: 
                    case 59: 
                    case 60: 
                    case 61: 
                    case 62: 
                    case 63: 
                    case 64: 
                    case 65: 
                    case 66: 
                    case 67: 
                    case 68: {
                        long l2 = this.zzat(n2) & 1048575;
                        if (zznd.zzj(t, l2) != zznd.zzj(t2, l2)) return false;
                        if (!zzmh.zzd(zznd.zzo(t, l), zznd.zzo(t2, l))) {
                            return false;
                        }
                        break block44;
                    }
                    case 50: {
                        bl = zzmh.zzd(zznd.zzo(t, l), zznd.zzo(t2, l));
                        break block44;
                    }
                    case 18: 
                    case 19: 
                    case 20: 
                    case 21: 
                    case 22: 
                    case 23: 
                    case 24: 
                    case 25: 
                    case 26: 
                    case 27: 
                    case 28: 
                    case 29: 
                    case 30: 
                    case 31: 
                    case 32: 
                    case 33: 
                    case 34: 
                    case 35: 
                    case 36: 
                    case 37: 
                    case 38: 
                    case 39: 
                    case 40: 
                    case 41: 
                    case 42: 
                    case 43: 
                    case 44: 
                    case 45: 
                    case 46: 
                    case 47: 
                    case 48: 
                    case 49: {
                        bl = zzmh.zzd(zznd.zzo(t, l), zznd.zzo(t2, l));
                        break block44;
                    }
                    case 17: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (!zzmh.zzd(zznd.zzo(t, l), zznd.zzo(t2, l))) {
                            return false;
                        }
                        break block44;
                    }
                    case 16: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzk(t, l) != zznd.zzk(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 15: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzj(t, l) != zznd.zzj(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 14: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzk(t, l) != zznd.zzk(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 13: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzj(t, l) != zznd.zzj(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 12: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzj(t, l) != zznd.zzj(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 11: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzj(t, l) != zznd.zzj(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 10: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (!zzmh.zzd(zznd.zzo(t, l), zznd.zzo(t2, l))) {
                            return false;
                        }
                        break block44;
                    }
                    case 9: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (!zzmh.zzd(zznd.zzo(t, l), zznd.zzo(t2, l))) {
                            return false;
                        }
                        break block44;
                    }
                    case 8: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (!zzmh.zzd(zznd.zzo(t, l), zznd.zzo(t2, l))) {
                            return false;
                        }
                        break block44;
                    }
                    case 7: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzl(t, l) != zznd.zzl(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 6: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzj(t, l) != zznd.zzj(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 5: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzk(t, l) != zznd.zzk(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 4: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzj(t, l) != zznd.zzj(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 3: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzk(t, l) != zznd.zzk(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 2: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (zznd.zzk(t, l) != zznd.zzk(t2, l)) {
                            return false;
                        }
                        break block44;
                    }
                    case 1: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (Float.floatToIntBits(zznd.zzm(t, l)) != Float.floatToIntBits(zznd.zzm(t2, l))) {
                            return false;
                        }
                        break block44;
                    }
                    case 0: {
                        if (!this.zzc(t, t2, n2)) return false;
                        if (Double.doubleToLongBits(zznd.zzn(t, l)) == Double.doubleToLongBits(zznd.zzn(t2, l))) break block44;
                    }
                }
                return false;
            }
            if (!bl) {
                return false;
            }
            n2 += 3;
        } while (true);
        if (!this.zzur.zzr(t).equals(this.zzur.zzr(t2))) {
            return false;
        }
        if (!this.zzui) return true;
        return this.zzus.zzb(t).equals(this.zzus.zzb(t2));
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public final int hashCode(T var1_1) {
        var2_2 = this.zzud.length;
        var3_3 = 0;
        var4_4 = 0;
        do {
            block44 : {
                block45 : {
                    if (var3_3 >= var2_2) {
                        var9_8 = var4_4 = var4_4 * 53 + this.zzur.zzr(var1_1).hashCode();
                        if (this.zzui == false) return var9_8;
                        return var4_4 * 53 + this.zzus.zzb(var1_1).hashCode();
                    }
                    var5_5 = this.zzas(var3_3);
                    var6_6 = this.zzud[var3_3];
                    var7_7 = 1048575 & var5_5;
                    var9_8 = 37;
                    switch ((var5_5 & 267386880) >>> 20) {
                        default: {
                            var9_8 = var4_4;
                            break block44;
                        }
                        case 68: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var10_9 = zznd.zzo(var1_1, var7_7);
                            var9_8 = var4_4 * 53;
                            var4_4 = var10_9.hashCode();
                            break block45;
                        }
                        case 67: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zzu(zzlu.zzh(var1_1, var7_7));
                            break block45;
                        }
                        case 66: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzlu.zzg(var1_1, var7_7);
                            break block45;
                        }
                        case 65: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zzu(zzlu.zzh(var1_1, var7_7));
                            break block45;
                        }
                        case 64: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzlu.zzg(var1_1, var7_7);
                            break block45;
                        }
                        case 63: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzlu.zzg(var1_1, var7_7);
                            break block45;
                        }
                        case 62: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzlu.zzg(var1_1, var7_7);
                            break block45;
                        }
                        case 61: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zznd.zzo(var1_1, var7_7).hashCode();
                            break block45;
                        }
                        case 60: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var10_9 = zznd.zzo(var1_1, var7_7);
                            var9_8 = var4_4 * 53;
                            var4_4 = var10_9.hashCode();
                            break block45;
                        }
                        case 59: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = ((String)zznd.zzo(var1_1, var7_7)).hashCode();
                            break block45;
                        }
                        case 58: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zze(zzlu.zzi(var1_1, var7_7));
                            break block45;
                        }
                        case 57: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzlu.zzg(var1_1, var7_7);
                            break block45;
                        }
                        case 56: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zzu(zzlu.zzh(var1_1, var7_7));
                            break block45;
                        }
                        case 55: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzlu.zzg(var1_1, var7_7);
                            break block45;
                        }
                        case 54: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zzu(zzlu.zzh(var1_1, var7_7));
                            break block45;
                        }
                        case 53: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zzu(zzlu.zzh(var1_1, var7_7));
                            break block45;
                        }
                        case 52: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = Float.floatToIntBits(zzlu.zzf(var1_1, var7_7));
                            break block45;
                        }
                        case 51: {
                            var9_8 = var4_4;
                            if (!this.zza(var1_1, var6_6, var3_3)) break block44;
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zzu(Double.doubleToLongBits(zzlu.zze(var1_1, var7_7)));
                            break block45;
                        }
                        case 50: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zznd.zzo(var1_1, var7_7).hashCode();
                            break block45;
                        }
                        case 18: 
                        case 19: 
                        case 20: 
                        case 21: 
                        case 22: 
                        case 23: 
                        case 24: 
                        case 25: 
                        case 26: 
                        case 27: 
                        case 28: 
                        case 29: 
                        case 30: 
                        case 31: 
                        case 32: 
                        case 33: 
                        case 34: 
                        case 35: 
                        case 36: 
                        case 37: 
                        case 38: 
                        case 39: 
                        case 40: 
                        case 41: 
                        case 42: 
                        case 43: 
                        case 44: 
                        case 45: 
                        case 46: 
                        case 47: 
                        case 48: 
                        case 49: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zznd.zzo(var1_1, var7_7).hashCode();
                            break block45;
                        }
                        case 17: {
                            var10_9 = zznd.zzo(var1_1, var7_7);
                            if (var10_9 != null) {
                                var9_8 = var10_9.hashCode();
                            }
                            ** GOTO lbl172
                        }
                        case 16: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zzu(zznd.zzk(var1_1, var7_7));
                            break block45;
                        }
                        case 15: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zznd.zzj(var1_1, var7_7);
                            break block45;
                        }
                        case 14: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zzu(zznd.zzk(var1_1, var7_7));
                            break block45;
                        }
                        case 13: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zznd.zzj(var1_1, var7_7);
                            break block45;
                        }
                        case 12: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zznd.zzj(var1_1, var7_7);
                            break block45;
                        }
                        case 11: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zznd.zzj(var1_1, var7_7);
                            break block45;
                        }
                        case 10: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zznd.zzo(var1_1, var7_7).hashCode();
                            break block45;
                        }
                        case 9: {
                            var10_9 = zznd.zzo(var1_1, var7_7);
                            if (var10_9 != null) {
                                var9_8 = var10_9.hashCode();
                            }
lbl172: // 4 sources:
                            var9_8 = var4_4 * 53 + var9_8;
                            break block44;
                        }
                        case 8: {
                            var9_8 = var4_4 * 53;
                            var4_4 = ((String)zznd.zzo(var1_1, var7_7)).hashCode();
                            break block45;
                        }
                        case 7: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zze(zznd.zzl(var1_1, var7_7));
                            break block45;
                        }
                        case 6: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zznd.zzj(var1_1, var7_7);
                            break block45;
                        }
                        case 5: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zzu(zznd.zzk(var1_1, var7_7));
                            break block45;
                        }
                        case 4: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zznd.zzj(var1_1, var7_7);
                            break block45;
                        }
                        case 3: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zzu(zznd.zzk(var1_1, var7_7));
                            break block45;
                        }
                        case 2: {
                            var9_8 = var4_4 * 53;
                            var4_4 = zzkm.zzu(zznd.zzk(var1_1, var7_7));
                            break block45;
                        }
                        case 1: {
                            var9_8 = var4_4 * 53;
                            var4_4 = Float.floatToIntBits(zznd.zzm(var1_1, var7_7));
                            break block45;
                        }
                        case 0: 
                    }
                    var9_8 = var4_4 * 53;
                    var4_4 = zzkm.zzu(Double.doubleToLongBits(zznd.zzn(var1_1, var7_7)));
                }
                var9_8 += var4_4;
            }
            var3_3 += 3;
            var4_4 = var9_8;
        } while (true);
    }

    @Override
    public final T newInstance() {
        return (T)this.zzup.newInstance(this.zzuh);
    }

    /*
     * Unable to fully structure code
     */
    final int zza(T var1_1, byte[] var2_2, int var3_3, int var4_4, int var5_5, zziz var6_6) throws IOException {
        block47 : {
            var7_10 = var1_1;
            var8_19 = var4_7;
            var9_20 = var5_8;
            var10_21 = var6_9;
            var11_22 = zzlu.zzuc;
            var12_23 = -1;
            var13_24 = 0;
            var14_25 = 0;
            var15_26 = 0;
            var16_27 = -1;
            block16 : do {
                block60 : {
                    block49 : {
                        block58 : {
                            block59 : {
                                block57 : {
                                    block56 : {
                                        block50 : {
                                            block53 : {
                                                block55 : {
                                                    block51 : {
                                                        block54 : {
                                                            block52 : {
                                                                block48 : {
                                                                    var17_28 = this;
                                                                    var18_30 /* !! */  = var2_2 /* !! */ ;
                                                                    if (var3_6 >= var8_19) break;
                                                                    var19_36 = var3_6 + true;
                                                                    if ((var3_6 = var18_30 /* !! */ [var3_6]) < 0) {
                                                                        var19_36 = zziy.zza(var3_6, var18_30 /* !! */ , var19_36, (zziz)var10_21);
                                                                        var3_6 = var10_21.zznk;
                                                                    }
                                                                    var8_19 = var3_6 >>> 3;
                                                                    var20_37 = var3_6 & 7;
                                                                    var14_25 = var8_19 > var12_23 ? var17_28.zzp(var8_19, var13_24 / 3) : var17_28.zzau(var8_19);
                                                                    if (var14_25 != -1) break block48;
                                                                    var12_23 = var8_19;
                                                                    var14_25 = var19_36;
                                                                    var13_24 = var3_6;
                                                                    var3_6 = var9_20;
                                                                    var9_20 = 0;
                                                                    var8_19 = var15_26;
                                                                    var15_26 = var13_24;
                                                                    break block49;
                                                                }
                                                                var18_31 = var17_28.zzud;
                                                                var21_38 = var18_31[var14_25 + 1];
                                                                var22_39 = (var21_38 & 267386880) >>> 20;
                                                                var23_40 = var21_38 & 1048575;
                                                                if (var22_39 > 17) break block50;
                                                                var9_20 = var18_31[var14_25 + 2];
                                                                var25_41 = 1 << (var9_20 >>> 20);
                                                                var13_24 = var9_20 & 1048575;
                                                                var12_23 = var15_26;
                                                                var9_20 = var16_27;
                                                                if (var13_24 != var16_27) {
                                                                    if (var16_27 != -1) {
                                                                        var11_22.putInt(var7_11, var16_27, var15_26);
                                                                    }
                                                                    var12_23 = var11_22.getInt(var7_11, var13_24);
                                                                    var9_20 = var13_24;
                                                                }
                                                                switch (var22_39) {
                                                                    case 17: {
                                                                        if (var20_37 == 3) {
                                                                            var13_24 = zziy.zza(zzlu.super.zzap(var14_25), var2_2 /* !! */ , var19_36, (int)var4_7, var8_19 << 3 | 4, (zziz)var6_9);
                                                                            if ((var12_23 & var25_41) == 0) {
                                                                                var11_22.putObject(var7_11, var23_40, var6_9.zznm);
                                                                            } else {
                                                                                var11_22.putObject(var7_11, var23_40, zzkm.zza(var11_22.getObject(var7_11, var23_40), var6_9.zznm));
                                                                            }
                                                                            var16_27 = var12_23 | var25_41;
                                                                            var19_36 = var4_7;
                                                                            var15_26 = var3_6;
                                                                            var12_23 = var8_19;
                                                                            var8_19 = var5_8;
                                                                            var10_21 = var6_9;
                                                                            var3_6 = var13_24;
                                                                            var13_24 = var14_25;
                                                                            var14_25 = var15_26;
                                                                            var15_26 = var16_27;
                                                                            var16_27 = var9_20;
                                                                            var9_20 = var8_19;
                                                                            var8_19 = var19_36;
                                                                            continue block16;
                                                                        }
                                                                        ** GOTO lbl147
                                                                    }
                                                                    case 16: {
                                                                        if (var20_37 != 0) ** GOTO lbl147
                                                                        var15_26 = zziy.zzb(var2_2 /* !! */ , var19_36, (zziz)var6_9);
                                                                        var11_22.putLong(var1_1, var23_40, zzjo.zzk(var6_9.zznl));
                                                                        var16_27 = var12_23 | var25_41;
                                                                        break block51;
                                                                    }
                                                                    case 15: {
                                                                        var10_21 = var6_9;
                                                                        if (var20_37 != 0) ** GOTO lbl147
                                                                        var15_26 = zziy.zza(var2_2 /* !! */ , var19_36, (zziz)var10_21);
                                                                        var11_22.putInt(var7_11, var23_40, zzjo.zzw(var10_21.zznk));
                                                                        ** GOTO lbl99
                                                                    }
                                                                    case 12: {
                                                                        var10_21 = var6_9;
                                                                        if (var20_37 != 0) ** GOTO lbl147
                                                                        var15_26 = zziy.zza(var2_2 /* !! */ , var19_36, (zziz)var10_21);
                                                                        var16_27 = var10_21.zznk;
                                                                        var10_21 = zzlu.super.zzar(var14_25);
                                                                        if (var10_21 == null || var10_21.zzan(var16_27)) ** GOTO lbl92
                                                                        zzlu.zzo(var1_1).zzb(var3_6, var16_27);
                                                                        var16_27 = var12_23;
                                                                        break block51;
lbl92: // 1 sources:
                                                                        var11_22.putInt(var7_11, var23_40, var16_27);
                                                                        ** GOTO lbl99
                                                                    }
                                                                    case 10: {
                                                                        var10_21 = var6_9;
                                                                        if (var20_37 != 2) ** GOTO lbl147
                                                                        var15_26 = zziy.zze(var2_2 /* !! */ , var19_36, (zziz)var10_21);
                                                                        var11_22.putObject(var7_11, var23_40, var10_21.zznm);
lbl99: // 3 sources:
                                                                        var16_27 = var12_23 | var25_41;
                                                                        break block51;
                                                                    }
                                                                    case 9: {
                                                                        var10_21 = var6_9;
                                                                        if (var20_37 != 2) ** GOTO lbl147
                                                                        var15_26 = zziy.zza(zzlu.super.zzap(var14_25), var2_2 /* !! */ , var19_36, (int)var4_7, (zziz)var10_21);
                                                                        if ((var12_23 & var25_41) == 0) {
                                                                            var11_22.putObject(var7_11, var23_40, var10_21.zznm);
                                                                        } else {
                                                                            var11_22.putObject(var7_11, var23_40, zzkm.zza(var11_22.getObject(var7_11, var23_40), var10_21.zznm));
                                                                        }
                                                                        ** GOTO lbl129
                                                                    }
                                                                    case 8: {
                                                                        var10_21 = var2_2 /* !! */ ;
                                                                        var17_28 = var6_9;
                                                                        if (var20_37 != 2) ** GOTO lbl147
                                                                        var15_26 = (var21_38 & 536870912) == 0 ? zziy.zzc((byte[])var10_21, var19_36, (zziz)var17_28) : zziy.zzd((byte[])var10_21, var19_36, (zziz)var17_28);
                                                                        var11_22.putObject(var7_11, var23_40, var17_28.zznm);
                                                                        ** GOTO lbl129
                                                                    }
                                                                    case 7: {
                                                                        var10_21 = var6_9;
                                                                        if (var20_37 != 0) ** GOTO lbl147
                                                                        var15_26 = zziy.zzb(var2_2 /* !! */ , var19_36, (zziz)var10_21);
                                                                        var26_42 = var10_21.zznl != 0L;
                                                                        zznd.zza((Object)var7_11, var23_40, var26_42);
                                                                        var16_27 = var12_23 | var25_41;
                                                                        ** GOTO lbl130
                                                                    }
                                                                    case 6: 
                                                                    case 13: {
                                                                        if (var20_37 != 5) ** GOTO lbl147
                                                                        var11_22.putInt(var7_11, var23_40, zziy.zza(var2_2 /* !! */ , var19_36));
                                                                        var15_26 = var19_36 + 4;
lbl129: // 4 sources:
                                                                        var16_27 = var12_23 | var25_41;
lbl130: // 2 sources:
                                                                        var25_41 = var3_6;
                                                                        var12_23 = var8_19;
                                                                        var10_21 = var6_9;
                                                                        var19_36 = var5_8;
                                                                        var8_19 = var4_7;
                                                                        var3_6 = var15_26;
                                                                        var13_24 = var14_25;
                                                                        var14_25 = var25_41;
                                                                        var15_26 = var16_27;
                                                                        var16_27 = var9_20;
                                                                        var9_20 = var19_36;
                                                                        continue block16;
                                                                    }
                                                                    case 5: 
                                                                    case 14: {
                                                                        if (var20_37 != 1) ** GOTO lbl147
                                                                        var11_22.putLong(var1_1, var23_40, zziy.zzb(var2_2 /* !! */ , var19_36));
                                                                        break block52;
                                                                    }
lbl147: // 11 sources:
                                                                    default: {
                                                                        break block53;
                                                                    }
                                                                    case 4: 
                                                                    case 11: {
                                                                        var10_21 = var6_9;
                                                                        if (var20_37 != 0) break block53;
                                                                        var15_26 = zziy.zza(var2_2 /* !! */ , var19_36, (zziz)var10_21);
                                                                        var11_22.putInt(var7_11, var23_40, var10_21.zznk);
                                                                        break block54;
                                                                    }
                                                                    case 2: 
                                                                    case 3: {
                                                                        if (var20_37 != 0) break block53;
                                                                        var16_27 = zziy.zzb(var2_2 /* !! */ , var19_36, (zziz)var6_9);
                                                                        var11_22.putLong(var1_1, var23_40, var6_9.zznl);
                                                                        var19_36 = var12_23 | var25_41;
                                                                        var15_26 = var3_6;
                                                                        var12_23 = var8_19;
                                                                        var10_21 = var6_9;
                                                                        var8_19 = var9_20;
                                                                        var3_6 = var16_27;
                                                                        var13_24 = var14_25;
                                                                        var14_25 = var15_26;
                                                                        var16_27 = var19_36;
                                                                        break block55;
                                                                    }
                                                                    case 1: {
                                                                        var15_26 = var19_36;
                                                                        if (var20_37 != 5) break block53;
                                                                        zznd.zza((Object)var7_11, var23_40, zziy.zzd(var2_2 /* !! */ , var15_26));
                                                                        var15_26 += 4;
                                                                        break block54;
                                                                    }
                                                                    case 0: 
                                                                }
                                                                if (var20_37 != 1) break block53;
                                                                zznd.zza((Object)var7_11, var23_40, zziy.zzc(var2_2 /* !! */ , var19_36));
                                                            }
                                                            var15_26 = var19_36 + 8;
                                                        }
                                                        var16_27 = var12_23 | var25_41;
                                                    }
                                                    var13_24 = var14_25;
                                                    var12_23 = var8_19;
                                                    var10_21 = var6_9;
                                                    var8_19 = var9_20;
                                                    var14_25 = var3_6;
                                                    var3_6 = var15_26;
                                                }
                                                var19_36 = var4_7;
                                                var9_20 = var5_8;
                                                var15_26 = var16_27;
                                                var16_27 = var8_19;
                                                var8_19 = var19_36;
                                                continue;
                                            }
                                            var16_27 = var19_36;
                                            var19_36 = var14_25;
                                            var13_24 = var8_19;
                                            var15_26 = var3_6;
                                            var3_6 = var5_8;
                                            var14_25 = var16_27;
                                            var8_19 = var12_23;
                                            var16_27 = var9_20;
                                            var9_20 = var19_36;
                                            var12_23 = var13_24;
                                            break block49;
                                        }
                                        if (var22_39 != 27) break block56;
                                        if (var20_37 == 2) {
                                            var18_32 = var27_43 = (zzkp)var11_22.getObject(var7_11, var23_40);
                                            if (!var27_43.zzbo()) {
                                                var9_20 = var27_43.size();
                                                var9_20 = var9_20 == 0 ? 10 : (var9_20 <<= 1);
                                                var18_33 = var27_43.zzr(var9_20);
                                                var11_22.putObject(var7_11, var23_40, var18_33);
                                            }
                                            var13_24 = zziy.zza(zzlu.super.zzap(var14_25), var3_6, var2_2 /* !! */ , var19_36, (int)var4_7, var18_34, (zziz)var6_9);
                                            var9_20 = var5_8;
                                            var12_23 = var8_19;
                                            var19_36 = var3_6;
                                            var8_19 = var4_7;
                                            var3_6 = var13_24;
                                            var13_24 = var14_25;
                                            var14_25 = var19_36;
                                            continue;
                                        }
                                        ** GOTO lbl260
                                    }
                                    var9_20 = var15_26;
                                    if (var22_39 > 49) break block57;
                                    var12_23 = var13_24 = this.zza(var1_1, var2_2 /* !! */ , var19_36, (int)var4_7, var3_6, var8_19, var20_37, var14_25, (long)var21_38, var22_39, var23_40, (zziz)var6_9);
                                    if (var13_24 != var19_36) ** GOTO lbl-1000
                                    var9_20 = var13_24;
                                    break block58;
                                }
                                var25_41 = var8_19;
                                var28_44 = var19_36;
                                var29_45 = var3_6;
                                var12_23 = var14_25;
                                if (var22_39 != 50) break block59;
                                if (var20_37 == 2) {
                                    var12_23 = var13_24 = this.zza(var1_1, var2_2 /* !! */ , var28_44, (int)var4_7, var12_23, var23_40, (zziz)var6_9);
                                    ** if (var13_24 != var28_44) goto lbl-1000
lbl-1000: // 1 sources:
                                    {
                                        var9_20 = var13_24;
                                        ** GOTO lbl266
                                    }
                                }
                                ** GOTO lbl260
lbl-1000: // 2 sources:
                                {
                                    var7_12 = var1_1;
                                    var19_36 = var4_7;
                                    var25_41 = var5_8;
                                    var10_21 = var6_9;
                                    var15_26 = var9_20;
                                    var9_20 = var8_19;
                                    var8_19 = var3_6;
                                    var3_6 = var12_23;
                                    var12_23 = var9_20;
                                    var13_24 = var14_25;
                                    var14_25 = var8_19;
                                    var9_20 = var25_41;
                                    var8_19 = var19_36;
                                    continue;
lbl260: // 2 sources:
                                    var9_20 = var19_36;
                                }
                                break block58;
                            }
                            var13_24 = this.zza(var1_1, var2_2 /* !! */ , var28_44, (int)var4_7, var29_45, var25_41, var20_37, var21_38, var22_39, var23_40, var12_23, (zziz)var6_9);
                            if (var13_24 != var28_44) break block60;
                            var9_20 = var13_24;
                        }
                        var12_23 = var8_19;
                        var13_24 = var14_25;
                        var19_36 = var5_8;
                        var8_19 = var15_26;
                        var15_26 = var3_6;
                        var14_25 = var9_20;
                        var3_6 = var19_36;
                        var9_20 = var13_24;
                    }
                    var17_28 = var11_22;
                    if (var15_26 == var3_6 && var3_6 != 0) {
                        var6_9 = this;
                        var5_8 = var14_25;
                        var14_25 = var3_6;
                        var3_6 = var15_26;
                        var2_3 = var1_1;
                        break block47;
                    }
                    if (!this.zzui) ** GOTO lbl-1000
                    var10_21 = var6_9;
                    if (var10_21.zznn != zzjx.zzci()) {
                        var11_22 = this.zzuh;
                        if (var10_21.zznn.zza(var11_22, var12_23) != null) {
                            var1_1 = (zzkk.zzc)var1_1;
                            var1_1.zzdg();
                            var1_1 = var1_1.zzrw;
                            throw new NoSuchMethodError();
                        }
                        var14_25 = zziy.zza(var15_26, var2_2 /* !! */ , var14_25, (int)var4_7, zzlu.zzo(var1_1), (zziz)var6_9);
                        var7_13 = var1_1;
                    } else lbl-1000: // 2 sources:
                    {
                        var14_25 = zziy.zza(var15_26, var2_2 /* !! */ , var14_25, (int)var4_7, zzlu.zzo(var1_1), (zziz)var6_9);
                        var10_21 = var6_9;
                        var7_14 = var1_1;
                    }
                    var19_36 = var4_7;
                    var11_22 = var17_28;
                    var13_24 = var9_20;
                    var9_20 = var3_6;
                    var3_6 = var14_25;
                    var14_25 = var15_26;
                    var15_26 = var8_19;
                    var8_19 = var19_36;
                    continue;
                }
                var7_16 = var1_1;
                var8_19 = var4_7;
                var19_36 = var5_8;
                var14_25 = var29_45;
                var29_45 = var16_27;
                var16_27 = var12_23;
                var15_26 = var9_20;
                var10_21 = var6_9;
                var3_6 = var13_24;
                var12_23 = var25_41;
                var13_24 = var16_27;
                var16_27 = var29_45;
                var9_20 = var19_36;
            } while (true);
            var2_4 = var7_11;
            var6_9 = var17_28;
            var5_8 = var3_6;
            var3_6 = var14_25;
            var14_25 = var9_20;
            var8_19 = var15_26;
        }
        if (var16_27 != -1) {
            var11_22.putInt(var2_5, var16_27, var8_19);
        }
        var1_1 = null;
        for (var15_26 = var6_9.zzun; var15_26 < var6_9.zzuo; ++var15_26) {
            var8_19 = var6_9.zzum[var15_26];
            var10_21 = var6_9.zzur;
            var9_20 = var6_9.zzud[var8_19];
            var11_22 = zznd.zzo(var2_5, var6_9.zzas(var8_19) & 1048575);
            if (var11_22 != null && (var7_18 = var6_9.zzar(var8_19)) != null) {
                var1_1 = this.zza(var8_19, var9_20, var6_9.zzut.zzh(var11_22), var7_18, (UB)var1_1, (zzmx<UT, UB>)var10_21);
            }
            var1_1 = (zzmy)var1_1;
        }
        if (var1_1 != null) {
            var6_9.zzur.zzf(var2_5, var1_1);
        }
        if (var14_25 == 0) {
            if (var5_8 != var4_7) throw zzkq.zzdm();
            return var5_8;
        }
        if (var5_8 > var4_7) throw zzkq.zzdm();
        if (var3_6 != var14_25) throw zzkq.zzdm();
        return var5_8;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public final void zza(T var1_1, zzns var2_2) throws IOException {
        block159 : {
            block158 : {
                if (var2_2.zzcd() == zzkk.zze.zzsj) break block158;
                if (!this.zzuk) {
                    this.zzb(var1_1, var2_2);
                    return;
                }
                if (!this.zzui) ** GOTO lbl-1000
                var3_4 = this.zzus.zzb(var1_1);
                if (!var3_4.zzos.isEmpty()) {
                    var4_6 = var3_4.iterator();
                    var3_4 = (Map.Entry)var4_6.next();
                } else lbl-1000: // 2 sources:
                {
                    var3_4 = var4_6 = null;
                }
                break block159;
            }
            zzlu.zza(this.zzur, var1_1, var2_2);
            if (!this.zzui) ** GOTO lbl-1000
            var3_3 = this.zzus.zzb(var1_1);
            if (!var3_3.zzos.isEmpty()) {
                var4_5 = var3_3.descendingIterator();
                var5_7 = var4_5.next();
            } else lbl-1000: // 2 sources:
            {
                var4_5 = null;
                var5_7 = var4_5;
            }
            var6_9 = this.zzud.length - 3;
            do {
                var3_3 = var5_7;
                if (var6_9 < 0) break;
                var7_11 = this.zzas(var6_9);
                var8_13 = this.zzud[var6_9];
                while (var5_7 != null && this.zzus.zza(var5_7) > var8_13) {
                    this.zzus.zza(var2_2, var5_7);
                    if (var4_5.hasNext()) {
                        var5_7 = var4_5.next();
                        continue;
                    }
                    var5_7 = null;
                }
                switch ((var7_11 & 267386880) >>> 20) {
                    default: {
                        break;
                    }
                    case 68: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zzb(var8_13, zznd.zzo(var1_1, var7_11 & 1048575), this.zzap(var6_9));
                        break;
                    }
                    case 67: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zzb(var8_13, zzlu.zzh(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 66: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zze(var8_13, zzlu.zzg(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 65: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zzj(var8_13, zzlu.zzh(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 64: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zzm(var8_13, zzlu.zzg(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 63: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zzn(var8_13, zzlu.zzg(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 62: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zzd(var8_13, zzlu.zzg(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 61: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zza(var8_13, (zzjc)zznd.zzo(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 60: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zza(var8_13, zznd.zzo(var1_1, var7_11 & 1048575), this.zzap(var6_9));
                        break;
                    }
                    case 59: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        zzlu.zza(var8_13, zznd.zzo(var1_1, var7_11 & 1048575), var2_2);
                        break;
                    }
                    case 58: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zzb(var8_13, zzlu.zzi(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 57: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zzf(var8_13, zzlu.zzg(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 56: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zzc(var8_13, zzlu.zzh(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 55: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zzc(var8_13, zzlu.zzg(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 54: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zza(var8_13, zzlu.zzh(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 53: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zzi(var8_13, zzlu.zzh(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 52: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zza(var8_13, zzlu.zzf(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 51: {
                        if (!this.zza(var1_1, var8_13, var6_9)) break;
                        var2_2.zza(var8_13, zzlu.zze(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 50: {
                        this.zza(var2_2, var8_13, zznd.zzo(var1_1, var7_11 & 1048575), var6_9);
                        break;
                    }
                    case 49: {
                        zzmh.zzb(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, this.zzap(var6_9));
                        break;
                    }
                    case 48: {
                        zzmh.zze(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 47: {
                        zzmh.zzj(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 46: {
                        zzmh.zzg(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 45: {
                        zzmh.zzl(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 44: {
                        zzmh.zzm(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 43: {
                        zzmh.zzi(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 42: {
                        zzmh.zzn(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 41: {
                        zzmh.zzk(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 40: {
                        zzmh.zzf(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 39: {
                        zzmh.zzh(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 38: {
                        zzmh.zzd(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 37: {
                        zzmh.zzc(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 36: {
                        zzmh.zzb(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 35: {
                        zzmh.zza(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, true);
                        break;
                    }
                    case 34: {
                        zzmh.zze(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 33: {
                        zzmh.zzj(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 32: {
                        zzmh.zzg(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 31: {
                        zzmh.zzl(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 30: {
                        zzmh.zzm(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 29: {
                        zzmh.zzi(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 28: {
                        zzmh.zzb(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2);
                        break;
                    }
                    case 27: {
                        zzmh.zza(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, this.zzap(var6_9));
                        break;
                    }
                    case 26: {
                        zzmh.zza(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2);
                        break;
                    }
                    case 25: {
                        zzmh.zzn(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 24: {
                        zzmh.zzk(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 23: {
                        zzmh.zzf(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 22: {
                        zzmh.zzh(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 21: {
                        zzmh.zzd(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 20: {
                        zzmh.zzc(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 19: {
                        zzmh.zzb(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 18: {
                        zzmh.zza(this.zzud[var6_9], (List)zznd.zzo(var1_1, var7_11 & 1048575), var2_2, false);
                        break;
                    }
                    case 17: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zzb(var8_13, zznd.zzo(var1_1, var7_11 & 1048575), this.zzap(var6_9));
                        break;
                    }
                    case 16: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zzb(var8_13, zznd.zzk(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 15: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zze(var8_13, zznd.zzj(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 14: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zzj(var8_13, zznd.zzk(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 13: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zzm(var8_13, zznd.zzj(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 12: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zzn(var8_13, zznd.zzj(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 11: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zzd(var8_13, zznd.zzj(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 10: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zza(var8_13, (zzjc)zznd.zzo(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 9: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zza(var8_13, zznd.zzo(var1_1, var7_11 & 1048575), this.zzap(var6_9));
                        break;
                    }
                    case 8: {
                        if (!this.zza(var1_1, var6_9)) break;
                        zzlu.zza(var8_13, zznd.zzo(var1_1, var7_11 & 1048575), var2_2);
                        break;
                    }
                    case 7: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zzb(var8_13, zznd.zzl(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 6: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zzf(var8_13, zznd.zzj(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 5: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zzc(var8_13, zznd.zzk(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 4: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zzc(var8_13, zznd.zzj(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 3: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zza(var8_13, zznd.zzk(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 2: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zzi(var8_13, zznd.zzk(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 1: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zza(var8_13, zznd.zzm(var1_1, var7_11 & 1048575));
                        break;
                    }
                    case 0: {
                        if (!this.zza(var1_1, var6_9)) break;
                        var2_2.zza(var8_13, zznd.zzn(var1_1, var7_11 & 1048575));
                    }
                }
                var6_9 -= 3;
            } while (true);
            while (var3_3 != null) {
                this.zzus.zza(var2_2, var3_3);
                if (var4_5.hasNext()) {
                    var3_3 = var4_5.next();
                    continue;
                }
                var3_3 = null;
            }
            return;
        }
        var7_12 = this.zzud.length;
        var6_10 = 0;
        do {
            var5_8 = var3_4;
            if (var6_10 >= var7_12) break;
            var9_15 = this.zzas(var6_10);
            var8_14 = this.zzud[var6_10];
            while (var3_4 != null && this.zzus.zza((Map.Entry<?, ?>)var3_4) <= var8_14) {
                this.zzus.zza(var2_2, (Map.Entry<?, ?>)var3_4);
                if (var4_6.hasNext()) {
                    var3_4 = (Map.Entry)var4_6.next();
                    continue;
                }
                var3_4 = null;
            }
            switch ((var9_15 & 267386880) >>> 20) {
                default: {
                    break;
                }
                case 68: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zzb(var8_14, zznd.zzo(var1_1, var9_15 & 1048575), this.zzap(var6_10));
                    break;
                }
                case 67: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zzb(var8_14, zzlu.zzh(var1_1, var9_15 & 1048575));
                    break;
                }
                case 66: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zze(var8_14, zzlu.zzg(var1_1, var9_15 & 1048575));
                    break;
                }
                case 65: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zzj(var8_14, zzlu.zzh(var1_1, var9_15 & 1048575));
                    break;
                }
                case 64: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zzm(var8_14, zzlu.zzg(var1_1, var9_15 & 1048575));
                    break;
                }
                case 63: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zzn(var8_14, zzlu.zzg(var1_1, var9_15 & 1048575));
                    break;
                }
                case 62: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zzd(var8_14, zzlu.zzg(var1_1, var9_15 & 1048575));
                    break;
                }
                case 61: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zza(var8_14, (zzjc)zznd.zzo(var1_1, var9_15 & 1048575));
                    break;
                }
                case 60: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zza(var8_14, zznd.zzo(var1_1, var9_15 & 1048575), this.zzap(var6_10));
                    break;
                }
                case 59: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    zzlu.zza(var8_14, zznd.zzo(var1_1, var9_15 & 1048575), var2_2);
                    break;
                }
                case 58: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zzb(var8_14, zzlu.zzi(var1_1, var9_15 & 1048575));
                    break;
                }
                case 57: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zzf(var8_14, zzlu.zzg(var1_1, var9_15 & 1048575));
                    break;
                }
                case 56: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zzc(var8_14, zzlu.zzh(var1_1, var9_15 & 1048575));
                    break;
                }
                case 55: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zzc(var8_14, zzlu.zzg(var1_1, var9_15 & 1048575));
                    break;
                }
                case 54: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zza(var8_14, zzlu.zzh(var1_1, var9_15 & 1048575));
                    break;
                }
                case 53: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zzi(var8_14, zzlu.zzh(var1_1, var9_15 & 1048575));
                    break;
                }
                case 52: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zza(var8_14, zzlu.zzf(var1_1, var9_15 & 1048575));
                    break;
                }
                case 51: {
                    if (!this.zza(var1_1, var8_14, var6_10)) break;
                    var2_2.zza(var8_14, zzlu.zze(var1_1, var9_15 & 1048575));
                    break;
                }
                case 50: {
                    this.zza(var2_2, var8_14, zznd.zzo(var1_1, var9_15 & 1048575), var6_10);
                    break;
                }
                case 49: {
                    zzmh.zzb(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, this.zzap(var6_10));
                    break;
                }
                case 48: {
                    zzmh.zze(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 47: {
                    zzmh.zzj(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 46: {
                    zzmh.zzg(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 45: {
                    zzmh.zzl(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 44: {
                    zzmh.zzm(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 43: {
                    zzmh.zzi(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 42: {
                    zzmh.zzn(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 41: {
                    zzmh.zzk(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 40: {
                    zzmh.zzf(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 39: {
                    zzmh.zzh(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 38: {
                    zzmh.zzd(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 37: {
                    zzmh.zzc(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 36: {
                    zzmh.zzb(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 35: {
                    zzmh.zza(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, true);
                    break;
                }
                case 34: {
                    zzmh.zze(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 33: {
                    zzmh.zzj(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 32: {
                    zzmh.zzg(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 31: {
                    zzmh.zzl(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 30: {
                    zzmh.zzm(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 29: {
                    zzmh.zzi(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 28: {
                    zzmh.zzb(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2);
                    break;
                }
                case 27: {
                    zzmh.zza(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, this.zzap(var6_10));
                    break;
                }
                case 26: {
                    zzmh.zza(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2);
                    break;
                }
                case 25: {
                    zzmh.zzn(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 24: {
                    zzmh.zzk(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 23: {
                    zzmh.zzf(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 22: {
                    zzmh.zzh(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 21: {
                    zzmh.zzd(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 20: {
                    zzmh.zzc(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 19: {
                    zzmh.zzb(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 18: {
                    zzmh.zza(this.zzud[var6_10], (List)zznd.zzo(var1_1, var9_15 & 1048575), var2_2, false);
                    break;
                }
                case 17: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zzb(var8_14, zznd.zzo(var1_1, var9_15 & 1048575), this.zzap(var6_10));
                    break;
                }
                case 16: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zzb(var8_14, zznd.zzk(var1_1, var9_15 & 1048575));
                    break;
                }
                case 15: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zze(var8_14, zznd.zzj(var1_1, var9_15 & 1048575));
                    break;
                }
                case 14: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zzj(var8_14, zznd.zzk(var1_1, var9_15 & 1048575));
                    break;
                }
                case 13: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zzm(var8_14, zznd.zzj(var1_1, var9_15 & 1048575));
                    break;
                }
                case 12: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zzn(var8_14, zznd.zzj(var1_1, var9_15 & 1048575));
                    break;
                }
                case 11: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zzd(var8_14, zznd.zzj(var1_1, var9_15 & 1048575));
                    break;
                }
                case 10: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zza(var8_14, (zzjc)zznd.zzo(var1_1, var9_15 & 1048575));
                    break;
                }
                case 9: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zza(var8_14, zznd.zzo(var1_1, var9_15 & 1048575), this.zzap(var6_10));
                    break;
                }
                case 8: {
                    if (!this.zza(var1_1, var6_10)) break;
                    zzlu.zza(var8_14, zznd.zzo(var1_1, var9_15 & 1048575), var2_2);
                    break;
                }
                case 7: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zzb(var8_14, zznd.zzl(var1_1, var9_15 & 1048575));
                    break;
                }
                case 6: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zzf(var8_14, zznd.zzj(var1_1, var9_15 & 1048575));
                    break;
                }
                case 5: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zzc(var8_14, zznd.zzk(var1_1, var9_15 & 1048575));
                    break;
                }
                case 4: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zzc(var8_14, zznd.zzj(var1_1, var9_15 & 1048575));
                    break;
                }
                case 3: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zza(var8_14, zznd.zzk(var1_1, var9_15 & 1048575));
                    break;
                }
                case 2: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zzi(var8_14, zznd.zzk(var1_1, var9_15 & 1048575));
                    break;
                }
                case 1: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zza(var8_14, zznd.zzm(var1_1, var9_15 & 1048575));
                    break;
                }
                case 0: {
                    if (!this.zza(var1_1, var6_10)) break;
                    var2_2.zza(var8_14, zznd.zzn(var1_1, var9_15 & 1048575));
                }
            }
            var6_10 += 3;
        } while (true);
        do {
            if (var5_8 == null) {
                zzlu.zza(this.zzur, var1_1, var2_2);
                return;
            }
            this.zzus.zza(var2_2, (Map.Entry<?, ?>)var5_8);
            if (var4_6.hasNext()) {
                var5_8 = (Map.Entry)var4_6.next();
                continue;
            }
            var5_8 = null;
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public final void zza(T var1_1, byte[] var2_2, int var3_3, int var4_4, zziz var5_5) throws IOException {
        var6_6 = this;
        var7_7 = var1_1;
        var8_8 = var2_2;
        var9_9 = var4_4;
        var10_10 = var5_5;
        if (!var6_6.zzuk) {
            this.zza(var1_1, var2_2, var3_3, var4_4, 0, var5_5);
            return;
        }
        var11_11 = zzlu.zzuc;
        var12_12 = var3_3;
        var13_13 = -1;
        var3_3 = 0;
        do {
            block31 : {
                block35 : {
                    block27 : {
                        block36 : {
                            block37 : {
                                block29 : {
                                    block34 : {
                                        block33 : {
                                            block28 : {
                                                block30 : {
                                                    block32 : {
                                                        block26 : {
                                                            if (var12_12 >= var9_9) {
                                                                if (var12_12 != var9_9) throw zzkq.zzdm();
                                                                return;
                                                            }
                                                            var14_14 = var12_12 + 1;
                                                            if ((var12_12 = var8_8[var12_12]) < 0) {
                                                                var14_14 = zziy.zza(var12_12, var8_8, var14_14, var10_10);
                                                                var12_12 = var10_10.zznk;
                                                            }
                                                            var15_15 = var12_12 >>> 3;
                                                            var16_16 = var12_12 & 7;
                                                            var3_3 = var15_15 > var13_13 ? var6_6.zzp(var15_15, var3_3 / 3) : var6_6.zzau(var15_15);
                                                            if (var3_3 != -1) break block26;
                                                            var9_9 = 0;
                                                            break block27;
                                                        }
                                                        var13_13 = var6_6.zzud[var3_3 + 1];
                                                        var17_17 = (267386880 & var13_13) >>> 20;
                                                        var18_18 = 1048575 & var13_13;
                                                        if (var17_17 > 17) break block28;
                                                        var20_19 = true;
                                                        switch (var17_17) {
                                                            default: {
                                                                break block29;
                                                            }
                                                            case 16: {
                                                                if (var16_16 != 0) break block29;
                                                                var14_14 = zziy.zzb(var8_8, var14_14, var10_10);
                                                                var11_11.putLong(var1_1, var18_18, zzjo.zzk(var10_10.zznl));
                                                                break block30;
                                                            }
                                                            case 15: {
                                                                if (var16_16 != 0) break block29;
                                                                var14_14 = zziy.zza(var8_8, var14_14, var10_10);
                                                                var11_11.putInt(var7_7, var18_18, zzjo.zzw(var10_10.zznk));
                                                                break block30;
                                                            }
                                                            case 12: {
                                                                if (var16_16 != 0) break block29;
                                                                var14_14 = zziy.zza(var8_8, var14_14, var10_10);
                                                                var11_11.putInt(var7_7, var18_18, var10_10.zznk);
                                                                break block30;
                                                            }
                                                            case 10: {
                                                                if (var16_16 != 2) break block29;
                                                                var14_14 = zziy.zze(var8_8, var14_14, var10_10);
                                                                var11_11.putObject(var7_7, var18_18, var10_10.zznm);
                                                                ** GOTO lbl82
                                                            }
                                                            case 9: {
                                                                if (var16_16 != 2) break block29;
                                                                var14_14 = zziy.zza(var6_6.zzap(var3_3), var8_8, var14_14, var9_9, var10_10);
                                                                var21_20 = var11_11.getObject(var7_7, var18_18);
                                                                if (var21_20 == null) {
                                                                    var11_11.putObject(var7_7, var18_18, var10_10.zznm);
                                                                } else {
                                                                    var11_11.putObject(var7_7, var18_18, zzkm.zza(var21_20, var10_10.zznm));
                                                                }
                                                                ** GOTO lbl82
                                                            }
                                                            case 8: {
                                                                if (var16_16 != 2) break block29;
                                                                var14_14 = (536870912 & var13_13) == 0 ? zziy.zzc(var8_8, var14_14, var10_10) : zziy.zzd(var8_8, var14_14, var10_10);
                                                                var11_11.putObject(var7_7, var18_18, var10_10.zznm);
                                                                ** GOTO lbl82
                                                            }
                                                            case 7: {
                                                                if (var16_16 != 0) break block29;
                                                                var14_14 = zziy.zzb(var8_8, var14_14, var10_10);
                                                                if (var10_10.zznl == 0L) {
                                                                    var20_19 = false;
                                                                }
                                                                zznd.zza(var7_7, var18_18, var20_19);
                                                                ** GOTO lbl82
                                                            }
                                                            case 6: 
                                                            case 13: {
                                                                if (var16_16 != 5) break block29;
                                                                var11_11.putInt(var7_7, var18_18, zziy.zza(var8_8, var14_14));
                                                                var14_14 += 4;
lbl82: // 6 sources:
                                                                var13_13 = var3_3;
                                                                var3_3 = var14_14;
                                                                var14_14 = var15_15;
                                                                var15_15 = var13_13;
                                                                break block31;
                                                            }
                                                            case 5: 
                                                            case 14: {
                                                                if (var16_16 != 1) break block29;
                                                                var11_11.putLong(var1_1, var18_18, zziy.zzb(var8_8, var14_14));
                                                                break block32;
                                                            }
                                                            case 4: 
                                                            case 11: {
                                                                if (var16_16 != 0) break block29;
                                                                var14_14 = zziy.zza(var8_8, var14_14, var10_10);
                                                                var11_11.putInt(var7_7, var18_18, var10_10.zznk);
                                                                break block30;
                                                            }
                                                            case 2: 
                                                            case 3: {
                                                                if (var16_16 != 0) break block29;
                                                                var14_14 = zziy.zzb(var8_8, var14_14, var10_10);
                                                                var11_11.putLong(var1_1, var18_18, var10_10.zznl);
                                                                break block30;
                                                            }
                                                            case 1: {
                                                                if (var16_16 != 5) break block29;
                                                                zznd.zza(var7_7, var18_18, zziy.zzd(var8_8, var14_14));
                                                                var14_14 += 4;
                                                                break block30;
                                                            }
                                                            case 0: 
                                                        }
                                                        if (var16_16 != 1) break block29;
                                                        zznd.zza(var7_7, var18_18, zziy.zzc(var8_8, var14_14));
                                                    }
                                                    var14_14 += 8;
                                                }
                                                var13_13 = var3_3;
                                                var3_3 = var14_14;
                                                var14_14 = var15_15;
                                                var15_15 = var13_13;
                                                break block31;
                                            }
                                            if (var17_17 != 27) break block33;
                                            if (var16_16 != 2) break block29;
                                            var22_21 = (zzkp)var11_11.getObject(var7_7, var18_18);
                                            var21_20 = var22_21;
                                            if (!var22_21.zzbo()) {
                                                var13_13 = var22_21.size();
                                                var13_13 = var13_13 == 0 ? 10 : (var13_13 <<= 1);
                                                var21_20 = var22_21.zzr(var13_13);
                                                var11_11.putObject(var7_7, var18_18, var21_20);
                                            }
                                            var13_13 = zziy.zza(var6_6.zzap(var3_3), var12_12, var2_2, var14_14, var4_4, var21_20, var5_5);
                                            var14_14 = var15_15;
                                            var15_15 = var3_3;
                                            var3_3 = var13_13;
                                            break block31;
                                        }
                                        var9_9 = var3_3;
                                        if (var17_17 > 49) break block34;
                                        var3_3 = var23_22 = this.zza(var1_1, var2_2, var14_14, var4_4, var12_12, var15_15, var16_16, var9_9, (long)var13_13, var17_17, var18_18, var5_5);
                                        var13_13 = var9_9;
                                        if (var23_22 != var14_14) break block35;
                                        var3_3 = var23_22;
                                        break block36;
                                    }
                                    var23_22 = var14_14;
                                    if (var17_17 != 50) break block37;
                                    var3_3 = var9_9;
                                    if (var16_16 != 2) break block29;
                                    var3_3 = var14_14 = this.zza(var1_1, var2_2, var23_22, var4_4, var9_9, var18_18, var5_5);
                                    var13_13 = var9_9;
                                    if (var14_14 != var23_22) break block35;
                                    var3_3 = var14_14;
                                    break block36;
                                }
                                var9_9 = var3_3;
                                break block27;
                            }
                            var3_3 = var14_14 = this.zza(var1_1, var2_2, var23_22, var4_4, var12_12, var15_15, var16_16, var13_13, var17_17, var18_18, var9_9, var5_5);
                            var13_13 = var9_9;
                            if (var14_14 != var23_22) break block35;
                            var3_3 = var14_14;
                        }
                        var14_14 = var3_3;
                    }
                    var3_3 = zziy.zza(var12_12, var2_2, var14_14, var4_4, zzlu.zzo(var1_1), var5_5);
                    var13_13 = var9_9;
                }
                var6_6 = this;
                var7_7 = var1_1;
                var8_8 = var2_2;
                var9_9 = var4_4;
                var10_10 = var5_5;
                var14_14 = var15_15;
                var15_15 = var13_13;
            }
            var12_12 = var3_3;
            var13_13 = var14_14;
            var3_3 = var15_15;
        } while (true);
    }

    @Override
    public final void zzc(T t, T t2) {
        if (t2 == null) throw null;
        int n = 0;
        do {
            if (n >= this.zzud.length) {
                if (this.zzuk) return;
                zzmh.zza(this.zzur, t, t2);
                if (!this.zzui) return;
                zzmh.zza(this.zzus, t, t2);
                return;
            }
            int n2 = this.zzas(n);
            long l = 1048575 & n2;
            int n3 = this.zzud[n];
            switch ((n2 & 267386880) >>> 20) {
                default: {
                    break;
                }
                case 68: {
                    this.zzb(t, t2, n);
                    break;
                }
                case 61: 
                case 62: 
                case 63: 
                case 64: 
                case 65: 
                case 66: 
                case 67: {
                    if (!this.zza(t2, n3, n)) break;
                    zznd.zza(t, l, zznd.zzo(t2, l));
                    this.zzb(t, (T)n3, n);
                    break;
                }
                case 60: {
                    this.zzb(t, t2, n);
                    break;
                }
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: 
                case 58: 
                case 59: {
                    if (!this.zza(t2, n3, n)) break;
                    zznd.zza(t, l, zznd.zzo(t2, l));
                    this.zzb(t, (T)n3, n);
                    break;
                }
                case 50: {
                    zzmh.zza(this.zzut, t, t2, l);
                    break;
                }
                case 18: 
                case 19: 
                case 20: 
                case 21: 
                case 22: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 32: 
                case 33: 
                case 34: 
                case 35: 
                case 36: 
                case 37: 
                case 38: 
                case 39: 
                case 40: 
                case 41: 
                case 42: 
                case 43: 
                case 44: 
                case 45: 
                case 46: 
                case 47: 
                case 48: 
                case 49: {
                    this.zzuq.zza(t, t2, l);
                    break;
                }
                case 17: {
                    this.zza(t, t2, n);
                    break;
                }
                case 16: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzk(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 15: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzj(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 14: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzk(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 13: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzj(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 12: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzj(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 11: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzj(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 10: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzo(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 9: {
                    this.zza(t, t2, n);
                    break;
                }
                case 8: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzo(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 7: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzl(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 6: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzj(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 5: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzk(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 4: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzj(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 3: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzk(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 2: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzk(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 1: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzm(t2, l));
                    this.zzb(t, n);
                    break;
                }
                case 0: {
                    if (!this.zza(t2, n)) break;
                    zznd.zza(t, l, zznd.zzn(t2, l));
                    this.zzb(t, n);
                }
            }
            n += 3;
        } while (true);
    }

    @Override
    public final void zzd(T t) {
        int n;
        int n2;
        for (n = this.zzun; n < (n2 = this.zzuo); ++n) {
            long l = this.zzas(this.zzum[n]) & 1048575;
            Object object = zznd.zzo(t, l);
            if (object == null) continue;
            zznd.zza(t, l, this.zzut.zzk(object));
        }
        int n3 = this.zzum.length;
        n = n2;
        do {
            if (n >= n3) {
                this.zzur.zzd(t);
                if (!this.zzui) return;
                this.zzus.zzd(t);
                return;
            }
            this.zzuq.zza(t, this.zzum[n]);
            ++n;
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public final int zzn(T var1_1) {
        block194 : {
            block193 : {
                if (this.zzuk) break block193;
                var2_3 = zzlu.zzuc;
                var6_11 = -1;
                var4_7 = 0;
                var5_9 = 0;
                break block194;
            }
            var2_2 = zzlu.zzuc;
            var3_4 = 0;
            var4_6 = 0;
            while (var3_4 < this.zzud.length) {
                block195 : {
                    block196 : {
                        var5_8 = this.zzas(var3_4);
                        var6_10 = (var5_8 & 267386880) >>> 20;
                        var7_12 = this.zzud[var3_4];
                        var8_14 = var5_8 & 1048575;
                        var5_8 = var6_10 >= zzke.zzqh.id() && var6_10 <= zzke.zzqu.id() ? this.zzud[var3_4 + 2] & 1048575 : 0;
                        switch (var6_10) {
                            default: {
                                var6_10 = var4_6;
                                break block195;
                            }
                            case 68: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzc(var7_12, (zzlq)zznd.zzo(var1_1, var8_14), this.zzap(var3_4));
                                break block196;
                            }
                            case 67: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzf(var7_12, zzlu.zzh(var1_1, var8_14));
                                break block196;
                            }
                            case 66: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzi(var7_12, zzlu.zzg(var1_1, var8_14));
                                break block196;
                            }
                            case 65: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzh(var7_12, 0L);
                                break block196;
                            }
                            case 64: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzk(var7_12, 0);
                                break block196;
                            }
                            case 63: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzl(var7_12, zzlu.zzg(var1_1, var8_14));
                                break block196;
                            }
                            case 62: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzh(var7_12, zzlu.zzg(var1_1, var8_14));
                                break block196;
                            }
                            case 61: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzc(var7_12, (zzjc)zznd.zzo(var1_1, var8_14));
                                break block196;
                            }
                            case 60: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzmh.zzc(var7_12, zznd.zzo(var1_1, var8_14), this.zzap(var3_4));
                                break block196;
                            }
                            case 59: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var10_17 = zznd.zzo(var1_1, var8_14);
                                var5_8 = var10_17 instanceof zzjc ? zzjr.zzc(var7_12, (zzjc)var10_17) : zzjr.zzb(var7_12, (String)var10_17);
                                break block196;
                            }
                            case 58: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzc(var7_12, true);
                                break block196;
                            }
                            case 57: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzj(var7_12, 0);
                                break block196;
                            }
                            case 56: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzg(var7_12, 0L);
                                break block196;
                            }
                            case 55: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzg(var7_12, zzlu.zzg(var1_1, var8_14));
                                break block196;
                            }
                            case 54: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zze(var7_12, zzlu.zzh(var1_1, var8_14));
                                break block196;
                            }
                            case 53: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzd(var7_12, zzlu.zzh(var1_1, var8_14));
                                break block196;
                            }
                            case 52: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzb(var7_12, 0.0f);
                                break block196;
                            }
                            case 51: {
                                var6_10 = var4_6;
                                if (!this.zza((T)var1_1, var7_12, var3_4)) break block195;
                                var5_8 = zzjr.zzb(var7_12, 0.0);
                                break block196;
                            }
                            case 50: {
                                var5_8 = this.zzut.zzb(var7_12, zznd.zzo(var1_1, var8_14), this.zzaq(var3_4));
                                break block196;
                            }
                            case 49: {
                                var5_8 = zzmh.zzd(var7_12, zzlu.zzd(var1_1, var8_14), this.zzap(var3_4));
                                break block196;
                            }
                            case 48: {
                                var11_23 = zzmh.zzc((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 47: {
                                var11_23 = zzmh.zzg((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 46: {
                                var11_23 = zzmh.zzi((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 45: {
                                var11_23 = zzmh.zzh((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 44: {
                                var11_23 = zzmh.zzd((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 43: {
                                var11_23 = zzmh.zzf((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 42: {
                                var11_23 = zzmh.zzj((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 41: {
                                var11_23 = zzmh.zzh((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 40: {
                                var11_23 = zzmh.zzi((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 39: {
                                var11_23 = zzmh.zze((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 38: {
                                var11_23 = zzmh.zzb((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 37: {
                                var11_23 = zzmh.zza((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 36: {
                                var11_23 = zzmh.zzh((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var11_23 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var11_23);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var12_25 = zzjr.zzad(var11_23);
                                var6_10 = var11_23;
                                var11_23 = var12_25;
                                ** GOTO lbl270
                            }
                            case 35: {
                                var12_25 = zzmh.zzi((List)var2_2.getObject(var1_1, var8_14));
                                var6_10 = var4_6;
                                if (var12_25 <= 0) break block195;
                                if (this.zzul) {
                                    var2_2.putInt(var1_1, var5_8, var12_25);
                                }
                                var5_8 = zzjr.zzab(var7_12);
                                var11_23 = zzjr.zzad(var12_25);
                                var6_10 = var12_25;
lbl270: // 14 sources:
                                var5_8 = var5_8 + var11_23 + var6_10;
                                break block196;
                            }
                            case 34: {
                                var5_8 = zzmh.zzq(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 33: {
                                var5_8 = zzmh.zzu(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 32: {
                                var5_8 = zzmh.zzw(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 31: {
                                var5_8 = zzmh.zzv(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 30: {
                                var5_8 = zzmh.zzr(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 29: {
                                var5_8 = zzmh.zzt(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 28: {
                                var5_8 = zzmh.zzd(var7_12, zzlu.zzd(var1_1, var8_14));
                                break block196;
                            }
                            case 27: {
                                var5_8 = zzmh.zzc(var7_12, zzlu.zzd(var1_1, var8_14), this.zzap(var3_4));
                                break block196;
                            }
                            case 26: {
                                var5_8 = zzmh.zzc(var7_12, zzlu.zzd(var1_1, var8_14));
                                break block196;
                            }
                            case 25: {
                                var5_8 = zzmh.zzx(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 24: {
                                var5_8 = zzmh.zzv(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 23: {
                                var5_8 = zzmh.zzw(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 22: {
                                var5_8 = zzmh.zzs(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 21: {
                                var5_8 = zzmh.zzp(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 20: {
                                var5_8 = zzmh.zzo(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 19: {
                                var5_8 = zzmh.zzv(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 18: {
                                var5_8 = zzmh.zzw(var7_12, zzlu.zzd(var1_1, var8_14), false);
                                break block196;
                            }
                            case 17: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzc(var7_12, (zzlq)zznd.zzo(var1_1, var8_14), this.zzap(var3_4));
                                break block196;
                            }
                            case 16: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzf(var7_12, zznd.zzk(var1_1, var8_14));
                                break block196;
                            }
                            case 15: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzi(var7_12, zznd.zzj(var1_1, var8_14));
                                break block196;
                            }
                            case 14: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzh(var7_12, 0L);
                                break block196;
                            }
                            case 13: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzk(var7_12, 0);
                                break block196;
                            }
                            case 12: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzl(var7_12, zznd.zzj(var1_1, var8_14));
                                break block196;
                            }
                            case 11: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzh(var7_12, zznd.zzj(var1_1, var8_14));
                                break block196;
                            }
                            case 10: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzc(var7_12, (zzjc)zznd.zzo(var1_1, var8_14));
                                break block196;
                            }
                            case 9: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzmh.zzc(var7_12, zznd.zzo(var1_1, var8_14), this.zzap(var3_4));
                                break block196;
                            }
                            case 8: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var10_18 = zznd.zzo(var1_1, var8_14);
                                var5_8 = var10_18 instanceof zzjc ? zzjr.zzc(var7_12, (zzjc)var10_18) : zzjr.zzb(var7_12, (String)var10_18);
                                break block196;
                            }
                            case 7: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzc(var7_12, true);
                                break block196;
                            }
                            case 6: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzj(var7_12, 0);
                                break block196;
                            }
                            case 5: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzg(var7_12, 0L);
                                break block196;
                            }
                            case 4: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzg(var7_12, zznd.zzj(var1_1, var8_14));
                                break block196;
                            }
                            case 3: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zze(var7_12, zznd.zzk(var1_1, var8_14));
                                break block196;
                            }
                            case 2: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzd(var7_12, zznd.zzk(var1_1, var8_14));
                                break block196;
                            }
                            case 1: {
                                var6_10 = var4_6;
                                if (!this.zza(var1_1, var3_4)) break block195;
                                var5_8 = zzjr.zzb(var7_12, 0.0f);
                                break block196;
                            }
                            case 0: 
                        }
                        var6_10 = var4_6;
                        if (!this.zza(var1_1, var3_4)) break block195;
                        var5_8 = zzjr.zzb(var7_12, 0.0);
                    }
                    var6_10 = var4_6 + var5_8;
                }
                var3_4 += 3;
                var4_6 = var6_10;
            }
            return var4_6 + zzlu.zza(this.zzur, var1_1);
        }
        for (var3_5 = 0; var3_5 < this.zzud.length; var3_5 += 3) {
            block197 : {
                var13_27 = this.zzas(var3_5);
                var10_20 = this.zzud;
                var14_28 = var10_20[var3_5];
                var15_29 = (var13_27 & 267386880) >>> 20;
                if (var15_29 <= 17) {
                    var12_26 = var10_20[var3_5 + 2];
                    var7_13 = var12_26 & 1048575;
                    var16_30 = 1 << (var12_26 >>> 20);
                    var11_24 = var6_11;
                    if (var7_13 != var6_11) {
                        var5_9 = var2_3.getInt(var1_1, var7_13);
                        var11_24 = var7_13;
                    }
                    var6_11 = var12_26;
                    var7_13 = var11_24;
                    var11_24 = var6_11;
                    var12_26 = var5_9;
                } else {
                    var11_24 = this.zzul != false && var15_29 >= zzke.zzqh.id() && var15_29 <= zzke.zzqu.id() ? this.zzud[var3_5 + 2] & 1048575 : 0;
                    var16_30 = 0;
                    var12_26 = var5_9;
                    var7_13 = var6_11;
                }
                var8_15 = var13_27 & 1048575;
                switch (var15_29) {
                    default: {
                        var5_9 = var4_7;
                        break block197;
                    }
                    case 68: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzc(var14_28, (zzlq)var2_3.getObject(var1_1, var8_15), this.zzap(var3_5));
                        ** GOTO lbl803
                    }
                    case 67: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzf(var14_28, zzlu.zzh(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 66: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzi(var14_28, zzlu.zzg(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 65: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzh(var14_28, 0L);
                        ** GOTO lbl803
                    }
                    case 64: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzk(var14_28, 0);
                        ** GOTO lbl775
                    }
                    case 63: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzl(var14_28, zzlu.zzg(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 62: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzh(var14_28, zzlu.zzg(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 61: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzc(var14_28, (zzjc)var2_3.getObject(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 60: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzmh.zzc(var14_28, var2_3.getObject(var1_1, var8_15), this.zzap(var3_5));
                        ** GOTO lbl803
                    }
                    case 59: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var10_21 = var2_3.getObject(var1_1, var8_15);
                        var5_9 = var10_21 instanceof zzjc ? zzjr.zzc(var14_28, (zzjc)var10_21) : zzjr.zzb(var14_28, (String)var10_21);
                        ** GOTO lbl803
                    }
                    case 58: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzc(var14_28, true);
                        ** GOTO lbl775
                    }
                    case 57: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzj(var14_28, 0);
                        ** GOTO lbl775
                    }
                    case 56: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzg(var14_28, 0L);
                        ** GOTO lbl803
                    }
                    case 55: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzg(var14_28, zzlu.zzg(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 54: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zze(var14_28, zzlu.zzh(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 53: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzd(var14_28, zzlu.zzh(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 52: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzb(var14_28, 0.0f);
                        ** GOTO lbl775
                    }
                    case 51: {
                        var5_9 = var4_7;
                        if (!this.zza((T)var1_1, var14_28, var3_5)) break block197;
                        var5_9 = zzjr.zzb(var14_28, 0.0);
                        ** GOTO lbl803
                    }
                    case 50: {
                        var5_9 = this.zzut.zzb(var14_28, var2_3.getObject(var1_1, var8_15), this.zzaq(var3_5));
                        ** GOTO lbl803
                    }
                    case 49: {
                        var5_9 = zzmh.zzd(var14_28, (List)var2_3.getObject(var1_1, var8_15), this.zzap(var3_5));
                        ** GOTO lbl803
                    }
                    case 48: {
                        var6_11 = zzmh.zzc((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 47: {
                        var6_11 = zzmh.zzg((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 46: {
                        var6_11 = zzmh.zzi((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 45: {
                        var6_11 = zzmh.zzh((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 44: {
                        var6_11 = zzmh.zzd((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 43: {
                        var6_11 = zzmh.zzf((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 42: {
                        var6_11 = zzmh.zzj((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 41: {
                        var6_11 = zzmh.zzh((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 40: {
                        var6_11 = zzmh.zzi((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 39: {
                        var6_11 = zzmh.zze((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 38: {
                        var6_11 = zzmh.zzb((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 37: {
                        var6_11 = zzmh.zza((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 36: {
                        var6_11 = zzmh.zzh((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var6_11 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var6_11);
                        }
                        var16_30 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var6_11);
                        var5_9 = var6_11;
                        var6_11 = var16_30;
                        ** GOTO lbl697
                    }
                    case 35: {
                        var16_30 = zzmh.zzi((List)var2_3.getObject(var1_1, var8_15));
                        var5_9 = var4_7;
                        if (var16_30 <= 0) break block197;
                        if (this.zzul) {
                            var2_3.putInt(var1_1, var11_24, var16_30);
                        }
                        var6_11 = zzjr.zzab(var14_28);
                        var11_24 = zzjr.zzad(var16_30);
                        var5_9 = var16_30;
lbl697: // 14 sources:
                        var5_9 = var6_11 + var11_24 + var5_9;
                        ** GOTO lbl775
                    }
                    case 34: {
                        var5_9 = zzmh.zzq(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl746
                    }
                    case 33: {
                        var5_9 = zzmh.zzu(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl746
                    }
                    case 32: {
                        var5_9 = zzmh.zzw(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl746
                    }
                    case 31: {
                        var5_9 = zzmh.zzv(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl746
                    }
                    case 30: {
                        var5_9 = zzmh.zzr(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl746
                    }
                    case 29: {
                        var5_9 = zzmh.zzt(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl803
                    }
                    case 28: {
                        var5_9 = zzmh.zzd(var14_28, (List)var2_3.getObject(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 27: {
                        var5_9 = zzmh.zzc(var14_28, (List)var2_3.getObject(var1_1, var8_15), this.zzap(var3_5));
                        ** GOTO lbl803
                    }
                    case 26: {
                        var5_9 = zzmh.zzc(var14_28, (List)var2_3.getObject(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 25: {
                        var5_9 = zzmh.zzx(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl746
                    }
                    case 24: {
                        var5_9 = zzmh.zzv(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl746
                    }
                    case 23: {
                        var5_9 = zzmh.zzw(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl746
                    }
                    case 22: {
                        var5_9 = zzmh.zzs(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl746
                    }
                    case 21: {
                        var5_9 = zzmh.zzp(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl746
                    }
                    case 20: {
                        var5_9 = zzmh.zzo(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl746
                    }
                    case 19: {
                        var5_9 = zzmh.zzv(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
lbl746: // 12 sources:
                        var5_9 = var4_7 + var5_9;
                        break block197;
                    }
                    case 18: {
                        var5_9 = zzmh.zzw(var14_28, (List)var2_3.getObject(var1_1, var8_15), false);
                        ** GOTO lbl803
                    }
                    case 17: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zzc(var14_28, (zzlq)var2_3.getObject(var1_1, var8_15), this.zzap(var3_5));
                        ** GOTO lbl803
                    }
                    case 16: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zzf(var14_28, var2_3.getLong(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 15: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zzi(var14_28, var2_3.getInt(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 14: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zzh(var14_28, 0L);
                        ** GOTO lbl803
                    }
                    case 13: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zzk(var14_28, 0);
lbl775: // 6 sources:
                        var5_9 = var4_7 + var5_9;
                        break block197;
                    }
                    case 12: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zzl(var14_28, var2_3.getInt(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 11: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zzh(var14_28, var2_3.getInt(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 10: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zzc(var14_28, (zzjc)var2_3.getObject(var1_1, var8_15));
                        ** GOTO lbl803
                    }
                    case 9: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzmh.zzc(var14_28, var2_3.getObject(var1_1, var8_15), this.zzap(var3_5));
                        ** GOTO lbl803
                    }
                    case 8: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var10_22 = var2_3.getObject(var1_1, var8_15);
                        var5_9 = var10_22 instanceof zzjc != false ? zzjr.zzc(var14_28, (zzjc)var10_22) : zzjr.zzb(var14_28, (String)var10_22);
lbl803: // 30 sources:
                        var5_9 = var4_7 + var5_9;
                        break block197;
                    }
                    case 7: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) != 0) {
                            var5_9 = var4_7 + zzjr.zzc(var14_28, true);
                        }
                        break block197;
                    }
                    case 6: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) != 0) {
                            var5_9 = var4_7 + zzjr.zzj(var14_28, 0);
                        }
                        break block197;
                    }
                    case 5: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zzg(var14_28, 0L);
                        ** GOTO lbl835
                    }
                    case 4: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zzg(var14_28, var2_3.getInt(var1_1, var8_15));
                        ** GOTO lbl835
                    }
                    case 3: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zze(var14_28, var2_3.getLong(var1_1, var8_15));
                        ** GOTO lbl835
                    }
                    case 2: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) == 0) break block197;
                        var5_9 = zzjr.zzd(var14_28, var2_3.getLong(var1_1, var8_15));
lbl835: // 4 sources:
                        var5_9 = var4_7 + var5_9;
                        break block197;
                    }
                    case 1: {
                        var5_9 = var4_7;
                        if ((var12_26 & var16_30) != 0) {
                            var5_9 = var4_7 + zzjr.zzb(var14_28, 0.0f);
                        }
                        break block197;
                    }
                    case 0: 
                }
                var5_9 = var4_7;
                if ((var12_26 & var16_30) != 0) {
                    var5_9 = var4_7 + zzjr.zzb(var14_28, 0.0);
                }
            }
            var6_11 = var7_13;
            var4_7 = var5_9;
            var5_9 = var12_26;
        }
        var3_5 = 0;
        var5_9 = var11_24 = var4_7 + zzlu.zza(this.zzur, var1_1);
        if (this.zzui == false) return var5_9;
        var1_1 = this.zzus.zzb(var1_1);
        var5_9 = var3_5;
        for (var6_11 = 0; var6_11 < var1_1.zzos.zzer(); var5_9 += zzkb.zzb((zzkd)var2_3.getKey(), var2_3.getValue()), ++var6_11) {
            var2_3 = var1_1.zzos.zzaw(var6_11);
        }
        var1_1 = var1_1.zzos.zzes().iterator();
        do {
            if (!var1_1.hasNext()) {
                return var11_24 + var5_9;
            }
            var2_3 = (Map.Entry)var1_1.next();
            var5_9 += zzkb.zzb((zzkd)var2_3.getKey(), var2_3.getValue());
        } while (true);
    }

    @Override
    public final boolean zzp(T t) {
        int n = -1;
        int n2 = 0;
        int n3 = 0;
        do {
            int n4;
            block21 : {
                int n5;
                block22 : {
                    zzmf<?> zzmf2;
                    int n6;
                    Object object;
                    int n7;
                    block23 : {
                        int n8;
                        block18 : {
                            block19 : {
                                int n9;
                                block20 : {
                                    n4 = this.zzun;
                                    n7 = 1;
                                    int n10 = 1;
                                    if (n2 >= n4) {
                                        if (!this.zzui) return true;
                                        if (this.zzus.zzb(t).isInitialized()) return true;
                                        return false;
                                    }
                                    n6 = this.zzum[n2];
                                    n9 = this.zzud[n6];
                                    n8 = this.zzas(n6);
                                    if (!this.zzuk) {
                                        n4 = this.zzud[n6 + 2];
                                        int n11 = n4 & 1048575;
                                        int n12 = 1 << (n4 >>> 20);
                                        n4 = n;
                                        n5 = n12;
                                        if (n11 != n) {
                                            n3 = zzuc.getInt(t, n11);
                                            n4 = n11;
                                            n5 = n12;
                                        }
                                    } else {
                                        n5 = 0;
                                        n4 = n;
                                    }
                                    if ((n = (268435456 & n8) != 0 ? 1 : 0) != 0 && !this.zza(t, n6, n3, n5)) {
                                        return false;
                                    }
                                    n = (267386880 & n8) >>> 20;
                                    if (n == 9 || n == 17) break block18;
                                    if (n == 27) break block19;
                                    if (n == 60 || n == 68) break block20;
                                    if (n == 49) break block19;
                                    if (n == 50) {
                                        block17 : {
                                            zzmf2 = this.zzut.zzi(zznd.zzo(t, n8 & 1048575));
                                            n5 = n10;
                                            if (!zzmf2.isEmpty()) {
                                                object = this.zzaq(n6);
                                                n5 = n10;
                                                if (this.zzut.zzm((Object)object).zztw.zzfj() == zznr.zzxx) {
                                                    Object v;
                                                    object = null;
                                                    Iterator iterator2 = zzmf2.values().iterator();
                                                    do {
                                                        n5 = n10;
                                                        if (!iterator2.hasNext()) break block17;
                                                        v = iterator2.next();
                                                        zzmf2 = object;
                                                        if (object == null) {
                                                            zzmf2 = zzmd.zzej().zzf(v.getClass());
                                                        }
                                                        object = zzmf2;
                                                    } while (zzmf2.zzp(v));
                                                    return false;
                                                }
                                            }
                                        }
                                        if (n5 == 0) {
                                            return false;
                                        }
                                    }
                                    break block21;
                                }
                                if (this.zza(t, n9, n6) && !zzlu.zza(t, n8, this.zzap(n6))) {
                                    return false;
                                }
                                break block21;
                            }
                            object = (List)zznd.zzo(t, n8 & 1048575);
                            n5 = n7;
                            if (object.isEmpty()) break block22;
                            break block23;
                        }
                        if (this.zza(t, n6, n3, n5) && !zzlu.zza(t, n8, this.zzap(n6))) {
                            return false;
                        }
                        break block21;
                    }
                    zzmf2 = this.zzap(n6);
                    n = 0;
                    do {
                        n5 = n7;
                        if (n >= object.size()) break;
                        if (!zzmf2.zzp(object.get(n))) {
                            return false;
                        }
                        ++n;
                    } while (true);
                }
                if (n5 == 0) {
                    return false;
                }
            }
            ++n2;
            n = n4;
        } while (true);
    }
}


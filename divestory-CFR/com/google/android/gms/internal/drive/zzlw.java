/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zziy;
import com.google.android.gms.internal.drive.zziz;
import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjx;
import com.google.android.gms.internal.drive.zzjy;
import com.google.android.gms.internal.drive.zzkb;
import com.google.android.gms.internal.drive.zzkd;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzkq;
import com.google.android.gms.internal.drive.zzkt;
import com.google.android.gms.internal.drive.zzkv;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzlr;
import com.google.android.gms.internal.drive.zzmd;
import com.google.android.gms.internal.drive.zzmf;
import com.google.android.gms.internal.drive.zzmh;
import com.google.android.gms.internal.drive.zzmx;
import com.google.android.gms.internal.drive.zzmy;
import com.google.android.gms.internal.drive.zznr;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

final class zzlw<T>
implements zzmf<T> {
    private final zzlq zzuh;
    private final boolean zzui;
    private final zzmx<?, ?> zzur;
    private final zzjy<?> zzus;

    private zzlw(zzmx<?, ?> zzmx2, zzjy<?> zzjy2, zzlq zzlq2) {
        this.zzur = zzmx2;
        this.zzui = zzjy2.zze(zzlq2);
        this.zzus = zzjy2;
        this.zzuh = zzlq2;
    }

    static <T> zzlw<T> zza(zzmx<?, ?> zzmx2, zzjy<?> zzjy2, zzlq zzlq2) {
        return new zzlw<T>(zzmx2, zzjy2, zzlq2);
    }

    @Override
    public final boolean equals(T t, T t2) {
        if (!this.zzur.zzr(t).equals(this.zzur.zzr(t2))) {
            return false;
        }
        if (!this.zzui) return true;
        return this.zzus.zzb(t).equals(this.zzus.zzb(t2));
    }

    @Override
    public final int hashCode(T t) {
        int n;
        int n2 = n = this.zzur.zzr(t).hashCode();
        if (!this.zzui) return n2;
        return n * 53 + this.zzus.zzb(t).hashCode();
    }

    @Override
    public final T newInstance() {
        return (T)this.zzuh.zzcz().zzde();
    }

    @Override
    public final void zza(T t, zzns zzns2) throws IOException {
        Iterator<Map.Entry<?, Object>> iterator2 = this.zzus.zzb(t).iterator();
        do {
            Object object;
            if (!iterator2.hasNext()) {
                object = this.zzur;
                ((zzmx)object).zzc(((zzmx)object).zzr(t), zzns2);
                return;
            }
            Map.Entry<?, Object> entry = iterator2.next();
            object = (zzkd)entry.getKey();
            if (object.zzcr() != zznr.zzxx) throw new IllegalStateException("Found invalid MessageSet item.");
            if (object.zzcs()) throw new IllegalStateException("Found invalid MessageSet item.");
            if (object.zzct()) throw new IllegalStateException("Found invalid MessageSet item.");
            if (entry instanceof zzkv) {
                zzns2.zza(object.zzcp(), (Object)((zzkv)entry).zzdq().zzbl());
                continue;
            }
            zzns2.zza(object.zzcp(), entry.getValue());
        } while (true);
    }

    @Override
    public final void zza(T object, byte[] arrby, int n, int n2, zziz zziz2) throws IOException {
        zzkk zzkk2 = (zzkk)object;
        Object object2 = zzkk2.zzrq;
        zzmy zzmy2 = object2;
        if (object2 == zzmy.zzfa()) {
            zzkk2.zzrq = zzmy2 = zzmy.zzfb();
        }
        ((zzkk.zzc)object).zzdg();
        object = null;
        do {
            if (n >= n2) {
                if (n != n2) throw zzkq.zzdm();
                return;
            }
            n = zziy.zza(arrby, n, zziz2);
            int n3 = zziz2.zznk;
            if (n3 != 11) {
                if ((n3 & 7) == 2) {
                    object = (zzkk.zzd)this.zzus.zza(zziz2.zznn, this.zzuh, n3 >>> 3);
                    if (object != null) {
                        zzmd.zzej();
                        throw new NoSuchMethodError();
                    }
                    n = zziy.zza(n3, arrby, n, n2, zzmy2, zziz2);
                    continue;
                }
                n = zziy.zza(n3, arrby, n, n2, zziz2);
                continue;
            }
            int n4 = 0;
            object2 = null;
            do {
                n3 = n;
                if (n >= n2) break;
                n = zziy.zza(arrby, n, zziz2);
                int n5 = zziz2.zznk;
                int n6 = n5 >>> 3;
                n3 = n5 & 7;
                if (n6 != 2) {
                    if (n6 == 3) {
                        if (object != null) {
                            zzmd.zzej();
                            throw new NoSuchMethodError();
                        }
                        if (n3 == 2) {
                            n = zziy.zze(arrby, n, zziz2);
                            object2 = (zzjc)zziz2.zznm;
                            continue;
                        }
                    }
                } else if (n3 == 0) {
                    n = zziy.zza(arrby, n, zziz2);
                    n4 = zziz2.zznk;
                    object = (zzkk.zzd)this.zzus.zza(zziz2.zznn, this.zzuh, n4);
                    continue;
                }
                n3 = n;
                if (n5 == 12) break;
                n = zziy.zza(n5, arrby, n, n2, zziz2);
            } while (true);
            if (object2 != null) {
                zzmy2.zzb(n4 << 3 | 2, object2);
            }
            n = n3;
        } while (true);
    }

    @Override
    public final void zzc(T t, T t2) {
        zzmh.zza(this.zzur, t, t2);
        if (!this.zzui) return;
        zzmh.zza(this.zzus, t, t2);
    }

    @Override
    public final void zzd(T t) {
        this.zzur.zzd(t);
        this.zzus.zzd(t);
    }

    @Override
    public final int zzn(T t) {
        int n;
        zzmx<?, ?> zzmx2 = this.zzur;
        int n2 = n = zzmx2.zzs(zzmx2.zzr(t)) + 0;
        if (!this.zzui) return n2;
        return n + this.zzus.zzb(t).zzco();
    }

    @Override
    public final boolean zzp(T t) {
        return this.zzus.zzb(t).isInitialized();
    }
}


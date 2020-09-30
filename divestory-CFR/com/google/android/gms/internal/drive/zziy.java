/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zziz;
import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzkl;
import com.google.android.gms.internal.drive.zzkm;
import com.google.android.gms.internal.drive.zzkp;
import com.google.android.gms.internal.drive.zzkq;
import com.google.android.gms.internal.drive.zzlu;
import com.google.android.gms.internal.drive.zzmf;
import com.google.android.gms.internal.drive.zzmy;
import com.google.android.gms.internal.drive.zznf;
import java.io.IOException;
import java.nio.charset.Charset;

final class zziy {
    static int zza(int n, byte[] arrby, int n2, int n3, zziz zziz2) throws zzkq {
        int n4;
        if (n >>> 3 == 0) throw zzkq.zzdk();
        int n5 = n & 7;
        if (n5 == 0) return zziy.zzb(arrby, n2, zziz2);
        if (n5 == 1) return n2 + 8;
        if (n5 == 2) return zziy.zza(arrby, n2, zziz2) + zziz2.zznk;
        if (n5 != 3) {
            if (n5 != 5) throw zzkq.zzdk();
            return n2 + 4;
        }
        int n6 = n & -8 | 4;
        n = 0;
        do {
            n4 = n;
            n5 = n2;
            if (n2 >= n3) break;
            n2 = zziy.zza(arrby, n2, zziz2);
            n4 = n = zziz2.zznk;
            n5 = n2;
            if (n == n6) break;
            n2 = zziy.zza(n, arrby, n2, n3, zziz2);
        } while (true);
        if (n5 > n3) throw zzkq.zzdm();
        if (n4 != n6) throw zzkq.zzdm();
        return n5;
    }

    static int zza(int n, byte[] arrby, int n2, int n3, zzkp<?> zzkl2, zziz zziz2) {
        zzkl2 = zzkl2;
        n2 = zziy.zza(arrby, n2, zziz2);
        zzkl2.zzam(zziz2.zznk);
        while (n2 < n3) {
            int n4 = zziy.zza(arrby, n2, zziz2);
            if (n != zziz2.zznk) return n2;
            n2 = zziy.zza(arrby, n4, zziz2);
            zzkl2.zzam(zziz2.zznk);
        }
        return n2;
    }

    static int zza(int n, byte[] arrby, int n2, int n3, zzmy zzmy2, zziz zziz2) throws zzkq {
        if (n >>> 3 == 0) throw zzkq.zzdk();
        int n4 = n & 7;
        if (n4 == 0) {
            n2 = zziy.zzb(arrby, n2, zziz2);
            zzmy2.zzb(n, zziz2.zznl);
            return n2;
        }
        if (n4 == 1) {
            zzmy2.zzb(n, zziy.zzb(arrby, n2));
            return n2 + 8;
        }
        if (n4 != 2) {
            zzmy zzmy3;
            int n5;
            int n6;
            block6 : {
                if (n4 != 3) {
                    if (n4 != 5) throw zzkq.zzdk();
                    zzmy2.zzb(n, zziy.zza(arrby, n2));
                    return n2 + 4;
                }
                zzmy3 = zzmy.zzfb();
                n6 = n & -8 | 4;
                n4 = 0;
                do {
                    n5 = n4;
                    n4 = n2;
                    if (n2 >= n3) break block6;
                    n5 = zziy.zza(arrby, n2, zziz2);
                    n2 = n4 = zziz2.zznk;
                    if (n4 == n6) break;
                    n2 = zziy.zza(n2, arrby, n5, n3, zzmy3, zziz2);
                } while (true);
                n4 = n5;
                n5 = n2;
            }
            if (n4 > n3) throw zzkq.zzdm();
            if (n5 != n6) throw zzkq.zzdm();
            zzmy2.zzb(n, zzmy3);
            return n4;
        }
        n2 = zziy.zza(arrby, n2, zziz2);
        n3 = zziz2.zznk;
        if (n3 < 0) throw zzkq.zzdj();
        if (n3 > arrby.length - n2) throw zzkq.zzdi();
        if (n3 == 0) {
            zzmy2.zzb(n, zzjc.zznq);
            return n2 + n3;
        }
        zzmy2.zzb(n, zzjc.zzb(arrby, n2, n3));
        return n2 + n3;
    }

    static int zza(int n, byte[] arrby, int n2, zziz zziz2) {
        int n3 = n & 127;
        n = n2 + 1;
        if ((n2 = arrby[n2]) >= 0) {
            zziz2.zznk = n3 | n2 << 7;
            return n;
        }
        n2 = n3 | (n2 & 127) << 7;
        n3 = n + 1;
        if ((n = arrby[n]) >= 0) {
            zziz2.zznk = n2 | n << 14;
            return n3;
        }
        n = n2 | (n & 127) << 14;
        n2 = n3 + 1;
        if ((n3 = arrby[n3]) >= 0) {
            zziz2.zznk = n | n3 << 21;
            return n2;
        }
        n3 = n | (n3 & 127) << 21;
        n = n2 + 1;
        byte by = arrby[n2];
        if (by >= 0) {
            zziz2.zznk = n3 | by << 28;
            return n;
        }
        do {
            n2 = n + 1;
            if (arrby[n] >= 0) {
                zziz2.zznk = n3 | (by & 127) << 28;
                return n2;
            }
            n = n2;
        } while (true);
    }

    static int zza(zzmf<?> zzmf2, int n, byte[] arrby, int n2, int n3, zzkp<?> zzkp2, zziz zziz2) throws IOException {
        n2 = zziy.zza(zzmf2, arrby, n2, n3, zziz2);
        zzkp2.add(zziz2.zznm);
        while (n2 < n3) {
            int n4 = zziy.zza(arrby, n2, zziz2);
            if (n != zziz2.zznk) return n2;
            n2 = zziy.zza(zzmf2, arrby, n4, n3, zziz2);
            zzkp2.add(zziz2.zznm);
        }
        return n2;
    }

    static int zza(zzmf zzmf2, byte[] arrby, int n, int n2, int n3, zziz zziz2) throws IOException {
        zzmf2 = (zzlu)zzmf2;
        Object t = ((zzlu)zzmf2).newInstance();
        n = ((zzlu)zzmf2).zza(t, arrby, n, n2, n3, zziz2);
        ((zzlu)zzmf2).zzd(t);
        zziz2.zznm = t;
        return n;
    }

    static int zza(zzmf zzmf2, byte[] arrby, int n, int n2, zziz zziz2) throws IOException {
        int n3 = n + 1;
        int n4 = arrby[n];
        int n5 = n3;
        n = n4;
        if (n4 < 0) {
            n5 = zziy.zza(n4, arrby, n3, zziz2);
            n = zziz2.zznk;
        }
        if (n < 0) throw zzkq.zzdi();
        if (n > n2 - n5) throw zzkq.zzdi();
        Object t = zzmf2.newInstance();
        zzmf2.zza(t, arrby, n5, n += n5, zziz2);
        zzmf2.zzd(t);
        zziz2.zznm = t;
        return n;
    }

    static int zza(byte[] arrby, int n) {
        byte by = arrby[n];
        byte by2 = arrby[n + 1];
        byte by3 = arrby[n + 2];
        return (arrby[n + 3] & 255) << 24 | (by & 255 | (by2 & 255) << 8 | (by3 & 255) << 16);
    }

    static int zza(byte[] arrby, int n, zziz zziz2) {
        int n2 = n + 1;
        if ((n = arrby[n]) < 0) return zziy.zza(n, arrby, n2, zziz2);
        zziz2.zznk = n;
        return n2;
    }

    static int zza(byte[] arrby, int n, zzkp<?> zzkl2, zziz zziz2) throws IOException {
        zzkl2 = zzkl2;
        n = zziy.zza(arrby, n, zziz2);
        int n2 = zziz2.zznk + n;
        do {
            if (n >= n2) {
                if (n != n2) throw zzkq.zzdi();
                return n;
            }
            n = zziy.zza(arrby, n, zziz2);
            zzkl2.zzam(zziz2.zznk);
        } while (true);
    }

    static int zzb(byte[] arrby, int n, zziz zziz2) {
        int n2 = n + 1;
        long l = arrby[n];
        if (l >= 0L) {
            zziz2.zznl = l;
            return n2;
        }
        n = n2 + 1;
        byte by = arrby[n2];
        l = l & 127L | (long)(by & 127) << 7;
        n2 = 7;
        do {
            if (by >= 0) {
                zziz2.zznl = l;
                return n;
            }
            by = arrby[n];
            l |= (long)(by & 127) << (n2 += 7);
            ++n;
        } while (true);
    }

    static long zzb(byte[] arrby, int n) {
        long l = arrby[n];
        long l2 = arrby[n + 1];
        long l3 = arrby[n + 2];
        long l4 = arrby[n + 3];
        long l5 = arrby[n + 4];
        long l6 = arrby[n + 5];
        long l7 = arrby[n + 6];
        return ((long)arrby[n + 7] & 255L) << 56 | (l & 255L | (l2 & 255L) << 8 | (l3 & 255L) << 16 | (l4 & 255L) << 24 | (l5 & 255L) << 32 | (l6 & 255L) << 40 | (l7 & 255L) << 48);
    }

    static double zzc(byte[] arrby, int n) {
        return Double.longBitsToDouble(zziy.zzb(arrby, n));
    }

    static int zzc(byte[] arrby, int n, zziz zziz2) throws zzkq {
        int n2 = zziy.zza(arrby, n, zziz2);
        n = zziz2.zznk;
        if (n < 0) throw zzkq.zzdj();
        if (n == 0) {
            zziz2.zznm = "";
            return n2;
        }
        zziz2.zznm = new String(arrby, n2, n, zzkm.UTF_8);
        return n2 + n;
    }

    static float zzd(byte[] arrby, int n) {
        return Float.intBitsToFloat(zziy.zza(arrby, n));
    }

    static int zzd(byte[] arrby, int n, zziz zziz2) throws zzkq {
        n = zziy.zza(arrby, n, zziz2);
        int n2 = zziz2.zznk;
        if (n2 < 0) throw zzkq.zzdj();
        if (n2 == 0) {
            zziz2.zznm = "";
            return n;
        }
        zziz2.zznm = zznf.zzg(arrby, n, n2);
        return n + n2;
    }

    static int zze(byte[] arrby, int n, zziz zziz2) throws zzkq {
        int n2 = zziy.zza(arrby, n, zziz2);
        n = zziz2.zznk;
        if (n < 0) throw zzkq.zzdj();
        if (n > arrby.length - n2) throw zzkq.zzdi();
        if (n == 0) {
            zziz2.zznm = zzjc.zznq;
            return n2;
        }
        zziz2.zznm = zzjc.zzb(arrby, n2, n);
        return n2 + n;
    }
}


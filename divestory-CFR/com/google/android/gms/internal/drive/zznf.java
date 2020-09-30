/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzix;
import com.google.android.gms.internal.drive.zzkq;
import com.google.android.gms.internal.drive.zznd;
import com.google.android.gms.internal.drive.zznh;
import com.google.android.gms.internal.drive.zzni;
import com.google.android.gms.internal.drive.zznj;
import com.google.android.gms.internal.drive.zznk;

final class zznf {
    private static final zznh zzwt;

    static {
        boolean bl = zznd.zzfd() && zznd.zzfe();
        zznh zznh2 = bl && !zzix.zzbr() ? new zznk() : new zzni();
        zzwt = zznh2;
    }

    static int zza(CharSequence charSequence) {
        int n;
        int n2;
        block8 : {
            int n3;
            n = charSequence.length();
            int n4 = 0;
            for (n3 = 0; n3 < n && charSequence.charAt(n3) < ''; ++n3) {
            }
            int n5 = n;
            do {
                n2 = n5;
                if (n3 >= n) break block8;
                n2 = charSequence.charAt(n3);
                if (n2 >= 2048) break;
                n5 += 127 - n2 >>> 31;
                ++n3;
            } while (true);
            int n6 = charSequence.length();
            n2 = n4;
            while (n3 < n6) {
                char c = charSequence.charAt(n3);
                if (c < '\u0800') {
                    n2 += 127 - c >>> 31;
                    n4 = n3;
                } else {
                    int n7;
                    n2 = n7 = n2 + 2;
                    n4 = n3;
                    if ('\ud800' <= c) {
                        n2 = n7;
                        n4 = n3;
                        if (c <= '\udfff') {
                            if (Character.codePointAt(charSequence, n3) < 65536) throw new zznj(n3, n6);
                            n4 = n3 + 1;
                            n2 = n7;
                        }
                    }
                }
                n3 = n4 + 1;
            }
            n2 = n5 + n2;
        }
        if (n2 >= n) {
            return n2;
        }
        long l = n2;
        charSequence = new StringBuilder(54);
        ((StringBuilder)charSequence).append("UTF-8 length does not fit in int: ");
        ((StringBuilder)charSequence).append(l + 0x100000000L);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    static int zza(CharSequence charSequence, byte[] arrby, int n, int n2) {
        return zzwt.zzb(charSequence, arrby, n, n2);
    }

    private static int zzay(int n) {
        int n2 = n;
        if (n <= -12) return n2;
        return -1;
    }

    static /* synthetic */ int zzaz(int n) {
        return zznf.zzay(n);
    }

    private static int zzc(int n, int n2, int n3) {
        if (n > -12) return -1;
        if (n2 > -65) return -1;
        if (n3 <= -65) return n ^ n2 << 8 ^ n3 << 16;
        return -1;
    }

    static /* synthetic */ int zzd(int n, int n2, int n3) {
        return zznf.zzc(n, n2, n3);
    }

    public static boolean zzd(byte[] arrby) {
        return zzwt.zze(arrby, 0, arrby.length);
    }

    public static boolean zze(byte[] arrby, int n, int n2) {
        return zzwt.zze(arrby, n, n2);
    }

    private static int zzf(byte[] arrby, int n, int n2) {
        byte by = arrby[n - 1];
        if ((n2 -= n) == 0) return zznf.zzay(by);
        if (n2 == 1) return zznf.zzr(by, arrby[n]);
        if (n2 != 2) throw new AssertionError();
        return zznf.zzc(by, arrby[n], arrby[n + 1]);
    }

    static String zzg(byte[] arrby, int n, int n2) throws zzkq {
        return zzwt.zzg(arrby, n, n2);
    }

    static /* synthetic */ int zzh(byte[] arrby, int n, int n2) {
        return zznf.zzf(arrby, n, n2);
    }

    private static int zzr(int n, int n2) {
        if (n > -12) return -1;
        if (n2 <= -65) return n ^ n2 << 8;
        return -1;
    }

    static /* synthetic */ int zzs(int n, int n2) {
        return zznf.zzr(n, n2);
    }
}


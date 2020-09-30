/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkq;
import com.google.android.gms.internal.drive.zznd;
import com.google.android.gms.internal.drive.zznf;
import com.google.android.gms.internal.drive.zzng;
import com.google.android.gms.internal.drive.zznh;
import com.google.android.gms.internal.drive.zznj;

final class zznk
extends zznh {
    zznk() {
    }

    private static int zza(byte[] arrby, int n, long l, int n2) {
        if (n2 == 0) return zznf.zzaz(n);
        if (n2 == 1) return zznf.zzs(n, zznd.zza(arrby, l));
        if (n2 != 2) throw new AssertionError();
        return zznf.zzd(n, zznd.zza(arrby, l), zznd.zza(arrby, l + 1L));
    }

    /*
     * Unable to fully structure code
     */
    @Override
    final int zzb(int var1_1, byte[] var2_2, int var3_3, int var4_4) {
        block13 : {
            if ((var3_3 | var4_4 | var2_2.length - var4_4) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", new Object[]{var2_2.length, var3_3, var4_4}));
            }
            var5_5 = var3_3;
            var3_3 = (int)((long)var4_4 - var5_5);
            if (var3_3 < 16) {
                var1_1 = 0;
            } else {
                var7_6 = var5_5;
                for (var1_1 = 0; var1_1 < var3_3; ++var1_1, ++var7_6) {
                    if (zznd.zza(var2_2, var7_6) >= 0) {
                        continue;
                    }
                    break block13;
                }
                var1_1 = var3_3;
            }
        }
        var3_3 -= var1_1;
        var7_6 = var5_5 + (long)var1_1;
        var1_1 = var3_3;
        do lbl-1000: // 4 sources:
        {
            block15 : {
                block14 : {
                    var4_4 = 0;
                    var3_3 = var1_1;
                    var1_1 = var4_4;
                    do {
                        var5_5 = var7_6;
                        if (var3_3 <= 0) break;
                        var5_5 = var7_6 + 1L;
                        var1_1 = zznd.zza(var2_2, var7_6);
                        if (var1_1 < 0) break;
                        --var3_3;
                        var7_6 = var5_5;
                    } while (true);
                    if (var3_3 == 0) {
                        return 0;
                    }
                    --var3_3;
                    if (var1_1 >= -32) break block14;
                    if (var3_3 == 0) {
                        return var1_1;
                    }
                    if (var1_1 < -62) return -1;
                    var7_6 = var5_5 + 1L;
                    var1_1 = --var3_3;
                    if (zznd.zza(var2_2, var5_5) <= -65) ** GOTO lbl-1000
                    return -1;
                }
                if (var1_1 >= -16) break block15;
                if (var3_3 < 2) {
                    return zznk.zza(var2_2, var1_1, var5_5, var3_3);
                }
                var3_3 -= 2;
                var9_7 = var5_5 + 1L;
                var4_4 = zznd.zza(var2_2, var5_5);
                if (var4_4 > -65) return -1;
                if (var1_1 == -32) {
                    if (var4_4 < -96) return -1;
                }
                if (var1_1 == -19) {
                    if (var4_4 >= -96) return -1;
                }
                var7_6 = var9_7 + 1L;
                var1_1 = var3_3;
                if (zznd.zza(var2_2, var9_7) <= -65) ** GOTO lbl-1000
                return -1;
            }
            if (var3_3 < 3) {
                return zznk.zza(var2_2, var1_1, var5_5, var3_3);
            }
            var7_6 = var5_5 + 1L;
            var4_4 = zznd.zza(var2_2, var5_5);
            if (var4_4 > -65) return -1;
            if ((var1_1 << 28) + (var4_4 + 112) >> 30 != 0) return -1;
            var5_5 = var7_6 + 1L;
            if (zznd.zza(var2_2, var7_6) > -65) return -1;
            var7_6 = var5_5 + 1L;
            var1_1 = var3_3 -= 3;
        } while (zznd.zza(var2_2, var5_5) <= -65);
        return -1;
    }

    @Override
    final int zzb(CharSequence charSequence, byte[] arrby, int n, int n2) {
        char c;
        int n3;
        long l;
        block16 : {
            long l2;
            long l3 = n;
            long l4 = (long)n2 + l3;
            n3 = charSequence.length();
            if (n3 <= n2 && arrby.length - n2 >= n) {
                n2 = 0;
                do {
                    l2 = 1L;
                    if (n2 >= n3 || (n = (int)charSequence.charAt(n2)) >= 128) break;
                    zznd.zza(arrby, l3, (byte)n);
                    ++n2;
                    l3 = 1L + l3;
                } while (true);
                n = n2;
                l = l3;
                if (n2 == n3) {
                    return (int)l3;
                }
            } else {
                char c2 = charSequence.charAt(n3 - 1);
                charSequence = new StringBuilder(37);
                ((StringBuilder)charSequence).append("Failed writing ");
                ((StringBuilder)charSequence).append(c2);
                ((StringBuilder)charSequence).append(" at index ");
                ((StringBuilder)charSequence).append(n + n2);
                throw new ArrayIndexOutOfBoundsException(((StringBuilder)charSequence).toString());
            }
            while (n < n3) {
                c = charSequence.charAt(n);
                if (c < '' && l < l4) {
                    zznd.zza(arrby, l, (byte)c);
                    long l5 = l2;
                    l3 = l + l2;
                    l2 = l5;
                } else if (c < '\u0800' && l <= l4 - 2L) {
                    l3 = l + l2;
                    zznd.zza(arrby, l, (byte)(c >>> 6 | 960));
                    zznd.zza(arrby, l3, (byte)(c & 63 | 128));
                    l3 += l2;
                } else if ((c < '\ud800' || '\udfff' < c) && l <= l4 - 3L) {
                    l3 = l + l2;
                    zznd.zza(arrby, l, (byte)(c >>> 12 | 480));
                    l2 = l3 + l2;
                    zznd.zza(arrby, l3, (byte)(c >>> 6 & 63 | 128));
                    zznd.zza(arrby, l2, (byte)(c & 63 | 128));
                    l3 = l2 + 1L;
                    l2 = 1L;
                } else {
                    if (l > l4 - 4L) break block16;
                    n2 = n + 1;
                    if (n2 == n3) throw new zznj(n - 1, n3);
                    char c3 = charSequence.charAt(n2);
                    if (!Character.isSurrogatePair(c, c3)) {
                        n = n2;
                        throw new zznj(n - 1, n3);
                    }
                    n = Character.toCodePoint(c, c3);
                    l2 = l + 1L;
                    zznd.zza(arrby, l, (byte)(n >>> 18 | 240));
                    l3 = l2 + 1L;
                    zznd.zza(arrby, l2, (byte)(n >>> 12 & 63 | 128));
                    l = l3 + 1L;
                    zznd.zza(arrby, l3, (byte)(n >>> 6 & 63 | 128));
                    l2 = 1L;
                    l3 = l + 1L;
                    zznd.zza(arrby, l, (byte)(n & 63 | 128));
                    n = n2;
                }
                ++n;
                l = l3;
            }
            return (int)l;
        }
        if ('\ud800' <= c && c <= '\udfff') {
            n2 = n + 1;
            if (n2 == n3) throw new zznj(n, n3);
            if (!Character.isSurrogatePair(c, charSequence.charAt(n2))) {
                throw new zznj(n, n3);
            }
        }
        charSequence = new StringBuilder(46);
        ((StringBuilder)charSequence).append("Failed writing ");
        ((StringBuilder)charSequence).append(c);
        ((StringBuilder)charSequence).append(" at index ");
        ((StringBuilder)charSequence).append(l);
        throw new ArrayIndexOutOfBoundsException(((StringBuilder)charSequence).toString());
    }

    @Override
    final String zzg(byte[] arrby, int n, int n2) throws zzkq {
        byte by;
        if ((n | n2 | arrby.length - n - n2) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", arrby.length, n, n2));
        }
        int n3 = n + n2;
        char[] arrc = new char[n2];
        n2 = 0;
        while (n < n3 && zzng.zzh(by = zznd.zza(arrby, n))) {
            ++n;
            zzng.zzb(by, arrc, n2);
            ++n2;
        }
        int n4 = n2;
        n2 = n;
        n = n4;
        block1 : while (n2 < n3) {
            n4 = n2 + 1;
            by = zznd.zza(arrby, n2);
            if (zzng.zzh(by)) {
                n2 = n + 1;
                zzng.zzb(by, arrc, n);
                n = n2;
                n2 = n4;
                do {
                    if (n2 >= n3 || !zzng.zzh(by = zznd.zza(arrby, n2))) continue block1;
                    ++n2;
                    zzng.zzb(by, arrc, n);
                    ++n;
                } while (true);
            }
            if (zzng.zzi(by)) {
                if (n4 >= n3) throw zzkq.zzdn();
                zzng.zzb(by, zznd.zza(arrby, n4), arrc, n);
                n2 = n4 + 1;
                ++n;
                continue;
            }
            if (zzng.zzj(by)) {
                if (n4 >= n3 - 1) throw zzkq.zzdn();
                n2 = n4 + 1;
                zzng.zzb(by, zznd.zza(arrby, n4), zznd.zza(arrby, n2), arrc, n);
                ++n2;
                ++n;
                continue;
            }
            if (n4 >= n3 - 2) throw zzkq.zzdn();
            n2 = n4 + 1;
            byte by2 = zznd.zza(arrby, n4);
            n4 = n2 + 1;
            zzng.zzb(by, by2, zznd.zza(arrby, n2), zznd.zza(arrby, n4), arrc, n);
            n2 = n4 + 1;
            n = n + 1 + 1;
        }
        return new String(arrc, 0, n);
    }
}


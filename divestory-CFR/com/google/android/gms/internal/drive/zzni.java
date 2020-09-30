/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkq;
import com.google.android.gms.internal.drive.zznf;
import com.google.android.gms.internal.drive.zzng;
import com.google.android.gms.internal.drive.zznh;
import com.google.android.gms.internal.drive.zznj;

final class zzni
extends zznh {
    zzni() {
    }

    /*
     * Unable to fully structure code
     */
    @Override
    final int zzb(int var1_1, byte[] var2_2, int var3_3, int var4_4) {
        while (var3_3 < var4_4 && var2_2[var3_3] >= 0) {
            ++var3_3;
        }
        var1_1 = var3_3;
        if (var3_3 >= var4_4) {
            return 0;
        }
        do lbl-1000: // 5 sources:
        {
            block10 : {
                block9 : {
                    if (var1_1 >= var4_4) {
                        return 0;
                    }
                    var3_3 = var1_1 + 1;
                    var5_5 = var2_2[var1_1];
                    var1_1 = var3_3;
                    if (var5_5 >= 0) ** GOTO lbl-1000
                    if (var5_5 >= -32) break block9;
                    if (var3_3 >= var4_4) {
                        return var5_5;
                    }
                    if (var5_5 < -62) return -1;
                    var1_1 = var3_3 + 1;
                    if (var2_2[var3_3] <= -65) ** GOTO lbl-1000
                    return -1;
                }
                if (var5_5 >= -16) break block10;
                if (var3_3 >= var4_4 - 1) {
                    return zznf.zzh(var2_2, var3_3, var4_4);
                }
                var6_6 = var3_3 + 1;
                var1_1 = var2_2[var3_3];
                if (var1_1 > -65) return -1;
                if (var5_5 == -32) {
                    if (var1_1 < -96) return -1;
                }
                if (var5_5 == -19) {
                    if (var1_1 >= -96) return -1;
                }
                var1_1 = var6_6 + 1;
                if (var2_2[var6_6] <= -65) ** GOTO lbl-1000
                return -1;
            }
            if (var3_3 >= var4_4 - 2) {
                return zznf.zzh(var2_2, var3_3, var4_4);
            }
            var1_1 = var3_3 + 1;
            if ((var3_3 = var2_2[var3_3]) > -65) return -1;
            if ((var5_5 << 28) + (var3_3 + 112) >> 30 != 0) return -1;
            var3_3 = var1_1 + 1;
            if (var2_2[var1_1] > -65) return -1;
            var1_1 = var3_3 + 1;
        } while (var2_2[var3_3] <= -65);
        return -1;
    }

    @Override
    final int zzb(CharSequence charSequence, byte[] arrby, int n, int n2) {
        char c;
        int n3;
        int n4;
        block13 : {
            int n5;
            n3 = charSequence.length();
            int n6 = n2 + n;
            for (n2 = 0; n2 < n3 && (n5 = n2 + n) < n6 && (n4 = (int)charSequence.charAt(n2)) < 128; ++n2) {
                arrby[n5] = (byte)n4;
            }
            if (n2 == n3) {
                return n + n3;
            }
            n4 = n + n2;
            n = n2;
            while (n < n3) {
                c = charSequence.charAt(n);
                if (c < '' && n4 < n6) {
                    n2 = n4 + 1;
                    arrby[n4] = (byte)c;
                } else if (c < '\u0800' && n4 <= n6 - 2) {
                    n5 = n4 + 1;
                    arrby[n4] = (byte)(c >>> 6 | 960);
                    n2 = n5 + 1;
                    arrby[n5] = (byte)(c & 63 | 128);
                } else if ((c < '\ud800' || '\udfff' < c) && n4 <= n6 - 3) {
                    n2 = n4 + 1;
                    arrby[n4] = (byte)(c >>> 12 | 480);
                    n4 = n2 + 1;
                    arrby[n2] = (byte)(c >>> 6 & 63 | 128);
                    n2 = n4 + 1;
                    arrby[n4] = (byte)(c & 63 | 128);
                } else {
                    if (n4 > n6 - 4) break block13;
                    n2 = n + 1;
                    if (n2 == charSequence.length()) throw new zznj(n - 1, n3);
                    char c2 = charSequence.charAt(n2);
                    if (!Character.isSurrogatePair(c, c2)) {
                        n = n2;
                        throw new zznj(n - 1, n3);
                    }
                    n = Character.toCodePoint(c, c2);
                    n5 = n4 + 1;
                    arrby[n4] = (byte)(n >>> 18 | 240);
                    n4 = n5 + 1;
                    arrby[n5] = (byte)(n >>> 12 & 63 | 128);
                    n5 = n4 + 1;
                    arrby[n4] = (byte)(n >>> 6 & 63 | 128);
                    n4 = n5 + 1;
                    arrby[n5] = (byte)(n & 63 | 128);
                    n = n2;
                    n2 = n4;
                }
                ++n;
                n4 = n2;
            }
            return n4;
        }
        if ('\ud800' <= c && c <= '\udfff') {
            n2 = n + 1;
            if (n2 == charSequence.length()) throw new zznj(n, n3);
            if (!Character.isSurrogatePair(c, charSequence.charAt(n2))) {
                throw new zznj(n, n3);
            }
        }
        charSequence = new StringBuilder(37);
        ((StringBuilder)charSequence).append("Failed writing ");
        ((StringBuilder)charSequence).append(c);
        ((StringBuilder)charSequence).append(" at index ");
        ((StringBuilder)charSequence).append(n4);
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
        while (n < n3 && zzng.zzh(by = arrby[n])) {
            ++n;
            zzng.zzb(by, arrc, n2);
            ++n2;
        }
        int n4 = n2;
        n2 = n;
        n = n4;
        block1 : while (n2 < n3) {
            n4 = n2 + 1;
            byte by2 = arrby[n2];
            if (zzng.zzh(by2)) {
                n2 = n + 1;
                zzng.zzb(by2, arrc, n);
                n = n2;
                n2 = n4;
                do {
                    if (n2 >= n3 || !zzng.zzh(by = arrby[n2])) continue block1;
                    ++n2;
                    zzng.zzb(by, arrc, n);
                    ++n;
                } while (true);
            }
            if (zzng.zzi(by2)) {
                if (n4 >= n3) throw zzkq.zzdn();
                zzng.zzb(by2, arrby[n4], arrc, n);
                n2 = n4 + 1;
                ++n;
                continue;
            }
            if (zzng.zzj(by2)) {
                if (n4 >= n3 - 1) throw zzkq.zzdn();
                n2 = n4 + 1;
                zzng.zzb(by2, arrby[n4], arrby[n2], arrc, n);
                ++n2;
                ++n;
                continue;
            }
            if (n4 >= n3 - 2) throw zzkq.zzdn();
            n2 = n4 + 1;
            by = arrby[n4];
            n4 = n2 + 1;
            zzng.zzb(by2, by, arrby[n2], arrby[n4], arrc, n);
            n2 = n4 + 1;
            n = n + 1 + 1;
        }
        return new String(arrc, 0, n);
    }
}


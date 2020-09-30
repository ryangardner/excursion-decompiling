/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Preconditions;

public final class Utf8 {
    private Utf8() {
    }

    public static int encodedLength(CharSequence charSequence) {
        int n;
        int n2;
        block3 : {
            int n3;
            n2 = charSequence.length();
            for (n3 = 0; n3 < n2 && charSequence.charAt(n3) < ''; ++n3) {
            }
            int n4 = n2;
            do {
                n = n4;
                if (n3 >= n2) break block3;
                n = charSequence.charAt(n3);
                if (n >= 2048) break;
                n4 += 127 - n >>> 31;
                ++n3;
            } while (true);
            n = n4 + Utf8.encodedLengthGeneral(charSequence, n3);
        }
        if (n >= n2) {
            return n;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("UTF-8 length does not fit in int: ");
        ((StringBuilder)charSequence).append((long)n + 0x100000000L);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static int encodedLengthGeneral(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        int n3 = 0;
        while (n < n2) {
            int n4;
            char c = charSequence.charAt(n);
            if (c < '\u0800') {
                n3 += 127 - c >>> 31;
                n4 = n;
            } else {
                int n5;
                n3 = n5 = n3 + 2;
                n4 = n;
                if ('\ud800' <= c) {
                    n3 = n5;
                    n4 = n;
                    if (c <= '\udfff') {
                        if (Character.codePointAt(charSequence, n) == c) throw new IllegalArgumentException(Utf8.unpairedSurrogateMsg(n));
                        n4 = n + 1;
                        n3 = n5;
                    }
                }
            }
            n = n4 + 1;
        }
        return n3;
    }

    public static boolean isWellFormed(byte[] arrby) {
        return Utf8.isWellFormed(arrby, 0, arrby.length);
    }

    public static boolean isWellFormed(byte[] arrby, int n, int n2) {
        Preconditions.checkPositionIndexes(n, n2 += n, arrby.length);
        while (n < n2) {
            if (arrby[n] < 0) {
                return Utf8.isWellFormedSlowPath(arrby, n, n2);
            }
            ++n;
        }
        return true;
    }

    /*
     * Unable to fully structure code
     */
    private static boolean isWellFormedSlowPath(byte[] var0, int var1_1, int var2_2) {
        do lbl-1000: // 5 sources:
        {
            block8 : {
                block7 : {
                    if (var1_1 >= var2_2) {
                        return true;
                    }
                    var3_3 = var1_1 + 1;
                    var4_4 = var0[var1_1];
                    var1_1 = var3_3;
                    if (var4_4 >= 0) ** GOTO lbl-1000
                    if (var4_4 >= -32) break block7;
                    if (var3_3 == var2_2) {
                        return false;
                    }
                    if (var4_4 < -62) return false;
                    var1_1 = var3_3 + 1;
                    if (var0[var3_3] <= -65) ** GOTO lbl-1000
                    return false;
                }
                if (var4_4 >= -16) break block8;
                var5_5 = var3_3 + 1;
                if (var5_5 >= var2_2) {
                    return false;
                }
                var1_1 = var0[var3_3];
                if (var1_1 > -65) return false;
                if (var4_4 == -32) {
                    if (var1_1 < -96) return false;
                }
                if (var4_4 == -19) {
                    if (-96 <= var1_1) return false;
                }
                var1_1 = var5_5 + 1;
                if (var0[var5_5] <= -65) ** GOTO lbl-1000
                return false;
            }
            if (var3_3 + 2 >= var2_2) {
                return false;
            }
            var1_1 = var3_3 + 1;
            if ((var3_3 = var0[var3_3]) > -65) return false;
            if ((var4_4 << 28) + (var3_3 + 112) >> 30 != 0) return false;
            var3_3 = var1_1 + 1;
            if (var0[var1_1] > -65) return false;
            var1_1 = var3_3 + 1;
        } while (var0[var3_3] <= -65);
        return false;
    }

    private static String unpairedSurrogateMsg(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unpaired surrogate at index ");
        stringBuilder.append(n);
        return stringBuilder.toString();
    }
}


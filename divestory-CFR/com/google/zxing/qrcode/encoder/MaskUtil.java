/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.encoder;

import com.google.zxing.qrcode.encoder.ByteMatrix;

final class MaskUtil {
    private static final int N1 = 3;
    private static final int N2 = 3;
    private static final int N3 = 40;
    private static final int N4 = 10;

    private MaskUtil() {
    }

    static int applyMaskPenaltyRule1(ByteMatrix byteMatrix) {
        return MaskUtil.applyMaskPenaltyRule1Internal(byteMatrix, true) + MaskUtil.applyMaskPenaltyRule1Internal(byteMatrix, false);
    }

    private static int applyMaskPenaltyRule1Internal(ByteMatrix arrby, boolean bl) {
        int n = bl ? arrby.getHeight() : arrby.getWidth();
        int n2 = bl ? arrby.getWidth() : arrby.getHeight();
        arrby = arrby.getArray();
        int n3 = 0;
        int n4 = 0;
        while (n3 < n) {
            int n5;
            int n6 = -1;
            int n7 = 0;
            for (int i = 0; i < n2; ++i) {
                n5 = bl ? arrby[n3][i] : arrby[i][n3];
                if (n5 == n6) {
                    n5 = n7 + 1;
                    n7 = n6;
                } else {
                    n6 = n4;
                    if (n7 >= 5) {
                        n6 = n4 + (n7 - 5 + 3);
                    }
                    n7 = n5;
                    n5 = 1;
                    n4 = n6;
                }
                n6 = n7;
                n7 = n5;
            }
            n5 = n4;
            if (n7 >= 5) {
                n5 = n4 + (n7 - 5 + 3);
            }
            ++n3;
            n4 = n5;
        }
        return n4;
    }

    static int applyMaskPenaltyRule2(ByteMatrix arrby) {
        byte[][] arrby2 = arrby.getArray();
        int n = arrby.getWidth();
        int n2 = arrby.getHeight();
        int n3 = 0;
        int n4 = 0;
        while (n3 < n2 - 1) {
            int n5 = 0;
            while (n5 < n - 1) {
                byte by = arrby2[n3][n5];
                arrby = arrby2[n3];
                int n6 = n5 + 1;
                int n7 = n4;
                if (by == arrby[n6]) {
                    int n8 = n3 + 1;
                    n7 = n4;
                    if (by == arrby2[n8][n5]) {
                        n7 = n4;
                        if (by == arrby2[n8][n6]) {
                            n7 = n4 + 1;
                        }
                    }
                }
                n5 = n6;
                n4 = n7;
            }
            ++n3;
        }
        return n4 * 3;
    }

    static int applyMaskPenaltyRule3(ByteMatrix arrby) {
        byte[][] arrby2 = arrby.getArray();
        int n = arrby.getWidth();
        int n2 = arrby.getHeight();
        int n3 = 0;
        int n4 = 0;
        while (n3 < n2) {
            for (int i = 0; i < n; ++i) {
                int n5;
                int n6;
                block6 : {
                    block7 : {
                        arrby = arrby2[n3];
                        n5 = i + 6;
                        n6 = n4;
                        if (n5 >= n) break block6;
                        n6 = n4;
                        if (arrby[i] != 1) break block6;
                        n6 = n4;
                        if (arrby[i + 1] != 0) break block6;
                        n6 = n4;
                        if (arrby[i + 2] != 1) break block6;
                        n6 = n4;
                        if (arrby[i + 3] != 1) break block6;
                        n6 = n4;
                        if (arrby[i + 4] != 1) break block6;
                        n6 = n4;
                        if (arrby[i + 5] != 0) break block6;
                        n6 = n4;
                        if (arrby[n5] != 1) break block6;
                        if (MaskUtil.isWhiteHorizontal(arrby, i - 4, i)) break block7;
                        n6 = n4;
                        if (!MaskUtil.isWhiteHorizontal(arrby, i + 7, i + 11)) break block6;
                    }
                    n6 = n4 + 1;
                }
                n5 = n3 + 6;
                n4 = n6;
                if (n5 >= n2) continue;
                n4 = n6;
                if (arrby2[n3][i] != 1) continue;
                n4 = n6;
                if (arrby2[n3 + 1][i] != 0) continue;
                n4 = n6;
                if (arrby2[n3 + 2][i] != 1) continue;
                n4 = n6;
                if (arrby2[n3 + 3][i] != 1) continue;
                n4 = n6;
                if (arrby2[n3 + 4][i] != 1) continue;
                n4 = n6;
                if (arrby2[n3 + 5][i] != 0) continue;
                n4 = n6;
                if (arrby2[n5][i] != 1) continue;
                if (!MaskUtil.isWhiteVertical(arrby2, i, n3 - 4, n3)) {
                    n4 = n6;
                    if (!MaskUtil.isWhiteVertical(arrby2, i, n3 + 7, n3 + 11)) continue;
                }
                n4 = n6 + 1;
            }
            ++n3;
        }
        return n4 * 40;
    }

    static int applyMaskPenaltyRule4(ByteMatrix byteMatrix) {
        byte[][] arrby = byteMatrix.getArray();
        int n = byteMatrix.getWidth();
        int n2 = byteMatrix.getHeight();
        int n3 = 0;
        int n4 = 0;
        do {
            if (n3 >= n2) {
                n3 = byteMatrix.getHeight() * byteMatrix.getWidth();
                return Math.abs(n4 * 2 - n3) * 10 / n3 * 10;
            }
            byte[] arrby2 = arrby[n3];
            for (int i = 0; i < n; ++i) {
                int n5 = n4;
                if (arrby2[i] == 1) {
                    n5 = n4 + 1;
                }
                n4 = n5;
            }
            ++n3;
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    static boolean getDataMaskBit(int var0, int var1_1, int var2_2) {
        block10 : {
            var3_3 = true;
            var4_4 = var1_1;
            var5_5 = var2_2;
            var6_6 = var2_2;
            switch (var0) {
                default: {
                    var7_7 = new StringBuilder();
                    var7_7.append("Invalid mask pattern: ");
                    var7_7.append(var0);
                    throw new IllegalArgumentException(var7_7.toString());
                }
                case 7: {
                    var0 = var2_2 * var1_1 % 3 + (var2_2 + var1_1 & 1);
                    ** GOTO lbl19
                }
                case 6: {
                    var0 = var2_2 * var1_1;
                    var0 = (var0 & 1) + var0 % 3;
lbl19: // 2 sources:
                    var0 &= 1;
                    break block10;
                }
                case 5: {
                    var0 = var2_2 * var1_1;
                    var0 = (var0 & 1) + var0 % 3;
                    break block10;
                }
                case 4: {
                    var5_5 = var2_2 / 2;
                    var4_4 = var1_1 / 3;
                    ** GOTO lbl35
                }
                case 3: {
                    var0 = (var2_2 + var1_1) % 3;
                    break block10;
                }
                case 2: {
                    var0 = var1_1 % 3;
                    break block10;
                }
lbl35: // 2 sources:
                case 0: {
                    var6_6 = var5_5 + var4_4;
                }
                case 1: 
            }
            var0 = var6_6 & 1;
        }
        if (var0 != 0) return false;
        return var3_3;
    }

    private static boolean isWhiteHorizontal(byte[] arrby, int n, int n2) {
        while (n < n2) {
            if (n >= 0 && n < arrby.length && arrby[n] == 1) {
                return false;
            }
            ++n;
        }
        return true;
    }

    private static boolean isWhiteVertical(byte[][] arrby, int n, int n2, int n3) {
        while (n2 < n3) {
            if (n2 >= 0 && n2 < arrby.length && arrby[n2][n] == 1) {
                return false;
            }
            ++n2;
        }
        return true;
    }
}


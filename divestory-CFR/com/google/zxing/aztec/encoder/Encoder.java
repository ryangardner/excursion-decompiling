/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.AztecCode;
import com.google.zxing.aztec.encoder.HighLevelEncoder;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;

public final class Encoder {
    public static final int DEFAULT_AZTEC_LAYERS = 0;
    public static final int DEFAULT_EC_PERCENT = 33;
    private static final int MAX_NB_BITS = 32;
    private static final int MAX_NB_BITS_COMPACT = 4;
    private static final int[] WORD_SIZE = new int[]{4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    private Encoder() {
    }

    private static int[] bitsToWords(BitArray bitArray, int n, int n2) {
        int[] arrn = new int[n2];
        int n3 = bitArray.getSize() / n;
        n2 = 0;
        while (n2 < n3) {
            int n4;
            int n5 = 0;
            for (int i = 0; i < n; n5 |= n4, ++i) {
                n4 = bitArray.get(n2 * n + i) ? 1 << n - i - 1 : 0;
            }
            arrn[n2] = n5;
            ++n2;
        }
        return arrn;
    }

    private static void drawBullsEye(BitMatrix bitMatrix, int n, int n2) {
        int n3 = 0;
        do {
            int n4;
            int n5;
            int n6;
            if (n3 >= n2) {
                n6 = n - n2;
                bitMatrix.set(n6, n6);
                n3 = n6 + 1;
                bitMatrix.set(n3, n6);
                bitMatrix.set(n6, n3);
                bitMatrix.set(n += n2, n6);
                bitMatrix.set(n, n3);
                bitMatrix.set(n, n - 1);
                return;
            }
            for (n6 = n4 = n - n3; n6 <= (n5 = n + n3); ++n6) {
                bitMatrix.set(n6, n4);
                bitMatrix.set(n6, n5);
                bitMatrix.set(n4, n6);
                bitMatrix.set(n5, n6);
            }
            n3 += 2;
        } while (true);
    }

    private static void drawModeMessage(BitMatrix bitMatrix, boolean bl, int n, BitArray bitArray) {
        int n2 = n / 2;
        n = 0;
        int n3 = 0;
        if (bl) {
            n = n3;
            while (n < 7) {
                n3 = n2 - 3 + n;
                if (bitArray.get(n)) {
                    bitMatrix.set(n3, n2 - 5);
                }
                if (bitArray.get(n + 7)) {
                    bitMatrix.set(n2 + 5, n3);
                }
                if (bitArray.get(20 - n)) {
                    bitMatrix.set(n3, n2 + 5);
                }
                if (bitArray.get(27 - n)) {
                    bitMatrix.set(n2 - 5, n3);
                }
                ++n;
            }
            return;
        }
        while (n < 10) {
            n3 = n2 - 5 + n + n / 5;
            if (bitArray.get(n)) {
                bitMatrix.set(n3, n2 - 7);
            }
            if (bitArray.get(n + 10)) {
                bitMatrix.set(n2 + 7, n3);
            }
            if (bitArray.get(29 - n)) {
                bitMatrix.set(n3, n2 + 7);
            }
            if (bitArray.get(39 - n)) {
                bitMatrix.set(n2 - 7, n3);
            }
            ++n;
        }
    }

    public static AztecCode encode(byte[] arrby) {
        return Encoder.encode(arrby, 33, 0);
    }

    /*
     * Unable to fully structure code
     */
    public static AztecCode encode(byte[] var0, int var1_1, int var2_2) {
        block23 : {
            block22 : {
                block24 : {
                    var3_3 = new HighLevelEncoder((byte[])var0).encode();
                    var4_4 = var3_3.getSize() * var1_1 / 100 + 11;
                    var5_5 = var3_3.getSize();
                    var1_1 = 32;
                    if (var2_2 == 0) break block24;
                    var6_6 = var2_2 < 0;
                    var7_7 = Math.abs(var2_2);
                    if (var6_6) {
                        var1_1 = 4;
                    }
                    if (var7_7 > var1_1) {
                        throw new IllegalArgumentException(String.format("Illegal value %s for layers", new Object[]{var2_2}));
                    }
                    var8_8 = Encoder.totalBitsInLayer(var7_7, var6_6);
                    var5_5 = Encoder.WORD_SIZE[var7_7];
                    var9_9 = Encoder.stuffBits((BitArray)var3_3, var5_5);
                    if (var9_9.getSize() + var4_4 > var8_8 - var8_8 % var5_5) throw new IllegalArgumentException("Data to large for user specified layer");
                    var0 = var9_9;
                    var10_10 = var6_6;
                    var11_11 = var8_8;
                    var2_2 = var7_7;
                    var1_1 = var5_5;
                    if (var6_6) {
                        if (var9_9.getSize() > var5_5 * 64) throw new IllegalArgumentException("Data to large for user specified layer");
                        var0 = var9_9;
                        var10_10 = var6_6;
                        var11_11 = var8_8;
                        var2_2 = var7_7;
                        var1_1 = var5_5;
                    }
                    ** GOTO lbl57
                }
                var0 = null;
                var2_2 = 0;
                var7_7 = 0;
                while (var2_2 <= 32) {
                    block26 : {
                        block27 : {
                            block25 : {
                                var6_6 = var2_2 <= 3;
                                var11_11 = var6_6 != false ? var2_2 + 1 : var2_2;
                                var8_8 = Encoder.totalBitsInLayer(var11_11, var6_6);
                                if (var5_5 + var4_4 <= var8_8) break block25;
                                var9_9 = var0;
                                break block26;
                            }
                            var9_9 = Encoder.WORD_SIZE;
                            var1_1 = var7_7;
                            if (var7_7 != var9_9[var11_11]) {
                                var1_1 = var9_9[var11_11];
                                var0 = Encoder.stuffBits((BitArray)var3_3, var1_1);
                            }
                            if (!var6_6 || var0.getSize() <= var1_1 * 64) break block27;
                            var9_9 = var0;
                            var7_7 = var1_1;
                            break block26;
                        }
                        var9_9 = var0;
                        var7_7 = var1_1;
                        if (var0.getSize() + var4_4 > var8_8 - var8_8 % var1_1) break block26;
                        var2_2 = var11_11;
                        var11_11 = var8_8;
                        var10_10 = var6_6;
lbl57: // 2 sources:
                        var9_9 = Encoder.generateCheckWords((BitArray)var0, var11_11, var1_1);
                        var12_12 = var0.getSize() / var1_1;
                        var13_13 = Encoder.generateModeMessage(var10_10, var2_2, var12_12);
                        var1_1 = var10_10 != false ? var2_2 * 4 + 11 : var2_2 * 4 + 14;
                        var3_3 = new int[var1_1];
                        if (var10_10) {
                            for (var11_11 = 0; var11_11 < var1_1; ++var11_11) {
                                var3_3[var11_11] = var11_11;
                            }
                            var11_11 = var1_1;
                        } else {
                            var5_5 = var1_1 / 2;
                            var8_8 = var1_1 + 1 + (var5_5 - 1) / 15 * 2;
                            var4_4 = var8_8 / 2;
                            var7_7 = 0;
                            do {
                                var11_11 = var8_8;
                                if (var7_7 >= var5_5) break;
                                var11_11 = var7_7 / 15 + var7_7;
                                var3_3[var5_5 - var7_7 - 1] = var4_4 - var11_11 - 1;
                                var3_3[var5_5 + var7_7] = var11_11 + var4_4 + 1;
                                ++var7_7;
                            } while (true);
                        }
                        break block22;
                    }
                    ++var2_2;
                    var0 = var9_9;
                }
                throw new IllegalArgumentException("Data too large for an Aztec code");
            }
            var0 = new BitMatrix(var11_11);
            var7_7 = 0;
            var8_8 = 0;
            do {
                if (var7_7 < var2_2) {
                    var5_5 = (var2_2 - var7_7) * 4;
                    var5_5 = var10_10 ? (var5_5 += 9) : (var5_5 += 12);
                } else {
                    Encoder.drawModeMessage((BitMatrix)var0, var10_10, var11_11, var13_13);
                    if (var10_10) {
                        Encoder.drawBullsEye((BitMatrix)var0, var11_11 / 2, 5);
                        break block23;
                    }
                    var4_4 = var11_11 / 2;
                    Encoder.drawBullsEye((BitMatrix)var0, var4_4, 7);
                    var7_7 = 0;
                    break;
                }
                var4_4 = 0;
                do {
                    if (var4_4 >= var5_5) break;
                    var15_15 = var4_4 * 2;
                    for (var14_14 = 0; var14_14 < 2; ++var14_14) {
                        if (var9_9.get(var8_8 + var15_15 + var14_14)) {
                            var16_16 = var7_7 * 2;
                            var0.set(var3_3[var16_16 + var14_14], var3_3[var16_16 + var4_4]);
                        }
                        if (var9_9.get(var5_5 * 2 + var8_8 + var15_15 + var14_14)) {
                            var16_16 = var7_7 * 2;
                            var0.set(var3_3[var16_16 + var4_4], var3_3[var1_1 - 1 - var16_16 - var14_14]);
                        }
                        if (var9_9.get(var5_5 * 4 + var8_8 + var15_15 + var14_14)) {
                            var16_16 = var1_1 - 1 - var7_7 * 2;
                            var0.set(var3_3[var16_16 - var14_14], var3_3[var16_16 - var4_4]);
                        }
                        if (!var9_9.get(var5_5 * 6 + var8_8 + var15_15 + var14_14)) continue;
                        var16_16 = var7_7 * 2;
                        var0.set(var3_3[var1_1 - 1 - var16_16 - var4_4], var3_3[var16_16 + var14_14]);
                    }
                    ++var4_4;
                } while (true);
                var8_8 += var5_5 * 8;
                ++var7_7;
            } while (true);
            for (var8_8 = 0; var8_8 < var1_1 / 2 - 1; var8_8 += 15, var7_7 += 16) {
                for (var5_5 = var4_4 & 1; var5_5 < var11_11; var5_5 += 2) {
                    var14_14 = var4_4 - var7_7;
                    var0.set(var14_14, var5_5);
                    var15_15 = var4_4 + var7_7;
                    var0.set(var15_15, var5_5);
                    var0.set(var5_5, var14_14);
                    var0.set(var5_5, var15_15);
                }
            }
        }
        var9_9 = new AztecCode();
        var9_9.setCompact(var10_10);
        var9_9.setSize(var11_11);
        var9_9.setLayers(var2_2);
        var9_9.setCodeWords(var12_12);
        var9_9.setMatrix((BitMatrix)var0);
        return var9_9;
    }

    private static BitArray generateCheckWords(BitArray arrn, int n, int n2) {
        int n3 = arrn.getSize() / n2;
        Object object = new ReedSolomonEncoder(Encoder.getGF(n2));
        int n4 = n / n2;
        arrn = Encoder.bitsToWords((BitArray)arrn, n2, n4);
        ((ReedSolomonEncoder)object).encode(arrn, n4 - n3);
        object = new BitArray();
        n3 = 0;
        ((BitArray)object).appendBits(0, n % n2);
        n4 = arrn.length;
        n = n3;
        while (n < n4) {
            ((BitArray)object).appendBits(arrn[n], n2);
            ++n;
        }
        return object;
    }

    static BitArray generateModeMessage(boolean bl, int n, int n2) {
        BitArray bitArray = new BitArray();
        if (bl) {
            bitArray.appendBits(n - 1, 2);
            bitArray.appendBits(n2 - 1, 6);
            return Encoder.generateCheckWords(bitArray, 28, 4);
        }
        bitArray.appendBits(n - 1, 5);
        bitArray.appendBits(n2 - 1, 11);
        return Encoder.generateCheckWords(bitArray, 40, 4);
    }

    private static GenericGF getGF(int n) {
        if (n == 4) return GenericGF.AZTEC_PARAM;
        if (n == 6) return GenericGF.AZTEC_DATA_6;
        if (n == 8) return GenericGF.AZTEC_DATA_8;
        if (n == 10) return GenericGF.AZTEC_DATA_10;
        if (n == 12) {
            return GenericGF.AZTEC_DATA_12;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported word size ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * Unable to fully structure code
     */
    static BitArray stuffBits(BitArray var0, int var1_1) {
        var2_2 = new BitArray();
        var3_3 = var0.getSize();
        var4_4 = (1 << var1_1) - 2;
        var5_5 = 0;
        while (var5_5 < var3_3) {
            block9 : {
                var7_7 = 0;
                for (var6_6 = 0; var6_6 < var1_1; ++var6_6) {
                    block8 : {
                        block7 : {
                            var8_8 = var5_5 + var6_6;
                            if (var8_8 >= var3_3) break block7;
                            var9_9 = var7_7;
                            if (!var0.get(var8_8)) break block8;
                        }
                        var9_9 = var7_7 | 1 << var1_1 - 1 - var6_6;
                    }
                    var7_7 = var9_9;
                }
                var9_9 = var7_7 & var4_4;
                if (var9_9 != var4_4) break block9;
                var2_2.appendBits(var9_9, var1_1);
                ** GOTO lbl24
            }
            if (var9_9 == 0) {
                var2_2.appendBits(var7_7 | 1, var1_1);
lbl24: // 2 sources:
                --var5_5;
            } else {
                var2_2.appendBits(var7_7, var1_1);
            }
            var5_5 += var1_1;
        }
        return var2_2;
    }

    private static int totalBitsInLayer(int n, boolean bl) {
        int n2;
        if (bl) {
            n2 = 88;
            return (n2 + n * 16) * n;
        }
        n2 = 112;
        return (n2 + n * 16) * n;
    }
}


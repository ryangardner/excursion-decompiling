/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.decoder.Version;

final class BitMatrixParser {
    private final BitMatrix mappingBitMatrix;
    private final BitMatrix readMappingMatrix;
    private final Version version;

    BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int n = bitMatrix.getHeight();
        if (n < 8) throw FormatException.getFormatInstance();
        if (n > 144) throw FormatException.getFormatInstance();
        if ((n & 1) != 0) throw FormatException.getFormatInstance();
        this.version = BitMatrixParser.readVersion(bitMatrix);
        this.mappingBitMatrix = this.extractDataRegion(bitMatrix);
        this.readMappingMatrix = new BitMatrix(this.mappingBitMatrix.getWidth(), this.mappingBitMatrix.getHeight());
    }

    private static Version readVersion(BitMatrix bitMatrix) throws FormatException {
        return Version.getVersionForDimensions(bitMatrix.getHeight(), bitMatrix.getWidth());
    }

    BitMatrix extractDataRegion(BitMatrix bitMatrix) {
        int n = this.version.getSymbolSizeRows();
        int n2 = this.version.getSymbolSizeColumns();
        if (bitMatrix.getHeight() != n) throw new IllegalArgumentException("Dimension of bitMarix must match the version size");
        int n3 = this.version.getDataRegionSizeRows();
        int n4 = this.version.getDataRegionSizeColumns();
        int n5 = n / n3;
        int n6 = n2 / n4;
        BitMatrix bitMatrix2 = new BitMatrix(n6 * n4, n5 * n3);
        n2 = 0;
        block0 : while (n2 < n5) {
            n = 0;
            do {
                if (n < n6) {
                } else {
                    ++n2;
                    continue block0;
                }
                for (int i = 0; i < n3; ++i) {
                    for (int j = 0; j < n4; ++j) {
                        if (!bitMatrix.get((n4 + 2) * n + 1 + j, (n3 + 2) * n2 + 1 + i)) continue;
                        bitMatrix2.set(n * n4 + j, n2 * n3 + i);
                    }
                }
                ++n;
            } while (true);
            break;
        }
        return bitMatrix2;
    }

    Version getVersion() {
        return this.version;
    }

    /*
     * Unable to fully structure code
     */
    byte[] readCodewords() throws FormatException {
        var1_1 = new byte[this.version.getTotalCodewords()];
        var2_2 = this.mappingBitMatrix.getHeight();
        var3_3 = this.mappingBitMatrix.getWidth();
        var4_4 = 0;
        var5_5 = 0;
        var6_6 = 0;
        var7_7 = false;
        var8_8 = false;
        var9_9 = false;
        var10_10 = 4;
        do lbl-1000: // 3 sources:
        {
            block12 : {
                block14 : {
                    block13 : {
                        block11 : {
                            if (var10_10 != var2_2 || var4_4 != 0 || var5_5 != 0) break block11;
                            var1_1[var6_6] = (byte)this.readCorner1(var2_2, var3_3);
                            var11_11 = var10_10 - 2;
                            var12_12 = var4_4 + 2;
                            var13_13 = var6_6 + 1;
                            var14_14 = 1;
                            var15_15 = var7_7;
                            var16_16 = var8_8;
                            var17_17 = var9_9;
                            break block12;
                        }
                        var12_12 = var2_2 - 2;
                        if (var10_10 != var12_12 || var4_4 != 0 || (var3_3 & 3) == 0 || var7_7) break block13;
                        var1_1[var6_6] = (byte)this.readCorner2(var2_2, var3_3);
                        var11_11 = var10_10 - 2;
                        var12_12 = var4_4 + 2;
                        var13_13 = var6_6 + 1;
                        var15_15 = true;
                        var14_14 = var5_5;
                        var16_16 = var8_8;
                        var17_17 = var9_9;
                        break block12;
                    }
                    if (var10_10 != var2_2 + 4 || var4_4 != 2 || (var3_3 & 7) != 0 || var8_8) break block14;
                    var1_1[var6_6] = (byte)this.readCorner3(var2_2, var3_3);
                    var11_11 = var10_10 - 2;
                    var12_12 = var4_4 + 2;
                    var13_13 = var6_6 + 1;
                    var16_16 = true;
                    var14_14 = var5_5;
                    var15_15 = var7_7;
                    var17_17 = var9_9;
                    break block12;
                }
                var13_13 = var4_4;
                var14_14 = var6_6;
                var11_11 = var10_10;
                if (var10_10 != var12_12) ** GOTO lbl-1000
                var13_13 = var4_4;
                var14_14 = var6_6;
                var11_11 = var10_10;
                if (var4_4 != 0) ** GOTO lbl-1000
                var13_13 = var4_4;
                var14_14 = var6_6;
                var11_11 = var10_10;
                if ((var3_3 & 7) != 4) ** GOTO lbl-1000
                var13_13 = var4_4;
                var14_14 = var6_6;
                var11_11 = var10_10;
                if (!var9_9) {
                    var1_1[var6_6] = (byte)this.readCorner4(var2_2, var3_3);
                    var11_11 = var10_10 - 2;
                    var12_12 = var4_4 + 2;
                    var13_13 = var6_6 + 1;
                    var17_17 = true;
                    var14_14 = var5_5;
                    var15_15 = var7_7;
                    var16_16 = var8_8;
                } else lbl-1000: // 4 sources:
                {
                    do {
                        var12_12 = var14_14;
                        if (var11_11 < var2_2) {
                            var12_12 = var14_14;
                            if (var13_13 >= 0) {
                                var12_12 = var14_14;
                                if (!this.readMappingMatrix.get(var13_13, var11_11)) {
                                    var1_1[var14_14] = (byte)this.readUtah(var11_11, var13_13, var2_2, var3_3);
                                    var12_12 = var14_14 + 1;
                                }
                            }
                        }
                        var10_10 = var11_11 - 2;
                        var6_6 = var13_13 + 2;
                        if (var10_10 < 0) break;
                        var13_13 = var6_6;
                        var14_14 = var12_12;
                        var11_11 = var10_10;
                    } while (var6_6 < var3_3);
                    var11_11 = var10_10 + 1;
                    var6_6 += 3;
                    do {
                        var13_13 = var12_12;
                        if (var11_11 >= 0) {
                            var13_13 = var12_12;
                            if (var6_6 < var3_3) {
                                var13_13 = var12_12;
                                if (!this.readMappingMatrix.get(var6_6, var11_11)) {
                                    var1_1[var12_12] = (byte)this.readUtah(var11_11, var6_6, var2_2, var3_3);
                                    var13_13 = var12_12 + 1;
                                }
                            }
                        }
                        var4_4 = var11_11 + 2;
                        var10_10 = var6_6 - 2;
                        if (var4_4 >= var2_2) break;
                        var6_6 = var10_10;
                        var12_12 = var13_13;
                        var11_11 = var4_4;
                    } while (var10_10 >= 0);
                    var11_11 = var4_4 + 3;
                    var12_12 = var10_10 + 1;
                    var17_17 = var9_9;
                    var16_16 = var8_8;
                    var15_15 = var7_7;
                    var14_14 = var5_5;
                }
            }
            var4_4 = var12_12;
            var5_5 = var14_14;
            var6_6 = var13_13;
            var7_7 = var15_15;
            var8_8 = var16_16;
            var9_9 = var17_17;
            var10_10 = var11_11;
            if (var11_11 < var2_2) ** GOTO lbl-1000
            var4_4 = var12_12;
            var5_5 = var14_14;
            var6_6 = var13_13;
            var7_7 = var15_15;
            var8_8 = var16_16;
            var9_9 = var17_17;
            var10_10 = var11_11;
        } while (var12_12 < var3_3);
        if (var13_13 != this.version.getTotalCodewords()) throw FormatException.getFormatInstance();
        return var1_1;
    }

    int readCorner1(int n, int n2) {
        int n3;
        int n4 = n - 1;
        int n5 = this.readModule(n4, 0, n, n2) ? 1 : 0;
        n5 = n3 = n5 << 1;
        if (this.readModule(n4, 1, n, n2)) {
            n5 = n3 | 1;
        }
        n5 = n3 = n5 << 1;
        if (this.readModule(n4, 2, n, n2)) {
            n5 = n3 | 1;
        }
        n5 = n3 = n5 << 1;
        if (this.readModule(0, n2 - 2, n, n2)) {
            n5 = n3 | 1;
        }
        n3 = n5 << 1;
        n4 = n2 - 1;
        n5 = n3;
        if (this.readModule(0, n4, n, n2)) {
            n5 = n3 | 1;
        }
        n5 = n3 = n5 << 1;
        if (this.readModule(1, n4, n, n2)) {
            n5 = n3 | 1;
        }
        n5 = n3 = n5 << 1;
        if (this.readModule(2, n4, n, n2)) {
            n5 = n3 | 1;
        }
        n5 = n3 = n5 << 1;
        if (!this.readModule(3, n4, n, n2)) return n5;
        return n3 | 1;
    }

    int readCorner2(int n, int n2) {
        int n3;
        int n4 = this.readModule(n - 3, 0, n, n2) ? 1 : 0;
        n4 = n3 = n4 << 1;
        if (this.readModule(n - 2, 0, n, n2)) {
            n4 = n3 | 1;
        }
        n4 = n3 = n4 << 1;
        if (this.readModule(n - 1, 0, n, n2)) {
            n4 = n3 | 1;
        }
        n4 = n3 = n4 << 1;
        if (this.readModule(0, n2 - 4, n, n2)) {
            n4 = n3 | 1;
        }
        n4 = n3 = n4 << 1;
        if (this.readModule(0, n2 - 3, n, n2)) {
            n4 = n3 | 1;
        }
        n4 = n3 = n4 << 1;
        if (this.readModule(0, n2 - 2, n, n2)) {
            n4 = n3 | 1;
        }
        n3 = n4 << 1;
        int n5 = n2 - 1;
        n4 = n3;
        if (this.readModule(0, n5, n, n2)) {
            n4 = n3 | 1;
        }
        n4 = n3 = n4 << 1;
        if (!this.readModule(1, n5, n, n2)) return n4;
        return n3 | 1;
    }

    int readCorner3(int n, int n2) {
        int n3 = n - 1;
        int n4 = this.readModule(n3, 0, n, n2) ? 1 : 0;
        int n5 = n4 << 1;
        int n6 = n2 - 1;
        n4 = n5;
        if (this.readModule(n3, n6, n, n2)) {
            n4 = n5 | 1;
        }
        n5 = n4 << 1;
        int n7 = n2 - 3;
        n4 = n5;
        if (this.readModule(0, n7, n, n2)) {
            n4 = n5 | 1;
        }
        n5 = n4 << 1;
        n3 = n2 - 2;
        n4 = n5;
        if (this.readModule(0, n3, n, n2)) {
            n4 = n5 | 1;
        }
        n4 = n5 = n4 << 1;
        if (this.readModule(0, n6, n, n2)) {
            n4 = n5 | 1;
        }
        n4 = n5 = n4 << 1;
        if (this.readModule(1, n7, n, n2)) {
            n4 = n5 | 1;
        }
        n4 = n5 = n4 << 1;
        if (this.readModule(1, n3, n, n2)) {
            n4 = n5 | 1;
        }
        n4 = n5 = n4 << 1;
        if (!this.readModule(1, n6, n, n2)) return n4;
        return n5 | 1;
    }

    int readCorner4(int n, int n2) {
        int n3;
        int n4 = this.readModule(n - 3, 0, n, n2) ? 1 : 0;
        n4 = n3 = n4 << 1;
        if (this.readModule(n - 2, 0, n, n2)) {
            n4 = n3 | 1;
        }
        n4 = n3 = n4 << 1;
        if (this.readModule(n - 1, 0, n, n2)) {
            n4 = n3 | 1;
        }
        n4 = n3 = n4 << 1;
        if (this.readModule(0, n2 - 2, n, n2)) {
            n4 = n3 | 1;
        }
        n3 = n4 << 1;
        int n5 = n2 - 1;
        n4 = n3;
        if (this.readModule(0, n5, n, n2)) {
            n4 = n3 | 1;
        }
        n4 = n3 = n4 << 1;
        if (this.readModule(1, n5, n, n2)) {
            n4 = n3 | 1;
        }
        n4 = n3 = n4 << 1;
        if (this.readModule(2, n5, n, n2)) {
            n4 = n3 | 1;
        }
        n4 = n3 = n4 << 1;
        if (!this.readModule(3, n5, n, n2)) return n4;
        return n3 | 1;
    }

    boolean readModule(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        if (n < 0) {
            n5 = n + n3;
            n6 = n2 + (4 - (n3 + 4 & 7));
        }
        n2 = n5;
        n = n6;
        if (n6 < 0) {
            n = n6 + n4;
            n2 = n5 + (4 - (n4 + 4 & 7));
        }
        this.readMappingMatrix.set(n, n2);
        return this.mappingBitMatrix.get(n, n2);
    }

    int readUtah(int n, int n2, int n3, int n4) {
        int n5 = n - 2;
        int n6 = n2 - 2;
        int n7 = this.readModule(n5, n6, n3, n4) ? 1 : 0;
        int n8 = n7 << 1;
        int n9 = n2 - 1;
        n7 = n8;
        if (this.readModule(n5, n9, n3, n4)) {
            n7 = n8 | 1;
        }
        n8 = n7 << 1;
        n5 = n - 1;
        n7 = n8;
        if (this.readModule(n5, n6, n3, n4)) {
            n7 = n8 | 1;
        }
        n7 = n8 = n7 << 1;
        if (this.readModule(n5, n9, n3, n4)) {
            n7 = n8 | 1;
        }
        n7 = n8 = n7 << 1;
        if (this.readModule(n5, n2, n3, n4)) {
            n7 = n8 | 1;
        }
        n7 = n8 = n7 << 1;
        if (this.readModule(n, n6, n3, n4)) {
            n7 = n8 | 1;
        }
        n7 = n8 = n7 << 1;
        if (this.readModule(n, n9, n3, n4)) {
            n7 = n8 | 1;
        }
        n7 = n8 = n7 << 1;
        if (!this.readModule(n, n2, n3, n4)) return n7;
        return n8 | 1;
    }
}


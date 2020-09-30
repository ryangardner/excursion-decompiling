/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;

public final class HybridBinarizer
extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int[][] calculateBlackPoints(byte[] arrby, int n, int n2, int n3, int n4) {
        int[][] arrn = new int[n2][n];
        int n5 = 0;
        block0 : while (n5 < n2) {
            int n6 = n5 << 3;
            int n7 = n4 - 8;
            int n8 = n6;
            if (n6 > n7) {
                n8 = n7;
            }
            int n9 = 0;
            do {
                int n10;
                int n11;
                int n12;
                int n13;
                if (n9 < n) {
                    n7 = n9 << 3;
                    n13 = n3 - 8;
                    n6 = n7;
                    if (n7 > n13) {
                        n6 = n13;
                    }
                    n6 = n8 * n3 + n6;
                    n7 = 0;
                    n13 = 0;
                    n10 = 0;
                    n12 = 255;
                } else {
                    ++n5;
                    continue block0;
                }
                while (n7 < 8) {
                    int n14;
                    int n15;
                    for (n14 = 0; n14 < 8; ++n14) {
                        n15 = arrby[n6 + n14] & 255;
                        n13 += n15;
                        n11 = n12;
                        if (n15 < n12) {
                            n11 = n15;
                        }
                        n12 = n10;
                        if (n15 > n10) {
                            n12 = n15;
                        }
                        n10 = n12;
                        n12 = n11;
                    }
                    n14 = n6;
                    n15 = n7;
                    n11 = n13;
                    if (n10 - n12 > 24) {
                        n15 = n7;
                        int n16 = n6;
                        block4 : do {
                            n6 = n15 + 1;
                            n14 = n7 = n16 + n3;
                            n15 = n6;
                            n11 = n13;
                            if (n6 >= 8) break;
                            n11 = 0;
                            n14 = n13;
                            do {
                                n16 = n7;
                                n15 = n6;
                                n13 = n14;
                                if (n11 >= 8) continue block4;
                                n14 += arrby[n7 + n11] & 255;
                                ++n11;
                            } while (true);
                            break;
                        } while (true);
                    }
                    n7 = n15 + 1;
                    n6 = n14 + n3;
                    n13 = n11;
                }
                n6 = n13 >> 6;
                if (n10 - n12 <= 24) {
                    n6 = n7 = n12 / 2;
                    if (n5 > 0) {
                        n6 = n7;
                        if (n9 > 0) {
                            n13 = n5 - 1;
                            n11 = arrn[n13][n9];
                            int[] arrn2 = arrn[n5];
                            n6 = n9 - 1;
                            n13 = (n11 + arrn2[n6] * 2 + arrn[n13][n6]) / 4;
                            n6 = n7;
                            if (n12 < n13) {
                                n6 = n13;
                            }
                        }
                    }
                }
                arrn[n5][n9] = n6;
                ++n9;
            } while (true);
            break;
        }
        return arrn;
    }

    private static void calculateThresholdForBlock(byte[] arrby, int n, int n2, int n3, int n4, int[][] arrn, BitMatrix bitMatrix) {
        int n5 = 0;
        while (n5 < n2) {
            int n6 = n5 << 3;
            int n7 = n4 - 8;
            int n8 = n6;
            if (n6 > n7) {
                n8 = n7;
            }
            for (n7 = 0; n7 < n; ++n7) {
                int[] arrn2;
                int n9 = n7 << 3;
                n6 = n3 - 8;
                if (n9 <= n6) {
                    n6 = n9;
                }
                int n10 = HybridBinarizer.cap(n7, 2, n - 3);
                int n11 = HybridBinarizer.cap(n5, 2, n2 - 3);
                int n12 = 0;
                for (n9 = -2; n9 <= 2; n12 += arrn2[n10 - 2] + arrn2[n10 - 1] + arrn2[n10] + arrn2[n10 + 1] + arrn2[n10 + 2], ++n9) {
                    arrn2 = arrn[n11 + n9];
                }
                HybridBinarizer.thresholdBlock(arrby, n6, n8, n12 / 25, n3, bitMatrix);
            }
            ++n5;
        }
    }

    private static int cap(int n, int n2, int n3) {
        if (n < n2) {
            return n2;
        }
        n2 = n;
        if (n <= n3) return n2;
        return n3;
    }

    private static void thresholdBlock(byte[] arrby, int n, int n2, int n3, int n4, BitMatrix bitMatrix) {
        int n5 = n2 * n4 + n;
        int n6 = 0;
        while (n6 < 8) {
            for (int i = 0; i < 8; ++i) {
                if ((arrby[n5 + i] & 255) > n3) continue;
                bitMatrix.set(n + i, n2 + n6);
            }
            ++n6;
            n5 += n4;
        }
    }

    @Override
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    @Override
    public BitMatrix getBlackMatrix() throws NotFoundException {
        Object object = this.matrix;
        if (object != null) {
            return object;
        }
        object = this.getLuminanceSource();
        int n = object.getWidth();
        int n2 = object.getHeight();
        if (n >= 40 && n2 >= 40) {
            int n3;
            int n4;
            byte[] arrby = object.getMatrix();
            int n5 = n4 = n >> 3;
            if ((n & 7) != 0) {
                n5 = n4 + 1;
            }
            n4 = n3 = n2 >> 3;
            if ((n2 & 7) != 0) {
                n4 = n3 + 1;
            }
            object = HybridBinarizer.calculateBlackPoints(arrby, n5, n4, n, n2);
            BitMatrix bitMatrix = new BitMatrix(n, n2);
            HybridBinarizer.calculateThresholdForBlock(arrby, n5, n4, n, n2, object, bitMatrix);
            this.matrix = bitMatrix;
            return this.matrix;
        }
        this.matrix = super.getBlackMatrix();
        return this.matrix;
    }
}


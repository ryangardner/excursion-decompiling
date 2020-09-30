/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;

final class PDF417CodewordDecoder {
    private static final float[][] RATIOS_TABLE = new float[PDF417Common.SYMBOL_TABLE.length][8];

    static {
        int n = 0;
        while (n < PDF417Common.SYMBOL_TABLE.length) {
            int n2 = PDF417Common.SYMBOL_TABLE[n];
            int n3 = n2 & 1;
            for (int i = 0; i < 8; ++i) {
                int n4;
                float f = 0.0f;
                while ((n4 = n2 & 1) == n3) {
                    f += 1.0f;
                    n2 >>= 1;
                }
                PDF417CodewordDecoder.RATIOS_TABLE[n][8 - i - 1] = f / 17.0f;
                n3 = n4;
            }
            ++n;
        }
    }

    private PDF417CodewordDecoder() {
    }

    private static int getBitValue(int[] arrn) {
        long l = 0L;
        int n = 0;
        while (n < arrn.length) {
            for (int i = 0; i < arrn[n]; ++i) {
                int n2 = 1;
                if (n % 2 != 0) {
                    n2 = 0;
                }
                l = l << 1 | (long)n2;
            }
            ++n;
        }
        return (int)l;
    }

    private static int getClosestDecodedValue(int[] object) {
        int n;
        int n2 = PDF417Common.getBitCountSum(object);
        float[] arrf = new float[8];
        for (n = 0; n < 8; ++n) {
            arrf[n] = (float)object[n] / (float)n2;
        }
        float f = Float.MAX_VALUE;
        n2 = -1;
        n = 0;
        while (n < ((int[])(object = RATIOS_TABLE)).length) {
            float f2;
            float f3 = 0.0f;
            object = object[n];
            int n3 = 0;
            do {
                f2 = f3;
                if (n3 >= 8) break;
                f2 = object[n3] - arrf[n3];
                if ((f2 = f3 + f2 * f2) >= f) break;
                ++n3;
                f3 = f2;
            } while (true);
            f3 = f;
            if (f2 < f) {
                n2 = PDF417Common.SYMBOL_TABLE[n];
                f3 = f2;
            }
            ++n;
            f = f3;
        }
        return n2;
    }

    private static int getDecodedCodewordValue(int[] arrn) {
        int n;
        int n2 = n = PDF417CodewordDecoder.getBitValue(arrn);
        if (PDF417Common.getCodeword(n) != -1) return n2;
        return -1;
    }

    static int getDecodedValue(int[] arrn) {
        int n = PDF417CodewordDecoder.getDecodedCodewordValue(PDF417CodewordDecoder.sampleBitCounts(arrn));
        if (n == -1) return PDF417CodewordDecoder.getClosestDecodedValue(arrn);
        return n;
    }

    private static int[] sampleBitCounts(int[] arrn) {
        float f = PDF417Common.getBitCountSum(arrn);
        int[] arrn2 = new int[8];
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        while (n < 17) {
            float f2 = f / 34.0f;
            float f3 = (float)n * f / 17.0f;
            int n4 = n2;
            int n5 = n3;
            if ((float)(arrn[n3] + n2) <= f2 + f3) {
                n4 = n2 + arrn[n3];
                n5 = n3 + 1;
            }
            arrn2[n5] = arrn2[n5] + 1;
            ++n;
            n2 = n4;
            n3 = n5;
        }
        return arrn2;
    }
}


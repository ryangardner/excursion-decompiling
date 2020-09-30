/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

final class FormatInformation {
    private static final int[] BITS_SET_IN_HALF_BYTE;
    private static final int[][] FORMAT_INFO_DECODE_LOOKUP;
    private static final int FORMAT_INFO_MASK_QR = 21522;
    private final byte dataMask;
    private final ErrorCorrectionLevel errorCorrectionLevel;

    static {
        int[] arrn = new int[]{24188, 2};
        int[] arrn2 = new int[]{23371, 3};
        int[] arrn3 = new int[]{17913, 4};
        int[] arrn4 = new int[]{16590, 5};
        int[] arrn5 = new int[]{19104, 7};
        int[] arrn6 = new int[]{25368, 13};
        int[] arrn7 = new int[]{27713, 14};
        int[] arrn8 = new int[]{26998, 15};
        int[] arrn9 = new int[]{5769, 16};
        int[] arrn10 = new int[]{5054, 17};
        int[] arrn11 = new int[]{3340, 22};
        int[] arrn12 = new int[]{13663, 24};
        int[] arrn13 = new int[]{8579, 29};
        int[] arrn14 = new int[]{11245, 31};
        FORMAT_INFO_DECODE_LOOKUP = new int[][]{{21522, 0}, {20773, 1}, arrn, arrn2, arrn3, arrn4, {20375, 6}, arrn5, {30660, 8}, {29427, 9}, {32170, 10}, {30877, 11}, {26159, 12}, arrn6, arrn7, arrn8, arrn9, arrn10, {7399, 18}, {6608, 19}, {1890, 20}, {597, 21}, arrn11, {2107, 23}, arrn12, {12392, 25}, {16177, 26}, {14854, 27}, {9396, 28}, arrn13, {11994, 30}, arrn14};
        BITS_SET_IN_HALF_BYTE = new int[]{0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4};
    }

    private FormatInformation(int n) {
        this.errorCorrectionLevel = ErrorCorrectionLevel.forBits(n >> 3 & 3);
        this.dataMask = (byte)(n & 7);
    }

    static FormatInformation decodeFormatInformation(int n, int n2) {
        FormatInformation formatInformation = FormatInformation.doDecodeFormatInformation(n, n2);
        if (formatInformation == null) return FormatInformation.doDecodeFormatInformation(n ^ 21522, n2 ^ 21522);
        return formatInformation;
    }

    private static FormatInformation doDecodeFormatInformation(int n, int n2) {
        int[][] arrn = FORMAT_INFO_DECODE_LOOKUP;
        int n3 = arrn.length;
        int n4 = Integer.MAX_VALUE;
        int n5 = 0;
        int n6 = 0;
        do {
            if (n5 >= n3) {
                if (n4 > 3) return null;
                return new FormatInformation(n6);
            }
            int[] arrn2 = arrn[n5];
            int n7 = arrn2[0];
            if (n7 == n) return new FormatInformation(arrn2[1]);
            if (n7 == n2) {
                return new FormatInformation(arrn2[1]);
            }
            int n8 = FormatInformation.numBitsDiffering(n, n7);
            int n9 = n4;
            int n10 = n6;
            if (n8 < n4) {
                n10 = arrn2[1];
                n9 = n8;
            }
            n4 = n9;
            n6 = n10;
            if (n != n2) {
                n8 = FormatInformation.numBitsDiffering(n2, n7);
                n4 = n9;
                n6 = n10;
                if (n8 < n9) {
                    n6 = arrn2[1];
                    n4 = n8;
                }
            }
            ++n5;
        } while (true);
    }

    static int numBitsDiffering(int n, int n2) {
        int[] arrn = BITS_SET_IN_HALF_BYTE;
        return arrn[(n ^= n2) & 15] + arrn[n >>> 4 & 15] + arrn[n >>> 8 & 15] + arrn[n >>> 12 & 15] + arrn[n >>> 16 & 15] + arrn[n >>> 20 & 15] + arrn[n >>> 24 & 15] + arrn[n >>> 28 & 15];
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof FormatInformation;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (FormatInformation)object;
        bl = bl2;
        if (this.errorCorrectionLevel != ((FormatInformation)object).errorCorrectionLevel) return bl;
        bl = bl2;
        if (this.dataMask != ((FormatInformation)object).dataMask) return bl;
        return true;
    }

    byte getDataMask() {
        return this.dataMask;
    }

    ErrorCorrectionLevel getErrorCorrectionLevel() {
        return this.errorCorrectionLevel;
    }

    public int hashCode() {
        return this.errorCorrectionLevel.ordinal() << 3 | this.dataMask;
    }
}


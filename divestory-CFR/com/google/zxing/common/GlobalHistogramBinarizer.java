/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

public class GlobalHistogramBinarizer
extends Binarizer {
    private static final byte[] EMPTY = new byte[0];
    private static final int LUMINANCE_BITS = 5;
    private static final int LUMINANCE_BUCKETS = 32;
    private static final int LUMINANCE_SHIFT = 3;
    private final int[] buckets = new int[32];
    private byte[] luminances = EMPTY;

    public GlobalHistogramBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int estimateBlackPoint(int[] arrn) throws NotFoundException {
        int n;
        int n2;
        int n3;
        int n4 = arrn.length;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        for (n2 = 0; n2 < n4; ++n2) {
            n = n6;
            if (arrn[n2] > n6) {
                n = arrn[n2];
                n8 = n2;
            }
            n3 = n7;
            if (arrn[n2] > n7) {
                n3 = arrn[n2];
            }
            n6 = n;
            n7 = n3;
        }
        n2 = 0;
        n6 = 0;
        for (n = n5; n < n4; ++n) {
            n3 = n - n8;
            n5 = arrn[n] * n3 * n3;
            n3 = n6;
            if (n5 > n6) {
                n2 = n;
                n3 = n5;
            }
            n6 = n3;
        }
        if (n8 > n2) {
            n = n2;
            n2 = n8;
        } else {
            n = n8;
        }
        if (n2 - n <= n4 / 16) throw NotFoundException.getNotFoundInstance();
        n5 = n8 = n2 - 1;
        n6 = -1;
        while (n8 > n) {
            n3 = n8 - n;
            n4 = n3 * n3 * (n2 - n8) * (n7 - arrn[n8]);
            n3 = n6;
            if (n4 > n6) {
                n5 = n8;
                n3 = n4;
            }
            --n8;
            n6 = n3;
        }
        return n5 << 3;
    }

    private void initArrays(int n) {
        if (this.luminances.length < n) {
            this.luminances = new byte[n];
        }
        n = 0;
        while (n < 32) {
            this.buckets[n] = 0;
            ++n;
        }
    }

    @Override
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new GlobalHistogramBinarizer(luminanceSource);
    }

    @Override
    public BitMatrix getBlackMatrix() throws NotFoundException {
        int n;
        int n2;
        int n3;
        byte[] arrby = this.getLuminanceSource();
        int n4 = arrby.getWidth();
        int n5 = arrby.getHeight();
        BitMatrix bitMatrix = new BitMatrix(n4, n5);
        this.initArrays(n4);
        int[] arrn = this.buckets;
        for (n2 = 1; n2 < 5; ++n2) {
            byte[] arrby2 = arrby.getRow(n5 * n2 / 5, this.luminances);
            n3 = n4 * 4 / 5;
            for (n = n4 / 5; n < n3; ++n) {
                int n6 = (arrby2[n] & 255) >> 3;
                arrn[n6] = arrn[n6] + 1;
            }
        }
        n3 = GlobalHistogramBinarizer.estimateBlackPoint(arrn);
        arrby = arrby.getMatrix();
        n2 = 0;
        while (n2 < n5) {
            for (n = 0; n < n4; ++n) {
                if ((arrby[n2 * n4 + n] & 255) >= n3) continue;
                bitMatrix.set(n, n2);
            }
            ++n2;
        }
        return bitMatrix;
    }

    @Override
    public BitArray getBlackRow(int n, BitArray bitArray) throws NotFoundException {
        int n2;
        byte[] arrby = this.getLuminanceSource();
        int n3 = arrby.getWidth();
        if (bitArray != null && bitArray.getSize() >= n3) {
            bitArray.clear();
        } else {
            bitArray = new BitArray(n3);
        }
        this.initArrays(n3);
        arrby = arrby.getRow(n, this.luminances);
        int[] arrn = this.buckets;
        for (n = 0; n < n3; ++n) {
            n2 = (arrby[n] & 255) >> 3;
            arrn[n2] = arrn[n2] + 1;
        }
        int n4 = GlobalHistogramBinarizer.estimateBlackPoint(arrn);
        n2 = arrby[0];
        n = arrby[1];
        int n5 = 1;
        n2 &= 255;
        n &= 255;
        while (n5 < n3 - 1) {
            int n6 = n5 + 1;
            int n7 = arrby[n6] & 255;
            if ((n * 4 - n2 - n7) / 2 < n4) {
                bitArray.set(n5);
            }
            n2 = n;
            n5 = n6;
            n = n7;
        }
        return bitArray;
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANReader;

public final class EAN8Reader
extends UPCEANReader {
    private final int[] decodeMiddleCounters = new int[4];

    @Override
    protected int decodeMiddle(BitArray bitArray, int[] arrn, StringBuilder stringBuilder) throws NotFoundException {
        int n;
        int n2;
        int n3;
        int[] arrn2 = this.decodeMiddleCounters;
        arrn2[0] = 0;
        arrn2[1] = 0;
        arrn2[2] = 0;
        arrn2[3] = 0;
        int n4 = bitArray.getSize();
        int n5 = arrn[1];
        for (n = 0; n < 4 && n5 < n4; ++n) {
            stringBuilder.append((char)(EAN8Reader.decodeDigit(bitArray, arrn2, n5, L_PATTERNS) + 48));
            n3 = arrn2.length;
            for (n2 = 0; n2 < n3; n5 += arrn2[n2], ++n2) {
            }
        }
        n5 = EAN8Reader.findGuardPattern(bitArray, n5, true, MIDDLE_PATTERN)[1];
        n = 0;
        while (n < 4) {
            if (n5 >= n4) return n5;
            stringBuilder.append((char)(EAN8Reader.decodeDigit(bitArray, arrn2, n5, L_PATTERNS) + 48));
            n3 = arrn2.length;
            for (n2 = 0; n2 < n3; n5 += arrn2[n2], ++n2) {
            }
            ++n;
        }
        return n5;
    }

    @Override
    BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.EAN_8;
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI01weightDecoder;
import com.google.zxing.oned.rss.expanded.decoders.GeneralAppIdDecoder;

final class AI013x0x1xDecoder
extends AI01weightDecoder {
    private static final int DATE_SIZE = 16;
    private static final int HEADER_SIZE = 8;
    private static final int WEIGHT_SIZE = 20;
    private final String dateCode;
    private final String firstAIdigits;

    AI013x0x1xDecoder(BitArray bitArray, String string2, String string3) {
        super(bitArray);
        this.dateCode = string3;
        this.firstAIdigits = string2;
    }

    private void encodeCompressedDate(StringBuilder stringBuilder, int n) {
        int n2 = this.getGeneralDecoder().extractNumericValueFromBitArray(n, 16);
        if (n2 == 38400) {
            return;
        }
        stringBuilder.append('(');
        stringBuilder.append(this.dateCode);
        stringBuilder.append(')');
        n = n2 % 32;
        int n3 = n2 / 32;
        n2 = n3 % 12 + 1;
        if ((n3 /= 12) / 10 == 0) {
            stringBuilder.append('0');
        }
        stringBuilder.append(n3);
        if (n2 / 10 == 0) {
            stringBuilder.append('0');
        }
        stringBuilder.append(n2);
        if (n / 10 == 0) {
            stringBuilder.append('0');
        }
        stringBuilder.append(n);
    }

    @Override
    protected void addWeightCode(StringBuilder stringBuilder, int n) {
        stringBuilder.append('(');
        stringBuilder.append(this.firstAIdigits);
        stringBuilder.append(n /= 100000);
        stringBuilder.append(')');
    }

    @Override
    protected int checkWeight(int n) {
        return n % 100000;
    }

    @Override
    public String parseInformation() throws NotFoundException {
        if (this.getInformation().getSize() != 84) throw NotFoundException.getNotFoundInstance();
        StringBuilder stringBuilder = new StringBuilder();
        this.encodeCompressedGtin(stringBuilder, 8);
        this.encodeCompressedWeight(stringBuilder, 48, 20);
        this.encodeCompressedDate(stringBuilder, 68);
        return stringBuilder.toString();
    }
}


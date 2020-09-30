/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI01decoder;
import com.google.zxing.oned.rss.expanded.decoders.DecodedInformation;
import com.google.zxing.oned.rss.expanded.decoders.GeneralAppIdDecoder;

final class AI01392xDecoder
extends AI01decoder {
    private static final int HEADER_SIZE = 8;
    private static final int LAST_DIGIT_SIZE = 2;

    AI01392xDecoder(BitArray bitArray) {
        super(bitArray);
    }

    @Override
    public String parseInformation() throws NotFoundException, FormatException {
        if (this.getInformation().getSize() < 48) throw NotFoundException.getNotFoundInstance();
        StringBuilder stringBuilder = new StringBuilder();
        this.encodeCompressedGtin(stringBuilder, 8);
        int n = this.getGeneralDecoder().extractNumericValueFromBitArray(48, 2);
        stringBuilder.append("(392");
        stringBuilder.append(n);
        stringBuilder.append(')');
        stringBuilder.append(this.getGeneralDecoder().decodeGeneralPurposeField(50, null).getNewString());
        return stringBuilder.toString();
    }
}


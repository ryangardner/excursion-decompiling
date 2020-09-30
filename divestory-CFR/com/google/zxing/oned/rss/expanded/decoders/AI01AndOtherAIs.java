/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI01decoder;
import com.google.zxing.oned.rss.expanded.decoders.GeneralAppIdDecoder;

final class AI01AndOtherAIs
extends AI01decoder {
    private static final int HEADER_SIZE = 4;

    AI01AndOtherAIs(BitArray bitArray) {
        super(bitArray);
    }

    @Override
    public String parseInformation() throws NotFoundException, FormatException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(01)");
        int n = stringBuilder.length();
        stringBuilder.append(this.getGeneralDecoder().extractNumericValueFromBitArray(4, 4));
        this.encodeCompressedGtinWithoutAI(stringBuilder, 8, n);
        return this.getGeneralDecoder().decodeAllCodes(stringBuilder, 48);
    }
}


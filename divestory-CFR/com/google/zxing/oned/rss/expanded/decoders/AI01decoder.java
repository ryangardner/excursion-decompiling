/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import com.google.zxing.oned.rss.expanded.decoders.GeneralAppIdDecoder;

abstract class AI01decoder
extends AbstractExpandedDecoder {
    protected static final int GTIN_SIZE = 40;

    AI01decoder(BitArray bitArray) {
        super(bitArray);
    }

    private static void appendCheckDigit(StringBuilder stringBuilder, int n) {
        int n2;
        int n3 = 0;
        int n4 = 0;
        for (int i = 0; i < 13; n4 += n2, ++i) {
            int n5;
            n2 = n5 = stringBuilder.charAt(i + n) - 48;
            if ((i & 1) != 0) continue;
            n2 = n5 * 3;
        }
        n = 10 - n4 % 10;
        if (n == 10) {
            n = n3;
        }
        stringBuilder.append(n);
    }

    protected final void encodeCompressedGtin(StringBuilder stringBuilder, int n) {
        stringBuilder.append("(01)");
        int n2 = stringBuilder.length();
        stringBuilder.append('9');
        this.encodeCompressedGtinWithoutAI(stringBuilder, n, n2);
    }

    protected final void encodeCompressedGtinWithoutAI(StringBuilder stringBuilder, int n, int n2) {
        int n3 = 0;
        do {
            if (n3 >= 4) {
                AI01decoder.appendCheckDigit(stringBuilder, n2);
                return;
            }
            int n4 = this.getGeneralDecoder().extractNumericValueFromBitArray(n3 * 10 + n, 10);
            if (n4 / 100 == 0) {
                stringBuilder.append('0');
            }
            if (n4 / 10 == 0) {
                stringBuilder.append('0');
            }
            stringBuilder.append(n4);
            ++n3;
        } while (true);
    }
}


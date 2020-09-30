/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI01decoder;
import com.google.zxing.oned.rss.expanded.decoders.GeneralAppIdDecoder;

abstract class AI01weightDecoder
extends AI01decoder {
    AI01weightDecoder(BitArray bitArray) {
        super(bitArray);
    }

    protected abstract void addWeightCode(StringBuilder var1, int var2);

    protected abstract int checkWeight(int var1);

    protected final void encodeCompressedWeight(StringBuilder stringBuilder, int n, int n2) {
        n = this.getGeneralDecoder().extractNumericValueFromBitArray(n, n2);
        this.addWeightCode(stringBuilder, n);
        int n3 = this.checkWeight(n);
        n2 = 100000;
        n = 0;
        do {
            if (n >= 5) {
                stringBuilder.append(n3);
                return;
            }
            if (n3 / n2 == 0) {
                stringBuilder.append('0');
            }
            n2 /= 10;
            ++n;
        } while (true);
    }
}


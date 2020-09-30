/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI013x0xDecoder;

final class AI01320xDecoder
extends AI013x0xDecoder {
    AI01320xDecoder(BitArray bitArray) {
        super(bitArray);
    }

    @Override
    protected void addWeightCode(StringBuilder stringBuilder, int n) {
        if (n < 10000) {
            stringBuilder.append("(3202)");
            return;
        }
        stringBuilder.append("(3203)");
    }

    @Override
    protected int checkWeight(int n) {
        if (n >= 10000) return n - 10000;
        return n;
    }
}


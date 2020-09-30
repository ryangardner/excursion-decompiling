/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI013x0xDecoder;

final class AI013103decoder
extends AI013x0xDecoder {
    AI013103decoder(BitArray bitArray) {
        super(bitArray);
    }

    @Override
    protected void addWeightCode(StringBuilder stringBuilder, int n) {
        stringBuilder.append("(3103)");
    }

    @Override
    protected int checkWeight(int n) {
        return n;
    }
}


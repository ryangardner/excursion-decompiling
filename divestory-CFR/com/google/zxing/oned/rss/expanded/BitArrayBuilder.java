/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.expanded.ExpandedPair;
import java.util.List;

final class BitArrayBuilder {
    private BitArrayBuilder() {
    }

    static BitArray buildBitArray(List<ExpandedPair> list) {
        int n;
        int n2 = n = list.size() * 2 - 1;
        if (list.get(list.size() - 1).getRightChar() == null) {
            n2 = n - 1;
        }
        BitArray bitArray = new BitArray(n2 * 12);
        n2 = 0;
        int n3 = list.get(0).getRightChar().getValue();
        for (n = 11; n >= 0; ++n2, --n) {
            if ((1 << n & n3) == 0) continue;
            bitArray.set(n2);
        }
        n3 = 1;
        while (n3 < list.size()) {
            ExpandedPair expandedPair = list.get(n3);
            int n4 = expandedPair.getLeftChar().getValue();
            for (n = 11; n >= 0; ++n2, --n) {
                if ((1 << n & n4) == 0) continue;
                bitArray.set(n2);
            }
            n = n2;
            if (expandedPair.getRightChar() != null) {
                int n5 = expandedPair.getRightChar().getValue();
                n4 = 11;
                do {
                    n = ++n2;
                    if (n4 < 0) break;
                    if ((1 << n4 & n5) != 0) {
                        bitArray.set(n2);
                    }
                    --n4;
                } while (true);
            }
            ++n3;
            n2 = n;
        }
        return bitArray;
    }
}


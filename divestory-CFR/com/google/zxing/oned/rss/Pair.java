/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss;

import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;

final class Pair
extends DataCharacter {
    private int count;
    private final FinderPattern finderPattern;

    Pair(int n, int n2, FinderPattern finderPattern) {
        super(n, n2);
        this.finderPattern = finderPattern;
    }

    int getCount() {
        return this.count;
    }

    FinderPattern getFinderPattern() {
        return this.finderPattern;
    }

    void incrementCount() {
        ++this.count;
    }
}


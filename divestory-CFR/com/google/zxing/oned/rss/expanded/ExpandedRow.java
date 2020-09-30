/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.oned.rss.expanded.ExpandedPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class ExpandedRow {
    private final List<ExpandedPair> pairs;
    private final int rowNumber;
    private final boolean wasReversed;

    ExpandedRow(List<ExpandedPair> list, int n, boolean bl) {
        this.pairs = new ArrayList<ExpandedPair>(list);
        this.rowNumber = n;
        this.wasReversed = bl;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof ExpandedRow;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (ExpandedRow)object;
        bl = bl2;
        if (!this.pairs.equals(((ExpandedRow)object).getPairs())) return bl;
        bl = bl2;
        if (this.wasReversed != ((ExpandedRow)object).wasReversed) return bl;
        return true;
    }

    List<ExpandedPair> getPairs() {
        return this.pairs;
    }

    int getRowNumber() {
        return this.rowNumber;
    }

    public int hashCode() {
        return this.pairs.hashCode() ^ Boolean.valueOf(this.wasReversed).hashCode();
    }

    boolean isEquivalent(List<ExpandedPair> list) {
        return this.pairs.equals(list);
    }

    boolean isReversed() {
        return this.wasReversed;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ ");
        stringBuilder.append(this.pairs);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}


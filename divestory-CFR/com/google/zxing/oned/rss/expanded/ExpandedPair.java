/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;

final class ExpandedPair {
    private final FinderPattern finderPattern;
    private final DataCharacter leftChar;
    private final boolean mayBeLast;
    private final DataCharacter rightChar;

    ExpandedPair(DataCharacter dataCharacter, DataCharacter dataCharacter2, FinderPattern finderPattern, boolean bl) {
        this.leftChar = dataCharacter;
        this.rightChar = dataCharacter2;
        this.finderPattern = finderPattern;
        this.mayBeLast = bl;
    }

    private static boolean equalsOrNull(Object object, Object object2) {
        if (object != null) {
            return object.equals(object2);
        }
        if (object2 != null) return false;
        return true;
    }

    private static int hashNotNull(Object object) {
        if (object != null) return object.hashCode();
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof ExpandedPair;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (ExpandedPair)object;
        bl = bl2;
        if (!ExpandedPair.equalsOrNull(this.leftChar, ((ExpandedPair)object).leftChar)) return bl;
        bl = bl2;
        if (!ExpandedPair.equalsOrNull(this.rightChar, ((ExpandedPair)object).rightChar)) return bl;
        bl = bl2;
        if (!ExpandedPair.equalsOrNull(this.finderPattern, ((ExpandedPair)object).finderPattern)) return bl;
        return true;
    }

    FinderPattern getFinderPattern() {
        return this.finderPattern;
    }

    DataCharacter getLeftChar() {
        return this.leftChar;
    }

    DataCharacter getRightChar() {
        return this.rightChar;
    }

    public int hashCode() {
        return ExpandedPair.hashNotNull(this.leftChar) ^ ExpandedPair.hashNotNull(this.rightChar) ^ ExpandedPair.hashNotNull(this.finderPattern);
    }

    boolean mayBeLast() {
        return this.mayBeLast;
    }

    public boolean mustBeLast() {
        if (this.rightChar != null) return false;
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        stringBuilder.append(this.leftChar);
        stringBuilder.append(" , ");
        stringBuilder.append(this.rightChar);
        stringBuilder.append(" : ");
        Object object = this.finderPattern;
        object = object == null ? "null" : Integer.valueOf(((FinderPattern)object).getValue());
        stringBuilder.append(object);
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.CharMatcher;
import java.util.BitSet;

final class SmallCharMatcher
extends CharMatcher.NamedFastMatcher {
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final double DESIRED_LOAD_FACTOR = 0.5;
    static final int MAX_SIZE = 1023;
    private final boolean containsZero;
    private final long filter;
    private final char[] table;

    private SmallCharMatcher(char[] arrc, long l, boolean bl, String string2) {
        super(string2);
        this.table = arrc;
        this.filter = l;
        this.containsZero = bl;
    }

    private boolean checkFilter(int n) {
        if (1L != (this.filter >> n & 1L)) return false;
        return true;
    }

    static int chooseTableSize(int n) {
        if (n == 1) {
            return 2;
        }
        int n2 = Integer.highestOneBit(n - 1) << 1;
        while ((double)n2 * 0.5 < (double)n) {
            n2 <<= 1;
        }
        return n2;
    }

    static CharMatcher from(BitSet bitSet, String string2) {
        int n = bitSet.cardinality();
        boolean bl = bitSet.get(0);
        int n2 = SmallCharMatcher.chooseTableSize(n);
        char[] arrc = new char[n2];
        n = bitSet.nextSetBit(0);
        long l = 0L;
        int n3;
        block0 : while ((n3 = n) != -1) {
            n = SmallCharMatcher.smear(n3);
            do {
                if (arrc[n &= n2 - 1] == '\u0000') {
                    arrc[n] = (char)n3;
                    n = bitSet.nextSetBit(n3 + 1);
                    l = 1L << n3 | l;
                    continue block0;
                }
                ++n;
            } while (true);
            break;
        }
        return new SmallCharMatcher(arrc, l, bl, string2);
    }

    static int smear(int n) {
        return Integer.rotateLeft(n * -862048943, 15) * 461845907;
    }

    @Override
    public boolean matches(char c) {
        int n;
        int n2;
        if (c == '\u0000') {
            return this.containsZero;
        }
        if (!this.checkFilter(c)) {
            return false;
        }
        int n3 = this.table.length - 1;
        int n4 = n2 = SmallCharMatcher.smear(c) & n3;
        do {
            char[] arrc;
            if ((arrc = this.table)[n4] == '\u0000') {
                return false;
            }
            if (arrc[n4] == c) {
                return true;
            }
            n4 = n = n4 + 1 & n3;
        } while (n != n2);
        return false;
    }

    @Override
    void setBits(BitSet bitSet) {
        boolean bl = this.containsZero;
        int n = 0;
        if (bl) {
            bitSet.set(0);
        }
        char[] arrc = this.table;
        int n2 = arrc.length;
        while (n < n2) {
            char c = arrc[n];
            if (c != '\u0000') {
                bitSet.set(c);
            }
            ++n;
        }
    }
}


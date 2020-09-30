/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Hashing {
    private static final long C1 = -862048943L;
    private static final long C2 = 461845907L;
    private static final int MAX_TABLE_SIZE = 1073741824;

    private Hashing() {
    }

    static int closedTableSize(int n, double d) {
        int n2 = Math.max(n, 2);
        n = Integer.highestOneBit(n2);
        if (n2 <= (int)(d * (double)n)) return n;
        if ((n <<= 1) <= 0) return 1073741824;
        return n;
    }

    static boolean needsResizing(int n, int n2, double d) {
        if (!((double)n > d * (double)n2)) return false;
        if (n2 >= 1073741824) return false;
        return true;
    }

    static int smear(int n) {
        return (int)((long)Integer.rotateLeft((int)((long)n * -862048943L), 15) * 461845907L);
    }

    static int smearedHash(@NullableDecl Object object) {
        int n;
        if (object == null) {
            n = 0;
            return Hashing.smear(n);
        }
        n = object.hashCode();
        return Hashing.smear(n);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.collect.Hashing;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class CompactHashing {
    private static final int BYTE_MASK = 255;
    private static final int BYTE_MAX_SIZE = 256;
    static final int DEFAULT_SIZE = 3;
    static final int HASH_TABLE_BITS_MASK = 31;
    private static final int HASH_TABLE_BITS_MAX_BITS = 5;
    static final int MAX_SIZE = 1073741823;
    private static final int MIN_HASH_TABLE_SIZE = 4;
    static final int MODIFICATION_COUNT_INCREMENT = 32;
    private static final int SHORT_MASK = 65535;
    private static final int SHORT_MAX_SIZE = 65536;
    static final byte UNSET = 0;

    private CompactHashing() {
    }

    static Object createTable(int n) {
        if (n >= 2 && n <= 1073741824 && Integer.highestOneBit(n) == n) {
            if (n <= 256) {
                return new byte[n];
            }
            if (n > 65536) return new int[n];
            return new short[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("must be power of 2 between 2^1 and 2^30: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static int getHashPrefix(int n, int n2) {
        return n & n2;
    }

    static int getNext(int n, int n2) {
        return n & n2;
    }

    static int maskCombine(int n, int n2, int n3) {
        return n & n3 | n2 & n3;
    }

    static int newCapacity(int n) {
        int n2;
        if (n < 32) {
            n2 = 4;
            return n2 * (n + 1);
        }
        n2 = 2;
        return n2 * (n + 1);
    }

    static int remove(@NullableDecl Object object, @NullableDecl Object object2, int n, Object object3, int[] arrn, Object[] arrobject, @NullableDecl Object[] arrobject2) {
        int n2;
        int n3 = Hashing.smearedHash(object);
        int n4 = n3 & n;
        int n5 = CompactHashing.tableGet(object3, n4);
        if (n5 == 0) {
            return -1;
        }
        int n6 = CompactHashing.getHashPrefix(n3, n);
        n3 = -1;
        do {
            if (CompactHashing.getHashPrefix(n2 = arrn[--n5], n) == n6 && Objects.equal(object, arrobject[n5]) && (arrobject2 == null || Objects.equal(object2, arrobject2[n5]))) {
                n2 = CompactHashing.getNext(n2, n);
                if (n3 != -1) break;
                CompactHashing.tableSet(object3, n4, n2);
                return n5;
            }
            if ((n2 = CompactHashing.getNext(n2, n)) == 0) {
                return -1;
            }
            n3 = n5;
            n5 = n2;
        } while (true);
        arrn[n3] = CompactHashing.maskCombine(arrn[n3], n2, n);
        return n5;
    }

    static void tableClear(Object object) {
        if (object instanceof byte[]) {
            Arrays.fill((byte[])object, (byte)0);
            return;
        }
        if (object instanceof short[]) {
            Arrays.fill((short[])object, (short)0);
            return;
        }
        Arrays.fill((int[])object, 0);
    }

    static int tableGet(Object object, int n) {
        if (object instanceof byte[]) {
            return ((byte[])object)[n] & 255;
        }
        if (!(object instanceof short[])) return ((int[])object)[n];
        return ((short[])object)[n] & 65535;
    }

    static void tableSet(Object object, int n, int n2) {
        if (object instanceof byte[]) {
            ((byte[])object)[n] = (byte)n2;
            return;
        }
        if (object instanceof short[]) {
            ((short[])object)[n] = (short)n2;
            return;
        }
        ((int[])object)[n] = n2;
    }

    static int tableSize(int n) {
        return Math.max(4, Hashing.closedTableSize(n + 1, 1.0));
    }
}


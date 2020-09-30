/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Bytes;
import java.util.Arrays;
import java.util.Comparator;

public final class SignedBytes {
    public static final byte MAX_POWER_OF_TWO = 64;

    private SignedBytes() {
    }

    public static byte checkedCast(long l) {
        byte by = (byte)l;
        boolean bl = (long)by == l;
        Preconditions.checkArgument(bl, "Out of range: %s", l);
        return by;
    }

    public static int compare(byte by, byte by2) {
        return by - by2;
    }

    public static String join(String string2, byte ... arrby) {
        Preconditions.checkNotNull(string2);
        if (arrby.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(arrby.length * 5);
        stringBuilder.append(arrby[0]);
        int n = 1;
        while (n < arrby.length) {
            stringBuilder.append(string2);
            stringBuilder.append(arrby[n]);
            ++n;
        }
        return stringBuilder.toString();
    }

    public static Comparator<byte[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static byte max(byte ... arrby) {
        byte by = arrby.length;
        int n = 1;
        boolean bl = by > 0;
        Preconditions.checkArgument(bl);
        byte by2 = by = arrby[0];
        while (n < arrby.length) {
            by = by2;
            if (arrby[n] > by2) {
                by = arrby[n];
            }
            ++n;
            by2 = by;
        }
        return by2;
    }

    public static byte min(byte ... arrby) {
        byte by = arrby.length;
        int n = 1;
        boolean bl = by > 0;
        Preconditions.checkArgument(bl);
        byte by2 = by = arrby[0];
        while (n < arrby.length) {
            by = by2;
            if (arrby[n] < by2) {
                by = arrby[n];
            }
            ++n;
            by2 = by;
        }
        return by2;
    }

    public static byte saturatedCast(long l) {
        if (l > 127L) {
            return 127;
        }
        if (l >= -128L) return (byte)l;
        return -128;
    }

    public static void sortDescending(byte[] arrby) {
        Preconditions.checkNotNull(arrby);
        SignedBytes.sortDescending(arrby, 0, arrby.length);
    }

    public static void sortDescending(byte[] arrby, int n, int n2) {
        Preconditions.checkNotNull(arrby);
        Preconditions.checkPositionIndexes(n, n2, arrby.length);
        Arrays.sort(arrby, n, n2);
        Bytes.reverse(arrby, n, n2);
    }

    private static final class LexicographicalComparator
    extends Enum<LexicographicalComparator>
    implements Comparator<byte[]> {
        private static final /* synthetic */ LexicographicalComparator[] $VALUES;
        public static final /* enum */ LexicographicalComparator INSTANCE;

        static {
            LexicographicalComparator lexicographicalComparator;
            INSTANCE = lexicographicalComparator = new LexicographicalComparator();
            $VALUES = new LexicographicalComparator[]{lexicographicalComparator};
        }

        public static LexicographicalComparator valueOf(String string2) {
            return Enum.valueOf(LexicographicalComparator.class, string2);
        }

        public static LexicographicalComparator[] values() {
            return (LexicographicalComparator[])$VALUES.clone();
        }

        @Override
        public int compare(byte[] arrby, byte[] arrby2) {
            int n = Math.min(arrby.length, arrby2.length);
            int n2 = 0;
            while (n2 < n) {
                int n3 = SignedBytes.compare(arrby[n2], arrby2[n2]);
                if (n3 != 0) {
                    return n3;
                }
                ++n2;
            }
            return arrby.length - arrby2.length;
        }

        public String toString() {
            return "SignedBytes.lexicographicalComparator()";
        }
    }

}


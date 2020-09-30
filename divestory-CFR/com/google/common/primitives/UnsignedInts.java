/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.ParseRequest;
import java.util.Arrays;
import java.util.Comparator;

public final class UnsignedInts {
    static final long INT_MASK = 0xFFFFFFFFL;

    private UnsignedInts() {
    }

    public static int checkedCast(long l) {
        boolean bl = l >> 32 == 0L;
        Preconditions.checkArgument(bl, "out of range: %s", l);
        return (int)l;
    }

    public static int compare(int n, int n2) {
        return Ints.compare(UnsignedInts.flip(n), UnsignedInts.flip(n2));
    }

    public static int decode(String object) {
        ParseRequest parseRequest = ParseRequest.fromString((String)object);
        try {
            return UnsignedInts.parseUnsignedInt(parseRequest.rawValue, parseRequest.radix);
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error parsing value: ");
            stringBuilder.append((String)object);
            object = new NumberFormatException(stringBuilder.toString());
            ((Throwable)object).initCause(numberFormatException);
            throw object;
        }
    }

    public static int divide(int n, int n2) {
        return (int)(UnsignedInts.toLong(n) / UnsignedInts.toLong(n2));
    }

    static int flip(int n) {
        return n ^ Integer.MIN_VALUE;
    }

    public static String join(String string2, int ... arrn) {
        Preconditions.checkNotNull(string2);
        if (arrn.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(arrn.length * 5);
        stringBuilder.append(UnsignedInts.toString(arrn[0]));
        int n = 1;
        while (n < arrn.length) {
            stringBuilder.append(string2);
            stringBuilder.append(UnsignedInts.toString(arrn[n]));
            ++n;
        }
        return stringBuilder.toString();
    }

    public static Comparator<int[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static int max(int ... arrn) {
        int n = arrn.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        int n3 = UnsignedInts.flip(arrn[0]);
        while (n2 < arrn.length) {
            int n4 = UnsignedInts.flip(arrn[n2]);
            n = n3;
            if (n4 > n3) {
                n = n4;
            }
            ++n2;
            n3 = n;
        }
        return UnsignedInts.flip(n3);
    }

    public static int min(int ... arrn) {
        int n = arrn.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        int n3 = UnsignedInts.flip(arrn[0]);
        while (n2 < arrn.length) {
            int n4 = UnsignedInts.flip(arrn[n2]);
            n = n3;
            if (n4 < n3) {
                n = n4;
            }
            ++n2;
            n3 = n;
        }
        return UnsignedInts.flip(n3);
    }

    public static int parseUnsignedInt(String string2) {
        return UnsignedInts.parseUnsignedInt(string2, 10);
    }

    public static int parseUnsignedInt(String string2, int n) {
        Preconditions.checkNotNull(string2);
        long l = Long.parseLong(string2, n);
        if ((0xFFFFFFFFL & l) == l) {
            return (int)l;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Input ");
        stringBuilder.append(string2);
        stringBuilder.append(" in base ");
        stringBuilder.append(n);
        stringBuilder.append(" is not in the range of an unsigned integer");
        throw new NumberFormatException(stringBuilder.toString());
    }

    public static int remainder(int n, int n2) {
        return (int)(UnsignedInts.toLong(n) % UnsignedInts.toLong(n2));
    }

    public static int saturatedCast(long l) {
        if (l <= 0L) {
            return 0;
        }
        if (l < 0x100000000L) return (int)l;
        return -1;
    }

    public static void sort(int[] arrn) {
        Preconditions.checkNotNull(arrn);
        UnsignedInts.sort(arrn, 0, arrn.length);
    }

    public static void sort(int[] arrn, int n, int n2) {
        Preconditions.checkNotNull(arrn);
        Preconditions.checkPositionIndexes(n, n2, arrn.length);
        for (int i = n; i < n2; ++i) {
            arrn[i] = UnsignedInts.flip(arrn[i]);
        }
        Arrays.sort(arrn, n, n2);
        while (n < n2) {
            arrn[n] = UnsignedInts.flip(arrn[n]);
            ++n;
        }
    }

    public static void sortDescending(int[] arrn) {
        Preconditions.checkNotNull(arrn);
        UnsignedInts.sortDescending(arrn, 0, arrn.length);
    }

    public static void sortDescending(int[] arrn, int n, int n2) {
        Preconditions.checkNotNull(arrn);
        Preconditions.checkPositionIndexes(n, n2, arrn.length);
        for (int i = n; i < n2; ++i) {
            arrn[i] = Integer.MAX_VALUE ^ arrn[i];
        }
        Arrays.sort(arrn, n, n2);
        while (n < n2) {
            arrn[n] = arrn[n] ^ Integer.MAX_VALUE;
            ++n;
        }
    }

    public static long toLong(int n) {
        return (long)n & 0xFFFFFFFFL;
    }

    public static String toString(int n) {
        return UnsignedInts.toString(n, 10);
    }

    public static String toString(int n, int n2) {
        return Long.toString((long)n & 0xFFFFFFFFL, n2);
    }

    static final class LexicographicalComparator
    extends Enum<LexicographicalComparator>
    implements Comparator<int[]> {
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
        public int compare(int[] arrn, int[] arrn2) {
            int n = Math.min(arrn.length, arrn2.length);
            int n2 = 0;
            while (n2 < n) {
                if (arrn[n2] != arrn2[n2]) {
                    return UnsignedInts.compare(arrn[n2], arrn2[n2]);
                }
                ++n2;
            }
            return arrn.length - arrn2.length;
        }

        public String toString() {
            return "UnsignedInts.lexicographicalComparator()";
        }
    }

}


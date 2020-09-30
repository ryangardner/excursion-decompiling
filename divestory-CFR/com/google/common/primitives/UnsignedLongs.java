/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import com.google.common.primitives.ParseRequest;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;

public final class UnsignedLongs {
    public static final long MAX_VALUE = -1L;

    private UnsignedLongs() {
    }

    public static int compare(long l, long l2) {
        return Longs.compare(UnsignedLongs.flip(l), UnsignedLongs.flip(l2));
    }

    public static long decode(String object) {
        ParseRequest parseRequest = ParseRequest.fromString((String)object);
        try {
            return UnsignedLongs.parseUnsignedLong(parseRequest.rawValue, parseRequest.radix);
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

    public static long divide(long l, long l2) {
        if (l2 < 0L) {
            if (UnsignedLongs.compare(l, l2) >= 0) return 1L;
            return 0L;
        }
        if (l >= 0L) {
            return l / l2;
        }
        int n = 1;
        long l3 = (l >>> 1) / l2 << 1;
        if (UnsignedLongs.compare(l - l3 * l2, l2) >= 0) {
            return l3 + (long)n;
        }
        n = 0;
        return l3 + (long)n;
    }

    private static long flip(long l) {
        return l ^ Long.MIN_VALUE;
    }

    public static String join(String string2, long ... arrl) {
        Preconditions.checkNotNull(string2);
        if (arrl.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(arrl.length * 5);
        stringBuilder.append(UnsignedLongs.toString(arrl[0]));
        int n = 1;
        while (n < arrl.length) {
            stringBuilder.append(string2);
            stringBuilder.append(UnsignedLongs.toString(arrl[n]));
            ++n;
        }
        return stringBuilder.toString();
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static long max(long ... arrl) {
        int n = arrl.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        long l = UnsignedLongs.flip(arrl[0]);
        while (n2 < arrl.length) {
            long l2 = UnsignedLongs.flip(arrl[n2]);
            long l3 = l;
            if (l2 > l) {
                l3 = l2;
            }
            ++n2;
            l = l3;
        }
        return UnsignedLongs.flip(l);
    }

    public static long min(long ... arrl) {
        int n = arrl.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        long l = UnsignedLongs.flip(arrl[0]);
        while (n2 < arrl.length) {
            long l2 = UnsignedLongs.flip(arrl[n2]);
            long l3 = l;
            if (l2 < l) {
                l3 = l2;
            }
            ++n2;
            l = l3;
        }
        return UnsignedLongs.flip(l);
    }

    public static long parseUnsignedLong(String string2) {
        return UnsignedLongs.parseUnsignedLong(string2, 10);
    }

    public static long parseUnsignedLong(String charSequence, int n) {
        Preconditions.checkNotNull(charSequence);
        if (((String)charSequence).length() == 0) throw new NumberFormatException("empty string");
        if (n >= 2 && n <= 36) {
            int n2 = ParseOverflowDetection.maxSafeDigits[n];
            long l = 0L;
            int n3 = 0;
            while (n3 < ((String)charSequence).length()) {
                int n4 = Character.digit(((String)charSequence).charAt(n3), n);
                if (n4 == -1) throw new NumberFormatException((String)charSequence);
                if (n3 > n2 - 1 && ParseOverflowDetection.overflowInParse(l, n4, n)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Too large for unsigned long: ");
                    stringBuilder.append((String)charSequence);
                    throw new NumberFormatException(stringBuilder.toString());
                }
                l = l * (long)n + (long)n4;
                ++n3;
            }
            return l;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("illegal radix: ");
        ((StringBuilder)charSequence).append(n);
        throw new NumberFormatException(((StringBuilder)charSequence).toString());
    }

    public static long remainder(long l, long l2) {
        if (l2 < 0L) {
            if (UnsignedLongs.compare(l, l2) >= 0) return l - l2;
            return l;
        }
        if (l >= 0L) {
            return l % l2;
        }
        if (UnsignedLongs.compare(l -= ((l >>> 1) / l2 << 1) * l2, l2) >= 0) {
            return l - l2;
        }
        l2 = 0L;
        return l - l2;
    }

    public static void sort(long[] arrl) {
        Preconditions.checkNotNull(arrl);
        UnsignedLongs.sort(arrl, 0, arrl.length);
    }

    public static void sort(long[] arrl, int n, int n2) {
        Preconditions.checkNotNull(arrl);
        Preconditions.checkPositionIndexes(n, n2, arrl.length);
        for (int i = n; i < n2; ++i) {
            arrl[i] = UnsignedLongs.flip(arrl[i]);
        }
        Arrays.sort(arrl, n, n2);
        while (n < n2) {
            arrl[n] = UnsignedLongs.flip(arrl[n]);
            ++n;
        }
    }

    public static void sortDescending(long[] arrl) {
        Preconditions.checkNotNull(arrl);
        UnsignedLongs.sortDescending(arrl, 0, arrl.length);
    }

    public static void sortDescending(long[] arrl, int n, int n2) {
        Preconditions.checkNotNull(arrl);
        Preconditions.checkPositionIndexes(n, n2, arrl.length);
        for (int i = n; i < n2; ++i) {
            arrl[i] = Long.MAX_VALUE ^ arrl[i];
        }
        Arrays.sort(arrl, n, n2);
        while (n < n2) {
            arrl[n] = arrl[n] ^ Long.MAX_VALUE;
            ++n;
        }
    }

    public static String toString(long l) {
        return UnsignedLongs.toString(l, 10);
    }

    public static String toString(long l, int n) {
        long l2;
        boolean bl = n >= 2 && n <= 36;
        Preconditions.checkArgument(bl, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", n);
        long l3 = l LCMP 0L;
        if (l3 == false) {
            return "0";
        }
        if (l3 > 0) {
            return Long.toString(l, n);
        }
        l3 = 64;
        char[] arrc = new char[64];
        int n2 = n - 1;
        if ((n & n2) == 0) {
            long l4;
            int n3 = Integer.numberOfTrailingZeros(n);
            do {
                l2 = l3 - true;
                arrc[l2] = Character.forDigit((int)l & n2, n);
                l4 = l >>> n3;
                l3 = l2;
                l = l4;
            } while (l4 != 0L);
            return new String(arrc, (int)l2, 64 - l2);
        }
        long l5 = (n & 1) == 0 ? (l >>> 1) / (long)(n >>> 1) : UnsignedLongs.divide(l, n);
        long l6 = n;
        arrc[63] = Character.forDigit((int)(l - l5 * l6), n);
        l3 = 63;
        do {
            l2 = l3--;
            if (l5 <= 0L) return new String(arrc, (int)l2, 64 - l2);
            arrc[l3] = Character.forDigit((int)(l5 % l6), n);
            l5 /= l6;
        } while (true);
    }

    static final class LexicographicalComparator
    extends Enum<LexicographicalComparator>
    implements Comparator<long[]> {
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
        public int compare(long[] arrl, long[] arrl2) {
            int n = Math.min(arrl.length, arrl2.length);
            int n2 = 0;
            while (n2 < n) {
                if (arrl[n2] != arrl2[n2]) {
                    return UnsignedLongs.compare(arrl[n2], arrl2[n2]);
                }
                ++n2;
            }
            return arrl.length - arrl2.length;
        }

        public String toString() {
            return "UnsignedLongs.lexicographicalComparator()";
        }
    }

    private static final class ParseOverflowDetection {
        static final int[] maxSafeDigits;
        static final long[] maxValueDivs;
        static final int[] maxValueMods;

        static {
            maxValueDivs = new long[37];
            maxValueMods = new int[37];
            maxSafeDigits = new int[37];
            BigInteger bigInteger = new BigInteger("10000000000000000", 16);
            int n = 2;
            while (n <= 36) {
                long[] arrl = maxValueDivs;
                long l = n;
                arrl[n] = UnsignedLongs.divide(-1L, l);
                ParseOverflowDetection.maxValueMods[n] = (int)UnsignedLongs.remainder(-1L, l);
                ParseOverflowDetection.maxSafeDigits[n] = bigInteger.toString(n).length() - 1;
                ++n;
            }
        }

        private ParseOverflowDetection() {
        }

        static boolean overflowInParse(long l, int n, int n2) {
            boolean bl;
            boolean bl2 = bl = true;
            if (l < 0L) return bl2;
            long[] arrl = maxValueDivs;
            if (l < arrl[n2]) {
                return false;
            }
            if (l > arrl[n2]) {
                return true;
            }
            if (n <= maxValueMods[n2]) return false;
            return bl;
        }
    }

}


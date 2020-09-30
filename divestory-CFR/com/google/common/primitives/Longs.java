/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Longs {
    public static final int BYTES = 8;
    public static final long MAX_POWER_OF_TWO = 0x4000000000000000L;

    private Longs() {
    }

    public static List<Long> asList(long ... arrl) {
        if (arrl.length != 0) return new LongArrayAsList(arrl);
        return Collections.emptyList();
    }

    public static int compare(long l, long l2) {
        long l3 = l LCMP l2;
        if (l3 < 0) {
            return (int)((long)-1);
        }
        if (l3 <= 0) return (int)((long)0);
        return (int)((long)1);
    }

    public static long[] concat(long[] ... arrl) {
        int n;
        int n2 = arrl.length;
        int n3 = 0;
        for (n = 0; n < n2; n3 += arrl[n].length, ++n) {
        }
        long[] arrl2 = new long[n3];
        n2 = arrl.length;
        n3 = 0;
        n = 0;
        while (n3 < n2) {
            long[] arrl3 = arrl[n3];
            System.arraycopy(arrl3, 0, arrl2, n, arrl3.length);
            n += arrl3.length;
            ++n3;
        }
        return arrl2;
    }

    public static long constrainToRange(long l, long l2, long l3) {
        boolean bl = l2 <= l3;
        Preconditions.checkArgument(bl, "min (%s) must be less than or equal to max (%s)", l2, l3);
        return Math.min(Math.max(l, l2), l3);
    }

    public static boolean contains(long[] arrl, long l) {
        int n = arrl.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrl[n2] == l) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static long[] ensureCapacity(long[] arrl, int n, int n2) {
        boolean bl = true;
        boolean bl2 = n >= 0;
        Preconditions.checkArgument(bl2, "Invalid minLength: %s", n);
        bl2 = n2 >= 0 ? bl : false;
        Preconditions.checkArgument(bl2, "Invalid padding: %s", n2);
        long[] arrl2 = arrl;
        if (arrl.length >= n) return arrl2;
        return Arrays.copyOf(arrl, n + n2);
    }

    public static long fromByteArray(byte[] arrby) {
        boolean bl = arrby.length >= 8;
        Preconditions.checkArgument(bl, "array too small: %s < %s", arrby.length, 8);
        return Longs.fromBytes(arrby[0], arrby[1], arrby[2], arrby[3], arrby[4], arrby[5], arrby[6], arrby[7]);
    }

    public static long fromBytes(byte by, byte by2, byte by3, byte by4, byte by5, byte by6, byte by7, byte by8) {
        long l = by;
        return ((long)by2 & 255L) << 48 | (l & 255L) << 56 | ((long)by3 & 255L) << 40 | ((long)by4 & 255L) << 32 | ((long)by5 & 255L) << 24 | ((long)by6 & 255L) << 16 | ((long)by7 & 255L) << 8 | (long)by8 & 255L;
    }

    public static int hashCode(long l) {
        return (int)(l ^ l >>> 32);
    }

    public static int indexOf(long[] arrl, long l) {
        return Longs.indexOf(arrl, l, 0, arrl.length);
    }

    private static int indexOf(long[] arrl, long l, int n, int n2) {
        while (n < n2) {
            if (arrl[n] == l) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static int indexOf(long[] arrl, long[] arrl2) {
        Preconditions.checkNotNull(arrl, "array");
        Preconditions.checkNotNull(arrl2, "target");
        if (arrl2.length == 0) {
            return 0;
        }
        int n = 0;
        block0 : while (n < arrl.length - arrl2.length + 1) {
            int n2 = 0;
            while (n2 < arrl2.length) {
                if (arrl[n + n2] != arrl2[n2]) {
                    ++n;
                    continue block0;
                }
                ++n2;
            }
            return n;
            break;
        }
        return -1;
    }

    public static String join(String string2, long ... arrl) {
        Preconditions.checkNotNull(string2);
        if (arrl.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(arrl.length * 10);
        stringBuilder.append(arrl[0]);
        int n = 1;
        while (n < arrl.length) {
            stringBuilder.append(string2);
            stringBuilder.append(arrl[n]);
            ++n;
        }
        return stringBuilder.toString();
    }

    public static int lastIndexOf(long[] arrl, long l) {
        return Longs.lastIndexOf(arrl, l, 0, arrl.length);
    }

    private static int lastIndexOf(long[] arrl, long l, int n, int n2) {
        --n2;
        while (n2 >= n) {
            if (arrl[n2] == l) {
                return n2;
            }
            --n2;
        }
        return -1;
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static long max(long ... arrl) {
        int n = arrl.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        long l = arrl[0];
        while (n2 < arrl.length) {
            long l2 = l;
            if (arrl[n2] > l) {
                l2 = arrl[n2];
            }
            ++n2;
            l = l2;
        }
        return l;
    }

    public static long min(long ... arrl) {
        int n = arrl.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        long l = arrl[0];
        while (n2 < arrl.length) {
            long l2 = l;
            if (arrl[n2] < l) {
                l2 = arrl[n2];
            }
            ++n2;
            l = l2;
        }
        return l;
    }

    public static void reverse(long[] arrl) {
        Preconditions.checkNotNull(arrl);
        Longs.reverse(arrl, 0, arrl.length);
    }

    public static void reverse(long[] arrl, int n, int n2) {
        Preconditions.checkNotNull(arrl);
        Preconditions.checkPositionIndexes(n, n2, arrl.length);
        --n2;
        while (n < n2) {
            long l = arrl[n];
            arrl[n] = arrl[n2];
            arrl[n2] = l;
            ++n;
            --n2;
        }
    }

    public static void sortDescending(long[] arrl) {
        Preconditions.checkNotNull(arrl);
        Longs.sortDescending(arrl, 0, arrl.length);
    }

    public static void sortDescending(long[] arrl, int n, int n2) {
        Preconditions.checkNotNull(arrl);
        Preconditions.checkPositionIndexes(n, n2, arrl.length);
        Arrays.sort(arrl, n, n2);
        Longs.reverse(arrl, n, n2);
    }

    public static Converter<String, Long> stringConverter() {
        return LongConverter.INSTANCE;
    }

    public static long[] toArray(Collection<? extends Number> arrobject) {
        if (arrobject instanceof LongArrayAsList) {
            return ((LongArrayAsList)arrobject).toLongArray();
        }
        arrobject = arrobject.toArray();
        int n = arrobject.length;
        long[] arrl = new long[n];
        int n2 = 0;
        while (n2 < n) {
            arrl[n2] = ((Number)Preconditions.checkNotNull(arrobject[n2])).longValue();
            ++n2;
        }
        return arrl;
    }

    public static byte[] toByteArray(long l) {
        byte[] arrby = new byte[8];
        int n = 7;
        while (n >= 0) {
            arrby[n] = (byte)(255L & l);
            l >>= 8;
            --n;
        }
        return arrby;
    }

    @NullableDecl
    public static Long tryParse(String string2) {
        return Longs.tryParse(string2, 10);
    }

    @NullableDecl
    public static Long tryParse(String charSequence, int n) {
        long l;
        int n2;
        long l2;
        int n3;
        long l3;
        long l4;
        if (Preconditions.checkNotNull(charSequence).isEmpty()) {
            return null;
        }
        if (n >= 2 && n <= 36) {
            n3 = 0;
            if (((String)charSequence).charAt(0) == '-') {
                n3 = 1;
            }
            if (n3 == ((String)charSequence).length()) {
                return null;
            }
            n2 = AsciiDigits.digit(((String)charSequence).charAt(n3));
            if (n2 < 0) return null;
            if (n2 >= n) {
                return null;
            }
            l = -n2;
            l3 = n;
            l4 = Long.MIN_VALUE / l3;
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("radix must be between MIN_RADIX and MAX_RADIX but was ");
            ((StringBuilder)charSequence).append(n);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        for (int i = n3 + 1; i < ((String)charSequence).length(); l -= l2, ++i) {
            n2 = AsciiDigits.digit(((String)charSequence).charAt(i));
            if (n2 < 0) return null;
            if (n2 >= n) return null;
            if (l < l4) {
                return null;
            }
            l2 = n2;
            if ((l *= l3) >= l2 - Long.MIN_VALUE) continue;
            return null;
        }
        if (n3 != 0) {
            return l;
        }
        if (l != Long.MIN_VALUE) return -l;
        return null;
    }

    static final class AsciiDigits {
        private static final byte[] asciiDigits;

        static {
            int n;
            byte[] arrby = new byte[128];
            Arrays.fill(arrby, (byte)-1);
            int n2 = 0;
            int n3 = 0;
            do {
                n = n2;
                if (n3 > 9) break;
                arrby[n3 + 48] = (byte)n3;
                ++n3;
            } while (true);
            do {
                if (n > 26) {
                    asciiDigits = arrby;
                    return;
                }
                n3 = (byte)(n + 10);
                arrby[n + 65] = (byte)n3;
                arrby[n + 97] = (byte)n3;
                ++n;
            } while (true);
        }

        private AsciiDigits() {
        }

        static int digit(char c) {
            if (c >= '') return (char)-1;
            return asciiDigits[c];
        }
    }

    private static final class LexicographicalComparator
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
                int n3 = Longs.compare(arrl[n2], arrl2[n2]);
                if (n3 != 0) {
                    return n3;
                }
                ++n2;
            }
            return arrl.length - arrl2.length;
        }

        public String toString() {
            return "Longs.lexicographicalComparator()";
        }
    }

    private static class LongArrayAsList
    extends AbstractList<Long>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;
        final long[] array;
        final int end;
        final int start;

        LongArrayAsList(long[] arrl) {
            this(arrl, 0, arrl.length);
        }

        LongArrayAsList(long[] arrl, int n, int n2) {
            this.array = arrl;
            this.start = n;
            this.end = n2;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Long)) return false;
            if (Longs.indexOf(this.array, (Long)object, this.start, this.end) == -1) return false;
            return true;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof LongArrayAsList)) return super.equals(object);
            object = (LongArrayAsList)object;
            int n = this.size();
            if (((LongArrayAsList)object).size() != n) {
                return false;
            }
            int n2 = 0;
            while (n2 < n) {
                if (this.array[this.start + n2] != ((LongArrayAsList)object).array[((LongArrayAsList)object).start + n2]) {
                    return false;
                }
                ++n2;
            }
            return true;
        }

        @Override
        public Long get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }

        @Override
        public int hashCode() {
            int n = this.start;
            int n2 = 1;
            while (n < this.end) {
                n2 = n2 * 31 + Longs.hashCode(this.array[n]);
                ++n;
            }
            return n2;
        }

        @Override
        public int indexOf(Object object) {
            if (!(object instanceof Long)) return -1;
            int n = Longs.indexOf(this.array, (Long)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int lastIndexOf(Object object) {
            if (!(object instanceof Long)) return -1;
            int n = Longs.lastIndexOf(this.array, (Long)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public Long set(int n, Long l) {
            Preconditions.checkElementIndex(n, this.size());
            long[] arrl = this.array;
            int n2 = this.start;
            long l2 = arrl[n2 + n];
            arrl[n2 + n] = Preconditions.checkNotNull(l);
            return l2;
        }

        @Override
        public int size() {
            return this.end - this.start;
        }

        @Override
        public List<Long> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            long[] arrl = this.array;
            int n3 = this.start;
            return new LongArrayAsList(arrl, n + n3, n3 + n2);
        }

        long[] toLongArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 10);
            stringBuilder.append('[');
            stringBuilder.append(this.array[this.start]);
            int n = this.start;
            do {
                if (++n >= this.end) {
                    stringBuilder.append(']');
                    return stringBuilder.toString();
                }
                stringBuilder.append(", ");
                stringBuilder.append(this.array[n]);
            } while (true);
        }
    }

    private static final class LongConverter
    extends Converter<String, Long>
    implements Serializable {
        static final LongConverter INSTANCE = new LongConverter();
        private static final long serialVersionUID = 1L;

        private LongConverter() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        protected String doBackward(Long l) {
            return l.toString();
        }

        @Override
        protected Long doForward(String string2) {
            return Long.decode(string2);
        }

        public String toString() {
            return "Longs.stringConverter()";
        }
    }

}


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

public final class Shorts {
    public static final int BYTES = 2;
    public static final short MAX_POWER_OF_TWO = 16384;

    private Shorts() {
    }

    public static List<Short> asList(short ... arrs) {
        if (arrs.length != 0) return new ShortArrayAsList(arrs);
        return Collections.emptyList();
    }

    public static short checkedCast(long l) {
        short s = (short)l;
        boolean bl = (long)s == l;
        Preconditions.checkArgument(bl, "Out of range: %s", l);
        return s;
    }

    public static int compare(short s, short s2) {
        return s - s2;
    }

    public static short[] concat(short[] ... arrs) {
        int n;
        int n2 = arrs.length;
        int n3 = 0;
        for (n = 0; n < n2; n3 += arrs[n].length, ++n) {
        }
        short[] arrs2 = new short[n3];
        n2 = arrs.length;
        n = 0;
        n3 = 0;
        while (n < n2) {
            short[] arrs3 = arrs[n];
            System.arraycopy(arrs3, 0, arrs2, n3, arrs3.length);
            n3 += arrs3.length;
            ++n;
        }
        return arrs2;
    }

    public static short constrainToRange(short s, short s2, short s3) {
        boolean bl = s2 <= s3;
        Preconditions.checkArgument(bl, "min (%s) must be less than or equal to max (%s)", (int)s2, (int)s3);
        if (s < s2) {
            return s2;
        }
        if (s >= s3) return s3;
        return s;
    }

    public static boolean contains(short[] arrs, short s) {
        int n = arrs.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrs[n2] == s) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static short[] ensureCapacity(short[] arrs, int n, int n2) {
        boolean bl = true;
        boolean bl2 = n >= 0;
        Preconditions.checkArgument(bl2, "Invalid minLength: %s", n);
        bl2 = n2 >= 0 ? bl : false;
        Preconditions.checkArgument(bl2, "Invalid padding: %s", n2);
        short[] arrs2 = arrs;
        if (arrs.length >= n) return arrs2;
        return Arrays.copyOf(arrs, n + n2);
    }

    public static short fromByteArray(byte[] arrby) {
        boolean bl = arrby.length >= 2;
        Preconditions.checkArgument(bl, "array too small: %s < %s", arrby.length, 2);
        return Shorts.fromBytes(arrby[0], arrby[1]);
    }

    public static short fromBytes(byte by, byte by2) {
        return (short)(by << 8 | by2 & 255);
    }

    public static int hashCode(short s) {
        return s;
    }

    public static int indexOf(short[] arrs, short s) {
        return Shorts.indexOf(arrs, s, 0, arrs.length);
    }

    private static int indexOf(short[] arrs, short s, int n, int n2) {
        while (n < n2) {
            if (arrs[n] == s) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static int indexOf(short[] arrs, short[] arrs2) {
        Preconditions.checkNotNull(arrs, "array");
        Preconditions.checkNotNull(arrs2, "target");
        if (arrs2.length == 0) {
            return 0;
        }
        int n = 0;
        block0 : while (n < arrs.length - arrs2.length + 1) {
            int n2 = 0;
            while (n2 < arrs2.length) {
                if (arrs[n + n2] != arrs2[n2]) {
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

    public static String join(String string2, short ... arrs) {
        Preconditions.checkNotNull(string2);
        if (arrs.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(arrs.length * 6);
        stringBuilder.append(arrs[0]);
        int n = 1;
        while (n < arrs.length) {
            stringBuilder.append(string2);
            stringBuilder.append(arrs[n]);
            ++n;
        }
        return stringBuilder.toString();
    }

    public static int lastIndexOf(short[] arrs, short s) {
        return Shorts.lastIndexOf(arrs, s, 0, arrs.length);
    }

    private static int lastIndexOf(short[] arrs, short s, int n, int n2) {
        --n2;
        while (n2 >= n) {
            if (arrs[n2] == s) {
                return n2;
            }
            --n2;
        }
        return -1;
    }

    public static Comparator<short[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static short max(short ... arrs) {
        short s = arrs.length;
        int n = 1;
        boolean bl = s > 0;
        Preconditions.checkArgument(bl);
        short s2 = s = arrs[0];
        while (n < arrs.length) {
            s = s2;
            if (arrs[n] > s2) {
                s = arrs[n];
            }
            ++n;
            s2 = s;
        }
        return s2;
    }

    public static short min(short ... arrs) {
        short s = arrs.length;
        int n = 1;
        boolean bl = s > 0;
        Preconditions.checkArgument(bl);
        short s2 = s = arrs[0];
        while (n < arrs.length) {
            s = s2;
            if (arrs[n] < s2) {
                s = arrs[n];
            }
            ++n;
            s2 = s;
        }
        return s2;
    }

    public static void reverse(short[] arrs) {
        Preconditions.checkNotNull(arrs);
        Shorts.reverse(arrs, 0, arrs.length);
    }

    public static void reverse(short[] arrs, int n, int n2) {
        Preconditions.checkNotNull(arrs);
        Preconditions.checkPositionIndexes(n, n2, arrs.length);
        --n2;
        while (n < n2) {
            short s = arrs[n];
            arrs[n] = arrs[n2];
            arrs[n2] = s;
            ++n;
            --n2;
        }
    }

    public static short saturatedCast(long l) {
        if (l > 32767L) {
            return 32767;
        }
        if (l >= -32768L) return (short)l;
        return -32768;
    }

    public static void sortDescending(short[] arrs) {
        Preconditions.checkNotNull(arrs);
        Shorts.sortDescending(arrs, 0, arrs.length);
    }

    public static void sortDescending(short[] arrs, int n, int n2) {
        Preconditions.checkNotNull(arrs);
        Preconditions.checkPositionIndexes(n, n2, arrs.length);
        Arrays.sort(arrs, n, n2);
        Shorts.reverse(arrs, n, n2);
    }

    public static Converter<String, Short> stringConverter() {
        return ShortConverter.INSTANCE;
    }

    public static short[] toArray(Collection<? extends Number> arrobject) {
        if (arrobject instanceof ShortArrayAsList) {
            return ((ShortArrayAsList)arrobject).toShortArray();
        }
        arrobject = arrobject.toArray();
        int n = arrobject.length;
        short[] arrs = new short[n];
        int n2 = 0;
        while (n2 < n) {
            arrs[n2] = ((Number)Preconditions.checkNotNull(arrobject[n2])).shortValue();
            ++n2;
        }
        return arrs;
    }

    public static byte[] toByteArray(short s) {
        return new byte[]{(byte)(s >> 8), (byte)s};
    }

    private static final class LexicographicalComparator
    extends Enum<LexicographicalComparator>
    implements Comparator<short[]> {
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
        public int compare(short[] arrs, short[] arrs2) {
            int n = Math.min(arrs.length, arrs2.length);
            int n2 = 0;
            while (n2 < n) {
                int n3 = Shorts.compare(arrs[n2], arrs2[n2]);
                if (n3 != 0) {
                    return n3;
                }
                ++n2;
            }
            return arrs.length - arrs2.length;
        }

        public String toString() {
            return "Shorts.lexicographicalComparator()";
        }
    }

    private static class ShortArrayAsList
    extends AbstractList<Short>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;
        final short[] array;
        final int end;
        final int start;

        ShortArrayAsList(short[] arrs) {
            this(arrs, 0, arrs.length);
        }

        ShortArrayAsList(short[] arrs, int n, int n2) {
            this.array = arrs;
            this.start = n;
            this.end = n2;
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            if (!(object instanceof Short)) return false;
            if (Shorts.indexOf(this.array, (Short)object, this.start, this.end) == -1) return false;
            return true;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof ShortArrayAsList)) return super.equals(object);
            object = (ShortArrayAsList)object;
            int n = this.size();
            if (((ShortArrayAsList)object).size() != n) {
                return false;
            }
            int n2 = 0;
            while (n2 < n) {
                if (this.array[this.start + n2] != ((ShortArrayAsList)object).array[((ShortArrayAsList)object).start + n2]) {
                    return false;
                }
                ++n2;
            }
            return true;
        }

        @Override
        public Short get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }

        @Override
        public int hashCode() {
            int n = this.start;
            int n2 = 1;
            while (n < this.end) {
                n2 = n2 * 31 + Shorts.hashCode(this.array[n]);
                ++n;
            }
            return n2;
        }

        @Override
        public int indexOf(@NullableDecl Object object) {
            if (!(object instanceof Short)) return -1;
            int n = Shorts.indexOf(this.array, (Short)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int lastIndexOf(@NullableDecl Object object) {
            if (!(object instanceof Short)) return -1;
            int n = Shorts.lastIndexOf(this.array, (Short)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public Short set(int n, Short s) {
            Preconditions.checkElementIndex(n, this.size());
            short[] arrs = this.array;
            int n2 = this.start;
            short s2 = arrs[n2 + n];
            arrs[n2 + n] = Preconditions.checkNotNull(s);
            return s2;
        }

        @Override
        public int size() {
            return this.end - this.start;
        }

        @Override
        public List<Short> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            short[] arrs = this.array;
            int n3 = this.start;
            return new ShortArrayAsList(arrs, n + n3, n3 + n2);
        }

        short[] toShortArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 6);
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

    private static final class ShortConverter
    extends Converter<String, Short>
    implements Serializable {
        static final ShortConverter INSTANCE = new ShortConverter();
        private static final long serialVersionUID = 1L;

        private ShortConverter() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        protected String doBackward(Short s) {
            return s.toString();
        }

        @Override
        protected Short doForward(String string2) {
            return Short.decode(string2);
        }

        public String toString() {
            return "Shorts.stringConverter()";
        }
    }

}


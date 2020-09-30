/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Ints {
    public static final int BYTES = 4;
    public static final int MAX_POWER_OF_TWO = 1073741824;

    private Ints() {
    }

    public static List<Integer> asList(int ... arrn) {
        if (arrn.length != 0) return new IntArrayAsList(arrn);
        return Collections.emptyList();
    }

    public static int checkedCast(long l) {
        int n = (int)l;
        boolean bl = (long)n == l;
        Preconditions.checkArgument(bl, "Out of range: %s", l);
        return n;
    }

    public static int compare(int n, int n2) {
        if (n < n2) {
            return -1;
        }
        if (n <= n2) return 0;
        return 1;
    }

    public static int[] concat(int[] ... arrn) {
        int n;
        int n2 = arrn.length;
        int n3 = 0;
        for (n = 0; n < n2; n3 += arrn[n].length, ++n) {
        }
        int[] arrn2 = new int[n3];
        n2 = arrn.length;
        n = 0;
        n3 = 0;
        while (n < n2) {
            int[] arrn3 = arrn[n];
            System.arraycopy(arrn3, 0, arrn2, n3, arrn3.length);
            n3 += arrn3.length;
            ++n;
        }
        return arrn2;
    }

    public static int constrainToRange(int n, int n2, int n3) {
        boolean bl = n2 <= n3;
        Preconditions.checkArgument(bl, "min (%s) must be less than or equal to max (%s)", n2, n3);
        return Math.min(Math.max(n, n2), n3);
    }

    public static boolean contains(int[] arrn, int n) {
        int n2 = arrn.length;
        int n3 = 0;
        while (n3 < n2) {
            if (arrn[n3] == n) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public static int[] ensureCapacity(int[] arrn, int n, int n2) {
        boolean bl = true;
        boolean bl2 = n >= 0;
        Preconditions.checkArgument(bl2, "Invalid minLength: %s", n);
        bl2 = n2 >= 0 ? bl : false;
        Preconditions.checkArgument(bl2, "Invalid padding: %s", n2);
        int[] arrn2 = arrn;
        if (arrn.length >= n) return arrn2;
        return Arrays.copyOf(arrn, n + n2);
    }

    public static int fromByteArray(byte[] arrby) {
        boolean bl = arrby.length >= 4;
        Preconditions.checkArgument(bl, "array too small: %s < %s", arrby.length, 4);
        return Ints.fromBytes(arrby[0], arrby[1], arrby[2], arrby[3]);
    }

    public static int fromBytes(byte by, byte by2, byte by3, byte by4) {
        return by << 24 | (by2 & 255) << 16 | (by3 & 255) << 8 | by4 & 255;
    }

    public static int hashCode(int n) {
        return n;
    }

    public static int indexOf(int[] arrn, int n) {
        return Ints.indexOf(arrn, n, 0, arrn.length);
    }

    private static int indexOf(int[] arrn, int n, int n2, int n3) {
        while (n2 < n3) {
            if (arrn[n2] == n) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public static int indexOf(int[] arrn, int[] arrn2) {
        Preconditions.checkNotNull(arrn, "array");
        Preconditions.checkNotNull(arrn2, "target");
        if (arrn2.length == 0) {
            return 0;
        }
        int n = 0;
        block0 : while (n < arrn.length - arrn2.length + 1) {
            int n2 = 0;
            while (n2 < arrn2.length) {
                if (arrn[n + n2] != arrn2[n2]) {
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

    public static String join(String string2, int ... arrn) {
        Preconditions.checkNotNull(string2);
        if (arrn.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(arrn.length * 5);
        stringBuilder.append(arrn[0]);
        int n = 1;
        while (n < arrn.length) {
            stringBuilder.append(string2);
            stringBuilder.append(arrn[n]);
            ++n;
        }
        return stringBuilder.toString();
    }

    public static int lastIndexOf(int[] arrn, int n) {
        return Ints.lastIndexOf(arrn, n, 0, arrn.length);
    }

    private static int lastIndexOf(int[] arrn, int n, int n2, int n3) {
        --n3;
        while (n3 >= n2) {
            if (arrn[n3] == n) {
                return n3;
            }
            --n3;
        }
        return -1;
    }

    public static Comparator<int[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static int max(int ... arrn) {
        int n = arrn.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        int n3 = arrn[0];
        while (n2 < arrn.length) {
            n = n3;
            if (arrn[n2] > n3) {
                n = arrn[n2];
            }
            ++n2;
            n3 = n;
        }
        return n3;
    }

    public static int min(int ... arrn) {
        int n = arrn.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        int n3 = arrn[0];
        while (n2 < arrn.length) {
            n = n3;
            if (arrn[n2] < n3) {
                n = arrn[n2];
            }
            ++n2;
            n3 = n;
        }
        return n3;
    }

    public static void reverse(int[] arrn) {
        Preconditions.checkNotNull(arrn);
        Ints.reverse(arrn, 0, arrn.length);
    }

    public static void reverse(int[] arrn, int n, int n2) {
        Preconditions.checkNotNull(arrn);
        Preconditions.checkPositionIndexes(n, n2, arrn.length);
        --n2;
        while (n < n2) {
            int n3 = arrn[n];
            arrn[n] = arrn[n2];
            arrn[n2] = n3;
            ++n;
            --n2;
        }
    }

    public static int saturatedCast(long l) {
        if (l > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (l >= Integer.MIN_VALUE) return (int)l;
        return Integer.MIN_VALUE;
    }

    public static void sortDescending(int[] arrn) {
        Preconditions.checkNotNull(arrn);
        Ints.sortDescending(arrn, 0, arrn.length);
    }

    public static void sortDescending(int[] arrn, int n, int n2) {
        Preconditions.checkNotNull(arrn);
        Preconditions.checkPositionIndexes(n, n2, arrn.length);
        Arrays.sort(arrn, n, n2);
        Ints.reverse(arrn, n, n2);
    }

    public static Converter<String, Integer> stringConverter() {
        return IntConverter.INSTANCE;
    }

    public static int[] toArray(Collection<? extends Number> arrn) {
        if (arrn instanceof IntArrayAsList) {
            return ((IntArrayAsList)arrn).toIntArray();
        }
        Object[] arrobject = arrn.toArray();
        int n = arrobject.length;
        arrn = new int[n];
        int n2 = 0;
        while (n2 < n) {
            arrn[n2] = ((Number)Preconditions.checkNotNull(arrobject[n2])).intValue();
            ++n2;
        }
        return arrn;
    }

    public static byte[] toByteArray(int n) {
        return new byte[]{(byte)(n >> 24), (byte)(n >> 16), (byte)(n >> 8), (byte)n};
    }

    @NullableDecl
    public static Integer tryParse(String string2) {
        return Ints.tryParse(string2, 10);
    }

    @NullableDecl
    public static Integer tryParse(String object, int n) {
        if ((object = Longs.tryParse((String)object, n)) == null) return null;
        if ((Long)object == (long)((Long)object).intValue()) return ((Long)object).intValue();
        return null;
    }

    private static class IntArrayAsList
    extends AbstractList<Integer>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;
        final int[] array;
        final int end;
        final int start;

        IntArrayAsList(int[] arrn) {
            this(arrn, 0, arrn.length);
        }

        IntArrayAsList(int[] arrn, int n, int n2) {
            this.array = arrn;
            this.start = n;
            this.end = n2;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Integer)) return false;
            if (Ints.indexOf(this.array, (Integer)object, this.start, this.end) == -1) return false;
            return true;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof IntArrayAsList)) return super.equals(object);
            object = (IntArrayAsList)object;
            int n = this.size();
            if (((IntArrayAsList)object).size() != n) {
                return false;
            }
            int n2 = 0;
            while (n2 < n) {
                if (this.array[this.start + n2] != ((IntArrayAsList)object).array[((IntArrayAsList)object).start + n2]) {
                    return false;
                }
                ++n2;
            }
            return true;
        }

        @Override
        public Integer get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }

        @Override
        public int hashCode() {
            int n = this.start;
            int n2 = 1;
            while (n < this.end) {
                n2 = n2 * 31 + Ints.hashCode(this.array[n]);
                ++n;
            }
            return n2;
        }

        @Override
        public int indexOf(Object object) {
            if (!(object instanceof Integer)) return -1;
            int n = Ints.indexOf(this.array, (Integer)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int lastIndexOf(Object object) {
            if (!(object instanceof Integer)) return -1;
            int n = Ints.lastIndexOf(this.array, (Integer)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public Integer set(int n, Integer n2) {
            Preconditions.checkElementIndex(n, this.size());
            int[] arrn = this.array;
            int n3 = this.start;
            int n4 = arrn[n3 + n];
            arrn[n3 + n] = Preconditions.checkNotNull(n2);
            return n4;
        }

        @Override
        public int size() {
            return this.end - this.start;
        }

        @Override
        public List<Integer> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            int[] arrn = this.array;
            int n3 = this.start;
            return new IntArrayAsList(arrn, n + n3, n3 + n2);
        }

        int[] toIntArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 5);
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

    private static final class IntConverter
    extends Converter<String, Integer>
    implements Serializable {
        static final IntConverter INSTANCE = new IntConverter();
        private static final long serialVersionUID = 1L;

        private IntConverter() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        protected String doBackward(Integer n) {
            return n.toString();
        }

        @Override
        protected Integer doForward(String string2) {
            return Integer.decode(string2);
        }

        public String toString() {
            return "Ints.stringConverter()";
        }
    }

    private static final class LexicographicalComparator
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
                int n3 = Ints.compare(arrn[n2], arrn2[n2]);
                if (n3 != 0) {
                    return n3;
                }
                ++n2;
            }
            return arrn.length - arrn2.length;
        }

        public String toString() {
            return "Ints.lexicographicalComparator()";
        }
    }

}


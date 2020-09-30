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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Doubles {
    public static final int BYTES = 8;
    static final Pattern FLOATING_POINT_PATTERN = Doubles.fpPattern();

    private Doubles() {
    }

    public static List<Double> asList(double ... arrd) {
        if (arrd.length != 0) return new DoubleArrayAsList(arrd);
        return Collections.emptyList();
    }

    public static int compare(double d, double d2) {
        return Double.compare(d, d2);
    }

    public static double[] concat(double[] ... arrd) {
        int n;
        int n2 = arrd.length;
        int n3 = 0;
        for (n = 0; n < n2; n3 += arrd[n].length, ++n) {
        }
        double[] arrd2 = new double[n3];
        n2 = arrd.length;
        n3 = 0;
        n = 0;
        while (n3 < n2) {
            double[] arrd3 = arrd[n3];
            System.arraycopy(arrd3, 0, arrd2, n, arrd3.length);
            n += arrd3.length;
            ++n3;
        }
        return arrd2;
    }

    public static double constrainToRange(double d, double d2, double d3) {
        boolean bl = d2 <= d3;
        Preconditions.checkArgument(bl, "min (%s) must be less than or equal to max (%s)", (Object)d2, (Object)d3);
        return Math.min(Math.max(d, d2), d3);
    }

    public static boolean contains(double[] arrd, double d) {
        int n = arrd.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrd[n2] == d) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static double[] ensureCapacity(double[] arrd, int n, int n2) {
        boolean bl = true;
        boolean bl2 = n >= 0;
        Preconditions.checkArgument(bl2, "Invalid minLength: %s", n);
        bl2 = n2 >= 0 ? bl : false;
        Preconditions.checkArgument(bl2, "Invalid padding: %s", n2);
        double[] arrd2 = arrd;
        if (arrd.length >= n) return arrd2;
        return Arrays.copyOf(arrd, n + n2);
    }

    private static Pattern fpPattern() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("(?:\\d+#(?:\\.\\d*#)?|\\.\\d+#)");
        charSequence.append("(?:[eE][+-]?\\d+#)?[fFdD]?");
        charSequence = charSequence.toString();
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append("0[xX]");
        charSequence2.append("(?:[0-9a-fA-F]+#(?:\\.[0-9a-fA-F]*#)?|\\.[0-9a-fA-F]+#)");
        charSequence2.append("[pP][+-]?\\d+#[fFdD]?");
        charSequence2 = charSequence2.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[+-]?(?:NaN|Infinity|");
        stringBuilder.append((String)charSequence);
        stringBuilder.append("|");
        stringBuilder.append((String)charSequence2);
        stringBuilder.append(")");
        return Pattern.compile(stringBuilder.toString().replace("#", "+"));
    }

    public static int hashCode(double d) {
        return Double.valueOf(d).hashCode();
    }

    public static int indexOf(double[] arrd, double d) {
        return Doubles.indexOf(arrd, d, 0, arrd.length);
    }

    private static int indexOf(double[] arrd, double d, int n, int n2) {
        while (n < n2) {
            if (arrd[n] == d) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static int indexOf(double[] arrd, double[] arrd2) {
        Preconditions.checkNotNull(arrd, "array");
        Preconditions.checkNotNull(arrd2, "target");
        if (arrd2.length == 0) {
            return 0;
        }
        int n = 0;
        block0 : while (n < arrd.length - arrd2.length + 1) {
            int n2 = 0;
            while (n2 < arrd2.length) {
                if (arrd[n + n2] != arrd2[n2]) {
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

    public static boolean isFinite(double d) {
        if (!(Double.NEGATIVE_INFINITY < d)) return false;
        if (!(d < Double.POSITIVE_INFINITY)) return false;
        return true;
    }

    public static String join(String string2, double ... arrd) {
        Preconditions.checkNotNull(string2);
        if (arrd.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(arrd.length * 12);
        stringBuilder.append(arrd[0]);
        int n = 1;
        while (n < arrd.length) {
            stringBuilder.append(string2);
            stringBuilder.append(arrd[n]);
            ++n;
        }
        return stringBuilder.toString();
    }

    public static int lastIndexOf(double[] arrd, double d) {
        return Doubles.lastIndexOf(arrd, d, 0, arrd.length);
    }

    private static int lastIndexOf(double[] arrd, double d, int n, int n2) {
        --n2;
        while (n2 >= n) {
            if (arrd[n2] == d) {
                return n2;
            }
            --n2;
        }
        return -1;
    }

    public static Comparator<double[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static double max(double ... arrd) {
        int n = arrd.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        double d = arrd[0];
        while (n2 < arrd.length) {
            d = Math.max(d, arrd[n2]);
            ++n2;
        }
        return d;
    }

    public static double min(double ... arrd) {
        int n = arrd.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        double d = arrd[0];
        while (n2 < arrd.length) {
            d = Math.min(d, arrd[n2]);
            ++n2;
        }
        return d;
    }

    public static void reverse(double[] arrd) {
        Preconditions.checkNotNull(arrd);
        Doubles.reverse(arrd, 0, arrd.length);
    }

    public static void reverse(double[] arrd, int n, int n2) {
        Preconditions.checkNotNull(arrd);
        Preconditions.checkPositionIndexes(n, n2, arrd.length);
        --n2;
        while (n < n2) {
            double d = arrd[n];
            arrd[n] = arrd[n2];
            arrd[n2] = d;
            ++n;
            --n2;
        }
    }

    public static void sortDescending(double[] arrd) {
        Preconditions.checkNotNull(arrd);
        Doubles.sortDescending(arrd, 0, arrd.length);
    }

    public static void sortDescending(double[] arrd, int n, int n2) {
        Preconditions.checkNotNull(arrd);
        Preconditions.checkPositionIndexes(n, n2, arrd.length);
        Arrays.sort(arrd, n, n2);
        Doubles.reverse(arrd, n, n2);
    }

    public static Converter<String, Double> stringConverter() {
        return DoubleConverter.INSTANCE;
    }

    public static double[] toArray(Collection<? extends Number> arrobject) {
        if (arrobject instanceof DoubleArrayAsList) {
            return ((DoubleArrayAsList)arrobject).toDoubleArray();
        }
        arrobject = arrobject.toArray();
        int n = arrobject.length;
        double[] arrd = new double[n];
        int n2 = 0;
        while (n2 < n) {
            arrd[n2] = ((Number)Preconditions.checkNotNull(arrobject[n2])).doubleValue();
            ++n2;
        }
        return arrd;
    }

    @NullableDecl
    public static Double tryParse(String string2) {
        double d;
        if (!FLOATING_POINT_PATTERN.matcher(string2).matches()) return null;
        try {
            d = Double.parseDouble(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
        return d;
    }

    private static class DoubleArrayAsList
    extends AbstractList<Double>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;
        final double[] array;
        final int end;
        final int start;

        DoubleArrayAsList(double[] arrd) {
            this(arrd, 0, arrd.length);
        }

        DoubleArrayAsList(double[] arrd, int n, int n2) {
            this.array = arrd;
            this.start = n;
            this.end = n2;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Double)) return false;
            if (Doubles.indexOf(this.array, (Double)object, this.start, this.end) == -1) return false;
            return true;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof DoubleArrayAsList)) return super.equals(object);
            object = (DoubleArrayAsList)object;
            int n = this.size();
            if (((DoubleArrayAsList)object).size() != n) {
                return false;
            }
            int n2 = 0;
            while (n2 < n) {
                if (this.array[this.start + n2] != ((DoubleArrayAsList)object).array[((DoubleArrayAsList)object).start + n2]) {
                    return false;
                }
                ++n2;
            }
            return true;
        }

        @Override
        public Double get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }

        @Override
        public int hashCode() {
            int n = this.start;
            int n2 = 1;
            while (n < this.end) {
                n2 = n2 * 31 + Doubles.hashCode(this.array[n]);
                ++n;
            }
            return n2;
        }

        @Override
        public int indexOf(Object object) {
            if (!(object instanceof Double)) return -1;
            int n = Doubles.indexOf(this.array, (Double)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int lastIndexOf(Object object) {
            if (!(object instanceof Double)) return -1;
            int n = Doubles.lastIndexOf(this.array, (Double)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public Double set(int n, Double d) {
            Preconditions.checkElementIndex(n, this.size());
            double[] arrd = this.array;
            int n2 = this.start;
            double d2 = arrd[n2 + n];
            arrd[n2 + n] = Preconditions.checkNotNull(d);
            return d2;
        }

        @Override
        public int size() {
            return this.end - this.start;
        }

        @Override
        public List<Double> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            double[] arrd = this.array;
            int n3 = this.start;
            return new DoubleArrayAsList(arrd, n + n3, n3 + n2);
        }

        double[] toDoubleArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 12);
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

    private static final class DoubleConverter
    extends Converter<String, Double>
    implements Serializable {
        static final DoubleConverter INSTANCE = new DoubleConverter();
        private static final long serialVersionUID = 1L;

        private DoubleConverter() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        protected String doBackward(Double d) {
            return d.toString();
        }

        @Override
        protected Double doForward(String string2) {
            return Double.valueOf(string2);
        }

        public String toString() {
            return "Doubles.stringConverter()";
        }
    }

    private static final class LexicographicalComparator
    extends Enum<LexicographicalComparator>
    implements Comparator<double[]> {
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
        public int compare(double[] arrd, double[] arrd2) {
            int n = Math.min(arrd.length, arrd2.length);
            int n2 = 0;
            while (n2 < n) {
                int n3 = Double.compare(arrd[n2], arrd2[n2]);
                if (n3 != 0) {
                    return n3;
                }
                ++n2;
            }
            return arrd.length - arrd2.length;
        }

        public String toString() {
            return "Doubles.lexicographicalComparator()";
        }
    }

}


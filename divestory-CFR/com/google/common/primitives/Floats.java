/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
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

public final class Floats {
    public static final int BYTES = 4;

    private Floats() {
    }

    public static List<Float> asList(float ... arrf) {
        if (arrf.length != 0) return new FloatArrayAsList(arrf);
        return Collections.emptyList();
    }

    public static int compare(float f, float f2) {
        return Float.compare(f, f2);
    }

    public static float[] concat(float[] ... arrf) {
        int n;
        int n2 = arrf.length;
        int n3 = 0;
        for (n = 0; n < n2; n3 += arrf[n].length, ++n) {
        }
        float[] arrf2 = new float[n3];
        n2 = arrf.length;
        n = 0;
        n3 = 0;
        while (n < n2) {
            float[] arrf3 = arrf[n];
            System.arraycopy(arrf3, 0, arrf2, n3, arrf3.length);
            n3 += arrf3.length;
            ++n;
        }
        return arrf2;
    }

    public static float constrainToRange(float f, float f2, float f3) {
        boolean bl = f2 <= f3;
        Preconditions.checkArgument(bl, "min (%s) must be less than or equal to max (%s)", (Object)Float.valueOf(f2), (Object)Float.valueOf(f3));
        return Math.min(Math.max(f, f2), f3);
    }

    public static boolean contains(float[] arrf, float f) {
        int n = arrf.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrf[n2] == f) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static float[] ensureCapacity(float[] arrf, int n, int n2) {
        boolean bl = true;
        boolean bl2 = n >= 0;
        Preconditions.checkArgument(bl2, "Invalid minLength: %s", n);
        bl2 = n2 >= 0 ? bl : false;
        Preconditions.checkArgument(bl2, "Invalid padding: %s", n2);
        float[] arrf2 = arrf;
        if (arrf.length >= n) return arrf2;
        return Arrays.copyOf(arrf, n + n2);
    }

    public static int hashCode(float f) {
        return Float.valueOf(f).hashCode();
    }

    public static int indexOf(float[] arrf, float f) {
        return Floats.indexOf(arrf, f, 0, arrf.length);
    }

    private static int indexOf(float[] arrf, float f, int n, int n2) {
        while (n < n2) {
            if (arrf[n] == f) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static int indexOf(float[] arrf, float[] arrf2) {
        Preconditions.checkNotNull(arrf, "array");
        Preconditions.checkNotNull(arrf2, "target");
        if (arrf2.length == 0) {
            return 0;
        }
        int n = 0;
        block0 : while (n < arrf.length - arrf2.length + 1) {
            int n2 = 0;
            while (n2 < arrf2.length) {
                if (arrf[n + n2] != arrf2[n2]) {
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

    public static boolean isFinite(float f) {
        if (!(Float.NEGATIVE_INFINITY < f)) return false;
        if (!(f < Float.POSITIVE_INFINITY)) return false;
        return true;
    }

    public static String join(String string2, float ... arrf) {
        Preconditions.checkNotNull(string2);
        if (arrf.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(arrf.length * 12);
        stringBuilder.append(arrf[0]);
        int n = 1;
        while (n < arrf.length) {
            stringBuilder.append(string2);
            stringBuilder.append(arrf[n]);
            ++n;
        }
        return stringBuilder.toString();
    }

    public static int lastIndexOf(float[] arrf, float f) {
        return Floats.lastIndexOf(arrf, f, 0, arrf.length);
    }

    private static int lastIndexOf(float[] arrf, float f, int n, int n2) {
        --n2;
        while (n2 >= n) {
            if (arrf[n2] == f) {
                return n2;
            }
            --n2;
        }
        return -1;
    }

    public static Comparator<float[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static float max(float ... arrf) {
        int n = arrf.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        float f = arrf[0];
        while (n2 < arrf.length) {
            f = Math.max(f, arrf[n2]);
            ++n2;
        }
        return f;
    }

    public static float min(float ... arrf) {
        int n = arrf.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        float f = arrf[0];
        while (n2 < arrf.length) {
            f = Math.min(f, arrf[n2]);
            ++n2;
        }
        return f;
    }

    public static void reverse(float[] arrf) {
        Preconditions.checkNotNull(arrf);
        Floats.reverse(arrf, 0, arrf.length);
    }

    public static void reverse(float[] arrf, int n, int n2) {
        Preconditions.checkNotNull(arrf);
        Preconditions.checkPositionIndexes(n, n2, arrf.length);
        --n2;
        while (n < n2) {
            float f = arrf[n];
            arrf[n] = arrf[n2];
            arrf[n2] = f;
            ++n;
            --n2;
        }
    }

    public static void sortDescending(float[] arrf) {
        Preconditions.checkNotNull(arrf);
        Floats.sortDescending(arrf, 0, arrf.length);
    }

    public static void sortDescending(float[] arrf, int n, int n2) {
        Preconditions.checkNotNull(arrf);
        Preconditions.checkPositionIndexes(n, n2, arrf.length);
        Arrays.sort(arrf, n, n2);
        Floats.reverse(arrf, n, n2);
    }

    public static Converter<String, Float> stringConverter() {
        return FloatConverter.INSTANCE;
    }

    public static float[] toArray(Collection<? extends Number> arrobject) {
        if (arrobject instanceof FloatArrayAsList) {
            return ((FloatArrayAsList)arrobject).toFloatArray();
        }
        arrobject = arrobject.toArray();
        int n = arrobject.length;
        float[] arrf = new float[n];
        int n2 = 0;
        while (n2 < n) {
            arrf[n2] = ((Number)Preconditions.checkNotNull(arrobject[n2])).floatValue();
            ++n2;
        }
        return arrf;
    }

    @NullableDecl
    public static Float tryParse(String string2) {
        float f;
        if (!Doubles.FLOATING_POINT_PATTERN.matcher(string2).matches()) return null;
        try {
            f = Float.parseFloat(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
        return Float.valueOf(f);
    }

    private static class FloatArrayAsList
    extends AbstractList<Float>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;
        final float[] array;
        final int end;
        final int start;

        FloatArrayAsList(float[] arrf) {
            this(arrf, 0, arrf.length);
        }

        FloatArrayAsList(float[] arrf, int n, int n2) {
            this.array = arrf;
            this.start = n;
            this.end = n2;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Float)) return false;
            if (Floats.indexOf(this.array, ((Float)object).floatValue(), this.start, this.end) == -1) return false;
            return true;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof FloatArrayAsList)) return super.equals(object);
            object = (FloatArrayAsList)object;
            int n = this.size();
            if (((FloatArrayAsList)object).size() != n) {
                return false;
            }
            int n2 = 0;
            while (n2 < n) {
                if (this.array[this.start + n2] != ((FloatArrayAsList)object).array[((FloatArrayAsList)object).start + n2]) {
                    return false;
                }
                ++n2;
            }
            return true;
        }

        @Override
        public Float get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return Float.valueOf(this.array[this.start + n]);
        }

        @Override
        public int hashCode() {
            int n = this.start;
            int n2 = 1;
            while (n < this.end) {
                n2 = n2 * 31 + Floats.hashCode(this.array[n]);
                ++n;
            }
            return n2;
        }

        @Override
        public int indexOf(Object object) {
            if (!(object instanceof Float)) return -1;
            int n = Floats.indexOf(this.array, ((Float)object).floatValue(), this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int lastIndexOf(Object object) {
            if (!(object instanceof Float)) return -1;
            int n = Floats.lastIndexOf(this.array, ((Float)object).floatValue(), this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public Float set(int n, Float f) {
            Preconditions.checkElementIndex(n, this.size());
            float[] arrf = this.array;
            int n2 = this.start;
            float f2 = arrf[n2 + n];
            arrf[n2 + n] = Preconditions.checkNotNull(f).floatValue();
            return Float.valueOf(f2);
        }

        @Override
        public int size() {
            return this.end - this.start;
        }

        @Override
        public List<Float> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            float[] arrf = this.array;
            int n3 = this.start;
            return new FloatArrayAsList(arrf, n + n3, n3 + n2);
        }

        float[] toFloatArray() {
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

    private static final class FloatConverter
    extends Converter<String, Float>
    implements Serializable {
        static final FloatConverter INSTANCE = new FloatConverter();
        private static final long serialVersionUID = 1L;

        private FloatConverter() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        protected String doBackward(Float f) {
            return f.toString();
        }

        @Override
        protected Float doForward(String string2) {
            return Float.valueOf(string2);
        }

        public String toString() {
            return "Floats.stringConverter()";
        }
    }

    private static final class LexicographicalComparator
    extends Enum<LexicographicalComparator>
    implements Comparator<float[]> {
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
        public int compare(float[] arrf, float[] arrf2) {
            int n = Math.min(arrf.length, arrf2.length);
            int n2 = 0;
            while (n2 < n) {
                int n3 = Float.compare(arrf[n2], arrf2[n2]);
                if (n3 != 0) {
                    return n3;
                }
                ++n2;
            }
            return arrf.length - arrf2.length;
        }

        public String toString() {
            return "Floats.lexicographicalComparator()";
        }
    }

}


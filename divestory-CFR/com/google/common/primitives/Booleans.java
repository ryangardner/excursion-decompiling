/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

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

public final class Booleans {
    private Booleans() {
    }

    public static List<Boolean> asList(boolean ... arrbl) {
        if (arrbl.length != 0) return new BooleanArrayAsList(arrbl);
        return Collections.emptyList();
    }

    public static int compare(boolean bl, boolean bl2) {
        if (bl == bl2) {
            return 0;
        }
        if (!bl) return -1;
        return 1;
    }

    public static boolean[] concat(boolean[] ... arrbl) {
        int n;
        int n2 = arrbl.length;
        int n3 = 0;
        for (n = 0; n < n2; n3 += arrbl[n].length, ++n) {
        }
        boolean[] arrbl2 = new boolean[n3];
        n2 = arrbl.length;
        n = 0;
        n3 = 0;
        while (n < n2) {
            boolean[] arrbl3 = arrbl[n];
            System.arraycopy(arrbl3, 0, arrbl2, n3, arrbl3.length);
            n3 += arrbl3.length;
            ++n;
        }
        return arrbl2;
    }

    public static boolean contains(boolean[] arrbl, boolean bl) {
        int n = arrbl.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrbl[n2] == bl) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static int countTrue(boolean ... arrbl) {
        int n = arrbl.length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            int n4 = n3;
            if (arrbl[n2]) {
                n4 = n3 + 1;
            }
            ++n2;
            n3 = n4;
        }
        return n3;
    }

    public static boolean[] ensureCapacity(boolean[] arrbl, int n, int n2) {
        boolean bl = true;
        boolean bl2 = n >= 0;
        Preconditions.checkArgument(bl2, "Invalid minLength: %s", n);
        bl2 = n2 >= 0 ? bl : false;
        Preconditions.checkArgument(bl2, "Invalid padding: %s", n2);
        boolean[] arrbl2 = arrbl;
        if (arrbl.length >= n) return arrbl2;
        return Arrays.copyOf(arrbl, n + n2);
    }

    public static Comparator<Boolean> falseFirst() {
        return BooleanComparator.FALSE_FIRST;
    }

    public static int hashCode(boolean bl) {
        if (!bl) return 1237;
        return 1231;
    }

    public static int indexOf(boolean[] arrbl, boolean bl) {
        return Booleans.indexOf(arrbl, bl, 0, arrbl.length);
    }

    private static int indexOf(boolean[] arrbl, boolean bl, int n, int n2) {
        while (n < n2) {
            if (arrbl[n] == bl) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static int indexOf(boolean[] arrbl, boolean[] arrbl2) {
        Preconditions.checkNotNull(arrbl, "array");
        Preconditions.checkNotNull(arrbl2, "target");
        if (arrbl2.length == 0) {
            return 0;
        }
        int n = 0;
        block0 : while (n < arrbl.length - arrbl2.length + 1) {
            int n2 = 0;
            while (n2 < arrbl2.length) {
                if (arrbl[n + n2] != arrbl2[n2]) {
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

    public static String join(String string2, boolean ... arrbl) {
        Preconditions.checkNotNull(string2);
        if (arrbl.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(arrbl.length * 7);
        stringBuilder.append(arrbl[0]);
        int n = 1;
        while (n < arrbl.length) {
            stringBuilder.append(string2);
            stringBuilder.append(arrbl[n]);
            ++n;
        }
        return stringBuilder.toString();
    }

    public static int lastIndexOf(boolean[] arrbl, boolean bl) {
        return Booleans.lastIndexOf(arrbl, bl, 0, arrbl.length);
    }

    private static int lastIndexOf(boolean[] arrbl, boolean bl, int n, int n2) {
        --n2;
        while (n2 >= n) {
            if (arrbl[n2] == bl) {
                return n2;
            }
            --n2;
        }
        return -1;
    }

    public static Comparator<boolean[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static void reverse(boolean[] arrbl) {
        Preconditions.checkNotNull(arrbl);
        Booleans.reverse(arrbl, 0, arrbl.length);
    }

    public static void reverse(boolean[] arrbl, int n, int n2) {
        Preconditions.checkNotNull(arrbl);
        Preconditions.checkPositionIndexes(n, n2, arrbl.length);
        --n2;
        while (n < n2) {
            boolean bl = arrbl[n];
            arrbl[n] = arrbl[n2];
            arrbl[n2] = bl;
            ++n;
            --n2;
        }
    }

    public static boolean[] toArray(Collection<Boolean> arrobject) {
        if (arrobject instanceof BooleanArrayAsList) {
            return ((BooleanArrayAsList)arrobject).toBooleanArray();
        }
        arrobject = arrobject.toArray();
        int n = arrobject.length;
        boolean[] arrbl = new boolean[n];
        int n2 = 0;
        while (n2 < n) {
            arrbl[n2] = (Boolean)Preconditions.checkNotNull(arrobject[n2]);
            ++n2;
        }
        return arrbl;
    }

    public static Comparator<Boolean> trueFirst() {
        return BooleanComparator.TRUE_FIRST;
    }

    private static class BooleanArrayAsList
    extends AbstractList<Boolean>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;
        final boolean[] array;
        final int end;
        final int start;

        BooleanArrayAsList(boolean[] arrbl) {
            this(arrbl, 0, arrbl.length);
        }

        BooleanArrayAsList(boolean[] arrbl, int n, int n2) {
            this.array = arrbl;
            this.start = n;
            this.end = n2;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Boolean)) return false;
            if (Booleans.indexOf(this.array, (Boolean)object, this.start, this.end) == -1) return false;
            return true;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof BooleanArrayAsList)) return super.equals(object);
            object = (BooleanArrayAsList)object;
            int n = this.size();
            if (((BooleanArrayAsList)object).size() != n) {
                return false;
            }
            int n2 = 0;
            while (n2 < n) {
                if (this.array[this.start + n2] != ((BooleanArrayAsList)object).array[((BooleanArrayAsList)object).start + n2]) {
                    return false;
                }
                ++n2;
            }
            return true;
        }

        @Override
        public Boolean get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }

        @Override
        public int hashCode() {
            int n = this.start;
            int n2 = 1;
            while (n < this.end) {
                n2 = n2 * 31 + Booleans.hashCode(this.array[n]);
                ++n;
            }
            return n2;
        }

        @Override
        public int indexOf(Object object) {
            if (!(object instanceof Boolean)) return -1;
            int n = Booleans.indexOf(this.array, (Boolean)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int lastIndexOf(Object object) {
            if (!(object instanceof Boolean)) return -1;
            int n = Booleans.lastIndexOf(this.array, (Boolean)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public Boolean set(int n, Boolean bl) {
            Preconditions.checkElementIndex(n, this.size());
            boolean[] arrbl = this.array;
            int n2 = this.start;
            boolean bl2 = arrbl[n2 + n];
            arrbl[n2 + n] = Preconditions.checkNotNull(bl);
            return bl2;
        }

        @Override
        public int size() {
            return this.end - this.start;
        }

        @Override
        public List<Boolean> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            boolean[] arrbl = this.array;
            int n3 = this.start;
            return new BooleanArrayAsList(arrbl, n + n3, n3 + n2);
        }

        boolean[] toBooleanArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 7);
            String string2 = this.array[this.start] ? "[true" : "[false";
            stringBuilder.append(string2);
            int n = this.start;
            do {
                if (++n >= this.end) {
                    stringBuilder.append(']');
                    return stringBuilder.toString();
                }
                string2 = this.array[n] ? ", true" : ", false";
                stringBuilder.append(string2);
            } while (true);
        }
    }

    private static final class BooleanComparator
    extends Enum<BooleanComparator>
    implements Comparator<Boolean> {
        private static final /* synthetic */ BooleanComparator[] $VALUES;
        public static final /* enum */ BooleanComparator FALSE_FIRST;
        public static final /* enum */ BooleanComparator TRUE_FIRST;
        private final String toString;
        private final int trueValue;

        static {
            BooleanComparator booleanComparator;
            TRUE_FIRST = new BooleanComparator(1, "Booleans.trueFirst()");
            FALSE_FIRST = booleanComparator = new BooleanComparator(-1, "Booleans.falseFirst()");
            $VALUES = new BooleanComparator[]{TRUE_FIRST, booleanComparator};
        }

        private BooleanComparator(int n2, String string3) {
            this.trueValue = n2;
            this.toString = string3;
        }

        public static BooleanComparator valueOf(String string2) {
            return Enum.valueOf(BooleanComparator.class, string2);
        }

        public static BooleanComparator[] values() {
            return (BooleanComparator[])$VALUES.clone();
        }

        @Override
        public int compare(Boolean bl, Boolean bl2) {
            boolean bl3 = bl;
            int n = 0;
            int n2 = bl3 ? this.trueValue : 0;
            if (bl2 == false) return n - n2;
            n = this.trueValue;
            return n - n2;
        }

        public String toString() {
            return this.toString;
        }
    }

    private static final class LexicographicalComparator
    extends Enum<LexicographicalComparator>
    implements Comparator<boolean[]> {
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
        public int compare(boolean[] arrbl, boolean[] arrbl2) {
            int n = Math.min(arrbl.length, arrbl2.length);
            int n2 = 0;
            while (n2 < n) {
                int n3 = Booleans.compare(arrbl[n2], arrbl2[n2]);
                if (n3 != 0) {
                    return n3;
                }
                ++n2;
            }
            return arrbl.length - arrbl2.length;
        }

        public String toString() {
            return "Booleans.lexicographicalComparator()";
        }
    }

}


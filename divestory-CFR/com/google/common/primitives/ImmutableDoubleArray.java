/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
public final class ImmutableDoubleArray
implements Serializable {
    private static final ImmutableDoubleArray EMPTY = new ImmutableDoubleArray(new double[0]);
    private final double[] array;
    private final int end;
    private final transient int start;

    private ImmutableDoubleArray(double[] arrd) {
        this(arrd, 0, arrd.length);
    }

    private ImmutableDoubleArray(double[] arrd, int n, int n2) {
        this.array = arrd;
        this.start = n;
        this.end = n2;
    }

    private static boolean areEqual(double d, double d2) {
        if (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2)) return false;
        return true;
    }

    public static Builder builder() {
        return new Builder(10);
    }

    public static Builder builder(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "Invalid initialCapacity: %s", n);
        return new Builder(n);
    }

    public static ImmutableDoubleArray copyOf(Iterable<Double> iterable) {
        if (!(iterable instanceof Collection)) return ImmutableDoubleArray.builder().addAll(iterable).build();
        return ImmutableDoubleArray.copyOf((Collection)iterable);
    }

    public static ImmutableDoubleArray copyOf(Collection<Double> object) {
        if (!object.isEmpty()) return new ImmutableDoubleArray(Doubles.toArray((Collection<? extends Number>)object));
        return EMPTY;
    }

    public static ImmutableDoubleArray copyOf(double[] object) {
        if (((double[])object).length != 0) return new ImmutableDoubleArray(Arrays.copyOf(object, ((double[])object).length));
        return EMPTY;
    }

    private boolean isPartialView() {
        if (this.start > 0) return true;
        if (this.end < this.array.length) return true;
        return false;
    }

    public static ImmutableDoubleArray of() {
        return EMPTY;
    }

    public static ImmutableDoubleArray of(double d) {
        return new ImmutableDoubleArray(new double[]{d});
    }

    public static ImmutableDoubleArray of(double d, double d2) {
        return new ImmutableDoubleArray(new double[]{d, d2});
    }

    public static ImmutableDoubleArray of(double d, double d2, double d3) {
        return new ImmutableDoubleArray(new double[]{d, d2, d3});
    }

    public static ImmutableDoubleArray of(double d, double d2, double d3, double d4) {
        return new ImmutableDoubleArray(new double[]{d, d2, d3, d4});
    }

    public static ImmutableDoubleArray of(double d, double d2, double d3, double d4, double d5) {
        return new ImmutableDoubleArray(new double[]{d, d2, d3, d4, d5});
    }

    public static ImmutableDoubleArray of(double d, double d2, double d3, double d4, double d5, double d6) {
        return new ImmutableDoubleArray(new double[]{d, d2, d3, d4, d5, d6});
    }

    public static ImmutableDoubleArray of(double d, double ... arrd) {
        boolean bl = arrd.length <= 2147483646;
        Preconditions.checkArgument(bl, "the total number of elements must fit in an int");
        double[] arrd2 = new double[arrd.length + 1];
        arrd2[0] = d;
        System.arraycopy(arrd, 0, arrd2, 1, arrd.length);
        return new ImmutableDoubleArray(arrd2);
    }

    public List<Double> asList() {
        return new AsList(this);
    }

    public boolean contains(double d) {
        if (this.indexOf(d) < 0) return false;
        return true;
    }

    public boolean equals(@NullableDecl Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ImmutableDoubleArray)) {
            return false;
        }
        object = (ImmutableDoubleArray)object;
        if (this.length() != ((ImmutableDoubleArray)object).length()) {
            return false;
        }
        int n = 0;
        while (n < this.length()) {
            if (!ImmutableDoubleArray.areEqual(this.get(n), ((ImmutableDoubleArray)object).get(n))) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public double get(int n) {
        Preconditions.checkElementIndex(n, this.length());
        return this.array[this.start + n];
    }

    public int hashCode() {
        int n = this.start;
        int n2 = 1;
        while (n < this.end) {
            n2 = n2 * 31 + Doubles.hashCode(this.array[n]);
            ++n;
        }
        return n2;
    }

    public int indexOf(double d) {
        int n = this.start;
        while (n < this.end) {
            if (ImmutableDoubleArray.areEqual(this.array[n], d)) {
                return n - this.start;
            }
            ++n;
        }
        return -1;
    }

    public boolean isEmpty() {
        if (this.end != this.start) return false;
        return true;
    }

    public int lastIndexOf(double d) {
        int n = this.end - 1;
        while (n >= this.start) {
            if (ImmutableDoubleArray.areEqual(this.array[n], d)) {
                return n - this.start;
            }
            --n;
        }
        return -1;
    }

    public int length() {
        return this.end - this.start;
    }

    Object readResolve() {
        if (!this.isEmpty()) return this;
        return EMPTY;
    }

    public ImmutableDoubleArray subArray(int n, int n2) {
        Preconditions.checkPositionIndexes(n, n2, this.length());
        if (n == n2) {
            return EMPTY;
        }
        Object object = this.array;
        int n3 = this.start;
        return new ImmutableDoubleArray((double[])object, n + n3, n3 + n2);
    }

    public double[] toArray() {
        return Arrays.copyOfRange(this.array, this.start, this.end);
    }

    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder(this.length() * 5);
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

    public ImmutableDoubleArray trimmed() {
        if (!this.isPartialView()) return this;
        return new ImmutableDoubleArray(this.toArray());
    }

    Object writeReplace() {
        return this.trimmed();
    }

    static class AsList
    extends AbstractList<Double>
    implements RandomAccess,
    Serializable {
        private final ImmutableDoubleArray parent;

        private AsList(ImmutableDoubleArray immutableDoubleArray) {
            this.parent = immutableDoubleArray;
        }

        @Override
        public boolean contains(Object object) {
            if (this.indexOf(object) < 0) return false;
            return true;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (object instanceof AsList) {
                object = (AsList)object;
                return this.parent.equals(((AsList)object).parent);
            }
            if (!(object instanceof List)) {
                return false;
            }
            object = (List)object;
            if (this.size() != object.size()) {
                return false;
            }
            int n = this.parent.start;
            Iterator iterator2 = object.iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                if (!(object instanceof Double)) return false;
                if (!ImmutableDoubleArray.areEqual(this.parent.array[n], (Double)object)) {
                    return false;
                }
                ++n;
            }
            return true;
        }

        @Override
        public Double get(int n) {
            return this.parent.get(n);
        }

        @Override
        public int hashCode() {
            return this.parent.hashCode();
        }

        @Override
        public int indexOf(Object object) {
            if (!(object instanceof Double)) return -1;
            return this.parent.indexOf((Double)object);
        }

        @Override
        public int lastIndexOf(Object object) {
            if (!(object instanceof Double)) return -1;
            return this.parent.lastIndexOf((Double)object);
        }

        @Override
        public int size() {
            return this.parent.length();
        }

        @Override
        public List<Double> subList(int n, int n2) {
            return this.parent.subArray(n, n2).asList();
        }

        @Override
        public String toString() {
            return this.parent.toString();
        }
    }

    public static final class Builder {
        private double[] array;
        private int count = 0;

        Builder(int n) {
            this.array = new double[n];
        }

        private void ensureRoomFor(int n) {
            double[] arrd = this.array;
            if ((n = this.count + n) <= arrd.length) return;
            arrd = new double[Builder.expandedCapacity(arrd.length, n)];
            System.arraycopy(this.array, 0, arrd, 0, this.count);
            this.array = arrd;
        }

        private static int expandedCapacity(int n, int n2) {
            int n3;
            if (n2 < 0) throw new AssertionError((Object)"cannot store more than MAX_VALUE elements");
            n = n3 = n + (n >> 1) + 1;
            if (n3 < n2) {
                n = Integer.highestOneBit(n2 - 1) << 1;
            }
            n2 = n;
            if (n >= 0) return n2;
            return Integer.MAX_VALUE;
        }

        public Builder add(double d) {
            this.ensureRoomFor(1);
            double[] arrd = this.array;
            int n = this.count;
            arrd[n] = d;
            this.count = n + 1;
            return this;
        }

        public Builder addAll(ImmutableDoubleArray immutableDoubleArray) {
            this.ensureRoomFor(immutableDoubleArray.length());
            System.arraycopy(immutableDoubleArray.array, immutableDoubleArray.start, this.array, this.count, immutableDoubleArray.length());
            this.count += immutableDoubleArray.length();
            return this;
        }

        public Builder addAll(Iterable<Double> object) {
            if (object instanceof Collection) {
                return this.addAll((Collection)object);
            }
            object = object.iterator();
            while (object.hasNext()) {
                this.add((Double)object.next());
            }
            return this;
        }

        public Builder addAll(Collection<Double> arrd) {
            this.ensureRoomFor(arrd.size());
            Iterator<Double> iterator2 = arrd.iterator();
            while (iterator2.hasNext()) {
                Double d = iterator2.next();
                arrd = this.array;
                int n = this.count;
                this.count = n + 1;
                arrd[n] = d;
            }
            return this;
        }

        public Builder addAll(double[] arrd) {
            this.ensureRoomFor(arrd.length);
            System.arraycopy(arrd, 0, this.array, this.count, arrd.length);
            this.count += arrd.length;
            return this;
        }

        @CheckReturnValue
        public ImmutableDoubleArray build() {
            if (this.count != 0) return new ImmutableDoubleArray(this.array, 0, this.count);
            return EMPTY;
        }
    }

}


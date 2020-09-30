/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
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
public final class ImmutableIntArray
implements Serializable {
    private static final ImmutableIntArray EMPTY = new ImmutableIntArray(new int[0]);
    private final int[] array;
    private final int end;
    private final transient int start;

    private ImmutableIntArray(int[] arrn) {
        this(arrn, 0, arrn.length);
    }

    private ImmutableIntArray(int[] arrn, int n, int n2) {
        this.array = arrn;
        this.start = n;
        this.end = n2;
    }

    public static Builder builder() {
        return new Builder(10);
    }

    public static Builder builder(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "Invalid initialCapacity: %s", n);
        return new Builder(n);
    }

    public static ImmutableIntArray copyOf(Iterable<Integer> iterable) {
        if (!(iterable instanceof Collection)) return ImmutableIntArray.builder().addAll(iterable).build();
        return ImmutableIntArray.copyOf((Collection)iterable);
    }

    public static ImmutableIntArray copyOf(Collection<Integer> object) {
        if (!object.isEmpty()) return new ImmutableIntArray(Ints.toArray((Collection<? extends Number>)object));
        return EMPTY;
    }

    public static ImmutableIntArray copyOf(int[] object) {
        if (((int[])object).length != 0) return new ImmutableIntArray(Arrays.copyOf(object, ((int[])object).length));
        return EMPTY;
    }

    private boolean isPartialView() {
        if (this.start > 0) return true;
        if (this.end < this.array.length) return true;
        return false;
    }

    public static ImmutableIntArray of() {
        return EMPTY;
    }

    public static ImmutableIntArray of(int n) {
        return new ImmutableIntArray(new int[]{n});
    }

    public static ImmutableIntArray of(int n, int n2) {
        return new ImmutableIntArray(new int[]{n, n2});
    }

    public static ImmutableIntArray of(int n, int n2, int n3) {
        return new ImmutableIntArray(new int[]{n, n2, n3});
    }

    public static ImmutableIntArray of(int n, int n2, int n3, int n4) {
        return new ImmutableIntArray(new int[]{n, n2, n3, n4});
    }

    public static ImmutableIntArray of(int n, int n2, int n3, int n4, int n5) {
        return new ImmutableIntArray(new int[]{n, n2, n3, n4, n5});
    }

    public static ImmutableIntArray of(int n, int n2, int n3, int n4, int n5, int n6) {
        return new ImmutableIntArray(new int[]{n, n2, n3, n4, n5, n6});
    }

    public static ImmutableIntArray of(int n, int ... arrn) {
        boolean bl = arrn.length <= 2147483646;
        Preconditions.checkArgument(bl, "the total number of elements must fit in an int");
        int[] arrn2 = new int[arrn.length + 1];
        arrn2[0] = n;
        System.arraycopy(arrn, 0, arrn2, 1, arrn.length);
        return new ImmutableIntArray(arrn2);
    }

    public List<Integer> asList() {
        return new AsList(this);
    }

    public boolean contains(int n) {
        if (this.indexOf(n) < 0) return false;
        return true;
    }

    public boolean equals(@NullableDecl Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ImmutableIntArray)) {
            return false;
        }
        object = (ImmutableIntArray)object;
        if (this.length() != ((ImmutableIntArray)object).length()) {
            return false;
        }
        int n = 0;
        while (n < this.length()) {
            if (this.get(n) != ((ImmutableIntArray)object).get(n)) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public int get(int n) {
        Preconditions.checkElementIndex(n, this.length());
        return this.array[this.start + n];
    }

    public int hashCode() {
        int n = this.start;
        int n2 = 1;
        while (n < this.end) {
            n2 = n2 * 31 + Ints.hashCode(this.array[n]);
            ++n;
        }
        return n2;
    }

    public int indexOf(int n) {
        int n2 = this.start;
        while (n2 < this.end) {
            if (this.array[n2] == n) {
                return n2 - this.start;
            }
            ++n2;
        }
        return -1;
    }

    public boolean isEmpty() {
        if (this.end != this.start) return false;
        return true;
    }

    public int lastIndexOf(int n) {
        int n2;
        int n3 = this.end - 1;
        while (n3 >= (n2 = this.start)) {
            if (this.array[n3] == n) {
                return n3 - n2;
            }
            --n3;
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

    public ImmutableIntArray subArray(int n, int n2) {
        Preconditions.checkPositionIndexes(n, n2, this.length());
        if (n == n2) {
            return EMPTY;
        }
        Object object = this.array;
        int n3 = this.start;
        return new ImmutableIntArray((int[])object, n + n3, n3 + n2);
    }

    public int[] toArray() {
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

    public ImmutableIntArray trimmed() {
        if (!this.isPartialView()) return this;
        return new ImmutableIntArray(this.toArray());
    }

    Object writeReplace() {
        return this.trimmed();
    }

    static class AsList
    extends AbstractList<Integer>
    implements RandomAccess,
    Serializable {
        private final ImmutableIntArray parent;

        private AsList(ImmutableIntArray immutableIntArray) {
            this.parent = immutableIntArray;
        }

        @Override
        public boolean contains(Object object) {
            if (this.indexOf(object) < 0) return false;
            return true;
        }

        @Override
        public boolean equals(@NullableDecl Object iterator2) {
            if (iterator2 instanceof AsList) {
                iterator2 = (AsList)((Object)iterator2);
                return this.parent.equals(((AsList)iterator2).parent);
            }
            if (!(iterator2 instanceof List)) {
                return false;
            }
            iterator2 = (List)((Object)iterator2);
            if (this.size() != iterator2.size()) {
                return false;
            }
            int n = this.parent.start;
            iterator2 = iterator2.iterator();
            while (iterator2.hasNext()) {
                Object e = iterator2.next();
                if (!(e instanceof Integer)) return false;
                if (this.parent.array[n] != (Integer)e) {
                    return false;
                }
                ++n;
            }
            return true;
        }

        @Override
        public Integer get(int n) {
            return this.parent.get(n);
        }

        @Override
        public int hashCode() {
            return this.parent.hashCode();
        }

        @Override
        public int indexOf(Object object) {
            if (!(object instanceof Integer)) return -1;
            return this.parent.indexOf((Integer)object);
        }

        @Override
        public int lastIndexOf(Object object) {
            if (!(object instanceof Integer)) return -1;
            return this.parent.lastIndexOf((Integer)object);
        }

        @Override
        public int size() {
            return this.parent.length();
        }

        @Override
        public List<Integer> subList(int n, int n2) {
            return this.parent.subArray(n, n2).asList();
        }

        @Override
        public String toString() {
            return this.parent.toString();
        }
    }

    public static final class Builder {
        private int[] array;
        private int count = 0;

        Builder(int n) {
            this.array = new int[n];
        }

        private void ensureRoomFor(int n) {
            int[] arrn = this.array;
            if ((n = this.count + n) <= arrn.length) return;
            arrn = new int[Builder.expandedCapacity(arrn.length, n)];
            System.arraycopy(this.array, 0, arrn, 0, this.count);
            this.array = arrn;
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

        public Builder add(int n) {
            this.ensureRoomFor(1);
            int[] arrn = this.array;
            int n2 = this.count;
            arrn[n2] = n;
            this.count = n2 + 1;
            return this;
        }

        public Builder addAll(ImmutableIntArray immutableIntArray) {
            this.ensureRoomFor(immutableIntArray.length());
            System.arraycopy(immutableIntArray.array, immutableIntArray.start, this.array, this.count, immutableIntArray.length());
            this.count += immutableIntArray.length();
            return this;
        }

        public Builder addAll(Iterable<Integer> object) {
            if (object instanceof Collection) {
                return this.addAll((Collection)object);
            }
            object = object.iterator();
            while (object.hasNext()) {
                this.add((Integer)object.next());
            }
            return this;
        }

        public Builder addAll(Collection<Integer> object) {
            this.ensureRoomFor(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                Integer n = (Integer)object.next();
                int[] arrn = this.array;
                int n2 = this.count;
                this.count = n2 + 1;
                arrn[n2] = n;
            }
            return this;
        }

        public Builder addAll(int[] arrn) {
            this.ensureRoomFor(arrn.length);
            System.arraycopy(arrn, 0, this.array, this.count, arrn.length);
            this.count += arrn.length;
            return this;
        }

        @CheckReturnValue
        public ImmutableIntArray build() {
            if (this.count != 0) return new ImmutableIntArray(this.array, 0, this.count);
            return EMPTY;
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
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
public final class ImmutableLongArray
implements Serializable {
    private static final ImmutableLongArray EMPTY = new ImmutableLongArray(new long[0]);
    private final long[] array;
    private final int end;
    private final transient int start;

    private ImmutableLongArray(long[] arrl) {
        this(arrl, 0, arrl.length);
    }

    private ImmutableLongArray(long[] arrl, int n, int n2) {
        this.array = arrl;
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

    public static ImmutableLongArray copyOf(Iterable<Long> iterable) {
        if (!(iterable instanceof Collection)) return ImmutableLongArray.builder().addAll(iterable).build();
        return ImmutableLongArray.copyOf((Collection)iterable);
    }

    public static ImmutableLongArray copyOf(Collection<Long> object) {
        if (!object.isEmpty()) return new ImmutableLongArray(Longs.toArray((Collection<? extends Number>)object));
        return EMPTY;
    }

    public static ImmutableLongArray copyOf(long[] object) {
        if (((long[])object).length != 0) return new ImmutableLongArray(Arrays.copyOf(object, ((long[])object).length));
        return EMPTY;
    }

    private boolean isPartialView() {
        if (this.start > 0) return true;
        if (this.end < this.array.length) return true;
        return false;
    }

    public static ImmutableLongArray of() {
        return EMPTY;
    }

    public static ImmutableLongArray of(long l) {
        return new ImmutableLongArray(new long[]{l});
    }

    public static ImmutableLongArray of(long l, long l2) {
        return new ImmutableLongArray(new long[]{l, l2});
    }

    public static ImmutableLongArray of(long l, long l2, long l3) {
        return new ImmutableLongArray(new long[]{l, l2, l3});
    }

    public static ImmutableLongArray of(long l, long l2, long l3, long l4) {
        return new ImmutableLongArray(new long[]{l, l2, l3, l4});
    }

    public static ImmutableLongArray of(long l, long l2, long l3, long l4, long l5) {
        return new ImmutableLongArray(new long[]{l, l2, l3, l4, l5});
    }

    public static ImmutableLongArray of(long l, long l2, long l3, long l4, long l5, long l6) {
        return new ImmutableLongArray(new long[]{l, l2, l3, l4, l5, l6});
    }

    public static ImmutableLongArray of(long l, long ... arrl) {
        boolean bl = arrl.length <= 2147483646;
        Preconditions.checkArgument(bl, "the total number of elements must fit in an int");
        long[] arrl2 = new long[arrl.length + 1];
        arrl2[0] = l;
        System.arraycopy(arrl, 0, arrl2, 1, arrl.length);
        return new ImmutableLongArray(arrl2);
    }

    public List<Long> asList() {
        return new AsList(this);
    }

    public boolean contains(long l) {
        if (this.indexOf(l) < 0) return false;
        return true;
    }

    public boolean equals(@NullableDecl Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ImmutableLongArray)) {
            return false;
        }
        object = (ImmutableLongArray)object;
        if (this.length() != ((ImmutableLongArray)object).length()) {
            return false;
        }
        int n = 0;
        while (n < this.length()) {
            if (this.get(n) != ((ImmutableLongArray)object).get(n)) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public long get(int n) {
        Preconditions.checkElementIndex(n, this.length());
        return this.array[this.start + n];
    }

    public int hashCode() {
        int n = this.start;
        int n2 = 1;
        while (n < this.end) {
            n2 = n2 * 31 + Longs.hashCode(this.array[n]);
            ++n;
        }
        return n2;
    }

    public int indexOf(long l) {
        int n = this.start;
        while (n < this.end) {
            if (this.array[n] == l) {
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

    public int lastIndexOf(long l) {
        int n;
        int n2 = this.end - 1;
        while (n2 >= (n = this.start)) {
            if (this.array[n2] == l) {
                return n2 - n;
            }
            --n2;
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

    public ImmutableLongArray subArray(int n, int n2) {
        Preconditions.checkPositionIndexes(n, n2, this.length());
        if (n == n2) {
            return EMPTY;
        }
        Object object = this.array;
        int n3 = this.start;
        return new ImmutableLongArray((long[])object, n + n3, n3 + n2);
    }

    public long[] toArray() {
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

    public ImmutableLongArray trimmed() {
        if (!this.isPartialView()) return this;
        return new ImmutableLongArray(this.toArray());
    }

    Object writeReplace() {
        return this.trimmed();
    }

    static class AsList
    extends AbstractList<Long>
    implements RandomAccess,
    Serializable {
        private final ImmutableLongArray parent;

        private AsList(ImmutableLongArray immutableLongArray) {
            this.parent = immutableLongArray;
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
                if (!(object instanceof Long)) return false;
                if (this.parent.array[n] != (Long)object) {
                    return false;
                }
                ++n;
            }
            return true;
        }

        @Override
        public Long get(int n) {
            return this.parent.get(n);
        }

        @Override
        public int hashCode() {
            return this.parent.hashCode();
        }

        @Override
        public int indexOf(Object object) {
            if (!(object instanceof Long)) return -1;
            return this.parent.indexOf((Long)object);
        }

        @Override
        public int lastIndexOf(Object object) {
            if (!(object instanceof Long)) return -1;
            return this.parent.lastIndexOf((Long)object);
        }

        @Override
        public int size() {
            return this.parent.length();
        }

        @Override
        public List<Long> subList(int n, int n2) {
            return this.parent.subArray(n, n2).asList();
        }

        @Override
        public String toString() {
            return this.parent.toString();
        }
    }

    public static final class Builder {
        private long[] array;
        private int count = 0;

        Builder(int n) {
            this.array = new long[n];
        }

        private void ensureRoomFor(int n) {
            long[] arrl = this.array;
            if ((n = this.count + n) <= arrl.length) return;
            arrl = new long[Builder.expandedCapacity(arrl.length, n)];
            System.arraycopy(this.array, 0, arrl, 0, this.count);
            this.array = arrl;
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

        public Builder add(long l) {
            this.ensureRoomFor(1);
            long[] arrl = this.array;
            int n = this.count;
            arrl[n] = l;
            this.count = n + 1;
            return this;
        }

        public Builder addAll(ImmutableLongArray immutableLongArray) {
            this.ensureRoomFor(immutableLongArray.length());
            System.arraycopy(immutableLongArray.array, immutableLongArray.start, this.array, this.count, immutableLongArray.length());
            this.count += immutableLongArray.length();
            return this;
        }

        public Builder addAll(Iterable<Long> object) {
            if (object instanceof Collection) {
                return this.addAll((Collection)object);
            }
            object = object.iterator();
            while (object.hasNext()) {
                this.add((Long)object.next());
            }
            return this;
        }

        public Builder addAll(Collection<Long> object) {
            this.ensureRoomFor(object.size());
            Iterator<Long> iterator2 = object.iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                long[] arrl = this.array;
                int n = this.count;
                this.count = n + 1;
                arrl[n] = (Long)object;
            }
            return this;
        }

        public Builder addAll(long[] arrl) {
            this.ensureRoomFor(arrl.length);
            System.arraycopy(arrl, 0, this.array, this.count, arrl.length);
            this.count += arrl.length;
            return this;
        }

        @CheckReturnValue
        public ImmutableLongArray build() {
            if (this.count != 0) return new ImmutableLongArray(this.array, 0, this.count);
            return EMPTY;
        }
    }

}


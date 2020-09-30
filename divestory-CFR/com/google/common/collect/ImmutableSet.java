/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.RegularImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.SingletonImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableSet<E>
extends ImmutableCollection<E>
implements Set<E> {
    private static final int CUTOFF = 751619276;
    private static final double DESIRED_LOAD_FACTOR = 0.7;
    static final int MAX_TABLE_SIZE = 1073741824;
    @LazyInit
    @NullableDecl
    private transient ImmutableList<E> asList;

    ImmutableSet() {
    }

    public static <E> Builder<E> builder() {
        return new Builder();
    }

    public static <E> Builder<E> builderWithExpectedSize(int n) {
        CollectPreconditions.checkNonnegative(n, "expectedSize");
        return new Builder(n);
    }

    static int chooseTableSize(int n) {
        int n2 = Math.max(n, 2);
        boolean bl = true;
        if (n2 < 751619276) {
            n = Integer.highestOneBit(n2 - 1) << 1;
            while ((double)n * 0.7 < (double)n2) {
                n <<= 1;
            }
            return n;
        }
        if (n2 >= 1073741824) {
            bl = false;
        }
        Preconditions.checkArgument(bl, "collection too large");
        return 1073741824;
    }

    private static <E> ImmutableSet<E> construct(int n, Object ... arrobject) {
        if (n == 0) return ImmutableSet.of();
        if (n == 1) return ImmutableSet.of(arrobject[0]);
        int n2 = ImmutableSet.chooseTableSize(n);
        Object[] arrobject2 = new Object[n2];
        int n3 = n2 - 1;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        block0 : do {
            Object[] arrobject3;
            if (n4 >= n) {
                Arrays.fill(arrobject, n6, n, null);
                if (n6 == 1) {
                    return new SingletonImmutableSet<Object>(arrobject[0], n5);
                }
                if (ImmutableSet.chooseTableSize(n6) < n2 / 2) {
                    return ImmutableSet.construct(n6, arrobject);
                }
                arrobject3 = arrobject;
                if (!ImmutableSet.shouldTrim(n6, arrobject.length)) return new RegularImmutableSet(arrobject3, n5, arrobject2, n3, n6);
                arrobject3 = Arrays.copyOf(arrobject, n6);
                return new RegularImmutableSet(arrobject3, n5, arrobject2, n3, n6);
            }
            arrobject3 = ObjectArrays.checkElementNotNull(arrobject[n4], n4);
            int n7 = arrobject3.hashCode();
            int n8 = Hashing.smear(n7);
            do {
                block10 : {
                    block9 : {
                        Object object;
                        block8 : {
                            int n9;
                            if ((object = arrobject2[n9 = n8 & n3]) != null) break block8;
                            arrobject[n6] = arrobject3;
                            arrobject2[n9] = arrobject3;
                            n5 += n7;
                            ++n6;
                            break block9;
                        }
                        if (!object.equals(arrobject3)) break block10;
                    }
                    ++n4;
                    continue block0;
                }
                ++n8;
            } while (true);
            break;
        } while (true);
    }

    public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> iterable) {
        if (!(iterable instanceof Collection)) return ImmutableSet.copyOf(iterable.iterator());
        return ImmutableSet.copyOf((Collection)iterable);
    }

    public static <E> ImmutableSet<E> copyOf(Collection<? extends E> arrobject) {
        ImmutableSet immutableSet;
        if (arrobject instanceof ImmutableSet && !(arrobject instanceof SortedSet) && !(immutableSet = (ImmutableSet)arrobject).isPartialView()) {
            return immutableSet;
        }
        arrobject = arrobject.toArray();
        return ImmutableSet.construct(arrobject.length, arrobject);
    }

    public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> iterator2) {
        if (!iterator2.hasNext()) {
            return ImmutableSet.of();
        }
        E e = iterator2.next();
        if (iterator2.hasNext()) return ((Builder)((Builder)new Builder().add((Object)e)).addAll(iterator2)).build();
        return ImmutableSet.of(e);
    }

    public static <E> ImmutableSet<E> copyOf(E[] arrE) {
        int n = arrE.length;
        if (n == 0) return ImmutableSet.of();
        if (n == 1) return ImmutableSet.of(arrE[0]);
        return ImmutableSet.construct(arrE.length, (Object[])arrE.clone());
    }

    public static <E> ImmutableSet<E> of() {
        return RegularImmutableSet.EMPTY;
    }

    public static <E> ImmutableSet<E> of(E e) {
        return new SingletonImmutableSet<E>(e);
    }

    public static <E> ImmutableSet<E> of(E e, E e2) {
        return ImmutableSet.construct(2, e, e2);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3) {
        return ImmutableSet.construct(3, e, e2, e3);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4) {
        return ImmutableSet.construct(4, e, e2, e3, e4);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5) {
        return ImmutableSet.construct(5, e, e2, e3, e4, e5);
    }

    @SafeVarargs
    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5, E e6, E ... arrE) {
        boolean bl = arrE.length <= 2147483641;
        Preconditions.checkArgument(bl, "the total number of elements must fit in an int");
        int n = arrE.length + 6;
        Object[] arrobject = new Object[n];
        arrobject[0] = e;
        arrobject[1] = e2;
        arrobject[2] = e3;
        arrobject[3] = e4;
        arrobject[4] = e5;
        arrobject[5] = e6;
        System.arraycopy(arrE, 0, arrobject, 6, arrE.length);
        return ImmutableSet.construct(n, arrobject);
    }

    private static boolean shouldTrim(int n, int n2) {
        if (n >= (n2 >> 1) + (n2 >> 2)) return false;
        return true;
    }

    @Override
    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList;
        ImmutableList<E> immutableList2 = immutableList = this.asList;
        if (immutableList != null) return immutableList2;
        this.asList = immutableList2 = this.createAsList();
        return immutableList2;
    }

    ImmutableList<E> createAsList() {
        return ImmutableList.asImmutableList(this.toArray());
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ImmutableSet)) return Sets.equalsImpl(this, object);
        if (!this.isHashCodeFast()) return Sets.equalsImpl(this, object);
        if (!((ImmutableSet)object).isHashCodeFast()) return Sets.equalsImpl(this, object);
        if (this.hashCode() == object.hashCode()) return Sets.equalsImpl(this, object);
        return false;
    }

    @Override
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    boolean isHashCodeFast() {
        return false;
    }

    @Override
    public abstract UnmodifiableIterator<E> iterator();

    @Override
    Object writeReplace() {
        return new SerializedForm(this.toArray());
    }

    public static class Builder<E>
    extends ImmutableCollection.ArrayBasedBuilder<E> {
        private int hashCode;
        @NullableDecl
        Object[] hashTable;

        public Builder() {
            super(4);
        }

        Builder(int n) {
            super(n);
            this.hashTable = new Object[ImmutableSet.chooseTableSize(n)];
        }

        private void addDeduping(E e) {
            int n = this.hashTable.length;
            int n2 = e.hashCode();
            int n3 = Hashing.smear(n2);
            do {
                Object object;
                Object[] arrobject;
                if ((object = (arrobject = this.hashTable)[n3 &= n - 1]) == null) {
                    arrobject[n3] = e;
                    this.hashCode += n2;
                    super.add((Object)e);
                    return;
                }
                if (object.equals(e)) {
                    return;
                }
                ++n3;
            } while (true);
        }

        @Override
        public Builder<E> add(E e) {
            Preconditions.checkNotNull(e);
            if (this.hashTable != null && ImmutableSet.chooseTableSize(this.size) <= this.hashTable.length) {
                this.addDeduping(e);
                return this;
            }
            this.hashTable = null;
            super.add((Object)e);
            return this;
        }

        @Override
        public Builder<E> add(E ... arrE) {
            if (this.hashTable == null) {
                super.add(arrE);
                return this;
            }
            int n = arrE.length;
            int n2 = 0;
            while (n2 < n) {
                this.add((Object)arrE[n2]);
                ++n2;
            }
            return this;
        }

        @Override
        public Builder<E> addAll(Iterable<? extends E> object) {
            Preconditions.checkNotNull(object);
            if (this.hashTable == null) {
                super.addAll(object);
                return this;
            }
            object = object.iterator();
            while (object.hasNext()) {
                this.add(object.next());
            }
            return this;
        }

        @Override
        public Builder<E> addAll(Iterator<? extends E> iterator2) {
            Preconditions.checkNotNull(iterator2);
            while (iterator2.hasNext()) {
                this.add((Object)iterator2.next());
            }
            return this;
        }

        @Override
        public ImmutableSet<E> build() {
            Object object;
            int n = this.size;
            if (n == 0) return ImmutableSet.of();
            if (n == 1) return ImmutableSet.of(this.contents[0]);
            if (this.hashTable != null && ImmutableSet.chooseTableSize(this.size) == this.hashTable.length) {
                object = ImmutableSet.shouldTrim(this.size, this.contents.length) ? Arrays.copyOf(this.contents, this.size) : this.contents;
                n = this.hashCode;
                Object[] arrobject = this.hashTable;
                object = new RegularImmutableSet((Object[])object, n, arrobject, arrobject.length - 1, this.size);
            } else {
                object = ImmutableSet.construct(this.size, this.contents);
                this.size = object.size();
            }
            this.forceCopy = true;
            this.hashTable = null;
            return object;
        }
    }

    private static class SerializedForm
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final Object[] elements;

        SerializedForm(Object[] arrobject) {
            this.elements = arrobject;
        }

        Object readResolve() {
            return ImmutableSet.copyOf(this.elements);
        }
    }

}


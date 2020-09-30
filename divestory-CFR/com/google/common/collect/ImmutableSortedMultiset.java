/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.DescendingImmutableSortedMultiset;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMultisetFauxverideShim;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableSortedMultiset;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedMultiset;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

public abstract class ImmutableSortedMultiset<E>
extends ImmutableSortedMultisetFauxverideShim<E>
implements SortedMultiset<E> {
    @LazyInit
    transient ImmutableSortedMultiset<E> descendingMultiset;

    ImmutableSortedMultiset() {
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterable<? extends E> iterable) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), iterable);
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> iterable) {
        if (!(iterable instanceof ImmutableSortedMultiset)) return ((Builder)new Builder<E>(comparator).addAll(iterable)).build();
        ImmutableSortedMultiset immutableSortedMultiset = (ImmutableSortedMultiset)iterable;
        if (!comparator.equals(immutableSortedMultiset.comparator())) return ((Builder)new Builder<E>(comparator).addAll(iterable)).build();
        if (!immutableSortedMultiset.isPartialView()) return immutableSortedMultiset;
        return ImmutableSortedMultiset.copyOfSortedEntries(comparator, ((ImmutableSet)immutableSortedMultiset.entrySet()).asList());
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> iterator2) {
        Preconditions.checkNotNull(comparator);
        return ((Builder)new Builder<E>(comparator).addAll((Iterator)iterator2)).build();
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterator<? extends E> iterator2) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), iterator2);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> copyOf(E[] arrE) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), Arrays.asList(arrE));
    }

    public static <E> ImmutableSortedMultiset<E> copyOfSorted(SortedMultiset<E> sortedMultiset) {
        return ImmutableSortedMultiset.copyOfSortedEntries(sortedMultiset.comparator(), Lists.newArrayList(sortedMultiset.entrySet()));
    }

    private static <E> ImmutableSortedMultiset<E> copyOfSortedEntries(Comparator<? super E> comparator, Collection<Multiset.Entry<E>> collection) {
        if (collection.isEmpty()) {
            return ImmutableSortedMultiset.emptyMultiset(comparator);
        }
        ImmutableList.Builder builder = new ImmutableList.Builder(collection.size());
        long[] arrl = new long[collection.size() + 1];
        Iterator<Multiset.Entry<E>> iterator2 = collection.iterator();
        int n = 0;
        while (iterator2.hasNext()) {
            Multiset.Entry<E> entry = iterator2.next();
            builder.add((Object)entry.getElement());
            int n2 = n + 1;
            arrl[n2] = arrl[n] + (long)entry.getCount();
            n = n2;
        }
        return new RegularImmutableSortedMultiset<E>(new RegularImmutableSortedSet<E>((ImmutableList<? super E>)builder.build(), comparator), arrl, 0, collection.size());
    }

    static <E> ImmutableSortedMultiset<E> emptyMultiset(Comparator<? super E> comparator) {
        if (!Ordering.natural().equals(comparator)) return new RegularImmutableSortedMultiset<E>(comparator);
        return RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
    }

    public static <E extends Comparable<?>> Builder<E> naturalOrder() {
        return new Builder(Ordering.natural());
    }

    public static <E> ImmutableSortedMultiset<E> of() {
        return RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e) {
        return new RegularImmutableSortedMultiset((RegularImmutableSortedSet)ImmutableSortedSet.of(e), new long[]{0L, 1L}, 0, 1);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), Arrays.asList(e, e2));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), Arrays.asList(e, e2, e3));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3, E e4) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), Arrays.asList(e, e2, e3, e4));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3, E e4, E e5) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), Arrays.asList(e, e2, e3, e4, e5));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3, E e4, E e5, E e6, E ... arrE) {
        ArrayList arrayList = Lists.newArrayListWithCapacity(arrE.length + 6);
        Collections.addAll(arrayList, e, e2, e3, e4, e5, e6);
        Collections.addAll(arrayList, arrE);
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), arrayList);
    }

    public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
        return new Builder<E>(comparator);
    }

    public static <E extends Comparable<?>> Builder<E> reverseOrder() {
        return new Builder(Ordering.natural().reverse());
    }

    @Override
    public final Comparator<? super E> comparator() {
        return ((ImmutableSortedSet)this.elementSet()).comparator();
    }

    @Override
    public ImmutableSortedMultiset<E> descendingMultiset() {
        ImmutableSortedMultiset<E> immutableSortedMultiset;
        ImmutableSortedMultiset<Object> immutableSortedMultiset2 = immutableSortedMultiset = this.descendingMultiset;
        if (immutableSortedMultiset != null) return immutableSortedMultiset2;
        immutableSortedMultiset2 = this.isEmpty() ? ImmutableSortedMultiset.emptyMultiset(Ordering.from(this.comparator()).reverse()) : new DescendingImmutableSortedMultiset(this);
        this.descendingMultiset = immutableSortedMultiset2;
        return immutableSortedMultiset2;
    }

    @Override
    public abstract ImmutableSortedSet<E> elementSet();

    @Override
    public abstract ImmutableSortedMultiset<E> headMultiset(E var1, BoundType var2);

    @Deprecated
    @Override
    public final Multiset.Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final Multiset.Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableSortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        boolean bl = this.comparator().compare(e, e2) <= 0;
        Preconditions.checkArgument(bl, "Expected lowerBound <= upperBound but %s > %s", e, e2);
        return ((ImmutableSortedMultiset)this.tailMultiset((Object)e, boundType)).headMultiset((Object)e2, boundType2);
    }

    @Override
    public abstract ImmutableSortedMultiset<E> tailMultiset(E var1, BoundType var2);

    @Override
    Object writeReplace() {
        return new SerializedForm<E>(this);
    }

    public static class Builder<E>
    extends ImmutableMultiset.Builder<E> {
        private final Comparator<? super E> comparator;
        private int[] counts;
        E[] elements;
        private boolean forceCopyElements;
        private int length;

        public Builder(Comparator<? super E> comparator) {
            super(true);
            this.comparator = Preconditions.checkNotNull(comparator);
            this.elements = new Object[4];
            this.counts = new int[4];
        }

        private void dedupAndCoalesce(boolean bl) {
            int n;
            int n2;
            int n3 = this.length;
            if (n3 == 0) {
                return;
            }
            Object[] arrobject = Arrays.copyOf(this.elements, n3);
            Arrays.sort(arrobject, this.comparator);
            n3 = 1;
            for (n = 1; n < arrobject.length; ++n) {
                n2 = n3;
                if (this.comparator.compare(arrobject[n3 - 1], arrobject[n]) < 0) {
                    arrobject[n3] = arrobject[n];
                    n2 = n3 + 1;
                }
                n3 = n2;
            }
            Arrays.fill(arrobject, n3, this.length, null);
            Object[] arrobject2 = arrobject;
            if (bl) {
                n = this.length;
                arrobject2 = arrobject;
                if (n3 * 4 > n * 3) {
                    arrobject2 = Arrays.copyOf(arrobject, IntMath.saturatedAdd(n, n / 2 + 1));
                }
            }
            arrobject = new int[arrobject2.length];
            n = 0;
            do {
                if (n >= this.length) {
                    this.elements = arrobject2;
                    this.counts = arrobject;
                    this.length = n3;
                    return;
                }
                n2 = Arrays.binarySearch(arrobject2, 0, n3, this.elements[n], this.comparator);
                int[] arrn = this.counts;
                arrobject[n2] = arrn[n] >= 0 ? arrobject[n2] + arrn[n] : (Object)arrn[n];
                ++n;
            } while (true);
        }

        private void dedupAndCoalesceAndDeleteEmpty() {
            this.dedupAndCoalesce(false);
            int n = 0;
            int n2 = 0;
            do {
                int n3;
                if (n >= (n3 = this.length)) {
                    Arrays.fill(this.elements, n2, n3, null);
                    Arrays.fill(this.counts, n2, this.length, 0);
                    this.length = n2;
                    return;
                }
                int[] arrn = this.counts;
                n3 = n2;
                if (arrn[n] > 0) {
                    E[] arrE = this.elements;
                    arrE[n2] = arrE[n];
                    arrn[n2] = arrn[n];
                    n3 = n2 + 1;
                }
                ++n;
                n2 = n3;
            } while (true);
        }

        private void maintenance() {
            int n = this.length;
            E[] arrE = this.elements;
            if (n == arrE.length) {
                this.dedupAndCoalesce(true);
            } else if (this.forceCopyElements) {
                this.elements = Arrays.copyOf(arrE, arrE.length);
            }
            this.forceCopyElements = false;
        }

        @Override
        public Builder<E> add(E e) {
            return this.addCopies((Object)e, 1);
        }

        @Override
        public Builder<E> add(E ... arrE) {
            int n = arrE.length;
            int n2 = 0;
            while (n2 < n) {
                this.add((Object)arrE[n2]);
                ++n2;
            }
            return this;
        }

        @Override
        public Builder<E> addAll(Iterable<? extends E> iterator2) {
            if (iterator2 instanceof Multiset) {
                iterator2 = ((Multiset)((Object)iterator2)).entrySet().iterator();
                while (iterator2.hasNext()) {
                    Multiset.Entry entry = iterator2.next();
                    this.addCopies(entry.getElement(), entry.getCount());
                }
                return this;
            }
            iterator2 = iterator2.iterator();
            while (iterator2.hasNext()) {
                this.add(iterator2.next());
            }
            return this;
        }

        @Override
        public Builder<E> addAll(Iterator<? extends E> iterator2) {
            while (iterator2.hasNext()) {
                this.add((Object)iterator2.next());
            }
            return this;
        }

        @Override
        public Builder<E> addCopies(E e, int n) {
            Preconditions.checkNotNull(e);
            CollectPreconditions.checkNonnegative(n, "occurrences");
            if (n == 0) {
                return this;
            }
            this.maintenance();
            E[] arrE = this.elements;
            int n2 = this.length;
            arrE[n2] = e;
            this.counts[n2] = n;
            this.length = n2 + 1;
            return this;
        }

        @Override
        public ImmutableSortedMultiset<E> build() {
            this.dedupAndCoalesceAndDeleteEmpty();
            int n = this.length;
            if (n == 0) {
                return ImmutableSortedMultiset.emptyMultiset(this.comparator);
            }
            RegularImmutableSortedSet regularImmutableSortedSet = (RegularImmutableSortedSet)ImmutableSortedSet.construct(this.comparator, n, this.elements);
            long[] arrl = new long[this.length + 1];
            n = 0;
            do {
                if (n >= this.length) {
                    this.forceCopyElements = true;
                    return new RegularImmutableSortedMultiset(regularImmutableSortedSet, arrl, 0, this.length);
                }
                int n2 = n + 1;
                arrl[n2] = arrl[n] + (long)this.counts[n];
                n = n2;
            } while (true);
        }

        @Override
        public Builder<E> setCount(E e, int n) {
            Preconditions.checkNotNull(e);
            CollectPreconditions.checkNonnegative(n, "count");
            this.maintenance();
            E[] arrE = this.elements;
            int n2 = this.length;
            arrE[n2] = e;
            this.counts[n2] = n;
            this.length = n2 + 1;
            return this;
        }
    }

    private static final class SerializedForm<E>
    implements Serializable {
        final Comparator<? super E> comparator;
        final int[] counts;
        final E[] elements;

        SerializedForm(SortedMultiset<E> object) {
            this.comparator = object.comparator();
            int n = object.entrySet().size();
            this.elements = new Object[n];
            this.counts = new int[n];
            object = object.entrySet().iterator();
            n = 0;
            while (object.hasNext()) {
                Multiset.Entry entry = (Multiset.Entry)object.next();
                this.elements[n] = entry.getElement();
                this.counts[n] = entry.getCount();
                ++n;
            }
        }

        Object readResolve() {
            int n = this.elements.length;
            Builder<E> builder = new Builder<E>(this.comparator);
            int n2 = 0;
            while (n2 < n) {
                builder.addCopies((Object)this.elements[n2], this.counts[n2]);
                ++n2;
            }
            return builder.build();
        }
    }

}


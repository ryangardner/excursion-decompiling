/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSetFauxverideShim;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedIterable;
import com.google.common.collect.SortedIterables;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableSortedSet<E>
extends ImmutableSortedSetFauxverideShim<E>
implements NavigableSet<E>,
SortedIterable<E> {
    final transient Comparator<? super E> comparator;
    @LazyInit
    transient ImmutableSortedSet<E> descendingSet;

    ImmutableSortedSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    static <E> ImmutableSortedSet<E> construct(Comparator<? super E> comparator, int n, E ... arrE) {
        if (n == 0) {
            return ImmutableSortedSet.emptySet(comparator);
        }
        ObjectArrays.checkElementsNotNull((Object[])arrE, n);
        Arrays.sort(arrE, 0, n, comparator);
        int n2 = 1;
        int n3 = 1;
        do {
            Object object;
            if (n2 >= n) {
                Arrays.fill(arrE, n3, n, null);
                object = arrE;
                if (n3 >= arrE.length / 2) return new RegularImmutableSortedSet<E>(ImmutableList.asImmutableList(object, n3), comparator);
                object = Arrays.copyOf(arrE, n3);
                return new RegularImmutableSortedSet<E>(ImmutableList.asImmutableList(object, n3), comparator);
            }
            object = arrE[n2];
            int n4 = n3;
            if (comparator.compare(object, arrE[n3 - 1]) != 0) {
                arrE[n3] = object;
                n4 = n3 + 1;
            }
            ++n2;
            n3 = n4;
        } while (true);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterable<? extends E> iterable) {
        return ImmutableSortedSet.copyOf(Ordering.natural(), iterable);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Collection<? extends E> collection) {
        return ImmutableSortedSet.copyOf(Ordering.natural(), collection);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> arrobject) {
        ImmutableSortedSet immutableSortedSet;
        Preconditions.checkNotNull(comparator);
        if (SortedIterables.hasSameComparator(comparator, arrobject) && arrobject instanceof ImmutableSortedSet && !(immutableSortedSet = (ImmutableSortedSet)arrobject).isPartialView()) {
            return immutableSortedSet;
        }
        arrobject = Iterables.toArray(arrobject);
        return ImmutableSortedSet.construct(comparator, arrobject.length, arrobject);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Collection<? extends E> collection) {
        return ImmutableSortedSet.copyOf(comparator, collection);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> iterator2) {
        return ((Builder)new Builder<E>(comparator).addAll((Iterator)iterator2)).build();
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterator<? extends E> iterator2) {
        return ImmutableSortedSet.copyOf(Ordering.natural(), iterator2);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> copyOf(E[] arrE) {
        return ImmutableSortedSet.construct(Ordering.natural(), arrE.length, (Object[])arrE.clone());
    }

    public static <E> ImmutableSortedSet<E> copyOfSorted(SortedSet<E> collection) {
        Comparator<E> comparator = SortedIterables.comparator(collection);
        if (!((AbstractCollection)(collection = ImmutableList.copyOf(collection))).isEmpty()) return new RegularImmutableSortedSet<E>((ImmutableList<E>)collection, comparator);
        return ImmutableSortedSet.emptySet(comparator);
    }

    static <E> RegularImmutableSortedSet<E> emptySet(Comparator<? super E> comparator) {
        if (!Ordering.natural().equals(comparator)) return new RegularImmutableSortedSet<E>(ImmutableList.of(), comparator);
        return RegularImmutableSortedSet.NATURAL_EMPTY_SET;
    }

    public static <E extends Comparable<?>> Builder<E> naturalOrder() {
        return new Builder(Ordering.natural());
    }

    public static <E> ImmutableSortedSet<E> of() {
        return RegularImmutableSortedSet.NATURAL_EMPTY_SET;
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e) {
        return new RegularImmutableSortedSet<E>(ImmutableList.of(e), Ordering.natural());
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2) {
        return ImmutableSortedSet.construct(Ordering.natural(), 2, e, e2);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3) {
        return ImmutableSortedSet.construct(Ordering.natural(), 3, e, e2, e3);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4) {
        return ImmutableSortedSet.construct(Ordering.natural(), 4, e, e2, e3, e4);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4, E e5) {
        return ImmutableSortedSet.construct(Ordering.natural(), 5, e, e2, e3, e4, e5);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4, E e5, E e6, E ... arrE) {
        int n = arrE.length + 6;
        Comparable[] arrcomparable = new Comparable[n];
        arrcomparable[0] = e;
        arrcomparable[1] = e2;
        arrcomparable[2] = e3;
        arrcomparable[3] = e4;
        arrcomparable[4] = e5;
        arrcomparable[5] = e6;
        System.arraycopy(arrE, 0, arrcomparable, 6, arrE.length);
        return ImmutableSortedSet.construct(Ordering.natural(), n, arrcomparable);
    }

    public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
        return new Builder<E>(comparator);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    public static <E extends Comparable<?>> Builder<E> reverseOrder() {
        return new Builder(Collections.reverseOrder());
    }

    static int unsafeCompare(Comparator<?> comparator, Object object, Object object2) {
        return comparator.compare(object, object2);
    }

    @Override
    public E ceiling(E e) {
        return Iterables.getFirst(this.tailSet((Object)e, true), null);
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    abstract ImmutableSortedSet<E> createDescendingSet();

    @Override
    public abstract UnmodifiableIterator<E> descendingIterator();

    @Override
    public ImmutableSortedSet<E> descendingSet() {
        ImmutableSortedSet<E> immutableSortedSet;
        ImmutableSortedSet<E> immutableSortedSet2 = immutableSortedSet = this.descendingSet;
        if (immutableSortedSet != null) return immutableSortedSet2;
        this.descendingSet = immutableSortedSet2 = this.createDescendingSet();
        immutableSortedSet2.descendingSet = this;
        return immutableSortedSet2;
    }

    @Override
    public E first() {
        return this.iterator().next();
    }

    @Override
    public E floor(E e) {
        return Iterators.getNext(((ImmutableSortedSet)this.headSet((Object)e, true)).descendingIterator(), null);
    }

    @Override
    public ImmutableSortedSet<E> headSet(E e) {
        return this.headSet((Object)e, false);
    }

    @Override
    public ImmutableSortedSet<E> headSet(E e, boolean bl) {
        return this.headSetImpl(Preconditions.checkNotNull(e), bl);
    }

    abstract ImmutableSortedSet<E> headSetImpl(E var1, boolean var2);

    @Override
    public E higher(E e) {
        return Iterables.getFirst(this.tailSet((Object)e, false), null);
    }

    abstract int indexOf(@NullableDecl Object var1);

    @Override
    public abstract UnmodifiableIterator<E> iterator();

    @Override
    public E last() {
        return this.descendingIterator().next();
    }

    @Override
    public E lower(E e) {
        return Iterators.getNext(((ImmutableSortedSet)this.headSet((Object)e, false)).descendingIterator(), null);
    }

    @Deprecated
    @Override
    public final E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final E pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableSortedSet<E> subSet(E e, E e2) {
        return this.subSet((Object)e, true, (Object)e2, false);
    }

    @Override
    public ImmutableSortedSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
        Preconditions.checkNotNull(e);
        Preconditions.checkNotNull(e2);
        boolean bl3 = this.comparator.compare(e, e2) <= 0;
        Preconditions.checkArgument(bl3);
        return this.subSetImpl(e, bl, e2, bl2);
    }

    abstract ImmutableSortedSet<E> subSetImpl(E var1, boolean var2, E var3, boolean var4);

    @Override
    public ImmutableSortedSet<E> tailSet(E e) {
        return this.tailSet((Object)e, true);
    }

    @Override
    public ImmutableSortedSet<E> tailSet(E e, boolean bl) {
        return this.tailSetImpl(Preconditions.checkNotNull(e), bl);
    }

    abstract ImmutableSortedSet<E> tailSetImpl(E var1, boolean var2);

    int unsafeCompare(Object object, Object object2) {
        return ImmutableSortedSet.unsafeCompare(this.comparator, object, object2);
    }

    @Override
    Object writeReplace() {
        return new SerializedForm<E>(this.comparator, this.toArray());
    }

    public static final class Builder<E>
    extends ImmutableSet.Builder<E> {
        private final Comparator<? super E> comparator;

        public Builder(Comparator<? super E> comparator) {
            this.comparator = Preconditions.checkNotNull(comparator);
        }

        @Override
        public Builder<E> add(E e) {
            super.add((Object)e);
            return this;
        }

        @Override
        public Builder<E> add(E ... arrE) {
            super.add((Object[])arrE);
            return this;
        }

        @Override
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            super.addAll((Iterable)iterable);
            return this;
        }

        @Override
        public Builder<E> addAll(Iterator<? extends E> iterator2) {
            super.addAll((Iterator)iterator2);
            return this;
        }

        @Override
        public ImmutableSortedSet<E> build() {
            Object object = this.contents;
            object = ImmutableSortedSet.construct(this.comparator, this.size, object);
            this.size = ((AbstractCollection)object).size();
            this.forceCopy = true;
            return object;
        }
    }

    private static class SerializedForm<E>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final Comparator<? super E> comparator;
        final Object[] elements;

        public SerializedForm(Comparator<? super E> comparator, Object[] arrobject) {
            this.comparator = comparator;
            this.elements = arrobject;
        }

        Object readResolve() {
            return ((Builder)new Builder<E>(this.comparator).add(this.elements)).build();
        }
    }

}


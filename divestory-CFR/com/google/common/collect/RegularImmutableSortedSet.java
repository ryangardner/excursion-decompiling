/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SortedIterables;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularImmutableSortedSet<E>
extends ImmutableSortedSet<E> {
    static final RegularImmutableSortedSet<Comparable> NATURAL_EMPTY_SET = new RegularImmutableSortedSet(ImmutableList.of(), Ordering.natural());
    final transient ImmutableList<E> elements;

    RegularImmutableSortedSet(ImmutableList<E> immutableList, Comparator<? super E> comparator) {
        super(comparator);
        this.elements = immutableList;
    }

    private int unsafeBinarySearch(Object object) throws ClassCastException {
        return Collections.binarySearch(this.elements, object, this.unsafeComparator());
    }

    @Override
    public ImmutableList<E> asList() {
        return this.elements;
    }

    @Override
    public E ceiling(E e) {
        int n = this.tailIndex(e, true);
        if (n == this.size()) {
            e = null;
            return e;
        }
        e = this.elements.get(n);
        return e;
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = bl = false;
        if (object == null) return bl2;
        try {
            int n = this.unsafeBinarySearch(object);
            bl2 = bl;
            if (n < 0) return bl2;
            return true;
        }
        catch (ClassCastException classCastException) {
            return bl;
        }
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        Collection<?> collection2 = collection;
        if (collection instanceof Multiset) {
            collection2 = ((Multiset)collection).elementSet();
        }
        if (!SortedIterables.hasSameComparator(this.comparator(), collection2)) return super.containsAll(collection2);
        if (collection2.size() <= 1) {
            return super.containsAll(collection2);
        }
        Iterator iterator2 = this.iterator();
        Iterator<?> iterator3 = collection2.iterator();
        if (!iterator2.hasNext()) {
            return false;
        }
        collection2 = iterator3.next();
        collection = iterator2.next();
        try {
            do {
                int n;
                if ((n = this.unsafeCompare(collection, collection2)) < 0) {
                    if (!iterator2.hasNext()) {
                        return false;
                    }
                    collection = iterator2.next();
                    continue;
                }
                if (n == 0) {
                    if (!iterator3.hasNext()) {
                        return true;
                    }
                    collection2 = iterator3.next();
                    continue;
                }
                if (n > 0) return false;
            } while (true);
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return false;
        }
    }

    @Override
    int copyIntoArray(Object[] arrobject, int n) {
        return this.elements.copyIntoArray(arrobject, n);
    }

    @Override
    ImmutableSortedSet<E> createDescendingSet() {
        RegularImmutableSortedSet<E> regularImmutableSortedSet = Collections.reverseOrder(this.comparator);
        if (!this.isEmpty()) return new RegularImmutableSortedSet<E>(this.elements.reverse(), (Comparator<E>)((Object)regularImmutableSortedSet));
        return RegularImmutableSortedSet.emptySet(regularImmutableSortedSet);
    }

    @Override
    public UnmodifiableIterator<E> descendingIterator() {
        return this.elements.reverse().iterator();
    }

    @Override
    public boolean equals(@NullableDecl Object iterator2) {
        if (iterator2 == this) {
            return true;
        }
        if (!(iterator2 instanceof Set)) {
            return false;
        }
        iterator2 = (Set)((Object)iterator2);
        if (this.size() != iterator2.size()) {
            return false;
        }
        if (this.isEmpty()) {
            return true;
        }
        if (!SortedIterables.hasSameComparator(this.comparator, iterator2)) return this.containsAll((Collection<?>)((Object)iterator2));
        iterator2 = iterator2.iterator();
        try {
            Object e;
            Object e2;
            int n;
            Iterator iterator3 = this.iterator();
            do {
                if (!iterator3.hasNext()) return true;
                e = iterator3.next();
                e2 = iterator2.next();
                if (e2 == null) return false;
            } while ((n = this.unsafeCompare(e, e2)) == 0);
            return false;
        }
        catch (ClassCastException | NoSuchElementException runtimeException) {
            return false;
        }
    }

    @Override
    public E first() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.elements.get(0);
    }

    @Override
    public E floor(E e) {
        int n = this.headIndex(e, true) - 1;
        if (n == -1) {
            e = null;
            return e;
        }
        e = this.elements.get(n);
        return e;
    }

    RegularImmutableSortedSet<E> getSubSet(int n, int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        if (n >= n2) return RegularImmutableSortedSet.emptySet(this.comparator);
        return new RegularImmutableSortedSet<E>((ImmutableList<E>)this.elements.subList(n, n2), this.comparator);
    }

    int headIndex(E e, boolean bl) {
        int n = Collections.binarySearch(this.elements, Preconditions.checkNotNull(e), this.comparator());
        if (n < 0) return n;
        int n2 = n;
        if (!bl) return n2;
        return n + 1;
    }

    @Override
    ImmutableSortedSet<E> headSetImpl(E e, boolean bl) {
        return this.getSubSet(0, this.headIndex(e, bl));
    }

    @Override
    public E higher(E e) {
        int n = this.tailIndex(e, false);
        if (n == this.size()) {
            e = null;
            return e;
        }
        e = this.elements.get(n);
        return e;
    }

    @Override
    int indexOf(@NullableDecl Object object) {
        int n = -1;
        if (object == null) {
            return -1;
        }
        try {
            int n2 = Collections.binarySearch(this.elements, object, this.unsafeComparator());
            if (n2 < 0) return n;
            return n2;
        }
        catch (ClassCastException classCastException) {
            return n;
        }
    }

    @Override
    Object[] internalArray() {
        return this.elements.internalArray();
    }

    @Override
    int internalArrayEnd() {
        return this.elements.internalArrayEnd();
    }

    @Override
    int internalArrayStart() {
        return this.elements.internalArrayStart();
    }

    @Override
    boolean isPartialView() {
        return this.elements.isPartialView();
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return this.elements.iterator();
    }

    @Override
    public E last() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.elements.get(this.size() - 1);
    }

    @Override
    public E lower(E e) {
        int n = this.headIndex(e, false) - 1;
        if (n == -1) {
            e = null;
            return e;
        }
        e = this.elements.get(n);
        return e;
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    ImmutableSortedSet<E> subSetImpl(E e, boolean bl, E e2, boolean bl2) {
        return this.tailSetImpl(e, bl).headSetImpl(e2, bl2);
    }

    int tailIndex(E e, boolean bl) {
        int n = Collections.binarySearch(this.elements, Preconditions.checkNotNull(e), this.comparator());
        if (n < 0) return n;
        if (bl) {
            return n;
        }
        ++n;
        return n;
    }

    @Override
    ImmutableSortedSet<E> tailSetImpl(E e, boolean bl) {
        return this.getSubSet(this.tailIndex(e, bl), this.size());
    }

    Comparator<Object> unsafeComparator() {
        return this.comparator;
    }
}


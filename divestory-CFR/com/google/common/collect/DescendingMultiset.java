/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.BoundType;
import com.google.common.collect.ForwardingMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Ordering;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.SortedMultisets;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

abstract class DescendingMultiset<E>
extends ForwardingMultiset<E>
implements SortedMultiset<E> {
    @MonotonicNonNullDecl
    private transient Comparator<? super E> comparator;
    @MonotonicNonNullDecl
    private transient NavigableSet<E> elementSet;
    @MonotonicNonNullDecl
    private transient Set<Multiset.Entry<E>> entrySet;

    DescendingMultiset() {
    }

    @Override
    public Comparator<? super E> comparator() {
        Comparator<E> comparator;
        Comparator<E> comparator2 = comparator = this.comparator;
        if (comparator != null) return comparator2;
        comparator2 = Ordering.from(this.forwardMultiset().comparator()).reverse();
        this.comparator = comparator2;
        return comparator2;
    }

    Set<Multiset.Entry<E>> createEntrySet() {
        return new 1EntrySetImpl();
    }

    @Override
    protected Multiset<E> delegate() {
        return this.forwardMultiset();
    }

    @Override
    public SortedMultiset<E> descendingMultiset() {
        return this.forwardMultiset();
    }

    @Override
    public NavigableSet<E> elementSet() {
        NavigableSet<E> navigableSet;
        NavigableSet<E> navigableSet2 = navigableSet = this.elementSet;
        if (navigableSet != null) return navigableSet2;
        this.elementSet = navigableSet2 = new SortedMultisets.NavigableElementSet(this);
        return navigableSet2;
    }

    abstract Iterator<Multiset.Entry<E>> entryIterator();

    @Override
    public Set<Multiset.Entry<E>> entrySet() {
        Set<Multiset.Entry<Multiset.Entry<Multiset.Entry<Multiset.Entry<E>>>>> set;
        Set<Multiset.Entry<Multiset.Entry<Multiset.Entry<Multiset.Entry<E>>>>> set2 = set = this.entrySet;
        if (set != null) return set2;
        set2 = this.createEntrySet();
        this.entrySet = set2;
        return set2;
    }

    @Override
    public Multiset.Entry<E> firstEntry() {
        return this.forwardMultiset().lastEntry();
    }

    abstract SortedMultiset<E> forwardMultiset();

    @Override
    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return this.forwardMultiset().tailMultiset(e, boundType).descendingMultiset();
    }

    @Override
    public Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    @Override
    public Multiset.Entry<E> lastEntry() {
        return this.forwardMultiset().firstEntry();
    }

    @Override
    public Multiset.Entry<E> pollFirstEntry() {
        return this.forwardMultiset().pollLastEntry();
    }

    @Override
    public Multiset.Entry<E> pollLastEntry() {
        return this.forwardMultiset().pollFirstEntry();
    }

    @Override
    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return this.forwardMultiset().subMultiset(e2, boundType2, e, boundType).descendingMultiset();
    }

    @Override
    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return this.forwardMultiset().headMultiset(e, boundType).descendingMultiset();
    }

    @Override
    public Object[] toArray() {
        return this.standardToArray();
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        return this.standardToArray(arrT);
    }

    @Override
    public String toString() {
        return this.entrySet().toString();
    }

    class 1EntrySetImpl
    extends Multisets.EntrySet<E> {
        1EntrySetImpl() {
        }

        @Override
        public Iterator<Multiset.Entry<E>> iterator() {
            return DescendingMultiset.this.entryIterator();
        }

        @Override
        Multiset<E> multiset() {
            return DescendingMultiset.this;
        }

        @Override
        public int size() {
            return DescendingMultiset.this.forwardMultiset().entrySet().size();
        }
    }

}


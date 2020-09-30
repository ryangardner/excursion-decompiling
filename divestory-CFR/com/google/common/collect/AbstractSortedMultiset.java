/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.BoundType;
import com.google.common.collect.DescendingMultiset;
import com.google.common.collect.GwtTransient;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Ordering;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.SortedMultisets;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractSortedMultiset<E>
extends AbstractMultiset<E>
implements SortedMultiset<E> {
    @GwtTransient
    final Comparator<? super E> comparator;
    @MonotonicNonNullDecl
    private transient SortedMultiset<E> descendingMultiset;

    AbstractSortedMultiset() {
        this(Ordering.natural());
    }

    AbstractSortedMultiset(Comparator<? super E> comparator) {
        this.comparator = Preconditions.checkNotNull(comparator);
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    SortedMultiset<E> createDescendingMultiset() {
        return new 1DescendingMultisetImpl();
    }

    @Override
    NavigableSet<E> createElementSet() {
        return new SortedMultisets.NavigableElementSet(this);
    }

    abstract Iterator<Multiset.Entry<E>> descendingEntryIterator();

    Iterator<E> descendingIterator() {
        return Multisets.iteratorImpl(this.descendingMultiset());
    }

    @Override
    public SortedMultiset<E> descendingMultiset() {
        SortedMultiset<E> sortedMultiset;
        SortedMultiset<E> sortedMultiset2 = sortedMultiset = this.descendingMultiset;
        if (sortedMultiset != null) return sortedMultiset2;
        this.descendingMultiset = sortedMultiset2 = this.createDescendingMultiset();
        return sortedMultiset2;
    }

    @Override
    public NavigableSet<E> elementSet() {
        return (NavigableSet)super.elementSet();
    }

    @Override
    public Multiset.Entry<E> firstEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.entryIterator();
        if (!iterator2.hasNext()) return null;
        return iterator2.next();
    }

    @Override
    public Multiset.Entry<E> lastEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.descendingEntryIterator();
        if (!iterator2.hasNext()) return null;
        return iterator2.next();
    }

    @Override
    public Multiset.Entry<E> pollFirstEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.entryIterator();
        if (!iterator2.hasNext()) return null;
        Multiset.Entry<E> entry = iterator2.next();
        entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        iterator2.remove();
        return entry;
    }

    @Override
    public Multiset.Entry<E> pollLastEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.descendingEntryIterator();
        if (!iterator2.hasNext()) return null;
        Multiset.Entry<E> entry = iterator2.next();
        entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        iterator2.remove();
        return entry;
    }

    @Override
    public SortedMultiset<E> subMultiset(@NullableDecl E e, BoundType boundType, @NullableDecl E e2, BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        return this.tailMultiset(e, boundType).headMultiset(e2, boundType2);
    }

    class 1DescendingMultisetImpl
    extends DescendingMultiset<E> {
        1DescendingMultisetImpl() {
        }

        @Override
        Iterator<Multiset.Entry<E>> entryIterator() {
            return AbstractSortedMultiset.this.descendingEntryIterator();
        }

        @Override
        SortedMultiset<E> forwardMultiset() {
            return AbstractSortedMultiset.this;
        }

        @Override
        public Iterator<E> iterator() {
            return AbstractSortedMultiset.this.descendingIterator();
        }
    }

}


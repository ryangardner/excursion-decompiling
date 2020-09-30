/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.BoundType;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.SortedMultiset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class SortedMultisets {
    private SortedMultisets() {
    }

    private static <E> E getElementOrNull(@NullableDecl Multiset.Entry<E> entry) {
        if (entry == null) {
            entry = null;
            return (E)entry;
        }
        entry = entry.getElement();
        return (E)entry;
    }

    private static <E> E getElementOrThrow(Multiset.Entry<E> entry) {
        if (entry == null) throw new NoSuchElementException();
        return entry.getElement();
    }

    static class ElementSet<E>
    extends Multisets.ElementSet<E>
    implements SortedSet<E> {
        private final SortedMultiset<E> multiset;

        ElementSet(SortedMultiset<E> sortedMultiset) {
            this.multiset = sortedMultiset;
        }

        @Override
        public Comparator<? super E> comparator() {
            return this.multiset().comparator();
        }

        @Override
        public E first() {
            return (E)SortedMultisets.getElementOrThrow(this.multiset().firstEntry());
        }

        @Override
        public SortedSet<E> headSet(E e) {
            return this.multiset().headMultiset(e, BoundType.OPEN).elementSet();
        }

        @Override
        public Iterator<E> iterator() {
            return Multisets.elementIterator(this.multiset().entrySet().iterator());
        }

        @Override
        public E last() {
            return (E)SortedMultisets.getElementOrThrow(this.multiset().lastEntry());
        }

        @Override
        final SortedMultiset<E> multiset() {
            return this.multiset;
        }

        @Override
        public SortedSet<E> subSet(E e, E e2) {
            return this.multiset().subMultiset(e, BoundType.CLOSED, e2, BoundType.OPEN).elementSet();
        }

        @Override
        public SortedSet<E> tailSet(E e) {
            return this.multiset().tailMultiset(e, BoundType.CLOSED).elementSet();
        }
    }

    static class NavigableElementSet<E>
    extends ElementSet<E>
    implements NavigableSet<E> {
        NavigableElementSet(SortedMultiset<E> sortedMultiset) {
            super(sortedMultiset);
        }

        @Override
        public E ceiling(E e) {
            return (E)SortedMultisets.getElementOrNull(this.multiset().tailMultiset(e, BoundType.CLOSED).firstEntry());
        }

        @Override
        public Iterator<E> descendingIterator() {
            return this.descendingSet().iterator();
        }

        @Override
        public NavigableSet<E> descendingSet() {
            return new NavigableElementSet(this.multiset().descendingMultiset());
        }

        @Override
        public E floor(E e) {
            return (E)SortedMultisets.getElementOrNull(this.multiset().headMultiset(e, BoundType.CLOSED).lastEntry());
        }

        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            return new NavigableElementSet<E>(this.multiset().headMultiset(e, BoundType.forBoolean(bl)));
        }

        @Override
        public E higher(E e) {
            return (E)SortedMultisets.getElementOrNull(this.multiset().tailMultiset(e, BoundType.OPEN).firstEntry());
        }

        @Override
        public E lower(E e) {
            return (E)SortedMultisets.getElementOrNull(this.multiset().headMultiset(e, BoundType.OPEN).lastEntry());
        }

        @Override
        public E pollFirst() {
            return (E)SortedMultisets.getElementOrNull(this.multiset().pollFirstEntry());
        }

        @Override
        public E pollLast() {
            return (E)SortedMultisets.getElementOrNull(this.multiset().pollLastEntry());
        }

        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            return new NavigableElementSet<E>(this.multiset().subMultiset(e, BoundType.forBoolean(bl), e2, BoundType.forBoolean(bl2)));
        }

        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            return new NavigableElementSet<E>(this.multiset().tailMultiset(e, BoundType.forBoolean(bl)));
        }
    }

}


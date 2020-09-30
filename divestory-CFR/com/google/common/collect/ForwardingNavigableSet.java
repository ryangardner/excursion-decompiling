/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ForwardingSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

public abstract class ForwardingNavigableSet<E>
extends ForwardingSortedSet<E>
implements NavigableSet<E> {
    protected ForwardingNavigableSet() {
    }

    @Override
    public E ceiling(E e) {
        return this.delegate().ceiling(e);
    }

    @Override
    protected abstract NavigableSet<E> delegate();

    @Override
    public Iterator<E> descendingIterator() {
        return this.delegate().descendingIterator();
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return this.delegate().descendingSet();
    }

    @Override
    public E floor(E e) {
        return this.delegate().floor(e);
    }

    @Override
    public NavigableSet<E> headSet(E e, boolean bl) {
        return this.delegate().headSet(e, bl);
    }

    @Override
    public E higher(E e) {
        return this.delegate().higher(e);
    }

    @Override
    public E lower(E e) {
        return this.delegate().lower(e);
    }

    @Override
    public E pollFirst() {
        return this.delegate().pollFirst();
    }

    @Override
    public E pollLast() {
        return this.delegate().pollLast();
    }

    protected E standardCeiling(E e) {
        return Iterators.getNext(this.tailSet(e, true).iterator(), null);
    }

    protected E standardFirst() {
        return this.iterator().next();
    }

    protected E standardFloor(E e) {
        return Iterators.getNext(this.headSet(e, true).descendingIterator(), null);
    }

    protected SortedSet<E> standardHeadSet(E e) {
        return this.headSet(e, false);
    }

    protected E standardHigher(E e) {
        return Iterators.getNext(this.tailSet(e, false).iterator(), null);
    }

    protected E standardLast() {
        return this.descendingIterator().next();
    }

    protected E standardLower(E e) {
        return Iterators.getNext(this.headSet(e, false).descendingIterator(), null);
    }

    protected E standardPollFirst() {
        return Iterators.pollNext(this.iterator());
    }

    protected E standardPollLast() {
        return Iterators.pollNext(this.descendingIterator());
    }

    protected NavigableSet<E> standardSubSet(E e, boolean bl, E e2, boolean bl2) {
        return this.tailSet(e, bl).headSet(e2, bl2);
    }

    @Override
    protected SortedSet<E> standardSubSet(E e, E e2) {
        return this.subSet(e, true, e2, false);
    }

    protected SortedSet<E> standardTailSet(E e) {
        return this.tailSet(e, true);
    }

    @Override
    public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
        return this.delegate().subSet(e, bl, e2, bl2);
    }

    @Override
    public NavigableSet<E> tailSet(E e, boolean bl) {
        return this.delegate().tailSet(e, bl);
    }

    protected class StandardDescendingSet
    extends Sets.DescendingSet<E> {
        public StandardDescendingSet() {
            super(ForwardingNavigableSet.this);
        }
    }

}


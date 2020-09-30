/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class DescendingImmutableSortedSet<E>
extends ImmutableSortedSet<E> {
    private final ImmutableSortedSet<E> forward;

    DescendingImmutableSortedSet(ImmutableSortedSet<E> immutableSortedSet) {
        super(Ordering.from(immutableSortedSet.comparator()).reverse());
        this.forward = immutableSortedSet;
    }

    @Override
    public E ceiling(E e) {
        return this.forward.floor(e);
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        return this.forward.contains(object);
    }

    @Override
    ImmutableSortedSet<E> createDescendingSet() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    public UnmodifiableIterator<E> descendingIterator() {
        return this.forward.iterator();
    }

    @Override
    public ImmutableSortedSet<E> descendingSet() {
        return this.forward;
    }

    @Override
    public E floor(E e) {
        return this.forward.ceiling(e);
    }

    @Override
    ImmutableSortedSet<E> headSetImpl(E e, boolean bl) {
        return ((ImmutableSortedSet)this.forward.tailSet((Object)e, bl)).descendingSet();
    }

    @Override
    public E higher(E e) {
        return this.forward.lower(e);
    }

    @Override
    int indexOf(@NullableDecl Object object) {
        int n = this.forward.indexOf(object);
        if (n != -1) return this.size() - 1 - n;
        return n;
    }

    @Override
    boolean isPartialView() {
        return this.forward.isPartialView();
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return this.forward.descendingIterator();
    }

    @Override
    public E lower(E e) {
        return this.forward.higher(e);
    }

    @Override
    public int size() {
        return this.forward.size();
    }

    @Override
    ImmutableSortedSet<E> subSetImpl(E e, boolean bl, E e2, boolean bl2) {
        return ((ImmutableSortedSet)this.forward.subSet((Object)e2, bl2, (Object)e, bl)).descendingSet();
    }

    @Override
    ImmutableSortedSet<E> tailSetImpl(E e, boolean bl) {
        return ((ImmutableSortedSet)this.forward.headSet((Object)e, bl)).descendingSet();
    }
}


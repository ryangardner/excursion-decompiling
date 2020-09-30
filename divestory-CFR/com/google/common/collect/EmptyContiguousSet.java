/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.BoundType;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class EmptyContiguousSet<C extends Comparable>
extends ContiguousSet<C> {
    EmptyContiguousSet(DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
    }

    @Override
    public ImmutableList<C> asList() {
        return ImmutableList.of();
    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    ImmutableSortedSet<C> createDescendingSet() {
        return ImmutableSortedSet.emptySet(Ordering.natural().reverse());
    }

    @Override
    public UnmodifiableIterator<C> descendingIterator() {
        return Iterators.emptyIterator();
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (!(object instanceof Set)) return false;
        return ((Set)object).isEmpty();
    }

    @Override
    public C first() {
        throw new NoSuchElementException();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    ContiguousSet<C> headSetImpl(C c, boolean bl) {
        return this;
    }

    @Override
    int indexOf(Object object) {
        return -1;
    }

    @Override
    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    boolean isHashCodeFast() {
        return true;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public UnmodifiableIterator<C> iterator() {
        return Iterators.emptyIterator();
    }

    @Override
    public C last() {
        throw new NoSuchElementException();
    }

    @Override
    public Range<C> range() {
        throw new NoSuchElementException();
    }

    @Override
    public Range<C> range(BoundType boundType, BoundType boundType2) {
        throw new NoSuchElementException();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    ContiguousSet<C> subSetImpl(C c, boolean bl, C c2, boolean bl2) {
        return this;
    }

    @Override
    ContiguousSet<C> tailSetImpl(C c, boolean bl) {
        return this;
    }

    @Override
    public String toString() {
        return "[]";
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this.domain);
    }

    private static final class SerializedForm<C extends Comparable>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final DiscreteDomain<C> domain;

        private SerializedForm(DiscreteDomain<C> discreteDomain) {
            this.domain = discreteDomain;
        }

        private Object readResolve() {
            return new EmptyContiguousSet<C>(this.domain);
        }
    }

}


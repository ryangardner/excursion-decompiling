/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractMultiset<E>
extends AbstractCollection<E>
implements Multiset<E> {
    @LazyInit
    @MonotonicNonNullDecl
    private transient Set<E> elementSet;
    @LazyInit
    @MonotonicNonNullDecl
    private transient Set<Multiset.Entry<E>> entrySet;

    AbstractMultiset() {
    }

    @Override
    public int add(@NullableDecl E e, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean add(@NullableDecl E e) {
        this.add(e, 1);
        return true;
    }

    @Override
    public final boolean addAll(Collection<? extends E> collection) {
        return Multisets.addAllImpl(this, collection);
    }

    @Override
    public abstract void clear();

    @Override
    public boolean contains(@NullableDecl Object object) {
        if (this.count(object) <= 0) return false;
        return true;
    }

    Set<E> createElementSet() {
        return new ElementSet();
    }

    Set<Multiset.Entry<E>> createEntrySet() {
        return new EntrySet();
    }

    abstract int distinctElements();

    abstract Iterator<E> elementIterator();

    @Override
    public Set<E> elementSet() {
        Set<E> set;
        Set<E> set2 = set = this.elementSet;
        if (set != null) return set2;
        this.elementSet = set2 = this.createElementSet();
        return set2;
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
    public final boolean equals(@NullableDecl Object object) {
        return Multisets.equalsImpl(this, object);
    }

    @Override
    public final int hashCode() {
        return this.entrySet().hashCode();
    }

    @Override
    public boolean isEmpty() {
        return this.entrySet().isEmpty();
    }

    @Override
    public int remove(@NullableDecl Object object, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean remove(@NullableDecl Object object) {
        boolean bl = true;
        if (this.remove(object, 1) <= 0) return false;
        return bl;
    }

    @Override
    public final boolean removeAll(Collection<?> collection) {
        return Multisets.removeAllImpl(this, collection);
    }

    @Override
    public final boolean retainAll(Collection<?> collection) {
        return Multisets.retainAllImpl(this, collection);
    }

    @Override
    public int setCount(@NullableDecl E e, int n) {
        return Multisets.setCountImpl(this, e, n);
    }

    @Override
    public boolean setCount(@NullableDecl E e, int n, int n2) {
        return Multisets.setCountImpl(this, e, n, n2);
    }

    @Override
    public final String toString() {
        return this.entrySet().toString();
    }

    class ElementSet
    extends Multisets.ElementSet<E> {
        ElementSet() {
        }

        @Override
        public Iterator<E> iterator() {
            return AbstractMultiset.this.elementIterator();
        }

        @Override
        Multiset<E> multiset() {
            return AbstractMultiset.this;
        }
    }

    class EntrySet
    extends Multisets.EntrySet<E> {
        EntrySet() {
        }

        @Override
        public Iterator<Multiset.Entry<E>> iterator() {
            return AbstractMultiset.this.entryIterator();
        }

        @Override
        Multiset<E> multiset() {
            return AbstractMultiset.this;
        }

        @Override
        public int size() {
            return AbstractMultiset.this.distinctElements();
        }
    }

}


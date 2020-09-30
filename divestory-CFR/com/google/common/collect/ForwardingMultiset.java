/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingMultiset<E>
extends ForwardingCollection<E>
implements Multiset<E> {
    protected ForwardingMultiset() {
    }

    @Override
    public int add(E e, int n) {
        return this.delegate().add(e, n);
    }

    @Override
    public int count(Object object) {
        return this.delegate().count(object);
    }

    @Override
    protected abstract Multiset<E> delegate();

    @Override
    public Set<E> elementSet() {
        return this.delegate().elementSet();
    }

    @Override
    public Set<Multiset.Entry<E>> entrySet() {
        return this.delegate().entrySet();
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (object == this) return true;
        if (this.delegate().equals(object)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    @Override
    public int remove(Object object, int n) {
        return this.delegate().remove(object, n);
    }

    @Override
    public int setCount(E e, int n) {
        return this.delegate().setCount(e, n);
    }

    @Override
    public boolean setCount(E e, int n, int n2) {
        return this.delegate().setCount(e, n, n2);
    }

    protected boolean standardAdd(E e) {
        this.add(e, 1);
        return true;
    }

    @Override
    protected boolean standardAddAll(Collection<? extends E> collection) {
        return Multisets.addAllImpl(this, collection);
    }

    @Override
    protected void standardClear() {
        Iterators.clear(this.entrySet().iterator());
    }

    @Override
    protected boolean standardContains(@NullableDecl Object object) {
        if (this.count(object) <= 0) return false;
        return true;
    }

    protected int standardCount(@NullableDecl Object object) {
        Multiset.Entry<E> entry;
        Iterator<Multiset.Entry<E>> iterator2 = this.entrySet().iterator();
        do {
            if (!iterator2.hasNext()) return 0;
        } while (!Objects.equal((entry = iterator2.next()).getElement(), object));
        return entry.getCount();
    }

    protected boolean standardEquals(@NullableDecl Object object) {
        return Multisets.equalsImpl(this, object);
    }

    protected int standardHashCode() {
        return this.entrySet().hashCode();
    }

    protected Iterator<E> standardIterator() {
        return Multisets.iteratorImpl(this);
    }

    @Override
    protected boolean standardRemove(Object object) {
        boolean bl = true;
        if (this.remove(object, 1) <= 0) return false;
        return bl;
    }

    @Override
    protected boolean standardRemoveAll(Collection<?> collection) {
        return Multisets.removeAllImpl(this, collection);
    }

    @Override
    protected boolean standardRetainAll(Collection<?> collection) {
        return Multisets.retainAllImpl(this, collection);
    }

    protected int standardSetCount(E e, int n) {
        return Multisets.setCountImpl(this, e, n);
    }

    protected boolean standardSetCount(E e, int n, int n2) {
        return Multisets.setCountImpl(this, e, n, n2);
    }

    protected int standardSize() {
        return Multisets.linearTimeSizeImpl(this);
    }

    @Override
    protected String standardToString() {
        return this.entrySet().toString();
    }

    protected class StandardElementSet
    extends Multisets.ElementSet<E> {
        @Override
        public Iterator<E> iterator() {
            return Multisets.elementIterator(this.multiset().entrySet().iterator());
        }

        @Override
        Multiset<E> multiset() {
            return ForwardingMultiset.this;
        }
    }

}


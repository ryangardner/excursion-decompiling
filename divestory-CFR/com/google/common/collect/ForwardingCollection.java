/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingCollection<E>
extends ForwardingObject
implements Collection<E> {
    protected ForwardingCollection() {
    }

    @Override
    public boolean add(E e) {
        return this.delegate().add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return this.delegate().addAll(collection);
    }

    @Override
    public void clear() {
        this.delegate().clear();
    }

    @Override
    public boolean contains(Object object) {
        return this.delegate().contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.delegate().containsAll(collection);
    }

    @Override
    protected abstract Collection<E> delegate();

    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return this.delegate().iterator();
    }

    @Override
    public boolean remove(Object object) {
        return this.delegate().remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.delegate().removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.delegate().retainAll(collection);
    }

    @Override
    public int size() {
        return this.delegate().size();
    }

    protected boolean standardAddAll(Collection<? extends E> collection) {
        return Iterators.addAll(this, collection.iterator());
    }

    protected void standardClear() {
        Iterators.clear(this.iterator());
    }

    protected boolean standardContains(@NullableDecl Object object) {
        return Iterators.contains(this.iterator(), object);
    }

    protected boolean standardContainsAll(Collection<?> collection) {
        return Collections2.containsAllImpl(this, collection);
    }

    protected boolean standardIsEmpty() {
        return this.iterator().hasNext() ^ true;
    }

    protected boolean standardRemove(@NullableDecl Object object) {
        Iterator<E> iterator2 = this.iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while (!Objects.equal(iterator2.next(), object));
        iterator2.remove();
        return true;
    }

    protected boolean standardRemoveAll(Collection<?> collection) {
        return Iterators.removeAll(this.iterator(), collection);
    }

    protected boolean standardRetainAll(Collection<?> collection) {
        return Iterators.retainAll(this.iterator(), collection);
    }

    protected Object[] standardToArray() {
        return this.toArray(new Object[this.size()]);
    }

    protected <T> T[] standardToArray(T[] arrT) {
        return ObjectArrays.toArrayImpl(this, arrT);
    }

    protected String standardToString() {
        return Collections2.toStringImpl(this);
    }

    @Override
    public Object[] toArray() {
        return this.delegate().toArray();
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        return this.delegate().toArray(arrT);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingList<E>
extends ForwardingCollection<E>
implements List<E> {
    protected ForwardingList() {
    }

    @Override
    public void add(int n, E e) {
        this.delegate().add(n, e);
    }

    @Override
    public boolean addAll(int n, Collection<? extends E> collection) {
        return this.delegate().addAll(n, collection);
    }

    @Override
    protected abstract List<E> delegate();

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (object == this) return true;
        if (this.delegate().equals(object)) return true;
        return false;
    }

    @Override
    public E get(int n) {
        return this.delegate().get(n);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    @Override
    public int indexOf(Object object) {
        return this.delegate().indexOf(object);
    }

    @Override
    public int lastIndexOf(Object object) {
        return this.delegate().lastIndexOf(object);
    }

    @Override
    public ListIterator<E> listIterator() {
        return this.delegate().listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int n) {
        return this.delegate().listIterator(n);
    }

    @Override
    public E remove(int n) {
        return this.delegate().remove(n);
    }

    @Override
    public E set(int n, E e) {
        return this.delegate().set(n, e);
    }

    protected boolean standardAdd(E e) {
        this.add(this.size(), e);
        return true;
    }

    protected boolean standardAddAll(int n, Iterable<? extends E> iterable) {
        return Lists.addAllImpl(this, n, iterable);
    }

    protected boolean standardEquals(@NullableDecl Object object) {
        return Lists.equalsImpl(this, object);
    }

    protected int standardHashCode() {
        return Lists.hashCodeImpl(this);
    }

    protected int standardIndexOf(@NullableDecl Object object) {
        return Lists.indexOfImpl(this, object);
    }

    protected Iterator<E> standardIterator() {
        return this.listIterator();
    }

    protected int standardLastIndexOf(@NullableDecl Object object) {
        return Lists.lastIndexOfImpl(this, object);
    }

    protected ListIterator<E> standardListIterator() {
        return this.listIterator(0);
    }

    protected ListIterator<E> standardListIterator(int n) {
        return Lists.listIteratorImpl(this, n);
    }

    protected List<E> standardSubList(int n, int n2) {
        return Lists.subListImpl(this, n, n2);
    }

    @Override
    public List<E> subList(int n, int n2) {
        return this.delegate().subList(n, n2);
    }
}


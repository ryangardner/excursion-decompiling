/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.Iterator;

final class SingletonImmutableSet<E>
extends ImmutableSet<E> {
    @LazyInit
    private transient int cachedHashCode;
    final transient E element;

    SingletonImmutableSet(E e) {
        this.element = Preconditions.checkNotNull(e);
    }

    SingletonImmutableSet(E e, int n) {
        this.element = e;
        this.cachedHashCode = n;
    }

    @Override
    public boolean contains(Object object) {
        return this.element.equals(object);
    }

    @Override
    int copyIntoArray(Object[] arrobject, int n) {
        arrobject[n] = this.element;
        return n + 1;
    }

    @Override
    ImmutableList<E> createAsList() {
        return ImmutableList.of(this.element);
    }

    @Override
    public final int hashCode() {
        int n;
        int n2 = n = this.cachedHashCode;
        if (n != 0) return n2;
        this.cachedHashCode = n2 = this.element.hashCode();
        return n2;
    }

    @Override
    boolean isHashCodeFast() {
        if (this.cachedHashCode == 0) return false;
        return true;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        stringBuilder.append(this.element.toString());
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}


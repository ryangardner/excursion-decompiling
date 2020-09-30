/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

class RegularImmutableList<E>
extends ImmutableList<E> {
    static final ImmutableList<Object> EMPTY = new RegularImmutableList<Object>(new Object[0], 0);
    final transient Object[] array;
    private final transient int size;

    RegularImmutableList(Object[] arrobject, int n) {
        this.array = arrobject;
        this.size = n;
    }

    @Override
    int copyIntoArray(Object[] arrobject, int n) {
        System.arraycopy(this.array, 0, arrobject, n, this.size);
        return n + this.size;
    }

    @Override
    public E get(int n) {
        Preconditions.checkElementIndex(n, this.size);
        return (E)this.array[n];
    }

    @Override
    Object[] internalArray() {
        return this.array;
    }

    @Override
    int internalArrayEnd() {
        return this.size;
    }

    @Override
    int internalArrayStart() {
        return 0;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }
}


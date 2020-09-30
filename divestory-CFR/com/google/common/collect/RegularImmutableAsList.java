/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableListIterator;
import java.util.ListIterator;

class RegularImmutableAsList<E>
extends ImmutableAsList<E> {
    private final ImmutableCollection<E> delegate;
    private final ImmutableList<? extends E> delegateList;

    RegularImmutableAsList(ImmutableCollection<E> immutableCollection, ImmutableList<? extends E> immutableList) {
        this.delegate = immutableCollection;
        this.delegateList = immutableList;
    }

    RegularImmutableAsList(ImmutableCollection<E> immutableCollection, Object[] arrobject) {
        this(immutableCollection, ImmutableList.asImmutableList(arrobject));
    }

    RegularImmutableAsList(ImmutableCollection<E> immutableCollection, Object[] arrobject, int n) {
        this(immutableCollection, ImmutableList.asImmutableList(arrobject, n));
    }

    @Override
    int copyIntoArray(Object[] arrobject, int n) {
        return this.delegateList.copyIntoArray(arrobject, n);
    }

    @Override
    ImmutableCollection<E> delegateCollection() {
        return this.delegate;
    }

    ImmutableList<? extends E> delegateList() {
        return this.delegateList;
    }

    @Override
    public E get(int n) {
        return this.delegateList.get(n);
    }

    @Override
    Object[] internalArray() {
        return this.delegateList.internalArray();
    }

    @Override
    int internalArrayEnd() {
        return this.delegateList.internalArrayEnd();
    }

    @Override
    int internalArrayStart() {
        return this.delegateList.internalArrayStart();
    }

    @Override
    public UnmodifiableListIterator<E> listIterator(int n) {
        return this.delegateList.listIterator(n);
    }
}


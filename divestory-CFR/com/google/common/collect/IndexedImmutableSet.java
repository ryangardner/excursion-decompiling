/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Iterator;

abstract class IndexedImmutableSet<E>
extends ImmutableSet<E> {
    IndexedImmutableSet() {
    }

    @Override
    int copyIntoArray(Object[] arrobject, int n) {
        return this.asList().copyIntoArray(arrobject, n);
    }

    @Override
    ImmutableList<E> createAsList() {
        return new ImmutableList<E>(){

            @Override
            public E get(int n) {
                return IndexedImmutableSet.this.get(n);
            }

            @Override
            boolean isPartialView() {
                return IndexedImmutableSet.this.isPartialView();
            }

            @Override
            public int size() {
                return IndexedImmutableSet.this.size();
            }
        };
    }

    abstract E get(int var1);

    @Override
    public UnmodifiableIterator<E> iterator() {
        return this.asList().iterator();
    }

}


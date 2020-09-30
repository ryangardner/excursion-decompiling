/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Iterators;
import com.google.common.collect.TransformedIterator;
import java.util.Iterator;
import java.util.ListIterator;

abstract class TransformedListIterator<F, T>
extends TransformedIterator<F, T>
implements ListIterator<T> {
    TransformedListIterator(ListIterator<? extends F> listIterator) {
        super(listIterator);
    }

    private ListIterator<? extends F> backingIterator() {
        return Iterators.cast(this.backingIterator);
    }

    @Override
    public void add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean hasPrevious() {
        return this.backingIterator().hasPrevious();
    }

    @Override
    public final int nextIndex() {
        return this.backingIterator().nextIndex();
    }

    @Override
    public final T previous() {
        return this.transform(this.backingIterator().previous());
    }

    @Override
    public final int previousIndex() {
        return this.backingIterator().previousIndex();
    }

    @Override
    public void set(T t) {
        throw new UnsupportedOperationException();
    }
}


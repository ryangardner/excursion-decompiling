/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ForwardingObject;
import java.util.Iterator;

public abstract class ForwardingIterator<T>
extends ForwardingObject
implements Iterator<T> {
    protected ForwardingIterator() {
    }

    @Override
    protected abstract Iterator<T> delegate();

    @Override
    public boolean hasNext() {
        return this.delegate().hasNext();
    }

    @Override
    public T next() {
        return (T)this.delegate().next();
    }

    @Override
    public void remove() {
        this.delegate().remove();
    }
}


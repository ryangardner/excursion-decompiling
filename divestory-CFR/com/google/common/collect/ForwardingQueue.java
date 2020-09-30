/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ForwardingCollection;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Queue;

public abstract class ForwardingQueue<E>
extends ForwardingCollection<E>
implements Queue<E> {
    protected ForwardingQueue() {
    }

    @Override
    protected abstract Queue<E> delegate();

    @Override
    public E element() {
        return this.delegate().element();
    }

    @Override
    public boolean offer(E e) {
        return this.delegate().offer(e);
    }

    @Override
    public E peek() {
        return this.delegate().peek();
    }

    @Override
    public E poll() {
        return this.delegate().poll();
    }

    @Override
    public E remove() {
        return this.delegate().remove();
    }

    protected boolean standardOffer(E e) {
        try {
            return this.add(e);
        }
        catch (IllegalStateException illegalStateException) {
            return false;
        }
    }

    protected E standardPeek() {
        E e;
        try {
            e = this.element();
        }
        catch (NoSuchElementException noSuchElementException) {
            return null;
        }
        return e;
    }

    protected E standardPoll() {
        E e;
        try {
            e = this.remove();
        }
        catch (NoSuchElementException noSuchElementException) {
            return null;
        }
        return e;
    }
}


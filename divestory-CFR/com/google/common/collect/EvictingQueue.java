/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingQueue;
import com.google.common.collect.Iterables;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public final class EvictingQueue<E>
extends ForwardingQueue<E>
implements Serializable {
    private static final long serialVersionUID = 0L;
    private final Queue<E> delegate;
    final int maxSize;

    private EvictingQueue(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "maxSize (%s) must >= 0", n);
        this.delegate = new ArrayDeque(n);
        this.maxSize = n;
    }

    public static <E> EvictingQueue<E> create(int n) {
        return new EvictingQueue<E>(n);
    }

    @Override
    public boolean add(E e) {
        Preconditions.checkNotNull(e);
        if (this.maxSize == 0) {
            return true;
        }
        if (this.size() == this.maxSize) {
            this.delegate.remove();
        }
        this.delegate.add(e);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        int n = collection.size();
        if (n < this.maxSize) return this.standardAddAll(collection);
        this.clear();
        return Iterables.addAll(this, Iterables.skip(collection, n - this.maxSize));
    }

    @Override
    public boolean contains(Object object) {
        return this.delegate().contains(Preconditions.checkNotNull(object));
    }

    @Override
    protected Queue<E> delegate() {
        return this.delegate;
    }

    @Override
    public boolean offer(E e) {
        return this.add(e);
    }

    public int remainingCapacity() {
        return this.maxSize - this.size();
    }

    @Override
    public boolean remove(Object object) {
        return this.delegate().remove(Preconditions.checkNotNull(object));
    }
}


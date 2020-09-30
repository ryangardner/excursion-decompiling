/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.collect.ForwardingDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

public abstract class ForwardingBlockingDeque<E>
extends ForwardingDeque<E>
implements BlockingDeque<E> {
    protected ForwardingBlockingDeque() {
    }

    @Override
    protected abstract BlockingDeque<E> delegate();

    @Override
    public int drainTo(Collection<? super E> collection) {
        return this.delegate().drainTo(collection);
    }

    @Override
    public int drainTo(Collection<? super E> collection, int n) {
        return this.delegate().drainTo(collection, n);
    }

    @Override
    public boolean offer(E e, long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().offer(e, l, timeUnit);
    }

    @Override
    public boolean offerFirst(E e, long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().offerFirst(e, l, timeUnit);
    }

    @Override
    public boolean offerLast(E e, long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().offerLast(e, l, timeUnit);
    }

    @Override
    public E poll(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().poll(l, timeUnit);
    }

    @Override
    public E pollFirst(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().pollFirst(l, timeUnit);
    }

    @Override
    public E pollLast(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().pollLast(l, timeUnit);
    }

    @Override
    public void put(E e) throws InterruptedException {
        this.delegate().put(e);
    }

    @Override
    public void putFirst(E e) throws InterruptedException {
        this.delegate().putFirst(e);
    }

    @Override
    public void putLast(E e) throws InterruptedException {
        this.delegate().putLast(e);
    }

    @Override
    public int remainingCapacity() {
        return this.delegate().remainingCapacity();
    }

    @Override
    public E take() throws InterruptedException {
        return this.delegate().take();
    }

    @Override
    public E takeFirst() throws InterruptedException {
        return this.delegate().takeFirst();
    }

    @Override
    public E takeLast() throws InterruptedException {
        return this.delegate().takeLast();
    }
}


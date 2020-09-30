/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

abstract class ForwardingCondition
implements Condition {
    ForwardingCondition() {
    }

    @Override
    public void await() throws InterruptedException {
        this.delegate().await();
    }

    @Override
    public boolean await(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().await(l, timeUnit);
    }

    @Override
    public long awaitNanos(long l) throws InterruptedException {
        return this.delegate().awaitNanos(l);
    }

    @Override
    public void awaitUninterruptibly() {
        this.delegate().awaitUninterruptibly();
    }

    @Override
    public boolean awaitUntil(Date date) throws InterruptedException {
        return this.delegate().awaitUntil(date);
    }

    abstract Condition delegate();

    @Override
    public void signal() {
        this.delegate().signal();
    }

    @Override
    public void signalAll() {
        this.delegate().signalAll();
    }
}


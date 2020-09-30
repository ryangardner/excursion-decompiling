/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class ReverseOrdering<T>
extends Ordering<T>
implements Serializable {
    private static final long serialVersionUID = 0L;
    final Ordering<? super T> forwardOrder;

    ReverseOrdering(Ordering<? super T> ordering) {
        this.forwardOrder = Preconditions.checkNotNull(ordering);
    }

    @Override
    public int compare(T t, T t2) {
        return this.forwardOrder.compare(t2, t);
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ReverseOrdering)) return false;
        object = (ReverseOrdering)object;
        return this.forwardOrder.equals(((ReverseOrdering)object).forwardOrder);
    }

    public int hashCode() {
        return -this.forwardOrder.hashCode();
    }

    @Override
    public <E extends T> E max(Iterable<E> iterable) {
        return this.forwardOrder.min(iterable);
    }

    @Override
    public <E extends T> E max(E e, E e2) {
        return this.forwardOrder.min(e, e2);
    }

    @Override
    public <E extends T> E max(E e, E e2, E e3, E ... arrE) {
        return this.forwardOrder.min(e, e2, e3, arrE);
    }

    @Override
    public <E extends T> E max(Iterator<E> iterator2) {
        return this.forwardOrder.min(iterator2);
    }

    @Override
    public <E extends T> E min(Iterable<E> iterable) {
        return this.forwardOrder.max(iterable);
    }

    @Override
    public <E extends T> E min(E e, E e2) {
        return this.forwardOrder.max(e, e2);
    }

    @Override
    public <E extends T> E min(E e, E e2, E e3, E ... arrE) {
        return this.forwardOrder.max(e, e2, e3, arrE);
    }

    @Override
    public <E extends T> E min(Iterator<E> iterator2) {
        return this.forwardOrder.max(iterator2);
    }

    @Override
    public <S extends T> Ordering<S> reverse() {
        return this.forwardOrder;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.forwardOrder);
        stringBuilder.append(".reverse()");
        return stringBuilder.toString();
    }
}


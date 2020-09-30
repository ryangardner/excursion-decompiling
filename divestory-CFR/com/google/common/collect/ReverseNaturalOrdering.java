/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.NaturalOrdering;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Iterator;

final class ReverseNaturalOrdering
extends Ordering<Comparable>
implements Serializable {
    static final ReverseNaturalOrdering INSTANCE = new ReverseNaturalOrdering();
    private static final long serialVersionUID = 0L;

    private ReverseNaturalOrdering() {
    }

    private Object readResolve() {
        return INSTANCE;
    }

    @Override
    public int compare(Comparable comparable, Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        if (comparable != comparable2) return comparable2.compareTo(comparable);
        return 0;
    }

    @Override
    public <E extends Comparable> E max(E e, E e2) {
        return NaturalOrdering.INSTANCE.min(e, e2);
    }

    @Override
    public <E extends Comparable> E max(E e, E e2, E e3, E ... arrE) {
        return NaturalOrdering.INSTANCE.min(e, e2, e3, arrE);
    }

    @Override
    public <E extends Comparable> E max(Iterable<E> iterable) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.min(iterable));
    }

    @Override
    public <E extends Comparable> E max(Iterator<E> iterator2) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.min(iterator2));
    }

    @Override
    public <E extends Comparable> E min(E e, E e2) {
        return NaturalOrdering.INSTANCE.max(e, e2);
    }

    @Override
    public <E extends Comparable> E min(E e, E e2, E e3, E ... arrE) {
        return NaturalOrdering.INSTANCE.max(e, e2, e3, arrE);
    }

    @Override
    public <E extends Comparable> E min(Iterable<E> iterable) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.max(iterable));
    }

    @Override
    public <E extends Comparable> E min(Iterator<E> iterator2) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.max(iterator2));
    }

    @Override
    public <S extends Comparable> Ordering<S> reverse() {
        return Ordering.natural();
    }

    public String toString() {
        return "Ordering.natural().reverse()";
    }
}


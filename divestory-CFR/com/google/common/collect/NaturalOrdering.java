/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.common.collect.ReverseNaturalOrdering;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

final class NaturalOrdering
extends Ordering<Comparable>
implements Serializable {
    static final NaturalOrdering INSTANCE = new NaturalOrdering();
    private static final long serialVersionUID = 0L;
    @MonotonicNonNullDecl
    private transient Ordering<Comparable> nullsFirst;
    @MonotonicNonNullDecl
    private transient Ordering<Comparable> nullsLast;

    private NaturalOrdering() {
    }

    private Object readResolve() {
        return INSTANCE;
    }

    @Override
    public int compare(Comparable comparable, Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        Preconditions.checkNotNull(comparable2);
        return comparable.compareTo(comparable2);
    }

    @Override
    public <S extends Comparable> Ordering<S> nullsFirst() {
        Ordering<Comparable> ordering;
        Ordering<Comparable<Object>> ordering2 = ordering = this.nullsFirst;
        if (ordering != null) return ordering2;
        this.nullsFirst = ordering2 = super.nullsFirst();
        return ordering2;
    }

    @Override
    public <S extends Comparable> Ordering<S> nullsLast() {
        Ordering<Comparable> ordering;
        Ordering<Comparable<Object>> ordering2 = ordering = this.nullsLast;
        if (ordering != null) return ordering2;
        this.nullsLast = ordering2 = super.nullsLast();
        return ordering2;
    }

    @Override
    public <S extends Comparable> Ordering<S> reverse() {
        return ReverseNaturalOrdering.INSTANCE;
    }

    public String toString() {
        return "Ordering.natural()";
    }
}


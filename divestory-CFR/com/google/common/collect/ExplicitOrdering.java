/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class ExplicitOrdering<T>
extends Ordering<T>
implements Serializable {
    private static final long serialVersionUID = 0L;
    final ImmutableMap<T, Integer> rankMap;

    ExplicitOrdering(ImmutableMap<T, Integer> immutableMap) {
        this.rankMap = immutableMap;
    }

    ExplicitOrdering(List<T> list) {
        this(Maps.indexMap(list));
    }

    private int rank(T t) {
        Integer n = this.rankMap.get(t);
        if (n == null) throw new Ordering.IncomparableValueException(t);
        return n;
    }

    @Override
    public int compare(T t, T t2) {
        return this.rank(t) - this.rank(t2);
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (!(object instanceof ExplicitOrdering)) return false;
        object = (ExplicitOrdering)object;
        return this.rankMap.equals(((ExplicitOrdering)object).rankMap);
    }

    public int hashCode() {
        return this.rankMap.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ordering.explicit(");
        stringBuilder.append(this.rankMap.keySet());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}


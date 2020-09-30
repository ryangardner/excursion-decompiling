/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

final class CompoundOrdering<T>
extends Ordering<T>
implements Serializable {
    private static final long serialVersionUID = 0L;
    final Comparator<? super T>[] comparators;

    CompoundOrdering(Iterable<? extends Comparator<? super T>> iterable) {
        this.comparators = Iterables.toArray(iterable, new Comparator[0]);
    }

    CompoundOrdering(Comparator<? super T> comparator, Comparator<? super T> comparator2) {
        this.comparators = new Comparator[]{comparator, comparator2};
    }

    @Override
    public int compare(T t, T t2) {
        Comparator<? super T>[] arrcomparator;
        int n = 0;
        while (n < (arrcomparator = this.comparators).length) {
            int n2 = arrcomparator[n].compare(t, t2);
            if (n2 != 0) {
                return n2;
            }
            ++n;
        }
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CompoundOrdering)) return false;
        object = (CompoundOrdering)object;
        return Arrays.equals(this.comparators, ((CompoundOrdering)object).comparators);
    }

    public int hashCode() {
        return Arrays.hashCode(this.comparators);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ordering.compound(");
        stringBuilder.append(Arrays.toString(this.comparators));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}


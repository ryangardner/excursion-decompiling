/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.common.collect.SortedIterable;
import java.util.Comparator;
import java.util.SortedSet;

final class SortedIterables {
    private SortedIterables() {
    }

    public static <E> Comparator<? super E> comparator(SortedSet<E> ordering) {
        Comparator<E> comparator = ordering.comparator();
        ordering = comparator;
        if (comparator != null) return ordering;
        return Ordering.natural();
    }

    public static boolean hasSameComparator(Comparator<?> comparator, Iterable<?> comparator2) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(comparator2);
        if (comparator2 instanceof SortedSet) {
            comparator2 = SortedIterables.comparator((SortedSet)((Object)comparator2));
            return comparator.equals(comparator2);
        }
        if (!(comparator2 instanceof SortedIterable)) return false;
        comparator2 = ((SortedIterable)((Object)comparator2)).comparator();
        return comparator.equals(comparator2);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.LexicographicalOrdering;
import java.util.Comparator;
import java.util.Iterator;

public final class Comparators {
    private Comparators() {
    }

    public static <T> boolean isInOrder(Iterable<? extends T> iterable, Comparator<T> comparator) {
        Preconditions.checkNotNull(comparator);
        Iterator<T> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) return true;
        iterable = iterator2.next();
        while (iterator2.hasNext()) {
            T t = iterator2.next();
            if (comparator.compare(iterable, (Iterable<T>)t) > 0) {
                return false;
            }
            iterable = t;
        }
        return true;
    }

    public static <T> boolean isInStrictOrder(Iterable<? extends T> iterable, Comparator<T> comparator) {
        Preconditions.checkNotNull(comparator);
        Iterator<T> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) return true;
        iterable = iterator2.next();
        while (iterator2.hasNext()) {
            T t = iterator2.next();
            if (comparator.compare(iterable, (Iterable<T>)t) >= 0) {
                return false;
            }
            iterable = t;
        }
        return true;
    }

    public static <T, S extends T> Comparator<Iterable<S>> lexicographical(Comparator<T> comparator) {
        return new LexicographicalOrdering<T>(Preconditions.checkNotNull(comparator));
    }
}


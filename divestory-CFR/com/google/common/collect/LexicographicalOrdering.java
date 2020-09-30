/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class LexicographicalOrdering<T>
extends Ordering<Iterable<T>>
implements Serializable {
    private static final long serialVersionUID = 0L;
    final Comparator<? super T> elementOrder;

    LexicographicalOrdering(Comparator<? super T> comparator) {
        this.elementOrder = comparator;
    }

    @Override
    public int compare(Iterable<T> object, Iterable<T> object2) {
        int n;
        object = object.iterator();
        object2 = object2.iterator();
        do {
            if (!object.hasNext()) {
                if (!object2.hasNext()) return 0;
                return -1;
            }
            if (object2.hasNext()) continue;
            return 1;
        } while ((n = this.elementOrder.compare(object.next(), object2.next())) == 0);
        return n;
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof LexicographicalOrdering)) return false;
        object = (LexicographicalOrdering)object;
        return this.elementOrder.equals(((LexicographicalOrdering)object).elementOrder);
    }

    public int hashCode() {
        return this.elementOrder.hashCode() ^ 2075626741;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.elementOrder);
        stringBuilder.append(".lexicographical()");
        return stringBuilder.toString();
    }
}


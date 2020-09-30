/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ForwardingSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingSortedSet<E>
extends ForwardingSet<E>
implements SortedSet<E> {
    protected ForwardingSortedSet() {
    }

    private int unsafeCompare(@NullableDecl Object object, @NullableDecl Object object2) {
        Comparator<E> comparator = this.comparator();
        if (comparator != null) return comparator.compare(object, object2);
        return ((Comparable)object).compareTo(object2);
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.delegate().comparator();
    }

    @Override
    protected abstract SortedSet<E> delegate();

    @Override
    public E first() {
        return this.delegate().first();
    }

    @Override
    public SortedSet<E> headSet(E e) {
        return this.delegate().headSet(e);
    }

    @Override
    public E last() {
        return this.delegate().last();
    }

    @Override
    protected boolean standardContains(@NullableDecl Object object) {
        boolean bl = false;
        try {
            int n = this.unsafeCompare(this.tailSet(object).first(), object);
            if (n != 0) return bl;
            return true;
        }
        catch (ClassCastException | NullPointerException | NoSuchElementException runtimeException) {
            return bl;
        }
    }

    @Override
    protected boolean standardRemove(@NullableDecl Object object) {
        try {
            Iterator<E> iterator2 = this.tailSet(object).iterator();
            if (!iterator2.hasNext()) return false;
            if (this.unsafeCompare(iterator2.next(), object) != 0) return false;
            iterator2.remove();
            return true;
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return false;
        }
    }

    protected SortedSet<E> standardSubSet(E e, E e2) {
        return this.tailSet(e).headSet(e2);
    }

    @Override
    public SortedSet<E> subSet(E e, E e2) {
        return this.delegate().subSet(e, e2);
    }

    @Override
    public SortedSet<E> tailSet(E e) {
        return this.delegate().tailSet(e);
    }
}


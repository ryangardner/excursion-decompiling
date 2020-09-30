/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.BoundType;
import com.google.common.collect.DescendingMultiset;
import com.google.common.collect.ForwardingMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.SortedMultisets;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

public abstract class ForwardingSortedMultiset<E>
extends ForwardingMultiset<E>
implements SortedMultiset<E> {
    protected ForwardingSortedMultiset() {
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.delegate().comparator();
    }

    @Override
    protected abstract SortedMultiset<E> delegate();

    @Override
    public SortedMultiset<E> descendingMultiset() {
        return this.delegate().descendingMultiset();
    }

    @Override
    public NavigableSet<E> elementSet() {
        return this.delegate().elementSet();
    }

    @Override
    public Multiset.Entry<E> firstEntry() {
        return this.delegate().firstEntry();
    }

    @Override
    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return this.delegate().headMultiset(e, boundType);
    }

    @Override
    public Multiset.Entry<E> lastEntry() {
        return this.delegate().lastEntry();
    }

    @Override
    public Multiset.Entry<E> pollFirstEntry() {
        return this.delegate().pollFirstEntry();
    }

    @Override
    public Multiset.Entry<E> pollLastEntry() {
        return this.delegate().pollLastEntry();
    }

    protected Multiset.Entry<E> standardFirstEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.entrySet().iterator();
        if (!iterator2.hasNext()) {
            return null;
        }
        iterator2 = iterator2.next();
        return Multisets.immutableEntry(iterator2.getElement(), iterator2.getCount());
    }

    protected Multiset.Entry<E> standardLastEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.descendingMultiset().entrySet().iterator();
        if (!iterator2.hasNext()) {
            return null;
        }
        iterator2 = iterator2.next();
        return Multisets.immutableEntry(iterator2.getElement(), iterator2.getCount());
    }

    protected Multiset.Entry<E> standardPollFirstEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.entrySet().iterator();
        if (!iterator2.hasNext()) {
            return null;
        }
        Multiset.Entry<E> entry = iterator2.next();
        entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        iterator2.remove();
        return entry;
    }

    protected Multiset.Entry<E> standardPollLastEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.descendingMultiset().entrySet().iterator();
        if (!iterator2.hasNext()) {
            return null;
        }
        Multiset.Entry<E> entry = iterator2.next();
        entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        iterator2.remove();
        return entry;
    }

    protected SortedMultiset<E> standardSubMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return this.tailMultiset(e, boundType).headMultiset(e2, boundType2);
    }

    @Override
    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return this.delegate().subMultiset(e, boundType, e2, boundType2);
    }

    @Override
    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return this.delegate().tailMultiset(e, boundType);
    }

    protected abstract class StandardDescendingMultiset
    extends DescendingMultiset<E> {
        @Override
        SortedMultiset<E> forwardMultiset() {
            return ForwardingSortedMultiset.this;
        }
    }

    protected class StandardElementSet
    extends SortedMultisets.NavigableElementSet<E> {
        public StandardElementSet() {
            super(ForwardingSortedMultiset.this);
        }
    }

}


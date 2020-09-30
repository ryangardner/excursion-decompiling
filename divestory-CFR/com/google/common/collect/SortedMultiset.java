/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.BoundType;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedIterable;
import com.google.common.collect.SortedMultisetBridge;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;

public interface SortedMultiset<E>
extends SortedMultisetBridge<E>,
SortedIterable<E> {
    @Override
    public Comparator<? super E> comparator();

    public SortedMultiset<E> descendingMultiset();

    @Override
    public NavigableSet<E> elementSet();

    @Override
    public Set<Multiset.Entry<E>> entrySet();

    public Multiset.Entry<E> firstEntry();

    public SortedMultiset<E> headMultiset(E var1, BoundType var2);

    @Override
    public Iterator<E> iterator();

    public Multiset.Entry<E> lastEntry();

    public Multiset.Entry<E> pollFirstEntry();

    public Multiset.Entry<E> pollLastEntry();

    public SortedMultiset<E> subMultiset(E var1, BoundType var2, E var3, BoundType var4);

    public SortedMultiset<E> tailMultiset(E var1, BoundType var2);
}


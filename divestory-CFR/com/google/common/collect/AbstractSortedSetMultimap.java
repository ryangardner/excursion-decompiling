/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.common.collect.AbstractSetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractSortedSetMultimap<K, V>
extends AbstractSetMultimap<K, V>
implements SortedSetMultimap<K, V> {
    private static final long serialVersionUID = 430848587173315748L;

    protected AbstractSortedSetMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override
    abstract SortedSet<V> createCollection();

    @Override
    SortedSet<V> createUnmodifiableEmptyCollection() {
        return this.unmodifiableCollectionSubclass(this.createCollection());
    }

    @Override
    public SortedSet<V> get(@NullableDecl K k) {
        return (SortedSet)super.get((Object)k);
    }

    @Override
    public SortedSet<V> removeAll(@NullableDecl Object object) {
        return (SortedSet)super.removeAll(object);
    }

    @Override
    public SortedSet<V> replaceValues(@NullableDecl K k, Iterable<? extends V> iterable) {
        return (SortedSet)super.replaceValues((Object)k, (Iterable)iterable);
    }

    @Override
    <E> SortedSet<E> unmodifiableCollectionSubclass(Collection<E> collection) {
        if (!(collection instanceof NavigableSet)) return Collections.unmodifiableSortedSet((SortedSet)collection);
        return Sets.unmodifiableNavigableSet((NavigableSet)collection);
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    @Override
    Collection<V> wrapCollection(K k, Collection<V> collection) {
        if (!(collection instanceof NavigableSet)) return new AbstractMapBasedMultimap.WrappedSortedSet((AbstractMapBasedMultimap)this, k, (SortedSet)collection, null);
        return new AbstractMapBasedMultimap.WrappedNavigableSet((AbstractMapBasedMultimap)this, k, (NavigableSet)collection, null);
    }
}


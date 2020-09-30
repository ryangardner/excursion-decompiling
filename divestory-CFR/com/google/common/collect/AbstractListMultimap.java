/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.common.collect.ListMultimap;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractListMultimap<K, V>
extends AbstractMapBasedMultimap<K, V>
implements ListMultimap<K, V> {
    private static final long serialVersionUID = 6588350623831699109L;

    protected AbstractListMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override
    abstract List<V> createCollection();

    @Override
    List<V> createUnmodifiableEmptyCollection() {
        return Collections.emptyList();
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        return super.equals(object);
    }

    @Override
    public List<V> get(@NullableDecl K k) {
        return (List)super.get(k);
    }

    @Override
    public boolean put(@NullableDecl K k, @NullableDecl V v) {
        return super.put(k, v);
    }

    @Override
    public List<V> removeAll(@NullableDecl Object object) {
        return (List)super.removeAll(object);
    }

    @Override
    public List<V> replaceValues(@NullableDecl K k, Iterable<? extends V> iterable) {
        return (List)super.replaceValues(k, iterable);
    }

    @Override
    <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> collection) {
        return Collections.unmodifiableList((List)collection);
    }

    @Override
    Collection<V> wrapCollection(K k, Collection<V> collection) {
        return this.wrapList(k, (List)collection, null);
    }
}


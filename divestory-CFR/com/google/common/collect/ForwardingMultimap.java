/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ForwardingObject;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingMultimap<K, V>
extends ForwardingObject
implements Multimap<K, V> {
    protected ForwardingMultimap() {
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return this.delegate().asMap();
    }

    @Override
    public void clear() {
        this.delegate().clear();
    }

    @Override
    public boolean containsEntry(@NullableDecl Object object, @NullableDecl Object object2) {
        return this.delegate().containsEntry(object, object2);
    }

    @Override
    public boolean containsKey(@NullableDecl Object object) {
        return this.delegate().containsKey(object);
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        return this.delegate().containsValue(object);
    }

    @Override
    protected abstract Multimap<K, V> delegate();

    @Override
    public Collection<Map.Entry<K, V>> entries() {
        return this.delegate().entries();
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (object == this) return true;
        if (this.delegate().equals(object)) return true;
        return false;
    }

    @Override
    public Collection<V> get(@NullableDecl K k) {
        return this.delegate().get(k);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }

    @Override
    public Set<K> keySet() {
        return this.delegate().keySet();
    }

    @Override
    public Multiset<K> keys() {
        return this.delegate().keys();
    }

    @Override
    public boolean put(K k, V v) {
        return this.delegate().put(k, v);
    }

    @Override
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        return this.delegate().putAll(multimap);
    }

    @Override
    public boolean putAll(K k, Iterable<? extends V> iterable) {
        return this.delegate().putAll(k, iterable);
    }

    @Override
    public boolean remove(@NullableDecl Object object, @NullableDecl Object object2) {
        return this.delegate().remove(object, object2);
    }

    @Override
    public Collection<V> removeAll(@NullableDecl Object object) {
        return this.delegate().removeAll(object);
    }

    @Override
    public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return this.delegate().replaceValues(k, iterable);
    }

    @Override
    public int size() {
        return this.delegate().size();
    }

    @Override
    public Collection<V> values() {
        return this.delegate().values();
    }
}


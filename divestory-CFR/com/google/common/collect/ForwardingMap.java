/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingMap<K, V>
extends ForwardingObject
implements Map<K, V> {
    protected ForwardingMap() {
    }

    @Override
    public void clear() {
        this.delegate().clear();
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
    protected abstract Map<K, V> delegate();

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.delegate().entrySet();
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (object == this) return true;
        if (this.delegate().equals(object)) return true;
        return false;
    }

    @Override
    public V get(@NullableDecl Object object) {
        return this.delegate().get(object);
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
    public V put(K k, V v) {
        return this.delegate().put(k, v);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        this.delegate().putAll(map);
    }

    @Override
    public V remove(Object object) {
        return this.delegate().remove(object);
    }

    @Override
    public int size() {
        return this.delegate().size();
    }

    protected void standardClear() {
        Iterators.clear(this.entrySet().iterator());
    }

    protected boolean standardContainsKey(@NullableDecl Object object) {
        return Maps.containsKeyImpl(this, object);
    }

    protected boolean standardContainsValue(@NullableDecl Object object) {
        return Maps.containsValueImpl(this, object);
    }

    protected boolean standardEquals(@NullableDecl Object object) {
        return Maps.equalsImpl(this, object);
    }

    protected int standardHashCode() {
        return Sets.hashCodeImpl(this.entrySet());
    }

    protected boolean standardIsEmpty() {
        return this.entrySet().iterator().hasNext() ^ true;
    }

    protected void standardPutAll(Map<? extends K, ? extends V> map) {
        Maps.putAllImpl(this, map);
    }

    protected V standardRemove(@NullableDecl Object object) {
        Map.Entry<K, V> entry;
        Iterator<Map.Entry<K, V>> iterator2 = this.entrySet().iterator();
        do {
            if (!iterator2.hasNext()) return null;
        } while (!Objects.equal((entry = iterator2.next()).getKey(), object));
        object = entry.getValue();
        iterator2.remove();
        return (V)object;
    }

    protected String standardToString() {
        return Maps.toStringImpl(this);
    }

    @Override
    public Collection<V> values() {
        return this.delegate().values();
    }

    protected abstract class StandardEntrySet
    extends Maps.EntrySet<K, V> {
        @Override
        Map<K, V> map() {
            return ForwardingMap.this;
        }
    }

    protected class StandardKeySet
    extends Maps.KeySet<K, V> {
        public StandardKeySet() {
            super(ForwardingMap.this);
        }
    }

    protected class StandardValues
    extends Maps.Values<K, V> {
        public StandardValues() {
            super(ForwardingMap.this);
        }
    }

}


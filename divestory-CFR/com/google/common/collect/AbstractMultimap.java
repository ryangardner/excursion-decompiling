/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractMultimap<K, V>
implements Multimap<K, V> {
    @MonotonicNonNullDecl
    private transient Map<K, Collection<V>> asMap;
    @MonotonicNonNullDecl
    private transient Collection<Map.Entry<K, V>> entries;
    @MonotonicNonNullDecl
    private transient Set<K> keySet;
    @MonotonicNonNullDecl
    private transient Multiset<K> keys;
    @MonotonicNonNullDecl
    private transient Collection<V> values;

    AbstractMultimap() {
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<Collection<Collection<Collection<V>>>>> map;
        Map<K, Collection<Collection<Collection<Collection<V>>>>> map2 = map = this.asMap;
        if (map != null) return map2;
        map2 = this.createAsMap();
        this.asMap = map2;
        return map2;
    }

    @Override
    public boolean containsEntry(@NullableDecl Object collection, @NullableDecl Object object) {
        collection = this.asMap().get(collection);
        if (collection == null) return false;
        if (!collection.contains(object)) return false;
        return true;
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        Iterator<Collection<V>> iterator2 = this.asMap().values().iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while (!iterator2.next().contains(object));
        return true;
    }

    abstract Map<K, Collection<V>> createAsMap();

    abstract Collection<Map.Entry<K, V>> createEntries();

    abstract Set<K> createKeySet();

    abstract Multiset<K> createKeys();

    abstract Collection<V> createValues();

    @Override
    public Collection<Map.Entry<K, V>> entries() {
        Collection<Map.Entry<K, V>> collection;
        Collection<Map.Entry<K, V>> collection2 = collection = this.entries;
        if (collection != null) return collection2;
        this.entries = collection2 = this.createEntries();
        return collection2;
    }

    abstract Iterator<Map.Entry<K, V>> entryIterator();

    @Override
    public boolean equals(@NullableDecl Object object) {
        return Multimaps.equalsImpl(this, object);
    }

    @Override
    public int hashCode() {
        return this.asMap().hashCode();
    }

    @Override
    public boolean isEmpty() {
        if (this.size() != 0) return false;
        return true;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set;
        Set<K> set2 = set = this.keySet;
        if (set != null) return set2;
        this.keySet = set2 = this.createKeySet();
        return set2;
    }

    @Override
    public Multiset<K> keys() {
        Multiset<K> multiset;
        Multiset<K> multiset2 = multiset = this.keys;
        if (multiset != null) return multiset2;
        this.keys = multiset2 = this.createKeys();
        return multiset2;
    }

    @Override
    public boolean put(@NullableDecl K k, @NullableDecl V v) {
        return this.get(k).add(v);
    }

    @Override
    public boolean putAll(Multimap<? extends K, ? extends V> object) {
        Iterator<Map.Entry<K, V>> iterator2 = object.entries().iterator();
        boolean bl = false;
        while (iterator2.hasNext()) {
            object = iterator2.next();
            bl |= this.put(object.getKey(), object.getValue());
        }
        return bl;
    }

    @Override
    public boolean putAll(@NullableDecl K k, Iterable<? extends V> object) {
        Preconditions.checkNotNull(object);
        boolean bl = object instanceof Collection;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            if ((object = (Collection)object).isEmpty()) return false;
            if (!this.get(k).addAll(object)) return false;
            return bl3;
        }
        if (!(object = object.iterator()).hasNext()) return false;
        if (!Iterators.addAll(this.get(k), object)) return false;
        return bl2;
    }

    @Override
    public boolean remove(@NullableDecl Object collection, @NullableDecl Object object) {
        collection = this.asMap().get(collection);
        if (collection == null) return false;
        if (!collection.remove(object)) return false;
        return true;
    }

    @Override
    public Collection<V> replaceValues(@NullableDecl K k, Iterable<? extends V> iterable) {
        Preconditions.checkNotNull(iterable);
        Collection<V> collection = this.removeAll(k);
        this.putAll(k, iterable);
        return collection;
    }

    public String toString() {
        return this.asMap().toString();
    }

    Iterator<V> valueIterator() {
        return Maps.valueIterator(this.entries().iterator());
    }

    @Override
    public Collection<V> values() {
        Collection<V> collection;
        Collection<V> collection2 = collection = this.values;
        if (collection != null) return collection2;
        this.values = collection2 = this.createValues();
        return collection2;
    }

    class Entries
    extends Multimaps.Entries<K, V> {
        Entries() {
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return AbstractMultimap.this.entryIterator();
        }

        @Override
        Multimap<K, V> multimap() {
            return AbstractMultimap.this;
        }
    }

    class EntrySet
    extends AbstractMultimap<K, V>
    implements Set<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            return Sets.equalsImpl(this, object);
        }

        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }

    class Values
    extends AbstractCollection<V> {
        Values() {
        }

        @Override
        public void clear() {
            AbstractMultimap.this.clear();
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            return AbstractMultimap.this.containsValue(object);
        }

        @Override
        public Iterator<V> iterator() {
            return AbstractMultimap.this.valueIterator();
        }

        @Override
        public int size() {
            return AbstractMultimap.this.size();
        }
    }

}


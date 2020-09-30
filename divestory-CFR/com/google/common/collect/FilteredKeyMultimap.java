/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.FilteredMultimap;
import com.google.common.collect.FilteredMultimapValues;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class FilteredKeyMultimap<K, V>
extends AbstractMultimap<K, V>
implements FilteredMultimap<K, V> {
    final Predicate<? super K> keyPredicate;
    final Multimap<K, V> unfiltered;

    FilteredKeyMultimap(Multimap<K, V> multimap, Predicate<? super K> predicate) {
        this.unfiltered = Preconditions.checkNotNull(multimap);
        this.keyPredicate = Preconditions.checkNotNull(predicate);
    }

    @Override
    public void clear() {
        this.keySet().clear();
    }

    @Override
    public boolean containsKey(@NullableDecl Object object) {
        if (!this.unfiltered.containsKey(object)) return false;
        return this.keyPredicate.apply(object);
    }

    @Override
    Map<K, Collection<V>> createAsMap() {
        return Maps.filterKeys(this.unfiltered.asMap(), this.keyPredicate);
    }

    @Override
    Collection<Map.Entry<K, V>> createEntries() {
        return new Entries();
    }

    @Override
    Set<K> createKeySet() {
        return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
    }

    @Override
    Multiset<K> createKeys() {
        return Multisets.filter(this.unfiltered.keys(), this.keyPredicate);
    }

    @Override
    Collection<V> createValues() {
        return new FilteredMultimapValues(this);
    }

    @Override
    Iterator<Map.Entry<K, V>> entryIterator() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return Maps.keyPredicateOnEntries(this.keyPredicate);
    }

    @Override
    public Collection<V> get(K k) {
        if (this.keyPredicate.apply(k)) {
            return this.unfiltered.get(k);
        }
        if (!(this.unfiltered instanceof SetMultimap)) return new AddRejectingList(k);
        return new AddRejectingSet(k);
    }

    @Override
    public Collection<V> removeAll(Object collection) {
        if (!this.containsKey(collection)) return this.unmodifiableEmptyCollection();
        return this.unfiltered.removeAll(collection);
    }

    @Override
    public int size() {
        Iterator iterator2 = this.asMap().values().iterator();
        int n = 0;
        while (iterator2.hasNext()) {
            n += iterator2.next().size();
        }
        return n;
    }

    @Override
    public Multimap<K, V> unfiltered() {
        return this.unfiltered;
    }

    Collection<V> unmodifiableEmptyCollection() {
        if (!(this.unfiltered instanceof SetMultimap)) return ImmutableList.of();
        return ImmutableSet.of();
    }

    static class AddRejectingList<K, V>
    extends ForwardingList<V> {
        final K key;

        AddRejectingList(K k) {
            this.key = k;
        }

        @Override
        public void add(int n, V object) {
            Preconditions.checkPositionIndex(n, 0);
            object = new StringBuilder();
            ((StringBuilder)object).append("Key does not satisfy predicate: ");
            ((StringBuilder)object).append(this.key);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @Override
        public boolean add(V v) {
            this.add(0, v);
            return true;
        }

        @Override
        public boolean addAll(int n, Collection<? extends V> object) {
            Preconditions.checkNotNull(object);
            Preconditions.checkPositionIndex(n, 0);
            object = new StringBuilder();
            ((StringBuilder)object).append("Key does not satisfy predicate: ");
            ((StringBuilder)object).append(this.key);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @Override
        public boolean addAll(Collection<? extends V> collection) {
            this.addAll(0, collection);
            return true;
        }

        @Override
        protected List<V> delegate() {
            return Collections.emptyList();
        }
    }

    static class AddRejectingSet<K, V>
    extends ForwardingSet<V> {
        final K key;

        AddRejectingSet(K k) {
            this.key = k;
        }

        @Override
        public boolean add(V object) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Key does not satisfy predicate: ");
            ((StringBuilder)object).append(this.key);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @Override
        public boolean addAll(Collection<? extends V> object) {
            Preconditions.checkNotNull(object);
            object = new StringBuilder();
            ((StringBuilder)object).append("Key does not satisfy predicate: ");
            ((StringBuilder)object).append(this.key);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @Override
        protected Set<V> delegate() {
            return Collections.emptySet();
        }
    }

    class Entries
    extends ForwardingCollection<Map.Entry<K, V>> {
        Entries() {
        }

        @Override
        protected Collection<Map.Entry<K, V>> delegate() {
            return Collections2.filter(FilteredKeyMultimap.this.unfiltered.entries(), FilteredKeyMultimap.this.entryPredicate());
        }

        @Override
        public boolean remove(@NullableDecl Object object) {
            if (!(object instanceof Map.Entry)) return false;
            if (!FilteredKeyMultimap.this.unfiltered.containsKey((object = (Map.Entry)object).getKey())) return false;
            if (!FilteredKeyMultimap.this.keyPredicate.apply(object.getKey())) return false;
            return FilteredKeyMultimap.this.unfiltered.remove(object.getKey(), object.getValue());
        }
    }

}


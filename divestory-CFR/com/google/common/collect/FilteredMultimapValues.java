/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FilteredMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class FilteredMultimapValues<K, V>
extends AbstractCollection<V> {
    private final FilteredMultimap<K, V> multimap;

    FilteredMultimapValues(FilteredMultimap<K, V> filteredMultimap) {
        this.multimap = Preconditions.checkNotNull(filteredMultimap);
    }

    @Override
    public void clear() {
        this.multimap.clear();
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        return this.multimap.containsValue(object);
    }

    @Override
    public Iterator<V> iterator() {
        return Maps.valueIterator(this.multimap.entries().iterator());
    }

    @Override
    public boolean remove(@NullableDecl Object object) {
        Map.Entry<K, V> entry;
        Predicate<Map.Entry<K, V>> predicate = this.multimap.entryPredicate();
        Iterator<Map.Entry<K, V>> iterator2 = this.multimap.unfiltered().entries().iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while (!predicate.apply(entry = iterator2.next()) || !Objects.equal(entry.getValue(), object));
        iterator2.remove();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return Iterables.removeIf(this.multimap.unfiltered().entries(), Predicates.and(this.multimap.entryPredicate(), Maps.valuePredicateOnEntries(Predicates.in(collection))));
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return Iterables.removeIf(this.multimap.unfiltered().entries(), Predicates.and(this.multimap.entryPredicate(), Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(collection)))));
    }

    @Override
    public int size() {
        return this.multimap.size();
    }
}


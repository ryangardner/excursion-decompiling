/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractNavigableMap<K, V>
extends Maps.IteratorBasedAbstractMap<K, V>
implements NavigableMap<K, V> {
    AbstractNavigableMap() {
    }

    @NullableDecl
    @Override
    public Map.Entry<K, V> ceilingEntry(K k) {
        return this.tailMap(k, true).firstEntry();
    }

    @Override
    public K ceilingKey(K k) {
        return Maps.keyOrNull(this.ceilingEntry(k));
    }

    abstract Iterator<Map.Entry<K, V>> descendingEntryIterator();

    @Override
    public NavigableSet<K> descendingKeySet() {
        return this.descendingMap().navigableKeySet();
    }

    @Override
    public NavigableMap<K, V> descendingMap() {
        return new DescendingMap();
    }

    @NullableDecl
    @Override
    public Map.Entry<K, V> firstEntry() {
        return Iterators.getNext(this.entryIterator(), null);
    }

    @Override
    public K firstKey() {
        Map.Entry<K, V> entry = this.firstEntry();
        if (entry == null) throw new NoSuchElementException();
        return entry.getKey();
    }

    @NullableDecl
    @Override
    public Map.Entry<K, V> floorEntry(K k) {
        return this.headMap(k, true).lastEntry();
    }

    @Override
    public K floorKey(K k) {
        return Maps.keyOrNull(this.floorEntry(k));
    }

    @NullableDecl
    @Override
    public abstract V get(@NullableDecl Object var1);

    @Override
    public SortedMap<K, V> headMap(K k) {
        return this.headMap(k, false);
    }

    @NullableDecl
    @Override
    public Map.Entry<K, V> higherEntry(K k) {
        return this.tailMap(k, false).firstEntry();
    }

    @Override
    public K higherKey(K k) {
        return Maps.keyOrNull(this.higherEntry(k));
    }

    @Override
    public Set<K> keySet() {
        return this.navigableKeySet();
    }

    @NullableDecl
    @Override
    public Map.Entry<K, V> lastEntry() {
        return Iterators.getNext(this.descendingEntryIterator(), null);
    }

    @Override
    public K lastKey() {
        Map.Entry<K, V> entry = this.lastEntry();
        if (entry == null) throw new NoSuchElementException();
        return entry.getKey();
    }

    @NullableDecl
    @Override
    public Map.Entry<K, V> lowerEntry(K k) {
        return this.headMap(k, false).lastEntry();
    }

    @Override
    public K lowerKey(K k) {
        return Maps.keyOrNull(this.lowerEntry(k));
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        return new Maps.NavigableKeySet<K, V>(this);
    }

    @NullableDecl
    @Override
    public Map.Entry<K, V> pollFirstEntry() {
        return Iterators.pollNext(this.entryIterator());
    }

    @NullableDecl
    @Override
    public Map.Entry<K, V> pollLastEntry() {
        return Iterators.pollNext(this.descendingEntryIterator());
    }

    @Override
    public SortedMap<K, V> subMap(K k, K k2) {
        return this.subMap(k, true, k2, false);
    }

    @Override
    public SortedMap<K, V> tailMap(K k) {
        return this.tailMap(k, true);
    }

    private final class DescendingMap
    extends Maps.DescendingMap<K, V> {
        private DescendingMap() {
        }

        @Override
        Iterator<Map.Entry<K, V>> entryIterator() {
            return AbstractNavigableMap.this.descendingEntryIterator();
        }

        @Override
        NavigableMap<K, V> forward() {
            return AbstractNavigableMap.this;
        }
    }

}


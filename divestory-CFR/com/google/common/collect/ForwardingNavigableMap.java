/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ForwardingSortedMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

public abstract class ForwardingNavigableMap<K, V>
extends ForwardingSortedMap<K, V>
implements NavigableMap<K, V> {
    protected ForwardingNavigableMap() {
    }

    @Override
    public Map.Entry<K, V> ceilingEntry(K k) {
        return this.delegate().ceilingEntry(k);
    }

    @Override
    public K ceilingKey(K k) {
        return this.delegate().ceilingKey(k);
    }

    @Override
    protected abstract NavigableMap<K, V> delegate();

    @Override
    public NavigableSet<K> descendingKeySet() {
        return this.delegate().descendingKeySet();
    }

    @Override
    public NavigableMap<K, V> descendingMap() {
        return this.delegate().descendingMap();
    }

    @Override
    public Map.Entry<K, V> firstEntry() {
        return this.delegate().firstEntry();
    }

    @Override
    public Map.Entry<K, V> floorEntry(K k) {
        return this.delegate().floorEntry(k);
    }

    @Override
    public K floorKey(K k) {
        return this.delegate().floorKey(k);
    }

    @Override
    public NavigableMap<K, V> headMap(K k, boolean bl) {
        return this.delegate().headMap(k, bl);
    }

    @Override
    public Map.Entry<K, V> higherEntry(K k) {
        return this.delegate().higherEntry(k);
    }

    @Override
    public K higherKey(K k) {
        return this.delegate().higherKey(k);
    }

    @Override
    public Map.Entry<K, V> lastEntry() {
        return this.delegate().lastEntry();
    }

    @Override
    public Map.Entry<K, V> lowerEntry(K k) {
        return this.delegate().lowerEntry(k);
    }

    @Override
    public K lowerKey(K k) {
        return this.delegate().lowerKey(k);
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        return this.delegate().navigableKeySet();
    }

    @Override
    public Map.Entry<K, V> pollFirstEntry() {
        return this.delegate().pollFirstEntry();
    }

    @Override
    public Map.Entry<K, V> pollLastEntry() {
        return this.delegate().pollLastEntry();
    }

    protected Map.Entry<K, V> standardCeilingEntry(K k) {
        return this.tailMap(k, true).firstEntry();
    }

    protected K standardCeilingKey(K k) {
        return Maps.keyOrNull(this.ceilingEntry(k));
    }

    protected NavigableSet<K> standardDescendingKeySet() {
        return this.descendingMap().navigableKeySet();
    }

    protected Map.Entry<K, V> standardFirstEntry() {
        return Iterables.getFirst(this.entrySet(), null);
    }

    protected K standardFirstKey() {
        Map.Entry<K, V> entry = this.firstEntry();
        if (entry == null) throw new NoSuchElementException();
        return entry.getKey();
    }

    protected Map.Entry<K, V> standardFloorEntry(K k) {
        return this.headMap(k, true).lastEntry();
    }

    protected K standardFloorKey(K k) {
        return Maps.keyOrNull(this.floorEntry(k));
    }

    protected SortedMap<K, V> standardHeadMap(K k) {
        return this.headMap(k, false);
    }

    protected Map.Entry<K, V> standardHigherEntry(K k) {
        return this.tailMap(k, false).firstEntry();
    }

    protected K standardHigherKey(K k) {
        return Maps.keyOrNull(this.higherEntry(k));
    }

    protected Map.Entry<K, V> standardLastEntry() {
        return Iterables.getFirst(this.descendingMap().entrySet(), null);
    }

    protected K standardLastKey() {
        Map.Entry<K, V> entry = this.lastEntry();
        if (entry == null) throw new NoSuchElementException();
        return entry.getKey();
    }

    protected Map.Entry<K, V> standardLowerEntry(K k) {
        return this.headMap(k, false).lastEntry();
    }

    protected K standardLowerKey(K k) {
        return Maps.keyOrNull(this.lowerEntry(k));
    }

    protected Map.Entry<K, V> standardPollFirstEntry() {
        return Iterators.pollNext(this.entrySet().iterator());
    }

    protected Map.Entry<K, V> standardPollLastEntry() {
        return Iterators.pollNext(this.descendingMap().entrySet().iterator());
    }

    @Override
    protected SortedMap<K, V> standardSubMap(K k, K k2) {
        return this.subMap(k, true, k2, false);
    }

    protected SortedMap<K, V> standardTailMap(K k) {
        return this.tailMap(k, true);
    }

    @Override
    public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
        return this.delegate().subMap(k, bl, k2, bl2);
    }

    @Override
    public NavigableMap<K, V> tailMap(K k, boolean bl) {
        return this.delegate().tailMap(k, bl);
    }

    protected class StandardDescendingMap
    extends Maps.DescendingMap<K, V> {
        @Override
        protected Iterator<Map.Entry<K, V>> entryIterator() {
            return new Iterator<Map.Entry<K, V>>(){
                private Map.Entry<K, V> nextOrNull = StandardDescendingMap.this.forward().lastEntry();
                private Map.Entry<K, V> toRemove = null;

                @Override
                public boolean hasNext() {
                    if (this.nextOrNull == null) return false;
                    return true;
                }

                @Override
                public Map.Entry<K, V> next() {
                    if (!this.hasNext()) throw new NoSuchElementException();
                    try {
                        Map.Entry<K, V> entry = this.nextOrNull;
                        return entry;
                    }
                    finally {
                        this.toRemove = this.nextOrNull;
                        this.nextOrNull = StandardDescendingMap.this.forward().lowerEntry(this.nextOrNull.getKey());
                    }
                }

                @Override
                public void remove() {
                    boolean bl = this.toRemove != null;
                    CollectPreconditions.checkRemove(bl);
                    StandardDescendingMap.this.forward().remove(this.toRemove.getKey());
                    this.toRemove = null;
                }
            };
        }

        @Override
        NavigableMap<K, V> forward() {
            return ForwardingNavigableMap.this;
        }

    }

    protected class StandardNavigableKeySet
    extends Maps.NavigableKeySet<K, V> {
        public StandardNavigableKeySet() {
            super(ForwardingNavigableMap.this);
        }
    }

}


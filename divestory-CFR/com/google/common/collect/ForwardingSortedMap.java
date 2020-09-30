/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingSortedMap<K, V>
extends ForwardingMap<K, V>
implements SortedMap<K, V> {
    protected ForwardingSortedMap() {
    }

    private int unsafeCompare(Object object, Object object2) {
        Comparator<K> comparator = this.comparator();
        if (comparator != null) return comparator.compare(object, object2);
        return ((Comparable)object).compareTo(object2);
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.delegate().comparator();
    }

    @Override
    protected abstract SortedMap<K, V> delegate();

    @Override
    public K firstKey() {
        return this.delegate().firstKey();
    }

    @Override
    public SortedMap<K, V> headMap(K k) {
        return this.delegate().headMap(k);
    }

    @Override
    public K lastKey() {
        return this.delegate().lastKey();
    }

    @Override
    protected boolean standardContainsKey(@NullableDecl Object object) {
        boolean bl = false;
        try {
            int n = this.unsafeCompare(this.tailMap(object).firstKey(), object);
            if (n != 0) return bl;
            return true;
        }
        catch (ClassCastException | NullPointerException | NoSuchElementException runtimeException) {
            return bl;
        }
    }

    protected SortedMap<K, V> standardSubMap(K k, K k2) {
        boolean bl = this.unsafeCompare(k, k2) <= 0;
        Preconditions.checkArgument(bl, "fromKey must be <= toKey");
        return this.tailMap(k).headMap(k2);
    }

    @Override
    public SortedMap<K, V> subMap(K k, K k2) {
        return this.delegate().subMap(k, k2);
    }

    @Override
    public SortedMap<K, V> tailMap(K k) {
        return this.delegate().tailMap(k);
    }

    protected class StandardKeySet
    extends Maps.SortedKeySet<K, V> {
        public StandardKeySet() {
            super(ForwardingSortedMap.this);
        }
    }

}


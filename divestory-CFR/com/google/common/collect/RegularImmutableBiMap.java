/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.RegularImmutableMap;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularImmutableBiMap<K, V>
extends ImmutableBiMap<K, V> {
    static final RegularImmutableBiMap<Object, Object> EMPTY = new RegularImmutableBiMap<K, V>();
    final transient Object[] alternatingKeysAndValues;
    private final transient RegularImmutableBiMap<V, K> inverse;
    private final transient Object keyHashTable;
    private final transient int keyOffset;
    private final transient int size;

    private RegularImmutableBiMap() {
        this.keyHashTable = null;
        this.alternatingKeysAndValues = new Object[0];
        this.keyOffset = 0;
        this.size = 0;
        this.inverse = this;
    }

    private RegularImmutableBiMap(Object object, Object[] arrobject, int n, RegularImmutableBiMap<V, K> regularImmutableBiMap) {
        this.keyHashTable = object;
        this.alternatingKeysAndValues = arrobject;
        this.keyOffset = 1;
        this.size = n;
        this.inverse = regularImmutableBiMap;
    }

    RegularImmutableBiMap(Object[] arrobject, int n) {
        this.alternatingKeysAndValues = arrobject;
        this.size = n;
        this.keyOffset = 0;
        int n2 = n >= 2 ? ImmutableSet.chooseTableSize(n) : 0;
        this.keyHashTable = RegularImmutableMap.createHashTable(arrobject, n, n2, 0);
        this.inverse = new RegularImmutableBiMap<K, V>(RegularImmutableMap.createHashTable(arrobject, n, n2, 1), arrobject, n, this);
    }

    @Override
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new RegularImmutableMap.EntrySet(this, this.alternatingKeysAndValues, this.keyOffset, this.size);
    }

    @Override
    ImmutableSet<K> createKeySet() {
        return new RegularImmutableMap.KeySet<Object>(this, new RegularImmutableMap.KeysOrValuesAsList(this.alternatingKeysAndValues, this.keyOffset, this.size));
    }

    @Override
    public V get(@NullableDecl Object object) {
        return (V)RegularImmutableMap.get(this.keyHashTable, this.alternatingKeysAndValues, this.size, this.keyOffset, object);
    }

    @Override
    public ImmutableBiMap<V, K> inverse() {
        return this.inverse;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }
}


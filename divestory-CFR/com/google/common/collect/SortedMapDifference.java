/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.MapDifference;
import java.util.SortedMap;

public interface SortedMapDifference<K, V>
extends MapDifference<K, V> {
    @Override
    public SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering();

    @Override
    public SortedMap<K, V> entriesInCommon();

    @Override
    public SortedMap<K, V> entriesOnlyOnLeft();

    @Override
    public SortedMap<K, V> entriesOnlyOnRight();
}


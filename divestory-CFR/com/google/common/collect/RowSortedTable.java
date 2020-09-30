/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Table;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

public interface RowSortedTable<R, C, V>
extends Table<R, C, V> {
    @Override
    public SortedSet<R> rowKeySet();

    @Override
    public SortedMap<R, Map<C, V>> rowMap();
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.RowSortedTable;
import com.google.common.collect.StandardTable;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

class StandardRowSortedTable<R, C, V>
extends StandardTable<R, C, V>
implements RowSortedTable<R, C, V> {
    private static final long serialVersionUID = 0L;

    StandardRowSortedTable(SortedMap<R, Map<C, V>> sortedMap, Supplier<? extends Map<C, V>> supplier) {
        super(sortedMap, supplier);
    }

    private SortedMap<R, Map<C, V>> sortedBackingMap() {
        return (SortedMap)this.backingMap;
    }

    @Override
    SortedMap<R, Map<C, V>> createRowMap() {
        return new RowSortedMap();
    }

    @Override
    public SortedSet<R> rowKeySet() {
        return (SortedSet)this.rowMap().keySet();
    }

    @Override
    public SortedMap<R, Map<C, V>> rowMap() {
        return (SortedMap)super.rowMap();
    }

    private class RowSortedMap
    extends StandardTable<R, C, V>
    implements SortedMap<R, Map<C, V>> {
        private RowSortedMap() {
        }

        @Override
        public Comparator<? super R> comparator() {
            return StandardRowSortedTable.this.sortedBackingMap().comparator();
        }

        SortedSet<R> createKeySet() {
            return new Maps.SortedKeySet(this);
        }

        @Override
        public R firstKey() {
            return (R)StandardRowSortedTable.this.sortedBackingMap().firstKey();
        }

        @Override
        public SortedMap<R, Map<C, V>> headMap(R r) {
            Preconditions.checkNotNull(r);
            return new StandardRowSortedTable(StandardRowSortedTable.this.sortedBackingMap().headMap(r), StandardRowSortedTable.this.factory).rowMap();
        }

        @Override
        public SortedSet<R> keySet() {
            return (SortedSet)StandardTable.RowMap.super.keySet();
        }

        @Override
        public R lastKey() {
            return (R)StandardRowSortedTable.this.sortedBackingMap().lastKey();
        }

        @Override
        public SortedMap<R, Map<C, V>> subMap(R r, R r2) {
            Preconditions.checkNotNull(r);
            Preconditions.checkNotNull(r2);
            return new StandardRowSortedTable(StandardRowSortedTable.this.sortedBackingMap().subMap(r, r2), StandardRowSortedTable.this.factory).rowMap();
        }

        @Override
        public SortedMap<R, Map<C, V>> tailMap(R r) {
            Preconditions.checkNotNull(r);
            return new StandardRowSortedTable(StandardRowSortedTable.this.sortedBackingMap().tailMap(r), StandardRowSortedTable.this.factory).rowMap();
        }
    }

}


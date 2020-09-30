/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import com.google.common.collect.RegularImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.Immutable;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable(containerOf={"R", "C", "V"})
final class DenseImmutableTable<R, C, V>
extends RegularImmutableTable<R, C, V> {
    private final int[] cellColumnIndices;
    private final int[] cellRowIndices;
    private final int[] columnCounts;
    private final ImmutableMap<C, Integer> columnKeyToIndex;
    private final ImmutableMap<C, ImmutableMap<R, V>> columnMap;
    private final int[] rowCounts;
    private final ImmutableMap<R, Integer> rowKeyToIndex;
    private final ImmutableMap<R, ImmutableMap<C, V>> rowMap;
    private final V[][] values;

    DenseImmutableTable(ImmutableList<Table.Cell<R, C, V>> immutableList, ImmutableSet<R> arrn, ImmutableSet<C> arrn2) {
        int n = arrn.size();
        int n2 = arrn2.size();
        int n3 = 0;
        this.values = new Object[n][n2];
        this.rowKeyToIndex = Maps.indexMap(arrn);
        this.columnKeyToIndex = Maps.indexMap(arrn2);
        this.rowCounts = new int[this.rowKeyToIndex.size()];
        this.columnCounts = new int[this.columnKeyToIndex.size()];
        arrn2 = new int[immutableList.size()];
        arrn = new int[immutableList.size()];
        do {
            if (n3 >= immutableList.size()) {
                this.cellRowIndices = arrn2;
                this.cellColumnIndices = arrn;
                this.rowMap = new RowMap();
                this.columnMap = new ColumnMap();
                return;
            }
            Table.Cell cell = (Table.Cell)immutableList.get(n3);
            Object r = cell.getRowKey();
            Object object = cell.getColumnKey();
            n2 = this.rowKeyToIndex.get(r);
            n = this.columnKeyToIndex.get(object);
            this.checkNoDuplicate(r, object, this.values[n2][n], cell.getValue());
            this.values[n2][n] = cell.getValue();
            object = this.rowCounts;
            object[n2] = object[n2] + true;
            object = this.columnCounts;
            object[n] = object[n] + true;
            arrn2[n3] = n2;
            arrn[n3] = n;
            ++n3;
        } while (true);
    }

    @Override
    public ImmutableMap<C, Map<R, V>> columnMap() {
        return ImmutableMap.copyOf(this.columnMap);
    }

    @Override
    ImmutableTable.SerializedForm createSerializedForm() {
        return ImmutableTable.SerializedForm.create(this, this.cellRowIndices, this.cellColumnIndices);
    }

    @Override
    public V get(@NullableDecl Object object, @NullableDecl Object object2) {
        object = this.rowKeyToIndex.get(object);
        object2 = this.columnKeyToIndex.get(object2);
        if (object != null && object2 != null) {
            object = this.values[(Integer)object][(Integer)object2];
            return (V)object;
        }
        object = null;
        return (V)object;
    }

    @Override
    Table.Cell<R, C, V> getCell(int n) {
        int n2 = this.cellRowIndices[n];
        n = this.cellColumnIndices[n];
        return DenseImmutableTable.cellOf(((ImmutableSet)this.rowKeySet()).asList().get(n2), ((ImmutableSet)this.columnKeySet()).asList().get(n), this.values[n2][n]);
    }

    @Override
    V getValue(int n) {
        return this.values[this.cellRowIndices[n]][this.cellColumnIndices[n]];
    }

    @Override
    public ImmutableMap<R, Map<C, V>> rowMap() {
        return ImmutableMap.copyOf(this.rowMap);
    }

    @Override
    public int size() {
        return this.cellRowIndices.length;
    }

    private final class Column
    extends ImmutableArrayMap<R, V> {
        private final int columnIndex;

        Column(int n) {
            super(DenseImmutableTable.this.columnCounts[n]);
            this.columnIndex = n;
        }

        @Override
        V getValue(int n) {
            return (V)DenseImmutableTable.this.values[n][this.columnIndex];
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        ImmutableMap<R, Integer> keyToIndex() {
            return DenseImmutableTable.this.rowKeyToIndex;
        }
    }

    private final class ColumnMap
    extends ImmutableArrayMap<C, ImmutableMap<R, V>> {
        private ColumnMap() {
            super(DenseImmutableTable.this.columnCounts.length);
        }

        @Override
        ImmutableMap<R, V> getValue(int n) {
            return new Column(n);
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        ImmutableMap<C, Integer> keyToIndex() {
            return DenseImmutableTable.this.columnKeyToIndex;
        }
    }

    private static abstract class ImmutableArrayMap<K, V>
    extends ImmutableMap.IteratorBasedImmutableMap<K, V> {
        private final int size;

        ImmutableArrayMap(int n) {
            this.size = n;
        }

        private boolean isFull() {
            if (this.size != this.keyToIndex().size()) return false;
            return true;
        }

        @Override
        ImmutableSet<K> createKeySet() {
            if (!this.isFull()) return super.createKeySet();
            return this.keyToIndex().keySet();
        }

        @Override
        UnmodifiableIterator<Map.Entry<K, V>> entryIterator() {
            return new AbstractIterator<Map.Entry<K, V>>(){
                private int index = -1;
                private final int maxIndex = ImmutableArrayMap.this.keyToIndex().size();

                @Override
                protected Map.Entry<K, V> computeNext() {
                    int n = this.index;
                    while ((n = (this.index = n + 1)) < this.maxIndex) {
                        Object v = ImmutableArrayMap.this.getValue(n);
                        if (v != null) {
                            return Maps.immutableEntry(ImmutableArrayMap.this.getKey(this.index), v);
                        }
                        n = this.index;
                    }
                    return (Map.Entry)this.endOfData();
                }
            };
        }

        @Override
        public V get(@NullableDecl Object object) {
            object = this.keyToIndex().get(object);
            if (object == null) {
                object = null;
                return (V)object;
            }
            object = this.getValue((Integer)object);
            return (V)object;
        }

        K getKey(int n) {
            return (K)((ImmutableSet)this.keyToIndex().keySet()).asList().get(n);
        }

        @NullableDecl
        abstract V getValue(int var1);

        abstract ImmutableMap<K, Integer> keyToIndex();

        @Override
        public int size() {
            return this.size;
        }

    }

    private final class Row
    extends ImmutableArrayMap<C, V> {
        private final int rowIndex;

        Row(int n) {
            super(DenseImmutableTable.this.rowCounts[n]);
            this.rowIndex = n;
        }

        @Override
        V getValue(int n) {
            return (V)DenseImmutableTable.this.values[this.rowIndex][n];
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        ImmutableMap<C, Integer> keyToIndex() {
            return DenseImmutableTable.this.columnKeyToIndex;
        }
    }

    private final class RowMap
    extends ImmutableArrayMap<R, ImmutableMap<C, V>> {
        private RowMap() {
            super(DenseImmutableTable.this.rowCounts.length);
        }

        @Override
        ImmutableMap<C, V> getValue(int n) {
            return new Row(n);
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        ImmutableMap<R, Integer> keyToIndex() {
            return DenseImmutableTable.this.rowKeyToIndex;
        }
    }

}


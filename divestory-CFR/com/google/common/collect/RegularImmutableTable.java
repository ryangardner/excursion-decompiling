/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.DenseImmutableTable;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.IndexedImmutableSet;
import com.google.common.collect.SparseImmutableTable;
import com.google.common.collect.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class RegularImmutableTable<R, C, V>
extends ImmutableTable<R, C, V> {
    RegularImmutableTable() {
    }

    static <R, C, V> RegularImmutableTable<R, C, V> forCells(Iterable<Table.Cell<R, C, V>> iterable) {
        return RegularImmutableTable.forCellsInternal(iterable, null, null);
    }

    static <R, C, V> RegularImmutableTable<R, C, V> forCells(List<Table.Cell<R, C, V>> list, final @NullableDecl Comparator<? super R> comparator, final @NullableDecl Comparator<? super C> comparator2) {
        Preconditions.checkNotNull(list);
        if (comparator == null) {
            if (comparator2 == null) return RegularImmutableTable.forCellsInternal(list, comparator, comparator2);
        }
        Collections.sort(list, new Comparator<Table.Cell<R, C, V>>(){

            @Override
            public int compare(Table.Cell<R, C, V> cell, Table.Cell<R, C, V> cell2) {
                Comparator comparator3 = comparator;
                int n = 0;
                int n2 = comparator3 == null ? 0 : comparator3.compare(cell.getRowKey(), cell2.getRowKey());
                if (n2 != 0) {
                    return n2;
                }
                comparator3 = comparator2;
                if (comparator3 != null) return comparator3.compare(cell.getColumnKey(), cell2.getColumnKey());
                return n;
            }
        });
        return RegularImmutableTable.forCellsInternal(list, comparator, comparator2);
    }

    private static <R, C, V> RegularImmutableTable<R, C, V> forCellsInternal(Iterable<Table.Cell<R, C, V>> immutableSet, @NullableDecl Comparator<? super R> immutableSet2, @NullableDecl Comparator<? super C> comparator) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        ImmutableList<Table.Cell<R, C, V>> immutableList = ImmutableList.copyOf(immutableSet);
        for (Table.Cell cell : immutableSet) {
            linkedHashSet.add(cell.getRowKey());
            linkedHashSet2.add(cell.getColumnKey());
        }
        immutableSet = immutableSet2 == null ? ImmutableSet.copyOf(linkedHashSet) : ImmutableSet.copyOf(ImmutableList.sortedCopyOf(immutableSet2, linkedHashSet));
        if (comparator == null) {
            immutableSet2 = ImmutableSet.copyOf(linkedHashSet2);
            return RegularImmutableTable.forOrderedComponents(immutableList, immutableSet, immutableSet2);
        }
        immutableSet2 = ImmutableSet.copyOf(ImmutableList.sortedCopyOf(comparator, linkedHashSet2));
        return RegularImmutableTable.forOrderedComponents(immutableList, immutableSet, immutableSet2);
    }

    static <R, C, V> RegularImmutableTable<R, C, V> forOrderedComponents(ImmutableList<Table.Cell<R, C, V>> serializable, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        if ((long)serializable.size() <= (long)immutableSet.size() * (long)immutableSet2.size() / 2L) return new SparseImmutableTable<R, C, V>((ImmutableList<Table.Cell<R, C, V>>)serializable, immutableSet, immutableSet2);
        return new DenseImmutableTable<R, C, V>((ImmutableList<Table.Cell<R, C, V>>)serializable, immutableSet, immutableSet2);
    }

    final void checkNoDuplicate(R r, C c, V v, V v2) {
        boolean bl = v == null;
        Preconditions.checkArgument(bl, "Duplicate key: (row=%s, column=%s), values: [%s, %s].", r, c, v2, v);
    }

    @Override
    final ImmutableSet<Table.Cell<R, C, V>> createCellSet() {
        if (!this.isEmpty()) return new CellSet();
        return ImmutableSet.of();
    }

    @Override
    final ImmutableCollection<V> createValues() {
        if (!this.isEmpty()) return new Values();
        return ImmutableList.of();
    }

    abstract Table.Cell<R, C, V> getCell(int var1);

    abstract V getValue(int var1);

    private final class CellSet
    extends IndexedImmutableSet<Table.Cell<R, C, V>> {
        private CellSet() {
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof Table.Cell;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            Table.Cell cell = (Table.Cell)object;
            object = RegularImmutableTable.this.get(cell.getRowKey(), cell.getColumnKey());
            bl3 = bl;
            if (object == null) return bl3;
            bl3 = bl;
            if (!object.equals(cell.getValue())) return bl3;
            return true;
        }

        @Override
        Table.Cell<R, C, V> get(int n) {
            return RegularImmutableTable.this.getCell(n);
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        public int size() {
            return RegularImmutableTable.this.size();
        }
    }

    private final class Values
    extends ImmutableList<V> {
        private Values() {
        }

        @Override
        public V get(int n) {
            return RegularImmutableTable.this.getValue(n);
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        public int size() {
            return RegularImmutableTable.this.size();
        }
    }

}


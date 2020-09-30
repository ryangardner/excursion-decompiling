/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractTable;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.RegularImmutableTable;
import com.google.common.collect.SingletonImmutableTable;
import com.google.common.collect.SparseImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableTable<R, C, V>
extends AbstractTable<R, C, V>
implements Serializable {
    ImmutableTable() {
    }

    public static <R, C, V> Builder<R, C, V> builder() {
        return new Builder();
    }

    static <R, C, V> Table.Cell<R, C, V> cellOf(R r, C c, V v) {
        return Tables.immutableCell(Preconditions.checkNotNull(r, "rowKey"), Preconditions.checkNotNull(c, "columnKey"), Preconditions.checkNotNull(v, "value"));
    }

    public static <R, C, V> ImmutableTable<R, C, V> copyOf(Table<? extends R, ? extends C, ? extends V> table) {
        if (!(table instanceof ImmutableTable)) return ImmutableTable.copyOf(table.cellSet());
        return (ImmutableTable)table;
    }

    private static <R, C, V> ImmutableTable<R, C, V> copyOf(Iterable<? extends Table.Cell<? extends R, ? extends C, ? extends V>> object) {
        Builder<R, C, V> builder = ImmutableTable.builder();
        object = object.iterator();
        while (object.hasNext()) {
            builder.put((Table.Cell)object.next());
        }
        return builder.build();
    }

    public static <R, C, V> ImmutableTable<R, C, V> of() {
        return SparseImmutableTable.EMPTY;
    }

    public static <R, C, V> ImmutableTable<R, C, V> of(R r, C c, V v) {
        return new SingletonImmutableTable<R, C, V>(r, c, v);
    }

    @Override
    final UnmodifiableIterator<Table.Cell<R, C, V>> cellIterator() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    public ImmutableSet<Table.Cell<R, C, V>> cellSet() {
        return (ImmutableSet)super.cellSet();
    }

    @Deprecated
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableMap<R, V> column(C c) {
        Preconditions.checkNotNull(c, "columnKey");
        return MoreObjects.firstNonNull((ImmutableMap)((ImmutableMap)this.columnMap()).get(c), ImmutableMap.of());
    }

    @Override
    public ImmutableSet<C> columnKeySet() {
        return ((ImmutableMap)this.columnMap()).keySet();
    }

    @Override
    public abstract ImmutableMap<C, Map<R, V>> columnMap();

    @Override
    public boolean contains(@NullableDecl Object object, @NullableDecl Object object2) {
        if (this.get(object, object2) == null) return false;
        return true;
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        return ((ImmutableCollection)this.values()).contains(object);
    }

    @Override
    abstract ImmutableSet<Table.Cell<R, C, V>> createCellSet();

    abstract SerializedForm createSerializedForm();

    @Override
    abstract ImmutableCollection<V> createValues();

    @Deprecated
    @Override
    public final V put(R r, C c, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final V remove(Object object, Object object2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableMap<C, V> row(R r) {
        Preconditions.checkNotNull(r, "rowKey");
        return MoreObjects.firstNonNull((ImmutableMap)((ImmutableMap)this.rowMap()).get(r), ImmutableMap.<K, V>of());
    }

    @Override
    public ImmutableSet<R> rowKeySet() {
        return ((ImmutableMap)this.rowMap()).keySet();
    }

    @Override
    public abstract ImmutableMap<R, Map<C, V>> rowMap();

    @Override
    public ImmutableCollection<V> values() {
        return (ImmutableCollection)super.values();
    }

    @Override
    final Iterator<V> valuesIterator() {
        throw new AssertionError((Object)"should never be called");
    }

    final Object writeReplace() {
        return this.createSerializedForm();
    }

    @DoNotMock
    public static final class Builder<R, C, V> {
        private final List<Table.Cell<R, C, V>> cells = Lists.newArrayList();
        @MonotonicNonNullDecl
        private Comparator<? super C> columnComparator;
        @MonotonicNonNullDecl
        private Comparator<? super R> rowComparator;

        public ImmutableTable<R, C, V> build() {
            int n = this.cells.size();
            if (n == 0) return ImmutableTable.of();
            if (n == 1) return new SingletonImmutableTable<R, C, V>(Iterables.getOnlyElement(this.cells));
            return RegularImmutableTable.forCells(this.cells, this.rowComparator, this.columnComparator);
        }

        public Builder<R, C, V> orderColumnsBy(Comparator<? super C> comparator) {
            this.columnComparator = Preconditions.checkNotNull(comparator, "columnComparator");
            return this;
        }

        public Builder<R, C, V> orderRowsBy(Comparator<? super R> comparator) {
            this.rowComparator = Preconditions.checkNotNull(comparator, "rowComparator");
            return this;
        }

        public Builder<R, C, V> put(Table.Cell<? extends R, ? extends C, ? extends V> cell) {
            if (cell instanceof Tables.ImmutableCell) {
                Preconditions.checkNotNull(cell.getRowKey(), "row");
                Preconditions.checkNotNull(cell.getColumnKey(), "column");
                Preconditions.checkNotNull(cell.getValue(), "value");
                this.cells.add(cell);
                return this;
            }
            this.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            return this;
        }

        public Builder<R, C, V> put(R r, C c, V v) {
            this.cells.add(ImmutableTable.cellOf(r, c, v));
            return this;
        }

        public Builder<R, C, V> putAll(Table<? extends R, ? extends C, ? extends V> object) {
            object = object.cellSet().iterator();
            while (object.hasNext()) {
                this.put((Table.Cell)object.next());
            }
            return this;
        }
    }

    static final class SerializedForm
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final int[] cellColumnIndices;
        private final int[] cellRowIndices;
        private final Object[] cellValues;
        private final Object[] columnKeys;
        private final Object[] rowKeys;

        private SerializedForm(Object[] arrobject, Object[] arrobject2, Object[] arrobject3, int[] arrn, int[] arrn2) {
            this.rowKeys = arrobject;
            this.columnKeys = arrobject2;
            this.cellValues = arrobject3;
            this.cellRowIndices = arrn;
            this.cellColumnIndices = arrn2;
        }

        static SerializedForm create(ImmutableTable<?, ?, ?> immutableTable, int[] arrn, int[] arrn2) {
            return new SerializedForm(((ImmutableCollection)((Object)immutableTable.rowKeySet())).toArray(), ((ImmutableCollection)((Object)immutableTable.columnKeySet())).toArray(), ((ImmutableCollection)immutableTable.values()).toArray(), arrn, arrn2);
        }

        Object readResolve() {
            Object[] arrobject;
            Object object = this.cellValues;
            if (((Object[])object).length == 0) {
                return ImmutableTable.of();
            }
            int n = ((Object[])object).length;
            int n2 = 0;
            if (n == 1) {
                return ImmutableTable.of(this.rowKeys[0], this.columnKeys[0], object[0]);
            }
            object = new ImmutableList.Builder(this.cellValues.length);
            while (n2 < (arrobject = this.cellValues).length) {
                ((ImmutableList.Builder)object).add(ImmutableTable.cellOf(this.rowKeys[this.cellRowIndices[n2]], this.columnKeys[this.cellColumnIndices[n2]], arrobject[n2]));
                ++n2;
            }
            return RegularImmutableTable.forOrderedComponents(((ImmutableList.Builder)object).build(), ImmutableSet.copyOf(this.rowKeys), ImmutableSet.copyOf(this.columnKeys));
        }
    }

}


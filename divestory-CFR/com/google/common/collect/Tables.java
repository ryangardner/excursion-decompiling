/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractTable;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingTable;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.RowSortedTable;
import com.google.common.collect.StandardTable;
import com.google.common.collect.Synchronized;
import com.google.common.collect.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Tables {
    private static final Function<? extends Map<?, ?>, ? extends Map<?, ?>> UNMODIFIABLE_WRAPPER = new Function<Map<Object, Object>, Map<Object, Object>>(){

        @Override
        public Map<Object, Object> apply(Map<Object, Object> map) {
            return Collections.unmodifiableMap(map);
        }
    };

    private Tables() {
    }

    static boolean equalsImpl(Table<?, ?, ?> table, @NullableDecl Object object) {
        if (object == table) {
            return true;
        }
        if (!(object instanceof Table)) return false;
        object = (Table)object;
        return table.cellSet().equals(object.cellSet());
    }

    public static <R, C, V> Table.Cell<R, C, V> immutableCell(@NullableDecl R r, @NullableDecl C c, @NullableDecl V v) {
        return new ImmutableCell<R, C, V>(r, c, v);
    }

    public static <R, C, V> Table<R, C, V> newCustomTable(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkNotNull(supplier);
        return new StandardTable<R, C, V>(map, supplier);
    }

    public static <R, C, V> Table<R, C, V> synchronizedTable(Table<R, C, V> table) {
        return Synchronized.table(table, null);
    }

    public static <R, C, V1, V2> Table<R, C, V2> transformValues(Table<R, C, V1> table, Function<? super V1, V2> function) {
        return new TransformedTable<R, C, V1, V2>(table, function);
    }

    public static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> table) {
        if (!(table instanceof TransposeTable)) return new TransposeTable<R, C, V>(table);
        return ((TransposeTable)table).original;
    }

    public static <R, C, V> RowSortedTable<R, C, V> unmodifiableRowSortedTable(RowSortedTable<R, ? extends C, ? extends V> rowSortedTable) {
        return new UnmodifiableRowSortedMap<R, C, V>(rowSortedTable);
    }

    public static <R, C, V> Table<R, C, V> unmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
        return new UnmodifiableTable<R, C, V>(table);
    }

    private static <K, V> Function<Map<K, V>, Map<K, V>> unmodifiableWrapper() {
        return UNMODIFIABLE_WRAPPER;
    }

    static abstract class AbstractCell<R, C, V>
    implements Table.Cell<R, C, V> {
        AbstractCell() {
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof Table.Cell)) return false;
            object = (Table.Cell)object;
            if (!Objects.equal(this.getRowKey(), object.getRowKey())) return false;
            if (!Objects.equal(this.getColumnKey(), object.getColumnKey())) return false;
            if (!Objects.equal(this.getValue(), object.getValue())) return false;
            return bl;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.getRowKey(), this.getColumnKey(), this.getValue());
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append(this.getRowKey());
            stringBuilder.append(",");
            stringBuilder.append(this.getColumnKey());
            stringBuilder.append(")=");
            stringBuilder.append(this.getValue());
            return stringBuilder.toString();
        }
    }

    static final class ImmutableCell<R, C, V>
    extends AbstractCell<R, C, V>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        @NullableDecl
        private final C columnKey;
        @NullableDecl
        private final R rowKey;
        @NullableDecl
        private final V value;

        ImmutableCell(@NullableDecl R r, @NullableDecl C c, @NullableDecl V v) {
            this.rowKey = r;
            this.columnKey = c;
            this.value = v;
        }

        @Override
        public C getColumnKey() {
            return this.columnKey;
        }

        @Override
        public R getRowKey() {
            return this.rowKey;
        }

        @Override
        public V getValue() {
            return this.value;
        }
    }

    private static class TransformedTable<R, C, V1, V2>
    extends AbstractTable<R, C, V2> {
        final Table<R, C, V1> fromTable;
        final Function<? super V1, V2> function;

        TransformedTable(Table<R, C, V1> table, Function<? super V1, V2> function) {
            this.fromTable = Preconditions.checkNotNull(table);
            this.function = Preconditions.checkNotNull(function);
        }

        Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>> cellFunction() {
            return new Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>>(){

                @Override
                public Table.Cell<R, C, V2> apply(Table.Cell<R, C, V1> cell) {
                    return Tables.immutableCell(cell.getRowKey(), cell.getColumnKey(), TransformedTable.this.function.apply(cell.getValue()));
                }
            };
        }

        @Override
        Iterator<Table.Cell<R, C, V2>> cellIterator() {
            return Iterators.transform(this.fromTable.cellSet().iterator(), this.cellFunction());
        }

        @Override
        public void clear() {
            this.fromTable.clear();
        }

        @Override
        public Map<R, V2> column(C c) {
            return Maps.transformValues(this.fromTable.column(c), this.function);
        }

        @Override
        public Set<C> columnKeySet() {
            return this.fromTable.columnKeySet();
        }

        @Override
        public Map<C, Map<R, V2>> columnMap() {
            Function function = new Function<Map<R, V1>, Map<R, V2>>(){

                @Override
                public Map<R, V2> apply(Map<R, V1> map) {
                    return Maps.transformValues(map, TransformedTable.this.function);
                }
            };
            return Maps.transformValues(this.fromTable.columnMap(), function);
        }

        @Override
        public boolean contains(Object object, Object object2) {
            return this.fromTable.contains(object, object2);
        }

        @Override
        Collection<V2> createValues() {
            return Collections2.transform(this.fromTable.values(), this.function);
        }

        @Override
        public V2 get(Object object, Object object2) {
            if (this.contains(object, object2)) {
                object = this.function.apply(this.fromTable.get(object, object2));
                return (V2)object;
            }
            object = null;
            return (V2)object;
        }

        @Override
        public V2 put(R r, C c, V2 V2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Table<? extends R, ? extends C, ? extends V2> table) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V2 remove(Object object, Object object2) {
            if (this.contains(object, object2)) {
                object = this.function.apply(this.fromTable.remove(object, object2));
                return (V2)object;
            }
            object = null;
            return (V2)object;
        }

        @Override
        public Map<C, V2> row(R r) {
            return Maps.transformValues(this.fromTable.row(r), this.function);
        }

        @Override
        public Set<R> rowKeySet() {
            return this.fromTable.rowKeySet();
        }

        @Override
        public Map<R, Map<C, V2>> rowMap() {
            Function function = new Function<Map<C, V1>, Map<C, V2>>(){

                @Override
                public Map<C, V2> apply(Map<C, V1> map) {
                    return Maps.transformValues(map, TransformedTable.this.function);
                }
            };
            return Maps.transformValues(this.fromTable.rowMap(), function);
        }

        @Override
        public int size() {
            return this.fromTable.size();
        }

    }

    private static class TransposeTable<C, R, V>
    extends AbstractTable<C, R, V> {
        private static final Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>> TRANSPOSE_CELL = new Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>>(){

            @Override
            public Table.Cell<?, ?, ?> apply(Table.Cell<?, ?, ?> cell) {
                return Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
            }
        };
        final Table<R, C, V> original;

        TransposeTable(Table<R, C, V> table) {
            this.original = Preconditions.checkNotNull(table);
        }

        @Override
        Iterator<Table.Cell<C, R, V>> cellIterator() {
            return Iterators.transform(this.original.cellSet().iterator(), TRANSPOSE_CELL);
        }

        @Override
        public void clear() {
            this.original.clear();
        }

        @Override
        public Map<C, V> column(R r) {
            return this.original.row(r);
        }

        @Override
        public Set<R> columnKeySet() {
            return this.original.rowKeySet();
        }

        @Override
        public Map<R, Map<C, V>> columnMap() {
            return this.original.rowMap();
        }

        @Override
        public boolean contains(@NullableDecl Object object, @NullableDecl Object object2) {
            return this.original.contains(object2, object);
        }

        @Override
        public boolean containsColumn(@NullableDecl Object object) {
            return this.original.containsRow(object);
        }

        @Override
        public boolean containsRow(@NullableDecl Object object) {
            return this.original.containsColumn(object);
        }

        @Override
        public boolean containsValue(@NullableDecl Object object) {
            return this.original.containsValue(object);
        }

        @Override
        public V get(@NullableDecl Object object, @NullableDecl Object object2) {
            return this.original.get(object2, object);
        }

        @Override
        public V put(C c, R r, V v) {
            return this.original.put(r, c, v);
        }

        @Override
        public void putAll(Table<? extends C, ? extends R, ? extends V> table) {
            this.original.putAll(Tables.transpose(table));
        }

        @Override
        public V remove(@NullableDecl Object object, @NullableDecl Object object2) {
            return this.original.remove(object2, object);
        }

        @Override
        public Map<R, V> row(C c) {
            return this.original.column(c);
        }

        @Override
        public Set<C> rowKeySet() {
            return this.original.columnKeySet();
        }

        @Override
        public Map<C, Map<R, V>> rowMap() {
            return this.original.columnMap();
        }

        @Override
        public int size() {
            return this.original.size();
        }

        @Override
        public Collection<V> values() {
            return this.original.values();
        }

    }

    static final class UnmodifiableRowSortedMap<R, C, V>
    extends UnmodifiableTable<R, C, V>
    implements RowSortedTable<R, C, V> {
        private static final long serialVersionUID = 0L;

        public UnmodifiableRowSortedMap(RowSortedTable<R, ? extends C, ? extends V> rowSortedTable) {
            super(rowSortedTable);
        }

        @Override
        protected RowSortedTable<R, C, V> delegate() {
            return (RowSortedTable)super.delegate();
        }

        @Override
        public SortedSet<R> rowKeySet() {
            return Collections.unmodifiableSortedSet(this.delegate().rowKeySet());
        }

        @Override
        public SortedMap<R, Map<C, V>> rowMap() {
            Function function = Tables.unmodifiableWrapper();
            return Collections.unmodifiableSortedMap(Maps.transformValues(this.delegate().rowMap(), function));
        }
    }

    private static class UnmodifiableTable<R, C, V>
    extends ForwardingTable<R, C, V>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final Table<? extends R, ? extends C, ? extends V> delegate;

        UnmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
            this.delegate = Preconditions.checkNotNull(table);
        }

        @Override
        public Set<Table.Cell<R, C, V>> cellSet() {
            return Collections.unmodifiableSet(super.cellSet());
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<R, V> column(@NullableDecl C c) {
            return Collections.unmodifiableMap(super.column(c));
        }

        @Override
        public Set<C> columnKeySet() {
            return Collections.unmodifiableSet(super.columnKeySet());
        }

        @Override
        public Map<C, Map<R, V>> columnMap() {
            Function function = Tables.unmodifiableWrapper();
            return Collections.unmodifiableMap(Maps.transformValues(super.columnMap(), function));
        }

        @Override
        protected Table<R, C, V> delegate() {
            return this.delegate;
        }

        @Override
        public V put(@NullableDecl R r, @NullableDecl C c, @NullableDecl V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V remove(@NullableDecl Object object, @NullableDecl Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<C, V> row(@NullableDecl R r) {
            return Collections.unmodifiableMap(super.row(r));
        }

        @Override
        public Set<R> rowKeySet() {
            return Collections.unmodifiableSet(super.rowKeySet());
        }

        @Override
        public Map<R, Map<C, V>> rowMap() {
            Function function = Tables.unmodifiableWrapper();
            return Collections.unmodifiableMap(Maps.transformValues(super.rowMap(), function));
        }

        @Override
        public Collection<V> values() {
            return Collections.unmodifiableCollection(super.values());
        }
    }

}


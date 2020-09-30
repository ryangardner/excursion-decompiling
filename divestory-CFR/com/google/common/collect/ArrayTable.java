/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.AbstractTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ArrayTable<R, C, V>
extends AbstractTable<R, C, V>
implements Serializable {
    private static final long serialVersionUID = 0L;
    private final V[][] array;
    private final ImmutableMap<C, Integer> columnKeyToIndex;
    private final ImmutableList<C> columnList;
    @MonotonicNonNullDecl
    private transient ArrayTable<R, C, V> columnMap;
    private final ImmutableMap<R, Integer> rowKeyToIndex;
    private final ImmutableList<R> rowList;
    @MonotonicNonNullDecl
    private transient ArrayTable<R, C, V> rowMap;

    private ArrayTable(ArrayTable<R, C, V> arrayTable) {
        V[][] arrV = arrayTable.rowList;
        this.rowList = arrV;
        this.columnList = arrayTable.columnList;
        this.rowKeyToIndex = arrayTable.rowKeyToIndex;
        this.columnKeyToIndex = arrayTable.columnKeyToIndex;
        Object[][] arrobject = new Object[arrV.size()][this.columnList.size()];
        this.array = arrobject;
        int n = 0;
        while (n < this.rowList.size()) {
            arrV = arrayTable.array;
            System.arraycopy(arrV[n], 0, arrobject[n], 0, arrV[n].length);
            ++n;
        }
    }

    private ArrayTable(Table<R, C, V> table) {
        this(table.rowKeySet(), table.columnKeySet());
        this.putAll(table);
    }

    private ArrayTable(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        this.rowList = ImmutableList.copyOf(iterable);
        this.columnList = ImmutableList.copyOf(iterable2);
        boolean bl = this.rowList.isEmpty() == this.columnList.isEmpty();
        Preconditions.checkArgument(bl);
        this.rowKeyToIndex = Maps.indexMap(this.rowList);
        this.columnKeyToIndex = Maps.indexMap(this.columnList);
        this.array = new Object[this.rowList.size()][this.columnList.size()];
        this.eraseAll();
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Table<R, C, V> table) {
        if (!(table instanceof ArrayTable)) return new ArrayTable<R, C, V>(table);
        return new ArrayTable<R, C, V>((ArrayTable)table);
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        return new ArrayTable<R, C, V>(iterable, iterable2);
    }

    private Table.Cell<R, C, V> getCell(final int n) {
        return new Tables.AbstractCell<R, C, V>(){
            final int columnIndex;
            final int rowIndex;
            {
                this.rowIndex = n / ArrayTable.this.columnList.size();
                this.columnIndex = n % ArrayTable.this.columnList.size();
            }

            @Override
            public C getColumnKey() {
                return (C)ArrayTable.this.columnList.get(this.columnIndex);
            }

            @Override
            public R getRowKey() {
                return (R)ArrayTable.this.rowList.get(this.rowIndex);
            }

            @Override
            public V getValue() {
                return ArrayTable.this.at(this.rowIndex, this.columnIndex);
            }
        };
    }

    private V getValue(int n) {
        return this.at(n / this.columnList.size(), n % this.columnList.size());
    }

    public V at(int n, int n2) {
        Preconditions.checkElementIndex(n, this.rowList.size());
        Preconditions.checkElementIndex(n2, this.columnList.size());
        return this.array[n][n2];
    }

    @Override
    Iterator<Table.Cell<R, C, V>> cellIterator() {
        return new AbstractIndexedListIterator<Table.Cell<R, C, V>>(this.size()){

            @Override
            protected Table.Cell<R, C, V> get(int n) {
                return ArrayTable.this.getCell(n);
            }
        };
    }

    @Override
    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    @Deprecated
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<R, V> column(C object) {
        Preconditions.checkNotNull(object);
        object = this.columnKeyToIndex.get(object);
        if (object != null) return new Column((Integer)object);
        return ImmutableMap.of();
    }

    public ImmutableList<C> columnKeyList() {
        return this.columnList;
    }

    @Override
    public ImmutableSet<C> columnKeySet() {
        return this.columnKeyToIndex.keySet();
    }

    @Override
    public Map<C, Map<R, V>> columnMap() {
        ArrayTable<R, C, V> arrayTable = this.columnMap;
        Object object = arrayTable;
        if (arrayTable != null) return object;
        this.columnMap = object = new ColumnMap();
        return object;
    }

    @Override
    public boolean contains(@NullableDecl Object object, @NullableDecl Object object2) {
        if (!this.containsRow(object)) return false;
        if (!this.containsColumn(object2)) return false;
        return true;
    }

    @Override
    public boolean containsColumn(@NullableDecl Object object) {
        return this.columnKeyToIndex.containsKey(object);
    }

    @Override
    public boolean containsRow(@NullableDecl Object object) {
        return this.rowKeyToIndex.containsKey(object);
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        V[][] arrV = this.array;
        int n = arrV.length;
        int n2 = 0;
        while (n2 < n) {
            V[] arrV2 = arrV[n2];
            int n3 = arrV2.length;
            for (int i = 0; i < n3; ++i) {
                if (!Objects.equal(object, arrV2[i])) continue;
                return true;
            }
            ++n2;
        }
        return false;
    }

    public V erase(@NullableDecl Object object, @NullableDecl Object object2) {
        object = this.rowKeyToIndex.get(object);
        object2 = this.columnKeyToIndex.get(object2);
        if (object == null) return null;
        if (object2 != null) return this.set((Integer)object, (Integer)object2, null);
        return null;
    }

    public void eraseAll() {
        V[][] arrV = this.array;
        int n = arrV.length;
        int n2 = 0;
        while (n2 < n) {
            Arrays.fill(arrV[n2], null);
            ++n2;
        }
    }

    @Override
    public V get(@NullableDecl Object object, @NullableDecl Object object2) {
        object = this.rowKeyToIndex.get(object);
        object2 = this.columnKeyToIndex.get(object2);
        if (object != null && object2 != null) {
            object = this.at((Integer)object, (Integer)object2);
            return (V)object;
        }
        object = null;
        return (V)object;
    }

    @Override
    public boolean isEmpty() {
        if (this.rowList.isEmpty()) return true;
        if (this.columnList.isEmpty()) return true;
        return false;
    }

    @Override
    public V put(R object, C c, @NullableDecl V v) {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(c);
        Integer n = this.rowKeyToIndex.get(object);
        boolean bl = true;
        boolean bl2 = n != null;
        Preconditions.checkArgument(bl2, "Row %s not in %s", object, this.rowList);
        object = this.columnKeyToIndex.get(c);
        bl2 = object != null ? bl : false;
        Preconditions.checkArgument(bl2, "Column %s not in %s", c, this.columnList);
        return this.set(n, (Integer)object, v);
    }

    @Override
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        super.putAll(table);
    }

    @Deprecated
    @Override
    public V remove(Object object, Object object2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<C, V> row(R object) {
        Preconditions.checkNotNull(object);
        object = this.rowKeyToIndex.get(object);
        if (object != null) return new Row((Integer)object);
        return ImmutableMap.of();
    }

    public ImmutableList<R> rowKeyList() {
        return this.rowList;
    }

    @Override
    public ImmutableSet<R> rowKeySet() {
        return this.rowKeyToIndex.keySet();
    }

    @Override
    public Map<R, Map<C, V>> rowMap() {
        ArrayTable<R, C, V> arrayTable = this.rowMap;
        Object object = arrayTable;
        if (arrayTable != null) return object;
        this.rowMap = object = new RowMap();
        return object;
    }

    public V set(int n, int n2, @NullableDecl V v) {
        Preconditions.checkElementIndex(n, this.rowList.size());
        Preconditions.checkElementIndex(n2, this.columnList.size());
        V[][] arrV = this.array;
        V v2 = arrV[n][n2];
        arrV[n][n2] = v;
        return v2;
    }

    @Override
    public int size() {
        return this.rowList.size() * this.columnList.size();
    }

    public V[][] toArray(Class<V> arrobject) {
        arrobject = (Object[][])Array.newInstance(arrobject, new int[]{this.rowList.size(), this.columnList.size()});
        int n = 0;
        while (n < this.rowList.size()) {
            V[][] arrV = this.array;
            System.arraycopy(arrV[n], 0, arrobject[n], 0, arrV[n].length);
            ++n;
        }
        return arrobject;
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    @Override
    Iterator<V> valuesIterator() {
        return new AbstractIndexedListIterator<V>(this.size()){

            @Override
            protected V get(int n) {
                return (V)ArrayTable.this.getValue(n);
            }
        };
    }

    private static abstract class ArrayMap<K, V>
    extends Maps.IteratorBasedAbstractMap<K, V> {
        private final ImmutableMap<K, Integer> keyIndex;

        private ArrayMap(ImmutableMap<K, Integer> immutableMap) {
            this.keyIndex = immutableMap;
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(@NullableDecl Object object) {
            return this.keyIndex.containsKey(object);
        }

        @Override
        Iterator<Map.Entry<K, V>> entryIterator() {
            return new AbstractIndexedListIterator<Map.Entry<K, V>>(this.size()){

                @Override
                protected Map.Entry<K, V> get(int n) {
                    return ArrayMap.this.getEntry(n);
                }
            };
        }

        @Override
        public V get(@NullableDecl Object object) {
            if ((object = this.keyIndex.get(object)) != null) return this.getValue((Integer)object);
            return null;
        }

        Map.Entry<K, V> getEntry(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return new AbstractMapEntry<K, V>(){

                @Override
                public K getKey() {
                    return ArrayMap.this.getKey(n);
                }

                @Override
                public V getValue() {
                    return ArrayMap.this.getValue(n);
                }

                @Override
                public V setValue(V v) {
                    return ArrayMap.this.setValue(n, v);
                }
            };
        }

        K getKey(int n) {
            return (K)((ImmutableSet)this.keyIndex.keySet()).asList().get(n);
        }

        abstract String getKeyRole();

        @NullableDecl
        abstract V getValue(int var1);

        @Override
        public boolean isEmpty() {
            return this.keyIndex.isEmpty();
        }

        @Override
        public Set<K> keySet() {
            return this.keyIndex.keySet();
        }

        @Override
        public V put(K k, V object) {
            Integer n = this.keyIndex.get(k);
            if (n != null) {
                return this.setValue(n, object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(this.getKeyRole());
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(k);
            ((StringBuilder)object).append(" not in ");
            ((StringBuilder)object).append(this.keyIndex.keySet());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @Override
        public V remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @NullableDecl
        abstract V setValue(int var1, V var2);

        @Override
        public int size() {
            return this.keyIndex.size();
        }

    }

    private class Column
    extends ArrayMap<R, V> {
        final int columnIndex;

        Column(int n) {
            super(ArrayTable.this.rowKeyToIndex);
            this.columnIndex = n;
        }

        @Override
        String getKeyRole() {
            return "Row";
        }

        @Override
        V getValue(int n) {
            return ArrayTable.this.at(n, this.columnIndex);
        }

        @Override
        V setValue(int n, V v) {
            return ArrayTable.this.set(n, this.columnIndex, v);
        }
    }

    private class ColumnMap
    extends ArrayMap<C, Map<R, V>> {
        private ColumnMap() {
            super(ArrayTable.this.columnKeyToIndex);
        }

        @Override
        String getKeyRole() {
            return "Column";
        }

        @Override
        Map<R, V> getValue(int n) {
            return new Column(n);
        }

        @Override
        public Map<R, V> put(C c, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        Map<R, V> setValue(int n, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    private class Row
    extends ArrayMap<C, V> {
        final int rowIndex;

        Row(int n) {
            super(ArrayTable.this.columnKeyToIndex);
            this.rowIndex = n;
        }

        @Override
        String getKeyRole() {
            return "Column";
        }

        @Override
        V getValue(int n) {
            return ArrayTable.this.at(this.rowIndex, n);
        }

        @Override
        V setValue(int n, V v) {
            return ArrayTable.this.set(this.rowIndex, n, v);
        }
    }

    private class RowMap
    extends ArrayMap<R, Map<C, V>> {
        private RowMap() {
            super(ArrayTable.this.rowKeyToIndex);
        }

        @Override
        String getKeyRole() {
            return "Row";
        }

        @Override
        Map<C, V> getValue(int n) {
            return new Row(n);
        }

        @Override
        public Map<C, V> put(R r, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        Map<C, V> setValue(int n, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }
    }

}


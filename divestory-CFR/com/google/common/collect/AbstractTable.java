/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.collect.TransformedIterator;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractTable<R, C, V>
implements Table<R, C, V> {
    @LazyInit
    @MonotonicNonNullDecl
    private transient Set<Table.Cell<R, C, V>> cellSet;
    @LazyInit
    @MonotonicNonNullDecl
    private transient Collection<V> values;

    AbstractTable() {
    }

    abstract Iterator<Table.Cell<R, C, V>> cellIterator();

    @Override
    public Set<Table.Cell<R, C, V>> cellSet() {
        Set<Table.Cell<R, C, V>> set;
        Set<Table.Cell<R, C, V>> set2 = set = this.cellSet;
        if (set != null) return set2;
        this.cellSet = set2 = this.createCellSet();
        return set2;
    }

    @Override
    public void clear() {
        Iterators.clear(this.cellSet().iterator());
    }

    @Override
    public Set<C> columnKeySet() {
        return this.columnMap().keySet();
    }

    @Override
    public boolean contains(@NullableDecl Object map, @NullableDecl Object object) {
        map = Maps.safeGet(this.rowMap(), map);
        if (map == null) return false;
        if (!Maps.safeContainsKey(map, object)) return false;
        return true;
    }

    @Override
    public boolean containsColumn(@NullableDecl Object object) {
        return Maps.safeContainsKey(this.columnMap(), object);
    }

    @Override
    public boolean containsRow(@NullableDecl Object object) {
        return Maps.safeContainsKey(this.rowMap(), object);
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        Iterator<Map<C, V>> iterator2 = this.rowMap().values().iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while (!iterator2.next().containsValue(object));
        return true;
    }

    Set<Table.Cell<R, C, V>> createCellSet() {
        return new CellSet();
    }

    Collection<V> createValues() {
        return new Values();
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        return Tables.equalsImpl(this, object);
    }

    @Override
    public V get(@NullableDecl Object map, @NullableDecl Object object) {
        map = Maps.safeGet(this.rowMap(), map);
        if (map == null) {
            map = null;
            return (V)map;
        }
        map = Maps.safeGet(map, object);
        return (V)map;
    }

    @Override
    public int hashCode() {
        return this.cellSet().hashCode();
    }

    @Override
    public boolean isEmpty() {
        if (this.size() != 0) return false;
        return true;
    }

    @Override
    public V put(R r, C c, V v) {
        return this.row(r).put(c, v);
    }

    @Override
    public void putAll(Table<? extends R, ? extends C, ? extends V> object) {
        object = object.cellSet().iterator();
        while (object.hasNext()) {
            Table.Cell cell = (Table.Cell)object.next();
            this.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
    }

    @Override
    public V remove(@NullableDecl Object map, @NullableDecl Object object) {
        map = Maps.safeGet(this.rowMap(), map);
        if (map == null) {
            map = null;
            return (V)map;
        }
        map = Maps.safeRemove(map, object);
        return (V)map;
    }

    @Override
    public Set<R> rowKeySet() {
        return this.rowMap().keySet();
    }

    public String toString() {
        return this.rowMap().toString();
    }

    @Override
    public Collection<V> values() {
        Collection<V> collection;
        Collection<V> collection2 = collection = this.values;
        if (collection != null) return collection2;
        this.values = collection2 = this.createValues();
        return collection2;
    }

    Iterator<V> valuesIterator() {
        return new TransformedIterator<Table.Cell<R, C, V>, V>(this.cellSet().iterator()){

            @Override
            V transform(Table.Cell<R, C, V> cell) {
                return cell.getValue();
            }
        };
    }

    class CellSet
    extends AbstractSet<Table.Cell<R, C, V>> {
        CellSet() {
        }

        @Override
        public void clear() {
            AbstractTable.this.clear();
        }

        @Override
        public boolean contains(Object map) {
            boolean bl;
            boolean bl2 = map instanceof Table.Cell;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            Table.Cell cell = (Table.Cell)((Object)map);
            map = Maps.safeGet(AbstractTable.this.rowMap(), cell.getRowKey());
            bl3 = bl;
            if (map == null) return bl3;
            bl3 = bl;
            if (!Collections2.safeContains(map.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()))) return bl3;
            return true;
        }

        @Override
        public Iterator<Table.Cell<R, C, V>> iterator() {
            return AbstractTable.this.cellIterator();
        }

        @Override
        public boolean remove(@NullableDecl Object map) {
            boolean bl;
            boolean bl2 = map instanceof Table.Cell;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            Table.Cell cell = (Table.Cell)((Object)map);
            map = Maps.safeGet(AbstractTable.this.rowMap(), cell.getRowKey());
            bl3 = bl;
            if (map == null) return bl3;
            bl3 = bl;
            if (!Collections2.safeRemove(map.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()))) return bl3;
            return true;
        }

        @Override
        public int size() {
            return AbstractTable.this.size();
        }
    }

    class Values
    extends AbstractCollection<V> {
        Values() {
        }

        @Override
        public void clear() {
            AbstractTable.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return AbstractTable.this.containsValue(object);
        }

        @Override
        public Iterator<V> iterator() {
            return AbstractTable.this.valuesIterator();
        }

        @Override
        public int size() {
            return AbstractTable.this.size();
        }
    }

}


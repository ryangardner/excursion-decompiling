/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.StandardTable.TableSet
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.AbstractTable;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.GwtTransient;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class StandardTable<R, C, V>
extends AbstractTable<R, C, V>
implements Serializable {
    private static final long serialVersionUID = 0L;
    @GwtTransient
    final Map<R, Map<C, V>> backingMap;
    @MonotonicNonNullDecl
    private transient Set<C> columnKeySet;
    @MonotonicNonNullDecl
    private transient StandardTable<R, C, V> columnMap;
    @GwtTransient
    final Supplier<? extends Map<C, V>> factory;
    @MonotonicNonNullDecl
    private transient Map<R, Map<C, V>> rowMap;

    StandardTable(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        this.backingMap = map;
        this.factory = supplier;
    }

    private boolean containsMapping(Object object, Object object2, Object object3) {
        if (object3 == null) return false;
        if (!object3.equals(this.get(object, object2))) return false;
        return true;
    }

    private Map<C, V> getOrCreate(R r) {
        Map<C, V> map;
        Map<C, V> map2 = map = this.backingMap.get(r);
        if (map != null) return map2;
        map2 = this.factory.get();
        this.backingMap.put(r, map2);
        return map2;
    }

    private Map<R, V> removeColumn(Object object) {
        LinkedHashMap<R, V> linkedHashMap = new LinkedHashMap<R, V>();
        Iterator<Map.Entry<R, Map<C, V>>> iterator2 = this.backingMap.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<R, Map<C, V>> entry = iterator2.next();
            V v = entry.getValue().remove(object);
            if (v == null) continue;
            linkedHashMap.put(entry.getKey(), v);
            if (!entry.getValue().isEmpty()) continue;
            iterator2.remove();
        }
        return linkedHashMap;
    }

    private boolean removeMapping(Object object, Object object2, Object object3) {
        if (!this.containsMapping(object, object2, object3)) return false;
        this.remove(object, object2);
        return true;
    }

    @Override
    Iterator<Table.Cell<R, C, V>> cellIterator() {
        return new CellIterator();
    }

    @Override
    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    @Override
    public void clear() {
        this.backingMap.clear();
    }

    @Override
    public Map<R, V> column(C c) {
        return new Column(c);
    }

    @Override
    public Set<C> columnKeySet() {
        Set<C> set = this.columnKeySet;
        Object object = set;
        if (set != null) return object;
        this.columnKeySet = object = new ColumnKeySet();
        return object;
    }

    @Override
    public Map<C, Map<R, V>> columnMap() {
        StandardTable<R, C, V> standardTable = this.columnMap;
        Object object = standardTable;
        if (standardTable != null) return object;
        this.columnMap = object = new ColumnMap();
        return object;
    }

    @Override
    public boolean contains(@NullableDecl Object object, @NullableDecl Object object2) {
        if (object == null) return false;
        if (object2 == null) return false;
        if (!super.contains(object, object2)) return false;
        return true;
    }

    @Override
    public boolean containsColumn(@NullableDecl Object object) {
        if (object == null) {
            return false;
        }
        Iterator<Map<C, V>> iterator2 = this.backingMap.values().iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while (!Maps.safeContainsKey(iterator2.next(), object));
        return true;
    }

    @Override
    public boolean containsRow(@NullableDecl Object object) {
        if (object == null) return false;
        if (!Maps.safeContainsKey(this.backingMap, object)) return false;
        return true;
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        if (object == null) return false;
        if (!super.containsValue(object)) return false;
        return true;
    }

    Iterator<C> createColumnKeyIterator() {
        return new ColumnKeyIterator();
    }

    Map<R, Map<C, V>> createRowMap() {
        return new RowMap();
    }

    @Override
    public V get(@NullableDecl Object object, @NullableDecl Object object2) {
        if (object != null && object2 != null) {
            object = super.get(object, object2);
            return (V)object;
        }
        object = null;
        return (V)object;
    }

    @Override
    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }

    @Override
    public V put(R r, C c, V v) {
        Preconditions.checkNotNull(r);
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(v);
        return this.getOrCreate(r).put(c, v);
    }

    @Override
    public V remove(@NullableDecl Object object, @NullableDecl Object object2) {
        if (object == null) return null;
        if (object2 == null) {
            return null;
        }
        Map<C, V> map = Maps.safeGet(this.backingMap, object);
        if (map == null) {
            return null;
        }
        object2 = map.remove(object2);
        if (!map.isEmpty()) return (V)object2;
        this.backingMap.remove(object);
        return (V)object2;
    }

    @Override
    public Map<C, V> row(R r) {
        return new Row(r);
    }

    @Override
    public Set<R> rowKeySet() {
        return this.rowMap().keySet();
    }

    @Override
    public Map<R, Map<C, V>> rowMap() {
        Map<R, Map<C, Map<C, Map<C, Map<C, V>>>>> map;
        Map<R, Map<C, Map<C, Map<C, Map<C, V>>>>> map2 = map = this.rowMap;
        if (map != null) return map2;
        map2 = this.createRowMap();
        this.rowMap = map2;
        return map2;
    }

    @Override
    public int size() {
        Iterator<Map<C, V>> iterator2 = this.backingMap.values().iterator();
        int n = 0;
        while (iterator2.hasNext()) {
            n += iterator2.next().size();
        }
        return n;
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    private class CellIterator
    implements Iterator<Table.Cell<R, C, V>> {
        Iterator<Map.Entry<C, V>> columnIterator;
        @NullableDecl
        Map.Entry<R, Map<C, V>> rowEntry;
        final Iterator<Map.Entry<R, Map<C, V>>> rowIterator;

        private CellIterator() {
            this.rowIterator = StandardTable.this.backingMap.entrySet().iterator();
            this.columnIterator = Iterators.emptyModifiableIterator();
        }

        @Override
        public boolean hasNext() {
            if (this.rowIterator.hasNext()) return true;
            if (this.columnIterator.hasNext()) return true;
            return false;
        }

        @Override
        public Table.Cell<R, C, V> next() {
            Map.Entry<Object, Map<Object, V>> entry;
            if (!this.columnIterator.hasNext()) {
                entry = this.rowIterator.next();
                this.rowEntry = entry;
                this.columnIterator = entry.getValue().entrySet().iterator();
            }
            entry = this.columnIterator.next();
            return Tables.immutableCell(this.rowEntry.getKey(), entry.getKey(), entry.getValue());
        }

        @Override
        public void remove() {
            this.columnIterator.remove();
            if (!this.rowEntry.getValue().isEmpty()) return;
            this.rowIterator.remove();
            this.rowEntry = null;
        }
    }

    private class Column
    extends Maps.ViewCachingAbstractMap<R, V> {
        final C columnKey;

        Column(C c) {
            this.columnKey = Preconditions.checkNotNull(c);
        }

        @Override
        public boolean containsKey(Object object) {
            return StandardTable.this.contains(object, this.columnKey);
        }

        @Override
        Set<Map.Entry<R, V>> createEntrySet() {
            return new EntrySet();
        }

        @Override
        Set<R> createKeySet() {
            return new KeySet();
        }

        @Override
        Collection<V> createValues() {
            return new Values();
        }

        @Override
        public V get(Object object) {
            return StandardTable.this.get(object, this.columnKey);
        }

        @Override
        public V put(R r, V v) {
            return StandardTable.this.put(r, this.columnKey, v);
        }

        @Override
        public V remove(Object object) {
            return StandardTable.this.remove(object, this.columnKey);
        }

        boolean removeFromColumnIf(Predicate<? super Map.Entry<R, V>> predicate) {
            Iterator iterator2 = StandardTable.this.backingMap.entrySet().iterator();
            boolean bl = false;
            while (iterator2.hasNext()) {
                boolean bl2;
                Map.Entry entry = iterator2.next();
                Map map = entry.getValue();
                Object v = map.get(this.columnKey);
                if (v == null || !predicate.apply(Maps.immutableEntry(entry.getKey(), v))) continue;
                map.remove(this.columnKey);
                bl = bl2 = true;
                if (!map.isEmpty()) continue;
                iterator2.remove();
                bl = bl2;
            }
            return bl;
        }

        private class EntrySet
        extends Sets.ImprovedAbstractSet<Map.Entry<R, V>> {
            private EntrySet() {
            }

            @Override
            public void clear() {
                Column.this.removeFromColumnIf(Predicates.alwaysTrue());
            }

            @Override
            public boolean contains(Object object) {
                if (!(object instanceof Map.Entry)) return false;
                object = (Map.Entry)object;
                return StandardTable.this.containsMapping(object.getKey(), Column.this.columnKey, object.getValue());
            }

            @Override
            public boolean isEmpty() {
                return StandardTable.this.containsColumn(Column.this.columnKey) ^ true;
            }

            @Override
            public Iterator<Map.Entry<R, V>> iterator() {
                return new EntrySetIterator();
            }

            @Override
            public boolean remove(Object object) {
                if (!(object instanceof Map.Entry)) return false;
                object = (Map.Entry)object;
                return StandardTable.this.removeMapping(object.getKey(), Column.this.columnKey, object.getValue());
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Predicates.not(Predicates.in(collection)));
            }

            @Override
            public int size() {
                Iterator iterator2 = StandardTable.this.backingMap.values().iterator();
                int n = 0;
                while (iterator2.hasNext()) {
                    if (!iterator2.next().containsKey(Column.this.columnKey)) continue;
                    ++n;
                }
                return n;
            }
        }

        private class EntrySetIterator
        extends AbstractIterator<Map.Entry<R, V>> {
            final Iterator<Map.Entry<R, Map<C, V>>> iterator;

            private EntrySetIterator() {
                this.iterator = StandardTable.this.backingMap.entrySet().iterator();
            }

            @Override
            protected Map.Entry<R, V> computeNext() {
                Map.Entry<R, Map<C, V>> entry;
                do {
                    if (!this.iterator.hasNext()) return (Map.Entry)this.endOfData();
                } while (!(entry = this.iterator.next()).getValue().containsKey(Column.this.columnKey));
                return new 1EntryImpl(this, entry);
            }

            class 1EntryImpl
            extends AbstractMapEntry<R, V> {
                final /* synthetic */ Map.Entry val$entry;

                1EntryImpl() {
                    this.val$entry = var2_2;
                }

                @Override
                public R getKey() {
                    return (R)this.val$entry.getKey();
                }

                @Override
                public V getValue() {
                    return ((Map)this.val$entry.getValue()).get(Column.this.columnKey);
                }

                @Override
                public V setValue(V v) {
                    return ((Map)this.val$entry.getValue()).put(Column.this.columnKey, Preconditions.checkNotNull(v));
                }
            }

        }

        private class KeySet
        extends Maps.KeySet<R, V> {
            KeySet() {
                super(Column.this);
            }

            @Override
            public boolean contains(Object object) {
                return StandardTable.this.contains(object, Column.this.columnKey);
            }

            @Override
            public boolean remove(Object object) {
                if (StandardTable.this.remove(object, Column.this.columnKey) == null) return false;
                return true;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }

        private class Values
        extends Maps.Values<R, V> {
            Values() {
                super(Column.this);
            }

            @Override
            public boolean remove(Object object) {
                if (object == null) return false;
                if (!Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.equalTo(object)))) return false;
                return true;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.in(collection)));
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }

    }

    private class ColumnKeyIterator
    extends AbstractIterator<C> {
        Iterator<Map.Entry<C, V>> entryIterator;
        final Iterator<Map<C, V>> mapIterator;
        final Map<C, V> seen;

        private ColumnKeyIterator() {
            this.seen = StandardTable.this.factory.get();
            this.mapIterator = StandardTable.this.backingMap.values().iterator();
            this.entryIterator = Iterators.emptyIterator();
        }

        @Override
        protected C computeNext() {
            do {
                if (this.entryIterator.hasNext()) {
                    Map.Entry<C, V> entry = this.entryIterator.next();
                    if (this.seen.containsKey(entry.getKey())) continue;
                    this.seen.put(entry.getKey(), entry.getValue());
                    return entry.getKey();
                }
                if (!this.mapIterator.hasNext()) return (C)this.endOfData();
                this.entryIterator = this.mapIterator.next().entrySet().iterator();
            } while (true);
        }
    }

    private class ColumnKeySet
    extends StandardTable<R, C, V> {
        private ColumnKeySet() {
        }

        public boolean contains(Object object) {
            return StandardTable.this.containsColumn(object);
        }

        public Iterator<C> iterator() {
            return StandardTable.this.createColumnKeyIterator();
        }

        public boolean remove(Object object) {
            boolean bl = false;
            if (object == null) {
                return false;
            }
            Iterator iterator2 = StandardTable.this.backingMap.values().iterator();
            while (iterator2.hasNext()) {
                boolean bl2;
                Map map = iterator2.next();
                if (!map.keySet().remove(object)) continue;
                bl = bl2 = true;
                if (!map.isEmpty()) continue;
                iterator2.remove();
                bl = bl2;
            }
            return bl;
        }

        public boolean removeAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Iterator iterator2 = StandardTable.this.backingMap.values().iterator();
            boolean bl = false;
            while (iterator2.hasNext()) {
                boolean bl2;
                Map map = iterator2.next();
                if (!Iterators.removeAll(map.keySet().iterator(), collection)) continue;
                bl = bl2 = true;
                if (!map.isEmpty()) continue;
                iterator2.remove();
                bl = bl2;
            }
            return bl;
        }

        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Iterator iterator2 = StandardTable.this.backingMap.values().iterator();
            boolean bl = false;
            while (iterator2.hasNext()) {
                boolean bl2;
                Map map = iterator2.next();
                if (!map.keySet().retainAll(collection)) continue;
                bl = bl2 = true;
                if (!map.isEmpty()) continue;
                iterator2.remove();
                bl = bl2;
            }
            return bl;
        }

        @Override
        public int size() {
            return Iterators.size(this.iterator());
        }
    }

    private class ColumnMap
    extends Maps.ViewCachingAbstractMap<C, Map<R, V>> {
        private ColumnMap() {
        }

        @Override
        public boolean containsKey(Object object) {
            return StandardTable.this.containsColumn(object);
        }

        @Override
        public Set<Map.Entry<C, Map<R, V>>> createEntrySet() {
            return new ColumnMapEntrySet();
        }

        @Override
        Collection<Map<R, V>> createValues() {
            return new ColumnMapValues();
        }

        @Override
        public Map<R, V> get(Object map) {
            if (!StandardTable.this.containsColumn(map)) return null;
            return StandardTable.this.column(map);
        }

        @Override
        public Set<C> keySet() {
            return StandardTable.this.columnKeySet();
        }

        @Override
        public Map<R, V> remove(Object object) {
            if (!StandardTable.this.containsColumn(object)) return null;
            return StandardTable.this.removeColumn(object);
        }

        class ColumnMapEntrySet
        extends com.google.common.collect.StandardTable.TableSet<Map.Entry<C, Map<R, V>>> {
            ColumnMapEntrySet() {
            }

            public boolean contains(Object object) {
                if (!(object instanceof Map.Entry)) return false;
                if (!StandardTable.this.containsColumn((object = (Map.Entry)object).getKey())) return false;
                Object k = object.getKey();
                return ColumnMap.this.get(k).equals(object.getValue());
            }

            public Iterator<Map.Entry<C, Map<R, V>>> iterator() {
                return Maps.asMapEntryIterator(StandardTable.this.columnKeySet(), new Function<C, Map<R, V>>(){

                    @Override
                    public Map<R, V> apply(C c) {
                        return StandardTable.this.column(c);
                    }
                });
            }

            public boolean remove(Object object) {
                if (!this.contains(object)) return false;
                object = (Map.Entry)object;
                StandardTable.this.removeColumn(object.getKey());
                return true;
            }

            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                return Sets.removeAllImpl(this, collection.iterator());
            }

            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator iterator2 = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean bl = false;
                while (iterator2.hasNext()) {
                    Object c = iterator2.next();
                    if (collection.contains(Maps.immutableEntry(c, StandardTable.this.column(c)))) continue;
                    StandardTable.this.removeColumn(c);
                    bl = true;
                }
                return bl;
            }

            public int size() {
                return StandardTable.this.columnKeySet().size();
            }

        }

        private class ColumnMapValues
        extends Maps.Values<C, Map<R, V>> {
            ColumnMapValues() {
                super(ColumnMap.this);
            }

            @Override
            public boolean remove(Object object) {
                Map.Entry entry;
                Iterator iterator2 = ColumnMap.this.entrySet().iterator();
                do {
                    if (!iterator2.hasNext()) return false;
                } while (!((Map)(entry = iterator2.next()).getValue()).equals(object));
                StandardTable.this.removeColumn(entry.getKey());
                return true;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator iterator2 = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean bl = false;
                while (iterator2.hasNext()) {
                    Object c = iterator2.next();
                    if (!collection.contains(StandardTable.this.column(c))) continue;
                    StandardTable.this.removeColumn(c);
                    bl = true;
                }
                return bl;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator iterator2 = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean bl = false;
                while (iterator2.hasNext()) {
                    Object c = iterator2.next();
                    if (collection.contains(StandardTable.this.column(c))) continue;
                    StandardTable.this.removeColumn(c);
                    bl = true;
                }
                return bl;
            }
        }

    }

    class Row
    extends Maps.IteratorBasedAbstractMap<C, V> {
        @NullableDecl
        Map<C, V> backingRowMap;
        final R rowKey;

        Row(R r) {
            this.rowKey = Preconditions.checkNotNull(r);
        }

        Map<C, V> backingRowMap() {
            Map<C, V> map = this.backingRowMap;
            if (map != null) {
                if (!map.isEmpty()) return this.backingRowMap;
                if (!StandardTable.this.backingMap.containsKey(this.rowKey)) return this.backingRowMap;
            }
            map = this.computeBackingRowMap();
            this.backingRowMap = map;
            return map;
        }

        @Override
        public void clear() {
            Map<C, V> map = this.backingRowMap();
            if (map != null) {
                map.clear();
            }
            this.maintainEmptyInvariant();
        }

        Map<C, V> computeBackingRowMap() {
            return StandardTable.this.backingMap.get(this.rowKey);
        }

        @Override
        public boolean containsKey(Object object) {
            Map<C, V> map = this.backingRowMap();
            if (object == null) return false;
            if (map == null) return false;
            if (!Maps.safeContainsKey(map, object)) return false;
            return true;
        }

        @Override
        Iterator<Map.Entry<C, V>> entryIterator() {
            Map<C, V> map = this.backingRowMap();
            if (map != null) return new Iterator<Map.Entry<C, V>>(map.entrySet().iterator()){
                final /* synthetic */ Iterator val$iterator;
                {
                    this.val$iterator = iterator2;
                }

                @Override
                public boolean hasNext() {
                    return this.val$iterator.hasNext();
                }

                @Override
                public Map.Entry<C, V> next() {
                    return Row.this.wrapEntry((Map.Entry)this.val$iterator.next());
                }

                @Override
                public void remove() {
                    this.val$iterator.remove();
                    Row.this.maintainEmptyInvariant();
                }
            };
            return Iterators.emptyModifiableIterator();
        }

        @Override
        public V get(Object object) {
            Map<C, V> map = this.backingRowMap();
            if (object != null && map != null) {
                object = Maps.safeGet(map, object);
                return (V)object;
            }
            object = null;
            return (V)object;
        }

        void maintainEmptyInvariant() {
            if (this.backingRowMap() == null) return;
            if (!this.backingRowMap.isEmpty()) return;
            StandardTable.this.backingMap.remove(this.rowKey);
            this.backingRowMap = null;
        }

        @Override
        public V put(C c, V v) {
            Preconditions.checkNotNull(c);
            Preconditions.checkNotNull(v);
            Map<C, V> map = this.backingRowMap;
            if (map == null) return StandardTable.this.put(this.rowKey, c, v);
            if (map.isEmpty()) return StandardTable.this.put(this.rowKey, c, v);
            return this.backingRowMap.put(c, v);
        }

        @Override
        public V remove(Object object) {
            Map<C, V> map = this.backingRowMap();
            if (map == null) {
                return null;
            }
            object = Maps.safeRemove(map, object);
            this.maintainEmptyInvariant();
            return (V)object;
        }

        @Override
        public int size() {
            Map<C, V> map = this.backingRowMap();
            if (map != null) return map.size();
            return 0;
        }

        Map.Entry<C, V> wrapEntry(final Map.Entry<C, V> entry) {
            return new ForwardingMapEntry<C, V>(){

                @Override
                protected Map.Entry<C, V> delegate() {
                    return entry;
                }

                @Override
                public boolean equals(Object object) {
                    return this.standardEquals(object);
                }

                @Override
                public V setValue(V v) {
                    return super.setValue(Preconditions.checkNotNull(v));
                }
            };
        }

    }

    class RowMap
    extends Maps.ViewCachingAbstractMap<R, Map<C, V>> {
        RowMap() {
        }

        @Override
        public boolean containsKey(Object object) {
            return StandardTable.this.containsRow(object);
        }

        @Override
        protected Set<Map.Entry<R, Map<C, V>>> createEntrySet() {
            return new EntrySet();
        }

        @Override
        public Map<C, V> get(Object map) {
            if (!StandardTable.this.containsRow(map)) return null;
            return StandardTable.this.row(map);
        }

        @Override
        public Map<C, V> remove(Object map) {
            if (map != null) return StandardTable.this.backingMap.remove(map);
            return null;
        }

        class EntrySet
        extends com.google.common.collect.StandardTable.TableSet<Map.Entry<R, Map<C, V>>> {
            EntrySet() {
            }

            public boolean contains(Object object) {
                boolean bl;
                boolean bl2 = object instanceof Map.Entry;
                boolean bl3 = bl = false;
                if (!bl2) return bl3;
                object = (Map.Entry)object;
                bl3 = bl;
                if (object.getKey() == null) return bl3;
                bl3 = bl;
                if (!(object.getValue() instanceof Map)) return bl3;
                bl3 = bl;
                if (!Collections2.safeContains(StandardTable.this.backingMap.entrySet(), object)) return bl3;
                return true;
            }

            public Iterator<Map.Entry<R, Map<C, V>>> iterator() {
                return Maps.asMapEntryIterator(StandardTable.this.backingMap.keySet(), new Function<R, Map<C, V>>(){

                    @Override
                    public Map<C, V> apply(R r) {
                        return StandardTable.this.row(r);
                    }
                });
            }

            public boolean remove(Object object) {
                boolean bl;
                boolean bl2 = object instanceof Map.Entry;
                boolean bl3 = bl = false;
                if (!bl2) return bl3;
                object = (Map.Entry)object;
                bl3 = bl;
                if (object.getKey() == null) return bl3;
                bl3 = bl;
                if (!(object.getValue() instanceof Map)) return bl3;
                bl3 = bl;
                if (!StandardTable.this.backingMap.entrySet().remove(object)) return bl3;
                return true;
            }

            public int size() {
                return StandardTable.this.backingMap.size();
            }

        }

    }

    private abstract class TableSet<T>
    extends Sets.ImprovedAbstractSet<T> {
        private TableSet() {
        }

        @Override
        public void clear() {
            StandardTable.this.backingMap.clear();
        }

        @Override
        public boolean isEmpty() {
            return StandardTable.this.backingMap.isEmpty();
        }
    }

}


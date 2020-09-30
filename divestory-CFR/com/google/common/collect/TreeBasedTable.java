/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.StandardRowSortedTable;
import com.google.common.collect.StandardTable;
import com.google.common.collect.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class TreeBasedTable<R, C, V>
extends StandardRowSortedTable<R, C, V> {
    private static final long serialVersionUID = 0L;
    private final Comparator<? super C> columnComparator;

    TreeBasedTable(Comparator<? super R> comparator, Comparator<? super C> comparator2) {
        super(new TreeMap(comparator), new Factory(comparator2));
        this.columnComparator = comparator2;
    }

    public static <R extends Comparable, C extends Comparable, V> TreeBasedTable<R, C, V> create() {
        return new TreeBasedTable(Ordering.natural(), Ordering.natural());
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(TreeBasedTable<R, C, ? extends V> treeBasedTable) {
        TreeBasedTable<R, C, V> treeBasedTable2 = new TreeBasedTable<R, C, V>(treeBasedTable.rowComparator(), treeBasedTable.columnComparator());
        treeBasedTable2.putAll(treeBasedTable);
        return treeBasedTable2;
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(Comparator<? super R> comparator, Comparator<? super C> comparator2) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(comparator2);
        return new TreeBasedTable<R, C, V>(comparator, comparator2);
    }

    @Deprecated
    public Comparator<? super C> columnComparator() {
        return this.columnComparator;
    }

    @Override
    Iterator<C> createColumnKeyIterator() {
        Comparator<C> comparator = this.columnComparator();
        return new AbstractIterator<C>(Iterators.mergeSorted(Iterables.transform(this.backingMap.values(), new Function<Map<C, V>, Iterator<C>>(){

            @Override
            public Iterator<C> apply(Map<C, V> map) {
                return map.keySet().iterator();
            }
        }), comparator), comparator){
            @NullableDecl
            C lastValue;
            final /* synthetic */ Comparator val$comparator;
            final /* synthetic */ Iterator val$merged;
            {
                this.val$merged = iterator2;
                this.val$comparator = comparator;
            }

            @Override
            protected C computeNext() {
                Object e;
                boolean bl;
                C c;
                do {
                    if (!this.val$merged.hasNext()) {
                        this.lastValue = null;
                        return (C)this.endOfData();
                    }
                    e = this.val$merged.next();
                } while (bl = (c = this.lastValue) != null && this.val$comparator.compare(e, c) == 0);
                this.lastValue = e;
                return (C)e;
            }
        };
    }

    @Override
    public SortedMap<C, V> row(R r) {
        return new TreeRow(r);
    }

    @Deprecated
    public Comparator<? super R> rowComparator() {
        return this.rowKeySet().comparator();
    }

    @Override
    public SortedSet<R> rowKeySet() {
        return super.rowKeySet();
    }

    @Override
    public SortedMap<R, Map<C, V>> rowMap() {
        return super.rowMap();
    }

    private static class Factory<C, V>
    implements Supplier<TreeMap<C, V>>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final Comparator<? super C> comparator;

        Factory(Comparator<? super C> comparator) {
            this.comparator = comparator;
        }

        @Override
        public TreeMap<C, V> get() {
            return new TreeMap(this.comparator);
        }
    }

    private class TreeRow
    extends StandardTable<R, C, V>
    implements SortedMap<C, V> {
        @NullableDecl
        final C lowerBound;
        @NullableDecl
        final C upperBound;
        @NullableDecl
        transient SortedMap<C, V> wholeRow;

        TreeRow(R r) {
            this(r, null, null);
        }

        TreeRow(@NullableDecl R r, @NullableDecl C c, C c2) {
            super(r);
            this.lowerBound = c;
            this.upperBound = c2;
            boolean bl = c == null || c2 == null || this.compare(c, c2) <= 0;
            Preconditions.checkArgument(bl);
        }

        SortedMap<C, V> backingRowMap() {
            return (SortedMap)StandardTable.Row.super.backingRowMap();
        }

        @Override
        public Comparator<? super C> comparator() {
            return TreeBasedTable.this.columnComparator();
        }

        int compare(Object object, Object object2) {
            return this.comparator().compare(object, object2);
        }

        SortedMap<C, V> computeBackingRowMap() {
            SortedMap<C, V> sortedMap = this.wholeRow();
            if (sortedMap == null) return null;
            C c = this.lowerBound;
            SortedMap<C, V> sortedMap2 = sortedMap;
            if (c != null) {
                sortedMap2 = sortedMap.tailMap(c);
            }
            c = this.upperBound;
            sortedMap = sortedMap2;
            if (c == null) return sortedMap;
            return sortedMap2.headMap(c);
        }

        @Override
        public boolean containsKey(Object object) {
            if (!this.rangeContains(object)) return false;
            if (!StandardTable.Row.super.containsKey(object)) return false;
            return true;
        }

        @Override
        public C firstKey() {
            if (this.backingRowMap() == null) throw new NoSuchElementException();
            return (C)this.backingRowMap().firstKey();
        }

        @Override
        public SortedMap<C, V> headMap(C c) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(c)));
            return new TreeRow(this.rowKey, this.lowerBound, c);
        }

        @Override
        public SortedSet<C> keySet() {
            return new Maps.SortedKeySet(this);
        }

        @Override
        public C lastKey() {
            if (this.backingRowMap() == null) throw new NoSuchElementException();
            return (C)this.backingRowMap().lastKey();
        }

        void maintainEmptyInvariant() {
            if (this.wholeRow() == null) return;
            if (!this.wholeRow.isEmpty()) return;
            TreeBasedTable.this.backingMap.remove(this.rowKey);
            this.wholeRow = null;
            this.backingRowMap = null;
        }

        @Override
        public V put(C c, V v) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(c)));
            return StandardTable.Row.super.put(c, v);
        }

        boolean rangeContains(@NullableDecl Object object) {
            if (object == null) return false;
            C c = this.lowerBound;
            if (c != null) {
                if (this.compare(c, object) > 0) return false;
            }
            if ((c = this.upperBound) == null) return true;
            if (this.compare(c, object) <= 0) return false;
            return true;
        }

        @Override
        public SortedMap<C, V> subMap(C c, C c2) {
            boolean bl = this.rangeContains(Preconditions.checkNotNull(c)) && this.rangeContains(Preconditions.checkNotNull(c2));
            Preconditions.checkArgument(bl);
            return new TreeRow(this.rowKey, c, c2);
        }

        @Override
        public SortedMap<C, V> tailMap(C c) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(c)));
            return new TreeRow(this.rowKey, c, this.upperBound);
        }

        SortedMap<C, V> wholeRow() {
            SortedMap<C, V> sortedMap = this.wholeRow;
            if (sortedMap != null) {
                if (!sortedMap.isEmpty()) return this.wholeRow;
                if (!TreeBasedTable.this.backingMap.containsKey(this.rowKey)) return this.wholeRow;
            }
            this.wholeRow = (SortedMap)TreeBasedTable.this.backingMap.get(this.rowKey);
            return this.wholeRow;
        }
    }

}


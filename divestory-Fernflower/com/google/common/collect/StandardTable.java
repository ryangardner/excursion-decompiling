package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class StandardTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
   private static final long serialVersionUID = 0L;
   @GwtTransient
   final Map<R, Map<C, V>> backingMap;
   @MonotonicNonNullDecl
   private transient Set<C> columnKeySet;
   @MonotonicNonNullDecl
   private transient StandardTable<R, C, V>.ColumnMap columnMap;
   @GwtTransient
   final Supplier<? extends Map<C, V>> factory;
   @MonotonicNonNullDecl
   private transient Map<R, Map<C, V>> rowMap;

   StandardTable(Map<R, Map<C, V>> var1, Supplier<? extends Map<C, V>> var2) {
      this.backingMap = var1;
      this.factory = var2;
   }

   private boolean containsMapping(Object var1, Object var2, Object var3) {
      boolean var4;
      if (var3 != null && var3.equals(this.get(var1, var2))) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   private Map<C, V> getOrCreate(R var1) {
      Map var2 = (Map)this.backingMap.get(var1);
      Map var3 = var2;
      if (var2 == null) {
         var3 = (Map)this.factory.get();
         this.backingMap.put(var1, var3);
      }

      return var3;
   }

   private Map<R, V> removeColumn(Object var1) {
      LinkedHashMap var2 = new LinkedHashMap();
      Iterator var3 = this.backingMap.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Object var5 = ((Map)var4.getValue()).remove(var1);
         if (var5 != null) {
            var2.put(var4.getKey(), var5);
            if (((Map)var4.getValue()).isEmpty()) {
               var3.remove();
            }
         }
      }

      return var2;
   }

   private boolean removeMapping(Object var1, Object var2, Object var3) {
      if (this.containsMapping(var1, var2, var3)) {
         this.remove(var1, var2);
         return true;
      } else {
         return false;
      }
   }

   Iterator<Table.Cell<R, C, V>> cellIterator() {
      return new StandardTable.CellIterator();
   }

   public Set<Table.Cell<R, C, V>> cellSet() {
      return super.cellSet();
   }

   public void clear() {
      this.backingMap.clear();
   }

   public Map<R, V> column(C var1) {
      return new StandardTable.Column(var1);
   }

   public Set<C> columnKeySet() {
      Set var1 = this.columnKeySet;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new StandardTable.ColumnKeySet();
         this.columnKeySet = (Set)var2;
      }

      return (Set)var2;
   }

   public Map<C, Map<R, V>> columnMap() {
      StandardTable.ColumnMap var1 = this.columnMap;
      StandardTable.ColumnMap var2 = var1;
      if (var1 == null) {
         var2 = new StandardTable.ColumnMap();
         this.columnMap = var2;
      }

      return var2;
   }

   public boolean contains(@NullableDecl Object var1, @NullableDecl Object var2) {
      boolean var3;
      if (var1 != null && var2 != null && super.contains(var1, var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean containsColumn(@NullableDecl Object var1) {
      if (var1 == null) {
         return false;
      } else {
         Iterator var2 = this.backingMap.values().iterator();

         do {
            if (!var2.hasNext()) {
               return false;
            }
         } while(!Maps.safeContainsKey((Map)var2.next(), var1));

         return true;
      }
   }

   public boolean containsRow(@NullableDecl Object var1) {
      boolean var2;
      if (var1 != null && Maps.safeContainsKey(this.backingMap, var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean containsValue(@NullableDecl Object var1) {
      boolean var2;
      if (var1 != null && super.containsValue(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   Iterator<C> createColumnKeyIterator() {
      return new StandardTable.ColumnKeyIterator();
   }

   Map<R, Map<C, V>> createRowMap() {
      return new StandardTable.RowMap();
   }

   public V get(@NullableDecl Object var1, @NullableDecl Object var2) {
      if (var1 != null && var2 != null) {
         var1 = super.get(var1, var2);
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean isEmpty() {
      return this.backingMap.isEmpty();
   }

   public V put(R var1, C var2, V var3) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      Preconditions.checkNotNull(var3);
      return this.getOrCreate(var1).put(var2, var3);
   }

   public V remove(@NullableDecl Object var1, @NullableDecl Object var2) {
      if (var1 != null && var2 != null) {
         Map var3 = (Map)Maps.safeGet(this.backingMap, var1);
         if (var3 == null) {
            return null;
         } else {
            var2 = var3.remove(var2);
            if (var3.isEmpty()) {
               this.backingMap.remove(var1);
            }

            return var2;
         }
      } else {
         return null;
      }
   }

   public Map<C, V> row(R var1) {
      return new StandardTable.Row(var1);
   }

   public Set<R> rowKeySet() {
      return this.rowMap().keySet();
   }

   public Map<R, Map<C, V>> rowMap() {
      Map var1 = this.rowMap;
      Map var2 = var1;
      if (var1 == null) {
         var2 = this.createRowMap();
         this.rowMap = var2;
      }

      return var2;
   }

   public int size() {
      Iterator var1 = this.backingMap.values().iterator();

      int var2;
      for(var2 = 0; var1.hasNext(); var2 += ((Map)var1.next()).size()) {
      }

      return var2;
   }

   public Collection<V> values() {
      return super.values();
   }

   private class CellIterator implements Iterator<Table.Cell<R, C, V>> {
      Iterator<Entry<C, V>> columnIterator;
      @NullableDecl
      Entry<R, Map<C, V>> rowEntry;
      final Iterator<Entry<R, Map<C, V>>> rowIterator;

      private CellIterator() {
         this.rowIterator = StandardTable.this.backingMap.entrySet().iterator();
         this.columnIterator = Iterators.emptyModifiableIterator();
      }

      // $FF: synthetic method
      CellIterator(Object var2) {
         this();
      }

      public boolean hasNext() {
         boolean var1;
         if (!this.rowIterator.hasNext() && !this.columnIterator.hasNext()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Table.Cell<R, C, V> next() {
         Entry var1;
         if (!this.columnIterator.hasNext()) {
            var1 = (Entry)this.rowIterator.next();
            this.rowEntry = var1;
            this.columnIterator = ((Map)var1.getValue()).entrySet().iterator();
         }

         var1 = (Entry)this.columnIterator.next();
         return Tables.immutableCell(this.rowEntry.getKey(), var1.getKey(), var1.getValue());
      }

      public void remove() {
         this.columnIterator.remove();
         if (((Map)this.rowEntry.getValue()).isEmpty()) {
            this.rowIterator.remove();
            this.rowEntry = null;
         }

      }
   }

   private class Column extends Maps.ViewCachingAbstractMap<R, V> {
      final C columnKey;

      Column(C var2) {
         this.columnKey = Preconditions.checkNotNull(var2);
      }

      public boolean containsKey(Object var1) {
         return StandardTable.this.contains(var1, this.columnKey);
      }

      Set<Entry<R, V>> createEntrySet() {
         return new StandardTable.Column.EntrySet();
      }

      Set<R> createKeySet() {
         return new StandardTable.Column.KeySet();
      }

      Collection<V> createValues() {
         return new StandardTable.Column.Values();
      }

      public V get(Object var1) {
         return StandardTable.this.get(var1, this.columnKey);
      }

      public V put(R var1, V var2) {
         return StandardTable.this.put(var1, this.columnKey, var2);
      }

      public V remove(Object var1) {
         return StandardTable.this.remove(var1, this.columnKey);
      }

      boolean removeFromColumnIf(Predicate<? super Entry<R, V>> var1) {
         Iterator var2 = StandardTable.this.backingMap.entrySet().iterator();
         boolean var3 = false;

         while(var2.hasNext()) {
            Entry var4 = (Entry)var2.next();
            Map var5 = (Map)var4.getValue();
            Object var6 = var5.get(this.columnKey);
            if (var6 != null && var1.apply(Maps.immutableEntry(var4.getKey(), var6))) {
               var5.remove(this.columnKey);
               boolean var7 = true;
               var3 = var7;
               if (var5.isEmpty()) {
                  var2.remove();
                  var3 = var7;
               }
            }
         }

         return var3;
      }

      private class EntrySet extends Sets.ImprovedAbstractSet<Entry<R, V>> {
         private EntrySet() {
         }

         // $FF: synthetic method
         EntrySet(Object var2) {
            this();
         }

         public void clear() {
            Column.this.removeFromColumnIf(Predicates.alwaysTrue());
         }

         public boolean contains(Object var1) {
            if (var1 instanceof Entry) {
               Entry var2 = (Entry)var1;
               return StandardTable.this.containsMapping(var2.getKey(), Column.this.columnKey, var2.getValue());
            } else {
               return false;
            }
         }

         public boolean isEmpty() {
            return StandardTable.this.containsColumn(Column.this.columnKey) ^ true;
         }

         public Iterator<Entry<R, V>> iterator() {
            return Column.this.new EntrySetIterator();
         }

         public boolean remove(Object var1) {
            if (var1 instanceof Entry) {
               Entry var2 = (Entry)var1;
               return StandardTable.this.removeMapping(var2.getKey(), Column.this.columnKey, var2.getValue());
            } else {
               return false;
            }
         }

         public boolean retainAll(Collection<?> var1) {
            return Column.this.removeFromColumnIf(Predicates.not(Predicates.in(var1)));
         }

         public int size() {
            Iterator var1 = StandardTable.this.backingMap.values().iterator();
            int var2 = 0;

            while(var1.hasNext()) {
               if (((Map)var1.next()).containsKey(Column.this.columnKey)) {
                  ++var2;
               }
            }

            return var2;
         }
      }

      private class EntrySetIterator extends AbstractIterator<Entry<R, V>> {
         final Iterator<Entry<R, Map<C, V>>> iterator;

         private EntrySetIterator() {
            this.iterator = StandardTable.this.backingMap.entrySet().iterator();
         }

         // $FF: synthetic method
         EntrySetIterator(Object var2) {
            this();
         }

         protected Entry<R, V> computeNext() {
            while(true) {
               if (this.iterator.hasNext()) {
                  Entry var1 = (Entry)this.iterator.next();
                  if (!((Map)var1.getValue()).containsKey(Column.this.columnKey)) {
                     continue;
                  }

                  return new StandardTable$Column$EntrySetIterator$1EntryImpl(this, var1);
               }

               return (Entry)this.endOfData();
            }
         }
      }

      private class KeySet extends Maps.KeySet<R, V> {
         KeySet() {
            super(Column.this);
         }

         public boolean contains(Object var1) {
            return StandardTable.this.contains(var1, Column.this.columnKey);
         }

         public boolean remove(Object var1) {
            boolean var2;
            if (StandardTable.this.remove(var1, Column.this.columnKey) != null) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }

         public boolean retainAll(Collection<?> var1) {
            return Column.this.removeFromColumnIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(var1))));
         }
      }

      private class Values extends Maps.Values<R, V> {
         Values() {
            super(Column.this);
         }

         public boolean remove(Object var1) {
            boolean var2;
            if (var1 != null && Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.equalTo(var1)))) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }

         public boolean removeAll(Collection<?> var1) {
            return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.in(var1)));
         }

         public boolean retainAll(Collection<?> var1) {
            return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(var1))));
         }
      }
   }

   private class ColumnKeyIterator extends AbstractIterator<C> {
      Iterator<Entry<C, V>> entryIterator;
      final Iterator<Map<C, V>> mapIterator;
      final Map<C, V> seen;

      private ColumnKeyIterator() {
         this.seen = (Map)StandardTable.this.factory.get();
         this.mapIterator = StandardTable.this.backingMap.values().iterator();
         this.entryIterator = Iterators.emptyIterator();
      }

      // $FF: synthetic method
      ColumnKeyIterator(Object var2) {
         this();
      }

      protected C computeNext() {
         while(true) {
            if (this.entryIterator.hasNext()) {
               Entry var1 = (Entry)this.entryIterator.next();
               if (!this.seen.containsKey(var1.getKey())) {
                  this.seen.put(var1.getKey(), var1.getValue());
                  return var1.getKey();
               }
            } else {
               if (!this.mapIterator.hasNext()) {
                  return this.endOfData();
               }

               this.entryIterator = ((Map)this.mapIterator.next()).entrySet().iterator();
            }
         }
      }
   }

   private class ColumnKeySet extends StandardTable<R, C, V>.TableSet<C> {
      private ColumnKeySet() {
         super(null);
      }

      // $FF: synthetic method
      ColumnKeySet(Object var2) {
         this();
      }

      public boolean contains(Object var1) {
         return StandardTable.this.containsColumn(var1);
      }

      public Iterator<C> iterator() {
         return StandardTable.this.createColumnKeyIterator();
      }

      public boolean remove(Object var1) {
         boolean var2 = false;
         if (var1 == null) {
            return false;
         } else {
            Iterator var3 = StandardTable.this.backingMap.values().iterator();

            while(var3.hasNext()) {
               Map var4 = (Map)var3.next();
               if (var4.keySet().remove(var1)) {
                  boolean var5 = true;
                  var2 = var5;
                  if (var4.isEmpty()) {
                     var3.remove();
                     var2 = var5;
                  }
               }
            }

            return var2;
         }
      }

      public boolean removeAll(Collection<?> var1) {
         Preconditions.checkNotNull(var1);
         Iterator var2 = StandardTable.this.backingMap.values().iterator();
         boolean var3 = false;

         while(var2.hasNext()) {
            Map var4 = (Map)var2.next();
            if (Iterators.removeAll(var4.keySet().iterator(), var1)) {
               boolean var5 = true;
               var3 = var5;
               if (var4.isEmpty()) {
                  var2.remove();
                  var3 = var5;
               }
            }
         }

         return var3;
      }

      public boolean retainAll(Collection<?> var1) {
         Preconditions.checkNotNull(var1);
         Iterator var2 = StandardTable.this.backingMap.values().iterator();
         boolean var3 = false;

         while(var2.hasNext()) {
            Map var4 = (Map)var2.next();
            if (var4.keySet().retainAll(var1)) {
               boolean var5 = true;
               var3 = var5;
               if (var4.isEmpty()) {
                  var2.remove();
                  var3 = var5;
               }
            }
         }

         return var3;
      }

      public int size() {
         return Iterators.size(this.iterator());
      }
   }

   private class ColumnMap extends Maps.ViewCachingAbstractMap<C, Map<R, V>> {
      private ColumnMap() {
      }

      // $FF: synthetic method
      ColumnMap(Object var2) {
         this();
      }

      public boolean containsKey(Object var1) {
         return StandardTable.this.containsColumn(var1);
      }

      public Set<Entry<C, Map<R, V>>> createEntrySet() {
         return new StandardTable.ColumnMap.ColumnMapEntrySet();
      }

      Collection<Map<R, V>> createValues() {
         return new StandardTable.ColumnMap.ColumnMapValues();
      }

      public Map<R, V> get(Object var1) {
         Map var2;
         if (StandardTable.this.containsColumn(var1)) {
            var2 = StandardTable.this.column(var1);
         } else {
            var2 = null;
         }

         return var2;
      }

      public Set<C> keySet() {
         return StandardTable.this.columnKeySet();
      }

      public Map<R, V> remove(Object var1) {
         Map var2;
         if (StandardTable.this.containsColumn(var1)) {
            var2 = StandardTable.this.removeColumn(var1);
         } else {
            var2 = null;
         }

         return var2;
      }

      class ColumnMapEntrySet extends StandardTable<R, C, V>.TableSet<Entry<C, Map<R, V>>> {
         ColumnMapEntrySet() {
            super(null);
         }

         public boolean contains(Object var1) {
            if (var1 instanceof Entry) {
               Entry var3 = (Entry)var1;
               if (StandardTable.this.containsColumn(var3.getKey())) {
                  Object var2 = var3.getKey();
                  return ColumnMap.this.get(var2).equals(var3.getValue());
               }
            }

            return false;
         }

         public Iterator<Entry<C, Map<R, V>>> iterator() {
            return Maps.asMapEntryIterator(StandardTable.this.columnKeySet(), new Function<C, Map<R, V>>() {
               public Map<R, V> apply(C var1) {
                  return StandardTable.this.column(var1);
               }
            });
         }

         public boolean remove(Object var1) {
            if (this.contains(var1)) {
               Entry var2 = (Entry)var1;
               StandardTable.this.removeColumn(var2.getKey());
               return true;
            } else {
               return false;
            }
         }

         public boolean removeAll(Collection<?> var1) {
            Preconditions.checkNotNull(var1);
            return Sets.removeAllImpl(this, (Iterator)var1.iterator());
         }

         public boolean retainAll(Collection<?> var1) {
            Preconditions.checkNotNull(var1);
            Iterator var2 = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
            boolean var3 = false;

            while(var2.hasNext()) {
               Object var4 = var2.next();
               if (!var1.contains(Maps.immutableEntry(var4, StandardTable.this.column(var4)))) {
                  StandardTable.this.removeColumn(var4);
                  var3 = true;
               }
            }

            return var3;
         }

         public int size() {
            return StandardTable.this.columnKeySet().size();
         }
      }

      private class ColumnMapValues extends Maps.Values<C, Map<R, V>> {
         ColumnMapValues() {
            super(ColumnMap.this);
         }

         public boolean remove(Object var1) {
            Iterator var2 = ColumnMap.this.entrySet().iterator();

            Entry var3;
            do {
               if (!var2.hasNext()) {
                  return false;
               }

               var3 = (Entry)var2.next();
            } while(!((Map)var3.getValue()).equals(var1));

            StandardTable.this.removeColumn(var3.getKey());
            return true;
         }

         public boolean removeAll(Collection<?> var1) {
            Preconditions.checkNotNull(var1);
            Iterator var2 = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
            boolean var3 = false;

            while(var2.hasNext()) {
               Object var4 = var2.next();
               if (var1.contains(StandardTable.this.column(var4))) {
                  StandardTable.this.removeColumn(var4);
                  var3 = true;
               }
            }

            return var3;
         }

         public boolean retainAll(Collection<?> var1) {
            Preconditions.checkNotNull(var1);
            Iterator var2 = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
            boolean var3 = false;

            while(var2.hasNext()) {
               Object var4 = var2.next();
               if (!var1.contains(StandardTable.this.column(var4))) {
                  StandardTable.this.removeColumn(var4);
                  var3 = true;
               }
            }

            return var3;
         }
      }
   }

   class Row extends Maps.IteratorBasedAbstractMap<C, V> {
      @NullableDecl
      Map<C, V> backingRowMap;
      final R rowKey;

      Row(R var2) {
         this.rowKey = Preconditions.checkNotNull(var2);
      }

      Map<C, V> backingRowMap() {
         Map var1 = this.backingRowMap;
         if (var1 == null || var1.isEmpty() && StandardTable.this.backingMap.containsKey(this.rowKey)) {
            var1 = this.computeBackingRowMap();
            this.backingRowMap = var1;
         } else {
            var1 = this.backingRowMap;
         }

         return var1;
      }

      public void clear() {
         Map var1 = this.backingRowMap();
         if (var1 != null) {
            var1.clear();
         }

         this.maintainEmptyInvariant();
      }

      Map<C, V> computeBackingRowMap() {
         return (Map)StandardTable.this.backingMap.get(this.rowKey);
      }

      public boolean containsKey(Object var1) {
         Map var2 = this.backingRowMap();
         boolean var3;
         if (var1 != null && var2 != null && Maps.safeContainsKey(var2, var1)) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      Iterator<Entry<C, V>> entryIterator() {
         Map var1 = this.backingRowMap();
         return var1 == null ? Iterators.emptyModifiableIterator() : new Iterator<Entry<C, V>>(var1.entrySet().iterator()) {
            // $FF: synthetic field
            final Iterator val$iterator;

            {
               this.val$iterator = var2;
            }

            public boolean hasNext() {
               return this.val$iterator.hasNext();
            }

            public Entry<C, V> next() {
               return Row.this.wrapEntry((Entry)this.val$iterator.next());
            }

            public void remove() {
               this.val$iterator.remove();
               Row.this.maintainEmptyInvariant();
            }
         };
      }

      public V get(Object var1) {
         Map var2 = this.backingRowMap();
         if (var1 != null && var2 != null) {
            var1 = Maps.safeGet(var2, var1);
         } else {
            var1 = null;
         }

         return var1;
      }

      void maintainEmptyInvariant() {
         if (this.backingRowMap() != null && this.backingRowMap.isEmpty()) {
            StandardTable.this.backingMap.remove(this.rowKey);
            this.backingRowMap = null;
         }

      }

      public V put(C var1, V var2) {
         Preconditions.checkNotNull(var1);
         Preconditions.checkNotNull(var2);
         Map var3 = this.backingRowMap;
         return var3 != null && !var3.isEmpty() ? this.backingRowMap.put(var1, var2) : StandardTable.this.put(this.rowKey, var1, var2);
      }

      public V remove(Object var1) {
         Map var2 = this.backingRowMap();
         if (var2 == null) {
            return null;
         } else {
            var1 = Maps.safeRemove(var2, var1);
            this.maintainEmptyInvariant();
            return var1;
         }
      }

      public int size() {
         Map var1 = this.backingRowMap();
         int var2;
         if (var1 == null) {
            var2 = 0;
         } else {
            var2 = var1.size();
         }

         return var2;
      }

      Entry<C, V> wrapEntry(final Entry<C, V> var1) {
         return new ForwardingMapEntry<C, V>() {
            protected Entry<C, V> delegate() {
               return var1;
            }

            public boolean equals(Object var1x) {
               return this.standardEquals(var1x);
            }

            public V setValue(V var1x) {
               return super.setValue(Preconditions.checkNotNull(var1x));
            }
         };
      }
   }

   class RowMap extends Maps.ViewCachingAbstractMap<R, Map<C, V>> {
      public boolean containsKey(Object var1) {
         return StandardTable.this.containsRow(var1);
      }

      protected Set<Entry<R, Map<C, V>>> createEntrySet() {
         return new StandardTable.RowMap.EntrySet();
      }

      public Map<C, V> get(Object var1) {
         Map var2;
         if (StandardTable.this.containsRow(var1)) {
            var2 = StandardTable.this.row(var1);
         } else {
            var2 = null;
         }

         return var2;
      }

      public Map<C, V> remove(Object var1) {
         Map var2;
         if (var1 == null) {
            var2 = null;
         } else {
            var2 = (Map)StandardTable.this.backingMap.remove(var1);
         }

         return var2;
      }

      class EntrySet extends StandardTable<R, C, V>.TableSet<Entry<R, Map<C, V>>> {
         EntrySet() {
            super(null);
         }

         public boolean contains(Object var1) {
            boolean var2 = var1 instanceof Entry;
            boolean var3 = false;
            boolean var4 = var3;
            if (var2) {
               Entry var5 = (Entry)var1;
               var4 = var3;
               if (var5.getKey() != null) {
                  var4 = var3;
                  if (var5.getValue() instanceof Map) {
                     var4 = var3;
                     if (Collections2.safeContains(StandardTable.this.backingMap.entrySet(), var5)) {
                        var4 = true;
                     }
                  }
               }
            }

            return var4;
         }

         public Iterator<Entry<R, Map<C, V>>> iterator() {
            return Maps.asMapEntryIterator(StandardTable.this.backingMap.keySet(), new Function<R, Map<C, V>>() {
               public Map<C, V> apply(R var1) {
                  return StandardTable.this.row(var1);
               }
            });
         }

         public boolean remove(Object var1) {
            boolean var2 = var1 instanceof Entry;
            boolean var3 = false;
            boolean var4 = var3;
            if (var2) {
               Entry var5 = (Entry)var1;
               var4 = var3;
               if (var5.getKey() != null) {
                  var4 = var3;
                  if (var5.getValue() instanceof Map) {
                     var4 = var3;
                     if (StandardTable.this.backingMap.entrySet().remove(var5)) {
                        var4 = true;
                     }
                  }
               }
            }

            return var4;
         }

         public int size() {
            return StandardTable.this.backingMap.size();
         }
      }
   }

   private abstract class TableSet<T> extends Sets.ImprovedAbstractSet<T> {
      private TableSet() {
      }

      // $FF: synthetic method
      TableSet(Object var2) {
         this();
      }

      public void clear() {
         StandardTable.this.backingMap.clear();
      }

      public boolean isEmpty() {
         return StandardTable.this.backingMap.isEmpty();
      }
   }
}

package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class TreeBasedTable<R, C, V> extends StandardRowSortedTable<R, C, V> {
   private static final long serialVersionUID = 0L;
   private final Comparator<? super C> columnComparator;

   TreeBasedTable(Comparator<? super R> var1, Comparator<? super C> var2) {
      super(new TreeMap(var1), new TreeBasedTable.Factory(var2));
      this.columnComparator = var2;
   }

   public static <R extends Comparable, C extends Comparable, V> TreeBasedTable<R, C, V> create() {
      return new TreeBasedTable(Ordering.natural(), Ordering.natural());
   }

   public static <R, C, V> TreeBasedTable<R, C, V> create(TreeBasedTable<R, C, ? extends V> var0) {
      TreeBasedTable var1 = new TreeBasedTable(var0.rowComparator(), var0.columnComparator());
      var1.putAll(var0);
      return var1;
   }

   public static <R, C, V> TreeBasedTable<R, C, V> create(Comparator<? super R> var0, Comparator<? super C> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new TreeBasedTable(var0, var1);
   }

   @Deprecated
   public Comparator<? super C> columnComparator() {
      return this.columnComparator;
   }

   Iterator<C> createColumnKeyIterator() {
      final Comparator var1 = this.columnComparator();
      return new AbstractIterator<C>(Iterators.mergeSorted(Iterables.transform(this.backingMap.values(), new Function<Map<C, V>, Iterator<C>>() {
         public Iterator<C> apply(Map<C, V> var1) {
            return var1.keySet().iterator();
         }
      }), var1)) {
         @NullableDecl
         C lastValue;
         // $FF: synthetic field
         final Iterator val$merged;

         {
            this.val$merged = var2;
         }

         protected C computeNext() {
            Object var1x;
            boolean var3;
            do {
               if (!this.val$merged.hasNext()) {
                  this.lastValue = null;
                  return this.endOfData();
               }

               var1x = this.val$merged.next();
               Object var2 = this.lastValue;
               if (var2 != null && var1.compare(var1x, var2) == 0) {
                  var3 = true;
               } else {
                  var3 = false;
               }
            } while(var3);

            this.lastValue = var1x;
            return var1x;
         }
      };
   }

   public SortedMap<C, V> row(R var1) {
      return new TreeBasedTable.TreeRow(var1);
   }

   @Deprecated
   public Comparator<? super R> rowComparator() {
      return this.rowKeySet().comparator();
   }

   public SortedSet<R> rowKeySet() {
      return super.rowKeySet();
   }

   public SortedMap<R, Map<C, V>> rowMap() {
      return super.rowMap();
   }

   private static class Factory<C, V> implements Supplier<TreeMap<C, V>>, Serializable {
      private static final long serialVersionUID = 0L;
      final Comparator<? super C> comparator;

      Factory(Comparator<? super C> var1) {
         this.comparator = var1;
      }

      public TreeMap<C, V> get() {
         return new TreeMap(this.comparator);
      }
   }

   private class TreeRow extends StandardTable<R, C, V>.Row implements SortedMap<C, V> {
      @NullableDecl
      final C lowerBound;
      @NullableDecl
      final C upperBound;
      @NullableDecl
      transient SortedMap<C, V> wholeRow;

      TreeRow(R var2) {
         this(var2, (Object)null, (Object)null);
      }

      TreeRow(R var2, @NullableDecl C var3, @NullableDecl C var4) {
         super(var2);
         this.lowerBound = var3;
         this.upperBound = var4;
         boolean var5;
         if (var3 != null && var4 != null && this.compare(var3, var4) > 0) {
            var5 = false;
         } else {
            var5 = true;
         }

         Preconditions.checkArgument(var5);
      }

      SortedMap<C, V> backingRowMap() {
         return (SortedMap)super.backingRowMap();
      }

      public Comparator<? super C> comparator() {
         return TreeBasedTable.this.columnComparator();
      }

      int compare(Object var1, Object var2) {
         return this.comparator().compare(var1, var2);
      }

      SortedMap<C, V> computeBackingRowMap() {
         SortedMap var1 = this.wholeRow();
         if (var1 != null) {
            Object var2 = this.lowerBound;
            SortedMap var3 = var1;
            if (var2 != null) {
               var3 = var1.tailMap(var2);
            }

            var2 = this.upperBound;
            var1 = var3;
            if (var2 != null) {
               var1 = var3.headMap(var2);
            }

            return var1;
         } else {
            return null;
         }
      }

      public boolean containsKey(Object var1) {
         boolean var2;
         if (this.rangeContains(var1) && super.containsKey(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public C firstKey() {
         if (this.backingRowMap() != null) {
            return this.backingRowMap().firstKey();
         } else {
            throw new NoSuchElementException();
         }
      }

      public SortedMap<C, V> headMap(C var1) {
         Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(var1)));
         return TreeBasedTable.this.new TreeRow(this.rowKey, this.lowerBound, var1);
      }

      public SortedSet<C> keySet() {
         return new Maps.SortedKeySet(this);
      }

      public C lastKey() {
         if (this.backingRowMap() != null) {
            return this.backingRowMap().lastKey();
         } else {
            throw new NoSuchElementException();
         }
      }

      void maintainEmptyInvariant() {
         if (this.wholeRow() != null && this.wholeRow.isEmpty()) {
            TreeBasedTable.this.backingMap.remove(this.rowKey);
            this.wholeRow = null;
            this.backingRowMap = null;
         }

      }

      public V put(C var1, V var2) {
         Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(var1)));
         return super.put(var1, var2);
      }

      boolean rangeContains(@NullableDecl Object var1) {
         boolean var3;
         if (var1 != null) {
            Object var2 = this.lowerBound;
            if (var2 == null || this.compare(var2, var1) <= 0) {
               var2 = this.upperBound;
               if (var2 == null || this.compare(var2, var1) > 0) {
                  var3 = true;
                  return var3;
               }
            }
         }

         var3 = false;
         return var3;
      }

      public SortedMap<C, V> subMap(C var1, C var2) {
         boolean var3;
         if (this.rangeContains(Preconditions.checkNotNull(var1)) && this.rangeContains(Preconditions.checkNotNull(var2))) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3);
         return TreeBasedTable.this.new TreeRow(this.rowKey, var1, var2);
      }

      public SortedMap<C, V> tailMap(C var1) {
         Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(var1)));
         return TreeBasedTable.this.new TreeRow(this.rowKey, var1, this.upperBound);
      }

      SortedMap<C, V> wholeRow() {
         SortedMap var1 = this.wholeRow;
         if (var1 == null || var1.isEmpty() && TreeBasedTable.this.backingMap.containsKey(this.rowKey)) {
            this.wholeRow = (SortedMap)TreeBasedTable.this.backingMap.get(this.rowKey);
         }

         return this.wholeRow;
      }
   }
}

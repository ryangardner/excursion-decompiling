package com.google.common.collect;

import com.google.errorprone.annotations.Immutable;
import java.util.Map;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable(
   containerOf = {"R", "C", "V"}
)
final class DenseImmutableTable<R, C, V> extends RegularImmutableTable<R, C, V> {
   private final int[] cellColumnIndices;
   private final int[] cellRowIndices;
   private final int[] columnCounts;
   private final ImmutableMap<C, Integer> columnKeyToIndex;
   private final ImmutableMap<C, ImmutableMap<R, V>> columnMap;
   private final int[] rowCounts;
   private final ImmutableMap<R, Integer> rowKeyToIndex;
   private final ImmutableMap<R, ImmutableMap<C, V>> rowMap;
   private final V[][] values;

   DenseImmutableTable(ImmutableList<Table.Cell<R, C, V>> var1, ImmutableSet<R> var2, ImmutableSet<C> var3) {
      int var4 = var2.size();
      int var5 = var3.size();
      int var6 = 0;
      this.values = new Object[var4][var5];
      this.rowKeyToIndex = Maps.indexMap(var2);
      this.columnKeyToIndex = Maps.indexMap(var3);
      this.rowCounts = new int[this.rowKeyToIndex.size()];
      this.columnCounts = new int[this.columnKeyToIndex.size()];
      int[] var11 = new int[var1.size()];

      int[] var10;
      for(var10 = new int[var1.size()]; var6 < var1.size(); ++var6) {
         Table.Cell var7 = (Table.Cell)var1.get(var6);
         Object var8 = var7.getRowKey();
         Object var9 = var7.getColumnKey();
         var5 = (Integer)this.rowKeyToIndex.get(var8);
         var4 = (Integer)this.columnKeyToIndex.get(var9);
         this.checkNoDuplicate(var8, var9, this.values[var5][var4], var7.getValue());
         this.values[var5][var4] = var7.getValue();
         int[] var12 = this.rowCounts;
         int var10002 = var12[var5]++;
         var12 = this.columnCounts;
         var10002 = var12[var4]++;
         var11[var6] = var5;
         var10[var6] = var4;
      }

      this.cellRowIndices = var11;
      this.cellColumnIndices = var10;
      this.rowMap = new DenseImmutableTable.RowMap();
      this.columnMap = new DenseImmutableTable.ColumnMap();
   }

   public ImmutableMap<C, Map<R, V>> columnMap() {
      return ImmutableMap.copyOf((Map)this.columnMap);
   }

   ImmutableTable.SerializedForm createSerializedForm() {
      return ImmutableTable.SerializedForm.create(this, this.cellRowIndices, this.cellColumnIndices);
   }

   public V get(@NullableDecl Object var1, @NullableDecl Object var2) {
      Integer var3 = (Integer)this.rowKeyToIndex.get(var1);
      Integer var4 = (Integer)this.columnKeyToIndex.get(var2);
      if (var3 != null && var4 != null) {
         var1 = this.values[var3][var4];
      } else {
         var1 = null;
      }

      return var1;
   }

   Table.Cell<R, C, V> getCell(int var1) {
      int var2 = this.cellRowIndices[var1];
      var1 = this.cellColumnIndices[var1];
      return cellOf(this.rowKeySet().asList().get(var2), this.columnKeySet().asList().get(var1), this.values[var2][var1]);
   }

   V getValue(int var1) {
      return this.values[this.cellRowIndices[var1]][this.cellColumnIndices[var1]];
   }

   public ImmutableMap<R, Map<C, V>> rowMap() {
      return ImmutableMap.copyOf((Map)this.rowMap);
   }

   public int size() {
      return this.cellRowIndices.length;
   }

   private final class Column extends DenseImmutableTable.ImmutableArrayMap<R, V> {
      private final int columnIndex;

      Column(int var2) {
         super(DenseImmutableTable.this.columnCounts[var2]);
         this.columnIndex = var2;
      }

      V getValue(int var1) {
         return DenseImmutableTable.this.values[var1][this.columnIndex];
      }

      boolean isPartialView() {
         return true;
      }

      ImmutableMap<R, Integer> keyToIndex() {
         return DenseImmutableTable.this.rowKeyToIndex;
      }
   }

   private final class ColumnMap extends DenseImmutableTable.ImmutableArrayMap<C, ImmutableMap<R, V>> {
      private ColumnMap() {
         super(DenseImmutableTable.this.columnCounts.length);
      }

      // $FF: synthetic method
      ColumnMap(Object var2) {
         this();
      }

      ImmutableMap<R, V> getValue(int var1) {
         return DenseImmutableTable.this.new Column(var1);
      }

      boolean isPartialView() {
         return false;
      }

      ImmutableMap<C, Integer> keyToIndex() {
         return DenseImmutableTable.this.columnKeyToIndex;
      }
   }

   private abstract static class ImmutableArrayMap<K, V> extends ImmutableMap.IteratorBasedImmutableMap<K, V> {
      private final int size;

      ImmutableArrayMap(int var1) {
         this.size = var1;
      }

      private boolean isFull() {
         boolean var1;
         if (this.size == this.keyToIndex().size()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      ImmutableSet<K> createKeySet() {
         ImmutableSet var1;
         if (this.isFull()) {
            var1 = this.keyToIndex().keySet();
         } else {
            var1 = super.createKeySet();
         }

         return var1;
      }

      UnmodifiableIterator<Entry<K, V>> entryIterator() {
         return new AbstractIterator<Entry<K, V>>() {
            private int index = -1;
            private final int maxIndex = ImmutableArrayMap.this.keyToIndex().size();

            protected Entry<K, V> computeNext() {
               int var1 = this.index;

               while(true) {
                  this.index = var1 + 1;
                  var1 = this.index;
                  if (var1 >= this.maxIndex) {
                     return (Entry)this.endOfData();
                  }

                  Object var2 = ImmutableArrayMap.this.getValue(var1);
                  if (var2 != null) {
                     return Maps.immutableEntry(ImmutableArrayMap.this.getKey(this.index), var2);
                  }

                  var1 = this.index;
               }
            }
         };
      }

      public V get(@NullableDecl Object var1) {
         Integer var2 = (Integer)this.keyToIndex().get(var1);
         if (var2 == null) {
            var1 = null;
         } else {
            var1 = this.getValue(var2);
         }

         return var1;
      }

      K getKey(int var1) {
         return this.keyToIndex().keySet().asList().get(var1);
      }

      @NullableDecl
      abstract V getValue(int var1);

      abstract ImmutableMap<K, Integer> keyToIndex();

      public int size() {
         return this.size;
      }
   }

   private final class Row extends DenseImmutableTable.ImmutableArrayMap<C, V> {
      private final int rowIndex;

      Row(int var2) {
         super(DenseImmutableTable.this.rowCounts[var2]);
         this.rowIndex = var2;
      }

      V getValue(int var1) {
         return DenseImmutableTable.this.values[this.rowIndex][var1];
      }

      boolean isPartialView() {
         return true;
      }

      ImmutableMap<C, Integer> keyToIndex() {
         return DenseImmutableTable.this.columnKeyToIndex;
      }
   }

   private final class RowMap extends DenseImmutableTable.ImmutableArrayMap<R, ImmutableMap<C, V>> {
      private RowMap() {
         super(DenseImmutableTable.this.rowCounts.length);
      }

      // $FF: synthetic method
      RowMap(Object var2) {
         this();
      }

      ImmutableMap<C, V> getValue(int var1) {
         return DenseImmutableTable.this.new Row(var1);
      }

      boolean isPartialView() {
         return false;
      }

      ImmutableMap<R, Integer> keyToIndex() {
         return DenseImmutableTable.this.rowKeyToIndex;
      }
   }
}

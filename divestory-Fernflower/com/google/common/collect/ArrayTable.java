package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ArrayTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
   private static final long serialVersionUID = 0L;
   private final V[][] array;
   private final ImmutableMap<C, Integer> columnKeyToIndex;
   private final ImmutableList<C> columnList;
   @MonotonicNonNullDecl
   private transient ArrayTable<R, C, V>.ColumnMap columnMap;
   private final ImmutableMap<R, Integer> rowKeyToIndex;
   private final ImmutableList<R> rowList;
   @MonotonicNonNullDecl
   private transient ArrayTable<R, C, V>.RowMap rowMap;

   private ArrayTable(ArrayTable<R, C, V> var1) {
      ImmutableList var2 = var1.rowList;
      this.rowList = var2;
      this.columnList = var1.columnList;
      this.rowKeyToIndex = var1.rowKeyToIndex;
      this.columnKeyToIndex = var1.columnKeyToIndex;
      Object[][] var3 = new Object[var2.size()][this.columnList.size()];
      this.array = var3;

      for(int var4 = 0; var4 < this.rowList.size(); ++var4) {
         Object[][] var5 = var1.array;
         System.arraycopy(var5[var4], 0, var3[var4], 0, var5[var4].length);
      }

   }

   private ArrayTable(Table<R, C, V> var1) {
      this(var1.rowKeySet(), var1.columnKeySet());
      this.putAll(var1);
   }

   private ArrayTable(Iterable<? extends R> var1, Iterable<? extends C> var2) {
      this.rowList = ImmutableList.copyOf(var1);
      this.columnList = ImmutableList.copyOf(var2);
      boolean var3;
      if (this.rowList.isEmpty() == this.columnList.isEmpty()) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      this.rowKeyToIndex = Maps.indexMap(this.rowList);
      this.columnKeyToIndex = Maps.indexMap(this.columnList);
      this.array = new Object[this.rowList.size()][this.columnList.size()];
      this.eraseAll();
   }

   public static <R, C, V> ArrayTable<R, C, V> create(Table<R, C, V> var0) {
      ArrayTable var1;
      if (var0 instanceof ArrayTable) {
         var1 = new ArrayTable((ArrayTable)var0);
      } else {
         var1 = new ArrayTable(var0);
      }

      return var1;
   }

   public static <R, C, V> ArrayTable<R, C, V> create(Iterable<? extends R> var0, Iterable<? extends C> var1) {
      return new ArrayTable(var0, var1);
   }

   private Table.Cell<R, C, V> getCell(final int var1) {
      return new Tables.AbstractCell<R, C, V>() {
         final int columnIndex;
         final int rowIndex;

         {
            this.rowIndex = var1 / ArrayTable.this.columnList.size();
            this.columnIndex = var1 % ArrayTable.this.columnList.size();
         }

         public C getColumnKey() {
            return ArrayTable.this.columnList.get(this.columnIndex);
         }

         public R getRowKey() {
            return ArrayTable.this.rowList.get(this.rowIndex);
         }

         public V getValue() {
            return ArrayTable.this.at(this.rowIndex, this.columnIndex);
         }
      };
   }

   private V getValue(int var1) {
      return this.at(var1 / this.columnList.size(), var1 % this.columnList.size());
   }

   public V at(int var1, int var2) {
      Preconditions.checkElementIndex(var1, this.rowList.size());
      Preconditions.checkElementIndex(var2, this.columnList.size());
      return this.array[var1][var2];
   }

   Iterator<Table.Cell<R, C, V>> cellIterator() {
      return new AbstractIndexedListIterator<Table.Cell<R, C, V>>(this.size()) {
         protected Table.Cell<R, C, V> get(int var1) {
            return ArrayTable.this.getCell(var1);
         }
      };
   }

   public Set<Table.Cell<R, C, V>> cellSet() {
      return super.cellSet();
   }

   @Deprecated
   public void clear() {
      throw new UnsupportedOperationException();
   }

   public Map<R, V> column(C var1) {
      Preconditions.checkNotNull(var1);
      Integer var2 = (Integer)this.columnKeyToIndex.get(var1);
      if (var2 == null) {
         var1 = ImmutableMap.of();
      } else {
         var1 = new ArrayTable.Column(var2);
      }

      return (Map)var1;
   }

   public ImmutableList<C> columnKeyList() {
      return this.columnList;
   }

   public ImmutableSet<C> columnKeySet() {
      return this.columnKeyToIndex.keySet();
   }

   public Map<C, Map<R, V>> columnMap() {
      ArrayTable.ColumnMap var1 = this.columnMap;
      ArrayTable.ColumnMap var2 = var1;
      if (var1 == null) {
         var2 = new ArrayTable.ColumnMap();
         this.columnMap = var2;
      }

      return var2;
   }

   public boolean contains(@NullableDecl Object var1, @NullableDecl Object var2) {
      boolean var3;
      if (this.containsRow(var1) && this.containsColumn(var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean containsColumn(@NullableDecl Object var1) {
      return this.columnKeyToIndex.containsKey(var1);
   }

   public boolean containsRow(@NullableDecl Object var1) {
      return this.rowKeyToIndex.containsKey(var1);
   }

   public boolean containsValue(@NullableDecl Object var1) {
      Object[][] var2 = this.array;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object[] var5 = var2[var4];
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            if (Objects.equal(var1, var5[var7])) {
               return true;
            }
         }
      }

      return false;
   }

   public V erase(@NullableDecl Object var1, @NullableDecl Object var2) {
      Integer var3 = (Integer)this.rowKeyToIndex.get(var1);
      Integer var4 = (Integer)this.columnKeyToIndex.get(var2);
      return var3 != null && var4 != null ? this.set(var3, var4, (Object)null) : null;
   }

   public void eraseAll() {
      Object[][] var1 = this.array;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Arrays.fill(var1[var3], (Object)null);
      }

   }

   public V get(@NullableDecl Object var1, @NullableDecl Object var2) {
      Integer var3 = (Integer)this.rowKeyToIndex.get(var1);
      Integer var4 = (Integer)this.columnKeyToIndex.get(var2);
      if (var3 != null && var4 != null) {
         var1 = this.at(var3, var4);
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean isEmpty() {
      boolean var1;
      if (!this.rowList.isEmpty() && !this.columnList.isEmpty()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public V put(R var1, C var2, @NullableDecl V var3) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      Integer var4 = (Integer)this.rowKeyToIndex.get(var1);
      boolean var5 = true;
      boolean var6;
      if (var4 != null) {
         var6 = true;
      } else {
         var6 = false;
      }

      Preconditions.checkArgument(var6, "Row %s not in %s", var1, this.rowList);
      Integer var7 = (Integer)this.columnKeyToIndex.get(var2);
      if (var7 != null) {
         var6 = var5;
      } else {
         var6 = false;
      }

      Preconditions.checkArgument(var6, "Column %s not in %s", var2, this.columnList);
      return this.set(var4, var7, var3);
   }

   public void putAll(Table<? extends R, ? extends C, ? extends V> var1) {
      super.putAll(var1);
   }

   @Deprecated
   public V remove(Object var1, Object var2) {
      throw new UnsupportedOperationException();
   }

   public Map<C, V> row(R var1) {
      Preconditions.checkNotNull(var1);
      Integer var2 = (Integer)this.rowKeyToIndex.get(var1);
      if (var2 == null) {
         var1 = ImmutableMap.of();
      } else {
         var1 = new ArrayTable.Row(var2);
      }

      return (Map)var1;
   }

   public ImmutableList<R> rowKeyList() {
      return this.rowList;
   }

   public ImmutableSet<R> rowKeySet() {
      return this.rowKeyToIndex.keySet();
   }

   public Map<R, Map<C, V>> rowMap() {
      ArrayTable.RowMap var1 = this.rowMap;
      ArrayTable.RowMap var2 = var1;
      if (var1 == null) {
         var2 = new ArrayTable.RowMap();
         this.rowMap = var2;
      }

      return var2;
   }

   public V set(int var1, int var2, @NullableDecl V var3) {
      Preconditions.checkElementIndex(var1, this.rowList.size());
      Preconditions.checkElementIndex(var2, this.columnList.size());
      Object[][] var4 = this.array;
      Object var5 = var4[var1][var2];
      var4[var1][var2] = var3;
      return var5;
   }

   public int size() {
      return this.rowList.size() * this.columnList.size();
   }

   public V[][] toArray(Class<V> var1) {
      Object[][] var4 = (Object[][])Array.newInstance(var1, new int[]{this.rowList.size(), this.columnList.size()});

      for(int var2 = 0; var2 < this.rowList.size(); ++var2) {
         Object[][] var3 = this.array;
         System.arraycopy(var3[var2], 0, var4[var2], 0, var3[var2].length);
      }

      return var4;
   }

   public Collection<V> values() {
      return super.values();
   }

   Iterator<V> valuesIterator() {
      return new AbstractIndexedListIterator<V>(this.size()) {
         protected V get(int var1) {
            return ArrayTable.this.getValue(var1);
         }
      };
   }

   private abstract static class ArrayMap<K, V> extends Maps.IteratorBasedAbstractMap<K, V> {
      private final ImmutableMap<K, Integer> keyIndex;

      private ArrayMap(ImmutableMap<K, Integer> var1) {
         this.keyIndex = var1;
      }

      // $FF: synthetic method
      ArrayMap(ImmutableMap var1, Object var2) {
         this(var1);
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public boolean containsKey(@NullableDecl Object var1) {
         return this.keyIndex.containsKey(var1);
      }

      Iterator<Entry<K, V>> entryIterator() {
         return new AbstractIndexedListIterator<Entry<K, V>>(this.size()) {
            protected Entry<K, V> get(int var1) {
               return ArrayMap.this.getEntry(var1);
            }
         };
      }

      public V get(@NullableDecl Object var1) {
         Integer var2 = (Integer)this.keyIndex.get(var1);
         return var2 == null ? null : this.getValue(var2);
      }

      Entry<K, V> getEntry(final int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return new AbstractMapEntry<K, V>() {
            public K getKey() {
               return ArrayMap.this.getKey(var1);
            }

            public V getValue() {
               return ArrayMap.this.getValue(var1);
            }

            public V setValue(V var1x) {
               return ArrayMap.this.setValue(var1, var1x);
            }
         };
      }

      K getKey(int var1) {
         return this.keyIndex.keySet().asList().get(var1);
      }

      abstract String getKeyRole();

      @NullableDecl
      abstract V getValue(int var1);

      public boolean isEmpty() {
         return this.keyIndex.isEmpty();
      }

      public Set<K> keySet() {
         return this.keyIndex.keySet();
      }

      public V put(K var1, V var2) {
         Integer var3 = (Integer)this.keyIndex.get(var1);
         if (var3 != null) {
            return this.setValue(var3, var2);
         } else {
            StringBuilder var4 = new StringBuilder();
            var4.append(this.getKeyRole());
            var4.append(" ");
            var4.append(var1);
            var4.append(" not in ");
            var4.append(this.keyIndex.keySet());
            throw new IllegalArgumentException(var4.toString());
         }
      }

      public V remove(Object var1) {
         throw new UnsupportedOperationException();
      }

      @NullableDecl
      abstract V setValue(int var1, V var2);

      public int size() {
         return this.keyIndex.size();
      }
   }

   private class Column extends ArrayTable.ArrayMap<R, V> {
      final int columnIndex;

      Column(int var2) {
         super(ArrayTable.this.rowKeyToIndex, null);
         this.columnIndex = var2;
      }

      String getKeyRole() {
         return "Row";
      }

      V getValue(int var1) {
         return ArrayTable.this.at(var1, this.columnIndex);
      }

      V setValue(int var1, V var2) {
         return ArrayTable.this.set(var1, this.columnIndex, var2);
      }
   }

   private class ColumnMap extends ArrayTable.ArrayMap<C, Map<R, V>> {
      private ColumnMap() {
         super(ArrayTable.this.columnKeyToIndex, null);
      }

      // $FF: synthetic method
      ColumnMap(Object var2) {
         this();
      }

      String getKeyRole() {
         return "Column";
      }

      Map<R, V> getValue(int var1) {
         return ArrayTable.this.new Column(var1);
      }

      public Map<R, V> put(C var1, Map<R, V> var2) {
         throw new UnsupportedOperationException();
      }

      Map<R, V> setValue(int var1, Map<R, V> var2) {
         throw new UnsupportedOperationException();
      }
   }

   private class Row extends ArrayTable.ArrayMap<C, V> {
      final int rowIndex;

      Row(int var2) {
         super(ArrayTable.this.columnKeyToIndex, null);
         this.rowIndex = var2;
      }

      String getKeyRole() {
         return "Column";
      }

      V getValue(int var1) {
         return ArrayTable.this.at(this.rowIndex, var1);
      }

      V setValue(int var1, V var2) {
         return ArrayTable.this.set(this.rowIndex, var1, var2);
      }
   }

   private class RowMap extends ArrayTable.ArrayMap<R, Map<C, V>> {
      private RowMap() {
         super(ArrayTable.this.rowKeyToIndex, null);
      }

      // $FF: synthetic method
      RowMap(Object var2) {
         this();
      }

      String getKeyRole() {
         return "Row";
      }

      Map<C, V> getValue(int var1) {
         return ArrayTable.this.new Row(var1);
      }

      public Map<C, V> put(R var1, Map<C, V> var2) {
         throw new UnsupportedOperationException();
      }

      Map<C, V> setValue(int var1, Map<C, V> var2) {
         throw new UnsupportedOperationException();
      }
   }
}

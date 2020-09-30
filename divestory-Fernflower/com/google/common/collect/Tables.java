package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
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
   private static final Function<? extends Map<?, ?>, ? extends Map<?, ?>> UNMODIFIABLE_WRAPPER = new Function<Map<Object, Object>, Map<Object, Object>>() {
      public Map<Object, Object> apply(Map<Object, Object> var1) {
         return Collections.unmodifiableMap(var1);
      }
   };

   private Tables() {
   }

   static boolean equalsImpl(Table<?, ?, ?> var0, @NullableDecl Object var1) {
      if (var1 == var0) {
         return true;
      } else if (var1 instanceof Table) {
         Table var2 = (Table)var1;
         return var0.cellSet().equals(var2.cellSet());
      } else {
         return false;
      }
   }

   public static <R, C, V> Table.Cell<R, C, V> immutableCell(@NullableDecl R var0, @NullableDecl C var1, @NullableDecl V var2) {
      return new Tables.ImmutableCell(var0, var1, var2);
   }

   public static <R, C, V> Table<R, C, V> newCustomTable(Map<R, Map<C, V>> var0, Supplier<? extends Map<C, V>> var1) {
      Preconditions.checkArgument(var0.isEmpty());
      Preconditions.checkNotNull(var1);
      return new StandardTable(var0, var1);
   }

   public static <R, C, V> Table<R, C, V> synchronizedTable(Table<R, C, V> var0) {
      return Synchronized.table(var0, (Object)null);
   }

   public static <R, C, V1, V2> Table<R, C, V2> transformValues(Table<R, C, V1> var0, Function<? super V1, V2> var1) {
      return new Tables.TransformedTable(var0, var1);
   }

   public static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> var0) {
      Object var1;
      if (var0 instanceof Tables.TransposeTable) {
         var1 = ((Tables.TransposeTable)var0).original;
      } else {
         var1 = new Tables.TransposeTable(var0);
      }

      return (Table)var1;
   }

   public static <R, C, V> RowSortedTable<R, C, V> unmodifiableRowSortedTable(RowSortedTable<R, ? extends C, ? extends V> var0) {
      return new Tables.UnmodifiableRowSortedMap(var0);
   }

   public static <R, C, V> Table<R, C, V> unmodifiableTable(Table<? extends R, ? extends C, ? extends V> var0) {
      return new Tables.UnmodifiableTable(var0);
   }

   private static <K, V> Function<Map<K, V>, Map<K, V>> unmodifiableWrapper() {
      return UNMODIFIABLE_WRAPPER;
   }

   abstract static class AbstractCell<R, C, V> implements Table.Cell<R, C, V> {
      public boolean equals(Object var1) {
         boolean var2 = true;
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof Table.Cell)) {
            return false;
         } else {
            Table.Cell var3 = (Table.Cell)var1;
            if (!Objects.equal(this.getRowKey(), var3.getRowKey()) || !Objects.equal(this.getColumnKey(), var3.getColumnKey()) || !Objects.equal(this.getValue(), var3.getValue())) {
               var2 = false;
            }

            return var2;
         }
      }

      public int hashCode() {
         return Objects.hashCode(this.getRowKey(), this.getColumnKey(), this.getValue());
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("(");
         var1.append(this.getRowKey());
         var1.append(",");
         var1.append(this.getColumnKey());
         var1.append(")=");
         var1.append(this.getValue());
         return var1.toString();
      }
   }

   static final class ImmutableCell<R, C, V> extends Tables.AbstractCell<R, C, V> implements Serializable {
      private static final long serialVersionUID = 0L;
      @NullableDecl
      private final C columnKey;
      @NullableDecl
      private final R rowKey;
      @NullableDecl
      private final V value;

      ImmutableCell(@NullableDecl R var1, @NullableDecl C var2, @NullableDecl V var3) {
         this.rowKey = var1;
         this.columnKey = var2;
         this.value = var3;
      }

      public C getColumnKey() {
         return this.columnKey;
      }

      public R getRowKey() {
         return this.rowKey;
      }

      public V getValue() {
         return this.value;
      }
   }

   private static class TransformedTable<R, C, V1, V2> extends AbstractTable<R, C, V2> {
      final Table<R, C, V1> fromTable;
      final Function<? super V1, V2> function;

      TransformedTable(Table<R, C, V1> var1, Function<? super V1, V2> var2) {
         this.fromTable = (Table)Preconditions.checkNotNull(var1);
         this.function = (Function)Preconditions.checkNotNull(var2);
      }

      Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>> cellFunction() {
         return new Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>>() {
            public Table.Cell<R, C, V2> apply(Table.Cell<R, C, V1> var1) {
               return Tables.immutableCell(var1.getRowKey(), var1.getColumnKey(), TransformedTable.this.function.apply(var1.getValue()));
            }
         };
      }

      Iterator<Table.Cell<R, C, V2>> cellIterator() {
         return Iterators.transform(this.fromTable.cellSet().iterator(), this.cellFunction());
      }

      public void clear() {
         this.fromTable.clear();
      }

      public Map<R, V2> column(C var1) {
         return Maps.transformValues(this.fromTable.column(var1), this.function);
      }

      public Set<C> columnKeySet() {
         return this.fromTable.columnKeySet();
      }

      public Map<C, Map<R, V2>> columnMap() {
         Function var1 = new Function<Map<R, V1>, Map<R, V2>>() {
            public Map<R, V2> apply(Map<R, V1> var1) {
               return Maps.transformValues(var1, TransformedTable.this.function);
            }
         };
         return Maps.transformValues(this.fromTable.columnMap(), var1);
      }

      public boolean contains(Object var1, Object var2) {
         return this.fromTable.contains(var1, var2);
      }

      Collection<V2> createValues() {
         return Collections2.transform(this.fromTable.values(), this.function);
      }

      public V2 get(Object var1, Object var2) {
         if (this.contains(var1, var2)) {
            var1 = this.function.apply(this.fromTable.get(var1, var2));
         } else {
            var1 = null;
         }

         return var1;
      }

      public V2 put(R var1, C var2, V2 var3) {
         throw new UnsupportedOperationException();
      }

      public void putAll(Table<? extends R, ? extends C, ? extends V2> var1) {
         throw new UnsupportedOperationException();
      }

      public V2 remove(Object var1, Object var2) {
         if (this.contains(var1, var2)) {
            var1 = this.function.apply(this.fromTable.remove(var1, var2));
         } else {
            var1 = null;
         }

         return var1;
      }

      public Map<C, V2> row(R var1) {
         return Maps.transformValues(this.fromTable.row(var1), this.function);
      }

      public Set<R> rowKeySet() {
         return this.fromTable.rowKeySet();
      }

      public Map<R, Map<C, V2>> rowMap() {
         Function var1 = new Function<Map<C, V1>, Map<C, V2>>() {
            public Map<C, V2> apply(Map<C, V1> var1) {
               return Maps.transformValues(var1, TransformedTable.this.function);
            }
         };
         return Maps.transformValues(this.fromTable.rowMap(), var1);
      }

      public int size() {
         return this.fromTable.size();
      }
   }

   private static class TransposeTable<C, R, V> extends AbstractTable<C, R, V> {
      private static final Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>> TRANSPOSE_CELL = new Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>>() {
         public Table.Cell<?, ?, ?> apply(Table.Cell<?, ?, ?> var1) {
            return Tables.immutableCell(var1.getColumnKey(), var1.getRowKey(), var1.getValue());
         }
      };
      final Table<R, C, V> original;

      TransposeTable(Table<R, C, V> var1) {
         this.original = (Table)Preconditions.checkNotNull(var1);
      }

      Iterator<Table.Cell<C, R, V>> cellIterator() {
         return Iterators.transform(this.original.cellSet().iterator(), TRANSPOSE_CELL);
      }

      public void clear() {
         this.original.clear();
      }

      public Map<C, V> column(R var1) {
         return this.original.row(var1);
      }

      public Set<R> columnKeySet() {
         return this.original.rowKeySet();
      }

      public Map<R, Map<C, V>> columnMap() {
         return this.original.rowMap();
      }

      public boolean contains(@NullableDecl Object var1, @NullableDecl Object var2) {
         return this.original.contains(var2, var1);
      }

      public boolean containsColumn(@NullableDecl Object var1) {
         return this.original.containsRow(var1);
      }

      public boolean containsRow(@NullableDecl Object var1) {
         return this.original.containsColumn(var1);
      }

      public boolean containsValue(@NullableDecl Object var1) {
         return this.original.containsValue(var1);
      }

      public V get(@NullableDecl Object var1, @NullableDecl Object var2) {
         return this.original.get(var2, var1);
      }

      public V put(C var1, R var2, V var3) {
         return this.original.put(var2, var1, var3);
      }

      public void putAll(Table<? extends C, ? extends R, ? extends V> var1) {
         this.original.putAll(Tables.transpose(var1));
      }

      public V remove(@NullableDecl Object var1, @NullableDecl Object var2) {
         return this.original.remove(var2, var1);
      }

      public Map<R, V> row(C var1) {
         return this.original.column(var1);
      }

      public Set<C> rowKeySet() {
         return this.original.columnKeySet();
      }

      public Map<C, Map<R, V>> rowMap() {
         return this.original.columnMap();
      }

      public int size() {
         return this.original.size();
      }

      public Collection<V> values() {
         return this.original.values();
      }
   }

   static final class UnmodifiableRowSortedMap<R, C, V> extends Tables.UnmodifiableTable<R, C, V> implements RowSortedTable<R, C, V> {
      private static final long serialVersionUID = 0L;

      public UnmodifiableRowSortedMap(RowSortedTable<R, ? extends C, ? extends V> var1) {
         super(var1);
      }

      protected RowSortedTable<R, C, V> delegate() {
         return (RowSortedTable)super.delegate();
      }

      public SortedSet<R> rowKeySet() {
         return Collections.unmodifiableSortedSet(this.delegate().rowKeySet());
      }

      public SortedMap<R, Map<C, V>> rowMap() {
         Function var1 = Tables.UNMODIFIABLE_WRAPPER;
         return Collections.unmodifiableSortedMap(Maps.transformValues(this.delegate().rowMap(), var1));
      }
   }

   private static class UnmodifiableTable<R, C, V> extends ForwardingTable<R, C, V> implements Serializable {
      private static final long serialVersionUID = 0L;
      final Table<? extends R, ? extends C, ? extends V> delegate;

      UnmodifiableTable(Table<? extends R, ? extends C, ? extends V> var1) {
         this.delegate = (Table)Preconditions.checkNotNull(var1);
      }

      public Set<Table.Cell<R, C, V>> cellSet() {
         return Collections.unmodifiableSet(super.cellSet());
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public Map<R, V> column(@NullableDecl C var1) {
         return Collections.unmodifiableMap(super.column(var1));
      }

      public Set<C> columnKeySet() {
         return Collections.unmodifiableSet(super.columnKeySet());
      }

      public Map<C, Map<R, V>> columnMap() {
         Function var1 = Tables.UNMODIFIABLE_WRAPPER;
         return Collections.unmodifiableMap(Maps.transformValues(super.columnMap(), var1));
      }

      protected Table<R, C, V> delegate() {
         return this.delegate;
      }

      public V put(@NullableDecl R var1, @NullableDecl C var2, @NullableDecl V var3) {
         throw new UnsupportedOperationException();
      }

      public void putAll(Table<? extends R, ? extends C, ? extends V> var1) {
         throw new UnsupportedOperationException();
      }

      public V remove(@NullableDecl Object var1, @NullableDecl Object var2) {
         throw new UnsupportedOperationException();
      }

      public Map<C, V> row(@NullableDecl R var1) {
         return Collections.unmodifiableMap(super.row(var1));
      }

      public Set<R> rowKeySet() {
         return Collections.unmodifiableSet(super.rowKeySet());
      }

      public Map<R, Map<C, V>> rowMap() {
         Function var1 = Tables.UNMODIFIABLE_WRAPPER;
         return Collections.unmodifiableMap(Maps.transformValues(super.rowMap(), var1));
      }

      public Collection<V> values() {
         return Collections.unmodifiableCollection(super.values());
      }
   }
}

package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
   ImmutableTable() {
   }

   public static <R, C, V> ImmutableTable.Builder<R, C, V> builder() {
      return new ImmutableTable.Builder();
   }

   static <R, C, V> Table.Cell<R, C, V> cellOf(R var0, C var1, V var2) {
      return Tables.immutableCell(Preconditions.checkNotNull(var0, "rowKey"), Preconditions.checkNotNull(var1, "columnKey"), Preconditions.checkNotNull(var2, "value"));
   }

   public static <R, C, V> ImmutableTable<R, C, V> copyOf(Table<? extends R, ? extends C, ? extends V> var0) {
      return var0 instanceof ImmutableTable ? (ImmutableTable)var0 : copyOf((Iterable)var0.cellSet());
   }

   private static <R, C, V> ImmutableTable<R, C, V> copyOf(Iterable<? extends Table.Cell<? extends R, ? extends C, ? extends V>> var0) {
      ImmutableTable.Builder var1 = builder();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         var1.put((Table.Cell)var2.next());
      }

      return var1.build();
   }

   public static <R, C, V> ImmutableTable<R, C, V> of() {
      return SparseImmutableTable.EMPTY;
   }

   public static <R, C, V> ImmutableTable<R, C, V> of(R var0, C var1, V var2) {
      return new SingletonImmutableTable(var0, var1, var2);
   }

   final UnmodifiableIterator<Table.Cell<R, C, V>> cellIterator() {
      throw new AssertionError("should never be called");
   }

   public ImmutableSet<Table.Cell<R, C, V>> cellSet() {
      return (ImmutableSet)super.cellSet();
   }

   @Deprecated
   public final void clear() {
      throw new UnsupportedOperationException();
   }

   public ImmutableMap<R, V> column(C var1) {
      Preconditions.checkNotNull(var1, "columnKey");
      return (ImmutableMap)MoreObjects.firstNonNull((ImmutableMap)this.columnMap().get(var1), ImmutableMap.of());
   }

   public ImmutableSet<C> columnKeySet() {
      return this.columnMap().keySet();
   }

   public abstract ImmutableMap<C, Map<R, V>> columnMap();

   public boolean contains(@NullableDecl Object var1, @NullableDecl Object var2) {
      boolean var3;
      if (this.get(var1, var2) != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean containsValue(@NullableDecl Object var1) {
      return this.values().contains(var1);
   }

   abstract ImmutableSet<Table.Cell<R, C, V>> createCellSet();

   abstract ImmutableTable.SerializedForm createSerializedForm();

   abstract ImmutableCollection<V> createValues();

   @Deprecated
   public final V put(R var1, C var2, V var3) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final void putAll(Table<? extends R, ? extends C, ? extends V> var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final V remove(Object var1, Object var2) {
      throw new UnsupportedOperationException();
   }

   public ImmutableMap<C, V> row(R var1) {
      Preconditions.checkNotNull(var1, "rowKey");
      return (ImmutableMap)MoreObjects.firstNonNull((ImmutableMap)this.rowMap().get(var1), ImmutableMap.of());
   }

   public ImmutableSet<R> rowKeySet() {
      return this.rowMap().keySet();
   }

   public abstract ImmutableMap<R, Map<C, V>> rowMap();

   public ImmutableCollection<V> values() {
      return (ImmutableCollection)super.values();
   }

   final Iterator<V> valuesIterator() {
      throw new AssertionError("should never be called");
   }

   final Object writeReplace() {
      return this.createSerializedForm();
   }

   @DoNotMock
   public static final class Builder<R, C, V> {
      private final List<Table.Cell<R, C, V>> cells = Lists.newArrayList();
      @MonotonicNonNullDecl
      private Comparator<? super C> columnComparator;
      @MonotonicNonNullDecl
      private Comparator<? super R> rowComparator;

      public ImmutableTable<R, C, V> build() {
         int var1 = this.cells.size();
         if (var1 != 0) {
            return (ImmutableTable)(var1 != 1 ? RegularImmutableTable.forCells(this.cells, this.rowComparator, this.columnComparator) : new SingletonImmutableTable((Table.Cell)Iterables.getOnlyElement(this.cells)));
         } else {
            return ImmutableTable.of();
         }
      }

      public ImmutableTable.Builder<R, C, V> orderColumnsBy(Comparator<? super C> var1) {
         this.columnComparator = (Comparator)Preconditions.checkNotNull(var1, "columnComparator");
         return this;
      }

      public ImmutableTable.Builder<R, C, V> orderRowsBy(Comparator<? super R> var1) {
         this.rowComparator = (Comparator)Preconditions.checkNotNull(var1, "rowComparator");
         return this;
      }

      public ImmutableTable.Builder<R, C, V> put(Table.Cell<? extends R, ? extends C, ? extends V> var1) {
         if (var1 instanceof Tables.ImmutableCell) {
            Preconditions.checkNotNull(var1.getRowKey(), "row");
            Preconditions.checkNotNull(var1.getColumnKey(), "column");
            Preconditions.checkNotNull(var1.getValue(), "value");
            this.cells.add(var1);
         } else {
            this.put(var1.getRowKey(), var1.getColumnKey(), var1.getValue());
         }

         return this;
      }

      public ImmutableTable.Builder<R, C, V> put(R var1, C var2, V var3) {
         this.cells.add(ImmutableTable.cellOf(var1, var2, var3));
         return this;
      }

      public ImmutableTable.Builder<R, C, V> putAll(Table<? extends R, ? extends C, ? extends V> var1) {
         Iterator var2 = var1.cellSet().iterator();

         while(var2.hasNext()) {
            this.put((Table.Cell)var2.next());
         }

         return this;
      }
   }

   static final class SerializedForm implements Serializable {
      private static final long serialVersionUID = 0L;
      private final int[] cellColumnIndices;
      private final int[] cellRowIndices;
      private final Object[] cellValues;
      private final Object[] columnKeys;
      private final Object[] rowKeys;

      private SerializedForm(Object[] var1, Object[] var2, Object[] var3, int[] var4, int[] var5) {
         this.rowKeys = var1;
         this.columnKeys = var2;
         this.cellValues = var3;
         this.cellRowIndices = var4;
         this.cellColumnIndices = var5;
      }

      static ImmutableTable.SerializedForm create(ImmutableTable<?, ?, ?> var0, int[] var1, int[] var2) {
         return new ImmutableTable.SerializedForm(var0.rowKeySet().toArray(), var0.columnKeySet().toArray(), var0.values().toArray(), var1, var2);
      }

      Object readResolve() {
         Object[] var1 = this.cellValues;
         if (var1.length == 0) {
            return ImmutableTable.of();
         } else {
            int var2 = var1.length;
            int var3 = 0;
            if (var2 == 1) {
               return ImmutableTable.of(this.rowKeys[0], this.columnKeys[0], var1[0]);
            } else {
               ImmutableList.Builder var5 = new ImmutableList.Builder(this.cellValues.length);

               while(true) {
                  Object[] var4 = this.cellValues;
                  if (var3 >= var4.length) {
                     return RegularImmutableTable.forOrderedComponents(var5.build(), ImmutableSet.copyOf(this.rowKeys), ImmutableSet.copyOf(this.columnKeys));
                  }

                  var5.add((Object)ImmutableTable.cellOf(this.rowKeys[this.cellRowIndices[var3]], this.columnKeys[this.cellColumnIndices[var3]], var4[var3]));
                  ++var3;
               }
            }
         }
      }
   }
}

package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class RegularImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {
   static <R, C, V> RegularImmutableTable<R, C, V> forCells(Iterable<Table.Cell<R, C, V>> var0) {
      return forCellsInternal(var0, (Comparator)null, (Comparator)null);
   }

   static <R, C, V> RegularImmutableTable<R, C, V> forCells(List<Table.Cell<R, C, V>> var0, @NullableDecl final Comparator<? super R> var1, @NullableDecl final Comparator<? super C> var2) {
      Preconditions.checkNotNull(var0);
      if (var1 != null || var2 != null) {
         Collections.sort(var0, new Comparator<Table.Cell<R, C, V>>() {
            public int compare(Table.Cell<R, C, V> var1x, Table.Cell<R, C, V> var2x) {
               Comparator var3 = var1;
               byte var4 = 0;
               int var5;
               if (var3 == null) {
                  var5 = 0;
               } else {
                  var5 = var3.compare(var1x.getRowKey(), var2x.getRowKey());
               }

               if (var5 != 0) {
                  return var5;
               } else {
                  var3 = var2;
                  if (var3 == null) {
                     var5 = var4;
                  } else {
                     var5 = var3.compare(var1x.getColumnKey(), var2x.getColumnKey());
                  }

                  return var5;
               }
            }
         });
      }

      return forCellsInternal(var0, var1, var2);
   }

   private static <R, C, V> RegularImmutableTable<R, C, V> forCellsInternal(Iterable<Table.Cell<R, C, V>> var0, @NullableDecl Comparator<? super R> var1, @NullableDecl Comparator<? super C> var2) {
      LinkedHashSet var3 = new LinkedHashSet();
      LinkedHashSet var4 = new LinkedHashSet();
      ImmutableList var5 = ImmutableList.copyOf(var0);
      Iterator var7 = var0.iterator();

      while(var7.hasNext()) {
         Table.Cell var6 = (Table.Cell)var7.next();
         var3.add(var6.getRowKey());
         var4.add(var6.getColumnKey());
      }

      ImmutableSet var8;
      if (var1 == null) {
         var8 = ImmutableSet.copyOf((Collection)var3);
      } else {
         var8 = ImmutableSet.copyOf((Collection)ImmutableList.sortedCopyOf(var1, var3));
      }

      ImmutableSet var9;
      if (var2 == null) {
         var9 = ImmutableSet.copyOf((Collection)var4);
      } else {
         var9 = ImmutableSet.copyOf((Collection)ImmutableList.sortedCopyOf(var2, var4));
      }

      return forOrderedComponents(var5, var8, var9);
   }

   static <R, C, V> RegularImmutableTable<R, C, V> forOrderedComponents(ImmutableList<Table.Cell<R, C, V>> var0, ImmutableSet<R> var1, ImmutableSet<C> var2) {
      Object var3;
      if ((long)var0.size() > (long)var1.size() * (long)var2.size() / 2L) {
         var3 = new DenseImmutableTable(var0, var1, var2);
      } else {
         var3 = new SparseImmutableTable(var0, var1, var2);
      }

      return (RegularImmutableTable)var3;
   }

   final void checkNoDuplicate(R var1, C var2, V var3, V var4) {
      boolean var5;
      if (var3 == null) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5, "Duplicate key: (row=%s, column=%s), values: [%s, %s].", var1, var2, var4, var3);
   }

   final ImmutableSet<Table.Cell<R, C, V>> createCellSet() {
      Object var1;
      if (this.isEmpty()) {
         var1 = ImmutableSet.of();
      } else {
         var1 = new RegularImmutableTable.CellSet();
      }

      return (ImmutableSet)var1;
   }

   final ImmutableCollection<V> createValues() {
      Object var1;
      if (this.isEmpty()) {
         var1 = ImmutableList.of();
      } else {
         var1 = new RegularImmutableTable.Values();
      }

      return (ImmutableCollection)var1;
   }

   abstract Table.Cell<R, C, V> getCell(int var1);

   abstract V getValue(int var1);

   private final class CellSet extends IndexedImmutableSet<Table.Cell<R, C, V>> {
      private CellSet() {
      }

      // $FF: synthetic method
      CellSet(Object var2) {
         this();
      }

      public boolean contains(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Table.Cell;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Table.Cell var5 = (Table.Cell)var1;
            var1 = RegularImmutableTable.this.get(var5.getRowKey(), var5.getColumnKey());
            var4 = var3;
            if (var1 != null) {
               var4 = var3;
               if (var1.equals(var5.getValue())) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      Table.Cell<R, C, V> get(int var1) {
         return RegularImmutableTable.this.getCell(var1);
      }

      boolean isPartialView() {
         return false;
      }

      public int size() {
         return RegularImmutableTable.this.size();
      }
   }

   private final class Values extends ImmutableList<V> {
      private Values() {
      }

      // $FF: synthetic method
      Values(Object var2) {
         this();
      }

      public V get(int var1) {
         return RegularImmutableTable.this.getValue(var1);
      }

      boolean isPartialView() {
         return true;
      }

      public int size() {
         return RegularImmutableTable.this.size();
      }
   }
}

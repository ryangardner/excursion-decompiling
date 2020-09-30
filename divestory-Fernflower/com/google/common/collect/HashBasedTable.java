package com.google.common.collect;

import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class HashBasedTable<R, C, V> extends StandardTable<R, C, V> {
   private static final long serialVersionUID = 0L;

   HashBasedTable(Map<R, Map<C, V>> var1, HashBasedTable.Factory<C, V> var2) {
      super(var1, var2);
   }

   public static <R, C, V> HashBasedTable<R, C, V> create() {
      return new HashBasedTable(new LinkedHashMap(), new HashBasedTable.Factory(0));
   }

   public static <R, C, V> HashBasedTable<R, C, V> create(int var0, int var1) {
      CollectPreconditions.checkNonnegative(var1, "expectedCellsPerRow");
      return new HashBasedTable(Maps.newLinkedHashMapWithExpectedSize(var0), new HashBasedTable.Factory(var1));
   }

   public static <R, C, V> HashBasedTable<R, C, V> create(Table<? extends R, ? extends C, ? extends V> var0) {
      HashBasedTable var1 = create();
      var1.putAll(var0);
      return var1;
   }

   public boolean contains(@NullableDecl Object var1, @NullableDecl Object var2) {
      return super.contains(var1, var2);
   }

   public boolean containsColumn(@NullableDecl Object var1) {
      return super.containsColumn(var1);
   }

   public boolean containsRow(@NullableDecl Object var1) {
      return super.containsRow(var1);
   }

   public boolean containsValue(@NullableDecl Object var1) {
      return super.containsValue(var1);
   }

   public boolean equals(@NullableDecl Object var1) {
      return super.equals(var1);
   }

   public V get(@NullableDecl Object var1, @NullableDecl Object var2) {
      return super.get(var1, var2);
   }

   public V remove(@NullableDecl Object var1, @NullableDecl Object var2) {
      return super.remove(var1, var2);
   }

   private static class Factory<C, V> implements Supplier<Map<C, V>>, Serializable {
      private static final long serialVersionUID = 0L;
      final int expectedSize;

      Factory(int var1) {
         this.expectedSize = var1;
      }

      public Map<C, V> get() {
         return Maps.newLinkedHashMapWithExpectedSize(this.expectedSize);
      }
   }
}

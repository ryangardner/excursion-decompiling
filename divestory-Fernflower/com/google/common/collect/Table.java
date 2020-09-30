package com.google.common.collect;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use ImmutableTable, HashBasedTable, or another implementation")
public interface Table<R, C, V> {
   Set<Table.Cell<R, C, V>> cellSet();

   void clear();

   Map<R, V> column(C var1);

   Set<C> columnKeySet();

   Map<C, Map<R, V>> columnMap();

   boolean contains(@NullableDecl Object var1, @NullableDecl Object var2);

   boolean containsColumn(@NullableDecl Object var1);

   boolean containsRow(@NullableDecl Object var1);

   boolean containsValue(@NullableDecl Object var1);

   boolean equals(@NullableDecl Object var1);

   V get(@NullableDecl Object var1, @NullableDecl Object var2);

   int hashCode();

   boolean isEmpty();

   @NullableDecl
   V put(R var1, C var2, V var3);

   void putAll(Table<? extends R, ? extends C, ? extends V> var1);

   @NullableDecl
   V remove(@NullableDecl Object var1, @NullableDecl Object var2);

   Map<C, V> row(R var1);

   Set<R> rowKeySet();

   Map<R, Map<C, V>> rowMap();

   int size();

   Collection<V> values();

   public interface Cell<R, C, V> {
      boolean equals(@NullableDecl Object var1);

      @NullableDecl
      C getColumnKey();

      @NullableDecl
      R getRowKey();

      @NullableDecl
      V getValue();

      int hashCode();
   }
}

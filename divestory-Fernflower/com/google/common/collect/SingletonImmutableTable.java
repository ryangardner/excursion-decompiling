package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Map;

class SingletonImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {
   final C singleColumnKey;
   final R singleRowKey;
   final V singleValue;

   SingletonImmutableTable(Table.Cell<R, C, V> var1) {
      this(var1.getRowKey(), var1.getColumnKey(), var1.getValue());
   }

   SingletonImmutableTable(R var1, C var2, V var3) {
      this.singleRowKey = Preconditions.checkNotNull(var1);
      this.singleColumnKey = Preconditions.checkNotNull(var2);
      this.singleValue = Preconditions.checkNotNull(var3);
   }

   public ImmutableMap<R, V> column(C var1) {
      Preconditions.checkNotNull(var1);
      ImmutableMap var2;
      if (this.containsColumn(var1)) {
         var2 = ImmutableMap.of(this.singleRowKey, this.singleValue);
      } else {
         var2 = ImmutableMap.of();
      }

      return var2;
   }

   public ImmutableMap<C, Map<R, V>> columnMap() {
      return ImmutableMap.of(this.singleColumnKey, ImmutableMap.of(this.singleRowKey, this.singleValue));
   }

   ImmutableSet<Table.Cell<R, C, V>> createCellSet() {
      return ImmutableSet.of(cellOf(this.singleRowKey, this.singleColumnKey, this.singleValue));
   }

   ImmutableTable.SerializedForm createSerializedForm() {
      return ImmutableTable.SerializedForm.create(this, new int[]{0}, new int[]{0});
   }

   ImmutableCollection<V> createValues() {
      return ImmutableSet.of(this.singleValue);
   }

   public ImmutableMap<R, Map<C, V>> rowMap() {
      return ImmutableMap.of(this.singleRowKey, ImmutableMap.of(this.singleColumnKey, this.singleValue));
   }

   public int size() {
      return 1;
   }
}

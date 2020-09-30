package com.google.common.collect;

import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularImmutableBiMap<K, V> extends ImmutableBiMap<K, V> {
   static final RegularImmutableBiMap<Object, Object> EMPTY = new RegularImmutableBiMap();
   final transient Object[] alternatingKeysAndValues;
   private final transient RegularImmutableBiMap<V, K> inverse;
   private final transient Object keyHashTable;
   private final transient int keyOffset;
   private final transient int size;

   private RegularImmutableBiMap() {
      this.keyHashTable = null;
      this.alternatingKeysAndValues = new Object[0];
      this.keyOffset = 0;
      this.size = 0;
      this.inverse = this;
   }

   private RegularImmutableBiMap(Object var1, Object[] var2, int var3, RegularImmutableBiMap<V, K> var4) {
      this.keyHashTable = var1;
      this.alternatingKeysAndValues = var2;
      this.keyOffset = 1;
      this.size = var3;
      this.inverse = var4;
   }

   RegularImmutableBiMap(Object[] var1, int var2) {
      this.alternatingKeysAndValues = var1;
      this.size = var2;
      this.keyOffset = 0;
      int var3;
      if (var2 >= 2) {
         var3 = ImmutableSet.chooseTableSize(var2);
      } else {
         var3 = 0;
      }

      this.keyHashTable = RegularImmutableMap.createHashTable(var1, var2, var3, 0);
      this.inverse = new RegularImmutableBiMap(RegularImmutableMap.createHashTable(var1, var2, var3, 1), var1, var2, this);
   }

   ImmutableSet<Entry<K, V>> createEntrySet() {
      return new RegularImmutableMap.EntrySet(this, this.alternatingKeysAndValues, this.keyOffset, this.size);
   }

   ImmutableSet<K> createKeySet() {
      return new RegularImmutableMap.KeySet(this, new RegularImmutableMap.KeysOrValuesAsList(this.alternatingKeysAndValues, this.keyOffset, this.size));
   }

   public V get(@NullableDecl Object var1) {
      return RegularImmutableMap.get(this.keyHashTable, this.alternatingKeysAndValues, this.size, this.keyOffset, var1);
   }

   public ImmutableBiMap<V, K> inverse() {
      return this.inverse;
   }

   boolean isPartialView() {
      return false;
   }

   public int size() {
      return this.size;
   }
}

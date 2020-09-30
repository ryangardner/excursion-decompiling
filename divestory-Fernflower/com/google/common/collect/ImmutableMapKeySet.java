package com.google.common.collect;

import java.io.Serializable;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class ImmutableMapKeySet<K, V> extends IndexedImmutableSet<K> {
   private final ImmutableMap<K, V> map;

   ImmutableMapKeySet(ImmutableMap<K, V> var1) {
      this.map = var1;
   }

   public boolean contains(@NullableDecl Object var1) {
      return this.map.containsKey(var1);
   }

   K get(int var1) {
      return ((Entry)this.map.entrySet().asList().get(var1)).getKey();
   }

   boolean isPartialView() {
      return true;
   }

   public UnmodifiableIterator<K> iterator() {
      return this.map.keyIterator();
   }

   public int size() {
      return this.map.size();
   }

   Object writeReplace() {
      return new ImmutableMapKeySet.KeySetSerializedForm(this.map);
   }

   private static class KeySetSerializedForm<K> implements Serializable {
      private static final long serialVersionUID = 0L;
      final ImmutableMap<K, ?> map;

      KeySetSerializedForm(ImmutableMap<K, ?> var1) {
         this.map = var1;
      }

      Object readResolve() {
         return this.map.keySet();
      }
   }
}

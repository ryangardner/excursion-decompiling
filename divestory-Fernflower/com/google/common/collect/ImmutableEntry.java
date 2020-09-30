package com.google.common.collect;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class ImmutableEntry<K, V> extends AbstractMapEntry<K, V> implements Serializable {
   private static final long serialVersionUID = 0L;
   @NullableDecl
   final K key;
   @NullableDecl
   final V value;

   ImmutableEntry(@NullableDecl K var1, @NullableDecl V var2) {
      this.key = var1;
      this.value = var2;
   }

   @NullableDecl
   public final K getKey() {
      return this.key;
   }

   @NullableDecl
   public final V getValue() {
      return this.value;
   }

   public final V setValue(V var1) {
      throw new UnsupportedOperationException();
   }
}

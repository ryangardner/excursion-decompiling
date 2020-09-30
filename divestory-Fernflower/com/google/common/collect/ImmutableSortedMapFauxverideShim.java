package com.google.common.collect;

abstract class ImmutableSortedMapFauxverideShim<K, V> extends ImmutableMap<K, V> {
   @Deprecated
   public static <K, V> ImmutableSortedMap.Builder<K, V> builder() {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <K, V> ImmutableSortedMap.Builder<K, V> builderWithExpectedSize(int var0) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <K, V> ImmutableSortedMap<K, V> of(K var0, V var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <K, V> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <K, V> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <K, V> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <K, V> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      throw new UnsupportedOperationException();
   }
}

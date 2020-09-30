package com.google.common.collect;

abstract class ImmutableSortedMultisetFauxverideShim<E> extends ImmutableMultiset<E> {
   @Deprecated
   public static <E> ImmutableSortedMultiset.Builder<E> builder() {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E> ImmutableSortedMultiset<E> copyOf(E[] var0) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E> ImmutableSortedMultiset<E> of(E var0) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E> ImmutableSortedMultiset<E> of(E var0, E var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E> ImmutableSortedMultiset<E> of(E var0, E var1, E var2) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E> ImmutableSortedMultiset<E> of(E var0, E var1, E var2, E var3) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E> ImmutableSortedMultiset<E> of(E var0, E var1, E var2, E var3, E var4) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E> ImmutableSortedMultiset<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E... var6) {
      throw new UnsupportedOperationException();
   }
}

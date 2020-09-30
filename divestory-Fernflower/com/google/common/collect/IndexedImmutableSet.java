package com.google.common.collect;

abstract class IndexedImmutableSet<E> extends ImmutableSet<E> {
   int copyIntoArray(Object[] var1, int var2) {
      return this.asList().copyIntoArray(var1, var2);
   }

   ImmutableList<E> createAsList() {
      return new ImmutableList<E>() {
         public E get(int var1) {
            return IndexedImmutableSet.this.get(var1);
         }

         boolean isPartialView() {
            return IndexedImmutableSet.this.isPartialView();
         }

         public int size() {
            return IndexedImmutableSet.this.size();
         }
      };
   }

   abstract E get(int var1);

   public UnmodifiableIterator<E> iterator() {
      return this.asList().iterator();
   }
}

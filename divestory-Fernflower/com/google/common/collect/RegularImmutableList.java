package com.google.common.collect;

import com.google.common.base.Preconditions;

class RegularImmutableList<E> extends ImmutableList<E> {
   static final ImmutableList<Object> EMPTY = new RegularImmutableList(new Object[0], 0);
   final transient Object[] array;
   private final transient int size;

   RegularImmutableList(Object[] var1, int var2) {
      this.array = var1;
      this.size = var2;
   }

   int copyIntoArray(Object[] var1, int var2) {
      System.arraycopy(this.array, 0, var1, var2, this.size);
      return var2 + this.size;
   }

   public E get(int var1) {
      Preconditions.checkElementIndex(var1, this.size);
      return this.array[var1];
   }

   Object[] internalArray() {
      return this.array;
   }

   int internalArrayEnd() {
      return this.size;
   }

   int internalArrayStart() {
      return 0;
   }

   boolean isPartialView() {
      return false;
   }

   public int size() {
      return this.size;
   }
}

package com.google.common.collect;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularImmutableSet<E> extends ImmutableSet<E> {
   static final RegularImmutableSet<Object> EMPTY = new RegularImmutableSet(new Object[0], 0, (Object[])null, 0, 0);
   final transient Object[] elements;
   private final transient int hashCode;
   private final transient int mask;
   private final transient int size;
   final transient Object[] table;

   RegularImmutableSet(Object[] var1, int var2, Object[] var3, int var4, int var5) {
      this.elements = var1;
      this.table = var3;
      this.mask = var4;
      this.hashCode = var2;
      this.size = var5;
   }

   public boolean contains(@NullableDecl Object var1) {
      Object[] var2 = this.table;
      if (var1 != null && var2 != null) {
         int var3 = Hashing.smearedHash(var1);

         while(true) {
            var3 &= this.mask;
            Object var4 = var2[var3];
            if (var4 == null) {
               return false;
            }

            if (var4.equals(var1)) {
               return true;
            }

            ++var3;
         }
      } else {
         return false;
      }
   }

   int copyIntoArray(Object[] var1, int var2) {
      System.arraycopy(this.elements, 0, var1, var2, this.size);
      return var2 + this.size;
   }

   ImmutableList<E> createAsList() {
      return ImmutableList.asImmutableList(this.elements, this.size);
   }

   public int hashCode() {
      return this.hashCode;
   }

   Object[] internalArray() {
      return this.elements;
   }

   int internalArrayEnd() {
      return this.size;
   }

   int internalArrayStart() {
      return 0;
   }

   boolean isHashCodeFast() {
      return true;
   }

   boolean isPartialView() {
      return false;
   }

   public UnmodifiableIterator<E> iterator() {
      return this.asList().iterator();
   }

   public int size() {
      return this.size;
   }
}

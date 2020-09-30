package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;

final class SingletonImmutableSet<E> extends ImmutableSet<E> {
   @LazyInit
   private transient int cachedHashCode;
   final transient E element;

   SingletonImmutableSet(E var1) {
      this.element = Preconditions.checkNotNull(var1);
   }

   SingletonImmutableSet(E var1, int var2) {
      this.element = var1;
      this.cachedHashCode = var2;
   }

   public boolean contains(Object var1) {
      return this.element.equals(var1);
   }

   int copyIntoArray(Object[] var1, int var2) {
      var1[var2] = this.element;
      return var2 + 1;
   }

   ImmutableList<E> createAsList() {
      return ImmutableList.of(this.element);
   }

   public final int hashCode() {
      int var1 = this.cachedHashCode;
      int var2 = var1;
      if (var1 == 0) {
         var2 = this.element.hashCode();
         this.cachedHashCode = var2;
      }

      return var2;
   }

   boolean isHashCodeFast() {
      boolean var1;
      if (this.cachedHashCode != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   boolean isPartialView() {
      return false;
   }

   public UnmodifiableIterator<E> iterator() {
      return Iterators.singletonIterator(this.element);
   }

   public int size() {
      return 1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append('[');
      var1.append(this.element.toString());
      var1.append(']');
      return var1.toString();
   }
}

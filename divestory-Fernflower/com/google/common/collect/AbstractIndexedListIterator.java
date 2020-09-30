package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.NoSuchElementException;

abstract class AbstractIndexedListIterator<E> extends UnmodifiableListIterator<E> {
   private int position;
   private final int size;

   protected AbstractIndexedListIterator(int var1) {
      this(var1, 0);
   }

   protected AbstractIndexedListIterator(int var1, int var2) {
      Preconditions.checkPositionIndex(var2, var1);
      this.size = var1;
      this.position = var2;
   }

   protected abstract E get(int var1);

   public final boolean hasNext() {
      boolean var1;
      if (this.position < this.size) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean hasPrevious() {
      boolean var1;
      if (this.position > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final E next() {
      if (this.hasNext()) {
         int var1 = this.position++;
         return this.get(var1);
      } else {
         throw new NoSuchElementException();
      }
   }

   public final int nextIndex() {
      return this.position;
   }

   public final E previous() {
      if (this.hasPrevious()) {
         int var1 = this.position - 1;
         this.position = var1;
         return this.get(var1);
      } else {
         throw new NoSuchElementException();
      }
   }

   public final int previousIndex() {
      return this.position - 1;
   }
}

package com.google.common.collect;

import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractSequentialIterator<T> extends UnmodifiableIterator<T> {
   @NullableDecl
   private T nextOrNull;

   protected AbstractSequentialIterator(@NullableDecl T var1) {
      this.nextOrNull = var1;
   }

   @NullableDecl
   protected abstract T computeNext(T var1);

   public final boolean hasNext() {
      boolean var1;
      if (this.nextOrNull != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final T next() {
      if (this.hasNext()) {
         Object var1;
         try {
            var1 = this.nextOrNull;
         } finally {
            this.nextOrNull = this.computeNext(this.nextOrNull);
         }

         return var1;
      } else {
         throw new NoSuchElementException();
      }
   }
}

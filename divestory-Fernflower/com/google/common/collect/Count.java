package com.google.common.collect;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Count implements Serializable {
   private int value;

   Count(int var1) {
      this.value = var1;
   }

   public void add(int var1) {
      this.value += var1;
   }

   public int addAndGet(int var1) {
      var1 += this.value;
      this.value = var1;
      return var1;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2;
      if (var1 instanceof Count && ((Count)var1).value == this.value) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int get() {
      return this.value;
   }

   public int getAndSet(int var1) {
      int var2 = this.value;
      this.value = var1;
      return var2;
   }

   public int hashCode() {
      return this.value;
   }

   public void set(int var1) {
      this.value = var1;
   }

   public String toString() {
      return Integer.toString(this.value);
   }
}

package com.google.common.cache;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

final class LongAdder extends Striped64 implements Serializable, LongAddable {
   private static final long serialVersionUID = 7249069246863182397L;

   public LongAdder() {
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.busy = 0;
      this.cells = null;
      this.base = var1.readLong();
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeLong(this.sum());
   }

   public void add(long var1) {
      Striped64.Cell[] var3 = this.cells;
      long var4;
      if (var3 == null) {
         var4 = this.base;
         if (this.casBase(var4, var4 + var1)) {
            return;
         }
      }

      int[] var6 = (int[])threadHashCode.get();
      boolean var7 = true;
      boolean var8 = var7;
      if (var6 != null) {
         var8 = var7;
         if (var3 != null) {
            int var9 = var3.length;
            var8 = var7;
            if (var9 >= 1) {
               Striped64.Cell var10 = var3[var9 - 1 & var6[0]];
               var8 = var7;
               if (var10 != null) {
                  var4 = var10.value;
                  var8 = var10.cas(var4, var4 + var1);
                  if (var8) {
                     return;
                  }
               }
            }
         }
      }

      this.retryUpdate(var1, var6, var8);
   }

   public void decrement() {
      this.add(-1L);
   }

   public double doubleValue() {
      return (double)this.sum();
   }

   public float floatValue() {
      return (float)this.sum();
   }

   final long fn(long var1, long var3) {
      return var1 + var3;
   }

   public void increment() {
      this.add(1L);
   }

   public int intValue() {
      return (int)this.sum();
   }

   public long longValue() {
      return this.sum();
   }

   public void reset() {
      this.internalReset(0L);
   }

   public long sum() {
      long var1 = this.base;
      Striped64.Cell[] var3 = this.cells;
      long var4 = var1;
      if (var3 != null) {
         int var6 = var3.length;
         int var7 = 0;

         while(true) {
            var4 = var1;
            if (var7 >= var6) {
               break;
            }

            Striped64.Cell var8 = var3[var7];
            var4 = var1;
            if (var8 != null) {
               var4 = var1 + var8.value;
            }

            ++var7;
            var1 = var4;
         }
      }

      return var4;
   }

   public long sumThenReset() {
      long var1 = this.base;
      Striped64.Cell[] var3 = this.cells;
      this.base = 0L;
      long var4 = var1;
      if (var3 != null) {
         int var6 = var3.length;
         int var7 = 0;

         while(true) {
            var4 = var1;
            if (var7 >= var6) {
               break;
            }

            Striped64.Cell var8 = var3[var7];
            var4 = var1;
            if (var8 != null) {
               var4 = var1 + var8.value;
               var8.value = 0L;
            }

            ++var7;
            var1 = var4;
         }
      }

      return var4;
   }

   public String toString() {
      return Long.toString(this.sum());
   }
}

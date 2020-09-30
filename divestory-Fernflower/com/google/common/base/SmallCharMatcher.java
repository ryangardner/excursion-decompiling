package com.google.common.base;

import java.util.BitSet;

final class SmallCharMatcher extends CharMatcher.NamedFastMatcher {
   private static final int C1 = -862048943;
   private static final int C2 = 461845907;
   private static final double DESIRED_LOAD_FACTOR = 0.5D;
   static final int MAX_SIZE = 1023;
   private final boolean containsZero;
   private final long filter;
   private final char[] table;

   private SmallCharMatcher(char[] var1, long var2, boolean var4, String var5) {
      super(var5);
      this.table = var1;
      this.filter = var2;
      this.containsZero = var4;
   }

   private boolean checkFilter(int var1) {
      boolean var2;
      if (1L == (this.filter >> var1 & 1L)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   static int chooseTableSize(int var0) {
      if (var0 == 1) {
         return 2;
      } else {
         int var1;
         for(var1 = Integer.highestOneBit(var0 - 1) << 1; (double)var1 * 0.5D < (double)var0; var1 <<= 1) {
         }

         return var1;
      }
   }

   static CharMatcher from(BitSet var0, String var1) {
      int var2 = var0.cardinality();
      boolean var3 = var0.get(0);
      int var4 = chooseTableSize(var2);
      char[] var5 = new char[var4];
      var2 = var0.nextSetBit(0);
      long var6 = 0L;

      while(true) {
         int var8 = var2;
         if (var2 == -1) {
            return new SmallCharMatcher(var5, var6, var3, var1);
         }

         var2 = smear(var2);

         while(true) {
            var2 &= var4 - 1;
            if (var5[var2] == 0) {
               var5[var2] = (char)((char)var8);
               var2 = var0.nextSetBit(var8 + 1);
               var6 |= 1L << var8;
               break;
            }

            ++var2;
         }
      }
   }

   static int smear(int var0) {
      return Integer.rotateLeft(var0 * -862048943, 15) * 461845907;
   }

   public boolean matches(char var1) {
      if (var1 == 0) {
         return this.containsZero;
      } else if (!this.checkFilter(var1)) {
         return false;
      } else {
         int var2 = this.table.length - 1;
         int var3 = smear(var1) & var2;
         int var4 = var3;

         int var6;
         do {
            char[] var5 = this.table;
            if (var5[var4] == 0) {
               return false;
            }

            if (var5[var4] == var1) {
               return true;
            }

            var6 = var4 + 1 & var2;
            var4 = var6;
         } while(var6 != var3);

         return false;
      }
   }

   void setBits(BitSet var1) {
      boolean var2 = this.containsZero;
      int var3 = 0;
      if (var2) {
         var1.set(0);
      }

      char[] var4 = this.table;

      for(int var5 = var4.length; var3 < var5; ++var3) {
         char var6 = var4[var3];
         if (var6 != 0) {
            var1.set(var6);
         }
      }

   }
}

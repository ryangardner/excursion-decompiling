package com.google.common.collect;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Hashing {
   private static final long C1 = -862048943L;
   private static final long C2 = 461845907L;
   private static final int MAX_TABLE_SIZE = 1073741824;

   private Hashing() {
   }

   static int closedTableSize(int var0, double var1) {
      int var3 = Math.max(var0, 2);
      var0 = Integer.highestOneBit(var3);
      if (var3 > (int)(var1 * (double)var0)) {
         var0 <<= 1;
         if (var0 <= 0) {
            var0 = 1073741824;
         }

         return var0;
      } else {
         return var0;
      }
   }

   static boolean needsResizing(int var0, int var1, double var2) {
      boolean var4;
      if ((double)var0 > var2 * (double)var1 && var1 < 1073741824) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   static int smear(int var0) {
      return (int)((long)Integer.rotateLeft((int)((long)var0 * -862048943L), 15) * 461845907L);
   }

   static int smearedHash(@NullableDecl Object var0) {
      int var1;
      if (var0 == null) {
         var1 = 0;
      } else {
         var1 = var0.hashCode();
      }

      return smear(var1);
   }
}

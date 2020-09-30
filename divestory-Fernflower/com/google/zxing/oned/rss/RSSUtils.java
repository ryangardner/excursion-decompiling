package com.google.zxing.oned.rss;

public final class RSSUtils {
   private RSSUtils() {
   }

   private static int combins(int var0, int var1) {
      int var2 = var0 - var1;
      int var3 = var2;
      int var4 = var1;
      if (var2 > var1) {
         var4 = var2;
         var3 = var1;
      }

      var1 = 1;
      byte var5 = 1;
      var2 = var0;
      var0 = var5;

      while(true) {
         int var6 = var1;
         int var7 = var0;
         if (var2 <= var4) {
            while(var7 <= var3) {
               var6 /= var7;
               ++var7;
            }

            return var6;
         }

         var6 = var1 * var2;
         var1 = var6;
         var7 = var0;
         if (var0 <= var3) {
            var1 = var6 / var0;
            var7 = var0 + 1;
         }

         --var2;
         var0 = var7;
      }
   }

   public static int getRSSvalue(int[] var0, int var1, boolean var2) {
      int[] var3 = var0;
      int var4 = var0.length;
      int var5 = var0.length;
      int var6 = 0;

      int var7;
      for(var7 = 0; var6 < var5; ++var6) {
         var7 += var3[var6];
      }

      int var8 = 0;
      int var9 = 0;
      var6 = 0;
      int var10 = var7;

      while(true) {
         int var11 = var4 - 1;
         if (var8 >= var11) {
            return var9;
         }

         int var12 = 1 << var8;
         var7 = var6 | var12;

         int var13;
         for(var13 = 1; var13 < var0[var8]; var7 &= var12) {
            int var14 = var10 - var13;
            int var15 = var4 - var8;
            int var16 = var15 - 2;
            var5 = combins(var14 - 1, var16);
            var6 = var5;
            if (var2) {
               var6 = var5;
               if (var7 == 0) {
                  int var17 = var15 - 1;
                  var6 = var5;
                  if (var14 - var17 >= var17) {
                     var6 = var5 - combins(var14 - var15, var16);
                  }
               }
            }

            if (var15 - 1 <= 1) {
               var5 = var6;
               if (var14 > var1) {
                  var5 = var6 - 1;
               }
            } else {
               var16 = var14 - var16;

               for(var5 = 0; var16 > var1; --var16) {
                  var5 += combins(var14 - var16 - 1, var15 - 3);
               }

               var5 = var6 - var5 * (var11 - var8);
            }

            var9 += var5;
            ++var13;
         }

         var10 -= var13;
         ++var8;
         var6 = var7;
      }
   }
}

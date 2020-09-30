package com.google.zxing.common;

import com.google.zxing.NotFoundException;

public abstract class GridSampler {
   private static GridSampler gridSampler = new DefaultGridSampler();

   protected static void checkAndNudgePoints(BitMatrix var0, float[] var1) throws NotFoundException {
      int var2 = var0.getWidth();
      int var3 = var0.getHeight();
      int var4 = 0;
      boolean var5 = true;

      while(true) {
         int var6;
         int var7;
         int var8;
         if (var4 < var1.length && var5) {
            var8 = (int)var1[var4];
            var6 = var4 + 1;
            var7 = (int)var1[var6];
            if (var8 >= -1 && var8 <= var2 && var7 >= -1 && var7 <= var3) {
               label79: {
                  if (var8 == -1) {
                     var1[var4] = 0.0F;
                  } else {
                     if (var8 != var2) {
                        var5 = false;
                        break label79;
                     }

                     var1[var4] = (float)(var2 - 1);
                  }

                  var5 = true;
               }

               label73: {
                  if (var7 == -1) {
                     var1[var6] = 0.0F;
                  } else {
                     if (var7 != var3) {
                        break label73;
                     }

                     var1[var6] = (float)(var3 - 1);
                  }

                  var5 = true;
               }

               var4 += 2;
               continue;
            }

            throw NotFoundException.getNotFoundInstance();
         }

         var4 = var1.length - 2;
         var5 = true;

         while(true) {
            if (var4 >= 0 && var5) {
               var8 = (int)var1[var4];
               var6 = var4 + 1;
               var7 = (int)var1[var6];
               if (var8 >= -1 && var8 <= var2 && var7 >= -1 && var7 <= var3) {
                  label56: {
                     if (var8 == -1) {
                        var1[var4] = 0.0F;
                     } else {
                        if (var8 != var2) {
                           var5 = false;
                           break label56;
                        }

                        var1[var4] = (float)(var2 - 1);
                     }

                     var5 = true;
                  }

                  label50: {
                     if (var7 == -1) {
                        var1[var6] = 0.0F;
                     } else {
                        if (var7 != var3) {
                           break label50;
                        }

                        var1[var6] = (float)(var3 - 1);
                     }

                     var5 = true;
                  }

                  var4 -= 2;
                  continue;
               }

               throw NotFoundException.getNotFoundInstance();
            }

            return;
         }
      }
   }

   public static GridSampler getInstance() {
      return gridSampler;
   }

   public static void setGridSampler(GridSampler var0) {
      gridSampler = var0;
   }

   public abstract BitMatrix sampleGrid(BitMatrix var1, int var2, int var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15, float var16, float var17, float var18, float var19) throws NotFoundException;

   public abstract BitMatrix sampleGrid(BitMatrix var1, int var2, int var3, PerspectiveTransform var4) throws NotFoundException;
}

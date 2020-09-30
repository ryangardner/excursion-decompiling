package com.google.zxing.qrcode.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class AlignmentPatternFinder {
   private final int[] crossCheckStateCount;
   private final int height;
   private final BitMatrix image;
   private final float moduleSize;
   private final List<AlignmentPattern> possibleCenters;
   private final ResultPointCallback resultPointCallback;
   private final int startX;
   private final int startY;
   private final int width;

   AlignmentPatternFinder(BitMatrix var1, int var2, int var3, int var4, int var5, float var6, ResultPointCallback var7) {
      this.image = var1;
      this.possibleCenters = new ArrayList(5);
      this.startX = var2;
      this.startY = var3;
      this.width = var4;
      this.height = var5;
      this.moduleSize = var6;
      this.crossCheckStateCount = new int[3];
      this.resultPointCallback = var7;
   }

   private static float centerFromEnd(int[] var0, int var1) {
      return (float)(var1 - var0[2]) - (float)var0[1] / 2.0F;
   }

   private float crossCheckVertical(int var1, int var2, int var3, int var4) {
      BitMatrix var5 = this.image;
      int var6 = var5.getHeight();
      int[] var7 = this.crossCheckStateCount;
      var7[0] = 0;
      var7[1] = 0;
      var7[2] = 0;

      int var10002;
      int var8;
      for(var8 = var1; var8 >= 0 && var5.get(var2, var8) && var7[1] <= var3; --var8) {
         var10002 = var7[1]++;
      }

      float var9 = Float.NaN;
      float var10 = var9;
      if (var8 >= 0) {
         if (var7[1] > var3) {
            var10 = var9;
         } else {
            while(var8 >= 0 && !var5.get(var2, var8) && var7[0] <= var3) {
               var10002 = var7[0]++;
               --var8;
            }

            if (var7[0] > var3) {
               return Float.NaN;
            }

            ++var1;

            while(var1 < var6 && var5.get(var2, var1) && var7[1] <= var3) {
               var10002 = var7[1]++;
               ++var1;
            }

            var10 = var9;
            if (var1 != var6) {
               if (var7[1] > var3) {
                  var10 = var9;
               } else {
                  while(var1 < var6 && !var5.get(var2, var1) && var7[2] <= var3) {
                     var10002 = var7[2]++;
                     ++var1;
                  }

                  if (var7[2] > var3) {
                     return Float.NaN;
                  }

                  if (Math.abs(var7[0] + var7[1] + var7[2] - var4) * 5 >= var4 * 2) {
                     return Float.NaN;
                  }

                  var10 = var9;
                  if (this.foundPatternCross(var7)) {
                     var10 = centerFromEnd(var7, var1);
                  }
               }
            }
         }
      }

      return var10;
   }

   private boolean foundPatternCross(int[] var1) {
      float var2 = this.moduleSize;
      float var3 = var2 / 2.0F;

      for(int var4 = 0; var4 < 3; ++var4) {
         if (Math.abs(var2 - (float)var1[var4]) >= var3) {
            return false;
         }
      }

      return true;
   }

   private AlignmentPattern handlePossibleCenter(int[] var1, int var2, int var3) {
      int var4 = var1[0];
      int var5 = var1[1];
      int var6 = var1[2];
      float var7 = centerFromEnd(var1, var3);
      float var8 = this.crossCheckVertical(var2, (int)var7, var1[1] * 2, var4 + var5 + var6);
      if (!Float.isNaN(var8)) {
         float var9 = (float)(var1[0] + var1[1] + var1[2]) / 3.0F;
         Iterator var11 = this.possibleCenters.iterator();

         AlignmentPattern var10;
         while(var11.hasNext()) {
            var10 = (AlignmentPattern)var11.next();
            if (var10.aboutEquals(var9, var8, var7)) {
               return var10.combineEstimate(var8, var7, var9);
            }
         }

         var10 = new AlignmentPattern(var7, var8, var9);
         this.possibleCenters.add(var10);
         ResultPointCallback var12 = this.resultPointCallback;
         if (var12 != null) {
            var12.foundPossibleResultPoint(var10);
         }
      }

      return null;
   }

   AlignmentPattern find() throws NotFoundException {
      int var1 = this.startX;
      int var2 = this.height;
      int var3 = this.width + var1;
      int var4 = this.startY;
      int var5 = var2 / 2;
      int[] var6 = new int[3];

      for(int var7 = 0; var7 < var2; ++var7) {
         int var8;
         if ((var7 & 1) == 0) {
            var8 = (var7 + 1) / 2;
         } else {
            var8 = -((var7 + 1) / 2);
         }

         int var9 = var8 + var4 + var5;
         var6[0] = 0;
         var6[1] = 0;
         var6[2] = 0;

         int var10;
         for(var10 = var1; var10 < var3 && !this.image.get(var10, var9); ++var10) {
         }

         var8 = 0;

         AlignmentPattern var12;
         for(int var11 = var10; var11 < var3; ++var11) {
            int var10002;
            if (this.image.get(var11, var9)) {
               if (var8 == 1) {
                  var10002 = var6[var8]++;
               } else if (var8 == 2) {
                  if (this.foundPatternCross(var6)) {
                     var12 = this.handlePossibleCenter(var6, var9, var11);
                     if (var12 != null) {
                        return var12;
                     }
                  }

                  var6[0] = var6[2];
                  var6[1] = 1;
                  var6[2] = 0;
                  var8 = 1;
               } else {
                  ++var8;
                  var10002 = var6[var8]++;
               }
            } else {
               var10 = var8;
               if (var8 == 1) {
                  var10 = var8 + 1;
               }

               var10002 = var6[var10]++;
               var8 = var10;
            }
         }

         if (this.foundPatternCross(var6)) {
            var12 = this.handlePossibleCenter(var6, var9, var3);
            if (var12 != null) {
               return var12;
            }
         }
      }

      if (!this.possibleCenters.isEmpty()) {
         return (AlignmentPattern)this.possibleCenters.get(0);
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }
}

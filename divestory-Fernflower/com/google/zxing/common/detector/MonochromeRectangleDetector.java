package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

public final class MonochromeRectangleDetector {
   private static final int MAX_MODULES = 32;
   private final BitMatrix image;

   public MonochromeRectangleDetector(BitMatrix var1) {
      this.image = var1;
   }

   private int[] blackWhiteRange(int var1, int var2, int var3, int var4, boolean var5) {
      int var6 = (var3 + var4) / 2;
      int var7 = var6;

      BitMatrix var8;
      int var9;
      while(var7 >= var3) {
         label92: {
            var8 = this.image;
            if (var5) {
               if (!var8.get(var7, var1)) {
                  break label92;
               }
            } else if (!var8.get(var1, var7)) {
               break label92;
            }

            --var7;
            continue;
         }

         var9 = var7;

         int var10;
         while(true) {
            var10 = var9 - 1;
            if (var10 < var3) {
               break;
            }

            var8 = this.image;
            if (var5) {
               var9 = var10;
               if (var8.get(var10, var1)) {
                  break;
               }
            } else {
               var9 = var10;
               if (var8.get(var1, var10)) {
                  break;
               }
            }
         }

         if (var10 < var3 || var7 - var10 > var2) {
            break;
         }

         var7 = var10;
      }

      var9 = var7 + 1;
      var3 = var6;

      while(var3 < var4) {
         label102: {
            var8 = this.image;
            if (var5) {
               if (var8.get(var3, var1)) {
                  break label102;
               }
            } else if (var8.get(var1, var3)) {
               break label102;
            }

            var7 = var3;

            while(true) {
               var6 = var7 + 1;
               if (var6 >= var4) {
                  break;
               }

               var8 = this.image;
               if (var5) {
                  var7 = var6;
                  if (var8.get(var6, var1)) {
                     break;
                  }
               } else {
                  var7 = var6;
                  if (var8.get(var1, var6)) {
                     break;
                  }
               }
            }

            if (var6 >= var4 || var6 - var3 > var2) {
               break;
            }

            var3 = var6;
            continue;
         }

         ++var3;
      }

      var1 = var3 - 1;
      int[] var11;
      if (var1 > var9) {
         var11 = new int[]{var9, var1};
      } else {
         var11 = null;
      }

      return var11;
   }

   private ResultPoint findCornerFromCenter(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) throws NotFoundException {
      int[] var10 = null;
      int var11 = var1;

      int[] var13;
      for(int var12 = var5; var12 < var8 && var12 >= var7 && var11 < var4 && var11 >= var3; var10 = var13) {
         if (var2 == 0) {
            var13 = this.blackWhiteRange(var12, var9, var3, var4, true);
         } else {
            var13 = this.blackWhiteRange(var11, var9, var7, var8, false);
         }

         if (var13 == null) {
            if (var10 != null) {
               if (var2 == 0) {
                  var2 = var12 - var6;
                  if (var10[0] < var1) {
                     if (var10[1] > var1) {
                        if (var6 > 0) {
                           var1 = var10[0];
                        } else {
                           var1 = var10[1];
                        }

                        return new ResultPoint((float)var1, (float)var2);
                     }

                     return new ResultPoint((float)var10[0], (float)var2);
                  }

                  return new ResultPoint((float)var10[1], (float)var2);
               }

               var1 = var11 - var2;
               if (var10[0] < var5) {
                  if (var10[1] > var5) {
                     float var14 = (float)var1;
                     if (var2 < 0) {
                        var1 = var10[0];
                     } else {
                        var1 = var10[1];
                     }

                     return new ResultPoint(var14, (float)var1);
                  }

                  return new ResultPoint((float)var1, (float)var10[0]);
               }

               return new ResultPoint((float)var1, (float)var10[1]);
            }

            throw NotFoundException.getNotFoundInstance();
         }

         var12 += var6;
         var11 += var2;
      }

      throw NotFoundException.getNotFoundInstance();
   }

   public ResultPoint[] detect() throws NotFoundException {
      int var1 = this.image.getHeight();
      int var2 = this.image.getWidth();
      int var3 = var1 / 2;
      int var4 = var2 / 2;
      int var5 = Math.max(1, var1 / 256);
      int var6 = Math.max(1, var2 / 256);
      int var7 = -var5;
      int var8 = var4 / 2;
      int var9 = (int)this.findCornerFromCenter(var4, 0, 0, var2, var3, var7, 0, var1, var8).getY() - 1;
      int var10 = -var6;
      int var11 = var3 / 2;
      ResultPoint var12 = this.findCornerFromCenter(var4, var10, 0, var2, var3, 0, var9, var1, var11);
      var10 = (int)var12.getX() - 1;
      ResultPoint var13 = this.findCornerFromCenter(var4, var6, var10, var2, var3, 0, var9, var1, var11);
      var11 = (int)var13.getX() + 1;
      ResultPoint var14 = this.findCornerFromCenter(var4, 0, var10, var11, var3, var5, var9, var1, var8);
      return new ResultPoint[]{this.findCornerFromCenter(var4, 0, var10, var11, var3, var7, var9, (int)var14.getY() + 1, var4 / 4), var12, var13, var14};
   }
}

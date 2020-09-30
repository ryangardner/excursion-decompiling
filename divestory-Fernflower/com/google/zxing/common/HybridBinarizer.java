package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;

public final class HybridBinarizer extends GlobalHistogramBinarizer {
   private static final int BLOCK_SIZE = 8;
   private static final int BLOCK_SIZE_MASK = 7;
   private static final int BLOCK_SIZE_POWER = 3;
   private static final int MINIMUM_DIMENSION = 40;
   private static final int MIN_DYNAMIC_RANGE = 24;
   private BitMatrix matrix;

   public HybridBinarizer(LuminanceSource var1) {
      super(var1);
   }

   private static int[][] calculateBlackPoints(byte[] var0, int var1, int var2, int var3, int var4) {
      int[][] var5 = new int[var2][var1];

      for(int var6 = 0; var6 < var2; ++var6) {
         int var7 = var6 << 3;
         int var8 = var4 - 8;
         int var9 = var7;
         if (var7 > var8) {
            var9 = var8;
         }

         for(int var10 = 0; var10 < var1; ++var10) {
            var8 = var10 << 3;
            int var11 = var3 - 8;
            var7 = var8;
            if (var8 > var11) {
               var7 = var11;
            }

            var7 += var9 * var3;
            var8 = 0;
            var11 = 0;
            int var12 = 0;

            int var13;
            int var16;
            for(var13 = 255; var8 < 8; var11 = var16) {
               int var14;
               int var15;
               for(var14 = 0; var14 < 8; var13 = var16) {
                  var15 = var0[var7 + var14] & 255;
                  var11 += var15;
                  var16 = var13;
                  if (var15 < var13) {
                     var16 = var15;
                  }

                  var13 = var12;
                  if (var15 > var12) {
                     var13 = var15;
                  }

                  ++var14;
                  var12 = var13;
               }

               var14 = var7;
               var15 = var8;
               var16 = var11;
               if (var12 - var13 > 24) {
                  var15 = var8;
                  int var17 = var7;

                  while(true) {
                     var7 = var15 + 1;
                     var8 = var17 + var3;
                     var14 = var8;
                     var15 = var7;
                     var16 = var11;
                     if (var7 >= 8) {
                        break;
                     }

                     var16 = 0;
                     var14 = var11;

                     while(true) {
                        var17 = var8;
                        var15 = var7;
                        var11 = var14;
                        if (var16 >= 8) {
                           break;
                        }

                        var14 += var0[var8 + var16] & 255;
                        ++var16;
                     }
                  }
               }

               var8 = var15 + 1;
               var7 = var14 + var3;
            }

            var7 = var11 >> 6;
            if (var12 - var13 <= 24) {
               var8 = var13 / 2;
               var7 = var8;
               if (var6 > 0) {
                  var7 = var8;
                  if (var10 > 0) {
                     var11 = var6 - 1;
                     var16 = var5[var11][var10];
                     int[] var18 = var5[var6];
                     var7 = var10 - 1;
                     var11 = (var16 + var18[var7] * 2 + var5[var11][var7]) / 4;
                     var7 = var8;
                     if (var13 < var11) {
                        var7 = var11;
                     }
                  }
               }
            }

            var5[var6][var10] = var7;
         }
      }

      return var5;
   }

   private static void calculateThresholdForBlock(byte[] var0, int var1, int var2, int var3, int var4, int[][] var5, BitMatrix var6) {
      for(int var7 = 0; var7 < var2; ++var7) {
         int var8 = var7 << 3;
         int var9 = var4 - 8;
         int var10 = var8;
         if (var8 > var9) {
            var10 = var9;
         }

         for(var9 = 0; var9 < var1; ++var9) {
            int var11 = var9 << 3;
            var8 = var3 - 8;
            if (var11 <= var8) {
               var8 = var11;
            }

            int var12 = cap(var9, 2, var1 - 3);
            int var13 = cap(var7, 2, var2 - 3);
            var11 = -2;

            int var14;
            for(var14 = 0; var11 <= 2; ++var11) {
               int[] var15 = var5[var13 + var11];
               var14 += var15[var12 - 2] + var15[var12 - 1] + var15[var12] + var15[var12 + 1] + var15[var12 + 2];
            }

            thresholdBlock(var0, var8, var10, var14 / 25, var3, var6);
         }
      }

   }

   private static int cap(int var0, int var1, int var2) {
      if (var0 >= var1) {
         var1 = var0;
         if (var0 > var2) {
            var1 = var2;
         }
      }

      return var1;
   }

   private static void thresholdBlock(byte[] var0, int var1, int var2, int var3, int var4, BitMatrix var5) {
      int var6 = var2 * var4 + var1;

      for(int var7 = 0; var7 < 8; var6 += var4) {
         for(int var8 = 0; var8 < 8; ++var8) {
            if ((var0[var6 + var8] & 255) <= var3) {
               var5.set(var1 + var8, var2 + var7);
            }
         }

         ++var7;
      }

   }

   public Binarizer createBinarizer(LuminanceSource var1) {
      return new HybridBinarizer(var1);
   }

   public BitMatrix getBlackMatrix() throws NotFoundException {
      BitMatrix var1 = this.matrix;
      if (var1 != null) {
         return var1;
      } else {
         LuminanceSource var9 = this.getLuminanceSource();
         int var2 = var9.getWidth();
         int var3 = var9.getHeight();
         if (var2 >= 40 && var3 >= 40) {
            byte[] var4 = var9.getMatrix();
            int var5 = var2 >> 3;
            int var6 = var5;
            if ((var2 & 7) != 0) {
               var6 = var5 + 1;
            }

            int var7 = var3 >> 3;
            var5 = var7;
            if ((var3 & 7) != 0) {
               var5 = var7 + 1;
            }

            int[][] var10 = calculateBlackPoints(var4, var6, var5, var2, var3);
            BitMatrix var8 = new BitMatrix(var2, var3);
            calculateThresholdForBlock(var4, var6, var5, var2, var3, var10, var8);
            this.matrix = var8;
         } else {
            this.matrix = super.getBlackMatrix();
         }

         return this.matrix;
      }
   }
}

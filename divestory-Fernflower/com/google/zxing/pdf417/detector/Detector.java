package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Detector {
   private static final int BARCODE_MIN_HEIGHT = 10;
   private static final int[] INDEXES_START_PATTERN = new int[]{0, 4, 1, 5};
   private static final int[] INDEXES_STOP_PATTERN = new int[]{6, 2, 7, 3};
   private static final float MAX_AVG_VARIANCE = 0.42F;
   private static final float MAX_INDIVIDUAL_VARIANCE = 0.8F;
   private static final int MAX_PATTERN_DRIFT = 5;
   private static final int MAX_PIXEL_DRIFT = 3;
   private static final int ROW_STEP = 5;
   private static final int SKIPPED_ROW_COUNT_MAX = 25;
   private static final int[] START_PATTERN = new int[]{8, 1, 1, 1, 1, 1, 1, 3};
   private static final int[] STOP_PATTERN = new int[]{7, 1, 1, 3, 1, 1, 1, 2, 1};

   private Detector() {
   }

   private static void copyToResult(ResultPoint[] var0, ResultPoint[] var1, int[] var2) {
      for(int var3 = 0; var3 < var2.length; ++var3) {
         var0[var2[var3]] = var1[var3];
      }

   }

   public static PDF417DetectorResult detect(BinaryBitmap var0, Map<DecodeHintType, ?> var1, boolean var2) throws NotFoundException {
      BitMatrix var3 = var0.getBlackMatrix();
      List var4 = detect(var2, var3);
      BitMatrix var6 = var3;
      List var5 = var4;
      if (var4.isEmpty()) {
         var6 = var3.clone();
         var6.rotate180();
         var5 = detect(var2, var6);
      }

      return new PDF417DetectorResult(var6, var5);
   }

   private static List<ResultPoint[]> detect(boolean var0, BitMatrix var1) {
      ArrayList var2 = new ArrayList();
      int var3 = 0;

      label48:
      while(true) {
         int var4 = 0;

         int var9;
         for(boolean var5 = false; var3 < var1.getHeight(); var3 = var9) {
            ResultPoint[] var6 = findVertices(var1, var3, var4);
            if (var6[0] == null && var6[3] == null) {
               if (!var5) {
                  return var2;
               }

               Iterator var7 = var2.iterator();

               while(var7.hasNext()) {
                  var6 = (ResultPoint[])var7.next();
                  int var10 = var3;
                  if (var6[1] != null) {
                     var10 = (int)Math.max((float)var3, var6[1].getY());
                  }

                  var3 = var10;
                  if (var6[3] != null) {
                     var3 = Math.max(var10, (int)var6[3].getY());
                  }
               }

               var3 += 5;
               continue label48;
            }

            var2.add(var6);
            if (!var0) {
               return var2;
            }

            float var8;
            if (var6[2] != null) {
               var3 = (int)var6[2].getX();
               var8 = var6[2].getY();
            } else {
               var3 = (int)var6[4].getX();
               var8 = var6[4].getY();
            }

            var9 = (int)var8;
            var4 = var3;
            var5 = true;
         }

         return var2;
      }
   }

   private static int[] findGuardPattern(BitMatrix var0, int var1, int var2, int var3, boolean var4, int[] var5, int[] var6) {
      Arrays.fill(var6, 0, var6.length, 0);
      int var7 = var5.length;

      int var8;
      for(var8 = 0; var0.get(var1, var2) && var1 > 0 && var8 < 3; ++var8) {
         --var1;
      }

      byte var9 = 0;
      var8 = var1;
      int var10 = var1;

      for(var1 = var9; var10 < var3; ++var10) {
         if (var0.get(var10, var2) ^ var4) {
            int var10002 = var6[var1]++;
         } else {
            int var12 = var7 - 1;
            if (var1 == var12) {
               if (patternMatchVariance(var6, var5, 0.8F) < 0.42F) {
                  return new int[]{var8, var10};
               }

               var8 += var6[0] + var6[1];
               int var11 = var7 - 2;
               System.arraycopy(var6, 2, var6, 0, var11);
               var6[var11] = 0;
               var6[var12] = 0;
               --var1;
            } else {
               ++var1;
            }

            var6[var1] = 1;
            var4 ^= true;
         }
      }

      if (var1 == var7 - 1 && patternMatchVariance(var6, var5, 0.8F) < 0.42F) {
         return new int[]{var8, var10 - 1};
      } else {
         return null;
      }
   }

   private static ResultPoint[] findRowsWithPattern(BitMatrix var0, int var1, int var2, int var3, int var4, int[] var5) {
      ResultPoint[] var6 = new ResultPoint[4];
      int[] var7 = new int[var5.length];

      byte var8;
      int[] var9;
      int[] var10;
      float var11;
      float var12;
      boolean var16;
      while(true) {
         var8 = 0;
         if (var3 >= var1) {
            boolean var13 = false;
            var4 = var3;
            var16 = var13;
            break;
         }

         var9 = findGuardPattern(var0, var4, var3, var2, false, var5, var7);
         if (var9 != null) {
            while(var3 > 0) {
               --var3;
               var10 = findGuardPattern(var0, var4, var3, var2, false, var5, var7);
               if (var10 == null) {
                  ++var3;
                  break;
               }

               var9 = var10;
            }

            var11 = (float)var9[0];
            var12 = (float)var3;
            var6[0] = new ResultPoint(var11, var12);
            var6[1] = new ResultPoint((float)var9[1], var12);
            var4 = var3;
            var16 = true;
            break;
         }

         var3 += 5;
      }

      int var17 = var4 + 1;
      int var14 = var17;
      if (var16) {
         var9 = new int[]{(int)var6[0].getX(), (int)var6[1].getX()};

         for(var3 = 0; var17 < var1; ++var17) {
            int var15 = var9[0];
            var10 = findGuardPattern(var0, var15, var17, var2, false, var5, var7);
            if (var10 != null && Math.abs(var9[0] - var10[0]) < 5 && Math.abs(var9[1] - var10[1]) < 5) {
               var9 = var10;
               var3 = 0;
            } else {
               if (var3 > 25) {
                  break;
               }

               ++var3;
            }
         }

         var14 = var17 - (var3 + 1);
         var12 = (float)var9[0];
         var11 = (float)var14;
         var6[2] = new ResultPoint(var12, var11);
         var6[3] = new ResultPoint((float)var9[1], var11);
      }

      if (var14 - var4 < 10) {
         for(var1 = var8; var1 < 4; ++var1) {
            var6[var1] = null;
         }
      }

      return var6;
   }

   private static ResultPoint[] findVertices(BitMatrix var0, int var1, int var2) {
      int var3 = var0.getHeight();
      int var4 = var0.getWidth();
      ResultPoint[] var5 = new ResultPoint[8];
      copyToResult(var5, findRowsWithPattern(var0, var3, var4, var1, var2, START_PATTERN), INDEXES_START_PATTERN);
      if (var5[4] != null) {
         var2 = (int)var5[4].getX();
         var1 = (int)var5[4].getY();
      }

      copyToResult(var5, findRowsWithPattern(var0, var3, var4, var1, var2, STOP_PATTERN), INDEXES_STOP_PATTERN);
      return var5;
   }

   private static float patternMatchVariance(int[] var0, int[] var1, float var2) {
      int var3 = var0.length;
      byte var4 = 0;
      int var5 = 0;
      int var6 = 0;

      int var7;
      for(var7 = 0; var5 < var3; ++var5) {
         var6 += var0[var5];
         var7 += var1[var5];
      }

      if (var6 < var7) {
         return Float.POSITIVE_INFINITY;
      } else {
         float var8 = (float)var6;
         float var9 = var8 / (float)var7;
         float var10 = 0.0F;

         for(var7 = var4; var7 < var3; ++var7) {
            var6 = var0[var7];
            float var11 = (float)var1[var7] * var9;
            float var12 = (float)var6;
            if (var12 > var11) {
               var11 = var12 - var11;
            } else {
               var11 -= var12;
            }

            if (var11 > var2 * var9) {
               return Float.POSITIVE_INFINITY;
            }

            var10 += var11;
         }

         return var10 / var8;
      }
   }
}

package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Map;

public final class ITFReader extends OneDReader {
   private static final int[] DEFAULT_ALLOWED_LENGTHS = new int[]{6, 8, 10, 12, 14};
   private static final int[] END_PATTERN_REVERSED = new int[]{1, 1, 3};
   private static final float MAX_AVG_VARIANCE = 0.38F;
   private static final float MAX_INDIVIDUAL_VARIANCE = 0.78F;
   private static final int N = 1;
   static final int[][] PATTERNS;
   private static final int[] START_PATTERN = new int[]{1, 1, 1, 1};
   private static final int W = 3;
   private int narrowLineWidth = -1;

   static {
      int[] var0 = new int[]{1, 1, 3, 3, 1};
      int[] var1 = new int[]{3, 3, 1, 1, 1};
      int[] var2 = new int[]{1, 1, 3, 1, 3};
      int[] var3 = new int[]{3, 1, 3, 1, 1};
      int[] var4 = new int[]{1, 3, 3, 1, 1};
      int[] var5 = new int[]{3, 1, 1, 3, 1};
      PATTERNS = new int[][]{var0, {3, 1, 1, 1, 3}, {1, 3, 1, 1, 3}, var1, var2, var3, var4, {1, 1, 1, 3, 3}, var5, {1, 3, 1, 3, 1}};
   }

   private static int decodeDigit(int[] var0) throws NotFoundException {
      int var1 = PATTERNS.length;
      float var2 = 0.38F;
      int var3 = -1;

      float var6;
      for(int var4 = 0; var4 < var1; var2 = var6) {
         float var5 = patternMatchVariance(var0, PATTERNS[var4], 0.78F);
         var6 = var2;
         if (var5 < var2) {
            var3 = var4;
            var6 = var5;
         }

         ++var4;
      }

      if (var3 >= 0) {
         return var3;
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   private static void decodeMiddle(BitArray var0, int var1, int var2, StringBuilder var3) throws NotFoundException {
      int[] var4 = new int[10];
      int[] var5 = new int[5];
      int[] var6 = new int[5];

      while(var1 < var2) {
         recordPattern(var0, var1, var4);
         byte var7 = 0;

         int var8;
         int var9;
         for(var8 = 0; var8 < 5; ++var8) {
            var9 = var8 * 2;
            var5[var8] = var4[var9];
            var6[var8] = var4[var9 + 1];
         }

         var3.append((char)(decodeDigit(var5) + 48));
         var3.append((char)(decodeDigit(var6) + 48));
         var9 = var1;
         var8 = var7;

         while(true) {
            var1 = var9;
            if (var8 >= 10) {
               break;
            }

            var9 += var4[var8];
            ++var8;
         }
      }

   }

   private static int[] findGuardPattern(BitArray var0, int var1, int[] var2) throws NotFoundException {
      int var3 = var2.length;
      int[] var4 = new int[var3];
      int var5 = var0.getSize();
      boolean var7 = false;
      int var8 = 0;
      int var9 = var1;

      for(var1 = var1; var9 < var5; ++var9) {
         if (var0.get(var9) ^ var7) {
            int var10002 = var4[var8]++;
         } else {
            int var6 = var3 - 1;
            if (var8 == var6) {
               if (patternMatchVariance(var4, var2, 0.78F) < 0.38F) {
                  return new int[]{var1, var9};
               }

               var1 += var4[0] + var4[1];
               int var10 = var3 - 2;
               System.arraycopy(var4, 2, var4, 0, var10);
               var4[var10] = 0;
               var4[var6] = 0;
               --var8;
            } else {
               ++var8;
            }

            var4[var8] = 1;
            var7 ^= true;
         }
      }

      throw NotFoundException.getNotFoundInstance();
   }

   private static int skipWhiteSpace(BitArray var0) throws NotFoundException {
      int var1 = var0.getSize();
      int var2 = var0.getNextSet(0);
      if (var2 != var1) {
         return var2;
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   private void validateQuietZone(BitArray var1, int var2) throws NotFoundException {
      int var3 = this.narrowLineWidth * 10;
      if (var3 >= var2) {
         var3 = var2;
      }

      --var2;

      while(var3 > 0 && var2 >= 0 && !var1.get(var2)) {
         --var3;
         --var2;
      }

      if (var3 != 0) {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   int[] decodeEnd(BitArray var1) throws NotFoundException {
      var1.reverse();

      int[] var2;
      label68: {
         Throwable var10000;
         label72: {
            boolean var10001;
            try {
               var2 = findGuardPattern(var1, skipWhiteSpace(var1), END_PATTERN_REVERSED);
               this.validateQuietZone(var1, var2[0]);
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label72;
            }

            int var3 = var2[0];

            label63:
            try {
               var2[0] = var1.getSize() - var2[1];
               var2[1] = var1.getSize() - var3;
               break label68;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label63;
            }
         }

         Throwable var10 = var10000;
         var1.reverse();
         throw var10;
      }

      var1.reverse();
      return var2;
   }

   public Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws FormatException, NotFoundException {
      int[] var4 = this.decodeStart(var2);
      int[] var5 = this.decodeEnd(var2);
      StringBuilder var6 = new StringBuilder(20);
      decodeMiddle(var2, var4[1], var5[0], var6);
      String var20 = var6.toString();
      int[] var15;
      if (var3 != null) {
         var15 = (int[])var3.get(DecodeHintType.ALLOWED_LENGTHS);
      } else {
         var15 = null;
      }

      int[] var16 = var15;
      if (var15 == null) {
         var16 = DEFAULT_ALLOWED_LENGTHS;
      }

      int var7 = var20.length();
      int var8 = var16.length;
      int var9 = 0;
      int var10 = 0;

      boolean var22;
      while(true) {
         if (var9 >= var8) {
            var22 = false;
            break;
         }

         int var11 = var16[var9];
         if (var7 == var11) {
            var22 = true;
            break;
         }

         int var12 = var10;
         if (var11 > var10) {
            var12 = var11;
         }

         ++var9;
         var10 = var12;
      }

      boolean var21 = var22;
      if (!var22) {
         var21 = var22;
         if (var7 > var10) {
            var21 = true;
         }
      }

      if (var21) {
         float var13 = (float)var4[1];
         float var14 = (float)var1;
         ResultPoint var17 = new ResultPoint(var13, var14);
         ResultPoint var19 = new ResultPoint((float)var5[0], var14);
         BarcodeFormat var18 = BarcodeFormat.ITF;
         return new Result(var20, (byte[])null, new ResultPoint[]{var17, var19}, var18);
      } else {
         throw FormatException.getFormatInstance();
      }
   }

   int[] decodeStart(BitArray var1) throws NotFoundException {
      int[] var2 = findGuardPattern(var1, skipWhiteSpace(var1), START_PATTERN);
      this.narrowLineWidth = (var2[1] - var2[0]) / 4;
      this.validateQuietZone(var1, var2[0]);
      return var2;
   }
}

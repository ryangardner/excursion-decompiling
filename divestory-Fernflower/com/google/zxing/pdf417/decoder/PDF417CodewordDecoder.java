package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;

final class PDF417CodewordDecoder {
   private static final float[][] RATIOS_TABLE;

   static {
      RATIOS_TABLE = new float[PDF417Common.SYMBOL_TABLE.length][8];

      for(int var0 = 0; var0 < PDF417Common.SYMBOL_TABLE.length; ++var0) {
         int var1 = PDF417Common.SYMBOL_TABLE[var0];
         int var2 = var1 & 1;

         int var5;
         for(int var3 = 0; var3 < 8; var2 = var5) {
            float var4 = 0.0F;

            while(true) {
               var5 = var1 & 1;
               if (var5 != var2) {
                  RATIOS_TABLE[var0][8 - var3 - 1] = var4 / 17.0F;
                  ++var3;
                  break;
               }

               ++var4;
               var1 >>= 1;
            }
         }
      }

   }

   private PDF417CodewordDecoder() {
   }

   private static int getBitValue(int[] var0) {
      long var1 = 0L;

      for(int var3 = 0; var3 < var0.length; ++var3) {
         for(int var4 = 0; var4 < var0[var3]; ++var4) {
            byte var5 = 1;
            if (var3 % 2 != 0) {
               var5 = 0;
            }

            var1 = var1 << 1 | (long)var5;
         }
      }

      return (int)var1;
   }

   private static int getClosestDecodedValue(int[] var0) {
      int var1 = PDF417Common.getBitCountSum(var0);
      float[] var2 = new float[8];

      int var3;
      for(var3 = 0; var3 < 8; ++var3) {
         var2[var3] = (float)var0[var3] / (float)var1;
      }

      float var4 = Float.MAX_VALUE;
      var1 = -1;
      var3 = 0;

      while(true) {
         float[][] var8 = RATIOS_TABLE;
         if (var3 >= var8.length) {
            return var1;
         }

         float var5 = 0.0F;
         float[] var9 = var8[var3];
         int var6 = 0;

         float var7;
         while(true) {
            var7 = var5;
            if (var6 >= 8) {
               break;
            }

            var7 = var9[var6] - var2[var6];
            var7 = var5 + var7 * var7;
            if (var7 >= var4) {
               break;
            }

            ++var6;
            var5 = var7;
         }

         var5 = var4;
         if (var7 < var4) {
            var1 = PDF417Common.SYMBOL_TABLE[var3];
            var5 = var7;
         }

         ++var3;
         var4 = var5;
      }
   }

   private static int getDecodedCodewordValue(int[] var0) {
      int var1 = getBitValue(var0);
      int var2 = var1;
      if (PDF417Common.getCodeword(var1) == -1) {
         var2 = -1;
      }

      return var2;
   }

   static int getDecodedValue(int[] var0) {
      int var1 = getDecodedCodewordValue(sampleBitCounts(var0));
      return var1 != -1 ? var1 : getClosestDecodedValue(var0);
   }

   private static int[] sampleBitCounts(int[] var0) {
      float var1 = (float)PDF417Common.getBitCountSum(var0);
      int[] var2 = new int[8];
      int var3 = 0;
      int var4 = 0;

      int var9;
      for(int var5 = 0; var3 < 17; var5 = var9) {
         float var6 = var1 / 34.0F;
         float var7 = (float)var3 * var1 / 17.0F;
         int var8 = var4;
         var9 = var5;
         if ((float)(var0[var5] + var4) <= var6 + var7) {
            var8 = var4 + var0[var5];
            var9 = var5 + 1;
         }

         int var10002 = var2[var9]++;
         ++var3;
         var4 = var8;
      }

      return var2;
   }
}

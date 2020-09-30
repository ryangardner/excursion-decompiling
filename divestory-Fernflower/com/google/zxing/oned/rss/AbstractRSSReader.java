package com.google.zxing.oned.rss;

import com.google.zxing.NotFoundException;
import com.google.zxing.oned.OneDReader;

public abstract class AbstractRSSReader extends OneDReader {
   private static final float MAX_AVG_VARIANCE = 0.2F;
   private static final float MAX_FINDER_PATTERN_RATIO = 0.89285713F;
   private static final float MAX_INDIVIDUAL_VARIANCE = 0.45F;
   private static final float MIN_FINDER_PATTERN_RATIO = 0.7916667F;
   private final int[] dataCharacterCounters;
   private final int[] decodeFinderCounters = new int[4];
   private final int[] evenCounts;
   private final float[] evenRoundingErrors;
   private final int[] oddCounts;
   private final float[] oddRoundingErrors;

   protected AbstractRSSReader() {
      int[] var1 = new int[8];
      this.dataCharacterCounters = var1;
      this.oddRoundingErrors = new float[4];
      this.evenRoundingErrors = new float[4];
      this.oddCounts = new int[var1.length / 2];
      this.evenCounts = new int[var1.length / 2];
   }

   protected static int count(int[] var0) {
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 += var0[var2];
      }

      return var3;
   }

   protected static void decrement(int[] var0, float[] var1) {
      int var2 = 0;
      float var3 = var1[0];

      float var5;
      for(int var4 = 1; var4 < var0.length; var3 = var5) {
         var5 = var3;
         if (var1[var4] < var3) {
            var5 = var1[var4];
            var2 = var4;
         }

         ++var4;
      }

      int var10002 = var0[var2]--;
   }

   protected static void increment(int[] var0, float[] var1) {
      int var2 = 0;
      float var3 = var1[0];

      float var5;
      for(int var4 = 1; var4 < var0.length; var3 = var5) {
         var5 = var3;
         if (var1[var4] > var3) {
            var5 = var1[var4];
            var2 = var4;
         }

         ++var4;
      }

      int var10002 = var0[var2]++;
   }

   protected static boolean isFinderPattern(int[] var0) {
      boolean var1 = false;
      int var2 = var0[0] + var0[1];
      int var3 = var0[2];
      int var4 = var0[3];
      float var5 = (float)var2 / (float)(var3 + var2 + var4);
      boolean var6 = var1;
      if (var5 >= 0.7916667F) {
         var6 = var1;
         if (var5 <= 0.89285713F) {
            var2 = Integer.MAX_VALUE;
            int var7 = Integer.MIN_VALUE;
            int var8 = var0.length;

            for(var4 = 0; var4 < var8; var7 = var3) {
               int var9 = var0[var4];
               var3 = var7;
               if (var9 > var7) {
                  var3 = var9;
               }

               var7 = var2;
               if (var9 < var2) {
                  var7 = var9;
               }

               ++var4;
               var2 = var7;
            }

            var6 = var1;
            if (var7 < var2 * 10) {
               var6 = true;
            }
         }
      }

      return var6;
   }

   protected static int parseFinderValue(int[] var0, int[][] var1) throws NotFoundException {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (patternMatchVariance(var0, var1[var2], 0.45F) < 0.2F) {
            return var2;
         }
      }

      throw NotFoundException.getNotFoundInstance();
   }

   protected final int[] getDataCharacterCounters() {
      return this.dataCharacterCounters;
   }

   protected final int[] getDecodeFinderCounters() {
      return this.decodeFinderCounters;
   }

   protected final int[] getEvenCounts() {
      return this.evenCounts;
   }

   protected final float[] getEvenRoundingErrors() {
      return this.evenRoundingErrors;
   }

   protected final int[] getOddCounts() {
      return this.oddCounts;
   }

   protected final float[] getOddRoundingErrors() {
      return this.oddRoundingErrors;
   }
}

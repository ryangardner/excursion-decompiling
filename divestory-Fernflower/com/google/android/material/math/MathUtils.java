package com.google.android.material.math;

public final class MathUtils {
   public static final float DEFAULT_EPSILON = 1.0E-4F;

   private MathUtils() {
   }

   public static float dist(float var0, float var1, float var2, float var3) {
      return (float)Math.hypot((double)(var2 - var0), (double)(var3 - var1));
   }

   public static float distanceToFurthestCorner(float var0, float var1, float var2, float var3, float var4, float var5) {
      return max(dist(var0, var1, var2, var3), dist(var0, var1, var4, var3), dist(var0, var1, var4, var5), dist(var0, var1, var2, var5));
   }

   public static float floorMod(float var0, int var1) {
      float var2 = (float)var1;
      int var3 = (int)(var0 / var2);
      int var4 = var3;
      if (Math.signum(var0) * var2 < 0.0F) {
         var4 = var3;
         if ((float)(var3 * var1) != var0) {
            var4 = var3 - 1;
         }
      }

      return var0 - (float)(var4 * var1);
   }

   public static int floorMod(int var0, int var1) {
      int var2 = var0 / var1;
      int var3 = var2;
      if ((var0 ^ var1) < 0) {
         var3 = var2;
         if (var2 * var1 != var0) {
            var3 = var2 - 1;
         }
      }

      return var0 - var3 * var1;
   }

   public static boolean geq(float var0, float var1, float var2) {
      boolean var3;
      if (var0 + var2 >= var1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static float lerp(float var0, float var1, float var2) {
      return (1.0F - var2) * var0 + var2 * var1;
   }

   private static float max(float var0, float var1, float var2, float var3) {
      if (var0 <= var1 || var0 <= var2 || var0 <= var3) {
         if (var1 > var2 && var1 > var3) {
            var0 = var1;
         } else if (var2 > var3) {
            var0 = var2;
         } else {
            var0 = var3;
         }
      }

      return var0;
   }
}

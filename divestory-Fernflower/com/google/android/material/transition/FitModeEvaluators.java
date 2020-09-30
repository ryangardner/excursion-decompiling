package com.google.android.material.transition;

import android.graphics.RectF;

class FitModeEvaluators {
   private static final FitModeEvaluator HEIGHT = new FitModeEvaluator() {
      public void applyMask(RectF var1, float var2, FitModeResult var3) {
         float var4 = Math.abs(var3.currentEndWidth - var3.currentStartWidth);
         float var5 = var1.left;
         var2 = var4 / 2.0F * var2;
         var1.left = var5 + var2;
         var1.right -= var2;
      }

      public FitModeResult evaluate(float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         var1 = TransitionUtils.lerp(var5, var7, var2, var3, var1);
         var2 = var1 / var5;
         var3 = var1 / var7;
         return new FitModeResult(var2, var3, var4 * var2, var1, var6 * var3, var1);
      }

      public boolean shouldMaskStartBounds(FitModeResult var1) {
         boolean var2;
         if (var1.currentStartWidth > var1.currentEndWidth) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   };
   private static final FitModeEvaluator WIDTH = new FitModeEvaluator() {
      public void applyMask(RectF var1, float var2, FitModeResult var3) {
         float var4 = Math.abs(var3.currentEndHeight - var3.currentStartHeight);
         var1.bottom -= var4 * var2;
      }

      public FitModeResult evaluate(float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         var1 = TransitionUtils.lerp(var4, var6, var2, var3, var1);
         var2 = var1 / var4;
         var3 = var1 / var6;
         return new FitModeResult(var2, var3, var1, var5 * var2, var1, var7 * var3);
      }

      public boolean shouldMaskStartBounds(FitModeResult var1) {
         boolean var2;
         if (var1.currentStartHeight > var1.currentEndHeight) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   };

   private FitModeEvaluators() {
   }

   static FitModeEvaluator get(int var0, boolean var1, RectF var2, RectF var3) {
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 == 2) {
               return HEIGHT;
            } else {
               StringBuilder var5 = new StringBuilder();
               var5.append("Invalid fit mode: ");
               var5.append(var0);
               throw new IllegalArgumentException(var5.toString());
            }
         } else {
            return WIDTH;
         }
      } else {
         FitModeEvaluator var4;
         if (shouldAutoFitToWidth(var1, var2, var3)) {
            var4 = WIDTH;
         } else {
            var4 = HEIGHT;
         }

         return var4;
      }
   }

   private static boolean shouldAutoFitToWidth(boolean var0, RectF var1, RectF var2) {
      float var3 = var1.width();
      float var4 = var1.height();
      float var5 = var2.width();
      float var6 = var2.height();
      float var7 = var6 * var3 / var5;
      var5 = var5 * var4 / var3;
      boolean var8 = true;
      if (var0) {
         if (var7 >= var4) {
            var0 = var8;
            return var0;
         }
      } else if (var5 >= var6) {
         var0 = var8;
         return var0;
      }

      var0 = false;
      return var0;
   }
}

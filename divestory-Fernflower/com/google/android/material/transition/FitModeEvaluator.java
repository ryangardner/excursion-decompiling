package com.google.android.material.transition;

import android.graphics.RectF;

interface FitModeEvaluator {
   void applyMask(RectF var1, float var2, FitModeResult var3);

   FitModeResult evaluate(float var1, float var2, float var3, float var4, float var5, float var6, float var7);

   boolean shouldMaskStartBounds(FitModeResult var1);
}

package com.google.android.material.transition;

class FitModeResult {
   final float currentEndHeight;
   final float currentEndWidth;
   final float currentStartHeight;
   final float currentStartWidth;
   final float endScale;
   final float startScale;

   FitModeResult(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.startScale = var1;
      this.endScale = var2;
      this.currentStartWidth = var3;
      this.currentStartHeight = var4;
      this.currentEndWidth = var5;
      this.currentEndHeight = var6;
   }
}

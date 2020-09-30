package com.google.android.material.transition;

class FadeModeResult {
   final int endAlpha;
   final boolean endOnTop;
   final int startAlpha;

   private FadeModeResult(int var1, int var2, boolean var3) {
      this.startAlpha = var1;
      this.endAlpha = var2;
      this.endOnTop = var3;
   }

   static FadeModeResult endOnTop(int var0, int var1) {
      return new FadeModeResult(var0, var1, true);
   }

   static FadeModeResult startOnTop(int var0, int var1) {
      return new FadeModeResult(var0, var1, false);
   }
}

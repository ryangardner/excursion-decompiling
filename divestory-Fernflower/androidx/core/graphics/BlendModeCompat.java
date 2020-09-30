package androidx.core.graphics;

public enum BlendModeCompat {
   CLEAR,
   COLOR,
   COLOR_BURN,
   COLOR_DODGE,
   DARKEN,
   DIFFERENCE,
   DST,
   DST_ATOP,
   DST_IN,
   DST_OUT,
   DST_OVER,
   EXCLUSION,
   HARD_LIGHT,
   HUE,
   LIGHTEN,
   LUMINOSITY,
   MODULATE,
   MULTIPLY,
   OVERLAY,
   PLUS,
   SATURATION,
   SCREEN,
   SOFT_LIGHT,
   SRC,
   SRC_ATOP,
   SRC_IN,
   SRC_OUT,
   SRC_OVER,
   XOR;

   static {
      BlendModeCompat var0 = new BlendModeCompat("LUMINOSITY", 28);
      LUMINOSITY = var0;
   }
}

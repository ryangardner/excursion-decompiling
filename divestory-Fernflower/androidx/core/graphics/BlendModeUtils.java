package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.PorterDuff.Mode;

class BlendModeUtils {
   private BlendModeUtils() {
   }

   static BlendMode obtainBlendModeFromCompat(BlendModeCompat var0) {
      switch(null.$SwitchMap$androidx$core$graphics$BlendModeCompat[var0.ordinal()]) {
      case 1:
         return BlendMode.CLEAR;
      case 2:
         return BlendMode.SRC;
      case 3:
         return BlendMode.DST;
      case 4:
         return BlendMode.SRC_OVER;
      case 5:
         return BlendMode.DST_OVER;
      case 6:
         return BlendMode.SRC_IN;
      case 7:
         return BlendMode.DST_IN;
      case 8:
         return BlendMode.SRC_OUT;
      case 9:
         return BlendMode.DST_OUT;
      case 10:
         return BlendMode.SRC_ATOP;
      case 11:
         return BlendMode.DST_ATOP;
      case 12:
         return BlendMode.XOR;
      case 13:
         return BlendMode.PLUS;
      case 14:
         return BlendMode.MODULATE;
      case 15:
         return BlendMode.SCREEN;
      case 16:
         return BlendMode.OVERLAY;
      case 17:
         return BlendMode.DARKEN;
      case 18:
         return BlendMode.LIGHTEN;
      case 19:
         return BlendMode.COLOR_DODGE;
      case 20:
         return BlendMode.COLOR_BURN;
      case 21:
         return BlendMode.HARD_LIGHT;
      case 22:
         return BlendMode.SOFT_LIGHT;
      case 23:
         return BlendMode.DIFFERENCE;
      case 24:
         return BlendMode.EXCLUSION;
      case 25:
         return BlendMode.MULTIPLY;
      case 26:
         return BlendMode.HUE;
      case 27:
         return BlendMode.SATURATION;
      case 28:
         return BlendMode.COLOR;
      case 29:
         return BlendMode.LUMINOSITY;
      default:
         return null;
      }
   }

   static Mode obtainPorterDuffFromCompat(BlendModeCompat var0) {
      if (var0 != null) {
         switch(null.$SwitchMap$androidx$core$graphics$BlendModeCompat[var0.ordinal()]) {
         case 1:
            return Mode.CLEAR;
         case 2:
            return Mode.SRC;
         case 3:
            return Mode.DST;
         case 4:
            return Mode.SRC_OVER;
         case 5:
            return Mode.DST_OVER;
         case 6:
            return Mode.SRC_IN;
         case 7:
            return Mode.DST_IN;
         case 8:
            return Mode.SRC_OUT;
         case 9:
            return Mode.DST_OUT;
         case 10:
            return Mode.SRC_ATOP;
         case 11:
            return Mode.DST_ATOP;
         case 12:
            return Mode.XOR;
         case 13:
            return Mode.ADD;
         case 14:
            return Mode.MULTIPLY;
         case 15:
            return Mode.SCREEN;
         case 16:
            return Mode.OVERLAY;
         case 17:
            return Mode.DARKEN;
         case 18:
            return Mode.LIGHTEN;
         default:
            return null;
         }
      } else {
         return null;
      }
   }
}

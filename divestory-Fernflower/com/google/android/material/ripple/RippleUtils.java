package com.google.android.material.ripple;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.StateSet;
import androidx.core.graphics.ColorUtils;

public class RippleUtils {
   private static final int[] ENABLED_PRESSED_STATE_SET;
   private static final int[] FOCUSED_STATE_SET;
   private static final int[] HOVERED_FOCUSED_STATE_SET;
   private static final int[] HOVERED_STATE_SET;
   static final String LOG_TAG;
   private static final int[] PRESSED_STATE_SET;
   private static final int[] SELECTED_FOCUSED_STATE_SET;
   private static final int[] SELECTED_HOVERED_FOCUSED_STATE_SET;
   private static final int[] SELECTED_HOVERED_STATE_SET;
   private static final int[] SELECTED_PRESSED_STATE_SET;
   private static final int[] SELECTED_STATE_SET;
   static final String TRANSPARENT_DEFAULT_COLOR_WARNING = "Use a non-transparent color for the default color as it will be used to finish ripple animations.";
   public static final boolean USE_FRAMEWORK_RIPPLE;

   static {
      boolean var0;
      if (VERSION.SDK_INT >= 21) {
         var0 = true;
      } else {
         var0 = false;
      }

      USE_FRAMEWORK_RIPPLE = var0;
      PRESSED_STATE_SET = new int[]{16842919};
      HOVERED_FOCUSED_STATE_SET = new int[]{16843623, 16842908};
      FOCUSED_STATE_SET = new int[]{16842908};
      HOVERED_STATE_SET = new int[]{16843623};
      SELECTED_PRESSED_STATE_SET = new int[]{16842913, 16842919};
      SELECTED_HOVERED_FOCUSED_STATE_SET = new int[]{16842913, 16843623, 16842908};
      SELECTED_FOCUSED_STATE_SET = new int[]{16842913, 16842908};
      SELECTED_HOVERED_STATE_SET = new int[]{16842913, 16843623};
      SELECTED_STATE_SET = new int[]{16842913};
      ENABLED_PRESSED_STATE_SET = new int[]{16842910, 16842919};
      LOG_TAG = RippleUtils.class.getSimpleName();
   }

   private RippleUtils() {
   }

   public static ColorStateList convertToRippleDrawableColor(ColorStateList var0) {
      int[] var1;
      int var2;
      int[] var3;
      int var4;
      if (USE_FRAMEWORK_RIPPLE) {
         var1 = SELECTED_STATE_SET;
         var2 = getColorForState(var0, SELECTED_PRESSED_STATE_SET);
         var3 = StateSet.NOTHING;
         var4 = getColorForState(var0, PRESSED_STATE_SET);
         return new ColorStateList(new int[][]{var1, var3}, new int[]{var2, var4});
      } else {
         var3 = SELECTED_PRESSED_STATE_SET;
         var2 = getColorForState(var0, var3);
         int[] var5 = SELECTED_HOVERED_FOCUSED_STATE_SET;
         int var6 = getColorForState(var0, var5);
         int[] var7 = SELECTED_FOCUSED_STATE_SET;
         int var8 = getColorForState(var0, var7);
         int[] var9 = SELECTED_HOVERED_STATE_SET;
         int var10 = getColorForState(var0, var9);
         int[] var11 = SELECTED_STATE_SET;
         int[] var12 = PRESSED_STATE_SET;
         int var13 = getColorForState(var0, var12);
         int[] var14 = HOVERED_FOCUSED_STATE_SET;
         int var15 = getColorForState(var0, var14);
         var1 = FOCUSED_STATE_SET;
         int var16 = getColorForState(var0, var1);
         int[] var17 = HOVERED_STATE_SET;
         var4 = getColorForState(var0, var17);
         return new ColorStateList(new int[][]{var3, var5, var7, var9, var11, var12, var14, var1, var17, StateSet.NOTHING}, new int[]{var2, var6, var8, var10, 0, var13, var15, var16, var4, 0});
      }
   }

   private static int doubleAlpha(int var0) {
      return ColorUtils.setAlphaComponent(var0, Math.min(Color.alpha(var0) * 2, 255));
   }

   private static int getColorForState(ColorStateList var0, int[] var1) {
      int var2;
      if (var0 != null) {
         var2 = var0.getColorForState(var1, var0.getDefaultColor());
      } else {
         var2 = 0;
      }

      int var3 = var2;
      if (USE_FRAMEWORK_RIPPLE) {
         var3 = doubleAlpha(var2);
      }

      return var3;
   }

   public static ColorStateList sanitizeRippleDrawableColor(ColorStateList var0) {
      if (var0 != null) {
         if (VERSION.SDK_INT >= 22 && VERSION.SDK_INT <= 27 && Color.alpha(var0.getDefaultColor()) == 0 && Color.alpha(var0.getColorForState(ENABLED_PRESSED_STATE_SET, 0)) != 0) {
            Log.w(LOG_TAG, "Use a non-transparent color for the default color as it will be used to finish ripple animations.");
         }

         return var0;
      } else {
         return ColorStateList.valueOf(0);
      }
   }

   public static boolean shouldDrawRippleCompat(int[] var0) {
      int var1 = var0.length;
      boolean var2 = false;
      int var3 = 0;
      boolean var4 = false;

      boolean var5;
      boolean var7;
      for(var5 = false; var3 < var1; var4 = var7) {
         int var6 = var0[var3];
         if (var6 == 16842910) {
            var7 = true;
         } else {
            label36: {
               if (var6 != 16842908 && var6 != 16842919) {
                  var7 = var4;
                  if (var6 != 16843623) {
                     break label36;
                  }
               }

               var5 = true;
               var7 = var4;
            }
         }

         ++var3;
      }

      boolean var8 = var2;
      if (var4) {
         var8 = var2;
         if (var5) {
            var8 = true;
         }
      }

      return var8;
   }
}

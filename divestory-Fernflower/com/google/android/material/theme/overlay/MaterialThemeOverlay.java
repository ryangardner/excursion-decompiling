package com.google.android.material.theme.overlay;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.view.ContextThemeWrapper;
import com.google.android.material.R;

public class MaterialThemeOverlay {
   private static final int[] ANDROID_THEME_OVERLAY_ATTRS;
   private static final int[] MATERIAL_THEME_OVERLAY_ATTR;

   static {
      ANDROID_THEME_OVERLAY_ATTRS = new int[]{16842752, R.attr.theme};
      MATERIAL_THEME_OVERLAY_ATTR = new int[]{R.attr.materialThemeOverlay};
   }

   private MaterialThemeOverlay() {
   }

   private static int obtainAndroidThemeOverlayId(Context var0, AttributeSet var1) {
      TypedArray var4 = var0.obtainStyledAttributes(var1, ANDROID_THEME_OVERLAY_ATTRS);
      int var2 = var4.getResourceId(0, 0);
      int var3 = var4.getResourceId(1, 0);
      var4.recycle();
      if (var2 != 0) {
         var3 = var2;
      }

      return var3;
   }

   private static int obtainMaterialThemeOverlayId(Context var0, AttributeSet var1, int var2, int var3) {
      TypedArray var4 = var0.obtainStyledAttributes(var1, MATERIAL_THEME_OVERLAY_ATTR, var2, var3);
      var2 = var4.getResourceId(0, 0);
      var4.recycle();
      return var2;
   }

   public static Context wrap(Context var0, AttributeSet var1, int var2, int var3) {
      var3 = obtainMaterialThemeOverlayId(var0, var1, var2, var3);
      boolean var5;
      if (var0 instanceof ContextThemeWrapper && ((ContextThemeWrapper)var0).getThemeResId() == var3) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var3 != 0 && !var5) {
         ContextThemeWrapper var4 = new ContextThemeWrapper(var0, var3);
         var2 = obtainAndroidThemeOverlayId(var0, var1);
         if (var2 != 0) {
            var4.getTheme().applyStyle(var2, true);
         }

         return var4;
      } else {
         return var0;
      }
   }
}

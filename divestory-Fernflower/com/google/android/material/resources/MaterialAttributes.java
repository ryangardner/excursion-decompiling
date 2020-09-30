package com.google.android.material.resources;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import com.google.android.material.R;

public class MaterialAttributes {
   public static TypedValue resolve(Context var0, int var1) {
      TypedValue var2 = new TypedValue();
      return var0.getTheme().resolveAttribute(var1, var2, true) ? var2 : null;
   }

   public static boolean resolveBoolean(Context var0, int var1, boolean var2) {
      TypedValue var4 = resolve(var0, var1);
      boolean var3 = var2;
      if (var4 != null) {
         var3 = var2;
         if (var4.type == 18) {
            if (var4.data != 0) {
               var3 = true;
            } else {
               var3 = false;
            }
         }
      }

      return var3;
   }

   public static boolean resolveBooleanOrThrow(Context var0, int var1, String var2) {
      boolean var3;
      if (resolveOrThrow(var0, var1, var2) != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static int resolveDimension(Context var0, int var1, int var2) {
      TypedValue var3 = resolve(var0, var1);
      float var4;
      if (var3 != null && var3.type == 5) {
         var4 = var3.getDimension(var0.getResources().getDisplayMetrics());
      } else {
         var4 = var0.getResources().getDimension(var2);
      }

      return (int)var4;
   }

   public static int resolveMinimumAccessibleTouchTarget(Context var0) {
      return resolveDimension(var0, R.attr.minTouchTargetSize, R.dimen.mtrl_min_touch_target_size);
   }

   public static int resolveOrThrow(Context var0, int var1, String var2) {
      TypedValue var3 = resolve(var0, var1);
      if (var3 != null) {
         return var3.data;
      } else {
         throw new IllegalArgumentException(String.format("%1$s requires a value for the %2$s attribute to be set in your app theme. You can either set the attribute in your theme or update your theme to inherit from Theme.MaterialComponents (or a descendant).", var2, var0.getResources().getResourceName(var1)));
      }
   }

   public static int resolveOrThrow(View var0, int var1) {
      return resolveOrThrow(var0.getContext(), var1, var0.getClass().getCanonicalName());
   }
}

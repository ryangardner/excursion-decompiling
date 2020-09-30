package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.TintTypedArray;
import com.google.android.material.R;

public final class ThemeEnforcement {
   private static final int[] APPCOMPAT_CHECK_ATTRS;
   private static final String APPCOMPAT_THEME_NAME = "Theme.AppCompat";
   private static final int[] MATERIAL_CHECK_ATTRS;
   private static final String MATERIAL_THEME_NAME = "Theme.MaterialComponents";

   static {
      APPCOMPAT_CHECK_ATTRS = new int[]{R.attr.colorPrimary};
      MATERIAL_CHECK_ATTRS = new int[]{R.attr.colorPrimaryVariant};
   }

   private ThemeEnforcement() {
   }

   public static void checkAppCompatTheme(Context var0) {
      checkTheme(var0, APPCOMPAT_CHECK_ATTRS, "Theme.AppCompat");
   }

   private static void checkCompatibleTheme(Context var0, AttributeSet var1, int var2, int var3) {
      TypedArray var5 = var0.obtainStyledAttributes(var1, R.styleable.ThemeEnforcement, var2, var3);
      boolean var4 = var5.getBoolean(R.styleable.ThemeEnforcement_enforceMaterialTheme, false);
      var5.recycle();
      if (var4) {
         TypedValue var6 = new TypedValue();
         if (!var0.getTheme().resolveAttribute(R.attr.isMaterialTheme, var6, true) || var6.type == 18 && var6.data == 0) {
            checkMaterialTheme(var0);
         }
      }

      checkAppCompatTheme(var0);
   }

   public static void checkMaterialTheme(Context var0) {
      checkTheme(var0, MATERIAL_CHECK_ATTRS, "Theme.MaterialComponents");
   }

   private static void checkTextAppearance(Context var0, AttributeSet var1, int[] var2, int var3, int var4, int... var5) {
      TypedArray var6 = var0.obtainStyledAttributes(var1, R.styleable.ThemeEnforcement, var3, var4);
      int var7 = R.styleable.ThemeEnforcement_enforceTextAppearance;
      boolean var8 = false;
      if (!var6.getBoolean(var7, false)) {
         var6.recycle();
      } else {
         if (var5 != null && var5.length != 0) {
            var8 = isCustomTextAppearanceValid(var0, var1, var2, var3, var4, var5);
         } else if (var6.getResourceId(R.styleable.ThemeEnforcement_android_textAppearance, -1) != -1) {
            var8 = true;
         }

         var6.recycle();
         if (!var8) {
            throw new IllegalArgumentException("This component requires that you specify a valid TextAppearance attribute. Update your app theme to inherit from Theme.MaterialComponents (or a descendant).");
         }
      }
   }

   private static void checkTheme(Context var0, int[] var1, String var2) {
      if (!isTheme(var0, var1)) {
         StringBuilder var3 = new StringBuilder();
         var3.append("The style on this component requires your app theme to be ");
         var3.append(var2);
         var3.append(" (or a descendant).");
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public static boolean isAppCompatTheme(Context var0) {
      return isTheme(var0, APPCOMPAT_CHECK_ATTRS);
   }

   private static boolean isCustomTextAppearanceValid(Context var0, AttributeSet var1, int[] var2, int var3, int var4, int... var5) {
      TypedArray var6 = var0.obtainStyledAttributes(var1, var2, var3, var4);
      var4 = var5.length;

      for(var3 = 0; var3 < var4; ++var3) {
         if (var6.getResourceId(var5[var3], -1) == -1) {
            var6.recycle();
            return false;
         }
      }

      var6.recycle();
      return true;
   }

   public static boolean isMaterialTheme(Context var0) {
      return isTheme(var0, MATERIAL_CHECK_ATTRS);
   }

   private static boolean isTheme(Context var0, int[] var1) {
      TypedArray var3 = var0.obtainStyledAttributes(var1);

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (!var3.hasValue(var2)) {
            var3.recycle();
            return false;
         }
      }

      var3.recycle();
      return true;
   }

   public static TypedArray obtainStyledAttributes(Context var0, AttributeSet var1, int[] var2, int var3, int var4, int... var5) {
      checkCompatibleTheme(var0, var1, var3, var4);
      checkTextAppearance(var0, var1, var2, var3, var4, var5);
      return var0.obtainStyledAttributes(var1, var2, var3, var4);
   }

   public static TintTypedArray obtainTintedStyledAttributes(Context var0, AttributeSet var1, int[] var2, int var3, int var4, int... var5) {
      checkCompatibleTheme(var0, var1, var3, var4);
      checkTextAppearance(var0, var1, var2, var3, var4, var5);
      return TintTypedArray.obtainStyledAttributes(var0, var1, var2, var3, var4);
   }
}

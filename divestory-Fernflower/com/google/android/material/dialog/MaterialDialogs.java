package com.google.android.material.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;

public class MaterialDialogs {
   private MaterialDialogs() {
   }

   public static Rect getDialogBackgroundInsets(Context var0, int var1, int var2) {
      TypedArray var3 = ThemeEnforcement.obtainStyledAttributes(var0, (AttributeSet)null, R.styleable.MaterialAlertDialog, var1, var2);
      int var4 = var3.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetStart, var0.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_start));
      int var5 = var3.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetTop, var0.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_top));
      var2 = var3.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetEnd, var0.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_end));
      int var6 = var3.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetBottom, var0.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_bottom));
      var3.recycle();
      int var7 = var2;
      var1 = var4;
      if (VERSION.SDK_INT >= 17) {
         var7 = var2;
         var1 = var4;
         if (var0.getResources().getConfiguration().getLayoutDirection() == 1) {
            var1 = var2;
            var7 = var4;
         }
      }

      return new Rect(var1, var5, var7, var6);
   }

   public static InsetDrawable insetDrawable(Drawable var0, Rect var1) {
      return new InsetDrawable(var0, var1.left, var1.top, var1.right, var1.bottom);
   }
}

package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.widget.Button;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.gms.base.R;
import com.google.android.gms.common.util.DeviceProperties;

public final class zay extends Button {
   public zay(Context var1) {
      this(var1, (AttributeSet)null);
   }

   private zay(Context var1, AttributeSet var2) {
      super(var1, (AttributeSet)null, 16842824);
   }

   private static int zaa(int var0, int var1, int var2, int var3) {
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 == 2) {
               return var3;
            } else {
               StringBuilder var4 = new StringBuilder(33);
               var4.append("Unknown color scheme: ");
               var4.append(var0);
               throw new IllegalStateException(var4.toString());
            }
         } else {
            return var2;
         }
      } else {
         return var1;
      }
   }

   public final void zaa(Resources var1, int var2, int var3) {
      this.setTypeface(Typeface.DEFAULT_BOLD);
      this.setTextSize(14.0F);
      int var4 = (int)(var1.getDisplayMetrics().density * 48.0F + 0.5F);
      this.setMinHeight(var4);
      this.setMinWidth(var4);
      int var5 = zaa(var3, R.drawable.common_google_signin_btn_icon_dark, R.drawable.common_google_signin_btn_icon_light, R.drawable.common_google_signin_btn_icon_light);
      var4 = zaa(var3, R.drawable.common_google_signin_btn_text_dark, R.drawable.common_google_signin_btn_text_light, R.drawable.common_google_signin_btn_text_light);
      StringBuilder var7;
      if (var2 != 0 && var2 != 1) {
         if (var2 != 2) {
            var7 = new StringBuilder(32);
            var7.append("Unknown button size: ");
            var7.append(var2);
            throw new IllegalStateException(var7.toString());
         }

         var4 = var5;
      }

      Drawable var6 = DrawableCompat.wrap(var1.getDrawable(var4));
      DrawableCompat.setTintList(var6, var1.getColorStateList(R.color.common_google_signin_btn_tint));
      DrawableCompat.setTintMode(var6, Mode.SRC_ATOP);
      this.setBackgroundDrawable(var6);
      this.setTextColor((ColorStateList)Preconditions.checkNotNull(var1.getColorStateList(zaa(var3, R.color.common_google_signin_btn_text_dark, R.color.common_google_signin_btn_text_light, R.color.common_google_signin_btn_text_light))));
      if (var2 != 0) {
         if (var2 != 1) {
            if (var2 != 2) {
               var7 = new StringBuilder(32);
               var7.append("Unknown button size: ");
               var7.append(var2);
               throw new IllegalStateException(var7.toString());
            }

            this.setText((CharSequence)null);
         } else {
            this.setText(var1.getString(R.string.common_signin_button_text_long));
         }
      } else {
         this.setText(var1.getString(R.string.common_signin_button_text));
      }

      this.setTransformationMethod((TransformationMethod)null);
      if (DeviceProperties.isWearable(this.getContext())) {
         this.setGravity(19);
      }

   }
}

package com.google.android.material.appbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialToolbar extends Toolbar {
   private static final int DEF_STYLE_RES;

   static {
      DEF_STYLE_RES = R.style.Widget_MaterialComponents_Toolbar;
   }

   public MaterialToolbar(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public MaterialToolbar(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.toolbarStyle);
   }

   public MaterialToolbar(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, DEF_STYLE_RES), var2, var3);
      this.initBackground(this.getContext());
   }

   private void initBackground(Context var1) {
      Drawable var2 = this.getBackground();
      if (var2 == null || var2 instanceof ColorDrawable) {
         MaterialShapeDrawable var3 = new MaterialShapeDrawable();
         int var4;
         if (var2 != null) {
            var4 = ((ColorDrawable)var2).getColor();
         } else {
            var4 = 0;
         }

         var3.setFillColor(ColorStateList.valueOf(var4));
         var3.initializeElevationOverlay(var1);
         var3.setElevation(ViewCompat.getElevation(this));
         ViewCompat.setBackground(this, var3);
      }
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      MaterialShapeUtils.setParentAbsoluteElevation(this);
   }

   public void setElevation(float var1) {
      super.setElevation(var1);
      MaterialShapeUtils.setElevation(this, var1);
   }
}

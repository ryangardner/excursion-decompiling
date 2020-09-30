package com.google.android.gms.internal.base;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;

final class zah extends ConstantState {
   int zaa;
   int zab;

   zah(zah var1) {
      if (var1 != null) {
         this.zaa = var1.zaa;
         this.zab = var1.zab;
      }

   }

   public final int getChangingConfigurations() {
      return this.zaa;
   }

   public final Drawable newDrawable() {
      return new zae(this);
   }
}

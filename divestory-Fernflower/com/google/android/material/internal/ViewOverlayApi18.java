package com.google.android.material.internal;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewOverlay;

class ViewOverlayApi18 implements ViewOverlayImpl {
   private final ViewOverlay viewOverlay;

   ViewOverlayApi18(View var1) {
      this.viewOverlay = var1.getOverlay();
   }

   public void add(Drawable var1) {
      this.viewOverlay.add(var1);
   }

   public void remove(Drawable var1) {
      this.viewOverlay.remove(var1);
   }
}

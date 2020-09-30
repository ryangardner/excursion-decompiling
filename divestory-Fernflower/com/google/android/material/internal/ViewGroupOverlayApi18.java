package com.google.android.material.internal;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

class ViewGroupOverlayApi18 implements ViewGroupOverlayImpl {
   private final ViewGroupOverlay viewGroupOverlay;

   ViewGroupOverlayApi18(ViewGroup var1) {
      this.viewGroupOverlay = var1.getOverlay();
   }

   public void add(Drawable var1) {
      this.viewGroupOverlay.add(var1);
   }

   public void add(View var1) {
      this.viewGroupOverlay.add(var1);
   }

   public void remove(Drawable var1) {
      this.viewGroupOverlay.remove(var1);
   }

   public void remove(View var1) {
      this.viewGroupOverlay.remove(var1);
   }
}

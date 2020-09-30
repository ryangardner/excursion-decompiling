package com.github.mikephil.charting.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

public class ChartAnimator {
   private AnimatorUpdateListener mListener;
   protected float mPhaseX = 1.0F;
   protected float mPhaseY = 1.0F;

   public ChartAnimator() {
   }

   public ChartAnimator(AnimatorUpdateListener var1) {
      this.mListener = var1;
   }

   private ObjectAnimator xAnimator(int var1, Easing.EasingFunction var2) {
      ObjectAnimator var3 = ObjectAnimator.ofFloat(this, "phaseX", new float[]{0.0F, 1.0F});
      var3.setInterpolator(var2);
      var3.setDuration((long)var1);
      return var3;
   }

   private ObjectAnimator yAnimator(int var1, Easing.EasingFunction var2) {
      ObjectAnimator var3 = ObjectAnimator.ofFloat(this, "phaseY", new float[]{0.0F, 1.0F});
      var3.setInterpolator(var2);
      var3.setDuration((long)var1);
      return var3;
   }

   public void animateX(int var1) {
      this.animateX(var1, Easing.Linear);
   }

   public void animateX(int var1, Easing.EasingFunction var2) {
      ObjectAnimator var3 = this.xAnimator(var1, var2);
      var3.addUpdateListener(this.mListener);
      var3.start();
   }

   public void animateXY(int var1, int var2) {
      this.animateXY(var1, var2, Easing.Linear, Easing.Linear);
   }

   public void animateXY(int var1, int var2, Easing.EasingFunction var3) {
      ObjectAnimator var4 = this.xAnimator(var1, var3);
      ObjectAnimator var5 = this.yAnimator(var2, var3);
      if (var1 > var2) {
         var4.addUpdateListener(this.mListener);
      } else {
         var5.addUpdateListener(this.mListener);
      }

      var4.start();
      var5.start();
   }

   public void animateXY(int var1, int var2, Easing.EasingFunction var3, Easing.EasingFunction var4) {
      ObjectAnimator var5 = this.xAnimator(var1, var3);
      ObjectAnimator var6 = this.yAnimator(var2, var4);
      if (var1 > var2) {
         var5.addUpdateListener(this.mListener);
      } else {
         var6.addUpdateListener(this.mListener);
      }

      var5.start();
      var6.start();
   }

   public void animateY(int var1) {
      this.animateY(var1, Easing.Linear);
   }

   public void animateY(int var1, Easing.EasingFunction var2) {
      ObjectAnimator var3 = this.yAnimator(var1, var2);
      var3.addUpdateListener(this.mListener);
      var3.start();
   }

   public float getPhaseX() {
      return this.mPhaseX;
   }

   public float getPhaseY() {
      return this.mPhaseY;
   }

   public void setPhaseX(float var1) {
      float var2;
      if (var1 > 1.0F) {
         var2 = 1.0F;
      } else {
         var2 = var1;
         if (var1 < 0.0F) {
            var2 = 0.0F;
         }
      }

      this.mPhaseX = var2;
   }

   public void setPhaseY(float var1) {
      float var2;
      if (var1 > 1.0F) {
         var2 = 1.0F;
      } else {
         var2 = var1;
         if (var1 < 0.0F) {
            var2 = 0.0F;
         }
      }

      this.mPhaseY = var2;
   }
}

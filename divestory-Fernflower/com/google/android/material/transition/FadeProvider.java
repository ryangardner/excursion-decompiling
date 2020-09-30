package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import android.view.ViewGroup;

public final class FadeProvider implements VisibilityAnimatorProvider {
   private float incomingEndThreshold = 1.0F;

   private static Animator createFadeAnimator(final View var0, final float var1, final float var2, final float var3, final float var4) {
      ValueAnimator var5 = ValueAnimator.ofFloat(new float[]{0.0F, 1.0F});
      var5.addUpdateListener(new AnimatorUpdateListener() {
         public void onAnimationUpdate(ValueAnimator var1x) {
            float var2x = (Float)var1x.getAnimatedValue();
            var0.setAlpha(TransitionUtils.lerp(var1, var2, var3, var4, var2x));
         }
      });
      return var5;
   }

   public Animator createAppear(ViewGroup var1, View var2) {
      return createFadeAnimator(var2, 0.0F, 1.0F, 0.0F, this.incomingEndThreshold);
   }

   public Animator createDisappear(ViewGroup var1, View var2) {
      return createFadeAnimator(var2, 1.0F, 0.0F, 0.0F, 1.0F);
   }

   public float getIncomingEndThreshold() {
      return this.incomingEndThreshold;
   }

   public void setIncomingEndThreshold(float var1) {
      this.incomingEndThreshold = var1;
   }
}

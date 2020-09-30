package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import android.view.ViewGroup;

public final class FadeThroughProvider implements VisibilityAnimatorProvider {
   static final float PROGRESS_THRESHOLD = 0.35F;

   private static Animator createFadeThroughAnimator(final View var0, final float var1, final float var2, final float var3, final float var4) {
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
      return createFadeThroughAnimator(var2, 0.0F, 1.0F, 0.35F, 1.0F);
   }

   public Animator createDisappear(ViewGroup var1, View var2) {
      return createFadeThroughAnimator(var2, 1.0F, 0.0F, 0.0F, 0.35F);
   }
}

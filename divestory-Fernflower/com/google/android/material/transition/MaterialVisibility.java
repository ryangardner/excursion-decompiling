package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.TransitionValues;
import androidx.transition.Visibility;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import java.util.ArrayList;

abstract class MaterialVisibility<P extends VisibilityAnimatorProvider> extends Visibility {
   private final P primaryAnimatorProvider;
   private VisibilityAnimatorProvider secondaryAnimatorProvider;

   protected MaterialVisibility(P var1, VisibilityAnimatorProvider var2) {
      this.primaryAnimatorProvider = var1;
      this.secondaryAnimatorProvider = var2;
      this.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
   }

   private Animator createAnimator(ViewGroup var1, View var2, boolean var3) {
      AnimatorSet var4 = new AnimatorSet();
      ArrayList var5 = new ArrayList();
      Animator var6;
      if (var3) {
         var6 = this.primaryAnimatorProvider.createAppear(var1, var2);
      } else {
         var6 = this.primaryAnimatorProvider.createDisappear(var1, var2);
      }

      if (var6 != null) {
         var5.add(var6);
      }

      VisibilityAnimatorProvider var8 = this.secondaryAnimatorProvider;
      if (var8 != null) {
         Animator var7;
         if (var3) {
            var7 = var8.createAppear(var1, var2);
         } else {
            var7 = var8.createDisappear(var1, var2);
         }

         if (var7 != null) {
            var5.add(var7);
         }
      }

      AnimatorSetCompat.playTogether(var4, var5);
      return var4;
   }

   public P getPrimaryAnimatorProvider() {
      return this.primaryAnimatorProvider;
   }

   public VisibilityAnimatorProvider getSecondaryAnimatorProvider() {
      return this.secondaryAnimatorProvider;
   }

   public Animator onAppear(ViewGroup var1, View var2, TransitionValues var3, TransitionValues var4) {
      return this.createAnimator(var1, var2, true);
   }

   public Animator onDisappear(ViewGroup var1, View var2, TransitionValues var3, TransitionValues var4) {
      return this.createAnimator(var1, var2, false);
   }

   public void setSecondaryAnimatorProvider(VisibilityAnimatorProvider var1) {
      this.secondaryAnimatorProvider = var1;
   }
}

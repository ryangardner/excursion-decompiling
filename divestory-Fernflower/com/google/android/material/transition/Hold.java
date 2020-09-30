package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.TransitionValues;
import androidx.transition.Visibility;

public final class Hold extends Visibility {
   public Animator onAppear(ViewGroup var1, View var2, TransitionValues var3, TransitionValues var4) {
      return ValueAnimator.ofFloat(new float[]{0.0F});
   }

   public Animator onDisappear(ViewGroup var1, View var2, TransitionValues var3, TransitionValues var4) {
      return ValueAnimator.ofFloat(new float[]{0.0F});
   }
}

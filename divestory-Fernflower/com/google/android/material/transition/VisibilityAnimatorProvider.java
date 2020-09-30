package com.google.android.material.transition;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;

public interface VisibilityAnimatorProvider {
   Animator createAppear(ViewGroup var1, View var2);

   Animator createDisappear(ViewGroup var1, View var2);
}

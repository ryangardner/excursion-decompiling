/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.transition.TransitionUtils;
import com.google.android.material.transition.VisibilityAnimatorProvider;

public final class FadeProvider
implements VisibilityAnimatorProvider {
    private float incomingEndThreshold = 1.0f;

    private static Animator createFadeAnimator(final View view, final float f, final float f2, final float f3, final float f4) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f5 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                view.setAlpha(TransitionUtils.lerp(f, f2, f3, f4, f5));
            }
        });
        return valueAnimator;
    }

    @Override
    public Animator createAppear(ViewGroup viewGroup, View view) {
        return FadeProvider.createFadeAnimator(view, 0.0f, 1.0f, 0.0f, this.incomingEndThreshold);
    }

    @Override
    public Animator createDisappear(ViewGroup viewGroup, View view) {
        return FadeProvider.createFadeAnimator(view, 1.0f, 0.0f, 0.0f, 1.0f);
    }

    public float getIncomingEndThreshold() {
        return this.incomingEndThreshold;
    }

    public void setIncomingEndThreshold(float f) {
        this.incomingEndThreshold = f;
    }

}


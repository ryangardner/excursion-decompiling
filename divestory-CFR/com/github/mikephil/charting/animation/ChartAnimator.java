/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package com.github.mikephil.charting.animation;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import com.github.mikephil.charting.animation.Easing;

public class ChartAnimator {
    private ValueAnimator.AnimatorUpdateListener mListener;
    protected float mPhaseX = 1.0f;
    protected float mPhaseY = 1.0f;

    public ChartAnimator() {
    }

    public ChartAnimator(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.mListener = animatorUpdateListener;
    }

    private ObjectAnimator xAnimator(int n, Easing.EasingFunction easingFunction) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)this, (String)"phaseX", (float[])new float[]{0.0f, 1.0f});
        objectAnimator.setInterpolator((TimeInterpolator)easingFunction);
        objectAnimator.setDuration((long)n);
        return objectAnimator;
    }

    private ObjectAnimator yAnimator(int n, Easing.EasingFunction easingFunction) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)this, (String)"phaseY", (float[])new float[]{0.0f, 1.0f});
        objectAnimator.setInterpolator((TimeInterpolator)easingFunction);
        objectAnimator.setDuration((long)n);
        return objectAnimator;
    }

    public void animateX(int n) {
        this.animateX(n, Easing.Linear);
    }

    public void animateX(int n, Easing.EasingFunction easingFunction) {
        easingFunction = this.xAnimator(n, easingFunction);
        easingFunction.addUpdateListener(this.mListener);
        easingFunction.start();
    }

    public void animateXY(int n, int n2) {
        this.animateXY(n, n2, Easing.Linear, Easing.Linear);
    }

    public void animateXY(int n, int n2, Easing.EasingFunction easingFunction) {
        ObjectAnimator objectAnimator = this.xAnimator(n, easingFunction);
        easingFunction = this.yAnimator(n2, easingFunction);
        if (n > n2) {
            objectAnimator.addUpdateListener(this.mListener);
        } else {
            easingFunction.addUpdateListener(this.mListener);
        }
        objectAnimator.start();
        easingFunction.start();
    }

    public void animateXY(int n, int n2, Easing.EasingFunction easingFunction, Easing.EasingFunction easingFunction2) {
        easingFunction = this.xAnimator(n, easingFunction);
        easingFunction2 = this.yAnimator(n2, easingFunction2);
        if (n > n2) {
            easingFunction.addUpdateListener(this.mListener);
        } else {
            easingFunction2.addUpdateListener(this.mListener);
        }
        easingFunction.start();
        easingFunction2.start();
    }

    public void animateY(int n) {
        this.animateY(n, Easing.Linear);
    }

    public void animateY(int n, Easing.EasingFunction easingFunction) {
        easingFunction = this.yAnimator(n, easingFunction);
        easingFunction.addUpdateListener(this.mListener);
        easingFunction.start();
    }

    public float getPhaseX() {
        return this.mPhaseX;
    }

    public float getPhaseY() {
        return this.mPhaseY;
    }

    public void setPhaseX(float f) {
        float f2;
        if (f > 1.0f) {
            f2 = 1.0f;
        } else {
            f2 = f;
            if (f < 0.0f) {
                f2 = 0.0f;
            }
        }
        this.mPhaseX = f2;
    }

    public void setPhaseY(float f) {
        float f2;
        if (f > 1.0f) {
            f2 = 1.0f;
        } else {
            f2 = f;
            if (f < 0.0f) {
                f2 = 0.0f;
            }
        }
        this.mPhaseY = f2;
    }
}


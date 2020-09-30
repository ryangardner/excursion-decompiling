/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.ObjectAnimator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 */
package com.github.mikephil.charting.jobs;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import com.github.mikephil.charting.jobs.ViewPortJob;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class AnimatedViewPortJob
extends ViewPortJob
implements ValueAnimator.AnimatorUpdateListener,
Animator.AnimatorListener {
    protected ObjectAnimator animator;
    protected float phase;
    protected float xOrigin;
    protected float yOrigin;

    public AnimatedViewPortJob(ViewPortHandler viewPortHandler, float f, float f2, Transformer transformer, View view, float f3, float f4, long l) {
        super(viewPortHandler, f, f2, transformer, view);
        this.xOrigin = f3;
        this.yOrigin = f4;
        viewPortHandler = ObjectAnimator.ofFloat((Object)this, (String)"phase", (float[])new float[]{0.0f, 1.0f});
        this.animator = viewPortHandler;
        viewPortHandler.setDuration(l);
        this.animator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)this);
        this.animator.addListener((Animator.AnimatorListener)this);
    }

    public float getPhase() {
        return this.phase;
    }

    public float getXOrigin() {
        return this.xOrigin;
    }

    public float getYOrigin() {
        return this.yOrigin;
    }

    public void onAnimationCancel(Animator animator2) {
        try {
            this.recycleSelf();
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    public void onAnimationEnd(Animator animator2) {
        try {
            this.recycleSelf();
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    public void onAnimationRepeat(Animator animator2) {
    }

    public void onAnimationStart(Animator animator2) {
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
    }

    public abstract void recycleSelf();

    protected void resetAnimator() {
        this.animator.removeAllListeners();
        this.animator.removeAllUpdateListeners();
        this.animator.reverse();
        this.animator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)this);
        this.animator.addListener((Animator.AnimatorListener)this);
    }

    @Override
    public void run() {
        this.animator.start();
    }

    public void setPhase(float f) {
        this.phase = f;
    }
}


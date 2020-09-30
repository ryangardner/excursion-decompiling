/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.transition.platform.VisibilityAnimatorProvider;

public final class ScaleProvider
implements VisibilityAnimatorProvider {
    private boolean growing;
    private float incomingEndScale = 1.0f;
    private float incomingStartScale = 0.8f;
    private float outgoingEndScale = 1.1f;
    private float outgoingStartScale = 1.0f;
    private boolean scaleOnDisappear = true;

    public ScaleProvider() {
        this(true);
    }

    public ScaleProvider(boolean bl) {
        this.growing = bl;
    }

    private static Animator createScaleAnimator(View view, float f, float f2) {
        return ObjectAnimator.ofPropertyValuesHolder((Object)view, (PropertyValuesHolder[])new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat((Property)View.SCALE_X, (float[])new float[]{f, f2}), PropertyValuesHolder.ofFloat((Property)View.SCALE_Y, (float[])new float[]{f, f2})});
    }

    @Override
    public Animator createAppear(ViewGroup viewGroup, View view) {
        if (!this.growing) return ScaleProvider.createScaleAnimator(view, this.outgoingEndScale, this.outgoingStartScale);
        return ScaleProvider.createScaleAnimator(view, this.incomingStartScale, this.incomingEndScale);
    }

    @Override
    public Animator createDisappear(ViewGroup viewGroup, View view) {
        if (!this.scaleOnDisappear) {
            return null;
        }
        if (!this.growing) return ScaleProvider.createScaleAnimator(view, this.incomingEndScale, this.incomingStartScale);
        return ScaleProvider.createScaleAnimator(view, this.outgoingStartScale, this.outgoingEndScale);
    }

    public float getIncomingEndScale() {
        return this.incomingEndScale;
    }

    public float getIncomingStartScale() {
        return this.incomingStartScale;
    }

    public float getOutgoingEndScale() {
        return this.outgoingEndScale;
    }

    public float getOutgoingStartScale() {
        return this.outgoingStartScale;
    }

    public boolean isGrowing() {
        return this.growing;
    }

    public boolean isScaleOnDisappear() {
        return this.scaleOnDisappear;
    }

    public void setGrowing(boolean bl) {
        this.growing = bl;
    }

    public void setIncomingEndScale(float f) {
        this.incomingEndScale = f;
    }

    public void setIncomingStartScale(float f) {
        this.incomingStartScale = f;
    }

    public void setOutgoingEndScale(float f) {
        this.outgoingEndScale = f;
    }

    public void setOutgoingStartScale(float f) {
        this.outgoingStartScale = f;
    }

    public void setScaleOnDisappear(boolean bl) {
        this.scaleOnDisappear = bl;
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;
import java.util.Map;

public class TextScale
extends Transition {
    private static final String PROPNAME_SCALE = "android:textscale:scale";

    private void captureValues(TransitionValues transitionValues) {
        if (!(transitionValues.view instanceof TextView)) return;
        TextView textView = (TextView)transitionValues.view;
        transitionValues.values.put(PROPNAME_SCALE, Float.valueOf(textView.getScaleX()));
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup object, TransitionValues object2, TransitionValues transitionValues) {
        final TextView textView = null;
        object = textView;
        if (object2 == null) return object;
        object = textView;
        if (transitionValues == null) return object;
        object = textView;
        if (!(((TransitionValues)object2).view instanceof TextView)) return object;
        if (!(transitionValues.view instanceof TextView)) {
            return textView;
        }
        textView = (TextView)transitionValues.view;
        object = ((TransitionValues)object2).values;
        object2 = transitionValues.values;
        transitionValues = object.get(PROPNAME_SCALE);
        float f = 1.0f;
        float f2 = transitionValues != null ? ((Float)object.get(PROPNAME_SCALE)).floatValue() : 1.0f;
        if (object2.get(PROPNAME_SCALE) != null) {
            f = ((Float)object2.get(PROPNAME_SCALE)).floatValue();
        }
        if (f2 == f) {
            return null;
        }
        object = ValueAnimator.ofFloat((float[])new float[]{f2, f});
        object.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                textView.setScaleX(f);
                textView.setScaleY(f);
            }
        });
        return object;
    }

}


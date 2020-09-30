/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Paint
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import androidx.transition.Styleable;
import androidx.transition.Transition;
import androidx.transition.TransitionListenerAdapter;
import androidx.transition.TransitionValues;
import androidx.transition.ViewUtils;
import androidx.transition.Visibility;
import java.util.Map;

public class Fade
extends Visibility {
    public static final int IN = 1;
    private static final String LOG_TAG = "Fade";
    public static final int OUT = 2;
    private static final String PROPNAME_TRANSITION_ALPHA = "android:fade:transitionAlpha";

    public Fade() {
    }

    public Fade(int n) {
        this.setMode(n);
    }

    public Fade(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.FADE);
        this.setMode(TypedArrayUtils.getNamedInt((TypedArray)context, (XmlResourceParser)attributeSet, "fadingMode", 0, this.getMode()));
        context.recycle();
    }

    private Animator createAnimation(final View view, float f, float f2) {
        if (f == f2) {
            return null;
        }
        ViewUtils.setTransitionAlpha(view, f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)view, ViewUtils.TRANSITION_ALPHA, (float[])new float[]{f2});
        objectAnimator.addListener((Animator.AnimatorListener)new FadeAnimatorListener(view));
        this.addListener(new TransitionListenerAdapter(){

            @Override
            public void onTransitionEnd(Transition transition) {
                ViewUtils.setTransitionAlpha(view, 1.0f);
                ViewUtils.clearNonTransitionAlpha(view);
                transition.removeListener(this);
            }
        });
        return objectAnimator;
    }

    private static float getStartAlpha(TransitionValues object, float f) {
        float f2 = f;
        if (object == null) return f2;
        object = (Float)((TransitionValues)object).values.get(PROPNAME_TRANSITION_ALPHA);
        f2 = f;
        if (object == null) return f2;
        return ((Float)object).floatValue();
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        transitionValues.values.put(PROPNAME_TRANSITION_ALPHA, Float.valueOf(ViewUtils.getTransitionAlpha(transitionValues.view)));
    }

    @Override
    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        float f = 0.0f;
        float f2 = Fade.getStartAlpha(transitionValues, 0.0f);
        if (f2 == 1.0f) {
            return this.createAnimation(view, f, 1.0f);
        }
        f = f2;
        return this.createAnimation(view, f, 1.0f);
    }

    @Override
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        ViewUtils.saveNonTransitionAlpha(view);
        return this.createAnimation(view, Fade.getStartAlpha(transitionValues, 1.0f), 0.0f);
    }

    private static class FadeAnimatorListener
    extends AnimatorListenerAdapter {
        private boolean mLayerTypeChanged = false;
        private final View mView;

        FadeAnimatorListener(View view) {
            this.mView = view;
        }

        public void onAnimationEnd(Animator animator2) {
            ViewUtils.setTransitionAlpha(this.mView, 1.0f);
            if (!this.mLayerTypeChanged) return;
            this.mView.setLayerType(0, null);
        }

        public void onAnimationStart(Animator animator2) {
            if (!ViewCompat.hasOverlappingRendering(this.mView)) return;
            if (this.mView.getLayerType() != 0) return;
            this.mLayerTypeChanged = true;
            this.mView.setLayerType(2, null);
        }
    }

}


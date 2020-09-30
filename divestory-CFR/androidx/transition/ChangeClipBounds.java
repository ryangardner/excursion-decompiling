/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.transition.RectEvaluator;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;
import androidx.transition.ViewUtils;
import java.util.Map;

public class ChangeClipBounds
extends Transition {
    private static final String PROPNAME_BOUNDS = "android:clipBounds:bounds";
    private static final String PROPNAME_CLIP = "android:clipBounds:clip";
    private static final String[] sTransitionProperties = new String[]{"android:clipBounds:clip"};

    public ChangeClipBounds() {
    }

    public ChangeClipBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        Rect rect = ViewCompat.getClipBounds(view);
        transitionValues.values.put(PROPNAME_CLIP, (Object)rect);
        if (rect != null) return;
        view = new Rect(0, 0, view.getWidth(), view.getHeight());
        transitionValues.values.put(PROPNAME_BOUNDS, (Object)view);
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
    public Animator createAnimator(ViewGroup object, TransitionValues transitionValues, TransitionValues transitionValues2) {
        Object object2 = null;
        object = object2;
        if (transitionValues == null) return object;
        object = object2;
        if (transitionValues2 == null) return object;
        object = object2;
        if (!transitionValues.values.containsKey(PROPNAME_CLIP)) return object;
        if (!transitionValues2.values.containsKey(PROPNAME_CLIP)) {
            return object2;
        }
        Rect rect = (Rect)transitionValues.values.get(PROPNAME_CLIP);
        object2 = (Rect)transitionValues2.values.get(PROPNAME_CLIP);
        boolean bl = object2 == null;
        if (rect == null && object2 == null) {
            return null;
        }
        if (rect == null) {
            object = (Rect)transitionValues.values.get(PROPNAME_BOUNDS);
            transitionValues = object2;
        } else {
            object = rect;
            transitionValues = object2;
            if (object2 == null) {
                transitionValues = (Rect)transitionValues2.values.get(PROPNAME_BOUNDS);
                object = rect;
            }
        }
        if (object.equals((Object)transitionValues)) {
            return null;
        }
        ViewCompat.setClipBounds(transitionValues2.view, (Rect)object);
        object2 = new RectEvaluator(new Rect());
        transitionValues = ObjectAnimator.ofObject((Object)transitionValues2.view, ViewUtils.CLIP_BOUNDS, (TypeEvaluator)object2, (Object[])new Rect[]{object, transitionValues});
        object = transitionValues;
        if (!bl) return object;
        transitionValues.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(transitionValues2.view){
            final /* synthetic */ View val$endView;
            {
                this.val$endView = view;
            }

            public void onAnimationEnd(Animator animator2) {
                ViewCompat.setClipBounds(this.val$endView, null);
            }
        });
        return transitionValues;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

}


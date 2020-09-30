/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import androidx.transition.TransitionUtils;
import androidx.transition.TransitionValues;
import java.util.Map;

public class ChangeScroll
extends Transition {
    private static final String[] PROPERTIES = new String[]{"android:changeScroll:x", "android:changeScroll:y"};
    private static final String PROPNAME_SCROLL_X = "android:changeScroll:x";
    private static final String PROPNAME_SCROLL_Y = "android:changeScroll:y";

    public ChangeScroll() {
    }

    public ChangeScroll(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_SCROLL_X, transitionValues.view.getScrollX());
        transitionValues.values.put(PROPNAME_SCROLL_Y, transitionValues.view.getScrollY());
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
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        View view = null;
        Object var5_5 = null;
        viewGroup = view;
        if (transitionValues == null) return viewGroup;
        if (transitionValues2 == null) {
            return view;
        }
        view = transitionValues2.view;
        int n = (Integer)transitionValues.values.get(PROPNAME_SCROLL_X);
        int n2 = (Integer)transitionValues2.values.get(PROPNAME_SCROLL_X);
        int n3 = (Integer)transitionValues.values.get(PROPNAME_SCROLL_Y);
        int n4 = (Integer)transitionValues2.values.get(PROPNAME_SCROLL_Y);
        if (n != n2) {
            view.setScrollX(n);
            viewGroup = ObjectAnimator.ofInt((Object)view, (String)"scrollX", (int[])new int[]{n, n2});
        } else {
            viewGroup = null;
        }
        transitionValues = var5_5;
        if (n3 == n4) return TransitionUtils.mergeAnimators((Animator)viewGroup, (Animator)transitionValues);
        view.setScrollY(n3);
        transitionValues = ObjectAnimator.ofInt((Object)view, (String)"scrollY", (int[])new int[]{n3, n4});
        return TransitionUtils.mergeAnimators((Animator)viewGroup, (Animator)transitionValues);
    }

    @Override
    public String[] getTransitionProperties() {
        return PROPERTIES;
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.MotionEvent
 *  android.view.View
 */
package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transformation.ExpandableTransformationBehavior;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class FabTransformationScrimBehavior
extends ExpandableTransformationBehavior {
    public static final long COLLAPSE_DELAY = 0L;
    public static final long COLLAPSE_DURATION = 150L;
    public static final long EXPAND_DELAY = 75L;
    public static final long EXPAND_DURATION = 150L;
    private final MotionTiming collapseTiming = new MotionTiming(0L, 150L);
    private final MotionTiming expandTiming = new MotionTiming(75L, 150L);

    public FabTransformationScrimBehavior() {
    }

    public FabTransformationScrimBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void createScrimAnimation(View view, boolean bl, boolean bl2, List<Animator> list, List<Animator.AnimatorListener> object) {
        object = bl ? this.expandTiming : this.collapseTiming;
        if (bl) {
            if (!bl2) {
                view.setAlpha(0.0f);
            }
            view = ObjectAnimator.ofFloat((Object)view, (Property)View.ALPHA, (float[])new float[]{1.0f});
        } else {
            view = ObjectAnimator.ofFloat((Object)view, (Property)View.ALPHA, (float[])new float[]{0.0f});
        }
        ((MotionTiming)object).apply((Animator)view);
        list.add((Animator)view);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
        return view2 instanceof FloatingActionButton;
    }

    @Override
    protected AnimatorSet onCreateExpandedStateChangeAnimation(View object, final View view, final boolean bl, boolean bl2) {
        object = new ArrayList();
        this.createScrimAnimation(view, bl, bl2, (List<Animator>)object, new ArrayList<Animator.AnimatorListener>());
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSetCompat.playTogether(animatorSet, (List<Animator>)object);
        animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                if (bl) return;
                view.setVisibility(4);
            }

            public void onAnimationStart(Animator animator2) {
                if (!bl) return;
                view.setVisibility(0);
            }
        });
        return animatorSet;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        return super.onTouchEvent(coordinatorLayout, view, motionEvent);
    }

}


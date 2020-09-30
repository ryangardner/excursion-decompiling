/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.util.Property
 *  android.view.View
 */
package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Property;
import android.view.View;
import androidx.core.util.Preconditions;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.floatingactionbutton.AnimatorTracker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.MotionStrategy;
import java.util.ArrayList;
import java.util.List;

abstract class BaseMotionStrategy
implements MotionStrategy {
    private final Context context;
    private MotionSpec defaultMotionSpec;
    private final ExtendedFloatingActionButton fab;
    private final ArrayList<Animator.AnimatorListener> listeners = new ArrayList();
    private MotionSpec motionSpec;
    private final AnimatorTracker tracker;

    BaseMotionStrategy(ExtendedFloatingActionButton extendedFloatingActionButton, AnimatorTracker animatorTracker) {
        this.fab = extendedFloatingActionButton;
        this.context = extendedFloatingActionButton.getContext();
        this.tracker = animatorTracker;
    }

    @Override
    public final void addAnimationListener(Animator.AnimatorListener animatorListener) {
        this.listeners.add(animatorListener);
    }

    @Override
    public AnimatorSet createAnimator() {
        return this.createAnimator(this.getCurrentMotionSpec());
    }

    AnimatorSet createAnimator(MotionSpec motionSpec) {
        ArrayList<Animator> arrayList = new ArrayList<Animator>();
        if (motionSpec.hasPropertyValues("opacity")) {
            arrayList.add((Animator)motionSpec.getAnimator("opacity", this.fab, View.ALPHA));
        }
        if (motionSpec.hasPropertyValues("scale")) {
            arrayList.add((Animator)motionSpec.getAnimator("scale", this.fab, View.SCALE_Y));
            arrayList.add((Animator)motionSpec.getAnimator("scale", this.fab, View.SCALE_X));
        }
        if (motionSpec.hasPropertyValues("width")) {
            arrayList.add((Animator)motionSpec.getAnimator("width", this.fab, ExtendedFloatingActionButton.WIDTH));
        }
        if (motionSpec.hasPropertyValues("height")) {
            arrayList.add((Animator)motionSpec.getAnimator("height", this.fab, ExtendedFloatingActionButton.HEIGHT));
        }
        motionSpec = new AnimatorSet();
        AnimatorSetCompat.playTogether((AnimatorSet)motionSpec, arrayList);
        return motionSpec;
    }

    @Override
    public final MotionSpec getCurrentMotionSpec() {
        MotionSpec motionSpec = this.motionSpec;
        if (motionSpec != null) {
            return motionSpec;
        }
        if (this.defaultMotionSpec != null) return Preconditions.checkNotNull(this.defaultMotionSpec);
        this.defaultMotionSpec = MotionSpec.createFromResource(this.context, this.getDefaultMotionSpecResource());
        return Preconditions.checkNotNull(this.defaultMotionSpec);
    }

    @Override
    public final List<Animator.AnimatorListener> getListeners() {
        return this.listeners;
    }

    @Override
    public MotionSpec getMotionSpec() {
        return this.motionSpec;
    }

    @Override
    public void onAnimationCancel() {
        this.tracker.clear();
    }

    @Override
    public void onAnimationEnd() {
        this.tracker.clear();
    }

    @Override
    public void onAnimationStart(Animator animator2) {
        this.tracker.onNextAnimationStart(animator2);
    }

    @Override
    public final void removeAnimationListener(Animator.AnimatorListener animatorListener) {
        this.listeners.remove((Object)animatorListener);
    }

    @Override
    public final void setMotionSpec(MotionSpec motionSpec) {
        this.motionSpec = motionSpec;
    }
}


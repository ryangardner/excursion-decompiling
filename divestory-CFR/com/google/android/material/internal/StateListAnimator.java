/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 *  android.util.StateSet
 */
package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.StateSet;
import java.util.ArrayList;

public final class StateListAnimator {
    private final Animator.AnimatorListener animationListener = new AnimatorListenerAdapter(){

        public void onAnimationEnd(Animator animator2) {
            if (StateListAnimator.this.runningAnimator != animator2) return;
            StateListAnimator.this.runningAnimator = null;
        }
    };
    private Tuple lastMatch = null;
    ValueAnimator runningAnimator = null;
    private final ArrayList<Tuple> tuples = new ArrayList();

    private void cancel() {
        ValueAnimator valueAnimator = this.runningAnimator;
        if (valueAnimator == null) return;
        valueAnimator.cancel();
        this.runningAnimator = null;
    }

    private void start(Tuple tuple) {
        tuple = tuple.animator;
        this.runningAnimator = tuple;
        tuple.start();
    }

    public void addState(int[] object, ValueAnimator valueAnimator) {
        object = new Tuple((int[])object, valueAnimator);
        valueAnimator.addListener(this.animationListener);
        this.tuples.add((Tuple)object);
    }

    public void jumpToCurrentState() {
        ValueAnimator valueAnimator = this.runningAnimator;
        if (valueAnimator == null) return;
        valueAnimator.end();
        this.runningAnimator = null;
    }

    public void setState(int[] object) {
        Tuple tuple;
        block3 : {
            int n = this.tuples.size();
            for (int i = 0; i < n; ++i) {
                tuple = this.tuples.get(i);
                if (!StateSet.stateSetMatches((int[])tuple.specs, (int[])object)) continue;
                object = tuple;
                break block3;
            }
            object = null;
        }
        tuple = this.lastMatch;
        if (object == tuple) {
            return;
        }
        if (tuple != null) {
            this.cancel();
        }
        this.lastMatch = object;
        if (object == null) return;
        this.start((Tuple)object);
    }

    static class Tuple {
        final ValueAnimator animator;
        final int[] specs;

        Tuple(int[] arrn, ValueAnimator valueAnimator) {
            this.specs = arrn;
            this.animator = valueAnimator;
        }
    }

}


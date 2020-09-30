/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorSet
 *  android.animation.ValueAnimator
 */
package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import java.util.Collection;
import java.util.List;

public class AnimatorSetCompat {
    public static void playTogether(AnimatorSet animatorSet, List<Animator> list) {
        int n = list.size();
        long l = 0L;
        int n2 = 0;
        do {
            Animator animator2;
            if (n2 >= n) {
                animator2 = ValueAnimator.ofInt((int[])new int[]{0, 0});
                animator2.setDuration(l);
                list.add(0, animator2);
                animatorSet.playTogether(list);
                return;
            }
            animator2 = list.get(n2);
            l = Math.max(l, animator2.getStartDelay() + animator2.getDuration());
            ++n2;
        } while (true);
    }
}


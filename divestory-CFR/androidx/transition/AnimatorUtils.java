/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.Animator$AnimatorPauseListener
 *  android.animation.AnimatorListenerAdapter
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import java.util.ArrayList;

class AnimatorUtils {
    private AnimatorUtils() {
    }

    static void addPauseListener(Animator animator2, AnimatorListenerAdapter animatorListenerAdapter) {
        if (Build.VERSION.SDK_INT < 19) return;
        animator2.addPauseListener((Animator.AnimatorPauseListener)animatorListenerAdapter);
    }

    static void pause(Animator animator2) {
        if (Build.VERSION.SDK_INT >= 19) {
            animator2.pause();
            return;
        }
        ArrayList arrayList = animator2.getListeners();
        if (arrayList == null) return;
        int n = 0;
        int n2 = arrayList.size();
        while (n < n2) {
            Animator.AnimatorListener animatorListener = (Animator.AnimatorListener)arrayList.get(n);
            if (animatorListener instanceof AnimatorPauseListenerCompat) {
                ((AnimatorPauseListenerCompat)animatorListener).onAnimationPause(animator2);
            }
            ++n;
        }
    }

    static void resume(Animator animator2) {
        if (Build.VERSION.SDK_INT >= 19) {
            animator2.resume();
            return;
        }
        ArrayList arrayList = animator2.getListeners();
        if (arrayList == null) return;
        int n = 0;
        int n2 = arrayList.size();
        while (n < n2) {
            Animator.AnimatorListener animatorListener = (Animator.AnimatorListener)arrayList.get(n);
            if (animatorListener instanceof AnimatorPauseListenerCompat) {
                ((AnimatorPauseListenerCompat)animatorListener).onAnimationResume(animator2);
            }
            ++n;
        }
    }

    static interface AnimatorPauseListenerCompat {
        public void onAnimationPause(Animator var1);

        public void onAnimationResume(Animator var1);
    }

}


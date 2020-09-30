/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Resources;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.transition.VisibilityAnimatorProvider;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SlideDistanceProvider
implements VisibilityAnimatorProvider {
    private static final int DEFAULT_DISTANCE = -1;
    private int slideDistance = -1;
    private int slideEdge;

    public SlideDistanceProvider(int n) {
        this.slideEdge = n;
    }

    private static Animator createTranslationAppearAnimator(View object, View view, int n, int n2) {
        float f;
        if (n == 3) return SlideDistanceProvider.createTranslationXAnimator(view, n2, 0.0f);
        if (n == 5) return SlideDistanceProvider.createTranslationXAnimator(view, -n2, 0.0f);
        if (n == 48) return SlideDistanceProvider.createTranslationYAnimator(view, -n2, 0.0f);
        if (n == 80) return SlideDistanceProvider.createTranslationYAnimator(view, n2, 0.0f);
        if (n != 8388611) {
            float f2;
            if (n != 8388613) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid slide direction: ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if (SlideDistanceProvider.isRtl((View)object)) {
                f2 = -n2;
                return SlideDistanceProvider.createTranslationXAnimator(view, f2, 0.0f);
            }
            f2 = n2;
            return SlideDistanceProvider.createTranslationXAnimator(view, f2, 0.0f);
        }
        if (SlideDistanceProvider.isRtl((View)object)) {
            f = n2;
            return SlideDistanceProvider.createTranslationXAnimator(view, f, 0.0f);
        }
        f = -n2;
        return SlideDistanceProvider.createTranslationXAnimator(view, f, 0.0f);
    }

    private static Animator createTranslationDisappearAnimator(View object, View view, int n, int n2) {
        float f;
        if (n == 3) return SlideDistanceProvider.createTranslationXAnimator(view, 0.0f, -n2);
        if (n == 5) return SlideDistanceProvider.createTranslationXAnimator(view, 0.0f, n2);
        if (n == 48) return SlideDistanceProvider.createTranslationYAnimator(view, 0.0f, n2);
        if (n == 80) return SlideDistanceProvider.createTranslationYAnimator(view, 0.0f, -n2);
        if (n != 8388611) {
            float f2;
            if (n != 8388613) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid slide direction: ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if (SlideDistanceProvider.isRtl((View)object)) {
                f2 = n2;
                return SlideDistanceProvider.createTranslationXAnimator(view, 0.0f, f2);
            }
            f2 = -n2;
            return SlideDistanceProvider.createTranslationXAnimator(view, 0.0f, f2);
        }
        if (SlideDistanceProvider.isRtl((View)object)) {
            f = -n2;
            return SlideDistanceProvider.createTranslationXAnimator(view, 0.0f, f);
        }
        f = n2;
        return SlideDistanceProvider.createTranslationXAnimator(view, 0.0f, f);
    }

    private static Animator createTranslationXAnimator(View view, float f, float f2) {
        return ObjectAnimator.ofPropertyValuesHolder((Object)view, (PropertyValuesHolder[])new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat((Property)View.TRANSLATION_X, (float[])new float[]{f, f2})});
    }

    private static Animator createTranslationYAnimator(View view, float f, float f2) {
        return ObjectAnimator.ofPropertyValuesHolder((Object)view, (PropertyValuesHolder[])new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat((Property)View.TRANSLATION_Y, (float[])new float[]{f, f2})});
    }

    private int getSlideDistanceOrDefault(Context context) {
        int n = this.slideDistance;
        if (n == -1) return context.getResources().getDimensionPixelSize(R.dimen.mtrl_transition_shared_axis_slide_distance);
        return n;
    }

    private static boolean isRtl(View view) {
        int n = ViewCompat.getLayoutDirection(view);
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    @Override
    public Animator createAppear(ViewGroup viewGroup, View view) {
        return SlideDistanceProvider.createTranslationAppearAnimator((View)viewGroup, view, this.slideEdge, this.getSlideDistanceOrDefault(view.getContext()));
    }

    @Override
    public Animator createDisappear(ViewGroup viewGroup, View view) {
        return SlideDistanceProvider.createTranslationDisappearAnimator((View)viewGroup, view, this.slideEdge, this.getSlideDistanceOrDefault(view.getContext()));
    }

    public int getSlideDistance() {
        return this.slideDistance;
    }

    public int getSlideEdge() {
        return this.slideEdge;
    }

    public void setSlideDistance(int n) {
        if (n < 0) throw new IllegalArgumentException("Slide distance must be positive. If attempting to reverse the direction of the slide, use setSlideEdge(int) instead.");
        this.slideDistance = n;
    }

    public void setSlideEdge(int n) {
        this.slideEdge = n;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GravityFlag {
    }

}


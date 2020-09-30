/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.transition;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.TransitionValues;
import com.google.android.material.transition.FadeThroughProvider;
import com.google.android.material.transition.MaterialVisibility;
import com.google.android.material.transition.ScaleProvider;
import com.google.android.material.transition.SlideDistanceProvider;
import com.google.android.material.transition.VisibilityAnimatorProvider;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class MaterialSharedAxis
extends MaterialVisibility<VisibilityAnimatorProvider> {
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    private final int axis;
    private final boolean forward;

    public MaterialSharedAxis(int n, boolean bl) {
        super(MaterialSharedAxis.createPrimaryAnimatorProvider(n, bl), MaterialSharedAxis.createSecondaryAnimatorProvider());
        this.axis = n;
        this.forward = bl;
    }

    private static VisibilityAnimatorProvider createPrimaryAnimatorProvider(int n, boolean bl) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    return new ScaleProvider(bl);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid axis: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            if (bl) {
                n = 80;
                return new SlideDistanceProvider(n);
            }
            n = 48;
            return new SlideDistanceProvider(n);
        }
        if (bl) {
            n = 8388613;
            return new SlideDistanceProvider(n);
        }
        n = 8388611;
        return new SlideDistanceProvider(n);
    }

    private static VisibilityAnimatorProvider createSecondaryAnimatorProvider() {
        return new FadeThroughProvider();
    }

    public int getAxis() {
        return this.axis;
    }

    public boolean isForward() {
        return this.forward;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Axis {
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.transition.TransitionValues
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.transition.platform.FadeProvider;
import com.google.android.material.transition.platform.MaterialVisibility;
import com.google.android.material.transition.platform.ScaleProvider;
import com.google.android.material.transition.platform.VisibilityAnimatorProvider;

public final class MaterialElevationScale
extends MaterialVisibility<ScaleProvider> {
    private static final float DEFAULT_SCALE = 0.85f;
    private final boolean growing;

    public MaterialElevationScale(boolean bl) {
        super(MaterialElevationScale.createPrimaryAnimatorProvider(bl), MaterialElevationScale.createSecondaryAnimatorProvider());
        this.growing = bl;
    }

    private static ScaleProvider createPrimaryAnimatorProvider(boolean bl) {
        ScaleProvider scaleProvider = new ScaleProvider(bl);
        scaleProvider.setOutgoingEndScale(0.85f);
        scaleProvider.setIncomingStartScale(0.85f);
        return scaleProvider;
    }

    private static VisibilityAnimatorProvider createSecondaryAnimatorProvider() {
        return new FadeProvider();
    }

    public boolean isGrowing() {
        return this.growing;
    }
}


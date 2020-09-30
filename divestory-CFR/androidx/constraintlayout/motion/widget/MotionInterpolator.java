/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.animation.Interpolator
 */
package androidx.constraintlayout.motion.widget;

import android.view.animation.Interpolator;

public abstract class MotionInterpolator
implements Interpolator {
    public abstract float getInterpolation(float var1);

    public abstract float getVelocity();
}


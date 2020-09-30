/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.VelocityTracker
 */
package androidx.core.view;

import android.view.VelocityTracker;

@Deprecated
public final class VelocityTrackerCompat {
    private VelocityTrackerCompat() {
    }

    @Deprecated
    public static float getXVelocity(VelocityTracker velocityTracker, int n) {
        return velocityTracker.getXVelocity(n);
    }

    @Deprecated
    public static float getYVelocity(VelocityTracker velocityTracker, int n) {
        return velocityTracker.getYVelocity(n);
    }
}


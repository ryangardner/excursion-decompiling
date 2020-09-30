/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.material.shape;

import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;

public final class MarkerEdgeTreatment
extends EdgeTreatment {
    private final float radius;

    public MarkerEdgeTreatment(float f) {
        this.radius = f - 0.001f;
    }

    @Override
    boolean forceIntersection() {
        return true;
    }

    @Override
    public void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        f3 = (float)((double)this.radius * Math.sqrt(2.0) / 2.0);
        f = (float)Math.sqrt(Math.pow(this.radius, 2.0) - Math.pow(f3, 2.0));
        shapePath.reset(f2 - f3, (float)(-((double)this.radius * Math.sqrt(2.0) - (double)this.radius)) + f);
        shapePath.lineTo(f2, (float)(-((double)this.radius * Math.sqrt(2.0) - (double)this.radius)));
        shapePath.lineTo(f2 + f3, (float)(-((double)this.radius * Math.sqrt(2.0) - (double)this.radius)) + f);
    }
}


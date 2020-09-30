/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.material.shape;

import com.google.android.material.shape.ShapePath;

public class EdgeTreatment {
    boolean forceIntersection() {
        return false;
    }

    public void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        shapePath.lineTo(f, 0.0f);
    }

    @Deprecated
    public void getEdgePath(float f, float f2, ShapePath shapePath) {
        this.getEdgePath(f, f / 2.0f, f2, shapePath);
    }
}


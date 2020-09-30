/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.material.shape;

import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;

public class TriangleEdgeTreatment
extends EdgeTreatment {
    private final boolean inside;
    private final float size;

    public TriangleEdgeTreatment(float f, boolean bl) {
        this.size = f;
        this.inside = bl;
    }

    @Override
    public void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        shapePath.lineTo(f2 - this.size * f3, 0.0f);
        float f4 = this.inside ? this.size : -this.size;
        shapePath.lineTo(f2, f4 * f3);
        shapePath.lineTo(f2 + this.size * f3, 0.0f);
        shapePath.lineTo(f, 0.0f);
    }
}


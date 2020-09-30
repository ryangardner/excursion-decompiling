/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.material.shape;

import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.ShapePath;

public class CutCornerTreatment
extends CornerTreatment {
    float size = -1.0f;

    public CutCornerTreatment() {
    }

    @Deprecated
    public CutCornerTreatment(float f) {
        this.size = f;
    }

    @Override
    public void getCornerPath(ShapePath shapePath, float f, float f2, float f3) {
        shapePath.reset(0.0f, f3 * f2, 180.0f, 180.0f - f);
        double d = Math.sin(Math.toRadians(f));
        double d2 = f3;
        double d3 = f2;
        shapePath.lineTo((float)(d * d2 * d3), (float)(Math.sin(Math.toRadians(90.0f - f)) * d2 * d3));
    }
}


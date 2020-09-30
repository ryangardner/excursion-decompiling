/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.material.shape;

import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.ShapePath;

public class RoundedCornerTreatment
extends CornerTreatment {
    float radius = -1.0f;

    public RoundedCornerTreatment() {
    }

    @Deprecated
    public RoundedCornerTreatment(float f) {
        this.radius = f;
    }

    @Override
    public void getCornerPath(ShapePath shapePath, float f, float f2, float f3) {
        shapePath.reset(0.0f, f3 * f2, 180.0f, 180.0f - f);
        f2 = f3 * 2.0f * f2;
        shapePath.addArc(0.0f, 0.0f, f2, f2, 180.0f, f);
    }
}


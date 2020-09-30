/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.material.bottomappbar;

import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;

public class BottomAppBarTopEdgeTreatment
extends EdgeTreatment
implements Cloneable {
    private static final int ANGLE_LEFT = 180;
    private static final int ANGLE_UP = 270;
    private static final int ARC_HALF = 180;
    private static final int ARC_QUARTER = 90;
    private float cradleVerticalOffset;
    private float fabDiameter;
    private float fabMargin;
    private float horizontalOffset;
    private float roundedCornerRadius;

    public BottomAppBarTopEdgeTreatment(float f, float f2, float f3) {
        this.fabMargin = f;
        this.roundedCornerRadius = f2;
        this.setCradleVerticalOffset(f3);
        this.horizontalOffset = 0.0f;
    }

    float getCradleVerticalOffset() {
        return this.cradleVerticalOffset;
    }

    @Override
    public void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        float f4 = this.fabDiameter;
        if (f4 == 0.0f) {
            shapePath.lineTo(f, 0.0f);
            return;
        }
        f4 = (this.fabMargin * 2.0f + f4) / 2.0f;
        float f5 = f3 * this.roundedCornerRadius;
        f2 += this.horizontalOffset;
        if ((f3 = this.cradleVerticalOffset * f3 + (1.0f - f3) * f4) / f4 >= 1.0f) {
            shapePath.lineTo(f, 0.0f);
            return;
        }
        float f6 = f4 + f5;
        float f7 = f3 + f5;
        float f8 = (float)Math.sqrt(f6 * f6 - f7 * f7);
        f6 = f2 - f8;
        float f9 = f2 + f8;
        f8 = (float)Math.toDegrees(Math.atan(f8 / f7));
        float f10 = 90.0f - f8;
        shapePath.lineTo(f6, 0.0f);
        f7 = f5 * 2.0f;
        shapePath.addArc(f6 - f5, 0.0f, f6 + f5, f7, 270.0f, f8);
        shapePath.addArc(f2 - f4, -f4 - f3, f2 + f4, f4 - f3, 180.0f - f10, f10 * 2.0f - 180.0f);
        shapePath.addArc(f9 - f5, 0.0f, f9 + f5, f7, 270.0f - f8, f8);
        shapePath.lineTo(f, 0.0f);
    }

    float getFabCradleMargin() {
        return this.fabMargin;
    }

    float getFabCradleRoundedCornerRadius() {
        return this.roundedCornerRadius;
    }

    public float getFabDiameter() {
        return this.fabDiameter;
    }

    public float getHorizontalOffset() {
        return this.horizontalOffset;
    }

    void setCradleVerticalOffset(float f) {
        if (f < 0.0f) throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
        this.cradleVerticalOffset = f;
    }

    void setFabCradleMargin(float f) {
        this.fabMargin = f;
    }

    void setFabCradleRoundedCornerRadius(float f) {
        this.roundedCornerRadius = f;
    }

    public void setFabDiameter(float f) {
        this.fabDiameter = f;
    }

    void setHorizontalOffset(float f) {
        this.horizontalOffset = f;
    }
}


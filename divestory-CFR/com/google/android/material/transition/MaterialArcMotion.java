/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Path
 *  android.graphics.PointF
 */
package com.google.android.material.transition;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.transition.PathMotion;

public final class MaterialArcMotion
extends PathMotion {
    private static PointF getControlPoint(float f, float f2, float f3, float f4) {
        if (!(f2 > f4)) return new PointF(f, f4);
        return new PointF(f3, f2);
    }

    @Override
    public Path getPath(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(f, f2);
        PointF pointF = MaterialArcMotion.getControlPoint(f, f2, f3, f4);
        path.quadTo(pointF.x, pointF.y, f3, f4);
        return path;
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

public final class AlignmentPattern
extends ResultPoint {
    private final float estimatedModuleSize;

    AlignmentPattern(float f, float f2, float f3) {
        super(f, f2);
        this.estimatedModuleSize = f3;
    }

    boolean aboutEquals(float f, float f2, float f3) {
        boolean bl;
        f2 = Math.abs(f2 - this.getY());
        boolean bl2 = bl = false;
        if (!(f2 <= f)) return bl2;
        bl2 = bl;
        if (!(Math.abs(f3 - this.getX()) <= f)) return bl2;
        if ((f = Math.abs(f - this.estimatedModuleSize)) <= 1.0f) return true;
        bl2 = bl;
        if (!(f <= this.estimatedModuleSize)) return bl2;
        return true;
    }

    AlignmentPattern combineEstimate(float f, float f2, float f3) {
        return new AlignmentPattern((this.getX() + f2) / 2.0f, (this.getY() + f) / 2.0f, (this.estimatedModuleSize + f3) / 2.0f);
    }
}


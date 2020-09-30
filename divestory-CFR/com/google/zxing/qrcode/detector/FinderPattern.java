/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

public final class FinderPattern
extends ResultPoint {
    private final int count;
    private final float estimatedModuleSize;

    FinderPattern(float f, float f2, float f3) {
        this(f, f2, f3, 1);
    }

    private FinderPattern(float f, float f2, float f3, int n) {
        super(f, f2);
        this.estimatedModuleSize = f3;
        this.count = n;
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

    FinderPattern combineEstimate(float f, float f2, float f3) {
        int n = this.count;
        int n2 = n + 1;
        float f4 = n;
        float f5 = this.getX();
        float f6 = n2;
        return new FinderPattern((f4 * f5 + f2) / f6, ((float)this.count * this.getY() + f) / f6, ((float)this.count * this.estimatedModuleSize + f3) / f6, n2);
    }

    int getCount() {
        return this.count;
    }

    public float getEstimatedModuleSize() {
        return this.estimatedModuleSize;
    }
}


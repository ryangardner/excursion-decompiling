/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 */
package com.google.android.material.shape;

import android.graphics.RectF;
import com.google.android.material.shape.CornerSize;
import java.util.Arrays;

public final class AdjustedCornerSize
implements CornerSize {
    private final float adjustment;
    private final CornerSize other;

    public AdjustedCornerSize(float f, CornerSize cornerSize) {
        do {
            if (!(cornerSize instanceof AdjustedCornerSize)) {
                this.other = cornerSize;
                this.adjustment = f;
                return;
            }
            cornerSize = ((AdjustedCornerSize)cornerSize).other;
            f += ((AdjustedCornerSize)cornerSize).adjustment;
        } while (true);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof AdjustedCornerSize)) {
            return false;
        }
        object = (AdjustedCornerSize)object;
        if (!this.other.equals(((AdjustedCornerSize)object).other)) return false;
        if (this.adjustment != ((AdjustedCornerSize)object).adjustment) return false;
        return bl;
    }

    @Override
    public float getCornerSize(RectF rectF) {
        return Math.max(0.0f, this.other.getCornerSize(rectF) + this.adjustment);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.other, Float.valueOf(this.adjustment)});
    }
}


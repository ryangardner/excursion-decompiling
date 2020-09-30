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

public final class AbsoluteCornerSize
implements CornerSize {
    private final float size;

    public AbsoluteCornerSize(float f) {
        this.size = f;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof AbsoluteCornerSize)) {
            return false;
        }
        object = (AbsoluteCornerSize)object;
        if (this.size != ((AbsoluteCornerSize)object).size) return false;
        return bl;
    }

    public float getCornerSize() {
        return this.size;
    }

    @Override
    public float getCornerSize(RectF rectF) {
        return this.size;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.size)});
    }
}


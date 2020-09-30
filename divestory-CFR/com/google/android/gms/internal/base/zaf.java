/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 */
package com.google.android.gms.internal.base;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import com.google.android.gms.internal.base.zag;
import com.google.android.gms.internal.base.zai;

final class zaf
extends Drawable {
    private static final zaf zaa = new zaf();
    private static final zai zab = new zai(null);

    private zaf() {
    }

    static /* synthetic */ zaf zaa() {
        return zaa;
    }

    public final void draw(Canvas canvas) {
    }

    public final Drawable.ConstantState getConstantState() {
        return zab;
    }

    public final int getOpacity() {
        return -2;
    }

    public final void setAlpha(int n) {
    }

    public final void setColorFilter(ColorFilter colorFilter) {
    }
}


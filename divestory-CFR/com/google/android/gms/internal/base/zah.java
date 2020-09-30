/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 */
package com.google.android.gms.internal.base;

import android.graphics.drawable.Drawable;
import com.google.android.gms.internal.base.zae;

final class zah
extends Drawable.ConstantState {
    int zaa;
    int zab;

    zah(zah zah2) {
        if (zah2 == null) return;
        this.zaa = zah2.zaa;
        this.zab = zah2.zab;
    }

    public final int getChangingConfigurations() {
        return this.zaa;
    }

    public final Drawable newDrawable() {
        return new zae(this);
    }
}


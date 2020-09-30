/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 */
package com.google.android.gms.internal.base;

import android.graphics.drawable.Drawable;
import com.google.android.gms.internal.base.zaf;
import com.google.android.gms.internal.base.zag;

final class zai
extends Drawable.ConstantState {
    private zai() {
    }

    /* synthetic */ zai(zag zag2) {
        this();
    }

    public final int getChangingConfigurations() {
        return 0;
    }

    public final Drawable newDrawable() {
        return zaf.zaa();
    }
}


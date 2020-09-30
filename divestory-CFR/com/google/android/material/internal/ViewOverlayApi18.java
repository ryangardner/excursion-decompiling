/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.ViewOverlay
 */
package com.google.android.material.internal;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewOverlay;
import com.google.android.material.internal.ViewOverlayImpl;

class ViewOverlayApi18
implements ViewOverlayImpl {
    private final ViewOverlay viewOverlay;

    ViewOverlayApi18(View view) {
        this.viewOverlay = view.getOverlay();
    }

    @Override
    public void add(Drawable drawable2) {
        this.viewOverlay.add(drawable2);
    }

    @Override
    public void remove(Drawable drawable2) {
        this.viewOverlay.remove(drawable2);
    }
}


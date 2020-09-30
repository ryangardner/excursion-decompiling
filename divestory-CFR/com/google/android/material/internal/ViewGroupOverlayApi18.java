/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroupOverlay
 */
package com.google.android.material.internal;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import com.google.android.material.internal.ViewGroupOverlayImpl;

class ViewGroupOverlayApi18
implements ViewGroupOverlayImpl {
    private final ViewGroupOverlay viewGroupOverlay;

    ViewGroupOverlayApi18(ViewGroup viewGroup) {
        this.viewGroupOverlay = viewGroup.getOverlay();
    }

    @Override
    public void add(Drawable drawable2) {
        this.viewGroupOverlay.add(drawable2);
    }

    @Override
    public void add(View view) {
        this.viewGroupOverlay.add(view);
    }

    @Override
    public void remove(Drawable drawable2) {
        this.viewGroupOverlay.remove(drawable2);
    }

    @Override
    public void remove(View view) {
        this.viewGroupOverlay.remove(view);
    }
}


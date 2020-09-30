/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.internal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.internal.ViewGroupOverlayImpl;
import com.google.android.material.internal.ViewOverlayApi14;

class ViewGroupOverlayApi14
extends ViewOverlayApi14
implements ViewGroupOverlayImpl {
    ViewGroupOverlayApi14(Context context, ViewGroup viewGroup, View view) {
        super(context, viewGroup, view);
    }

    static ViewGroupOverlayApi14 createFrom(ViewGroup viewGroup) {
        return (ViewGroupOverlayApi14)ViewOverlayApi14.createFrom((View)viewGroup);
    }

    @Override
    public void add(View view) {
        this.overlayViewGroup.add(view);
    }

    @Override
    public void remove(View view) {
        this.overlayViewGroup.remove(view);
    }
}


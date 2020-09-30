/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.google.android.material.animation;

import android.view.View;

public interface TransformationCallback<T extends View> {
    public void onScaleChanged(T var1);

    public void onTranslationChanged(T var1);
}


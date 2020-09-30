/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.data;

import android.graphics.drawable.Drawable;

public abstract class BaseEntry {
    private Object mData = null;
    private Drawable mIcon = null;
    private float y = 0.0f;

    public BaseEntry() {
    }

    public BaseEntry(float f) {
        this.y = f;
    }

    public BaseEntry(float f, Drawable drawable2) {
        this(f);
        this.mIcon = drawable2;
    }

    public BaseEntry(float f, Drawable drawable2, Object object) {
        this(f);
        this.mIcon = drawable2;
        this.mData = object;
    }

    public BaseEntry(float f, Object object) {
        this(f);
        this.mData = object;
    }

    public Object getData() {
        return this.mData;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public float getY() {
        return this.y;
    }

    public void setData(Object object) {
        this.mData = object;
    }

    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
    }

    public void setY(float f) {
        this.y = f;
    }
}


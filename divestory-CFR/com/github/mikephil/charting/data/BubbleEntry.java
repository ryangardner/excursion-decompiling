/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.data;

import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.Entry;

public class BubbleEntry
extends Entry {
    private float mSize = 0.0f;

    public BubbleEntry(float f, float f2, float f3) {
        super(f, f2);
        this.mSize = f3;
    }

    public BubbleEntry(float f, float f2, float f3, Drawable drawable2) {
        super(f, f2, drawable2);
        this.mSize = f3;
    }

    public BubbleEntry(float f, float f2, float f3, Drawable drawable2, Object object) {
        super(f, f2, drawable2, object);
        this.mSize = f3;
    }

    public BubbleEntry(float f, float f2, float f3, Object object) {
        super(f, f2, object);
        this.mSize = f3;
    }

    @Override
    public BubbleEntry copy() {
        return new BubbleEntry(this.getX(), this.getY(), this.mSize, this.getData());
    }

    public float getSize() {
        return this.mSize;
    }

    public void setSize(float f) {
        this.mSize = f;
    }
}


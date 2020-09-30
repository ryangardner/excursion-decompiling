/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.Entry;

public class RadarEntry
extends Entry {
    public RadarEntry(float f) {
        super(0.0f, f);
    }

    public RadarEntry(float f, Object object) {
        super(0.0f, f, object);
    }

    @Override
    public RadarEntry copy() {
        return new RadarEntry(this.getY(), this.getData());
    }

    public float getValue() {
        return this.getY();
    }

    @Deprecated
    @Override
    public float getX() {
        return super.getX();
    }

    @Deprecated
    @Override
    public void setX(float f) {
        super.setX(f);
    }
}


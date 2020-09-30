/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.util.Log
 */
package com.github.mikephil.charting.data;

import android.graphics.drawable.Drawable;
import android.util.Log;
import com.github.mikephil.charting.data.Entry;

public class PieEntry
extends Entry {
    private String label;

    public PieEntry(float f) {
        super(0.0f, f);
    }

    public PieEntry(float f, Drawable drawable2) {
        super(0.0f, f, drawable2);
    }

    public PieEntry(float f, Drawable drawable2, Object object) {
        super(0.0f, f, drawable2, object);
    }

    public PieEntry(float f, Object object) {
        super(0.0f, f, object);
    }

    public PieEntry(float f, String string2) {
        super(0.0f, f);
        this.label = string2;
    }

    public PieEntry(float f, String string2, Drawable drawable2) {
        super(0.0f, f, drawable2);
        this.label = string2;
    }

    public PieEntry(float f, String string2, Drawable drawable2, Object object) {
        super(0.0f, f, drawable2, object);
        this.label = string2;
    }

    public PieEntry(float f, String string2, Object object) {
        super(0.0f, f, object);
        this.label = string2;
    }

    @Override
    public PieEntry copy() {
        return new PieEntry(this.getY(), this.label, this.getData());
    }

    public String getLabel() {
        return this.label;
    }

    public float getValue() {
        return this.getY();
    }

    @Deprecated
    @Override
    public float getX() {
        Log.i((String)"DEPRECATED", (String)"Pie entries do not have x values");
        return super.getX();
    }

    public void setLabel(String string2) {
        this.label = string2;
    }

    @Deprecated
    @Override
    public void setX(float f) {
        super.setX(f);
        Log.i((String)"DEPRECATED", (String)"Pie entries do not have x values");
    }
}


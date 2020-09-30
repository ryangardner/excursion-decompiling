/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.components.YAxis;

public class Highlight {
    private YAxis.AxisDependency axis;
    private int mDataIndex = -1;
    private int mDataSetIndex;
    private float mDrawX;
    private float mDrawY;
    private int mStackIndex = -1;
    private float mX = Float.NaN;
    private float mXPx;
    private float mY = Float.NaN;
    private float mYPx;

    public Highlight(float f, float f2, float f3, float f4, int n, int n2, YAxis.AxisDependency axisDependency) {
        this(f, f2, f3, f4, n, axisDependency);
        this.mStackIndex = n2;
    }

    public Highlight(float f, float f2, float f3, float f4, int n, YAxis.AxisDependency axisDependency) {
        this.mX = f;
        this.mY = f2;
        this.mXPx = f3;
        this.mYPx = f4;
        this.mDataSetIndex = n;
        this.axis = axisDependency;
    }

    public Highlight(float f, float f2, int n) {
        this.mX = f;
        this.mY = f2;
        this.mDataSetIndex = n;
    }

    public Highlight(float f, int n, int n2) {
        this(f, Float.NaN, n);
        this.mStackIndex = n2;
    }

    public boolean equalTo(Highlight highlight) {
        if (highlight == null) {
            return false;
        }
        if (this.mDataSetIndex != highlight.mDataSetIndex) return false;
        if (this.mX != highlight.mX) return false;
        if (this.mStackIndex != highlight.mStackIndex) return false;
        if (this.mDataIndex != highlight.mDataIndex) return false;
        return true;
    }

    public YAxis.AxisDependency getAxis() {
        return this.axis;
    }

    public int getDataIndex() {
        return this.mDataIndex;
    }

    public int getDataSetIndex() {
        return this.mDataSetIndex;
    }

    public float getDrawX() {
        return this.mDrawX;
    }

    public float getDrawY() {
        return this.mDrawY;
    }

    public int getStackIndex() {
        return this.mStackIndex;
    }

    public float getX() {
        return this.mX;
    }

    public float getXPx() {
        return this.mXPx;
    }

    public float getY() {
        return this.mY;
    }

    public float getYPx() {
        return this.mYPx;
    }

    public boolean isStacked() {
        if (this.mStackIndex < 0) return false;
        return true;
    }

    public void setDataIndex(int n) {
        this.mDataIndex = n;
    }

    public void setDraw(float f, float f2) {
        this.mDrawX = f;
        this.mDrawY = f2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Highlight, x: ");
        stringBuilder.append(this.mX);
        stringBuilder.append(", y: ");
        stringBuilder.append(this.mY);
        stringBuilder.append(", dataSetIndex: ");
        stringBuilder.append(this.mDataSetIndex);
        stringBuilder.append(", stackIndex (only stacked barentry): ");
        stringBuilder.append(this.mStackIndex);
        return stringBuilder.toString();
    }
}


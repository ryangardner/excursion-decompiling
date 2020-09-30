/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.data;

import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Range;

public class BarEntry
extends Entry {
    private float mNegativeSum;
    private float mPositiveSum;
    private Range[] mRanges;
    private float[] mYVals;

    public BarEntry(float f, float f2) {
        super(f, f2);
    }

    public BarEntry(float f, float f2, Drawable drawable2) {
        super(f, f2, drawable2);
    }

    public BarEntry(float f, float f2, Drawable drawable2, Object object) {
        super(f, f2, drawable2, object);
    }

    public BarEntry(float f, float f2, Object object) {
        super(f, f2, object);
    }

    public BarEntry(float f, float[] arrf) {
        super(f, BarEntry.calcSum(arrf));
        this.mYVals = arrf;
        this.calcPosNegSum();
        this.calcRanges();
    }

    public BarEntry(float f, float[] arrf, Drawable drawable2) {
        super(f, BarEntry.calcSum(arrf), drawable2);
        this.mYVals = arrf;
        this.calcPosNegSum();
        this.calcRanges();
    }

    public BarEntry(float f, float[] arrf, Drawable drawable2, Object object) {
        super(f, BarEntry.calcSum(arrf), drawable2, object);
        this.mYVals = arrf;
        this.calcPosNegSum();
        this.calcRanges();
    }

    public BarEntry(float f, float[] arrf, Object object) {
        super(f, BarEntry.calcSum(arrf), object);
        this.mYVals = arrf;
        this.calcPosNegSum();
        this.calcRanges();
    }

    private void calcPosNegSum() {
        float[] arrf = this.mYVals;
        if (arrf == null) {
            this.mNegativeSum = 0.0f;
            this.mPositiveSum = 0.0f;
            return;
        }
        int n = arrf.length;
        int n2 = 0;
        float f = 0.0f;
        float f2 = 0.0f;
        do {
            if (n2 >= n) {
                this.mNegativeSum = f;
                this.mPositiveSum = f2;
                return;
            }
            float f3 = arrf[n2];
            if (f3 <= 0.0f) {
                f += Math.abs(f3);
            } else {
                f2 += f3;
            }
            ++n2;
        } while (true);
    }

    private static float calcSum(float[] arrf) {
        float f = 0.0f;
        if (arrf == null) {
            return 0.0f;
        }
        int n = arrf.length;
        int n2 = 0;
        while (n2 < n) {
            f += arrf[n2];
            ++n2;
        }
        return f;
    }

    protected void calcRanges() {
        Range[] arrrange;
        float[] arrf = this.getYVals();
        if (arrf == null) return;
        if (arrf.length == 0) {
            return;
        }
        this.mRanges = new Range[arrf.length];
        float f = -this.getNegativeSum();
        int n = 0;
        float f2 = 0.0f;
        while (n < (arrrange = this.mRanges).length) {
            float f3 = arrf[n];
            if (f3 < 0.0f) {
                f3 = f - f3;
                arrrange[n] = new Range(f, f3);
                f = f3;
            } else {
                arrrange[n] = new Range(f2, f3 += f2);
                f2 = f3;
            }
            ++n;
        }
    }

    @Override
    public BarEntry copy() {
        BarEntry barEntry = new BarEntry(this.getX(), this.getY(), this.getData());
        barEntry.setVals(this.mYVals);
        return barEntry;
    }

    @Deprecated
    public float getBelowSum(int n) {
        return this.getSumBelow(n);
    }

    public float getNegativeSum() {
        return this.mNegativeSum;
    }

    public float getPositiveSum() {
        return this.mPositiveSum;
    }

    public Range[] getRanges() {
        return this.mRanges;
    }

    public float getSumBelow(int n) {
        float[] arrf = this.mYVals;
        float f = 0.0f;
        if (arrf == null) {
            return 0.0f;
        }
        int n2 = arrf.length - 1;
        while (n2 > n) {
            if (n2 < 0) return f;
            f += this.mYVals[n2];
            --n2;
        }
        return f;
    }

    @Override
    public float getY() {
        return super.getY();
    }

    public float[] getYVals() {
        return this.mYVals;
    }

    public boolean isStacked() {
        if (this.mYVals == null) return false;
        return true;
    }

    public void setVals(float[] arrf) {
        this.setY(BarEntry.calcSum(arrf));
        this.mYVals = arrf;
        this.calcPosNegSum();
        this.calcRanges();
    }
}


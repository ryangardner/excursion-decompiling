/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.data;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineScatterCandleRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet;
import com.github.mikephil.charting.utils.Utils;
import java.util.List;

public abstract class LineRadarDataSet<T extends Entry>
extends LineScatterCandleRadarDataSet<T>
implements ILineRadarDataSet<T> {
    private boolean mDrawFilled = false;
    private int mFillAlpha = 85;
    private int mFillColor = Color.rgb((int)140, (int)234, (int)255);
    protected Drawable mFillDrawable;
    private float mLineWidth = 2.5f;

    public LineRadarDataSet(List<T> list, String string2) {
        super(list, string2);
    }

    protected void copy(LineRadarDataSet lineRadarDataSet) {
        super.copy(lineRadarDataSet);
        lineRadarDataSet.mDrawFilled = this.mDrawFilled;
        lineRadarDataSet.mFillAlpha = this.mFillAlpha;
        lineRadarDataSet.mFillColor = this.mFillColor;
        lineRadarDataSet.mFillDrawable = this.mFillDrawable;
        lineRadarDataSet.mLineWidth = this.mLineWidth;
    }

    @Override
    public int getFillAlpha() {
        return this.mFillAlpha;
    }

    @Override
    public int getFillColor() {
        return this.mFillColor;
    }

    @Override
    public Drawable getFillDrawable() {
        return this.mFillDrawable;
    }

    @Override
    public float getLineWidth() {
        return this.mLineWidth;
    }

    @Override
    public boolean isDrawFilledEnabled() {
        return this.mDrawFilled;
    }

    @Override
    public void setDrawFilled(boolean bl) {
        this.mDrawFilled = bl;
    }

    public void setFillAlpha(int n) {
        this.mFillAlpha = n;
    }

    public void setFillColor(int n) {
        this.mFillColor = n;
        this.mFillDrawable = null;
    }

    public void setFillDrawable(Drawable drawable2) {
        this.mFillDrawable = drawable2;
    }

    public void setLineWidth(float f) {
        float f2 = f;
        if (f < 0.0f) {
            f2 = 0.0f;
        }
        f = f2;
        if (f2 > 10.0f) {
            f = 10.0f;
        }
        this.mLineWidth = Utils.convertDpToPixel(f);
    }
}


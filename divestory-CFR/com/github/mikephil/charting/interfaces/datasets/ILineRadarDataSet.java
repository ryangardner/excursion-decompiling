/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.interfaces.datasets;

import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;

public interface ILineRadarDataSet<T extends Entry>
extends ILineScatterCandleRadarDataSet<T> {
    public int getFillAlpha();

    public int getFillColor();

    public Drawable getFillDrawable();

    public float getLineWidth();

    public boolean isDrawFilledEnabled();

    public void setDrawFilled(boolean var1);
}


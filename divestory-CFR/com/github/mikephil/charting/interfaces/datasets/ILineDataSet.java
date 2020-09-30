/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.DashPathEffect
 */
package com.github.mikephil.charting.interfaces.datasets;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet;

public interface ILineDataSet
extends ILineRadarDataSet<Entry> {
    public int getCircleColor(int var1);

    public int getCircleColorCount();

    public int getCircleHoleColor();

    public float getCircleHoleRadius();

    public float getCircleRadius();

    public float getCubicIntensity();

    public DashPathEffect getDashPathEffect();

    public IFillFormatter getFillFormatter();

    public LineDataSet.Mode getMode();

    public boolean isDashedLineEnabled();

    public boolean isDrawCircleHoleEnabled();

    public boolean isDrawCirclesEnabled();

    @Deprecated
    public boolean isDrawCubicEnabled();

    @Deprecated
    public boolean isDrawSteppedEnabled();
}


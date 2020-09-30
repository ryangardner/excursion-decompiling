/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 */
package com.github.mikephil.charting.interfaces.dataprovider;

import android.graphics.RectF;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.MPPointF;

public interface ChartInterface {
    public MPPointF getCenterOfView();

    public MPPointF getCenterOffsets();

    public RectF getContentRect();

    public ChartData getData();

    public ValueFormatter getDefaultValueFormatter();

    public int getHeight();

    public float getMaxHighlightDistance();

    public int getMaxVisibleCount();

    public int getWidth();

    public float getXChartMax();

    public float getXChartMin();

    public float getXRange();

    public float getYChartMax();

    public float getYChartMin();
}


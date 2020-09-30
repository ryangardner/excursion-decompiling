/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 */
package com.github.mikephil.charting.interfaces.datasets;

import android.graphics.Paint;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;

public interface ICandleDataSet
extends ILineScatterCandleRadarDataSet<CandleEntry> {
    public float getBarSpace();

    public int getDecreasingColor();

    public Paint.Style getDecreasingPaintStyle();

    public int getIncreasingColor();

    public Paint.Style getIncreasingPaintStyle();

    public int getNeutralColor();

    public int getShadowColor();

    public boolean getShadowColorSameAsCandle();

    public float getShadowWidth();

    public boolean getShowCandleBar();
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.DashPathEffect
 */
package com.github.mikephil.charting.interfaces.datasets;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;

public interface ILineScatterCandleRadarDataSet<T extends Entry>
extends IBarLineScatterCandleBubbleDataSet<T> {
    public DashPathEffect getDashPathEffectHighlight();

    public float getHighlightLineWidth();

    public boolean isHorizontalHighlightIndicatorEnabled();

    public boolean isVerticalHighlightIndicatorEnabled();
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;

public interface BarDataProvider
extends BarLineScatterCandleBubbleDataProvider {
    public BarData getBarData();

    public boolean isDrawBarShadowEnabled();

    public boolean isDrawValueAboveBarEnabled();

    public boolean isHighlightFullBarEnabled();
}


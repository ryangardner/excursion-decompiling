/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ScatterDataProvider;

public interface CombinedDataProvider
extends LineDataProvider,
BarDataProvider,
BubbleDataProvider,
CandleDataProvider,
ScatterDataProvider {
    public CombinedData getCombinedData();
}


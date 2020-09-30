/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;

public interface LineDataProvider
extends BarLineScatterCandleBubbleDataProvider {
    public YAxis getAxis(YAxis.AxisDependency var1);

    public LineData getLineData();
}


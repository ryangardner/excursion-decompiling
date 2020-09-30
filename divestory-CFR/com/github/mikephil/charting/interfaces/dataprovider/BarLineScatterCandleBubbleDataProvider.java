/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider
extends ChartInterface {
    @Override
    public BarLineScatterCandleBubbleData getData();

    public float getHighestVisibleX();

    public float getLowestVisibleX();

    public Transformer getTransformer(YAxis.AxisDependency var1);

    public boolean isInverted(YAxis.AxisDependency var1);
}


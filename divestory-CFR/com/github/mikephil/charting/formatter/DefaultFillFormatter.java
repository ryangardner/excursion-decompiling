/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class DefaultFillFormatter
implements IFillFormatter {
    @Override
    public float getFillLinePosition(ILineDataSet iLineDataSet, LineDataProvider object) {
        float f = object.getYChartMax();
        float f2 = object.getYChartMin();
        object = object.getLineData();
        float f3 = iLineDataSet.getYMax();
        float f4 = 0.0f;
        if (f3 > 0.0f && iLineDataSet.getYMin() < 0.0f) {
            return f4;
        }
        if (((ChartData)object).getYMax() > 0.0f) {
            f = 0.0f;
        }
        if (((ChartData)object).getYMin() < 0.0f) {
            f2 = 0.0f;
        }
        if (!(iLineDataSet.getYMin() >= 0.0f)) return f;
        return f2;
    }
}


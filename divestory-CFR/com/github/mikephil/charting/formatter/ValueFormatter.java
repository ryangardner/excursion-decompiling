/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class ValueFormatter
implements IAxisValueFormatter,
IValueFormatter {
    public String getAxisLabel(float f, AxisBase axisBase) {
        return this.getFormattedValue(f);
    }

    public String getBarLabel(BarEntry barEntry) {
        return this.getFormattedValue(barEntry.getY());
    }

    public String getBarStackedLabel(float f, BarEntry barEntry) {
        return this.getFormattedValue(f);
    }

    public String getBubbleLabel(BubbleEntry bubbleEntry) {
        return this.getFormattedValue(bubbleEntry.getSize());
    }

    public String getCandleLabel(CandleEntry candleEntry) {
        return this.getFormattedValue(candleEntry.getHigh());
    }

    public String getFormattedValue(float f) {
        return String.valueOf(f);
    }

    @Deprecated
    @Override
    public String getFormattedValue(float f, AxisBase axisBase) {
        return this.getFormattedValue(f);
    }

    @Deprecated
    @Override
    public String getFormattedValue(float f, Entry entry, int n, ViewPortHandler viewPortHandler) {
        return this.getFormattedValue(f);
    }

    public String getPieLabel(float f, PieEntry pieEntry) {
        return this.getFormattedValue(f);
    }

    public String getPointLabel(Entry entry) {
        return this.getFormattedValue(entry.getY());
    }

    public String getRadarLabel(RadarEntry radarEntry) {
        return this.getFormattedValue(radarEntry.getY());
    }
}


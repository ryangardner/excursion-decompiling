/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import java.text.DecimalFormat;

public class PercentFormatter
extends ValueFormatter {
    public DecimalFormat mFormat = new DecimalFormat("###,###,##0.0");
    private PieChart pieChart;

    public PercentFormatter() {
    }

    public PercentFormatter(PieChart pieChart) {
        this();
        this.pieChart = pieChart;
    }

    @Override
    public String getFormattedValue(float f) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mFormat.format(f));
        stringBuilder.append(" %");
        return stringBuilder.toString();
    }

    @Override
    public String getPieLabel(float f, PieEntry object) {
        object = this.pieChart;
        if (object == null) return this.mFormat.format(f);
        if (!((PieChart)object).isUsePercentValuesEnabled()) return this.mFormat.format(f);
        return this.getFormattedValue(f);
    }
}


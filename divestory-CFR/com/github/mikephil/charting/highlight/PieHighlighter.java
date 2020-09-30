/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.PieRadarHighlighter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

public class PieHighlighter
extends PieRadarHighlighter<PieChart> {
    public PieHighlighter(PieChart pieChart) {
        super(pieChart);
    }

    @Override
    protected Highlight getClosestHighlight(int n, float f, float f2) {
        IPieDataSet iPieDataSet = ((PieData)((PieChart)this.mChart).getData()).getDataSet();
        Object t = iPieDataSet.getEntryForIndex(n);
        return new Highlight(n, ((BaseEntry)t).getY(), f, f2, 0, iPieDataSet.getAxisDependency());
    }
}


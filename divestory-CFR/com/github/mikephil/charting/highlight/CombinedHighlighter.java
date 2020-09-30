/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.highlight.BarHighlighter;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.CombinedDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.List;

public class CombinedHighlighter
extends ChartHighlighter<CombinedDataProvider>
implements IHighlighter {
    protected BarHighlighter barHighlighter;

    public CombinedHighlighter(CombinedDataProvider object, BarDataProvider barDataProvider) {
        super(object);
        object = barDataProvider.getBarData() == null ? null : new BarHighlighter(barDataProvider);
        this.barHighlighter = object;
    }

    @Override
    protected List<Highlight> getHighlightsAtXValue(float f, float f2, float f3) {
        this.mHighlightBuffer.clear();
        List<BarLineScatterCandleBubbleData> list = ((CombinedDataProvider)this.mChart).getCombinedData().getAllData();
        int n = 0;
        while (n < list.size()) {
            ChartData chartData = list.get(n);
            BarHighlighter barHighlighter = this.barHighlighter;
            if (barHighlighter != null && chartData instanceof BarData) {
                Highlight highlight = barHighlighter.getHighlight(f2, f3);
                if (highlight != null) {
                    highlight.setDataIndex(n);
                    this.mHighlightBuffer.add(highlight);
                }
            } else {
                int n2 = chartData.getDataSetCount();
                for (int i = 0; i < n2; ++i) {
                    Object t = list.get(n).getDataSetByIndex(i);
                    if (!t.isHighlightEnabled()) continue;
                    for (Highlight highlight : this.buildHighlights((IDataSet)t, i, f, DataSet.Rounding.CLOSEST)) {
                        highlight.setDataIndex(n);
                        this.mHighlightBuffer.add(highlight);
                    }
                }
            }
            ++n;
        }
        return this.mHighlightBuffer;
    }
}


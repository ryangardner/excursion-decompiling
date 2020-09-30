/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;

public class BarHighlighter
extends ChartHighlighter<BarDataProvider> {
    public BarHighlighter(BarDataProvider barDataProvider) {
        super(barDataProvider);
    }

    protected int getClosestStackIndex(Range[] arrrange, float f) {
        int n;
        int n2 = n = 0;
        if (arrrange == null) return n2;
        if (arrrange.length == 0) {
            return n;
        }
        int n3 = arrrange.length;
        n2 = 0;
        int n4 = 0;
        do {
            if (n2 >= n3) {
                n4 = Math.max(arrrange.length - 1, 0);
                n2 = n;
                if (!(f > arrrange[n4].to)) return n2;
                return n4;
            }
            if (arrrange[n2].contains(f)) {
                return n4;
            }
            ++n4;
            ++n2;
        } while (true);
    }

    @Override
    protected BarLineScatterCandleBubbleData getData() {
        return ((BarDataProvider)this.mChart).getBarData();
    }

    @Override
    protected float getDistance(float f, float f2, float f3, float f4) {
        return Math.abs(f - f3);
    }

    @Override
    public Highlight getHighlight(float f, float f2) {
        Highlight highlight = super.getHighlight(f, f2);
        if (highlight == null) {
            return null;
        }
        MPPointD mPPointD = this.getValsForTouch(f, f2);
        IBarDataSet iBarDataSet = (IBarDataSet)((BarDataProvider)this.mChart).getBarData().getDataSetByIndex(highlight.getDataSetIndex());
        if (iBarDataSet.isStacked()) {
            return this.getStackedHighlight(highlight, iBarDataSet, (float)mPPointD.x, (float)mPPointD.y);
        }
        MPPointD.recycleInstance(mPPointD);
        return highlight;
    }

    public Highlight getStackedHighlight(Highlight highlight, IBarDataSet object, float f, float f2) {
        BarEntry barEntry = (BarEntry)object.getEntryForXValue(f, f2);
        if (barEntry == null) {
            return null;
        }
        if (barEntry.getYVals() == null) {
            return highlight;
        }
        Range[] arrrange = barEntry.getRanges();
        if (arrrange.length <= 0) return null;
        int n = this.getClosestStackIndex(arrrange, f2);
        object = ((BarDataProvider)this.mChart).getTransformer(object.getAxisDependency()).getPixelForValues(highlight.getX(), arrrange[n].to);
        highlight = new Highlight(barEntry.getX(), barEntry.getY(), (float)((MPPointD)object).x, (float)((MPPointD)object).y, highlight.getDataSetIndex(), n, highlight.getAxis());
        MPPointD.recycleInstance((MPPointD)object);
        return highlight;
    }
}


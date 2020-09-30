/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.BarHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HorizontalBarHighlighter
extends BarHighlighter {
    public HorizontalBarHighlighter(BarDataProvider barDataProvider) {
        super(barDataProvider);
    }

    @Override
    protected List<Highlight> buildHighlights(IDataSet iDataSet, int n, float f, DataSet.Rounding object) {
        ArrayList<Highlight> arrayList = new ArrayList<Highlight>();
        Object object2 = iDataSet.getEntriesForXValue(f);
        List list = object2;
        if (object2.size() == 0) {
            object = iDataSet.getEntryForXValue(f, Float.NaN, (DataSet.Rounding)((Object)object));
            list = object2;
            if (object != null) {
                list = iDataSet.getEntriesForXValue(((Entry)object).getX());
            }
        }
        if (list.size() == 0) {
            return arrayList;
        }
        object2 = list.iterator();
        while (object2.hasNext()) {
            list = (Entry)object2.next();
            object = ((BarDataProvider)this.mChart).getTransformer(iDataSet.getAxisDependency()).getPixelForValues(((BaseEntry)((Object)list)).getY(), ((Entry)((Object)list)).getX());
            arrayList.add(new Highlight(((Entry)((Object)list)).getX(), ((BaseEntry)((Object)list)).getY(), (float)((MPPointD)object).x, (float)((MPPointD)object).y, n, iDataSet.getAxisDependency()));
        }
        return arrayList;
    }

    @Override
    protected float getDistance(float f, float f2, float f3, float f4) {
        return Math.abs(f2 - f4);
    }

    @Override
    public Highlight getHighlight(float f, float f2) {
        Object object = ((BarDataProvider)this.mChart).getBarData();
        MPPointD mPPointD = this.getValsForTouch(f2, f);
        Highlight highlight = this.getHighlightForX((float)mPPointD.y, f2, f);
        if (highlight == null) {
            return null;
        }
        if ((object = (IBarDataSet)((ChartData)object).getDataSetByIndex(highlight.getDataSetIndex())).isStacked()) {
            return this.getStackedHighlight(highlight, (IBarDataSet)object, (float)mPPointD.y, (float)mPPointD.x);
        }
        MPPointD.recycleInstance(mPPointD);
        return highlight;
    }
}


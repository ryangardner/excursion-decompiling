/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import java.util.List;

public class PieData
extends ChartData<IPieDataSet> {
    public PieData() {
    }

    public PieData(IPieDataSet iPieDataSet) {
        super((IDataSet[])new IPieDataSet[]{iPieDataSet});
    }

    public IPieDataSet getDataSet() {
        return (IPieDataSet)this.mDataSets.get(0);
    }

    @Override
    public IPieDataSet getDataSetByIndex(int n) {
        if (n != 0) return null;
        return this.getDataSet();
    }

    @Override
    public IPieDataSet getDataSetByLabel(String string2, boolean bl) {
        IPieDataSet iPieDataSet = null;
        if (bl) {
            if (!string2.equalsIgnoreCase(((IPieDataSet)this.mDataSets.get(0)).getLabel())) return iPieDataSet;
            return (IPieDataSet)this.mDataSets.get(0);
        }
        if (!string2.equals(((IPieDataSet)this.mDataSets.get(0)).getLabel())) return iPieDataSet;
        return (IPieDataSet)this.mDataSets.get(0);
    }

    @Override
    public Entry getEntryForHighlight(Highlight highlight) {
        return this.getDataSet().getEntryForIndex((int)highlight.getX());
    }

    public float getYValueSum() {
        float f = 0.0f;
        int n = 0;
        while (n < this.getDataSet().getEntryCount()) {
            f += ((PieEntry)this.getDataSet().getEntryForIndex(n)).getY();
            ++n;
        }
        return f;
    }

    public void setDataSet(IPieDataSet iPieDataSet) {
        this.mDataSets.clear();
        this.mDataSets.add(iPieDataSet);
        this.notifyDataChanged();
    }
}


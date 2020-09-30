/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import java.util.Arrays;
import java.util.List;

public class RadarData
extends ChartData<IRadarDataSet> {
    private List<String> mLabels;

    public RadarData() {
    }

    public RadarData(List<IRadarDataSet> list) {
        super(list);
    }

    public RadarData(IRadarDataSet ... arriRadarDataSet) {
        super((IDataSet[])arriRadarDataSet);
    }

    @Override
    public Entry getEntryForHighlight(Highlight highlight) {
        return ((IRadarDataSet)this.getDataSetByIndex(highlight.getDataSetIndex())).getEntryForIndex((int)highlight.getX());
    }

    public List<String> getLabels() {
        return this.mLabels;
    }

    public void setLabels(List<String> list) {
        this.mLabels = list;
    }

    public void setLabels(String ... arrstring) {
        this.mLabels = Arrays.asList(arrstring);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package com.github.mikephil.charting.data;

import android.graphics.Color;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import java.util.ArrayList;
import java.util.List;

public class BarDataSet
extends BarLineScatterCandleBubbleDataSet<BarEntry>
implements IBarDataSet {
    private int mBarBorderColor = -16777216;
    private float mBarBorderWidth = 0.0f;
    private int mBarShadowColor = Color.rgb((int)215, (int)215, (int)215);
    private int mEntryCountStacks = 0;
    private int mHighLightAlpha = 120;
    private String[] mStackLabels = new String[]{"Stack"};
    private int mStackSize = 1;

    public BarDataSet(List<BarEntry> list, String string2) {
        super(list, string2);
        this.mHighLightColor = Color.rgb((int)0, (int)0, (int)0);
        this.calcStackSize(list);
        this.calcEntryCountIncludingStacks(list);
    }

    private void calcEntryCountIncludingStacks(List<BarEntry> list) {
        int n = 0;
        this.mEntryCountStacks = 0;
        while (n < list.size()) {
            float[] arrf = list.get(n).getYVals();
            this.mEntryCountStacks = arrf == null ? ++this.mEntryCountStacks : (this.mEntryCountStacks += arrf.length);
            ++n;
        }
    }

    private void calcStackSize(List<BarEntry> list) {
        int n = 0;
        while (n < list.size()) {
            float[] arrf = list.get(n).getYVals();
            if (arrf != null && arrf.length > this.mStackSize) {
                this.mStackSize = arrf.length;
            }
            ++n;
        }
    }

    @Override
    protected void calcMinMax(BarEntry barEntry) {
        if (barEntry == null) return;
        if (Float.isNaN(barEntry.getY())) return;
        if (barEntry.getYVals() == null) {
            if (barEntry.getY() < this.mYMin) {
                this.mYMin = barEntry.getY();
            }
            if (barEntry.getY() > this.mYMax) {
                this.mYMax = barEntry.getY();
            }
        } else {
            if (-barEntry.getNegativeSum() < this.mYMin) {
                this.mYMin = -barEntry.getNegativeSum();
            }
            if (barEntry.getPositiveSum() > this.mYMax) {
                this.mYMax = barEntry.getPositiveSum();
            }
        }
        this.calcMinMaxX(barEntry);
    }

    @Override
    public DataSet<BarEntry> copy() {
        Object object = new ArrayList<BarEntry>();
        int n = 0;
        do {
            if (n >= this.mValues.size()) {
                object = new BarDataSet((List<BarEntry>)object, this.getLabel());
                this.copy((BarDataSet)object);
                return object;
            }
            object.add((BarEntry)((BarEntry)this.mValues.get(n)).copy());
            ++n;
        } while (true);
    }

    protected void copy(BarDataSet barDataSet) {
        super.copy(barDataSet);
        barDataSet.mStackSize = this.mStackSize;
        barDataSet.mBarShadowColor = this.mBarShadowColor;
        barDataSet.mBarBorderWidth = this.mBarBorderWidth;
        barDataSet.mStackLabels = this.mStackLabels;
        barDataSet.mHighLightAlpha = this.mHighLightAlpha;
    }

    @Override
    public int getBarBorderColor() {
        return this.mBarBorderColor;
    }

    @Override
    public float getBarBorderWidth() {
        return this.mBarBorderWidth;
    }

    @Override
    public int getBarShadowColor() {
        return this.mBarShadowColor;
    }

    public int getEntryCountStacks() {
        return this.mEntryCountStacks;
    }

    @Override
    public int getHighLightAlpha() {
        return this.mHighLightAlpha;
    }

    @Override
    public String[] getStackLabels() {
        return this.mStackLabels;
    }

    @Override
    public int getStackSize() {
        return this.mStackSize;
    }

    @Override
    public boolean isStacked() {
        int n = this.mStackSize;
        boolean bl = true;
        if (n <= 1) return false;
        return bl;
    }

    public void setBarBorderColor(int n) {
        this.mBarBorderColor = n;
    }

    public void setBarBorderWidth(float f) {
        this.mBarBorderWidth = f;
    }

    public void setBarShadowColor(int n) {
        this.mBarShadowColor = n;
    }

    public void setHighLightAlpha(int n) {
        this.mHighLightAlpha = n;
    }

    public void setStackLabels(String[] arrstring) {
        this.mStackLabels = arrstring;
    }
}


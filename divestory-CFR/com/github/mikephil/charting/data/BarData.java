/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import java.util.List;

public class BarData
extends BarLineScatterCandleBubbleData<IBarDataSet> {
    private float mBarWidth = 0.85f;

    public BarData() {
    }

    public BarData(List<IBarDataSet> list) {
        super(list);
    }

    public BarData(IBarDataSet ... arriBarDataSet) {
        super((IBarLineScatterCandleBubbleDataSet[])arriBarDataSet);
    }

    public float getBarWidth() {
        return this.mBarWidth;
    }

    public float getGroupWidth(float f, float f2) {
        return (float)this.mDataSets.size() * (this.mBarWidth + f2) + f;
    }

    public void groupBars(float f, float f2, float f3) {
        if (this.mDataSets.size() <= 1) throw new RuntimeException("BarData needs to hold at least 2 BarDataSets to allow grouping.");
        int n = ((IBarDataSet)this.getMaxEntryCountSet()).getEntryCount();
        float f4 = f2 / 2.0f;
        float f5 = f3 / 2.0f;
        float f6 = this.mBarWidth / 2.0f;
        f3 = this.getGroupWidth(f2, f3);
        int n2 = 0;
        do {
            block8 : {
                float f7;
                block7 : {
                    if (n2 >= n) {
                        this.notifyDataChanged();
                        return;
                    }
                    f2 = f + f4;
                    for (Object object : this.mDataSets) {
                        f2 = f2 + f5 + f6;
                        if (n2 < object.getEntryCount() && (object = (BarEntry)object.getEntryForIndex(n2)) != null) {
                            ((Entry)object).setX(f2);
                        }
                        f2 = f2 + f6 + f5;
                    }
                    f7 = f3 - ((f2 += f4) - f);
                    if (f7 > 0.0f) break block7;
                    f = f2;
                    if (!(f7 < 0.0f)) break block8;
                }
                f = f2 + f7;
            }
            ++n2;
        } while (true);
    }

    public void setBarWidth(float f) {
        this.mBarWidth = f;
    }
}


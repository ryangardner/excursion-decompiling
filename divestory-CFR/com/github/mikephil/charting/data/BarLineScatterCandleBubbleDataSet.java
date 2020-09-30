/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package com.github.mikephil.charting.data;

import android.graphics.Color;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import java.util.List;

public abstract class BarLineScatterCandleBubbleDataSet<T extends Entry>
extends DataSet<T>
implements IBarLineScatterCandleBubbleDataSet<T> {
    protected int mHighLightColor = Color.rgb((int)255, (int)187, (int)115);

    public BarLineScatterCandleBubbleDataSet(List<T> list, String string2) {
        super(list, string2);
    }

    protected void copy(BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet) {
        super.copy(barLineScatterCandleBubbleDataSet);
        barLineScatterCandleBubbleDataSet.mHighLightColor = this.mHighLightColor;
    }

    @Override
    public int getHighLightColor() {
        return this.mHighLightColor;
    }

    public void setHighLightColor(int n) {
        this.mHighLightColor = n;
    }
}


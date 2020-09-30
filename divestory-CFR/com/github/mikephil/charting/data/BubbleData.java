/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import java.util.Iterator;
import java.util.List;

public class BubbleData
extends BarLineScatterCandleBubbleData<IBubbleDataSet> {
    public BubbleData() {
    }

    public BubbleData(List<IBubbleDataSet> list) {
        super(list);
    }

    public BubbleData(IBubbleDataSet ... arriBubbleDataSet) {
        super((IBarLineScatterCandleBubbleDataSet[])arriBubbleDataSet);
    }

    public void setHighlightCircleWidth(float f) {
        Iterator iterator2 = this.mDataSets.iterator();
        while (iterator2.hasNext()) {
            ((IBubbleDataSet)iterator2.next()).setHighlightCircleWidth(f);
        }
    }
}


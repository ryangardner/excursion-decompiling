/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import java.util.Iterator;
import java.util.List;

public class ScatterData
extends BarLineScatterCandleBubbleData<IScatterDataSet> {
    public ScatterData() {
    }

    public ScatterData(List<IScatterDataSet> list) {
        super(list);
    }

    public ScatterData(IScatterDataSet ... arriScatterDataSet) {
        super((IBarLineScatterCandleBubbleDataSet[])arriScatterDataSet);
    }

    public float getGreatestShapeSize() {
        Iterator iterator2 = this.mDataSets.iterator();
        float f = 0.0f;
        while (iterator2.hasNext()) {
            float f2 = ((IScatterDataSet)iterator2.next()).getScatterShapeSize();
            if (!(f2 > f)) continue;
            f = f2;
        }
        return f;
    }
}


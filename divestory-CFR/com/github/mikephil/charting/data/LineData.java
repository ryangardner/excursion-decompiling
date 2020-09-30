/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.List;

public class LineData
extends BarLineScatterCandleBubbleData<ILineDataSet> {
    public LineData() {
    }

    public LineData(List<ILineDataSet> list) {
        super(list);
    }

    public LineData(ILineDataSet ... arriLineDataSet) {
        super((IBarLineScatterCandleBubbleDataSet[])arriLineDataSet);
    }
}


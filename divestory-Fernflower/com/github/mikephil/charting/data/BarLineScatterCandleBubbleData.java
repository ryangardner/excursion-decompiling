package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.List;

public abstract class BarLineScatterCandleBubbleData<T extends IBarLineScatterCandleBubbleDataSet<? extends Entry>> extends ChartData<T> {
   public BarLineScatterCandleBubbleData() {
   }

   public BarLineScatterCandleBubbleData(List<T> var1) {
      super(var1);
   }

   public BarLineScatterCandleBubbleData(T... var1) {
      super((IDataSet[])var1);
   }
}

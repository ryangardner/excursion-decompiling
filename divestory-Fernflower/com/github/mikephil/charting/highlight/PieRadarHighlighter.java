package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import java.util.ArrayList;
import java.util.List;

public abstract class PieRadarHighlighter<T extends PieRadarChartBase> implements IHighlighter {
   protected T mChart;
   protected List<Highlight> mHighlightBuffer = new ArrayList();

   public PieRadarHighlighter(T var1) {
      this.mChart = var1;
   }

   protected abstract Highlight getClosestHighlight(int var1, float var2, float var3);

   public Highlight getHighlight(float var1, float var2) {
      if (this.mChart.distanceToCenter(var1, var2) > this.mChart.getRadius()) {
         return null;
      } else {
         float var3 = this.mChart.getAngleForPoint(var1, var2);
         PieRadarChartBase var4 = this.mChart;
         float var5 = var3;
         if (var4 instanceof PieChart) {
            var5 = var3 / var4.getAnimator().getPhaseY();
         }

         int var6 = this.mChart.getIndexForAngle(var5);
         return var6 >= 0 && var6 < this.mChart.getData().getMaxEntryCountSet().getEntryCount() ? this.getClosestHighlight(var6, var1, var2) : null;
      }
   }
}

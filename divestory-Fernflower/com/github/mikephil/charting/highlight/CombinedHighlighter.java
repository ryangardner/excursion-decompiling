package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.CombinedDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.Iterator;
import java.util.List;

public class CombinedHighlighter extends ChartHighlighter<CombinedDataProvider> implements IHighlighter {
   protected BarHighlighter barHighlighter;

   public CombinedHighlighter(CombinedDataProvider var1, BarDataProvider var2) {
      super(var1);
      BarHighlighter var3;
      if (var2.getBarData() == null) {
         var3 = null;
      } else {
         var3 = new BarHighlighter(var2);
      }

      this.barHighlighter = var3;
   }

   protected List<Highlight> getHighlightsAtXValue(float var1, float var2, float var3) {
      this.mHighlightBuffer.clear();
      List var4 = ((CombinedDataProvider)this.mChart).getCombinedData().getAllData();

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         ChartData var6 = (ChartData)var4.get(var5);
         BarHighlighter var7 = this.barHighlighter;
         Highlight var11;
         if (var7 != null && var6 instanceof BarData) {
            var11 = var7.getHighlight(var2, var3);
            if (var11 != null) {
               var11.setDataIndex(var5);
               this.mHighlightBuffer.add(var11);
            }
         } else {
            int var8 = var6.getDataSetCount();

            for(int var9 = 0; var9 < var8; ++var9) {
               IDataSet var10 = ((BarLineScatterCandleBubbleData)var4.get(var5)).getDataSetByIndex(var9);
               if (var10.isHighlightEnabled()) {
                  Iterator var12 = this.buildHighlights(var10, var9, var1, DataSet.Rounding.CLOSEST).iterator();

                  while(var12.hasNext()) {
                     var11 = (Highlight)var12.next();
                     var11.setDataIndex(var5);
                     this.mHighlightBuffer.add(var11);
                  }
               }
            }
         }
      }

      return this.mHighlightBuffer;
   }
}

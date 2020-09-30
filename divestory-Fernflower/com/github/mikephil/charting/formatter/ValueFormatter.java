package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class ValueFormatter implements IAxisValueFormatter, IValueFormatter {
   public String getAxisLabel(float var1, AxisBase var2) {
      return this.getFormattedValue(var1);
   }

   public String getBarLabel(BarEntry var1) {
      return this.getFormattedValue(var1.getY());
   }

   public String getBarStackedLabel(float var1, BarEntry var2) {
      return this.getFormattedValue(var1);
   }

   public String getBubbleLabel(BubbleEntry var1) {
      return this.getFormattedValue(var1.getSize());
   }

   public String getCandleLabel(CandleEntry var1) {
      return this.getFormattedValue(var1.getHigh());
   }

   public String getFormattedValue(float var1) {
      return String.valueOf(var1);
   }

   @Deprecated
   public String getFormattedValue(float var1, AxisBase var2) {
      return this.getFormattedValue(var1);
   }

   @Deprecated
   public String getFormattedValue(float var1, Entry var2, int var3, ViewPortHandler var4) {
      return this.getFormattedValue(var1);
   }

   public String getPieLabel(float var1, PieEntry var2) {
      return this.getFormattedValue(var1);
   }

   public String getPointLabel(Entry var1) {
      return this.getFormattedValue(var1.getY());
   }

   public String getRadarLabel(RadarEntry var1) {
      return this.getFormattedValue(var1.getY());
   }
}

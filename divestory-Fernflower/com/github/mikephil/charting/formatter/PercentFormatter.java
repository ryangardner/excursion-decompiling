package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import java.text.DecimalFormat;

public class PercentFormatter extends ValueFormatter {
   public DecimalFormat mFormat;
   private PieChart pieChart;

   public PercentFormatter() {
      this.mFormat = new DecimalFormat("###,###,##0.0");
   }

   public PercentFormatter(PieChart var1) {
      this();
      this.pieChart = var1;
   }

   public String getFormattedValue(float var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(this.mFormat.format((double)var1));
      var2.append(" %");
      return var2.toString();
   }

   public String getPieLabel(float var1, PieEntry var2) {
      PieChart var3 = this.pieChart;
      return var3 != null && var3.isUsePercentValuesEnabled() ? this.getFormattedValue(var1) : this.mFormat.format((double)var1);
   }
}

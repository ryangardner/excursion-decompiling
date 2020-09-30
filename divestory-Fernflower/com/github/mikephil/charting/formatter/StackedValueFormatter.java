package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.data.BarEntry;
import java.text.DecimalFormat;

public class StackedValueFormatter extends ValueFormatter {
   private boolean mDrawWholeStack;
   private DecimalFormat mFormat;
   private String mSuffix;

   public StackedValueFormatter(boolean var1, String var2, int var3) {
      this.mDrawWholeStack = var1;
      this.mSuffix = var2;
      StringBuffer var4 = new StringBuffer();

      for(int var5 = 0; var5 < var3; ++var5) {
         if (var5 == 0) {
            var4.append(".");
         }

         var4.append("0");
      }

      StringBuilder var6 = new StringBuilder();
      var6.append("###,###,###,##0");
      var6.append(var4.toString());
      this.mFormat = new DecimalFormat(var6.toString());
   }

   public String getBarStackedLabel(float var1, BarEntry var2) {
      if (!this.mDrawWholeStack) {
         float[] var3 = var2.getYVals();
         if (var3 != null) {
            if (var3[var3.length - 1] == var1) {
               StringBuilder var5 = new StringBuilder();
               var5.append(this.mFormat.format((double)var2.getY()));
               var5.append(this.mSuffix);
               return var5.toString();
            }

            return "";
         }
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(this.mFormat.format((double)var1));
      var4.append(this.mSuffix);
      return var4.toString();
   }
}

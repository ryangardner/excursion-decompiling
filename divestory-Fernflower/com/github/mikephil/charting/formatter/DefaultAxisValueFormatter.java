package com.github.mikephil.charting.formatter;

import java.text.DecimalFormat;

public class DefaultAxisValueFormatter extends ValueFormatter {
   protected int digits;
   protected DecimalFormat mFormat;

   public DefaultAxisValueFormatter(int var1) {
      this.digits = var1;
      StringBuffer var2 = new StringBuffer();

      for(int var3 = 0; var3 < var1; ++var3) {
         if (var3 == 0) {
            var2.append(".");
         }

         var2.append("0");
      }

      StringBuilder var4 = new StringBuilder();
      var4.append("###,###,###,##0");
      var4.append(var2.toString());
      this.mFormat = new DecimalFormat(var4.toString());
   }

   public int getDecimalDigits() {
      return this.digits;
   }

   public String getFormattedValue(float var1) {
      return this.mFormat.format((double)var1);
   }
}

package com.github.mikephil.charting.formatter;

import java.text.DecimalFormat;

public class DefaultValueFormatter extends ValueFormatter {
   protected int mDecimalDigits;
   protected DecimalFormat mFormat;

   public DefaultValueFormatter(int var1) {
      this.setup(var1);
   }

   public int getDecimalDigits() {
      return this.mDecimalDigits;
   }

   public String getFormattedValue(float var1) {
      return this.mFormat.format((double)var1);
   }

   public void setup(int var1) {
      this.mDecimalDigits = var1;
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
}

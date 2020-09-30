package com.github.mikephil.charting.formatter;

import java.util.Collection;

public class IndexAxisValueFormatter extends ValueFormatter {
   private int mValueCount = 0;
   private String[] mValues = new String[0];

   public IndexAxisValueFormatter() {
   }

   public IndexAxisValueFormatter(Collection<String> var1) {
      if (var1 != null) {
         this.setValues((String[])var1.toArray(new String[var1.size()]));
      }

   }

   public IndexAxisValueFormatter(String[] var1) {
      if (var1 != null) {
         this.setValues(var1);
      }

   }

   public String getFormattedValue(float var1) {
      int var2 = Math.round(var1);
      return var2 >= 0 && var2 < this.mValueCount && var2 == (int)var1 ? this.mValues[var2] : "";
   }

   public String[] getValues() {
      return this.mValues;
   }

   public void setValues(String[] var1) {
      String[] var2 = var1;
      if (var1 == null) {
         var2 = new String[0];
      }

      this.mValues = var2;
      this.mValueCount = var2.length;
   }
}

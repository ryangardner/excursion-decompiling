package com.github.mikephil.charting.formatter;

import java.text.DecimalFormat;

public class LargeValueFormatter extends ValueFormatter {
   private DecimalFormat mFormat;
   private int mMaxLength;
   private String[] mSuffix;
   private String mText;

   public LargeValueFormatter() {
      this.mSuffix = new String[]{"", "k", "m", "b", "t"};
      this.mMaxLength = 5;
      this.mText = "";
      this.mFormat = new DecimalFormat("###E00");
   }

   public LargeValueFormatter(String var1) {
      this();
      this.mText = var1;
   }

   private String makePretty(double var1) {
      String var3 = this.mFormat.format(var1);
      int var4 = Character.getNumericValue(var3.charAt(var3.length() - 1));
      int var5 = Character.getNumericValue(var3.charAt(var3.length() - 2));
      StringBuilder var6 = new StringBuilder();
      var6.append(var5);
      var6.append("");
      var6.append(var4);
      var5 = Integer.valueOf(var6.toString());

      for(var3 = var3.replaceAll("E[0-9][0-9]", this.mSuffix[var5 / 3]); var3.length() > this.mMaxLength || var3.matches("[0-9]+\\.[a-z]"); var3 = var6.toString()) {
         var6 = new StringBuilder();
         var6.append(var3.substring(0, var3.length() - 2));
         var6.append(var3.substring(var3.length() - 1));
      }

      return var3;
   }

   public int getDecimalDigits() {
      return 0;
   }

   public String getFormattedValue(float var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(this.makePretty((double)var1));
      var2.append(this.mText);
      return var2.toString();
   }

   public void setAppendix(String var1) {
      this.mText = var1;
   }

   public void setMaxLength(int var1) {
      this.mMaxLength = var1;
   }

   public void setSuffix(String[] var1) {
      this.mSuffix = var1;
   }
}

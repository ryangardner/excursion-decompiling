package com.github.mikephil.charting.model;

public class GradientColor {
   private int endColor;
   private int startColor;

   public GradientColor(int var1, int var2) {
      this.startColor = var1;
      this.endColor = var2;
   }

   public int getEndColor() {
      return this.endColor;
   }

   public int getStartColor() {
      return this.startColor;
   }

   public void setEndColor(int var1) {
      this.endColor = var1;
   }

   public void setStartColor(int var1) {
      this.startColor = var1;
   }
}

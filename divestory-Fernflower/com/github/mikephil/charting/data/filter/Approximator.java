package com.github.mikephil.charting.data.filter;

import java.util.Arrays;

public class Approximator {
   float[] concat(float[]... var1) {
      int var2 = var1.length;
      int var3 = 0;

      int var4;
      for(var4 = 0; var3 < var2; ++var3) {
         var4 += var1[var3].length;
      }

      float[] var5 = new float[var4];
      int var6 = var1.length;
      var3 = 0;

      for(var4 = 0; var3 < var6; ++var3) {
         float[] var7 = var1[var3];
         int var8 = var7.length;

         for(var2 = 0; var2 < var8; ++var2) {
            var5[var4] = var7[var2];
            ++var4;
         }
      }

      return var5;
   }

   public float[] reduceWithDouglasPeucker(float[] var1, float var2) {
      Approximator.Line var3 = new Approximator.Line(var1[0], var1[1], var1[var1.length - 2], var1[var1.length - 1]);
      float var4 = 0.0F;
      int var5 = 2;

      int var6;
      float var8;
      for(var6 = 0; var5 < var1.length - 2; var4 = var8) {
         float var7 = var3.distance(var1[var5], var1[var5 + 1]);
         var8 = var4;
         if (var7 > var4) {
            var6 = var5;
            var8 = var7;
         }

         var5 += 2;
      }

      if (var4 > var2) {
         float[] var9 = this.reduceWithDouglasPeucker(Arrays.copyOfRange(var1, 0, var6 + 2), var2);
         var1 = this.reduceWithDouglasPeucker(Arrays.copyOfRange(var1, var6, var1.length), var2);
         return this.concat(var9, Arrays.copyOfRange(var1, 2, var1.length));
      } else {
         return var3.getPoints();
      }
   }

   private class Line {
      private float dx;
      private float dy;
      private float exsy;
      private float length;
      private float[] points;
      private float sxey;

      public Line(float var2, float var3, float var4, float var5) {
         float var6 = var2 - var4;
         this.dx = var6;
         float var7 = var3 - var5;
         this.dy = var7;
         this.sxey = var2 * var5;
         this.exsy = var4 * var3;
         this.length = (float)Math.sqrt((double)(var6 * var6 + var7 * var7));
         this.points = new float[]{var2, var3, var4, var5};
      }

      public float distance(float var1, float var2) {
         return Math.abs(this.dy * var1 - this.dx * var2 + this.sxey - this.exsy) / this.length;
      }

      public float[] getPoints() {
         return this.points;
      }
   }
}

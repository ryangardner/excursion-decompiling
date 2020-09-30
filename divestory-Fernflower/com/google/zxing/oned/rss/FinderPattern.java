package com.google.zxing.oned.rss;

import com.google.zxing.ResultPoint;

public final class FinderPattern {
   private final ResultPoint[] resultPoints;
   private final int[] startEnd;
   private final int value;

   public FinderPattern(int var1, int[] var2, int var3, int var4, int var5) {
      this.value = var1;
      this.startEnd = var2;
      float var6 = (float)var3;
      float var7 = (float)var5;
      this.resultPoints = new ResultPoint[]{new ResultPoint(var6, var7), new ResultPoint((float)var4, var7)};
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof FinderPattern;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         FinderPattern var4 = (FinderPattern)var1;
         if (this.value == var4.value) {
            var3 = true;
         }

         return var3;
      }
   }

   public ResultPoint[] getResultPoints() {
      return this.resultPoints;
   }

   public int[] getStartEnd() {
      return this.startEnd;
   }

   public int getValue() {
      return this.value;
   }

   public int hashCode() {
      return this.value;
   }
}

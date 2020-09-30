package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

public final class FinderPattern extends ResultPoint {
   private final int count;
   private final float estimatedModuleSize;

   FinderPattern(float var1, float var2, float var3) {
      this(var1, var2, var3, 1);
   }

   private FinderPattern(float var1, float var2, float var3, int var4) {
      super(var1, var2);
      this.estimatedModuleSize = var3;
      this.count = var4;
   }

   boolean aboutEquals(float var1, float var2, float var3) {
      var2 = Math.abs(var2 - this.getY());
      boolean var4 = false;
      boolean var5 = var4;
      if (var2 <= var1) {
         var5 = var4;
         if (Math.abs(var3 - this.getX()) <= var1) {
            var1 = Math.abs(var1 - this.estimatedModuleSize);
            if (var1 > 1.0F) {
               var5 = var4;
               if (var1 > this.estimatedModuleSize) {
                  return var5;
               }
            }

            var5 = true;
         }
      }

      return var5;
   }

   FinderPattern combineEstimate(float var1, float var2, float var3) {
      int var4 = this.count;
      int var5 = var4 + 1;
      float var6 = (float)var4;
      float var7 = this.getX();
      float var8 = (float)var5;
      return new FinderPattern((var6 * var7 + var2) / var8, ((float)this.count * this.getY() + var1) / var8, ((float)this.count * this.estimatedModuleSize + var3) / var8, var5);
   }

   int getCount() {
      return this.count;
   }

   public float getEstimatedModuleSize() {
      return this.estimatedModuleSize;
   }
}

package com.google.android.material.shape;

public class CutCornerTreatment extends CornerTreatment {
   float size = -1.0F;

   public CutCornerTreatment() {
   }

   @Deprecated
   public CutCornerTreatment(float var1) {
      this.size = var1;
   }

   public void getCornerPath(ShapePath var1, float var2, float var3, float var4) {
      var1.reset(0.0F, var4 * var3, 180.0F, 180.0F - var2);
      double var5 = Math.sin(Math.toRadians((double)var2));
      double var7 = (double)var4;
      double var9 = (double)var3;
      var1.lineTo((float)(var5 * var7 * var9), (float)(Math.sin(Math.toRadians((double)(90.0F - var2))) * var7 * var9));
   }
}

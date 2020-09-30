package com.google.android.material.shape;

public final class MarkerEdgeTreatment extends EdgeTreatment {
   private final float radius;

   public MarkerEdgeTreatment(float var1) {
      this.radius = var1 - 0.001F;
   }

   boolean forceIntersection() {
      return true;
   }

   public void getEdgePath(float var1, float var2, float var3, ShapePath var4) {
      var3 = (float)((double)this.radius * Math.sqrt(2.0D) / 2.0D);
      var1 = (float)Math.sqrt(Math.pow((double)this.radius, 2.0D) - Math.pow((double)var3, 2.0D));
      var4.reset(var2 - var3, (float)(-((double)this.radius * Math.sqrt(2.0D) - (double)this.radius)) + var1);
      var4.lineTo(var2, (float)(-((double)this.radius * Math.sqrt(2.0D) - (double)this.radius)));
      var4.lineTo(var2 + var3, (float)(-((double)this.radius * Math.sqrt(2.0D) - (double)this.radius)) + var1);
   }
}

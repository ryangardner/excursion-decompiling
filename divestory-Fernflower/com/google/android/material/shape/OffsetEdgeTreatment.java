package com.google.android.material.shape;

public final class OffsetEdgeTreatment extends EdgeTreatment {
   private final float offset;
   private final EdgeTreatment other;

   public OffsetEdgeTreatment(EdgeTreatment var1, float var2) {
      this.other = var1;
      this.offset = var2;
   }

   boolean forceIntersection() {
      return this.other.forceIntersection();
   }

   public void getEdgePath(float var1, float var2, float var3, ShapePath var4) {
      this.other.getEdgePath(var1, var2 - this.offset, var3, var4);
   }
}

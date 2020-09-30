package io.opencensus.metrics.export;

final class AutoValue_Summary_Snapshot_ValueAtPercentile extends Summary.Snapshot.ValueAtPercentile {
   private final double percentile;
   private final double value;

   AutoValue_Summary_Snapshot_ValueAtPercentile(double var1, double var3) {
      this.percentile = var1;
      this.value = var3;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Summary.Snapshot.ValueAtPercentile)) {
         return false;
      } else {
         Summary.Snapshot.ValueAtPercentile var3 = (Summary.Snapshot.ValueAtPercentile)var1;
         if (Double.doubleToLongBits(this.percentile) != Double.doubleToLongBits(var3.getPercentile()) || Double.doubleToLongBits(this.value) != Double.doubleToLongBits(var3.getValue())) {
            var2 = false;
         }

         return var2;
      }
   }

   public double getPercentile() {
      return this.percentile;
   }

   public double getValue() {
      return this.value;
   }

   public int hashCode() {
      return (int)((long)((int)((long)1000003 ^ Double.doubleToLongBits(this.percentile) >>> 32 ^ Double.doubleToLongBits(this.percentile)) * 1000003) ^ Double.doubleToLongBits(this.value) >>> 32 ^ Double.doubleToLongBits(this.value));
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("ValueAtPercentile{percentile=");
      var1.append(this.percentile);
      var1.append(", value=");
      var1.append(this.value);
      var1.append("}");
      return var1.toString();
   }
}

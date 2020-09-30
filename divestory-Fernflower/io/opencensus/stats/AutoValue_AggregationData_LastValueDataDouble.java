package io.opencensus.stats;

final class AutoValue_AggregationData_LastValueDataDouble extends AggregationData.LastValueDataDouble {
   private final double lastValue;

   AutoValue_AggregationData_LastValueDataDouble(double var1) {
      this.lastValue = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (var1 instanceof AggregationData.LastValueDataDouble) {
         AggregationData.LastValueDataDouble var3 = (AggregationData.LastValueDataDouble)var1;
         if (Double.doubleToLongBits(this.lastValue) != Double.doubleToLongBits(var3.getLastValue())) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public double getLastValue() {
      return this.lastValue;
   }

   public int hashCode() {
      return (int)((long)1000003 ^ Double.doubleToLongBits(this.lastValue) >>> 32 ^ Double.doubleToLongBits(this.lastValue));
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("LastValueDataDouble{lastValue=");
      var1.append(this.lastValue);
      var1.append("}");
      return var1.toString();
   }
}

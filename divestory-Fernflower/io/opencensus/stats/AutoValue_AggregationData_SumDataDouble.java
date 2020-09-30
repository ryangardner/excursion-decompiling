package io.opencensus.stats;

final class AutoValue_AggregationData_SumDataDouble extends AggregationData.SumDataDouble {
   private final double sum;

   AutoValue_AggregationData_SumDataDouble(double var1) {
      this.sum = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (var1 instanceof AggregationData.SumDataDouble) {
         AggregationData.SumDataDouble var3 = (AggregationData.SumDataDouble)var1;
         if (Double.doubleToLongBits(this.sum) != Double.doubleToLongBits(var3.getSum())) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public double getSum() {
      return this.sum;
   }

   public int hashCode() {
      return (int)((long)1000003 ^ Double.doubleToLongBits(this.sum) >>> 32 ^ Double.doubleToLongBits(this.sum));
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("SumDataDouble{sum=");
      var1.append(this.sum);
      var1.append("}");
      return var1.toString();
   }
}

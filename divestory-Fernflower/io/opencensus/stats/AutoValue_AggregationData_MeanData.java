package io.opencensus.stats;

@Deprecated
final class AutoValue_AggregationData_MeanData extends AggregationData.MeanData {
   private final long count;
   private final double mean;

   AutoValue_AggregationData_MeanData(double var1, long var3) {
      this.mean = var1;
      this.count = var3;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof AggregationData.MeanData)) {
         return false;
      } else {
         AggregationData.MeanData var3 = (AggregationData.MeanData)var1;
         if (Double.doubleToLongBits(this.mean) != Double.doubleToLongBits(var3.getMean()) || this.count != var3.getCount()) {
            var2 = false;
         }

         return var2;
      }
   }

   public long getCount() {
      return this.count;
   }

   public double getMean() {
      return this.mean;
   }

   public int hashCode() {
      long var1 = (long)((int)((long)1000003 ^ Double.doubleToLongBits(this.mean) >>> 32 ^ Double.doubleToLongBits(this.mean)) * 1000003);
      long var3 = this.count;
      return (int)(var1 ^ var3 ^ var3 >>> 32);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("MeanData{mean=");
      var1.append(this.mean);
      var1.append(", count=");
      var1.append(this.count);
      var1.append("}");
      return var1.toString();
   }
}

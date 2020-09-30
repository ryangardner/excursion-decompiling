package io.opencensus.stats;

final class AutoValue_AggregationData_SumDataLong extends AggregationData.SumDataLong {
   private final long sum;

   AutoValue_AggregationData_SumDataLong(long var1) {
      this.sum = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (var1 instanceof AggregationData.SumDataLong) {
         AggregationData.SumDataLong var3 = (AggregationData.SumDataLong)var1;
         if (this.sum != var3.getSum()) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public long getSum() {
      return this.sum;
   }

   public int hashCode() {
      long var1 = (long)1000003;
      long var3 = this.sum;
      return (int)(var1 ^ var3 ^ var3 >>> 32);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("SumDataLong{sum=");
      var1.append(this.sum);
      var1.append("}");
      return var1.toString();
   }
}

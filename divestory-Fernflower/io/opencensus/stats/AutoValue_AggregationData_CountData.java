package io.opencensus.stats;

final class AutoValue_AggregationData_CountData extends AggregationData.CountData {
   private final long count;

   AutoValue_AggregationData_CountData(long var1) {
      this.count = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (var1 instanceof AggregationData.CountData) {
         AggregationData.CountData var3 = (AggregationData.CountData)var1;
         if (this.count != var3.getCount()) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public long getCount() {
      return this.count;
   }

   public int hashCode() {
      long var1 = (long)1000003;
      long var3 = this.count;
      return (int)(var1 ^ var3 ^ var3 >>> 32);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("CountData{count=");
      var1.append(this.count);
      var1.append("}");
      return var1.toString();
   }
}

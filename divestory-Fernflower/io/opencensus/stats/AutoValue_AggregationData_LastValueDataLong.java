package io.opencensus.stats;

final class AutoValue_AggregationData_LastValueDataLong extends AggregationData.LastValueDataLong {
   private final long lastValue;

   AutoValue_AggregationData_LastValueDataLong(long var1) {
      this.lastValue = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (var1 instanceof AggregationData.LastValueDataLong) {
         AggregationData.LastValueDataLong var3 = (AggregationData.LastValueDataLong)var1;
         if (this.lastValue != var3.getLastValue()) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public long getLastValue() {
      return this.lastValue;
   }

   public int hashCode() {
      long var1 = (long)1000003;
      long var3 = this.lastValue;
      return (int)(var1 ^ var3 ^ var3 >>> 32);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("LastValueDataLong{lastValue=");
      var1.append(this.lastValue);
      var1.append("}");
      return var1.toString();
   }
}

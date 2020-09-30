package io.opencensus.stats;

import io.opencensus.common.Timestamp;

@Deprecated
final class AutoValue_ViewData_AggregationWindowData_CumulativeData extends ViewData.AggregationWindowData.CumulativeData {
   private final Timestamp end;
   private final Timestamp start;

   AutoValue_ViewData_AggregationWindowData_CumulativeData(Timestamp var1, Timestamp var2) {
      if (var1 != null) {
         this.start = var1;
         if (var2 != null) {
            this.end = var2;
         } else {
            throw new NullPointerException("Null end");
         }
      } else {
         throw new NullPointerException("Null start");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ViewData.AggregationWindowData.CumulativeData)) {
         return false;
      } else {
         ViewData.AggregationWindowData.CumulativeData var3 = (ViewData.AggregationWindowData.CumulativeData)var1;
         if (!this.start.equals(var3.getStart()) || !this.end.equals(var3.getEnd())) {
            var2 = false;
         }

         return var2;
      }
   }

   public Timestamp getEnd() {
      return this.end;
   }

   public Timestamp getStart() {
      return this.start;
   }

   public int hashCode() {
      return (this.start.hashCode() ^ 1000003) * 1000003 ^ this.end.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("CumulativeData{start=");
      var1.append(this.start);
      var1.append(", end=");
      var1.append(this.end);
      var1.append("}");
      return var1.toString();
   }
}

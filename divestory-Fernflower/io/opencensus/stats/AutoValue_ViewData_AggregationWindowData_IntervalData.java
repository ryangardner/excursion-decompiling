package io.opencensus.stats;

import io.opencensus.common.Timestamp;

@Deprecated
final class AutoValue_ViewData_AggregationWindowData_IntervalData extends ViewData.AggregationWindowData.IntervalData {
   private final Timestamp end;

   AutoValue_ViewData_AggregationWindowData_IntervalData(Timestamp var1) {
      if (var1 != null) {
         this.end = var1;
      } else {
         throw new NullPointerException("Null end");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof ViewData.AggregationWindowData.IntervalData) {
         ViewData.AggregationWindowData.IntervalData var2 = (ViewData.AggregationWindowData.IntervalData)var1;
         return this.end.equals(var2.getEnd());
      } else {
         return false;
      }
   }

   public Timestamp getEnd() {
      return this.end;
   }

   public int hashCode() {
      return this.end.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("IntervalData{end=");
      var1.append(this.end);
      var1.append("}");
      return var1.toString();
   }
}

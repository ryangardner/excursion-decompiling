package io.opencensus.stats;

import io.opencensus.common.Duration;

@Deprecated
final class AutoValue_View_AggregationWindow_Interval extends View.AggregationWindow.Interval {
   private final Duration duration;

   AutoValue_View_AggregationWindow_Interval(Duration var1) {
      if (var1 != null) {
         this.duration = var1;
      } else {
         throw new NullPointerException("Null duration");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof View.AggregationWindow.Interval) {
         View.AggregationWindow.Interval var2 = (View.AggregationWindow.Interval)var1;
         return this.duration.equals(var2.getDuration());
      } else {
         return false;
      }
   }

   public Duration getDuration() {
      return this.duration;
   }

   public int hashCode() {
      return this.duration.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Interval{duration=");
      var1.append(this.duration);
      var1.append("}");
      return var1.toString();
   }
}

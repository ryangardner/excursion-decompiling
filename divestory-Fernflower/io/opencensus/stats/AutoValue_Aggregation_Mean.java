package io.opencensus.stats;

@Deprecated
final class AutoValue_Aggregation_Mean extends Aggregation.Mean {
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else {
         return var1 instanceof Aggregation.Mean;
      }
   }

   public int hashCode() {
      return 1;
   }

   public String toString() {
      return "Mean{}";
   }
}

package io.opencensus.stats;

final class AutoValue_Aggregation_Count extends Aggregation.Count {
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else {
         return var1 instanceof Aggregation.Count;
      }
   }

   public int hashCode() {
      return 1;
   }

   public String toString() {
      return "Count{}";
   }
}

package io.opencensus.stats;

final class AutoValue_Aggregation_Sum extends Aggregation.Sum {
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else {
         return var1 instanceof Aggregation.Sum;
      }
   }

   public int hashCode() {
      return 1;
   }

   public String toString() {
      return "Sum{}";
   }
}

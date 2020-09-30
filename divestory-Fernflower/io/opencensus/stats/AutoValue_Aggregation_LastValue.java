package io.opencensus.stats;

final class AutoValue_Aggregation_LastValue extends Aggregation.LastValue {
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else {
         return var1 instanceof Aggregation.LastValue;
      }
   }

   public int hashCode() {
      return 1;
   }

   public String toString() {
      return "LastValue{}";
   }
}

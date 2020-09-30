package io.opencensus.metrics.export;

final class AutoValue_Value_ValueDistribution extends Value.ValueDistribution {
   private final Distribution value;

   AutoValue_Value_ValueDistribution(Distribution var1) {
      if (var1 != null) {
         this.value = var1;
      } else {
         throw new NullPointerException("Null value");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof Value.ValueDistribution) {
         Value.ValueDistribution var2 = (Value.ValueDistribution)var1;
         return this.value.equals(var2.getValue());
      } else {
         return false;
      }
   }

   Distribution getValue() {
      return this.value;
   }

   public int hashCode() {
      return this.value.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("ValueDistribution{value=");
      var1.append(this.value);
      var1.append("}");
      return var1.toString();
   }
}

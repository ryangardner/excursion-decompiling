package io.opencensus.metrics.export;

final class AutoValue_Value_ValueSummary extends Value.ValueSummary {
   private final Summary value;

   AutoValue_Value_ValueSummary(Summary var1) {
      if (var1 != null) {
         this.value = var1;
      } else {
         throw new NullPointerException("Null value");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof Value.ValueSummary) {
         Value.ValueSummary var2 = (Value.ValueSummary)var1;
         return this.value.equals(var2.getValue());
      } else {
         return false;
      }
   }

   Summary getValue() {
      return this.value;
   }

   public int hashCode() {
      return this.value.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("ValueSummary{value=");
      var1.append(this.value);
      var1.append("}");
      return var1.toString();
   }
}

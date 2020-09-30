package io.opencensus.metrics.export;

final class AutoValue_Value_ValueDouble extends Value.ValueDouble {
   private final double value;

   AutoValue_Value_ValueDouble(double var1) {
      this.value = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (var1 instanceof Value.ValueDouble) {
         Value.ValueDouble var3 = (Value.ValueDouble)var1;
         if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(var3.getValue())) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   double getValue() {
      return this.value;
   }

   public int hashCode() {
      return (int)((long)1000003 ^ Double.doubleToLongBits(this.value) >>> 32 ^ Double.doubleToLongBits(this.value));
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("ValueDouble{value=");
      var1.append(this.value);
      var1.append("}");
      return var1.toString();
   }
}

package io.opencensus.metrics.export;

import io.opencensus.common.Timestamp;

final class AutoValue_Point extends Point {
   private final Timestamp timestamp;
   private final Value value;

   AutoValue_Point(Value var1, Timestamp var2) {
      if (var1 != null) {
         this.value = var1;
         if (var2 != null) {
            this.timestamp = var2;
         } else {
            throw new NullPointerException("Null timestamp");
         }
      } else {
         throw new NullPointerException("Null value");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Point)) {
         return false;
      } else {
         Point var3 = (Point)var1;
         if (!this.value.equals(var3.getValue()) || !this.timestamp.equals(var3.getTimestamp())) {
            var2 = false;
         }

         return var2;
      }
   }

   public Timestamp getTimestamp() {
      return this.timestamp;
   }

   public Value getValue() {
      return this.value;
   }

   public int hashCode() {
      return (this.value.hashCode() ^ 1000003) * 1000003 ^ this.timestamp.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Point{value=");
      var1.append(this.value);
      var1.append(", timestamp=");
      var1.append(this.timestamp);
      var1.append("}");
      return var1.toString();
   }
}

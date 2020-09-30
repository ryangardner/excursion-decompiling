package io.opencensus.metrics.export;

final class AutoValue_Value_ValueLong extends Value.ValueLong {
   private final long value;

   AutoValue_Value_ValueLong(long var1) {
      this.value = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (var1 instanceof Value.ValueLong) {
         Value.ValueLong var3 = (Value.ValueLong)var1;
         if (this.value != var3.getValue()) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   long getValue() {
      return this.value;
   }

   public int hashCode() {
      long var1 = (long)1000003;
      long var3 = this.value;
      return (int)(var1 ^ var3 ^ var3 >>> 32);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("ValueLong{value=");
      var1.append(this.value);
      var1.append("}");
      return var1.toString();
   }
}

package io.opencensus.common;

final class AutoValue_Duration extends Duration {
   private final int nanos;
   private final long seconds;

   AutoValue_Duration(long var1, int var3) {
      this.seconds = var1;
      this.nanos = var3;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Duration)) {
         return false;
      } else {
         Duration var3 = (Duration)var1;
         if (this.seconds != var3.getSeconds() || this.nanos != var3.getNanos()) {
            var2 = false;
         }

         return var2;
      }
   }

   public int getNanos() {
      return this.nanos;
   }

   public long getSeconds() {
      return this.seconds;
   }

   public int hashCode() {
      long var1 = (long)1000003;
      long var3 = this.seconds;
      int var5 = (int)(var1 ^ var3 ^ var3 >>> 32);
      return this.nanos ^ var5 * 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Duration{seconds=");
      var1.append(this.seconds);
      var1.append(", nanos=");
      var1.append(this.nanos);
      var1.append("}");
      return var1.toString();
   }
}

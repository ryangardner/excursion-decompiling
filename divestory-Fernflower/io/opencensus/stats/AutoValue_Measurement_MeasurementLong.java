package io.opencensus.stats;

final class AutoValue_Measurement_MeasurementLong extends Measurement.MeasurementLong {
   private final Measure.MeasureLong measure;
   private final long value;

   AutoValue_Measurement_MeasurementLong(Measure.MeasureLong var1, long var2) {
      if (var1 != null) {
         this.measure = var1;
         this.value = var2;
      } else {
         throw new NullPointerException("Null measure");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Measurement.MeasurementLong)) {
         return false;
      } else {
         Measurement.MeasurementLong var3 = (Measurement.MeasurementLong)var1;
         if (!this.measure.equals(var3.getMeasure()) || this.value != var3.getValue()) {
            var2 = false;
         }

         return var2;
      }
   }

   public Measure.MeasureLong getMeasure() {
      return this.measure;
   }

   public long getValue() {
      return this.value;
   }

   public int hashCode() {
      long var1 = (long)((this.measure.hashCode() ^ 1000003) * 1000003);
      long var3 = this.value;
      return (int)(var1 ^ var3 ^ var3 >>> 32);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("MeasurementLong{measure=");
      var1.append(this.measure);
      var1.append(", value=");
      var1.append(this.value);
      var1.append("}");
      return var1.toString();
   }
}

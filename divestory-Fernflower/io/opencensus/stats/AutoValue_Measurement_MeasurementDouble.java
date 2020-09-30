package io.opencensus.stats;

final class AutoValue_Measurement_MeasurementDouble extends Measurement.MeasurementDouble {
   private final Measure.MeasureDouble measure;
   private final double value;

   AutoValue_Measurement_MeasurementDouble(Measure.MeasureDouble var1, double var2) {
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
      } else if (!(var1 instanceof Measurement.MeasurementDouble)) {
         return false;
      } else {
         Measurement.MeasurementDouble var3 = (Measurement.MeasurementDouble)var1;
         if (!this.measure.equals(var3.getMeasure()) || Double.doubleToLongBits(this.value) != Double.doubleToLongBits(var3.getValue())) {
            var2 = false;
         }

         return var2;
      }
   }

   public Measure.MeasureDouble getMeasure() {
      return this.measure;
   }

   public double getValue() {
      return this.value;
   }

   public int hashCode() {
      return (int)((long)((this.measure.hashCode() ^ 1000003) * 1000003) ^ Double.doubleToLongBits(this.value) >>> 32 ^ Double.doubleToLongBits(this.value));
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("MeasurementDouble{measure=");
      var1.append(this.measure);
      var1.append(", value=");
      var1.append(this.value);
      var1.append("}");
      return var1.toString();
   }
}

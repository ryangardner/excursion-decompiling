package io.opencensus.stats;

import io.opencensus.common.Function;

public abstract class Measurement {
   private Measurement() {
   }

   // $FF: synthetic method
   Measurement(Object var1) {
      this();
   }

   public abstract Measure getMeasure();

   public abstract <T> T match(Function<? super Measurement.MeasurementDouble, T> var1, Function<? super Measurement.MeasurementLong, T> var2, Function<? super Measurement, T> var3);

   public abstract static class MeasurementDouble extends Measurement {
      MeasurementDouble() {
         super(null);
      }

      public static Measurement.MeasurementDouble create(Measure.MeasureDouble var0, double var1) {
         return new AutoValue_Measurement_MeasurementDouble(var0, var1);
      }

      public abstract Measure.MeasureDouble getMeasure();

      public abstract double getValue();

      public <T> T match(Function<? super Measurement.MeasurementDouble, T> var1, Function<? super Measurement.MeasurementLong, T> var2, Function<? super Measurement, T> var3) {
         return var1.apply(this);
      }
   }

   public abstract static class MeasurementLong extends Measurement {
      MeasurementLong() {
         super(null);
      }

      public static Measurement.MeasurementLong create(Measure.MeasureLong var0, long var1) {
         return new AutoValue_Measurement_MeasurementLong(var0, var1);
      }

      public abstract Measure.MeasureLong getMeasure();

      public abstract long getValue();

      public <T> T match(Function<? super Measurement.MeasurementDouble, T> var1, Function<? super Measurement.MeasurementLong, T> var2, Function<? super Measurement, T> var3) {
         return var2.apply(this);
      }
   }
}

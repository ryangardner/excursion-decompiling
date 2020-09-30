package io.opencensus.metrics.export;

import io.opencensus.common.Timestamp;

public abstract class Point {
   Point() {
   }

   public static Point create(Value var0, Timestamp var1) {
      return new AutoValue_Point(var0, var1);
   }

   public abstract Timestamp getTimestamp();

   public abstract Value getValue();
}

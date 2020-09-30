package io.opencensus.metrics.export;

import io.opencensus.common.Timestamp;
import io.opencensus.metrics.LabelValue;
import java.util.List;
import javax.annotation.Nullable;

final class AutoValue_TimeSeries extends TimeSeries {
   private final List<LabelValue> labelValues;
   private final List<Point> points;
   private final Timestamp startTimestamp;

   AutoValue_TimeSeries(List<LabelValue> var1, List<Point> var2, @Nullable Timestamp var3) {
      if (var1 != null) {
         this.labelValues = var1;
         if (var2 != null) {
            this.points = var2;
            this.startTimestamp = var3;
         } else {
            throw new NullPointerException("Null points");
         }
      } else {
         throw new NullPointerException("Null labelValues");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof TimeSeries)) {
         return false;
      } else {
         TimeSeries var3 = (TimeSeries)var1;
         if (this.labelValues.equals(var3.getLabelValues()) && this.points.equals(var3.getPoints())) {
            Timestamp var4 = this.startTimestamp;
            if (var4 == null) {
               if (var3.getStartTimestamp() == null) {
                  return var2;
               }
            } else if (var4.equals(var3.getStartTimestamp())) {
               return var2;
            }
         }

         var2 = false;
         return var2;
      }
   }

   public List<LabelValue> getLabelValues() {
      return this.labelValues;
   }

   public List<Point> getPoints() {
      return this.points;
   }

   @Nullable
   public Timestamp getStartTimestamp() {
      return this.startTimestamp;
   }

   public int hashCode() {
      int var1 = this.labelValues.hashCode();
      int var2 = this.points.hashCode();
      Timestamp var3 = this.startTimestamp;
      int var4;
      if (var3 == null) {
         var4 = 0;
      } else {
         var4 = var3.hashCode();
      }

      return ((var1 ^ 1000003) * 1000003 ^ var2) * 1000003 ^ var4;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("TimeSeries{labelValues=");
      var1.append(this.labelValues);
      var1.append(", points=");
      var1.append(this.points);
      var1.append(", startTimestamp=");
      var1.append(this.startTimestamp);
      var1.append("}");
      return var1.toString();
   }
}

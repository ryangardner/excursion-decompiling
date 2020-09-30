package io.opencensus.metrics.export;

import io.opencensus.common.Timestamp;
import io.opencensus.internal.Utils;
import io.opencensus.metrics.LabelValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public abstract class TimeSeries {
   TimeSeries() {
   }

   public static TimeSeries create(List<LabelValue> var0) {
      return createInternal(var0, Collections.emptyList(), (Timestamp)null);
   }

   public static TimeSeries create(List<LabelValue> var0, List<Point> var1, @Nullable Timestamp var2) {
      Utils.checkListElementNotNull((List)Utils.checkNotNull(var1, "points"), "point");
      return createInternal(var0, Collections.unmodifiableList(new ArrayList(var1)), var2);
   }

   private static TimeSeries createInternal(List<LabelValue> var0, List<Point> var1, @Nullable Timestamp var2) {
      Utils.checkListElementNotNull((List)Utils.checkNotNull(var0, "labelValues"), "labelValue");
      return new AutoValue_TimeSeries(Collections.unmodifiableList(new ArrayList(var0)), var1, var2);
   }

   public static TimeSeries createWithOnePoint(List<LabelValue> var0, Point var1, @Nullable Timestamp var2) {
      Utils.checkNotNull(var1, "point");
      return createInternal(var0, Collections.singletonList(var1), var2);
   }

   public abstract List<LabelValue> getLabelValues();

   public abstract List<Point> getPoints();

   @Nullable
   public abstract Timestamp getStartTimestamp();

   public TimeSeries setPoint(Point var1) {
      Utils.checkNotNull(var1, "point");
      return new AutoValue_TimeSeries(this.getLabelValues(), Collections.singletonList(var1), (Timestamp)null);
   }
}

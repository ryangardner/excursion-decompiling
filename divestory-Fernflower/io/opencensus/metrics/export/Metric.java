package io.opencensus.metrics.export;

import io.opencensus.internal.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class Metric {
   Metric() {
   }

   private static void checkTypeMatch(MetricDescriptor.Type var0, List<TimeSeries> var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         Iterator var3 = ((TimeSeries)var2.next()).getPoints().iterator();

         while(var3.hasNext()) {
            Value var4 = ((Point)var3.next()).getValue();
            String var5;
            if (var4.getClass().getSuperclass() != null) {
               var5 = var4.getClass().getSuperclass().getSimpleName();
            } else {
               var5 = "";
            }

            switch(null.$SwitchMap$io$opencensus$metrics$export$MetricDescriptor$Type[var0.ordinal()]) {
            case 1:
            case 2:
               Utils.checkArgument(var4 instanceof Value.ValueLong, "Type mismatch: %s, %s.", var0, var5);
               break;
            case 3:
            case 4:
               Utils.checkArgument(var4 instanceof Value.ValueDouble, "Type mismatch: %s, %s.", var0, var5);
               break;
            case 5:
            case 6:
               Utils.checkArgument(var4 instanceof Value.ValueDistribution, "Type mismatch: %s, %s.", var0, var5);
               break;
            case 7:
               Utils.checkArgument(var4 instanceof Value.ValueSummary, "Type mismatch: %s, %s.", var0, var5);
            }
         }
      }

   }

   public static Metric create(MetricDescriptor var0, List<TimeSeries> var1) {
      Utils.checkListElementNotNull((List)Utils.checkNotNull(var1, "timeSeriesList"), "timeSeries");
      return createInternal(var0, Collections.unmodifiableList(new ArrayList(var1)));
   }

   private static Metric createInternal(MetricDescriptor var0, List<TimeSeries> var1) {
      Utils.checkNotNull(var0, "metricDescriptor");
      checkTypeMatch(var0.getType(), var1);
      return new AutoValue_Metric(var0, var1);
   }

   public static Metric createWithOneTimeSeries(MetricDescriptor var0, TimeSeries var1) {
      return createInternal(var0, Collections.singletonList(Utils.checkNotNull(var1, "timeSeries")));
   }

   public abstract MetricDescriptor getMetricDescriptor();

   public abstract List<TimeSeries> getTimeSeriesList();
}

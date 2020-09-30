package io.opencensus.metrics.export;

import io.opencensus.internal.Utils;
import io.opencensus.metrics.LabelKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MetricDescriptor {
   MetricDescriptor() {
   }

   public static MetricDescriptor create(String var0, String var1, String var2, MetricDescriptor.Type var3, List<LabelKey> var4) {
      Utils.checkListElementNotNull((List)Utils.checkNotNull(var4, "labelKeys"), "labelKey");
      return new AutoValue_MetricDescriptor(var0, var1, var2, var3, Collections.unmodifiableList(new ArrayList(var4)));
   }

   public abstract String getDescription();

   public abstract List<LabelKey> getLabelKeys();

   public abstract String getName();

   public abstract MetricDescriptor.Type getType();

   public abstract String getUnit();

   public static enum Type {
      CUMULATIVE_DISTRIBUTION,
      CUMULATIVE_DOUBLE,
      CUMULATIVE_INT64,
      GAUGE_DISTRIBUTION,
      GAUGE_DOUBLE,
      GAUGE_INT64,
      SUMMARY;

      static {
         MetricDescriptor.Type var0 = new MetricDescriptor.Type("SUMMARY", 6);
         SUMMARY = var0;
      }
   }
}

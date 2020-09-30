package io.opencensus.metrics;

import io.opencensus.internal.Utils;
import java.util.List;

public abstract class MetricRegistry {
   static MetricRegistry newNoopMetricRegistry() {
      return new MetricRegistry.NoopMetricRegistry();
   }

   public abstract DerivedDoubleCumulative addDerivedDoubleCumulative(String var1, MetricOptions var2);

   public abstract DerivedDoubleGauge addDerivedDoubleGauge(String var1, MetricOptions var2);

   @Deprecated
   public DerivedDoubleGauge addDerivedDoubleGauge(String var1, String var2, String var3, List<LabelKey> var4) {
      return this.addDerivedDoubleGauge(var1, MetricOptions.builder().setDescription(var2).setUnit(var3).setLabelKeys(var4).build());
   }

   public abstract DerivedLongCumulative addDerivedLongCumulative(String var1, MetricOptions var2);

   public abstract DerivedLongGauge addDerivedLongGauge(String var1, MetricOptions var2);

   @Deprecated
   public DerivedLongGauge addDerivedLongGauge(String var1, String var2, String var3, List<LabelKey> var4) {
      return this.addDerivedLongGauge(var1, MetricOptions.builder().setDescription(var2).setUnit(var3).setLabelKeys(var4).build());
   }

   public abstract DoubleCumulative addDoubleCumulative(String var1, MetricOptions var2);

   public abstract DoubleGauge addDoubleGauge(String var1, MetricOptions var2);

   @Deprecated
   public DoubleGauge addDoubleGauge(String var1, String var2, String var3, List<LabelKey> var4) {
      return this.addDoubleGauge(var1, MetricOptions.builder().setDescription(var2).setUnit(var3).setLabelKeys(var4).build());
   }

   public abstract LongCumulative addLongCumulative(String var1, MetricOptions var2);

   public abstract LongGauge addLongGauge(String var1, MetricOptions var2);

   @Deprecated
   public LongGauge addLongGauge(String var1, String var2, String var3, List<LabelKey> var4) {
      return this.addLongGauge(var1, MetricOptions.builder().setDescription(var2).setUnit(var3).setLabelKeys(var4).build());
   }

   private static final class NoopMetricRegistry extends MetricRegistry {
      private NoopMetricRegistry() {
      }

      // $FF: synthetic method
      NoopMetricRegistry(Object var1) {
         this();
      }

      public DerivedDoubleCumulative addDerivedDoubleCumulative(String var1, MetricOptions var2) {
         return DerivedDoubleCumulative.newNoopDerivedDoubleCumulative((String)Utils.checkNotNull(var1, "name"), var2.getDescription(), var2.getUnit(), var2.getLabelKeys());
      }

      public DerivedDoubleGauge addDerivedDoubleGauge(String var1, MetricOptions var2) {
         return DerivedDoubleGauge.newNoopDerivedDoubleGauge((String)Utils.checkNotNull(var1, "name"), var2.getDescription(), var2.getUnit(), var2.getLabelKeys());
      }

      public DerivedLongCumulative addDerivedLongCumulative(String var1, MetricOptions var2) {
         return DerivedLongCumulative.newNoopDerivedLongCumulative((String)Utils.checkNotNull(var1, "name"), var2.getDescription(), var2.getUnit(), var2.getLabelKeys());
      }

      public DerivedLongGauge addDerivedLongGauge(String var1, MetricOptions var2) {
         return DerivedLongGauge.newNoopDerivedLongGauge((String)Utils.checkNotNull(var1, "name"), var2.getDescription(), var2.getUnit(), var2.getLabelKeys());
      }

      public DoubleCumulative addDoubleCumulative(String var1, MetricOptions var2) {
         return DoubleCumulative.newNoopDoubleCumulative((String)Utils.checkNotNull(var1, "name"), var2.getDescription(), var2.getUnit(), var2.getLabelKeys());
      }

      public DoubleGauge addDoubleGauge(String var1, MetricOptions var2) {
         return DoubleGauge.newNoopDoubleGauge((String)Utils.checkNotNull(var1, "name"), var2.getDescription(), var2.getUnit(), var2.getLabelKeys());
      }

      public LongCumulative addLongCumulative(String var1, MetricOptions var2) {
         return LongCumulative.newNoopLongCumulative((String)Utils.checkNotNull(var1, "name"), var2.getDescription(), var2.getUnit(), var2.getLabelKeys());
      }

      public LongGauge addLongGauge(String var1, MetricOptions var2) {
         return LongGauge.newNoopLongGauge((String)Utils.checkNotNull(var1, "name"), var2.getDescription(), var2.getUnit(), var2.getLabelKeys());
      }
   }
}

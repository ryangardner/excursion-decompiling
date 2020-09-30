package io.opencensus.metrics.export;

import io.opencensus.internal.Utils;
import java.util.Collections;
import java.util.Set;

public abstract class MetricProducerManager {
   static MetricProducerManager newNoopMetricProducerManager() {
      return new MetricProducerManager.NoopMetricProducerManager();
   }

   public abstract void add(MetricProducer var1);

   public abstract Set<MetricProducer> getAllMetricProducer();

   public abstract void remove(MetricProducer var1);

   private static final class NoopMetricProducerManager extends MetricProducerManager {
      private NoopMetricProducerManager() {
      }

      // $FF: synthetic method
      NoopMetricProducerManager(Object var1) {
         this();
      }

      public void add(MetricProducer var1) {
         Utils.checkNotNull(var1, "metricProducer");
      }

      public Set<MetricProducer> getAllMetricProducer() {
         return Collections.emptySet();
      }

      public void remove(MetricProducer var1) {
         Utils.checkNotNull(var1, "metricProducer");
      }
   }
}

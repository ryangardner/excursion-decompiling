package io.opencensus.metrics.export;

public abstract class ExportComponent {
   public static ExportComponent newNoopExportComponent() {
      return new ExportComponent.NoopExportComponent();
   }

   public abstract MetricProducerManager getMetricProducerManager();

   private static final class NoopExportComponent extends ExportComponent {
      private static final MetricProducerManager METRIC_PRODUCER_MANAGER = MetricProducerManager.newNoopMetricProducerManager();

      private NoopExportComponent() {
      }

      // $FF: synthetic method
      NoopExportComponent(Object var1) {
         this();
      }

      public MetricProducerManager getMetricProducerManager() {
         return METRIC_PRODUCER_MANAGER;
      }
   }
}

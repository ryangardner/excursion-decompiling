package io.opencensus.metrics;

import io.opencensus.metrics.export.ExportComponent;

public abstract class MetricsComponent {
   static MetricsComponent newNoopMetricsComponent() {
      return new MetricsComponent.NoopMetricsComponent();
   }

   public abstract ExportComponent getExportComponent();

   public abstract MetricRegistry getMetricRegistry();

   private static final class NoopMetricsComponent extends MetricsComponent {
      private static final ExportComponent EXPORT_COMPONENT = ExportComponent.newNoopExportComponent();
      private static final MetricRegistry METRIC_REGISTRY = MetricRegistry.newNoopMetricRegistry();

      private NoopMetricsComponent() {
      }

      // $FF: synthetic method
      NoopMetricsComponent(Object var1) {
         this();
      }

      public ExportComponent getExportComponent() {
         return EXPORT_COMPONENT;
      }

      public MetricRegistry getMetricRegistry() {
         return METRIC_REGISTRY;
      }
   }
}

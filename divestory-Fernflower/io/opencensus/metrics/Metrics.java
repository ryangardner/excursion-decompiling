package io.opencensus.metrics;

import io.opencensus.internal.Provider;
import io.opencensus.metrics.export.ExportComponent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class Metrics {
   private static final Logger logger = Logger.getLogger(Metrics.class.getName());
   private static final MetricsComponent metricsComponent = loadMetricsComponent(MetricsComponent.class.getClassLoader());

   private Metrics() {
   }

   public static ExportComponent getExportComponent() {
      return metricsComponent.getExportComponent();
   }

   public static MetricRegistry getMetricRegistry() {
      return metricsComponent.getMetricRegistry();
   }

   static MetricsComponent loadMetricsComponent(@Nullable ClassLoader var0) {
      try {
         MetricsComponent var1 = (MetricsComponent)Provider.createInstance(Class.forName("io.opencensus.impl.metrics.MetricsComponentImpl", true, var0), MetricsComponent.class);
         return var1;
      } catch (ClassNotFoundException var3) {
         logger.log(Level.FINE, "Couldn't load full implementation for MetricsComponent, now trying to load lite implementation.", var3);

         try {
            MetricsComponent var4 = (MetricsComponent)Provider.createInstance(Class.forName("io.opencensus.impllite.metrics.MetricsComponentImplLite", true, var0), MetricsComponent.class);
            return var4;
         } catch (ClassNotFoundException var2) {
            logger.log(Level.FINE, "Couldn't load lite implementation for MetricsComponent, now using default implementation for MetricsComponent.", var2);
            return MetricsComponent.newNoopMetricsComponent();
         }
      }
   }
}

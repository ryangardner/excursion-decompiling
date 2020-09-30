/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics;

import io.opencensus.internal.Provider;
import io.opencensus.metrics.MetricRegistry;
import io.opencensus.metrics.MetricsComponent;
import io.opencensus.metrics.export.ExportComponent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class Metrics {
    private static final Logger logger = Logger.getLogger(Metrics.class.getName());
    private static final MetricsComponent metricsComponent = Metrics.loadMetricsComponent(MetricsComponent.class.getClassLoader());

    private Metrics() {
    }

    public static ExportComponent getExportComponent() {
        return metricsComponent.getExportComponent();
    }

    public static MetricRegistry getMetricRegistry() {
        return metricsComponent.getMetricRegistry();
    }

    static MetricsComponent loadMetricsComponent(@Nullable ClassLoader object) {
        try {
            return Provider.createInstance(Class.forName("io.opencensus.impl.metrics.MetricsComponentImpl", true, (ClassLoader)object), MetricsComponent.class);
        }
        catch (ClassNotFoundException classNotFoundException) {
            logger.log(Level.FINE, "Couldn't load full implementation for MetricsComponent, now trying to load lite implementation.", classNotFoundException);
            try {
                return Provider.createInstance(Class.forName("io.opencensus.impllite.metrics.MetricsComponentImplLite", true, (ClassLoader)object), MetricsComponent.class);
            }
            catch (ClassNotFoundException classNotFoundException2) {
                logger.log(Level.FINE, "Couldn't load lite implementation for MetricsComponent, now using default implementation for MetricsComponent.", classNotFoundException2);
                return MetricsComponent.newNoopMetricsComponent();
            }
        }
    }
}


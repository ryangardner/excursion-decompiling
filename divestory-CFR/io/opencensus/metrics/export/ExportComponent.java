/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.MetricProducerManager;

public abstract class ExportComponent {
    public static ExportComponent newNoopExportComponent() {
        return new NoopExportComponent();
    }

    public abstract MetricProducerManager getMetricProducerManager();

    private static final class NoopExportComponent
    extends ExportComponent {
        private static final MetricProducerManager METRIC_PRODUCER_MANAGER = MetricProducerManager.newNoopMetricProducerManager();

        private NoopExportComponent() {
        }

        @Override
        public MetricProducerManager getMetricProducerManager() {
            return METRIC_PRODUCER_MANAGER;
        }
    }

}


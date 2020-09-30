/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.internal.Utils;
import io.opencensus.metrics.export.MetricProducer;
import java.util.Collections;
import java.util.Set;

public abstract class MetricProducerManager {
    static MetricProducerManager newNoopMetricProducerManager() {
        return new NoopMetricProducerManager();
    }

    public abstract void add(MetricProducer var1);

    public abstract Set<MetricProducer> getAllMetricProducer();

    public abstract void remove(MetricProducer var1);

    private static final class NoopMetricProducerManager
    extends MetricProducerManager {
        private NoopMetricProducerManager() {
        }

        @Override
        public void add(MetricProducer metricProducer) {
            Utils.checkNotNull(metricProducer, "metricProducer");
        }

        @Override
        public Set<MetricProducer> getAllMetricProducer() {
            return Collections.emptySet();
        }

        @Override
        public void remove(MetricProducer metricProducer) {
            Utils.checkNotNull(metricProducer, "metricProducer");
        }
    }

}


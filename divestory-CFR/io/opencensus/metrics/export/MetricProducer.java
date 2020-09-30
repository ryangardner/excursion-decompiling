/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.Metric;
import java.util.Collection;

public abstract class MetricProducer {
    public abstract Collection<Metric> getMetrics();
}


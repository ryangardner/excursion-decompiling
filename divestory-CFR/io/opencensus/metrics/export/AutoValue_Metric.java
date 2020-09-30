/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.Metric;
import io.opencensus.metrics.export.MetricDescriptor;
import io.opencensus.metrics.export.TimeSeries;
import java.util.List;

final class AutoValue_Metric
extends Metric {
    private final MetricDescriptor metricDescriptor;
    private final List<TimeSeries> timeSeriesList;

    AutoValue_Metric(MetricDescriptor metricDescriptor, List<TimeSeries> list) {
        if (metricDescriptor == null) throw new NullPointerException("Null metricDescriptor");
        this.metricDescriptor = metricDescriptor;
        if (list == null) throw new NullPointerException("Null timeSeriesList");
        this.timeSeriesList = list;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Metric)) return false;
        if (!this.metricDescriptor.equals(((Metric)(object = (Metric)object)).getMetricDescriptor())) return false;
        if (!this.timeSeriesList.equals(((Metric)object).getTimeSeriesList())) return false;
        return bl;
    }

    @Override
    public MetricDescriptor getMetricDescriptor() {
        return this.metricDescriptor;
    }

    @Override
    public List<TimeSeries> getTimeSeriesList() {
        return this.timeSeriesList;
    }

    public int hashCode() {
        return (this.metricDescriptor.hashCode() ^ 1000003) * 1000003 ^ this.timeSeriesList.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Metric{metricDescriptor=");
        stringBuilder.append(this.metricDescriptor);
        stringBuilder.append(", timeSeriesList=");
        stringBuilder.append(this.timeSeriesList);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


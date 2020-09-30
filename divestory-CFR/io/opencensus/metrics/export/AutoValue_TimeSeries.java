/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.common.Timestamp;
import io.opencensus.metrics.LabelValue;
import io.opencensus.metrics.export.Point;
import io.opencensus.metrics.export.TimeSeries;
import java.util.List;
import javax.annotation.Nullable;

final class AutoValue_TimeSeries
extends TimeSeries {
    private final List<LabelValue> labelValues;
    private final List<Point> points;
    private final Timestamp startTimestamp;

    AutoValue_TimeSeries(List<LabelValue> list, List<Point> list2, @Nullable Timestamp timestamp) {
        if (list == null) throw new NullPointerException("Null labelValues");
        this.labelValues = list;
        if (list2 == null) throw new NullPointerException("Null points");
        this.points = list2;
        this.startTimestamp = timestamp;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof TimeSeries)) return false;
        TimeSeries timeSeries = (TimeSeries)object;
        if (!this.labelValues.equals(timeSeries.getLabelValues())) return false;
        if (!this.points.equals(timeSeries.getPoints())) return false;
        object = this.startTimestamp;
        if (object == null) {
            if (timeSeries.getStartTimestamp() != null) return false;
            return bl;
        }
        if (!object.equals(timeSeries.getStartTimestamp())) return false;
        return bl;
    }

    @Override
    public List<LabelValue> getLabelValues() {
        return this.labelValues;
    }

    @Override
    public List<Point> getPoints() {
        return this.points;
    }

    @Nullable
    @Override
    public Timestamp getStartTimestamp() {
        return this.startTimestamp;
    }

    public int hashCode() {
        int n;
        int n2 = this.labelValues.hashCode();
        int n3 = this.points.hashCode();
        Timestamp timestamp = this.startTimestamp;
        if (timestamp == null) {
            n = 0;
            return ((n2 ^ 1000003) * 1000003 ^ n3) * 1000003 ^ n;
        }
        n = timestamp.hashCode();
        return ((n2 ^ 1000003) * 1000003 ^ n3) * 1000003 ^ n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TimeSeries{labelValues=");
        stringBuilder.append(this.labelValues);
        stringBuilder.append(", points=");
        stringBuilder.append(this.points);
        stringBuilder.append(", startTimestamp=");
        stringBuilder.append(this.startTimestamp);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


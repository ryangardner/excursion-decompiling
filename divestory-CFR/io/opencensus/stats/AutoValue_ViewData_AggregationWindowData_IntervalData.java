/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.common.Timestamp;
import io.opencensus.stats.ViewData;

@Deprecated
final class AutoValue_ViewData_AggregationWindowData_IntervalData
extends ViewData.AggregationWindowData.IntervalData {
    private final Timestamp end;

    AutoValue_ViewData_AggregationWindowData_IntervalData(Timestamp timestamp) {
        if (timestamp == null) throw new NullPointerException("Null end");
        this.end = timestamp;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ViewData.AggregationWindowData.IntervalData)) return false;
        object = (ViewData.AggregationWindowData.IntervalData)object;
        return this.end.equals(((ViewData.AggregationWindowData.IntervalData)object).getEnd());
    }

    @Override
    public Timestamp getEnd() {
        return this.end;
    }

    public int hashCode() {
        return this.end.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IntervalData{end=");
        stringBuilder.append(this.end);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


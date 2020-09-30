/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.common.Timestamp;
import io.opencensus.stats.ViewData;

@Deprecated
final class AutoValue_ViewData_AggregationWindowData_CumulativeData
extends ViewData.AggregationWindowData.CumulativeData {
    private final Timestamp end;
    private final Timestamp start;

    AutoValue_ViewData_AggregationWindowData_CumulativeData(Timestamp timestamp, Timestamp timestamp2) {
        if (timestamp == null) throw new NullPointerException("Null start");
        this.start = timestamp;
        if (timestamp2 == null) throw new NullPointerException("Null end");
        this.end = timestamp2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof ViewData.AggregationWindowData.CumulativeData)) return false;
        if (!this.start.equals(((ViewData.AggregationWindowData.CumulativeData)(object = (ViewData.AggregationWindowData.CumulativeData)object)).getStart())) return false;
        if (!this.end.equals(((ViewData.AggregationWindowData.CumulativeData)object).getEnd())) return false;
        return bl;
    }

    @Override
    public Timestamp getEnd() {
        return this.end;
    }

    @Override
    public Timestamp getStart() {
        return this.start;
    }

    public int hashCode() {
        return (this.start.hashCode() ^ 1000003) * 1000003 ^ this.end.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CumulativeData{start=");
        stringBuilder.append(this.start);
        stringBuilder.append(", end=");
        stringBuilder.append(this.end);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


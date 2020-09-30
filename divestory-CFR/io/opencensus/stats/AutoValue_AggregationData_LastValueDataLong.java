/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.AggregationData;

final class AutoValue_AggregationData_LastValueDataLong
extends AggregationData.LastValueDataLong {
    private final long lastValue;

    AutoValue_AggregationData_LastValueDataLong(long l) {
        this.lastValue = l;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof AggregationData.LastValueDataLong)) return false;
        if (this.lastValue != ((AggregationData.LastValueDataLong)(object = (AggregationData.LastValueDataLong)object)).getLastValue()) return false;
        return bl;
    }

    @Override
    public long getLastValue() {
        return this.lastValue;
    }

    public int hashCode() {
        long l = 1000003;
        long l2 = this.lastValue;
        return (int)(l ^ (l2 ^ l2 >>> 32));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LastValueDataLong{lastValue=");
        stringBuilder.append(this.lastValue);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


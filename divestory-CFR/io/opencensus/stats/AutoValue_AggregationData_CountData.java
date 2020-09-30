/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.AggregationData;

final class AutoValue_AggregationData_CountData
extends AggregationData.CountData {
    private final long count;

    AutoValue_AggregationData_CountData(long l) {
        this.count = l;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof AggregationData.CountData)) return false;
        if (this.count != ((AggregationData.CountData)(object = (AggregationData.CountData)object)).getCount()) return false;
        return bl;
    }

    @Override
    public long getCount() {
        return this.count;
    }

    public int hashCode() {
        long l = 1000003;
        long l2 = this.count;
        return (int)(l ^ (l2 ^ l2 >>> 32));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CountData{count=");
        stringBuilder.append(this.count);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


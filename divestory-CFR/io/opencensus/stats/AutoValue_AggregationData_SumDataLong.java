/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.AggregationData;

final class AutoValue_AggregationData_SumDataLong
extends AggregationData.SumDataLong {
    private final long sum;

    AutoValue_AggregationData_SumDataLong(long l) {
        this.sum = l;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof AggregationData.SumDataLong)) return false;
        if (this.sum != ((AggregationData.SumDataLong)(object = (AggregationData.SumDataLong)object)).getSum()) return false;
        return bl;
    }

    @Override
    public long getSum() {
        return this.sum;
    }

    public int hashCode() {
        long l = 1000003;
        long l2 = this.sum;
        return (int)(l ^ (l2 ^ l2 >>> 32));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SumDataLong{sum=");
        stringBuilder.append(this.sum);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


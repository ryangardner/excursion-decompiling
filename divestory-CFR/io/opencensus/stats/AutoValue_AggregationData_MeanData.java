/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.AggregationData;

@Deprecated
final class AutoValue_AggregationData_MeanData
extends AggregationData.MeanData {
    private final long count;
    private final double mean;

    AutoValue_AggregationData_MeanData(double d, long l) {
        this.mean = d;
        this.count = l;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof AggregationData.MeanData)) return false;
        object = (AggregationData.MeanData)object;
        if (Double.doubleToLongBits(this.mean) != Double.doubleToLongBits(((AggregationData.MeanData)object).getMean())) return false;
        if (this.count != ((AggregationData.MeanData)object).getCount()) return false;
        return bl;
    }

    @Override
    public long getCount() {
        return this.count;
    }

    @Override
    public double getMean() {
        return this.mean;
    }

    public int hashCode() {
        long l = (int)((long)1000003 ^ (Double.doubleToLongBits(this.mean) >>> 32 ^ Double.doubleToLongBits(this.mean))) * 1000003;
        long l2 = this.count;
        return (int)(l ^ (l2 ^ l2 >>> 32));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MeanData{mean=");
        stringBuilder.append(this.mean);
        stringBuilder.append(", count=");
        stringBuilder.append(this.count);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


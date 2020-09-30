/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.AggregationData;

final class AutoValue_AggregationData_SumDataDouble
extends AggregationData.SumDataDouble {
    private final double sum;

    AutoValue_AggregationData_SumDataDouble(double d) {
        this.sum = d;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof AggregationData.SumDataDouble)) return false;
        object = (AggregationData.SumDataDouble)object;
        if (Double.doubleToLongBits(this.sum) != Double.doubleToLongBits(((AggregationData.SumDataDouble)object).getSum())) return false;
        return bl;
    }

    @Override
    public double getSum() {
        return this.sum;
    }

    public int hashCode() {
        return (int)((long)1000003 ^ (Double.doubleToLongBits(this.sum) >>> 32 ^ Double.doubleToLongBits(this.sum)));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SumDataDouble{sum=");
        stringBuilder.append(this.sum);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


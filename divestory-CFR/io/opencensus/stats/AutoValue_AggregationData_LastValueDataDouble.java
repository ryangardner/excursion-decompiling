/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.AggregationData;

final class AutoValue_AggregationData_LastValueDataDouble
extends AggregationData.LastValueDataDouble {
    private final double lastValue;

    AutoValue_AggregationData_LastValueDataDouble(double d) {
        this.lastValue = d;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof AggregationData.LastValueDataDouble)) return false;
        object = (AggregationData.LastValueDataDouble)object;
        if (Double.doubleToLongBits(this.lastValue) != Double.doubleToLongBits(((AggregationData.LastValueDataDouble)object).getLastValue())) return false;
        return bl;
    }

    @Override
    public double getLastValue() {
        return this.lastValue;
    }

    public int hashCode() {
        return (int)((long)1000003 ^ (Double.doubleToLongBits(this.lastValue) >>> 32 ^ Double.doubleToLongBits(this.lastValue)));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LastValueDataDouble{lastValue=");
        stringBuilder.append(this.lastValue);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


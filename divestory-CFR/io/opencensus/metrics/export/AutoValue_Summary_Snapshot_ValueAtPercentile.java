/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.Summary;

final class AutoValue_Summary_Snapshot_ValueAtPercentile
extends Summary.Snapshot.ValueAtPercentile {
    private final double percentile;
    private final double value;

    AutoValue_Summary_Snapshot_ValueAtPercentile(double d, double d2) {
        this.percentile = d;
        this.value = d2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Summary.Snapshot.ValueAtPercentile)) return false;
        object = (Summary.Snapshot.ValueAtPercentile)object;
        if (Double.doubleToLongBits(this.percentile) != Double.doubleToLongBits(((Summary.Snapshot.ValueAtPercentile)object).getPercentile())) return false;
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(((Summary.Snapshot.ValueAtPercentile)object).getValue())) return false;
        return bl;
    }

    @Override
    public double getPercentile() {
        return this.percentile;
    }

    @Override
    public double getValue() {
        return this.value;
    }

    public int hashCode() {
        return (int)((long)((int)((long)1000003 ^ (Double.doubleToLongBits(this.percentile) >>> 32 ^ Double.doubleToLongBits(this.percentile))) * 1000003) ^ (Double.doubleToLongBits(this.value) >>> 32 ^ Double.doubleToLongBits(this.value)));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ValueAtPercentile{percentile=");
        stringBuilder.append(this.percentile);
        stringBuilder.append(", value=");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.Summary;
import java.util.List;
import javax.annotation.Nullable;

final class AutoValue_Summary_Snapshot
extends Summary.Snapshot {
    private final Long count;
    private final Double sum;
    private final List<Summary.Snapshot.ValueAtPercentile> valueAtPercentiles;

    AutoValue_Summary_Snapshot(@Nullable Long l, @Nullable Double d, List<Summary.Snapshot.ValueAtPercentile> list) {
        this.count = l;
        this.sum = d;
        if (list == null) throw new NullPointerException("Null valueAtPercentiles");
        this.valueAtPercentiles = list;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Summary.Snapshot)) return false;
        object = (Summary.Snapshot)object;
        Number number = this.count;
        if (number == null) {
            if (((Summary.Snapshot)object).getCount() != null) return false;
        } else if (!((Long)number).equals(((Summary.Snapshot)object).getCount())) return false;
        number = this.sum;
        if (number == null) {
            if (((Summary.Snapshot)object).getSum() != null) return false;
        } else if (!((Double)number).equals(((Summary.Snapshot)object).getSum())) return false;
        if (!this.valueAtPercentiles.equals(((Summary.Snapshot)object).getValueAtPercentiles())) return false;
        return bl;
    }

    @Nullable
    @Override
    public Long getCount() {
        return this.count;
    }

    @Nullable
    @Override
    public Double getSum() {
        return this.sum;
    }

    @Override
    public List<Summary.Snapshot.ValueAtPercentile> getValueAtPercentiles() {
        return this.valueAtPercentiles;
    }

    public int hashCode() {
        Number number = this.count;
        int n = 0;
        int n2 = number == null ? 0 : ((Long)number).hashCode();
        number = this.sum;
        if (number == null) {
            return ((n2 ^ 1000003) * 1000003 ^ n) * 1000003 ^ this.valueAtPercentiles.hashCode();
        }
        n = ((Double)number).hashCode();
        return ((n2 ^ 1000003) * 1000003 ^ n) * 1000003 ^ this.valueAtPercentiles.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Snapshot{count=");
        stringBuilder.append(this.count);
        stringBuilder.append(", sum=");
        stringBuilder.append(this.sum);
        stringBuilder.append(", valueAtPercentiles=");
        stringBuilder.append(this.valueAtPercentiles);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


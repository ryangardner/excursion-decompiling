/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.Summary;
import javax.annotation.Nullable;

final class AutoValue_Summary
extends Summary {
    private final Long count;
    private final Summary.Snapshot snapshot;
    private final Double sum;

    AutoValue_Summary(@Nullable Long l, @Nullable Double d, Summary.Snapshot snapshot) {
        this.count = l;
        this.sum = d;
        if (snapshot == null) throw new NullPointerException("Null snapshot");
        this.snapshot = snapshot;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Summary)) return false;
        object = (Summary)object;
        Number number = this.count;
        if (number == null) {
            if (((Summary)object).getCount() != null) return false;
        } else if (!((Long)number).equals(((Summary)object).getCount())) return false;
        number = this.sum;
        if (number == null) {
            if (((Summary)object).getSum() != null) return false;
        } else if (!((Double)number).equals(((Summary)object).getSum())) return false;
        if (!this.snapshot.equals(((Summary)object).getSnapshot())) return false;
        return bl;
    }

    @Nullable
    @Override
    public Long getCount() {
        return this.count;
    }

    @Override
    public Summary.Snapshot getSnapshot() {
        return this.snapshot;
    }

    @Nullable
    @Override
    public Double getSum() {
        return this.sum;
    }

    public int hashCode() {
        Number number = this.count;
        int n = 0;
        int n2 = number == null ? 0 : ((Long)number).hashCode();
        number = this.sum;
        if (number == null) {
            return ((n2 ^ 1000003) * 1000003 ^ n) * 1000003 ^ this.snapshot.hashCode();
        }
        n = ((Double)number).hashCode();
        return ((n2 ^ 1000003) * 1000003 ^ n) * 1000003 ^ this.snapshot.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Summary{count=");
        stringBuilder.append(this.count);
        stringBuilder.append(", sum=");
        stringBuilder.append(this.sum);
        stringBuilder.append(", snapshot=");
        stringBuilder.append(this.snapshot);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


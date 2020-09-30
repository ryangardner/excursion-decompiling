/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.BucketBoundaries;
import java.util.List;

final class AutoValue_BucketBoundaries
extends BucketBoundaries {
    private final List<Double> boundaries;

    AutoValue_BucketBoundaries(List<Double> list) {
        if (list == null) throw new NullPointerException("Null boundaries");
        this.boundaries = list;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof BucketBoundaries)) return false;
        object = (BucketBoundaries)object;
        return this.boundaries.equals(((BucketBoundaries)object).getBoundaries());
    }

    @Override
    public List<Double> getBoundaries() {
        return this.boundaries;
    }

    public int hashCode() {
        return this.boundaries.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BucketBoundaries{boundaries=");
        stringBuilder.append(this.boundaries);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


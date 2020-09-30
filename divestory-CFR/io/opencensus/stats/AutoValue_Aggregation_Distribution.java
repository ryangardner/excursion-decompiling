/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.Aggregation;
import io.opencensus.stats.BucketBoundaries;

final class AutoValue_Aggregation_Distribution
extends Aggregation.Distribution {
    private final BucketBoundaries bucketBoundaries;

    AutoValue_Aggregation_Distribution(BucketBoundaries bucketBoundaries) {
        if (bucketBoundaries == null) throw new NullPointerException("Null bucketBoundaries");
        this.bucketBoundaries = bucketBoundaries;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Aggregation.Distribution)) return false;
        object = (Aggregation.Distribution)object;
        return this.bucketBoundaries.equals(((Aggregation.Distribution)object).getBucketBoundaries());
    }

    @Override
    public BucketBoundaries getBucketBoundaries() {
        return this.bucketBoundaries;
    }

    public int hashCode() {
        return this.bucketBoundaries.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Distribution{bucketBoundaries=");
        stringBuilder.append(this.bucketBoundaries);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


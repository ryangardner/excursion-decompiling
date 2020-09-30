/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.Distribution;
import java.util.List;

final class AutoValue_Distribution_BucketOptions_ExplicitOptions
extends Distribution.BucketOptions.ExplicitOptions {
    private final List<Double> bucketBoundaries;

    AutoValue_Distribution_BucketOptions_ExplicitOptions(List<Double> list) {
        if (list == null) throw new NullPointerException("Null bucketBoundaries");
        this.bucketBoundaries = list;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Distribution.BucketOptions.ExplicitOptions)) return false;
        object = (Distribution.BucketOptions.ExplicitOptions)object;
        return this.bucketBoundaries.equals(((Distribution.BucketOptions.ExplicitOptions)object).getBucketBoundaries());
    }

    @Override
    public List<Double> getBucketBoundaries() {
        return this.bucketBoundaries;
    }

    public int hashCode() {
        return this.bucketBoundaries.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ExplicitOptions{bucketBoundaries=");
        stringBuilder.append(this.bucketBoundaries);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


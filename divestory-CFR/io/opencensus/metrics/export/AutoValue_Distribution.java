/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.Distribution;
import java.util.List;
import javax.annotation.Nullable;

final class AutoValue_Distribution
extends Distribution {
    private final Distribution.BucketOptions bucketOptions;
    private final List<Distribution.Bucket> buckets;
    private final long count;
    private final double sum;
    private final double sumOfSquaredDeviations;

    AutoValue_Distribution(long l, double d, double d2, @Nullable Distribution.BucketOptions bucketOptions, List<Distribution.Bucket> list) {
        this.count = l;
        this.sum = d;
        this.sumOfSquaredDeviations = d2;
        this.bucketOptions = bucketOptions;
        if (list == null) throw new NullPointerException("Null buckets");
        this.buckets = list;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Distribution)) return false;
        if (this.count != ((Distribution)(object = (Distribution)object)).getCount()) return false;
        if (Double.doubleToLongBits(this.sum) != Double.doubleToLongBits(((Distribution)object).getSum())) return false;
        if (Double.doubleToLongBits(this.sumOfSquaredDeviations) != Double.doubleToLongBits(((Distribution)object).getSumOfSquaredDeviations())) return false;
        Distribution.BucketOptions bucketOptions = this.bucketOptions;
        if (bucketOptions == null) {
            if (((Distribution)object).getBucketOptions() != null) return false;
        } else if (!bucketOptions.equals(((Distribution)object).getBucketOptions())) return false;
        if (!this.buckets.equals(((Distribution)object).getBuckets())) return false;
        return bl;
    }

    @Nullable
    @Override
    public Distribution.BucketOptions getBucketOptions() {
        return this.bucketOptions;
    }

    @Override
    public List<Distribution.Bucket> getBuckets() {
        return this.buckets;
    }

    @Override
    public long getCount() {
        return this.count;
    }

    @Override
    public double getSum() {
        return this.sum;
    }

    @Override
    public double getSumOfSquaredDeviations() {
        return this.sumOfSquaredDeviations;
    }

    public int hashCode() {
        int n;
        long l = 1000003;
        long l2 = this.count;
        int n2 = (int)((long)((int)((long)((int)(l ^ (l2 ^ l2 >>> 32)) * 1000003) ^ (Double.doubleToLongBits(this.sum) >>> 32 ^ Double.doubleToLongBits(this.sum))) * 1000003) ^ (Double.doubleToLongBits(this.sumOfSquaredDeviations) >>> 32 ^ Double.doubleToLongBits(this.sumOfSquaredDeviations)));
        Distribution.BucketOptions bucketOptions = this.bucketOptions;
        if (bucketOptions == null) {
            n = 0;
            return this.buckets.hashCode() ^ (n ^ n2 * 1000003) * 1000003;
        }
        n = bucketOptions.hashCode();
        return this.buckets.hashCode() ^ (n ^ n2 * 1000003) * 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Distribution{count=");
        stringBuilder.append(this.count);
        stringBuilder.append(", sum=");
        stringBuilder.append(this.sum);
        stringBuilder.append(", sumOfSquaredDeviations=");
        stringBuilder.append(this.sumOfSquaredDeviations);
        stringBuilder.append(", bucketOptions=");
        stringBuilder.append(this.bucketOptions);
        stringBuilder.append(", buckets=");
        stringBuilder.append(this.buckets);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


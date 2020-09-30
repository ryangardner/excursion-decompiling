/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.metrics.data.Exemplar;
import io.opencensus.stats.AggregationData;
import java.util.List;

final class AutoValue_AggregationData_DistributionData
extends AggregationData.DistributionData {
    private final List<Long> bucketCounts;
    private final long count;
    private final List<Exemplar> exemplars;
    private final double mean;
    private final double sumOfSquaredDeviations;

    AutoValue_AggregationData_DistributionData(double d, long l, double d2, List<Long> list, List<Exemplar> list2) {
        this.mean = d;
        this.count = l;
        this.sumOfSquaredDeviations = d2;
        if (list == null) throw new NullPointerException("Null bucketCounts");
        this.bucketCounts = list;
        if (list2 == null) throw new NullPointerException("Null exemplars");
        this.exemplars = list2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof AggregationData.DistributionData)) return false;
        object = (AggregationData.DistributionData)object;
        if (Double.doubleToLongBits(this.mean) != Double.doubleToLongBits(((AggregationData.DistributionData)object).getMean())) return false;
        if (this.count != ((AggregationData.DistributionData)object).getCount()) return false;
        if (Double.doubleToLongBits(this.sumOfSquaredDeviations) != Double.doubleToLongBits(((AggregationData.DistributionData)object).getSumOfSquaredDeviations())) return false;
        if (!this.bucketCounts.equals(((AggregationData.DistributionData)object).getBucketCounts())) return false;
        if (!this.exemplars.equals(((AggregationData.DistributionData)object).getExemplars())) return false;
        return bl;
    }

    @Override
    public List<Long> getBucketCounts() {
        return this.bucketCounts;
    }

    @Override
    public long getCount() {
        return this.count;
    }

    @Override
    public List<Exemplar> getExemplars() {
        return this.exemplars;
    }

    @Override
    public double getMean() {
        return this.mean;
    }

    @Override
    public double getSumOfSquaredDeviations() {
        return this.sumOfSquaredDeviations;
    }

    public int hashCode() {
        long l = (int)((long)1000003 ^ (Double.doubleToLongBits(this.mean) >>> 32 ^ Double.doubleToLongBits(this.mean))) * 1000003;
        long l2 = this.count;
        int n = (int)((long)((int)(l ^ (l2 ^ l2 >>> 32)) * 1000003) ^ (Double.doubleToLongBits(this.sumOfSquaredDeviations) >>> 32 ^ Double.doubleToLongBits(this.sumOfSquaredDeviations)));
        int n2 = this.bucketCounts.hashCode();
        return this.exemplars.hashCode() ^ (n2 ^ n * 1000003) * 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DistributionData{mean=");
        stringBuilder.append(this.mean);
        stringBuilder.append(", count=");
        stringBuilder.append(this.count);
        stringBuilder.append(", sumOfSquaredDeviations=");
        stringBuilder.append(this.sumOfSquaredDeviations);
        stringBuilder.append(", bucketCounts=");
        stringBuilder.append(this.bucketCounts);
        stringBuilder.append(", exemplars=");
        stringBuilder.append(this.exemplars);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


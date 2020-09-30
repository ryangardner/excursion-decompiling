package io.opencensus.stats;

import io.opencensus.metrics.data.Exemplar;
import java.util.List;

final class AutoValue_AggregationData_DistributionData extends AggregationData.DistributionData {
   private final List<Long> bucketCounts;
   private final long count;
   private final List<Exemplar> exemplars;
   private final double mean;
   private final double sumOfSquaredDeviations;

   AutoValue_AggregationData_DistributionData(double var1, long var3, double var5, List<Long> var7, List<Exemplar> var8) {
      this.mean = var1;
      this.count = var3;
      this.sumOfSquaredDeviations = var5;
      if (var7 != null) {
         this.bucketCounts = var7;
         if (var8 != null) {
            this.exemplars = var8;
         } else {
            throw new NullPointerException("Null exemplars");
         }
      } else {
         throw new NullPointerException("Null bucketCounts");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof AggregationData.DistributionData)) {
         return false;
      } else {
         AggregationData.DistributionData var3 = (AggregationData.DistributionData)var1;
         if (Double.doubleToLongBits(this.mean) != Double.doubleToLongBits(var3.getMean()) || this.count != var3.getCount() || Double.doubleToLongBits(this.sumOfSquaredDeviations) != Double.doubleToLongBits(var3.getSumOfSquaredDeviations()) || !this.bucketCounts.equals(var3.getBucketCounts()) || !this.exemplars.equals(var3.getExemplars())) {
            var2 = false;
         }

         return var2;
      }
   }

   public List<Long> getBucketCounts() {
      return this.bucketCounts;
   }

   public long getCount() {
      return this.count;
   }

   public List<Exemplar> getExemplars() {
      return this.exemplars;
   }

   public double getMean() {
      return this.mean;
   }

   public double getSumOfSquaredDeviations() {
      return this.sumOfSquaredDeviations;
   }

   public int hashCode() {
      long var1 = (long)((int)((long)1000003 ^ Double.doubleToLongBits(this.mean) >>> 32 ^ Double.doubleToLongBits(this.mean)) * 1000003);
      long var3 = this.count;
      int var5 = (int)((long)((int)(var1 ^ var3 ^ var3 >>> 32) * 1000003) ^ Double.doubleToLongBits(this.sumOfSquaredDeviations) >>> 32 ^ Double.doubleToLongBits(this.sumOfSquaredDeviations));
      int var6 = this.bucketCounts.hashCode();
      return this.exemplars.hashCode() ^ (var6 ^ var5 * 1000003) * 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("DistributionData{mean=");
      var1.append(this.mean);
      var1.append(", count=");
      var1.append(this.count);
      var1.append(", sumOfSquaredDeviations=");
      var1.append(this.sumOfSquaredDeviations);
      var1.append(", bucketCounts=");
      var1.append(this.bucketCounts);
      var1.append(", exemplars=");
      var1.append(this.exemplars);
      var1.append("}");
      return var1.toString();
   }
}

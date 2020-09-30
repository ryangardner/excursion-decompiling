package io.opencensus.metrics.export;

import java.util.List;
import javax.annotation.Nullable;

final class AutoValue_Distribution extends Distribution {
   private final Distribution.BucketOptions bucketOptions;
   private final List<Distribution.Bucket> buckets;
   private final long count;
   private final double sum;
   private final double sumOfSquaredDeviations;

   AutoValue_Distribution(long var1, double var3, double var5, @Nullable Distribution.BucketOptions var7, List<Distribution.Bucket> var8) {
      this.count = var1;
      this.sum = var3;
      this.sumOfSquaredDeviations = var5;
      this.bucketOptions = var7;
      if (var8 != null) {
         this.buckets = var8;
      } else {
         throw new NullPointerException("Null buckets");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Distribution)) {
         return false;
      } else {
         Distribution var4 = (Distribution)var1;
         if (this.count == var4.getCount() && Double.doubleToLongBits(this.sum) == Double.doubleToLongBits(var4.getSum()) && Double.doubleToLongBits(this.sumOfSquaredDeviations) == Double.doubleToLongBits(var4.getSumOfSquaredDeviations())) {
            label26: {
               Distribution.BucketOptions var3 = this.bucketOptions;
               if (var3 == null) {
                  if (var4.getBucketOptions() != null) {
                     break label26;
                  }
               } else if (!var3.equals(var4.getBucketOptions())) {
                  break label26;
               }

               if (this.buckets.equals(var4.getBuckets())) {
                  return var2;
               }
            }
         }

         var2 = false;
         return var2;
      }
   }

   @Nullable
   public Distribution.BucketOptions getBucketOptions() {
      return this.bucketOptions;
   }

   public List<Distribution.Bucket> getBuckets() {
      return this.buckets;
   }

   public long getCount() {
      return this.count;
   }

   public double getSum() {
      return this.sum;
   }

   public double getSumOfSquaredDeviations() {
      return this.sumOfSquaredDeviations;
   }

   public int hashCode() {
      long var1 = (long)1000003;
      long var3 = this.count;
      int var5 = (int)((long)((int)((long)((int)(var1 ^ var3 ^ var3 >>> 32) * 1000003) ^ Double.doubleToLongBits(this.sum) >>> 32 ^ Double.doubleToLongBits(this.sum)) * 1000003) ^ Double.doubleToLongBits(this.sumOfSquaredDeviations) >>> 32 ^ Double.doubleToLongBits(this.sumOfSquaredDeviations));
      Distribution.BucketOptions var6 = this.bucketOptions;
      int var7;
      if (var6 == null) {
         var7 = 0;
      } else {
         var7 = var6.hashCode();
      }

      return this.buckets.hashCode() ^ (var7 ^ var5 * 1000003) * 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Distribution{count=");
      var1.append(this.count);
      var1.append(", sum=");
      var1.append(this.sum);
      var1.append(", sumOfSquaredDeviations=");
      var1.append(this.sumOfSquaredDeviations);
      var1.append(", bucketOptions=");
      var1.append(this.bucketOptions);
      var1.append(", buckets=");
      var1.append(this.buckets);
      var1.append("}");
      return var1.toString();
   }
}

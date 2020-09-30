package io.opencensus.metrics.export;

import java.util.List;

final class AutoValue_Distribution_BucketOptions_ExplicitOptions extends Distribution.BucketOptions.ExplicitOptions {
   private final List<Double> bucketBoundaries;

   AutoValue_Distribution_BucketOptions_ExplicitOptions(List<Double> var1) {
      if (var1 != null) {
         this.bucketBoundaries = var1;
      } else {
         throw new NullPointerException("Null bucketBoundaries");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof Distribution.BucketOptions.ExplicitOptions) {
         Distribution.BucketOptions.ExplicitOptions var2 = (Distribution.BucketOptions.ExplicitOptions)var1;
         return this.bucketBoundaries.equals(var2.getBucketBoundaries());
      } else {
         return false;
      }
   }

   public List<Double> getBucketBoundaries() {
      return this.bucketBoundaries;
   }

   public int hashCode() {
      return this.bucketBoundaries.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("ExplicitOptions{bucketBoundaries=");
      var1.append(this.bucketBoundaries);
      var1.append("}");
      return var1.toString();
   }
}

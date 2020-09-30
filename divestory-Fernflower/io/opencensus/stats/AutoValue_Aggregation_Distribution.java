package io.opencensus.stats;

final class AutoValue_Aggregation_Distribution extends Aggregation.Distribution {
   private final BucketBoundaries bucketBoundaries;

   AutoValue_Aggregation_Distribution(BucketBoundaries var1) {
      if (var1 != null) {
         this.bucketBoundaries = var1;
      } else {
         throw new NullPointerException("Null bucketBoundaries");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof Aggregation.Distribution) {
         Aggregation.Distribution var2 = (Aggregation.Distribution)var1;
         return this.bucketBoundaries.equals(var2.getBucketBoundaries());
      } else {
         return false;
      }
   }

   public BucketBoundaries getBucketBoundaries() {
      return this.bucketBoundaries;
   }

   public int hashCode() {
      return this.bucketBoundaries.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Distribution{bucketBoundaries=");
      var1.append(this.bucketBoundaries);
      var1.append("}");
      return var1.toString();
   }
}

package io.opencensus.stats;

import java.util.List;

final class AutoValue_BucketBoundaries extends BucketBoundaries {
   private final List<Double> boundaries;

   AutoValue_BucketBoundaries(List<Double> var1) {
      if (var1 != null) {
         this.boundaries = var1;
      } else {
         throw new NullPointerException("Null boundaries");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof BucketBoundaries) {
         BucketBoundaries var2 = (BucketBoundaries)var1;
         return this.boundaries.equals(var2.getBoundaries());
      } else {
         return false;
      }
   }

   public List<Double> getBoundaries() {
      return this.boundaries;
   }

   public int hashCode() {
      return this.boundaries.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("BucketBoundaries{boundaries=");
      var1.append(this.boundaries);
      var1.append("}");
      return var1.toString();
   }
}

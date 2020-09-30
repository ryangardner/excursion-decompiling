package io.opencensus.metrics.export;

import io.opencensus.common.Function;
import io.opencensus.internal.Utils;
import io.opencensus.metrics.data.Exemplar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public abstract class Distribution {
   Distribution() {
   }

   public static Distribution create(long var0, double var2, double var4, Distribution.BucketOptions var6, List<Distribution.Bucket> var7) {
      boolean var8 = true;
      long var12;
      int var9 = (var12 = var0 - 0L) == 0L ? 0 : (var12 < 0L ? -1 : 1);
      boolean var10;
      if (var9 >= 0) {
         var10 = true;
      } else {
         var10 = false;
      }

      Utils.checkArgument(var10, "count should be non-negative.");
      double var13;
      int var11 = (var13 = var4 - 0.0D) == 0.0D ? 0 : (var13 < 0.0D ? -1 : 1);
      if (var11 >= 0) {
         var10 = true;
      } else {
         var10 = false;
      }

      Utils.checkArgument(var10, "sum of squared deviations should be non-negative.");
      if (var9 == 0) {
         if (var2 == 0.0D) {
            var10 = true;
         } else {
            var10 = false;
         }

         Utils.checkArgument(var10, "sum should be 0 if count is 0.");
         if (var11 == 0) {
            var10 = var8;
         } else {
            var10 = false;
         }

         Utils.checkArgument(var10, "sum of squared deviations should be 0 if count is 0.");
      }

      Utils.checkNotNull(var6, "bucketOptions");
      var7 = Collections.unmodifiableList(new ArrayList((Collection)Utils.checkNotNull(var7, "buckets")));
      Utils.checkListElementNotNull(var7, "bucket");
      return new AutoValue_Distribution(var0, var2, var4, var6, var7);
   }

   @Nullable
   public abstract Distribution.BucketOptions getBucketOptions();

   public abstract List<Distribution.Bucket> getBuckets();

   public abstract long getCount();

   public abstract double getSum();

   public abstract double getSumOfSquaredDeviations();

   public abstract static class Bucket {
      Bucket() {
      }

      public static Distribution.Bucket create(long var0) {
         boolean var2;
         if (var0 >= 0L) {
            var2 = true;
         } else {
            var2 = false;
         }

         Utils.checkArgument(var2, "bucket count should be non-negative.");
         return new AutoValue_Distribution_Bucket(var0, (Exemplar)null);
      }

      public static Distribution.Bucket create(long var0, Exemplar var2) {
         boolean var3;
         if (var0 >= 0L) {
            var3 = true;
         } else {
            var3 = false;
         }

         Utils.checkArgument(var3, "bucket count should be non-negative.");
         Utils.checkNotNull(var2, "exemplar");
         return new AutoValue_Distribution_Bucket(var0, var2);
      }

      public abstract long getCount();

      @Nullable
      public abstract Exemplar getExemplar();
   }

   public abstract static class BucketOptions {
      private BucketOptions() {
      }

      // $FF: synthetic method
      BucketOptions(Object var1) {
         this();
      }

      public static Distribution.BucketOptions explicitOptions(List<Double> var0) {
         return Distribution.BucketOptions.ExplicitOptions.create(var0);
      }

      public abstract <T> T match(Function<? super Distribution.BucketOptions.ExplicitOptions, T> var1, Function<? super Distribution.BucketOptions, T> var2);

      public abstract static class ExplicitOptions extends Distribution.BucketOptions {
         ExplicitOptions() {
            super(null);
         }

         private static void checkBucketBoundsAreSorted(List<Double> var0) {
            if (var0.size() >= 1) {
               double var1 = (Double)Utils.checkNotNull(var0.get(0), "bucketBoundary");
               boolean var3;
               if (var1 > 0.0D) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               Utils.checkArgument(var3, "bucket boundary should be > 0");

               double var5;
               for(int var4 = 1; var4 < var0.size(); var1 = var5) {
                  var5 = (Double)Utils.checkNotNull(var0.get(var4), "bucketBoundary");
                  if (var1 < var5) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  Utils.checkArgument(var3, "bucket boundaries not sorted.");
                  ++var4;
               }
            }

         }

         private static Distribution.BucketOptions.ExplicitOptions create(List<Double> var0) {
            Utils.checkNotNull(var0, "bucketBoundaries");
            var0 = Collections.unmodifiableList(new ArrayList(var0));
            checkBucketBoundsAreSorted(var0);
            return new AutoValue_Distribution_BucketOptions_ExplicitOptions(var0);
         }

         public abstract List<Double> getBucketBoundaries();

         public final <T> T match(Function<? super Distribution.BucketOptions.ExplicitOptions, T> var1, Function<? super Distribution.BucketOptions, T> var2) {
            return var1.apply(this);
         }
      }
   }
}

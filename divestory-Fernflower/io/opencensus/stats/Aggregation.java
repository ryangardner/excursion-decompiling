package io.opencensus.stats;

import io.opencensus.common.Function;
import io.opencensus.internal.Utils;

public abstract class Aggregation {
   private Aggregation() {
   }

   // $FF: synthetic method
   Aggregation(Object var1) {
      this();
   }

   public abstract <T> T match(Function<? super Aggregation.Sum, T> var1, Function<? super Aggregation.Count, T> var2, Function<? super Aggregation.Distribution, T> var3, Function<? super Aggregation.LastValue, T> var4, Function<? super Aggregation, T> var5);

   public abstract static class Count extends Aggregation {
      private static final Aggregation.Count INSTANCE = new AutoValue_Aggregation_Count();

      Count() {
         super(null);
      }

      public static Aggregation.Count create() {
         return INSTANCE;
      }

      public final <T> T match(Function<? super Aggregation.Sum, T> var1, Function<? super Aggregation.Count, T> var2, Function<? super Aggregation.Distribution, T> var3, Function<? super Aggregation.LastValue, T> var4, Function<? super Aggregation, T> var5) {
         return var2.apply(this);
      }
   }

   public abstract static class Distribution extends Aggregation {
      Distribution() {
         super(null);
      }

      public static Aggregation.Distribution create(BucketBoundaries var0) {
         Utils.checkNotNull(var0, "bucketBoundaries");
         return new AutoValue_Aggregation_Distribution(var0);
      }

      public abstract BucketBoundaries getBucketBoundaries();

      public final <T> T match(Function<? super Aggregation.Sum, T> var1, Function<? super Aggregation.Count, T> var2, Function<? super Aggregation.Distribution, T> var3, Function<? super Aggregation.LastValue, T> var4, Function<? super Aggregation, T> var5) {
         return var3.apply(this);
      }
   }

   public abstract static class LastValue extends Aggregation {
      private static final Aggregation.LastValue INSTANCE = new AutoValue_Aggregation_LastValue();

      LastValue() {
         super(null);
      }

      public static Aggregation.LastValue create() {
         return INSTANCE;
      }

      public final <T> T match(Function<? super Aggregation.Sum, T> var1, Function<? super Aggregation.Count, T> var2, Function<? super Aggregation.Distribution, T> var3, Function<? super Aggregation.LastValue, T> var4, Function<? super Aggregation, T> var5) {
         return var4.apply(this);
      }
   }

   @Deprecated
   public abstract static class Mean extends Aggregation {
      private static final Aggregation.Mean INSTANCE = new AutoValue_Aggregation_Mean();

      Mean() {
         super(null);
      }

      public static Aggregation.Mean create() {
         return INSTANCE;
      }

      public final <T> T match(Function<? super Aggregation.Sum, T> var1, Function<? super Aggregation.Count, T> var2, Function<? super Aggregation.Distribution, T> var3, Function<? super Aggregation.LastValue, T> var4, Function<? super Aggregation, T> var5) {
         return var5.apply(this);
      }
   }

   public abstract static class Sum extends Aggregation {
      private static final Aggregation.Sum INSTANCE = new AutoValue_Aggregation_Sum();

      Sum() {
         super(null);
      }

      public static Aggregation.Sum create() {
         return INSTANCE;
      }

      public final <T> T match(Function<? super Aggregation.Sum, T> var1, Function<? super Aggregation.Count, T> var2, Function<? super Aggregation.Distribution, T> var3, Function<? super Aggregation.LastValue, T> var4, Function<? super Aggregation, T> var5) {
         return var1.apply(this);
      }
   }
}

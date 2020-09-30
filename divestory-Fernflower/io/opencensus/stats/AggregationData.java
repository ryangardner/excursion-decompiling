package io.opencensus.stats;

import io.opencensus.common.Function;
import io.opencensus.internal.Utils;
import io.opencensus.metrics.data.Exemplar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class AggregationData {
   private AggregationData() {
   }

   // $FF: synthetic method
   AggregationData(Object var1) {
      this();
   }

   public abstract <T> T match(Function<? super AggregationData.SumDataDouble, T> var1, Function<? super AggregationData.SumDataLong, T> var2, Function<? super AggregationData.CountData, T> var3, Function<? super AggregationData.DistributionData, T> var4, Function<? super AggregationData.LastValueDataDouble, T> var5, Function<? super AggregationData.LastValueDataLong, T> var6, Function<? super AggregationData, T> var7);

   public abstract static class CountData extends AggregationData {
      CountData() {
         super(null);
      }

      public static AggregationData.CountData create(long var0) {
         return new AutoValue_AggregationData_CountData(var0);
      }

      public abstract long getCount();

      public final <T> T match(Function<? super AggregationData.SumDataDouble, T> var1, Function<? super AggregationData.SumDataLong, T> var2, Function<? super AggregationData.CountData, T> var3, Function<? super AggregationData.DistributionData, T> var4, Function<? super AggregationData.LastValueDataDouble, T> var5, Function<? super AggregationData.LastValueDataLong, T> var6, Function<? super AggregationData, T> var7) {
         return var3.apply(this);
      }
   }

   public abstract static class DistributionData extends AggregationData {
      DistributionData() {
         super(null);
      }

      @Deprecated
      public static AggregationData.DistributionData create(double var0, long var2, double var4, double var6, double var8, List<Long> var10) {
         return create(var0, var2, var8, var10, Collections.emptyList());
      }

      @Deprecated
      public static AggregationData.DistributionData create(double var0, long var2, double var4, double var6, double var8, List<Long> var10, List<Exemplar> var11) {
         return create(var0, var2, var8, var10, var11);
      }

      public static AggregationData.DistributionData create(double var0, long var2, double var4, List<Long> var6) {
         return create(var0, var2, var4, var6, Collections.emptyList());
      }

      public static AggregationData.DistributionData create(double var0, long var2, double var4, List<Long> var6, List<Exemplar> var7) {
         var6 = Collections.unmodifiableList(new ArrayList((Collection)Utils.checkNotNull(var6, "bucketCounts")));
         Iterator var8 = var6.iterator();

         while(var8.hasNext()) {
            Utils.checkNotNull((Long)var8.next(), "bucketCount");
         }

         Utils.checkNotNull(var7, "exemplars");
         var8 = var7.iterator();

         while(var8.hasNext()) {
            Utils.checkNotNull((Exemplar)var8.next(), "exemplar");
         }

         return new AutoValue_AggregationData_DistributionData(var0, var2, var4, var6, Collections.unmodifiableList(new ArrayList(var7)));
      }

      public abstract List<Long> getBucketCounts();

      public abstract long getCount();

      public abstract List<Exemplar> getExemplars();

      @Deprecated
      public double getMax() {
         return 0.0D;
      }

      public abstract double getMean();

      @Deprecated
      public double getMin() {
         return 0.0D;
      }

      public abstract double getSumOfSquaredDeviations();

      public final <T> T match(Function<? super AggregationData.SumDataDouble, T> var1, Function<? super AggregationData.SumDataLong, T> var2, Function<? super AggregationData.CountData, T> var3, Function<? super AggregationData.DistributionData, T> var4, Function<? super AggregationData.LastValueDataDouble, T> var5, Function<? super AggregationData.LastValueDataLong, T> var6, Function<? super AggregationData, T> var7) {
         return var4.apply(this);
      }
   }

   public abstract static class LastValueDataDouble extends AggregationData {
      LastValueDataDouble() {
         super(null);
      }

      public static AggregationData.LastValueDataDouble create(double var0) {
         return new AutoValue_AggregationData_LastValueDataDouble(var0);
      }

      public abstract double getLastValue();

      public final <T> T match(Function<? super AggregationData.SumDataDouble, T> var1, Function<? super AggregationData.SumDataLong, T> var2, Function<? super AggregationData.CountData, T> var3, Function<? super AggregationData.DistributionData, T> var4, Function<? super AggregationData.LastValueDataDouble, T> var5, Function<? super AggregationData.LastValueDataLong, T> var6, Function<? super AggregationData, T> var7) {
         return var5.apply(this);
      }
   }

   public abstract static class LastValueDataLong extends AggregationData {
      LastValueDataLong() {
         super(null);
      }

      public static AggregationData.LastValueDataLong create(long var0) {
         return new AutoValue_AggregationData_LastValueDataLong(var0);
      }

      public abstract long getLastValue();

      public final <T> T match(Function<? super AggregationData.SumDataDouble, T> var1, Function<? super AggregationData.SumDataLong, T> var2, Function<? super AggregationData.CountData, T> var3, Function<? super AggregationData.DistributionData, T> var4, Function<? super AggregationData.LastValueDataDouble, T> var5, Function<? super AggregationData.LastValueDataLong, T> var6, Function<? super AggregationData, T> var7) {
         return var6.apply(this);
      }
   }

   @Deprecated
   public abstract static class MeanData extends AggregationData {
      MeanData() {
         super(null);
      }

      public static AggregationData.MeanData create(double var0, long var2) {
         return new AutoValue_AggregationData_MeanData(var0, var2);
      }

      public abstract long getCount();

      public abstract double getMean();

      public final <T> T match(Function<? super AggregationData.SumDataDouble, T> var1, Function<? super AggregationData.SumDataLong, T> var2, Function<? super AggregationData.CountData, T> var3, Function<? super AggregationData.DistributionData, T> var4, Function<? super AggregationData.LastValueDataDouble, T> var5, Function<? super AggregationData.LastValueDataLong, T> var6, Function<? super AggregationData, T> var7) {
         return var7.apply(this);
      }
   }

   public abstract static class SumDataDouble extends AggregationData {
      SumDataDouble() {
         super(null);
      }

      public static AggregationData.SumDataDouble create(double var0) {
         return new AutoValue_AggregationData_SumDataDouble(var0);
      }

      public abstract double getSum();

      public final <T> T match(Function<? super AggregationData.SumDataDouble, T> var1, Function<? super AggregationData.SumDataLong, T> var2, Function<? super AggregationData.CountData, T> var3, Function<? super AggregationData.DistributionData, T> var4, Function<? super AggregationData.LastValueDataDouble, T> var5, Function<? super AggregationData.LastValueDataLong, T> var6, Function<? super AggregationData, T> var7) {
         return var1.apply(this);
      }
   }

   public abstract static class SumDataLong extends AggregationData {
      SumDataLong() {
         super(null);
      }

      public static AggregationData.SumDataLong create(long var0) {
         return new AutoValue_AggregationData_SumDataLong(var0);
      }

      public abstract long getSum();

      public final <T> T match(Function<? super AggregationData.SumDataDouble, T> var1, Function<? super AggregationData.SumDataLong, T> var2, Function<? super AggregationData.CountData, T> var3, Function<? super AggregationData.DistributionData, T> var4, Function<? super AggregationData.LastValueDataDouble, T> var5, Function<? super AggregationData.LastValueDataLong, T> var6, Function<? super AggregationData, T> var7) {
         return var2.apply(this);
      }
   }
}

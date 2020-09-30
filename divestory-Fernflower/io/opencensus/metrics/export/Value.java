package io.opencensus.metrics.export;

import io.opencensus.common.Function;

public abstract class Value {
   Value() {
   }

   public static Value distributionValue(Distribution var0) {
      return Value.ValueDistribution.create(var0);
   }

   public static Value doubleValue(double var0) {
      return Value.ValueDouble.create(var0);
   }

   public static Value longValue(long var0) {
      return Value.ValueLong.create(var0);
   }

   public static Value summaryValue(Summary var0) {
      return Value.ValueSummary.create(var0);
   }

   public abstract <T> T match(Function<? super Double, T> var1, Function<? super Long, T> var2, Function<? super Distribution, T> var3, Function<? super Summary, T> var4, Function<? super Value, T> var5);

   abstract static class ValueDistribution extends Value {
      static Value.ValueDistribution create(Distribution var0) {
         return new AutoValue_Value_ValueDistribution(var0);
      }

      abstract Distribution getValue();

      public final <T> T match(Function<? super Double, T> var1, Function<? super Long, T> var2, Function<? super Distribution, T> var3, Function<? super Summary, T> var4, Function<? super Value, T> var5) {
         return var3.apply(this.getValue());
      }
   }

   abstract static class ValueDouble extends Value {
      static Value.ValueDouble create(double var0) {
         return new AutoValue_Value_ValueDouble(var0);
      }

      abstract double getValue();

      public final <T> T match(Function<? super Double, T> var1, Function<? super Long, T> var2, Function<? super Distribution, T> var3, Function<? super Summary, T> var4, Function<? super Value, T> var5) {
         return var1.apply(this.getValue());
      }
   }

   abstract static class ValueLong extends Value {
      static Value.ValueLong create(long var0) {
         return new AutoValue_Value_ValueLong(var0);
      }

      abstract long getValue();

      public final <T> T match(Function<? super Double, T> var1, Function<? super Long, T> var2, Function<? super Distribution, T> var3, Function<? super Summary, T> var4, Function<? super Value, T> var5) {
         return var2.apply(this.getValue());
      }
   }

   abstract static class ValueSummary extends Value {
      static Value.ValueSummary create(Summary var0) {
         return new AutoValue_Value_ValueSummary(var0);
      }

      abstract Summary getValue();

      public final <T> T match(Function<? super Double, T> var1, Function<? super Long, T> var2, Function<? super Distribution, T> var3, Function<? super Summary, T> var4, Function<? super Value, T> var5) {
         return var4.apply(this.getValue());
      }
   }
}

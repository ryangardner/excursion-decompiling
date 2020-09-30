package com.google.common.math;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Stats implements Serializable {
   static final int BYTES = 40;
   private static final long serialVersionUID = 0L;
   private final long count;
   private final double max;
   private final double mean;
   private final double min;
   private final double sumOfSquaresOfDeltas;

   Stats(long var1, double var3, double var5, double var7, double var9) {
      this.count = var1;
      this.mean = var3;
      this.sumOfSquaresOfDeltas = var5;
      this.min = var7;
      this.max = var9;
   }

   public static Stats fromByteArray(byte[] var0) {
      Preconditions.checkNotNull(var0);
      boolean var1;
      if (var0.length == 40) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Expected Stats.BYTES = %s remaining , got %s", (int)40, (int)var0.length);
      return readFrom(ByteBuffer.wrap(var0).order(ByteOrder.LITTLE_ENDIAN));
   }

   public static double meanOf(Iterable<? extends Number> var0) {
      return meanOf(var0.iterator());
   }

   public static double meanOf(Iterator<? extends Number> var0) {
      Preconditions.checkArgument(var0.hasNext());
      double var1 = ((Number)var0.next()).doubleValue();
      long var3 = 1L;

      while(true) {
         while(var0.hasNext()) {
            double var5 = ((Number)var0.next()).doubleValue();
            ++var3;
            if (Doubles.isFinite(var5) && Doubles.isFinite(var1)) {
               var1 += (var5 - var1) / (double)var3;
            } else {
               var1 = StatsAccumulator.calculateNewMeanNonFinite(var1, var5);
            }
         }

         return var1;
      }
   }

   public static double meanOf(double... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      double var4;
      for(var4 = var0[0]; var2 < var0.length; ++var2) {
         double var6 = var0[var2];
         if (Doubles.isFinite(var6) && Doubles.isFinite(var4)) {
            var4 += (var6 - var4) / (double)(var2 + 1);
         } else {
            var4 = StatsAccumulator.calculateNewMeanNonFinite(var4, var6);
         }
      }

      return var4;
   }

   public static double meanOf(int... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      double var4;
      for(var4 = (double)var0[0]; var2 < var0.length; ++var2) {
         double var6 = (double)var0[var2];
         if (Doubles.isFinite(var6) && Doubles.isFinite(var4)) {
            var4 += (var6 - var4) / (double)(var2 + 1);
         } else {
            var4 = StatsAccumulator.calculateNewMeanNonFinite(var4, var6);
         }
      }

      return var4;
   }

   public static double meanOf(long... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      double var4;
      for(var4 = (double)var0[0]; var2 < var0.length; ++var2) {
         double var6 = (double)var0[var2];
         if (Doubles.isFinite(var6) && Doubles.isFinite(var4)) {
            var4 += (var6 - var4) / (double)(var2 + 1);
         } else {
            var4 = StatsAccumulator.calculateNewMeanNonFinite(var4, var6);
         }
      }

      return var4;
   }

   public static Stats of(Iterable<? extends Number> var0) {
      StatsAccumulator var1 = new StatsAccumulator();
      var1.addAll(var0);
      return var1.snapshot();
   }

   public static Stats of(Iterator<? extends Number> var0) {
      StatsAccumulator var1 = new StatsAccumulator();
      var1.addAll(var0);
      return var1.snapshot();
   }

   public static Stats of(double... var0) {
      StatsAccumulator var1 = new StatsAccumulator();
      var1.addAll(var0);
      return var1.snapshot();
   }

   public static Stats of(int... var0) {
      StatsAccumulator var1 = new StatsAccumulator();
      var1.addAll(var0);
      return var1.snapshot();
   }

   public static Stats of(long... var0) {
      StatsAccumulator var1 = new StatsAccumulator();
      var1.addAll(var0);
      return var1.snapshot();
   }

   static Stats readFrom(ByteBuffer var0) {
      Preconditions.checkNotNull(var0);
      boolean var1;
      if (var0.remaining() >= 40) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Expected at least Stats.BYTES = %s remaining , got %s", (int)40, (int)var0.remaining());
      return new Stats(var0.getLong(), var0.getDouble(), var0.getDouble(), var0.getDouble(), var0.getDouble());
   }

   public long count() {
      return this.count;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = false;
      if (var1 == null) {
         return false;
      } else if (this.getClass() != var1.getClass()) {
         return false;
      } else {
         Stats var4 = (Stats)var1;
         boolean var3 = var2;
         if (this.count == var4.count) {
            var3 = var2;
            if (Double.doubleToLongBits(this.mean) == Double.doubleToLongBits(var4.mean)) {
               var3 = var2;
               if (Double.doubleToLongBits(this.sumOfSquaresOfDeltas) == Double.doubleToLongBits(var4.sumOfSquaresOfDeltas)) {
                  var3 = var2;
                  if (Double.doubleToLongBits(this.min) == Double.doubleToLongBits(var4.min)) {
                     var3 = var2;
                     if (Double.doubleToLongBits(this.max) == Double.doubleToLongBits(var4.max)) {
                        var3 = true;
                     }
                  }
               }
            }
         }

         return var3;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.count, this.mean, this.sumOfSquaresOfDeltas, this.min, this.max);
   }

   public double max() {
      boolean var1;
      if (this.count != 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      return this.max;
   }

   public double mean() {
      boolean var1;
      if (this.count != 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      return this.mean;
   }

   public double min() {
      boolean var1;
      if (this.count != 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      return this.min;
   }

   public double populationStandardDeviation() {
      return Math.sqrt(this.populationVariance());
   }

   public double populationVariance() {
      boolean var1;
      if (this.count > 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
         return Double.NaN;
      } else {
         return this.count == 1L ? 0.0D : DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)this.count();
      }
   }

   public double sampleStandardDeviation() {
      return Math.sqrt(this.sampleVariance());
   }

   public double sampleVariance() {
      boolean var1;
      if (this.count > 1L) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      return Double.isNaN(this.sumOfSquaresOfDeltas) ? Double.NaN : DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)(this.count - 1L);
   }

   public double sum() {
      return this.mean * (double)this.count;
   }

   double sumOfSquaresOfDeltas() {
      return this.sumOfSquaresOfDeltas;
   }

   public byte[] toByteArray() {
      ByteBuffer var1 = ByteBuffer.allocate(40).order(ByteOrder.LITTLE_ENDIAN);
      this.writeTo(var1);
      return var1.array();
   }

   public String toString() {
      return this.count() > 0L ? MoreObjects.toStringHelper((Object)this).add("count", this.count).add("mean", this.mean).add("populationStandardDeviation", this.populationStandardDeviation()).add("min", this.min).add("max", this.max).toString() : MoreObjects.toStringHelper((Object)this).add("count", this.count).toString();
   }

   void writeTo(ByteBuffer var1) {
      Preconditions.checkNotNull(var1);
      boolean var2;
      if (var1.remaining() >= 40) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "Expected at least Stats.BYTES = %s remaining , got %s", (int)40, (int)var1.remaining());
      var1.putLong(this.count).putDouble(this.mean).putDouble(this.sumOfSquaresOfDeltas).putDouble(this.min).putDouble(this.max);
   }
}

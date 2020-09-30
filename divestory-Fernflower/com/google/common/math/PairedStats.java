package com.google.common.math;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class PairedStats implements Serializable {
   private static final int BYTES = 88;
   private static final long serialVersionUID = 0L;
   private final double sumOfProductsOfDeltas;
   private final Stats xStats;
   private final Stats yStats;

   PairedStats(Stats var1, Stats var2, double var3) {
      this.xStats = var1;
      this.yStats = var2;
      this.sumOfProductsOfDeltas = var3;
   }

   private static double ensureInUnitRange(double var0) {
      if (var0 >= 1.0D) {
         return 1.0D;
      } else {
         return var0 <= -1.0D ? -1.0D : var0;
      }
   }

   private static double ensurePositive(double var0) {
      return var0 > 0.0D ? var0 : Double.MIN_VALUE;
   }

   public static PairedStats fromByteArray(byte[] var0) {
      Preconditions.checkNotNull(var0);
      boolean var1;
      if (var0.length == 88) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Expected PairedStats.BYTES = %s, got %s", (int)88, (int)var0.length);
      ByteBuffer var2 = ByteBuffer.wrap(var0).order(ByteOrder.LITTLE_ENDIAN);
      return new PairedStats(Stats.readFrom(var2), Stats.readFrom(var2), var2.getDouble());
   }

   public long count() {
      return this.xStats.count();
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = false;
      if (var1 == null) {
         return false;
      } else if (this.getClass() != var1.getClass()) {
         return false;
      } else {
         PairedStats var4 = (PairedStats)var1;
         boolean var3 = var2;
         if (this.xStats.equals(var4.xStats)) {
            var3 = var2;
            if (this.yStats.equals(var4.yStats)) {
               var3 = var2;
               if (Double.doubleToLongBits(this.sumOfProductsOfDeltas) == Double.doubleToLongBits(var4.sumOfProductsOfDeltas)) {
                  var3 = true;
               }
            }
         }

         return var3;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.xStats, this.yStats, this.sumOfProductsOfDeltas);
   }

   public LinearTransformation leastSquaresFit() {
      long var1 = this.count();
      boolean var3 = true;
      boolean var4;
      if (var1 > 1L) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4);
      if (Double.isNaN(this.sumOfProductsOfDeltas)) {
         return LinearTransformation.forNaN();
      } else {
         double var5 = this.xStats.sumOfSquaresOfDeltas();
         if (var5 > 0.0D) {
            return this.yStats.sumOfSquaresOfDeltas() > 0.0D ? LinearTransformation.mapping(this.xStats.mean(), this.yStats.mean()).withSlope(this.sumOfProductsOfDeltas / var5) : LinearTransformation.horizontal(this.yStats.mean());
         } else {
            if (this.yStats.sumOfSquaresOfDeltas() > 0.0D) {
               var4 = var3;
            } else {
               var4 = false;
            }

            Preconditions.checkState(var4);
            return LinearTransformation.vertical(this.xStats.mean());
         }
      }
   }

   public double pearsonsCorrelationCoefficient() {
      long var1 = this.count();
      boolean var3 = true;
      boolean var4;
      if (var1 > 1L) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4);
      if (Double.isNaN(this.sumOfProductsOfDeltas)) {
         return Double.NaN;
      } else {
         double var5 = this.xStats().sumOfSquaresOfDeltas();
         double var7 = this.yStats().sumOfSquaresOfDeltas();
         if (var5 > 0.0D) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkState(var4);
         if (var7 > 0.0D) {
            var4 = var3;
         } else {
            var4 = false;
         }

         Preconditions.checkState(var4);
         var7 = ensurePositive(var5 * var7);
         return ensureInUnitRange(this.sumOfProductsOfDeltas / Math.sqrt(var7));
      }
   }

   public double populationCovariance() {
      boolean var1;
      if (this.count() != 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      return this.sumOfProductsOfDeltas / (double)this.count();
   }

   public double sampleCovariance() {
      boolean var1;
      if (this.count() > 1L) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      return this.sumOfProductsOfDeltas / (double)(this.count() - 1L);
   }

   double sumOfProductsOfDeltas() {
      return this.sumOfProductsOfDeltas;
   }

   public byte[] toByteArray() {
      ByteBuffer var1 = ByteBuffer.allocate(88).order(ByteOrder.LITTLE_ENDIAN);
      this.xStats.writeTo(var1);
      this.yStats.writeTo(var1);
      var1.putDouble(this.sumOfProductsOfDeltas);
      return var1.array();
   }

   public String toString() {
      return this.count() > 0L ? MoreObjects.toStringHelper((Object)this).add("xStats", this.xStats).add("yStats", this.yStats).add("populationCovariance", this.populationCovariance()).toString() : MoreObjects.toStringHelper((Object)this).add("xStats", this.xStats).add("yStats", this.yStats).toString();
   }

   public Stats xStats() {
      return this.xStats;
   }

   public Stats yStats() {
      return this.yStats;
   }
}

package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;

public final class PairedStatsAccumulator {
   private double sumOfProductsOfDeltas = 0.0D;
   private final StatsAccumulator xStats = new StatsAccumulator();
   private final StatsAccumulator yStats = new StatsAccumulator();

   private static double ensureInUnitRange(double var0) {
      return Doubles.constrainToRange(var0, -1.0D, 1.0D);
   }

   private double ensurePositive(double var1) {
      return var1 > 0.0D ? var1 : Double.MIN_VALUE;
   }

   public void add(double var1, double var3) {
      this.xStats.add(var1);
      if (Doubles.isFinite(var1) && Doubles.isFinite(var3)) {
         if (this.xStats.count() > 1L) {
            this.sumOfProductsOfDeltas += (var1 - this.xStats.mean()) * (var3 - this.yStats.mean());
         }
      } else {
         this.sumOfProductsOfDeltas = Double.NaN;
      }

      this.yStats.add(var3);
   }

   public void addAll(PairedStats var1) {
      if (var1.count() != 0L) {
         this.xStats.addAll(var1.xStats());
         if (this.yStats.count() == 0L) {
            this.sumOfProductsOfDeltas = var1.sumOfProductsOfDeltas();
         } else {
            this.sumOfProductsOfDeltas += var1.sumOfProductsOfDeltas() + (var1.xStats().mean() - this.xStats.mean()) * (var1.yStats().mean() - this.yStats.mean()) * (double)var1.count();
         }

         this.yStats.addAll(var1.yStats());
      }
   }

   public long count() {
      return this.xStats.count();
   }

   public final LinearTransformation leastSquaresFit() {
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

   public final double pearsonsCorrelationCoefficient() {
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
         double var5 = this.xStats.sumOfSquaresOfDeltas();
         double var7 = this.yStats.sumOfSquaresOfDeltas();
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
         var7 = this.ensurePositive(var5 * var7);
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

   public final double sampleCovariance() {
      boolean var1;
      if (this.count() > 1L) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      return this.sumOfProductsOfDeltas / (double)(this.count() - 1L);
   }

   public PairedStats snapshot() {
      return new PairedStats(this.xStats.snapshot(), this.yStats.snapshot(), this.sumOfProductsOfDeltas);
   }

   public Stats xStats() {
      return this.xStats.snapshot();
   }

   public Stats yStats() {
      return this.yStats.snapshot();
   }
}

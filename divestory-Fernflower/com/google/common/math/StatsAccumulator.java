package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import java.util.Iterator;

public final class StatsAccumulator {
   private long count = 0L;
   private double max = Double.NaN;
   private double mean = 0.0D;
   private double min = Double.NaN;
   private double sumOfSquaresOfDeltas = 0.0D;

   static double calculateNewMeanNonFinite(double var0, double var2) {
      if (Doubles.isFinite(var0)) {
         return var2;
      } else {
         double var4 = var0;
         if (!Doubles.isFinite(var2)) {
            if (var0 == var2) {
               var4 = var0;
            } else {
               var4 = Double.NaN;
            }
         }

         return var4;
      }
   }

   private void merge(long var1, double var3, double var5, double var7, double var9) {
      long var11 = this.count;
      if (var11 == 0L) {
         this.count = var1;
         this.mean = var3;
         this.sumOfSquaresOfDeltas = var5;
         this.min = var7;
         this.max = var9;
      } else {
         this.count = var11 + var1;
         if (Doubles.isFinite(this.mean) && Doubles.isFinite(var3)) {
            double var13 = this.mean;
            double var15 = var3 - var13;
            double var17 = (double)var1;
            var13 += var15 * var17 / (double)this.count;
            this.mean = var13;
            this.sumOfSquaresOfDeltas += var5 + var15 * (var3 - var13) * var17;
         } else {
            this.mean = calculateNewMeanNonFinite(this.mean, var3);
            this.sumOfSquaresOfDeltas = Double.NaN;
         }

         this.min = Math.min(this.min, var7);
         this.max = Math.max(this.max, var9);
      }

   }

   public void add(double var1) {
      long var3 = this.count;
      if (var3 == 0L) {
         this.count = 1L;
         this.mean = var1;
         this.min = var1;
         this.max = var1;
         if (!Doubles.isFinite(var1)) {
            this.sumOfSquaresOfDeltas = Double.NaN;
         }
      } else {
         this.count = var3 + 1L;
         if (Doubles.isFinite(var1) && Doubles.isFinite(this.mean)) {
            double var5 = this.mean;
            double var7 = var1 - var5;
            var5 += var7 / (double)this.count;
            this.mean = var5;
            this.sumOfSquaresOfDeltas += var7 * (var1 - var5);
         } else {
            this.mean = calculateNewMeanNonFinite(this.mean, var1);
            this.sumOfSquaresOfDeltas = Double.NaN;
         }

         this.min = Math.min(this.min, var1);
         this.max = Math.max(this.max, var1);
      }

   }

   public void addAll(Stats var1) {
      if (var1.count() != 0L) {
         this.merge(var1.count(), var1.mean(), var1.sumOfSquaresOfDeltas(), var1.min(), var1.max());
      }
   }

   public void addAll(StatsAccumulator var1) {
      if (var1.count() != 0L) {
         this.merge(var1.count(), var1.mean(), var1.sumOfSquaresOfDeltas(), var1.min(), var1.max());
      }
   }

   public void addAll(Iterable<? extends Number> var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         this.add(((Number)var2.next()).doubleValue());
      }

   }

   public void addAll(Iterator<? extends Number> var1) {
      while(var1.hasNext()) {
         this.add(((Number)var1.next()).doubleValue());
      }

   }

   public void addAll(double... var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         this.add(var1[var3]);
      }

   }

   public void addAll(int... var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         this.add((double)var1[var3]);
      }

   }

   public void addAll(long... var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         this.add((double)var1[var3]);
      }

   }

   public long count() {
      return this.count;
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

   public final double populationStandardDeviation() {
      return Math.sqrt(this.populationVariance());
   }

   public final double populationVariance() {
      boolean var1;
      if (this.count != 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
         return Double.NaN;
      } else {
         return this.count == 1L ? 0.0D : DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)this.count;
      }
   }

   public final double sampleStandardDeviation() {
      return Math.sqrt(this.sampleVariance());
   }

   public final double sampleVariance() {
      boolean var1;
      if (this.count > 1L) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      return Double.isNaN(this.sumOfSquaresOfDeltas) ? Double.NaN : DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)(this.count - 1L);
   }

   public Stats snapshot() {
      return new Stats(this.count, this.mean, this.sumOfSquaresOfDeltas, this.min, this.max);
   }

   public final double sum() {
      return this.mean * (double)this.count;
   }

   double sumOfSquaresOfDeltas() {
      return this.sumOfSquaresOfDeltas;
   }
}

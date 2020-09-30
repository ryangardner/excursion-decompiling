package com.google.common.util.concurrent;

import com.google.common.math.LongMath;
import java.util.concurrent.TimeUnit;

abstract class SmoothRateLimiter extends RateLimiter {
   double maxPermits;
   private long nextFreeTicketMicros;
   double stableIntervalMicros;
   double storedPermits;

   private SmoothRateLimiter(RateLimiter.SleepingStopwatch var1) {
      super(var1);
      this.nextFreeTicketMicros = 0L;
   }

   // $FF: synthetic method
   SmoothRateLimiter(RateLimiter.SleepingStopwatch var1, Object var2) {
      this(var1);
   }

   abstract double coolDownIntervalMicros();

   final double doGetRate() {
      return (double)TimeUnit.SECONDS.toMicros(1L) / this.stableIntervalMicros;
   }

   abstract void doSetRate(double var1, double var3);

   final void doSetRate(double var1, long var3) {
      this.resync(var3);
      double var5 = (double)TimeUnit.SECONDS.toMicros(1L) / var1;
      this.stableIntervalMicros = var5;
      this.doSetRate(var1, var5);
   }

   final long queryEarliestAvailable(long var1) {
      return this.nextFreeTicketMicros;
   }

   final long reserveEarliestAvailable(int var1, long var2) {
      this.resync(var2);
      long var4 = this.nextFreeTicketMicros;
      double var6 = (double)var1;
      double var8 = Math.min(var6, this.storedPermits);
      long var10 = this.storedPermitsToWaitTime(this.storedPermits, var8);
      var2 = (long)((var6 - var8) * this.stableIntervalMicros);
      this.nextFreeTicketMicros = LongMath.saturatedAdd(this.nextFreeTicketMicros, var10 + var2);
      this.storedPermits -= var8;
      return var4;
   }

   void resync(long var1) {
      long var3 = this.nextFreeTicketMicros;
      if (var1 > var3) {
         double var5 = (double)(var1 - var3) / this.coolDownIntervalMicros();
         this.storedPermits = Math.min(this.maxPermits, this.storedPermits + var5);
         this.nextFreeTicketMicros = var1;
      }

   }

   abstract long storedPermitsToWaitTime(double var1, double var3);

   static final class SmoothBursty extends SmoothRateLimiter {
      final double maxBurstSeconds;

      SmoothBursty(RateLimiter.SleepingStopwatch var1, double var2) {
         super(var1, null);
         this.maxBurstSeconds = var2;
      }

      double coolDownIntervalMicros() {
         return this.stableIntervalMicros;
      }

      void doSetRate(double var1, double var3) {
         var3 = this.maxPermits;
         this.maxPermits = this.maxBurstSeconds * var1;
         if (var3 == Double.POSITIVE_INFINITY) {
            this.storedPermits = this.maxPermits;
         } else {
            var1 = 0.0D;
            if (var3 != 0.0D) {
               var1 = this.storedPermits * this.maxPermits / var3;
            }

            this.storedPermits = var1;
         }

      }

      long storedPermitsToWaitTime(double var1, double var3) {
         return 0L;
      }
   }

   static final class SmoothWarmingUp extends SmoothRateLimiter {
      private double coldFactor;
      private double slope;
      private double thresholdPermits;
      private final long warmupPeriodMicros;

      SmoothWarmingUp(RateLimiter.SleepingStopwatch var1, long var2, TimeUnit var4, double var5) {
         super(var1, null);
         this.warmupPeriodMicros = var4.toMicros(var2);
         this.coldFactor = var5;
      }

      private double permitsToTime(double var1) {
         return this.stableIntervalMicros + var1 * this.slope;
      }

      double coolDownIntervalMicros() {
         return (double)this.warmupPeriodMicros / this.maxPermits;
      }

      void doSetRate(double var1, double var3) {
         var1 = this.maxPermits;
         double var5 = this.coldFactor * var3;
         long var7 = this.warmupPeriodMicros;
         double var9 = (double)var7 * 0.5D / var3;
         this.thresholdPermits = var9;
         this.maxPermits = var9 + (double)var7 * 2.0D / (var3 + var5);
         this.slope = (var5 - var3) / (this.maxPermits - this.thresholdPermits);
         if (var1 == Double.POSITIVE_INFINITY) {
            this.storedPermits = 0.0D;
         } else {
            if (var1 == 0.0D) {
               var1 = this.maxPermits;
            } else {
               var1 = this.storedPermits * this.maxPermits / var1;
            }

            this.storedPermits = var1;
         }

      }

      long storedPermitsToWaitTime(double var1, double var3) {
         var1 -= this.thresholdPermits;
         long var7;
         if (var1 > 0.0D) {
            double var5 = Math.min(var1, var3);
            var7 = (long)((this.permitsToTime(var1) + this.permitsToTime(var1 - var5)) * var5 / 2.0D);
            var3 -= var5;
         } else {
            var7 = 0L;
         }

         return var7 + (long)(this.stableIntervalMicros * var3);
      }
   }
}

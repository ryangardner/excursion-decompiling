package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

public abstract class RateLimiter {
   @MonotonicNonNullDecl
   private volatile Object mutexDoNotUseDirectly;
   private final RateLimiter.SleepingStopwatch stopwatch;

   RateLimiter(RateLimiter.SleepingStopwatch var1) {
      this.stopwatch = (RateLimiter.SleepingStopwatch)Preconditions.checkNotNull(var1);
   }

   private boolean canAcquire(long var1, long var3) {
      boolean var5;
      if (this.queryEarliestAvailable(var1) - var3 <= var1) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   private static void checkPermits(int var0) {
      boolean var1;
      if (var0 > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Requested permits (%s) must be positive", var0);
   }

   public static RateLimiter create(double var0) {
      return create(var0, RateLimiter.SleepingStopwatch.createFromSystemTimer());
   }

   public static RateLimiter create(double var0, long var2, TimeUnit var4) {
      boolean var5;
      if (var2 >= 0L) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5, "warmupPeriod must not be negative: %s", var2);
      return create(var0, var2, var4, 3.0D, RateLimiter.SleepingStopwatch.createFromSystemTimer());
   }

   static RateLimiter create(double var0, long var2, TimeUnit var4, double var5, RateLimiter.SleepingStopwatch var7) {
      SmoothRateLimiter.SmoothWarmingUp var8 = new SmoothRateLimiter.SmoothWarmingUp(var7, var2, var4, var5);
      var8.setRate(var0);
      return var8;
   }

   static RateLimiter create(double var0, RateLimiter.SleepingStopwatch var2) {
      SmoothRateLimiter.SmoothBursty var3 = new SmoothRateLimiter.SmoothBursty(var2, 1.0D);
      var3.setRate(var0);
      return var3;
   }

   private Object mutex() {
      Object var1 = this.mutexDoNotUseDirectly;
      Object var2 = var1;
      if (var1 == null) {
         synchronized(this){}

         Throwable var10000;
         boolean var10001;
         label206: {
            try {
               var1 = this.mutexDoNotUseDirectly;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label206;
            }

            var2 = var1;
            if (var1 == null) {
               try {
                  var2 = new Object();
                  this.mutexDoNotUseDirectly = var2;
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label206;
               }
            }

            label193:
            try {
               return var2;
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               break label193;
            }
         }

         while(true) {
            Throwable var23 = var10000;

            try {
               throw var23;
            } catch (Throwable var19) {
               var10000 = var19;
               var10001 = false;
               continue;
            }
         }
      } else {
         return var2;
      }
   }

   public double acquire() {
      return this.acquire(1);
   }

   public double acquire(int var1) {
      long var2 = this.reserve(var1);
      this.stopwatch.sleepMicrosUninterruptibly(var2);
      return (double)var2 * 1.0D / (double)TimeUnit.SECONDS.toMicros(1L);
   }

   abstract double doGetRate();

   abstract void doSetRate(double var1, long var3);

   public final double getRate() {
      // $FF: Couldn't be decompiled
   }

   abstract long queryEarliestAvailable(long var1);

   final long reserve(int param1) {
      // $FF: Couldn't be decompiled
   }

   final long reserveAndGetWaitLength(int var1, long var2) {
      return Math.max(this.reserveEarliestAvailable(var1, var2) - var2, 0L);
   }

   abstract long reserveEarliestAvailable(int var1, long var2);

   public final void setRate(double param1) {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", this.getRate());
   }

   public boolean tryAcquire() {
      return this.tryAcquire(1, 0L, TimeUnit.MICROSECONDS);
   }

   public boolean tryAcquire(int var1) {
      return this.tryAcquire(var1, 0L, TimeUnit.MICROSECONDS);
   }

   public boolean tryAcquire(int var1, long var2, TimeUnit var4) {
      long var5 = Math.max(var4.toMicros(var2), 0L);
      checkPermits(var1);
      Object var20 = this.mutex();
      synchronized(var20){}

      Throwable var10000;
      boolean var10001;
      label137: {
         try {
            var2 = this.stopwatch.readMicros();
            if (!this.canAcquire(var2, var5)) {
               return false;
            }
         } catch (Throwable var19) {
            var10000 = var19;
            var10001 = false;
            break label137;
         }

         try {
            var2 = this.reserveAndGetWaitLength(var1, var2);
         } catch (Throwable var18) {
            var10000 = var18;
            var10001 = false;
            break label137;
         }

         this.stopwatch.sleepMicrosUninterruptibly(var2);
         return true;
      }

      while(true) {
         Throwable var7 = var10000;

         try {
            throw var7;
         } catch (Throwable var17) {
            var10000 = var17;
            var10001 = false;
            continue;
         }
      }
   }

   public boolean tryAcquire(long var1, TimeUnit var3) {
      return this.tryAcquire(1, var1, var3);
   }

   abstract static class SleepingStopwatch {
      protected SleepingStopwatch() {
      }

      public static RateLimiter.SleepingStopwatch createFromSystemTimer() {
         return new RateLimiter.SleepingStopwatch() {
            final Stopwatch stopwatch = Stopwatch.createStarted();

            protected long readMicros() {
               return this.stopwatch.elapsed(TimeUnit.MICROSECONDS);
            }

            protected void sleepMicrosUninterruptibly(long var1) {
               if (var1 > 0L) {
                  Uninterruptibles.sleepUninterruptibly(var1, TimeUnit.MICROSECONDS);
               }

            }
         };
      }

      protected abstract long readMicros();

      protected abstract void sleepMicrosUninterruptibly(long var1);
   }
}

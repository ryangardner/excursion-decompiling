package com.google.api.client.util;

import java.io.IOException;

public class ExponentialBackOff implements BackOff {
   public static final int DEFAULT_INITIAL_INTERVAL_MILLIS = 500;
   public static final int DEFAULT_MAX_ELAPSED_TIME_MILLIS = 900000;
   public static final int DEFAULT_MAX_INTERVAL_MILLIS = 60000;
   public static final double DEFAULT_MULTIPLIER = 1.5D;
   public static final double DEFAULT_RANDOMIZATION_FACTOR = 0.5D;
   private int currentIntervalMillis;
   private final int initialIntervalMillis;
   private final int maxElapsedTimeMillis;
   private final int maxIntervalMillis;
   private final double multiplier;
   private final NanoClock nanoClock;
   private final double randomizationFactor;
   long startTimeNanos;

   public ExponentialBackOff() {
      this(new ExponentialBackOff.Builder());
   }

   protected ExponentialBackOff(ExponentialBackOff.Builder var1) {
      this.initialIntervalMillis = var1.initialIntervalMillis;
      this.randomizationFactor = var1.randomizationFactor;
      this.multiplier = var1.multiplier;
      this.maxIntervalMillis = var1.maxIntervalMillis;
      this.maxElapsedTimeMillis = var1.maxElapsedTimeMillis;
      this.nanoClock = var1.nanoClock;
      int var2 = this.initialIntervalMillis;
      boolean var3 = true;
      boolean var4;
      if (var2 > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      double var5 = this.randomizationFactor;
      if (0.0D <= var5 && var5 < 1.0D) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      if (this.multiplier >= 1.0D) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      if (this.maxIntervalMillis >= this.initialIntervalMillis) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      if (this.maxElapsedTimeMillis > 0) {
         var4 = var3;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      this.reset();
   }

   static int getRandomValueFromInterval(double var0, double var2, int var4) {
      double var5 = (double)var4;
      var0 *= var5;
      double var7 = var5 - var0;
      return (int)(var7 + var2 * (var5 + var0 - var7 + 1.0D));
   }

   private void incrementCurrentInterval() {
      int var1 = this.currentIntervalMillis;
      double var2 = (double)var1;
      int var4 = this.maxIntervalMillis;
      double var5 = (double)var4;
      double var7 = this.multiplier;
      if (var2 >= var5 / var7) {
         this.currentIntervalMillis = var4;
      } else {
         this.currentIntervalMillis = (int)((double)var1 * var7);
      }

   }

   public final int getCurrentIntervalMillis() {
      return this.currentIntervalMillis;
   }

   public final long getElapsedTimeMillis() {
      return (this.nanoClock.nanoTime() - this.startTimeNanos) / 1000000L;
   }

   public final int getInitialIntervalMillis() {
      return this.initialIntervalMillis;
   }

   public final int getMaxElapsedTimeMillis() {
      return this.maxElapsedTimeMillis;
   }

   public final int getMaxIntervalMillis() {
      return this.maxIntervalMillis;
   }

   public final double getMultiplier() {
      return this.multiplier;
   }

   public final double getRandomizationFactor() {
      return this.randomizationFactor;
   }

   public long nextBackOffMillis() throws IOException {
      if (this.getElapsedTimeMillis() > (long)this.maxElapsedTimeMillis) {
         return -1L;
      } else {
         int var1 = getRandomValueFromInterval(this.randomizationFactor, Math.random(), this.currentIntervalMillis);
         this.incrementCurrentInterval();
         return (long)var1;
      }
   }

   public final void reset() {
      this.currentIntervalMillis = this.initialIntervalMillis;
      this.startTimeNanos = this.nanoClock.nanoTime();
   }

   public static class Builder {
      int initialIntervalMillis = 500;
      int maxElapsedTimeMillis = 900000;
      int maxIntervalMillis = 60000;
      double multiplier = 1.5D;
      NanoClock nanoClock;
      double randomizationFactor = 0.5D;

      public Builder() {
         this.nanoClock = NanoClock.SYSTEM;
      }

      public ExponentialBackOff build() {
         return new ExponentialBackOff(this);
      }

      public final int getInitialIntervalMillis() {
         return this.initialIntervalMillis;
      }

      public final int getMaxElapsedTimeMillis() {
         return this.maxElapsedTimeMillis;
      }

      public final int getMaxIntervalMillis() {
         return this.maxIntervalMillis;
      }

      public final double getMultiplier() {
         return this.multiplier;
      }

      public final NanoClock getNanoClock() {
         return this.nanoClock;
      }

      public final double getRandomizationFactor() {
         return this.randomizationFactor;
      }

      public ExponentialBackOff.Builder setInitialIntervalMillis(int var1) {
         this.initialIntervalMillis = var1;
         return this;
      }

      public ExponentialBackOff.Builder setMaxElapsedTimeMillis(int var1) {
         this.maxElapsedTimeMillis = var1;
         return this;
      }

      public ExponentialBackOff.Builder setMaxIntervalMillis(int var1) {
         this.maxIntervalMillis = var1;
         return this;
      }

      public ExponentialBackOff.Builder setMultiplier(double var1) {
         this.multiplier = var1;
         return this;
      }

      public ExponentialBackOff.Builder setNanoClock(NanoClock var1) {
         this.nanoClock = (NanoClock)Preconditions.checkNotNull(var1);
         return this;
      }

      public ExponentialBackOff.Builder setRandomizationFactor(double var1) {
         this.randomizationFactor = var1;
         return this;
      }
   }
}

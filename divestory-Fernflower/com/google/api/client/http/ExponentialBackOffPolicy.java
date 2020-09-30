package com.google.api.client.http;

import com.google.api.client.util.ExponentialBackOff;
import com.google.api.client.util.NanoClock;
import java.io.IOException;

@Deprecated
public class ExponentialBackOffPolicy implements BackOffPolicy {
   public static final int DEFAULT_INITIAL_INTERVAL_MILLIS = 500;
   public static final int DEFAULT_MAX_ELAPSED_TIME_MILLIS = 900000;
   public static final int DEFAULT_MAX_INTERVAL_MILLIS = 60000;
   public static final double DEFAULT_MULTIPLIER = 1.5D;
   public static final double DEFAULT_RANDOMIZATION_FACTOR = 0.5D;
   private final ExponentialBackOff exponentialBackOff;

   public ExponentialBackOffPolicy() {
      this(new ExponentialBackOffPolicy.Builder());
   }

   protected ExponentialBackOffPolicy(ExponentialBackOffPolicy.Builder var1) {
      this.exponentialBackOff = var1.exponentialBackOffBuilder.build();
   }

   public static ExponentialBackOffPolicy.Builder builder() {
      return new ExponentialBackOffPolicy.Builder();
   }

   public final int getCurrentIntervalMillis() {
      return this.exponentialBackOff.getCurrentIntervalMillis();
   }

   public final long getElapsedTimeMillis() {
      return this.exponentialBackOff.getElapsedTimeMillis();
   }

   public final int getInitialIntervalMillis() {
      return this.exponentialBackOff.getInitialIntervalMillis();
   }

   public final int getMaxElapsedTimeMillis() {
      return this.exponentialBackOff.getMaxElapsedTimeMillis();
   }

   public final int getMaxIntervalMillis() {
      return this.exponentialBackOff.getMaxIntervalMillis();
   }

   public final double getMultiplier() {
      return this.exponentialBackOff.getMultiplier();
   }

   public long getNextBackOffMillis() throws IOException {
      return this.exponentialBackOff.nextBackOffMillis();
   }

   public final double getRandomizationFactor() {
      return this.exponentialBackOff.getRandomizationFactor();
   }

   public boolean isBackOffRequired(int var1) {
      return var1 == 500 || var1 == 503;
   }

   public final void reset() {
      this.exponentialBackOff.reset();
   }

   @Deprecated
   public static class Builder {
      final ExponentialBackOff.Builder exponentialBackOffBuilder = new ExponentialBackOff.Builder();

      protected Builder() {
      }

      public ExponentialBackOffPolicy build() {
         return new ExponentialBackOffPolicy(this);
      }

      public final int getInitialIntervalMillis() {
         return this.exponentialBackOffBuilder.getInitialIntervalMillis();
      }

      public final int getMaxElapsedTimeMillis() {
         return this.exponentialBackOffBuilder.getMaxElapsedTimeMillis();
      }

      public final int getMaxIntervalMillis() {
         return this.exponentialBackOffBuilder.getMaxIntervalMillis();
      }

      public final double getMultiplier() {
         return this.exponentialBackOffBuilder.getMultiplier();
      }

      public final NanoClock getNanoClock() {
         return this.exponentialBackOffBuilder.getNanoClock();
      }

      public final double getRandomizationFactor() {
         return this.exponentialBackOffBuilder.getRandomizationFactor();
      }

      public ExponentialBackOffPolicy.Builder setInitialIntervalMillis(int var1) {
         this.exponentialBackOffBuilder.setInitialIntervalMillis(var1);
         return this;
      }

      public ExponentialBackOffPolicy.Builder setMaxElapsedTimeMillis(int var1) {
         this.exponentialBackOffBuilder.setMaxElapsedTimeMillis(var1);
         return this;
      }

      public ExponentialBackOffPolicy.Builder setMaxIntervalMillis(int var1) {
         this.exponentialBackOffBuilder.setMaxIntervalMillis(var1);
         return this;
      }

      public ExponentialBackOffPolicy.Builder setMultiplier(double var1) {
         this.exponentialBackOffBuilder.setMultiplier(var1);
         return this;
      }

      public ExponentialBackOffPolicy.Builder setNanoClock(NanoClock var1) {
         this.exponentialBackOffBuilder.setNanoClock(var1);
         return this;
      }

      public ExponentialBackOffPolicy.Builder setRandomizationFactor(double var1) {
         this.exponentialBackOffBuilder.setRandomizationFactor(var1);
         return this;
      }
   }
}

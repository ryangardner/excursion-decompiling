package com.google.common.base;

import java.util.concurrent.TimeUnit;

public final class Stopwatch {
   private long elapsedNanos;
   private boolean isRunning;
   private long startTick;
   private final Ticker ticker;

   Stopwatch() {
      this.ticker = Ticker.systemTicker();
   }

   Stopwatch(Ticker var1) {
      this.ticker = (Ticker)Preconditions.checkNotNull(var1, "ticker");
   }

   private static String abbreviate(TimeUnit var0) {
      switch(null.$SwitchMap$java$util$concurrent$TimeUnit[var0.ordinal()]) {
      case 1:
         return "ns";
      case 2:
         return "μs";
      case 3:
         return "ms";
      case 4:
         return "s";
      case 5:
         return "min";
      case 6:
         return "h";
      case 7:
         return "d";
      default:
         throw new AssertionError();
      }
   }

   private static TimeUnit chooseUnit(long var0) {
      if (TimeUnit.DAYS.convert(var0, TimeUnit.NANOSECONDS) > 0L) {
         return TimeUnit.DAYS;
      } else if (TimeUnit.HOURS.convert(var0, TimeUnit.NANOSECONDS) > 0L) {
         return TimeUnit.HOURS;
      } else if (TimeUnit.MINUTES.convert(var0, TimeUnit.NANOSECONDS) > 0L) {
         return TimeUnit.MINUTES;
      } else if (TimeUnit.SECONDS.convert(var0, TimeUnit.NANOSECONDS) > 0L) {
         return TimeUnit.SECONDS;
      } else if (TimeUnit.MILLISECONDS.convert(var0, TimeUnit.NANOSECONDS) > 0L) {
         return TimeUnit.MILLISECONDS;
      } else {
         return TimeUnit.MICROSECONDS.convert(var0, TimeUnit.NANOSECONDS) > 0L ? TimeUnit.MICROSECONDS : TimeUnit.NANOSECONDS;
      }
   }

   public static Stopwatch createStarted() {
      return (new Stopwatch()).start();
   }

   public static Stopwatch createStarted(Ticker var0) {
      return (new Stopwatch(var0)).start();
   }

   public static Stopwatch createUnstarted() {
      return new Stopwatch();
   }

   public static Stopwatch createUnstarted(Ticker var0) {
      return new Stopwatch(var0);
   }

   private long elapsedNanos() {
      long var1;
      if (this.isRunning) {
         var1 = this.ticker.read() - this.startTick + this.elapsedNanos;
      } else {
         var1 = this.elapsedNanos;
      }

      return var1;
   }

   public long elapsed(TimeUnit var1) {
      return var1.convert(this.elapsedNanos(), TimeUnit.NANOSECONDS);
   }

   public boolean isRunning() {
      return this.isRunning;
   }

   public Stopwatch reset() {
      this.elapsedNanos = 0L;
      this.isRunning = false;
      return this;
   }

   public Stopwatch start() {
      Preconditions.checkState(this.isRunning ^ true, "This stopwatch is already running.");
      this.isRunning = true;
      this.startTick = this.ticker.read();
      return this;
   }

   public Stopwatch stop() {
      long var1 = this.ticker.read();
      Preconditions.checkState(this.isRunning, "This stopwatch is already stopped.");
      this.isRunning = false;
      this.elapsedNanos += var1 - this.startTick;
      return this;
   }

   public String toString() {
      long var1 = this.elapsedNanos();
      TimeUnit var3 = chooseUnit(var1);
      double var4 = (double)var1 / (double)TimeUnit.NANOSECONDS.convert(1L, var3);
      StringBuilder var6 = new StringBuilder();
      var6.append(Platform.formatCompact4Digits(var4));
      var6.append(" ");
      var6.append(abbreviate(var3));
      return var6.toString();
   }
}

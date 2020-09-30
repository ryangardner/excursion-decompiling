package io.grpc;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class Deadline implements Comparable<Deadline> {
   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final long MAX_OFFSET;
   private static final long MIN_OFFSET;
   private static final long NANOS_PER_SECOND;
   private static final Deadline.SystemTicker SYSTEM_TICKER = new Deadline.SystemTicker();
   private final long deadlineNanos;
   private volatile boolean expired;
   private final Deadline.Ticker ticker;

   static {
      long var0 = TimeUnit.DAYS.toNanos(36500L);
      MAX_OFFSET = var0;
      MIN_OFFSET = -var0;
      NANOS_PER_SECOND = TimeUnit.SECONDS.toNanos(1L);
   }

   private Deadline(Deadline.Ticker var1, long var2, long var4, boolean var6) {
      this.ticker = var1;
      var4 = Math.min(MAX_OFFSET, Math.max(MIN_OFFSET, var4));
      this.deadlineNanos = var2 + var4;
      if (var6 && var4 <= 0L) {
         var6 = true;
      } else {
         var6 = false;
      }

      this.expired = var6;
   }

   private Deadline(Deadline.Ticker var1, long var2, boolean var4) {
      this(var1, var1.read(), var2, var4);
   }

   public static Deadline after(long var0, TimeUnit var2) {
      return after(var0, var2, SYSTEM_TICKER);
   }

   static Deadline after(long var0, TimeUnit var2, Deadline.Ticker var3) {
      checkNotNull(var2, "units");
      return new Deadline(var3, var2.toNanos(var0), true);
   }

   private static <T> T checkNotNull(T var0, Object var1) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(String.valueOf(var1));
      }
   }

   public int compareTo(Deadline var1) {
      long var3;
      int var2 = (var3 = this.deadlineNanos - var1.deadlineNanos - 0L) == 0L ? 0 : (var3 < 0L ? -1 : 1);
      if (var2 < 0) {
         return -1;
      } else {
         return var2 > 0 ? 1 : 0;
      }
   }

   public boolean isBefore(Deadline var1) {
      boolean var2;
      if (this.deadlineNanos - var1.deadlineNanos < 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isExpired() {
      if (!this.expired) {
         if (this.deadlineNanos - this.ticker.read() > 0L) {
            return false;
         }

         this.expired = true;
      }

      return true;
   }

   public Deadline minimum(Deadline var1) {
      Deadline var2 = var1;
      if (this.isBefore(var1)) {
         var2 = this;
      }

      return var2;
   }

   public Deadline offset(long var1, TimeUnit var3) {
      return var1 == 0L ? this : new Deadline(this.ticker, this.deadlineNanos, var3.toNanos(var1), this.isExpired());
   }

   public ScheduledFuture<?> runOnExpiration(Runnable var1, ScheduledExecutorService var2) {
      checkNotNull(var1, "task");
      checkNotNull(var2, "scheduler");
      return var2.schedule(var1, this.deadlineNanos - this.ticker.read(), TimeUnit.NANOSECONDS);
   }

   public long timeRemaining(TimeUnit var1) {
      long var2 = this.ticker.read();
      if (!this.expired && this.deadlineNanos - var2 <= 0L) {
         this.expired = true;
      }

      return var1.convert(this.deadlineNanos - var2, TimeUnit.NANOSECONDS);
   }

   public String toString() {
      long var1 = this.timeRemaining(TimeUnit.NANOSECONDS);
      long var3 = Math.abs(var1) / NANOS_PER_SECOND;
      long var5 = Math.abs(var1) % NANOS_PER_SECOND;
      StringBuilder var7 = new StringBuilder();
      if (var1 < 0L) {
         var7.append('-');
      }

      var7.append(var3);
      if (var5 > 0L) {
         var7.append(String.format(".%09d", var5));
      }

      var7.append("s from now");
      return var7.toString();
   }

   private static class SystemTicker extends Deadline.Ticker {
      private SystemTicker() {
      }

      // $FF: synthetic method
      SystemTicker(Object var1) {
         this();
      }

      public long read() {
         return System.nanoTime();
      }
   }

   abstract static class Ticker {
      public abstract long read();
   }
}

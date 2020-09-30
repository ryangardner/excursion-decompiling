package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadFactoryBuilder {
   private ThreadFactory backingThreadFactory = null;
   private Boolean daemon = null;
   private String nameFormat = null;
   private Integer priority = null;
   private UncaughtExceptionHandler uncaughtExceptionHandler = null;

   private static ThreadFactory doBuild(ThreadFactoryBuilder var0) {
      final String var1 = var0.nameFormat;
      final Boolean var2 = var0.daemon;
      final Integer var3 = var0.priority;
      final UncaughtExceptionHandler var4 = var0.uncaughtExceptionHandler;
      final ThreadFactory var6 = var0.backingThreadFactory;
      if (var6 == null) {
         var6 = Executors.defaultThreadFactory();
      }

      final AtomicLong var5;
      if (var1 != null) {
         var5 = new AtomicLong(0L);
      } else {
         var5 = null;
      }

      return new ThreadFactory() {
         public Thread newThread(Runnable var1x) {
            Thread var3x = var6.newThread(var1x);
            String var2x = var1;
            if (var2x != null) {
               var3x.setName(ThreadFactoryBuilder.format(var2x, var5.getAndIncrement()));
            }

            Boolean var4x = var2;
            if (var4x != null) {
               var3x.setDaemon(var4x);
            }

            Integer var5x = var3;
            if (var5x != null) {
               var3x.setPriority(var5x);
            }

            UncaughtExceptionHandler var6x = var4;
            if (var6x != null) {
               var3x.setUncaughtExceptionHandler(var6x);
            }

            return var3x;
         }
      };
   }

   private static String format(String var0, Object... var1) {
      return String.format(Locale.ROOT, var0, var1);
   }

   @CheckReturnValue
   public ThreadFactory build() {
      return doBuild(this);
   }

   public ThreadFactoryBuilder setDaemon(boolean var1) {
      this.daemon = var1;
      return this;
   }

   public ThreadFactoryBuilder setNameFormat(String var1) {
      format(var1, 0);
      this.nameFormat = var1;
      return this;
   }

   public ThreadFactoryBuilder setPriority(int var1) {
      boolean var2 = false;
      boolean var3;
      if (var1 >= 1) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "Thread priority (%s) must be >= %s", (int)var1, (int)1);
      var3 = var2;
      if (var1 <= 10) {
         var3 = true;
      }

      Preconditions.checkArgument(var3, "Thread priority (%s) must be <= %s", (int)var1, (int)10);
      this.priority = var1;
      return this;
   }

   public ThreadFactoryBuilder setThreadFactory(ThreadFactory var1) {
      this.backingThreadFactory = (ThreadFactory)Preconditions.checkNotNull(var1);
      return this;
   }

   public ThreadFactoryBuilder setUncaughtExceptionHandler(UncaughtExceptionHandler var1) {
      this.uncaughtExceptionHandler = (UncaughtExceptionHandler)Preconditions.checkNotNull(var1);
      return this;
   }
}

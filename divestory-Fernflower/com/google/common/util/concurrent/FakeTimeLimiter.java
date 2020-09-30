package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public final class FakeTimeLimiter implements TimeLimiter {
   public <T> T callUninterruptiblyWithTimeout(Callable<T> var1, long var2, TimeUnit var4) throws ExecutionException {
      return this.callWithTimeout(var1, var2, var4);
   }

   public <T> T callWithTimeout(Callable<T> var1, long var2, TimeUnit var4) throws ExecutionException {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var4);

      RuntimeException var15;
      try {
         try {
            Object var16 = var1.call();
            return var16;
         } catch (RuntimeException var9) {
            var15 = var9;
         } catch (Exception var10) {
            Exception var14 = var10;
            throw new ExecutionException(var14);
         } catch (Error var11) {
            Error var13 = var11;
            throw new ExecutionError(var13);
         }
      } catch (Throwable var12) {
         throw new ExecutionException(var12);
      }

      throw new UncheckedExecutionException(var15);
   }

   public <T> T newProxy(T var1, Class<T> var2, long var3, TimeUnit var5) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      Preconditions.checkNotNull(var5);
      return var1;
   }

   public void runUninterruptiblyWithTimeout(Runnable var1, long var2, TimeUnit var4) {
      this.runWithTimeout(var1, var2, var4);
   }

   public void runWithTimeout(Runnable var1, long var2, TimeUnit var4) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var4);

      RuntimeException var12;
      try {
         try {
            var1.run();
            return;
         } catch (RuntimeException var8) {
            var12 = var8;
         } catch (Error var9) {
            Error var11 = var9;
            throw new ExecutionError(var11);
         }
      } catch (Throwable var10) {
         throw new UncheckedExecutionException(var10);
      }

      throw new UncheckedExecutionException(var12);
   }
}

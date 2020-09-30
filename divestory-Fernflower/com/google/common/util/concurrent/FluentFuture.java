package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.DoNotMock;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@DoNotMock("Use FluentFuture.from(Futures.immediate*Future) or SettableFuture")
public abstract class FluentFuture<V> extends GwtFluentFutureCatchingSpecialization<V> {
   FluentFuture() {
   }

   @Deprecated
   public static <V> FluentFuture<V> from(FluentFuture<V> var0) {
      return (FluentFuture)Preconditions.checkNotNull(var0);
   }

   public static <V> FluentFuture<V> from(ListenableFuture<V> var0) {
      Object var1;
      if (var0 instanceof FluentFuture) {
         var1 = (FluentFuture)var0;
      } else {
         var1 = new ForwardingFluentFuture(var0);
      }

      return (FluentFuture)var1;
   }

   public final void addCallback(FutureCallback<? super V> var1, Executor var2) {
      Futures.addCallback(this, var1, var2);
   }

   public final <X extends Throwable> FluentFuture<V> catching(Class<X> var1, Function<? super X, ? extends V> var2, Executor var3) {
      return (FluentFuture)Futures.catching(this, var1, var2, var3);
   }

   public final <X extends Throwable> FluentFuture<V> catchingAsync(Class<X> var1, AsyncFunction<? super X, ? extends V> var2, Executor var3) {
      return (FluentFuture)Futures.catchingAsync(this, var1, var2, var3);
   }

   public final <T> FluentFuture<T> transform(Function<? super V, T> var1, Executor var2) {
      return (FluentFuture)Futures.transform(this, var1, var2);
   }

   public final <T> FluentFuture<T> transformAsync(AsyncFunction<? super V, T> var1, Executor var2) {
      return (FluentFuture)Futures.transformAsync(this, var1, var2);
   }

   public final FluentFuture<V> withTimeout(long var1, TimeUnit var3, ScheduledExecutorService var4) {
      return (FluentFuture)Futures.withTimeout(this, var1, var3, var4);
   }

   abstract static class TrustedFuture<V> extends FluentFuture<V> implements AbstractFuture.Trusted<V> {
      public final void addListener(Runnable var1, Executor var2) {
         super.addListener(var1, var2);
      }

      public final boolean cancel(boolean var1) {
         return super.cancel(var1);
      }

      public final V get() throws InterruptedException, ExecutionException {
         return super.get();
      }

      public final V get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
         return super.get(var1, var3);
      }

      public final boolean isCancelled() {
         return super.isCancelled();
      }

      public final boolean isDone() {
         return super.isDone();
      }
   }
}

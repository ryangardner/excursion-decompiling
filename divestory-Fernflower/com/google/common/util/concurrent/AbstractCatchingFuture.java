package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractCatchingFuture<V, X extends Throwable, F, T> extends FluentFuture.TrustedFuture<V> implements Runnable {
   @NullableDecl
   Class<X> exceptionType;
   @NullableDecl
   F fallback;
   @NullableDecl
   ListenableFuture<? extends V> inputFuture;

   AbstractCatchingFuture(ListenableFuture<? extends V> var1, Class<X> var2, F var3) {
      this.inputFuture = (ListenableFuture)Preconditions.checkNotNull(var1);
      this.exceptionType = (Class)Preconditions.checkNotNull(var2);
      this.fallback = Preconditions.checkNotNull(var3);
   }

   static <V, X extends Throwable> ListenableFuture<V> create(ListenableFuture<? extends V> var0, Class<X> var1, Function<? super X, ? extends V> var2, Executor var3) {
      AbstractCatchingFuture.CatchingFuture var4 = new AbstractCatchingFuture.CatchingFuture(var0, var1, var2);
      var0.addListener(var4, MoreExecutors.rejectionPropagatingExecutor(var3, var4));
      return var4;
   }

   static <X extends Throwable, V> ListenableFuture<V> create(ListenableFuture<? extends V> var0, Class<X> var1, AsyncFunction<? super X, ? extends V> var2, Executor var3) {
      AbstractCatchingFuture.AsyncCatchingFuture var4 = new AbstractCatchingFuture.AsyncCatchingFuture(var0, var1, var2);
      var0.addListener(var4, MoreExecutors.rejectionPropagatingExecutor(var3, var4));
      return var4;
   }

   protected final void afterDone() {
      this.maybePropagateCancellationTo(this.inputFuture);
      this.inputFuture = null;
      this.exceptionType = null;
      this.fallback = null;
   }

   @NullableDecl
   abstract T doFallback(F var1, X var2) throws Exception;

   protected String pendingToString() {
      ListenableFuture var1 = this.inputFuture;
      Class var2 = this.exceptionType;
      Object var3 = this.fallback;
      String var4 = super.pendingToString();
      String var6;
      if (var1 != null) {
         StringBuilder var5 = new StringBuilder();
         var5.append("inputFuture=[");
         var5.append(var1);
         var5.append("], ");
         var6 = var5.toString();
      } else {
         var6 = "";
      }

      if (var2 != null && var3 != null) {
         StringBuilder var8 = new StringBuilder();
         var8.append(var6);
         var8.append("exceptionType=[");
         var8.append(var2);
         var8.append("], fallback=[");
         var8.append(var3);
         var8.append("]");
         return var8.toString();
      } else if (var4 != null) {
         StringBuilder var7 = new StringBuilder();
         var7.append(var6);
         var7.append(var4);
         return var7.toString();
      } else {
         return null;
      }
   }

   public final void run() {
      // $FF: Couldn't be decompiled
   }

   abstract void setResult(@NullableDecl T var1);

   private static final class AsyncCatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, AsyncFunction<? super X, ? extends V>, ListenableFuture<? extends V>> {
      AsyncCatchingFuture(ListenableFuture<? extends V> var1, Class<X> var2, AsyncFunction<? super X, ? extends V> var3) {
         super(var1, var2, var3);
      }

      ListenableFuture<? extends V> doFallback(AsyncFunction<? super X, ? extends V> var1, X var2) throws Exception {
         ListenableFuture var3 = var1.apply(var2);
         Preconditions.checkNotNull(var3, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", (Object)var1);
         return var3;
      }

      void setResult(ListenableFuture<? extends V> var1) {
         this.setFuture(var1);
      }
   }

   private static final class CatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, Function<? super X, ? extends V>, V> {
      CatchingFuture(ListenableFuture<? extends V> var1, Class<X> var2, Function<? super X, ? extends V> var3) {
         super(var1, var2, var3);
      }

      @NullableDecl
      V doFallback(Function<? super X, ? extends V> var1, X var2) throws Exception {
         return var1.apply(var2);
      }

      void setResult(@NullableDecl V var1) {
         this.set(var1);
      }
   }
}

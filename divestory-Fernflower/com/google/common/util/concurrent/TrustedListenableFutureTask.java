package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class TrustedListenableFutureTask<V> extends FluentFuture.TrustedFuture<V> implements RunnableFuture<V> {
   private volatile InterruptibleTask<?> task;

   TrustedListenableFutureTask(AsyncCallable<V> var1) {
      this.task = new TrustedListenableFutureTask.TrustedFutureInterruptibleAsyncTask(var1);
   }

   TrustedListenableFutureTask(Callable<V> var1) {
      this.task = new TrustedListenableFutureTask.TrustedFutureInterruptibleTask(var1);
   }

   static <V> TrustedListenableFutureTask<V> create(AsyncCallable<V> var0) {
      return new TrustedListenableFutureTask(var0);
   }

   static <V> TrustedListenableFutureTask<V> create(Runnable var0, @NullableDecl V var1) {
      return new TrustedListenableFutureTask(Executors.callable(var0, var1));
   }

   static <V> TrustedListenableFutureTask<V> create(Callable<V> var0) {
      return new TrustedListenableFutureTask(var0);
   }

   protected void afterDone() {
      super.afterDone();
      if (this.wasInterrupted()) {
         InterruptibleTask var1 = this.task;
         if (var1 != null) {
            var1.interruptTask();
         }
      }

      this.task = null;
   }

   protected String pendingToString() {
      InterruptibleTask var1 = this.task;
      if (var1 != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append("task=[");
         var2.append(var1);
         var2.append("]");
         return var2.toString();
      } else {
         return super.pendingToString();
      }
   }

   public void run() {
      InterruptibleTask var1 = this.task;
      if (var1 != null) {
         var1.run();
      }

      this.task = null;
   }

   private final class TrustedFutureInterruptibleAsyncTask extends InterruptibleTask<ListenableFuture<V>> {
      private final AsyncCallable<V> callable;

      TrustedFutureInterruptibleAsyncTask(AsyncCallable<V> var2) {
         this.callable = (AsyncCallable)Preconditions.checkNotNull(var2);
      }

      void afterRanInterruptibly(ListenableFuture<V> var1, Throwable var2) {
         if (var2 == null) {
            TrustedListenableFutureTask.this.setFuture(var1);
         } else {
            TrustedListenableFutureTask.this.setException(var2);
         }

      }

      final boolean isDone() {
         return TrustedListenableFutureTask.this.isDone();
      }

      ListenableFuture<V> runInterruptibly() throws Exception {
         return (ListenableFuture)Preconditions.checkNotNull(this.callable.call(), "AsyncCallable.call returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", (Object)this.callable);
      }

      String toPendingString() {
         return this.callable.toString();
      }
   }

   private final class TrustedFutureInterruptibleTask extends InterruptibleTask<V> {
      private final Callable<V> callable;

      TrustedFutureInterruptibleTask(Callable<V> var2) {
         this.callable = (Callable)Preconditions.checkNotNull(var2);
      }

      void afterRanInterruptibly(V var1, Throwable var2) {
         if (var2 == null) {
            TrustedListenableFutureTask.this.set(var1);
         } else {
            TrustedListenableFutureTask.this.setException(var2);
         }

      }

      final boolean isDone() {
         return TrustedListenableFutureTask.this.isDone();
      }

      V runInterruptibly() throws Exception {
         return this.callable.call();
      }

      String toPendingString() {
         return this.callable.toString();
      }
   }
}

package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class CombinedFuture<V> extends AggregateFuture<Object, V> {
   private CombinedFuture<V>.CombinedFutureInterruptibleTask<?> task;

   CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> var1, boolean var2, Executor var3, AsyncCallable<V> var4) {
      super(var1, var2, false);
      this.task = new CombinedFuture.AsyncCallableInterruptibleTask(var4, var3);
      this.init();
   }

   CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> var1, boolean var2, Executor var3, Callable<V> var4) {
      super(var1, var2, false);
      this.task = new CombinedFuture.CallableInterruptibleTask(var4, var3);
      this.init();
   }

   void collectOneValue(int var1, @NullableDecl Object var2) {
   }

   void handleAllCompleted() {
      CombinedFuture.CombinedFutureInterruptibleTask var1 = this.task;
      if (var1 != null) {
         var1.execute();
      }

   }

   protected void interruptTask() {
      CombinedFuture.CombinedFutureInterruptibleTask var1 = this.task;
      if (var1 != null) {
         var1.interruptTask();
      }

   }

   void releaseResources(AggregateFuture.ReleaseResourcesReason var1) {
      super.releaseResources(var1);
      if (var1 == AggregateFuture.ReleaseResourcesReason.OUTPUT_FUTURE_DONE) {
         this.task = null;
      }

   }

   private final class AsyncCallableInterruptibleTask extends CombinedFuture<V>.CombinedFutureInterruptibleTask<ListenableFuture<V>> {
      private final AsyncCallable<V> callable;

      AsyncCallableInterruptibleTask(AsyncCallable<V> var2, Executor var3) {
         super(var3);
         this.callable = (AsyncCallable)Preconditions.checkNotNull(var2);
      }

      ListenableFuture<V> runInterruptibly() throws Exception {
         this.thrownByExecute = false;
         return (ListenableFuture)Preconditions.checkNotNull(this.callable.call(), "AsyncCallable.call returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", (Object)this.callable);
      }

      void setValue(ListenableFuture<V> var1) {
         CombinedFuture.this.setFuture(var1);
      }

      String toPendingString() {
         return this.callable.toString();
      }
   }

   private final class CallableInterruptibleTask extends CombinedFuture<V>.CombinedFutureInterruptibleTask<V> {
      private final Callable<V> callable;

      CallableInterruptibleTask(Callable<V> var2, Executor var3) {
         super(var3);
         this.callable = (Callable)Preconditions.checkNotNull(var2);
      }

      V runInterruptibly() throws Exception {
         this.thrownByExecute = false;
         return this.callable.call();
      }

      void setValue(V var1) {
         CombinedFuture.this.set(var1);
      }

      String toPendingString() {
         return this.callable.toString();
      }
   }

   private abstract class CombinedFutureInterruptibleTask<T> extends InterruptibleTask<T> {
      private final Executor listenerExecutor;
      boolean thrownByExecute = true;

      CombinedFutureInterruptibleTask(Executor var2) {
         this.listenerExecutor = (Executor)Preconditions.checkNotNull(var2);
      }

      final void afterRanInterruptibly(T var1, Throwable var2) {
         CombinedFuture.this.task = null;
         if (var2 != null) {
            if (var2 instanceof ExecutionException) {
               CombinedFuture.this.setException(var2.getCause());
            } else if (var2 instanceof CancellationException) {
               CombinedFuture.this.cancel(false);
            } else {
               CombinedFuture.this.setException(var2);
            }
         } else {
            this.setValue(var1);
         }

      }

      final void execute() {
         try {
            this.listenerExecutor.execute(this);
         } catch (RejectedExecutionException var2) {
            if (this.thrownByExecute) {
               CombinedFuture.this.setException(var2);
            }
         }

      }

      final boolean isDone() {
         return CombinedFuture.this.isDone();
      }

      abstract void setValue(T var1);
   }
}

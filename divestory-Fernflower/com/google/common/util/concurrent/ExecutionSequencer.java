package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

public final class ExecutionSequencer {
   private final AtomicReference<ListenableFuture<Object>> ref = new AtomicReference(Futures.immediateFuture((Object)null));

   private ExecutionSequencer() {
   }

   public static ExecutionSequencer create() {
      return new ExecutionSequencer();
   }

   public <T> ListenableFuture<T> submit(final Callable<T> var1, Executor var2) {
      Preconditions.checkNotNull(var1);
      return this.submitAsync(new AsyncCallable<T>() {
         public ListenableFuture<T> call() throws Exception {
            return Futures.immediateFuture(var1.call());
         }

         public String toString() {
            return var1.toString();
         }
      }, var2);
   }

   public <T> ListenableFuture<T> submitAsync(final AsyncCallable<T> var1, final Executor var2) {
      Preconditions.checkNotNull(var1);
      final AtomicReference var3 = new AtomicReference(ExecutionSequencer.RunningState.NOT_RUN);
      AsyncCallable var4 = new AsyncCallable<T>() {
         public ListenableFuture<T> call() throws Exception {
            return !var3.compareAndSet(ExecutionSequencer.RunningState.NOT_RUN, ExecutionSequencer.RunningState.STARTED) ? Futures.immediateCancelledFuture() : var1.call();
         }

         public String toString() {
            return var1.toString();
         }
      };
      final SettableFuture var5 = SettableFuture.create();
      final ListenableFuture var6 = (ListenableFuture)this.ref.getAndSet(var5);
      final ListenableFuture var9 = Futures.submitAsync(var4, new Executor() {
         public void execute(Runnable var1) {
            var6.addListener(var1, var2);
         }
      });
      final ListenableFuture var8 = Futures.nonCancellationPropagating(var9);
      Runnable var7 = new Runnable() {
         public void run() {
            if (var9.isDone() || var8.isCancelled() && var3.compareAndSet(ExecutionSequencer.RunningState.NOT_RUN, ExecutionSequencer.RunningState.CANCELLED)) {
               var5.setFuture(var6);
            }

         }
      };
      var8.addListener(var7, MoreExecutors.directExecutor());
      var9.addListener(var7, MoreExecutors.directExecutor());
      return var8;
   }

   static enum RunningState {
      CANCELLED,
      NOT_RUN,
      STARTED;

      static {
         ExecutionSequencer.RunningState var0 = new ExecutionSequencer.RunningState("STARTED", 2);
         STARTED = var0;
      }
   }
}

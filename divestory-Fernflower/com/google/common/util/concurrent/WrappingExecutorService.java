package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

abstract class WrappingExecutorService implements ExecutorService {
   private final ExecutorService delegate;

   protected WrappingExecutorService(ExecutorService var1) {
      this.delegate = (ExecutorService)Preconditions.checkNotNull(var1);
   }

   private <T> ImmutableList<Callable<T>> wrapTasks(Collection<? extends Callable<T>> var1) {
      ImmutableList.Builder var2 = ImmutableList.builder();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         var2.add((Object)this.wrapTask((Callable)var3.next()));
      }

      return var2.build();
   }

   public final boolean awaitTermination(long var1, TimeUnit var3) throws InterruptedException {
      return this.delegate.awaitTermination(var1, var3);
   }

   public final void execute(Runnable var1) {
      this.delegate.execute(this.wrapTask(var1));
   }

   public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> var1) throws InterruptedException {
      return this.delegate.invokeAll(this.wrapTasks(var1));
   }

   public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> var1, long var2, TimeUnit var4) throws InterruptedException {
      return this.delegate.invokeAll(this.wrapTasks(var1), var2, var4);
   }

   public final <T> T invokeAny(Collection<? extends Callable<T>> var1) throws InterruptedException, ExecutionException {
      return this.delegate.invokeAny(this.wrapTasks(var1));
   }

   public final <T> T invokeAny(Collection<? extends Callable<T>> var1, long var2, TimeUnit var4) throws InterruptedException, ExecutionException, TimeoutException {
      return this.delegate.invokeAny(this.wrapTasks(var1), var2, var4);
   }

   public final boolean isShutdown() {
      return this.delegate.isShutdown();
   }

   public final boolean isTerminated() {
      return this.delegate.isTerminated();
   }

   public final void shutdown() {
      this.delegate.shutdown();
   }

   public final List<Runnable> shutdownNow() {
      return this.delegate.shutdownNow();
   }

   public final Future<?> submit(Runnable var1) {
      return this.delegate.submit(this.wrapTask(var1));
   }

   public final <T> Future<T> submit(Runnable var1, T var2) {
      return this.delegate.submit(this.wrapTask(var1), var2);
   }

   public final <T> Future<T> submit(Callable<T> var1) {
      return this.delegate.submit(this.wrapTask((Callable)Preconditions.checkNotNull(var1)));
   }

   protected Runnable wrapTask(Runnable var1) {
      return new Runnable(this.wrapTask(Executors.callable(var1, (Object)null))) {
         // $FF: synthetic field
         final Callable val$wrapped;

         {
            this.val$wrapped = var2;
         }

         public void run() {
            try {
               this.val$wrapped.call();
            } catch (Exception var2) {
               Throwables.throwIfUnchecked(var2);
               throw new RuntimeException(var2);
            }
         }
      };
   }

   protected abstract <T> Callable<T> wrapTask(Callable<T> var1);
}

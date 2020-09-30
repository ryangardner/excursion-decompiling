package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class ForwardingFluentFuture<V> extends FluentFuture<V> {
   private final ListenableFuture<V> delegate;

   ForwardingFluentFuture(ListenableFuture<V> var1) {
      this.delegate = (ListenableFuture)Preconditions.checkNotNull(var1);
   }

   public void addListener(Runnable var1, Executor var2) {
      this.delegate.addListener(var1, var2);
   }

   public boolean cancel(boolean var1) {
      return this.delegate.cancel(var1);
   }

   public V get() throws InterruptedException, ExecutionException {
      return this.delegate.get();
   }

   public V get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
      return this.delegate.get(var1, var3);
   }

   public boolean isCancelled() {
      return this.delegate.isCancelled();
   }

   public boolean isDone() {
      return this.delegate.isDone();
   }

   public String toString() {
      return this.delegate.toString();
   }
}

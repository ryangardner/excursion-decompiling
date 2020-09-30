package com.google.common.util.concurrent;

import java.util.concurrent.Callable;

public abstract class ForwardingListeningExecutorService extends ForwardingExecutorService implements ListeningExecutorService {
   protected ForwardingListeningExecutorService() {
   }

   protected abstract ListeningExecutorService delegate();

   public ListenableFuture<?> submit(Runnable var1) {
      return this.delegate().submit(var1);
   }

   public <T> ListenableFuture<T> submit(Runnable var1, T var2) {
      return this.delegate().submit(var1, var2);
   }

   public <T> ListenableFuture<T> submit(Callable<T> var1) {
      return this.delegate().submit(var1);
   }
}

package com.google.common.util.concurrent;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractListeningExecutorService extends AbstractExecutorService implements ListeningExecutorService {
   protected final <T> RunnableFuture<T> newTaskFor(Runnable var1, T var2) {
      return TrustedListenableFutureTask.create(var1, var2);
   }

   protected final <T> RunnableFuture<T> newTaskFor(Callable<T> var1) {
      return TrustedListenableFutureTask.create(var1);
   }

   public ListenableFuture<?> submit(Runnable var1) {
      return (ListenableFuture)super.submit(var1);
   }

   public <T> ListenableFuture<T> submit(Runnable var1, @NullableDecl T var2) {
      return (ListenableFuture)super.submit(var1, var2);
   }

   public <T> ListenableFuture<T> submit(Callable<T> var1) {
      return (ListenableFuture)super.submit(var1);
   }
}

package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;

public abstract class ForwardingListenableFuture<V> extends ForwardingFuture<V> implements ListenableFuture<V> {
   protected ForwardingListenableFuture() {
   }

   public void addListener(Runnable var1, Executor var2) {
      this.delegate().addListener(var1, var2);
   }

   protected abstract ListenableFuture<? extends V> delegate();

   public abstract static class SimpleForwardingListenableFuture<V> extends ForwardingListenableFuture<V> {
      private final ListenableFuture<V> delegate;

      protected SimpleForwardingListenableFuture(ListenableFuture<V> var1) {
         this.delegate = (ListenableFuture)Preconditions.checkNotNull(var1);
      }

      protected final ListenableFuture<V> delegate() {
         return this.delegate;
      }
   }
}

package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class ImmediateFuture<V> implements ListenableFuture<V> {
   static final ListenableFuture<?> NULL = new ImmediateFuture((Object)null);
   private static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());
   @NullableDecl
   private final V value;

   ImmediateFuture(@NullableDecl V var1) {
      this.value = var1;
   }

   public void addListener(Runnable var1, Executor var2) {
      Preconditions.checkNotNull(var1, "Runnable was null.");
      Preconditions.checkNotNull(var2, "Executor was null.");

      try {
         var2.execute(var1);
      } catch (RuntimeException var7) {
         Logger var4 = log;
         Level var5 = Level.SEVERE;
         StringBuilder var6 = new StringBuilder();
         var6.append("RuntimeException while executing runnable ");
         var6.append(var1);
         var6.append(" with executor ");
         var6.append(var2);
         var4.log(var5, var6.toString(), var7);
      }

   }

   public boolean cancel(boolean var1) {
      return false;
   }

   public V get() {
      return this.value;
   }

   public V get(long var1, TimeUnit var3) throws ExecutionException {
      Preconditions.checkNotNull(var3);
      return this.get();
   }

   public boolean isCancelled() {
      return false;
   }

   public boolean isDone() {
      return true;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(super.toString());
      var1.append("[status=SUCCESS, result=[");
      var1.append(this.value);
      var1.append("]]");
      return var1.toString();
   }

   static final class ImmediateCancelledFuture<V> extends AbstractFuture.TrustedFuture<V> {
      ImmediateCancelledFuture() {
         this.cancel(false);
      }
   }

   static final class ImmediateFailedFuture<V> extends AbstractFuture.TrustedFuture<V> {
      ImmediateFailedFuture(Throwable var1) {
         this.setException(var1);
      }
   }
}

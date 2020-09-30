package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class SettableFuture<V> extends AbstractFuture.TrustedFuture<V> {
   private SettableFuture() {
   }

   public static <V> SettableFuture<V> create() {
      return new SettableFuture();
   }

   public boolean set(@NullableDecl V var1) {
      return super.set(var1);
   }

   public boolean setException(Throwable var1) {
      return super.setException(var1);
   }

   public boolean setFuture(ListenableFuture<? extends V> var1) {
      return super.setFuture(var1);
   }
}

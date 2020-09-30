package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class UncheckedTimeoutException extends RuntimeException {
   private static final long serialVersionUID = 0L;

   public UncheckedTimeoutException() {
   }

   public UncheckedTimeoutException(@NullableDecl String var1) {
      super(var1);
   }

   public UncheckedTimeoutException(@NullableDecl String var1, @NullableDecl Throwable var2) {
      super(var1, var2);
   }

   public UncheckedTimeoutException(@NullableDecl Throwable var1) {
      super(var1);
   }
}

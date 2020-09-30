package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class UncheckedExecutionException extends RuntimeException {
   private static final long serialVersionUID = 0L;

   protected UncheckedExecutionException() {
   }

   protected UncheckedExecutionException(@NullableDecl String var1) {
      super(var1);
   }

   public UncheckedExecutionException(@NullableDecl String var1, @NullableDecl Throwable var2) {
      super(var1, var2);
   }

   public UncheckedExecutionException(@NullableDecl Throwable var1) {
      super(var1);
   }
}

package com.google.common.base;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class VerifyException extends RuntimeException {
   public VerifyException() {
   }

   public VerifyException(@NullableDecl String var1) {
      super(var1);
   }

   public VerifyException(@NullableDecl String var1, @NullableDecl Throwable var2) {
      super(var1, var2);
   }

   public VerifyException(@NullableDecl Throwable var1) {
      super(var1);
   }
}

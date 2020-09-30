package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ExecutionError extends Error {
   private static final long serialVersionUID = 0L;

   protected ExecutionError() {
   }

   public ExecutionError(@NullableDecl Error var1) {
      super(var1);
   }

   protected ExecutionError(@NullableDecl String var1) {
      super(var1);
   }

   public ExecutionError(@NullableDecl String var1, @NullableDecl Error var2) {
      super(var1, var2);
   }
}

package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Platform {
   private Platform() {
   }

   static boolean isInstanceOfThrowableClass(@NullableDecl Throwable var0, Class<? extends Throwable> var1) {
      return var1.isInstance(var0);
   }
}

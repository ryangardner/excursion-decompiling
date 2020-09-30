package com.google.api.client.util;

public final class Throwables {
   private Throwables() {
   }

   public static RuntimeException propagate(Throwable var0) {
      return com.google.common.base.Throwables.propagate(var0);
   }

   public static void propagateIfPossible(Throwable var0) {
      if (var0 != null) {
         com.google.common.base.Throwables.throwIfUnchecked(var0);
      }

   }

   public static <X extends Throwable> void propagateIfPossible(Throwable var0, Class<X> var1) throws X {
      com.google.common.base.Throwables.propagateIfPossible(var0, var1);
   }
}

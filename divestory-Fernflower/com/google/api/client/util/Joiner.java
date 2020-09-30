package com.google.api.client.util;

public final class Joiner {
   private final com.google.common.base.Joiner wrapped;

   private Joiner(com.google.common.base.Joiner var1) {
      this.wrapped = var1;
   }

   public static Joiner on(char var0) {
      return new Joiner(com.google.common.base.Joiner.on(var0));
   }

   public final String join(Iterable<?> var1) {
      return this.wrapped.join(var1);
   }
}

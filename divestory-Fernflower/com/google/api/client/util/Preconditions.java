package com.google.api.client.util;

public final class Preconditions {
   private Preconditions() {
   }

   public static void checkArgument(boolean var0) {
      com.google.common.base.Preconditions.checkArgument(var0);
   }

   public static void checkArgument(boolean var0, Object var1) {
      com.google.common.base.Preconditions.checkArgument(var0, var1);
   }

   public static void checkArgument(boolean var0, String var1, Object... var2) {
      com.google.common.base.Preconditions.checkArgument(var0, var1, var2);
   }

   public static <T> T checkNotNull(T var0) {
      return com.google.common.base.Preconditions.checkNotNull(var0);
   }

   public static <T> T checkNotNull(T var0, Object var1) {
      return com.google.common.base.Preconditions.checkNotNull(var0, var1);
   }

   public static <T> T checkNotNull(T var0, String var1, Object... var2) {
      return com.google.common.base.Preconditions.checkNotNull(var0, var1, var2);
   }

   public static void checkState(boolean var0) {
      com.google.common.base.Preconditions.checkState(var0);
   }

   public static void checkState(boolean var0, Object var1) {
      com.google.common.base.Preconditions.checkState(var0, var1);
   }

   public static void checkState(boolean var0, String var1, Object... var2) {
      com.google.common.base.Preconditions.checkState(var0, var1, var2);
   }
}

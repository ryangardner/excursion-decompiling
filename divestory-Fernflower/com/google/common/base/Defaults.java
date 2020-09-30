package com.google.common.base;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Defaults {
   private static final Double DOUBLE_DEFAULT = 0.0D;
   private static final Float FLOAT_DEFAULT = 0.0F;

   private Defaults() {
   }

   @NullableDecl
   public static <T> T defaultValue(Class<T> var0) {
      Preconditions.checkNotNull(var0);
      if (var0 == Boolean.TYPE) {
         return Boolean.FALSE;
      } else if (var0 == Character.TYPE) {
         return '\u0000';
      } else if (var0 == Byte.TYPE) {
         return 0;
      } else if (var0 == Short.TYPE) {
         return Short.valueOf((short)0);
      } else if (var0 == Integer.TYPE) {
         return 0;
      } else if (var0 == Long.TYPE) {
         return 0L;
      } else if (var0 == Float.TYPE) {
         return FLOAT_DEFAULT;
      } else {
         return var0 == Double.TYPE ? DOUBLE_DEFAULT : null;
      }
   }
}

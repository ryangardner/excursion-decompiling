package io.opencensus.common;

import javax.annotation.Nullable;

public final class Functions {
   private static final Function<Object, Void> RETURN_NULL = new Function<Object, Void>() {
      @Nullable
      public Void apply(Object var1) {
         return null;
      }
   };
   private static final Function<Object, String> RETURN_TO_STRING = new Function<Object, String>() {
      public String apply(Object var1) {
         String var2;
         if (var1 == null) {
            var2 = null;
         } else {
            var2 = var1.toString();
         }

         return var2;
      }
   };
   private static final Function<Object, Void> THROW_ASSERTION_ERROR = new Function<Object, Void>() {
      public Void apply(Object var1) {
         throw new AssertionError();
      }
   };
   private static final Function<Object, Void> THROW_ILLEGAL_ARGUMENT_EXCEPTION = new Function<Object, Void>() {
      public Void apply(Object var1) {
         throw new IllegalArgumentException();
      }
   };

   private Functions() {
   }

   public static <T> Function<Object, T> returnConstant(final T var0) {
      return new Function<Object, T>() {
         public T apply(Object var1) {
            return var0;
         }
      };
   }

   public static <T> Function<Object, T> returnNull() {
      return RETURN_NULL;
   }

   public static Function<Object, String> returnToString() {
      return RETURN_TO_STRING;
   }

   public static <T> Function<Object, T> throwAssertionError() {
      return THROW_ASSERTION_ERROR;
   }

   public static <T> Function<Object, T> throwIllegalArgumentException() {
      return THROW_ILLEGAL_ARGUMENT_EXCEPTION;
   }
}

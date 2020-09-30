package com.google.common.base;

abstract class CommonPattern {
   public static CommonPattern compile(String var0) {
      return Platform.compilePattern(var0);
   }

   public static boolean isPcreLike() {
      return Platform.patternCompilerIsPcreLike();
   }

   public abstract int flags();

   public abstract CommonMatcher matcher(CharSequence var1);

   public abstract String pattern();

   public abstract String toString();
}

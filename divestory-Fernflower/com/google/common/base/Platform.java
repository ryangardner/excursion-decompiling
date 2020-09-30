package com.google.common.base;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.ServiceConfigurationError;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Platform {
   private static final String GWT_RPC_PROPERTY_NAME = "guava.gwt.emergency_reenable_rpc";
   private static final Logger logger = Logger.getLogger(Platform.class.getName());
   private static final PatternCompiler patternCompiler = loadPatternCompiler();

   private Platform() {
   }

   static void checkGwtRpcEnabled() {
      if (!Boolean.parseBoolean(System.getProperty("guava.gwt.emergency_reenable_rpc", "true"))) {
         throw new UnsupportedOperationException(Strings.lenientFormat("We are removing GWT-RPC support for Guava types. You can temporarily reenable support by setting the system property %s to true. For more about system properties, see %s. For more about Guava's GWT-RPC support, see %s.", "guava.gwt.emergency_reenable_rpc", "https://stackoverflow.com/q/5189914/28465", "https://groups.google.com/d/msg/guava-announce/zHZTFg7YF3o/rQNnwdHeEwAJ"));
      }
   }

   static CommonPattern compilePattern(String var0) {
      Preconditions.checkNotNull(var0);
      return patternCompiler.compile(var0);
   }

   static String emptyToNull(@NullableDecl String var0) {
      String var1 = var0;
      if (stringIsNullOrEmpty(var0)) {
         var1 = null;
      }

      return var1;
   }

   static String formatCompact4Digits(double var0) {
      return String.format(Locale.ROOT, "%.4g", var0);
   }

   static <T extends Enum<T>> Optional<T> getEnumIfPresent(Class<T> var0, String var1) {
      WeakReference var3 = (WeakReference)Enums.getEnumConstants(var0).get(var1);
      Optional var2;
      if (var3 == null) {
         var2 = Optional.absent();
      } else {
         var2 = Optional.of(var0.cast(var3.get()));
      }

      return var2;
   }

   private static PatternCompiler loadPatternCompiler() {
      return new Platform.JdkPatternCompiler();
   }

   private static void logPatternCompilerError(ServiceConfigurationError var0) {
      logger.log(Level.WARNING, "Error loading regex compiler, falling back to next option", var0);
   }

   static String nullToEmpty(@NullableDecl String var0) {
      String var1 = var0;
      if (var0 == null) {
         var1 = "";
      }

      return var1;
   }

   static boolean patternCompilerIsPcreLike() {
      return patternCompiler.isPcreLike();
   }

   static CharMatcher precomputeCharMatcher(CharMatcher var0) {
      return var0.precomputedInternal();
   }

   static boolean stringIsNullOrEmpty(@NullableDecl String var0) {
      boolean var1;
      if (var0 != null && !var0.isEmpty()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   static long systemNanoTime() {
      return System.nanoTime();
   }

   private static final class JdkPatternCompiler implements PatternCompiler {
      private JdkPatternCompiler() {
      }

      // $FF: synthetic method
      JdkPatternCompiler(Object var1) {
         this();
      }

      public CommonPattern compile(String var1) {
         return new JdkPattern(Pattern.compile(var1));
      }

      public boolean isPcreLike() {
         return true;
      }
   }
}

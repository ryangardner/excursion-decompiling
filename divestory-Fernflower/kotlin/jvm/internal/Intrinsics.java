package kotlin.jvm.internal;

import java.util.Arrays;
import kotlin.KotlinNullPointerException;
import kotlin.UninitializedPropertyAccessException;

public class Intrinsics {
   private Intrinsics() {
   }

   public static boolean areEqual(double var0, Double var2) {
      boolean var3;
      if (var2 != null && var0 == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static boolean areEqual(float var0, Float var1) {
      boolean var2;
      if (var1 != null && var0 == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean areEqual(Double var0, double var1) {
      boolean var3;
      if (var0 != null && var0 == var1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static boolean areEqual(Double var0, Double var1) {
      boolean var2 = true;
      if (var0 == null) {
         if (var1 == null) {
            return var2;
         }
      } else if (var1 != null && var0 == var1) {
         return var2;
      }

      var2 = false;
      return var2;
   }

   public static boolean areEqual(Float var0, float var1) {
      boolean var2;
      if (var0 != null && var0 == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean areEqual(Float var0, Float var1) {
      boolean var2 = true;
      if (var0 == null) {
         if (var1 == null) {
            return var2;
         }
      } else if (var1 != null && var0 == var1) {
         return var2;
      }

      var2 = false;
      return var2;
   }

   public static boolean areEqual(Object var0, Object var1) {
      boolean var2;
      if (var0 == null) {
         if (var1 == null) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         var2 = var0.equals(var1);
      }

      return var2;
   }

   public static void checkExpressionValueIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append(" must not be null");
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var2.toString()));
      }
   }

   public static void checkFieldIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var1));
      }
   }

   public static void checkFieldIsNotNull(Object var0, String var1, String var2) {
      if (var0 == null) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Field specified as non-null is null: ");
         var3.append(var1);
         var3.append(".");
         var3.append(var2);
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var3.toString()));
      }
   }

   public static void checkHasClass(String var0) throws ClassNotFoundException {
      String var1 = var0.replace('/', '.');

      try {
         Class.forName(var1);
      } catch (ClassNotFoundException var3) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Class ");
         var4.append(var1);
         var4.append(" is not found. Please update the Kotlin runtime to the latest version");
         throw (ClassNotFoundException)sanitizeStackTrace(new ClassNotFoundException(var4.toString(), var3));
      }
   }

   public static void checkHasClass(String var0, String var1) throws ClassNotFoundException {
      String var2 = var0.replace('/', '.');

      try {
         Class.forName(var2);
      } catch (ClassNotFoundException var4) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Class ");
         var5.append(var2);
         var5.append(" is not found: this code requires the Kotlin runtime of version at least ");
         var5.append(var1);
         throw (ClassNotFoundException)sanitizeStackTrace(new ClassNotFoundException(var5.toString(), var4));
      }
   }

   public static void checkNotNull(Object var0) {
      if (var0 == null) {
         throwJavaNpe();
      }

   }

   public static void checkNotNull(Object var0, String var1) {
      if (var0 == null) {
         throwJavaNpe(var1);
      }

   }

   public static void checkNotNullExpressionValue(Object var0, String var1) {
      if (var0 == null) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append(" must not be null");
         throw (NullPointerException)sanitizeStackTrace(new NullPointerException(var2.toString()));
      }
   }

   public static void checkNotNullParameter(Object var0, String var1) {
      if (var0 == null) {
         throw (NullPointerException)sanitizeStackTrace(new NullPointerException(var1));
      }
   }

   public static void checkParameterIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         throwParameterIsNullException(var1);
      }

   }

   public static void checkReturnedValueIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var1));
      }
   }

   public static void checkReturnedValueIsNotNull(Object var0, String var1, String var2) {
      if (var0 == null) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Method specified as non-null returned null: ");
         var3.append(var1);
         var3.append(".");
         var3.append(var2);
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var3.toString()));
      }
   }

   public static int compare(int var0, int var1) {
      byte var2;
      if (var0 < var1) {
         var2 = -1;
      } else if (var0 == var1) {
         var2 = 0;
      } else {
         var2 = 1;
      }

      return var2;
   }

   public static int compare(long var0, long var2) {
      long var6;
      int var4 = (var6 = var0 - var2) == 0L ? 0 : (var6 < 0L ? -1 : 1);
      byte var5;
      if (var4 < 0) {
         var5 = -1;
      } else if (var4 == 0) {
         var5 = 0;
      } else {
         var5 = 1;
      }

      return var5;
   }

   public static void needClassReification() {
      throwUndefinedForReified();
   }

   public static void needClassReification(String var0) {
      throwUndefinedForReified(var0);
   }

   public static void reifiedOperationMarker(int var0, String var1) {
      throwUndefinedForReified();
   }

   public static void reifiedOperationMarker(int var0, String var1, String var2) {
      throwUndefinedForReified(var2);
   }

   private static <T extends Throwable> T sanitizeStackTrace(T var0) {
      return sanitizeStackTrace(var0, Intrinsics.class.getName());
   }

   static <T extends Throwable> T sanitizeStackTrace(T var0, String var1) {
      StackTraceElement[] var2 = var0.getStackTrace();
      int var3 = var2.length;
      int var4 = -1;

      for(int var5 = 0; var5 < var3; ++var5) {
         if (var1.equals(var2[var5].getClassName())) {
            var4 = var5;
         }
      }

      var0.setStackTrace((StackTraceElement[])Arrays.copyOfRange(var2, var4 + 1, var3));
      return var0;
   }

   public static String stringPlus(String var0, Object var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var0);
      var2.append(var1);
      return var2.toString();
   }

   public static void throwAssert() {
      throw (AssertionError)sanitizeStackTrace(new AssertionError());
   }

   public static void throwAssert(String var0) {
      throw (AssertionError)sanitizeStackTrace(new AssertionError(var0));
   }

   public static void throwIllegalArgument() {
      throw (IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException());
   }

   public static void throwIllegalArgument(String var0) {
      throw (IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException(var0));
   }

   public static void throwIllegalState() {
      throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException());
   }

   public static void throwIllegalState(String var0) {
      throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var0));
   }

   public static void throwJavaNpe() {
      throw (NullPointerException)sanitizeStackTrace(new NullPointerException());
   }

   public static void throwJavaNpe(String var0) {
      throw (NullPointerException)sanitizeStackTrace(new NullPointerException(var0));
   }

   public static void throwNpe() {
      throw (KotlinNullPointerException)sanitizeStackTrace(new KotlinNullPointerException());
   }

   public static void throwNpe(String var0) {
      throw (KotlinNullPointerException)sanitizeStackTrace(new KotlinNullPointerException(var0));
   }

   private static void throwParameterIsNullException(String var0) {
      StackTraceElement var1 = Thread.currentThread().getStackTrace()[3];
      String var2 = var1.getClassName();
      String var4 = var1.getMethodName();
      StringBuilder var3 = new StringBuilder();
      var3.append("Parameter specified as non-null is null: method ");
      var3.append(var2);
      var3.append(".");
      var3.append(var4);
      var3.append(", parameter ");
      var3.append(var0);
      throw (IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException(var3.toString()));
   }

   public static void throwUndefinedForReified() {
      throwUndefinedForReified("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
   }

   public static void throwUndefinedForReified(String var0) {
      throw new UnsupportedOperationException(var0);
   }

   public static void throwUninitializedProperty(String var0) {
      throw (UninitializedPropertyAccessException)sanitizeStackTrace(new UninitializedPropertyAccessException(var0));
   }

   public static void throwUninitializedPropertyAccessException(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("lateinit property ");
      var1.append(var0);
      var1.append(" has not been initialized");
      throwUninitializedProperty(var1.toString());
   }
}

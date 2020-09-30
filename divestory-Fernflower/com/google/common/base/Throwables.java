package com.google.common.base;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Throwables {
   private static final String JAVA_LANG_ACCESS_CLASSNAME = "sun.misc.JavaLangAccess";
   static final String SHARED_SECRETS_CLASSNAME = "sun.misc.SharedSecrets";
   @NullableDecl
   private static final Method getStackTraceDepthMethod;
   @NullableDecl
   private static final Method getStackTraceElementMethod;
   @NullableDecl
   private static final Object jla;

   static {
      Object var0 = getJLA();
      jla = var0;
      Object var1 = null;
      Method var2;
      if (var0 == null) {
         var2 = null;
      } else {
         var2 = getGetMethod();
      }

      getStackTraceElementMethod = var2;
      if (jla == null) {
         var2 = (Method)var1;
      } else {
         var2 = getSizeMethod();
      }

      getStackTraceDepthMethod = var2;
   }

   private Throwables() {
   }

   public static List<Throwable> getCausalChain(Throwable var0) {
      Preconditions.checkNotNull(var0);
      ArrayList var1 = new ArrayList(4);
      var1.add(var0);
      boolean var2 = false;
      Throwable var4 = var0;
      var0 = var0;

      while(true) {
         Throwable var3 = var4.getCause();
         if (var3 == null) {
            return Collections.unmodifiableList(var1);
         }

         var1.add(var3);
         if (var3 == var0) {
            throw new IllegalArgumentException("Loop in causal chain detected.", var3);
         }

         var4 = var0;
         if (var2) {
            var4 = var0.getCause();
         }

         var2 ^= true;
         var0 = var4;
         var4 = var3;
      }
   }

   public static <X extends Throwable> X getCauseAs(Throwable var0, Class<X> var1) {
      try {
         Throwable var3 = (Throwable)var1.cast(var0.getCause());
         return var3;
      } catch (ClassCastException var2) {
         var2.initCause(var0);
         throw var2;
      }
   }

   @NullableDecl
   private static Method getGetMethod() {
      return getJlaMethod("getStackTraceElement", Throwable.class, Integer.TYPE);
   }

   @NullableDecl
   private static Object getJLA() {
      label47: {
         ThreadDeath var0 = null;

         label39: {
            try {
               Object var1 = Class.forName("sun.misc.SharedSecrets", false, (ClassLoader)null).getMethod("getJavaLangAccess").invoke((Object)null);
               break label39;
            } catch (ThreadDeath var4) {
               var0 = var4;
            } finally {
               return var0;
            }

         }

         return var0;
      }
   }

   @NullableDecl
   private static Method getJlaMethod(String var0, Class<?>... var1) throws ThreadDeath {
      ThreadDeath var6;
      try {
         Method var7 = Class.forName("sun.misc.JavaLangAccess", false, (ClassLoader)null).getMethod(var0, var1);
         return var7;
      } catch (ThreadDeath var4) {
         var6 = var4;
      } finally {
         ;
      }

      throw var6;
   }

   public static Throwable getRootCause(Throwable var0) {
      boolean var1 = false;
      Throwable var3 = var0;
      var0 = var0;

      while(true) {
         Throwable var2 = var3.getCause();
         if (var2 == null) {
            return var3;
         }

         if (var2 == var0) {
            throw new IllegalArgumentException("Loop in causal chain detected.", var2);
         }

         var3 = var0;
         if (var1) {
            var3 = var0.getCause();
         }

         var1 ^= true;
         var0 = var3;
         var3 = var2;
      }
   }

   @NullableDecl
   private static Method getSizeMethod() {
      // $FF: Couldn't be decompiled
   }

   public static String getStackTraceAsString(Throwable var0) {
      StringWriter var1 = new StringWriter();
      var0.printStackTrace(new PrintWriter(var1));
      return var1.toString();
   }

   private static Object invokeAccessibleNonThrowingMethod(Method var0, Object var1, Object... var2) {
      try {
         Object var5 = var0.invoke(var1, var2);
         return var5;
      } catch (IllegalAccessException var3) {
         throw new RuntimeException(var3);
      } catch (InvocationTargetException var4) {
         throw propagate(var4.getCause());
      }
   }

   private static List<StackTraceElement> jlaStackTrace(final Throwable var0) {
      Preconditions.checkNotNull(var0);
      return new AbstractList<StackTraceElement>() {
         public StackTraceElement get(int var1) {
            return (StackTraceElement)Throwables.invokeAccessibleNonThrowingMethod(Throwables.getStackTraceElementMethod, Throwables.jla, var0, var1);
         }

         public int size() {
            return (Integer)Throwables.invokeAccessibleNonThrowingMethod(Throwables.getStackTraceDepthMethod, Throwables.jla, var0);
         }
      };
   }

   public static List<StackTraceElement> lazyStackTrace(Throwable var0) {
      List var1;
      if (lazyStackTraceIsLazy()) {
         var1 = jlaStackTrace(var0);
      } else {
         var1 = Collections.unmodifiableList(Arrays.asList(var0.getStackTrace()));
      }

      return var1;
   }

   public static boolean lazyStackTraceIsLazy() {
      boolean var0;
      if (getStackTraceElementMethod != null && getStackTraceDepthMethod != null) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   @Deprecated
   public static RuntimeException propagate(Throwable var0) {
      throwIfUnchecked(var0);
      throw new RuntimeException(var0);
   }

   @Deprecated
   public static <X extends Throwable> void propagateIfInstanceOf(@NullableDecl Throwable var0, Class<X> var1) throws X {
      if (var0 != null) {
         throwIfInstanceOf(var0, var1);
      }

   }

   @Deprecated
   public static void propagateIfPossible(@NullableDecl Throwable var0) {
      if (var0 != null) {
         throwIfUnchecked(var0);
      }

   }

   public static <X extends Throwable> void propagateIfPossible(@NullableDecl Throwable var0, Class<X> var1) throws X {
      propagateIfInstanceOf(var0, var1);
      propagateIfPossible(var0);
   }

   public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@NullableDecl Throwable var0, Class<X1> var1, Class<X2> var2) throws X1, X2 {
      Preconditions.checkNotNull(var2);
      propagateIfInstanceOf(var0, var1);
      propagateIfPossible(var0, var2);
   }

   public static <X extends Throwable> void throwIfInstanceOf(Throwable var0, Class<X> var1) throws X {
      Preconditions.checkNotNull(var0);
      if (var1.isInstance(var0)) {
         throw (Throwable)var1.cast(var0);
      }
   }

   public static void throwIfUnchecked(Throwable var0) {
      Preconditions.checkNotNull(var0);
      if (!(var0 instanceof RuntimeException)) {
         if (var0 instanceof Error) {
            throw (Error)var0;
         }
      } else {
         throw (RuntimeException)var0;
      }
   }
}

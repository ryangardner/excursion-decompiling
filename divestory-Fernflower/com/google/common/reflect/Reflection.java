package com.google.common.reflect;

import com.google.common.base.Preconditions;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public final class Reflection {
   private Reflection() {
   }

   public static String getPackageName(Class<?> var0) {
      return getPackageName(var0.getName());
   }

   public static String getPackageName(String var0) {
      int var1 = var0.lastIndexOf(46);
      if (var1 < 0) {
         var0 = "";
      } else {
         var0 = var0.substring(0, var1);
      }

      return var0;
   }

   public static void initialize(Class<?>... var0) {
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         Class var3 = var0[var2];

         try {
            Class.forName(var3.getName(), true, var3.getClassLoader());
         } catch (ClassNotFoundException var4) {
            throw new AssertionError(var4);
         }
      }

   }

   public static <T> T newProxy(Class<T> var0, InvocationHandler var1) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkArgument(var0.isInterface(), "%s is not an interface", (Object)var0);
      return var0.cast(Proxy.newProxyInstance(var0.getClassLoader(), new Class[]{var0}, var1));
   }
}

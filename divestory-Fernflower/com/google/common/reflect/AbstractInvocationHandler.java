package com.google.common.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractInvocationHandler implements InvocationHandler {
   private static final Object[] NO_ARGS = new Object[0];

   private static boolean isProxyOfSameInterfaces(Object var0, Class<?> var1) {
      boolean var2;
      if (var1.isInstance(var0) || Proxy.isProxyClass(var0.getClass()) && Arrays.equals(var0.getClass().getInterfaces(), var1.getInterfaces())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean equals(Object var1) {
      return super.equals(var1);
   }

   protected abstract Object handleInvocation(Object var1, Method var2, Object[] var3) throws Throwable;

   public int hashCode() {
      return super.hashCode();
   }

   public final Object invoke(Object var1, Method var2, @NullableDecl Object[] var3) throws Throwable {
      Object[] var4 = var3;
      if (var3 == null) {
         var4 = NO_ARGS;
      }

      if (var4.length == 0 && var2.getName().equals("hashCode")) {
         return this.hashCode();
      } else {
         int var5 = var4.length;
         boolean var6 = true;
         if (var5 == 1 && var2.getName().equals("equals") && var2.getParameterTypes()[0] == Object.class) {
            Object var7 = var4[0];
            if (var7 == null) {
               return false;
            } else if (var1 == var7) {
               return true;
            } else {
               if (!isProxyOfSameInterfaces(var7, var1.getClass()) || !this.equals(Proxy.getInvocationHandler(var7))) {
                  var6 = false;
               }

               return var6;
            }
         } else {
            return var4.length == 0 && var2.getName().equals("toString") ? this.toString() : this.handleInvocation(var1, var2, var4);
         }
      }
   }

   public String toString() {
      return super.toString();
   }
}

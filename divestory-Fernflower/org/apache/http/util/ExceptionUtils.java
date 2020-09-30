package org.apache.http.util;

import java.lang.reflect.Method;

public final class ExceptionUtils {
   private static final Method INIT_CAUSE_METHOD = getInitCauseMethod();
   // $FF: synthetic field
   static Class class$java$lang$Throwable;

   private ExceptionUtils() {
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         Class var2 = Class.forName(var0);
         return var2;
      } catch (ClassNotFoundException var1) {
         throw new NoClassDefFoundError(var1.getMessage());
      }
   }

   private static Method getInitCauseMethod() {
      Class var0;
      boolean var10001;
      try {
         var0 = class$java$lang$Throwable;
      } catch (NoSuchMethodException var7) {
         var10001 = false;
         return null;
      }

      if (var0 == null) {
         try {
            var0 = class$("java.lang.Throwable");
            class$java$lang$Throwable = var0;
         } catch (NoSuchMethodException var6) {
            var10001 = false;
            return null;
         }
      } else {
         try {
            var0 = class$java$lang$Throwable;
         } catch (NoSuchMethodException var5) {
            var10001 = false;
            return null;
         }
      }

      Class var1;
      label52: {
         try {
            if (class$java$lang$Throwable == null) {
               var1 = class$("java.lang.Throwable");
               class$java$lang$Throwable = var1;
               break label52;
            }
         } catch (NoSuchMethodException var4) {
            var10001 = false;
            return null;
         }

         try {
            var1 = class$java$lang$Throwable;
         } catch (NoSuchMethodException var3) {
            var10001 = false;
            return null;
         }
      }

      try {
         Method var8 = var1.getMethod("initCause", var0);
         return var8;
      } catch (NoSuchMethodException var2) {
         var10001 = false;
         return null;
      }
   }

   public static void initCause(Throwable var0, Throwable var1) {
      Method var2 = INIT_CAUSE_METHOD;
      if (var2 != null) {
         try {
            var2.invoke(var0, var1);
         } catch (Exception var3) {
         }
      }

   }
}

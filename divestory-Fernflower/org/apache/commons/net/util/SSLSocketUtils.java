package org.apache.commons.net.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.net.ssl.SSLSocket;

public class SSLSocketUtils {
   private SSLSocketUtils() {
   }

   public static boolean enableEndpointNameVerification(SSLSocket var0) {
      boolean var10001;
      Method var2;
      Method var3;
      Method var7;
      try {
         Class var1 = Class.forName("javax.net.ssl.SSLParameters");
         var2 = var1.getDeclaredMethod("setEndpointIdentificationAlgorithm", String.class);
         var3 = SSLSocket.class.getDeclaredMethod("getSSLParameters");
         var7 = SSLSocket.class.getDeclaredMethod("setSSLParameters", var1);
      } catch (ClassNotFoundException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | SecurityException var6) {
         var10001 = false;
         return false;
      }

      if (var2 != null && var3 != null && var7 != null) {
         Object var8;
         try {
            var8 = var3.invoke(var0);
         } catch (ClassNotFoundException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | SecurityException var5) {
            var10001 = false;
            return false;
         }

         if (var8 != null) {
            try {
               var2.invoke(var8, "HTTPS");
               var7.invoke(var0, var8);
               return true;
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | SecurityException var4) {
               var10001 = false;
            }
         }
      }

      return false;
   }
}

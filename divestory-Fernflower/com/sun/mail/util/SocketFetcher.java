package com.sun.mail.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SocketFetcher {
   private SocketFetcher() {
   }

   private static void configureSSLSocket(Socket var0, Properties var1, String var2) {
      if (var0 instanceof SSLSocket) {
         SSLSocket var4 = (SSLSocket)var0;
         StringBuilder var3 = new StringBuilder(String.valueOf(var2));
         var3.append(".ssl.protocols");
         String var7 = var1.getProperty(var3.toString(), (String)null);
         if (var7 != null) {
            var4.setEnabledProtocols(stringArray(var7));
         } else {
            var4.setEnabledProtocols(new String[]{"TLSv1"});
         }

         StringBuilder var6 = new StringBuilder(String.valueOf(var2));
         var6.append(".ssl.ciphersuites");
         String var5 = var1.getProperty(var6.toString(), (String)null);
         if (var5 != null) {
            var4.setEnabledCipherSuites(stringArray(var5));
         }

      }
   }

   private static Socket createSocket(InetAddress var0, int var1, String var2, int var3, int var4, SocketFactory var5, boolean var6) throws IOException {
      Socket var7;
      if (var5 != null) {
         var7 = var5.createSocket();
      } else if (var6) {
         var7 = SSLSocketFactory.getDefault().createSocket();
      } else {
         var7 = new Socket();
      }

      if (var0 != null) {
         var7.bind(new InetSocketAddress(var0, var1));
      }

      if (var4 >= 0) {
         var7.connect(new InetSocketAddress(var2, var3), var4);
      } else {
         var7.connect(new InetSocketAddress(var2, var3));
      }

      return var7;
   }

   private static ClassLoader getContextClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            ClassLoader var1;
            try {
               var1 = Thread.currentThread().getContextClassLoader();
            } catch (SecurityException var2) {
               var1 = null;
            }

            return var1;
         }
      });
   }

   public static Socket getSocket(String var0, int var1, Properties var2, String var3) throws IOException {
      return getSocket(var0, var1, var2, var3, false);
   }

   public static Socket getSocket(String param0, int param1, Properties param2, String param3, boolean param4) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static SocketFactory getSocketFactory(String var0) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Class var1 = null;
      if (var0 != null && var0.length() != 0) {
         ClassLoader var2 = getContextClassLoader();
         Class var3 = var1;
         if (var2 != null) {
            try {
               var3 = var2.loadClass(var0);
            } catch (ClassNotFoundException var4) {
               var3 = var1;
            }
         }

         var1 = var3;
         if (var3 == null) {
            var1 = Class.forName(var0);
         }

         return (SocketFactory)var1.getMethod("getDefault").invoke(new Object());
      } else {
         return null;
      }
   }

   public static Socket startTLS(Socket var0) throws IOException {
      return startTLS(var0, new Properties(), "socket");
   }

   public static Socket startTLS(Socket var0, Properties var1, String var2) throws IOException {
      String var3 = var0.getInetAddress().getHostName();
      int var4 = var0.getPort();

      Exception var10000;
      label54: {
         SocketFactory var15;
         boolean var10001;
         try {
            StringBuilder var5 = new StringBuilder(String.valueOf(var2));
            var5.append(".socketFactory.class");
            var15 = getSocketFactory(var1.getProperty(var5.toString(), (String)null));
         } catch (Exception var9) {
            var10000 = var9;
            var10001 = false;
            break label54;
         }

         SSLSocketFactory var16;
         label56: {
            if (var15 != null) {
               try {
                  if (var15 instanceof SSLSocketFactory) {
                     var16 = (SSLSocketFactory)var15;
                     break label56;
                  }
               } catch (Exception var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label54;
               }
            }

            try {
               var16 = (SSLSocketFactory)SSLSocketFactory.getDefault();
            } catch (Exception var7) {
               var10000 = var7;
               var10001 = false;
               break label54;
            }
         }

         try {
            var0 = var16.createSocket(var0, var3, var4, true);
            configureSSLSocket(var0, var1, var2);
            return var0;
         } catch (Exception var6) {
            var10000 = var6;
            var10001 = false;
         }
      }

      Exception var11 = var10000;
      Exception var10 = var11;
      if (var11 instanceof InvocationTargetException) {
         Throwable var14 = ((InvocationTargetException)var11).getTargetException();
         var10 = var11;
         if (var14 instanceof Exception) {
            var10 = (Exception)var14;
         }
      }

      if (var10 instanceof IOException) {
         throw (IOException)var10;
      } else {
         StringBuilder var12 = new StringBuilder("Exception in startTLS: host ");
         var12.append(var3);
         var12.append(", port ");
         var12.append(var4);
         var12.append("; Exception: ");
         var12.append(var10);
         IOException var13 = new IOException(var12.toString());
         var13.initCause(var10);
         throw var13;
      }
   }

   private static String[] stringArray(String var0) {
      StringTokenizer var2 = new StringTokenizer(var0);
      ArrayList var1 = new ArrayList();

      while(var2.hasMoreTokens()) {
         var1.add(var2.nextToken());
      }

      return (String[])var1.toArray(new String[var1.size()]);
   }
}

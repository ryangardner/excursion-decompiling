package org.apache.http.client.params;

import org.apache.http.params.HttpParams;

public class HttpClientParams {
   private HttpClientParams() {
   }

   public static String getCookiePolicy(HttpParams var0) {
      if (var0 != null) {
         String var1 = (String)var0.getParameter("http.protocol.cookie-policy");
         String var2 = var1;
         if (var1 == null) {
            var2 = "best-match";
         }

         return var2;
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static boolean isAuthenticating(HttpParams var0) {
      if (var0 != null) {
         return var0.getBooleanParameter("http.protocol.handle-authentication", true);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static boolean isRedirecting(HttpParams var0) {
      if (var0 != null) {
         return var0.getBooleanParameter("http.protocol.handle-redirects", true);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setAuthenticating(HttpParams var0, boolean var1) {
      if (var0 != null) {
         var0.setBooleanParameter("http.protocol.handle-authentication", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setCookiePolicy(HttpParams var0, String var1) {
      if (var0 != null) {
         var0.setParameter("http.protocol.cookie-policy", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setRedirecting(HttpParams var0, boolean var1) {
      if (var0 != null) {
         var0.setBooleanParameter("http.protocol.handle-redirects", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }
}

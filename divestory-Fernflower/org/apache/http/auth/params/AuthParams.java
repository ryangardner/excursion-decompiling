package org.apache.http.auth.params;

import org.apache.http.params.HttpParams;

public final class AuthParams {
   private AuthParams() {
   }

   public static String getCredentialCharset(HttpParams var0) {
      if (var0 != null) {
         String var1 = (String)var0.getParameter("http.auth.credential-charset");
         String var2 = var1;
         if (var1 == null) {
            var2 = "US-ASCII";
         }

         return var2;
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setCredentialCharset(HttpParams var0, String var1) {
      if (var0 != null) {
         var0.setParameter("http.auth.credential-charset", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }
}

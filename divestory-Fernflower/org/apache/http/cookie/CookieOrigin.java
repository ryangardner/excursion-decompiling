package org.apache.http.cookie;

import java.util.Locale;

public final class CookieOrigin {
   private final String host;
   private final String path;
   private final int port;
   private final boolean secure;

   public CookieOrigin(String var1, int var2, String var3, boolean var4) {
      if (var1 != null) {
         if (var1.trim().length() != 0) {
            if (var2 >= 0) {
               if (var3 != null) {
                  this.host = var1.toLowerCase(Locale.ENGLISH);
                  this.port = var2;
                  if (var3.trim().length() != 0) {
                     this.path = var3;
                  } else {
                     this.path = "/";
                  }

                  this.secure = var4;
               } else {
                  throw new IllegalArgumentException("Path of origin may not be null.");
               }
            } else {
               StringBuilder var5 = new StringBuilder();
               var5.append("Invalid port: ");
               var5.append(var2);
               throw new IllegalArgumentException(var5.toString());
            }
         } else {
            throw new IllegalArgumentException("Host of origin may not be blank");
         }
      } else {
         throw new IllegalArgumentException("Host of origin may not be null");
      }
   }

   public String getHost() {
      return this.host;
   }

   public String getPath() {
      return this.path;
   }

   public int getPort() {
      return this.port;
   }

   public boolean isSecure() {
      return this.secure;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append('[');
      if (this.secure) {
         var1.append("(secure)");
      }

      var1.append(this.host);
      var1.append(':');
      var1.append(Integer.toString(this.port));
      var1.append(this.path);
      var1.append(']');
      return var1.toString();
   }
}

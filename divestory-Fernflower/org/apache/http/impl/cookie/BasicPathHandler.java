package org.apache.http.impl.cookie;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class BasicPathHandler implements CookieAttributeHandler {
   public boolean match(Cookie var1, CookieOrigin var2) {
      if (var1 != null) {
         if (var2 != null) {
            String var3 = var2.getPath();
            String var9 = var1.getPath();
            String var8 = var9;
            if (var9 == null) {
               var8 = "/";
            }

            int var4 = var8.length();
            boolean var5 = false;
            var9 = var8;
            if (var4 > 1) {
               var9 = var8;
               if (var8.endsWith("/")) {
                  var9 = var8.substring(0, var8.length() - 1);
               }
            }

            boolean var6 = var3.startsWith(var9);
            boolean var7 = var6;
            if (var6) {
               var7 = var6;
               if (var3.length() != var9.length()) {
                  var7 = var6;
                  if (!var9.endsWith("/")) {
                     var7 = var5;
                     if (var3.charAt(var9.length()) == '/') {
                        var7 = true;
                     }
                  }
               }
            }

            return var7;
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }

   public void parse(SetCookie var1, String var2) throws MalformedCookieException {
      if (var1 == null) {
         throw new IllegalArgumentException("Cookie may not be null");
      } else {
         String var3;
         label14: {
            if (var2 != null) {
               var3 = var2;
               if (var2.trim().length() != 0) {
                  break label14;
               }
            }

            var3 = "/";
         }

         var1.setPath(var3);
      }
   }

   public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
      if (!this.match(var1, var2)) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Illegal path attribute \"");
         var3.append(var1.getPath());
         var3.append("\". Path of origin: \"");
         var3.append(var2.getPath());
         var3.append("\"");
         throw new CookieRestrictionViolationException(var3.toString());
      }
   }
}

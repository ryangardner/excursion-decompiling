package org.apache.http.impl.cookie;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class BasicDomainHandler implements CookieAttributeHandler {
   public boolean match(Cookie var1, CookieOrigin var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cookie may not be null");
      } else if (var2 != null) {
         String var3 = var2.getHost();
         String var7 = var1.getDomain();
         boolean var4 = false;
         if (var7 == null) {
            return false;
         } else if (var3.equals(var7)) {
            return true;
         } else {
            String var5 = var7;
            if (!var7.startsWith(".")) {
               StringBuilder var6 = new StringBuilder();
               var6.append('.');
               var6.append(var7);
               var5 = var6.toString();
            }

            if (var3.endsWith(var5) || var3.equals(var5.substring(1))) {
               var4 = true;
            }

            return var4;
         }
      } else {
         throw new IllegalArgumentException("Cookie origin may not be null");
      }
   }

   public void parse(SetCookie var1, String var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
            if (var2.trim().length() != 0) {
               var1.setDomain(var2);
            } else {
               throw new MalformedCookieException("Blank value for domain attribute");
            }
         } else {
            throw new MalformedCookieException("Missing value for domain attribute");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }

   public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
            String var3 = var2.getHost();
            String var5 = var1.getDomain();
            if (var5 != null) {
               if (var3.contains(".")) {
                  if (!var3.endsWith(var5)) {
                     String var4 = var5;
                     if (var5.startsWith(".")) {
                        var4 = var5.substring(1, var5.length());
                     }

                     if (!var3.equals(var4)) {
                        StringBuilder var7 = new StringBuilder();
                        var7.append("Illegal domain attribute \"");
                        var7.append(var4);
                        var7.append("\". Domain of origin: \"");
                        var7.append(var3);
                        var7.append("\"");
                        throw new CookieRestrictionViolationException(var7.toString());
                     }
                  }
               } else if (!var3.equals(var5)) {
                  StringBuilder var6 = new StringBuilder();
                  var6.append("Illegal domain attribute \"");
                  var6.append(var5);
                  var6.append("\". Domain of origin: \"");
                  var6.append(var3);
                  var6.append("\"");
                  throw new CookieRestrictionViolationException(var6.toString());
               }

            } else {
               throw new CookieRestrictionViolationException("Cookie domain may not be null");
            }
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }
}

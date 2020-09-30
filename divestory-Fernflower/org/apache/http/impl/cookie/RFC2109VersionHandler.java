package org.apache.http.impl.cookie;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class RFC2109VersionHandler extends AbstractCookieAttributeHandler {
   public void parse(SetCookie var1, String var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
            if (var2.trim().length() != 0) {
               try {
                  var1.setVersion(Integer.parseInt(var2));
               } catch (NumberFormatException var3) {
                  StringBuilder var4 = new StringBuilder();
                  var4.append("Invalid version: ");
                  var4.append(var3.getMessage());
                  throw new MalformedCookieException(var4.toString());
               }
            } else {
               throw new MalformedCookieException("Blank value for version attribute");
            }
         } else {
            throw new MalformedCookieException("Missing value for version attribute");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }

   public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var1.getVersion() < 0) {
            throw new CookieRestrictionViolationException("Cookie version may not be negative");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }
}

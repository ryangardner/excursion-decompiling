package org.apache.http.impl.cookie;

import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.cookie.SetCookie2;

public class RFC2965VersionAttributeHandler implements CookieAttributeHandler {
   public boolean match(Cookie var1, CookieOrigin var2) {
      return true;
   }

   public void parse(SetCookie var1, String var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
            int var3;
            try {
               var3 = Integer.parseInt(var2);
            } catch (NumberFormatException var4) {
               var3 = -1;
            }

            if (var3 >= 0) {
               var1.setVersion(var3);
            } else {
               throw new MalformedCookieException("Invalid cookie version.");
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
         if (var1 instanceof SetCookie2 && var1 instanceof ClientCookie && !((ClientCookie)var1).containsAttribute("version")) {
            throw new CookieRestrictionViolationException("Violates RFC 2965. Version attribute is required.");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }
}

package org.apache.http.impl.cookie;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class BasicSecureHandler extends AbstractCookieAttributeHandler {
   public boolean match(Cookie var1, CookieOrigin var2) {
      if (var1 != null) {
         if (var2 == null) {
            throw new IllegalArgumentException("Cookie origin may not be null");
         } else {
            boolean var3;
            if (var1.isSecure() && !var2.isSecure()) {
               var3 = false;
            } else {
               var3 = true;
            }

            return var3;
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }

   public void parse(SetCookie var1, String var2) throws MalformedCookieException {
      if (var1 != null) {
         var1.setSecure(true);
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }
}

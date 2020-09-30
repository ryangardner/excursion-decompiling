package org.apache.http.impl.cookie;

import java.util.Date;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class BasicMaxAgeHandler extends AbstractCookieAttributeHandler {
   public void parse(SetCookie var1, String var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
            int var3;
            StringBuilder var5;
            try {
               var3 = Integer.parseInt(var2);
            } catch (NumberFormatException var4) {
               var5 = new StringBuilder();
               var5.append("Invalid max-age attribute: ");
               var5.append(var2);
               throw new MalformedCookieException(var5.toString());
            }

            if (var3 >= 0) {
               var1.setExpiryDate(new Date(System.currentTimeMillis() + (long)var3 * 1000L));
            } else {
               var5 = new StringBuilder();
               var5.append("Negative max-age attribute: ");
               var5.append(var2);
               throw new MalformedCookieException(var5.toString());
            }
         } else {
            throw new MalformedCookieException("Missing value for max-age attribute");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }
}

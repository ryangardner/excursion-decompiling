package org.apache.http.impl.cookie;

import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class BasicExpiresHandler extends AbstractCookieAttributeHandler {
   private final String[] datepatterns;

   public BasicExpiresHandler(String[] var1) {
      if (var1 != null) {
         this.datepatterns = var1;
      } else {
         throw new IllegalArgumentException("Array of date patterns may not be null");
      }
   }

   public void parse(SetCookie var1, String var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
            try {
               var1.setExpiryDate(DateUtils.parseDate(var2, this.datepatterns));
            } catch (DateParseException var3) {
               StringBuilder var4 = new StringBuilder();
               var4.append("Unable to parse expires attribute: ");
               var4.append(var2);
               throw new MalformedCookieException(var4.toString());
            }
         } else {
            throw new MalformedCookieException("Missing value for expires attribute");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }
}

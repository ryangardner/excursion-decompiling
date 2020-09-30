package org.apache.http.impl.cookie;

import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;

public class NetscapeDomainHandler extends BasicDomainHandler {
   private static boolean isSpecialDomain(String var0) {
      var0 = var0.toUpperCase(Locale.ENGLISH);
      boolean var1;
      if (!var0.endsWith(".COM") && !var0.endsWith(".EDU") && !var0.endsWith(".NET") && !var0.endsWith(".GOV") && !var0.endsWith(".MIL") && !var0.endsWith(".ORG") && !var0.endsWith(".INT")) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean match(Cookie var1, CookieOrigin var2) {
      if (var1 != null) {
         if (var2 != null) {
            String var4 = var2.getHost();
            String var3 = var1.getDomain();
            return var3 == null ? false : var4.endsWith(var3);
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }

   public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
      super.validate(var1, var2);
      String var5 = var2.getHost();
      String var4 = var1.getDomain();
      if (var5.contains(".")) {
         int var3 = (new StringTokenizer(var4, ".")).countTokens();
         StringBuilder var6;
         if (isSpecialDomain(var4)) {
            if (var3 < 2) {
               var6 = new StringBuilder();
               var6.append("Domain attribute \"");
               var6.append(var4);
               var6.append("\" violates the Netscape cookie specification for ");
               var6.append("special domains");
               throw new CookieRestrictionViolationException(var6.toString());
            }
         } else if (var3 < 3) {
            var6 = new StringBuilder();
            var6.append("Domain attribute \"");
            var6.append(var4);
            var6.append("\" violates the Netscape cookie specification");
            throw new CookieRestrictionViolationException(var6.toString());
         }
      }

   }
}

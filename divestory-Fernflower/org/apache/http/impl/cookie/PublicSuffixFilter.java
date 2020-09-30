package org.apache.http.impl.cookie;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.client.utils.Punycode;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class PublicSuffixFilter implements CookieAttributeHandler {
   private Set<String> exceptions;
   private Set<String> suffixes;
   private final CookieAttributeHandler wrapped;

   public PublicSuffixFilter(CookieAttributeHandler var1) {
      this.wrapped = var1;
   }

   private boolean isForPublicSuffix(Cookie var1) {
      String var2 = var1.getDomain();
      String var4 = var2;
      if (var2.startsWith(".")) {
         var4 = var2.substring(1);
      }

      var4 = Punycode.toUnicode(var4);
      Set var5 = this.exceptions;
      if (var5 != null && var5.contains(var4)) {
         return false;
      } else if (this.suffixes == null) {
         return false;
      } else {
         while(!this.suffixes.contains(var4)) {
            var2 = var4;
            if (var4.startsWith("*.")) {
               var2 = var4.substring(2);
            }

            int var3 = var2.indexOf(46);
            if (var3 != -1) {
               StringBuilder var6 = new StringBuilder();
               var6.append("*");
               var6.append(var2.substring(var3));
               var2 = var6.toString();
               var4 = var2;
               if (var2.length() > 0) {
                  continue;
               }
            }

            return false;
         }

         return true;
      }
   }

   public boolean match(Cookie var1, CookieOrigin var2) {
      return this.isForPublicSuffix(var1) ? false : this.wrapped.match(var1, var2);
   }

   public void parse(SetCookie var1, String var2) throws MalformedCookieException {
      this.wrapped.parse(var1, var2);
   }

   public void setExceptions(Collection<String> var1) {
      this.exceptions = new HashSet(var1);
   }

   public void setPublicSuffixes(Collection<String> var1) {
      this.suffixes = new HashSet(var1);
   }

   public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
      this.wrapped.validate(var1, var2);
   }
}

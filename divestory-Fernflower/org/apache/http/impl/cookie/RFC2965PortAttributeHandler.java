package org.apache.http.impl.cookie;

import java.util.StringTokenizer;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.cookie.SetCookie2;

public class RFC2965PortAttributeHandler implements CookieAttributeHandler {
   private static int[] parsePortAttribute(String var0) throws MalformedCookieException {
      StringTokenizer var1 = new StringTokenizer(var0, ",");
      int[] var5 = new int[var1.countTokens()];
      int var2 = 0;

      NumberFormatException var10000;
      while(true) {
         boolean var10001;
         try {
            if (!var1.hasMoreTokens()) {
               return var5;
            }

            var5[var2] = Integer.parseInt(var1.nextToken().trim());
         } catch (NumberFormatException var4) {
            var10000 = var4;
            var10001 = false;
            break;
         }

         if (var5[var2] < 0) {
            try {
               MalformedCookieException var7 = new MalformedCookieException("Invalid Port attribute.");
               throw var7;
            } catch (NumberFormatException var3) {
               var10000 = var3;
               var10001 = false;
               break;
            }
         }

         ++var2;
      }

      NumberFormatException var6 = var10000;
      StringBuilder var8 = new StringBuilder();
      var8.append("Invalid Port attribute: ");
      var8.append(var6.getMessage());
      throw new MalformedCookieException(var8.toString());
   }

   private static boolean portMatch(int var0, int[] var1) {
      int var2 = var1.length;
      boolean var3 = false;
      int var4 = 0;

      boolean var5;
      while(true) {
         var5 = var3;
         if (var4 >= var2) {
            break;
         }

         if (var0 == var1[var4]) {
            var5 = true;
            break;
         }

         ++var4;
      }

      return var5;
   }

   public boolean match(Cookie var1, CookieOrigin var2) {
      if (var1 != null) {
         if (var2 != null) {
            int var3 = var2.getPort();
            if (var1 instanceof ClientCookie && ((ClientCookie)var1).containsAttribute("port")) {
               if (var1.getPorts() == null) {
                  return false;
               }

               if (!portMatch(var3, var1.getPorts())) {
                  return false;
               }
            }

            return true;
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }

   public void parse(SetCookie var1, String var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var1 instanceof SetCookie2) {
            SetCookie2 var3 = (SetCookie2)var1;
            if (var2 != null && var2.trim().length() > 0) {
               var3.setPorts(parsePortAttribute(var2));
            }
         }

      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }

   public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
            int var3 = var2.getPort();
            if (var1 instanceof ClientCookie && ((ClientCookie)var1).containsAttribute("port") && !portMatch(var3, var1.getPorts())) {
               throw new CookieRestrictionViolationException("Port attribute violates RFC 2965: Request port not found in cookie's port list.");
            }
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }
}

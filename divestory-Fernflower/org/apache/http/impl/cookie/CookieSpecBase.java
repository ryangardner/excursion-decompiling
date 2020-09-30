package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;

public abstract class CookieSpecBase extends AbstractCookieSpec {
   protected static String getDefaultDomain(CookieOrigin var0) {
      return var0.getHost();
   }

   protected static String getDefaultPath(CookieOrigin var0) {
      String var1 = var0.getPath();
      int var2 = var1.lastIndexOf(47);
      String var4 = var1;
      if (var2 >= 0) {
         int var3 = var2;
         if (var2 == 0) {
            var3 = 1;
         }

         var4 = var1.substring(0, var3);
      }

      return var4;
   }

   public boolean match(Cookie var1, CookieOrigin var2) {
      if (var1 != null) {
         if (var2 != null) {
            Iterator var3 = this.getAttribHandlers().iterator();

            do {
               if (!var3.hasNext()) {
                  return true;
               }
            } while(((CookieAttributeHandler)var3.next()).match(var1, var2));

            return false;
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }

   protected List<Cookie> parse(HeaderElement[] var1, CookieOrigin var2) throws MalformedCookieException {
      ArrayList var3 = new ArrayList(var1.length);
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         HeaderElement var6 = var1[var5];
         String var7 = var6.getName();
         String var8 = var6.getValue();
         if (var7 == null || var7.length() == 0) {
            throw new MalformedCookieException("Cookie name may not be empty");
         }

         BasicClientCookie var14 = new BasicClientCookie(var7, var8);
         var14.setPath(getDefaultPath(var2));
         var14.setDomain(getDefaultDomain(var2));
         NameValuePair[] var12 = var6.getParameters();

         for(int var9 = var12.length - 1; var9 >= 0; --var9) {
            NameValuePair var13 = var12[var9];
            String var10 = var13.getName().toLowerCase(Locale.ENGLISH);
            var14.setAttribute(var10, var13.getValue());
            CookieAttributeHandler var11 = this.findAttribHandler(var10);
            if (var11 != null) {
               var11.parse(var14, var13.getValue());
            }
         }

         var3.add(var14);
      }

      return var3;
   }

   public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
      if (var1 == null) {
         throw new IllegalArgumentException("Cookie may not be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("Cookie origin may not be null");
      } else {
         Iterator var3 = this.getAttribHandlers().iterator();

         while(var3.hasNext()) {
            ((CookieAttributeHandler)var3.next()).validate(var1, var2);
         }

      }
   }
}

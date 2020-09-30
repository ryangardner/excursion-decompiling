package org.apache.http.impl.cookie;

import java.util.Locale;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class RFC2109DomainHandler implements CookieAttributeHandler {
   public boolean match(Cookie var1, CookieOrigin var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cookie may not be null");
      } else if (var2 != null) {
         String var6 = var2.getHost();
         String var5 = var1.getDomain();
         boolean var3 = false;
         if (var5 == null) {
            return false;
         } else {
            boolean var4;
            if (!var6.equals(var5)) {
               var4 = var3;
               if (!var5.startsWith(".")) {
                  return var4;
               }

               var4 = var3;
               if (!var6.endsWith(var5)) {
                  return var4;
               }
            }

            var4 = true;
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
      if (var1 == null) {
         throw new IllegalArgumentException("Cookie may not be null");
      } else if (var2 != null) {
         String var6 = var2.getHost();
         String var5 = var1.getDomain();
         if (var5 == null) {
            throw new CookieRestrictionViolationException("Cookie domain may not be null");
         } else {
            if (!var5.equals(var6)) {
               StringBuilder var4;
               if (var5.indexOf(46) == -1) {
                  var4 = new StringBuilder();
                  var4.append("Domain attribute \"");
                  var4.append(var5);
                  var4.append("\" does not match the host \"");
                  var4.append(var6);
                  var4.append("\"");
                  throw new CookieRestrictionViolationException(var4.toString());
               }

               StringBuilder var7;
               if (!var5.startsWith(".")) {
                  var7 = new StringBuilder();
                  var7.append("Domain attribute \"");
                  var7.append(var5);
                  var7.append("\" violates RFC 2109: domain must start with a dot");
                  throw new CookieRestrictionViolationException(var7.toString());
               }

               int var3 = var5.indexOf(46, 1);
               if (var3 < 0 || var3 == var5.length() - 1) {
                  var7 = new StringBuilder();
                  var7.append("Domain attribute \"");
                  var7.append(var5);
                  var7.append("\" violates RFC 2109: domain must contain an embedded dot");
                  throw new CookieRestrictionViolationException(var7.toString());
               }

               var6 = var6.toLowerCase(Locale.ENGLISH);
               if (!var6.endsWith(var5)) {
                  var4 = new StringBuilder();
                  var4.append("Illegal domain attribute \"");
                  var4.append(var5);
                  var4.append("\". Domain of origin: \"");
                  var4.append(var6);
                  var4.append("\"");
                  throw new CookieRestrictionViolationException(var4.toString());
               }

               if (var6.substring(0, var6.length() - var5.length()).indexOf(46) != -1) {
                  var7 = new StringBuilder();
                  var7.append("Domain attribute \"");
                  var7.append(var5);
                  var7.append("\" violates RFC 2109: host minus domain may not contain any dots");
                  throw new CookieRestrictionViolationException(var7.toString());
               }
            }

         }
      } else {
         throw new IllegalArgumentException("Cookie origin may not be null");
      }
   }
}

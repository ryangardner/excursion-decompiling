package org.apache.http.impl.cookie;

import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class RFC2965DomainAttributeHandler implements CookieAttributeHandler {
   public boolean domainMatch(String var1, String var2) {
      boolean var3;
      if (var1.equals(var2) || var2.startsWith(".") && var1.endsWith(var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean match(Cookie var1, CookieOrigin var2) {
      if (var1 != null) {
         if (var2 != null) {
            String var6 = var2.getHost().toLowerCase(Locale.ENGLISH);
            String var5 = var1.getDomain();
            boolean var3 = this.domainMatch(var6, var5);
            boolean var4 = false;
            if (!var3) {
               return false;
            } else {
               if (var6.substring(0, var6.length() - var5.length()).indexOf(46) == -1) {
                  var4 = true;
               }

               return var4;
            }
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }

   public void parse(SetCookie var1, String var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
            if (var2.trim().length() != 0) {
               String var3 = var2.toLowerCase(Locale.ENGLISH);
               var2 = var3;
               if (!var3.startsWith(".")) {
                  StringBuilder var4 = new StringBuilder();
                  var4.append('.');
                  var4.append(var3);
                  var2 = var4.toString();
               }

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
         String var5 = var2.getHost().toLowerCase(Locale.ENGLISH);
         if (var1.getDomain() == null) {
            throw new CookieRestrictionViolationException("Invalid cookie state: domain not specified");
         } else {
            String var3 = var1.getDomain().toLowerCase(Locale.ENGLISH);
            if (var1 instanceof ClientCookie && ((ClientCookie)var1).containsAttribute("domain")) {
               StringBuilder var6;
               if (!var3.startsWith(".")) {
                  var6 = new StringBuilder();
                  var6.append("Domain attribute \"");
                  var6.append(var1.getDomain());
                  var6.append("\" violates RFC 2109: domain must start with a dot");
                  throw new CookieRestrictionViolationException(var6.toString());
               }

               int var4 = var3.indexOf(46, 1);
               if ((var4 < 0 || var4 == var3.length() - 1) && !var3.equals(".local")) {
                  var6 = new StringBuilder();
                  var6.append("Domain attribute \"");
                  var6.append(var1.getDomain());
                  var6.append("\" violates RFC 2965: the value contains no embedded dots ");
                  var6.append("and the value is not .local");
                  throw new CookieRestrictionViolationException(var6.toString());
               }

               if (!this.domainMatch(var5, var3)) {
                  var6 = new StringBuilder();
                  var6.append("Domain attribute \"");
                  var6.append(var1.getDomain());
                  var6.append("\" violates RFC 2965: effective host name does not ");
                  var6.append("domain-match domain attribute.");
                  throw new CookieRestrictionViolationException(var6.toString());
               }

               if (var5.substring(0, var5.length() - var3.length()).indexOf(46) != -1) {
                  var6 = new StringBuilder();
                  var6.append("Domain attribute \"");
                  var6.append(var1.getDomain());
                  var6.append("\" violates RFC 2965: ");
                  var6.append("effective host minus domain may not contain any dots");
                  throw new CookieRestrictionViolationException(var6.toString());
               }
            } else if (!var1.getDomain().equals(var5)) {
               StringBuilder var7 = new StringBuilder();
               var7.append("Illegal domain attribute: \"");
               var7.append(var1.getDomain());
               var7.append("\".");
               var7.append("Domain of origin: \"");
               var7.append(var5);
               var7.append("\"");
               throw new CookieRestrictionViolationException(var7.toString());
            }

         }
      } else {
         throw new IllegalArgumentException("Cookie origin may not be null");
      }
   }
}

package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookiePathComparator;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;

public class RFC2109Spec extends CookieSpecBase {
   private static final String[] DATE_PATTERNS = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy"};
   private static final CookiePathComparator PATH_COMPARATOR = new CookiePathComparator();
   private final String[] datepatterns;
   private final boolean oneHeader;

   public RFC2109Spec() {
      this((String[])null, false);
   }

   public RFC2109Spec(String[] var1, boolean var2) {
      if (var1 != null) {
         this.datepatterns = (String[])var1.clone();
      } else {
         this.datepatterns = DATE_PATTERNS;
      }

      this.oneHeader = var2;
      this.registerAttribHandler("version", new RFC2109VersionHandler());
      this.registerAttribHandler("path", new BasicPathHandler());
      this.registerAttribHandler("domain", new RFC2109DomainHandler());
      this.registerAttribHandler("max-age", new BasicMaxAgeHandler());
      this.registerAttribHandler("secure", new BasicSecureHandler());
      this.registerAttribHandler("comment", new BasicCommentHandler());
      this.registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
   }

   private List<Header> doFormatManyHeaders(List<Cookie> var1) {
      ArrayList var2 = new ArrayList(var1.size());
      Iterator var6 = var1.iterator();

      while(var6.hasNext()) {
         Cookie var3 = (Cookie)var6.next();
         int var4 = var3.getVersion();
         CharArrayBuffer var5 = new CharArrayBuffer(40);
         var5.append("Cookie: ");
         var5.append("$Version=");
         var5.append(Integer.toString(var4));
         var5.append("; ");
         this.formatCookieAsVer(var5, var3, var4);
         var2.add(new BufferedHeader(var5));
      }

      return var2;
   }

   private List<Header> doFormatOneHeader(List<Cookie> var1) {
      Iterator var2 = var1.iterator();
      int var3 = Integer.MAX_VALUE;

      Cookie var4;
      while(var2.hasNext()) {
         var4 = (Cookie)var2.next();
         if (var4.getVersion() < var3) {
            var3 = var4.getVersion();
         }
      }

      CharArrayBuffer var7 = new CharArrayBuffer(var1.size() * 40);
      var7.append("Cookie");
      var7.append(": ");
      var7.append("$Version=");
      var7.append(Integer.toString(var3));
      Iterator var5 = var1.iterator();

      while(var5.hasNext()) {
         var4 = (Cookie)var5.next();
         var7.append("; ");
         this.formatCookieAsVer(var7, var4, var3);
      }

      ArrayList var6 = new ArrayList(1);
      var6.add(new BufferedHeader(var7));
      return var6;
   }

   protected void formatCookieAsVer(CharArrayBuffer var1, Cookie var2, int var3) {
      this.formatParamAsVer(var1, var2.getName(), var2.getValue(), var3);
      if (var2.getPath() != null && var2 instanceof ClientCookie && ((ClientCookie)var2).containsAttribute("path")) {
         var1.append("; ");
         this.formatParamAsVer(var1, "$Path", var2.getPath(), var3);
      }

      if (var2.getDomain() != null && var2 instanceof ClientCookie && ((ClientCookie)var2).containsAttribute("domain")) {
         var1.append("; ");
         this.formatParamAsVer(var1, "$Domain", var2.getDomain(), var3);
      }

   }

   public List<Header> formatCookies(List<Cookie> var1) {
      if (var1 != null) {
         if (!var1.isEmpty()) {
            Object var2 = var1;
            if (var1.size() > 1) {
               var2 = new ArrayList(var1);
               Collections.sort((List)var2, PATH_COMPARATOR);
            }

            return this.oneHeader ? this.doFormatOneHeader((List)var2) : this.doFormatManyHeaders((List)var2);
         } else {
            throw new IllegalArgumentException("List of cookies may not be empty");
         }
      } else {
         throw new IllegalArgumentException("List of cookies may not be null");
      }
   }

   protected void formatParamAsVer(CharArrayBuffer var1, String var2, String var3, int var4) {
      var1.append(var2);
      var1.append("=");
      if (var3 != null) {
         if (var4 > 0) {
            var1.append('"');
            var1.append(var3);
            var1.append('"');
         } else {
            var1.append(var3);
         }
      }

   }

   public int getVersion() {
      return 1;
   }

   public Header getVersionHeader() {
      return null;
   }

   public List<Cookie> parse(Header var1, CookieOrigin var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
            if (var1.getName().equalsIgnoreCase("Set-Cookie")) {
               return this.parse(var1.getElements(), var2);
            } else {
               StringBuilder var3 = new StringBuilder();
               var3.append("Unrecognized cookie header '");
               var3.append(var1.toString());
               var3.append("'");
               throw new MalformedCookieException(var3.toString());
            }
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Header may not be null");
      }
   }

   public String toString() {
      return "rfc2109";
   }

   public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
      if (var1 != null) {
         String var3 = var1.getName();
         if (var3.indexOf(32) == -1) {
            if (!var3.startsWith("$")) {
               super.validate(var1, var2);
            } else {
               throw new CookieRestrictionViolationException("Cookie name may not start with $");
            }
         } else {
            throw new CookieRestrictionViolationException("Cookie name may not contain blanks");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }
}

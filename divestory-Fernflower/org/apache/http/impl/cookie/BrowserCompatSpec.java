package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.message.BufferedHeader;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public class BrowserCompatSpec extends CookieSpecBase {
   @Deprecated
   protected static final String[] DATE_PATTERNS = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"};
   private static final String[] DEFAULT_DATE_PATTERNS = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"};
   private final String[] datepatterns;

   public BrowserCompatSpec() {
      this((String[])null);
   }

   public BrowserCompatSpec(String[] var1) {
      if (var1 != null) {
         this.datepatterns = (String[])var1.clone();
      } else {
         this.datepatterns = DEFAULT_DATE_PATTERNS;
      }

      this.registerAttribHandler("path", new BasicPathHandler());
      this.registerAttribHandler("domain", new BasicDomainHandler());
      this.registerAttribHandler("max-age", new BasicMaxAgeHandler());
      this.registerAttribHandler("secure", new BasicSecureHandler());
      this.registerAttribHandler("comment", new BasicCommentHandler());
      this.registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
   }

   public List<Header> formatCookies(List<Cookie> var1) {
      if (var1 != null) {
         if (!var1.isEmpty()) {
            CharArrayBuffer var2 = new CharArrayBuffer(var1.size() * 20);
            var2.append("Cookie");
            var2.append(": ");

            for(int var3 = 0; var3 < var1.size(); ++var3) {
               Cookie var4 = (Cookie)var1.get(var3);
               if (var3 > 0) {
                  var2.append("; ");
               }

               var2.append(var4.getName());
               var2.append("=");
               String var6 = var4.getValue();
               if (var6 != null) {
                  var2.append(var6);
               }
            }

            ArrayList var5 = new ArrayList(1);
            var5.add(new BufferedHeader(var2));
            return var5;
         } else {
            throw new IllegalArgumentException("List of cookies may not be empty");
         }
      } else {
         throw new IllegalArgumentException("List of cookies may not be null");
      }
   }

   public int getVersion() {
      return 0;
   }

   public Header getVersionHeader() {
      return null;
   }

   public List<Cookie> parse(Header var1, CookieOrigin var2) throws MalformedCookieException {
      if (var1 == null) {
         throw new IllegalArgumentException("Header may not be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("Cookie origin may not be null");
      } else if (!var1.getName().equalsIgnoreCase("Set-Cookie")) {
         StringBuilder var12 = new StringBuilder();
         var12.append("Unrecognized cookie header '");
         var12.append(var1.toString());
         var12.append("'");
         throw new MalformedCookieException(var12.toString());
      } else {
         HeaderElement[] var3 = var1.getElements();
         int var4 = var3.length;
         int var5 = 0;
         boolean var6 = false;

         boolean var7;
         for(var7 = false; var5 < var4; ++var5) {
            HeaderElement var8 = var3[var5];
            if (var8.getParameterByName("version") != null) {
               var7 = true;
            }

            if (var8.getParameterByName("expires") != null) {
               var6 = true;
            }
         }

         if (var6 || !var7) {
            NetscapeDraftHeaderParser var14 = NetscapeDraftHeaderParser.DEFAULT;
            ParserCursor var10;
            CharArrayBuffer var13;
            if (var1 instanceof FormattedHeader) {
               FormattedHeader var9 = (FormattedHeader)var1;
               var13 = var9.getBuffer();
               var10 = new ParserCursor(var9.getValuePos(), var13.length());
            } else {
               String var11 = var1.getValue();
               if (var11 == null) {
                  throw new MalformedCookieException("Header value is null");
               }

               var13 = new CharArrayBuffer(var11.length());
               var13.append(var11);
               var10 = new ParserCursor(0, var13.length());
            }

            var3 = new HeaderElement[]{var14.parseHeader(var13, var10)};
         }

         return this.parse(var3, var2);
      }
   }

   public String toString() {
      return "compatibility";
   }
}

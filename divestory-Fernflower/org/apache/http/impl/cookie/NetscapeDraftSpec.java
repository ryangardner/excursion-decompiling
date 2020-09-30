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

public class NetscapeDraftSpec extends CookieSpecBase {
   protected static final String EXPIRES_PATTERN = "EEE, dd-MMM-yy HH:mm:ss z";
   private final String[] datepatterns;

   public NetscapeDraftSpec() {
      this((String[])null);
   }

   public NetscapeDraftSpec(String[] var1) {
      if (var1 != null) {
         this.datepatterns = (String[])var1.clone();
      } else {
         this.datepatterns = new String[]{"EEE, dd-MMM-yy HH:mm:ss z"};
      }

      this.registerAttribHandler("path", new BasicPathHandler());
      this.registerAttribHandler("domain", new NetscapeDomainHandler());
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
               String var6 = var4.getValue();
               if (var6 != null) {
                  var2.append("=");
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
      if (var1 != null) {
         if (var2 != null) {
            if (var1.getName().equalsIgnoreCase("Set-Cookie")) {
               NetscapeDraftHeaderParser var3 = NetscapeDraftHeaderParser.DEFAULT;
               CharArrayBuffer var4;
               ParserCursor var6;
               if (var1 instanceof FormattedHeader) {
                  FormattedHeader var5 = (FormattedHeader)var1;
                  var4 = var5.getBuffer();
                  var6 = new ParserCursor(var5.getValuePos(), var4.length());
               } else {
                  String var7 = var1.getValue();
                  if (var7 == null) {
                     throw new MalformedCookieException("Header value is null");
                  }

                  var4 = new CharArrayBuffer(var7.length());
                  var4.append(var7);
                  var6 = new ParserCursor(0, var4.length());
               }

               return this.parse(new HeaderElement[]{var3.parseHeader(var4, var6)}, var2);
            } else {
               StringBuilder var8 = new StringBuilder();
               var8.append("Unrecognized cookie header '");
               var8.append(var1.toString());
               var8.append("'");
               throw new MalformedCookieException(var8.toString());
            }
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Header may not be null");
      }
   }

   public String toString() {
      return "netscape";
   }
}

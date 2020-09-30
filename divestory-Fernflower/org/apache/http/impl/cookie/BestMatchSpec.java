package org.apache.http.impl.cookie;

import java.util.Iterator;
import java.util.List;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie2;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public class BestMatchSpec implements CookieSpec {
   private BrowserCompatSpec compat;
   private final String[] datepatterns;
   private RFC2109Spec obsoleteStrict;
   private final boolean oneHeader;
   private RFC2965Spec strict;

   public BestMatchSpec() {
      this((String[])null, false);
   }

   public BestMatchSpec(String[] var1, boolean var2) {
      if (var1 == null) {
         var1 = null;
      } else {
         var1 = (String[])var1.clone();
      }

      this.datepatterns = var1;
      this.oneHeader = var2;
   }

   private BrowserCompatSpec getCompat() {
      if (this.compat == null) {
         this.compat = new BrowserCompatSpec(this.datepatterns);
      }

      return this.compat;
   }

   private RFC2109Spec getObsoleteStrict() {
      if (this.obsoleteStrict == null) {
         this.obsoleteStrict = new RFC2109Spec(this.datepatterns, this.oneHeader);
      }

      return this.obsoleteStrict;
   }

   private RFC2965Spec getStrict() {
      if (this.strict == null) {
         this.strict = new RFC2965Spec(this.datepatterns, this.oneHeader);
      }

      return this.strict;
   }

   public List<Header> formatCookies(List<Cookie> var1) {
      if (var1 != null) {
         int var2 = Integer.MAX_VALUE;
         boolean var3 = true;
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            Cookie var5 = (Cookie)var4.next();
            boolean var6 = var3;
            if (!(var5 instanceof SetCookie2)) {
               var6 = false;
            }

            var3 = var6;
            if (var5.getVersion() < var2) {
               var2 = var5.getVersion();
               var3 = var6;
            }
         }

         if (var2 > 0) {
            if (var3) {
               return this.getStrict().formatCookies(var1);
            } else {
               return this.getObsoleteStrict().formatCookies(var1);
            }
         } else {
            return this.getCompat().formatCookies(var1);
         }
      } else {
         throw new IllegalArgumentException("List of cookies may not be null");
      }
   }

   public int getVersion() {
      return this.getStrict().getVersion();
   }

   public Header getVersionHeader() {
      return this.getStrict().getVersionHeader();
   }

   public boolean match(Cookie var1, CookieOrigin var2) {
      if (var1 != null) {
         if (var2 != null) {
            if (var1.getVersion() > 0) {
               return var1 instanceof SetCookie2 ? this.getStrict().match(var1, var2) : this.getObsoleteStrict().match(var1, var2);
            } else {
               return this.getCompat().match(var1, var2);
            }
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }

   public List<Cookie> parse(Header var1, CookieOrigin var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
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

            if (!var6 && var7) {
               if ("Set-Cookie2".equals(var1.getName())) {
                  return this.getStrict().parse(var3, var2);
               } else {
                  return this.getObsoleteStrict().parse(var3, var2);
               }
            } else {
               NetscapeDraftHeaderParser var13 = NetscapeDraftHeaderParser.DEFAULT;
               ParserCursor var10;
               CharArrayBuffer var14;
               if (var1 instanceof FormattedHeader) {
                  FormattedHeader var9 = (FormattedHeader)var1;
                  var14 = var9.getBuffer();
                  var10 = new ParserCursor(var9.getValuePos(), var14.length());
               } else {
                  String var11 = var1.getValue();
                  if (var11 == null) {
                     throw new MalformedCookieException("Header value is null");
                  }

                  var14 = new CharArrayBuffer(var11.length());
                  var14.append(var11);
                  var10 = new ParserCursor(0, var14.length());
               }

               HeaderElement var12 = var13.parseHeader(var14, var10);
               return this.getCompat().parse(new HeaderElement[]{var12}, var2);
            }
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Header may not be null");
      }
   }

   public String toString() {
      return "best-match";
   }

   public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
            if (var1.getVersion() > 0) {
               if (var1 instanceof SetCookie2) {
                  this.getStrict().validate(var1, var2);
               } else {
                  this.getObsoleteStrict().validate(var1, var2);
               }
            } else {
               this.getCompat().validate(var1, var2);
            }

         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }
}

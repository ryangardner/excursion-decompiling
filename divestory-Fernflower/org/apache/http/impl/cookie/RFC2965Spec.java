package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;

public class RFC2965Spec extends RFC2109Spec {
   public RFC2965Spec() {
      this((String[])null, false);
   }

   public RFC2965Spec(String[] var1, boolean var2) {
      super(var1, var2);
      this.registerAttribHandler("domain", new RFC2965DomainAttributeHandler());
      this.registerAttribHandler("port", new RFC2965PortAttributeHandler());
      this.registerAttribHandler("commenturl", new RFC2965CommentUrlAttributeHandler());
      this.registerAttribHandler("discard", new RFC2965DiscardAttributeHandler());
      this.registerAttribHandler("version", new RFC2965VersionAttributeHandler());
   }

   private static CookieOrigin adjustEffectiveHost(CookieOrigin var0) {
      String var1 = var0.getHost();
      boolean var2 = false;
      int var3 = 0;

      boolean var5;
      while(true) {
         if (var3 >= var1.length()) {
            var5 = true;
            break;
         }

         char var4 = var1.charAt(var3);
         var5 = var2;
         if (var4 == '.') {
            break;
         }

         if (var4 == ':') {
            var5 = var2;
            break;
         }

         ++var3;
      }

      if (var5) {
         StringBuilder var6 = new StringBuilder();
         var6.append(var1);
         var6.append(".local");
         return new CookieOrigin(var6.toString(), var0.getPort(), var0.getPath(), var0.isSecure());
      } else {
         return var0;
      }
   }

   private List<Cookie> createCookies(HeaderElement[] var1, CookieOrigin var2) throws MalformedCookieException {
      ArrayList var3 = new ArrayList(var1.length);
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         HeaderElement var6 = var1[var5];
         String var7 = var6.getName();
         String var8 = var6.getValue();
         if (var7 == null || var7.length() == 0) {
            throw new MalformedCookieException("Cookie name may not be empty");
         }

         BasicClientCookie2 var14 = new BasicClientCookie2(var7, var8);
         var14.setPath(getDefaultPath(var2));
         var14.setDomain(getDefaultDomain(var2));
         var14.setPorts(new int[]{var2.getPort()});
         NameValuePair[] var9 = var6.getParameters();
         HashMap var11 = new HashMap(var9.length);

         NameValuePair var13;
         for(int var10 = var9.length - 1; var10 >= 0; --var10) {
            var13 = var9[var10];
            var11.put(var13.getName().toLowerCase(Locale.ENGLISH), var13);
         }

         Iterator var12 = var11.entrySet().iterator();

         while(var12.hasNext()) {
            var13 = (NameValuePair)((Entry)var12.next()).getValue();
            String var15 = var13.getName().toLowerCase(Locale.ENGLISH);
            var14.setAttribute(var15, var13.getValue());
            CookieAttributeHandler var16 = this.findAttribHandler(var15);
            if (var16 != null) {
               var16.parse(var14, var13.getValue());
            }
         }

         var3.add(var14);
      }

      return var3;
   }

   protected void formatCookieAsVer(CharArrayBuffer var1, Cookie var2, int var3) {
      super.formatCookieAsVer(var1, var2, var3);
      if (var2 instanceof ClientCookie) {
         String var4 = ((ClientCookie)var2).getAttribute("port");
         if (var4 != null) {
            var1.append("; $Port");
            var1.append("=\"");
            if (var4.trim().length() > 0) {
               int[] var6 = var2.getPorts();
               if (var6 != null) {
                  var3 = 0;

                  for(int var5 = var6.length; var3 < var5; ++var3) {
                     if (var3 > 0) {
                        var1.append(",");
                     }

                     var1.append(Integer.toString(var6[var3]));
                  }
               }
            }

            var1.append("\"");
         }
      }

   }

   public int getVersion() {
      return 1;
   }

   public Header getVersionHeader() {
      CharArrayBuffer var1 = new CharArrayBuffer(40);
      var1.append("Cookie2");
      var1.append(": ");
      var1.append("$Version=");
      var1.append(Integer.toString(this.getVersion()));
      return new BufferedHeader(var1);
   }

   public boolean match(Cookie var1, CookieOrigin var2) {
      if (var1 != null) {
         if (var2 != null) {
            return super.match(var1, adjustEffectiveHost(var2));
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
            if (var1.getName().equalsIgnoreCase("Set-Cookie2")) {
               var2 = adjustEffectiveHost(var2);
               return this.createCookies(var1.getElements(), var2);
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

   protected List<Cookie> parse(HeaderElement[] var1, CookieOrigin var2) throws MalformedCookieException {
      return this.createCookies(var1, adjustEffectiveHost(var2));
   }

   public String toString() {
      return "rfc2965";
   }

   public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
      if (var1 != null) {
         if (var2 != null) {
            super.validate(var1, adjustEffectiveHost(var2));
         } else {
            throw new IllegalArgumentException("Cookie origin may not be null");
         }
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }
}

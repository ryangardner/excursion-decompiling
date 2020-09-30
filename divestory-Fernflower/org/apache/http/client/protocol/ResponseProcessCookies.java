package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.protocol.HttpContext;

public class ResponseProcessCookies implements HttpResponseInterceptor {
   private final Log log = LogFactory.getLog(this.getClass());

   private void processCookies(HeaderIterator var1, CookieSpec var2, CookieOrigin var3, CookieStore var4) {
      label50:
      while(var1.hasNext()) {
         Header var5 = var1.nextHeader();

         MalformedCookieException var10000;
         label45: {
            Iterator var6;
            boolean var10001;
            try {
               var6 = var2.parse(var5, var3).iterator();
            } catch (MalformedCookieException var14) {
               var10000 = var14;
               var10001 = false;
               break label45;
            }

            while(true) {
               Cookie var7;
               try {
                  if (!var6.hasNext()) {
                     continue label50;
                  }

                  var7 = (Cookie)var6.next();
               } catch (MalformedCookieException var13) {
                  var10000 = var13;
                  var10001 = false;
                  break;
               }

               try {
                  var2.validate(var7, var3);
                  var4.addCookie(var7);
                  if (this.log.isDebugEnabled()) {
                     Log var18 = this.log;
                     StringBuilder var19 = new StringBuilder();
                     var19.append("Cookie accepted: \"");
                     var19.append(var7);
                     var19.append("\". ");
                     var18.debug(var19.toString());
                  }
               } catch (MalformedCookieException var12) {
                  MalformedCookieException var8 = var12;

                  try {
                     if (this.log.isWarnEnabled()) {
                        Log var9 = this.log;
                        StringBuilder var10 = new StringBuilder();
                        var10.append("Cookie rejected: \"");
                        var10.append(var7);
                        var10.append("\". ");
                        var10.append(var8.getMessage());
                        var9.warn(var10.toString());
                     }
                  } catch (MalformedCookieException var11) {
                     var10000 = var11;
                     var10001 = false;
                     break;
                  }
               }
            }
         }

         MalformedCookieException var16 = var10000;
         if (this.log.isWarnEnabled()) {
            Log var15 = this.log;
            StringBuilder var17 = new StringBuilder();
            var17.append("Invalid cookie header: \"");
            var17.append(var5);
            var17.append("\". ");
            var17.append(var16.getMessage());
            var15.warn(var17.toString());
         }
      }

   }

   public void process(HttpResponse var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (var2 != null) {
            CookieSpec var3 = (CookieSpec)var2.getAttribute("http.cookie-spec");
            if (var3 == null) {
               this.log.debug("Cookie spec not specified in HTTP context");
            } else {
               CookieStore var4 = (CookieStore)var2.getAttribute("http.cookie-store");
               if (var4 == null) {
                  this.log.debug("Cookie store not specified in HTTP context");
               } else {
                  CookieOrigin var5 = (CookieOrigin)var2.getAttribute("http.cookie-origin");
                  if (var5 == null) {
                     this.log.debug("Cookie origin not specified in HTTP context");
                  } else {
                     this.processCookies(var1.headerIterator("Set-Cookie"), var3, var5, var4);
                     if (var3.getVersion() > 0) {
                        this.processCookies(var1.headerIterator("Set-Cookie2"), var3, var5, var4);
                     }

                  }
               }
            }
         } else {
            throw new IllegalArgumentException("HTTP context may not be null");
         }
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }
}

package org.apache.http.client.protocol;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.cookie.SetCookie2;
import org.apache.http.protocol.HttpContext;

public class RequestAddCookies implements HttpRequestInterceptor {
   private final Log log = LogFactory.getLog(this.getClass());

   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("HTTP request may not be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("HTTP context may not be null");
      } else if (!var1.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
         CookieStore var3 = (CookieStore)var2.getAttribute("http.cookie-store");
         if (var3 == null) {
            this.log.debug("Cookie store not specified in HTTP context");
         } else {
            CookieSpecRegistry var4 = (CookieSpecRegistry)var2.getAttribute("http.cookiespec-registry");
            if (var4 == null) {
               this.log.debug("CookieSpec registry not specified in HTTP context");
            } else {
               HttpHost var5 = (HttpHost)var2.getAttribute("http.target_host");
               if (var5 == null) {
                  this.log.debug("Target host not set in the context");
               } else {
                  HttpRoutedConnection var6 = (HttpRoutedConnection)var2.getAttribute("http.connection");
                  if (var6 == null) {
                     this.log.debug("HTTP connection not set in the context");
                  } else {
                     String var7 = HttpClientParams.getCookiePolicy(var1.getParams());
                     if (this.log.isDebugEnabled()) {
                        Log var8 = this.log;
                        StringBuilder var9 = new StringBuilder();
                        var9.append("CookieSpec selected: ");
                        var9.append(var7);
                        var8.debug(var9.toString());
                     }

                     URI var28;
                     if (var1 instanceof HttpUriRequest) {
                        var28 = ((HttpUriRequest)var1).getURI();
                     } else {
                        try {
                           var28 = new URI(var1.getRequestLine().getUri());
                        } catch (URISyntaxException var14) {
                           StringBuilder var15 = new StringBuilder();
                           var15.append("Invalid request URI: ");
                           var15.append(var1.getRequestLine().getUri());
                           throw new ProtocolException(var15.toString(), var14);
                        }
                     }

                     String var26 = var5.getHostName();
                     int var10 = var5.getPort();
                     boolean var11 = false;
                     int var12 = var10;
                     if (var10 < 0) {
                        if (var6.getRoute().getHopCount() == 1) {
                           var12 = var6.getRemotePort();
                        } else {
                           String var20 = var5.getSchemeName();
                           if (var20.equalsIgnoreCase("http")) {
                              var12 = 80;
                           } else if (var20.equalsIgnoreCase("https")) {
                              var12 = 443;
                           } else {
                              var12 = 0;
                           }
                        }
                     }

                     CookieOrigin var29 = new CookieOrigin(var26, var12, var28.getPath(), var6.isSecure());
                     CookieSpec var19 = var4.getCookieSpec(var7, var1.getParams());
                     ArrayList var22 = new ArrayList(var3.getCookies());
                     ArrayList var16 = new ArrayList();
                     Date var24 = new Date();
                     Iterator var21 = var22.iterator();

                     while(var21.hasNext()) {
                        Cookie var23 = (Cookie)var21.next();
                        Log var13;
                        StringBuilder var27;
                        if (!var23.isExpired(var24)) {
                           if (var19.match(var23, var29)) {
                              if (this.log.isDebugEnabled()) {
                                 var13 = this.log;
                                 var27 = new StringBuilder();
                                 var27.append("Cookie ");
                                 var27.append(var23);
                                 var27.append(" match ");
                                 var27.append(var29);
                                 var13.debug(var27.toString());
                              }

                              var16.add(var23);
                           }
                        } else if (this.log.isDebugEnabled()) {
                           var13 = this.log;
                           var27 = new StringBuilder();
                           var27.append("Cookie ");
                           var27.append(var23);
                           var27.append(" expired");
                           var13.debug(var27.toString());
                        }
                     }

                     Iterator var25;
                     if (!var16.isEmpty()) {
                        var25 = var19.formatCookies(var16).iterator();

                        while(var25.hasNext()) {
                           var1.addHeader((Header)var25.next());
                        }
                     }

                     var10 = var19.getVersion();
                     if (var10 > 0) {
                        var25 = var16.iterator();
                        boolean var30 = var11;

                        label97:
                        while(true) {
                           Cookie var17;
                           do {
                              if (!var25.hasNext()) {
                                 if (var30) {
                                    Header var18 = var19.getVersionHeader();
                                    if (var18 != null) {
                                       var1.addHeader(var18);
                                    }
                                 }
                                 break label97;
                              }

                              var17 = (Cookie)var25.next();
                           } while(var10 == var17.getVersion() && var17 instanceof SetCookie2);

                           var30 = true;
                        }
                     }

                     var2.setAttribute("http.cookie-spec", var19);
                     var2.setAttribute("http.cookie-origin", var29);
                  }
               }
            }
         }
      }
   }
}

package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.protocol.HttpContext;

public class RequestProxyAuthentication implements HttpRequestInterceptor {
   private final Log log = LogFactory.getLog(this.getClass());

   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (var2 == null) {
            throw new IllegalArgumentException("HTTP context may not be null");
         } else if (!var1.containsHeader("Proxy-Authorization")) {
            HttpRoutedConnection var3 = (HttpRoutedConnection)var2.getAttribute("http.connection");
            if (var3 == null) {
               this.log.debug("HTTP connection not set in the context");
            } else if (!var3.getRoute().isTunnelled()) {
               AuthState var12 = (AuthState)var2.getAttribute("http.auth.proxy-scope");
               if (var12 == null) {
                  this.log.debug("Proxy auth state not set in the context");
               } else {
                  AuthScheme var4 = var12.getAuthScheme();
                  if (var4 != null) {
                     Credentials var5 = var12.getCredentials();
                     if (var5 == null) {
                        this.log.debug("User credentials not available");
                     } else {
                        if (var12.getAuthScope() != null || !var4.isConnectionBased()) {
                           AuthenticationException var10000;
                           label77: {
                              Header var10;
                              boolean var10001;
                              label54: {
                                 try {
                                    if (var4 instanceof ContextAwareAuthScheme) {
                                       var10 = ((ContextAwareAuthScheme)var4).authenticate(var5, var1, var2);
                                       break label54;
                                    }
                                 } catch (AuthenticationException var8) {
                                    var10000 = var8;
                                    var10001 = false;
                                    break label77;
                                 }

                                 try {
                                    var10 = var4.authenticate(var5, var1);
                                 } catch (AuthenticationException var7) {
                                    var10000 = var7;
                                    var10001 = false;
                                    break label77;
                                 }
                              }

                              try {
                                 var1.addHeader(var10);
                                 return;
                              } catch (AuthenticationException var6) {
                                 var10000 = var6;
                                 var10001 = false;
                              }
                           }

                           AuthenticationException var9 = var10000;
                           if (this.log.isErrorEnabled()) {
                              Log var11 = this.log;
                              StringBuilder var13 = new StringBuilder();
                              var13.append("Proxy authentication error: ");
                              var13.append(var9.getMessage());
                              var11.error(var13.toString());
                           }
                        }

                     }
                  }
               }
            }
         }
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }
}

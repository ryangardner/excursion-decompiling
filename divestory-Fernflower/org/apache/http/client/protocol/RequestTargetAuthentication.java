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
import org.apache.http.protocol.HttpContext;

public class RequestTargetAuthentication implements HttpRequestInterceptor {
   private final Log log = LogFactory.getLog(this.getClass());

   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("HTTP request may not be null");
      } else if (var2 != null) {
         if (!var1.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            if (!var1.containsHeader("Authorization")) {
               AuthState var3 = (AuthState)var2.getAttribute("http.auth.target-scope");
               if (var3 == null) {
                  this.log.debug("Target auth state not set in the context");
               } else {
                  AuthScheme var4 = var3.getAuthScheme();
                  if (var4 != null) {
                     Credentials var5 = var3.getCredentials();
                     if (var5 == null) {
                        this.log.debug("User credentials not available");
                     } else {
                        if (var3.getAuthScope() != null || !var4.isConnectionBased()) {
                           AuthenticationException var10000;
                           label74: {
                              Header var10;
                              boolean var10001;
                              label52: {
                                 try {
                                    if (var4 instanceof ContextAwareAuthScheme) {
                                       var10 = ((ContextAwareAuthScheme)var4).authenticate(var5, var1, var2);
                                       break label52;
                                    }
                                 } catch (AuthenticationException var8) {
                                    var10000 = var8;
                                    var10001 = false;
                                    break label74;
                                 }

                                 try {
                                    var10 = var4.authenticate(var5, var1);
                                 } catch (AuthenticationException var7) {
                                    var10000 = var7;
                                    var10001 = false;
                                    break label74;
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
                              StringBuilder var12 = new StringBuilder();
                              var12.append("Authentication error: ");
                              var12.append(var9.getMessage());
                              var11.error(var12.toString());
                           }
                        }

                     }
                  }
               }
            }
         }
      } else {
         throw new IllegalArgumentException("HTTP context may not be null");
      }
   }
}

package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.client.AuthCache;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.HttpContext;

public class ResponseAuthCache implements HttpResponseInterceptor {
   private final Log log = LogFactory.getLog(this.getClass());

   private void cache(AuthCache var1, HttpHost var2, AuthState var3) {
      AuthScheme var4 = var3.getAuthScheme();
      if (var3.getAuthScope() != null) {
         if (var3.getCredentials() != null) {
            if (this.log.isDebugEnabled()) {
               Log var5 = this.log;
               StringBuilder var6 = new StringBuilder();
               var6.append("Caching '");
               var6.append(var4.getSchemeName());
               var6.append("' auth scheme for ");
               var6.append(var2);
               var5.debug(var6.toString());
            }

            var1.put(var2, var4);
         } else {
            var1.remove(var2);
         }
      }

   }

   private boolean isCachable(AuthState var1) {
      AuthScheme var4 = var1.getAuthScheme();
      boolean var2 = false;
      boolean var3 = var2;
      if (var4 != null) {
         if (!var4.isComplete()) {
            var3 = var2;
         } else {
            String var5 = var4.getSchemeName();
            if (!var5.equalsIgnoreCase("Basic")) {
               var3 = var2;
               if (!var5.equalsIgnoreCase("Digest")) {
                  return var3;
               }
            }

            var3 = true;
         }
      }

      return var3;
   }

   public void process(HttpResponse var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (var2 != null) {
            AuthCache var3 = (AuthCache)var2.getAttribute("http.auth.auth-cache");
            HttpHost var4 = (HttpHost)var2.getAttribute("http.target_host");
            AuthState var5 = (AuthState)var2.getAttribute("http.auth.target-scope");
            Object var6 = var3;
            if (var4 != null) {
               var6 = var3;
               if (var5 != null) {
                  var6 = var3;
                  if (this.isCachable(var5)) {
                     var6 = var3;
                     if (var3 == null) {
                        var6 = new BasicAuthCache();
                        var2.setAttribute("http.auth.auth-cache", var6);
                     }

                     this.cache((AuthCache)var6, var4, var5);
                  }
               }
            }

            var4 = (HttpHost)var2.getAttribute("http.proxy_host");
            var5 = (AuthState)var2.getAttribute("http.auth.proxy-scope");
            if (var4 != null && var5 != null && this.isCachable(var5)) {
               Object var7 = var6;
               if (var6 == null) {
                  var7 = new BasicAuthCache();
                  var2.setAttribute("http.auth.auth-cache", var7);
               }

               this.cache((AuthCache)var7, var4, var5);
            }

         } else {
            throw new IllegalArgumentException("HTTP context may not be null");
         }
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }
}

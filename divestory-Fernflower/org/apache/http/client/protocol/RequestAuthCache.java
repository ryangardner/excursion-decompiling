package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.protocol.HttpContext;

public class RequestAuthCache implements HttpRequestInterceptor {
   private final Log log = LogFactory.getLog(this.getClass());

   private void doPreemptiveAuth(HttpHost var1, AuthScheme var2, AuthState var3, CredentialsProvider var4) {
      String var5 = var2.getSchemeName();
      if (this.log.isDebugEnabled()) {
         Log var6 = this.log;
         StringBuilder var7 = new StringBuilder();
         var7.append("Re-using cached '");
         var7.append(var5);
         var7.append("' auth scheme for ");
         var7.append(var1);
         var6.debug(var7.toString());
      }

      Credentials var8 = var4.getCredentials(new AuthScope(var1.getHostName(), var1.getPort(), AuthScope.ANY_REALM, var5));
      if (var8 != null) {
         var3.setAuthScheme(var2);
         var3.setCredentials(var8);
      } else {
         this.log.debug("No credentials for preemptive authentication");
      }

   }

   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (var2 != null) {
            AuthCache var3 = (AuthCache)var2.getAttribute("http.auth.auth-cache");
            if (var3 == null) {
               this.log.debug("Auth cache not set in the context");
            } else {
               CredentialsProvider var8 = (CredentialsProvider)var2.getAttribute("http.auth.credentials-provider");
               if (var8 == null) {
                  this.log.debug("Credentials provider not set in the context");
               } else {
                  HttpHost var4 = (HttpHost)var2.getAttribute("http.target_host");
                  AuthState var5 = (AuthState)var2.getAttribute("http.auth.target-scope");
                  if (var4 != null && var5 != null && var5.getAuthScheme() == null) {
                     AuthScheme var6 = var3.get(var4);
                     if (var6 != null) {
                        this.doPreemptiveAuth(var4, var6, var5, var8);
                     }
                  }

                  HttpHost var7 = (HttpHost)var2.getAttribute("http.proxy_host");
                  AuthState var9 = (AuthState)var2.getAttribute("http.auth.proxy-scope");
                  if (var7 != null && var9 != null && var9.getAuthScheme() == null) {
                     AuthScheme var10 = var3.get(var7);
                     if (var10 != null) {
                        this.doPreemptiveAuth(var7, var10, var9, var8);
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

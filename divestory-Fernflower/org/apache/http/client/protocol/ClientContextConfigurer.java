package org.apache.http.client.protocol;

import java.util.List;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.protocol.HttpContext;

public class ClientContextConfigurer implements ClientContext {
   private final HttpContext context;

   public ClientContextConfigurer(HttpContext var1) {
      if (var1 != null) {
         this.context = var1;
      } else {
         throw new IllegalArgumentException("HTTP context may not be null");
      }
   }

   @Deprecated
   public void setAuthSchemePref(List<String> var1) {
      this.context.setAttribute("http.auth.scheme-pref", var1);
   }

   public void setAuthSchemeRegistry(AuthSchemeRegistry var1) {
      this.context.setAttribute("http.authscheme-registry", var1);
   }

   public void setCookieSpecRegistry(CookieSpecRegistry var1) {
      this.context.setAttribute("http.cookiespec-registry", var1);
   }

   public void setCookieStore(CookieStore var1) {
      this.context.setAttribute("http.cookie-store", var1);
   }

   public void setCredentialsProvider(CredentialsProvider var1) {
      this.context.setAttribute("http.auth.credentials-provider", var1);
   }
}

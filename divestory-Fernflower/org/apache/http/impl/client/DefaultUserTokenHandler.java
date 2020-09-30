package org.apache.http.impl.client;

import java.security.Principal;
import javax.net.ssl.SSLSession;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.protocol.HttpContext;

public class DefaultUserTokenHandler implements UserTokenHandler {
   private static Principal getAuthPrincipal(AuthState var0) {
      AuthScheme var1 = var0.getAuthScheme();
      if (var1 != null && var1.isComplete() && var1.isConnectionBased()) {
         Credentials var2 = var0.getCredentials();
         if (var2 != null) {
            return var2.getUserPrincipal();
         }
      }

      return null;
   }

   public Object getUserToken(HttpContext var1) {
      AuthState var2 = (AuthState)var1.getAttribute("http.auth.target-scope");
      Principal var3;
      Principal var6;
      if (var2 != null) {
         var3 = getAuthPrincipal(var2);
         var6 = var3;
         if (var3 == null) {
            var6 = getAuthPrincipal((AuthState)var1.getAttribute("http.auth.proxy-scope"));
         }
      } else {
         var6 = null;
      }

      var3 = var6;
      if (var6 == null) {
         HttpRoutedConnection var4 = (HttpRoutedConnection)var1.getAttribute("http.connection");
         var3 = var6;
         if (var4.isOpen()) {
            SSLSession var5 = var4.getSSLSession();
            var3 = var6;
            if (var5 != null) {
               var3 = var5.getLocalPrincipal();
            }
         }
      }

      return var3;
   }
}

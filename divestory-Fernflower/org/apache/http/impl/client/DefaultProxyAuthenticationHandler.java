package org.apache.http.impl.client;

import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.protocol.HttpContext;

public class DefaultProxyAuthenticationHandler extends AbstractAuthenticationHandler {
   protected List<String> getAuthPreferences(HttpResponse var1, HttpContext var2) {
      List var3 = (List)var1.getParams().getParameter("http.auth.proxy-scheme-pref");
      return var3 != null ? var3 : super.getAuthPreferences(var1, var2);
   }

   public Map<String, Header> getChallenges(HttpResponse var1, HttpContext var2) throws MalformedChallengeException {
      if (var1 != null) {
         return this.parseChallenges(var1.getHeaders("Proxy-Authenticate"));
      } else {
         throw new IllegalArgumentException("HTTP response may not be null");
      }
   }

   public boolean isAuthenticationRequested(HttpResponse var1, HttpContext var2) {
      if (var1 != null) {
         boolean var3;
         if (var1.getStatusLine().getStatusCode() == 407) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      } else {
         throw new IllegalArgumentException("HTTP response may not be null");
      }
   }
}

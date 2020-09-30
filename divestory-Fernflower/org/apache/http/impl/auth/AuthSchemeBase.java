package org.apache.http.impl.auth;

import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;

public abstract class AuthSchemeBase implements ContextAwareAuthScheme {
   private boolean proxy;

   public Header authenticate(Credentials var1, HttpRequest var2, HttpContext var3) throws AuthenticationException {
      return this.authenticate(var1, var2);
   }

   public boolean isProxy() {
      return this.proxy;
   }

   protected abstract void parseChallenge(CharArrayBuffer var1, int var2, int var3) throws MalformedChallengeException;

   public void processChallenge(Header var1) throws MalformedChallengeException {
      if (var1 == null) {
         throw new IllegalArgumentException("Header may not be null");
      } else {
         String var2 = var1.getName();
         boolean var3 = var2.equalsIgnoreCase("WWW-Authenticate");
         int var4 = 0;
         StringBuilder var8;
         if (var3) {
            this.proxy = false;
         } else {
            if (!var2.equalsIgnoreCase("Proxy-Authenticate")) {
               var8 = new StringBuilder();
               var8.append("Unexpected header name: ");
               var8.append(var2);
               throw new MalformedChallengeException(var8.toString());
            }

            this.proxy = true;
         }

         CharArrayBuffer var6;
         if (var1 instanceof FormattedHeader) {
            FormattedHeader var7 = (FormattedHeader)var1;
            var6 = var7.getBuffer();
            var4 = var7.getValuePos();
         } else {
            var2 = var1.getValue();
            if (var2 == null) {
               throw new MalformedChallengeException("Header value is null");
            }

            var6 = new CharArrayBuffer(var2.length());
            var6.append(var2);
         }

         while(var4 < var6.length() && HTTP.isWhitespace(var6.charAt(var4))) {
            ++var4;
         }

         int var5;
         for(var5 = var4; var5 < var6.length() && !HTTP.isWhitespace(var6.charAt(var5)); ++var5) {
         }

         var2 = var6.substring(var4, var5);
         if (var2.equalsIgnoreCase(this.getSchemeName())) {
            this.parseChallenge(var6, var5, var6.length());
         } else {
            var8 = new StringBuilder();
            var8.append("Invalid scheme identifier: ");
            var8.append(var2);
            throw new MalformedChallengeException(var8.toString());
         }
      }
   }

   public String toString() {
      return this.getSchemeName();
   }
}

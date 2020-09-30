package org.apache.http.impl.auth;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.params.AuthParams;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class BasicScheme extends RFC2617Scheme {
   private boolean complete = false;

   public static Header authenticate(Credentials var0, String var1, boolean var2) {
      if (var0 != null) {
         if (var1 != null) {
            StringBuilder var3 = new StringBuilder();
            var3.append(var0.getUserPrincipal().getName());
            var3.append(":");
            String var4;
            if (var0.getPassword() == null) {
               var4 = "null";
            } else {
               var4 = var0.getPassword();
            }

            var3.append(var4);
            byte[] var5 = Base64.encodeBase64(EncodingUtils.getBytes(var3.toString(), var1));
            CharArrayBuffer var6 = new CharArrayBuffer(32);
            if (var2) {
               var6.append("Proxy-Authorization");
            } else {
               var6.append("Authorization");
            }

            var6.append(": Basic ");
            var6.append((byte[])var5, 0, var5.length);
            return new BufferedHeader(var6);
         } else {
            throw new IllegalArgumentException("charset may not be null");
         }
      } else {
         throw new IllegalArgumentException("Credentials may not be null");
      }
   }

   public Header authenticate(Credentials var1, HttpRequest var2) throws AuthenticationException {
      if (var1 != null) {
         if (var2 != null) {
            return authenticate(var1, AuthParams.getCredentialCharset(var2.getParams()), this.isProxy());
         } else {
            throw new IllegalArgumentException("HTTP request may not be null");
         }
      } else {
         throw new IllegalArgumentException("Credentials may not be null");
      }
   }

   public String getSchemeName() {
      return "basic";
   }

   public boolean isComplete() {
      return this.complete;
   }

   public boolean isConnectionBased() {
      return false;
   }

   public void processChallenge(Header var1) throws MalformedChallengeException {
      super.processChallenge(var1);
      this.complete = true;
   }
}

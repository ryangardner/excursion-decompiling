package org.apache.http.impl.auth;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.NTCredentials;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;

public class NTLMScheme extends AuthSchemeBase {
   private String challenge;
   private final NTLMEngine engine;
   private NTLMScheme.State state;

   public NTLMScheme(NTLMEngine var1) {
      if (var1 != null) {
         this.engine = var1;
         this.state = NTLMScheme.State.UNINITIATED;
         this.challenge = null;
      } else {
         throw new IllegalArgumentException("NTLM engine may not be null");
      }
   }

   public Header authenticate(Credentials var1, HttpRequest var2) throws AuthenticationException {
      NTCredentials var7;
      try {
         var7 = (NTCredentials)var1;
      } catch (ClassCastException var3) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Credentials cannot be used for NTLM authentication: ");
         var5.append(var1.getClass().getName());
         throw new InvalidCredentialsException(var5.toString());
      }

      String var4;
      if (this.state != NTLMScheme.State.CHALLENGE_RECEIVED && this.state != NTLMScheme.State.FAILED) {
         if (this.state != NTLMScheme.State.MSG_TYPE2_RECEVIED) {
            StringBuilder var6 = new StringBuilder();
            var6.append("Unexpected state: ");
            var6.append(this.state);
            throw new AuthenticationException(var6.toString());
         }

         var4 = this.engine.generateType3Msg(var7.getUserName(), var7.getPassword(), var7.getDomain(), var7.getWorkstation(), this.challenge);
         this.state = NTLMScheme.State.MSG_TYPE3_GENERATED;
      } else {
         var4 = this.engine.generateType1Msg(var7.getDomain(), var7.getWorkstation());
         this.state = NTLMScheme.State.MSG_TYPE1_GENERATED;
      }

      CharArrayBuffer var8 = new CharArrayBuffer(32);
      if (this.isProxy()) {
         var8.append("Proxy-Authorization");
      } else {
         var8.append("Authorization");
      }

      var8.append(": NTLM ");
      var8.append(var4);
      return new BufferedHeader(var8);
   }

   public String getParameter(String var1) {
      return null;
   }

   public String getRealm() {
      return null;
   }

   public String getSchemeName() {
      return "ntlm";
   }

   public boolean isComplete() {
      boolean var1;
      if (this.state != NTLMScheme.State.MSG_TYPE3_GENERATED && this.state != NTLMScheme.State.FAILED) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isConnectionBased() {
      return true;
   }

   protected void parseChallenge(CharArrayBuffer var1, int var2, int var3) throws MalformedChallengeException {
      String var4 = var1.substringTrimmed(var2, var3);
      if (var4.length() == 0) {
         if (this.state == NTLMScheme.State.UNINITIATED) {
            this.state = NTLMScheme.State.CHALLENGE_RECEIVED;
         } else {
            this.state = NTLMScheme.State.FAILED;
         }

         this.challenge = null;
      } else {
         this.state = NTLMScheme.State.MSG_TYPE2_RECEVIED;
         this.challenge = var4;
      }

   }

   static enum State {
      CHALLENGE_RECEIVED,
      FAILED,
      MSG_TYPE1_GENERATED,
      MSG_TYPE2_RECEVIED,
      MSG_TYPE3_GENERATED,
      UNINITIATED;

      static {
         NTLMScheme.State var0 = new NTLMScheme.State("FAILED", 5);
         FAILED = var0;
      }
   }
}

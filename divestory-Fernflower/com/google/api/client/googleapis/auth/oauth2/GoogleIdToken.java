package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.util.Key;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class GoogleIdToken extends IdToken {
   public GoogleIdToken(JsonWebSignature.Header var1, GoogleIdToken.Payload var2, byte[] var3, byte[] var4) {
      super(var1, var2, var3, var4);
   }

   public static GoogleIdToken parse(JsonFactory var0, String var1) throws IOException {
      JsonWebSignature var2 = JsonWebSignature.parser(var0).setPayloadClass(GoogleIdToken.Payload.class).parse(var1);
      return new GoogleIdToken(var2.getHeader(), (GoogleIdToken.Payload)var2.getPayload(), var2.getSignatureBytes(), var2.getSignedContentBytes());
   }

   public GoogleIdToken.Payload getPayload() {
      return (GoogleIdToken.Payload)super.getPayload();
   }

   public boolean verify(GoogleIdTokenVerifier var1) throws GeneralSecurityException, IOException {
      return var1.verify(this);
   }

   public static class Payload extends IdToken.Payload {
      @Key("email")
      private String email;
      @Key("email_verified")
      private Object emailVerified;
      @Key("hd")
      private String hostedDomain;

      public GoogleIdToken.Payload clone() {
         return (GoogleIdToken.Payload)super.clone();
      }

      public String getEmail() {
         return this.email;
      }

      public Boolean getEmailVerified() {
         Object var1 = this.emailVerified;
         if (var1 == null) {
            return null;
         } else {
            return var1 instanceof Boolean ? (Boolean)var1 : Boolean.valueOf((String)var1);
         }
      }

      public String getHostedDomain() {
         return this.hostedDomain;
      }

      @Deprecated
      public String getIssuee() {
         return this.getAuthorizedParty();
      }

      @Deprecated
      public String getUserId() {
         return this.getSubject();
      }

      public GoogleIdToken.Payload set(String var1, Object var2) {
         return (GoogleIdToken.Payload)super.set(var1, var2);
      }

      public GoogleIdToken.Payload setAccessTokenHash(String var1) {
         return (GoogleIdToken.Payload)super.setAccessTokenHash(var1);
      }

      public GoogleIdToken.Payload setAudience(Object var1) {
         return (GoogleIdToken.Payload)super.setAudience(var1);
      }

      public GoogleIdToken.Payload setAuthorizationTimeSeconds(Long var1) {
         return (GoogleIdToken.Payload)super.setAuthorizationTimeSeconds(var1);
      }

      public GoogleIdToken.Payload setAuthorizedParty(String var1) {
         return (GoogleIdToken.Payload)super.setAuthorizedParty(var1);
      }

      public GoogleIdToken.Payload setClassReference(String var1) {
         return (GoogleIdToken.Payload)super.setClassReference(var1);
      }

      public GoogleIdToken.Payload setEmail(String var1) {
         this.email = var1;
         return this;
      }

      public GoogleIdToken.Payload setEmailVerified(Boolean var1) {
         this.emailVerified = var1;
         return this;
      }

      public GoogleIdToken.Payload setExpirationTimeSeconds(Long var1) {
         return (GoogleIdToken.Payload)super.setExpirationTimeSeconds(var1);
      }

      public GoogleIdToken.Payload setHostedDomain(String var1) {
         this.hostedDomain = var1;
         return this;
      }

      public GoogleIdToken.Payload setIssuedAtTimeSeconds(Long var1) {
         return (GoogleIdToken.Payload)super.setIssuedAtTimeSeconds(var1);
      }

      @Deprecated
      public GoogleIdToken.Payload setIssuee(String var1) {
         return this.setAuthorizedParty(var1);
      }

      public GoogleIdToken.Payload setIssuer(String var1) {
         return (GoogleIdToken.Payload)super.setIssuer(var1);
      }

      public GoogleIdToken.Payload setJwtId(String var1) {
         return (GoogleIdToken.Payload)super.setJwtId(var1);
      }

      public GoogleIdToken.Payload setMethodsReferences(List<String> var1) {
         return (GoogleIdToken.Payload)super.setMethodsReferences(var1);
      }

      public GoogleIdToken.Payload setNonce(String var1) {
         return (GoogleIdToken.Payload)super.setNonce(var1);
      }

      public GoogleIdToken.Payload setNotBeforeTimeSeconds(Long var1) {
         return (GoogleIdToken.Payload)super.setNotBeforeTimeSeconds(var1);
      }

      public GoogleIdToken.Payload setSubject(String var1) {
         return (GoogleIdToken.Payload)super.setSubject(var1);
      }

      public GoogleIdToken.Payload setType(String var1) {
         return (GoogleIdToken.Payload)super.setType(var1);
      }

      @Deprecated
      public GoogleIdToken.Payload setUserId(String var1) {
         return this.setSubject(var1);
      }
   }
}

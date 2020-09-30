package com.google.api.client.auth.openidconnect;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.api.client.util.Key;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IdToken extends JsonWebSignature {
   public IdToken(JsonWebSignature.Header var1, IdToken.Payload var2, byte[] var3, byte[] var4) {
      super(var1, var2, var3, var4);
   }

   public static IdToken parse(JsonFactory var0, String var1) throws IOException {
      JsonWebSignature var2 = JsonWebSignature.parser(var0).setPayloadClass(IdToken.Payload.class).parse(var1);
      return new IdToken(var2.getHeader(), (IdToken.Payload)var2.getPayload(), var2.getSignatureBytes(), var2.getSignedContentBytes());
   }

   public IdToken.Payload getPayload() {
      return (IdToken.Payload)super.getPayload();
   }

   public final boolean verifyAudience(Collection<String> var1) {
      List var2 = this.getPayload().getAudienceAsList();
      return var2.isEmpty() ? false : var1.containsAll(var2);
   }

   public final boolean verifyExpirationTime(long var1, long var3) {
      boolean var5;
      if (var1 <= (this.getPayload().getExpirationTimeSeconds() + var3) * 1000L) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public final boolean verifyIssuedAtTime(long var1, long var3) {
      boolean var5;
      if (var1 >= (this.getPayload().getIssuedAtTimeSeconds() - var3) * 1000L) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public final boolean verifyIssuer(String var1) {
      return this.verifyIssuer((Collection)Collections.singleton(var1));
   }

   public final boolean verifyIssuer(Collection<String> var1) {
      return var1.contains(this.getPayload().getIssuer());
   }

   public final boolean verifyTime(long var1, long var3) {
      boolean var5;
      if (this.verifyExpirationTime(var1, var3) && this.verifyIssuedAtTime(var1, var3)) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public static class Payload extends JsonWebToken.Payload {
      @Key("at_hash")
      private String accessTokenHash;
      @Key("auth_time")
      private Long authorizationTimeSeconds;
      @Key("azp")
      private String authorizedParty;
      @Key("acr")
      private String classReference;
      @Key("amr")
      private List<String> methodsReferences;
      @Key
      private String nonce;

      public IdToken.Payload clone() {
         return (IdToken.Payload)super.clone();
      }

      public final String getAccessTokenHash() {
         return this.accessTokenHash;
      }

      public final Long getAuthorizationTimeSeconds() {
         return this.authorizationTimeSeconds;
      }

      public final String getAuthorizedParty() {
         return this.authorizedParty;
      }

      public final String getClassReference() {
         return this.classReference;
      }

      public final List<String> getMethodsReferences() {
         return this.methodsReferences;
      }

      public final String getNonce() {
         return this.nonce;
      }

      public IdToken.Payload set(String var1, Object var2) {
         return (IdToken.Payload)super.set(var1, var2);
      }

      public IdToken.Payload setAccessTokenHash(String var1) {
         this.accessTokenHash = var1;
         return this;
      }

      public IdToken.Payload setAudience(Object var1) {
         return (IdToken.Payload)super.setAudience(var1);
      }

      public IdToken.Payload setAuthorizationTimeSeconds(Long var1) {
         this.authorizationTimeSeconds = var1;
         return this;
      }

      public IdToken.Payload setAuthorizedParty(String var1) {
         this.authorizedParty = var1;
         return this;
      }

      public IdToken.Payload setClassReference(String var1) {
         this.classReference = var1;
         return this;
      }

      public IdToken.Payload setExpirationTimeSeconds(Long var1) {
         return (IdToken.Payload)super.setExpirationTimeSeconds(var1);
      }

      public IdToken.Payload setIssuedAtTimeSeconds(Long var1) {
         return (IdToken.Payload)super.setIssuedAtTimeSeconds(var1);
      }

      public IdToken.Payload setIssuer(String var1) {
         return (IdToken.Payload)super.setIssuer(var1);
      }

      public IdToken.Payload setJwtId(String var1) {
         return (IdToken.Payload)super.setJwtId(var1);
      }

      public IdToken.Payload setMethodsReferences(List<String> var1) {
         this.methodsReferences = var1;
         return this;
      }

      public IdToken.Payload setNonce(String var1) {
         this.nonce = var1;
         return this;
      }

      public IdToken.Payload setNotBeforeTimeSeconds(Long var1) {
         return (IdToken.Payload)super.setNotBeforeTimeSeconds(var1);
      }

      public IdToken.Payload setSubject(String var1) {
         return (IdToken.Payload)super.setSubject(var1);
      }

      public IdToken.Payload setType(String var1) {
         return (IdToken.Payload)super.setType(var1);
      }
   }
}

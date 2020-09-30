package com.google.api.client.json.webtoken;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import java.util.Collections;
import java.util.List;

public class JsonWebToken {
   private final JsonWebToken.Header header;
   private final JsonWebToken.Payload payload;

   public JsonWebToken(JsonWebToken.Header var1, JsonWebToken.Payload var2) {
      this.header = (JsonWebToken.Header)Preconditions.checkNotNull(var1);
      this.payload = (JsonWebToken.Payload)Preconditions.checkNotNull(var2);
   }

   public JsonWebToken.Header getHeader() {
      return this.header;
   }

   public JsonWebToken.Payload getPayload() {
      return this.payload;
   }

   public String toString() {
      return Objects.toStringHelper(this).add("header", this.header).add("payload", this.payload).toString();
   }

   public static class Header extends GenericJson {
      @Key("cty")
      private String contentType;
      @Key("typ")
      private String type;

      public JsonWebToken.Header clone() {
         return (JsonWebToken.Header)super.clone();
      }

      public final String getContentType() {
         return this.contentType;
      }

      public final String getType() {
         return this.type;
      }

      public JsonWebToken.Header set(String var1, Object var2) {
         return (JsonWebToken.Header)super.set(var1, var2);
      }

      public JsonWebToken.Header setContentType(String var1) {
         this.contentType = var1;
         return this;
      }

      public JsonWebToken.Header setType(String var1) {
         this.type = var1;
         return this;
      }
   }

   public static class Payload extends GenericJson {
      @Key("aud")
      private Object audience;
      @Key("exp")
      private Long expirationTimeSeconds;
      @Key("iat")
      private Long issuedAtTimeSeconds;
      @Key("iss")
      private String issuer;
      @Key("jti")
      private String jwtId;
      @Key("nbf")
      private Long notBeforeTimeSeconds;
      @Key("sub")
      private String subject;
      @Key("typ")
      private String type;

      public JsonWebToken.Payload clone() {
         return (JsonWebToken.Payload)super.clone();
      }

      public final Object getAudience() {
         return this.audience;
      }

      public final List<String> getAudienceAsList() {
         Object var1 = this.audience;
         if (var1 == null) {
            return Collections.emptyList();
         } else {
            return var1 instanceof String ? Collections.singletonList((String)var1) : (List)var1;
         }
      }

      public final Long getExpirationTimeSeconds() {
         return this.expirationTimeSeconds;
      }

      public final Long getIssuedAtTimeSeconds() {
         return this.issuedAtTimeSeconds;
      }

      public final String getIssuer() {
         return this.issuer;
      }

      public final String getJwtId() {
         return this.jwtId;
      }

      public final Long getNotBeforeTimeSeconds() {
         return this.notBeforeTimeSeconds;
      }

      public final String getSubject() {
         return this.subject;
      }

      public final String getType() {
         return this.type;
      }

      public JsonWebToken.Payload set(String var1, Object var2) {
         return (JsonWebToken.Payload)super.set(var1, var2);
      }

      public JsonWebToken.Payload setAudience(Object var1) {
         this.audience = var1;
         return this;
      }

      public JsonWebToken.Payload setExpirationTimeSeconds(Long var1) {
         this.expirationTimeSeconds = var1;
         return this;
      }

      public JsonWebToken.Payload setIssuedAtTimeSeconds(Long var1) {
         this.issuedAtTimeSeconds = var1;
         return this;
      }

      public JsonWebToken.Payload setIssuer(String var1) {
         this.issuer = var1;
         return this;
      }

      public JsonWebToken.Payload setJwtId(String var1) {
         this.jwtId = var1;
         return this;
      }

      public JsonWebToken.Payload setNotBeforeTimeSeconds(Long var1) {
         this.notBeforeTimeSeconds = var1;
         return this;
      }

      public JsonWebToken.Payload setSubject(String var1) {
         this.subject = var1;
         return this;
      }

      public JsonWebToken.Payload setType(String var1) {
         this.type = var1;
         return this;
      }
   }
}

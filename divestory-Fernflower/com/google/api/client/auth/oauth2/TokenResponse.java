package com.google.api.client.auth.oauth2;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;

public class TokenResponse extends GenericJson {
   @Key("access_token")
   private String accessToken;
   @Key("expires_in")
   private Long expiresInSeconds;
   @Key("refresh_token")
   private String refreshToken;
   @Key
   private String scope;
   @Key("token_type")
   private String tokenType;

   public TokenResponse clone() {
      return (TokenResponse)super.clone();
   }

   public String getAccessToken() {
      return this.accessToken;
   }

   public Long getExpiresInSeconds() {
      return this.expiresInSeconds;
   }

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public String getScope() {
      return this.scope;
   }

   public String getTokenType() {
      return this.tokenType;
   }

   public TokenResponse set(String var1, Object var2) {
      return (TokenResponse)super.set(var1, var2);
   }

   public TokenResponse setAccessToken(String var1) {
      this.accessToken = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public TokenResponse setExpiresInSeconds(Long var1) {
      this.expiresInSeconds = var1;
      return this;
   }

   public TokenResponse setRefreshToken(String var1) {
      this.refreshToken = var1;
      return this;
   }

   public TokenResponse setScope(String var1) {
      this.scope = var1;
      return this;
   }

   public TokenResponse setTokenType(String var1) {
      this.tokenType = (String)Preconditions.checkNotNull(var1);
      return this;
   }
}

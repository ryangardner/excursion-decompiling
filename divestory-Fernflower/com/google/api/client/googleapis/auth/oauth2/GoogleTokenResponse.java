package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class GoogleTokenResponse extends TokenResponse {
   @Key("id_token")
   private String idToken;

   public GoogleTokenResponse clone() {
      return (GoogleTokenResponse)super.clone();
   }

   public final String getIdToken() {
      return this.idToken;
   }

   public GoogleIdToken parseIdToken() throws IOException {
      return GoogleIdToken.parse(this.getFactory(), this.getIdToken());
   }

   public GoogleTokenResponse set(String var1, Object var2) {
      return (GoogleTokenResponse)super.set(var1, var2);
   }

   public GoogleTokenResponse setAccessToken(String var1) {
      return (GoogleTokenResponse)super.setAccessToken(var1);
   }

   public GoogleTokenResponse setExpiresInSeconds(Long var1) {
      return (GoogleTokenResponse)super.setExpiresInSeconds(var1);
   }

   public GoogleTokenResponse setIdToken(String var1) {
      this.idToken = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public GoogleTokenResponse setRefreshToken(String var1) {
      return (GoogleTokenResponse)super.setRefreshToken(var1);
   }

   public GoogleTokenResponse setScope(String var1) {
      return (GoogleTokenResponse)super.setScope(var1);
   }

   public GoogleTokenResponse setTokenType(String var1) {
      return (GoogleTokenResponse)super.setTokenType(var1);
   }
}

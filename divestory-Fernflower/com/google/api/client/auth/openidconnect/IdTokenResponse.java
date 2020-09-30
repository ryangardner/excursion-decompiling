package com.google.api.client.auth.openidconnect;

import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class IdTokenResponse extends TokenResponse {
   @Key("id_token")
   private String idToken;

   public static IdTokenResponse execute(TokenRequest var0) throws IOException {
      return (IdTokenResponse)var0.executeUnparsed().parseAs(IdTokenResponse.class);
   }

   public IdTokenResponse clone() {
      return (IdTokenResponse)super.clone();
   }

   public final String getIdToken() {
      return this.idToken;
   }

   public IdToken parseIdToken() throws IOException {
      return IdToken.parse(this.getFactory(), this.idToken);
   }

   public IdTokenResponse set(String var1, Object var2) {
      return (IdTokenResponse)super.set(var1, var2);
   }

   public IdTokenResponse setAccessToken(String var1) {
      super.setAccessToken(var1);
      return this;
   }

   public IdTokenResponse setExpiresInSeconds(Long var1) {
      super.setExpiresInSeconds(var1);
      return this;
   }

   public IdTokenResponse setIdToken(String var1) {
      this.idToken = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public IdTokenResponse setRefreshToken(String var1) {
      super.setRefreshToken(var1);
      return this;
   }

   public IdTokenResponse setScope(String var1) {
      super.setScope(var1);
      return this;
   }

   public IdTokenResponse setTokenType(String var1) {
      super.setTokenType(var1);
      return this;
   }
}

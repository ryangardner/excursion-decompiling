package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

public class RefreshTokenRequest extends TokenRequest {
   @Key("refresh_token")
   private String refreshToken;

   public RefreshTokenRequest(HttpTransport var1, JsonFactory var2, GenericUrl var3, String var4) {
      super(var1, var2, var3, "refresh_token");
      this.setRefreshToken(var4);
   }

   public final String getRefreshToken() {
      return this.refreshToken;
   }

   public RefreshTokenRequest set(String var1, Object var2) {
      return (RefreshTokenRequest)super.set(var1, var2);
   }

   public RefreshTokenRequest setClientAuthentication(HttpExecuteInterceptor var1) {
      return (RefreshTokenRequest)super.setClientAuthentication(var1);
   }

   public RefreshTokenRequest setGrantType(String var1) {
      return (RefreshTokenRequest)super.setGrantType(var1);
   }

   public RefreshTokenRequest setRefreshToken(String var1) {
      this.refreshToken = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public RefreshTokenRequest setRequestInitializer(HttpRequestInitializer var1) {
      return (RefreshTokenRequest)super.setRequestInitializer(var1);
   }

   public RefreshTokenRequest setResponseClass(Class<? extends TokenResponse> var1) {
      return (RefreshTokenRequest)super.setResponseClass(var1);
   }

   public RefreshTokenRequest setScopes(Collection<String> var1) {
      return (RefreshTokenRequest)super.setScopes(var1);
   }

   public RefreshTokenRequest setTokenServerUrl(GenericUrl var1) {
      return (RefreshTokenRequest)super.setTokenServerUrl(var1);
   }
}

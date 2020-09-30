package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import java.io.IOException;
import java.util.Collection;

public class GoogleRefreshTokenRequest extends RefreshTokenRequest {
   public GoogleRefreshTokenRequest(HttpTransport var1, JsonFactory var2, String var3, String var4, String var5) {
      super(var1, var2, new GenericUrl("https://oauth2.googleapis.com/token"), var3);
      this.setClientAuthentication(new ClientParametersAuthentication(var4, var5));
   }

   public GoogleTokenResponse execute() throws IOException {
      return (GoogleTokenResponse)this.executeUnparsed().parseAs(GoogleTokenResponse.class);
   }

   public GoogleRefreshTokenRequest set(String var1, Object var2) {
      return (GoogleRefreshTokenRequest)super.set(var1, var2);
   }

   public GoogleRefreshTokenRequest setClientAuthentication(HttpExecuteInterceptor var1) {
      return (GoogleRefreshTokenRequest)super.setClientAuthentication(var1);
   }

   public GoogleRefreshTokenRequest setGrantType(String var1) {
      return (GoogleRefreshTokenRequest)super.setGrantType(var1);
   }

   public GoogleRefreshTokenRequest setRefreshToken(String var1) {
      return (GoogleRefreshTokenRequest)super.setRefreshToken(var1);
   }

   public GoogleRefreshTokenRequest setRequestInitializer(HttpRequestInitializer var1) {
      return (GoogleRefreshTokenRequest)super.setRequestInitializer(var1);
   }

   public GoogleRefreshTokenRequest setScopes(Collection<String> var1) {
      return (GoogleRefreshTokenRequest)super.setScopes(var1);
   }

   public GoogleRefreshTokenRequest setTokenServerUrl(GenericUrl var1) {
      return (GoogleRefreshTokenRequest)super.setTokenServerUrl(var1);
   }
}

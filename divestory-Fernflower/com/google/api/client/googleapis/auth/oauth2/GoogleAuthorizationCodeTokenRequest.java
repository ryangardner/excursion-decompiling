package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;

public class GoogleAuthorizationCodeTokenRequest extends AuthorizationCodeTokenRequest {
   public GoogleAuthorizationCodeTokenRequest(HttpTransport var1, JsonFactory var2, String var3, String var4, String var5, String var6) {
      this(var1, var2, "https://oauth2.googleapis.com/token", var3, var4, var5, var6);
   }

   public GoogleAuthorizationCodeTokenRequest(HttpTransport var1, JsonFactory var2, String var3, String var4, String var5, String var6, String var7) {
      super(var1, var2, new GenericUrl(var3), var6);
      this.setClientAuthentication(new ClientParametersAuthentication(var4, var5));
      this.setRedirectUri(var7);
   }

   public GoogleTokenResponse execute() throws IOException {
      return (GoogleTokenResponse)this.executeUnparsed().parseAs(GoogleTokenResponse.class);
   }

   public GoogleAuthorizationCodeTokenRequest set(String var1, Object var2) {
      return (GoogleAuthorizationCodeTokenRequest)super.set(var1, var2);
   }

   public GoogleAuthorizationCodeTokenRequest setClientAuthentication(HttpExecuteInterceptor var1) {
      Preconditions.checkNotNull(var1);
      return (GoogleAuthorizationCodeTokenRequest)super.setClientAuthentication(var1);
   }

   public GoogleAuthorizationCodeTokenRequest setCode(String var1) {
      return (GoogleAuthorizationCodeTokenRequest)super.setCode(var1);
   }

   public GoogleAuthorizationCodeTokenRequest setGrantType(String var1) {
      return (GoogleAuthorizationCodeTokenRequest)super.setGrantType(var1);
   }

   public GoogleAuthorizationCodeTokenRequest setRedirectUri(String var1) {
      Preconditions.checkNotNull(var1);
      return (GoogleAuthorizationCodeTokenRequest)super.setRedirectUri(var1);
   }

   public GoogleAuthorizationCodeTokenRequest setRequestInitializer(HttpRequestInitializer var1) {
      return (GoogleAuthorizationCodeTokenRequest)super.setRequestInitializer(var1);
   }

   public GoogleAuthorizationCodeTokenRequest setScopes(Collection<String> var1) {
      return (GoogleAuthorizationCodeTokenRequest)super.setScopes(var1);
   }

   public GoogleAuthorizationCodeTokenRequest setTokenServerUrl(GenericUrl var1) {
      return (GoogleAuthorizationCodeTokenRequest)super.setTokenServerUrl(var1);
   }
}

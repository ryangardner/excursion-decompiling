package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

public class AuthorizationCodeTokenRequest extends TokenRequest {
   @Key
   private String code;
   @Key("redirect_uri")
   private String redirectUri;

   public AuthorizationCodeTokenRequest(HttpTransport var1, JsonFactory var2, GenericUrl var3, String var4) {
      super(var1, var2, var3, "authorization_code");
      this.setCode(var4);
   }

   public final String getCode() {
      return this.code;
   }

   public final String getRedirectUri() {
      return this.redirectUri;
   }

   public AuthorizationCodeTokenRequest set(String var1, Object var2) {
      return (AuthorizationCodeTokenRequest)super.set(var1, var2);
   }

   public AuthorizationCodeTokenRequest setClientAuthentication(HttpExecuteInterceptor var1) {
      return (AuthorizationCodeTokenRequest)super.setClientAuthentication(var1);
   }

   public AuthorizationCodeTokenRequest setCode(String var1) {
      this.code = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public AuthorizationCodeTokenRequest setGrantType(String var1) {
      return (AuthorizationCodeTokenRequest)super.setGrantType(var1);
   }

   public AuthorizationCodeTokenRequest setRedirectUri(String var1) {
      this.redirectUri = var1;
      return this;
   }

   public AuthorizationCodeTokenRequest setRequestInitializer(HttpRequestInitializer var1) {
      return (AuthorizationCodeTokenRequest)super.setRequestInitializer(var1);
   }

   public AuthorizationCodeTokenRequest setResponseClass(Class<? extends TokenResponse> var1) {
      return (AuthorizationCodeTokenRequest)super.setResponseClass(var1);
   }

   public AuthorizationCodeTokenRequest setScopes(Collection<String> var1) {
      return (AuthorizationCodeTokenRequest)super.setScopes(var1);
   }

   public AuthorizationCodeTokenRequest setTokenServerUrl(GenericUrl var1) {
      return (AuthorizationCodeTokenRequest)super.setTokenServerUrl(var1);
   }
}

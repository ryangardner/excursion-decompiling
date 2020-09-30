package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import java.util.Collection;

public class ClientCredentialsTokenRequest extends TokenRequest {
   public ClientCredentialsTokenRequest(HttpTransport var1, JsonFactory var2, GenericUrl var3) {
      super(var1, var2, var3, "client_credentials");
   }

   public ClientCredentialsTokenRequest set(String var1, Object var2) {
      return (ClientCredentialsTokenRequest)super.set(var1, var2);
   }

   public ClientCredentialsTokenRequest setClientAuthentication(HttpExecuteInterceptor var1) {
      return (ClientCredentialsTokenRequest)super.setClientAuthentication(var1);
   }

   public ClientCredentialsTokenRequest setGrantType(String var1) {
      return (ClientCredentialsTokenRequest)super.setGrantType(var1);
   }

   public ClientCredentialsTokenRequest setRequestInitializer(HttpRequestInitializer var1) {
      return (ClientCredentialsTokenRequest)super.setRequestInitializer(var1);
   }

   public ClientCredentialsTokenRequest setResponseClass(Class<? extends TokenResponse> var1) {
      return (ClientCredentialsTokenRequest)super.setResponseClass(var1);
   }

   public ClientCredentialsTokenRequest setScopes(Collection<String> var1) {
      return (ClientCredentialsTokenRequest)super.setScopes(var1);
   }

   public ClientCredentialsTokenRequest setTokenServerUrl(GenericUrl var1) {
      return (ClientCredentialsTokenRequest)super.setTokenServerUrl(var1);
   }
}

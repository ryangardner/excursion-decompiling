package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

public class PasswordTokenRequest extends TokenRequest {
   @Key
   private String password;
   @Key
   private String username;

   public PasswordTokenRequest(HttpTransport var1, JsonFactory var2, GenericUrl var3, String var4, String var5) {
      super(var1, var2, var3, "password");
      this.setUsername(var4);
      this.setPassword(var5);
   }

   public final String getPassword() {
      return this.password;
   }

   public final String getUsername() {
      return this.username;
   }

   public PasswordTokenRequest set(String var1, Object var2) {
      return (PasswordTokenRequest)super.set(var1, var2);
   }

   public PasswordTokenRequest setClientAuthentication(HttpExecuteInterceptor var1) {
      return (PasswordTokenRequest)super.setClientAuthentication(var1);
   }

   public PasswordTokenRequest setGrantType(String var1) {
      return (PasswordTokenRequest)super.setGrantType(var1);
   }

   public PasswordTokenRequest setPassword(String var1) {
      this.password = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public PasswordTokenRequest setRequestInitializer(HttpRequestInitializer var1) {
      return (PasswordTokenRequest)super.setRequestInitializer(var1);
   }

   public PasswordTokenRequest setResponseClass(Class<? extends TokenResponse> var1) {
      return (PasswordTokenRequest)super.setResponseClass(var1);
   }

   public PasswordTokenRequest setScopes(Collection<String> var1) {
      return (PasswordTokenRequest)super.setScopes(var1);
   }

   public PasswordTokenRequest setTokenServerUrl(GenericUrl var1) {
      return (PasswordTokenRequest)super.setTokenServerUrl(var1);
   }

   public PasswordTokenRequest setUsername(String var1) {
      this.username = (String)Preconditions.checkNotNull(var1);
      return this;
   }
}

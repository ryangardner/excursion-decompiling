package com.google.api.client.auth.oauth2;

import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.util.Data;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Map;

public class ClientParametersAuthentication implements HttpRequestInitializer, HttpExecuteInterceptor {
   private final String clientId;
   private final String clientSecret;

   public ClientParametersAuthentication(String var1, String var2) {
      this.clientId = (String)Preconditions.checkNotNull(var1);
      this.clientSecret = var2;
   }

   public final String getClientId() {
      return this.clientId;
   }

   public final String getClientSecret() {
      return this.clientSecret;
   }

   public void initialize(HttpRequest var1) throws IOException {
      var1.setInterceptor(this);
   }

   public void intercept(HttpRequest var1) throws IOException {
      Map var3 = Data.mapOf(UrlEncodedContent.getContent(var1).getData());
      var3.put("client_id", this.clientId);
      String var2 = this.clientSecret;
      if (var2 != null) {
         var3.put("client_secret", var2);
      }

   }
}

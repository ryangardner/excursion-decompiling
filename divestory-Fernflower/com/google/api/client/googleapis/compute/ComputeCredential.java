package com.google.api.client.googleapis.compute;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;

public class ComputeCredential extends Credential {
   public static final String TOKEN_SERVER_ENCODED_URL;

   static {
      StringBuilder var0 = new StringBuilder();
      var0.append(OAuth2Utils.getMetadataServerUrl());
      var0.append("/computeMetadata/v1/instance/service-accounts/default/token");
      TOKEN_SERVER_ENCODED_URL = var0.toString();
   }

   protected ComputeCredential(ComputeCredential.Builder var1) {
      super((Credential.Builder)var1);
   }

   public ComputeCredential(HttpTransport var1, JsonFactory var2) {
      this(new ComputeCredential.Builder(var1, var2));
   }

   protected TokenResponse executeRefreshToken() throws IOException {
      GenericUrl var1 = new GenericUrl(this.getTokenServerEncodedUrl());
      HttpRequest var2 = this.getTransport().createRequestFactory().buildGetRequest(var1);
      var2.setParser(new JsonObjectParser(this.getJsonFactory()));
      var2.getHeaders().set("Metadata-Flavor", "Google");
      return (TokenResponse)var2.execute().parseAs(TokenResponse.class);
   }

   public static class Builder extends Credential.Builder {
      public Builder(HttpTransport var1, JsonFactory var2) {
         super(BearerToken.authorizationHeaderAccessMethod());
         this.setTransport(var1);
         this.setJsonFactory(var2);
         this.setTokenServerEncodedUrl(ComputeCredential.TOKEN_SERVER_ENCODED_URL);
      }

      public ComputeCredential.Builder addRefreshListener(CredentialRefreshListener var1) {
         return (ComputeCredential.Builder)super.addRefreshListener(var1);
      }

      public ComputeCredential build() {
         return new ComputeCredential(this);
      }

      public ComputeCredential.Builder setClientAuthentication(HttpExecuteInterceptor var1) {
         boolean var2;
         if (var1 == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2);
         return this;
      }

      public ComputeCredential.Builder setClock(Clock var1) {
         return (ComputeCredential.Builder)super.setClock(var1);
      }

      public ComputeCredential.Builder setJsonFactory(JsonFactory var1) {
         return (ComputeCredential.Builder)super.setJsonFactory((JsonFactory)Preconditions.checkNotNull(var1));
      }

      public ComputeCredential.Builder setRefreshListeners(Collection<CredentialRefreshListener> var1) {
         return (ComputeCredential.Builder)super.setRefreshListeners(var1);
      }

      public ComputeCredential.Builder setRequestInitializer(HttpRequestInitializer var1) {
         return (ComputeCredential.Builder)super.setRequestInitializer(var1);
      }

      public ComputeCredential.Builder setTokenServerEncodedUrl(String var1) {
         return (ComputeCredential.Builder)super.setTokenServerEncodedUrl((String)Preconditions.checkNotNull(var1));
      }

      public ComputeCredential.Builder setTokenServerUrl(GenericUrl var1) {
         return (ComputeCredential.Builder)super.setTokenServerUrl((GenericUrl)Preconditions.checkNotNull(var1));
      }

      public ComputeCredential.Builder setTransport(HttpTransport var1) {
         return (ComputeCredential.Builder)super.setTransport((HttpTransport)Preconditions.checkNotNull(var1));
      }
   }
}

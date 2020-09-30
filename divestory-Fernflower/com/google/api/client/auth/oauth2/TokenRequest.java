package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;

public class TokenRequest extends GenericData {
   HttpExecuteInterceptor clientAuthentication;
   @Key("grant_type")
   private String grantType;
   private final JsonFactory jsonFactory;
   HttpRequestInitializer requestInitializer;
   protected Class<? extends TokenResponse> responseClass;
   @Key("scope")
   private String scopes;
   private GenericUrl tokenServerUrl;
   private final HttpTransport transport;

   public TokenRequest(HttpTransport var1, JsonFactory var2, GenericUrl var3, String var4) {
      this(var1, var2, var3, var4, TokenResponse.class);
   }

   public TokenRequest(HttpTransport var1, JsonFactory var2, GenericUrl var3, String var4, Class<? extends TokenResponse> var5) {
      this.transport = (HttpTransport)Preconditions.checkNotNull(var1);
      this.jsonFactory = (JsonFactory)Preconditions.checkNotNull(var2);
      this.setTokenServerUrl(var3);
      this.setGrantType(var4);
      this.setResponseClass(var5);
   }

   public TokenResponse execute() throws IOException {
      return (TokenResponse)this.executeUnparsed().parseAs(this.responseClass);
   }

   public final HttpResponse executeUnparsed() throws IOException {
      HttpRequest var1 = this.transport.createRequestFactory(new HttpRequestInitializer() {
         public void initialize(HttpRequest var1) throws IOException {
            if (TokenRequest.this.requestInitializer != null) {
               TokenRequest.this.requestInitializer.initialize(var1);
            }

            var1.setInterceptor(new HttpExecuteInterceptor(var1.getInterceptor()) {
               // $FF: synthetic field
               final HttpExecuteInterceptor val$interceptor;

               {
                  this.val$interceptor = var2;
               }

               public void intercept(HttpRequest var1) throws IOException {
                  HttpExecuteInterceptor var2 = this.val$interceptor;
                  if (var2 != null) {
                     var2.intercept(var1);
                  }

                  if (TokenRequest.this.clientAuthentication != null) {
                     TokenRequest.this.clientAuthentication.intercept(var1);
                  }

               }
            });
         }
      }).buildPostRequest(this.tokenServerUrl, new UrlEncodedContent(this));
      var1.setParser(new JsonObjectParser(this.jsonFactory));
      var1.setThrowExceptionOnExecuteError(false);
      HttpResponse var2 = var1.execute();
      if (var2.isSuccessStatusCode()) {
         return var2;
      } else {
         throw TokenResponseException.from(this.jsonFactory, var2);
      }
   }

   public final HttpExecuteInterceptor getClientAuthentication() {
      return this.clientAuthentication;
   }

   public final String getGrantType() {
      return this.grantType;
   }

   public final JsonFactory getJsonFactory() {
      return this.jsonFactory;
   }

   public final HttpRequestInitializer getRequestInitializer() {
      return this.requestInitializer;
   }

   public final Class<? extends TokenResponse> getResponseClass() {
      return this.responseClass;
   }

   public final String getScopes() {
      return this.scopes;
   }

   public final GenericUrl getTokenServerUrl() {
      return this.tokenServerUrl;
   }

   public final HttpTransport getTransport() {
      return this.transport;
   }

   public TokenRequest set(String var1, Object var2) {
      return (TokenRequest)super.set(var1, var2);
   }

   public TokenRequest setClientAuthentication(HttpExecuteInterceptor var1) {
      this.clientAuthentication = var1;
      return this;
   }

   public TokenRequest setGrantType(String var1) {
      this.grantType = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public TokenRequest setRequestInitializer(HttpRequestInitializer var1) {
      this.requestInitializer = var1;
      return this;
   }

   public TokenRequest setResponseClass(Class<? extends TokenResponse> var1) {
      this.responseClass = var1;
      return this;
   }

   public TokenRequest setScopes(Collection<String> var1) {
      String var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = Joiner.on(' ').join(var1);
      }

      this.scopes = var2;
      return this;
   }

   public TokenRequest setTokenServerUrl(GenericUrl var1) {
      this.tokenServerUrl = var1;
      boolean var2;
      if (var1.getFragment() == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      return this;
   }
}

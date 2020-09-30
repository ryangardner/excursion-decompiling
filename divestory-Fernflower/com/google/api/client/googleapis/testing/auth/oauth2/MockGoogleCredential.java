package com.google.api.client.googleapis.testing.auth.oauth2;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.client.util.Clock;
import java.io.IOException;

public class MockGoogleCredential extends GoogleCredential {
   public static final String ACCESS_TOKEN = "access_xyz";
   private static final String DEFAULT_TOKEN_RESPONSE_JSON = String.format("{\"access_token\": \"%s\", \"expires_in\":  %s, \"refresh_token\": \"%s\", \"token_type\": \"%s\"}", "access_xyz", "3600", "refresh123", "Bearer");
   private static final String EXPIRES_IN_SECONDS = "3600";
   public static final String REFRESH_TOKEN = "refresh123";
   private static final String TOKEN_RESPONSE = "{\"access_token\": \"%s\", \"expires_in\":  %s, \"refresh_token\": \"%s\", \"token_type\": \"%s\"}";
   private static final String TOKEN_TYPE = "Bearer";

   public MockGoogleCredential(MockGoogleCredential.Builder var1) {
      super(var1);
   }

   public static MockHttpTransport newMockHttpTransportWithSampleTokenResponse() {
      MockLowLevelHttpResponse var0 = (new MockLowLevelHttpResponse()).setContentType("application/json; charset=UTF-8").setContent(DEFAULT_TOKEN_RESPONSE_JSON);
      MockLowLevelHttpRequest var1 = (new MockLowLevelHttpRequest()).setResponse(var0);
      return (new MockHttpTransport.Builder()).setLowLevelHttpRequest(var1).build();
   }

   public static class Builder extends GoogleCredential.Builder {
      public MockGoogleCredential build() {
         if (this.getTransport() == null) {
            this.setTransport((new MockHttpTransport.Builder()).build());
         }

         if (this.getClientAuthentication() == null) {
            this.setClientAuthentication(new MockGoogleCredential.MockClientAuthentication());
         }

         if (this.getJsonFactory() == null) {
            this.setJsonFactory(new JacksonFactory());
         }

         return new MockGoogleCredential(this);
      }

      public MockGoogleCredential.Builder setClientAuthentication(HttpExecuteInterceptor var1) {
         return (MockGoogleCredential.Builder)super.setClientAuthentication(var1);
      }

      public MockGoogleCredential.Builder setClock(Clock var1) {
         return (MockGoogleCredential.Builder)super.setClock(var1);
      }

      public MockGoogleCredential.Builder setJsonFactory(JsonFactory var1) {
         return (MockGoogleCredential.Builder)super.setJsonFactory(var1);
      }

      public MockGoogleCredential.Builder setTransport(HttpTransport var1) {
         return (MockGoogleCredential.Builder)super.setTransport(var1);
      }
   }

   private static class MockClientAuthentication implements HttpExecuteInterceptor {
      private MockClientAuthentication() {
      }

      // $FF: synthetic method
      MockClientAuthentication(Object var1) {
         this();
      }

      public void intercept(HttpRequest var1) throws IOException {
      }
   }
}

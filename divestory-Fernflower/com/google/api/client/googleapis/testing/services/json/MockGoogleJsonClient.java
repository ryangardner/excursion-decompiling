package com.google.api.client.googleapis.testing.services.json;

import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

public class MockGoogleJsonClient extends AbstractGoogleJsonClient {
   protected MockGoogleJsonClient(MockGoogleJsonClient.Builder var1) {
      super(var1);
   }

   public MockGoogleJsonClient(HttpTransport var1, JsonFactory var2, String var3, String var4, HttpRequestInitializer var5, boolean var6) {
      this(new MockGoogleJsonClient.Builder(var1, var2, var3, var4, var5, var6));
   }

   public static class Builder extends AbstractGoogleJsonClient.Builder {
      public Builder(HttpTransport var1, JsonFactory var2, String var3, String var4, HttpRequestInitializer var5, boolean var6) {
         super(var1, var2, var3, var4, var5, var6);
      }

      public MockGoogleJsonClient build() {
         return new MockGoogleJsonClient(this);
      }

      public MockGoogleJsonClient.Builder setApplicationName(String var1) {
         return (MockGoogleJsonClient.Builder)super.setApplicationName(var1);
      }

      public MockGoogleJsonClient.Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer var1) {
         return (MockGoogleJsonClient.Builder)super.setGoogleClientRequestInitializer(var1);
      }

      public MockGoogleJsonClient.Builder setHttpRequestInitializer(HttpRequestInitializer var1) {
         return (MockGoogleJsonClient.Builder)super.setHttpRequestInitializer(var1);
      }

      public MockGoogleJsonClient.Builder setRootUrl(String var1) {
         return (MockGoogleJsonClient.Builder)super.setRootUrl(var1);
      }

      public MockGoogleJsonClient.Builder setServicePath(String var1) {
         return (MockGoogleJsonClient.Builder)super.setServicePath(var1);
      }

      public MockGoogleJsonClient.Builder setSuppressAllChecks(boolean var1) {
         return (MockGoogleJsonClient.Builder)super.setSuppressAllChecks(var1);
      }

      public MockGoogleJsonClient.Builder setSuppressPatternChecks(boolean var1) {
         return (MockGoogleJsonClient.Builder)super.setSuppressPatternChecks(var1);
      }

      public MockGoogleJsonClient.Builder setSuppressRequiredParameterChecks(boolean var1) {
         return (MockGoogleJsonClient.Builder)super.setSuppressRequiredParameterChecks(var1);
      }
   }
}

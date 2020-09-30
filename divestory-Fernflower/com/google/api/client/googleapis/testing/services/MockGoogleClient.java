package com.google.api.client.googleapis.testing.services;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.ObjectParser;

public class MockGoogleClient extends AbstractGoogleClient {
   protected MockGoogleClient(MockGoogleClient.Builder var1) {
      super(var1);
   }

   public MockGoogleClient(HttpTransport var1, String var2, String var3, ObjectParser var4, HttpRequestInitializer var5) {
      this(new MockGoogleClient.Builder(var1, var2, var3, var4, var5));
   }

   public static class Builder extends AbstractGoogleClient.Builder {
      public Builder(HttpTransport var1, String var2, String var3, ObjectParser var4, HttpRequestInitializer var5) {
         super(var1, var2, var3, var4, var5);
      }

      public MockGoogleClient build() {
         return new MockGoogleClient(this);
      }

      public MockGoogleClient.Builder setApplicationName(String var1) {
         return (MockGoogleClient.Builder)super.setApplicationName(var1);
      }

      public MockGoogleClient.Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer var1) {
         return (MockGoogleClient.Builder)super.setGoogleClientRequestInitializer(var1);
      }

      public MockGoogleClient.Builder setHttpRequestInitializer(HttpRequestInitializer var1) {
         return (MockGoogleClient.Builder)super.setHttpRequestInitializer(var1);
      }

      public MockGoogleClient.Builder setRootUrl(String var1) {
         return (MockGoogleClient.Builder)super.setRootUrl(var1);
      }

      public MockGoogleClient.Builder setServicePath(String var1) {
         return (MockGoogleClient.Builder)super.setServicePath(var1);
      }

      public MockGoogleClient.Builder setSuppressAllChecks(boolean var1) {
         return (MockGoogleClient.Builder)super.setSuppressAllChecks(var1);
      }

      public MockGoogleClient.Builder setSuppressPatternChecks(boolean var1) {
         return (MockGoogleClient.Builder)super.setSuppressPatternChecks(var1);
      }

      public MockGoogleClient.Builder setSuppressRequiredParameterChecks(boolean var1) {
         return (MockGoogleClient.Builder)super.setSuppressRequiredParameterChecks(var1);
      }
   }
}

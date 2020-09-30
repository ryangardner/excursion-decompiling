package com.google.api.client.googleapis.testing.services;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;

public class MockGoogleClientRequest<T> extends AbstractGoogleClientRequest<T> {
   public MockGoogleClientRequest(AbstractGoogleClient var1, String var2, String var3, HttpContent var4, Class<T> var5) {
      super(var1, var2, var3, var4, var5);
   }

   public MockGoogleClientRequest<T> set(String var1, Object var2) {
      return (MockGoogleClientRequest)super.set(var1, var2);
   }

   public MockGoogleClientRequest<T> setDisableGZipContent(boolean var1) {
      return (MockGoogleClientRequest)super.setDisableGZipContent(var1);
   }

   public MockGoogleClientRequest<T> setRequestHeaders(HttpHeaders var1) {
      return (MockGoogleClientRequest)super.setRequestHeaders(var1);
   }
}

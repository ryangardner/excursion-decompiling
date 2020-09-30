package com.google.api.client.googleapis.testing.services.json;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.HttpHeaders;

public class MockGoogleJsonClientRequest<T> extends AbstractGoogleJsonClientRequest<T> {
   public MockGoogleJsonClientRequest(AbstractGoogleJsonClient var1, String var2, String var3, Object var4, Class<T> var5) {
      super(var1, var2, var3, var4, var5);
   }

   public MockGoogleJsonClient getAbstractGoogleClient() {
      return (MockGoogleJsonClient)super.getAbstractGoogleClient();
   }

   public MockGoogleJsonClientRequest<T> setDisableGZipContent(boolean var1) {
      return (MockGoogleJsonClientRequest)super.setDisableGZipContent(var1);
   }

   public MockGoogleJsonClientRequest<T> setRequestHeaders(HttpHeaders var1) {
      return (MockGoogleJsonClientRequest)super.setRequestHeaders(var1);
   }
}

package com.google.api.client.testing.http;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import java.io.IOException;

public class MockHttpUnsuccessfulResponseHandler implements HttpUnsuccessfulResponseHandler {
   private boolean isCalled;
   private boolean successfullyHandleResponse;

   public MockHttpUnsuccessfulResponseHandler(boolean var1) {
      this.successfullyHandleResponse = var1;
   }

   public boolean handleResponse(HttpRequest var1, HttpResponse var2, boolean var3) throws IOException {
      this.isCalled = true;
      return this.successfullyHandleResponse;
   }

   public boolean isCalled() {
      return this.isCalled;
   }
}

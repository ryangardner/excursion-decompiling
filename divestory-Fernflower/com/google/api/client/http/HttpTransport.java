package com.google.api.client.http;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public abstract class HttpTransport {
   static final Logger LOGGER = Logger.getLogger(HttpTransport.class.getName());
   private static final String[] SUPPORTED_METHODS;

   static {
      String[] var0 = new String[]{"DELETE", "GET", "POST", "PUT"};
      SUPPORTED_METHODS = var0;
      Arrays.sort(var0);
   }

   HttpRequest buildRequest() {
      return new HttpRequest(this, (String)null);
   }

   protected abstract LowLevelHttpRequest buildRequest(String var1, String var2) throws IOException;

   public final HttpRequestFactory createRequestFactory() {
      return this.createRequestFactory((HttpRequestInitializer)null);
   }

   public final HttpRequestFactory createRequestFactory(HttpRequestInitializer var1) {
      return new HttpRequestFactory(this, var1);
   }

   public void shutdown() throws IOException {
   }

   public boolean supportsMethod(String var1) throws IOException {
      boolean var2;
      if (Arrays.binarySearch(SUPPORTED_METHODS, var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}

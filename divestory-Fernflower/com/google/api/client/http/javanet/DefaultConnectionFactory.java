package com.google.api.client.http.javanet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class DefaultConnectionFactory implements ConnectionFactory {
   private final Proxy proxy;

   public DefaultConnectionFactory() {
      this((Proxy)null);
   }

   public DefaultConnectionFactory(Proxy var1) {
      this.proxy = var1;
   }

   public HttpURLConnection openConnection(URL var1) throws IOException {
      Proxy var2 = this.proxy;
      URLConnection var3;
      if (var2 == null) {
         var3 = var1.openConnection();
      } else {
         var3 = var1.openConnection(var2);
      }

      return (HttpURLConnection)var3;
   }
}

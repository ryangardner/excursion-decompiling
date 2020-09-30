package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.params.HttpProtocolParams;

public class RequestUserAgent implements HttpRequestInterceptor {
   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (!var1.containsHeader("User-Agent")) {
            String var3 = HttpProtocolParams.getUserAgent(var1.getParams());
            if (var3 != null) {
               var1.addHeader("User-Agent", var3);
            }
         }

      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }
}

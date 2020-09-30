package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;

public class RequestConnControl implements HttpRequestInterceptor {
   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (!var1.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            if (!var1.containsHeader("Connection")) {
               var1.addHeader("Connection", "Keep-Alive");
            }

         }
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }
}

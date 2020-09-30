package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;

public class ResponseServer implements HttpResponseInterceptor {
   public void process(HttpResponse var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (!var1.containsHeader("Server")) {
            String var3 = (String)var1.getParams().getParameter("http.origin-server");
            if (var3 != null) {
               var1.addHeader("Server", var3);
            }
         }

      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }
}

package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;

public class ResponseDate implements HttpResponseInterceptor {
   private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();

   public void process(HttpResponse var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (var1.getStatusLine().getStatusCode() >= 200 && !var1.containsHeader("Date")) {
            var1.setHeader("Date", DATE_GENERATOR.getCurrentDate());
         }

      } else {
         throw new IllegalArgumentException("HTTP response may not be null.");
      }
   }
}

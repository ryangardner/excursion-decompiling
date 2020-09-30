package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;

public class RequestDate implements HttpRequestInterceptor {
   private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();

   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (var1 instanceof HttpEntityEnclosingRequest && !var1.containsHeader("Date")) {
            var1.setHeader("Date", DATE_GENERATOR.getCurrentDate());
         }

      } else {
         throw new IllegalArgumentException("HTTP request may not be null.");
      }
   }
}

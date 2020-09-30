package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

public class RequestDefaultHeaders implements HttpRequestInterceptor {
   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (!var1.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            Collection var3 = (Collection)var1.getParams().getParameter("http.default-headers");
            if (var3 != null) {
               Iterator var4 = var3.iterator();

               while(var4.hasNext()) {
                  var1.addHeader((Header)var4.next());
               }
            }

         }
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }
}

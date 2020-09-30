package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.protocol.HttpContext;

public class RequestClientConnControl implements HttpRequestInterceptor {
   private static final String PROXY_CONN_DIRECTIVE = "Proxy-Connection";
   private final Log log = LogFactory.getLog(this.getClass());

   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("HTTP request may not be null");
      } else if (var1.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
         var1.setHeader("Proxy-Connection", "Keep-Alive");
      } else {
         HttpRoutedConnection var3 = (HttpRoutedConnection)var2.getAttribute("http.connection");
         if (var3 == null) {
            this.log.debug("HTTP connection not set in the context");
         } else {
            HttpRoute var4 = var3.getRoute();
            if ((var4.getHopCount() == 1 || var4.isTunnelled()) && !var1.containsHeader("Connection")) {
               var1.addHeader("Connection", "Keep-Alive");
            }

            if (var4.getHopCount() == 2 && !var4.isTunnelled() && !var1.containsHeader("Proxy-Connection")) {
               var1.addHeader("Proxy-Connection", "Keep-Alive");
            }

         }
      }
   }
}

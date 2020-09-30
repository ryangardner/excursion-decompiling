package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;

public class ResponseConnControl implements HttpResponseInterceptor {
   public void process(HttpResponse var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (var2 == null) {
            throw new IllegalArgumentException("HTTP context may not be null");
         } else {
            int var3 = var1.getStatusLine().getStatusCode();
            if (var3 != 400 && var3 != 408 && var3 != 411 && var3 != 413 && var3 != 414 && var3 != 503 && var3 != 501) {
               HttpEntity var4 = var1.getEntity();
               if (var4 != null) {
                  ProtocolVersion var5 = var1.getStatusLine().getProtocolVersion();
                  if (var4.getContentLength() < 0L && (!var4.isChunked() || var5.lessEquals(HttpVersion.HTTP_1_0))) {
                     var1.setHeader("Connection", "Close");
                     return;
                  }
               }

               HttpRequest var6 = (HttpRequest)var2.getAttribute("http.request");
               if (var6 != null) {
                  Header var7 = var6.getFirstHeader("Connection");
                  if (var7 != null) {
                     var1.setHeader("Connection", var7.getValue());
                  }
               }

            } else {
               var1.setHeader("Connection", "Close");
            }
         }
      } else {
         throw new IllegalArgumentException("HTTP response may not be null");
      }
   }
}

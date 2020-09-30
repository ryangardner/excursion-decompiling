package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.params.HttpProtocolParams;

public class RequestExpectContinue implements HttpRequestInterceptor {
   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (var1 instanceof HttpEntityEnclosingRequest) {
            HttpEntity var3 = ((HttpEntityEnclosingRequest)var1).getEntity();
            if (var3 != null && var3.getContentLength() != 0L) {
               ProtocolVersion var4 = var1.getRequestLine().getProtocolVersion();
               if (HttpProtocolParams.useExpectContinue(var1.getParams()) && !var4.lessEquals(HttpVersion.HTTP_1_0)) {
                  var1.addHeader("Expect", "100-continue");
               }
            }
         }

      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }
}

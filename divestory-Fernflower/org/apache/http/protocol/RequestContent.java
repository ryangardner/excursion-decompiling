package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;

public class RequestContent implements HttpRequestInterceptor {
   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("HTTP request may not be null");
      } else {
         if (var1 instanceof HttpEntityEnclosingRequest) {
            if (var1.containsHeader("Transfer-Encoding")) {
               throw new ProtocolException("Transfer-encoding header already present");
            }

            if (var1.containsHeader("Content-Length")) {
               throw new ProtocolException("Content-Length header already present");
            }

            ProtocolVersion var5 = var1.getRequestLine().getProtocolVersion();
            HttpEntity var3 = ((HttpEntityEnclosingRequest)var1).getEntity();
            if (var3 == null) {
               var1.addHeader("Content-Length", "0");
               return;
            }

            if (!var3.isChunked() && var3.getContentLength() >= 0L) {
               var1.addHeader("Content-Length", Long.toString(var3.getContentLength()));
            } else {
               if (var5.lessEquals(HttpVersion.HTTP_1_0)) {
                  StringBuffer var4 = new StringBuffer();
                  var4.append("Chunked transfer encoding not allowed for ");
                  var4.append(var5);
                  throw new ProtocolException(var4.toString());
               }

               var1.addHeader("Transfer-Encoding", "chunked");
            }

            if (var3.getContentType() != null && !var1.containsHeader("Content-Type")) {
               var1.addHeader(var3.getContentType());
            }

            if (var3.getContentEncoding() != null && !var1.containsHeader("Content-Encoding")) {
               var1.addHeader(var3.getContentEncoding());
            }
         }

      }
   }
}

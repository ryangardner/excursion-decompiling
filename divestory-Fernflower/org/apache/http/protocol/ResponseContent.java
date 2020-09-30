package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;

public class ResponseContent implements HttpResponseInterceptor {
   public void process(HttpResponse var1, HttpContext var2) throws HttpException, IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("HTTP response may not be null");
      } else if (!var1.containsHeader("Transfer-Encoding")) {
         if (var1.containsHeader("Content-Length")) {
            throw new ProtocolException("Content-Length header already present");
         } else {
            ProtocolVersion var7 = var1.getStatusLine().getProtocolVersion();
            HttpEntity var3 = var1.getEntity();
            if (var3 != null) {
               long var4 = var3.getContentLength();
               if (var3.isChunked() && !var7.lessEquals(HttpVersion.HTTP_1_0)) {
                  var1.addHeader("Transfer-Encoding", "chunked");
               } else if (var4 >= 0L) {
                  var1.addHeader("Content-Length", Long.toString(var3.getContentLength()));
               }

               if (var3.getContentType() != null && !var1.containsHeader("Content-Type")) {
                  var1.addHeader(var3.getContentType());
               }

               if (var3.getContentEncoding() != null && !var1.containsHeader("Content-Encoding")) {
                  var1.addHeader(var3.getContentEncoding());
               }
            } else {
               int var6 = var1.getStatusLine().getStatusCode();
               if (var6 != 204 && var6 != 304 && var6 != 205) {
                  var1.addHeader("Content-Length", "0");
               }
            }

         }
      } else {
         throw new ProtocolException("Transfer-encoding header already present");
      }
   }
}

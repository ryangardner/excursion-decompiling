package com.google.api.client.googleapis.batch;

import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

class HttpRequestContent extends AbstractHttpContent {
   private static final String HTTP_VERSION = "HTTP/1.1";
   static final String NEWLINE = "\r\n";
   private final HttpRequest request;

   HttpRequestContent(HttpRequest var1) {
      super("application/http");
      this.request = var1;
   }

   public void writeTo(OutputStream var1) throws IOException {
      OutputStreamWriter var2 = new OutputStreamWriter(var1, this.getCharset());
      var2.write(this.request.getRequestMethod());
      var2.write(" ");
      var2.write(this.request.getUrl().build());
      var2.write(" ");
      var2.write("HTTP/1.1");
      var2.write("\r\n");
      HttpHeaders var3 = new HttpHeaders();
      var3.fromHttpHeaders(this.request.getHeaders());
      var3.setAcceptEncoding((String)null).setUserAgent((String)null).setContentEncoding((String)null).setContentType((String)null).setContentLength((Long)null);
      HttpContent var4 = this.request.getContent();
      if (var4 != null) {
         var3.setContentType(var4.getType());
         long var5 = var4.getLength();
         if (var5 != -1L) {
            var3.setContentLength(var5);
         }
      }

      HttpHeaders.serializeHeadersForMultipartRequests(var3, (StringBuilder)null, (Logger)null, var2);
      var2.write("\r\n");
      var2.flush();
      if (var4 != null) {
         var4.writeTo(var1);
      }

   }
}

package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

public class RequestAcceptEncoding implements HttpRequestInterceptor {
   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      var1.addHeader("Accept-Encoding", "gzip,deflate");
   }
}

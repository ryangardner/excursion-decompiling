package org.apache.http.protocol;

import java.io.IOException;
import java.net.InetAddress;
import org.apache.http.HttpConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpInetConnection;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;

public class RequestTargetHost implements HttpRequestInterceptor {
   public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
      if (var1 != null) {
         if (var2 != null) {
            ProtocolVersion var3 = var1.getRequestLine().getProtocolVersion();
            if (!var1.getRequestLine().getMethod().equalsIgnoreCase("CONNECT") || !var3.lessEquals(HttpVersion.HTTP_1_0)) {
               if (!var1.containsHeader("Host")) {
                  HttpHost var4 = (HttpHost)var2.getAttribute("http.target_host");
                  HttpHost var5 = var4;
                  if (var4 == null) {
                     HttpConnection var9 = (HttpConnection)var2.getAttribute("http.connection");
                     HttpHost var7 = var4;
                     if (var9 instanceof HttpInetConnection) {
                        HttpInetConnection var8 = (HttpInetConnection)var9;
                        InetAddress var10 = var8.getRemoteAddress();
                        int var6 = var8.getRemotePort();
                        var7 = var4;
                        if (var10 != null) {
                           var7 = new HttpHost(var10.getHostName(), var6);
                        }
                     }

                     var5 = var7;
                     if (var7 == null) {
                        if (var3.lessEquals(HttpVersion.HTTP_1_0)) {
                           return;
                        }

                        throw new ProtocolException("Target host missing");
                     }
                  }

                  var1.addHeader("Host", var5.toHostString());
               }

            }
         } else {
            throw new IllegalArgumentException("HTTP context may not be null");
         }
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }
}

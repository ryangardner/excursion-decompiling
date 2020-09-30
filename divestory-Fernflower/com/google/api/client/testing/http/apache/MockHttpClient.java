package com.google.api.client.testing.http.apache;

import com.google.api.client.util.Preconditions;
import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RequestDirector;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;

public class MockHttpClient extends DefaultHttpClient {
   int responseCode;

   protected RequestDirector createClientRequestDirector(HttpRequestExecutor var1, ClientConnectionManager var2, ConnectionReuseStrategy var3, ConnectionKeepAliveStrategy var4, HttpRoutePlanner var5, HttpProcessor var6, HttpRequestRetryHandler var7, RedirectHandler var8, AuthenticationHandler var9, AuthenticationHandler var10, UserTokenHandler var11, HttpParams var12) {
      return new RequestDirector() {
         public HttpResponse execute(HttpHost var1, HttpRequest var2, HttpContext var3) throws HttpException, IOException {
            return new BasicHttpResponse(HttpVersion.HTTP_1_1, MockHttpClient.this.responseCode, (String)null);
         }
      };
   }

   public final int getResponseCode() {
      return this.responseCode;
   }

   public MockHttpClient setResponseCode(int var1) {
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      this.responseCode = var1;
      return this;
   }
}

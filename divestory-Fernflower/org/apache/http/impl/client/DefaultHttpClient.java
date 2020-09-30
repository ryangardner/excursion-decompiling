package org.apache.http.impl.client;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.client.protocol.RequestAuthCache;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.client.protocol.RequestProxyAuthentication;
import org.apache.http.client.protocol.RequestTargetAuthentication;
import org.apache.http.client.protocol.ResponseAuthCache;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.VersionInfo;

public class DefaultHttpClient extends AbstractHttpClient {
   public DefaultHttpClient() {
      super((ClientConnectionManager)null, (HttpParams)null);
   }

   public DefaultHttpClient(ClientConnectionManager var1) {
      super(var1, (HttpParams)null);
   }

   public DefaultHttpClient(ClientConnectionManager var1, HttpParams var2) {
      super(var1, var2);
   }

   public DefaultHttpClient(HttpParams var1) {
      super((ClientConnectionManager)null, var1);
   }

   public static void setDefaultHttpParams(HttpParams var0) {
      HttpProtocolParams.setVersion(var0, HttpVersion.HTTP_1_1);
      HttpProtocolParams.setContentCharset(var0, "ISO-8859-1");
      HttpConnectionParams.setTcpNoDelay(var0, true);
      HttpConnectionParams.setSocketBufferSize(var0, 8192);
      VersionInfo var1 = VersionInfo.loadVersionInfo("org.apache.http.client", DefaultHttpClient.class.getClassLoader());
      String var3;
      if (var1 != null) {
         var3 = var1.getRelease();
      } else {
         var3 = "UNAVAILABLE";
      }

      StringBuilder var2 = new StringBuilder();
      var2.append("Apache-HttpClient/");
      var2.append(var3);
      var2.append(" (java 1.5)");
      HttpProtocolParams.setUserAgent(var0, var2.toString());
   }

   protected HttpParams createHttpParams() {
      SyncBasicHttpParams var1 = new SyncBasicHttpParams();
      setDefaultHttpParams(var1);
      return var1;
   }

   protected BasicHttpProcessor createHttpProcessor() {
      BasicHttpProcessor var1 = new BasicHttpProcessor();
      var1.addInterceptor((HttpRequestInterceptor)(new RequestDefaultHeaders()));
      var1.addInterceptor((HttpRequestInterceptor)(new RequestContent()));
      var1.addInterceptor((HttpRequestInterceptor)(new RequestTargetHost()));
      var1.addInterceptor((HttpRequestInterceptor)(new RequestClientConnControl()));
      var1.addInterceptor((HttpRequestInterceptor)(new RequestUserAgent()));
      var1.addInterceptor((HttpRequestInterceptor)(new RequestExpectContinue()));
      var1.addInterceptor((HttpRequestInterceptor)(new RequestAddCookies()));
      var1.addInterceptor((HttpResponseInterceptor)(new ResponseProcessCookies()));
      var1.addInterceptor((HttpRequestInterceptor)(new RequestAuthCache()));
      var1.addInterceptor((HttpResponseInterceptor)(new ResponseAuthCache()));
      var1.addInterceptor((HttpRequestInterceptor)(new RequestTargetAuthentication()));
      var1.addInterceptor((HttpRequestInterceptor)(new RequestProxyAuthentication()));
      return var1;
   }
}

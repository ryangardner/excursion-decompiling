package com.google.api.client.http.apache;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.SslUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

@Deprecated
public final class ApacheHttpTransport extends HttpTransport {
   private final HttpClient httpClient;

   public ApacheHttpTransport() {
      this(newDefaultHttpClient());
   }

   public ApacheHttpTransport(HttpClient var1) {
      this.httpClient = var1;
      HttpParams var2 = var1.getParams();
      HttpParams var3 = var2;
      if (var2 == null) {
         var3 = newDefaultHttpClient().getParams();
      }

      HttpProtocolParams.setVersion(var3, HttpVersion.HTTP_1_1);
      var3.setBooleanParameter("http.protocol.handle-redirects", false);
   }

   public static DefaultHttpClient newDefaultHttpClient() {
      return newDefaultHttpClient(SSLSocketFactory.getSocketFactory(), newDefaultHttpParams(), ProxySelector.getDefault());
   }

   static DefaultHttpClient newDefaultHttpClient(SSLSocketFactory var0, HttpParams var1, ProxySelector var2) {
      SchemeRegistry var3 = new SchemeRegistry();
      var3.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
      var3.register(new Scheme("https", var0, 443));
      DefaultHttpClient var4 = new DefaultHttpClient(new ThreadSafeClientConnManager(var1, var3), var1);
      var4.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
      if (var2 != null) {
         var4.setRoutePlanner(new ProxySelectorRoutePlanner(var3, var2));
      }

      return var4;
   }

   static HttpParams newDefaultHttpParams() {
      BasicHttpParams var0 = new BasicHttpParams();
      HttpConnectionParams.setStaleCheckingEnabled(var0, false);
      HttpConnectionParams.setSocketBufferSize(var0, 8192);
      ConnManagerParams.setMaxTotalConnections(var0, 200);
      ConnManagerParams.setMaxConnectionsPerRoute(var0, new ConnPerRouteBean(20));
      return var0;
   }

   protected ApacheHttpRequest buildRequest(String var1, String var2) {
      Object var3;
      if (var1.equals("DELETE")) {
         var3 = new HttpDelete(var2);
      } else if (var1.equals("GET")) {
         var3 = new HttpGet(var2);
      } else if (var1.equals("HEAD")) {
         var3 = new HttpHead(var2);
      } else if (var1.equals("POST")) {
         var3 = new HttpPost(var2);
      } else if (var1.equals("PUT")) {
         var3 = new HttpPut(var2);
      } else if (var1.equals("TRACE")) {
         var3 = new HttpTrace(var2);
      } else if (var1.equals("OPTIONS")) {
         var3 = new HttpOptions(var2);
      } else {
         var3 = new HttpExtensionMethod(var1, var2);
      }

      return new ApacheHttpRequest(this.httpClient, (HttpRequestBase)var3);
   }

   public HttpClient getHttpClient() {
      return this.httpClient;
   }

   public void shutdown() {
      this.httpClient.getConnectionManager().shutdown();
   }

   public boolean supportsMethod(String var1) {
      return true;
   }

   public static final class Builder {
      private HttpParams params = ApacheHttpTransport.newDefaultHttpParams();
      private ProxySelector proxySelector = ProxySelector.getDefault();
      private SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();

      public ApacheHttpTransport build() {
         return new ApacheHttpTransport(ApacheHttpTransport.newDefaultHttpClient(this.socketFactory, this.params, this.proxySelector));
      }

      public ApacheHttpTransport.Builder doNotValidateCertificate() throws GeneralSecurityException {
         SSLSocketFactoryExtension var1 = new SSLSocketFactoryExtension(SslUtils.trustAllSSLContext());
         this.socketFactory = var1;
         var1.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
         return this;
      }

      public HttpParams getHttpParams() {
         return this.params;
      }

      public SSLSocketFactory getSSLSocketFactory() {
         return this.socketFactory;
      }

      public ApacheHttpTransport.Builder setProxy(HttpHost var1) {
         ConnRouteParams.setDefaultProxy(this.params, var1);
         if (var1 != null) {
            this.proxySelector = null;
         }

         return this;
      }

      public ApacheHttpTransport.Builder setProxySelector(ProxySelector var1) {
         this.proxySelector = var1;
         if (var1 != null) {
            ConnRouteParams.setDefaultProxy(this.params, (HttpHost)null);
         }

         return this;
      }

      public ApacheHttpTransport.Builder setSocketFactory(SSLSocketFactory var1) {
         this.socketFactory = (SSLSocketFactory)Preconditions.checkNotNull(var1);
         return this;
      }

      public ApacheHttpTransport.Builder trustCertificates(KeyStore var1) throws GeneralSecurityException {
         SSLContext var2 = SslUtils.getTlsSslContext();
         SslUtils.initSslContext(var2, var1, SslUtils.getPkixTrustManagerFactory());
         return this.setSocketFactory(new SSLSocketFactoryExtension(var2));
      }

      public ApacheHttpTransport.Builder trustCertificatesFromJavaKeyStore(InputStream var1, String var2) throws GeneralSecurityException, IOException {
         KeyStore var3 = SecurityUtils.getJavaKeyStore();
         SecurityUtils.loadKeyStore(var3, var1, var2);
         return this.trustCertificates(var3);
      }

      public ApacheHttpTransport.Builder trustCertificatesFromStream(InputStream var1) throws GeneralSecurityException, IOException {
         KeyStore var2 = SecurityUtils.getJavaKeyStore();
         var2.load((InputStream)null, (char[])null);
         SecurityUtils.loadKeyStoreFromCertificates(var2, SecurityUtils.getX509CertificateFactory(), var1);
         return this.trustCertificates(var2);
      }
   }
}

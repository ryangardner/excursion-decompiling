package com.google.api.client.http.javanet;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.SslUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.Proxy.Type;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public final class NetHttpTransport extends HttpTransport {
   private static final String SHOULD_USE_PROXY_FLAG = "com.google.api.client.should_use_proxy";
   private static final String[] SUPPORTED_METHODS;
   private final ConnectionFactory connectionFactory;
   private final HostnameVerifier hostnameVerifier;
   private final SSLSocketFactory sslSocketFactory;

   static {
      String[] var0 = new String[]{"DELETE", "GET", "HEAD", "OPTIONS", "POST", "PUT", "TRACE"};
      SUPPORTED_METHODS = var0;
      Arrays.sort(var0);
   }

   public NetHttpTransport() {
      this((ConnectionFactory)((ConnectionFactory)null), (SSLSocketFactory)null, (HostnameVerifier)null);
   }

   NetHttpTransport(ConnectionFactory var1, SSLSocketFactory var2, HostnameVerifier var3) {
      this.connectionFactory = this.getConnectionFactory(var1);
      this.sslSocketFactory = var2;
      this.hostnameVerifier = var3;
   }

   NetHttpTransport(Proxy var1, SSLSocketFactory var2, HostnameVerifier var3) {
      this((ConnectionFactory)(new DefaultConnectionFactory(var1)), var2, var3);
   }

   private static Proxy defaultProxy() {
      return new Proxy(Type.HTTP, new InetSocketAddress(System.getProperty("https.proxyHost"), Integer.parseInt(System.getProperty("https.proxyPort"))));
   }

   private ConnectionFactory getConnectionFactory(ConnectionFactory var1) {
      Object var2 = var1;
      if (var1 == null) {
         if (System.getProperty("com.google.api.client.should_use_proxy") != null) {
            return new DefaultConnectionFactory(defaultProxy());
         }

         var2 = new DefaultConnectionFactory();
      }

      return (ConnectionFactory)var2;
   }

   protected NetHttpRequest buildRequest(String var1, String var2) throws IOException {
      Preconditions.checkArgument(this.supportsMethod(var1), "HTTP method %s not supported", var1);
      URL var5 = new URL(var2);
      HttpURLConnection var6 = this.connectionFactory.openConnection(var5);
      var6.setRequestMethod(var1);
      if (var6 instanceof HttpsURLConnection) {
         HttpsURLConnection var4 = (HttpsURLConnection)var6;
         HostnameVerifier var3 = this.hostnameVerifier;
         if (var3 != null) {
            var4.setHostnameVerifier(var3);
         }

         SSLSocketFactory var7 = this.sslSocketFactory;
         if (var7 != null) {
            var4.setSSLSocketFactory(var7);
         }
      }

      return new NetHttpRequest(var6);
   }

   public boolean supportsMethod(String var1) {
      boolean var2;
      if (Arrays.binarySearch(SUPPORTED_METHODS, var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static final class Builder {
      private ConnectionFactory connectionFactory;
      private HostnameVerifier hostnameVerifier;
      private Proxy proxy;
      private SSLSocketFactory sslSocketFactory;

      public NetHttpTransport build() {
         if (System.getProperty("com.google.api.client.should_use_proxy") != null) {
            this.setProxy(NetHttpTransport.defaultProxy());
         }

         NetHttpTransport var1;
         if (this.proxy == null) {
            var1 = new NetHttpTransport(this.connectionFactory, this.sslSocketFactory, this.hostnameVerifier);
         } else {
            var1 = new NetHttpTransport(this.proxy, this.sslSocketFactory, this.hostnameVerifier);
         }

         return var1;
      }

      public NetHttpTransport.Builder doNotValidateCertificate() throws GeneralSecurityException {
         this.hostnameVerifier = SslUtils.trustAllHostnameVerifier();
         this.sslSocketFactory = SslUtils.trustAllSSLContext().getSocketFactory();
         return this;
      }

      public HostnameVerifier getHostnameVerifier() {
         return this.hostnameVerifier;
      }

      public SSLSocketFactory getSslSocketFactory() {
         return this.sslSocketFactory;
      }

      public NetHttpTransport.Builder setConnectionFactory(ConnectionFactory var1) {
         this.connectionFactory = var1;
         return this;
      }

      public NetHttpTransport.Builder setHostnameVerifier(HostnameVerifier var1) {
         this.hostnameVerifier = var1;
         return this;
      }

      public NetHttpTransport.Builder setProxy(Proxy var1) {
         this.proxy = var1;
         return this;
      }

      public NetHttpTransport.Builder setSslSocketFactory(SSLSocketFactory var1) {
         this.sslSocketFactory = var1;
         return this;
      }

      public NetHttpTransport.Builder trustCertificates(KeyStore var1) throws GeneralSecurityException {
         SSLContext var2 = SslUtils.getTlsSslContext();
         SslUtils.initSslContext(var2, var1, SslUtils.getPkixTrustManagerFactory());
         return this.setSslSocketFactory(var2.getSocketFactory());
      }

      public NetHttpTransport.Builder trustCertificatesFromJavaKeyStore(InputStream var1, String var2) throws GeneralSecurityException, IOException {
         KeyStore var3 = SecurityUtils.getJavaKeyStore();
         SecurityUtils.loadKeyStore(var3, var1, var2);
         return this.trustCertificates(var3);
      }

      public NetHttpTransport.Builder trustCertificatesFromStream(InputStream var1) throws GeneralSecurityException, IOException {
         KeyStore var2 = SecurityUtils.getJavaKeyStore();
         var2.load((InputStream)null, (char[])null);
         SecurityUtils.loadKeyStoreFromCertificates(var2, SecurityUtils.getX509CertificateFactory(), var1);
         return this.trustCertificates(var2);
      }
   }
}

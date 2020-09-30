package com.google.api.client.googleapis.apache;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.util.SslUtils;
import java.io.IOException;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;

public final class GoogleApacheHttpTransport {
   private GoogleApacheHttpTransport() {
   }

   public static ApacheHttpTransport newTrustedTransport() throws GeneralSecurityException, IOException {
      SocketConfig var0 = SocketConfig.custom().setRcvBufSize(8192).setSndBufSize(8192).build();
      PoolingHttpClientConnectionManager var1 = new PoolingHttpClientConnectionManager(-1L, TimeUnit.MILLISECONDS);
      var1.setValidateAfterInactivity(-1);
      KeyStore var2 = GoogleUtils.getCertificateTrustStore();
      SSLContext var3 = SslUtils.getTlsSslContext();
      SslUtils.initSslContext(var3, var2, SslUtils.getPkixTrustManagerFactory());
      SSLConnectionSocketFactory var4 = new SSLConnectionSocketFactory(var3);
      return new ApacheHttpTransport(HttpClientBuilder.create().useSystemProperties().setSSLSocketFactory(var4).setDefaultSocketConfig(var0).setMaxConnTotal(200).setMaxConnPerRoute(20).setRoutePlanner(new SystemDefaultRoutePlanner(ProxySelector.getDefault())).setConnectionManager(var1).disableRedirectHandling().disableAutomaticRetries().build());
   }
}

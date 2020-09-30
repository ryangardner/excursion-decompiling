package com.google.api.client.util;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public final class SslUtils {
   private SslUtils() {
   }

   public static KeyManagerFactory getDefaultKeyManagerFactory() throws NoSuchAlgorithmException {
      return KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
   }

   public static TrustManagerFactory getDefaultTrustManagerFactory() throws NoSuchAlgorithmException {
      return TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
   }

   public static KeyManagerFactory getPkixKeyManagerFactory() throws NoSuchAlgorithmException {
      return KeyManagerFactory.getInstance("PKIX");
   }

   public static TrustManagerFactory getPkixTrustManagerFactory() throws NoSuchAlgorithmException {
      return TrustManagerFactory.getInstance("PKIX");
   }

   public static SSLContext getSslContext() throws NoSuchAlgorithmException {
      return SSLContext.getInstance("SSL");
   }

   public static SSLContext getTlsSslContext() throws NoSuchAlgorithmException {
      return SSLContext.getInstance("TLS");
   }

   public static SSLContext initSslContext(SSLContext var0, KeyStore var1, TrustManagerFactory var2) throws GeneralSecurityException {
      var2.init(var1);
      var0.init((KeyManager[])null, var2.getTrustManagers(), (SecureRandom)null);
      return var0;
   }

   public static HostnameVerifier trustAllHostnameVerifier() {
      return new HostnameVerifier() {
         public boolean verify(String var1, SSLSession var2) {
            return true;
         }
      };
   }

   public static SSLContext trustAllSSLContext() throws GeneralSecurityException {
      X509TrustManager var0 = new X509TrustManager() {
         public void checkClientTrusted(X509Certificate[] var1, String var2) throws CertificateException {
         }

         public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {
         }

         public X509Certificate[] getAcceptedIssuers() {
            return null;
         }
      };
      SSLContext var1 = getTlsSslContext();
      var1.init((KeyManager[])null, new TrustManager[]{var0}, (SecureRandom)null);
      return var1;
   }
}

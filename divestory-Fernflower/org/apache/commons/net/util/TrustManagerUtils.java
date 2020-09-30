package org.apache.commons.net.util;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public final class TrustManagerUtils {
   private static final X509TrustManager ACCEPT_ALL = new TrustManagerUtils.TrustManager(false);
   private static final X509TrustManager CHECK_SERVER_VALIDITY = new TrustManagerUtils.TrustManager(true);
   private static final X509Certificate[] EMPTY_X509CERTIFICATE_ARRAY = new X509Certificate[0];

   public static X509TrustManager getAcceptAllTrustManager() {
      return ACCEPT_ALL;
   }

   public static X509TrustManager getDefaultTrustManager(KeyStore var0) throws GeneralSecurityException {
      TrustManagerFactory var1 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      var1.init(var0);
      return (X509TrustManager)var1.getTrustManagers()[0];
   }

   public static X509TrustManager getValidateServerCertificateTrustManager() {
      return CHECK_SERVER_VALIDITY;
   }

   private static class TrustManager implements X509TrustManager {
      private final boolean checkServerValidity;

      TrustManager(boolean var1) {
         this.checkServerValidity = var1;
      }

      public void checkClientTrusted(X509Certificate[] var1, String var2) {
      }

      public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {
         if (this.checkServerValidity) {
            int var3 = var1.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               var1[var4].checkValidity();
            }
         }

      }

      public X509Certificate[] getAcceptedIssuers() {
         return TrustManagerUtils.EMPTY_X509CERTIFICATE_ARRAY;
      }
   }
}

package org.apache.http.conn.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

class TrustManagerDecorator implements X509TrustManager {
   private final X509TrustManager trustManager;
   private final TrustStrategy trustStrategy;

   TrustManagerDecorator(X509TrustManager var1, TrustStrategy var2) {
      this.trustManager = var1;
      this.trustStrategy = var2;
   }

   public void checkClientTrusted(X509Certificate[] var1, String var2) throws CertificateException {
      this.trustManager.checkClientTrusted(var1, var2);
   }

   public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {
      if (!this.trustStrategy.isTrusted(var1, var2)) {
         this.trustManager.checkServerTrusted(var1, var2);
      }

   }

   public X509Certificate[] getAcceptedIssuers() {
      return this.trustManager.getAcceptedIssuers();
   }
}

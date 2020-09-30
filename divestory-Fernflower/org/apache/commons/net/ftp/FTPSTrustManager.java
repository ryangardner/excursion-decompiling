package org.apache.commons.net.ftp;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

@Deprecated
public class FTPSTrustManager implements X509TrustManager {
   private static final X509Certificate[] EMPTY_X509CERTIFICATE_ARRAY = new X509Certificate[0];

   public void checkClientTrusted(X509Certificate[] var1, String var2) {
   }

   public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         var1[var4].checkValidity();
      }

   }

   public X509Certificate[] getAcceptedIssuers() {
      return EMPTY_X509CERTIFICATE_ARRAY;
   }
}

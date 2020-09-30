package org.apache.http.conn.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class TrustSelfSignedStrategy implements TrustStrategy {
   public boolean isTrusted(X509Certificate[] var1, String var2) throws CertificateException {
      int var3 = var1.length;
      boolean var4 = true;
      if (var3 != 1) {
         var4 = false;
      }

      return var4;
   }
}

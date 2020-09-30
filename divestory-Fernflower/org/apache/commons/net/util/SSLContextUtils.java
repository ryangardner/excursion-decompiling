package org.apache.commons.net.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class SSLContextUtils {
   private SSLContextUtils() {
   }

   public static SSLContext createSSLContext(String var0, KeyManager var1, TrustManager var2) throws IOException {
      TrustManager[] var3 = null;
      KeyManager[] var5;
      if (var1 == null) {
         var5 = null;
      } else {
         KeyManager[] var4 = new KeyManager[]{var1};
         var5 = var4;
      }

      TrustManager[] var6;
      if (var2 == null) {
         var6 = var3;
      } else {
         var3 = new TrustManager[]{var2};
         var6 = var3;
      }

      return createSSLContext(var0, var5, var6);
   }

   public static SSLContext createSSLContext(String var0, KeyManager[] var1, TrustManager[] var2) throws IOException {
      try {
         SSLContext var4 = SSLContext.getInstance(var0);
         var4.init(var1, var2, (SecureRandom)null);
         return var4;
      } catch (GeneralSecurityException var3) {
         IOException var5 = new IOException("Could not initialize SSL context");
         var5.initCause(var3);
         throw var5;
      }
   }
}

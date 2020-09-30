package org.apache.commons.net.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.net.ssl.KeyManager;
import javax.net.ssl.X509ExtendedKeyManager;
import org.apache.commons.net.io.Util;

public final class KeyManagerUtils {
   private static final String DEFAULT_STORE_TYPE = KeyStore.getDefaultType();

   private KeyManagerUtils() {
   }

   public static KeyManager createClientKeyManager(File var0, String var1) throws IOException, GeneralSecurityException {
      return createClientKeyManager(DEFAULT_STORE_TYPE, var0, var1, (String)null, var1);
   }

   public static KeyManager createClientKeyManager(File var0, String var1, String var2) throws IOException, GeneralSecurityException {
      return createClientKeyManager(DEFAULT_STORE_TYPE, var0, var1, var2, var1);
   }

   public static KeyManager createClientKeyManager(String var0, File var1, String var2, String var3, String var4) throws IOException, GeneralSecurityException {
      return createClientKeyManager(loadStore(var0, var1, var2), var3, var4);
   }

   public static KeyManager createClientKeyManager(KeyStore var0, String var1, String var2) throws GeneralSecurityException {
      if (var1 == null) {
         var1 = findAlias(var0);
      }

      return new KeyManagerUtils.X509KeyManager(new KeyManagerUtils.ClientKeyStore(var0, var1, var2));
   }

   private static String findAlias(KeyStore var0) throws KeyStoreException {
      Enumeration var1 = var0.aliases();

      String var2;
      do {
         if (!var1.hasMoreElements()) {
            throw new KeyStoreException("Cannot find a private key entry");
         }

         var2 = (String)var1.nextElement();
      } while(!var0.isKeyEntry(var2));

      return var2;
   }

   private static KeyStore loadStore(String var0, File var1, String var2) throws KeyStoreException, IOException, GeneralSecurityException {
      KeyStore var12 = KeyStore.getInstance(var0);
      Object var3 = null;
      boolean var9 = false;

      FileInputStream var4;
      try {
         var9 = true;
         var4 = new FileInputStream(var1);
         var9 = false;
      } finally {
         if (var9) {
            Util.closeQuietly((Closeable)var3);
         }
      }

      try {
         var12.load(var4, var2.toCharArray());
      } finally {
         ;
      }

      Util.closeQuietly((Closeable)var4);
      return var12;
   }

   private static class ClientKeyStore {
      private final X509Certificate[] certChain;
      private final PrivateKey key;
      private final String keyAlias;

      ClientKeyStore(KeyStore var1, String var2, String var3) throws GeneralSecurityException {
         this.keyAlias = var2;
         this.key = (PrivateKey)var1.getKey(var2, var3.toCharArray());
         Certificate[] var5 = var1.getCertificateChain(this.keyAlias);
         X509Certificate[] var6 = new X509Certificate[var5.length];

         for(int var4 = 0; var4 < var5.length; ++var4) {
            var6[var4] = (X509Certificate)var5[var4];
         }

         this.certChain = var6;
      }

      final String getAlias() {
         return this.keyAlias;
      }

      final X509Certificate[] getCertificateChain() {
         return this.certChain;
      }

      final PrivateKey getPrivateKey() {
         return this.key;
      }
   }

   private static class X509KeyManager extends X509ExtendedKeyManager {
      private final KeyManagerUtils.ClientKeyStore keyStore;

      X509KeyManager(KeyManagerUtils.ClientKeyStore var1) {
         this.keyStore = var1;
      }

      public String chooseClientAlias(String[] var1, Principal[] var2, Socket var3) {
         return this.keyStore.getAlias();
      }

      public String chooseServerAlias(String var1, Principal[] var2, Socket var3) {
         return null;
      }

      public X509Certificate[] getCertificateChain(String var1) {
         return this.keyStore.getCertificateChain();
      }

      public String[] getClientAliases(String var1, Principal[] var2) {
         return new String[]{this.keyStore.getAlias()};
      }

      public PrivateKey getPrivateKey(String var1) {
         return this.keyStore.getPrivateKey();
      }

      public String[] getServerAliases(String var1, Principal[] var2) {
         return null;
      }
   }
}

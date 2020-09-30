package com.google.api.client.googleapis;

import com.google.api.client.util.SecurityUtils;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GoogleUtils {
   public static final Integer BUGFIX_VERSION;
   public static final Integer MAJOR_VERSION;
   public static final Integer MINOR_VERSION;
   public static final String VERSION = getVersion();
   static final Pattern VERSION_PATTERN;
   static KeyStore certTrustStore;

   static {
      Pattern var0 = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(-SNAPSHOT)?");
      VERSION_PATTERN = var0;
      Matcher var1 = var0.matcher(VERSION);
      var1.find();
      MAJOR_VERSION = Integer.parseInt(var1.group(1));
      MINOR_VERSION = Integer.parseInt(var1.group(2));
      BUGFIX_VERSION = Integer.parseInt(var1.group(3));
   }

   private GoogleUtils() {
   }

   public static KeyStore getCertificateTrustStore() throws IOException, GeneralSecurityException {
      synchronized(GoogleUtils.class){}

      KeyStore var3;
      try {
         if (certTrustStore == null) {
            certTrustStore = SecurityUtils.getJavaKeyStore();
            InputStream var0 = GoogleUtils.class.getResourceAsStream("google.jks");
            SecurityUtils.loadKeyStore(certTrustStore, var0, "notasecret");
         }

         var3 = certTrustStore;
      } finally {
         ;
      }

      return var3;
   }

   private static String getVersion() {
      Object var0 = null;
      String var1 = null;
      String var2 = (String)var0;

      label252: {
         boolean var10001;
         InputStream var3;
         try {
            var3 = GoogleUtils.class.getResourceAsStream("google-api-client.properties");
         } catch (IOException var30) {
            var10001 = false;
            break label252;
         }

         if (var3 != null) {
            try {
               Properties var31 = new Properties();
               var31.load(var3);
               var1 = var31.getProperty("google-api-client.version");
            } catch (Throwable var29) {
               Throwable var4 = var29;

               try {
                  throw var4;
               } finally {
                  if (var3 != null) {
                     try {
                        var3.close();
                     } catch (Throwable var26) {
                        Throwable var32 = var26;
                        var2 = (String)var0;

                        label226:
                        try {
                           var4.addSuppressed(var32);
                           break label226;
                        } catch (IOException var25) {
                           var10001 = false;
                           break label252;
                        }
                     }
                  }

               }
            }
         }

         var2 = var1;
         if (var3 != null) {
            var2 = var1;

            try {
               var3.close();
            } catch (IOException var28) {
               var10001 = false;
               break label252;
            }

            var2 = var1;
         }
      }

      var1 = var2;
      if (var2 == null) {
         var1 = "unknown-version";
      }

      return var1;
   }
}

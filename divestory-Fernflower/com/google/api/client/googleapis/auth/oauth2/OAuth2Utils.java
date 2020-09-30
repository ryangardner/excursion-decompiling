package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OAuth2Utils {
   private static final int COMPUTE_PING_CONNECTION_TIMEOUT_MS = 500;
   private static final String DEFAULT_METADATA_SERVER_URL = "http://169.254.169.254";
   private static final Logger LOGGER = Logger.getLogger(OAuth2Utils.class.getName());
   private static final int MAX_COMPUTE_PING_TRIES = 3;
   static final Charset UTF_8 = Charset.forName("UTF-8");

   static <T extends Throwable> T exceptionWithCause(T var0, Throwable var1) {
      var0.initCause(var1);
      return var0;
   }

   public static String getMetadataServerUrl() {
      return getMetadataServerUrl(SystemEnvironmentProvider.INSTANCE);
   }

   static String getMetadataServerUrl(SystemEnvironmentProvider var0) {
      String var1 = var0.getEnv("GCE_METADATA_HOST");
      if (var1 != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append("http://");
         var2.append(var1);
         return var2.toString();
      } else {
         return "http://169.254.169.254";
      }
   }

   static boolean headersContainValue(HttpHeaders var0, String var1, String var2) {
      Object var3 = var0.get(var1);
      if (var3 instanceof Collection) {
         Iterator var4 = ((Collection)var3).iterator();

         while(var4.hasNext()) {
            var3 = var4.next();
            if (var3 instanceof String && ((String)var3).equals(var2)) {
               return true;
            }
         }
      }

      return false;
   }

   static boolean runningOnComputeEngine(HttpTransport var0, SystemEnvironmentProvider var1) {
      if (Boolean.parseBoolean(var1.getEnv("NO_GCE_CHECK"))) {
         return false;
      } else {
         GenericUrl var18 = new GenericUrl(getMetadataServerUrl(var1));
         int var2 = 1;

         while(true) {
            if (var2 <= 3) {
               label103: {
                  label92: {
                     IOException var10000;
                     label101: {
                        boolean var10001;
                        HttpResponse var19;
                        try {
                           HttpRequest var3 = var0.createRequestFactory().buildGetRequest(var18);
                           var3.setConnectTimeout(500);
                           var3.getHeaders().set("Metadata-Flavor", "Google");
                           var19 = var3.execute();
                        } catch (SocketTimeoutException var16) {
                           var10001 = false;
                           break label92;
                        } catch (IOException var17) {
                           var10000 = var17;
                           var10001 = false;
                           break label101;
                        }

                        try {
                           boolean var4 = headersContainValue(var19.getHeaders(), "Metadata-Flavor", "Google");
                           break label103;
                        } finally {
                           label87:
                           try {
                              var19.disconnect();
                           } catch (SocketTimeoutException var14) {
                              var10001 = false;
                              break label92;
                           } catch (IOException var15) {
                              var10000 = var15;
                              var10001 = false;
                              break label87;
                           }
                        }
                     }

                     IOException var20 = var10000;
                     LOGGER.log(Level.WARNING, "Failed to detect whether we are running on Google Compute Engine.", var20);
                  }

                  ++var2;
                  continue;
               }

            }

            return false;
         }
      }
   }
}

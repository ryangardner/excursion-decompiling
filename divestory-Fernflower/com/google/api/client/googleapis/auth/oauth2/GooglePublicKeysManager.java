package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GooglePublicKeysManager {
   private static final Pattern MAX_AGE_PATTERN = Pattern.compile("\\s*max-age\\s*=\\s*(\\d+)\\s*");
   private static final long REFRESH_SKEW_MILLIS = 300000L;
   private final Clock clock;
   private long expirationTimeMilliseconds;
   private final JsonFactory jsonFactory;
   private final Lock lock;
   private final String publicCertsEncodedUrl;
   private List<PublicKey> publicKeys;
   private final HttpTransport transport;

   protected GooglePublicKeysManager(GooglePublicKeysManager.Builder var1) {
      this.lock = new ReentrantLock();
      this.transport = var1.transport;
      this.jsonFactory = var1.jsonFactory;
      this.clock = var1.clock;
      this.publicCertsEncodedUrl = var1.publicCertsEncodedUrl;
   }

   public GooglePublicKeysManager(HttpTransport var1, JsonFactory var2) {
      this(new GooglePublicKeysManager.Builder(var1, var2));
   }

   long getCacheTimeInSec(HttpHeaders var1) {
      long var6;
      label25: {
         if (var1.getCacheControl() != null) {
            String[] var2 = var1.getCacheControl().split(",");
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               String var5 = var2[var4];
               Matcher var10 = MAX_AGE_PATTERN.matcher(var5);
               if (var10.matches()) {
                  var6 = Long.parseLong(var10.group(1));
                  break label25;
               }
            }
         }

         var6 = 0L;
      }

      long var8 = var6;
      if (var1.getAge() != null) {
         var8 = var6 - var1.getAge();
      }

      return Math.max(0L, var8);
   }

   public final Clock getClock() {
      return this.clock;
   }

   public final long getExpirationTimeMilliseconds() {
      return this.expirationTimeMilliseconds;
   }

   public final JsonFactory getJsonFactory() {
      return this.jsonFactory;
   }

   public final String getPublicCertsEncodedUrl() {
      return this.publicCertsEncodedUrl;
   }

   public final List<PublicKey> getPublicKeys() throws GeneralSecurityException, IOException {
      this.lock.lock();

      List var1;
      try {
         if (this.publicKeys == null || this.clock.currentTimeMillis() + 300000L > this.expirationTimeMilliseconds) {
            this.refresh();
         }

         var1 = this.publicKeys;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public final HttpTransport getTransport() {
      return this.transport;
   }

   public GooglePublicKeysManager refresh() throws GeneralSecurityException, IOException {
      this.lock.lock();

      label650: {
         Throwable var10000;
         Throwable var81;
         label654: {
            CertificateFactory var2;
            JsonParser var4;
            boolean var10001;
            JsonToken var82;
            try {
               ArrayList var1 = new ArrayList();
               this.publicKeys = var1;
               var2 = SecurityUtils.getX509CertificateFactory();
               HttpRequestFactory var78 = this.transport.createRequestFactory();
               GenericUrl var3 = new GenericUrl(this.publicCertsEncodedUrl);
               HttpResponse var79 = var78.buildGetRequest(var3).execute();
               this.expirationTimeMilliseconds = this.clock.currentTimeMillis() + this.getCacheTimeInSec(var79.getHeaders()) * 1000L;
               var4 = this.jsonFactory.createJsonParser(var79.getContent());
               var82 = var4.getCurrentToken();
            } catch (Throwable var77) {
               var10000 = var77;
               var10001 = false;
               break label654;
            }

            JsonToken var80 = var82;
            if (var82 == null) {
               try {
                  var80 = var4.nextToken();
               } catch (Throwable var76) {
                  var10000 = var76;
                  var10001 = false;
                  break label654;
               }
            }

            boolean var5;
            label641: {
               label640: {
                  try {
                     if (var80 == JsonToken.START_OBJECT) {
                        break label640;
                     }
                  } catch (Throwable var75) {
                     var10000 = var75;
                     var10001 = false;
                     break label654;
                  }

                  var5 = false;
                  break label641;
               }

               var5 = true;
            }

            try {
               Preconditions.checkArgument(var5);
            } catch (Throwable var72) {
               var10000 = var72;
               var10001 = false;
               break label654;
            }

            label655: {
               while(true) {
                  try {
                     if (var4.nextToken() != JsonToken.END_OBJECT) {
                        var4.nextToken();
                        String var83 = var4.getText();
                        ByteArrayInputStream var84 = new ByteArrayInputStream(StringUtils.getBytesUtf8(var83));
                        X509Certificate var85 = (X509Certificate)var2.generateCertificate(var84);
                        this.publicKeys.add(var85.getPublicKey());
                        continue;
                     }
                  } catch (Throwable var74) {
                     var10000 = var74;
                     var10001 = false;
                     break;
                  }

                  try {
                     this.publicKeys = Collections.unmodifiableList(this.publicKeys);
                     break label655;
                  } catch (Throwable var73) {
                     var10000 = var73;
                     var10001 = false;
                     break;
                  }
               }

               var81 = var10000;

               try {
                  var4.close();
                  throw var81;
               } catch (Throwable var70) {
                  var10000 = var70;
                  var10001 = false;
                  break label654;
               }
            }

            label617:
            try {
               var4.close();
               break label650;
            } catch (Throwable var71) {
               var10000 = var71;
               var10001 = false;
               break label617;
            }
         }

         var81 = var10000;
         this.lock.unlock();
         throw var81;
      }

      this.lock.unlock();
      return this;
   }

   public static class Builder {
      Clock clock;
      final JsonFactory jsonFactory;
      String publicCertsEncodedUrl;
      final HttpTransport transport;

      public Builder(HttpTransport var1, JsonFactory var2) {
         this.clock = Clock.SYSTEM;
         this.publicCertsEncodedUrl = "https://www.googleapis.com/oauth2/v1/certs";
         this.transport = (HttpTransport)Preconditions.checkNotNull(var1);
         this.jsonFactory = (JsonFactory)Preconditions.checkNotNull(var2);
      }

      public GooglePublicKeysManager build() {
         return new GooglePublicKeysManager(this);
      }

      public final Clock getClock() {
         return this.clock;
      }

      public final JsonFactory getJsonFactory() {
         return this.jsonFactory;
      }

      public final String getPublicCertsEncodedUrl() {
         return this.publicCertsEncodedUrl;
      }

      public final HttpTransport getTransport() {
         return this.transport;
      }

      public GooglePublicKeysManager.Builder setClock(Clock var1) {
         this.clock = (Clock)Preconditions.checkNotNull(var1);
         return this;
      }

      public GooglePublicKeysManager.Builder setPublicCertsEncodedUrl(String var1) {
         this.publicCertsEncodedUrl = (String)Preconditions.checkNotNull(var1);
         return this;
      }
   }
}

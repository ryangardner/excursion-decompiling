package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.openidconnect.IdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GoogleIdTokenVerifier extends IdTokenVerifier {
   private final GooglePublicKeysManager publicKeys;

   protected GoogleIdTokenVerifier(GoogleIdTokenVerifier.Builder var1) {
      super(var1);
      this.publicKeys = var1.publicKeys;
   }

   public GoogleIdTokenVerifier(GooglePublicKeysManager var1) {
      this(new GoogleIdTokenVerifier.Builder(var1));
   }

   public GoogleIdTokenVerifier(HttpTransport var1, JsonFactory var2) {
      this(new GoogleIdTokenVerifier.Builder(var1, var2));
   }

   @Deprecated
   public final long getExpirationTimeMilliseconds() {
      return this.publicKeys.getExpirationTimeMilliseconds();
   }

   public final JsonFactory getJsonFactory() {
      return this.publicKeys.getJsonFactory();
   }

   @Deprecated
   public final String getPublicCertsEncodedUrl() {
      return this.publicKeys.getPublicCertsEncodedUrl();
   }

   @Deprecated
   public final List<PublicKey> getPublicKeys() throws GeneralSecurityException, IOException {
      return this.publicKeys.getPublicKeys();
   }

   public final GooglePublicKeysManager getPublicKeysManager() {
      return this.publicKeys;
   }

   public final HttpTransport getTransport() {
      return this.publicKeys.getTransport();
   }

   @Deprecated
   public GoogleIdTokenVerifier loadPublicCerts() throws GeneralSecurityException, IOException {
      this.publicKeys.refresh();
      return this;
   }

   public GoogleIdToken verify(String var1) throws GeneralSecurityException, IOException {
      GoogleIdToken var2 = GoogleIdToken.parse(this.getJsonFactory(), var1);
      if (!this.verify(var2)) {
         var2 = null;
      }

      return var2;
   }

   public boolean verify(GoogleIdToken var1) throws GeneralSecurityException, IOException {
      if (!super.verify(var1)) {
         return false;
      } else {
         Iterator var2 = this.publicKeys.getPublicKeys().iterator();

         do {
            if (!var2.hasNext()) {
               return false;
            }
         } while(!var1.verifySignature((PublicKey)var2.next()));

         return true;
      }
   }

   public static class Builder extends IdTokenVerifier.Builder {
      GooglePublicKeysManager publicKeys;

      public Builder(GooglePublicKeysManager var1) {
         this.publicKeys = (GooglePublicKeysManager)Preconditions.checkNotNull(var1);
         this.setIssuers(Arrays.asList("accounts.google.com", "https://accounts.google.com"));
      }

      public Builder(HttpTransport var1, JsonFactory var2) {
         this(new GooglePublicKeysManager(var1, var2));
      }

      public GoogleIdTokenVerifier build() {
         return new GoogleIdTokenVerifier(this);
      }

      public final JsonFactory getJsonFactory() {
         return this.publicKeys.getJsonFactory();
      }

      public final GooglePublicKeysManager getPublicCerts() {
         return this.publicKeys;
      }

      @Deprecated
      public final String getPublicCertsEncodedUrl() {
         return this.publicKeys.getPublicCertsEncodedUrl();
      }

      public final HttpTransport getTransport() {
         return this.publicKeys.getTransport();
      }

      public GoogleIdTokenVerifier.Builder setAcceptableTimeSkewSeconds(long var1) {
         return (GoogleIdTokenVerifier.Builder)super.setAcceptableTimeSkewSeconds(var1);
      }

      public GoogleIdTokenVerifier.Builder setAudience(Collection<String> var1) {
         return (GoogleIdTokenVerifier.Builder)super.setAudience(var1);
      }

      public GoogleIdTokenVerifier.Builder setClock(Clock var1) {
         return (GoogleIdTokenVerifier.Builder)super.setClock(var1);
      }

      public GoogleIdTokenVerifier.Builder setIssuer(String var1) {
         return (GoogleIdTokenVerifier.Builder)super.setIssuer(var1);
      }

      public GoogleIdTokenVerifier.Builder setIssuers(Collection<String> var1) {
         return (GoogleIdTokenVerifier.Builder)super.setIssuers(var1);
      }

      @Deprecated
      public GoogleIdTokenVerifier.Builder setPublicCertsEncodedUrl(String var1) {
         this.publicKeys = (new GooglePublicKeysManager.Builder(this.getTransport(), this.getJsonFactory())).setPublicCertsEncodedUrl(var1).setClock(this.publicKeys.getClock()).build();
         return this;
      }
   }
}

package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Credential implements HttpExecuteInterceptor, HttpRequestInitializer, HttpUnsuccessfulResponseHandler {
   static final Logger LOGGER = Logger.getLogger(Credential.class.getName());
   private String accessToken;
   private final HttpExecuteInterceptor clientAuthentication;
   private final Clock clock;
   private Long expirationTimeMilliseconds;
   private final JsonFactory jsonFactory;
   private final Lock lock;
   private final Credential.AccessMethod method;
   private final Collection<CredentialRefreshListener> refreshListeners;
   private String refreshToken;
   private final HttpRequestInitializer requestInitializer;
   private final String tokenServerEncodedUrl;
   private final HttpTransport transport;

   public Credential(Credential.AccessMethod var1) {
      this(new Credential.Builder(var1));
   }

   protected Credential(Credential.Builder var1) {
      this.lock = new ReentrantLock();
      this.method = (Credential.AccessMethod)Preconditions.checkNotNull(var1.method);
      this.transport = var1.transport;
      this.jsonFactory = var1.jsonFactory;
      String var2;
      if (var1.tokenServerUrl == null) {
         var2 = null;
      } else {
         var2 = var1.tokenServerUrl.build();
      }

      this.tokenServerEncodedUrl = var2;
      this.clientAuthentication = var1.clientAuthentication;
      this.requestInitializer = var1.requestInitializer;
      this.refreshListeners = Collections.unmodifiableCollection(var1.refreshListeners);
      this.clock = (Clock)Preconditions.checkNotNull(var1.clock);
   }

   protected TokenResponse executeRefreshToken() throws IOException {
      return this.refreshToken == null ? null : (new RefreshTokenRequest(this.transport, this.jsonFactory, new GenericUrl(this.tokenServerEncodedUrl), this.refreshToken)).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).execute();
   }

   public final String getAccessToken() {
      this.lock.lock();

      String var1;
      try {
         var1 = this.accessToken;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public final HttpExecuteInterceptor getClientAuthentication() {
      return this.clientAuthentication;
   }

   public final Clock getClock() {
      return this.clock;
   }

   public final Long getExpirationTimeMilliseconds() {
      this.lock.lock();

      Long var1;
      try {
         var1 = this.expirationTimeMilliseconds;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public final Long getExpiresInSeconds() {
      this.lock.lock();

      Long var1;
      label75: {
         Throwable var10000;
         label79: {
            boolean var10001;
            try {
               var1 = this.expirationTimeMilliseconds;
            } catch (Throwable var7) {
               var10000 = var7;
               var10001 = false;
               break label79;
            }

            if (var1 == null) {
               var1 = null;
               break label75;
            }

            label69:
            try {
               var1 = (this.expirationTimeMilliseconds - this.clock.currentTimeMillis()) / 1000L;
               break label75;
            } catch (Throwable var6) {
               var10000 = var6;
               var10001 = false;
               break label69;
            }
         }

         Throwable var8 = var10000;
         this.lock.unlock();
         throw var8;
      }

      this.lock.unlock();
      return var1;
   }

   public final JsonFactory getJsonFactory() {
      return this.jsonFactory;
   }

   public final Credential.AccessMethod getMethod() {
      return this.method;
   }

   public final Collection<CredentialRefreshListener> getRefreshListeners() {
      return this.refreshListeners;
   }

   public final String getRefreshToken() {
      this.lock.lock();

      String var1;
      try {
         var1 = this.refreshToken;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public final HttpRequestInitializer getRequestInitializer() {
      return this.requestInitializer;
   }

   public final String getTokenServerEncodedUrl() {
      return this.tokenServerEncodedUrl;
   }

   public final HttpTransport getTransport() {
      return this.transport;
   }

   public boolean handleResponse(HttpRequest var1, HttpResponse var2, boolean var3) {
      boolean var5;
      boolean var7;
      label137: {
         List var4 = var2.getHeaders().getAuthenticateAsList();
         var5 = true;
         if (var4 != null) {
            Iterator var18 = var4.iterator();

            while(var18.hasNext()) {
               String var6 = (String)var18.next();
               if (var6.startsWith("Bearer ")) {
                  var3 = BearerToken.INVALID_TOKEN_ERROR.matcher(var6).find();
                  var7 = true;
                  break label137;
               }
            }
         }

         var3 = false;
         var7 = false;
      }

      if (!var7) {
         if (var2.getStatusCode() == 401) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      if (var3) {
         IOException var10000;
         label140: {
            boolean var10001;
            try {
               this.lock.lock();
            } catch (IOException var16) {
               var10000 = var16;
               var10001 = false;
               break label140;
            }

            var3 = var5;
            boolean var12 = false;

            label119: {
               try {
                  var12 = true;
                  if (!Objects.equal(this.accessToken, this.method.getAccessTokenFromRequest(var1))) {
                     var12 = false;
                     break label119;
                  }

                  var3 = this.refreshToken();
                  var12 = false;
               } finally {
                  if (var12) {
                     try {
                        this.lock.unlock();
                     } catch (IOException var14) {
                        var10000 = var14;
                        var10001 = false;
                        break label140;
                     }
                  }
               }

               if (var3) {
                  var3 = var5;
               } else {
                  var3 = false;
               }
            }

            try {
               this.lock.unlock();
               return var3;
            } catch (IOException var13) {
               var10000 = var13;
               var10001 = false;
            }
         }

         IOException var17 = var10000;
         LOGGER.log(Level.SEVERE, "unable to refresh token", var17);
      }

      return false;
   }

   public void initialize(HttpRequest var1) throws IOException {
      var1.setInterceptor(this);
      var1.setUnsuccessfulResponseHandler(this);
   }

   public void intercept(HttpRequest var1) throws IOException {
      this.lock.lock();

      label205: {
         Throwable var10000;
         label204: {
            boolean var10001;
            label209: {
               label210: {
                  Long var2;
                  try {
                     var2 = this.getExpiresInSeconds();
                     if (this.accessToken == null) {
                        break label210;
                     }
                  } catch (Throwable var22) {
                     var10000 = var22;
                     var10001 = false;
                     break label204;
                  }

                  if (var2 == null) {
                     break label209;
                  }

                  try {
                     if (var2 > 60L) {
                        break label209;
                     }
                  } catch (Throwable var21) {
                     var10000 = var21;
                     var10001 = false;
                     break label204;
                  }
               }

               String var24;
               try {
                  this.refreshToken();
                  var24 = this.accessToken;
               } catch (Throwable var20) {
                  var10000 = var20;
                  var10001 = false;
                  break label204;
               }

               if (var24 == null) {
                  this.lock.unlock();
                  return;
               }
            }

            label190:
            try {
               this.method.intercept(var1, this.accessToken);
               break label205;
            } catch (Throwable var19) {
               var10000 = var19;
               var10001 = false;
               break label190;
            }
         }

         Throwable var23 = var10000;
         this.lock.unlock();
         throw var23;
      }

      this.lock.unlock();
   }

   public final boolean refreshToken() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public Credential setAccessToken(String var1) {
      this.lock.lock();

      try {
         this.accessToken = var1;
      } finally {
         this.lock.unlock();
      }

      return this;
   }

   public Credential setExpirationTimeMilliseconds(Long var1) {
      this.lock.lock();

      try {
         this.expirationTimeMilliseconds = var1;
      } finally {
         this.lock.unlock();
      }

      return this;
   }

   public Credential setExpiresInSeconds(Long var1) {
      if (var1 == null) {
         var1 = null;
      } else {
         var1 = this.clock.currentTimeMillis() + var1 * 1000L;
      }

      return this.setExpirationTimeMilliseconds(var1);
   }

   public Credential setFromTokenResponse(TokenResponse var1) {
      this.setAccessToken(var1.getAccessToken());
      if (var1.getRefreshToken() != null) {
         this.setRefreshToken(var1.getRefreshToken());
      }

      this.setExpiresInSeconds(var1.getExpiresInSeconds());
      return this;
   }

   public Credential setRefreshToken(String var1) {
      label170: {
         Throwable var10000;
         label174: {
            this.lock.lock();
            boolean var10001;
            if (var1 != null) {
               boolean var2;
               label166: {
                  label165: {
                     try {
                        if (this.jsonFactory != null && this.transport != null && this.clientAuthentication != null && this.tokenServerEncodedUrl != null) {
                           break label165;
                        }
                     } catch (Throwable var14) {
                        var10000 = var14;
                        var10001 = false;
                        break label174;
                     }

                     var2 = false;
                     break label166;
                  }

                  var2 = true;
               }

               try {
                  Preconditions.checkArgument(var2, "Please use the Builder and call setJsonFactory, setTransport, setClientAuthentication and setTokenServerUrl/setTokenServerEncodedUrl");
               } catch (Throwable var13) {
                  var10000 = var13;
                  var10001 = false;
                  break label174;
               }
            }

            label150:
            try {
               this.refreshToken = var1;
               break label170;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break label150;
            }
         }

         Throwable var15 = var10000;
         this.lock.unlock();
         throw var15;
      }

      this.lock.unlock();
      return this;
   }

   public interface AccessMethod {
      String getAccessTokenFromRequest(HttpRequest var1);

      void intercept(HttpRequest var1, String var2) throws IOException;
   }

   public static class Builder {
      HttpExecuteInterceptor clientAuthentication;
      Clock clock;
      JsonFactory jsonFactory;
      final Credential.AccessMethod method;
      Collection<CredentialRefreshListener> refreshListeners;
      HttpRequestInitializer requestInitializer;
      GenericUrl tokenServerUrl;
      HttpTransport transport;

      public Builder(Credential.AccessMethod var1) {
         this.clock = Clock.SYSTEM;
         this.refreshListeners = Lists.newArrayList();
         this.method = (Credential.AccessMethod)Preconditions.checkNotNull(var1);
      }

      public Credential.Builder addRefreshListener(CredentialRefreshListener var1) {
         this.refreshListeners.add(Preconditions.checkNotNull(var1));
         return this;
      }

      public Credential build() {
         return new Credential(this);
      }

      public final HttpExecuteInterceptor getClientAuthentication() {
         return this.clientAuthentication;
      }

      public final Clock getClock() {
         return this.clock;
      }

      public final JsonFactory getJsonFactory() {
         return this.jsonFactory;
      }

      public final Credential.AccessMethod getMethod() {
         return this.method;
      }

      public final Collection<CredentialRefreshListener> getRefreshListeners() {
         return this.refreshListeners;
      }

      public final HttpRequestInitializer getRequestInitializer() {
         return this.requestInitializer;
      }

      public final GenericUrl getTokenServerUrl() {
         return this.tokenServerUrl;
      }

      public final HttpTransport getTransport() {
         return this.transport;
      }

      public Credential.Builder setClientAuthentication(HttpExecuteInterceptor var1) {
         this.clientAuthentication = var1;
         return this;
      }

      public Credential.Builder setClock(Clock var1) {
         this.clock = (Clock)Preconditions.checkNotNull(var1);
         return this;
      }

      public Credential.Builder setJsonFactory(JsonFactory var1) {
         this.jsonFactory = var1;
         return this;
      }

      public Credential.Builder setRefreshListeners(Collection<CredentialRefreshListener> var1) {
         this.refreshListeners = (Collection)Preconditions.checkNotNull(var1);
         return this;
      }

      public Credential.Builder setRequestInitializer(HttpRequestInitializer var1) {
         this.requestInitializer = var1;
         return this;
      }

      public Credential.Builder setTokenServerEncodedUrl(String var1) {
         GenericUrl var2;
         if (var1 == null) {
            var2 = null;
         } else {
            var2 = new GenericUrl(var1);
         }

         this.tokenServerUrl = var2;
         return this;
      }

      public Credential.Builder setTokenServerUrl(GenericUrl var1) {
         this.tokenServerUrl = var1;
         return this;
      }

      public Credential.Builder setTransport(HttpTransport var1) {
         this.transport = var1;
         return this;
      }
   }
}

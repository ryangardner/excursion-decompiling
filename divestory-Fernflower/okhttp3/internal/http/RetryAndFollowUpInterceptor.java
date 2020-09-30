package okhttp3.internal.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.Proxy.Type;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RealConnection;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0002J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012H\u0002J(\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0012H\u0002J\u0018\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u0006H\u0002J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u001cH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"},
   d2 = {"Lokhttp3/internal/http/RetryAndFollowUpInterceptor;", "Lokhttp3/Interceptor;", "client", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;)V", "buildRedirectRequest", "Lokhttp3/Request;", "userResponse", "Lokhttp3/Response;", "method", "", "followUpRequest", "exchange", "Lokhttp3/internal/connection/Exchange;", "intercept", "chain", "Lokhttp3/Interceptor$Chain;", "isRecoverable", "", "e", "Ljava/io/IOException;", "requestSendStarted", "recover", "call", "Lokhttp3/internal/connection/RealCall;", "userRequest", "requestIsOneShot", "retryAfter", "", "defaultDelay", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class RetryAndFollowUpInterceptor implements Interceptor {
   public static final RetryAndFollowUpInterceptor.Companion Companion = new RetryAndFollowUpInterceptor.Companion((DefaultConstructorMarker)null);
   private static final int MAX_FOLLOW_UPS = 20;
   private final OkHttpClient client;

   public RetryAndFollowUpInterceptor(OkHttpClient var1) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      super();
      this.client = var1;
   }

   private final Request buildRedirectRequest(Response var1, String var2) {
      boolean var3 = this.client.followRedirects();
      RequestBody var4 = null;
      if (!var3) {
         return null;
      } else {
         String var5 = Response.header$default(var1, "Location", (String)null, 2, (Object)null);
         if (var5 != null) {
            HttpUrl var6 = var1.request().url().resolve(var5);
            if (var6 != null) {
               if (!Intrinsics.areEqual((Object)var6.scheme(), (Object)var1.request().url().scheme()) && !this.client.followSslRedirects()) {
                  return null;
               }

               Request.Builder var9 = var1.request().newBuilder();
               if (HttpMethod.permitsRequestBody(var2)) {
                  int var7 = var1.code();
                  boolean var8;
                  if (!HttpMethod.INSTANCE.redirectsWithBody(var2) && var7 != 308 && var7 != 307) {
                     var8 = false;
                  } else {
                     var8 = true;
                  }

                  if (HttpMethod.INSTANCE.redirectsToGet(var2) && var7 != 308 && var7 != 307) {
                     var9.method("GET", (RequestBody)null);
                  } else {
                     if (var8) {
                        var4 = var1.request().body();
                     }

                     var9.method(var2, var4);
                  }

                  if (!var8) {
                     var9.removeHeader("Transfer-Encoding");
                     var9.removeHeader("Content-Length");
                     var9.removeHeader("Content-Type");
                  }
               }

               if (!Util.canReuseConnectionFor(var1.request().url(), var6)) {
                  var9.removeHeader("Authorization");
               }

               return var9.url(var6).build();
            }
         }

         return null;
      }
   }

   private final Request followUpRequest(Response var1, Exchange var2) throws IOException {
      Route var8;
      label93: {
         if (var2 != null) {
            RealConnection var3 = var2.getConnection$okhttp();
            if (var3 != null) {
               var8 = var3.route();
               break label93;
            }
         }

         var8 = null;
      }

      int var4 = var1.code();
      String var5 = var1.request().method();
      if (var4 != 307 && var4 != 308) {
         if (var4 == 401) {
            return this.client.authenticator().authenticate(var8, var1);
         }

         if (var4 == 421) {
            RequestBody var9 = var1.request().body();
            if (var9 != null && var9.isOneShot()) {
               return null;
            }

            if (var2 != null && var2.isCoalescedConnection$okhttp()) {
               var2.getConnection$okhttp().noCoalescedConnections$okhttp();
               return var1.request();
            }

            return null;
         }

         Response var7;
         if (var4 == 503) {
            var7 = var1.priorResponse();
            if (var7 != null && var7.code() == 503) {
               return null;
            }

            if (this.retryAfter(var1, Integer.MAX_VALUE) == 0) {
               return var1.request();
            }

            return null;
         }

         if (var4 == 407) {
            if (var8 == null) {
               Intrinsics.throwNpe();
            }

            if (var8.proxy().type() == Type.HTTP) {
               return this.client.proxyAuthenticator().authenticate(var8, var1);
            }

            throw (Throwable)(new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy"));
         }

         if (var4 == 408) {
            if (!this.client.retryOnConnectionFailure()) {
               return null;
            }

            RequestBody var6 = var1.request().body();
            if (var6 != null && var6.isOneShot()) {
               return null;
            }

            var7 = var1.priorResponse();
            if (var7 != null && var7.code() == 408) {
               return null;
            }

            if (this.retryAfter(var1, 0) > 0) {
               return null;
            }

            return var1.request();
         }

         switch(var4) {
         case 300:
         case 301:
         case 302:
         case 303:
            break;
         default:
            return null;
         }
      }

      return this.buildRedirectRequest(var1, var5);
   }

   private final boolean isRecoverable(IOException var1, boolean var2) {
      boolean var3 = var1 instanceof ProtocolException;
      boolean var4 = false;
      if (var3) {
         return false;
      } else if (var1 instanceof InterruptedIOException) {
         var3 = var4;
         if (var1 instanceof SocketTimeoutException) {
            var3 = var4;
            if (!var2) {
               var3 = true;
            }
         }

         return var3;
      } else if (var1 instanceof SSLHandshakeException && var1.getCause() instanceof CertificateException) {
         return false;
      } else {
         return !(var1 instanceof SSLPeerUnverifiedException);
      }
   }

   private final boolean recover(IOException var1, RealCall var2, Request var3, boolean var4) {
      if (!this.client.retryOnConnectionFailure()) {
         return false;
      } else if (var4 && this.requestIsOneShot(var1, var3)) {
         return false;
      } else if (!this.isRecoverable(var1, var4)) {
         return false;
      } else {
         return var2.retryAfterFailure();
      }
   }

   private final boolean requestIsOneShot(IOException var1, Request var2) {
      RequestBody var4 = var2.body();
      boolean var3;
      if ((var4 == null || !var4.isOneShot()) && !(var1 instanceof FileNotFoundException)) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   private final int retryAfter(Response var1, int var2) {
      String var3 = Response.header$default(var1, "Retry-After", (String)null, 2, (Object)null);
      if (var3 != null) {
         CharSequence var4 = (CharSequence)var3;
         if ((new Regex("\\d+")).matches(var4)) {
            Integer var5 = Integer.valueOf(var3);
            Intrinsics.checkExpressionValueIsNotNull(var5, "Integer.valueOf(header)");
            return var5;
         } else {
            return Integer.MAX_VALUE;
         }
      } else {
         return var2;
      }
   }

   public Response intercept(Interceptor.Chain param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"},
      d2 = {"Lokhttp3/internal/http/RetryAndFollowUpInterceptor$Companion;", "", "()V", "MAX_FOLLOW_UPS", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }
}

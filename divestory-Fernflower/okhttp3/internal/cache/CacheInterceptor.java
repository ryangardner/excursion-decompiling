package okhttp3.internal.cache;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\bH\u0002J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"},
   d2 = {"Lokhttp3/internal/cache/CacheInterceptor;", "Lokhttp3/Interceptor;", "cache", "Lokhttp3/Cache;", "(Lokhttp3/Cache;)V", "getCache$okhttp", "()Lokhttp3/Cache;", "cacheWritingResponse", "Lokhttp3/Response;", "cacheRequest", "Lokhttp3/internal/cache/CacheRequest;", "response", "intercept", "chain", "Lokhttp3/Interceptor$Chain;", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class CacheInterceptor implements Interceptor {
   public static final CacheInterceptor.Companion Companion = new CacheInterceptor.Companion((DefaultConstructorMarker)null);
   private final Cache cache;

   public CacheInterceptor(Cache var1) {
      this.cache = var1;
   }

   private final Response cacheWritingResponse(final CacheRequest var1, Response var2) throws IOException {
      if (var1 == null) {
         return var2;
      } else {
         Sink var3 = var1.body();
         ResponseBody var4 = var2.body();
         if (var4 == null) {
            Intrinsics.throwNpe();
         }

         Source var7 = new Source(var4.source(), Okio.buffer(var3)) {
            // $FF: synthetic field
            final BufferedSink $cacheBody;
            // $FF: synthetic field
            final BufferedSource $source;
            private boolean cacheRequestClosed;

            {
               this.$source = var1x;
               this.$cacheBody = var3;
            }

            public void close() throws IOException {
               if (!this.cacheRequestClosed && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                  this.cacheRequestClosed = true;
                  var1.abort();
               }

               this.$source.close();
            }

            public long read(Buffer var1x, long var2) throws IOException {
               Intrinsics.checkParameterIsNotNull(var1x, "sink");

               try {
                  var2 = this.$source.read(var1x, var2);
               } catch (IOException var4) {
                  if (!this.cacheRequestClosed) {
                     this.cacheRequestClosed = true;
                     var1.abort();
                  }

                  throw (Throwable)var4;
               }

               if (var2 == -1L) {
                  if (!this.cacheRequestClosed) {
                     this.cacheRequestClosed = true;
                     this.$cacheBody.close();
                  }

                  return -1L;
               } else {
                  var1x.copyTo(this.$cacheBody.getBuffer(), var1x.size() - var2, var2);
                  this.$cacheBody.emitCompleteSegments();
                  return var2;
               }
            }

            public Timeout timeout() {
               return this.$source.timeout();
            }
         };
         String var8 = Response.header$default(var2, "Content-Type", (String)null, 2, (Object)null);
         long var5 = var2.body().contentLength();
         return var2.newBuilder().body((ResponseBody)(new RealResponseBody(var8, var5, Okio.buffer((Source)var7)))).build();
      }
   }

   public final Cache getCache$okhttp() {
      return this.cache;
   }

   public Response intercept(Interceptor.Chain var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      Call var2 = var1.call();
      Cache var3 = this.cache;
      Response var16;
      if (var3 != null) {
         var16 = var3.get$okhttp(var1.request());
      } else {
         var16 = null;
      }

      CacheStrategy var4 = (new CacheStrategy.Factory(System.currentTimeMillis(), var1.request(), var16)).compute();
      Request var5 = var4.getNetworkRequest();
      Response var6 = var4.getCacheResponse();
      Cache var7 = this.cache;
      if (var7 != null) {
         var7.trackResponse$okhttp(var4);
      }

      Call var20;
      if (!(var2 instanceof RealCall)) {
         var20 = null;
      } else {
         var20 = var2;
      }

      EventListener var22;
      label239: {
         RealCall var21 = (RealCall)var20;
         if (var21 != null) {
            var22 = var21.getEventListener$okhttp();
            if (var22 != null) {
               break label239;
            }
         }

         var22 = EventListener.NONE;
      }

      if (var16 != null && var6 == null) {
         ResponseBody var17 = var16.body();
         if (var17 != null) {
            Util.closeQuietly((Closeable)var17);
         }
      }

      Response var13;
      if (var5 == null && var6 == null) {
         var13 = (new Response.Builder()).request(var1.request()).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(System.currentTimeMillis()).build();
         var22.satisfactionFailure(var2, var13);
         return var13;
      } else if (var5 == null) {
         if (var6 == null) {
            Intrinsics.throwNpe();
         }

         var13 = var6.newBuilder().cacheResponse(Companion.stripBody(var6)).build();
         var22.cacheHit(var2, var13);
         return var13;
      } else {
         if (var6 != null) {
            var22.cacheConditionalHit(var2, var6);
         } else if (this.cache != null) {
            var22.cacheMiss(var2);
         }

         Response var19 = (Response)null;
         boolean var10 = false;

         ResponseBody var18;
         try {
            var10 = true;
            var13 = var1.proceed(var5);
            var10 = false;
         } finally {
            if (var10) {
               if (var16 != null) {
                  var18 = var16.body();
                  if (var18 != null) {
                     Util.closeQuietly((Closeable)var18);
                  }
               }

            }
         }

         if (var13 == null && var16 != null) {
            var18 = var16.body();
            if (var18 != null) {
               Util.closeQuietly((Closeable)var18);
            }
         }

         if (var6 != null) {
            if (var13 != null && var13.code() == 304) {
               var16 = var6.newBuilder().headers(Companion.combine(var6.headers(), var13.headers())).sentRequestAtMillis(var13.sentRequestAtMillis()).receivedResponseAtMillis(var13.receivedResponseAtMillis()).cacheResponse(Companion.stripBody(var6)).networkResponse(Companion.stripBody(var13)).build();
               ResponseBody var14 = var13.body();
               if (var14 == null) {
                  Intrinsics.throwNpe();
               }

               var14.close();
               Cache var15 = this.cache;
               if (var15 == null) {
                  Intrinsics.throwNpe();
               }

               var15.trackConditionalCacheHit$okhttp();
               this.cache.update$okhttp(var6, var16);
               var22.cacheHit(var2, var16);
               return var16;
            }

            var18 = var6.body();
            if (var18 != null) {
               Util.closeQuietly((Closeable)var18);
            }
         }

         if (var13 == null) {
            Intrinsics.throwNpe();
         }

         var13 = var13.newBuilder().cacheResponse(Companion.stripBody(var6)).networkResponse(Companion.stripBody(var13)).build();
         if (this.cache != null) {
            if (HttpHeaders.promisesBody(var13) && CacheStrategy.Companion.isCacheable(var13, var5)) {
               var13 = this.cacheWritingResponse(this.cache.put$okhttp(var13), var13);
               if (var6 != null) {
                  var22.cacheMiss(var2);
               }

               return var13;
            }

            if (HttpMethod.INSTANCE.invalidatesCache(var5.method())) {
               try {
                  this.cache.remove$okhttp(var5);
               } catch (IOException var11) {
               }
            }
         }

         return var13;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0014\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0002¨\u0006\u000f"},
      d2 = {"Lokhttp3/internal/cache/CacheInterceptor$Companion;", "", "()V", "combine", "Lokhttp3/Headers;", "cachedHeaders", "networkHeaders", "isContentSpecificHeader", "", "fieldName", "", "isEndToEnd", "stripBody", "Lokhttp3/Response;", "response", "okhttp"},
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

      private final Headers combine(Headers var1, Headers var2) {
         Headers.Builder var3 = new Headers.Builder();
         int var4 = var1.size();
         byte var5 = 0;

         int var6;
         String var8;
         for(var6 = 0; var6 < var4; ++var6) {
            String var7 = var1.name(var6);
            var8 = var1.value(var6);
            if (!StringsKt.equals("Warning", var7, true) || !StringsKt.startsWith$default(var8, "1", false, 2, (Object)null)) {
               CacheInterceptor.Companion var9 = (CacheInterceptor.Companion)this;
               if (var9.isContentSpecificHeader(var7) || !var9.isEndToEnd(var7) || var2.get(var7) == null) {
                  var3.addLenient$okhttp(var7, var8);
               }
            }
         }

         var4 = var2.size();

         for(var6 = var5; var6 < var4; ++var6) {
            var8 = var2.name(var6);
            CacheInterceptor.Companion var10 = (CacheInterceptor.Companion)this;
            if (!var10.isContentSpecificHeader(var8) && var10.isEndToEnd(var8)) {
               var3.addLenient$okhttp(var8, var2.value(var6));
            }
         }

         return var3.build();
      }

      private final boolean isContentSpecificHeader(String var1) {
         boolean var2 = true;
         boolean var3 = var2;
         if (!StringsKt.equals("Content-Length", var1, true)) {
            var3 = var2;
            if (!StringsKt.equals("Content-Encoding", var1, true)) {
               if (StringsKt.equals("Content-Type", var1, true)) {
                  var3 = var2;
               } else {
                  var3 = false;
               }
            }
         }

         return var3;
      }

      private final boolean isEndToEnd(String var1) {
         boolean var2 = true;
         if (StringsKt.equals("Connection", var1, true) || StringsKt.equals("Keep-Alive", var1, true) || StringsKt.equals("Proxy-Authenticate", var1, true) || StringsKt.equals("Proxy-Authorization", var1, true) || StringsKt.equals("TE", var1, true) || StringsKt.equals("Trailers", var1, true) || StringsKt.equals("Transfer-Encoding", var1, true) || StringsKt.equals("Upgrade", var1, true)) {
            var2 = false;
         }

         return var2;
      }

      private final Response stripBody(Response var1) {
         ResponseBody var2;
         if (var1 != null) {
            var2 = var1.body();
         } else {
            var2 = null;
         }

         Response var3 = var1;
         if (var2 != null) {
            var3 = var1.newBuilder().body((ResponseBody)null).build();
         }

         return var3;
      }
   }
}

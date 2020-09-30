package okhttp3.internal.cache;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.http.DatesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u000b2\u00020\u0001:\u0002\u000b\fB\u001b\b\u0000\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"},
   d2 = {"Lokhttp3/internal/cache/CacheStrategy;", "", "networkRequest", "Lokhttp3/Request;", "cacheResponse", "Lokhttp3/Response;", "(Lokhttp3/Request;Lokhttp3/Response;)V", "getCacheResponse", "()Lokhttp3/Response;", "getNetworkRequest", "()Lokhttp3/Request;", "Companion", "Factory", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class CacheStrategy {
   public static final CacheStrategy.Companion Companion = new CacheStrategy.Companion((DefaultConstructorMarker)null);
   private final Response cacheResponse;
   private final Request networkRequest;

   public CacheStrategy(Request var1, Response var2) {
      this.networkRequest = var1;
      this.cacheResponse = var2;
   }

   public final Response getCacheResponse() {
      return this.cacheResponse;
   }

   public final Request getNetworkRequest() {
      return this.networkRequest;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"},
      d2 = {"Lokhttp3/internal/cache/CacheStrategy$Companion;", "", "()V", "isCacheable", "", "response", "Lokhttp3/Response;", "request", "Lokhttp3/Request;", "okhttp"},
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

      public final boolean isCacheable(Response var1, Request var2) {
         Intrinsics.checkParameterIsNotNull(var1, "response");
         Intrinsics.checkParameterIsNotNull(var2, "request");
         int var3 = var1.code();
         boolean var4 = false;
         if (var3 != 200 && var3 != 410 && var3 != 414 && var3 != 501 && var3 != 203 && var3 != 204) {
            label67: {
               if (var3 != 307) {
                  if (var3 == 308 || var3 == 404 || var3 == 405) {
                     break label67;
                  }

                  switch(var3) {
                  case 300:
                  case 301:
                     break label67;
                  case 302:
                     break;
                  default:
                     return false;
                  }
               }

               if (Response.header$default(var1, "Expires", (String)null, 2, (Object)null) == null && var1.cacheControl().maxAgeSeconds() == -1 && !var1.cacheControl().isPublic() && !var1.cacheControl().isPrivate()) {
                  return false;
               }
            }
         }

         boolean var5 = var4;
         if (!var1.cacheControl().noStore()) {
            var5 = var4;
            if (!var2.cacheControl().noStore()) {
               var5 = true;
            }
         }

         return var5;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0017\u001a\u00020\u0003H\u0002J\u0006\u0010\u0018\u001a\u00020\u0019J\b\u0010\u001a\u001a\u00020\u0019H\u0002J\b\u0010\u001b\u001a\u00020\u0003H\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"},
      d2 = {"Lokhttp3/internal/cache/CacheStrategy$Factory;", "", "nowMillis", "", "request", "Lokhttp3/Request;", "cacheResponse", "Lokhttp3/Response;", "(JLokhttp3/Request;Lokhttp3/Response;)V", "ageSeconds", "", "etag", "", "expires", "Ljava/util/Date;", "lastModified", "lastModifiedString", "receivedResponseMillis", "getRequest$okhttp", "()Lokhttp3/Request;", "sentRequestMillis", "servedDate", "servedDateString", "cacheResponseAge", "compute", "Lokhttp3/internal/cache/CacheStrategy;", "computeCandidate", "computeFreshnessLifetime", "hasConditions", "", "isFreshnessLifetimeHeuristic", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Factory {
      private int ageSeconds;
      private final Response cacheResponse;
      private String etag;
      private Date expires;
      private Date lastModified;
      private String lastModifiedString;
      private final long nowMillis;
      private long receivedResponseMillis;
      private final Request request;
      private long sentRequestMillis;
      private Date servedDate;
      private String servedDateString;

      public Factory(long var1, Request var3, Response var4) {
         Intrinsics.checkParameterIsNotNull(var3, "request");
         super();
         this.nowMillis = var1;
         this.request = var3;
         this.cacheResponse = var4;
         this.ageSeconds = -1;
         if (var4 != null) {
            this.sentRequestMillis = var4.sentRequestAtMillis();
            this.receivedResponseMillis = this.cacheResponse.receivedResponseAtMillis();
            Headers var5 = this.cacheResponse.headers();
            int var6 = 0;

            for(int var7 = var5.size(); var6 < var7; ++var6) {
               String var8 = var5.name(var6);
               String var9 = var5.value(var6);
               if (StringsKt.equals(var8, "Date", true)) {
                  this.servedDate = DatesKt.toHttpDateOrNull(var9);
                  this.servedDateString = var9;
               } else if (StringsKt.equals(var8, "Expires", true)) {
                  this.expires = DatesKt.toHttpDateOrNull(var9);
               } else if (StringsKt.equals(var8, "Last-Modified", true)) {
                  this.lastModified = DatesKt.toHttpDateOrNull(var9);
                  this.lastModifiedString = var9;
               } else if (StringsKt.equals(var8, "ETag", true)) {
                  this.etag = var9;
               } else if (StringsKt.equals(var8, "Age", true)) {
                  this.ageSeconds = Util.toNonNegativeInt(var9, -1);
               }
            }
         }

      }

      private final long cacheResponseAge() {
         Date var1 = this.servedDate;
         long var2 = 0L;
         if (var1 != null) {
            var2 = Math.max(0L, this.receivedResponseMillis - var1.getTime());
         }

         long var4 = var2;
         if (this.ageSeconds != -1) {
            var4 = Math.max(var2, TimeUnit.SECONDS.toMillis((long)this.ageSeconds));
         }

         var2 = this.receivedResponseMillis;
         return var4 + (var2 - this.sentRequestMillis) + (this.nowMillis - var2);
      }

      private final CacheStrategy computeCandidate() {
         if (this.cacheResponse == null) {
            return new CacheStrategy(this.request, (Response)null);
         } else if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
            return new CacheStrategy(this.request, (Response)null);
         } else if (!CacheStrategy.Companion.isCacheable(this.cacheResponse, this.request)) {
            return new CacheStrategy(this.request, (Response)null);
         } else {
            CacheControl var1 = this.request.cacheControl();
            if (!var1.noCache() && !this.hasConditions(this.request)) {
               CacheControl var2 = this.cacheResponse.cacheControl();
               long var3 = this.cacheResponseAge();
               long var5 = this.computeFreshnessLifetime();
               long var7 = var5;
               if (var1.maxAgeSeconds() != -1) {
                  var7 = Math.min(var5, TimeUnit.SECONDS.toMillis((long)var1.maxAgeSeconds()));
               }

               int var9 = var1.minFreshSeconds();
               long var10 = 0L;
               if (var9 != -1) {
                  var5 = TimeUnit.SECONDS.toMillis((long)var1.minFreshSeconds());
               } else {
                  var5 = 0L;
               }

               long var12 = var10;
               if (!var2.mustRevalidate()) {
                  var12 = var10;
                  if (var1.maxStaleSeconds() != -1) {
                     var12 = TimeUnit.SECONDS.toMillis((long)var1.maxStaleSeconds());
                  }
               }

               if (!var2.noCache()) {
                  var5 += var3;
                  if (var5 < var12 + var7) {
                     Response.Builder var17 = this.cacheResponse.newBuilder();
                     if (var5 >= var7) {
                        var17.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                     }

                     if (var3 > 86400000L && this.isFreshnessLifetimeHeuristic()) {
                        var17.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                     }

                     return new CacheStrategy((Request)null, var17.build());
                  }
               }

               String var15 = this.etag;
               String var16 = "If-Modified-Since";
               if (var15 != null) {
                  var16 = "If-None-Match";
               } else if (this.lastModified != null) {
                  var15 = this.lastModifiedString;
               } else {
                  if (this.servedDate == null) {
                     return new CacheStrategy(this.request, (Response)null);
                  }

                  var15 = this.servedDateString;
               }

               Headers.Builder var14 = this.request.headers().newBuilder();
               if (var15 == null) {
                  Intrinsics.throwNpe();
               }

               var14.addLenient$okhttp(var16, var15);
               return new CacheStrategy(this.request.newBuilder().headers(var14.build()).build(), this.cacheResponse);
            } else {
               return new CacheStrategy(this.request, (Response)null);
            }
         }
      }

      private final long computeFreshnessLifetime() {
         Response var1 = this.cacheResponse;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         CacheControl var9 = var1.cacheControl();
         if (var9.maxAgeSeconds() != -1) {
            return TimeUnit.SECONDS.toMillis((long)var9.maxAgeSeconds());
         } else {
            Date var2 = this.expires;
            long var3 = 0L;
            long var5;
            Date var10;
            if (var2 != null) {
               var10 = this.servedDate;
               if (var10 != null) {
                  var5 = var10.getTime();
               } else {
                  var5 = this.receivedResponseMillis;
               }

               var5 = var2.getTime() - var5;
               if (var5 > 0L) {
                  var3 = var5;
               }

               return var3;
            } else {
               var5 = var3;
               if (this.lastModified != null) {
                  var5 = var3;
                  if (this.cacheResponse.request().url().query() == null) {
                     var10 = this.servedDate;
                     if (var10 != null) {
                        var5 = var10.getTime();
                     } else {
                        var5 = this.sentRequestMillis;
                     }

                     var10 = this.lastModified;
                     if (var10 == null) {
                        Intrinsics.throwNpe();
                     }

                     long var7 = var5 - var10.getTime();
                     var5 = var3;
                     if (var7 > 0L) {
                        var5 = var7 / (long)10;
                     }
                  }
               }

               return var5;
            }
         }
      }

      private final boolean hasConditions(Request var1) {
         boolean var2;
         if (var1.header("If-Modified-Since") == null && var1.header("If-None-Match") == null) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      private final boolean isFreshnessLifetimeHeuristic() {
         Response var1 = this.cacheResponse;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         boolean var2;
         if (var1.cacheControl().maxAgeSeconds() == -1 && this.expires == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public final CacheStrategy compute() {
         CacheStrategy var1 = this.computeCandidate();
         CacheStrategy var2 = var1;
         if (var1.getNetworkRequest() != null) {
            var2 = var1;
            if (this.request.cacheControl().onlyIfCached()) {
               var2 = new CacheStrategy((Request)null, (Response)null);
            }
         }

         return var2;
      }

      public final Request getRequest$okhttp() {
         return this.request;
      }
   }
}

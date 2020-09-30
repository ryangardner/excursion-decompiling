/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.cache;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.CacheControl;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.http.DatesKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u000b2\u00020\u0001:\u0002\u000b\fB\u001b\b\u0000\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lokhttp3/internal/cache/CacheStrategy;", "", "networkRequest", "Lokhttp3/Request;", "cacheResponse", "Lokhttp3/Response;", "(Lokhttp3/Request;Lokhttp3/Response;)V", "getCacheResponse", "()Lokhttp3/Response;", "getNetworkRequest", "()Lokhttp3/Request;", "Companion", "Factory", "okhttp"}, k=1, mv={1, 1, 16})
public final class CacheStrategy {
    public static final Companion Companion = new Companion(null);
    private final Response cacheResponse;
    private final Request networkRequest;

    public CacheStrategy(Request request, Response response) {
        this.networkRequest = request;
        this.cacheResponse = response;
    }

    public final Response getCacheResponse() {
        return this.cacheResponse;
    }

    public final Request getNetworkRequest() {
        return this.networkRequest;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\t"}, d2={"Lokhttp3/internal/cache/CacheStrategy$Companion;", "", "()V", "isCacheable", "", "response", "Lokhttp3/Response;", "request", "Lokhttp3/Request;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /*
         * Unable to fully structure code
         */
        public final boolean isCacheable(Response var1_1, Request var2_2) {
            block6 : {
                Intrinsics.checkParameterIsNotNull(var1_1, "response");
                Intrinsics.checkParameterIsNotNull(var2_2, "request");
                var3_3 = var1_1.code();
                var4_4 = false;
                if (var3_3 == 200 || var3_3 == 410 || var3_3 == 414 || var3_3 == 501 || var3_3 == 203 || var3_3 == 204) break block6;
                if (var3_3 == 307) ** GOTO lbl-1000
                if (var3_3 == 308 || var3_3 == 404 || var3_3 == 405) break block6;
                switch (var3_3) {
                    default: {
                        return false;
                    }
                    case 302: lbl-1000: // 2 sources:
                    {
                        if (Response.header$default(var1_1, "Expires", null, 2, null) != null || var1_1.cacheControl().maxAgeSeconds() != -1 || var1_1.cacheControl().isPublic() || var1_1.cacheControl().isPrivate()) break;
                        return false;
                    }
                    case 300: 
                    case 301: 
                }
            }
            var5_5 = var4_4;
            if (var1_1.cacheControl().noStore() != false) return var5_5;
            var5_5 = var4_4;
            if (var2_2.cacheControl().noStore() != false) return var5_5;
            return true;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0017\u001a\u00020\u0003H\u0002J\u0006\u0010\u0018\u001a\u00020\u0019J\b\u0010\u001a\u001a\u00020\u0019H\u0002J\b\u0010\u001b\u001a\u00020\u0003H\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2={"Lokhttp3/internal/cache/CacheStrategy$Factory;", "", "nowMillis", "", "request", "Lokhttp3/Request;", "cacheResponse", "Lokhttp3/Response;", "(JLokhttp3/Request;Lokhttp3/Response;)V", "ageSeconds", "", "etag", "", "expires", "Ljava/util/Date;", "lastModified", "lastModifiedString", "receivedResponseMillis", "getRequest$okhttp", "()Lokhttp3/Request;", "sentRequestMillis", "servedDate", "servedDateString", "cacheResponseAge", "compute", "Lokhttp3/internal/cache/CacheStrategy;", "computeCandidate", "computeFreshnessLifetime", "hasConditions", "", "isFreshnessLifetimeHeuristic", "okhttp"}, k=1, mv={1, 1, 16})
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

        public Factory(long l, Request object, Response object2) {
            Intrinsics.checkParameterIsNotNull(object, "request");
            this.nowMillis = l;
            this.request = object;
            this.cacheResponse = object2;
            this.ageSeconds = -1;
            if (object2 == null) return;
            this.sentRequestMillis = ((Response)object2).sentRequestAtMillis();
            this.receivedResponseMillis = this.cacheResponse.receivedResponseAtMillis();
            Headers headers = this.cacheResponse.headers();
            int n = 0;
            int n2 = headers.size();
            while (n < n2) {
                object = headers.name(n);
                object2 = headers.value(n);
                if (StringsKt.equals((String)object, "Date", true)) {
                    this.servedDate = DatesKt.toHttpDateOrNull((String)object2);
                    this.servedDateString = object2;
                } else if (StringsKt.equals((String)object, "Expires", true)) {
                    this.expires = DatesKt.toHttpDateOrNull((String)object2);
                } else if (StringsKt.equals((String)object, "Last-Modified", true)) {
                    this.lastModified = DatesKt.toHttpDateOrNull((String)object2);
                    this.lastModifiedString = object2;
                } else if (StringsKt.equals((String)object, "ETag", true)) {
                    this.etag = object2;
                } else if (StringsKt.equals((String)object, "Age", true)) {
                    this.ageSeconds = Util.toNonNegativeInt((String)object2, -1);
                }
                ++n;
            }
        }

        private final long cacheResponseAge() {
            Date date = this.servedDate;
            long l = 0L;
            if (date != null) {
                l = Math.max(0L, this.receivedResponseMillis - date.getTime());
            }
            long l2 = l;
            if (this.ageSeconds != -1) {
                l2 = Math.max(l, TimeUnit.SECONDS.toMillis(this.ageSeconds));
            }
            l = this.receivedResponseMillis;
            return l2 + (l - this.sentRequestMillis) + (this.nowMillis - l);
        }

        private final CacheStrategy computeCandidate() {
            long l;
            if (this.cacheResponse == null) {
                return new CacheStrategy(this.request, null);
            }
            if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
                return new CacheStrategy(this.request, null);
            }
            if (!Companion.isCacheable(this.cacheResponse, this.request)) {
                return new CacheStrategy(this.request, null);
            }
            Object object = this.request.cacheControl();
            if (((CacheControl)object).noCache()) return new CacheStrategy(this.request, null);
            if (this.hasConditions(this.request)) {
                return new CacheStrategy(this.request, null);
            }
            Object object2 = this.cacheResponse.cacheControl();
            long l2 = this.cacheResponseAge();
            long l3 = l = this.computeFreshnessLifetime();
            if (((CacheControl)object).maxAgeSeconds() != -1) {
                l3 = Math.min(l, TimeUnit.SECONDS.toMillis(((CacheControl)object).maxAgeSeconds()));
            }
            int n = ((CacheControl)object).minFreshSeconds();
            long l4 = 0L;
            l = n != -1 ? TimeUnit.SECONDS.toMillis(((CacheControl)object).minFreshSeconds()) : 0L;
            long l5 = l4;
            if (!((CacheControl)object2).mustRevalidate()) {
                l5 = l4;
                if (((CacheControl)object).maxStaleSeconds() != -1) {
                    l5 = TimeUnit.SECONDS.toMillis(((CacheControl)object).maxStaleSeconds());
                }
            }
            if (!((CacheControl)object2).noCache() && (l += l2) < l5 + l3) {
                object = this.cacheResponse.newBuilder();
                if (l >= l3) {
                    ((Response.Builder)object).addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                }
                if (l2 <= 86400000L) return new CacheStrategy(null, ((Response.Builder)object).build());
                if (!this.isFreshnessLifetimeHeuristic()) return new CacheStrategy(null, ((Response.Builder)object).build());
                ((Response.Builder)object).addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                return new CacheStrategy(null, ((Response.Builder)object).build());
            }
            object = this.etag;
            object2 = "If-Modified-Since";
            if (object != null) {
                object2 = "If-None-Match";
            } else if (this.lastModified != null) {
                object = this.lastModifiedString;
            } else {
                if (this.servedDate == null) return new CacheStrategy(this.request, null);
                object = this.servedDateString;
            }
            Headers.Builder builder = this.request.headers().newBuilder();
            if (object == null) {
                Intrinsics.throwNpe();
            }
            builder.addLenient$okhttp((String)object2, (String)object);
            return new CacheStrategy(this.request.newBuilder().headers(builder.build()).build(), this.cacheResponse);
        }

        private final long computeFreshnessLifetime() {
            Object object = this.cacheResponse;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            if (((CacheControl)(object = ((Response)object).cacheControl())).maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis(((CacheControl)object).maxAgeSeconds());
            }
            Date date = this.expires;
            long l = 0L;
            if (date != null) {
                object = this.servedDate;
                long l2 = object != null ? ((Date)object).getTime() : this.receivedResponseMillis;
                l2 = date.getTime() - l2;
                if (l2 <= 0L) return l;
                return l2;
            }
            long l3 = l;
            if (this.lastModified == null) return l3;
            l3 = l;
            if (this.cacheResponse.request().url().query() != null) return l3;
            object = this.servedDate;
            l3 = object != null ? ((Date)object).getTime() : this.sentRequestMillis;
            object = this.lastModified;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            long l4 = l3 - ((Date)object).getTime();
            l3 = l;
            if (l4 <= 0L) return l3;
            return l4 / (long)10;
        }

        private final boolean hasConditions(Request request) {
            if (request.header("If-Modified-Since") != null) return true;
            if (request.header("If-None-Match") != null) return true;
            return false;
        }

        private final boolean isFreshnessLifetimeHeuristic() {
            Response response = this.cacheResponse;
            if (response == null) {
                Intrinsics.throwNpe();
            }
            if (response.cacheControl().maxAgeSeconds() != -1) return false;
            if (this.expires != null) return false;
            return true;
        }

        public final CacheStrategy compute() {
            CacheStrategy cacheStrategy;
            CacheStrategy cacheStrategy2 = cacheStrategy = this.computeCandidate();
            if (cacheStrategy.getNetworkRequest() == null) return cacheStrategy2;
            cacheStrategy2 = cacheStrategy;
            if (!this.request.cacheControl().onlyIfCached()) return cacheStrategy2;
            return new CacheStrategy(null, null);
        }

        public final Request getRequest$okhttp() {
            return this.request;
        }
    }

}


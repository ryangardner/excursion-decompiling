/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.cache.CacheInterceptor$cacheWritingResponse
 *  okhttp3.internal.cache.CacheInterceptor$cacheWritingResponse$cacheWritingSource
 */
package okhttp3.internal.cache;

import java.io.Closeable;
import java.io.IOException;
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
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\bH\u0002J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2={"Lokhttp3/internal/cache/CacheInterceptor;", "Lokhttp3/Interceptor;", "cache", "Lokhttp3/Cache;", "(Lokhttp3/Cache;)V", "getCache$okhttp", "()Lokhttp3/Cache;", "cacheWritingResponse", "Lokhttp3/Response;", "cacheRequest", "Lokhttp3/internal/cache/CacheRequest;", "response", "intercept", "chain", "Lokhttp3/Interceptor$Chain;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class CacheInterceptor
implements Interceptor {
    public static final Companion Companion = new Companion(null);
    private final Cache cache;

    public CacheInterceptor(Cache cache) {
        this.cache = cache;
    }

    private final Response cacheWritingResponse(CacheRequest object, Response response) throws IOException {
        if (object == null) {
            return response;
        }
        Object object2 = object.body();
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            Intrinsics.throwNpe();
        }
        object = new Source(responseBody.source(), (CacheRequest)object, Okio.buffer((Sink)object2)){
            final /* synthetic */ BufferedSink $cacheBody;
            final /* synthetic */ CacheRequest $cacheRequest;
            final /* synthetic */ BufferedSource $source;
            private boolean cacheRequestClosed;
            {
                this.$source = bufferedSource;
                this.$cacheRequest = cacheRequest;
                this.$cacheBody = bufferedSink;
            }

            public void close() throws IOException {
                if (!this.cacheRequestClosed && !Util.discard(this, 100, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                    this.cacheRequestClosed = true;
                    this.$cacheRequest.abort();
                }
                this.$source.close();
            }

            public long read(okio.Buffer buffer, long l) throws IOException {
                block2 : {
                    Intrinsics.checkParameterIsNotNull(buffer, "sink");
                    try {
                        l = this.$source.read(buffer, l);
                        if (l != -1L) break block2;
                        if (this.cacheRequestClosed) return -1L;
                        this.cacheRequestClosed = true;
                    }
                    catch (IOException iOException) {
                        if (this.cacheRequestClosed) throw (Throwable)iOException;
                        this.cacheRequestClosed = true;
                        this.$cacheRequest.abort();
                        throw (Throwable)iOException;
                    }
                    this.$cacheBody.close();
                    return -1L;
                }
                buffer.copyTo(this.$cacheBody.getBuffer(), buffer.size() - l, l);
                this.$cacheBody.emitCompleteSegments();
                return l;
            }

            public okio.Timeout timeout() {
                return this.$source.timeout();
            }
        };
        object2 = Response.header$default(response, "Content-Type", null, 2, null);
        long l = response.body().contentLength();
        return response.newBuilder().body(new RealResponseBody((String)object2, l, Okio.buffer((Source)object))).build();
    }

    public final Cache getCache$okhttp() {
        return this.cache;
    }

    @Override
    public Response intercept(Interceptor.Chain object) throws IOException {
        Response response;
        Closeable closeable;
        Request request;
        Object object2;
        Call call;
        block20 : {
            Intrinsics.checkParameterIsNotNull(object, "chain");
            call = object.call();
            closeable = this.cache;
            closeable = closeable != null ? ((Cache)closeable).get$okhttp(object.request()) : null;
            Object object3 = new CacheStrategy.Factory(System.currentTimeMillis(), object.request(), (Response)closeable).compute();
            request = ((CacheStrategy)object3).getNetworkRequest();
            response = ((CacheStrategy)object3).getCacheResponse();
            object2 = this.cache;
            if (object2 != null) {
                ((Cache)object2).trackResponse$okhttp((CacheStrategy)object3);
            }
            object2 = !(call instanceof RealCall) ? null : call;
            object2 = (RealCall)object2;
            if (object2 == null || (object2 = ((RealCall)object2).getEventListener$okhttp()) == null) {
                object2 = EventListener.NONE;
            }
            if (closeable != null && response == null && (object3 = ((Response)closeable).body()) != null) {
                Util.closeQuietly((Closeable)object3);
            }
            if (request == null && response == null) {
                object = new Response.Builder().request(object.request()).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(System.currentTimeMillis()).build();
                ((EventListener)object2).satisfactionFailure(call, (Response)object);
                return object;
            }
            if (request == null) {
                if (response == null) {
                    Intrinsics.throwNpe();
                }
                object = response.newBuilder().cacheResponse(CacheInterceptor.Companion.stripBody(response)).build();
                ((EventListener)object2).cacheHit(call, (Response)object);
                return object;
            }
            if (response != null) {
                ((EventListener)object2).cacheConditionalHit(call, response);
            } else if (this.cache != null) {
                ((EventListener)object2).cacheMiss(call);
            }
            object3 = null;
            try {
                object = object.proceed(request);
                if (object != null || closeable == null || (closeable = ((Response)closeable).body()) == null) break block20;
            }
            catch (Throwable throwable) {
                if (closeable == null) throw throwable;
                if ((closeable = ((Response)closeable).body()) == null) throw throwable;
                Util.closeQuietly(closeable);
                throw throwable;
            }
            Util.closeQuietly(closeable);
        }
        if (response != null) {
            if (object != null && ((Response)object).code() == 304) {
                closeable = response.newBuilder().headers(CacheInterceptor.Companion.combine(response.headers(), ((Response)object).headers())).sentRequestAtMillis(((Response)object).sentRequestAtMillis()).receivedResponseAtMillis(((Response)object).receivedResponseAtMillis()).cacheResponse(CacheInterceptor.Companion.stripBody(response)).networkResponse(CacheInterceptor.Companion.stripBody((Response)object)).build();
                if ((object = ((Response)object).body()) == null) {
                    Intrinsics.throwNpe();
                }
                ((ResponseBody)object).close();
                object = this.cache;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                ((Cache)object).trackConditionalCacheHit$okhttp();
                this.cache.update$okhttp(response, (Response)closeable);
                ((EventListener)object2).cacheHit(call, (Response)closeable);
                return closeable;
            }
            closeable = response.body();
            if (closeable != null) {
                Util.closeQuietly(closeable);
            }
        }
        if (object == null) {
            Intrinsics.throwNpe();
        }
        object = ((Response)object).newBuilder().cacheResponse(CacheInterceptor.Companion.stripBody(response)).networkResponse(CacheInterceptor.Companion.stripBody((Response)object)).build();
        if (this.cache == null) return object;
        if (HttpHeaders.promisesBody((Response)object) && CacheStrategy.Companion.isCacheable((Response)object, request)) {
            object = this.cacheWritingResponse(this.cache.put$okhttp((Response)object), (Response)object);
            if (response == null) return object;
            ((EventListener)object2).cacheMiss(call);
            return object;
        }
        if (!HttpMethod.INSTANCE.invalidatesCache(request.method())) return object;
        try {
            this.cache.remove$okhttp(request);
            return object;
        }
        catch (IOException iOException) {
            return object;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0014\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0002\u00a8\u0006\u000f"}, d2={"Lokhttp3/internal/cache/CacheInterceptor$Companion;", "", "()V", "combine", "Lokhttp3/Headers;", "cachedHeaders", "networkHeaders", "isContentSpecificHeader", "", "fieldName", "", "isEndToEnd", "stripBody", "Lokhttp3/Response;", "response", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Headers combine(Headers object, Headers headers) {
            String string2;
            int n;
            Headers.Builder builder = new Headers.Builder();
            int n2 = ((Headers)object).size();
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                Companion companion;
                String string3 = ((Headers)object).name(n);
                string2 = ((Headers)object).value(n);
                if (StringsKt.equals("Warning", string3, true) && StringsKt.startsWith$default(string2, "1", false, 2, null) || !(companion = this).isContentSpecificHeader(string3) && companion.isEndToEnd(string3) && headers.get(string3) != null) continue;
                builder.addLenient$okhttp(string3, string2);
            }
            n2 = headers.size();
            n = n3;
            while (n < n2) {
                object = this;
                string2 = headers.name(n);
                if (!Companion.super.isContentSpecificHeader(string2) && Companion.super.isEndToEnd(string2)) {
                    builder.addLenient$okhttp(string2, headers.value(n));
                }
                ++n;
            }
            return builder.build();
        }

        private final boolean isContentSpecificHeader(String string2) {
            boolean bl;
            boolean bl2 = bl = true;
            if (StringsKt.equals("Content-Length", string2, true)) return bl2;
            bl2 = bl;
            if (StringsKt.equals("Content-Encoding", string2, true)) return bl2;
            if (!StringsKt.equals("Content-Type", string2, true)) return false;
            return bl;
        }

        private final boolean isEndToEnd(String string2) {
            boolean bl = true;
            if (StringsKt.equals("Connection", string2, true)) return false;
            if (StringsKt.equals("Keep-Alive", string2, true)) return false;
            if (StringsKt.equals("Proxy-Authenticate", string2, true)) return false;
            if (StringsKt.equals("Proxy-Authorization", string2, true)) return false;
            if (StringsKt.equals("TE", string2, true)) return false;
            if (StringsKt.equals("Trailers", string2, true)) return false;
            if (StringsKt.equals("Transfer-Encoding", string2, true)) return false;
            if (StringsKt.equals("Upgrade", string2, true)) return false;
            return bl;
        }

        private final Response stripBody(Response response) {
            ResponseBody responseBody = response != null ? response.body() : null;
            Response response2 = response;
            if (responseBody == null) return response2;
            return response.newBuilder().body(null).build();
        }
    }

}


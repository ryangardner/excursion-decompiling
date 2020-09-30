/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lokhttp3/internal/http/BridgeInterceptor;", "Lokhttp3/Interceptor;", "cookieJar", "Lokhttp3/CookieJar;", "(Lokhttp3/CookieJar;)V", "cookieHeader", "", "cookies", "", "Lokhttp3/Cookie;", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "okhttp"}, k=1, mv={1, 1, 16})
public final class BridgeInterceptor
implements Interceptor {
    private final CookieJar cookieJar;

    public BridgeInterceptor(CookieJar cookieJar) {
        Intrinsics.checkParameterIsNotNull(cookieJar, "cookieJar");
        this.cookieJar = cookieJar;
    }

    private final String cookieHeader(List<Cookie> object) {
        StringBuilder stringBuilder = new StringBuilder();
        object = ((Iterable)object).iterator();
        int n = 0;
        do {
            if (!object.hasNext()) {
                object = stringBuilder.toString();
                Intrinsics.checkExpressionValueIsNotNull(object, "StringBuilder().apply(builderAction).toString()");
                return object;
            }
            Object object2 = object.next();
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            object2 = (Cookie)object2;
            if (n > 0) {
                stringBuilder.append("; ");
            }
            stringBuilder.append(((Cookie)object2).name());
            stringBuilder.append('=');
            stringBuilder.append(((Cookie)object2).value());
            ++n;
        } while (true);
    }

    @Override
    public Response intercept(Interceptor.Chain object) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "chain");
        Object object2 = object.request();
        Object object3 = ((Request)object2).newBuilder();
        List<Cookie> list = ((Request)object2).body();
        if (list != null) {
            long l;
            MediaType mediaType = ((RequestBody)((Object)list)).contentType();
            if (mediaType != null) {
                ((Request.Builder)object3).header("Content-Type", mediaType.toString());
            }
            if ((l = ((RequestBody)((Object)list)).contentLength()) != -1L) {
                ((Request.Builder)object3).header("Content-Length", String.valueOf(l));
                ((Request.Builder)object3).removeHeader("Transfer-Encoding");
            } else {
                ((Request.Builder)object3).header("Transfer-Encoding", "chunked");
                ((Request.Builder)object3).removeHeader("Content-Length");
            }
        }
        list = ((Request)object2).header("Host");
        boolean bl = false;
        if (list == null) {
            ((Request.Builder)object3).header("Host", Util.toHostHeader$default(((Request)object2).url(), false, 1, null));
        }
        if (((Request)object2).header("Connection") == null) {
            ((Request.Builder)object3).header("Connection", "Keep-Alive");
        }
        boolean bl2 = bl;
        if (((Request)object2).header("Accept-Encoding") == null) {
            bl2 = bl;
            if (((Request)object2).header("Range") == null) {
                ((Request.Builder)object3).header("Accept-Encoding", "gzip");
                bl2 = true;
            }
        }
        if (((Collection)(list = this.cookieJar.loadForRequest(((Request)object2).url()))).isEmpty() ^ true) {
            ((Request.Builder)object3).header("Cookie", this.cookieHeader(list));
        }
        if (((Request)object2).header("User-Agent") == null) {
            ((Request.Builder)object3).header("User-Agent", "okhttp/4.8.1");
        }
        object = object.proceed(((Request.Builder)object3).build());
        HttpHeaders.receiveHeaders(this.cookieJar, ((Request)object2).url(), ((Response)object).headers());
        object2 = ((Response)object).newBuilder().request((Request)object2);
        if (!bl2) return ((Response.Builder)object2).build();
        if (!StringsKt.equals("gzip", Response.header$default((Response)object, "Content-Encoding", null, 2, null), true)) return ((Response.Builder)object2).build();
        if (!HttpHeaders.promisesBody((Response)object)) return ((Response.Builder)object2).build();
        object3 = ((Response)object).body();
        if (object3 == null) return ((Response.Builder)object2).build();
        object3 = new GzipSource(((ResponseBody)object3).source());
        ((Response.Builder)object2).headers(((Response)object).headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build());
        ((Response.Builder)object2).body(new RealResponseBody(Response.header$default((Response)object, "Content-Type", null, 2, null), -1L, Okio.buffer((Source)object3)));
        return ((Response.Builder)object2).build();
    }
}


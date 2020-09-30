/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Handshake;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealInterceptorChain;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lokhttp3/internal/http/CallServerInterceptor;", "Lokhttp3/Interceptor;", "forWebSocket", "", "(Z)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "okhttp"}, k=1, mv={1, 1, 16})
public final class CallServerInterceptor
implements Interceptor {
    private final boolean forWebSocket;

    public CallServerInterceptor(boolean bl) {
        this.forWebSocket = bl;
    }

    @Override
    public Response intercept(Interceptor.Chain object) throws IOException {
        int n;
        int n2;
        int n3;
        Object object2;
        Intrinsics.checkParameterIsNotNull(object, "chain");
        object = (RealInterceptorChain)object;
        Object object3 = ((RealInterceptorChain)object).getExchange$okhttp();
        if (object3 == null) {
            Intrinsics.throwNpe();
        }
        Request request = ((RealInterceptorChain)object).getRequest$okhttp();
        RequestBody requestBody = request.body();
        long l = System.currentTimeMillis();
        ((Exchange)object3).writeRequestHeaders(request);
        Object var7_6 = null;
        object = null;
        if (HttpMethod.permitsRequestBody(request.method()) && requestBody != null) {
            if (StringsKt.equals("100-continue", request.header("Expect"), true)) {
                ((Exchange)object3).flushRequest();
                object2 = ((Exchange)object3).readResponseHeaders(true);
                ((Exchange)object3).responseHeadersStart();
                n3 = 0;
            } else {
                n3 = 1;
                object2 = object;
            }
            if (object2 == null) {
                if (requestBody.isDuplex()) {
                    ((Exchange)object3).flushRequest();
                    requestBody.writeTo(Okio.buffer(((Exchange)object3).createRequestBody(request, true)));
                    object = object2;
                    n = n3;
                } else {
                    object = Okio.buffer(((Exchange)object3).createRequestBody(request, false));
                    requestBody.writeTo((BufferedSink)object);
                    object.close();
                    object = object2;
                    n = n3;
                }
            } else {
                ((Exchange)object3).noRequestBody();
                object = object2;
                n = n3;
                if (!((Exchange)object3).getConnection$okhttp().isMultiplexed$okhttp()) {
                    ((Exchange)object3).noNewExchangesOnConnection();
                    object = object2;
                    n = n3;
                }
            }
        } else {
            ((Exchange)object3).noRequestBody();
            n = 1;
        }
        if (requestBody == null || !requestBody.isDuplex()) {
            ((Exchange)object3).finishRequest();
        }
        object2 = object;
        n3 = n;
        if (object == null) {
            object = ((Exchange)object3).readResponseHeaders(false);
            if (object == null) {
                Intrinsics.throwNpe();
            }
            object2 = object;
            n3 = n;
            if (n != 0) {
                ((Exchange)object3).responseHeadersStart();
                n3 = 0;
                object2 = object;
            }
        }
        object = ((Response.Builder)object2).request(request).handshake(((Exchange)object3).getConnection$okhttp().handshake()).sentRequestAtMillis(l).receivedResponseAtMillis(System.currentTimeMillis()).build();
        n = n2 = ((Response)object).code();
        if (n2 == 100) {
            object = ((Exchange)object3).readResponseHeaders(false);
            if (object == null) {
                Intrinsics.throwNpe();
            }
            if (n3 != 0) {
                ((Exchange)object3).responseHeadersStart();
            }
            object = ((Response.Builder)object).request(request).handshake(((Exchange)object3).getConnection$okhttp().handshake()).sentRequestAtMillis(l).receivedResponseAtMillis(System.currentTimeMillis()).build();
            n = ((Response)object).code();
        }
        ((Exchange)object3).responseHeadersEnd((Response)object);
        object = this.forWebSocket && n == 101 ? ((Response)object).newBuilder().body(Util.EMPTY_RESPONSE).build() : ((Response)object).newBuilder().body(((Exchange)object3).openResponseBody((Response)object)).build();
        if (StringsKt.equals("close", ((Response)object).request().header("Connection"), true) || StringsKt.equals("close", Response.header$default((Response)object, "Connection", null, 2, null), true)) {
            ((Exchange)object3).noNewExchangesOnConnection();
        }
        if (n != 204) {
            if (n != 205) return object;
        }
        l = (object2 = ((Response)object).body()) != null ? ((ResponseBody)object2).contentLength() : -1L;
        if (l <= 0L) return object;
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("HTTP ");
        ((StringBuilder)object2).append(n);
        ((StringBuilder)object2).append(" had non-zero Content-Length: ");
        object3 = ((Response)object).body();
        object = var7_6;
        if (object3 != null) {
            object = ((ResponseBody)object3).contentLength();
        }
        ((StringBuilder)object2).append(object);
        throw (Throwable)new ProtocolException(((StringBuilder)object2).toString());
    }
}


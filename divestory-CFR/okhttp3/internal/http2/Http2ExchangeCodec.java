/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http2;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2Stream;
import okio.ByteString;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 (2\u00020\u0001:\u0001(B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0014H\u0016J\b\u0010\u001c\u001a\u00020\u0014H\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0012\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\fH\u0016J\u0010\u0010$\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 H\u0016J\b\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2={"Lokhttp3/internal/http2/Http2ExchangeCodec;", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "connection", "Lokhttp3/internal/connection/RealConnection;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "http2Connection", "Lokhttp3/internal/http2/Http2Connection;", "(Lokhttp3/OkHttpClient;Lokhttp3/internal/connection/RealConnection;Lokhttp3/internal/http/RealInterceptorChain;Lokhttp3/internal/http2/Http2Connection;)V", "canceled", "", "getConnection", "()Lokhttp3/internal/connection/RealConnection;", "protocol", "Lokhttp3/Protocol;", "stream", "Lokhttp3/internal/http2/Http2Stream;", "cancel", "", "createRequestBody", "Lokio/Sink;", "request", "Lokhttp3/Request;", "contentLength", "", "finishRequest", "flushRequest", "openResponseBodySource", "Lokio/Source;", "response", "Lokhttp3/Response;", "readResponseHeaders", "Lokhttp3/Response$Builder;", "expectContinue", "reportedContentLength", "trailers", "Lokhttp3/Headers;", "writeRequestHeaders", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class Http2ExchangeCodec
implements ExchangeCodec {
    private static final String CONNECTION = "connection";
    public static final Companion Companion = new Companion(null);
    private static final String ENCODING = "encoding";
    private static final String HOST = "host";
    private static final List<String> HTTP_2_SKIPPED_REQUEST_HEADERS = Util.immutableListOf("connection", "host", "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade", ":method", ":path", ":scheme", ":authority");
    private static final List<String> HTTP_2_SKIPPED_RESPONSE_HEADERS = Util.immutableListOf("connection", "host", "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade");
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String PROXY_CONNECTION = "proxy-connection";
    private static final String TE = "te";
    private static final String TRANSFER_ENCODING = "transfer-encoding";
    private static final String UPGRADE = "upgrade";
    private volatile boolean canceled;
    private final RealInterceptorChain chain;
    private final RealConnection connection;
    private final Http2Connection http2Connection;
    private final Protocol protocol;
    private volatile Http2Stream stream;

    public Http2ExchangeCodec(OkHttpClient object, RealConnection realConnection, RealInterceptorChain realInterceptorChain, Http2Connection http2Connection) {
        Intrinsics.checkParameterIsNotNull(object, "client");
        Intrinsics.checkParameterIsNotNull(realConnection, CONNECTION);
        Intrinsics.checkParameterIsNotNull(realInterceptorChain, "chain");
        Intrinsics.checkParameterIsNotNull(http2Connection, "http2Connection");
        this.connection = realConnection;
        this.chain = realInterceptorChain;
        this.http2Connection = http2Connection;
        object = object.protocols().contains((Object)Protocol.H2_PRIOR_KNOWLEDGE) ? Protocol.H2_PRIOR_KNOWLEDGE : Protocol.HTTP_2;
        this.protocol = object;
    }

    @Override
    public void cancel() {
        this.canceled = true;
        Http2Stream http2Stream = this.stream;
        if (http2Stream == null) return;
        http2Stream.closeLater(ErrorCode.CANCEL);
    }

    @Override
    public Sink createRequestBody(Request object, long l) {
        Intrinsics.checkParameterIsNotNull(object, "request");
        object = this.stream;
        if (object != null) return ((Http2Stream)object).getSink();
        Intrinsics.throwNpe();
        return ((Http2Stream)object).getSink();
    }

    @Override
    public void finishRequest() {
        Http2Stream http2Stream = this.stream;
        if (http2Stream == null) {
            Intrinsics.throwNpe();
        }
        http2Stream.getSink().close();
    }

    @Override
    public void flushRequest() {
        this.http2Connection.flush();
    }

    @Override
    public RealConnection getConnection() {
        return this.connection;
    }

    @Override
    public Source openResponseBodySource(Response object) {
        Intrinsics.checkParameterIsNotNull(object, "response");
        object = this.stream;
        if (object != null) return ((Http2Stream)object).getSource$okhttp();
        Intrinsics.throwNpe();
        return ((Http2Stream)object).getSource$okhttp();
    }

    @Override
    public Response.Builder readResponseHeaders(boolean bl) {
        Object object = this.stream;
        if (object == null) {
            Intrinsics.throwNpe();
        }
        object = ((Http2Stream)object).takeHeaders();
        Response.Builder builder = Companion.readHttp2HeadersList((Headers)object, this.protocol);
        object = builder;
        if (!bl) return object;
        object = builder;
        if (builder.getCode$okhttp() != 100) return object;
        return null;
    }

    @Override
    public long reportedContentLength(Response response) {
        Intrinsics.checkParameterIsNotNull(response, "response");
        if (HttpHeaders.promisesBody(response)) return Util.headersContentLength(response);
        return 0L;
    }

    @Override
    public Headers trailers() {
        Http2Stream http2Stream = this.stream;
        if (http2Stream != null) return http2Stream.trailers();
        Intrinsics.throwNpe();
        return http2Stream.trailers();
    }

    @Override
    public void writeRequestHeaders(Request object) {
        Intrinsics.checkParameterIsNotNull(object, "request");
        if (this.stream != null) {
            return;
        }
        boolean bl = ((Request)object).body() != null;
        object = Companion.http2HeadersList((Request)object);
        this.stream = this.http2Connection.newStream((List<Header>)object, bl);
        if (this.canceled) {
            object = this.stream;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            ((Http2Stream)object).closeLater(ErrorCode.CANCEL);
            throw (Throwable)new IOException("Canceled");
        }
        object = this.stream;
        if (object == null) {
            Intrinsics.throwNpe();
        }
        ((Http2Stream)object).readTimeout().timeout(this.chain.getReadTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
        object = this.stream;
        if (object == null) {
            Intrinsics.throwNpe();
        }
        ((Http2Stream)object).writeTimeout().timeout(this.chain.getWriteTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\b2\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2={"Lokhttp3/internal/http2/Http2ExchangeCodec$Companion;", "", "()V", "CONNECTION", "", "ENCODING", "HOST", "HTTP_2_SKIPPED_REQUEST_HEADERS", "", "HTTP_2_SKIPPED_RESPONSE_HEADERS", "KEEP_ALIVE", "PROXY_CONNECTION", "TE", "TRANSFER_ENCODING", "UPGRADE", "http2HeadersList", "Lokhttp3/internal/http2/Header;", "request", "Lokhttp3/Request;", "readHttp2HeadersList", "Lokhttp3/Response$Builder;", "headerBlock", "Lokhttp3/Headers;", "protocol", "Lokhttp3/Protocol;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final List<Header> http2HeadersList(Request object) {
            Intrinsics.checkParameterIsNotNull(object, "request");
            Headers headers = ((Request)object).headers();
            ArrayList<Header> arrayList = new ArrayList<Header>(headers.size() + 4);
            arrayList.add(new Header(Header.TARGET_METHOD, ((Request)object).method()));
            arrayList.add(new Header(Header.TARGET_PATH, RequestLine.INSTANCE.requestPath(((Request)object).url())));
            Object object2 = ((Request)object).header("Host");
            if (object2 != null) {
                arrayList.add(new Header(Header.TARGET_AUTHORITY, (String)object2));
            }
            arrayList.add(new Header(Header.TARGET_SCHEME, ((Request)object).url().scheme()));
            int n = 0;
            int n2 = headers.size();
            while (n < n2) {
                object = headers.name(n);
                object2 = Locale.US;
                Intrinsics.checkExpressionValueIsNotNull(object2, "Locale.US");
                if (object == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                object = ((String)object).toLowerCase((Locale)object2);
                Intrinsics.checkExpressionValueIsNotNull(object, "(this as java.lang.String).toLowerCase(locale)");
                if (!HTTP_2_SKIPPED_REQUEST_HEADERS.contains(object) || Intrinsics.areEqual(object, Http2ExchangeCodec.TE) && Intrinsics.areEqual(headers.value(n), "trailers")) {
                    arrayList.add(new Header((String)object, headers.value(n)));
                }
                ++n;
            }
            return arrayList;
        }

        public final Response.Builder readHttp2HeadersList(Headers headers, Protocol protocol) {
            Intrinsics.checkParameterIsNotNull(headers, "headerBlock");
            Intrinsics.checkParameterIsNotNull((Object)protocol, "protocol");
            Object object = null;
            Headers.Builder builder = new Headers.Builder();
            int n = headers.size();
            int n2 = 0;
            do {
                Object object2;
                if (n2 >= n) {
                    if (object == null) throw (Throwable)new ProtocolException("Expected ':status' header not present");
                    return new Response.Builder().protocol(protocol).code(((StatusLine)object).code).message(((StatusLine)object).message).headers(builder.build());
                }
                String string2 = headers.name(n2);
                String string3 = headers.value(n2);
                if (Intrinsics.areEqual(string2, ":status")) {
                    object = StatusLine.Companion;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("HTTP/1.1 ");
                    ((StringBuilder)object2).append(string3);
                    object2 = ((StatusLine.Companion)object).parse(((StringBuilder)object2).toString());
                } else {
                    object2 = object;
                    if (!HTTP_2_SKIPPED_RESPONSE_HEADERS.contains(string2)) {
                        builder.addLenient$okhttp(string2, string3);
                        object2 = object;
                    }
                }
                ++n2;
                object = object2;
            } while (true);
        }
    }

}


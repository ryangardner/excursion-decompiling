/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http1;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Address;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.http1.HeadersReader;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u0000 ?2\u00020\u0001:\u0007<=>?@ABB'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\u001cH\u0016J\b\u0010&\u001a\u00020\u001cH\u0016J\b\u0010'\u001a\u00020\u001eH\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020)2\u0006\u0010-\u001a\u00020!H\u0002J\b\u0010.\u001a\u00020\u001eH\u0002J\b\u0010/\u001a\u00020)H\u0002J\u0010\u00100\u001a\u00020)2\u0006\u00101\u001a\u00020\u0019H\u0016J\u0012\u00102\u001a\u0004\u0018\u0001032\u0006\u00104\u001a\u00020\u0010H\u0016J\u0010\u00105\u001a\u00020!2\u0006\u00101\u001a\u00020\u0019H\u0016J\u000e\u00106\u001a\u00020\u001c2\u0006\u00101\u001a\u00020\u0019J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0016\u00107\u001a\u00020\u001c2\u0006\u00108\u001a\u00020\u00152\u0006\u00109\u001a\u00020:J\u0010\u0010;\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u0017H\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0016\u001a\u00020\u0010*\u00020\u00178BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u0018\u0010\u0016\u001a\u00020\u0010*\u00020\u00198BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u001a\u00a8\u0006C"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec;", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "connection", "Lokhttp3/internal/connection/RealConnection;", "source", "Lokio/BufferedSource;", "sink", "Lokio/BufferedSink;", "(Lokhttp3/OkHttpClient;Lokhttp3/internal/connection/RealConnection;Lokio/BufferedSource;Lokio/BufferedSink;)V", "getConnection", "()Lokhttp3/internal/connection/RealConnection;", "headersReader", "Lokhttp3/internal/http1/HeadersReader;", "isClosed", "", "()Z", "state", "", "trailers", "Lokhttp3/Headers;", "isChunked", "Lokhttp3/Request;", "(Lokhttp3/Request;)Z", "Lokhttp3/Response;", "(Lokhttp3/Response;)Z", "cancel", "", "createRequestBody", "Lokio/Sink;", "request", "contentLength", "", "detachTimeout", "timeout", "Lokio/ForwardingTimeout;", "finishRequest", "flushRequest", "newChunkedSink", "newChunkedSource", "Lokio/Source;", "url", "Lokhttp3/HttpUrl;", "newFixedLengthSource", "length", "newKnownLengthSink", "newUnknownLengthSource", "openResponseBodySource", "response", "readResponseHeaders", "Lokhttp3/Response$Builder;", "expectContinue", "reportedContentLength", "skipConnectBody", "writeRequest", "headers", "requestLine", "", "writeRequestHeaders", "AbstractSource", "ChunkedSink", "ChunkedSource", "Companion", "FixedLengthSource", "KnownLengthSink", "UnknownLengthSource", "okhttp"}, k=1, mv={1, 1, 16})
public final class Http1ExchangeCodec
implements ExchangeCodec {
    public static final Companion Companion = new Companion(null);
    private static final long NO_CHUNK_YET = -1L;
    private static final int STATE_CLOSED = 6;
    private static final int STATE_IDLE = 0;
    private static final int STATE_OPEN_REQUEST_BODY = 1;
    private static final int STATE_OPEN_RESPONSE_BODY = 4;
    private static final int STATE_READING_RESPONSE_BODY = 5;
    private static final int STATE_READ_RESPONSE_HEADERS = 3;
    private static final int STATE_WRITING_REQUEST_BODY = 2;
    private final OkHttpClient client;
    private final RealConnection connection;
    private final HeadersReader headersReader;
    private final BufferedSink sink;
    private final BufferedSource source;
    private int state;
    private Headers trailers;

    public Http1ExchangeCodec(OkHttpClient okHttpClient, RealConnection realConnection, BufferedSource bufferedSource, BufferedSink bufferedSink) {
        Intrinsics.checkParameterIsNotNull(realConnection, "connection");
        Intrinsics.checkParameterIsNotNull(bufferedSource, "source");
        Intrinsics.checkParameterIsNotNull(bufferedSink, "sink");
        this.client = okHttpClient;
        this.connection = realConnection;
        this.source = bufferedSource;
        this.sink = bufferedSink;
        this.headersReader = new HeadersReader(this.source);
    }

    public static final /* synthetic */ OkHttpClient access$getClient$p(Http1ExchangeCodec http1ExchangeCodec) {
        return http1ExchangeCodec.client;
    }

    public static final /* synthetic */ HeadersReader access$getHeadersReader$p(Http1ExchangeCodec http1ExchangeCodec) {
        return http1ExchangeCodec.headersReader;
    }

    public static final /* synthetic */ Headers access$getTrailers$p(Http1ExchangeCodec http1ExchangeCodec) {
        return http1ExchangeCodec.trailers;
    }

    public static final /* synthetic */ void access$setState$p(Http1ExchangeCodec http1ExchangeCodec, int n) {
        http1ExchangeCodec.state = n;
    }

    public static final /* synthetic */ void access$setTrailers$p(Http1ExchangeCodec http1ExchangeCodec, Headers headers) {
        http1ExchangeCodec.trailers = headers;
    }

    private final void detachTimeout(ForwardingTimeout forwardingTimeout) {
        Timeout timeout2 = forwardingTimeout.delegate();
        forwardingTimeout.setDelegate(Timeout.NONE);
        timeout2.clearDeadline();
        timeout2.clearTimeout();
    }

    private final boolean isChunked(Request request) {
        return StringsKt.equals("chunked", request.header("Transfer-Encoding"), true);
    }

    private final boolean isChunked(Response response) {
        return StringsKt.equals("chunked", Response.header$default(response, "Transfer-Encoding", null, 2, null), true);
    }

    private final Sink newChunkedSink() {
        int n = this.state;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        if (bl) {
            this.state = 2;
            return new ChunkedSink();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw (Throwable)new IllegalStateException(stringBuilder.toString().toString());
    }

    private final Source newChunkedSource(HttpUrl object) {
        boolean bl = this.state == 4;
        if (bl) {
            this.state = 5;
            return new ChunkedSource((HttpUrl)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("state: ");
        ((StringBuilder)object).append(this.state);
        throw (Throwable)new IllegalStateException(((StringBuilder)object).toString().toString());
    }

    private final Source newFixedLengthSource(long l) {
        boolean bl = this.state == 4;
        if (bl) {
            this.state = 5;
            return new FixedLengthSource(l);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw (Throwable)new IllegalStateException(stringBuilder.toString().toString());
    }

    private final Sink newKnownLengthSink() {
        int n = this.state;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        if (bl) {
            this.state = 2;
            return new KnownLengthSink();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw (Throwable)new IllegalStateException(stringBuilder.toString().toString());
    }

    private final Source newUnknownLengthSource() {
        boolean bl = this.state == 4;
        if (bl) {
            this.state = 5;
            this.getConnection().noNewExchanges$okhttp();
            return new UnknownLengthSource();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw (Throwable)new IllegalStateException(stringBuilder.toString().toString());
    }

    @Override
    public void cancel() {
        this.getConnection().cancel();
    }

    @Override
    public Sink createRequestBody(Request object, long l) {
        Intrinsics.checkParameterIsNotNull(object, "request");
        if (((Request)object).body() != null) {
            if (((Request)object).body().isDuplex()) throw (Throwable)new ProtocolException("Duplex connections are not supported for HTTP/1");
        }
        if (this.isChunked((Request)object)) {
            return this.newChunkedSink();
        }
        if (l == -1L) throw (Throwable)new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
        return this.newKnownLengthSink();
    }

    @Override
    public void finishRequest() {
        this.sink.flush();
    }

    @Override
    public void flushRequest() {
        this.sink.flush();
    }

    @Override
    public RealConnection getConnection() {
        return this.connection;
    }

    public final boolean isClosed() {
        if (this.state != 6) return false;
        return true;
    }

    @Override
    public Source openResponseBodySource(Response closeable) {
        Intrinsics.checkParameterIsNotNull(closeable, "response");
        if (!HttpHeaders.promisesBody((Response)closeable)) {
            return this.newFixedLengthSource(0L);
        }
        if (this.isChunked((Response)closeable)) {
            return this.newChunkedSource(((Response)closeable).request().url());
        }
        long l = Util.headersContentLength((Response)closeable);
        if (l == -1L) return this.newUnknownLengthSource();
        return this.newFixedLengthSource(l);
    }

    @Override
    public Response.Builder readResponseHeaders(boolean bl) {
        boolean bl2;
        int n = this.state;
        boolean bl3 = bl2 = true;
        if (n != 1) {
            bl3 = n == 3 ? bl2 : false;
        }
        if (!bl3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(this.state);
            throw (Throwable)new IllegalStateException(stringBuilder.toString().toString());
        }
        try {
            StatusLine statusLine = StatusLine.Companion.parse(this.headersReader.readLine());
            Response.Builder builder = new Response.Builder();
            builder = builder.protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message).headers(this.headersReader.readHeaders());
            if (bl && statusLine.code == 100) {
                return null;
            }
            if (statusLine.code == 100) {
                this.state = 3;
                return builder;
            }
            this.state = 4;
            return builder;
        }
        catch (EOFException eOFException) {
            String string2 = this.getConnection().route().address().url().redact();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected end of stream on ");
            stringBuilder.append(string2);
            throw (Throwable)new IOException(stringBuilder.toString(), eOFException);
        }
    }

    @Override
    public long reportedContentLength(Response response) {
        Intrinsics.checkParameterIsNotNull(response, "response");
        if (!HttpHeaders.promisesBody(response)) {
            return 0L;
        }
        if (!this.isChunked(response)) return Util.headersContentLength(response);
        return -1L;
    }

    public final void skipConnectBody(Response closeable) {
        Intrinsics.checkParameterIsNotNull(closeable, "response");
        long l = Util.headersContentLength((Response)closeable);
        if (l == -1L) {
            return;
        }
        closeable = this.newFixedLengthSource(l);
        Util.skipAll((Source)closeable, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        closeable.close();
    }

    @Override
    public Headers trailers() {
        boolean bl = this.state == 6;
        if (!bl) throw (Throwable)new IllegalStateException("too early; can't read the trailers yet".toString());
        Headers headers = this.trailers;
        if (headers == null) return Util.EMPTY_HEADERS;
        return headers;
    }

    public final void writeRequest(Headers object, String string2) {
        Intrinsics.checkParameterIsNotNull(object, "headers");
        Intrinsics.checkParameterIsNotNull(string2, "requestLine");
        int n = this.state;
        int n2 = 0;
        n = n == 0 ? 1 : 0;
        if (n == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("state: ");
            ((StringBuilder)object).append(this.state);
            throw (Throwable)new IllegalStateException(((StringBuilder)object).toString().toString());
        }
        this.sink.writeUtf8(string2).writeUtf8("\r\n");
        int n3 = ((Headers)object).size();
        n = n2;
        do {
            if (n >= n3) {
                this.sink.writeUtf8("\r\n");
                this.state = 1;
                return;
            }
            this.sink.writeUtf8(((Headers)object).name(n)).writeUtf8(": ").writeUtf8(((Headers)object).value(n)).writeUtf8("\r\n");
            ++n;
        } while (true);
    }

    @Override
    public void writeRequestHeaders(Request request) {
        Intrinsics.checkParameterIsNotNull(request, "request");
        Object object = RequestLine.INSTANCE;
        Proxy.Type type = this.getConnection().route().proxy().type();
        Intrinsics.checkExpressionValueIsNotNull((Object)type, "connection.route().proxy.type()");
        object = ((RequestLine)object).get(request, type);
        this.writeRequest(request.headers(), (String)object);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00a2\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000eH\u0016J\r\u0010\u0012\u001a\u00020\u0013H\u0000\u00a2\u0006\u0002\b\u0014J\b\u0010\t\u001a\u00020\u0015H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0016"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokio/Source;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "closed", "", "getClosed", "()Z", "setClosed", "(Z)V", "timeout", "Lokio/ForwardingTimeout;", "getTimeout", "()Lokio/ForwardingTimeout;", "read", "", "sink", "Lokio/Buffer;", "byteCount", "responseBodyComplete", "", "responseBodyComplete$okhttp", "Lokio/Timeout;", "okhttp"}, k=1, mv={1, 1, 16})
    private abstract class AbstractSource
    implements Source {
        private boolean closed;
        private final ForwardingTimeout timeout;

        public AbstractSource() {
            this.timeout = new ForwardingTimeout(Http1ExchangeCodec.this.source.timeout());
        }

        protected final boolean getClosed() {
            return this.closed;
        }

        protected final ForwardingTimeout getTimeout() {
            return this.timeout;
        }

        @Override
        public long read(Buffer buffer, long l) {
            Intrinsics.checkParameterIsNotNull(buffer, "sink");
            try {
                return Http1ExchangeCodec.this.source.read(buffer, l);
            }
            catch (IOException iOException) {
                Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
                this.responseBodyComplete$okhttp();
                throw (Throwable)iOException;
            }
        }

        public final void responseBodyComplete$okhttp() {
            if (Http1ExchangeCodec.this.state == 6) {
                return;
            }
            if (Http1ExchangeCodec.this.state == 5) {
                Http1ExchangeCodec.this.detachTimeout(this.timeout);
                Http1ExchangeCodec.access$setState$p(Http1ExchangeCodec.this, 6);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(Http1ExchangeCodec.this.state);
            throw (Throwable)new IllegalStateException(stringBuilder.toString());
        }

        protected final void setClosed(boolean bl) {
            this.closed = bl;
        }

        @Override
        public Timeout timeout() {
            return this.timeout;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\b\u0010\u0005\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink;", "Lokio/Sink;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "closed", "", "timeout", "Lokio/ForwardingTimeout;", "close", "", "flush", "Lokio/Timeout;", "write", "source", "Lokio/Buffer;", "byteCount", "", "okhttp"}, k=1, mv={1, 1, 16})
    private final class ChunkedSink
    implements Sink {
        private boolean closed;
        private final ForwardingTimeout timeout;

        public ChunkedSink() {
            this.timeout = new ForwardingTimeout(Http1ExchangeCodec.this.sink.timeout());
        }

        @Override
        public void close() {
            synchronized (this) {
                boolean bl = this.closed;
                if (bl) {
                    return;
                }
                this.closed = true;
                Http1ExchangeCodec.this.sink.writeUtf8("0\r\n\r\n");
                Http1ExchangeCodec.this.detachTimeout(this.timeout);
                Http1ExchangeCodec.access$setState$p(Http1ExchangeCodec.this, 3);
                return;
            }
        }

        @Override
        public void flush() {
            synchronized (this) {
                boolean bl = this.closed;
                if (bl) {
                    return;
                }
                Http1ExchangeCodec.this.sink.flush();
                return;
            }
        }

        @Override
        public Timeout timeout() {
            return this.timeout;
        }

        @Override
        public void write(Buffer buffer, long l) {
            Intrinsics.checkParameterIsNotNull(buffer, "source");
            if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
            if (l == 0L) {
                return;
            }
            Http1ExchangeCodec.this.sink.writeHexadecimalUnsignedLong(l);
            Http1ExchangeCodec.this.sink.writeUtf8("\r\n");
            Http1ExchangeCodec.this.sink.write(buffer, l);
            Http1ExchangeCodec.this.sink.writeUtf8("\r\n");
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007H\u0016J\b\u0010\u0010\u001a\u00020\u000bH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$ChunkedSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "url", "Lokhttp3/HttpUrl;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;Lokhttp3/HttpUrl;)V", "bytesRemainingInChunk", "", "hasMoreChunks", "", "close", "", "read", "sink", "Lokio/Buffer;", "byteCount", "readChunkSize", "okhttp"}, k=1, mv={1, 1, 16})
    private final class ChunkedSource
    extends AbstractSource {
        private long bytesRemainingInChunk;
        private boolean hasMoreChunks;
        private final HttpUrl url;

        public ChunkedSource(HttpUrl httpUrl) {
            Intrinsics.checkParameterIsNotNull(httpUrl, "url");
            this.url = httpUrl;
            this.bytesRemainingInChunk = -1L;
            this.hasMoreChunks = true;
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        private final void readChunkSize() {
            block6 : {
                if (this.bytesRemainingInChunk != -1L) {
                    Http1ExchangeCodec.access$getSource$p(Http1ExchangeCodec.this).readUtf8LineStrict();
                }
                try {
                    this.bytesRemainingInChunk = Http1ExchangeCodec.access$getSource$p(Http1ExchangeCodec.this).readHexadecimalUnsignedLong();
                    var1_1 = Http1ExchangeCodec.access$getSource$p(Http1ExchangeCodec.this).readUtf8LineStrict();
                    if (var1_1 == null) ** GOTO lbl42
                    var1_1 = StringsKt.trim((CharSequence)var1_1).toString();
                    if (this.bytesRemainingInChunk < 0L || (var2_3 = ((CharSequence)var1_1).length() > 0) && !(var3_4 = StringsKt.startsWith$default((String)var1_1, ";", false, 2, null))) break block6;
                    if (this.bytesRemainingInChunk != 0L) return;
                }
                catch (NumberFormatException var1_2) {
                    throw (Throwable)new ProtocolException(var1_2.getMessage());
                }
                this.hasMoreChunks = false;
                var1_1 = Http1ExchangeCodec.this;
                Http1ExchangeCodec.access$setTrailers$p((Http1ExchangeCodec)var1_1, Http1ExchangeCodec.access$getHeadersReader$p((Http1ExchangeCodec)var1_1).readHeaders());
                var1_1 = Http1ExchangeCodec.access$getClient$p(Http1ExchangeCodec.this);
                if (var1_1 == null) {
                    Intrinsics.throwNpe();
                }
                var4_5 = var1_1.cookieJar();
                var5_7 = this.url;
                var1_1 = Http1ExchangeCodec.access$getTrailers$p(Http1ExchangeCodec.this);
                if (var1_1 == null) {
                    Intrinsics.throwNpe();
                }
                HttpHeaders.receiveHeaders(var4_5, var5_7, (Headers)var1_1);
                this.responseBodyComplete$okhttp();
                return;
            }
            var5_8 = new StringBuilder();
            var5_8.append("expected chunk size and optional extensions");
            var5_8.append(" but was \"");
            var5_8.append(this.bytesRemainingInChunk);
            var5_8.append((String)var1_1);
            var5_8.append('\"');
            var4_6 = new ProtocolException(var5_8.toString());
            throw (Throwable)var4_6;
lbl42: // 1 sources:
            var1_1 = new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            throw var1_1;
        }

        @Override
        public void close() {
            if (this.getClosed()) {
                return;
            }
            if (this.hasMoreChunks && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
                this.responseBodyComplete$okhttp();
            }
            this.setClosed(true);
        }

        @Override
        public long read(Buffer object, long l) {
            Intrinsics.checkParameterIsNotNull(object, "sink");
            boolean bl = l >= 0L;
            if (!bl) {
                object = new StringBuilder();
                ((StringBuilder)object).append("byteCount < 0: ");
                ((StringBuilder)object).append(l);
                throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
            }
            if (!(true ^ this.getClosed())) throw (Throwable)new IllegalStateException("closed".toString());
            if (!this.hasMoreChunks) {
                return -1L;
            }
            long l2 = this.bytesRemainingInChunk;
            if (l2 == 0L || l2 == -1L) {
                this.readChunkSize();
                if (!this.hasMoreChunks) {
                    return -1L;
                }
            }
            if ((l = super.read((Buffer)object, Math.min(l, this.bytesRemainingInChunk))) != -1L) {
                this.bytesRemainingInChunk -= l;
                return l;
            }
            Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
            object = new ProtocolException("unexpected end of stream");
            this.responseBodyComplete$okhttp();
            throw (Throwable)object;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$Companion;", "", "()V", "NO_CHUNK_YET", "", "STATE_CLOSED", "", "STATE_IDLE", "STATE_OPEN_REQUEST_BODY", "STATE_OPEN_RESPONSE_BODY", "STATE_READING_RESPONSE_BODY", "STATE_READ_RESPONSE_HEADERS", "STATE_WRITING_REQUEST_BODY", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$FixedLengthSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "bytesRemaining", "", "(Lokhttp3/internal/http1/Http1ExchangeCodec;J)V", "close", "", "read", "sink", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 1, 16})
    private final class FixedLengthSource
    extends AbstractSource {
        private long bytesRemaining;

        public FixedLengthSource(long l) {
            this.bytesRemaining = l;
            if (l != 0L) return;
            this.responseBodyComplete$okhttp();
        }

        @Override
        public void close() {
            if (this.getClosed()) {
                return;
            }
            if (this.bytesRemaining != 0L && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
                this.responseBodyComplete$okhttp();
            }
            this.setClosed(true);
        }

        @Override
        public long read(Buffer object, long l) {
            Intrinsics.checkParameterIsNotNull(object, "sink");
            boolean bl = l >= 0L;
            if (!bl) {
                object = new StringBuilder();
                ((StringBuilder)object).append("byteCount < 0: ");
                ((StringBuilder)object).append(l);
                throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
            }
            if (!(true ^ this.getClosed())) throw (Throwable)new IllegalStateException("closed".toString());
            long l2 = this.bytesRemaining;
            if (l2 == 0L) {
                return -1L;
            }
            if ((l = super.read((Buffer)object, Math.min(l2, l))) != -1L) {
                this.bytesRemaining = l2 = this.bytesRemaining - l;
                if (l2 != 0L) return l;
                this.responseBodyComplete$okhttp();
                return l;
            }
            Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
            object = new ProtocolException("unexpected end of stream");
            this.responseBodyComplete$okhttp();
            throw (Throwable)object;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\b\u0010\u0005\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$KnownLengthSink;", "Lokio/Sink;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "closed", "", "timeout", "Lokio/ForwardingTimeout;", "close", "", "flush", "Lokio/Timeout;", "write", "source", "Lokio/Buffer;", "byteCount", "", "okhttp"}, k=1, mv={1, 1, 16})
    private final class KnownLengthSink
    implements Sink {
        private boolean closed;
        private final ForwardingTimeout timeout;

        public KnownLengthSink() {
            this.timeout = new ForwardingTimeout(Http1ExchangeCodec.this.sink.timeout());
        }

        @Override
        public void close() {
            if (this.closed) {
                return;
            }
            this.closed = true;
            Http1ExchangeCodec.this.detachTimeout(this.timeout);
            Http1ExchangeCodec.access$setState$p(Http1ExchangeCodec.this, 3);
        }

        @Override
        public void flush() {
            if (this.closed) {
                return;
            }
            Http1ExchangeCodec.this.sink.flush();
        }

        @Override
        public Timeout timeout() {
            return this.timeout;
        }

        @Override
        public void write(Buffer buffer, long l) {
            Intrinsics.checkParameterIsNotNull(buffer, "source");
            if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
            Util.checkOffsetAndCount(buffer.size(), 0L, l);
            Http1ExchangeCodec.this.sink.write(buffer, l);
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$UnknownLengthSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "inputExhausted", "", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 1, 16})
    private final class UnknownLengthSource
    extends AbstractSource {
        private boolean inputExhausted;

        @Override
        public void close() {
            if (this.getClosed()) {
                return;
            }
            if (!this.inputExhausted) {
                this.responseBodyComplete$okhttp();
            }
            this.setClosed(true);
        }

        @Override
        public long read(Buffer object, long l) {
            Intrinsics.checkParameterIsNotNull(object, "sink");
            boolean bl = l >= 0L;
            if (!bl) {
                object = new StringBuilder();
                ((StringBuilder)object).append("byteCount < 0: ");
                ((StringBuilder)object).append(l);
                throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
            }
            if (!(this.getClosed() ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
            if (this.inputExhausted) {
                return -1L;
            }
            if ((l = super.read((Buffer)object, l)) != -1L) return l;
            this.inputExhausted = true;
            this.responseBodyComplete$okhttp();
            return -1L;
        }
    }

}


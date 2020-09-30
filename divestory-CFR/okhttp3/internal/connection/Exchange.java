/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.connection;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.internal.connection.ExchangeFinder;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.ws.RealWebSocket;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0002ABB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ7\u0010\u001c\u001a\u0002H\u001d\"\n\b\u0000\u0010\u001d*\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u0002H\u001d\u00a2\u0006\u0002\u0010$J\u0006\u0010%\u001a\u00020&J\u0016\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0016J\u0006\u0010,\u001a\u00020&J\u0006\u0010-\u001a\u00020&J\u0006\u0010.\u001a\u00020&J\u0006\u0010/\u001a\u000200J\u0006\u00101\u001a\u00020&J\u0006\u00102\u001a\u00020&J\u000e\u00103\u001a\u0002042\u0006\u00105\u001a\u000206J\u0010\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020\u0016J\u000e\u0010:\u001a\u00020&2\u0006\u00105\u001a\u000206J\u0006\u0010;\u001a\u00020&J\u0010\u0010<\u001a\u00020&2\u0006\u0010#\u001a\u00020\u001eH\u0002J\u0006\u0010=\u001a\u00020>J\u0006\u0010?\u001a\u00020&J\u000e\u0010@\u001a\u00020&2\u0006\u0010)\u001a\u00020*R\u0014\u0010\u0002\u001a\u00020\u0003X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000eX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0004\u001a\u00020\u0005X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0006\u001a\u00020\u0007X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168@X\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0016@BX\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018\u00a8\u0006C"}, d2={"Lokhttp3/internal/connection/Exchange;", "", "call", "Lokhttp3/internal/connection/RealCall;", "eventListener", "Lokhttp3/EventListener;", "finder", "Lokhttp3/internal/connection/ExchangeFinder;", "codec", "Lokhttp3/internal/http/ExchangeCodec;", "(Lokhttp3/internal/connection/RealCall;Lokhttp3/EventListener;Lokhttp3/internal/connection/ExchangeFinder;Lokhttp3/internal/http/ExchangeCodec;)V", "getCall$okhttp", "()Lokhttp3/internal/connection/RealCall;", "connection", "Lokhttp3/internal/connection/RealConnection;", "getConnection$okhttp", "()Lokhttp3/internal/connection/RealConnection;", "getEventListener$okhttp", "()Lokhttp3/EventListener;", "getFinder$okhttp", "()Lokhttp3/internal/connection/ExchangeFinder;", "isCoalescedConnection", "", "isCoalescedConnection$okhttp", "()Z", "<set-?>", "isDuplex", "isDuplex$okhttp", "bodyComplete", "E", "Ljava/io/IOException;", "bytesRead", "", "responseDone", "requestDone", "e", "(JZZLjava/io/IOException;)Ljava/io/IOException;", "cancel", "", "createRequestBody", "Lokio/Sink;", "request", "Lokhttp3/Request;", "duplex", "detachWithViolence", "finishRequest", "flushRequest", "newWebSocketStreams", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "noNewExchangesOnConnection", "noRequestBody", "openResponseBody", "Lokhttp3/ResponseBody;", "response", "Lokhttp3/Response;", "readResponseHeaders", "Lokhttp3/Response$Builder;", "expectContinue", "responseHeadersEnd", "responseHeadersStart", "trackFailure", "trailers", "Lokhttp3/Headers;", "webSocketUpgradeFailed", "writeRequestHeaders", "RequestBodySink", "ResponseBodySource", "okhttp"}, k=1, mv={1, 1, 16})
public final class Exchange {
    private final RealCall call;
    private final ExchangeCodec codec;
    private final RealConnection connection;
    private final EventListener eventListener;
    private final ExchangeFinder finder;
    private boolean isDuplex;

    public Exchange(RealCall realCall, EventListener eventListener, ExchangeFinder exchangeFinder, ExchangeCodec exchangeCodec) {
        Intrinsics.checkParameterIsNotNull(realCall, "call");
        Intrinsics.checkParameterIsNotNull(eventListener, "eventListener");
        Intrinsics.checkParameterIsNotNull(exchangeFinder, "finder");
        Intrinsics.checkParameterIsNotNull(exchangeCodec, "codec");
        this.call = realCall;
        this.eventListener = eventListener;
        this.finder = exchangeFinder;
        this.codec = exchangeCodec;
        this.connection = exchangeCodec.getConnection();
    }

    private final void trackFailure(IOException iOException) {
        this.finder.trackFailure(iOException);
        this.codec.getConnection().trackFailure$okhttp(this.call, iOException);
    }

    public final <E extends IOException> E bodyComplete(long l, boolean bl, boolean bl2, E e) {
        if (e != null) {
            this.trackFailure(e);
        }
        if (bl2) {
            if (e != null) {
                this.eventListener.requestFailed(this.call, e);
            } else {
                this.eventListener.requestBodyEnd(this.call, l);
            }
        }
        if (!bl) return this.call.messageDone$okhttp(this, bl2, bl, e);
        if (e != null) {
            this.eventListener.responseFailed(this.call, e);
            return this.call.messageDone$okhttp(this, bl2, bl, e);
        }
        this.eventListener.responseBodyEnd(this.call, l);
        return this.call.messageDone$okhttp(this, bl2, bl, e);
    }

    public final void cancel() {
        this.codec.cancel();
    }

    public final Sink createRequestBody(Request request, boolean bl) throws IOException {
        Intrinsics.checkParameterIsNotNull(request, "request");
        this.isDuplex = bl;
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            Intrinsics.throwNpe();
        }
        long l = requestBody.contentLength();
        this.eventListener.requestBodyStart(this.call);
        return new RequestBodySink(this.codec.createRequestBody(request, l), l);
    }

    public final void detachWithViolence() {
        this.codec.cancel();
        this.call.messageDone$okhttp(this, true, true, null);
    }

    public final void finishRequest() throws IOException {
        try {
            this.codec.finishRequest();
            return;
        }
        catch (IOException iOException) {
            this.eventListener.requestFailed(this.call, iOException);
            this.trackFailure(iOException);
            throw (Throwable)iOException;
        }
    }

    public final void flushRequest() throws IOException {
        try {
            this.codec.flushRequest();
            return;
        }
        catch (IOException iOException) {
            this.eventListener.requestFailed(this.call, iOException);
            this.trackFailure(iOException);
            throw (Throwable)iOException;
        }
    }

    public final RealCall getCall$okhttp() {
        return this.call;
    }

    public final RealConnection getConnection$okhttp() {
        return this.connection;
    }

    public final EventListener getEventListener$okhttp() {
        return this.eventListener;
    }

    public final ExchangeFinder getFinder$okhttp() {
        return this.finder;
    }

    public final boolean isCoalescedConnection$okhttp() {
        return Intrinsics.areEqual(this.finder.getAddress$okhttp().url().host(), this.connection.route().address().url().host()) ^ true;
    }

    public final boolean isDuplex$okhttp() {
        return this.isDuplex;
    }

    public final RealWebSocket.Streams newWebSocketStreams() throws SocketException {
        this.call.timeoutEarlyExit();
        return this.codec.getConnection().newWebSocketStreams$okhttp(this);
    }

    public final void noNewExchangesOnConnection() {
        this.codec.getConnection().noNewExchanges$okhttp();
    }

    public final void noRequestBody() {
        this.call.messageDone$okhttp(this, true, false, null);
    }

    public final ResponseBody openResponseBody(Response closeable) throws IOException {
        Intrinsics.checkParameterIsNotNull(closeable, "response");
        try {
            String string2 = Response.header$default(closeable, "Content-Type", null, 2, null);
            long l = this.codec.reportedContentLength((Response)closeable);
            Closeable closeable2 = this.codec.openResponseBodySource((Response)closeable);
            closeable = new ResponseBodySource((Source)closeable2, l);
            closeable2 = new RealResponseBody(string2, l, Okio.buffer((Source)closeable));
            return (ResponseBody)closeable2;
        }
        catch (IOException iOException) {
            this.eventListener.responseFailed(this.call, iOException);
            this.trackFailure(iOException);
            throw (Throwable)iOException;
        }
    }

    public final Response.Builder readResponseHeaders(boolean bl) throws IOException {
        try {
            Response.Builder builder = this.codec.readResponseHeaders(bl);
            if (builder == null) return builder;
            builder.initExchange$okhttp(this);
            return builder;
        }
        catch (IOException iOException) {
            this.eventListener.responseFailed(this.call, iOException);
            this.trackFailure(iOException);
            throw (Throwable)iOException;
        }
    }

    public final void responseHeadersEnd(Response response) {
        Intrinsics.checkParameterIsNotNull(response, "response");
        this.eventListener.responseHeadersEnd(this.call, response);
    }

    public final void responseHeadersStart() {
        this.eventListener.responseHeadersStart(this.call);
    }

    public final Headers trailers() throws IOException {
        return this.codec.trailers();
    }

    public final void webSocketUpgradeFailed() {
        this.bodyComplete(-1L, true, true, null);
    }

    public final void writeRequestHeaders(Request request) throws IOException {
        Intrinsics.checkParameterIsNotNull(request, "request");
        try {
            this.eventListener.requestHeadersStart(this.call);
            this.codec.writeRequestHeaders(request);
            this.eventListener.requestHeadersEnd(this.call, request);
            return;
        }
        catch (IOException iOException) {
            this.eventListener.requestFailed(this.call, iOException);
            this.trackFailure(iOException);
            throw (Throwable)iOException;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J!\u0010\r\u001a\u0002H\u000e\"\n\b\u0000\u0010\u000e*\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u0002H\u000eH\u0002\u00a2\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\fH\u0016J\u0018\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0005H\u0016R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lokhttp3/internal/connection/Exchange$RequestBodySink;", "Lokio/ForwardingSink;", "delegate", "Lokio/Sink;", "contentLength", "", "(Lokhttp3/internal/connection/Exchange;Lokio/Sink;J)V", "bytesReceived", "closed", "", "completed", "close", "", "complete", "E", "Ljava/io/IOException;", "e", "(Ljava/io/IOException;)Ljava/io/IOException;", "flush", "write", "source", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 1, 16})
    private final class RequestBodySink
    extends ForwardingSink {
        private long bytesReceived;
        private boolean closed;
        private boolean completed;
        private final long contentLength;

        public RequestBodySink(Sink sink2, long l) {
            Intrinsics.checkParameterIsNotNull(sink2, "delegate");
            super(sink2);
            this.contentLength = l;
        }

        private final <E extends IOException> E complete(E e) {
            if (this.completed) {
                return e;
            }
            this.completed = true;
            return Exchange.this.bodyComplete(this.bytesReceived, false, true, e);
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            long l = this.contentLength;
            if (l != -1L) {
                if (this.bytesReceived != l) throw (Throwable)new ProtocolException("unexpected end of stream");
            }
            try {
                super.close();
                this.complete(null);
                return;
            }
            catch (IOException iOException) {
                throw (Throwable)this.complete(iOException);
            }
        }

        @Override
        public void flush() throws IOException {
            try {
                super.flush();
                return;
            }
            catch (IOException iOException) {
                throw (Throwable)this.complete(iOException);
            }
        }

        @Override
        public void write(Buffer object, long l) throws IOException {
            Intrinsics.checkParameterIsNotNull(object, "source");
            if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
            long l2 = this.contentLength;
            if (l2 != -1L && this.bytesReceived + l > l2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("expected ");
                ((StringBuilder)object).append(this.contentLength);
                ((StringBuilder)object).append(" bytes but received ");
                ((StringBuilder)object).append(this.bytesReceived + l);
                throw (Throwable)new ProtocolException(((StringBuilder)object).toString());
            }
            try {
                super.write((Buffer)object, l);
                this.bytesReceived += l;
                return;
            }
            catch (IOException iOException) {
                throw (Throwable)this.complete(iOException);
            }
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\f\u001a\u00020\rH\u0016J\u001f\u0010\u000e\u001a\u0002H\u000f\"\n\b\u0000\u0010\u000f*\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u0002H\u000f\u00a2\u0006\u0002\u0010\u0012J\u0018\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0005H\u0016R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lokhttp3/internal/connection/Exchange$ResponseBodySource;", "Lokio/ForwardingSource;", "delegate", "Lokio/Source;", "contentLength", "", "(Lokhttp3/internal/connection/Exchange;Lokio/Source;J)V", "bytesReceived", "closed", "", "completed", "invokeStartEvent", "close", "", "complete", "E", "Ljava/io/IOException;", "e", "(Ljava/io/IOException;)Ljava/io/IOException;", "read", "sink", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 1, 16})
    public final class ResponseBodySource
    extends ForwardingSource {
        private long bytesReceived;
        private boolean closed;
        private boolean completed;
        private final long contentLength;
        private boolean invokeStartEvent;

        public ResponseBodySource(Source source2, long l) {
            Intrinsics.checkParameterIsNotNull(source2, "delegate");
            super(source2);
            this.contentLength = l;
            this.invokeStartEvent = true;
            if (l != 0L) return;
            this.complete(null);
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            try {
                super.close();
                this.complete(null);
                return;
            }
            catch (IOException iOException) {
                throw (Throwable)this.complete(iOException);
            }
        }

        public final <E extends IOException> E complete(E e) {
            if (this.completed) {
                return e;
            }
            this.completed = true;
            if (e != null) return Exchange.this.bodyComplete(this.bytesReceived, true, false, e);
            if (!this.invokeStartEvent) return Exchange.this.bodyComplete(this.bytesReceived, true, false, e);
            this.invokeStartEvent = false;
            Exchange.this.getEventListener$okhttp().responseBodyStart(Exchange.this.getCall$okhttp());
            return Exchange.this.bodyComplete(this.bytesReceived, true, false, e);
        }

        @Override
        public long read(Buffer object, long l) throws IOException {
            Intrinsics.checkParameterIsNotNull(object, "sink");
            if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
            try {
                l = this.delegate().read((Buffer)object, l);
                if (this.invokeStartEvent) {
                    this.invokeStartEvent = false;
                    Exchange.this.getEventListener$okhttp().responseBodyStart(Exchange.this.getCall$okhttp());
                }
                if (l == -1L) {
                    this.complete(null);
                    return -1L;
                }
                long l2 = this.bytesReceived + l;
                if (this.contentLength != -1L && l2 > this.contentLength) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("expected ");
                    ((StringBuilder)object).append(this.contentLength);
                    ((StringBuilder)object).append(" bytes but received ");
                    ((StringBuilder)object).append(l2);
                    ProtocolException protocolException = new ProtocolException(((StringBuilder)object).toString());
                    throw (Throwable)protocolException;
                }
                this.bytesReceived = l2;
                if (l2 != this.contentLength) return l;
                this.complete(null);
                return l;
            }
            catch (IOException iOException) {
                throw (Throwable)this.complete(iOException);
            }
        }
    }

}


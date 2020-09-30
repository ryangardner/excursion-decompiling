/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okhttp3.internal.http1.HeadersReader;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001c2\u00020\u0001:\u0003\u001c\u001d\u001eB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018H\u0002J\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bR\u0013\u0010\u0007\u001a\u00020\b8\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0018\u00010\u0010R\u00020\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2={"Lokhttp3/MultipartReader;", "Ljava/io/Closeable;", "response", "Lokhttp3/ResponseBody;", "(Lokhttp3/ResponseBody;)V", "source", "Lokio/BufferedSource;", "boundary", "", "(Lokio/BufferedSource;Ljava/lang/String;)V", "()Ljava/lang/String;", "closed", "", "crlfDashDashBoundary", "Lokio/ByteString;", "currentPart", "Lokhttp3/MultipartReader$PartSource;", "dashDashBoundary", "noMoreParts", "partCount", "", "close", "", "currentPartBytesRemaining", "", "maxResult", "nextPart", "Lokhttp3/MultipartReader$Part;", "Companion", "Part", "PartSource", "okhttp"}, k=1, mv={1, 1, 16})
public final class MultipartReader
implements Closeable {
    public static final Companion Companion = new Companion(null);
    private static final Options afterBoundaryOptions = Options.Companion.of(ByteString.Companion.encodeUtf8("\r\n"), ByteString.Companion.encodeUtf8("--"), ByteString.Companion.encodeUtf8(" "), ByteString.Companion.encodeUtf8("\t"));
    private final String boundary;
    private boolean closed;
    private final ByteString crlfDashDashBoundary;
    private PartSource currentPart;
    private final ByteString dashDashBoundary;
    private boolean noMoreParts;
    private int partCount;
    private final BufferedSource source;

    public MultipartReader(ResponseBody object) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "response");
        BufferedSource bufferedSource = ((ResponseBody)object).source();
        object = ((ResponseBody)object).contentType();
        if (object == null) throw (Throwable)new ProtocolException("expected the Content-Type to have a boundary parameter");
        if ((object = ((MediaType)object).parameter("boundary")) == null) throw (Throwable)new ProtocolException("expected the Content-Type to have a boundary parameter");
        this(bufferedSource, (String)object);
    }

    public MultipartReader(BufferedSource bufferedSource, String string2) throws IOException {
        Intrinsics.checkParameterIsNotNull(bufferedSource, "source");
        Intrinsics.checkParameterIsNotNull(string2, "boundary");
        this.source = bufferedSource;
        this.boundary = string2;
        this.dashDashBoundary = new Buffer().writeUtf8("--").writeUtf8(this.boundary).readByteString();
        this.crlfDashDashBoundary = new Buffer().writeUtf8("\r\n--").writeUtf8(this.boundary).readByteString();
    }

    public static final /* synthetic */ void access$setCurrentPart$p(MultipartReader multipartReader, PartSource partSource) {
        multipartReader.currentPart = partSource;
    }

    private final long currentPartBytesRemaining(long l) {
        this.source.require(this.crlfDashDashBoundary.size());
        long l2 = this.source.getBuffer().indexOf(this.crlfDashDashBoundary);
        if (l2 != -1L) return Math.min(l, l2);
        return Math.min(l, this.source.getBuffer().size() - (long)this.crlfDashDashBoundary.size() + 1L);
    }

    public final String boundary() {
        return this.boundary;
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.currentPart = null;
        this.source.close();
    }

    public final Part nextPart() throws IOException {
        int n;
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        if (this.noMoreParts) {
            return null;
        }
        if (this.partCount == 0 && this.source.rangeEquals(0L, this.dashDashBoundary)) {
            this.source.skip(this.dashDashBoundary.size());
        } else {
            do {
                long l;
                if ((l = this.currentPartBytesRemaining(8192L)) == 0L) {
                    this.source.skip(this.crlfDashDashBoundary.size());
                    break;
                }
                this.source.skip(l);
            } while (true);
        }
        boolean bl = false;
        while ((n = this.source.select(afterBoundaryOptions)) != -1) {
            if (n == 0) {
                PartSource partSource;
                ++this.partCount;
                Headers headers = new HeadersReader(this.source).readHeaders();
                this.currentPart = partSource = new PartSource();
                return new Part(headers, Okio.buffer(partSource));
            }
            if (n == 1) {
                if (bl) throw (Throwable)new ProtocolException("unexpected characters after boundary");
                if (this.partCount == 0) throw (Throwable)new ProtocolException("expected at least 1 part");
                this.noMoreParts = true;
                return null;
            }
            if (n != 2 && n != 3) continue;
            bl = true;
        }
        throw (Throwable)new ProtocolException("unexpected characters after boundary");
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lokhttp3/MultipartReader$Companion;", "", "()V", "afterBoundaryOptions", "Lokio/Options;", "getAfterBoundaryOptions", "()Lokio/Options;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Options getAfterBoundaryOptions() {
            return afterBoundaryOptions;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\t\u001a\u00020\nH\u0096\u0001R\u0013\u0010\u0004\u001a\u00020\u00058\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0013\u0010\u0002\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\b\u00a8\u0006\u000b"}, d2={"Lokhttp3/MultipartReader$Part;", "Ljava/io/Closeable;", "headers", "Lokhttp3/Headers;", "body", "Lokio/BufferedSource;", "(Lokhttp3/Headers;Lokio/BufferedSource;)V", "()Lokio/BufferedSource;", "()Lokhttp3/Headers;", "close", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Part
    implements Closeable {
        private final BufferedSource body;
        private final Headers headers;

        public Part(Headers headers, BufferedSource bufferedSource) {
            Intrinsics.checkParameterIsNotNull(headers, "headers");
            Intrinsics.checkParameterIsNotNull(bufferedSource, "body");
            this.headers = headers;
            this.body = bufferedSource;
        }

        public final BufferedSource body() {
            return this.body;
        }

        @Override
        public void close() {
            this.body.close();
        }

        public final Headers headers() {
            return this.headers;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0016J\b\u0010\u0003\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lokhttp3/MultipartReader$PartSource;", "Lokio/Source;", "(Lokhttp3/MultipartReader;)V", "timeout", "Lokio/Timeout;", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 1, 16})
    private final class PartSource
    implements Source {
        private final Timeout timeout = new Timeout();

        @Override
        public void close() {
            if (!Intrinsics.areEqual(MultipartReader.this.currentPart, this)) return;
            MultipartReader.access$setCurrentPart$p(MultipartReader.this, null);
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
            if (!Intrinsics.areEqual(MultipartReader.this.currentPart, this)) throw (Throwable)new IllegalStateException("closed".toString());
            Timeout timeout2 = MultipartReader.this.source.timeout();
            Timeout timeout3 = this.timeout;
            long l2 = timeout2.timeoutNanos();
            timeout2.timeout(Timeout.Companion.minTimeout(timeout3.timeoutNanos(), timeout2.timeoutNanos()), TimeUnit.NANOSECONDS);
            if (timeout2.hasDeadline()) {
                long l3 = timeout2.deadlineNanoTime();
                if (timeout3.hasDeadline()) {
                    timeout2.deadlineNanoTime(Math.min(timeout2.deadlineNanoTime(), timeout3.deadlineNanoTime()));
                }
                try {
                    l = MultipartReader.this.currentPartBytesRemaining(l);
                    if (l == 0L) {
                        l = -1L;
                        return l;
                    }
                    l = MultipartReader.this.source.read((Buffer)object, l);
                    return l;
                }
                finally {
                    timeout2.timeout(l2, TimeUnit.NANOSECONDS);
                    if (timeout3.hasDeadline()) {
                        timeout2.deadlineNanoTime(l3);
                    }
                }
            }
            if (timeout3.hasDeadline()) {
                timeout2.deadlineNanoTime(timeout3.deadlineNanoTime());
            }
            try {
                l = MultipartReader.this.currentPartBytesRemaining(l);
                if (l == 0L) {
                    l = -1L;
                    return l;
                }
                l = MultipartReader.this.source.read((Buffer)object, l);
                return l;
            }
            finally {
                timeout2.timeout(l2, TimeUnit.NANOSECONDS);
                if (timeout3.hasDeadline()) {
                    timeout2.clearDeadline();
                }
            }
        }

        @Override
        public Timeout timeout() {
            return this.timeout;
        }
    }

}


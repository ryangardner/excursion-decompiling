/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import okio.Segment;
import okio.SegmentPool;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0006\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0007J\b\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u0016\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fJ\u0006\u0010\u0014\u001a\u00020\u000bJ\b\u0010\u0015\u001a\u00020\rH\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lokio/InflaterSource;", "Lokio/Source;", "source", "inflater", "Ljava/util/zip/Inflater;", "(Lokio/Source;Ljava/util/zip/Inflater;)V", "Lokio/BufferedSource;", "(Lokio/BufferedSource;Ljava/util/zip/Inflater;)V", "bufferBytesHeldByInflater", "", "closed", "", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "readOrInflate", "refill", "releaseBytesAfterInflate", "timeout", "Lokio/Timeout;", "okio"}, k=1, mv={1, 1, 16})
public final class InflaterSource
implements Source {
    private int bufferBytesHeldByInflater;
    private boolean closed;
    private final Inflater inflater;
    private final BufferedSource source;

    public InflaterSource(BufferedSource bufferedSource, Inflater inflater) {
        Intrinsics.checkParameterIsNotNull(bufferedSource, "source");
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        this.source = bufferedSource;
        this.inflater = inflater;
    }

    public InflaterSource(Source source2, Inflater inflater) {
        Intrinsics.checkParameterIsNotNull(source2, "source");
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        this(Okio.buffer(source2), inflater);
    }

    private final void releaseBytesAfterInflate() {
        int n = this.bufferBytesHeldByInflater;
        if (n == 0) {
            return;
        }
        this.bufferBytesHeldByInflater -= (n -= this.inflater.getRemaining());
        this.source.skip(n);
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.inflater.end();
        this.closed = true;
        this.source.close();
    }

    @Override
    public long read(Buffer buffer, long l) throws IOException {
        Intrinsics.checkParameterIsNotNull(buffer, "sink");
        long l2;
        while ((l2 = this.readOrInflate(buffer, l)) <= 0L) {
            if (this.inflater.finished()) return -1L;
            if (this.inflater.needsDictionary()) {
                return -1L;
            }
            if (this.source.exhausted()) throw (Throwable)new EOFException("source exhausted prematurely");
        }
        return l2;
    }

    public final long readOrInflate(Buffer object, long l) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "sink");
        long l2 = l LCMP 0L;
        int n = l2 >= 0 ? 1 : 0;
        if (n == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount < 0: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        if (l2 == false) {
            return 0L;
        }
        try {
            Segment segment = ((Buffer)object).writableSegment$okio(1);
            n = (int)Math.min(l, (long)(8192 - segment.limit));
            this.refill();
            n = this.inflater.inflate(segment.data, segment.limit, n);
            this.releaseBytesAfterInflate();
            if (n > 0) {
                segment.limit += n;
                long l3 = ((Buffer)object).size();
                l = n;
                ((Buffer)object).setSize$okio(l3 + l);
                return l;
            }
            if (segment.pos != segment.limit) return 0L;
            ((Buffer)object).head = segment.pop();
            SegmentPool.recycle(segment);
            return 0L;
        }
        catch (DataFormatException dataFormatException) {
            throw (Throwable)new IOException(dataFormatException);
        }
    }

    public final boolean refill() throws IOException {
        if (!this.inflater.needsInput()) {
            return false;
        }
        if (this.source.exhausted()) {
            return true;
        }
        Segment segment = this.source.getBuffer().head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        this.bufferBytesHeldByInflater = segment.limit - segment.pos;
        this.inflater.setInput(segment.data, segment.pos, this.bufferBytesHeldByInflater);
        return false;
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }
}


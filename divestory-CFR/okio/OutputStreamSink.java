/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.Segment;
import okio.SegmentPool;
import okio.Sink;
import okio.Timeout;
import okio._Util;

@Metadata(bv={1, 0, 3}, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lokio/OutputStreamSink;", "Lokio/Sink;", "out", "Ljava/io/OutputStream;", "timeout", "Lokio/Timeout;", "(Ljava/io/OutputStream;Lokio/Timeout;)V", "close", "", "flush", "toString", "", "write", "source", "Lokio/Buffer;", "byteCount", "", "okio"}, k=1, mv={1, 1, 16})
final class OutputStreamSink
implements Sink {
    private final OutputStream out;
    private final Timeout timeout;

    public OutputStreamSink(OutputStream outputStream2, Timeout timeout2) {
        Intrinsics.checkParameterIsNotNull(outputStream2, "out");
        Intrinsics.checkParameterIsNotNull(timeout2, "timeout");
        this.out = outputStream2;
        this.timeout = timeout2;
    }

    @Override
    public void close() {
        this.out.close();
    }

    @Override
    public void flush() {
        this.out.flush();
    }

    @Override
    public Timeout timeout() {
        return this.timeout;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("sink(");
        stringBuilder.append(this.out);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    @Override
    public void write(Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "source");
        _Util.checkOffsetAndCount(buffer.size(), 0L, l);
        while (l > 0L) {
            this.timeout.throwIfReached();
            Segment segment = buffer.head;
            if (segment == null) {
                Intrinsics.throwNpe();
            }
            int n = (int)Math.min(l, (long)(segment.limit - segment.pos));
            this.out.write(segment.data, segment.pos, n);
            segment.pos += n;
            long l2 = n;
            long l3 = l - l2;
            buffer.setSize$okio(buffer.size() - l2);
            l = l3;
            if (segment.pos != segment.limit) continue;
            buffer.head = segment.pop();
            SegmentPool.recycle(segment);
            l = l3;
        }
    }
}


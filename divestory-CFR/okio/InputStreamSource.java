/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.Okio;
import okio.Segment;
import okio.SegmentPool;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nH\u0016J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lokio/InputStreamSource;", "Lokio/Source;", "input", "Ljava/io/InputStream;", "timeout", "Lokio/Timeout;", "(Ljava/io/InputStream;Lokio/Timeout;)V", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "toString", "", "okio"}, k=1, mv={1, 1, 16})
final class InputStreamSource
implements Source {
    private final InputStream input;
    private final Timeout timeout;

    public InputStreamSource(InputStream inputStream2, Timeout timeout2) {
        Intrinsics.checkParameterIsNotNull(inputStream2, "input");
        Intrinsics.checkParameterIsNotNull(timeout2, "timeout");
        this.input = inputStream2;
        this.timeout = timeout2;
    }

    @Override
    public void close() {
        this.input.close();
    }

    @Override
    public long read(Buffer object, long l) {
        Intrinsics.checkParameterIsNotNull(object, "sink");
        long l2 = l LCMP 0L;
        if (l2 == false) {
            return 0L;
        }
        if ((l2 = l2 >= 0 ? (long)1 : (long)0) == false) {
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount < 0: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        try {
            this.timeout.throwIfReached();
            Segment segment = ((Buffer)object).writableSegment$okio(1);
            l2 = (int)Math.min(l, (long)(8192 - segment.limit));
            l2 = this.input.read(segment.data, segment.limit, (int)l2);
            if (l2 == -1) {
                if (segment.pos != segment.limit) return -1L;
                ((Buffer)object).head = segment.pop();
                SegmentPool.recycle(segment);
                return -1L;
            }
            segment.limit += l2;
            l = ((Buffer)object).size();
            long l3 = l2;
            ((Buffer)object).setSize$okio(l + l3);
            return l3;
        }
        catch (AssertionError assertionError) {
            if (!Okio.isAndroidGetsocknameError(assertionError)) throw (Throwable)((Object)assertionError);
            throw (Throwable)new IOException((Throwable)((Object)assertionError));
        }
    }

    @Override
    public Timeout timeout() {
        return this.timeout;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("source(");
        stringBuilder.append(this.input);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio.internal;

import java.io.EOFException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.RealBufferedSink;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\b\u001a\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0002H\b\u001a\r\u0010\u0005\u001a\u00020\u0004*\u00020\u0002H\b\u001a\r\u0010\u0006\u001a\u00020\u0001*\u00020\u0002H\b\u001a\r\u0010\u0007\u001a\u00020\b*\u00020\u0002H\b\u001a\r\u0010\t\u001a\u00020\n*\u00020\u0002H\b\u001a\u0015\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\b\u001a%\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\b\u001a\u001d\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\f\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u0012H\b\u001a\u0015\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\b\u001a%\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\b\u001a\u001d\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\f\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u0012H\b\u001a\u0015\u0010\u0016\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\f\u001a\u00020\u0015H\b\u001a\u0015\u0010\u0017\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u000fH\b\u001a\u0015\u0010\u0019\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\b\u001a\u0015\u0010\u001b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\b\u001a\u0015\u0010\u001c\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u000fH\b\u001a\u0015\u0010\u001e\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u000fH\b\u001a\u0015\u0010\u001f\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\b\u001a\u0015\u0010 \u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\b\u001a\u0015\u0010!\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\"\u001a\u00020\u000fH\b\u001a\u0015\u0010#\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\"\u001a\u00020\u000fH\b\u001a\u0015\u0010$\u001a\u00020\u0004*\u00020\u00022\u0006\u0010%\u001a\u00020\nH\b\u001a%\u0010$\u001a\u00020\u0004*\u00020\u00022\u0006\u0010%\u001a\u00020\n2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000fH\b\u001a\u0015\u0010(\u001a\u00020\u0004*\u00020\u00022\u0006\u0010)\u001a\u00020\u000fH\b\u00a8\u0006*"}, d2={"commonClose", "", "Lokio/RealBufferedSink;", "commonEmit", "Lokio/BufferedSink;", "commonEmitCompleteSegments", "commonFlush", "commonTimeout", "Lokio/Timeout;", "commonToString", "", "commonWrite", "source", "", "offset", "", "byteCount", "Lokio/Buffer;", "", "byteString", "Lokio/ByteString;", "Lokio/Source;", "commonWriteAll", "commonWriteByte", "b", "commonWriteDecimalLong", "v", "commonWriteHexadecimalUnsignedLong", "commonWriteInt", "i", "commonWriteIntLe", "commonWriteLong", "commonWriteLongLe", "commonWriteShort", "s", "commonWriteShortLe", "commonWriteUtf8", "string", "beginIndex", "endIndex", "commonWriteUtf8CodePoint", "codePoint", "okio"}, k=2, mv={1, 1, 16})
public final class RealBufferedSinkKt {
    public static final void commonClose(RealBufferedSink realBufferedSink) {
        Throwable throwable;
        block6 : {
            Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonClose");
            if (realBufferedSink.closed) {
                return;
            }
            Throwable throwable2 = throwable = (Throwable)null;
            try {
                if (realBufferedSink.bufferField.size() > 0L) {
                    realBufferedSink.sink.write(realBufferedSink.bufferField, realBufferedSink.bufferField.size());
                    throwable2 = throwable;
                }
            }
            catch (Throwable throwable3) {
                // empty catch block
            }
            try {
                realBufferedSink.sink.close();
                throwable = throwable2;
            }
            catch (Throwable throwable4) {
                throwable = throwable2;
                if (throwable2 != null) break block6;
                throwable = throwable4;
            }
        }
        realBufferedSink.closed = true;
        if (throwable != null) throw throwable;
    }

    public static final BufferedSink commonEmit(RealBufferedSink realBufferedSink) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonEmit");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        long l = realBufferedSink.bufferField.size();
        if (l <= 0L) return realBufferedSink;
        realBufferedSink.sink.write(realBufferedSink.bufferField, l);
        return realBufferedSink;
    }

    public static final BufferedSink commonEmitCompleteSegments(RealBufferedSink realBufferedSink) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonEmitCompleteSegments");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        long l = realBufferedSink.bufferField.completeSegmentByteCount();
        if (l <= 0L) return realBufferedSink;
        realBufferedSink.sink.write(realBufferedSink.bufferField, l);
        return realBufferedSink;
    }

    public static final void commonFlush(RealBufferedSink realBufferedSink) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonFlush");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        if (realBufferedSink.bufferField.size() > 0L) {
            realBufferedSink.sink.write(realBufferedSink.bufferField, realBufferedSink.bufferField.size());
        }
        realBufferedSink.sink.flush();
    }

    public static final Timeout commonTimeout(RealBufferedSink realBufferedSink) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonTimeout");
        return realBufferedSink.sink.timeout();
    }

    public static final String commonToString(RealBufferedSink realBufferedSink) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonToString");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("buffer(");
        stringBuilder.append(realBufferedSink.sink);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public static final BufferedSink commonWrite(RealBufferedSink realBufferedSink, ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.write(byteString);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWrite(RealBufferedSink realBufferedSink, ByteString byteString, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.write(byteString, n, n2);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWrite(RealBufferedSink realBufferedSink, Source source2, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(source2, "source");
        while (l > 0L) {
            long l2 = source2.read(realBufferedSink.bufferField, l);
            if (l2 == -1L) throw (Throwable)new EOFException();
            l -= l2;
            realBufferedSink.emitCompleteSegments();
        }
        return realBufferedSink;
    }

    public static final BufferedSink commonWrite(RealBufferedSink realBufferedSink, byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(arrby, "source");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.write(arrby);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWrite(RealBufferedSink realBufferedSink, byte[] arrby, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(arrby, "source");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.write(arrby, n, n2);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final void commonWrite(RealBufferedSink realBufferedSink, Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(buffer, "source");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.write(buffer, l);
        realBufferedSink.emitCompleteSegments();
    }

    public static final long commonWriteAll(RealBufferedSink realBufferedSink, Source source2) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteAll");
        Intrinsics.checkParameterIsNotNull(source2, "source");
        long l = 0L;
        long l2;
        while ((l2 = source2.read(realBufferedSink.bufferField, 8192)) != -1L) {
            l += l2;
            realBufferedSink.emitCompleteSegments();
        }
        return l;
    }

    public static final BufferedSink commonWriteByte(RealBufferedSink realBufferedSink, int n) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteByte");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeByte(n);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWriteDecimalLong(RealBufferedSink realBufferedSink, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteDecimalLong");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeDecimalLong(l);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWriteHexadecimalUnsignedLong(RealBufferedSink realBufferedSink, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteHexadecimalUnsignedLong");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeHexadecimalUnsignedLong(l);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWriteInt(RealBufferedSink realBufferedSink, int n) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteInt");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeInt(n);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWriteIntLe(RealBufferedSink realBufferedSink, int n) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteIntLe");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeIntLe(n);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWriteLong(RealBufferedSink realBufferedSink, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteLong");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeLong(l);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWriteLongLe(RealBufferedSink realBufferedSink, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteLongLe");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeLongLe(l);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWriteShort(RealBufferedSink realBufferedSink, int n) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteShort");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeShort(n);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWriteShortLe(RealBufferedSink realBufferedSink, int n) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteShortLe");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeShortLe(n);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWriteUtf8(RealBufferedSink realBufferedSink, String string2) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteUtf8");
        Intrinsics.checkParameterIsNotNull(string2, "string");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeUtf8(string2);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWriteUtf8(RealBufferedSink realBufferedSink, String string2, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteUtf8");
        Intrinsics.checkParameterIsNotNull(string2, "string");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeUtf8(string2, n, n2);
        return realBufferedSink.emitCompleteSegments();
    }

    public static final BufferedSink commonWriteUtf8CodePoint(RealBufferedSink realBufferedSink, int n) {
        Intrinsics.checkParameterIsNotNull(realBufferedSink, "$this$commonWriteUtf8CodePoint");
        if (!(realBufferedSink.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        realBufferedSink.bufferField.writeUtf8CodePoint(n);
        return realBufferedSink.emitCompleteSegments();
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okio.RealBufferedSink$outputStream
 */
package okio;

import java.io.EOFException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.RealBufferedSink;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0001H\u0016J\b\u0010\u0011\u001a\u00020\u0001H\u0016J\b\u0010\u0012\u001a\u00020\u000fH\u0016J\b\u0010\u0013\u001a\u00020\rH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001eH\u0016J \u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u001bH\u0016J\u0018\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020#H\u0016J \u0010\u001a\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u001bH\u0016J\u0018\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020$2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010%\u001a\u00020!2\u0006\u0010\u001c\u001a\u00020$H\u0016J\u0010\u0010&\u001a\u00020\u00012\u0006\u0010'\u001a\u00020\u001bH\u0016J\u0010\u0010(\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u0010*\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u001bH\u0016J\u0010\u0010-\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u001bH\u0016J\u0010\u0010.\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u0010/\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u00100\u001a\u00020\u00012\u0006\u00101\u001a\u00020\u001bH\u0016J\u0010\u00102\u001a\u00020\u00012\u0006\u00101\u001a\u00020\u001bH\u0016J\u0018\u00103\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u00192\u0006\u00105\u001a\u000206H\u0016J(\u00103\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u00192\u0006\u00107\u001a\u00020\u001b2\u0006\u00108\u001a\u00020\u001b2\u0006\u00105\u001a\u000206H\u0016J\u0010\u00109\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u0019H\u0016J \u00109\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u00192\u0006\u00107\u001a\u00020\u001b2\u0006\u00108\u001a\u00020\u001bH\u0016J\u0010\u0010:\u001a\u00020\u00012\u0006\u0010;\u001a\u00020\u001bH\u0016R\u001b\u0010\u0005\u001a\u00020\u00068\u00d6\u0002X\u0096\u0004\u00a2\u0006\f\u0012\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00020\r8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006<"}, d2={"Lokio/RealBufferedSink;", "Lokio/BufferedSink;", "sink", "Lokio/Sink;", "(Lokio/Sink;)V", "buffer", "Lokio/Buffer;", "buffer$annotations", "()V", "getBuffer", "()Lokio/Buffer;", "bufferField", "closed", "", "close", "", "emit", "emitCompleteSegments", "flush", "isOpen", "outputStream", "Ljava/io/OutputStream;", "timeout", "Lokio/Timeout;", "toString", "", "write", "", "source", "Ljava/nio/ByteBuffer;", "", "offset", "byteCount", "", "byteString", "Lokio/ByteString;", "Lokio/Source;", "writeAll", "writeByte", "b", "writeDecimalLong", "v", "writeHexadecimalUnsignedLong", "writeInt", "i", "writeIntLe", "writeLong", "writeLongLe", "writeShort", "s", "writeShortLe", "writeString", "string", "charset", "Ljava/nio/charset/Charset;", "beginIndex", "endIndex", "writeUtf8", "writeUtf8CodePoint", "codePoint", "okio"}, k=1, mv={1, 1, 16})
public final class RealBufferedSink
implements BufferedSink {
    public final Buffer bufferField;
    public boolean closed;
    public final Sink sink;

    public RealBufferedSink(Sink sink2) {
        Intrinsics.checkParameterIsNotNull(sink2, "sink");
        this.sink = sink2;
        this.bufferField = new Buffer();
    }

    public static /* synthetic */ void buffer$annotations() {
    }

    @Override
    public Buffer buffer() {
        return this.bufferField;
    }

    @Override
    public void close() {
        Throwable throwable;
        block6 : {
            if (this.closed) {
                return;
            }
            Throwable throwable2 = throwable = (Throwable)null;
            try {
                if (this.bufferField.size() > 0L) {
                    this.sink.write(this.bufferField, this.bufferField.size());
                    throwable2 = throwable;
                }
            }
            catch (Throwable throwable3) {
                // empty catch block
            }
            try {
                this.sink.close();
                throwable = throwable2;
            }
            catch (Throwable throwable4) {
                throwable = throwable2;
                if (throwable2 != null) break block6;
                throwable = throwable4;
            }
        }
        this.closed = true;
        if (throwable != null) throw throwable;
    }

    @Override
    public BufferedSink emit() {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        long l = this.bufferField.size();
        if (l <= 0L) return this;
        this.sink.write(this.bufferField, l);
        return this;
    }

    @Override
    public BufferedSink emitCompleteSegments() {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        long l = this.bufferField.completeSegmentByteCount();
        if (l <= 0L) return this;
        this.sink.write(this.bufferField, l);
        return this;
    }

    @Override
    public void flush() {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        if (this.bufferField.size() > 0L) {
            Sink sink2 = this.sink;
            Buffer buffer = this.bufferField;
            sink2.write(buffer, buffer.size());
        }
        this.sink.flush();
    }

    @Override
    public Buffer getBuffer() {
        return this.bufferField;
    }

    @Override
    public boolean isOpen() {
        return this.closed ^ true;
    }

    @Override
    public OutputStream outputStream() {
        return new OutputStream(this){
            final /* synthetic */ RealBufferedSink this$0;
            {
                this.this$0 = realBufferedSink;
            }

            public void close() {
                this.this$0.close();
            }

            public void flush() {
                if (this.this$0.closed) return;
                this.this$0.flush();
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.this$0);
                stringBuilder.append(".outputStream()");
                return stringBuilder.toString();
            }

            public void write(int n) {
                if (this.this$0.closed) throw (Throwable)new java.io.IOException("closed");
                this.this$0.bufferField.writeByte((byte)n);
                this.this$0.emitCompleteSegments();
            }

            public void write(byte[] arrby, int n, int n2) {
                Intrinsics.checkParameterIsNotNull(arrby, "data");
                if (this.this$0.closed) throw (Throwable)new java.io.IOException("closed");
                this.this$0.bufferField.write(arrby, n, n2);
                this.this$0.emitCompleteSegments();
            }
        };
    }

    @Override
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("buffer(");
        stringBuilder.append(this.sink);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    @Override
    public int write(ByteBuffer byteBuffer) {
        Intrinsics.checkParameterIsNotNull(byteBuffer, "source");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        int n = this.bufferField.write(byteBuffer);
        this.emitCompleteSegments();
        return n;
    }

    @Override
    public BufferedSink write(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.write(byteString);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink write(ByteString byteString, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.write(byteString, n, n2);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink write(Source source2, long l) {
        Intrinsics.checkParameterIsNotNull(source2, "source");
        while (l > 0L) {
            long l2 = source2.read(this.bufferField, l);
            if (l2 == -1L) throw (Throwable)new EOFException();
            l -= l2;
            this.emitCompleteSegments();
        }
        return this;
    }

    @Override
    public BufferedSink write(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "source");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.write(arrby);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink write(byte[] arrby, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(arrby, "source");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.write(arrby, n, n2);
        return this.emitCompleteSegments();
    }

    @Override
    public void write(Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "source");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.write(buffer, l);
        this.emitCompleteSegments();
    }

    @Override
    public long writeAll(Source source2) {
        Intrinsics.checkParameterIsNotNull(source2, "source");
        long l = 0L;
        long l2;
        while ((l2 = source2.read(this.bufferField, 8192)) != -1L) {
            l += l2;
            this.emitCompleteSegments();
        }
        return l;
    }

    @Override
    public BufferedSink writeByte(int n) {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeByte(n);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeDecimalLong(long l) {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeDecimalLong(l);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeHexadecimalUnsignedLong(long l) {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeHexadecimalUnsignedLong(l);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeInt(int n) {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeInt(n);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeIntLe(int n) {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeIntLe(n);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeLong(long l) {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeLong(l);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeLongLe(long l) {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeLongLe(l);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeShort(int n) {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeShort(n);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeShortLe(int n) {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeShortLe(n);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeString(String string2, int n, int n2, Charset charset) {
        Intrinsics.checkParameterIsNotNull(string2, "string");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeString(string2, n, n2, charset);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeString(String string2, Charset charset) {
        Intrinsics.checkParameterIsNotNull(string2, "string");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeString(string2, charset);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeUtf8(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "string");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeUtf8(string2);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeUtf8(String string2, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(string2, "string");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeUtf8(string2, n, n2);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeUtf8CodePoint(int n) {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        this.bufferField.writeUtf8CodePoint(n);
        return this.emitCompleteSegments();
    }
}


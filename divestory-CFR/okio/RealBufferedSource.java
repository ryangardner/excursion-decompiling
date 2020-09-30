/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okio.RealBufferedSource$inputStream
 */
package okio;

import java.io.EOFException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.PeekSource;
import okio.RealBufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import okio._Util;
import okio.internal.BufferKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\rH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012H\u0016J \u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0012H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u0012H\u0016J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u0012H\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\rH\u0016J\b\u0010\u001e\u001a\u00020\u0001H\u0016J\u0018\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J(\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0016J\u0010\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020'H\u0016J \u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020'2\u0006\u0010 \u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0016J\u0018\u0010$\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0012H\u0016J\u0010\u0010(\u001a\u00020\u00122\u0006\u0010%\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020\u0014H\u0016J\b\u0010+\u001a\u00020'H\u0016J\u0010\u0010+\u001a\u00020'2\u0006\u0010#\u001a\u00020\u0012H\u0016J\b\u0010,\u001a\u00020\u0018H\u0016J\u0010\u0010,\u001a\u00020\u00182\u0006\u0010#\u001a\u00020\u0012H\u0016J\b\u0010-\u001a\u00020\u0012H\u0016J\u0010\u0010.\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020'H\u0016J\u0018\u0010.\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0012H\u0016J\b\u0010/\u001a\u00020\u0012H\u0016J\b\u00100\u001a\u00020\"H\u0016J\b\u00101\u001a\u00020\"H\u0016J\b\u00102\u001a\u00020\u0012H\u0016J\b\u00103\u001a\u00020\u0012H\u0016J\b\u00104\u001a\u000205H\u0016J\b\u00106\u001a\u000205H\u0016J\u0010\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:H\u0016J\u0018\u00107\u001a\u0002082\u0006\u0010#\u001a\u00020\u00122\u0006\u00109\u001a\u00020:H\u0016J\b\u0010;\u001a\u000208H\u0016J\u0010\u0010;\u001a\u0002082\u0006\u0010#\u001a\u00020\u0012H\u0016J\b\u0010<\u001a\u00020\"H\u0016J\n\u0010=\u001a\u0004\u0018\u000108H\u0016J\b\u0010>\u001a\u000208H\u0016J\u0010\u0010>\u001a\u0002082\u0006\u0010?\u001a\u00020\u0012H\u0016J\u0010\u0010@\u001a\u00020\r2\u0006\u0010#\u001a\u00020\u0012H\u0016J\u0010\u0010A\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u0012H\u0016J\u0010\u0010B\u001a\u00020\"2\u0006\u0010C\u001a\u00020DH\u0016J\u0010\u0010E\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u0012H\u0016J\b\u0010F\u001a\u00020GH\u0016J\b\u0010H\u001a\u000208H\u0016R\u001b\u0010\u0005\u001a\u00020\u00068\u00d6\u0002X\u0096\u0004\u00a2\u0006\f\u0012\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00020\r8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006I"}, d2={"Lokio/RealBufferedSource;", "Lokio/BufferedSource;", "source", "Lokio/Source;", "(Lokio/Source;)V", "buffer", "Lokio/Buffer;", "buffer$annotations", "()V", "getBuffer", "()Lokio/Buffer;", "bufferField", "closed", "", "close", "", "exhausted", "indexOf", "", "b", "", "fromIndex", "toIndex", "bytes", "Lokio/ByteString;", "indexOfElement", "targetBytes", "inputStream", "Ljava/io/InputStream;", "isOpen", "peek", "rangeEquals", "offset", "bytesOffset", "", "byteCount", "read", "sink", "Ljava/nio/ByteBuffer;", "", "readAll", "Lokio/Sink;", "readByte", "readByteArray", "readByteString", "readDecimalLong", "readFully", "readHexadecimalUnsignedLong", "readInt", "readIntLe", "readLong", "readLongLe", "readShort", "", "readShortLe", "readString", "", "charset", "Ljava/nio/charset/Charset;", "readUtf8", "readUtf8CodePoint", "readUtf8Line", "readUtf8LineStrict", "limit", "request", "require", "select", "options", "Lokio/Options;", "skip", "timeout", "Lokio/Timeout;", "toString", "okio"}, k=1, mv={1, 1, 16})
public final class RealBufferedSource
implements BufferedSource {
    public final Buffer bufferField;
    public boolean closed;
    public final Source source;

    public RealBufferedSource(Source source2) {
        Intrinsics.checkParameterIsNotNull(source2, "source");
        this.source = source2;
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
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.source.close();
        this.bufferField.clear();
    }

    @Override
    public boolean exhausted() {
        boolean bl = this.closed;
        boolean bl2 = true;
        if (!(bl ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        if (!this.bufferField.exhausted()) return false;
        if (this.source.read(this.bufferField, 8192) != -1L) return false;
        return bl2;
    }

    @Override
    public Buffer getBuffer() {
        return this.bufferField;
    }

    @Override
    public long indexOf(byte by) {
        return this.indexOf(by, 0L, Long.MAX_VALUE);
    }

    @Override
    public long indexOf(byte by, long l) {
        return this.indexOf(by, l, Long.MAX_VALUE);
    }

    @Override
    public long indexOf(byte by, long l, long l2) {
        boolean bl = this.closed;
        boolean bl2 = true;
        if (!(bl ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        if (0L > l || l2 < l) {
            bl2 = false;
        }
        if (!bl2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fromIndex=");
            stringBuilder.append(l);
            stringBuilder.append(" toIndex=");
            stringBuilder.append(l2);
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        do {
            long l3;
            long l4 = l3 = -1L;
            if (l >= l2) return l4;
            l4 = this.bufferField.indexOf(by, l, l2);
            if (l4 != -1L) {
                return l4;
            }
            long l5 = this.bufferField.size();
            l4 = l3;
            if (l5 >= l2) return l4;
            if (this.source.read(this.bufferField, 8192) == -1L) {
                return l3;
            }
            l = Math.max(l, l5);
        } while (true);
    }

    @Override
    public long indexOf(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        return this.indexOf(byteString, 0L);
    }

    @Override
    public long indexOf(ByteString byteString, long l) {
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        long l2;
        while ((l2 = this.bufferField.indexOf(byteString, l)) == -1L) {
            l2 = this.bufferField.size();
            if (this.source.read(this.bufferField, 8192) == -1L) {
                return -1L;
            }
            l = Math.max(l, l2 - (long)byteString.size() + 1L);
        }
        return l2;
    }

    @Override
    public long indexOfElement(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "targetBytes");
        return this.indexOfElement(byteString, 0L);
    }

    @Override
    public long indexOfElement(ByteString byteString, long l) {
        Intrinsics.checkParameterIsNotNull(byteString, "targetBytes");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        long l2;
        while ((l2 = this.bufferField.indexOfElement(byteString, l)) == -1L) {
            l2 = this.bufferField.size();
            if (this.source.read(this.bufferField, 8192) == -1L) {
                return -1L;
            }
            l = Math.max(l, l2);
        }
        return l2;
    }

    @Override
    public InputStream inputStream() {
        return new InputStream(this){
            final /* synthetic */ RealBufferedSource this$0;
            {
                this.this$0 = realBufferedSource;
            }

            public int available() {
                if (this.this$0.closed) throw (Throwable)new java.io.IOException("closed");
                return (int)Math.min(this.this$0.bufferField.size(), (long)Integer.MAX_VALUE);
            }

            public void close() {
                this.this$0.close();
            }

            public int read() {
                if (this.this$0.closed) throw (Throwable)new java.io.IOException("closed");
                if (this.this$0.bufferField.size() != 0L) return this.this$0.bufferField.readByte() & 255;
                if (this.this$0.source.read(this.this$0.bufferField, 8192) != -1L) return this.this$0.bufferField.readByte() & 255;
                return -1;
            }

            public int read(byte[] arrby, int n, int n2) {
                Intrinsics.checkParameterIsNotNull(arrby, "data");
                if (this.this$0.closed) throw (Throwable)new java.io.IOException("closed");
                _Util.checkOffsetAndCount(arrby.length, n, n2);
                if (this.this$0.bufferField.size() != 0L) return this.this$0.bufferField.read(arrby, n, n2);
                if (this.this$0.source.read(this.this$0.bufferField, 8192) != -1L) return this.this$0.bufferField.read(arrby, n, n2);
                return -1;
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.this$0);
                stringBuilder.append(".inputStream()");
                return stringBuilder.toString();
            }
        };
    }

    @Override
    public boolean isOpen() {
        return this.closed ^ true;
    }

    @Override
    public BufferedSource peek() {
        return Okio.buffer(new PeekSource(this));
    }

    @Override
    public boolean rangeEquals(long l, ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        return this.rangeEquals(l, byteString, 0, byteString.size());
    }

    @Override
    public boolean rangeEquals(long l, ByteString byteString, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        boolean bl = this.closed;
        boolean bl2 = true;
        if (!(bl ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        if (l < 0L) return false;
        if (n < 0) return false;
        if (n2 < 0) return false;
        if (byteString.size() - n < n2) return false;
        int n3 = 0;
        do {
            bl = bl2;
            if (n3 >= n2) return bl;
            long l2 = (long)n3 + l;
            if (!this.request(1L + l2) || this.bufferField.getByte(l2) != byteString.getByte(n + n3)) return false;
            ++n3;
        } while (true);
    }

    @Override
    public int read(ByteBuffer byteBuffer) {
        Intrinsics.checkParameterIsNotNull(byteBuffer, "sink");
        if (this.bufferField.size() != 0L) return this.bufferField.read(byteBuffer);
        if (this.source.read(this.bufferField, 8192) != -1L) return this.bufferField.read(byteBuffer);
        return -1;
    }

    @Override
    public int read(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "sink");
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(arrby, "sink");
        long l = arrby.length;
        long l2 = n;
        long l3 = n2;
        _Util.checkOffsetAndCount(l, l2, l3);
        if (this.bufferField.size() == 0L && this.source.read(this.bufferField, 8192) == -1L) {
            return -1;
        }
        n2 = (int)Math.min(l3, this.bufferField.size());
        return this.bufferField.read(arrby, n, n2);
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
        if (!(true ^ this.closed)) throw (Throwable)new IllegalStateException("closed".toString());
        long l2 = this.bufferField.size();
        long l3 = -1L;
        if (l2 == 0L && this.source.read(this.bufferField, 8192) == -1L) {
            return l3;
        }
        l = Math.min(l, this.bufferField.size());
        return this.bufferField.read((Buffer)object, l);
    }

    @Override
    public long readAll(Sink sink2) {
        Intrinsics.checkParameterIsNotNull(sink2, "sink");
        long l = 0L;
        do {
            long l2;
            if (this.source.read(this.bufferField, 8192) == -1L) {
                l2 = l;
                if (this.bufferField.size() <= 0L) return l2;
                l2 = l + this.bufferField.size();
                Buffer buffer = this.bufferField;
                sink2.write(buffer, buffer.size());
                return l2;
            }
            l2 = this.bufferField.completeSegmentByteCount();
            if (l2 <= 0L) continue;
            l += l2;
            sink2.write(this.bufferField, l2);
        } while (true);
    }

    @Override
    public byte readByte() {
        this.require(1L);
        return this.bufferField.readByte();
    }

    @Override
    public byte[] readByteArray() {
        this.bufferField.writeAll(this.source);
        return this.bufferField.readByteArray();
    }

    @Override
    public byte[] readByteArray(long l) {
        this.require(l);
        return this.bufferField.readByteArray(l);
    }

    @Override
    public ByteString readByteString() {
        this.bufferField.writeAll(this.source);
        return this.bufferField.readByteString();
    }

    @Override
    public ByteString readByteString(long l) {
        this.require(l);
        return this.bufferField.readByteString(l);
    }

    @Override
    public long readDecimalLong() {
        byte by;
        long l;
        block3 : {
            long l2;
            this.require(1L);
            long l3 = 0L;
            while (this.request(l2 = l3 + 1L)) {
                by = this.bufferField.getByte(l3);
                if (by >= (byte)48 && by <= (byte)57 || (l = (l3 LCMP 0L)) == false && by == (byte)45) {
                    l3 = l2;
                    continue;
                }
                break block3;
            }
            return this.bufferField.readDecimalLong();
        }
        if (l != false) {
            return this.bufferField.readDecimalLong();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected leading [0-9] or '-' character but was 0x");
        String string2 = Integer.toString(by, CharsKt.checkRadix(CharsKt.checkRadix(16)));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Integer.toStri\u2026(this, checkRadix(radix))");
        stringBuilder.append(string2);
        throw (Throwable)new NumberFormatException(stringBuilder.toString());
    }

    @Override
    public void readFully(Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "sink");
        try {
            this.require(l);
        }
        catch (EOFException eOFException) {
            buffer.writeAll(this.bufferField);
            throw (Throwable)eOFException;
        }
        this.bufferField.readFully(buffer, l);
    }

    @Override
    public void readFully(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "sink");
        try {
            this.require(arrby.length);
        }
        catch (EOFException eOFException) {
            int n = 0;
            while (this.bufferField.size() > 0L) {
                Buffer buffer = this.bufferField;
                int n2 = buffer.read(arrby, n, (int)buffer.size());
                if (n2 == -1) throw (Throwable)((Object)new AssertionError());
                n += n2;
            }
            throw (Throwable)eOFException;
        }
        this.bufferField.readFully(arrby);
    }

    @Override
    public long readHexadecimalUnsignedLong() {
        int n;
        byte by;
        block3 : {
            int n2;
            this.require(1L);
            n = 0;
            while (this.request(n2 = n + 1)) {
                by = this.bufferField.getByte(n);
                if (by >= (byte)48 && by <= (byte)57 || by >= (byte)97 && by <= (byte)102 || by >= (byte)65 && by <= (byte)70) {
                    n = n2;
                    continue;
                }
                break block3;
            }
            return this.bufferField.readHexadecimalUnsignedLong();
        }
        if (n != 0) {
            return this.bufferField.readHexadecimalUnsignedLong();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected leading [0-9a-fA-F] character but was 0x");
        String string2 = Integer.toString(by, CharsKt.checkRadix(CharsKt.checkRadix(16)));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Integer.toStri\u2026(this, checkRadix(radix))");
        stringBuilder.append(string2);
        throw (Throwable)new NumberFormatException(stringBuilder.toString());
    }

    @Override
    public int readInt() {
        this.require(4L);
        return this.bufferField.readInt();
    }

    @Override
    public int readIntLe() {
        this.require(4L);
        return this.bufferField.readIntLe();
    }

    @Override
    public long readLong() {
        this.require(8L);
        return this.bufferField.readLong();
    }

    @Override
    public long readLongLe() {
        this.require(8L);
        return this.bufferField.readLongLe();
    }

    @Override
    public short readShort() {
        this.require(2L);
        return this.bufferField.readShort();
    }

    @Override
    public short readShortLe() {
        this.require(2L);
        return this.bufferField.readShortLe();
    }

    @Override
    public String readString(long l, Charset charset) {
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        this.require(l);
        return this.bufferField.readString(l, charset);
    }

    @Override
    public String readString(Charset charset) {
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        this.bufferField.writeAll(this.source);
        return this.bufferField.readString(charset);
    }

    @Override
    public String readUtf8() {
        this.bufferField.writeAll(this.source);
        return this.bufferField.readUtf8();
    }

    @Override
    public String readUtf8(long l) {
        this.require(l);
        return this.bufferField.readUtf8(l);
    }

    @Override
    public int readUtf8CodePoint() {
        this.require(1L);
        byte by = this.bufferField.getByte(0L);
        if ((by & 224) == 192) {
            this.require(2L);
            return this.bufferField.readUtf8CodePoint();
        }
        if ((by & 240) == 224) {
            this.require(3L);
            return this.bufferField.readUtf8CodePoint();
        }
        if ((by & 248) != 240) return this.bufferField.readUtf8CodePoint();
        this.require(4L);
        return this.bufferField.readUtf8CodePoint();
    }

    @Override
    public String readUtf8Line() {
        long l = this.indexOf((byte)10);
        if (l != -1L) {
            return BufferKt.readUtf8Line(this.bufferField, l);
        }
        if (this.bufferField.size() == 0L) return null;
        return this.readUtf8(this.bufferField.size());
    }

    @Override
    public String readUtf8LineStrict() {
        return this.readUtf8LineStrict(Long.MAX_VALUE);
    }

    @Override
    public String readUtf8LineStrict(long l) {
        boolean bl = l >= 0L;
        if (!bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("limit < 0: ");
            stringBuilder.append(l);
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        byte by = (byte)10;
        long l2 = l == Long.MAX_VALUE ? Long.MAX_VALUE : l + 1L;
        long l3 = this.indexOf(by, 0L, l2);
        if (l3 != -1L) {
            return BufferKt.readUtf8Line(this.bufferField, l3);
        }
        if (l2 < Long.MAX_VALUE && this.request(l2) && this.bufferField.getByte(l2 - 1L) == (byte)13 && this.request(1L + l2) && this.bufferField.getByte(l2) == by) {
            return BufferKt.readUtf8Line(this.bufferField, l2);
        }
        Buffer buffer = new Buffer();
        Object object = this.bufferField;
        l2 = ((Buffer)object).size();
        ((Buffer)object).copyTo(buffer, 0L, Math.min((long)32, l2));
        object = new StringBuilder();
        ((StringBuilder)object).append("\\n not found: limit=");
        ((StringBuilder)object).append(Math.min(this.bufferField.size(), l));
        ((StringBuilder)object).append(" content=");
        ((StringBuilder)object).append(buffer.readByteString().hex());
        ((StringBuilder)object).append("\u2026");
        throw (Throwable)new EOFException(((StringBuilder)object).toString());
    }

    @Override
    public boolean request(long l) {
        boolean bl = false;
        boolean bl2 = l >= 0L;
        if (!bl2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("byteCount < 0: ");
            stringBuilder.append(l);
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        do {
            if (this.bufferField.size() < l) continue;
            return true;
        } while (this.source.read(this.bufferField, 8192) != -1L);
        return bl;
    }

    @Override
    public void require(long l) {
        if (!this.request(l)) throw (Throwable)new EOFException();
    }

    @Override
    public int select(Options options) {
        Intrinsics.checkParameterIsNotNull(options, "options");
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        do {
            int n;
            if ((n = BufferKt.selectPrefix(this.bufferField, options, true)) == -2) continue;
            if (n == -1) return -1;
            int n2 = options.getByteStrings$okio()[n].size();
            this.bufferField.skip(n2);
            return n;
        } while (this.source.read(this.bufferField, 8192) != -1L);
        return -1;
    }

    @Override
    public void skip(long l) {
        if (!(this.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        while (l > 0L) {
            if (this.bufferField.size() == 0L) {
                if (this.source.read(this.bufferField, 8192) == -1L) throw (Throwable)new EOFException();
            }
            long l2 = Math.min(l, this.bufferField.size());
            this.bufferField.skip(l2);
            l -= l2;
        }
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("buffer(");
        stringBuilder.append(this.source);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}


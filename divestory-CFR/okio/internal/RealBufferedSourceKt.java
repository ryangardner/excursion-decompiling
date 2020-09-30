/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio.internal;

import java.io.EOFException;
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

@Metadata(bv={1, 0, 3}, d1={"\u0000j\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\b\u001a\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0002H\b\u001a%\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\b\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\b\u001a\u001d\u0010\r\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\b\u001a\r\u0010\u000f\u001a\u00020\u0010*\u00020\u0002H\b\u001a-\u0010\u0011\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\b\u001a%\u0010\u0016\u001a\u00020\u0014*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\b\u001a\u001d\u0010\u0016\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\u0015\u0010\u001a\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u001bH\b\u001a\r\u0010\u001c\u001a\u00020\b*\u00020\u0002H\b\u001a\r\u0010\u001d\u001a\u00020\u0018*\u00020\u0002H\b\u001a\u0015\u0010\u001d\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\r\u0010\u001e\u001a\u00020\f*\u00020\u0002H\b\u001a\u0015\u0010\u001e\u001a\u00020\f*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\r\u0010\u001f\u001a\u00020\u0006*\u00020\u0002H\b\u001a\u0015\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0018H\b\u001a\u001d\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\r\u0010!\u001a\u00020\u0006*\u00020\u0002H\b\u001a\r\u0010\"\u001a\u00020\u0014*\u00020\u0002H\b\u001a\r\u0010#\u001a\u00020\u0014*\u00020\u0002H\b\u001a\r\u0010$\u001a\u00020\u0006*\u00020\u0002H\b\u001a\r\u0010%\u001a\u00020\u0006*\u00020\u0002H\b\u001a\r\u0010&\u001a\u00020'*\u00020\u0002H\b\u001a\r\u0010(\u001a\u00020'*\u00020\u0002H\b\u001a\r\u0010)\u001a\u00020**\u00020\u0002H\b\u001a\u0015\u0010)\u001a\u00020**\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\r\u0010+\u001a\u00020\u0014*\u00020\u0002H\b\u001a\u000f\u0010,\u001a\u0004\u0018\u00010**\u00020\u0002H\b\u001a\u0015\u0010-\u001a\u00020**\u00020\u00022\u0006\u0010.\u001a\u00020\u0006H\b\u001a\u0015\u0010/\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\u0015\u00100\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\u0015\u00101\u001a\u00020\u0014*\u00020\u00022\u0006\u00102\u001a\u000203H\b\u001a\u0015\u00104\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\r\u00105\u001a\u000206*\u00020\u0002H\b\u001a\r\u00107\u001a\u00020**\u00020\u0002H\b\u00a8\u00068"}, d2={"commonClose", "", "Lokio/RealBufferedSource;", "commonExhausted", "", "commonIndexOf", "", "b", "", "fromIndex", "toIndex", "bytes", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonPeek", "Lokio/BufferedSource;", "commonRangeEquals", "offset", "bytesOffset", "", "byteCount", "commonRead", "sink", "", "Lokio/Buffer;", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadIntLe", "commonReadLong", "commonReadLongLe", "commonReadShort", "", "commonReadShortLe", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonRequest", "commonRequire", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonTimeout", "Lokio/Timeout;", "commonToString", "okio"}, k=2, mv={1, 1, 16})
public final class RealBufferedSourceKt {
    public static final void commonClose(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonClose");
        if (realBufferedSource.closed) {
            return;
        }
        realBufferedSource.closed = true;
        realBufferedSource.source.close();
        realBufferedSource.bufferField.clear();
    }

    public static final boolean commonExhausted(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonExhausted");
        boolean bl = realBufferedSource.closed;
        boolean bl2 = true;
        if (!(bl ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        if (!realBufferedSource.bufferField.exhausted()) return false;
        if (realBufferedSource.source.read(realBufferedSource.bufferField, 8192) != -1L) return false;
        return bl2;
    }

    public static final long commonIndexOf(RealBufferedSource object, byte by, long l, long l2) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonIndexOf");
        boolean bl = ((RealBufferedSource)object).closed;
        boolean bl2 = true;
        if (!(bl ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        if (0L > l || l2 < l) {
            bl2 = false;
        }
        if (!bl2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("fromIndex=");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append(" toIndex=");
            ((StringBuilder)object).append(l2);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        while (l < l2) {
            long l3 = ((RealBufferedSource)object).bufferField.indexOf(by, l, l2);
            if (l3 != -1L) {
                return l3;
            }
            l3 = ((RealBufferedSource)object).bufferField.size();
            if (l3 >= l2) return -1L;
            if (((RealBufferedSource)object).source.read(((RealBufferedSource)object).bufferField, 8192) == -1L) {
                return -1L;
            }
            l = Math.max(l, l3);
        }
        return -1L;
    }

    public static final long commonIndexOf(RealBufferedSource realBufferedSource, ByteString byteString, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonIndexOf");
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        if (!(realBufferedSource.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        long l2;
        while ((l2 = realBufferedSource.bufferField.indexOf(byteString, l)) == -1L) {
            l2 = realBufferedSource.bufferField.size();
            if (realBufferedSource.source.read(realBufferedSource.bufferField, 8192) == -1L) {
                return -1L;
            }
            l = Math.max(l, l2 - (long)byteString.size() + 1L);
        }
        return l2;
    }

    public static final long commonIndexOfElement(RealBufferedSource realBufferedSource, ByteString byteString, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonIndexOfElement");
        Intrinsics.checkParameterIsNotNull(byteString, "targetBytes");
        if (!(realBufferedSource.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        long l2;
        while ((l2 = realBufferedSource.bufferField.indexOfElement(byteString, l)) == -1L) {
            l2 = realBufferedSource.bufferField.size();
            if (realBufferedSource.source.read(realBufferedSource.bufferField, 8192) == -1L) {
                return -1L;
            }
            l = Math.max(l, l2);
        }
        return l2;
    }

    public static final BufferedSource commonPeek(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonPeek");
        return Okio.buffer(new PeekSource(realBufferedSource));
    }

    public static final boolean commonRangeEquals(RealBufferedSource realBufferedSource, long l, ByteString byteString, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonRangeEquals");
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        if (!(realBufferedSource.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        if (l < 0L) return false;
        if (n < 0) return false;
        if (n2 < 0) return false;
        if (byteString.size() - n < n2) {
            return false;
        }
        int n3 = 0;
        while (n3 < n2) {
            long l2 = (long)n3 + l;
            if (!realBufferedSource.request(1L + l2)) {
                return false;
            }
            if (realBufferedSource.bufferField.getByte(l2) != byteString.getByte(n + n3)) {
                return false;
            }
            ++n3;
        }
        return true;
    }

    public static final int commonRead(RealBufferedSource realBufferedSource, byte[] arrby, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonRead");
        Intrinsics.checkParameterIsNotNull(arrby, "sink");
        long l = arrby.length;
        long l2 = n;
        long l3 = n2;
        _Util.checkOffsetAndCount(l, l2, l3);
        if (realBufferedSource.bufferField.size() == 0L && realBufferedSource.source.read(realBufferedSource.bufferField, 8192) == -1L) {
            return -1;
        }
        n2 = (int)Math.min(l3, realBufferedSource.bufferField.size());
        return realBufferedSource.bufferField.read(arrby, n, n2);
    }

    public static final long commonRead(RealBufferedSource object, Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonRead");
        Intrinsics.checkParameterIsNotNull(buffer, "sink");
        boolean bl = l >= 0L;
        if (!bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount < 0: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        if (!(true ^ ((RealBufferedSource)object).closed)) throw (Throwable)new IllegalStateException("closed".toString());
        if (((RealBufferedSource)object).bufferField.size() == 0L && ((RealBufferedSource)object).source.read(((RealBufferedSource)object).bufferField, 8192) == -1L) {
            return -1L;
        }
        l = Math.min(l, ((RealBufferedSource)object).bufferField.size());
        return ((RealBufferedSource)object).bufferField.read(buffer, l);
    }

    public static final long commonReadAll(RealBufferedSource realBufferedSource, Sink sink2) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadAll");
        Intrinsics.checkParameterIsNotNull(sink2, "sink");
        long l = 0L;
        do {
            long l2;
            if (realBufferedSource.source.read(realBufferedSource.bufferField, 8192) == -1L) {
                l2 = l;
                if (realBufferedSource.bufferField.size() <= 0L) return l2;
                l2 = l + realBufferedSource.bufferField.size();
                sink2.write(realBufferedSource.bufferField, realBufferedSource.bufferField.size());
                return l2;
            }
            l2 = realBufferedSource.bufferField.completeSegmentByteCount();
            if (l2 <= 0L) continue;
            l += l2;
            sink2.write(realBufferedSource.bufferField, l2);
        } while (true);
    }

    public static final byte commonReadByte(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadByte");
        realBufferedSource.require(1L);
        return realBufferedSource.bufferField.readByte();
    }

    public static final byte[] commonReadByteArray(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadByteArray");
        realBufferedSource.bufferField.writeAll(realBufferedSource.source);
        return realBufferedSource.bufferField.readByteArray();
    }

    public static final byte[] commonReadByteArray(RealBufferedSource realBufferedSource, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadByteArray");
        realBufferedSource.require(l);
        return realBufferedSource.bufferField.readByteArray(l);
    }

    public static final ByteString commonReadByteString(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadByteString");
        realBufferedSource.bufferField.writeAll(realBufferedSource.source);
        return realBufferedSource.bufferField.readByteString();
    }

    public static final ByteString commonReadByteString(RealBufferedSource realBufferedSource, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadByteString");
        realBufferedSource.require(l);
        return realBufferedSource.bufferField.readByteString(l);
    }

    public static final long commonReadDecimalLong(RealBufferedSource object) {
        byte by;
        long l;
        block3 : {
            long l2;
            Intrinsics.checkParameterIsNotNull(object, "$this$commonReadDecimalLong");
            ((RealBufferedSource)object).require(1L);
            long l3 = 0L;
            while (((RealBufferedSource)object).request(l2 = l3 + 1L)) {
                by = ((RealBufferedSource)object).bufferField.getByte(l3);
                if (by >= (byte)48 && by <= (byte)57 || (l = (l3 LCMP 0L)) == false && by == (byte)45) {
                    l3 = l2;
                    continue;
                }
                break block3;
            }
            return ((RealBufferedSource)object).bufferField.readDecimalLong();
        }
        if (l != false) {
            return ((RealBufferedSource)object).bufferField.readDecimalLong();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Expected leading [0-9] or '-' character but was 0x");
        String string2 = Integer.toString(by, CharsKt.checkRadix(CharsKt.checkRadix(16)));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Integer.toStri\u2026(this, checkRadix(radix))");
        ((StringBuilder)object).append(string2);
        throw (Throwable)new NumberFormatException(((StringBuilder)object).toString());
    }

    public static final void commonReadFully(RealBufferedSource realBufferedSource, Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadFully");
        Intrinsics.checkParameterIsNotNull(buffer, "sink");
        try {
            realBufferedSource.require(l);
        }
        catch (EOFException eOFException) {
            buffer.writeAll(realBufferedSource.bufferField);
            throw (Throwable)eOFException;
        }
        realBufferedSource.bufferField.readFully(buffer, l);
    }

    public static final void commonReadFully(RealBufferedSource realBufferedSource, byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadFully");
        Intrinsics.checkParameterIsNotNull(arrby, "sink");
        try {
            realBufferedSource.require(arrby.length);
        }
        catch (EOFException eOFException) {
            int n = 0;
            while (realBufferedSource.bufferField.size() > 0L) {
                int n2 = realBufferedSource.bufferField.read(arrby, n, (int)realBufferedSource.bufferField.size());
                if (n2 == -1) throw (Throwable)((Object)new AssertionError());
                n += n2;
            }
            throw (Throwable)eOFException;
        }
        realBufferedSource.bufferField.readFully(arrby);
    }

    public static final long commonReadHexadecimalUnsignedLong(RealBufferedSource object) {
        int n;
        byte by;
        block3 : {
            int n2;
            Intrinsics.checkParameterIsNotNull(object, "$this$commonReadHexadecimalUnsignedLong");
            ((RealBufferedSource)object).require(1L);
            n = 0;
            while (((RealBufferedSource)object).request(n2 = n + 1)) {
                by = ((RealBufferedSource)object).bufferField.getByte(n);
                if (by >= (byte)48 && by <= (byte)57 || by >= (byte)97 && by <= (byte)102 || by >= (byte)65 && by <= (byte)70) {
                    n = n2;
                    continue;
                }
                break block3;
            }
            return ((RealBufferedSource)object).bufferField.readHexadecimalUnsignedLong();
        }
        if (n != 0) {
            return ((RealBufferedSource)object).bufferField.readHexadecimalUnsignedLong();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Expected leading [0-9a-fA-F] character but was 0x");
        String string2 = Integer.toString(by, CharsKt.checkRadix(CharsKt.checkRadix(16)));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Integer.toStri\u2026(this, checkRadix(radix))");
        ((StringBuilder)object).append(string2);
        throw (Throwable)new NumberFormatException(((StringBuilder)object).toString());
    }

    public static final int commonReadInt(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadInt");
        realBufferedSource.require(4L);
        return realBufferedSource.bufferField.readInt();
    }

    public static final int commonReadIntLe(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadIntLe");
        realBufferedSource.require(4L);
        return realBufferedSource.bufferField.readIntLe();
    }

    public static final long commonReadLong(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadLong");
        realBufferedSource.require(8L);
        return realBufferedSource.bufferField.readLong();
    }

    public static final long commonReadLongLe(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadLongLe");
        realBufferedSource.require(8L);
        return realBufferedSource.bufferField.readLongLe();
    }

    public static final short commonReadShort(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadShort");
        realBufferedSource.require(2L);
        return realBufferedSource.bufferField.readShort();
    }

    public static final short commonReadShortLe(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadShortLe");
        realBufferedSource.require(2L);
        return realBufferedSource.bufferField.readShortLe();
    }

    public static final String commonReadUtf8(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadUtf8");
        realBufferedSource.bufferField.writeAll(realBufferedSource.source);
        return realBufferedSource.bufferField.readUtf8();
    }

    public static final String commonReadUtf8(RealBufferedSource realBufferedSource, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadUtf8");
        realBufferedSource.require(l);
        return realBufferedSource.bufferField.readUtf8(l);
    }

    public static final int commonReadUtf8CodePoint(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonReadUtf8CodePoint");
        realBufferedSource.require(1L);
        byte by = realBufferedSource.bufferField.getByte(0L);
        if ((by & 224) == 192) {
            realBufferedSource.require(2L);
            return realBufferedSource.bufferField.readUtf8CodePoint();
        }
        if ((by & 240) == 224) {
            realBufferedSource.require(3L);
            return realBufferedSource.bufferField.readUtf8CodePoint();
        }
        if ((by & 248) != 240) return realBufferedSource.bufferField.readUtf8CodePoint();
        realBufferedSource.require(4L);
        return realBufferedSource.bufferField.readUtf8CodePoint();
    }

    public static final String commonReadUtf8Line(RealBufferedSource object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonReadUtf8Line");
        long l = ((RealBufferedSource)object).indexOf((byte)10);
        if (l != -1L) {
            return BufferKt.readUtf8Line(((RealBufferedSource)object).bufferField, l);
        }
        if (((RealBufferedSource)object).bufferField.size() == 0L) return null;
        return ((RealBufferedSource)object).readUtf8(((RealBufferedSource)object).bufferField.size());
    }

    public static final String commonReadUtf8LineStrict(RealBufferedSource object, long l) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonReadUtf8LineStrict");
        boolean bl = l >= 0L;
        if (!bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("limit < 0: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        byte by = (byte)10;
        long l2 = l == Long.MAX_VALUE ? Long.MAX_VALUE : l + 1L;
        long l3 = ((RealBufferedSource)object).indexOf(by, 0L, l2);
        if (l3 != -1L) {
            return BufferKt.readUtf8Line(((RealBufferedSource)object).bufferField, l3);
        }
        if (l2 < Long.MAX_VALUE && ((RealBufferedSource)object).request(l2) && ((RealBufferedSource)object).bufferField.getByte(l2 - 1L) == (byte)13 && ((RealBufferedSource)object).request(1L + l2) && ((RealBufferedSource)object).bufferField.getByte(l2) == by) {
            return BufferKt.readUtf8Line(((RealBufferedSource)object).bufferField, l2);
        }
        Buffer buffer = new Buffer();
        Object object2 = ((RealBufferedSource)object).bufferField;
        l2 = ((RealBufferedSource)object).bufferField.size();
        ((Buffer)object2).copyTo(buffer, 0L, Math.min((long)32, l2));
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("\\n not found: limit=");
        ((StringBuilder)object2).append(Math.min(((RealBufferedSource)object).bufferField.size(), l));
        ((StringBuilder)object2).append(" content=");
        ((StringBuilder)object2).append(buffer.readByteString().hex());
        ((StringBuilder)object2).append("\u2026");
        throw (Throwable)new EOFException(((StringBuilder)object2).toString());
    }

    public static final boolean commonRequest(RealBufferedSource object, long l) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonRequest");
        boolean bl = l >= 0L;
        if (!bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount < 0: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        if (!(((RealBufferedSource)object).closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        do {
            if (((RealBufferedSource)object).bufferField.size() >= l) return true;
        } while (((RealBufferedSource)object).source.read(((RealBufferedSource)object).bufferField, 8192) != -1L);
        return false;
    }

    public static final void commonRequire(RealBufferedSource realBufferedSource, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonRequire");
        if (!realBufferedSource.request(l)) throw (Throwable)new EOFException();
    }

    public static final int commonSelect(RealBufferedSource realBufferedSource, Options options) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonSelect");
        Intrinsics.checkParameterIsNotNull(options, "options");
        if (!(realBufferedSource.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        do {
            int n;
            if ((n = BufferKt.selectPrefix(realBufferedSource.bufferField, options, true)) == -2) continue;
            if (n == -1) return -1;
            int n2 = options.getByteStrings$okio()[n].size();
            realBufferedSource.bufferField.skip(n2);
            return n;
        } while (realBufferedSource.source.read(realBufferedSource.bufferField, 8192) != -1L);
        return -1;
    }

    public static final void commonSkip(RealBufferedSource realBufferedSource, long l) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonSkip");
        if (!(realBufferedSource.closed ^ true)) throw (Throwable)new IllegalStateException("closed".toString());
        while (l > 0L) {
            if (realBufferedSource.bufferField.size() == 0L) {
                if (realBufferedSource.source.read(realBufferedSource.bufferField, 8192) == -1L) throw (Throwable)new EOFException();
            }
            long l2 = Math.min(l, realBufferedSource.bufferField.size());
            realBufferedSource.bufferField.skip(l2);
            l -= l2;
        }
    }

    public static final Timeout commonTimeout(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonTimeout");
        return realBufferedSource.source.timeout();
    }

    public static final String commonToString(RealBufferedSource realBufferedSource) {
        Intrinsics.checkParameterIsNotNull(realBufferedSource, "$this$commonToString");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("buffer(");
        stringBuilder.append(realBufferedSource.source);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}


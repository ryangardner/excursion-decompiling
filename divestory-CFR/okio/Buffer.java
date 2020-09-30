/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okio.Buffer$inputStream
 *  okio.Buffer$outputStream
 */
package okio;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.PeekSource;
import okio.Segment;
import okio.SegmentPool;
import okio.SegmentedByteString;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import okio._Util;
import okio.internal.BufferKt;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000\u00aa\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001a\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0017\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0002\u0090\u0001B\u0005\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0000H\u0016J\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u0000H\u0016J\b\u0010\u0014\u001a\u00020\u0012H\u0016J\u0006\u0010\u0015\u001a\u00020\fJ\u0006\u0010\u0016\u001a\u00020\u0000J$\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\f2\b\b\u0002\u0010\u001b\u001a\u00020\fH\u0007J\u0018\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u001a\u001a\u00020\fJ \u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\fJ\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\b\u0010 \u001a\u00020\u0000H\u0016J\b\u0010!\u001a\u00020\u0000H\u0016J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0096\u0002J\b\u0010&\u001a\u00020#H\u0016J\b\u0010'\u001a\u00020\u0012H\u0016J\u0016\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\fH\u0087\u0002\u00a2\u0006\u0002\b+J\u0015\u0010+\u001a\u00020)2\u0006\u0010,\u001a\u00020\fH\u0007\u00a2\u0006\u0002\b-J\b\u0010.\u001a\u00020/H\u0016J\u0018\u00100\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u00101\u001a\u00020\u001dH\u0002J\u000e\u00102\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u001dJ\u000e\u00103\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u001dJ\u000e\u00104\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u001dJ\u0010\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020)H\u0016J\u0018\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020)2\u0006\u00107\u001a\u00020\fH\u0016J \u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020)2\u0006\u00107\u001a\u00020\f2\u0006\u00108\u001a\u00020\fH\u0016J\u0010\u00105\u001a\u00020\f2\u0006\u00109\u001a\u00020\u001dH\u0016J\u0018\u00105\u001a\u00020\f2\u0006\u00109\u001a\u00020\u001d2\u0006\u00107\u001a\u00020\fH\u0016J\u0010\u0010:\u001a\u00020\f2\u0006\u0010;\u001a\u00020\u001dH\u0016J\u0018\u0010:\u001a\u00020\f2\u0006\u0010;\u001a\u00020\u001d2\u0006\u00107\u001a\u00020\fH\u0016J\b\u0010<\u001a\u00020=H\u0016J\b\u0010>\u001a\u00020#H\u0016J\u0006\u0010?\u001a\u00020\u001dJ\b\u0010@\u001a\u00020\u0019H\u0016J\b\u0010A\u001a\u00020\u0001H\u0016J\u0018\u0010B\u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\f2\u0006\u00109\u001a\u00020\u001dH\u0016J(\u0010B\u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\f2\u0006\u00109\u001a\u00020\u001d2\u0006\u0010C\u001a\u00020/2\u0006\u0010\u001b\u001a\u00020/H\u0016J\u0010\u0010D\u001a\u00020/2\u0006\u0010E\u001a\u00020FH\u0016J\u0010\u0010D\u001a\u00020/2\u0006\u0010E\u001a\u00020GH\u0016J \u0010D\u001a\u00020/2\u0006\u0010E\u001a\u00020G2\u0006\u0010\u001a\u001a\u00020/2\u0006\u0010\u001b\u001a\u00020/H\u0016J\u0018\u0010D\u001a\u00020\f2\u0006\u0010E\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0010\u0010H\u001a\u00020\f2\u0006\u0010E\u001a\u00020IH\u0016J\u0012\u0010J\u001a\u00020K2\b\b\u0002\u0010L\u001a\u00020KH\u0007J\b\u0010M\u001a\u00020)H\u0016J\b\u0010N\u001a\u00020GH\u0016J\u0010\u0010N\u001a\u00020G2\u0006\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010O\u001a\u00020\u001dH\u0016J\u0010\u0010O\u001a\u00020\u001d2\u0006\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010P\u001a\u00020\fH\u0016J\u000e\u0010Q\u001a\u00020\u00002\u0006\u0010R\u001a\u00020=J\u0016\u0010Q\u001a\u00020\u00002\u0006\u0010R\u001a\u00020=2\u0006\u0010\u001b\u001a\u00020\fJ \u0010Q\u001a\u00020\u00122\u0006\u0010R\u001a\u00020=2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010S\u001a\u00020#H\u0002J\u0010\u0010T\u001a\u00020\u00122\u0006\u0010E\u001a\u00020GH\u0016J\u0018\u0010T\u001a\u00020\u00122\u0006\u0010E\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010U\u001a\u00020\fH\u0016J\b\u0010V\u001a\u00020/H\u0016J\b\u0010W\u001a\u00020/H\u0016J\b\u0010X\u001a\u00020\fH\u0016J\b\u0010Y\u001a\u00020\fH\u0016J\b\u0010Z\u001a\u00020[H\u0016J\b\u0010\\\u001a\u00020[H\u0016J\u0010\u0010]\u001a\u00020\u001f2\u0006\u0010^\u001a\u00020_H\u0016J\u0018\u0010]\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010^\u001a\u00020_H\u0016J\u0012\u0010`\u001a\u00020K2\b\b\u0002\u0010L\u001a\u00020KH\u0007J\b\u0010a\u001a\u00020\u001fH\u0016J\u0010\u0010a\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010b\u001a\u00020/H\u0016J\n\u0010c\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010d\u001a\u00020\u001fH\u0016J\u0010\u0010d\u001a\u00020\u001f2\u0006\u0010e\u001a\u00020\fH\u0016J\u0010\u0010f\u001a\u00020#2\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0010\u0010g\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0010\u0010h\u001a\u00020/2\u0006\u0010i\u001a\u00020jH\u0016J\u0006\u0010k\u001a\u00020\u001dJ\u0006\u0010l\u001a\u00020\u001dJ\u0006\u0010m\u001a\u00020\u001dJ\r\u0010\r\u001a\u00020\fH\u0007\u00a2\u0006\u0002\bnJ\u0010\u0010o\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0006\u0010p\u001a\u00020\u001dJ\u000e\u0010p\u001a\u00020\u001d2\u0006\u0010\u001b\u001a\u00020/J\b\u0010q\u001a\u00020rH\u0016J\b\u0010s\u001a\u00020\u001fH\u0016J\u0015\u0010t\u001a\u00020\n2\u0006\u0010u\u001a\u00020/H\u0000\u00a2\u0006\u0002\bvJ\u0010\u0010w\u001a\u00020/2\u0006\u0010x\u001a\u00020FH\u0016J\u0010\u0010w\u001a\u00020\u00002\u0006\u0010x\u001a\u00020GH\u0016J \u0010w\u001a\u00020\u00002\u0006\u0010x\u001a\u00020G2\u0006\u0010\u001a\u001a\u00020/2\u0006\u0010\u001b\u001a\u00020/H\u0016J\u0018\u0010w\u001a\u00020\u00122\u0006\u0010x\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0010\u0010w\u001a\u00020\u00002\u0006\u0010y\u001a\u00020\u001dH\u0016J \u0010w\u001a\u00020\u00002\u0006\u0010y\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020/2\u0006\u0010\u001b\u001a\u00020/H\u0016J\u0018\u0010w\u001a\u00020\u00002\u0006\u0010x\u001a\u00020z2\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0010\u0010{\u001a\u00020\f2\u0006\u0010x\u001a\u00020zH\u0016J\u0010\u0010|\u001a\u00020\u00002\u0006\u00106\u001a\u00020/H\u0016J\u0010\u0010}\u001a\u00020\u00002\u0006\u0010~\u001a\u00020\fH\u0016J\u0010\u0010\u001a\u00020\u00002\u0006\u0010~\u001a\u00020\fH\u0016J\u0012\u0010\u0001\u001a\u00020\u00002\u0007\u0010\u0081\u0001\u001a\u00020/H\u0016J\u0012\u0010\u0082\u0001\u001a\u00020\u00002\u0007\u0010\u0081\u0001\u001a\u00020/H\u0016J\u0011\u0010\u0083\u0001\u001a\u00020\u00002\u0006\u0010~\u001a\u00020\fH\u0016J\u0011\u0010\u0084\u0001\u001a\u00020\u00002\u0006\u0010~\u001a\u00020\fH\u0016J\u0012\u0010\u0085\u0001\u001a\u00020\u00002\u0007\u0010\u0086\u0001\u001a\u00020/H\u0016J\u0012\u0010\u0087\u0001\u001a\u00020\u00002\u0007\u0010\u0086\u0001\u001a\u00020/H\u0016J\u001a\u0010\u0088\u0001\u001a\u00020\u00002\u0007\u0010\u0089\u0001\u001a\u00020\u001f2\u0006\u0010^\u001a\u00020_H\u0016J,\u0010\u0088\u0001\u001a\u00020\u00002\u0007\u0010\u0089\u0001\u001a\u00020\u001f2\u0007\u0010\u008a\u0001\u001a\u00020/2\u0007\u0010\u008b\u0001\u001a\u00020/2\u0006\u0010^\u001a\u00020_H\u0016J\u001b\u0010\u008c\u0001\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001b\u001a\u00020\fH\u0007J\u0012\u0010\u008d\u0001\u001a\u00020\u00002\u0007\u0010\u0089\u0001\u001a\u00020\u001fH\u0016J$\u0010\u008d\u0001\u001a\u00020\u00002\u0007\u0010\u0089\u0001\u001a\u00020\u001f2\u0007\u0010\u008a\u0001\u001a\u00020/2\u0007\u0010\u008b\u0001\u001a\u00020/H\u0016J\u0012\u0010\u008e\u0001\u001a\u00020\u00002\u0007\u0010\u008f\u0001\u001a\u00020/H\u0016R\u0014\u0010\u0006\u001a\u00020\u00008VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u0004\u0018\u00010\n8\u0000@\u0000X\u0081\u000e\u00a2\u0006\u0002\n\u0000R&\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f8G@@X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0091\u0001"}, d2={"Lokio/Buffer;", "Lokio/BufferedSource;", "Lokio/BufferedSink;", "", "Ljava/nio/channels/ByteChannel;", "()V", "buffer", "getBuffer", "()Lokio/Buffer;", "head", "Lokio/Segment;", "<set-?>", "", "size", "()J", "setSize$okio", "(J)V", "clear", "", "clone", "close", "completeSegmentByteCount", "copy", "copyTo", "out", "Ljava/io/OutputStream;", "offset", "byteCount", "digest", "Lokio/ByteString;", "algorithm", "", "emit", "emitCompleteSegments", "equals", "", "other", "", "exhausted", "flush", "get", "", "pos", "getByte", "index", "-deprecated_getByte", "hashCode", "", "hmac", "key", "hmacSha1", "hmacSha256", "hmacSha512", "indexOf", "b", "fromIndex", "toIndex", "bytes", "indexOfElement", "targetBytes", "inputStream", "Ljava/io/InputStream;", "isOpen", "md5", "outputStream", "peek", "rangeEquals", "bytesOffset", "read", "sink", "Ljava/nio/ByteBuffer;", "", "readAll", "Lokio/Sink;", "readAndWriteUnsafe", "Lokio/Buffer$UnsafeCursor;", "unsafeCursor", "readByte", "readByteArray", "readByteString", "readDecimalLong", "readFrom", "input", "forever", "readFully", "readHexadecimalUnsignedLong", "readInt", "readIntLe", "readLong", "readLongLe", "readShort", "", "readShortLe", "readString", "charset", "Ljava/nio/charset/Charset;", "readUnsafe", "readUtf8", "readUtf8CodePoint", "readUtf8Line", "readUtf8LineStrict", "limit", "request", "require", "select", "options", "Lokio/Options;", "sha1", "sha256", "sha512", "-deprecated_size", "skip", "snapshot", "timeout", "Lokio/Timeout;", "toString", "writableSegment", "minimumCapacity", "writableSegment$okio", "write", "source", "byteString", "Lokio/Source;", "writeAll", "writeByte", "writeDecimalLong", "v", "writeHexadecimalUnsignedLong", "writeInt", "i", "writeIntLe", "writeLong", "writeLongLe", "writeShort", "s", "writeShortLe", "writeString", "string", "beginIndex", "endIndex", "writeTo", "writeUtf8", "writeUtf8CodePoint", "codePoint", "UnsafeCursor", "okio"}, k=1, mv={1, 1, 16})
public final class Buffer
implements BufferedSource,
BufferedSink,
Cloneable,
ByteChannel {
    public Segment head;
    private long size;

    public static /* synthetic */ Buffer copyTo$default(Buffer buffer, OutputStream outputStream2, long l, long l2, int n, Object object) throws IOException {
        if ((n & 2) != 0) {
            l = 0L;
        }
        if ((n & 4) == 0) return buffer.copyTo(outputStream2, l, l2);
        l2 = buffer.size - l;
        return buffer.copyTo(outputStream2, l, l2);
    }

    public static /* synthetic */ Buffer copyTo$default(Buffer buffer, Buffer buffer2, long l, int n, Object object) {
        if ((n & 2) == 0) return buffer.copyTo(buffer2, l);
        l = 0L;
        return buffer.copyTo(buffer2, l);
    }

    public static /* synthetic */ Buffer copyTo$default(Buffer buffer, Buffer buffer2, long l, long l2, int n, Object object) {
        if ((n & 2) == 0) return buffer.copyTo(buffer2, l, l2);
        l = 0L;
        return buffer.copyTo(buffer2, l, l2);
    }

    private final ByteString digest(String object) {
        MessageDigest messageDigest = MessageDigest.getInstance((String)object);
        Segment segment = this.head;
        if (segment != null) {
            messageDigest.update(segment.data, segment.pos, segment.limit - segment.pos);
            Segment segment2 = segment.next;
            object = segment2;
            if (segment2 == null) {
                Intrinsics.throwNpe();
                object = segment2;
            }
            while (object != segment) {
                messageDigest.update(object.data, object.pos, object.limit - object.pos);
                segment2 = object.next;
                object = segment2;
                if (segment2 != null) continue;
                Intrinsics.throwNpe();
                object = segment2;
            }
        }
        object = messageDigest.digest();
        Intrinsics.checkExpressionValueIsNotNull(object, "messageDigest.digest()");
        return new ByteString((byte[])object);
    }

    /*
     * Exception decompiling
     */
    private final ByteString hmac(String var1_1, ByteString var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[WHILELOOP]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    public static /* synthetic */ UnsafeCursor readAndWriteUnsafe$default(Buffer buffer, UnsafeCursor unsafeCursor, int n, Object object) {
        if ((n & 1) == 0) return buffer.readAndWriteUnsafe(unsafeCursor);
        unsafeCursor = new UnsafeCursor();
        return buffer.readAndWriteUnsafe(unsafeCursor);
    }

    private final void readFrom(InputStream inputStream2, long l, boolean bl) throws IOException {
        do {
            if (l <= 0L) {
                if (!bl) return;
            }
            Segment segment = this.writableSegment$okio(1);
            int n = (int)Math.min(l, (long)(8192 - segment.limit));
            if ((n = inputStream2.read(segment.data, segment.limit, n)) == -1) {
                if (segment.pos == segment.limit) {
                    this.head = segment.pop();
                    SegmentPool.recycle(segment);
                }
                if (!bl) throw (Throwable)new EOFException();
                return;
            }
            segment.limit += n;
            long l2 = this.size;
            long l3 = n;
            this.size = l2 + l3;
            l -= l3;
        } while (true);
    }

    public static /* synthetic */ UnsafeCursor readUnsafe$default(Buffer buffer, UnsafeCursor unsafeCursor, int n, Object object) {
        if ((n & 1) == 0) return buffer.readUnsafe(unsafeCursor);
        unsafeCursor = new UnsafeCursor();
        return buffer.readUnsafe(unsafeCursor);
    }

    public static /* synthetic */ Buffer writeTo$default(Buffer buffer, OutputStream outputStream2, long l, int n, Object object) throws IOException {
        if ((n & 2) == 0) return buffer.writeTo(outputStream2, l);
        l = buffer.size;
        return buffer.writeTo(outputStream2, l);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to operator function", replaceWith=@ReplaceWith(expression="this[index]", imports={}))
    public final byte -deprecated_getByte(long l) {
        return this.getByte(l);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="size", imports={}))
    public final long -deprecated_size() {
        return this.size;
    }

    @Override
    public Buffer buffer() {
        return this;
    }

    public final void clear() {
        this.skip(this.size());
    }

    public Buffer clone() {
        return this.copy();
    }

    @Override
    public void close() {
    }

    public final long completeSegmentByteCount() {
        long l = this.size();
        long l2 = 0L;
        if (l == 0L) {
            return l2;
        }
        Segment segment = this.head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        if ((segment = segment.prev) == null) {
            Intrinsics.throwNpe();
        }
        l2 = l;
        if (segment.limit >= 8192) return l2;
        l2 = l;
        if (!segment.owner) return l2;
        return l - (long)(segment.limit - segment.pos);
    }

    public final Buffer copy() {
        Segment segment;
        Buffer buffer = new Buffer();
        if (this.size() == 0L) {
            return buffer;
        }
        Segment segment2 = this.head;
        if (segment2 == null) {
            Intrinsics.throwNpe();
        }
        buffer.head = segment = segment2.sharedCopy();
        segment.next = segment.prev = segment;
        Segment segment3 = segment2.next;
        do {
            if (segment3 == segment2) {
                buffer.setSize$okio(this.size());
                return buffer;
            }
            Segment segment4 = segment.prev;
            if (segment4 == null) {
                Intrinsics.throwNpe();
            }
            if (segment3 == null) {
                Intrinsics.throwNpe();
            }
            segment4.push(segment3.sharedCopy());
            segment3 = segment3.next;
        } while (true);
    }

    public final Buffer copyTo(OutputStream outputStream2) throws IOException {
        return Buffer.copyTo$default(this, outputStream2, 0L, 0L, 6, null);
    }

    public final Buffer copyTo(OutputStream outputStream2, long l) throws IOException {
        return Buffer.copyTo$default(this, outputStream2, l, 0L, 4, null);
    }

    public final Buffer copyTo(OutputStream outputStream2, long l, long l2) throws IOException {
        long l3;
        Segment segment;
        long l4;
        Intrinsics.checkParameterIsNotNull(outputStream2, "out");
        _Util.checkOffsetAndCount(this.size, l, l2);
        if (l2 == 0L) {
            return this;
        }
        Segment segment2 = this.head;
        do {
            if (segment2 == null) {
                Intrinsics.throwNpe();
            }
            segment = segment2;
            l3 = l;
            l4 = l2;
            if (l < (long)(segment2.limit - segment2.pos)) break;
            l -= (long)(segment2.limit - segment2.pos);
            segment2 = segment2.next;
        } while (true);
        while (l4 > 0L) {
            if (segment == null) {
                Intrinsics.throwNpe();
            }
            int n = (int)((long)segment.pos + l3);
            int n2 = (int)Math.min((long)(segment.limit - n), l4);
            outputStream2.write(segment.data, n, n2);
            l4 -= (long)n2;
            segment = segment.next;
            l3 = 0L;
        }
        return this;
    }

    public final Buffer copyTo(Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "out");
        return this.copyTo(buffer, l, this.size - l);
    }

    public final Buffer copyTo(Buffer buffer, long l, long l2) {
        long l3;
        Segment segment;
        long l4;
        Intrinsics.checkParameterIsNotNull(buffer, "out");
        _Util.checkOffsetAndCount(this.size(), l, l2);
        if (l2 == 0L) {
            return this;
        }
        buffer.setSize$okio(buffer.size() + l2);
        Segment segment2 = this.head;
        do {
            if (segment2 == null) {
                Intrinsics.throwNpe();
            }
            segment = segment2;
            l3 = l;
            l4 = l2;
            if (l < (long)(segment2.limit - segment2.pos)) break;
            l -= (long)(segment2.limit - segment2.pos);
            segment2 = segment2.next;
        } while (true);
        while (l4 > 0L) {
            if (segment == null) {
                Intrinsics.throwNpe();
            }
            segment2 = segment.sharedCopy();
            segment2.pos += (int)l3;
            segment2.limit = Math.min(segment2.pos + (int)l4, segment2.limit);
            Segment segment3 = buffer.head;
            if (segment3 == null) {
                buffer.head = segment2.next = (segment2.prev = segment2);
            } else {
                if (segment3 == null) {
                    Intrinsics.throwNpe();
                }
                if ((segment3 = segment3.prev) == null) {
                    Intrinsics.throwNpe();
                }
                segment3.push(segment2);
            }
            l4 -= (long)(segment2.limit - segment2.pos);
            segment = segment.next;
            l3 = 0L;
        }
        return this;
    }

    @Override
    public Buffer emit() {
        return this;
    }

    @Override
    public Buffer emitCompleteSegments() {
        return this;
    }

    /*
     * Unable to fully structure code
     */
    public boolean equals(Object var1_1) {
        var2_2 = false;
        if (this == var1_1) return true;
        if (!(var1_1 instanceof Buffer)) {
            return var2_2;
        }
        var3_3 = this.size();
        if (var3_3 != (var1_1 = (Buffer)var1_1).size()) {
            return var2_2;
        }
        if (this.size() == 0L) return true;
        var5_4 = this.head;
        if (var5_4 == null) {
            Intrinsics.throwNpe();
        }
        if ((var6_5 = var1_1.head) == null) {
            Intrinsics.throwNpe();
        }
        var7_6 = var5_4.pos;
        var8_7 = var6_5.pos;
        var3_3 = 0L;
lbl17: // 2 sources:
        do {
            if (var3_3 >= this.size()) return true;
            var9_8 = Math.min(var5_4.limit - var7_6, var6_5.limit - var8_7);
            var13_10 = var7_6;
            break;
        } while (true);
        for (var11_9 = 0L; var11_9 < var9_8; ++var11_9, ++var13_10, ++var8_7) {
            if (var5_4.data[var13_10] == var6_5.data[var8_7]) continue;
            return var2_2;
        }
        var1_1 = var5_4;
        var7_6 = var13_10;
        if (var13_10 == var5_4.limit) {
            var1_1 = var5_4.next;
            if (var1_1 == null) {
                Intrinsics.throwNpe();
            }
            var7_6 = var1_1.pos;
        }
        var5_4 = var6_5;
        var13_10 = var8_7;
        if (var8_7 == var6_5.limit) {
            var5_4 = var6_5.next;
            if (var5_4 == null) {
                Intrinsics.throwNpe();
            }
            var13_10 = var5_4.pos;
        }
        var3_3 += var9_8;
        var6_5 = var5_4;
        var5_4 = var1_1;
        var8_7 = var13_10;
        ** while (true)
    }

    @Override
    public boolean exhausted() {
        if (this.size != 0L) return false;
        return true;
    }

    @Override
    public void flush() {
    }

    @Override
    public Buffer getBuffer() {
        return this;
    }

    public final byte getByte(long l) {
        _Util.checkOffsetAndCount(this.size(), l, 1L);
        Segment segment = this.head;
        if (segment == null) {
            segment = null;
            Intrinsics.throwNpe();
            byte by = segment.data[(int)((long)segment.pos + l + 1L)];
            return by;
        }
        if (this.size() - l < l) {
            long l2;
            for (l2 = this.size(); l2 > l; l2 -= (long)(segment.limit - segment.pos)) {
                segment = segment.prev;
                if (segment != null) continue;
                Intrinsics.throwNpe();
            }
            byte by = segment.data[(int)((long)segment.pos + l - l2)];
            if (segment != null) return by;
            Intrinsics.throwNpe();
            return by;
        }
        long l3 = 0L;
        do {
            long l4;
            if ((l4 = (long)(segment.limit - segment.pos) + l3) > l) {
                byte by = segment.data[(int)((long)segment.pos + l - l3)];
                if (segment != null) return by;
                Intrinsics.throwNpe();
                return by;
            }
            segment = segment.next;
            if (segment == null) {
                Intrinsics.throwNpe();
            }
            l3 = l4;
        } while (true);
    }

    public int hashCode() {
        Segment segment;
        int n;
        Segment segment2 = this.head;
        if (segment2 == null) {
            return 0;
        }
        int n2 = 1;
        do {
            int n3 = segment2.limit;
            n = n2;
            for (int i = segment2.pos; i < n3; ++i) {
                n = n * 31 + segment2.data[i];
            }
            segment = segment2.next;
            if (segment == null) {
                Intrinsics.throwNpe();
            }
            segment2 = segment;
            n2 = n;
        } while (segment != this.head);
        return n;
    }

    public final ByteString hmacSha1(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "key");
        return this.hmac("HmacSHA1", byteString);
    }

    public final ByteString hmacSha256(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "key");
        return this.hmac("HmacSHA256", byteString);
    }

    public final ByteString hmacSha512(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "key");
        return this.hmac("HmacSHA512", byteString);
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
        long l3 = 0L;
        int n = 0L <= l && l2 >= l ? 1 : 0;
        if (n == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("size=");
            stringBuilder.append(this.size());
            stringBuilder.append(" fromIndex=");
            stringBuilder.append(l);
            stringBuilder.append(" toIndex=");
            stringBuilder.append(l2);
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        long l4 = l2;
        if (l2 > this.size()) {
            l4 = this.size();
        }
        long l5 = -1L;
        if (l == l4) {
            return l5;
        }
        byte[] arrby = this.head;
        if (arrby == null) {
            Segment segment = null;
            return l5;
        }
        l2 = l3;
        Object object = arrby;
        if (this.size() - l < l) {
            object = arrby;
            for (l2 = this.size(); l2 > l; l2 -= (long)(object.limit - object.pos)) {
                object = object.prev;
                if (object != null) continue;
                Intrinsics.throwNpe();
            }
            l3 = l5;
            if (object == null) return l3;
            long l6 = l;
            l = l2;
            do {
                l3 = l5;
                if (l >= l4) return l3;
                arrby = object.data;
                int n2 = (int)Math.min((long)object.limit, (long)object.pos + l4 - l);
                for (n = (int)((long)object.pos + l6 - l); n < n2; ++n) {
                    if (arrby[n] == by) return (long)(n - object.pos) + l;
                }
                l += (long)(object.limit - object.pos);
                object = object.next;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                l6 = l;
            } while (true);
        }
        do {
            if ((l3 = (long)(object.limit - object.pos) + l2) > l) break;
            object = object.next;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            l2 = l3;
        } while (true);
        l3 = l5;
        if (object == null) return l3;
        block4 : do {
            l3 = l5;
            if (l2 >= l4) return l3;
            arrby = object.data;
            int n3 = (int)Math.min((long)object.limit, (long)object.pos + l4 - l2);
            for (n = (int)((long)object.pos + l - l2); n < n3; ++n) {
                if (arrby[n] == by) break block4;
            }
            l2 += (long)(object.limit - object.pos);
            object = object.next;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            l = l2;
        } while (true);
        l = l2;
        return (long)(n - object.pos) + l;
    }

    @Override
    public long indexOf(ByteString byteString) throws IOException {
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        return this.indexOf(byteString, 0L);
    }

    @Override
    public long indexOf(ByteString arrby, long l) throws IOException {
        Intrinsics.checkParameterIsNotNull(arrby, "bytes");
        int n = arrby.size() > 0 ? 1 : 0;
        if (n == 0) throw (Throwable)new IllegalArgumentException("bytes is empty".toString());
        long l2 = 0L;
        n = l >= 0L ? 1 : 0;
        if (n == 0) {
            arrby = new StringBuilder();
            arrby.append("fromIndex < 0: ");
            arrby.append(l);
            throw (Throwable)new IllegalArgumentException(arrby.toString().toString());
        }
        byte[] arrby2 = this.head;
        if (arrby2 != null) {
            long l3;
            Object object = arrby2;
            if (this.size() - l < l) {
                object = arrby2;
                for (l2 = this.size(); l2 > l; l2 -= (long)(object.limit - object.pos)) {
                    object = object.prev;
                    if (object != null) continue;
                    Intrinsics.throwNpe();
                }
                if (object == null) return -1L;
                arrby2 = arrby.internalArray$okio();
                byte by = arrby2[0];
                int n2 = arrby.size();
                long l4 = this.size() - (long)n2 + 1L;
                while (l2 < l4) {
                    arrby = object.data;
                    n = object.limit;
                    long l5 = object.pos;
                    int n3 = (int)Math.min((long)n, l5 + l4 - l2);
                    for (n = (int)((long)object.pos + l - l2); n < n3; ++n) {
                        if (arrby[n] != by || !BufferKt.rangeEquals((Segment)object, n + 1, arrby2, 1, n2)) continue;
                        return (long)(n - object.pos) + l2;
                    }
                    l2 += (long)(object.limit - object.pos);
                    object = object.next;
                    if (object == null) {
                        Intrinsics.throwNpe();
                    }
                    l = l2;
                }
                return -1L;
            }
            do {
                if ((l3 = (long)(object.limit - object.pos) + l2) > l) break;
                object = object.next;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                l2 = l3;
            } while (true);
            if (object == null) return -1L;
            arrby2 = arrby.internalArray$okio();
            byte by = arrby2[0];
            int n4 = arrby.size();
            l3 = this.size() - (long)n4 + 1L;
            while (l2 < l3) {
                arrby = object.data;
                n = object.limit;
                long l6 = object.pos;
                int n5 = (int)Math.min((long)n, l6 + l3 - l2);
                for (n = (int)((long)object.pos + l - l2); n < n5; ++n) {
                    if (arrby[n] != by || !BufferKt.rangeEquals((Segment)object, n + 1, arrby2, 1, n4)) continue;
                    return (long)(n - object.pos) + l2;
                }
                l2 += (long)(object.limit - object.pos);
                object = object.next;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                l = l2;
            }
            return -1L;
        }
        arrby = null;
        return -1L;
    }

    @Override
    public long indexOfElement(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "targetBytes");
        return this.indexOfElement(byteString, 0L);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public long indexOfElement(ByteString var1_1, long var2_2) {
        block29 : {
            Intrinsics.checkParameterIsNotNull(var1_1, "targetBytes");
            var4_3 = 0L;
            var6_4 = var2_2 >= 0L ? 1 : 0;
            if (var6_4 == 0) {
                var1_1 = new StringBuilder();
                var1_1.append("fromIndex < 0: ");
                var1_1.append(var2_2);
                throw (Throwable)new IllegalArgumentException(var1_1.toString().toString());
            }
            var7_5 = this.head;
            var8_6 = -1L;
            if (var7_5 == null) {
                var1_1 = null;
                return var8_6;
            }
            var10_7 = var7_5;
            if (this.size() - var2_2 < var2_2) {
                var10_8 = var7_5;
                for (var4_3 = this.size(); var4_3 > var2_2; var4_3 -= (long)(var10_10.limit - var10_10.pos)) {
                    var10_10 = var10_9.prev;
                    if (var10_10 != null) continue;
                    Intrinsics.throwNpe();
                }
                var11_22 = var8_6;
                if (var10_9 == null) return var11_22;
                if (var1_1.size() == 2) {
                    var13_23 = var1_1.getByte(0);
                    var14_27 = var1_1.getByte(1);
                    block1 : do {
                        var11_22 = var8_6;
                        if (var4_3 >= this.size()) return var11_22;
                        var7_5 = var10_11.data;
                        var15_31 = (int)((long)var10_11.pos + var2_2 - var4_3);
                        var16_32 = var10_11.limit;
                        while (var15_31 < var16_32) {
                            var17_36 = var7_5[var15_31];
                            var2_2 = var4_3;
                            var1_1 = var10_11;
                            var6_4 = var15_31++;
                            if (var17_36 == var13_23) break block1;
                            if (var17_36 != var14_27) continue;
                            var2_2 = var4_3;
                            var1_1 = var10_11;
                            var6_4 = var15_31;
                            break block1;
                        }
                        var4_3 += (long)(var10_11.limit - var10_11.pos);
                        var10_12 = var10_11.next;
                        if (var10_12 == null) {
                            Intrinsics.throwNpe();
                        }
                        var2_2 = var4_3;
                    } while (true);
lbl52: // 4 sources:
                    do {
                        var15_31 = var1_1.pos;
                        do {
                            return (long)(var6_4 - var15_31) + var2_2;
                            break;
                        } while (true);
                        break;
                    } while (true);
                }
                var1_1 = var1_1.internalArray$okio();
                block5 : do {
                    var11_22 = var8_6;
                    if (var4_3 >= this.size()) return var11_22;
                    var7_5 = var10_13.data;
                    var6_4 = (int)((long)var10_13.pos + var2_2 - var4_3);
                    var14_28 = var10_13.limit;
                    do {
                        if (var6_4 < var14_28) {
                            var13_24 = var7_5[var6_4];
                            var16_33 = ((Object)var1_1).length;
                        } else {
                            var4_3 += (long)(var10_13.limit - var10_13.pos);
                            var10_15 = var10_13.next;
                            if (var10_15 == null) {
                                Intrinsics.throwNpe();
                            }
                            var2_2 = var4_3;
                            continue block5;
                        }
                        for (var15_31 = 0; var15_31 < var16_33; ++var15_31) {
                            if (var13_24 == var1_1[var15_31]) break block5;
                        }
                        ++var6_4;
                    } while (true);
                    break;
                } while (true);
                do {
                    var15_31 = var10_14.pos;
                    var2_2 = var4_3;
                    return (long)(var6_4 - var15_31) + var2_2;
                    break;
                } while (true);
            }
            do {
                if ((var11_22 = (long)(var10_16.limit - var10_16.pos) + var4_3) > var2_2) {
                    var11_22 = var8_6;
                    if (var10_16 == null) return var11_22;
                    if (var1_1.size() == 2) break;
                    break block29;
                }
                var10_21 = var10_16.next;
                if (var10_21 == null) {
                    Intrinsics.throwNpe();
                }
                var4_3 = var11_22;
            } while (true);
            var14_29 = var1_1.getByte(0);
            var13_25 = var1_1.getByte(1);
            do {
                var11_22 = var8_6;
                if (var4_3 >= this.size()) return var11_22;
                var7_5 = var10_17.data;
                var15_31 = (int)((long)var10_17.pos + var2_2 - var4_3);
                var16_34 = var10_17.limit;
                while (var15_31 < var16_34) {
                    var17_37 = var7_5[var15_31];
                    var2_2 = var4_3;
                    var1_1 = var10_17;
                    var6_4 = var15_31++;
                    if (var17_37 == var14_29) ** GOTO lbl52
                    if (var17_37 != var13_25) continue;
                    var2_2 = var4_3;
                    var1_1 = var10_17;
                    var6_4 = var15_31;
                    ** continue;
lbl113: // 1 sources:
                    ** GOTO lbl52
                }
                var4_3 += (long)(var10_17.limit - var10_17.pos);
                var10_18 = var10_17.next;
                if (var10_18 == null) {
                    Intrinsics.throwNpe();
                }
                var2_2 = var4_3;
            } while (true);
        }
        var1_1 = var1_1.internalArray$okio();
        block12 : do {
            var11_22 = var8_6;
            if (var4_3 >= this.size()) return var11_22;
            var7_5 = var10_19.data;
            var6_4 = (int)((long)var10_19.pos + var2_2 - var4_3);
            var14_30 = var10_19.limit;
            do {
                if (var6_4 < var14_30) {
                    var13_26 = var7_5[var6_4];
                    var16_35 = ((Object)var1_1).length;
                } else {
                    var4_3 += (long)(var10_19.limit - var10_19.pos);
                    var10_20 = var10_19.next;
                    if (var10_20 == null) {
                        Intrinsics.throwNpe();
                    }
                    var2_2 = var4_3;
                    continue block12;
                }
                for (var15_31 = 0; var15_31 < var16_35; ++var15_31) {
                    if (var13_26 == var1_1[var15_31]) ** continue;
                }
                ++var6_4;
            } while (true);
            break;
        } while (true);
    }

    @Override
    public InputStream inputStream() {
        return new InputStream(this){
            final /* synthetic */ Buffer this$0;
            {
                this.this$0 = buffer;
            }

            public int available() {
                return (int)Math.min(this.this$0.size(), (long)Integer.MAX_VALUE);
            }

            public void close() {
            }

            public int read() {
                if (this.this$0.size() <= 0L) return -1;
                return this.this$0.readByte() & 255;
            }

            public int read(byte[] arrby, int n, int n2) {
                Intrinsics.checkParameterIsNotNull(arrby, "sink");
                return this.this$0.read(arrby, n, n2);
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
        return true;
    }

    public final ByteString md5() {
        return this.digest("MD5");
    }

    @Override
    public OutputStream outputStream() {
        return new OutputStream(this){
            final /* synthetic */ Buffer this$0;
            {
                this.this$0 = buffer;
            }

            public void close() {
            }

            public void flush() {
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.this$0);
                stringBuilder.append(".outputStream()");
                return stringBuilder.toString();
            }

            public void write(int n) {
                this.this$0.writeByte(n);
            }

            public void write(byte[] arrby, int n, int n2) {
                Intrinsics.checkParameterIsNotNull(arrby, "data");
                this.this$0.write(arrby, n, n2);
            }
        };
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
        boolean bl;
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        boolean bl2 = bl = false;
        if (l < 0L) return bl2;
        bl2 = bl;
        if (n < 0) return bl2;
        bl2 = bl;
        if (n2 < 0) return bl2;
        bl2 = bl;
        if (this.size() - l < (long)n2) return bl2;
        if (byteString.size() - n < n2) {
            return bl;
        }
        int n3 = 0;
        while (n3 < n2) {
            if (this.getByte((long)n3 + l) != byteString.getByte(n + n3)) {
                return bl;
            }
            ++n3;
        }
        return true;
    }

    @Override
    public int read(ByteBuffer byteBuffer) throws IOException {
        Intrinsics.checkParameterIsNotNull(byteBuffer, "sink");
        Segment segment = this.head;
        if (segment == null) return -1;
        int n = Math.min(byteBuffer.remaining(), segment.limit - segment.pos);
        byteBuffer.put(segment.data, segment.pos, n);
        segment.pos += n;
        this.size -= (long)n;
        if (segment.pos != segment.limit) return n;
        this.head = segment.pop();
        SegmentPool.recycle(segment);
        return n;
    }

    @Override
    public int read(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "sink");
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(arrby, "sink");
        _Util.checkOffsetAndCount(arrby.length, n, n2);
        Segment segment = this.head;
        if (segment == null) return -1;
        n2 = Math.min(n2, segment.limit - segment.pos);
        ArraysKt.copyInto(segment.data, arrby, n, segment.pos, segment.pos + n2);
        segment.pos += n2;
        this.setSize$okio(this.size() - (long)n2);
        n = n2;
        if (segment.pos != segment.limit) return n;
        this.head = segment.pop();
        SegmentPool.recycle(segment);
        return n2;
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
        if (this.size() == 0L) {
            return -1L;
        }
        long l2 = l;
        if (l > this.size()) {
            l2 = this.size();
        }
        ((Buffer)object).write(this, l2);
        return l2;
    }

    @Override
    public long readAll(Sink sink2) throws IOException {
        Intrinsics.checkParameterIsNotNull(sink2, "sink");
        long l = this.size();
        if (l <= 0L) return l;
        sink2.write(this, l);
        return l;
    }

    public final UnsafeCursor readAndWriteUnsafe() {
        return Buffer.readAndWriteUnsafe$default(this, null, 1, null);
    }

    public final UnsafeCursor readAndWriteUnsafe(UnsafeCursor unsafeCursor) {
        Intrinsics.checkParameterIsNotNull(unsafeCursor, "unsafeCursor");
        boolean bl = unsafeCursor.buffer == null;
        if (!bl) throw (Throwable)new IllegalStateException("already attached to a buffer".toString());
        unsafeCursor.buffer = this;
        unsafeCursor.readWrite = true;
        return unsafeCursor;
    }

    @Override
    public byte readByte() throws EOFException {
        if (this.size() == 0L) throw (Throwable)new EOFException();
        Segment segment = this.head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        int n = segment.pos;
        int n2 = segment.limit;
        byte[] arrby = segment.data;
        int n3 = n + 1;
        byte by = arrby[n];
        this.setSize$okio(this.size() - 1L);
        if (n3 == n2) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            return by;
        }
        segment.pos = n3;
        return by;
    }

    @Override
    public byte[] readByteArray() {
        return this.readByteArray(this.size());
    }

    @Override
    public byte[] readByteArray(long l) throws EOFException {
        boolean bl = l >= 0L && l <= (long)Integer.MAX_VALUE;
        if (bl) {
            if (this.size() < l) throw (Throwable)new EOFException();
            byte[] arrby = new byte[(int)l];
            this.readFully(arrby);
            return arrby;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("byteCount: ");
        stringBuilder.append(l);
        throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
    }

    @Override
    public ByteString readByteString() {
        return this.readByteString(this.size());
    }

    @Override
    public ByteString readByteString(long l) throws EOFException {
        boolean bl = l >= 0L && l <= (long)Integer.MAX_VALUE;
        if (!bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("byteCount: ");
            stringBuilder.append(l);
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        if (this.size() < l) throw (Throwable)new EOFException();
        if (l < (long)4096) return new ByteString(this.readByteArray(l));
        ByteString byteString = this.snapshot((int)l);
        this.skip(l);
        return byteString;
    }

    @Override
    public long readDecimalLong() throws EOFException {
        Object object;
        byte by;
        long l;
        byte by2;
        Object object2;
        block10 : {
            int n;
            l = this.size();
            long l2 = 0L;
            if (l == 0L) throw (Throwable)new EOFException();
            long l3 = -7L;
            int n2 = 0;
            byte by3 = 0;
            boolean bl = false;
            do {
                int n3;
                if ((object2 = this.head) == null) {
                    Intrinsics.throwNpe();
                }
                object = ((Segment)object2).data;
                int n4 = ((Segment)object2).limit;
                by2 = by3;
                n = n2;
                l = l2;
                l2 = l3;
                for (n3 = object2.pos; n3 < n4; ++n3, ++n) {
                    by = object[n3];
                    by3 = (byte)48;
                    if (by >= by3 && by <= (byte)57) {
                        n2 = by3 - by;
                        by3 = (byte)(l LCMP -922337203685477580L);
                        if (by3 >= 0 && (by3 != 0 || (long)n2 >= l2)) {
                            l = l * 10L + (long)n2;
                            continue;
                        }
                        break block10;
                    }
                    if (by == (byte)45 && n == 0) {
                        --l2;
                        by2 = 1;
                        continue;
                    }
                    if (n == 0) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Expected leading [0-9] or '-' character but was 0x");
                        ((StringBuilder)object2).append(_Util.toHexString(by));
                        throw (Throwable)new NumberFormatException(((StringBuilder)object2).toString());
                    }
                    bl = true;
                    break;
                }
                if (n3 == n4) {
                    this.head = ((Segment)object2).pop();
                    SegmentPool.recycle((Segment)object2);
                } else {
                    ((Segment)object2).pos = n3;
                }
                if (bl) break;
                l3 = l2;
                l2 = l;
                n2 = n;
                by3 = by2;
            } while (this.head != null);
            this.setSize$okio(this.size() - (long)n);
            if (by2 == 0) return -l;
            return l;
        }
        object = new Buffer().writeDecimalLong(l).writeByte(by);
        if (by2 == 0) {
            ((Buffer)object).readByte();
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Number too large: ");
        ((StringBuilder)object2).append(((Buffer)object).readUtf8());
        throw (Throwable)new NumberFormatException(((StringBuilder)object2).toString());
    }

    public final Buffer readFrom(InputStream inputStream2) throws IOException {
        Intrinsics.checkParameterIsNotNull(inputStream2, "input");
        this.readFrom(inputStream2, Long.MAX_VALUE, true);
        return this;
    }

    public final Buffer readFrom(InputStream object, long l) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "input");
        boolean bl = l >= 0L;
        if (bl) {
            this.readFrom((InputStream)object, l, false);
            return this;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("byteCount < 0: ");
        ((StringBuilder)object).append(l);
        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
    }

    @Override
    public void readFully(Buffer buffer, long l) throws EOFException {
        Intrinsics.checkParameterIsNotNull(buffer, "sink");
        if (this.size() >= l) {
            buffer.write(this, l);
            return;
        }
        buffer.write(this, this.size());
        throw (Throwable)new EOFException();
    }

    @Override
    public void readFully(byte[] arrby) throws EOFException {
        Intrinsics.checkParameterIsNotNull(arrby, "sink");
        int n = 0;
        while (n < arrby.length) {
            int n2 = this.read(arrby, n, arrby.length - n);
            if (n2 == -1) throw (Throwable)new EOFException();
            n += n2;
        }
    }

    @Override
    public long readHexadecimalUnsignedLong() throws EOFException {
        int n;
        long l;
        if (this.size() == 0L) throw (Throwable)new EOFException();
        int n2 = 0;
        long l2 = 0L;
        boolean bl = false;
        do {
            int n3;
            Object object;
            int n4;
            boolean bl2;
            block9 : {
                byte by;
                if ((object = this.head) == null) {
                    Intrinsics.throwNpe();
                }
                Object object2 = ((Segment)object).data;
                n3 = ((Segment)object).pos;
                n4 = ((Segment)object).limit;
                l = l2;
                n = n2;
                do {
                    bl2 = bl;
                    if (n3 >= n4) break block9;
                    by = object2[n3];
                    n2 = 48;
                    if (by >= n2 && by <= (byte)57) {
                        n2 = by - n2;
                    } else {
                        n2 = (byte)97;
                        if ((by < n2 || by > (byte)102) && (by < (n2 = (byte)65) || by > (byte)70)) break;
                        n2 = by - n2 + 10;
                    }
                    if ((-1152921504606846976L & l) != 0L) {
                        object2 = new Buffer().writeHexadecimalUnsignedLong(l).writeByte(by);
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Number too large: ");
                        ((StringBuilder)object).append(((Buffer)object2).readUtf8());
                        throw (Throwable)new NumberFormatException(((StringBuilder)object).toString());
                    }
                    l = l << 4 | (long)n2;
                    ++n3;
                    ++n;
                } while (true);
                if (n == 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Expected leading [0-9a-fA-F] character but was 0x");
                    ((StringBuilder)object).append(_Util.toHexString(by));
                    throw (Throwable)new NumberFormatException(((StringBuilder)object).toString());
                }
                bl2 = true;
            }
            if (n3 == n4) {
                this.head = ((Segment)object).pop();
                SegmentPool.recycle((Segment)object);
            } else {
                ((Segment)object).pos = n3;
            }
            if (bl2) break;
            n2 = n;
            bl = bl2;
            l2 = l;
        } while (this.head != null);
        this.setSize$okio(this.size() - (long)n);
        return l;
    }

    @Override
    public int readInt() throws EOFException {
        int n;
        int n2;
        if (this.size() < 4L) throw (Throwable)new EOFException();
        Segment segment = this.head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        if ((long)((n2 = segment.limit) - (n = segment.pos)) < 4L) {
            return (this.readByte() & 255) << 24 | (this.readByte() & 255) << 16 | (this.readByte() & 255) << 8 | this.readByte() & 255;
        }
        byte[] arrby = segment.data;
        int n3 = n + 1;
        n = arrby[n];
        int n4 = n3 + 1;
        n3 = arrby[n3];
        int n5 = n4 + 1;
        n4 = arrby[n4];
        int n6 = n5 + 1;
        n5 = arrby[n5];
        this.setSize$okio(this.size() - 4L);
        if (n6 == n2) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            return (n & 255) << 24 | (n3 & 255) << 16 | (n4 & 255) << 8 | n5 & 255;
        } else {
            segment.pos = n6;
        }
        return (n & 255) << 24 | (n3 & 255) << 16 | (n4 & 255) << 8 | n5 & 255;
    }

    @Override
    public int readIntLe() throws EOFException {
        return _Util.reverseBytes(this.readInt());
    }

    @Override
    public long readLong() throws EOFException {
        int n;
        int n2;
        if (this.size() < 8L) throw (Throwable)new EOFException();
        Segment segment = this.head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        if ((long)((n2 = segment.limit) - (n = segment.pos)) < 8L) {
            return ((long)this.readInt() & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL & (long)this.readInt();
        }
        byte[] arrby = segment.data;
        int n3 = n + 1;
        long l = arrby[n];
        n = n3 + 1;
        long l2 = arrby[n3];
        n3 = n + 1;
        long l3 = arrby[n];
        n = n3 + 1;
        long l4 = arrby[n3];
        n3 = n + 1;
        long l5 = arrby[n];
        n = n3 + 1;
        long l6 = arrby[n3];
        n3 = n + 1;
        long l7 = arrby[n];
        n = n3 + 1;
        long l8 = arrby[n3];
        this.setSize$okio(this.size() - 8L);
        if (n == n2) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            return (l4 & 255L) << 32 | ((l & 255L) << 56 | (l2 & 255L) << 48 | (l3 & 255L) << 40) | (l5 & 255L) << 24 | (l6 & 255L) << 16 | (l7 & 255L) << 8 | l8 & 255L;
        } else {
            segment.pos = n;
        }
        return (l4 & 255L) << 32 | ((l & 255L) << 56 | (l2 & 255L) << 48 | (l3 & 255L) << 40) | (l5 & 255L) << 24 | (l6 & 255L) << 16 | (l7 & 255L) << 8 | l8 & 255L;
    }

    @Override
    public long readLongLe() throws EOFException {
        return _Util.reverseBytes(this.readLong());
    }

    @Override
    public short readShort() throws EOFException {
        short s;
        int n;
        if (this.size() < 2L) throw (Throwable)new EOFException();
        Segment segment = this.head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        if ((s = segment.limit) - (n = segment.pos) < 2) {
            s = (short)((this.readByte() & 255) << 8 | this.readByte() & 255);
            return s;
        }
        byte[] arrby = segment.data;
        int n2 = n + 1;
        n = arrby[n];
        int n3 = n2 + 1;
        n2 = arrby[n2];
        this.setSize$okio(this.size() - 2L);
        if (n3 == s) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            s = (short)((n & 255) << 8 | n2 & 255);
            return s;
        } else {
            segment.pos = n3;
        }
        s = (short)((n & 255) << 8 | n2 & 255);
        return s;
    }

    @Override
    public short readShortLe() throws EOFException {
        return _Util.reverseBytes(this.readShort());
    }

    @Override
    public String readString(long l, Charset object) throws EOFException {
        Intrinsics.checkParameterIsNotNull(object, "charset");
        long l2 = l LCMP 0L;
        int n = l2 >= 0 && l <= (long)Integer.MAX_VALUE ? 1 : 0;
        if (n == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        if (this.size < l) throw (Throwable)new EOFException();
        if (l2 == false) {
            return "";
        }
        Segment segment = this.head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        if ((long)segment.pos + l > (long)segment.limit) {
            return new String(this.readByteArray(l), (Charset)object);
        }
        byte[] arrby = segment.data;
        l2 = segment.pos;
        n = (int)l;
        object = new String(arrby, (int)l2, n, (Charset)object);
        segment.pos += n;
        this.size -= l;
        if (segment.pos != segment.limit) return object;
        this.head = segment.pop();
        SegmentPool.recycle(segment);
        return object;
    }

    @Override
    public String readString(Charset charset) {
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        return this.readString(this.size, charset);
    }

    public final UnsafeCursor readUnsafe() {
        return Buffer.readUnsafe$default(this, null, 1, null);
    }

    public final UnsafeCursor readUnsafe(UnsafeCursor unsafeCursor) {
        Intrinsics.checkParameterIsNotNull(unsafeCursor, "unsafeCursor");
        boolean bl = unsafeCursor.buffer == null;
        if (!bl) throw (Throwable)new IllegalStateException("already attached to a buffer".toString());
        unsafeCursor.buffer = this;
        unsafeCursor.readWrite = false;
        return unsafeCursor;
    }

    @Override
    public String readUtf8() {
        return this.readString(this.size, Charsets.UTF_8);
    }

    @Override
    public String readUtf8(long l) throws EOFException {
        return this.readString(l, Charsets.UTF_8);
    }

    @Override
    public int readUtf8CodePoint() throws EOFException {
        int n;
        int n2;
        int n3;
        if (this.size() == 0L) throw (Throwable)new EOFException();
        byte by = this.getByte(0L);
        int n4 = 1;
        int n5 = 65533;
        if ((by & 128) == 0) {
            n = by & 127;
            n3 = 1;
            n2 = 0;
        } else if ((by & 224) == 192) {
            n = by & 31;
            n3 = 2;
            n2 = 128;
        } else if ((by & 240) == 224) {
            n = by & 15;
            n3 = 3;
            n2 = 2048;
        } else {
            if ((by & 248) != 240) {
                this.skip(1L);
                return n5;
            }
            n = by & 7;
            n3 = 4;
            n2 = 65536;
        }
        long l = this.size();
        long l2 = n3;
        if (l < l2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("size < ");
            stringBuilder.append(n3);
            stringBuilder.append(": ");
            stringBuilder.append(this.size());
            stringBuilder.append(" (to read code point prefixed 0x");
            stringBuilder.append(_Util.toHexString(by));
            stringBuilder.append(')');
            throw (Throwable)new EOFException(stringBuilder.toString());
        }
        while (n4 < n3) {
            l = n4;
            byte by2 = this.getByte(l);
            if ((by2 & 192) != 128) {
                this.skip(l);
                return n5;
            }
            n = n << 6 | by2 & 63;
            ++n4;
        }
        this.skip(l2);
        if (n > 1114111) {
            return n5;
        }
        if (55296 <= n && 57343 >= n) {
            return n5;
        }
        if (n >= n2) return n;
        return n5;
    }

    @Override
    public String readUtf8Line() throws EOFException {
        long l = this.indexOf((byte)10);
        if (l != -1L) {
            return BufferKt.readUtf8Line(this, l);
        }
        if (this.size() == 0L) return null;
        return this.readUtf8(this.size());
    }

    @Override
    public String readUtf8LineStrict() throws EOFException {
        return this.readUtf8LineStrict(Long.MAX_VALUE);
    }

    @Override
    public String readUtf8LineStrict(long l) throws EOFException {
        boolean bl = l >= 0L;
        if (!bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("limit < 0: ");
            stringBuilder.append(l);
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        long l2 = Long.MAX_VALUE;
        if (l != Long.MAX_VALUE) {
            l2 = l + 1L;
        }
        byte by = (byte)10;
        long l3 = this.indexOf(by, 0L, l2);
        if (l3 != -1L) {
            return BufferKt.readUtf8Line(this, l3);
        }
        if (l2 < this.size() && this.getByte(l2 - 1L) == (byte)13 && this.getByte(l2) == by) {
            return BufferKt.readUtf8Line(this, l2);
        }
        Buffer buffer = new Buffer();
        l2 = this.size();
        this.copyTo(buffer, 0L, Math.min((long)32, l2));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\n not found: limit=");
        stringBuilder.append(Math.min(this.size(), l));
        stringBuilder.append(" content=");
        stringBuilder.append(buffer.readByteString().hex());
        stringBuilder.append('\u2026');
        throw (Throwable)new EOFException(stringBuilder.toString());
    }

    @Override
    public boolean request(long l) {
        if (this.size < l) return false;
        return true;
    }

    @Override
    public void require(long l) throws EOFException {
        if (this.size < l) throw (Throwable)new EOFException();
    }

    @Override
    public int select(Options options) {
        Intrinsics.checkParameterIsNotNull(options, "options");
        int n = BufferKt.selectPrefix$default(this, options, false, 2, null);
        if (n == -1) {
            return -1;
        }
        this.skip(options.getByteStrings$okio()[n].size());
        return n;
    }

    public final void setSize$okio(long l) {
        this.size = l;
    }

    public final ByteString sha1() {
        return this.digest("SHA-1");
    }

    public final ByteString sha256() {
        return this.digest("SHA-256");
    }

    public final ByteString sha512() {
        return this.digest("SHA-512");
    }

    public final long size() {
        return this.size;
    }

    @Override
    public void skip(long l) throws EOFException {
        while (l > 0L) {
            Segment segment = this.head;
            if (segment == null) throw (Throwable)new EOFException();
            int n = (int)Math.min(l, (long)(segment.limit - segment.pos));
            long l2 = this.size();
            long l3 = n;
            this.setSize$okio(l2 - l3);
            l3 = l - l3;
            segment.pos += n;
            l = l3;
            if (segment.pos != segment.limit) continue;
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            l = l3;
        }
    }

    public final ByteString snapshot() {
        boolean bl = this.size() <= (long)Integer.MAX_VALUE;
        if (bl) {
            return this.snapshot((int)this.size());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size > Int.MAX_VALUE: ");
        stringBuilder.append(this.size());
        throw (Throwable)new IllegalStateException(stringBuilder.toString().toString());
    }

    public final ByteString snapshot(int n) {
        int n2;
        if (n == 0) {
            return ByteString.EMPTY;
        }
        _Util.checkOffsetAndCount(this.size(), 0L, n);
        Object object = this.head;
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < n; n2 += object.limit - object.pos, ++n4) {
            if (object == null) {
                Intrinsics.throwNpe();
            }
            if (((Segment)object).limit == ((Segment)object).pos) throw (Throwable)((Object)new AssertionError((Object)"s.limit == s.pos"));
            object = ((Segment)object).next;
        }
        byte[][] arrarrby = new byte[n4][];
        int[] arrn = new int[n4 * 2];
        object = this.head;
        n4 = 0;
        n2 = n3;
        while (n2 < n) {
            if (object == null) {
                Intrinsics.throwNpe();
            }
            arrarrby[n4] = ((Segment)object).data;
            arrn[n4] = Math.min(n2 += ((Segment)object).limit - ((Segment)object).pos, n);
            arrn[((Object[])arrarrby).length + n4] = ((Segment)object).pos;
            ((Segment)object).shared = true;
            ++n4;
            object = ((Segment)object).next;
        }
        return new SegmentedByteString(arrarrby, arrn);
    }

    @Override
    public Timeout timeout() {
        return Timeout.NONE;
    }

    public String toString() {
        return this.snapshot().toString();
    }

    public final Segment writableSegment$okio(int n) {
        boolean bl = true;
        if (n < 1 || n > 8192) {
            bl = false;
        }
        if (!bl) throw (Throwable)new IllegalArgumentException("unexpected capacity".toString());
        Segment segment = this.head;
        if (segment == null) {
            this.head = segment = SegmentPool.take();
            segment.prev = segment;
            segment.next = segment;
            return segment;
        }
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        if ((segment = segment.prev) == null) {
            Intrinsics.throwNpe();
        }
        if (segment.limit + n > 8192) return segment.push(SegmentPool.take());
        if (segment.owner) return segment;
        return segment.push(SegmentPool.take());
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        int n;
        Intrinsics.checkParameterIsNotNull(byteBuffer, "source");
        int n2 = n = byteBuffer.remaining();
        do {
            if (n2 <= 0) {
                this.size += (long)n;
                return n;
            }
            Segment segment = this.writableSegment$okio(1);
            int n3 = Math.min(n2, 8192 - segment.limit);
            byteBuffer.get(segment.data, segment.limit, n3);
            n2 -= n3;
            segment.limit += n3;
        } while (true);
    }

    @Override
    public Buffer write(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        byteString.write$okio(this, 0, byteString.size());
        return this;
    }

    @Override
    public Buffer write(ByteString byteString, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        byteString.write$okio(this, n, n2);
        return this;
    }

    @Override
    public Buffer write(Source source2, long l) throws IOException {
        Intrinsics.checkParameterIsNotNull(source2, "source");
        while (l > 0L) {
            long l2 = source2.read(this, l);
            if (l2 == -1L) throw (Throwable)new EOFException();
            l -= l2;
        }
        return this;
    }

    @Override
    public Buffer write(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "source");
        return this.write(arrby, 0, arrby.length);
    }

    @Override
    public Buffer write(byte[] arrby, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(arrby, "source");
        long l = arrby.length;
        long l2 = n;
        long l3 = n2;
        _Util.checkOffsetAndCount(l, l2, l3);
        int n3 = n2 + n;
        do {
            if (n >= n3) {
                this.setSize$okio(this.size() + l3);
                return this;
            }
            Segment segment = this.writableSegment$okio(1);
            int n4 = Math.min(n3 - n, 8192 - segment.limit);
            byte[] arrby2 = segment.data;
            int n5 = segment.limit;
            n2 = n + n4;
            ArraysKt.copyInto(arrby, arrby2, n5, n, n2);
            segment.limit += n4;
            n = n2;
        } while (true);
    }

    @Override
    public void write(Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "source");
        int n = buffer != this ? 1 : 0;
        if (n == 0) throw (Throwable)new IllegalArgumentException("source == this".toString());
        _Util.checkOffsetAndCount(buffer.size(), 0L, l);
        while (l > 0L) {
            Segment segment;
            long l2;
            Segment segment2 = buffer.head;
            if (segment2 == null) {
                Intrinsics.throwNpe();
            }
            n = segment2.limit;
            segment2 = buffer.head;
            if (segment2 == null) {
                Intrinsics.throwNpe();
            }
            if (l < (long)(n - segment2.pos)) {
                segment2 = this.head;
                if (segment2 != null) {
                    if (segment2 == null) {
                        Intrinsics.throwNpe();
                    }
                    segment2 = segment2.prev;
                } else {
                    segment2 = null;
                }
                if (segment2 != null && segment2.owner && (l2 = (long)segment2.limit) + l - (long)(n = segment2.shared ? 0 : segment2.pos) <= (long)8192) {
                    segment = buffer.head;
                    if (segment == null) {
                        Intrinsics.throwNpe();
                    }
                    segment.writeTo(segment2, (int)l);
                    buffer.setSize$okio(buffer.size() - l);
                    this.setSize$okio(this.size() + l);
                    return;
                }
                segment2 = buffer.head;
                if (segment2 == null) {
                    Intrinsics.throwNpe();
                }
                buffer.head = segment2.split((int)l);
            }
            if ((segment2 = buffer.head) == null) {
                Intrinsics.throwNpe();
            }
            l2 = segment2.limit - segment2.pos;
            buffer.head = segment2.pop();
            segment = this.head;
            if (segment == null) {
                this.head = segment2;
                segment2.next = segment2.prev = segment2;
            } else {
                if (segment == null) {
                    Intrinsics.throwNpe();
                }
                if ((segment = segment.prev) == null) {
                    Intrinsics.throwNpe();
                }
                segment.push(segment2).compact();
            }
            buffer.setSize$okio(buffer.size() - l2);
            this.setSize$okio(this.size() + l2);
            l -= l2;
        }
    }

    @Override
    public long writeAll(Source source2) throws IOException {
        Intrinsics.checkParameterIsNotNull(source2, "source");
        long l = 0L;
        long l2;
        while ((l2 = source2.read(this, 8192)) != -1L) {
            l += l2;
        }
        return l;
    }

    @Override
    public Buffer writeByte(int n) {
        Segment segment = this.writableSegment$okio(1);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        segment.limit = n2 + 1;
        arrby[n2] = (byte)n;
        this.setSize$okio(this.size() + 1L);
        return this;
    }

    @Override
    public Buffer writeDecimalLong(long l) {
        long l2 = l LCMP 0L;
        if (l2 == false) {
            return this.writeByte(48);
        }
        boolean bl = false;
        int n = 1;
        long l3 = l;
        if (l2 < 0) {
            l3 = -l;
            if (l3 < 0L) {
                return this.writeUtf8("-9223372036854775808");
            }
            bl = true;
        }
        if (l3 < 100000000L) {
            if (l3 < 10000L) {
                if (l3 < 100L) {
                    if (l3 >= 10L) {
                        n = 2;
                    }
                } else {
                    n = l3 < 1000L ? 3 : 4;
                }
            } else {
                n = l3 < 1000000L ? (l3 < 100000L ? 5 : 6) : (l3 < 10000000L ? 7 : 8);
            }
        } else {
            n = l3 < 1000000000000L ? (l3 < 10000000000L ? (l3 < 1000000000L ? 9 : 10) : (l3 < 100000000000L ? 11 : 12)) : (l3 < 1000000000000000L ? (l3 < 10000000000000L ? 13 : (l3 < 100000000000000L ? 14 : 15)) : (l3 < 100000000000000000L ? (l3 < 10000000000000000L ? 16 : 17) : (l3 < 1000000000000000000L ? 18 : 19)));
        }
        l2 = n;
        if (bl) {
            l2 = n + 1;
        }
        Segment segment = this.writableSegment$okio((int)l2);
        byte[] arrby = segment.data;
        n = segment.limit + l2;
        while (l3 != 0L) {
            l = 10;
            int n2 = (int)(l3 % l);
            arrby[--n] = BufferKt.getHEX_DIGIT_BYTES()[n2];
            l3 /= l;
        }
        if (bl) {
            arrby[n - 1] = (byte)45;
        }
        segment.limit += l2;
        this.setSize$okio(this.size() + (long)l2);
        return this;
    }

    @Override
    public Buffer writeHexadecimalUnsignedLong(long l) {
        if (l == 0L) {
            return this.writeByte(48);
        }
        long l2 = l >>> 1 | l;
        l2 |= l2 >>> 2;
        l2 |= l2 >>> 4;
        l2 |= l2 >>> 8;
        l2 |= l2 >>> 16;
        l2 |= l2 >>> 32;
        l2 -= l2 >>> 1 & 0x5555555555555555L;
        l2 = (l2 >>> 2 & 0x3333333333333333L) + (l2 & 0x3333333333333333L);
        l2 = (l2 >>> 4) + l2 & 0xF0F0F0F0F0F0F0FL;
        l2 += l2 >>> 8;
        l2 += l2 >>> 16;
        int n = (int)(((l2 & 63L) + (l2 >>> 32 & 63L) + (long)3) / (long)4);
        Segment segment = this.writableSegment$okio(n);
        Object object = segment.data;
        int n2 = segment.limit + n - 1;
        int n3 = segment.limit;
        do {
            if (n2 < n3) {
                segment.limit += n;
                this.setSize$okio(this.size() + (long)n);
                return this;
            }
            object[n2] = BufferKt.getHEX_DIGIT_BYTES()[(int)(15L & l)];
            l >>>= 4;
            --n2;
        } while (true);
    }

    @Override
    public Buffer writeInt(int n) {
        Segment segment = this.writableSegment$okio(4);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        int n3 = n2 + 1;
        arrby[n2] = (byte)(n >>> 24 & 255);
        n2 = n3 + 1;
        arrby[n3] = (byte)(n >>> 16 & 255);
        n3 = n2 + 1;
        arrby[n2] = (byte)(n >>> 8 & 255);
        arrby[n3] = (byte)(n & 255);
        segment.limit = n3 + 1;
        this.setSize$okio(this.size() + 4L);
        return this;
    }

    @Override
    public Buffer writeIntLe(int n) {
        return this.writeInt(_Util.reverseBytes(n));
    }

    @Override
    public Buffer writeLong(long l) {
        Segment segment = this.writableSegment$okio(8);
        byte[] arrby = segment.data;
        int n = segment.limit;
        int n2 = n + 1;
        arrby[n] = (byte)(l >>> 56 & 255L);
        n = n2 + 1;
        arrby[n2] = (byte)(l >>> 48 & 255L);
        n2 = n + 1;
        arrby[n] = (byte)(l >>> 40 & 255L);
        int n3 = n2 + 1;
        arrby[n2] = (byte)(l >>> 32 & 255L);
        n = n3 + 1;
        arrby[n3] = (byte)(l >>> 24 & 255L);
        n2 = n + 1;
        arrby[n] = (byte)(l >>> 16 & 255L);
        n = n2 + 1;
        arrby[n2] = (byte)(l >>> 8 & 255L);
        arrby[n] = (byte)(l & 255L);
        segment.limit = n + 1;
        this.setSize$okio(this.size() + 8L);
        return this;
    }

    @Override
    public Buffer writeLongLe(long l) {
        return this.writeLong(_Util.reverseBytes(l));
    }

    @Override
    public Buffer writeShort(int n) {
        Segment segment = this.writableSegment$okio(2);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        int n3 = n2 + 1;
        arrby[n2] = (byte)(n >>> 8 & 255);
        arrby[n3] = (byte)(n & 255);
        segment.limit = n3 + 1;
        this.setSize$okio(this.size() + 2L);
        return this;
    }

    @Override
    public Buffer writeShortLe(int n) {
        return this.writeShort(_Util.reverseBytes((short)n));
    }

    @Override
    public Buffer writeString(String charSequence, int n, int n2, Charset comparable) {
        Intrinsics.checkParameterIsNotNull(charSequence, "string");
        Intrinsics.checkParameterIsNotNull(comparable, "charset");
        boolean bl = true;
        boolean bl2 = n >= 0;
        if (!bl2) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("beginIndex < 0: ");
            ((StringBuilder)charSequence).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
        }
        bl2 = n2 >= n;
        if (!bl2) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("endIndex < beginIndex: ");
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append(" < ");
            ((StringBuilder)charSequence).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
        }
        bl2 = n2 <= ((String)charSequence).length() ? bl : false;
        if (!bl2) {
            comparable = new StringBuilder();
            ((StringBuilder)comparable).append("endIndex > string.length: ");
            ((StringBuilder)comparable).append(n2);
            ((StringBuilder)comparable).append(" > ");
            ((StringBuilder)comparable).append(((String)charSequence).length());
            throw (Throwable)new IllegalArgumentException(((StringBuilder)comparable).toString().toString());
        }
        if (Intrinsics.areEqual(comparable, Charsets.UTF_8)) {
            return this.writeUtf8((String)charSequence, n, n2);
        }
        charSequence = ((String)charSequence).substring(n, n2);
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        if (charSequence == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        charSequence = ((String)charSequence).getBytes((Charset)comparable);
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "(this as java.lang.String).getBytes(charset)");
        return this.write((byte[])charSequence, 0, ((CharSequence)charSequence).length);
    }

    @Override
    public Buffer writeString(String string2, Charset charset) {
        Intrinsics.checkParameterIsNotNull(string2, "string");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        return this.writeString(string2, 0, string2.length(), charset);
    }

    public final Buffer writeTo(OutputStream outputStream2) throws IOException {
        return Buffer.writeTo$default(this, outputStream2, 0L, 2, null);
    }

    public final Buffer writeTo(OutputStream outputStream2, long l) throws IOException {
        Intrinsics.checkParameterIsNotNull(outputStream2, "out");
        _Util.checkOffsetAndCount(this.size, 0L, l);
        Segment segment = this.head;
        while (l > 0L) {
            Segment segment2;
            if (segment == null) {
                Intrinsics.throwNpe();
            }
            int n = (int)Math.min(l, (long)(segment.limit - segment.pos));
            outputStream2.write(segment.data, segment.pos, n);
            segment.pos += n;
            long l2 = this.size;
            long l3 = n;
            this.size = l2 - l3;
            l = l3 = l - l3;
            if (segment.pos != segment.limit) continue;
            this.head = segment2 = segment.pop();
            SegmentPool.recycle(segment);
            segment = segment2;
            l = l3;
        }
        return this;
    }

    @Override
    public Buffer writeUtf8(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "string");
        return this.writeUtf8(string2, 0, string2.length());
    }

    @Override
    public Buffer writeUtf8(String charSequence, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "string");
        int n3 = n >= 0 ? 1 : 0;
        if (n3 == 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("beginIndex < 0: ");
            ((StringBuilder)charSequence).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
        }
        n3 = n2 >= n ? 1 : 0;
        if (n3 == 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("endIndex < beginIndex: ");
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append(" < ");
            ((StringBuilder)charSequence).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
        }
        n3 = n2 <= ((String)charSequence).length() ? 1 : 0;
        if (n3 == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("endIndex > string.length: ");
            stringBuilder.append(n2);
            stringBuilder.append(" > ");
            stringBuilder.append(((String)charSequence).length());
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        while (n < n2) {
            int n4;
            Segment segment;
            Object object;
            int n5;
            char c = ((String)charSequence).charAt(n);
            if (c < '') {
                segment = this.writableSegment$okio(1);
                object = segment.data;
                n4 = segment.limit - n;
                n5 = Math.min(n2, 8192 - n4);
                n3 = n + 1;
                object[n + n4] = (byte)c;
                n = n3;
            } else {
                if (c < '\u0800') {
                    object = this.writableSegment$okio(2);
                    object.data[object.limit] = (byte)(c >> 6 | 192);
                    object.data[object.limit + 1] = (byte)(c & 63 | 128);
                    object.limit += 2;
                    this.setSize$okio(this.size() + 2L);
                } else {
                    if (c >= '\ud800' && c <= '\udfff') {
                        n5 = n + 1;
                        n3 = n5 < n2 ? ((String)charSequence).charAt(n5) : 0;
                        if (c <= '\udbff' && 56320 <= n3 && 57343 >= n3) {
                            n3 = ((c & 1023) << 10 | n3 & 1023) + 65536;
                            object = this.writableSegment$okio(4);
                            object.data[object.limit] = (byte)(n3 >> 18 | 240);
                            object.data[object.limit + 1] = (byte)(n3 >> 12 & 63 | 128);
                            object.data[object.limit + 2] = (byte)(n3 >> 6 & 63 | 128);
                            object.data[object.limit + 3] = (byte)(n3 & 63 | 128);
                            object.limit += 4;
                            this.setSize$okio(this.size() + 4L);
                            n += 2;
                            continue;
                        }
                        this.writeByte(63);
                        n = n5;
                        continue;
                    }
                    object = this.writableSegment$okio(3);
                    object.data[object.limit] = (byte)(c >> 12 | 224);
                    object.data[object.limit + 1] = (byte)(63 & c >> 6 | 128);
                    object.data[object.limit + 2] = (byte)(c & 63 | 128);
                    object.limit += 3;
                    this.setSize$okio(this.size() + 3L);
                }
                ++n;
                continue;
            }
            while (n < n5 && (n3 = (int)((String)charSequence).charAt(n)) < 128) {
                object[n + n4] = (byte)n3;
                ++n;
            }
            n3 = n4 + n - segment.limit;
            segment.limit += n3;
            this.setSize$okio(this.size() + (long)n3);
        }
        return this;
    }

    @Override
    public Buffer writeUtf8CodePoint(int n) {
        if (n < 128) {
            this.writeByte(n);
            return this;
        }
        if (n < 2048) {
            Segment segment = this.writableSegment$okio(2);
            segment.data[segment.limit] = (byte)(n >> 6 | 192);
            segment.data[segment.limit + 1] = (byte)(n & 63 | 128);
            segment.limit += 2;
            this.setSize$okio(this.size() + 2L);
            return this;
        }
        if (55296 <= n && 57343 >= n) {
            this.writeByte(63);
            return this;
        }
        if (n < 65536) {
            Segment segment = this.writableSegment$okio(3);
            segment.data[segment.limit] = (byte)(n >> 12 | 224);
            segment.data[segment.limit + 1] = (byte)(n >> 6 & 63 | 128);
            segment.data[segment.limit + 2] = (byte)(n & 63 | 128);
            segment.limit += 3;
            this.setSize$okio(this.size() + 3L);
            return this;
        }
        if (n <= 1114111) {
            Segment segment = this.writableSegment$okio(4);
            segment.data[segment.limit] = (byte)(n >> 18 | 240);
            segment.data[segment.limit + 1] = (byte)(n >> 12 & 63 | 128);
            segment.data[segment.limit + 2] = (byte)(n >> 6 & 63 | 128);
            segment.data[segment.limit + 3] = (byte)(n & 63 | 128);
            segment.limit += 4;
            this.setSize$okio(this.size() + 4L);
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected code point: 0x");
        stringBuilder.append(_Util.toHexString(n));
        throw (Throwable)new IllegalArgumentException(stringBuilder.toString());
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u000e\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\bJ\u0006\u0010\u0014\u001a\u00020\bJ\u000e\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\nJ\u000e\u0010\u0017\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u00020\b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lokio/Buffer$UnsafeCursor;", "Ljava/io/Closeable;", "()V", "buffer", "Lokio/Buffer;", "data", "", "end", "", "offset", "", "readWrite", "", "segment", "Lokio/Segment;", "start", "close", "", "expandBuffer", "minByteCount", "next", "resizeBuffer", "newSize", "seek", "okio"}, k=1, mv={1, 1, 16})
    public static final class UnsafeCursor
    implements Closeable {
        public Buffer buffer;
        public byte[] data;
        public int end = -1;
        public long offset = -1L;
        public boolean readWrite;
        private Segment segment;
        public int start = -1;

        @Override
        public void close() {
            boolean bl = this.buffer != null;
            if (!bl) throw (Throwable)new IllegalStateException("not attached to a buffer".toString());
            this.buffer = null;
            this.segment = null;
            this.offset = -1L;
            this.data = null;
            this.start = -1;
            this.end = -1;
        }

        public final long expandBuffer(int n) {
            boolean bl = true;
            boolean bl2 = n > 0;
            if (!bl2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("minByteCount <= 0: ");
                stringBuilder.append(n);
                throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
            }
            bl2 = n <= 8192 ? bl : false;
            if (bl2) {
                Buffer buffer = this.buffer;
                if (buffer == null) throw (Throwable)new IllegalStateException("not attached to a buffer".toString());
                if (!this.readWrite) throw (Throwable)new IllegalStateException("expandBuffer() only permitted for read/write buffers".toString());
                long l = buffer.size();
                Segment segment = buffer.writableSegment$okio(n);
                n = 8192 - segment.limit;
                segment.limit = 8192;
                long l2 = n;
                buffer.setSize$okio(l + l2);
                this.segment = segment;
                this.offset = l;
                this.data = segment.data;
                this.start = 8192 - n;
                this.end = 8192;
                return l2;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("minByteCount > Segment.SIZE: ");
            stringBuilder.append(n);
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }

        public final int next() {
            long l = this.offset;
            Buffer buffer = this.buffer;
            if (buffer == null) {
                Intrinsics.throwNpe();
            }
            boolean bl = l != buffer.size();
            if (!bl) throw (Throwable)new IllegalStateException("no more bytes".toString());
            l = this.offset;
            if (l == -1L) {
                l = 0L;
                return this.seek(l);
            }
            l += (long)(this.end - this.start);
            return this.seek(l);
        }

        public final long resizeBuffer(long l) {
            Object object = this.buffer;
            if (object == null) throw (Throwable)new IllegalStateException("not attached to a buffer".toString());
            if (!this.readWrite) throw (Throwable)new IllegalStateException("resizeBuffer() only permitted for read/write buffers".toString());
            long l2 = ((Buffer)object).size();
            long l3 = l LCMP l2;
            if (l3 <= 0) {
                long l4;
                l3 = l >= 0L ? (long)1 : (long)0;
                if (l3 == false) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("newSize < 0: ");
                    ((StringBuilder)object).append(l);
                    throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
                }
                for (long i = l2 - l; i > 0L; i -= l4) {
                    Segment segment = ((Buffer)object).head;
                    if (segment == null) {
                        Intrinsics.throwNpe();
                    }
                    if ((segment = segment.prev) == null) {
                        Intrinsics.throwNpe();
                    }
                    if ((l4 = (long)(segment.limit - segment.pos)) <= i) {
                        ((Buffer)object).head = segment.pop();
                        SegmentPool.recycle(segment);
                        continue;
                    }
                    segment.limit -= (int)i;
                    break;
                }
                this.segment = null;
                this.offset = l;
                this.data = null;
                this.start = -1;
                this.end = -1;
            } else if (l3 > 0) {
                long l5 = l - l2;
                int n = 1;
                while (l5 > 0L) {
                    Segment segment = ((Buffer)object).writableSegment$okio(1);
                    int n2 = (int)Math.min(l5, (long)(8192 - segment.limit));
                    segment.limit += n2;
                    l5 -= (long)n2;
                    l3 = n;
                    if (n != 0) {
                        this.segment = segment;
                        this.offset = l2;
                        this.data = segment.data;
                        this.start = segment.limit - n2;
                        this.end = segment.limit;
                        l3 = 0;
                    }
                    n = (int)l3;
                }
            }
            ((Buffer)object).setSize$okio(l);
            return l2;
        }

        public final int seek(long l) {
            Buffer buffer = this.buffer;
            if (buffer == null) throw (Throwable)new IllegalStateException("not attached to a buffer".toString());
            if (l >= (long)-1 && l <= buffer.size()) {
                if (l != -1L && l != buffer.size()) {
                    int n;
                    long l2 = 0L;
                    long l3 = buffer.size();
                    Segment segment = buffer.head;
                    Segment segment2 = buffer.head;
                    Segment segment3 = this.segment;
                    long l4 = l2;
                    long l5 = l3;
                    Segment segment4 = segment;
                    Segment segment5 = segment2;
                    if (segment3 != null) {
                        l4 = this.offset;
                        n = this.start;
                        if (segment3 == null) {
                            Intrinsics.throwNpe();
                        }
                        if ((l4 -= (long)(n - segment3.pos)) > l) {
                            segment5 = this.segment;
                            l5 = l4;
                            l4 = l2;
                            segment4 = segment;
                        } else {
                            segment4 = this.segment;
                            segment5 = segment2;
                            l5 = l3;
                        }
                    }
                    if (l5 - l > l - l4) {
                        segment5 = segment4;
                        do {
                            if (segment5 == null) {
                                Intrinsics.throwNpe();
                            }
                            l5 = l4;
                            segment4 = segment5;
                            if (l >= (long)(segment5.limit - segment5.pos) + l4) {
                                l4 += (long)(segment5.limit - segment5.pos);
                                segment5 = segment5.next;
                                continue;
                            }
                            break;
                        } while (true);
                    } else {
                        for (l3 = l5; l3 > l; l3 -= (long)(segment5.limit - segment5.pos)) {
                            if (segment5 == null) {
                                Intrinsics.throwNpe();
                            }
                            if ((segment5 = segment5.prev) != null) continue;
                            Intrinsics.throwNpe();
                        }
                        l5 = l3;
                        segment4 = segment5;
                    }
                    segment5 = segment4;
                    if (this.readWrite) {
                        if (segment4 == null) {
                            Intrinsics.throwNpe();
                        }
                        segment5 = segment4;
                        if (segment4.shared) {
                            segment5 = segment4.unsharedCopy();
                            if (buffer.head == segment4) {
                                buffer.head = segment5;
                            }
                            segment5 = segment4.push(segment5);
                            segment4 = segment5.prev;
                            if (segment4 == null) {
                                Intrinsics.throwNpe();
                            }
                            segment4.pop();
                        }
                    }
                    this.segment = segment5;
                    this.offset = l;
                    if (segment5 == null) {
                        Intrinsics.throwNpe();
                    }
                    this.data = segment5.data;
                    this.start = segment5.pos + (int)(l - l5);
                    this.end = n = segment5.limit;
                    return n - this.start;
                }
                this.segment = null;
                this.offset = l;
                this.data = null;
                this.start = -1;
                this.end = -1;
                return -1;
            }
            Object object = StringCompanionObject.INSTANCE;
            object = String.format("offset=%s > size=%s", Arrays.copyOf(new Object[]{l, buffer.size()}, 2));
            Intrinsics.checkExpressionValueIsNotNull(object, "java.lang.String.format(format, *args)");
            throw (Throwable)new ArrayIndexOutOfBoundsException((String)object);
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio.internal;

import java.io.EOFException;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Options;
import okio.Segment;
import okio.SegmentPool;
import okio.SegmentedByteString;
import okio.Sink;
import okio.Source;
import okio._Platform;
import okio._Util;
import okio.internal._Utf8Kt;

@Metadata(bv={1, 0, 3}, d1={"\u0000v\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a0\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0000\u001a\r\u0010\u0011\u001a\u00020\u0012*\u00020\u0013H\b\u001a\r\u0010\u0014\u001a\u00020\u0005*\u00020\u0013H\b\u001a\r\u0010\u0015\u001a\u00020\u0013*\u00020\u0013H\b\u001a%\u0010\u0016\u001a\u00020\u0013*\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0005H\b\u001a\u0017\u0010\u001a\u001a\u00020\n*\u00020\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\b\u001a\u0015\u0010\u001d\u001a\u00020\u001e*\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u0005H\b\u001a\r\u0010 \u001a\u00020\b*\u00020\u0013H\b\u001a%\u0010!\u001a\u00020\u0005*\u00020\u00132\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\b\u001a\u001d\u0010!\u001a\u00020\u0005*\u00020\u00132\u0006\u0010\u000e\u001a\u00020%2\u0006\u0010#\u001a\u00020\u0005H\b\u001a\u001d\u0010&\u001a\u00020\u0005*\u00020\u00132\u0006\u0010'\u001a\u00020%2\u0006\u0010#\u001a\u00020\u0005H\b\u001a-\u0010(\u001a\u00020\n*\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020%2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\b\u001a\u0015\u0010)\u001a\u00020\b*\u00020\u00132\u0006\u0010*\u001a\u00020\u0001H\b\u001a%\u0010)\u001a\u00020\b*\u00020\u00132\u0006\u0010*\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\b\u001a\u001d\u0010)\u001a\u00020\u0005*\u00020\u00132\u0006\u0010*\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\b\u001a\u0015\u0010+\u001a\u00020\u0005*\u00020\u00132\u0006\u0010*\u001a\u00020,H\b\u001a\r\u0010-\u001a\u00020\u001e*\u00020\u0013H\b\u001a\r\u0010.\u001a\u00020\u0001*\u00020\u0013H\b\u001a\u0015\u0010.\u001a\u00020\u0001*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\b\u001a\r\u0010/\u001a\u00020%*\u00020\u0013H\b\u001a\u0015\u0010/\u001a\u00020%*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\b\u001a\r\u00100\u001a\u00020\u0005*\u00020\u0013H\b\u001a\u0015\u00101\u001a\u00020\u0012*\u00020\u00132\u0006\u0010*\u001a\u00020\u0001H\b\u001a\u001d\u00101\u001a\u00020\u0012*\u00020\u00132\u0006\u0010*\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\b\u001a\r\u00102\u001a\u00020\u0005*\u00020\u0013H\b\u001a\r\u00103\u001a\u00020\b*\u00020\u0013H\b\u001a\r\u00104\u001a\u00020\u0005*\u00020\u0013H\b\u001a\r\u00105\u001a\u000206*\u00020\u0013H\b\u001a\u0015\u00107\u001a\u000208*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\b\u001a\r\u00109\u001a\u00020\b*\u00020\u0013H\b\u001a\u000f\u0010:\u001a\u0004\u0018\u000108*\u00020\u0013H\b\u001a\u0015\u0010;\u001a\u000208*\u00020\u00132\u0006\u0010<\u001a\u00020\u0005H\b\u001a\u0015\u0010=\u001a\u00020\b*\u00020\u00132\u0006\u0010>\u001a\u00020?H\b\u001a\u0015\u0010@\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\b\u001a\r\u0010A\u001a\u00020%*\u00020\u0013H\b\u001a\u0015\u0010A\u001a\u00020%*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\bH\b\u001a\u0015\u0010B\u001a\u00020\f*\u00020\u00132\u0006\u0010C\u001a\u00020\bH\b\u001a\u0015\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020\u0001H\b\u001a%\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\b\u001a\u001d\u0010D\u001a\u00020\u0012*\u00020\u00132\u0006\u0010E\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\b\u001a)\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010F\u001a\u00020%2\b\b\u0002\u0010\u0018\u001a\u00020\b2\b\b\u0002\u0010\u0019\u001a\u00020\bH\b\u001a\u001d\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020G2\u0006\u0010\u0019\u001a\u00020\u0005H\b\u001a\u0015\u0010H\u001a\u00020\u0005*\u00020\u00132\u0006\u0010E\u001a\u00020GH\b\u001a\u0015\u0010I\u001a\u00020\u0013*\u00020\u00132\u0006\u0010\"\u001a\u00020\bH\b\u001a\u0015\u0010J\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\b\u001a\u0015\u0010L\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\b\u001a\u0015\u0010M\u001a\u00020\u0013*\u00020\u00132\u0006\u0010N\u001a\u00020\bH\b\u001a\u0015\u0010O\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\b\u001a\u0015\u0010P\u001a\u00020\u0013*\u00020\u00132\u0006\u0010Q\u001a\u00020\bH\b\u001a%\u0010R\u001a\u00020\u0013*\u00020\u00132\u0006\u0010S\u001a\u0002082\u0006\u0010T\u001a\u00020\b2\u0006\u0010U\u001a\u00020\bH\b\u001a\u0015\u0010V\u001a\u00020\u0013*\u00020\u00132\u0006\u0010W\u001a\u00020\bH\b\u001a\u0014\u0010X\u001a\u000208*\u00020\u00132\u0006\u0010Y\u001a\u00020\u0005H\u0000\u001a<\u0010Z\u001a\u0002H[\"\u0004\b\u0000\u0010[*\u00020\u00132\u0006\u0010#\u001a\u00020\u00052\u001a\u0010\\\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H[0]H\b\u00a2\u0006\u0002\u0010^\u001a\u001e\u0010_\u001a\u00020\b*\u00020\u00132\u0006\u0010>\u001a\u00020?2\b\b\u0002\u0010`\u001a\u00020\nH\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005XT\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0005XT\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\bXT\u00a2\u0006\u0002\n\u0000\u00a8\u0006a"}, d2={"HEX_DIGIT_BYTES", "", "getHEX_DIGIT_BYTES", "()[B", "OVERFLOW_DIGIT_START", "", "OVERFLOW_ZONE", "SEGMENTING_THRESHOLD", "", "rangeEquals", "", "segment", "Lokio/Segment;", "segmentPos", "bytes", "bytesOffset", "bytesLimit", "commonClear", "", "Lokio/Buffer;", "commonCompleteSegmentByteCount", "commonCopy", "commonCopyTo", "out", "offset", "byteCount", "commonEquals", "other", "", "commonGet", "", "pos", "commonHashCode", "commonIndexOf", "b", "fromIndex", "toIndex", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonRangeEquals", "commonRead", "sink", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadLong", "commonReadShort", "", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonSnapshot", "commonWritableSegment", "minimumCapacity", "commonWrite", "source", "byteString", "Lokio/Source;", "commonWriteAll", "commonWriteByte", "commonWriteDecimalLong", "v", "commonWriteHexadecimalUnsignedLong", "commonWriteInt", "i", "commonWriteLong", "commonWriteShort", "s", "commonWriteUtf8", "string", "beginIndex", "endIndex", "commonWriteUtf8CodePoint", "codePoint", "readUtf8Line", "newline", "seek", "T", "lambda", "Lkotlin/Function2;", "(Lokio/Buffer;JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "selectPrefix", "selectTruncated", "okio"}, k=2, mv={1, 1, 16})
public final class BufferKt {
    private static final byte[] HEX_DIGIT_BYTES = _Platform.asUtf8ToByteArray("0123456789abcdef");
    public static final long OVERFLOW_DIGIT_START = -7L;
    public static final long OVERFLOW_ZONE = -922337203685477580L;
    public static final int SEGMENTING_THRESHOLD = 4096;

    public static final void commonClear(Buffer buffer) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonClear");
        buffer.skip(buffer.size());
    }

    public static final long commonCompleteSegmentByteCount(Buffer object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonCompleteSegmentByteCount");
        long l = ((Buffer)object).size();
        if (l == 0L) {
            return 0L;
        }
        object = ((Buffer)object).head;
        if (object == null) {
            Intrinsics.throwNpe();
        }
        if ((object = ((Segment)object).prev) == null) {
            Intrinsics.throwNpe();
        }
        long l2 = l;
        if (((Segment)object).limit >= 8192) return l2;
        l2 = l;
        if (!((Segment)object).owner) return l2;
        return l - (long)(((Segment)object).limit - ((Segment)object).pos);
    }

    public static final Buffer commonCopy(Buffer buffer) {
        Segment segment;
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonCopy");
        Buffer buffer2 = new Buffer();
        if (buffer.size() == 0L) {
            return buffer2;
        }
        Segment segment2 = buffer.head;
        if (segment2 == null) {
            Intrinsics.throwNpe();
        }
        segment.next = segment.prev = (buffer2.head = (segment = segment2.sharedCopy()));
        Segment segment3 = segment2.next;
        do {
            if (segment3 == segment2) {
                buffer2.setSize$okio(buffer.size());
                return buffer2;
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

    public static final Buffer commonCopyTo(Buffer buffer, Buffer buffer2, long l, long l2) {
        long l3;
        Segment segment;
        long l4;
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonCopyTo");
        Intrinsics.checkParameterIsNotNull(buffer2, "out");
        _Util.checkOffsetAndCount(buffer.size(), l, l2);
        if (l2 == 0L) {
            return buffer;
        }
        buffer2.setSize$okio(buffer2.size() + l2);
        Segment segment2 = buffer.head;
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
            if (buffer2.head == null) {
                buffer2.head = segment2.next = (segment2.prev = segment2);
            } else {
                Segment segment3 = buffer2.head;
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
        return buffer;
    }

    public static final boolean commonEquals(Buffer buffer, Object object) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonEquals");
        if (buffer == object) {
            return true;
        }
        if (!(object instanceof Buffer)) {
            return false;
        }
        long l = buffer.size();
        if (l != ((Buffer)(object = (Buffer)object)).size()) {
            return false;
        }
        if (buffer.size() == 0L) {
            return true;
        }
        Object object2 = buffer.head;
        if (object2 == null) {
            Intrinsics.throwNpe();
        }
        if ((object = ((Buffer)object).head) == null) {
            Intrinsics.throwNpe();
        }
        int n = ((Segment)object2).pos;
        int n2 = ((Segment)object).pos;
        l = 0L;
        while (l < buffer.size()) {
            long l2 = Math.min(((Segment)object2).limit - n, ((Segment)object).limit - n2);
            int n3 = n;
            for (long i = 0L; i < l2; ++i, ++n3, ++n2) {
                if (((Segment)object2).data[n3] == ((Segment)object).data[n2]) continue;
                return false;
            }
            Segment segment = object2;
            n = n3;
            if (n3 == ((Segment)object2).limit) {
                segment = ((Segment)object2).next;
                if (segment == null) {
                    Intrinsics.throwNpe();
                }
                n = segment.pos;
            }
            object2 = object;
            n3 = n2;
            if (n2 == ((Segment)object).limit) {
                object2 = ((Segment)object).next;
                if (object2 == null) {
                    Intrinsics.throwNpe();
                }
                n3 = ((Segment)object2).pos;
            }
            l += l2;
            object = object2;
            object2 = segment;
            n2 = n3;
        }
        return true;
    }

    public static final byte commonGet(Buffer object, long l) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonGet");
        _Util.checkOffsetAndCount(((Buffer)object).size(), l, 1L);
        Segment segment = ((Buffer)object).head;
        if (segment == null) {
            object = null;
            Intrinsics.throwNpe();
            return ((Segment)object).data[(int)((long)((Segment)object).pos + l + 1L)];
        }
        if (((Buffer)object).size() - l < l) {
            long l2 = ((Buffer)object).size();
            do {
                if (l2 <= l) {
                    if (segment != null) return segment.data[(int)((long)segment.pos + l - l2)];
                    Intrinsics.throwNpe();
                    return segment.data[(int)((long)segment.pos + l - l2)];
                }
                segment = segment.prev;
                if (segment == null) {
                    Intrinsics.throwNpe();
                }
                l2 -= (long)(segment.limit - segment.pos);
            } while (true);
        }
        long l3 = 0L;
        do {
            long l4;
            if ((l4 = (long)(segment.limit - segment.pos) + l3) > l) {
                if (segment != null) return segment.data[(int)((long)segment.pos + l - l3)];
                Intrinsics.throwNpe();
                return segment.data[(int)((long)segment.pos + l - l3)];
            }
            segment = segment.next;
            if (segment == null) {
                Intrinsics.throwNpe();
            }
            l3 = l4;
        } while (true);
    }

    public static final int commonHashCode(Buffer buffer) {
        Segment segment;
        int n;
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonHashCode");
        Segment segment2 = buffer.head;
        if (segment2 == null) return 0;
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
        } while (segment != buffer.head);
        return n;
    }

    public static final long commonIndexOf(Buffer object, byte by, long l, long l2) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonIndexOf");
        long l3 = 0L;
        int n = 0L <= l && l2 >= l ? 1 : 0;
        if (n == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("size=");
            stringBuilder.append(object.size());
            stringBuilder.append(" fromIndex=");
            stringBuilder.append(l);
            stringBuilder.append(" toIndex=");
            stringBuilder.append(l2);
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        long l4 = l2;
        if (l2 > object.size()) {
            l4 = object.size();
        }
        if (l == l4) {
            return -1L;
        }
        byte[] arrby = object.head;
        if (arrby == null) {
            object = null;
            return -1L;
        }
        l2 = l3;
        Object object2 = arrby;
        if (object.size() - l < l) {
            l2 = object.size();
            object = arrby;
            while (l2 > l) {
                object = object.prev;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                l2 -= (long)(object.limit - object.pos);
            }
            if (object == null) return -1L;
            l3 = l;
            l = l2;
            while (l < l4) {
                object2 = object.data;
                int n2 = (int)Math.min((long)object.limit, (long)object.pos + l4 - l);
                for (n = (int)((long)object.pos + l3 - l); n < n2; ++n) {
                    if (object2[n] != by) continue;
                    return (long)(n - object.pos) + l;
                }
                l += (long)(object.limit - object.pos);
                object = object.next;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                l3 = l;
            }
            return -1L;
        }
        do {
            if ((l3 = (long)(object2.limit - object2.pos) + l2) > l) break;
            object2 = object2.next;
            if (object2 == null) {
                Intrinsics.throwNpe();
            }
            l2 = l3;
        } while (true);
        if (object2 == null) return -1L;
        while (l2 < l4) {
            object = object2.data;
            int n3 = (int)Math.min((long)object2.limit, (long)object2.pos + l4 - l2);
            for (n = (int)((long)object2.pos + l - l2); n < n3; ++n) {
                if (object[n] != by) continue;
                l = l2;
                object = object2;
                return (long)(n - object.pos) + l;
            }
            l2 += (long)(object2.limit - object2.pos);
            object2 = object2.next;
            if (object2 == null) {
                Intrinsics.throwNpe();
            }
            l = l2;
        }
        return -1L;
    }

    public static final long commonIndexOf(Buffer arrby, ByteString byteString, long l) {
        long l2;
        Intrinsics.checkParameterIsNotNull(arrby, "$this$commonIndexOf");
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        int n = byteString.size() > 0 ? 1 : 0;
        if (n == 0) throw (Throwable)new IllegalArgumentException("bytes is empty".toString());
        long l3 = 0L;
        n = l >= 0L ? 1 : 0;
        if (n == 0) {
            arrby = new StringBuilder();
            arrby.append("fromIndex < 0: ");
            arrby.append(l);
            throw (Throwable)new IllegalArgumentException(arrby.toString().toString());
        }
        byte[] arrby2 = arrby.head;
        if (arrby2 == null) {
            arrby = null;
            return -1L;
        }
        Object object = arrby2;
        if (arrby.size() - l < l) {
            object = arrby2;
            for (l3 = arrby.size(); l3 > l; l3 -= (long)(object.limit - object.pos)) {
                object = object.prev;
                if (object != null) continue;
                Intrinsics.throwNpe();
            }
            if (object == null) return -1L;
            arrby2 = byteString.internalArray$okio();
            byte by = arrby2[0];
            int n2 = byteString.size();
            long l4 = arrby.size() - (long)n2 + 1L;
            while (l3 < l4) {
                arrby = object.data;
                n = object.limit;
                long l5 = object.pos;
                int n3 = (int)Math.min((long)n, l5 + l4 - l3);
                for (n = (int)((long)object.pos + l - l3); n < n3; ++n) {
                    if (arrby[n] != by || !BufferKt.rangeEquals((Segment)object, n + 1, arrby2, 1, n2)) continue;
                    return (long)(n - object.pos) + l3;
                }
                l3 += (long)(object.limit - object.pos);
                object = object.next;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                l = l3;
            }
            return -1L;
        }
        do {
            if ((l2 = (long)(object.limit - object.pos) + l3) > l) break;
            object = object.next;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            l3 = l2;
        } while (true);
        if (object == null) return -1L;
        arrby2 = byteString.internalArray$okio();
        byte by = arrby2[0];
        int n4 = byteString.size();
        l2 = arrby.size() - (long)n4 + 1L;
        while (l3 < l2) {
            arrby = object.data;
            n = object.limit;
            long l6 = object.pos;
            int n5 = (int)Math.min((long)n, l6 + l2 - l3);
            for (n = (int)((long)object.pos + l - l3); n < n5; ++n) {
                if (arrby[n] != by || !BufferKt.rangeEquals((Segment)object, n + 1, arrby2, 1, n4)) continue;
                return (long)(n - object.pos) + l3;
            }
            l3 += (long)(object.limit - object.pos);
            object = object.next;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            l = l3;
        }
        return -1L;
    }

    public static final long commonIndexOfElement(Buffer object, ByteString arrby, long l) {
        int n;
        Object object2;
        long l2;
        int n2;
        block28 : {
            byte[] arrby2;
            block29 : {
                block27 : {
                    Intrinsics.checkParameterIsNotNull(object, "$this$commonIndexOfElement");
                    Intrinsics.checkParameterIsNotNull(arrby, "targetBytes");
                    l2 = 0L;
                    n2 = l >= 0L ? 1 : 0;
                    if (n2 == 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("fromIndex < 0: ");
                        ((StringBuilder)object).append(l);
                        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
                    }
                    arrby2 = ((Buffer)object).head;
                    if (arrby2 == null) {
                        object = null;
                        return -1L;
                    }
                    object2 = arrby2;
                    if (((Buffer)object).size() - l < l) {
                        object2 = arrby2;
                        for (l2 = object.size(); l2 > l; l2 -= (long)(object2.limit - object2.pos)) {
                            object2 = object2.prev;
                            if (object2 != null) continue;
                            Intrinsics.throwNpe();
                        }
                        if (object2 == null) return -1L;
                        if (arrby.size() == 2) {
                            byte by = arrby.getByte(0);
                            byte by2 = arrby.getByte(1);
                            while (l2 < ((Buffer)object).size()) {
                                arrby2 = object2.data;
                                n = (int)((long)object2.pos + l - l2);
                                int n3 = object2.limit;
                                while (n < n3) {
                                    byte by3 = arrby2[n];
                                    l = l2;
                                    arrby = object2;
                                    n2 = n++;
                                    if (by3 != by) {
                                        if (by3 != by2) continue;
                                        l = l2;
                                        arrby = object2;
                                        n2 = n;
                                    }
                                    break block27;
                                }
                                l2 += (long)(object2.limit - object2.pos);
                                object2 = object2.next;
                                if (object2 == null) {
                                    Intrinsics.throwNpe();
                                }
                                l = l2;
                            }
                            return -1L;
                        }
                        arrby = arrby.internalArray$okio();
                        while (l2 < ((Buffer)object).size()) {
                            arrby2 = object2.data;
                            int n4 = object2.limit;
                            for (n2 = (int)((long)object2.pos + l - l2); n2 < n4; ++n2) {
                                byte by = arrby2[n2];
                                int n5 = arrby.length;
                                for (n = 0; n < n5; ++n) {
                                    if (by != arrby[n]) {
                                        continue;
                                    }
                                    break block28;
                                }
                            }
                            l2 += (long)(object2.limit - object2.pos);
                            object2 = object2.next;
                            if (object2 == null) {
                                Intrinsics.throwNpe();
                            }
                            l = l2;
                        }
                        return -1L;
                    }
                    do {
                        long l3;
                        if ((l3 = (long)(object2.limit - object2.pos) + l2) > l) {
                            if (object2 == null) return -1L;
                            if (arrby.size() == 2) break;
                            break block29;
                        }
                        object2 = object2.next;
                        if (object2 == null) {
                            Intrinsics.throwNpe();
                        }
                        l2 = l3;
                    } while (true);
                    byte by = arrby.getByte(0);
                    byte by4 = arrby.getByte(1);
                    while (l2 < ((Buffer)object).size()) {
                        arrby2 = object2.data;
                        n = (int)((long)object2.pos + l - l2);
                        int n6 = object2.limit;
                        while (n < n6) {
                            byte by5 = arrby2[n];
                            l = l2;
                            arrby = object2;
                            n2 = n++;
                            if (by5 != by) {
                                if (by5 != by4) continue;
                                l = l2;
                                arrby = object2;
                                n2 = n;
                            }
                            break block27;
                        }
                        l2 += (long)(object2.limit - object2.pos);
                        object2 = object2.next;
                        if (object2 == null) {
                            Intrinsics.throwNpe();
                        }
                        l = l2;
                    }
                    return -1L;
                }
                n = arrby.pos;
                return (long)(n2 - n) + l;
            }
            arrby = arrby.internalArray$okio();
            block9 : do {
                if (l2 >= ((Buffer)object).size()) return -1L;
                arrby2 = object2.data;
                n2 = (int)((long)object2.pos + l - l2);
                int n7 = object2.limit;
                do {
                    byte by;
                    int n8;
                    if (n2 < n7) {
                        by = arrby2[n2];
                        n8 = arrby.length;
                    } else {
                        l2 += (long)(object2.limit - object2.pos);
                        object2 = object2.next;
                        if (object2 == null) {
                            Intrinsics.throwNpe();
                        }
                        l = l2;
                        continue block9;
                    }
                    for (n = 0; n < n8; ++n) {
                        if (by == arrby[n]) break block9;
                    }
                    ++n2;
                } while (true);
                break;
            } while (true);
        }
        n = object2.pos;
        l = l2;
        return (long)(n2 - n) + l;
    }

    public static final boolean commonRangeEquals(Buffer buffer, long l, ByteString byteString, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonRangeEquals");
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        if (l < 0L) return false;
        if (n < 0) return false;
        if (n2 < 0) return false;
        if (buffer.size() - l < (long)n2) return false;
        if (byteString.size() - n < n2) {
            return false;
        }
        int n3 = 0;
        while (n3 < n2) {
            if (buffer.getByte((long)n3 + l) != byteString.getByte(n + n3)) {
                return false;
            }
            ++n3;
        }
        return true;
    }

    public static final int commonRead(Buffer buffer, byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonRead");
        Intrinsics.checkParameterIsNotNull(arrby, "sink");
        return buffer.read(arrby, 0, arrby.length);
    }

    public static final int commonRead(Buffer buffer, byte[] arrby, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonRead");
        Intrinsics.checkParameterIsNotNull(arrby, "sink");
        _Util.checkOffsetAndCount(arrby.length, n, n2);
        Segment segment = buffer.head;
        if (segment == null) return -1;
        n2 = Math.min(n2, segment.limit - segment.pos);
        ArraysKt.copyInto(segment.data, arrby, n, segment.pos, segment.pos + n2);
        segment.pos += n2;
        buffer.setSize$okio(buffer.size() - (long)n2);
        if (segment.pos != segment.limit) return n2;
        buffer.head = segment.pop();
        SegmentPool.recycle(segment);
        return n2;
    }

    public static final long commonRead(Buffer object, Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonRead");
        Intrinsics.checkParameterIsNotNull(buffer, "sink");
        boolean bl = l >= 0L;
        if (!bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount < 0: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        if (((Buffer)object).size() == 0L) {
            return -1L;
        }
        long l2 = l;
        if (l > ((Buffer)object).size()) {
            l2 = ((Buffer)object).size();
        }
        buffer.write((Buffer)object, l2);
        return l2;
    }

    public static final long commonReadAll(Buffer buffer, Sink sink2) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonReadAll");
        Intrinsics.checkParameterIsNotNull(sink2, "sink");
        long l = buffer.size();
        if (l <= 0L) return l;
        sink2.write(buffer, l);
        return l;
    }

    public static final byte commonReadByte(Buffer buffer) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonReadByte");
        if (buffer.size() == 0L) throw (Throwable)new EOFException();
        Segment segment = buffer.head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        int n = segment.pos;
        int n2 = segment.limit;
        byte[] arrby = segment.data;
        int n3 = n + 1;
        byte by = arrby[n];
        buffer.setSize$okio(buffer.size() - 1L);
        if (n3 == n2) {
            buffer.head = segment.pop();
            SegmentPool.recycle(segment);
            return by;
        }
        segment.pos = n3;
        return by;
    }

    public static final byte[] commonReadByteArray(Buffer buffer) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonReadByteArray");
        return buffer.readByteArray(buffer.size());
    }

    public static final byte[] commonReadByteArray(Buffer object, long l) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonReadByteArray");
        boolean bl = l >= 0L && l <= (long)Integer.MAX_VALUE;
        if (bl) {
            if (((Buffer)object).size() < l) throw (Throwable)new EOFException();
            byte[] arrby = new byte[(int)l];
            ((Buffer)object).readFully(arrby);
            return arrby;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("byteCount: ");
        ((StringBuilder)object).append(l);
        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
    }

    public static final ByteString commonReadByteString(Buffer buffer) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonReadByteString");
        return buffer.readByteString(buffer.size());
    }

    public static final ByteString commonReadByteString(Buffer object, long l) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonReadByteString");
        boolean bl = l >= 0L && l <= (long)Integer.MAX_VALUE;
        if (bl) {
            if (((Buffer)object).size() < l) throw (Throwable)new EOFException();
            if (l < (long)4096) return new ByteString(((Buffer)object).readByteArray(l));
            ByteString byteString = ((Buffer)object).snapshot((int)l);
            ((Buffer)object).skip(l);
            return byteString;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("byteCount: ");
        ((StringBuilder)object).append(l);
        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
    }

    public static final long commonReadDecimalLong(Buffer object) {
        byte by;
        long l;
        byte by2;
        Object object2;
        block10 : {
            int n;
            Intrinsics.checkParameterIsNotNull(object, "$this$commonReadDecimalLong");
            l = ((Buffer)object).size();
            long l2 = 0L;
            if (l == 0L) throw (Throwable)new EOFException();
            long l3 = -7L;
            int n2 = 0;
            byte by3 = 0;
            boolean bl = false;
            do {
                int n3;
                if ((object2 = ((Buffer)object).head) == null) {
                    Intrinsics.throwNpe();
                }
                byte[] arrby = ((Segment)object2).data;
                int n4 = ((Segment)object2).limit;
                by2 = by3;
                n = n2;
                l = l2;
                l2 = l3;
                for (n3 = object2.pos; n3 < n4; ++n3, ++n) {
                    by = arrby[n3];
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
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Expected leading [0-9] or '-' character but was 0x");
                        ((StringBuilder)object).append(_Util.toHexString(by));
                        throw (Throwable)new NumberFormatException(((StringBuilder)object).toString());
                    }
                    bl = true;
                    break;
                }
                if (n3 == n4) {
                    ((Buffer)object).head = ((Segment)object2).pop();
                    SegmentPool.recycle((Segment)object2);
                } else {
                    ((Segment)object2).pos = n3;
                }
                if (bl) break;
                l3 = l2;
                l2 = l;
                n2 = n;
                by3 = by2;
            } while (((Buffer)object).head != null);
            ((Buffer)object).setSize$okio(((Buffer)object).size() - (long)n);
            if (by2 == 0) return -l;
            return l;
        }
        object2 = new Buffer().writeDecimalLong(l).writeByte(by);
        if (by2 == 0) {
            ((Buffer)object2).readByte();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Number too large: ");
        ((StringBuilder)object).append(((Buffer)object2).readUtf8());
        throw (Throwable)new NumberFormatException(((StringBuilder)object).toString());
    }

    public static final void commonReadFully(Buffer buffer, Buffer buffer2, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonReadFully");
        Intrinsics.checkParameterIsNotNull(buffer2, "sink");
        if (buffer.size() >= l) {
            buffer2.write(buffer, l);
            return;
        }
        buffer2.write(buffer, buffer.size());
        throw (Throwable)new EOFException();
    }

    public static final void commonReadFully(Buffer buffer, byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonReadFully");
        Intrinsics.checkParameterIsNotNull(arrby, "sink");
        int n = 0;
        while (n < arrby.length) {
            int n2 = buffer.read(arrby, n, arrby.length - n);
            if (n2 == -1) throw (Throwable)new EOFException();
            n += n2;
        }
    }

    public static final long commonReadHexadecimalUnsignedLong(Buffer object) {
        int n;
        long l;
        Intrinsics.checkParameterIsNotNull(object, "$this$commonReadHexadecimalUnsignedLong");
        if (((Buffer)object).size() == 0L) throw (Throwable)new EOFException();
        int n2 = 0;
        long l2 = 0L;
        boolean bl = false;
        do {
            int n3;
            Object object2;
            int n4;
            boolean bl2;
            block9 : {
                byte by;
                if ((object2 = ((Buffer)object).head) == null) {
                    Intrinsics.throwNpe();
                }
                byte[] arrby = ((Segment)object2).data;
                n3 = ((Segment)object2).pos;
                n4 = ((Segment)object2).limit;
                l = l2;
                n = n2;
                do {
                    bl2 = bl;
                    if (n3 >= n4) break block9;
                    by = arrby[n3];
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
                ((Buffer)object).head = ((Segment)object2).pop();
                SegmentPool.recycle((Segment)object2);
            } else {
                ((Segment)object2).pos = n3;
            }
            if (bl2) break;
            n2 = n;
            bl = bl2;
            l2 = l;
        } while (((Buffer)object).head != null);
        ((Buffer)object).setSize$okio(((Buffer)object).size() - (long)n);
        return l;
    }

    public static final int commonReadInt(Buffer buffer) {
        int n;
        int n2;
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonReadInt");
        if (buffer.size() < 4L) throw (Throwable)new EOFException();
        Segment segment = buffer.head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        if ((long)((n2 = segment.limit) - (n = segment.pos)) < 4L) {
            n2 = buffer.readByte();
            byte by = buffer.readByte();
            n = buffer.readByte();
            return buffer.readByte() & 255 | ((n2 & 255) << 24 | (by & 255) << 16 | (n & 255) << 8);
        }
        byte[] arrby = segment.data;
        int n3 = n + 1;
        n = arrby[n];
        int n4 = n3 + 1;
        n3 = arrby[n3];
        int n5 = n4 + 1;
        byte by = arrby[n4];
        n4 = n5 + 1;
        n5 = arrby[n5];
        buffer.setSize$okio(buffer.size() - 4L);
        if (n4 == n2) {
            buffer.head = segment.pop();
            SegmentPool.recycle(segment);
            return (n & 255) << 24 | (n3 & 255) << 16 | (by & 255) << 8 | n5 & 255;
        }
        segment.pos = n4;
        return (n & 255) << 24 | (n3 & 255) << 16 | (by & 255) << 8 | n5 & 255;
    }

    public static final long commonReadLong(Buffer buffer) {
        int n;
        int n2;
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonReadLong");
        if (buffer.size() < 8L) throw (Throwable)new EOFException();
        Segment segment = buffer.head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        if ((long)((n2 = segment.limit) - (n = segment.pos)) < 8L) {
            return ((long)buffer.readInt() & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL & (long)buffer.readInt();
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
        buffer.setSize$okio(buffer.size() - 8L);
        if (n == n2) {
            buffer.head = segment.pop();
            SegmentPool.recycle(segment);
            return (l4 & 255L) << 32 | ((l & 255L) << 56 | (l2 & 255L) << 48 | (l3 & 255L) << 40) | (l5 & 255L) << 24 | (l6 & 255L) << 16 | (l7 & 255L) << 8 | l8 & 255L;
        }
        segment.pos = n;
        return (l4 & 255L) << 32 | ((l & 255L) << 56 | (l2 & 255L) << 48 | (l3 & 255L) << 40) | (l5 & 255L) << 24 | (l6 & 255L) << 16 | (l7 & 255L) << 8 | l8 & 255L;
    }

    public static final short commonReadShort(Buffer buffer) {
        int n;
        int n2;
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonReadShort");
        if (buffer.size() < 2L) throw (Throwable)new EOFException();
        Segment segment = buffer.head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        if ((n2 = segment.limit) - (n = segment.pos) < 2) {
            n2 = buffer.readByte();
            return (short)(buffer.readByte() & 255 | (n2 & 255) << 8);
        }
        byte[] arrby = segment.data;
        int n3 = n + 1;
        byte by = arrby[n];
        n = n3 + 1;
        n3 = arrby[n3];
        buffer.setSize$okio(buffer.size() - 2L);
        if (n == n2) {
            buffer.head = segment.pop();
            SegmentPool.recycle(segment);
            return (short)((by & 255) << 8 | n3 & 255);
        }
        segment.pos = n;
        return (short)((by & 255) << 8 | n3 & 255);
    }

    public static final String commonReadUtf8(Buffer object, long l) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonReadUtf8");
        long l2 = l LCMP 0L;
        int n = l2 >= 0 && l <= (long)Integer.MAX_VALUE ? 1 : 0;
        if (n == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        if (((Buffer)object).size() < l) throw (Throwable)new EOFException();
        if (l2 == false) {
            return "";
        }
        Segment segment = ((Buffer)object).head;
        if (segment == null) {
            Intrinsics.throwNpe();
        }
        if ((long)segment.pos + l > (long)segment.limit) {
            return _Utf8Kt.commonToUtf8String$default(((Buffer)object).readByteArray(l), 0, 0, 3, null);
        }
        Object object2 = segment.data;
        n = segment.pos;
        int n2 = segment.pos;
        l2 = (int)l;
        object2 = _Utf8Kt.commonToUtf8String(object2, n, n2 + l2);
        segment.pos += l2;
        ((Buffer)object).setSize$okio(((Buffer)object).size() - l);
        if (segment.pos != segment.limit) return object2;
        ((Buffer)object).head = segment.pop();
        SegmentPool.recycle(segment);
        return object2;
    }

    public static final int commonReadUtf8CodePoint(Buffer buffer) {
        int n;
        int n2;
        int n3;
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonReadUtf8CodePoint");
        if (buffer.size() == 0L) throw (Throwable)new EOFException();
        byte by = buffer.getByte(0L);
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
                buffer.skip(1L);
                return 65533;
            }
            n = by & 7;
            n3 = 4;
            n2 = 65536;
        }
        long l = buffer.size();
        long l2 = n3;
        if (l < l2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("size < ");
            stringBuilder.append(n3);
            stringBuilder.append(": ");
            stringBuilder.append(buffer.size());
            stringBuilder.append(" (to read code point prefixed 0x");
            stringBuilder.append(_Util.toHexString(by));
            stringBuilder.append(')');
            throw (Throwable)new EOFException(stringBuilder.toString());
        }
        while (n4 < n3) {
            l = n4;
            byte by2 = buffer.getByte(l);
            if ((by2 & 192) != 128) {
                buffer.skip(l);
                return 65533;
            }
            n = n << 6 | by2 & 63;
            ++n4;
        }
        buffer.skip(l2);
        if (n > 1114111) {
            return n5;
        }
        if (55296 <= n && 57343 >= n) {
            return n5;
        }
        if (n >= n2) return n;
        return n5;
    }

    public static final String commonReadUtf8Line(Buffer object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonReadUtf8Line");
        long l = ((Buffer)object).indexOf((byte)10);
        if (l != -1L) {
            return BufferKt.readUtf8Line((Buffer)object, l);
        }
        if (((Buffer)object).size() == 0L) return null;
        return ((Buffer)object).readUtf8(((Buffer)object).size());
    }

    public static final String commonReadUtf8LineStrict(Buffer object, long l) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonReadUtf8LineStrict");
        boolean bl = l >= 0L;
        if (!bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("limit < 0: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        long l2 = Long.MAX_VALUE;
        if (l != Long.MAX_VALUE) {
            l2 = l + 1L;
        }
        byte by = (byte)10;
        long l3 = ((Buffer)object).indexOf(by, 0L, l2);
        if (l3 != -1L) {
            return BufferKt.readUtf8Line((Buffer)object, l3);
        }
        if (l2 < ((Buffer)object).size() && ((Buffer)object).getByte(l2 - 1L) == (byte)13 && ((Buffer)object).getByte(l2) == by) {
            return BufferKt.readUtf8Line((Buffer)object, l2);
        }
        Buffer buffer = new Buffer();
        l2 = ((Buffer)object).size();
        ((Buffer)object).copyTo(buffer, 0L, Math.min((long)32, l2));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\n not found: limit=");
        stringBuilder.append(Math.min(((Buffer)object).size(), l));
        stringBuilder.append(" content=");
        stringBuilder.append(buffer.readByteString().hex());
        stringBuilder.append('\u2026');
        throw (Throwable)new EOFException(stringBuilder.toString());
    }

    public static final int commonSelect(Buffer buffer, Options options) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonSelect");
        Intrinsics.checkParameterIsNotNull(options, "options");
        int n = BufferKt.selectPrefix$default(buffer, options, false, 2, null);
        if (n == -1) {
            return -1;
        }
        buffer.skip(options.getByteStrings$okio()[n].size());
        return n;
    }

    public static final void commonSkip(Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonSkip");
        while (l > 0L) {
            Segment segment = buffer.head;
            if (segment == null) throw (Throwable)new EOFException();
            int n = (int)Math.min(l, (long)(segment.limit - segment.pos));
            long l2 = buffer.size();
            long l3 = n;
            buffer.setSize$okio(l2 - l3);
            l3 = l - l3;
            segment.pos += n;
            l = l3;
            if (segment.pos != segment.limit) continue;
            buffer.head = segment.pop();
            SegmentPool.recycle(segment);
            l = l3;
        }
    }

    public static final ByteString commonSnapshot(Buffer buffer) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonSnapshot");
        boolean bl = buffer.size() <= (long)Integer.MAX_VALUE;
        if (bl) {
            return buffer.snapshot((int)buffer.size());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size > Int.MAX_VALUE: ");
        stringBuilder.append(buffer.size());
        throw (Throwable)new IllegalStateException(stringBuilder.toString().toString());
    }

    public static final ByteString commonSnapshot(Buffer object, int n) {
        int n2;
        Intrinsics.checkParameterIsNotNull(object, "$this$commonSnapshot");
        if (n == 0) {
            return ByteString.EMPTY;
        }
        _Util.checkOffsetAndCount(((Buffer)object).size(), 0L, n);
        Object object2 = ((Buffer)object).head;
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < n; n2 += object2.limit - object2.pos, ++n4) {
            if (object2 == null) {
                Intrinsics.throwNpe();
            }
            if (object2.limit == object2.pos) throw (Throwable)((Object)new AssertionError((Object)"s.limit == s.pos"));
            object2 = object2.next;
        }
        object2 = new byte[n4][];
        int[] arrn = new int[n4 * 2];
        object = ((Buffer)object).head;
        n4 = 0;
        n2 = n3;
        while (n2 < n) {
            if (object == null) {
                Intrinsics.throwNpe();
            }
            object2[n4] = ((Segment)object).data;
            arrn[n4] = Math.min(n2 += ((Segment)object).limit - ((Segment)object).pos, n);
            arrn[((Object[])object2).length + n4] = ((Segment)object).pos;
            ((Segment)object).shared = true;
            ++n4;
            object = ((Segment)object).next;
        }
        return new SegmentedByteString((byte[][])object2, arrn);
    }

    public static final Segment commonWritableSegment(Buffer object, int n) {
        Segment segment;
        Intrinsics.checkParameterIsNotNull(object, "$this$commonWritableSegment");
        boolean bl = true;
        if (n < 1 || n > 8192) {
            bl = false;
        }
        if (!bl) throw (Throwable)new IllegalArgumentException("unexpected capacity".toString());
        if (((Buffer)object).head == null) {
            Segment segment2;
            ((Buffer)object).head = segment2 = SegmentPool.take();
            segment2.prev = segment2;
            segment2.next = segment2;
            return segment2;
        }
        object = ((Buffer)object).head;
        if (object == null) {
            Intrinsics.throwNpe();
        }
        if ((segment = ((Segment)object).prev) == null) {
            Intrinsics.throwNpe();
        }
        if (segment.limit + n > 8192) return segment.push(SegmentPool.take());
        object = segment;
        if (segment.owner) return object;
        return segment.push(SegmentPool.take());
    }

    public static final Buffer commonWrite(Buffer buffer, ByteString byteString, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        byteString.write$okio(buffer, n, n2);
        return buffer;
    }

    public static final Buffer commonWrite(Buffer buffer, Source source2, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(source2, "source");
        while (l > 0L) {
            long l2 = source2.read(buffer, l);
            if (l2 == -1L) throw (Throwable)new EOFException();
            l -= l2;
        }
        return buffer;
    }

    public static final Buffer commonWrite(Buffer buffer, byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(arrby, "source");
        return buffer.write(arrby, 0, arrby.length);
    }

    public static final Buffer commonWrite(Buffer buffer, byte[] arrby, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(arrby, "source");
        long l = arrby.length;
        long l2 = n;
        long l3 = n2;
        _Util.checkOffsetAndCount(l, l2, l3);
        int n3 = n2 + n;
        do {
            if (n >= n3) {
                buffer.setSize$okio(buffer.size() + l3);
                return buffer;
            }
            Segment segment = buffer.writableSegment$okio(1);
            int n4 = Math.min(n3 - n, 8192 - segment.limit);
            byte[] arrby2 = segment.data;
            int n5 = segment.limit;
            n2 = n + n4;
            ArraysKt.copyInto(arrby, arrby2, n5, n, n2);
            segment.limit += n4;
            n = n2;
        } while (true);
    }

    public static final void commonWrite(Buffer buffer, Buffer buffer2, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(buffer2, "source");
        int n = buffer2 != buffer ? 1 : 0;
        if (n == 0) throw (Throwable)new IllegalArgumentException("source == this".toString());
        _Util.checkOffsetAndCount(buffer2.size(), 0L, l);
        while (l > 0L) {
            Segment segment;
            long l2;
            Segment segment2 = buffer2.head;
            if (segment2 == null) {
                Intrinsics.throwNpe();
            }
            n = segment2.limit;
            segment2 = buffer2.head;
            if (segment2 == null) {
                Intrinsics.throwNpe();
            }
            if (l < (long)(n - segment2.pos)) {
                if (buffer.head != null) {
                    segment2 = buffer.head;
                    if (segment2 == null) {
                        Intrinsics.throwNpe();
                    }
                    segment2 = segment2.prev;
                } else {
                    segment2 = null;
                }
                if (segment2 != null && segment2.owner && (l2 = (long)segment2.limit) + l - (long)(n = segment2.shared ? 0 : segment2.pos) <= (long)8192) {
                    segment = buffer2.head;
                    if (segment == null) {
                        Intrinsics.throwNpe();
                    }
                    segment.writeTo(segment2, (int)l);
                    buffer2.setSize$okio(buffer2.size() - l);
                    buffer.setSize$okio(buffer.size() + l);
                    return;
                }
                segment2 = buffer2.head;
                if (segment2 == null) {
                    Intrinsics.throwNpe();
                }
                buffer2.head = segment2.split((int)l);
            }
            if ((segment2 = buffer2.head) == null) {
                Intrinsics.throwNpe();
            }
            l2 = segment2.limit - segment2.pos;
            buffer2.head = segment2.pop();
            if (buffer.head == null) {
                buffer.head = segment2;
                segment2.next = segment2.prev = segment2;
            } else {
                segment = buffer.head;
                if (segment == null) {
                    Intrinsics.throwNpe();
                }
                if ((segment = segment.prev) == null) {
                    Intrinsics.throwNpe();
                }
                segment.push(segment2).compact();
            }
            buffer2.setSize$okio(buffer2.size() - l2);
            buffer.setSize$okio(buffer.size() + l2);
            l -= l2;
        }
    }

    public static /* synthetic */ Buffer commonWrite$default(Buffer buffer, ByteString byteString, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = byteString.size();
        }
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        byteString.write$okio(buffer, n, n2);
        return buffer;
    }

    public static final long commonWriteAll(Buffer buffer, Source source2) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWriteAll");
        Intrinsics.checkParameterIsNotNull(source2, "source");
        long l = 0L;
        long l2;
        while ((l2 = source2.read(buffer, 8192)) != -1L) {
            l += l2;
        }
        return l;
    }

    public static final Buffer commonWriteByte(Buffer buffer, int n) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWriteByte");
        Segment segment = buffer.writableSegment$okio(1);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        segment.limit = n2 + 1;
        arrby[n2] = (byte)n;
        buffer.setSize$okio(buffer.size() + 1L);
        return buffer;
    }

    public static final Buffer commonWriteDecimalLong(Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWriteDecimalLong");
        long l2 = l LCMP 0L;
        if (l2 == false) {
            return buffer.writeByte(48);
        }
        boolean bl = false;
        int n = 1;
        long l3 = l;
        if (l2 < 0) {
            l3 = -l;
            if (l3 < 0L) {
                return buffer.writeUtf8("-9223372036854775808");
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
        Segment segment = buffer.writableSegment$okio((int)l2);
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
        buffer.setSize$okio(buffer.size() + (long)l2);
        return buffer;
    }

    public static final Buffer commonWriteHexadecimalUnsignedLong(Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWriteHexadecimalUnsignedLong");
        if (l == 0L) {
            return buffer.writeByte(48);
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
        Segment segment = buffer.writableSegment$okio(n);
        byte[] arrby = segment.data;
        int n2 = segment.limit + n - 1;
        int n3 = segment.limit;
        do {
            if (n2 < n3) {
                segment.limit += n;
                buffer.setSize$okio(buffer.size() + (long)n);
                return buffer;
            }
            arrby[n2] = BufferKt.getHEX_DIGIT_BYTES()[(int)(15L & l)];
            l >>>= 4;
            --n2;
        } while (true);
    }

    public static final Buffer commonWriteInt(Buffer buffer, int n) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWriteInt");
        Segment segment = buffer.writableSegment$okio(4);
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
        buffer.setSize$okio(buffer.size() + 4L);
        return buffer;
    }

    public static final Buffer commonWriteLong(Buffer buffer, long l) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWriteLong");
        Segment segment = buffer.writableSegment$okio(8);
        byte[] arrby = segment.data;
        int n = segment.limit;
        int n2 = n + 1;
        arrby[n] = (byte)(l >>> 56 & 255L);
        n = n2 + 1;
        arrby[n2] = (byte)(l >>> 48 & 255L);
        n2 = n + 1;
        arrby[n] = (byte)(l >>> 40 & 255L);
        n = n2 + 1;
        arrby[n2] = (byte)(l >>> 32 & 255L);
        n2 = n + 1;
        arrby[n] = (byte)(l >>> 24 & 255L);
        n = n2 + 1;
        arrby[n2] = (byte)(l >>> 16 & 255L);
        n2 = n + 1;
        arrby[n] = (byte)(l >>> 8 & 255L);
        arrby[n2] = (byte)(l & 255L);
        segment.limit = n2 + 1;
        buffer.setSize$okio(buffer.size() + 8L);
        return buffer;
    }

    public static final Buffer commonWriteShort(Buffer buffer, int n) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$commonWriteShort");
        Segment segment = buffer.writableSegment$okio(2);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        int n3 = n2 + 1;
        arrby[n2] = (byte)(n >>> 8 & 255);
        arrby[n3] = (byte)(n & 255);
        segment.limit = n3 + 1;
        buffer.setSize$okio(buffer.size() + 2L);
        return buffer;
    }

    public static final Buffer commonWriteUtf8(Buffer object, String string2, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonWriteUtf8");
        Intrinsics.checkParameterIsNotNull(string2, "string");
        int n3 = n >= 0 ? 1 : 0;
        if (n3 == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("beginIndex < 0: ");
            ((StringBuilder)object).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        n3 = n2 >= n ? 1 : 0;
        if (n3 == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("endIndex < beginIndex: ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" < ");
            ((StringBuilder)object).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        n3 = n2 <= string2.length() ? 1 : 0;
        if (n3 == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("endIndex > string.length: ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" > ");
            ((StringBuilder)object).append(string2.length());
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        while (n < n2) {
            Segment segment;
            int n4;
            int n5;
            char c = string2.charAt(n);
            if (c < '') {
                segment = ((Buffer)object).writableSegment$okio(1);
                byte[] arrby = segment.data;
                n5 = segment.limit - n;
                n4 = Math.min(n2, 8192 - n5);
                n3 = n + 1;
                arrby[n + n5] = (byte)c;
                n = n3;
            } else {
                if (c < '\u0800') {
                    segment = ((Buffer)object).writableSegment$okio(2);
                    segment.data[segment.limit] = (byte)(c >> 6 | 192);
                    segment.data[segment.limit + 1] = (byte)(c & 63 | 128);
                    segment.limit += 2;
                    ((Buffer)object).setSize$okio(((Buffer)object).size() + 2L);
                } else {
                    if (c >= '\ud800' && c <= '\udfff') {
                        n4 = n + 1;
                        n3 = n4 < n2 ? string2.charAt(n4) : 0;
                        if (c <= '\udbff' && 56320 <= n3 && 57343 >= n3) {
                            n3 = ((c & 1023) << 10 | n3 & 1023) + 65536;
                            segment = ((Buffer)object).writableSegment$okio(4);
                            segment.data[segment.limit] = (byte)(n3 >> 18 | 240);
                            segment.data[segment.limit + 1] = (byte)(n3 >> 12 & 63 | 128);
                            segment.data[segment.limit + 2] = (byte)(n3 >> 6 & 63 | 128);
                            segment.data[segment.limit + 3] = (byte)(n3 & 63 | 128);
                            segment.limit += 4;
                            ((Buffer)object).setSize$okio(((Buffer)object).size() + 4L);
                            n += 2;
                            continue;
                        }
                        ((Buffer)object).writeByte(63);
                        n = n4;
                        continue;
                    }
                    segment = ((Buffer)object).writableSegment$okio(3);
                    segment.data[segment.limit] = (byte)(c >> 12 | 224);
                    segment.data[segment.limit + 1] = (byte)(63 & c >> 6 | 128);
                    segment.data[segment.limit + 2] = (byte)(c & 63 | 128);
                    segment.limit += 3;
                    ((Buffer)object).setSize$okio(((Buffer)object).size() + 3L);
                }
                ++n;
                continue;
            }
            while (n < n4 && (n3 = (int)string2.charAt(n)) < 128) {
                arrby[n + n5] = (byte)n3;
                ++n;
            }
            n3 = n5 + n - segment.limit;
            segment.limit += n3;
            ((Buffer)object).setSize$okio(((Buffer)object).size() + (long)n3);
        }
        return object;
    }

    public static final Buffer commonWriteUtf8CodePoint(Buffer object, int n) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonWriteUtf8CodePoint");
        if (n < 128) {
            ((Buffer)object).writeByte(n);
            return object;
        }
        if (n < 2048) {
            Segment segment = ((Buffer)object).writableSegment$okio(2);
            segment.data[segment.limit] = (byte)(n >> 6 | 192);
            segment.data[segment.limit + 1] = (byte)(n & 63 | 128);
            segment.limit += 2;
            ((Buffer)object).setSize$okio(((Buffer)object).size() + 2L);
            return object;
        }
        if (55296 <= n && 57343 >= n) {
            ((Buffer)object).writeByte(63);
            return object;
        }
        if (n < 65536) {
            Segment segment = ((Buffer)object).writableSegment$okio(3);
            segment.data[segment.limit] = (byte)(n >> 12 | 224);
            segment.data[segment.limit + 1] = (byte)(n >> 6 & 63 | 128);
            segment.data[segment.limit + 2] = (byte)(n & 63 | 128);
            segment.limit += 3;
            ((Buffer)object).setSize$okio(((Buffer)object).size() + 3L);
            return object;
        }
        if (n <= 1114111) {
            Segment segment = ((Buffer)object).writableSegment$okio(4);
            segment.data[segment.limit] = (byte)(n >> 18 | 240);
            segment.data[segment.limit + 1] = (byte)(n >> 12 & 63 | 128);
            segment.data[segment.limit + 2] = (byte)(n >> 6 & 63 | 128);
            segment.data[segment.limit + 3] = (byte)(n & 63 | 128);
            segment.limit += 4;
            ((Buffer)object).setSize$okio(((Buffer)object).size() + 4L);
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unexpected code point: 0x");
        ((StringBuilder)object).append(_Util.toHexString(n));
        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static final byte[] getHEX_DIGIT_BYTES() {
        return HEX_DIGIT_BYTES;
    }

    public static final boolean rangeEquals(Segment arrby, int n, byte[] arrby2, int n2, int n3) {
        Intrinsics.checkParameterIsNotNull(arrby, "segment");
        Intrinsics.checkParameterIsNotNull(arrby2, "bytes");
        int n4 = arrby.limit;
        byte[] arrby3 = arrby.data;
        while (n2 < n3) {
            int n5 = n4;
            Object object = arrby;
            int n6 = n;
            if (n == n4) {
                object = arrby.next;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                arrby = object.data;
                n6 = object.pos;
                n5 = object.limit;
                arrby3 = arrby;
            }
            if (arrby3[n6] != arrby2[n2]) {
                return false;
            }
            n = n6 + 1;
            ++n2;
            n4 = n5;
            arrby = object;
        }
        return true;
    }

    public static final String readUtf8Line(Buffer object, long l) {
        long l2;
        Intrinsics.checkParameterIsNotNull(object, "$this$readUtf8Line");
        if (l > 0L && ((Buffer)object).getByte(l2 = l - 1L) == (byte)13) {
            String string2 = ((Buffer)object).readUtf8(l2);
            ((Buffer)object).skip(2L);
            return string2;
        }
        String string3 = ((Buffer)object).readUtf8(l);
        ((Buffer)object).skip(1L);
        return string3;
    }

    public static final <T> T seek(Buffer buffer, long l, Function2<? super Segment, ? super Long, ? extends T> function2) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$seek");
        Intrinsics.checkParameterIsNotNull(function2, "lambda");
        Segment segment = buffer.head;
        if (segment == null) return function2.invoke(null, (Long)-1L);
        if (buffer.size() - l < l) {
            long l2 = buffer.size();
            while (l2 > l) {
                segment = segment.prev;
                if (segment == null) {
                    Intrinsics.throwNpe();
                }
                l2 -= (long)(segment.limit - segment.pos);
            }
            return function2.invoke(segment, (Long)l2);
        }
        long l3 = 0L;
        long l4;
        while ((l4 = (long)(segment.limit - segment.pos) + l3) <= l) {
            segment = segment.next;
            if (segment == null) {
                Intrinsics.throwNpe();
            }
            l3 = l4;
        }
        return function2.invoke(segment, (Long)l3);
    }

    /*
     * Unable to fully structure code
     */
    public static final int selectPrefix(Buffer var0, Options var1_1, boolean var2_2) {
        Intrinsics.checkParameterIsNotNull(var0 /* !! */ , "$this$selectPrefix");
        Intrinsics.checkParameterIsNotNull(var1_10, "options");
        var3_22 = var0 /* !! */ .head;
        var4_23 = -2;
        if (var3_22 == null) {
            if (var2_21 == false) return -1;
            return var4_23;
        }
        var5_24 = var3_22.data;
        var4_23 = var3_22.pos;
        var6_30 = var3_22.limit;
        var7_31 = var1_10.getTrie$okio();
        var0_1 = var3_22;
        var8_32 = 0;
        var9_33 = -1;
        block0 : do {
            block21 : {
                block20 : {
                    var10_34 = var8_32 + 1;
                    var11_35 = var7_31[var8_32];
                    var12_36 = var10_34 + 1;
                    var8_32 = var7_31[var10_34];
                    if (var8_32 != -1) {
                        var9_33 = var8_32;
                    }
                    if (var0_2 == null) break block20;
                    if (var11_35 >= 0) break block21;
                    var8_32 = var12_36;
                    var1_12 = var5_25;
                    do {
                        block22 : {
                            var10_34 = var4_23 + 1;
                            var4_23 = var1_13[var4_23];
                            var13_37 = var8_32 + 1;
                            if ((var4_23 & 255) != var7_31[var8_32]) {
                                return var9_33;
                            }
                            var8_32 = var13_37 == var12_36 + var11_35 * -1 ? 1 : 0;
                            if (var10_34 == var6_30) {
                                if (var0_3 == null) {
                                    Intrinsics.throwNpe();
                                }
                                if ((var5_27 = var0_3.next) == null) {
                                    Intrinsics.throwNpe();
                                }
                                var6_30 = var5_27.pos;
                                var1_14 = var5_27.data;
                                var4_23 = var5_27.limit;
                                var0_4 = var5_27;
                                if (var5_27 == var3_22) {
                                    if (var8_32 == 0) break;
                                    var0_5 = null;
                                }
                            } else {
                                var4_23 = var6_30;
                                var6_30 = var10_34;
                            }
                            if (var8_32 == 0) break block22;
                            var12_36 = var7_31[var13_37];
                            var5_28 = var1_13;
                            var8_32 = var4_23;
                            var1_15 = var0_3;
                            var4_23 = var6_30;
                            ** GOTO lbl87
                        }
                        var10_34 = var4_23;
                        var8_32 = var13_37;
                        var4_23 = var6_30;
                        var6_30 = var10_34;
                    } while (true);
                }
                if (var2_21 == false) return var9_33;
                return -2;
            }
            var10_34 = var4_23 + 1;
            var8_32 = var5_25[var4_23];
            var4_23 = var12_36;
            do {
                block23 : {
                    if (var4_23 == var12_36 + var11_35) {
                        return var9_33;
                    }
                    if ((var8_32 & 255) != var7_31[var4_23]) break block23;
                    var12_36 = var13_37 = var7_31[var4_23 + var11_35];
                    var8_32 = var6_30;
                    var1_16 = var0_2;
                    var4_23 = var10_34;
                    if (var10_34 == var6_30) {
                        var0_7 = var0_2.next;
                        if (var0_7 == null) {
                            Intrinsics.throwNpe();
                        }
                        var4_23 = var0_7.pos;
                        var5_29 = var0_7.data;
                        var8_32 = var0_7.limit;
                        var1_17 = var0_7;
                        if (var0_7 == var3_22) {
                            var1_18 = null;
                        }
                        var12_36 = var13_37;
                    }
lbl87: // 4 sources:
                    if (var12_36 >= 0) {
                        return var12_36;
                    }
                    var12_36 = -var12_36;
                    var6_30 = var8_32;
                    var8_32 = var12_36;
                    var0_9 = var1_20;
                    continue block0;
                }
                ++var4_23;
            } while (true);
            break;
        } while (true);
    }

    public static /* synthetic */ int selectPrefix$default(Buffer buffer, Options options, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return BufferKt.selectPrefix(buffer, options, bl);
        bl = false;
        return BufferKt.selectPrefix(buffer, options, bl);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Segment;
import okio.SegmentedByteString;
import okio._Util;

@Metadata(bv={1, 0, 3}, d1={"\u0000R\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0000\u001a\u0017\u0010\u0006\u001a\u00020\u0007*\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\b\u001a\r\u0010\u000b\u001a\u00020\u0001*\u00020\bH\b\u001a\r\u0010\f\u001a\u00020\u0001*\u00020\bH\b\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0001H\b\u001a-\u0010\u0010\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\b\u001a-\u0010\u0010\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\b\u001a\u001d\u0010\u0016\u001a\u00020\u0015*\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0001H\b\u001a\r\u0010\u0019\u001a\u00020\u0012*\u00020\bH\b\u001a%\u0010\u001a\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\b\u001aZ\u0010\u001e\u001a\u00020\u001b*\u00020\b2K\u0010\u001f\u001aG\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u001b0 H\b\u001aj\u0010\u001e\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00012K\u0010\u001f\u001aG\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u001b0 H\u0082\b\u001a\u0014\u0010$\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0001H\u0000\u00a8\u0006%"}, d2={"binarySearch", "", "", "value", "fromIndex", "toIndex", "commonEquals", "", "Lokio/SegmentedByteString;", "other", "", "commonGetSize", "commonHashCode", "commonInternalGet", "", "pos", "commonRangeEquals", "offset", "", "otherOffset", "byteCount", "Lokio/ByteString;", "commonSubstring", "beginIndex", "endIndex", "commonToByteArray", "commonWrite", "", "buffer", "Lokio/Buffer;", "forEachSegment", "action", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "data", "segment", "okio"}, k=2, mv={1, 1, 16})
public final class SegmentedByteStringKt {
    public static final /* synthetic */ void access$forEachSegment(SegmentedByteString segmentedByteString, int n, int n2, Function3 function3) {
        SegmentedByteStringKt.forEachSegment(segmentedByteString, n, n2, function3);
    }

    public static final int binarySearch(int[] arrn, int n, int n2, int n3) {
        Intrinsics.checkParameterIsNotNull(arrn, "$this$binarySearch");
        --n3;
        while (n2 <= n3) {
            int n4 = n2 + n3 >>> 1;
            int n5 = arrn[n4];
            if (n5 < n) {
                n2 = n4 + 1;
                continue;
            }
            if (n5 <= n) return n4;
            n3 = n4 - 1;
        }
        return -n2 - 1;
    }

    public static final boolean commonEquals(SegmentedByteString segmentedByteString, Object object) {
        Intrinsics.checkParameterIsNotNull(segmentedByteString, "$this$commonEquals");
        boolean bl = true;
        if (object == segmentedByteString) {
            return bl;
        }
        if (!(object instanceof ByteString)) return false;
        if (((ByteString)(object = (ByteString)object)).size() != segmentedByteString.size()) return false;
        if (!segmentedByteString.rangeEquals(0, (ByteString)object, 0, segmentedByteString.size())) return false;
        return bl;
    }

    public static final int commonGetSize(SegmentedByteString segmentedByteString) {
        Intrinsics.checkParameterIsNotNull(segmentedByteString, "$this$commonGetSize");
        return segmentedByteString.getDirectory$okio()[((Object[])segmentedByteString.getSegments$okio()).length - 1];
    }

    public static final int commonHashCode(SegmentedByteString segmentedByteString) {
        Intrinsics.checkParameterIsNotNull(segmentedByteString, "$this$commonHashCode");
        int n = segmentedByteString.getHashCode$okio();
        if (n != 0) {
            return n;
        }
        int n2 = ((Object[])segmentedByteString.getSegments$okio()).length;
        int n3 = 0;
        int n4 = 0;
        int n5 = 1;
        do {
            if (n3 >= n2) {
                segmentedByteString.setHashCode$okio(n5);
                return n5;
            }
            int n6 = segmentedByteString.getDirectory$okio()[n2 + n3];
            int n7 = segmentedByteString.getDirectory$okio()[n3];
            byte[] arrby = segmentedByteString.getSegments$okio()[n3];
            for (n = n6; n < n7 - n4 + n6; ++n) {
                n5 = n5 * 31 + arrby[n];
            }
            ++n3;
            n4 = n7;
        } while (true);
    }

    public static final byte commonInternalGet(SegmentedByteString segmentedByteString, int n) {
        Intrinsics.checkParameterIsNotNull(segmentedByteString, "$this$commonInternalGet");
        _Util.checkOffsetAndCount(segmentedByteString.getDirectory$okio()[((Object[])segmentedByteString.getSegments$okio()).length - 1], n, 1L);
        int n2 = SegmentedByteStringKt.segment(segmentedByteString, n);
        int n3 = n2 == 0 ? 0 : segmentedByteString.getDirectory$okio()[n2 - 1];
        int n4 = segmentedByteString.getDirectory$okio()[((Object[])segmentedByteString.getSegments$okio()).length + n2];
        return segmentedByteString.getSegments$okio()[n2][n - n3 + n4];
    }

    public static final boolean commonRangeEquals(SegmentedByteString segmentedByteString, int n, ByteString byteString, int n2, int n3) {
        Intrinsics.checkParameterIsNotNull(segmentedByteString, "$this$commonRangeEquals");
        Intrinsics.checkParameterIsNotNull(byteString, "other");
        if (n < 0) return false;
        if (n > segmentedByteString.size() - n3) {
            return false;
        }
        int n4 = n3 + n;
        n3 = SegmentedByteStringKt.segment(segmentedByteString, n);
        while (n < n4) {
            int n5 = n3 == 0 ? 0 : segmentedByteString.getDirectory$okio()[n3 - 1];
            int n6 = segmentedByteString.getDirectory$okio()[n3];
            int n7 = segmentedByteString.getDirectory$okio()[((Object[])segmentedByteString.getSegments$okio()).length + n3];
            n6 = Math.min(n4, n6 - n5 + n5) - n;
            if (!byteString.rangeEquals(n2, segmentedByteString.getSegments$okio()[n3], n7 + (n - n5), n6)) {
                return false;
            }
            n2 += n6;
            n += n6;
            ++n3;
        }
        return true;
    }

    public static final boolean commonRangeEquals(SegmentedByteString segmentedByteString, int n, byte[] arrby, int n2, int n3) {
        Intrinsics.checkParameterIsNotNull(segmentedByteString, "$this$commonRangeEquals");
        Intrinsics.checkParameterIsNotNull(arrby, "other");
        if (n < 0) return false;
        if (n > segmentedByteString.size() - n3) return false;
        if (n2 < 0) return false;
        if (n2 > arrby.length - n3) {
            return false;
        }
        int n4 = n3 + n;
        n3 = SegmentedByteStringKt.segment(segmentedByteString, n);
        while (n < n4) {
            int n5 = n3 == 0 ? 0 : segmentedByteString.getDirectory$okio()[n3 - 1];
            int n6 = segmentedByteString.getDirectory$okio()[n3];
            int n7 = segmentedByteString.getDirectory$okio()[((Object[])segmentedByteString.getSegments$okio()).length + n3];
            n6 = Math.min(n4, n6 - n5 + n5) - n;
            if (!_Util.arrayRangeEquals(segmentedByteString.getSegments$okio()[n3], n7 + (n - n5), arrby, n2, n6)) {
                return false;
            }
            n2 += n6;
            n += n6;
            ++n3;
        }
        return true;
    }

    public static final ByteString commonSubstring(SegmentedByteString comparable, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(comparable, "$this$commonSubstring");
        int n3 = 0;
        int n4 = n >= 0 ? 1 : 0;
        if (n4 == 0) {
            comparable = new StringBuilder();
            ((StringBuilder)comparable).append("beginIndex=");
            ((StringBuilder)comparable).append(n);
            ((StringBuilder)comparable).append(" < 0");
            throw (Throwable)new IllegalArgumentException(((StringBuilder)comparable).toString().toString());
        }
        n4 = n2 <= ((ByteString)comparable).size() ? 1 : 0;
        if (n4 == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("endIndex=");
            stringBuilder.append(n2);
            stringBuilder.append(" > length(");
            stringBuilder.append(((ByteString)comparable).size());
            stringBuilder.append(')');
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        int n5 = n2 - n;
        n4 = n5 >= 0 ? 1 : 0;
        if (n4 == 0) {
            comparable = new StringBuilder();
            ((StringBuilder)comparable).append("endIndex=");
            ((StringBuilder)comparable).append(n2);
            ((StringBuilder)comparable).append(" < beginIndex=");
            ((StringBuilder)comparable).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)comparable).toString().toString());
        }
        if (n == 0 && n2 == ((ByteString)comparable).size()) {
            return (ByteString)comparable;
        }
        if (n == n2) {
            return ByteString.EMPTY;
        }
        int n6 = SegmentedByteStringKt.segment(comparable, n);
        int n7 = SegmentedByteStringKt.segment(comparable, n2 - 1);
        byte[][] arrby = (byte[][])ArraysKt.copyOfRange((Object[])((SegmentedByteString)comparable).getSegments$okio(), n6, n7 + 1);
        Object[] arrobject = (Object[])arrby;
        int[] arrn = new int[arrobject.length * 2];
        if (n6 <= n7) {
            n2 = n6;
            n4 = 0;
            do {
                arrn[n4] = Math.min(((SegmentedByteString)comparable).getDirectory$okio()[n2] - n, n5);
                arrn[n4 + arrobject.length] = ((SegmentedByteString)comparable).getDirectory$okio()[((Object[])((SegmentedByteString)comparable).getSegments$okio()).length + n2];
                if (n2 == n7) break;
                ++n2;
                ++n4;
            } while (true);
        }
        n2 = n6 == 0 ? n3 : ((SegmentedByteString)comparable).getDirectory$okio()[n6 - 1];
        n4 = arrobject.length;
        arrn[n4] = arrn[n4] + (n - n2);
        return new SegmentedByteString(arrby, arrn);
    }

    public static final byte[] commonToByteArray(SegmentedByteString segmentedByteString) {
        Intrinsics.checkParameterIsNotNull(segmentedByteString, "$this$commonToByteArray");
        byte[] arrby = new byte[segmentedByteString.size()];
        int n = ((Object[])segmentedByteString.getSegments$okio()).length;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        while (n2 < n) {
            int n5 = segmentedByteString.getDirectory$okio()[n + n2];
            int n6 = segmentedByteString.getDirectory$okio()[n2];
            byte[] arrby2 = segmentedByteString.getSegments$okio()[n2];
            n3 = n6 - n3;
            ArraysKt.copyInto(arrby2, arrby, n4, n5, n5 + n3);
            n4 += n3;
            ++n2;
            n3 = n6;
        }
        return arrby;
    }

    public static final void commonWrite(SegmentedByteString segmentedByteString, Buffer buffer, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(segmentedByteString, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        int n3 = n2 + n;
        n2 = SegmentedByteStringKt.segment(segmentedByteString, n);
        do {
            if (n >= n3) {
                buffer.setSize$okio(buffer.size() + (long)segmentedByteString.size());
                return;
            }
            int n4 = n2 == 0 ? 0 : segmentedByteString.getDirectory$okio()[n2 - 1];
            int n5 = segmentedByteString.getDirectory$okio()[n2];
            int n6 = segmentedByteString.getDirectory$okio()[((Object[])segmentedByteString.getSegments$okio()).length + n2];
            n5 = Math.min(n3, n5 - n4 + n4) - n;
            n4 = n6 + (n - n4);
            Segment segment = new Segment(segmentedByteString.getSegments$okio()[n2], n4, n4 + n5, true, false);
            if (buffer.head == null) {
                buffer.head = segment.next = (segment.prev = segment);
            } else {
                Segment segment2 = buffer.head;
                if (segment2 == null) {
                    Intrinsics.throwNpe();
                }
                if ((segment2 = segment2.prev) == null) {
                    Intrinsics.throwNpe();
                }
                segment2.push(segment);
            }
            n += n5;
            ++n2;
        } while (true);
    }

    private static final void forEachSegment(SegmentedByteString segmentedByteString, int n, int n2, Function3<? super byte[], ? super Integer, ? super Integer, Unit> function3) {
        int n3 = SegmentedByteStringKt.segment(segmentedByteString, n);
        while (n < n2) {
            int n4 = n3 == 0 ? 0 : segmentedByteString.getDirectory$okio()[n3 - 1];
            int n5 = segmentedByteString.getDirectory$okio()[n3];
            int n6 = segmentedByteString.getDirectory$okio()[((Object[])segmentedByteString.getSegments$okio()).length + n3];
            n5 = Math.min(n2, n5 - n4 + n4) - n;
            function3.invoke((byte[])segmentedByteString.getSegments$okio()[n3], (Integer)(n6 + (n - n4)), (Integer)n5);
            n += n5;
            ++n3;
        }
    }

    public static final void forEachSegment(SegmentedByteString segmentedByteString, Function3<? super byte[], ? super Integer, ? super Integer, Unit> function3) {
        Intrinsics.checkParameterIsNotNull(segmentedByteString, "$this$forEachSegment");
        Intrinsics.checkParameterIsNotNull(function3, "action");
        int n = ((Object[])segmentedByteString.getSegments$okio()).length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            int n4 = segmentedByteString.getDirectory$okio()[n + n2];
            int n5 = segmentedByteString.getDirectory$okio()[n2];
            function3.invoke((byte[])segmentedByteString.getSegments$okio()[n2], (Integer)n4, (Integer)(n5 - n3));
            ++n2;
            n3 = n5;
        }
    }

    public static final int segment(SegmentedByteString segmentedByteString, int n) {
        Intrinsics.checkParameterIsNotNull(segmentedByteString, "$this$segment");
        n = SegmentedByteStringKt.binarySearch(segmentedByteString.getDirectory$okio(), n + 1, 0, ((Object[])segmentedByteString.getSegments$okio()).length);
        if (n < 0) return n;
        return n;
    }
}


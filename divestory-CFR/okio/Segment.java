/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okio.SegmentPool;

@Metadata(bv={1, 0, 3}, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0000\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0002B/\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0002\u0010\u000bJ\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u0004\u0018\u00010\u0000J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0000J\u0006\u0010\u0013\u001a\u00020\u0000J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0006J\u0006\u0010\u0016\u001a\u00020\u0000J\u0016\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0006R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u0004\u0018\u00010\u00008\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u0004\u0018\u00010\u00008\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2={"Lokio/Segment;", "", "()V", "data", "", "pos", "", "limit", "shared", "", "owner", "([BIIZZ)V", "next", "prev", "compact", "", "pop", "push", "segment", "sharedCopy", "split", "byteCount", "unsharedCopy", "writeTo", "sink", "Companion", "okio"}, k=1, mv={1, 1, 16})
public final class Segment {
    public static final Companion Companion = new Companion(null);
    public static final int SHARE_MINIMUM = 1024;
    public static final int SIZE = 8192;
    public final byte[] data;
    public int limit;
    public Segment next;
    public boolean owner;
    public int pos;
    public Segment prev;
    public boolean shared;

    public Segment() {
        this.data = new byte[8192];
        this.owner = true;
        this.shared = false;
    }

    public Segment(byte[] arrby, int n, int n2, boolean bl, boolean bl2) {
        Intrinsics.checkParameterIsNotNull(arrby, "data");
        this.data = arrby;
        this.pos = n;
        this.limit = n2;
        this.shared = bl;
        this.owner = bl2;
    }

    public final void compact() {
        Segment segment = this.prev;
        Segment segment2 = this;
        int n = 0;
        int n2 = segment != segment2 ? 1 : 0;
        if (n2 == 0) throw (Throwable)new IllegalStateException("cannot compact".toString());
        segment2 = this.prev;
        if (segment2 == null) {
            Intrinsics.throwNpe();
        }
        if (!segment2.owner) {
            return;
        }
        int n3 = this.limit - this.pos;
        segment2 = this.prev;
        if (segment2 == null) {
            Intrinsics.throwNpe();
        }
        int n4 = segment2.limit;
        segment2 = this.prev;
        if (segment2 == null) {
            Intrinsics.throwNpe();
        }
        if (segment2.shared) {
            n2 = n;
        } else {
            segment2 = this.prev;
            if (segment2 == null) {
                Intrinsics.throwNpe();
            }
            n2 = segment2.pos;
        }
        if (n3 > 8192 - n4 + n2) {
            return;
        }
        segment2 = this.prev;
        if (segment2 == null) {
            Intrinsics.throwNpe();
        }
        this.writeTo(segment2, n3);
        this.pop();
        SegmentPool.recycle(this);
    }

    public final Segment pop() {
        Segment segment = this.next;
        if (segment == this) {
            segment = null;
        }
        Segment segment2 = this.prev;
        if (segment2 == null) {
            Intrinsics.throwNpe();
        }
        segment2.next = this.next;
        segment2 = this.next;
        if (segment2 == null) {
            Intrinsics.throwNpe();
        }
        segment2.prev = this.prev;
        this.next = segment2 = (Segment)null;
        this.prev = segment2;
        return segment;
    }

    public final Segment push(Segment segment) {
        Intrinsics.checkParameterIsNotNull(segment, "segment");
        segment.prev = this;
        segment.next = this.next;
        Segment segment2 = this.next;
        if (segment2 == null) {
            Intrinsics.throwNpe();
        }
        segment2.prev = segment;
        this.next = segment;
        return segment;
    }

    public final Segment sharedCopy() {
        this.shared = true;
        return new Segment(this.data, this.pos, this.limit, true, false);
    }

    public final Segment split(int n) {
        Object object;
        Segment segment;
        int n2 = n > 0 && n <= this.limit - this.pos ? 1 : 0;
        if (n2 == 0) throw (Throwable)new IllegalArgumentException("byteCount out of range".toString());
        if (n >= 1024) {
            segment = this.sharedCopy();
        } else {
            segment = SegmentPool.take();
            object = this.data;
            byte[] arrby = segment.data;
            n2 = this.pos;
            ArraysKt.copyInto$default((byte[])object, arrby, 0, n2, n2 + n, 2, null);
        }
        segment.limit = segment.pos + n;
        this.pos += n;
        object = this.prev;
        if (object == null) {
            Intrinsics.throwNpe();
        }
        ((Segment)object).push(segment);
        return segment;
    }

    public final Segment unsharedCopy() {
        byte[] arrby = this.data;
        arrby = Arrays.copyOf(arrby, arrby.length);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOf(this, size)");
        return new Segment(arrby, this.pos, this.limit, false, true);
    }

    public final void writeTo(Segment segment, int n) {
        int n2;
        byte[] arrby;
        Intrinsics.checkParameterIsNotNull(segment, "sink");
        if (!segment.owner) throw (Throwable)new IllegalStateException("only owner can write".toString());
        int n3 = segment.limit;
        if (n3 + n > 8192) {
            if (segment.shared) throw (Throwable)new IllegalArgumentException();
            n2 = segment.pos;
            if (n3 + n - n2 > 8192) throw (Throwable)new IllegalArgumentException();
            arrby = segment.data;
            ArraysKt.copyInto$default(arrby, arrby, 0, n2, n3, 2, null);
            segment.limit -= segment.pos;
            segment.pos = 0;
        }
        arrby = this.data;
        byte[] arrby2 = segment.data;
        n2 = segment.limit;
        n3 = this.pos;
        ArraysKt.copyInto(arrby, arrby2, n2, n3, n3 + n);
        segment.limit += n;
        this.pos += n;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2={"Lokio/Segment$Companion;", "", "()V", "SHARE_MINIMUM", "", "SIZE", "okio"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

}


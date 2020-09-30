/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import okio.Segment;

@Metadata(bv={1, 0, 3}, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u000eH\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u0007J\b\u0010\u0014\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u001e\u0010\f\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u000e0\rX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000f\u00a8\u0006\u0015"}, d2={"Lokio/SegmentPool;", "", "()V", "HASH_BUCKET_COUNT", "", "LOCK", "Lokio/Segment;", "MAX_SIZE", "getMAX_SIZE", "()I", "byteCount", "getByteCount", "hashBuckets", "", "Ljava/util/concurrent/atomic/AtomicReference;", "[Ljava/util/concurrent/atomic/AtomicReference;", "firstRef", "recycle", "", "segment", "take", "okio"}, k=1, mv={1, 1, 16})
public final class SegmentPool {
    private static final int HASH_BUCKET_COUNT;
    public static final SegmentPool INSTANCE;
    private static final Segment LOCK;
    private static final int MAX_SIZE = 65536;
    private static final AtomicReference<Segment>[] hashBuckets;

    static {
        int n;
        INSTANCE = new SegmentPool();
        MAX_SIZE = 65536;
        int n2 = 0;
        LOCK = new Segment(new byte[0], 0, 0, false, false);
        HASH_BUCKET_COUNT = n = Integer.highestOneBit(Runtime.getRuntime().availableProcessors() * 2 - 1);
        AtomicReference[] arratomicReference = new AtomicReference[n];
        do {
            if (n2 >= n) {
                hashBuckets = arratomicReference;
                return;
            }
            arratomicReference[n2] = new AtomicReference();
            ++n2;
        } while (true);
    }

    private SegmentPool() {
    }

    private final AtomicReference<Segment> firstRef() {
        Thread thread2 = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
        int n = (int)(thread2.getId() & (long)HASH_BUCKET_COUNT - 1L);
        return hashBuckets[n];
    }

    @JvmStatic
    public static final void recycle(Segment segment) {
        Intrinsics.checkParameterIsNotNull(segment, "segment");
        int n = segment.next == null && segment.prev == null ? 1 : 0;
        if (n == 0) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
        if (segment.shared) {
            return;
        }
        AtomicReference<Segment> atomicReference = INSTANCE.firstRef();
        Segment segment2 = atomicReference.get();
        if (segment2 == LOCK) {
            return;
        }
        n = segment2 != null ? segment2.limit : 0;
        if (n >= MAX_SIZE) {
            return;
        }
        segment.next = segment2;
        segment.pos = 0;
        segment.limit = n + 8192;
        if (atomicReference.compareAndSet(segment2, segment)) return;
        segment.next = null;
    }

    @JvmStatic
    public static final Segment take() {
        AtomicReference<Segment> atomicReference = INSTANCE.firstRef();
        Segment segment = atomicReference.getAndSet(LOCK);
        if (segment == LOCK) {
            return new Segment();
        }
        if (segment == null) {
            atomicReference.set(null);
            return new Segment();
        }
        atomicReference.set(segment.next);
        segment.next = null;
        segment.limit = 0;
        return segment;
    }

    public final int getByteCount() {
        Segment segment = this.firstRef().get();
        if (segment == null) return 0;
        return segment.limit;
    }

    public final int getMAX_SIZE() {
        return MAX_SIZE;
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okio.Throttler$sink
 *  okio.Throttler$source
 */
package okio;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Sink;
import okio.Source;
import okio.Throttler;

@Metadata(bv={1, 0, 3}, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0002B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u001d\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b\fJ$\u0010\u0006\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u0004H\u0007J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u0011J\u0015\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b\u0013J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0004H\u0002J\f\u0010\u0016\u001a\u00020\u0004*\u00020\u0004H\u0002J\f\u0010\u0017\u001a\u00020\u0004*\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lokio/Throttler;", "", "()V", "allocatedUntil", "", "(J)V", "bytesPerSecond", "maxByteCount", "waitByteCount", "byteCountOrWaitNanos", "now", "byteCount", "byteCountOrWaitNanos$okio", "", "sink", "Lokio/Sink;", "source", "Lokio/Source;", "take", "take$okio", "waitNanos", "nanosToWait", "bytesToNanos", "nanosToBytes", "okio"}, k=1, mv={1, 1, 16})
public final class Throttler {
    private long allocatedUntil;
    private long bytesPerSecond;
    private long maxByteCount;
    private long waitByteCount;

    public Throttler() {
        this(System.nanoTime());
    }

    public Throttler(long l) {
        this.allocatedUntil = l;
        this.waitByteCount = 8192L;
        this.maxByteCount = 262144L;
    }

    public static /* synthetic */ void bytesPerSecond$default(Throttler throttler, long l, long l2, long l3, int n, Object object) {
        if ((n & 2) != 0) {
            l2 = throttler.waitByteCount;
        }
        if ((n & 4) != 0) {
            l3 = throttler.maxByteCount;
        }
        throttler.bytesPerSecond(l, l2, l3);
    }

    private final long bytesToNanos(long l) {
        return l * 1000000000L / this.bytesPerSecond;
    }

    private final long nanosToBytes(long l) {
        return l * this.bytesPerSecond / 1000000000L;
    }

    private final void waitNanos(long l) {
        long l2 = l / 1000000L;
        ((Object)this).wait(l2, (int)(l - 1000000L * l2));
    }

    public final long byteCountOrWaitNanos$okio(long l, long l2) {
        if (this.bytesPerSecond == 0L) {
            return l2;
        }
        long l3 = Math.max(this.allocatedUntil - l, 0L);
        long l4 = this.maxByteCount - this.nanosToBytes(l3);
        if (l4 >= l2) {
            this.allocatedUntil = l + l3 + this.bytesToNanos(l2);
            return l2;
        }
        long l5 = this.waitByteCount;
        if (l4 >= l5) {
            this.allocatedUntil = l + this.bytesToNanos(this.maxByteCount);
            return l4;
        }
        if ((l3 += this.bytesToNanos((l2 = Math.min(l5, l2)) - this.maxByteCount)) != 0L) return -l3;
        this.allocatedUntil = l + this.bytesToNanos(this.maxByteCount);
        return l2;
    }

    public final void bytesPerSecond(long l) {
        Throttler.bytesPerSecond$default(this, l, 0L, 0L, 6, null);
    }

    public final void bytesPerSecond(long l, long l2) {
        Throttler.bytesPerSecond$default(this, l, l2, 0L, 4, null);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void bytesPerSecond(long l, long l2, long l3) {
        synchronized (this) {
            boolean bl = true;
            boolean bl2 = l >= 0L;
            if (!bl2) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Failed requirement.".toString());
                throw (Throwable)illegalArgumentException;
            }
            bl2 = l2 > 0L;
            if (!bl2) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Failed requirement.".toString());
                throw (Throwable)illegalArgumentException;
            }
            bl2 = l3 >= l2 ? bl : false;
            if (bl2) {
                this.bytesPerSecond = l;
                this.waitByteCount = l2;
                this.maxByteCount = l3;
                ((Object)this).notifyAll();
                Unit unit = Unit.INSTANCE;
                return;
            }
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Failed requirement.".toString());
            throw (Throwable)illegalArgumentException;
        }
    }

    public final Sink sink(Sink sink2) {
        Intrinsics.checkParameterIsNotNull(sink2, "sink");
        return new ForwardingSink(this, sink2, sink2){
            final /* synthetic */ Sink $sink;
            final /* synthetic */ Throttler this$0;
            {
                this.this$0 = throttler;
                this.$sink = sink2;
                super(sink3);
            }

            public void write(okio.Buffer buffer, long l) throws java.io.IOException {
                Intrinsics.checkParameterIsNotNull(buffer, "source");
                while (l > 0L) {
                    try {
                        long l2 = this.this$0.take$okio(l);
                        super.write(buffer, l2);
                        l -= l2;
                    }
                    catch (java.lang.InterruptedException interruptedException) {
                        java.lang.Thread.currentThread().interrupt();
                        throw (Throwable)new java.io.InterruptedIOException("interrupted");
                    }
                }
            }
        };
    }

    public final Source source(Source source2) {
        Intrinsics.checkParameterIsNotNull(source2, "source");
        return new ForwardingSource(this, source2, source2){
            final /* synthetic */ Source $source;
            final /* synthetic */ Throttler this$0;
            {
                this.this$0 = throttler;
                this.$source = source2;
                super(source3);
            }

            public long read(okio.Buffer buffer, long l) {
                Intrinsics.checkParameterIsNotNull(buffer, "sink");
                try {
                    return super.read(buffer, this.this$0.take$okio(l));
                }
                catch (java.lang.InterruptedException interruptedException) {
                    java.lang.Thread.currentThread().interrupt();
                    throw (Throwable)new java.io.InterruptedIOException("interrupted");
                }
            }
        };
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final long take$okio(long l) {
        boolean bl = l > 0L;
        if (!bl) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
        synchronized (this) {
            long l2;
            while ((l2 = this.byteCountOrWaitNanos$okio(System.nanoTime(), l)) < 0L) {
                l2 = -l2;
                this.waitNanos(l2);
            }
            return l2;
        }
    }
}


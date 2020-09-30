/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okio.Timeout$Companion$NONE
 */
package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\u0000H\u0016J\b\u0010\t\u001a\u00020\u0000H\u0016J\u0016\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rJ\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u001f\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00002\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0012H\u0086\bJ\b\u0010\u0013\u001a\u00020\u000fH\u0016J\u0018\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u0007\u001a\u00020\u0004H\u0016J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lokio/Timeout;", "", "()V", "deadlineNanoTime", "", "hasDeadline", "", "timeoutNanos", "clearDeadline", "clearTimeout", "deadline", "duration", "unit", "Ljava/util/concurrent/TimeUnit;", "intersectWith", "", "other", "block", "Lkotlin/Function0;", "throwIfReached", "timeout", "waitUntilNotified", "monitor", "Companion", "okio"}, k=1, mv={1, 1, 16})
public class Timeout {
    public static final Companion Companion = new Companion(null);
    public static final Timeout NONE = new Timeout(){

        public Timeout deadlineNanoTime(long l) {
            return this;
        }

        public void throwIfReached() {
        }

        public Timeout timeout(long l, TimeUnit timeUnit) {
            Intrinsics.checkParameterIsNotNull((Object)((Object)timeUnit), "unit");
            return this;
        }
    };
    private long deadlineNanoTime;
    private boolean hasDeadline;
    private long timeoutNanos;

    public Timeout clearDeadline() {
        this.hasDeadline = false;
        return this;
    }

    public Timeout clearTimeout() {
        this.timeoutNanos = 0L;
        return this;
    }

    public final Timeout deadline(long l, TimeUnit object) {
        Intrinsics.checkParameterIsNotNull(object, "unit");
        boolean bl = l > 0L;
        if (bl) {
            return this.deadlineNanoTime(System.nanoTime() + ((TimeUnit)((Object)object)).toNanos(l));
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("duration <= 0: ");
        ((StringBuilder)object).append(l);
        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
    }

    public long deadlineNanoTime() {
        if (!this.hasDeadline) throw (Throwable)new IllegalStateException("No deadline".toString());
        return this.deadlineNanoTime;
    }

    public Timeout deadlineNanoTime(long l) {
        this.hasDeadline = true;
        this.deadlineNanoTime = l;
        return this;
    }

    public boolean hasDeadline() {
        return this.hasDeadline;
    }

    public final void intersectWith(Timeout timeout2, Function0<Unit> function0) {
        Intrinsics.checkParameterIsNotNull(timeout2, "other");
        Intrinsics.checkParameterIsNotNull(function0, "block");
        long l = this.timeoutNanos();
        this.timeout(Companion.minTimeout(timeout2.timeoutNanos(), this.timeoutNanos()), TimeUnit.NANOSECONDS);
        if (this.hasDeadline()) {
            long l2 = this.deadlineNanoTime();
            if (timeout2.hasDeadline()) {
                this.deadlineNanoTime(Math.min(this.deadlineNanoTime(), timeout2.deadlineNanoTime()));
            }
            try {
                function0.invoke();
                return;
            }
            finally {
                InlineMarker.finallyStart(1);
                this.timeout(l, TimeUnit.NANOSECONDS);
                if (timeout2.hasDeadline()) {
                    this.deadlineNanoTime(l2);
                }
                InlineMarker.finallyEnd(1);
            }
        }
        if (timeout2.hasDeadline()) {
            this.deadlineNanoTime(timeout2.deadlineNanoTime());
        }
        try {
            function0.invoke();
            return;
        }
        finally {
            InlineMarker.finallyStart(1);
            this.timeout(l, TimeUnit.NANOSECONDS);
            if (timeout2.hasDeadline()) {
                this.clearDeadline();
            }
            InlineMarker.finallyEnd(1);
        }
    }

    public void throwIfReached() throws IOException {
        if (!Thread.interrupted()) {
            if (!this.hasDeadline) return;
            if (this.deadlineNanoTime - System.nanoTime() <= 0L) throw (Throwable)new InterruptedIOException("deadline reached");
            return;
        }
        Thread.currentThread().interrupt();
        throw (Throwable)new InterruptedIOException("interrupted");
    }

    public Timeout timeout(long l, TimeUnit object) {
        Intrinsics.checkParameterIsNotNull(object, "unit");
        boolean bl = l >= 0L;
        if (bl) {
            this.timeoutNanos = ((TimeUnit)((Object)object)).toNanos(l);
            return this;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("timeout < 0: ");
        ((StringBuilder)object).append(l);
        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
    }

    public long timeoutNanos() {
        return this.timeoutNanos;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public final void waitUntilNotified(Object var1_1) throws InterruptedIOException {
        Intrinsics.checkParameterIsNotNull(var1_1, "monitor");
        try {
            var2_3 = this.hasDeadline();
            var3_4 = this.timeoutNanos();
            var5_5 = 0L;
            if (!var2_3 && var3_4 == 0L) {
                var1_1.wait();
                return;
            }
            var7_6 = System.nanoTime();
            if (var2_3 && var3_4 != 0L) {
                var3_4 = Math.min(var3_4, this.deadlineNanoTime() - var7_6);
            } else if (var2_3) {
                var3_4 = this.deadlineNanoTime() - var7_6;
            }
            if (var3_4 <= 0L) ** GOTO lbl26
            var5_5 = var3_4 / 1000000L;
        }
        catch (InterruptedException var1_2) {
            Thread.currentThread().interrupt();
            throw (Throwable)new InterruptedIOException("interrupted");
        }
        Long.signum(var5_5);
        var9_7 = (int)(var3_4 - 1000000L * var5_5);
        var1_1.wait(var5_5, var9_7);
        var5_5 = System.nanoTime() - var7_6;
lbl26: // 2 sources:
        if (var5_5 < var3_4) {
            return;
        }
        var1_1 = new InterruptedIOException("timeout");
        throw (Throwable)var1_1;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lokio/Timeout$Companion;", "", "()V", "NONE", "Lokio/Timeout;", "minTimeout", "", "aNanos", "bNanos", "okio"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final long minTimeout(long l, long l2) {
            if (l == 0L) return l2;
            if (l2 == 0L) {
                return l;
            }
            if (l >= l2) return l2;
            return l;
        }
    }

}

